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

public class Prog4 {
	

	/* runQuery()
	 * NOTE: much of the code from this function was borrowed from
	 *    L. McCann's JDBC.java
	 * Parameters: query, a String that is a properly-formatted SQL query
	 * Returns: answerSet, an ArrayList of ResultSet objects. 
	 * Purpose: Takes an SQL query string and runs it through Oracle,
	 *    returning the output of the query as an ArrayList of 
	 *    ResultSet objects
	 * 
	 * Now, what is a ResultSet? 
	 * ResultSet objects are used to store entire entries from SQL 
	 *    queries, and for convenience of multi-purpose use, it made
	 *    the most sense to return an ArrayList that could then be
	 *    manipulated for other use!
	 *    
	 *  how ResultSets work:
	 *    for each ResultSet, you will have a row of a query responses. To get the
	 *    data you need (where answerSet is an array of ResultSet objects:
	 *       for (int i=0; i<answerSet.size(); i++){
	 *          value = (answerSet.get(i)).getString/Int/ETC("NAME_OF_ATTRIBUTE")
	 *          and now do whatever you need with value!
	 *       }
	 *       
	 *    Hopefully, this helps!
	 */
	public static ArrayList<ArrayList<Object>> runQuery(String query) {
		ArrayList<ArrayList<Object>> answerSet = new ArrayList<>();
		Connection dbconn = null;
		Statement stmt = null;
		final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

        String username = "alexanderxhaufe";
        String password = "a7394";

		// load the (Oracle) JDBC driver by initializing its base
		// class, 'oracle.jdbc.OracleDriver'.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} 
		catch (ClassNotFoundException e) {
			System.err.println("*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.  \n"
					+ "\tPerhaps the driver is not on the Classpath?");
			return answerSet;
		}

		// make and return a database connection to the user's
		// Oracle database
		//Connection 
		dbconn = null;
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password);

		} 
		catch (SQLException e) {

			System.err.println("*** SQLException:  " + "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			return answerSet;

		}

		// Send the query to the DBMS, and get and display the results
		//Statement 
		stmt = null;
		ResultSet answer = null;
		
		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
			
			if (answer != null) {
				ResultSetMetaData md = answer.getMetaData();
				int columns = md.getColumnCount();
				/* we only really want to store the values so that they can be taken apart later
				 * a base explanation of how to extract ResultSets is explained in the 
				 *    head comment of this function.
				 */
				while (answer.next()) {
					ArrayList<Object> attrs = new ArrayList<>();

				    for (int i = 1; i <= columns; i++) {
				        //String columnName = md.getColumnName(i);
				        Object value = answer.getObject(i);  // generic getter
				        attrs.add(value);
				        //System.out.print(value + " " + value.getClass().getName());
				    }
					answerSet.add(attrs);
					//System.out.println(answer.getString("sno") + "\t" + answer.getInt("status"));
				}
			}
			//System.out.println();

			// Shut down the connection to the DBMS.
			stmt.close();
			dbconn.close();

		} 
		catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not fetch query results.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			return answerSet;
		}
		
		return answerSet;
	}

    /*
     * Method: manipMenu (short for manipulation menu)
     * Purpose: Display the add/modify/delete menu and handle user input
     * Parameters: Connection dbconn - the connection to the database
     *            Scanner scanner - the scanner to read user input
     *            int action - the action to perform (1 = add, 2 = modify, 
     *            3 = delete)
     * Preconditions: action is 1, 2, or 3, decided by caller
     * Postconditions: appropriate action is taken on the selected table
     * Returns: void
     */
    private static void manipMenu(Connection dbconn, Scanner scanner, int action) {
        String menuHeader = "";
        switch (action) {
            case 1:
                menuHeader = "Add to which table?";
                break;
            case 2:
                menuHeader = "Modify on which table?";
                break;
            case 3:
                menuHeader = "Delete from which table?";
                break;
        }
        System.out.println(
                "+--------------------------------------------------------------------------------+");
        System.out.println(menuHeader);
        System.out.println(
                "1. Adoption\n2. EventBooking\n3. HealthRecord\n4. Member\n5. Pet\n6. Reservation\n7. TotalOrder");
        System.out.print("Enter a table choice (1-7): ");
        int tableNum; // variable to hold user input
        try {
            tableNum = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid: Must be a number\n");
            scanner.next(); // move scanner past invalid input
            return;
        }
        if (tableNum < 1 || tableNum > 7) { // invalid input
            System.out.println("Invalid: Must be between 1 and 7\n");
            return;
        }
        switch (action) {
            case 1: // add a record to table
                String statement = AddToTable.addToTable(tableNum, scanner);
                System.out.println(statement); // testing to display the generated SQL statement
                runQuery(statement);
                break;
            case 2: // modify a table record
                // TODO: implement this
                break;
            case 3: // delete a record from a table
                // TODO: implement this
                break;
        }
        return;
    }

    /*
     * Method: QueryMenu
     * Purpose: Display the query menu and handle user input for queries
     * Parameters: Connection dbconn - the connection to the database
     *            Scanner scanner - the scanner to read user input
     * Returns: void
     */
    private static void QueryMenu(Connection dbconn, Scanner scanner) {
        int userChoice; // variable to hold user input
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("Query Menu:");
        System.out.println("1: For a given pet name, list all adoption applications for that pet with details");
        System.out.println("2: For a given customer, show their complete visit history");
        System.out.println("3: List all upcoming events that have available capacity");
        System.out
                .println("4: For a given employee, list all pending adoption application(s) assigned to that employee");
        System.out.print("Enter a query choice: ");
        try {
            userChoice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input\n");
            scanner.next(); // move scanner past invalid input
            return;
        }
        if (userChoice < 1 || userChoice > 4) { // invalid input
            System.out.println("Invalid input\n");
            return;
        }
        System.out.println();
        try {
            scanner.nextLine(); // move scanner
            switch (userChoice) {
                case 1:
                    String pet_name = Common.inputString(scanner, "Enter pet name: ");
                    QueryHold.query1(dbconn, pet_name);
                    break;
                case 2:
                	String memberID = Common.inputString(scanner, "Enter memberID: ");
                    QueryHold.query1(dbconn, memberID);
                    break;
                case 3:
                    QueryHold.query3(dbconn);
                    break;
                case 4:
                    // TODO: query4
                    break;
                default:
                    System.out.println("Invalid input\n");
            }
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
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
            dbconn = DriverManager.getConnection(oracleURL, username, password);
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
            System.out.println("+--------------------------------------------------------------------------------+");
            System.out.println("Main Menu:");
            System.out.println("1: Add item");
            System.out.println("2: Modify item");
            System.out.println("3: Delete item");
            System.out.println("4: Query menu");
            System.out.print("Enter a menu choice (\"-1\" to quit): ");
            try {
                userInput = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid: Must be a number\n");
                scanner.next(); // move scanner past invalid input
                continue;
            }
            if (userInput == -1) { // exit loop and end
                break;
            } else if (userInput < 1 || userInput > 4) { // invalid input
                System.out.println("Invalid: Must be between 1 and 4");
                continue; // restart loop
            }
            if (userInput >= 1 && userInput <= 3) {
                manipMenu(dbconn, scanner, userInput);
            } else if (userInput == 4) {
                QueryMenu(dbconn, scanner);
            }
        }

        scanner.close();

    }
}
