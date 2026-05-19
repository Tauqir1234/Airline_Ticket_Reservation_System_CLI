```7. Airline Ticket Reservation System```

```Business Context```
Airlines require a system to manage flight schedules, seat booking, cancellations, and pricing.

```Design Objective```

Create a system to:
•	Register passengers 
•	Manage flights and seat inventory 
•	Book/cancel tickets 
•	Handle dynamic pricing 
•	Generate booking reports 

```Main Flows```
•	Flight management 
•	Seat availability tracking 
•	Ticket booking and cancellation 
•	Payment simulation 
•	Reporting 

```Business Rules```
•	Each seat can be booked only once per flight 
•	Overbooking is not allowed 
•	Cancellation rules depend on time before flight 

```Constraints```
•	Seat locking during booking 
•	Date/time validation required 
•	Price varies based on demand 

```Storage```
•	In-memory seat matrix per flight 

```SQL Use Case```

Find most profitable flights and highest booking routes.
