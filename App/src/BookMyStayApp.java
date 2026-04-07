import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomType {
    public static final String SINGLE = "Single";
    public static final String DOUBLE = "Double";
    public static final String SUITE = "Suite";
}

class InvalidBookingValidator {

    public static void validate(String roomType, int roomsRequested, Map<String, Integer> inventory)
            throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Number of rooms must be greater than zero.");
        }

        if (inventory.get(roomType) < roomsRequested) {
            throw new InvalidBookingException("Not enough rooms available.");
        }
    }
}

class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put(RoomType.SINGLE, 5);
        inventory.put(RoomType.DOUBLE, 3);
        inventory.put(RoomType.SUITE, 2);
    }

    public void bookRoom(String roomType, int roomsRequested) {
        try {
            InvalidBookingValidator.validate(roomType, roomsRequested, inventory);

            int remaining = inventory.get(roomType) - roomsRequested;
            inventory.put(roomType, remaining);

            System.out.println("✅ Booking successful!");
            System.out.println("Remaining " + roomType + " rooms: " + remaining);

        } catch (InvalidBookingException e) {
            System.out.println("❌ Booking failed: " + e.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println("\n📊 Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        bookingService.displayInventory();

        // Test cases
        bookingService.bookRoom(RoomType.SINGLE, 2);
        bookingService.bookRoom("Deluxe", 1);
        bookingService.bookRoom(RoomType.DOUBLE, -1);
        bookingService.bookRoom(RoomType.SUITE, 5);

        bookingService.displayInventory();
    }
}