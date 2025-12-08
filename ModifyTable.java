
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
import java.time.temporal.ChronoUnit;

public class ModifyTable {

    /*
    * Method: modAdoption
    * Purpose: Modify a record in the Adoption table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the Adoption table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modAdoption(Scanner scanner, Connection conn) {
        try {
            String updateSQL = "update alexanderxhaufe.Adoption set status = ? where appID = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            int statusChoice;
            // get adoptID to modify from the user and convert it to int
            String adoptID = Common.inputNumber(scanner, "Enter the adoptID of the record to modify: ").trim();
            int adoptIDInt = Integer.parseInt(adoptID);
            System.out.println("Update status to:");
            System.out.println("1. Pending\n2. Approved\n3. Rejected\n4. Withdrawn");
            System.out.print("Enter a status choice (1-4): ");
            int tableNum; // variable to hold user input
            try {
                tableNum = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                pstmt.close();
                return;
            }
            if (tableNum < 1 || tableNum > 4) { // invalid input
                System.out.println("Invalid: Must be between 1 and 4\n");
                pstmt.close();
                return;
            }
            String status = "";
            switch (tableNum) {
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
            pstmt.setString(1, status);
            pstmt.setInt(2, adoptIDInt);
            int rowsChanged = pstmt.executeUpdate();
            System.out.println(rowsChanged + " row(s) updated.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void modEB(Scanner scanner, Connection conn) {
        try {
            String EBID = Common.inputNumber(scanner, "Enter the event booking ID of the record to modify: ").trim();
            int EBIDInt = Integer.parseInt(EBID); // string EBID to int
            String query1 = "select e.eventName, m.firstName || \' \' || m.lastName \"custfName\", eb.bookDate, eb.attendanceStatus, e.eventDate "
                + "from alexanderxhaufe.EventBooking eb, alexanderxhaufe.Event e, alexanderxhaufe.Member m where eb.bookingID = " + EBID
                + " and eb.eventID = e.eventID and eb.custID = m.memberID";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            if (!rs.next()) {
                System.out.println("No record found with that event booking ID.");
                rs.close();
                stmt.close();
                return;
            }
            String eventName = rs.getString("eventName"); // event name
            String custName = rs.getString("custfName"); // cust name
            int attendanceStatus = rs.getInt("attendanceStatus"); // attendance status
            LocalDate eventDate = rs.getDate("eventDate").toLocalDate(); // event date
            rs.close();
            stmt.close();
            int userChoice;
            System.out.println("Event Name: " + eventName + ", Customer Name: " + custName);
            System.out.println("1. Check-in customer");
            System.out.println("2. Cancel booking");
            System.out.print("Enter a choice (1-2): ");
            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                return;
            }
            if (userChoice < 1 || userChoice > 2) { // invalid input
                System.out.println("Invalid: Must be between 1 and 2\n");
                return;
            }
            if (userChoice == 1) { // checkin
                String updateSQL = "update alexanderxhaufe.EventBooking set attendanceStatus = 1 where bookingID = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateSQL);
                pstmt.setInt(1, EBIDInt);
                int rowsChanged = pstmt.executeUpdate(); // execute update and get no rows changed
                if (rowsChanged > 0) {
                    System.out.println(custName + " checked in for event: " + eventName);
                } else {
                    System.out.println("Check-in failed");
                }
                pstmt.close();
                return;
            } 
            else {
                LocalDate currentDate = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(currentDate, eventDate);
                System.out.println(daysBetween);
                PreparedStatement pstmt;
                if (daysBetween <= 7) { // refund cutoff is a week before
                    String noRefundUpdate = "update alexanderxhaufe.EventBooking set attendanceStatus = 3 where bookingID = ?";
                    pstmt = conn.prepareStatement(noRefundUpdate);
                } else {
                    String refundUpdate = "update alexanderxhaufe.EventBooking set attendanceStatus = 3, paymentStatus = 2 where bookingID = ?";
                    pstmt = conn.prepareStatement(refundUpdate);
                }
                pstmt.setInt(1, EBIDInt);
                int rowsChanged = pstmt.executeUpdate();
                if (rowsChanged > 0) {
                    System.out.println("Booking for " + custName + " cancelled for event: " + eventName);
                } else {
                    System.out.println("Cancellation failed");
                }
                pstmt.close();
                return;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void modHR(Scanner scanner, Connection conn) {
        
    }
	
	public static void modTable(int tableValue, Scanner scanner, Connection conn) {
		switch (tableValue) {
			case 1: // modify adoption table
                modAdoption(scanner, conn);
				break;
			case 2: // modify event booking
                modEB(scanner, conn);
				break;
			case 3: // modify health record

				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			default:
				System.out.println("Invalid Value in addToTable()");
				break;
		}
	}
}