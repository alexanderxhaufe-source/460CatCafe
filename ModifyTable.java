
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

    /*
    * Method: modEB
    * Purpose: Modify a record in the event booking table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the event booking table is modified or
    * an error message is displayed
    * Returns: void
    */
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
    /*
    * Method: modHR
    * Purpose: Modify a record in the HealthRecord
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the HealthRecord table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modHR(Scanner scanner, Connection conn) {
        try {
            String updateSQL = "update HealthRecord set description = ? where recordID = ?";
            // get recordID to modify from the user and convert it to int
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            String recID = Common.inputNumber(scanner, "Enter the recordID of the healthrecord to modify: ").trim();
            int recIDInt = Integer.parseInt(recID);
            String newDescription = Common.inputString(scanner, "Enter new health record information: ");
            pstmt.setString(1, newDescription);
            pstmt.setInt(2, recIDInt);
            int rowsChanged = pstmt.executeUpdate();
            System.out.println(rowsChanged + " row(s) updated.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /*
    * Method: modMember
    * Purpose: Modify a record in the member table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the member table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modMember(Scanner scanner, Connection conn) {
        try {
            String memID = Common.inputNumber(scanner, "Enter the memberID of the customer to modify: ").trim();
            int memIDInt = Integer.parseInt(memID);
            String query = "select firstName || \' \' || lastName \"custName\" from Member where memberID = " + memIDInt;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                System.out.println("No record found with that event booking ID.");
                rs.close();
                stmt.close();
                return;
            }
            String custName = rs.getString("custName"); // customer full name
            rs.close();
            stmt.close();
            int userChoice;
            System.out.println("Customer Name: " + custName);
            System.out.println("1. Update Contact info");
            System.out.println("2. Modify membership tier");
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
                String updateSQL = "update Member set phoneNoAreaCode = ?, phoneNo = ? where memberID = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateSQL);
                String fullPhoneNo = Common.inputNumber(scanner, "Enter new phone number (only digits): ");
                if (fullPhoneNo.length() != 10) {
                    System.out.println("Phone numbers must be 10 digits.");
                    pstmt.close();
                }
                int areaCode = Integer.parseInt(fullPhoneNo.substring(0, 3)); // first 3 digits 
                int phoneNo = Integer.parseInt(fullPhoneNo.substring(3)); // last 7 digits
                pstmt.setInt(1, areaCode);
                pstmt.setInt(2, phoneNo);
                pstmt.setInt(3, memIDInt);
                int rowsChanged = pstmt.executeUpdate(); // execute update and get no rows changed
                System.out.println(rowsChanged + " row(s) updated.");
                pstmt.close();
                return;
            }
            String updateMembership = "update Member set membershipTier = ? where memberID = ?";
            System.out.println("Update Membership tier to:\n1. Day pass\n2. Monthly\n3. Annual");
            int memChoice;
            try {
                memChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                return;
            }
            if (memChoice < 1 || memChoice > 3) { // invalid input
                System.out.println("Invalid: Must be between 1 and 3\n");
                return;
            }
            PreparedStatement pstmt = conn.prepareStatement(updateMembership);
            pstmt.setInt(1, memChoice);
            pstmt.setInt(2, memIDInt);
            int rowsChanged = pstmt.executeUpdate(); // execute update and get no rows changed
            System.out.println(rowsChanged + " row(s) updated.");
            pstmt.close();
            return;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /*
    * Method: modPet
    * Purpose: Modify a record in the Pet table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the Pet table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modPet(Scanner scanner, Connection conn) {
        try {
            String petID = Common.inputNumber(scanner, "Enter the petID of the pet to modify: ").trim();
            int petIDInt = Integer.parseInt(petID);
            String query = "select name from alexanderxhaufe.Pet where petID = " + petIDInt;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                System.out.println("No record found with that pet ID.");
                rs.close();
                stmt.close();
                return;
            }
            String petName = rs.getString("name");
            rs.close();
            stmt.close();
            int userChoice;
            System.out.println("Pet Name: " + petName);
            System.out.println("1. Update age");
            System.out.println("2. Update temperament");
            System.out.println("3. Update current status");
            System.out.print("Enter a choice (1-3): ");
            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                return;
            }
            if (userChoice < 1 || userChoice > 3) { // invalid input
                System.out.println("Invalid: Must be between 1 and 3\n");
                return;
            }
            scanner.nextLine();
            switch (userChoice) {
                case 1: // update age
                    String newAge = Common.inputNumber(scanner, "Enter the age of the pet: ");
                    int newAgeInt = Integer.parseInt(newAge);
                    String update = "update Pet set age = ? where petID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setInt(1, newAgeInt);
                    pstmt.setInt(2, petIDInt);
                    int rowsChanged = pstmt.executeUpdate(); // execute update and get no rows changed
                    System.out.println(rowsChanged + " row(s) updated.");
                    pstmt.close();
                    break;
                case 2: // update temperament
                    String newTemp = Common.inputString(scanner, "Enter the temperament of the pet: ");
                    String update2 = "update Pet set temperament = ? where petID = ?";
                    PreparedStatement pstmt2 = conn.prepareStatement(update2);
                    pstmt2.setString(1, newTemp);
                    pstmt2.setInt(2, petIDInt);
                    int rowsChanged2 = pstmt2.executeUpdate(); // execute update and get no rows changed
                    System.out.println(rowsChanged2 + " row(s) updated.");
                    pstmt2.close();
                    break;
                case 3: // update current status
                    String newStatus = Common.inputString(scanner, "Enter the status of the pet: ");
                    String update3 = "update Pet set currStat = ? where petID = ?";
                    PreparedStatement pstmt3 = conn.prepareStatement(update3);
                    pstmt3.setString(1, newStatus);
                    pstmt3.setInt(2, petIDInt);
                    int rowsChanged3 = pstmt3.executeUpdate(); // execute update and get no rows changed
                    System.out.println(rowsChanged3 + " row(s) updated.");
                    pstmt3.close();
                    break;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /*
    * Method: modRes
    * Purpose: Modify a record in the Reservation table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the Reservation table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modRes(Scanner scanner, Connection conn) {
        try {
            String resID = Common.inputNumber(scanner, "Enter the reservationID to modify: ").trim();
            int resIDInt = Integer.parseInt(resID);
            String query = "select customerID from alexanderxhaufe.Reservation where reservationID = " + resIDInt;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                System.out.println("No record found with that reservation ID.");
                rs.close();
                stmt.close();
                return;
            }
            rs.close();
            stmt.close();
            
            int userChoice;
            System.out.println("1. Update reservation date");
            System.out.println("2. Extend duration");
            System.out.println("3. Update customer status (in or out)");
            System.out.print("Enter a choice (1-3): ");
            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next();
                return;
            }
            if (userChoice < 1 || userChoice > 3) {
                System.out.println("Invalid: Must be between 1 and 3\n");
                return;
            }
            scanner.nextLine();
            switch (userChoice) {
                case 1:
                    String newDate = Common.inputString(scanner, "Enter new reservation date (YYYY-MM-DD): ");
                    String newTime = Common.inputString(scanner, "Enter new reservation time (HH:MM AM/PM): ");
                    String updateDate = "update Reservation set resDate = TO_DATE(?, 'YYYY-MM-DD'), startTime = ? where reservationID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(updateDate);
                    pstmt.setString(1, newDate);
                    pstmt.setString(1, newTime);
                    pstmt.setInt(2, resIDInt);
                    int rowsChanged = pstmt.executeUpdate();
                    System.out.println(rowsChanged + " row(s) updated.");
                    pstmt.close();
                    break;
                case 2:
                    String newDuration = Common.inputString(scanner, "Enter new duration (HH:MM): ");
                    String updateDuration = "update Reservation set duration = ? where reservationID = ?";
                    PreparedStatement pstmt2 = conn.prepareStatement(updateDuration);
                    pstmt2.setString(1, newDuration);
                    pstmt2.setInt(2, resIDInt);
                    int rowsChanged2 = pstmt2.executeUpdate();
                    System.out.println(rowsChanged2 + " row(s) updated.");
                    pstmt2.close();
                    break;
                case 3:
                    String newStatus = Common.inputNumber(scanner, "Enter status (1=in, 0=out): ").trim();
                    int newStatusInt = Integer.parseInt(newStatus);
                    if (newStatusInt < 0 || newStatusInt > 1) {
                        System.out.println("Invalid: Must be 1 or 0\n");
                        return;
                    }
                    String updateStatus = "update Reservation set inStatus = ? where reservationID = ?";
                    PreparedStatement pstmt3 = conn.prepareStatement(updateStatus);
                    pstmt3.setInt(1, newStatusInt);
                    pstmt3.setInt(2, resIDInt);
                    int rowsChanged3 = pstmt3.executeUpdate();
                    System.out.println(rowsChanged3 + " row(s) updated.");
                    pstmt3.close();
                    break;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    /*
    * Method: modOrder
    * Purpose: Modify a record in the TotalOrder table
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * Preconditions: connection and scanner are valid
    * Postconditions: a record in the TotalOrder table is modified or
    * an error message is displayed
    * Returns: void
    */
    private static void modOrder(Scanner scanner, Connection conn) {
        try {
            String orderID = Common.inputNumber(scanner, "Enter the orderID to modify: ").trim();
            int orderIDInt = Integer.parseInt(orderID);
            int userChoice;
            System.out.println("1. Update order status");
            System.out.println("2. Update payment status");
            System.out.print("Enter a choice (1-2): ");
            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next();
                return;
            }
            if (userChoice < 1 || userChoice > 2) {
                System.out.println("Invalid: Must be between 1 and 2\n");
                return;
            }
            scanner.nextLine();
            if (userChoice == 1) {
                String newStatus = Common.inputString(scanner, "Enter new order status (placed/prepared/delivered): ");
                String updateStatus = "update TotalOrder set orderStatus = ? where orderID = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateStatus);
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, orderIDInt);
                int rowsChanged = pstmt.executeUpdate();
                System.out.println(rowsChanged + " row(s) updated.");
                pstmt.close();
            } else {
                String newPstatus = Common.inputNumber(scanner, "Enter new payment status (0: unpaid, 1: paid): ");
                int newPstatusint = Integer.parseInt(newPstatus);
                if (newPstatusint < 0 || newPstatusint > 1) {
                    System.out.println("Invalid: Must be 1 or 0\n");
                    return;
                }
                String updatePstatus = "update TotalOrder set paymentStatus = ? where orderID = ?";
                PreparedStatement pstmt = conn.prepareStatement(updatePstatus);
                pstmt.setInt(1, newPstatusint);
                pstmt.setInt(2, orderIDInt);
                int rowsChanged = pstmt.executeUpdate();
                System.out.println(rowsChanged + " row(s) updated.");
                pstmt.close();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
	
    /*
    * Method: modTable
    * Purpose: Modify records in the database
    * Parameters: Scanner scanner - the scanner to read user input
    * Connection conn - the connection to the database
    * int TableValue - the selected table to modify
    * Preconditions: connection and scanner are valid and tableValue is 
    * in the range of possible choices.
    * Postconditions: a table is either modified or not depending on whether
    * or not the user makes an incorrect input or an SQL error is returned
    * Returns: void
    */
	public static void modTable(int tableValue, Scanner scanner, Connection conn) {
		switch (tableValue) {
			case 1: // modify adoption table
                modAdoption(scanner, conn);
				break;
			case 2: // modify event booking
                modEB(scanner, conn);
				break;
			case 3: // modify health record
                modHR(scanner, conn);
				break;
			case 4: // modify member
                modMember(scanner, conn);
				break;
			case 5: // modify pet
                modPet(scanner, conn);
				break;
			case 6: // modify reservation
                modRes(scanner, conn);
				break;
			case 7: // modify totalorder
                modOrder(scanner, conn);
				break;
			default:
				System.out.println("Invalid Value in addToTable()");
				break;
		}
	}
}