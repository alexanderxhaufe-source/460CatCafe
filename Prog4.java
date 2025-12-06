import java.io.*;
import java.sql.*;
import java.util.*;

public class Prog4 {
        public static void main (String [] args) {
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
            String username = "alexanderxhaufe";
            String password = "a7394";
        try {
            Class.forName("oracle.jdbc.OracleDriver");

        } catch (ClassNotFoundException e) {
            System.err.println("*** ClassNotFoundException:  "
                + "Error loading Oracle JDBC driver.  \n"
                + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);

        }
        Connection dbconn = null; // database connection object
        try {
                dbconn = DriverManager.getConnection(oracleURL,username,password);
        } catch (SQLException e) {
                System.err.println("*** SQLException:  "
                    + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);
        }
        Scanner scanner = new Scanner(System.in); // initialize scanner
        int userInput; // variable to hold user input
        while (true) {
            System.out.println("1: Add item");
            System.out.println("2: Modify item");
            System.out.println("3: Delete item");
            System.out.println("4: Query menu");
            System.out.print("Enter a query choice (\"-1\" to quit): ");
            try {
                userInput = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                continue;
            }
            if (userInput == -1) { // exit loop and end
                break;
            }
            else if (userInput < 1 || userInput > 4) { // invalid input
                System.out.println("Invalid: Must be between 1 and 4");
                continue; // restart loop
            }
            System.out.println();
            switch (userInput) {
                case 1: // add item
                    System.out.println("\nAdd item to which table?");
                    System.out.println("1. Adoption\n2. Employee\n3. Event\n4. EventBooking\n5. HealthRecord\n6. Member\n7. MenuItem\n8. Pet\n9. Reservation\n10. Room\n11. TotalOrder");
                    System.out.print("Enter table number: ");
                    int tableNum;
                    try {
                        tableNum = scanner.nextInt();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid: Must be a number\n");
                        scanner.next(); // move scanner past invalid input
                        break;
                    }
                    if (tableNum < 1 || tableNum > 11) { // invalid input
                        System.out.println("Invalid: Must be between 1 and 11\n");
                        break;
                    }
                    // commented this following line out so the compiler only compiles this file
                    //String query = AddToTable.addToTable(tableNum);
                    System.out.println();
                    break;
                case 2: // modify item
                    // TODO: pain
                    break;
                case 3: // delete item
                    // TODO: pain
                    break;
                case 4: // query menu
                    // TODO: slightly less pain
                    break;
                default:
                    System.out.println("Invalid: Must be between 1 and 4");
            }
        }
        scanner.close();
        try {
            dbconn.close(); // close the database conn
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                + "Could not close JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }
}