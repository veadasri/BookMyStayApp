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

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
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
    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Booking request added: " + r.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}


class RoomAllocationService {

    private RoomInventory inventory;

    private Set<String> allocatedRoomIds = new HashSet<>();

    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation reservation) {

        String type = reservation.getRoomType();

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
        if (inventory.getAvailability(type) > 0) {

            String roomId = type.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);

            while (allocatedRoomIds.contains(roomId)) {
                roomId = type.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
            }

            allocatedRoomIds.add(roomId);

            roomAllocations.putIfAbsent(type, new HashSet<>());
            roomAllocations.get(type).add(roomId);

            inventory.decrementRoom(type);

            System.out.println("Reservation Confirmed for " + reservation.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Assigned Room ID: " + roomId + "\n");

        } else {
            System.out.println("Reservation Failed for " + reservation.getGuestName() +
                    " (No " + type + " available)\n");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay - Hotel Booking System");
        System.out.println("Application Version: v6.1\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Suite Room")); // may fail

        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            allocationService.processReservation(r);
        }

        inventory.displayInventory();
    }
}