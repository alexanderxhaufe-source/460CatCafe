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
	 * Then the logic to check for deletion is run. If the logic passes, 
	 * the deletion is made. If not, a message is printed as to why.
	 * 
	 * -----------------------------------------------------------------------
	 */
    private static void deleteFromAdoption(Connection dbconn, Scanner scanner) {
        /* Delete from Adoption table takes in a user input appID to delete.
         * This function then determines if the application is pending, if so it deletes
         * the record. If not pending, it updates the status to withdrawn.
         */
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
		
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
            try {
                query = "select * from Adoption where appID = '"+userChoice+"'";
                stmt = dbconn.createStatement();
                answer = stmt.executeQuery(query);
                if (answer.next()){
                    if (answer.getString("status").equals("pending")){
                        query = "delete from Adoption where appID = '"+userChoice+"'";
                    } else {
                        query = "update Adoption set status = 'withdrawn' where appID = '"+userChoice+"'";
                    }
                    stmt = dbconn.createStatement();
                    stmt.executeQuery(query);
                    return;
                }
            } catch (SQLException e) {
                System.err.println("*** SQLException:  "
                        + "Could not delete from Adoption applications.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                return;
            }
            //Ony get here if adoptionID not found in db
            System.out.println("Could not find an adoption record where appID = "+userChoice);
            return;
        
    }
    private static void deleteFromEventBooking(Connection dbconn, Scanner scanner) {
        /*
         * Delete from EventBooking table takes in a user input bookingID to delete.
         * This function then determines if the booking's paymentStatus is 2, meaning refunded,
         * and if so it deletes the record.
         */
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
		
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
        try {
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
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete from EventBooking.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        //Only get here if bookingID not found in db
        System.out.println("Could not find an event booking where bookingID = "+userChoice);
        return;	
    }
    private static void deleteFromMember(Connection dbconn, Scanner scanner) {
        /* Delete from Member table takes in a user input memberID to delete.
         * Then it performs the following checks:
         * 1. ensures the member does not have any future reservations
         * 2. ensures the member does not have any pending adoption applications
         * 3. ensures the member does not have any unpaid food orders
         * If all checks are passed, the member and all related records are deleted.
         */
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
		
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
        try {
            query = "select Member.*, Reservation.resDate, Reservation.reservationID "
                +"from Member, Reservation "
                +"where Member.memberID = '"+userChoice+"' "
                +"and Member.memberID = Reservation.customerID "
                +"and Reservation.resDate > SYSDATE";
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
                +"and Member.memberID = Adoption.custID "
                +"and Adoption.status = 'pending'";
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
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not verify member is ready for deletion.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        //If here, member is able to be deleted
        // delete member and all related records
        //related records being: reservation, application, eventBooking
        //TotalOrder left for money tracking
        
        //deleting members old reservations
        try {
            query = "select Member.*, Reservation.resDate, Reservation.reservationID "
                +"from Member, Reservation "
                +"where Member.memberID = '"+userChoice+"' "
                +"and Member.memberID = Reservation.customerID ";
            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);
            while (answer.next()){
                query = "delete from Reservation where reservationID = '"+answer.getInt("reservationID")+"'";
                stmt = dbconn.createStatement();
                stmt.executeQuery(query);
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
                stmt.executeQuery(query);
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
                stmt.executeQuery(query);
            }
            
            //deteting member account
            query = "delete from Member where memberID = '"+userChoice+"'";
            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete member account.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        
        System.out.println("Member with memberID "+userChoice+" deleted");
        return;
    }
    private static void deleteFromPet(Connection dbconn, Scanner scanner) {
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
		
        /* Delete From Pet table takes in a user input petID to delete. 
         * Then it verifies that the pet does not have any pending adoption applications.
         * If there are no pending applications, the pet record is deleted.
         */
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
        try {
            query = "select Pet.*, Adoption.appID "
                +"from Pet, Adoption "
                +"where Pet.petID = '"+userChoice+"' "
                +"and Pet.petID = Adoption.petID "
                +"and Adoption.status = 'pending'";
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
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not verify pet account is ready for deletion.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        //delete pet
        try {
            query = "delete from Pet where petID = '"+userChoice+"'";
            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete pet account.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        System.out.println("Pet with petID "+userChoice+" has been deleted");
        return;
    }
    private static void deleteFromReservation(Connection dbconn, Scanner scanner) {
        /* Delete from Reservation table takes in a user input reservationID to delete.
         * Then it verifies that the reservation is for a future date and that there are no
         * associated food orders that are not canceled/refunded.
         * If both checks are passed, the reservation is deleted.
         */
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
			
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
        try {
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
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not verify reservation can be deleted.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        try {
        //if here delete reservation
            query = "delete from Reservation where reservationID = '"+userChoice+"'";
            stmt = dbconn.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete reservation.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        System.out.println("Deleted Reservation with reservationID "+userChoice);
        return;
    }
    private static void deleteFromTotalOrder(Connection dbconn, Scanner scanner) {
        /* Delete from TotalOrder table takes in a user input orderID to delete.
         * Then it verifies that the orderStatus is 'placed', meaning no items have been prepared or delivered.
         * If the check is passed, the order is deleted.
         */
        String query = "";
		int userChoice = 0;
		Statement stmt = null;
		ResultSet answer = null;
			
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
            break;
        }
        scanner.nextLine(); // move scanner
        try {
            query = "select * from TotalOrder "
                +"where orderID = '"+userChoice+"' "
                +"and orderStatus <> 'placed'";
            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer.next()){
                System.out.println("Can only delete orders that have yet to have any items prepared or delivered");
                return;
            }
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not verify order could be deleted.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        try {
            query = "delete from TotalOrder where orderID ='"+userChoice+"'";
            stmt = dbconn.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete order.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            return;
        }
        System.out.println("Order with orderID "+userChoice+" deleted");
        return;
    }
	public static void deleteFromTable(int tableValue, Connection dbconn, Scanner scanner){
        /* Delete from Table is the main function that directs to the specific
         * delete functions for each table. It takes in an int representing
         * the table to delete from, a connection to the database, and a scanner
         * for user input.
         * The tableValue int corresponds to the following tables:
         * 1. Adoption
         * 2. EventBooking
         * 3. HealthRecord (will not delete, only print a message to mark it as void)
         * 4. Member
         * 5. Pet
         * 6. Reservation
         * 7. TotalOrder
         */
			switch (tableValue) {
				case 1:
					//delete from Adoption
                    deleteFromAdoption(dbconn, scanner);
					return;

				case 2:
					// delete from EventBooking
                    deleteFromEventBooking(dbconn, scanner);
					return;

				case 3:
					//Delete from HealthRecord
					System.out.println("Health records are legal documents and cannot be deleted, instead modify it to be marked void");
					return;

				case 4:
					// Delete From Member
                    deleteFromMember(dbconn, scanner);
					return;

				case 5:
					// delete from Pet
					deleteFromPet(dbconn, scanner);
					return;

				case 6:
					// Delete from Reservation
					deleteFromReservation(dbconn, scanner);
                    return;

				case 7:
					// delete from TotalOrder
					deleteFromTotalOrder(dbconn, scanner);
                    return;

				default:
					System.out.println("Invalid Value in DeleteFromTable()");
					break;
			}
	}
}
