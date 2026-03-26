import java.io.*;
import java.util.*;

// ---------- RESERVATION ----------
class Reservation implements Serializable {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public String toString() {
        return guestName + " (" + roomType + ")";
    }
}

// ---------- SYSTEM STATE ----------
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// ---------- PERSISTENCE SERVICE ----------
class PersistenceService {

    private static final String FILE_NAME = "bookmystay.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting safe mode.");
        }
        return null;
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 12.0) ===\n");

        PersistenceService persistence = new PersistenceService();

        // Try loading previous state
        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();
        }

        // Display current state
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings);

        // Simulate new booking
        Reservation r1 = new Reservation("Alice", "Single Room");
        bookings.add(r1);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("\nNew Booking Added: " + r1);

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        persistence.save(newState);

        System.out.println("\nSystem ready for restart with saved data.");
    }
}