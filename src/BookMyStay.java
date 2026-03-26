import java.util.*;

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
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- BOOKING QUEUE ----------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // REMOVE (processing now)
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------- BOOKING SERVICE ----------
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type -> allocated room IDs
    private HashMap<String, Set<String>> allocationMap = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingRequestQueue queue) {

        System.out.println("\n=== Processing Bookings ===");

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            System.out.println("\nProcessing: " + r.getGuestName());

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type.substring(0, 2).toUpperCase() + roomCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map allocation
                    allocationMap.putIfAbsent(type, new HashSet<>());
                    allocationMap.get(type).add(roomId);

                    // Reduce inventory (IMPORTANT)
                    inventory.reduceAvailability(type);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);

                }

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\n=== Allocated Rooms ===");
        for (Map.Entry<String, Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 6.0) ===");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail

        queue.addRequest(new Reservation("David", "Double Room"));
        queue.addRequest(new Reservation("Eve", "Suite Room"));

        // Booking Service
        BookingService service = new BookingService(inventory);

        // Process bookings
        service.processBookings(queue);

        // Show results
        service.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings procesgit add .ed safely (No double booking).");
    }