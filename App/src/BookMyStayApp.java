import java.io.*;
import java.util.*;

class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<String> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<String> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save state to file
    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (Exception e) {

            System.out.println("No valid persistence file found. Starting fresh.");
            return null;
        }
    }
}

class BookingSystem {

    Map<String, Integer> inventory = new HashMap<>();
    List<String> bookingHistory = new ArrayList<>();

    public BookingSystem() {

        SystemState state = PersistenceService.loadState();

        if (state != null) {
            inventory = state.inventory;
            bookingHistory = state.bookingHistory;
        } else {
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }
    }

    public void bookRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);
            bookingHistory.add("Booked " + roomType);

            System.out.println("Booking successful.");

        } else {
            System.out.println("Room not available.");
        }
    }

    public void showInventory() {

        System.out.println("\nInventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }

    public void shutdown() {

        SystemState state = new SystemState(inventory, bookingHistory);
        PersistenceService.saveState(state);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.showInventory();

        system.bookRoom("Single");
        system.bookRoom("Double");

        system.showInventory();

        system.shutdown();
    }
}