import java.util.*;

// ---------- CUSTOM EXCEPTION ----------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------- RESERVATION ----------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

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

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {
        int current = getAvailability(type);

        if (current <= 0) {
            throw new InvalidBookingException("No availability for " + type);
        }

        inventory.put(type, current - 1);
    }
}

// ---------- VALIDATOR ----------
class InvalidBookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Check null / empty
        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Check valid room type
        if (inventory.getAvailability(r.getRoomType()) == -1) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Check availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

// ---------- BOOKING SERVICE ----------
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation r) {
        try {
            // Validate first (Fail-Fast)
            InvalidBookingValidator.validate(r, inventory);

            // Allocate (only if valid)
            inventory.reduceAvailability(r.getRoomType());

            System.out.println("Booking Successful for " + r.getGuestName()
                    + " (" + r.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 9.0) ===\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        Reservation r1 = new Reservation("Alice", "Single Room");

        // Invalid room type
        Reservation r2 = new Reservation("Bob", "Luxury Room");

        // No availability case
        Reservation r3 = new Reservation("Charlie", "Single Room");

        // Empty name
        Reservation r4 = new Reservation("", "Double Room");

        service.processBooking(r1); // success
        service.processBooking(r2); // invalid type
        service.processBooking(r3); // no availability
        service.processBooking(r4); // empty name

        System.out.println("\nSystem continues running safely.");
    }
}