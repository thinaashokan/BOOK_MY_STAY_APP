import java.util.*;

// ---------- RESERVATION ----------
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}

// ---------- BOOKING HISTORY ----------
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\n=== Booking History ===");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }
}

// ---------- REPORT SERVICE ----------
class BookingReportService {

    // Generate summary report
    public void generateReport(List<Reservation> history) {

        System.out.println("\n=== Booking Report Summary ===");

        if (history.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : history) {
            String type = r.getRoomType();
            roomTypeCount.put(type, roomTypeCount.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }

        System.out.println("Total Bookings: " + history.size());
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 8.0) ===\n");

        // Booking history
        BookingHistory history = new BookingHistory();

        // Assume confirmed bookings from UC6
        Reservation r1 = new Reservation("Alice", "Single Room", "SI1");
        Reservation r2 = new Reservation("Bob", "Double Room", "DO1");
        Reservation r3 = new Reservation("Charlie", "Single Room", "SI2");

        // Store history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Display history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations());

        System.out.println("\nReporting completed (Read-only operation).");
    }
}