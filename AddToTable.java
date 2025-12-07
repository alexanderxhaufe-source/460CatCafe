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
	 * Once a query is crafted, it gets returned so that the query can be
	 * sent to Oracle.
	 * 
	 * 
	 * THIS FUNCTION IS NOT COMPLETE
	 * Currently, it is set up where all of the inputs stay as empty strings
	 * Input options will have to be added, either as an extension of the
	 * switch statement, or as an entirely new function ( addMember(), for
	 * example)
	 * 
	 * A print statement has been added and commented-out at the end of each
	 * statement just for testing. It does not guarentee the actual adding of
	 * an object to a table.
	 * 
	 * Some of the variables have names that end in numbers. Due to the way
	 * switch statements work (special fellas, aren't they?), I either would
	 * have to declare all of the variables before entering the switch
	 * statement, or would have to give all vars unique names; I chose the
	 * latter option :/
	 * -----------------------------------------------------------------------
	 */
	
	public static String addToTable(int tableValue, Scanner scanner) {
		String query = "";
		switch (tableValue) {
			case 1:
				// add Adoption
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
				query = "INSERT INTO Adoption (appID, custID, petID, empID, appDate"
						+ ", status, price) "
						+ "VALUES (" + appID + ", " + custID + ", " + petID + ", "
						+ empID + ", " + appDate + ", " + status + ", " + price
						+ ");";
				// System.out.println("Successfully added new Adoption to datatable");
				break;

			case 2:
				// add EventBooking
				String bookingID = "", custID1 = "", bookDate = "", eventID = "",
						attendanceStatus = "", paymentStatus = "", membershipTier = "";
				query = "INSERT INTO EventBooking (bookingID, custID, bookDate, "
						+ "eventID, attendanceStatus, paymentStatus, membershipTier) "
						+ "VALUES (" + bookingID + ", " + custID1 + ", " + bookDate
						+ ", " + eventID + ", " + attendanceStatus + ", "
						+ paymentStatus + ", " + membershipTier + ");";
				// System.out.println("Successfully added new EventBooking to datatable");
				break;

			case 3:
				// add HealthRecord
				String recordID = "", petID1 = "", employeeID = "", recordDate = "",
						recordType = "", description1 = "", nextDueDate = "";
				query = "INSERT INTO HealthRecord (recordID, petID, employeeID, "
						+ "recordDate, recordType, description, nextDueDate) "
						+ "VALUES (" + recordID + ", " + petID1 + ", " + employeeID
						+ ", " + recordDate + ", " + recordType + ", " +
						description1 + ", " + nextDueDate + ");";
				// System.out.println("Successfully added new HealthRecord to datatable");
				break;

			case 4:
				// add Member
				String memberID = "", lastName1 = "", firstName1 = "", DoB = "", email = "",
						membershipTier1 = "", emergencyContactAreaCode = "",
						emergencyContact = "", phoneNoAreaCode = "", phoneNo = "";
				query = "INSERT INTO Member (memberID, lastName, firstName, DoB, "
						+ "email, membershipTier," + " emergencyContactAreaCode, "
						+ "emergencyContact, phoneNoAreaCode, phoneNo) "
						+ "VALUES (" + memberID + ", " + lastName1 + ", " +
						firstName1 + ", " + DoB + ", " + email + ", " +
						membershipTier1 + ", " + emergencyContactAreaCode + ", " +
						emergencyContact + ", " + phoneNoAreaCode + ", " + phoneNo
						+ ");";
				// System.out.println("Successfully added new Member to datatable");
				break;

			case 5:
				// add Pet
				String petID2 = "", name1 = "", species = "", breed = "", age = "",
						arrivalDate = "", temperament = "", spNeeds = "", currStat = "";
				query = "INSERT INTO Pet (petID, name, species, breed, age, "
						+ "arrivalDate, temperament, spNeeds, currStat) "
						+ "VALUES (" + petID2 + ", " + name1 + ", " + species + ", "
						+ breed + ", " + age + ", " + arrivalDate + ", "
						+ temperament + ", " + spNeeds + ", " + currStat + ");";
				// System.out.println("Successfully added new Pet to datatable");
				break;

			case 6:
				// add Reservation
				String reservationID = "", customerID = "", roomID1 = "", resDate = "",
						startTime = "", duration = "", inStatus = "", membershipTier2 = "";
				query = "INSERT INTO Reservation (reservationID, customerID, roomID"
						+ ", resDate, startTime, duration, inStatus, membershipTier) "
						+ "VALUES (" + reservationID + ", " + customerID + ", " +
						roomID1 + ", " + resDate + ", " + startTime + ", " +
						duration + ", " + inStatus + ", " + membershipTier2 + ");";
				// System.out.println("Successfully added new Reservation to datatable");
				break;

			case 7:
				// add TotalOrder
				String orderID1 = "", memberID1 = "", reservationID1 = "", orderTime = "",
						totalPrice = "", paymentStatus1 = "", orderDate = "";
				query = "INSERT INTO TotalOrder (orderID, memberID, reservationID,"
						+ " orderTime, totalPrice, paymentStatus, orderDate) "
						+ "VALUES (" + orderID1 + ", " + memberID1 + ", " +
						reservationID1 + ", " + orderTime + ", " + totalPrice + ", "
						+ paymentStatus1 + ", " + orderDate + ");";
				// System.out.println("Successfully added new TotalOrder to datatable");
				break;

			default:
				System.out.println("Invalid Value in addToTable()");
				break;
		}
		return query;
	} // addToTable();
}
