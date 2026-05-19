import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class Main {
    static class Passenger {
        private int passengerId;
        private String name;
        private String email;
        public Passenger(int passengerId, String name, String email) {
            this.passengerId = passengerId;
            this.name = name;
            this.email = email;
        }
        public int getPassengerId() {
            return passengerId;
        }
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return "Passenger ID: " + passengerId +
                    ", Name: " + name +
                    ", Email: " + email;
        }
    }
    static class Flight {
        private String flightId;
        private String source;
        private String destination;
        private LocalDateTime departureTime;
        private double basePrice;
        private String[][] seats;
        private int totalSeats;
        private int bookedSeats;
        private double revenue;
        public Flight(String flightId, String source, String destination, LocalDateTime departureTime, double basePrice, int rows, int cols) {
            this.flightId = flightId;
            this.source = source;
            this.destination = destination;
            this.departureTime = departureTime;
            this.basePrice = basePrice;
            seats = new String[rows][cols];
            totalSeats = rows * cols;
            bookedSeats = 0;
            revenue = 0;
        }
        public String getFlightId() {
            return flightId;
        }
        public String getRoute() {
            return source + " -> " + destination;
        }
        public LocalDateTime getDepartureTime() {
            return departureTime;
        }
        public int getAvailableSeats() {
            return totalSeats - bookedSeats;
        }
        public double getRevenue() {
            return revenue;
        }
        public double calculatePrice() {
            double occupancy = (double) bookedSeats / totalSeats;
            if (occupancy >= 0.8)
                return basePrice * 1.8;
            else if (occupancy >= 0.6)
                return basePrice * 1.5;
            else if (occupancy >= 0.4)
                return basePrice * 1.3;
            else
                return basePrice;
        }
        public boolean bookSeat(int row, int col, String passengerName) {
            if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length) {
                System.out.println("Invalid Seat Position.");
                return false;
            }
            if (seats[row][col] != null) {
                System.out.println("Seat already booked.");
                return false;
            }
            synchronized (this) {
                if (seats[row][col] == null) {
                    seats[row][col] = passengerName;
                    bookedSeats++;
                    revenue += calculatePrice();
                    return true;
                }
            }
            return false;
        }
        public boolean cancelSeat(int row, int col) {
            if (seats[row][col] == null) {
                System.out.println("Seat is already empty.");
                return false;
            }
            LocalDateTime now = LocalDateTime.now();
            long hours = ChronoUnit.HOURS.between(now, departureTime);
            if (hours < 2) {
                System.out.println("Cancellation not allowed within 2 hours.");
                return false;
            }
            seats[row][col] = null;
            bookedSeats--;
            System.out.println("Ticket Cancelled Successfully.");
            return true;
        }
        public void displaySeats() {
            System.out.println("\nSeat Matrix:");
            System.out.println("X = Booked | O = Available\n");
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    if (seats[i][j] == null)
                        System.out.print("O ");
                    else
                        System.out.print("X ");
                }
                System.out.println();
            }
        }
        @Override
        public String toString() {
            return "\nFlight ID: " + flightId +
                    "\nRoute: " + source + " -> " + destination +
                    "\nDeparture: " + departureTime +
                    "\nAvailable Seats: " + getAvailableSeats() +
                    "\nCurrent Ticket Price: ₹" + calculatePrice() +
                    "\nRevenue: ₹" + revenue;
        }
    }
    static class Booking {
        private int bookingId;
        private Passenger passenger;
        private Flight flight;
        private int row;
        private int col;
        private double amount;
        private boolean paymentStatus;
        public Booking(int bookingId, Passenger passenger, Flight flight, int row, int col, double amount) {
            this.bookingId = bookingId;
            this.passenger = passenger;
            this.flight = flight;
            this.row = row;
            this.col = col;
            this.amount = amount;
            this.paymentStatus = false;
        }
        public void processPayment() {
            System.out.println("Processing Payment of ₹" + amount + " ...");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            paymentStatus = true;
            System.out.println("Payment Successful.");
        }
        @Override
        public String toString() {
            return "\nBooking ID: " + bookingId +
                    "\nPassenger: " + passenger.getName() +
                    "\nFlight: " + flight.getFlightId() +
                    "\nSeat: [" + row + "," + col + "]" +
                    "\nAmount: ₹" + amount +
                    "\nPayment Status: " + paymentStatus;
        }
    }
    static Map<Integer, Passenger> passengerDB = new HashMap<>();
    static Map<String, Flight> flightDB = new HashMap<>();
    static List<Booking> bookingDB = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int passengerCounter = 1;
    static int bookingCounter = 1001;
    public static void registerPassenger() {
        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        Passenger p = new Passenger(passengerCounter++, name, email);
        passengerDB.put(p.getPassengerId(), p);
        System.out.println("\nPassenger Registered Successfully.");
        System.out.println(p);
    }
    public static void addFlight() {
        System.out.print("Flight ID: ");
        String id = sc.nextLine();
        System.out.print("Source: ");
        String source = sc.nextLine();
        System.out.print("Destination: ");
        String destination = sc.nextLine();
        System.out.print("Departure After How Many Hours From Now?: ");
        int hrs = Integer.parseInt(sc.nextLine());
        LocalDateTime departure = LocalDateTime.now().plusHours(hrs);
        System.out.print("Base Ticket Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Rows: ");
        int rows = Integer.parseInt(sc.nextLine());
        System.out.print("Columns: ");
        int cols = Integer.parseInt(sc.nextLine());
        Flight flight = new Flight(id, source, destination, departure, price, rows, cols);
        flightDB.put(id, flight);
        System.out.println("\nFlight Added Successfully.");
    }
    public static void viewFlights() {
        if (flightDB.isEmpty()) {
            System.out.println("No Flights Available.");
            return;
        }
        for (Flight f : flightDB.values()) {
            System.out.println(f);
        }
    }
    public static void bookTicket() {
        System.out.print("Enter Passenger ID: ");
        int pid = Integer.parseInt(sc.nextLine());
        Passenger passenger = passengerDB.get(pid);
        if (passenger == null) {
            System.out.println("Passenger Not Found.");
            return;
        }
        System.out.print("Enter Flight ID: ");
        String fid = sc.nextLine();
        Flight flight = flightDB.get(fid);
        if (flight == null) {
            System.out.println("Flight Not Found.");
            return;
        }
        flight.displaySeats();
        if (flight.getAvailableSeats() == 0) {
            System.out.println("Flight Fully Booked.");
            return;
        }
        System.out.print("Enter Seat Row: ");
        int row = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Seat Column: ");
        int col = Integer.parseInt(sc.nextLine());
        boolean booked = flight.bookSeat(row, col, passenger.getName());
        if (!booked) {
            return;
        }
        double amount = flight.calculatePrice();
        Booking booking =
                new Booking(bookingCounter++, passenger, flight, row, col, amount);
        booking.processPayment();
        bookingDB.add(booking);
        System.out.println("\nTicket Booked Successfully.");
        System.out.println(booking);
    }
    public static void cancelTicket() {
        System.out.print("Enter Flight ID: ");
        String fid = sc.nextLine();
        Flight flight = flightDB.get(fid);
        if (flight == null) {
            System.out.println("Flight Not Found.");
            return;
        }
        System.out.print("Enter Seat Row: ");
        int row = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Seat Column: ");
        int col = Integer.parseInt(sc.nextLine());
        flight.cancelSeat(row, col);
    }
    public static void generateReports() {
        System.out.println("\n========== AIRLINE REPORT ==========");
        double totalRevenue = 0;
        Flight topFlight = null;
        for (Flight f : flightDB.values()) {
            totalRevenue += f.getRevenue();
            if (topFlight == null || f.getRevenue() > topFlight.getRevenue()) {
                topFlight = f;
            }
        }
        System.out.println("Total Flights: " + flightDB.size());
        System.out.println("Total Passengers: " + passengerDB.size());
        System.out.println("Total Bookings: " + bookingDB.size());
        System.out.println("Total Revenue: ₹" + totalRevenue);
        if (topFlight != null) {
            System.out.println("\nMost Profitable Flight:");
            System.out.println(topFlight.getFlightId()
                    + " Revenue: ₹" + topFlight.getRevenue());
        }
        System.out.println("\n========== BOOKINGS ==========");
        for (Booking b : bookingDB) {
            System.out.println(b);
        }
    }
    public static void sqlQueries() {
        System.out.println("\n========== SQL QUERY OUTPUT ==========");
        System.out.println("\n1. Most Profitable Flights");
        Map<String, Double> revenueMap = new HashMap<>();
        for (Booking b : bookingDB) {
            String flightId = b.flight.getFlightId();
            revenueMap.put(flightId, revenueMap.getOrDefault(flightId, 0.0) + b.amount);
        }
        for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
            System.out.println("Flight ID: " + entry.getKey() + " | Revenue: ₹" + entry.getValue());
        }
        System.out.println("\n2. Highest Booking Routes");
        Map<String, Integer> routeBookings = new HashMap<>();
        for (Booking b : bookingDB) {
            String route = b.flight.getRoute();
            routeBookings.put(route, routeBookings.getOrDefault(route, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : routeBookings.entrySet()) {
            System.out.println("Route: " + entry.getKey() + " | Total Bookings: " + entry.getValue());
        }
    }
    public static void menu() {
        while (true) {
            System.out.println("1. Register Passenger");
            System.out.println("2. Add Flight");
            System.out.println("3. View Flights");
            System.out.println("4. Book Ticket");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. Generate Reports");
            System.out.println("7. SQL Query Output");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    registerPassenger();
                    break;
                case 2:
                    addFlight();
                    break;
                case 3:
                    viewFlights();
                    break;
                case 4:
                    bookTicket();
                    break;
                case 5:
                    cancelTicket();
                    break;
                case 6:
                    generateReports();
                    break;
                case 7:
                    sqlQueries();
                    break;
                case 8:
                    System.out.println("Thank You.");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
    public static void main(String[] args) {
        Flight f1 = new Flight(
                "AI101",
                "Chennai",
                "Delhi",
                LocalDateTime.now().plusHours(10),
                5000,
                5,
                5);
        Flight f2 = new Flight(
                "AI202",
                "Mumbai",
                "Bangalore",
                LocalDateTime.now().plusHours(15),
                4500,
                4,
                4);
        flightDB.put(f1.getFlightId(), f1);
        flightDB.put(f2.getFlightId(), f2);
        menu();
    }
}