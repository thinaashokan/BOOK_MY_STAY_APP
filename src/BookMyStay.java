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

// ---------- THREAD-SAFE INVENTORY ----------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // synchronized method (critical section)
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(guestName + " booking SUCCESS (" + roomType + ")");
            inventory.put(roomType, available - 1);
            return true;
        } else {
            System.out.println(guestName + " booking FAILED (No rooms)");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// ---------- SHARED QUEUE ----------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// ---------- THREAD PROCESSOR ----------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // synchronized retrieval
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // critical section (inventory update)
            inventory.allocateRoom(r.getRoomType(), r.getGuestName());
        }
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 11.0) ===\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple guest requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail

        // Multiple threads (concurrent users)
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        // wait for threads
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking handled safely.");
    }
}