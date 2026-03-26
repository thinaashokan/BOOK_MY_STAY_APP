import java.util.*;

// ---------- RESERVATION ----------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// ---------- INVENTORY ----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- BOOKING HISTORY ----------
class BookingHistory {
    private Map<String, Reservation> history = new HashMap<>();

    public void addReservation(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return history.get(id);
    }

    public void removeReservation(String id) {
        history.remove(id);
    }

    public void displayHistory() {
        System.out.println("\n=== Booking History ===");
        if (history.isEmpty()) {
            System.out.println("No active bookings.");
            return;
        }
        for (Reservation r : history.values()) {
            System.out.println(r.getReservationId() + " - " + r.getGuestName()
                    + " (" + r.getRoomType() + ")");
        }
    }
}

// ---------- CANCELLATION SERVICE ----------
class CancellationService {

    private BookingHistory history;
    private RoomInventory inventory;

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Push to stack (LIFO)
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Remove booking
        history.removeReservation(reservationId);

        System.out.println("Cancellation Successful for " + r.getGuestName());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recent Cancellations): " + rollbackStack);
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 10.0) ===\n");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Assume confirmed bookings (from UC6)
        Reservation r1 = new Reservation("SI1", "Alice", "Single Room");
        Reservation r2 = new Reservation("DO1", "Bob", "Double Room");

        history.addReservation(r1);
        history.addReservation(r2);

        history.displayHistory();

        // Cancellation service
        CancellationService cancelService =
                new CancellationService(history, inventory);

        // Cancel booking
        cancelService.cancelBooking("SI1");

        // Invalid cancellation
        cancelService.cancelBooking("XX1");

        // Display updated state
        history.displayHistory();
        inventory.displayInventory();
        cancelService.displayRollbackStack();

        System.out.println("\nSystem state restored successfully.");
    }
}