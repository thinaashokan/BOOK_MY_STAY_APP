import java.util.*;

// ---------- ROOM DOMAIN (Same as UC2) ----------
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    public abstract void display();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    public void display() {
        System.out.println("Type: " + getType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    public void display() {
        System.out.println("Type: " + getType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    public void display() {
        System.out.println("Type: " + getType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

// ---------- INVENTORY (Same as UC3) ----------
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example: unavailable
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

// ---------- SEARCH SERVICE (NEW - UC4) ----------
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("=== Available Rooms ===");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Filter: only show available rooms
            if (available > 0) {
                room.display();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.prisntln("=== Book My Stay App (Version 4.0) ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("Search completed (No data modified).");
    }
}