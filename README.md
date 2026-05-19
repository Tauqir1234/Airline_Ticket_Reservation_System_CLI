# Airline Reservation System

This repository contains a simple Java-based Airline Reservation System implemented in `src/Main.java`.

## Features

- Register passengers with name and email
- Add new flights with route, departure time, price, and seat layout
- View available flights and flight details
- Book tickets with seat selection and payment simulation
- Cancel tickets (with a 2-hour cancellation restriction)
- Display seat availability as a matrix
- Generate summary reports for flights, passengers, bookings, and revenue
- Run basic SQL-style query outputs for profitable flights and most booked routes

## Files

- `src/Main.java` - Main application containing passenger, flight, and booking logic plus console menu

## Prerequisites

- Java JDK 8 or later

## Compile and Run

Open a terminal in the project root and run:

```bash
javac src/Main.java -d out
java -cp out Main
```

If you prefer to run directly from the source directory:

```bash
cd src
javac Main.java
java Main
```

## Usage

Upon running, the application presents a console menu:

1. Register Passenger
2. Add Flight
3. View Flights
4. Book Ticket
5. Cancel Ticket
6. Generate Reports
7. SQL Query Output
8. Exit

The program includes two preloaded flights (`AI101` and `AI202`) for immediate testing.

## Notes

- Seat prices increase automatically based on occupancy
- Cancellation is blocked when a flight departure is less than 2 hours away
- Payment processing is simulated with a short delay
