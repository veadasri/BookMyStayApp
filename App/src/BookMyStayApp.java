import java.util.HashMap;
import java.util.Map;


class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay - Hotel Booking System");
        System.out.println("Application Version: v3.1\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nUpdating Suite Room availability...\n");
        inventory.updateAvailability("Suite Room", 2);

        inventory.displayInventory();
    }
}