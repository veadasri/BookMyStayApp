import java.util.*;

class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean active;

    public Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}

class InvalidCancellationException extends Exception {
    public InvalidCancellationException(String message) {
        super(message);
    }
}

class CancellationService {

    Map<String, Booking> bookings = new HashMap<>();
    Map<String, Integer> inventory = new HashMap<>();
    Stack<String> rollbackStack = new Stack<>();
    List<String> bookingHistory = new ArrayList<>();

    public CancellationService() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void confirmBooking(String bookingId, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
            System.out.println("Booking failed. Room not available.");
            return;
        }

        String roomId = roomType + "_" + UUID.randomUUID().toString().substring(0,4);

        Booking booking = new Booking(bookingId, roomType, roomId);

        bookings.put(bookingId, booking);
        inventory.put(roomType, inventory.get(roomType) - 1);

        bookingHistory.add("BOOKED: " + bookingId);

        System.out.println("Booking confirmed. Room ID: " + roomId);
    }

    public void cancelBooking(String bookingId) {

        try {

            if (!bookings.containsKey(bookingId)) {
                throw new InvalidCancellationException("Reservation does not exist.");
            }

            Booking booking = bookings.get(bookingId);

            if (!booking.active) {
                throw new InvalidCancellationException("Booking already cancelled.");
            }

            rollbackStack.push(booking.roomId);

            inventory.put(booking.roomType,
                    inventory.get(booking.roomType) + 1);


            booking.active = false;

            bookingHistory.add("CANCELLED: " + bookingId);

            System.out.println("Cancellation successful.");
            System.out.println("Released Room ID: " + rollbackStack.pop());

        } catch (InvalidCancellationException e) {

            System.out.println("Cancellation failed: " + e.getMessage());

        }
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }

    public void showHistory() {
        System.out.println("\nBooking History:");
        for (String record : bookingHistory) {
            System.out.println(record);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        service.confirmBooking("B101", "Single");
        service.confirmBooking("B102", "Double");

        service.showInventory();

        service.cancelBooking("B101");

        service.cancelBooking("B999");

        service.cancelBooking("B101");
        service.showInventory();

        service.showHistory();
    }
}