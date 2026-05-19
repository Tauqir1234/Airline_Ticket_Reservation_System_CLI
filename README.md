# Airline Reservation System

## Business Context

Airlines require a system to manage flight schedules, seat booking, cancellations, and pricing.

## Design Objective

Create a system to:
-	Register passengers 
-	Manage flights and seat inventory 
-	Book/cancel tickets 
-	Handle dynamic pricing 
-	Generate booking reports 

## Main Flows
-	Flight management 
-	Seat availability tracking 
-	Ticket booking and cancellation 
-	Payment simulation 
-	Reporting 
## Business Rules
-	Each seat can be booked only once per flight 
-	Overbooking is not allowed 
-	Cancellation rules depend on time before flight 
## Constraints
-	Seat locking during booking 
-	Date/time validation required 
-	Price varies based on demand 
## Storage
-	In-memory seat matrix per flight 
## SQL Use Case
Find most profitable flights and highest booking routes.

## SQL Query

<img width="1080" height="577" alt="Screenshot 2026-05-19 161032" src="https://github.com/user-attachments/assets/a627ea93-14d4-4a6f-b40b-6381e76a9b71" />

## SQL Query Output

<img width="517" height="197" alt="Screenshot 2026-05-19 152201" src="https://github.com/user-attachments/assets/d413e23a-97fa-47f2-89c6-9333c58b2320" />


