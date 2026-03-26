import java.util.*;

// ---------- ADD-ON SERVICE ----------
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// ---------- ADD-ON SERVICE MANAGER ----------
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() + " to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }

        return total;
    }
}

// ---------- MAIN ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Version 7.0) ===\n");

        // Assume reservation IDs from UC6
        String res1 = "SI1";
        String res2 = "DO1";

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 500);
        AddOnService breakfast = new AddOnService("Breakfast", 800);
        AddOnService parking = new AddOnService("Parking", 300);

        // Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services to reservations
        manager.addService(res1, wifi);
        manager.addService(res1, breakfast);

        manager.addService(res2, parking);

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + manager.calculateTotalCost(res2));

        System.out.println("\nCore booking & inventory remain unchanged.");
    }
}