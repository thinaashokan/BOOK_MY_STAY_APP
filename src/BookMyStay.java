// Abstract Class
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Getter methods (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method
    public abstract void displayRoomDetails();
}

// Single Room Class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Double Room Class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Suite Room Class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Main Class (Entry Point)
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Welcome to Book My Stay App (Version 2.0) ===\n");

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display details
        System.out.println("--- Single Room ---");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailability + "\n");

        System.out.println("--- Double Room ---");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailability + "\n");

        System.out.println("--- Suite Room ---");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailability + "\n");

        System.out.println("Thank you for using Book My Stay App!");
    }
}