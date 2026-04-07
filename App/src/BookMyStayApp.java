import java.util.*;
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /* Add service to reservation */
    public void addService(String reservationId, Service service) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null) {
            services = new ArrayList<>();
            reservationServices.put(reservationId, services);
        }

        services.add(service);
        System.out.println("Service added to Reservation " + reservationId + ": " + service.getServiceName());
    }

    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + reservationId);
            return;
        }

        System.out.println("\nAdd-On Services for Reservation " + reservationId + ":");

        for (Service s : services) {
            System.out.println("- " + s);
        }
    }

    public double calculateTotalCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spa = new Service("Spa Access", 2000);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Cost: ₹" + totalCost);

        System.out.println("\nNote: Core booking and inventory state remain unchanged.");
    }
}