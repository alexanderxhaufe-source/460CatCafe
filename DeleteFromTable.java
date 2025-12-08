/* Authors: Jessica McManus, Alexander Haufe, Aleksei Weinberg
 * Course: CSC 460 Database Design
 * Assignment: Program 4
 * Instructor/TAs: L. McCann, J. Shenn, U. Upadhyay
 * Due: 12/8/2025
 * Description: This program acts as a front end for a database containing
 * information about a cat cafe. It is meant to do queries and allow
 * for manipulation of the database.
 * Requirements: Java 16, Oracle JDBC driver, Oracle DBMS access
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Math;
import java.time.LocalDate;

public class DeleteFromTable {
	/*
	 * ------------------------------------------------------------------------
	 * deleteFromTable(int, Scanner)
	 * Requires the DB to already contain the tables listed in Parameters.
	 * 
	 * Parameters: tableValue - an int that represents one of the many tables
	 * for our program! Here are the values:
	 * 1. Adoption
	 * 2. EventBooking
	 * 3. HealthRecord
	 * 4. Member
	 * 5. Pet
	 * 6. Reservation
	 * 7. TotalOrder
	 * Else: nothing! why would you try to delete from something else?
	 * 
	 * Returns: None, the deleteFromTable makes the deletion or prints why
	 * it was unable to delete.
	 * 
	 * Purpose: This function is meant to act as a hub for all delete
	 * statements in the program! It works with switch statements, each int
	 * representing a different Table in our DB (see above, in Parameters).
	 * Once a query is crafted, it gets returned so that the query can be
	 * sent to Oracle.
	 * 
	 * 
	 * Some of the variables have names that end in numbers. Due to the way
	 * switch statements work (special fellas, aren't they?), I either would
	 * have to declare all of the variables before entering the switch
	 * statement, or would have to give all vars unique names; I chose the
	 * latter option :/
	 * -----------------------------------------------------------------------
	 */
	
	public static String deleteFromTable(int tableValue, Connection dbconn, Scanner scanner) throws SQLException{
		String query = "";
		String userString = "";
		int userChoice = 0;
		int userChoice2 = 0;
		Statement stmt = null;
		ResultSet answer = null;
		String query = "";
		switch (tableValue) {
			case 1:
				//delete from Adoption
				while (true){
					System.out.println("Enter the appID to delete or -1 to cancel:");

					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
					break;
				}
				scanner.nextLine(); //move scanner
				query = "select * from Adoption where appID = '"+userChoice+"'";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					if (answer.getString("status")=="pending"){
						query = "delete from Adoption where appID = '"+userChoice+"'";
					} else {
						query = "update Adoption set status = withdrawn where appID = '"+userChoice+"'";
					}
					stmt = dbconn.createStatement();
					stmt.executeQuery(query);
					return;
				}
				//Ony get here if adoptionID not found in db
				System.out.pritnln("Could not find an adoption record where appID = "+userChoice);
				return;
				break;

			case 2:
				// delete from EventBooking
				while (true){
					System.out.println("Enter the bookingID of what to delete or -1 to cancel:");

					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
					break;
				}
				scanner.nextLine(); // move scanner
				query = "select * from EventBooking where bookingID = '"+userChoice+"'";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					if (answer.getInt("paymentStatus")==2){
						query = "delete from EventBooking where bookingID = '"+userChoice+"'";
					} // changing to canceled will be handled in modify table not here
					
					stmt = dbconn.createStatement();
					stmt.executeQuery(query);
					return;
				}
				//Only get here if bookingID not found in db
				System.out.println("Could not find an event booking where bookingID = "+userChoice);
				return;
				break;

			case 3:
				//Delete from HealthRecord
				System.out.println("Health records are legal documents and cannot be deleted, instead modify it to be marked void");
				return;
				break;

			case 4:
				// Delete From Member
				while (true){
					System.out.println("Enter the memberID of the account to delete or -1 to cancel:");
					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
					break;
				}
				scanner.nextLine(); // move scanner

				//check for future reservations
				query = "select Member.*, Reservation.bookDate, Reservation.reservationID"
					+"from Member, Reservation "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = Reservation.memberID "
					+"and Reservation.bookDate > SYSDATE";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					String errMessage = "Cannot delete member with id "+userChoice+" as they have the following reservations with reservationID:";
						while (true) {
							errMessage = errMessage + "\n" + answer.getInt("reservationID");
							if (!answer.next()) {
								break;
							}
						}
					System.out.println(errMessage);
					return;
				}
				//check for pending adoption applications
				query = "select Member.*, Adoption.appID "
					+"from Member, Adoption "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = Adoption.memberID "
					+"and Adoption.status = pending";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					String errMessage = "Cannot delete member with id "+userChoice+" as they have the following pending adoption applications with appID:";
						while (true) {
							errMessage = errMessage + "\n" + answer.getInt("appID");
							if (!answer.next()) {
								break;
							}
						}
					System.out.println(errMessage);
					return;
				}
				// check for unpaid food orders
				query = "select Member.*, TotalOrder.orderID "
					+"from Member, TotalOrder "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = TotalOrder.memberID "
					+"and TotalOrder.paymentStatus = 0";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					String errMessage = "Cannot delete member with id "+userChoice+" as they have the following unpaid orders with orderID:";
						while (true) {
							errMessage = errMessage + "\n" + answer.getInt("orderID");
							if (!answer.next()) {
								break;
							}
						}
					System.out.println(errMessage);
					return;
				}
				//If here, member is able to be deleted
				// delete member and all related records
				//related records being: reservation, application, eventBooking
				//TotalOrder left for money tracking
				
				//deleting members old reservations
				query = "select Member.*, Reservation.bookDate, Reservation.reservationID "
					+"from Member, Reservation "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = Reservation.memberID ";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				while (answer.next()){
					query = "delete from Reservation where reservationID = '"+answer.getInt("reservationID")+"'";
					stmt = dbconn.createStatement();
					answer = stmt.executeQuery(query);
				}

				//deleting members old pet applications
				query = "select Member.*, Adoption.appID "
					+"from Member, Adoption "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = Adoption.appID ";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				while (answer.next()){
					query = "delete from Adoption where appID = '"+answer.getInt("appID")+"'";
					stmt = dbconn.createStatement();
					answer = stmt.executeQuery(query);
				}

				//deleting members old evenBookings
				query = "select Member.*, EventBooking.bookingID "
					+"from Member, EventBooking "
					+"where Member.memberID = '"+userChoice+"' "
					+"and Member.memberID = EventBooking.bookingID ";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				while (answer.next()){
					query = "delete from EventBooking where bookingID = '"+answer.getInt("bookingID")+"'";
					stmt = dbconn.createStatement();
					answer = stmt.executeQuery(query);
				}
				
				//deteting member account
				query = "delete from Member where memberID = '"+userChoice+"'";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				
				System.out.println("Member with memberID "+userChoice+" deleted");
				return;
				break;

			case 5:
				// delete from Pet
				while (true){
					System.out.println("Enter the petID of the pet record to remove or -1 to cancel");
					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
					break;
				}
				scanner.nextLine(); // move scanner
				//verify no pending adoption applications
				query = "select Pet.*, Adoption.appID "
					+"from Pet, Adoption "
					+"where Pet.petID = '"+userChoice+"' "
					+"and Pet.petID = Adoption.petID "
					+"and Adoption.status = pending";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					String errMessage = "Cannot delete pet with id "+userChoice+" as they have the following pending adoption applications with appID:";
						while (true) {
							errMessage = errMessage + "\n" + answer.getInt("appID");
							if (!answer.next()) {
								break;
							}
						}
					System.out.println(errMessage);
					return;
				}
				//delete pet
				query = "delete from Pet where petID = '"+userChoice+"'";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				System.out.println("Pet with petID "+userChoice+" has been deleted");
				
				return;
				break;

			case 6:
				// Delete from Reservation
				while (true){
					System.out.println("Enter the reservationID of the resrvation to delete or -1 to cancel");
					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
					break;
				}
				scanner.nextLine(); // move scanner
				//check if it is before the reservation time
				query = "select * from Reservation "
					+"where Reservation.reservationID = '"+userChoice+"' "
					+"and Reservation.resDate < SYSDATE";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					System.out.println("Can only delete future reservations, the reservation with reservationID "+userChoice+" remains");
					return;
				}
				//paymentStatus = 2 is for if the order is canceled and/or refunded
				query = "select Reservation.*, TotalOrder.orderID "
					+"from Reservation, TotalOrder "
					+"where Reservation.reservationID = '"+userChoice+"' "
					+"and Reservation.reservationID = TotalOrder.reservationID "
					+"and TotalOrder.paymentStatus <> 2";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					String errMessage = "Cannot delete reservation with id "+userChoice+" as they have the following accociated order(s) with orderID:";
						while (true) {
							errMessage = errMessage + "\n" + answer.getInt("orderID");
							if (!answer.next()) {
								break;
							}
						}
					System.out.println(errMessage);
					return;
				}

				//if here delete reservation
				query = "delete from Reservation where reservationID = '"userChoice+"'";
				stmt = dbconn.createStatement();
				stmt.executeQuery(query);
				System.out.println("Deleted Reservation with reservationID "+userChoice);
				return;
				break;

			case 7:
				// delete from TotalOrder
				while (true){
					System.out.println("Enter the orderID of the order to delete or -1 to cancel");
					try {
						userChoice = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Invalid input\n");
						scanner.next();
						continue;
					}
					System.out.println();
					if (userChoice == -1) {
						return;
					}
				}
				scanner.nextLine(); // move scanner
				query = "select * from TotalOrder "
					+"where orderID = '"+userChoice+"' "
					+"and status <> placed";
				stmt = dbconn.createStatement();
				answer = stmt.executeQuery(query);
				if (answer.next()){
					System.out.println("Can only delete orders that have yet to have any items prepared or delivered");
					return;
				}
				query = "delete from TotalOrder where orderID ='"+userChoice+"'";
				stmt = dbconn.createStatement();
				stmt.executeQuery(query);
				System.out.println("Order with orderID "+userChoice+"deleted");
				return;
				break;

			default:
				System.out.println("Invalid Value in DeleteFromTable()");
				break;
			
		}
	}
}
