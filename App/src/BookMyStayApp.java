import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 7500);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

class SearchService {

    private RoomInventory inventory;
    private HashMap<String, Room> rooms;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;

        rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());
    }

    public void searchAvailableRooms() {

        System.out.println("Available Rooms:\n");

        for (String type : rooms.keySet()) {

            int available = inventory.getAvailability(type);

            if (available > 0) {
                Room room = rooms.get(type);

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay - Hotel Booking System");
        System.out.println("Application Version: v4.1\n");

        RoomInventory inventory = new RoomInventory();

        SearchService searchService = new SearchService(inventory);

        searchService.searchAvailableRooms();
    }
}