import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println(request.guestName + " requested " + request.roomType);
    }

    public synchronized BookingRequest getRequest() {
        return bookingQueue.poll();
    }

    public synchronized void allocateRoom(BookingRequest request) {

        int available = inventory.getOrDefault(request.roomType, 0);

        if (available > 0) {

            inventory.put(request.roomType, available - 1);

            System.out.println(
                    "Room allocated to " + request.guestName +
                            " | Remaining " + request.roomType +
                            " rooms: " + (available - 1)
            );

        } else {
            System.out.println(
                    "No rooms available for " + request.guestName
            );
        }
    }

    public void showInventory() {
        System.out.println("\nFinal Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system) {
        this.system = system;
    }

    public void run() {

        BookingRequest request;

        while ((request = system.getRequest()) != null) {
            system.allocateRoom(request);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

        BookingSystem system = new BookingSystem();

        system.addRequest(new BookingRequest("Guest1", "Single"));
        system.addRequest(new BookingRequest("Guest2", "Single"));
        system.addRequest(new BookingRequest("Guest3", "Single"));
        system.addRequest(new BookingRequest("Guest4", "Double"));
        system.addRequest(new BookingRequest("Guest5", "Double"));

        Thread t1 = new BookingProcessor(system);
        Thread t2 = new BookingProcessor(system);
        Thread t3 = new BookingProcessor(system);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        system.showInventory();
    }
}