
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

public class AddToTable {
	
	/* addToAdoption()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add an Adoption
	 *    object through user input. 
	 */
	private static String addToAdoption(Scanner scanner) {
		String appID = "", custID = "", petID = "", empID = "", appDate = "",
				status = "", price = "";
		appID = Common.inputNumber(scanner, "Enter appID: ");
		custID = Common.inputNumber(scanner, "Enter custID: ");
		petID = Common.inputNumber(scanner, "Enter petID: ");
		empID = Common.inputNumber(scanner, "Enter empID: ");
		appDate = Common.inputString(scanner, "Enter application date (YYYY-MM-DD): ");
		appDate = "TO_DATE(" + "\'" + appDate + "\'" + ", \'YYYY-MM-DD\')";
		int statusOption = 0;
		while (true) {
			System.out.println("Select status:");
			System.out.println("1. pending");
			System.out.println("2. approved");
			System.out.println("3. rejected");
			System.out.println("4. withdrawn");
			System.out.print("Enter status number: ");
			try {
				statusOption = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid: Must be a number\n");
				scanner.next(); // move scanner past invalid input
				continue;
			}
			if (statusOption < 1 || statusOption > 4) {
				System.out.println("Invalid: Must be between 1 and 4\n");
				continue;
			}
			switch (statusOption) {
				case 1:
					status = "pending";
					break;
				case 2:
					status = "approved";
					break;
				case 3:
					status = "rejected";
					break;
				case 4:
					status = "withdrawn";
					break;
			}
			break;
			
		}
		price = Common.inputPrice(scanner, "Enter price: ");
		String query = "INSERT INTO Adoption (appID, custID, petID, empID, appDate"
				+ ", status, price) "
				+ "VALUES (" + appID + ", " + custID + ", " + petID + ", "
				+ empID + ", " + appDate + ", " + status + ", " + price
				+ ");";
		// System.out.println("Successfully added new Adoption to datatable");
		return query;
		
	}	// addToAdoption();
	
	
	/* addToEventBooking()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add an 
	 *    EventBooking object through user input. 
	 */
	private static String addToEventBookinng(Scanner scanner) {
		String bookingID = "", custID = "", bookDate = "", eventID = "",
				attendanceStatus = "", paymentStatus = "", membershipTier = "";
		bookingID = Common.inputNumber(scanner, "Enter bookingID: ");
		custID = Common.inputNumber(scanner, "Enter custID: ");
		bookDate = Common.inputString(scanner, "Enter bookDate (YYYY-MM-DD): ");
		eventID = Common.inputNumber(scanner, "Enter eventID: ");
		attendanceStatus = Common.inputNumber(scanner, "Enter attendancee status (0/1): ");
		paymentStatus = Common.inputNumber(scanner, "Enter payment status (0/1): ");
		membershipTier = Common.inputString(scanner, "Enter membership tier: ");
		
		bookDate = "TO_DATE(" + "\'" + bookDate + "\'" + ", \'YYYY-MM-DD\')";
		
		String query = "INSERT INTO EventBooking (bookingID, custID, bookDate, "
				+ "eventID, attendanceStatus, paymentStatus, membershipTier) "
				+ "VALUES (" + bookingID + ", " + custID + ", " + bookDate
				+ ", " + eventID + ", " + attendanceStatus + ", "
				+ paymentStatus + ", " + membershipTier + ");";
		// System.out.println("Successfully added new EventBooking to datatable");
		return query;
		
	}	// addToEventBooking();
	
	
	/* addToHealthRecord()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add a
	 *    HealthRecord object through user input. 
	 */
	private static String addToHealthRecord(Scanner scanner) {
		String recordID = "", petID = "", employeeID = "", recordDate = "",
				recordType = "", description = "", nextDueDate = "";
		recordID = Common.inputNumber(scanner, "Enter recordID: ");
		petID = Common.inputNumber(scanner, "Enter petID: ");
		employeeID = Common.inputNumber(scanner, "Enter employeeID: ");
		recordDate = Common.inputString(scanner, "Enter recordDate (YYYY-MM-DD): ");
		recordType = Common.inputString(scanner, "Enter recordType: ");
		description = Common.inputString(scanner, "Enter description: ");
		nextDueDate = Common.inputString(scanner, "Enter next Due Date (YYYY-MM-DD): ");
		
		recordDate = "TO_DATE(" + "\'" + recordDate + "\'" + ", \'YYYY-MM-DD\')";
		nextDueDate = "TO_DATE(" + "\'" + nextDueDate + "\'" + ", \'YYYY-MM-DD\')";
		
		String query = "INSERT INTO HealthRecord (recordID, petID, employeeID, "
				+ "recordDate, recordType, description, nextDueDate) "
				+ "VALUES (" + recordID + ", " + petID + ", " + employeeID
				+ ", " + recordDate + ", " + recordType + ", " +
				description + ", " + nextDueDate + ");";
		// System.out.println("Successfully added new HealthRecord to datatable");
		return query;
		
	}	// addToHealthRecord();
	
	
	/* addToMember()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add a Member
	 *    object through user input. 
	 */
	private static String addToMember(Scanner scanner) {
		String memberID = "", lastName = "", firstName = "", DoB = "", email = "",
				membershipTier = "", emergencyContactAreaCode = "",
				emergencyContact = "", phoneNoAreaCode = "", phoneNo = "";
		memberID = Common.inputNumber(scanner, "Enter memberID: ");
		lastName = Common.inputString(scanner, "Enter last name: ");
		firstName = Common.inputString(scanner, "Enter first name: ");
		DoB = Common.inputString(scanner, "Enter date of birth (YYYY-MM-DD): ");
		email = Common.inputString(scanner, "Enter email: ");
		membershipTier = Common.inputString(scanner, "Enter membership tier: ");
		emergencyContactAreaCode = Common.inputNumber(scanner, "Enter emergency contact area code (xxx): ");
		emergencyContact = Common.inputString(scanner, "Enter emergency contact number (xxx-xxxx): ");
		phoneNoAreaCode = Common.inputNumber(scanner, "Enter phone# area code (xxx): ");
		phoneNo = Common.inputString(scanner, "Enter phone number (xxx-xxxx): ");
		
		DoB = "TO_DATE(" + "\'" + DoB + "\'" + ", \'YYYY-MM-DD\')";
		
		String query = "INSERT INTO Member (memberID, lastName, firstName, DoB, "
				+ "email, membershipTier," + " emergencyContactAreaCode, "
				+ "emergencyContact, phoneNoAreaCode, phoneNo) "
				+ "VALUES (" + memberID + ", " + lastName + ", " +
				firstName + ", " + DoB + ", " + email + ", " +
				membershipTier + ", " + emergencyContactAreaCode + ", " +
				emergencyContact + ", " + phoneNoAreaCode + ", " + phoneNo
				+ ");";
		// System.out.println("Successfully added new Member to datatable");
		return query;
		
	}	// addToMember();
	
	
	/* addToPet()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add a Pet
	 *    object through user input. 
	 */
	private static String addToPet(Scanner scanner) {
		String petID = "", name = "", species = "", breed = "", age = "",
				arrivalDate = "", temperament = "", spNeeds = "", currStat = "";
		petID = Common.inputNumber(scanner, "Enter petID: ");
		name = Common.inputString(scanner, "Enter pet name: ");
		species = Common.inputString(scanner, "Enter species: ");
		breed = Common.inputString(scanner, "Enter breed: ");
		age = Common.inputNumber(scanner, "Enter age: ");
		arrivalDate = Common.inputString(scanner, "Enter pet arrival date ('YYYY-MM-DD'): ");
		temperament = Common.inputString(scanner, "Enter pet temperament: ");
		spNeeds = Common.inputString(scanner, "Enter special needs (y/n): ");
		currStat = Common.inputNumber(scanner, "Enter pet status: ");
		
		arrivalDate = "TO_DATE(" + "\'" + arrivalDate + "\'" + ", \'YYYY-MM-DD\')";
		
		String query = "INSERT INTO Pet (petID, name, species, breed, age, "
				+ "arrivalDate, temperament, spNeeds, currStat) "
				+ "VALUES (" + petID + ", " + name + ", " + species + ", "
				+ breed + ", " + age + ", " + arrivalDate + ", "
				+ temperament + ", " + spNeeds + ", " + currStat + ");";
		// System.out.println("Successfully added new Pet to datatable");
		return query;
		
	}	// addToPet();

	
	/* addToReservation()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add a 
	 *    Reservation object through user input. 
	 */
	private static String addToReservation(Scanner scanner) {
		String reservationID = "", customerID = "", roomID = "", resDate = "",
				startTime = "", duration = "", inStatus = "", membershipTier = "";
		reservationID = Common.inputNumber(scanner, "Enter reservationID: ");
		customerID = Common.inputNumber(scanner, "Enter customerID: ");
		roomID = Common.inputNumber(scanner, "Enter roomID: ");
		resDate = Common.inputString(scanner, "Enter reservation date ('YYYY-MM-DD'): ");
		startTime = Common.inputString(scanner, "Enter start time (HH-MM-SS: ");
		duration = Common.inputNumber(scanner, "Enter duration (in hours): ");
		inStatus = Common.inputNumber(scanner, "Enter pet status: ");
		membershipTier = Common.inputString(scanner, "Enter membership tier: ");
		
		resDate = "TO_DATE(" + "\'" + resDate + "\'" + ", \'YYYY-MM-DD\')";
		startTime = "TO_TIME(" + "\'" + startTime + "\'" + ", \'HH:MM:SS\')";
		
		String query = "INSERT INTO Reservation (reservationID, customerID, roomID"
				+ ", resDate, startTime, duration, inStatus, membershipTier) "
				+ "VALUES (" + reservationID + ", " + customerID + ", " +
				roomID + ", " + resDate + ", " + startTime + ", " +
				duration + ", " + inStatus + ", " + membershipTier + ");";
		// System.out.println("Successfully added new Reservation to datatable");
		return query;
		
	}	// addToReservation();
	
	
	/* addToTotalOrder()
	 * Parameters: scanner, a Scanner object
	 * Returns: query, a String holding an SQL query
	 * Purpose: using a scanner object, creates an SQL query to add a 
	 *    TotalOrder object through user input. 
	 */
	private static String addToTotalOrder(Scanner scanner) {
		String orderID = "", memberID = "", reservationID = "", orderTime = "",
				totalPrice = "", paymentStatus = "", orderDate = "";
		orderID = Common.inputNumber(scanner, "Enter orderID: ");
		memberID = Common.inputNumber(scanner, "Enter memberID: ");
		reservationID = Common.inputNumber(scanner, "Enter reservationID: ");
		orderTime = Common.inputString(scanner, "Enter order time (HH-MM-SS: ");
		totalPrice = Common.inputNumber(scanner, "Enter price (xx.xx): ");
		paymentStatus = Common.inputString(scanner, "Enter payment status (y/n): ");
		orderDate = Common.inputNumber(scanner, "Enter order date ('YYYY-MM-DD'): ");
		
		orderDate = "TO_DATE(" + "\'" + orderDate + "\'" + ", \'YYYY-MM-DD\')";
		orderTime = "TO_TIME(" + "\'" + orderTime + "\'" + ", \'HH:MM:SS\')";
		
		String query = "INSERT INTO TotalOrder (orderID, memberID, reservationID,"
				+ " orderTime, totalPrice, paymentStatus, orderDate) "
				+ "VALUES (" + orderID + ", " + memberID + ", " +
				reservationID + ", " + orderTime + ", " + totalPrice + ", "
				+ paymentStatus + ", " + orderDate + ");";
		// System.out.println("Successfully added new TotalOrder to datatable");
		return query;
		
	}	// addToTotalOrder();
	
	
	/*
	 * ------------------------------------------------------------------------
	 * addToTable(int, Scanner)
	 * Requires the DB to already contain the tables listed in Parameters, each
	 * with the proper attributes in their given order. THIS WILL NOT WORK
	 * OTHERWISE.
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
	 * Else: nothing! why would you try to put in something else?
	 * 
	 * Returns: A String query that holds a ready SQL query. This query will
	 * specifically be for inserting an item into a specific table. If an
	 * invalid int is given as the tableValue, query will be an empty String
	 * 
	 * Purpose: This function is meant to act as a hub for all insert
	 * statements in the program! It works with switch statements, each int
	 * representing a different Table in our DB (see above, in Parameters).
	 * Once a query is crafted through a helper function, it gets returned so 
	 * that the query can be sent to Oracle.
	 * 
	 * 
	 * THIS FUNCTION IS NOT COMPLETE
	 * There are still not proper checks for unique IDs of objects; that is on
	 *    the way 
	 *    
	 * A print statement has been added and commented-out at the end of each
	 * statement just for testing. It does not guarentee the actual adding of
	 * an object to a table.
	 * -----------------------------------------------------------------------
	 */
	
	public static String addToTable(int tableValue, Scanner scanner) {
		String query = "";
		switch (tableValue) {
			case 1:
				// add Adoption
				query = addToAdoption(scanner);
				break;

			case 2:
				// add EventBooking
				query = addToEventBookinng(scanner);
				break;

			case 3:
				// add HealthRecord
				query = addToHealthRecord(scanner);
				break;

			case 4:
				// add Member
				query = addToMember(scanner);
				break;

			case 5:
				// add Pet
				query = addToPet(scanner);
				break;

			case 6:
				// add Reservation
				query = addToReservation(scanner);
				break;

			case 7:
				// add TotalOrder
				query = addToTotalOrder(scanner);
				break;

			default:
				System.out.println("Invalid Value in addToTable()");
				break;
		}
		return query;
	} // addToTable();
}
