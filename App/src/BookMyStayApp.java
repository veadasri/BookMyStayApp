import java.util.*;

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in booking history: " + reservation.getReservationId());
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n------ Booking History Report ------");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }

        System.out.println("-----------------------------------");
        System.out.println("Total Bookings: " + reservations.size());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("RES101", "Alice", "Deluxe", 2);
        Reservation r2 = new Reservation("RES102", "Bob", "Suite", 3);
        Reservation r3 = new Reservation("RES103", "Charlie", "Standard", 1);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        List<Reservation> storedReservations = history.getAllReservations();
        reportService.generateReport(storedReservations);
    }
}