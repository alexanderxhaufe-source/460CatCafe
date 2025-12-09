/* QueryHold.java
 * Authors: Jessica McManus, Alexander Haufe, Aleksei Weinberg
 * Course: CSC 460 Database Design
 * Assignment: Program 4
 * Instructor/TAs: L. McCann, J. Shenn, U. Upadhyay
 * Due: 12/8/2025
 * Description: This program holds all of the queries for Prog4.java
 *   each function in this program holds the code for a different query.
 *   
 * Requirements: Java 16, Oracle JDBC driver, Oracle DBMS access
*/
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.lang.Math;
import java.time.LocalDate;

public class QueryHold {
        /*
        * Method: query1
        * Purpose: For a given pet name, print all adoption applications for that pet with details
        * Parameters: Connection dbconn - the connection to the database
        *            String pet_name - the name of the pet
        * Returns: void
        * Throws: SQLException - if a database error occurs
        */
        public static void query1(Connection dbconn, String pet_name) throws SQLException {
                String query = "select a.appID \"Application ID\", m.firstName || \' \' || m.lastName \"Applicant Name\", "
                                +
                                "a.appDate \"Date\", a.status \"Status\", e.firstName || \' \' || e.lastName \"Adoption Coordinator\" "
                                +
                                "from alexanderxhaufe.Member m, alexanderxhaufe.Adoption a, alexanderxhaufe.Employee e, alexanderxhaufe.Pet p "
                                +
                                "where a.petID = p.petID " +
                                "and a.empID = e.empID " +
                                "and a.custID = m.memberID " +
                                "and p.name = \'" + pet_name + "\'";
                Statement stmt = null; // statement to execute SQL query
                ResultSet answer = null; // result set returned by the query
                stmt = dbconn.createStatement();
                answer = stmt.executeQuery(query);
                if (answer != null) {
                        ResultSetMetaData answermetadata = answer.getMetaData(); // get result set metadata
                        // print column headers
                        for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                                System.out.print(answermetadata.getColumnLabel(i) + "\t\t");
                        }
                        System.out.println();
                        // print rows of the result set
                        int rowCount = 0;
                        while (answer.next()) {
                                int appID = answer.getInt("Application ID");
                                String applicantName = answer.getString("Applicant Name");
                                Date appDate = answer.getDate("Date");
                                String status = answer.getString("Status");
                                String coordinatorName = answer.getString("Adoption Coordinator");
                                System.out.println(appID + "\t\t\t" + applicantName + "\t\t" + appDate + "\t" + status
                                                + "\t\t" + coordinatorName);
                                rowCount++;
                        }
                        System.out.println("\nTotal number of adoption applications for pet \"" + pet_name + "\": "
                                        + rowCount);
                }
                System.out.println();
                stmt.close();
        }

        /*
         * Method: query2
         * Purpose: print a list of customer visit history
         * Parameters: Connection dbconn - the connection to the database
         * Returns: void
         * Throws: SQLException - if a database error occurs
         * 
         * Query: For a given customer, show their complete visit history 
         *    including reservation dates, rooms visited, food orders placed 
         *    during each visit, total amount spent, and membership tier at the
         *    time of visit.
         *
         *         ** CURRENTLY UNTESTED
         */
         public static void query2(Connection dbconn) throws SQLException {
                 Statement stmt = null; // statement to execute SQL query
                 ResultSet answer = null; // result set returned by the query
                 // list all upcoming events that have available capacity
                 String query = """
                 		SELECT 
					m.memberID, 
					m.firstName,
					m.lastName,  
					r.resDate, 
					rm.roomID, 
					mi.name, 
					mi.price,
					SUM(mi.price) OVER (PARTITION BY r.reservationID) AS totalCost,
					r.membershipTier
				FROM alexanderxhaufe.Member m
				JOIN alexanderxhaufe.Reservation r
					ON m.membershipID = r.customerID
				JOIN alexanderxhaufe.room rm
					ON r.roomID = rm.roomID
				JOIN alexanderxhaufe.TotalOrder o
					ON r.reservationID = o.reservationID
				LEFT JOIN alexanderxhaufe.MenuItem mi
					ON o.orderID = mi.orderID
				WHERE m.memberID = :memberID
				ORDER BY r.resDate	
				""";
                 stmt = dbconn.createStatement();
                 answer = stmt.executeQuery(query);

                 System.out.println("Customer has the following visit history:\n");
                 // String toPrint = "";
                 while (answer.next()) {
                         System.out.println("MemberID: " + answer.getInt("memberID")
                                         + " | FirstName: " + answer.getString("firstName")
                                         + " | LastName: " + answer.getString("lastName")
                                         + " | Date: " + answer.getDate("resDate")
                                         + " | RoomID: " + answer.getInt("roomID")
                                         + " | Items Ordered: " + answer.getString("name")
                                         + " | Menu Cost: " + answer.getDouble("price"));
                 }
                 System.out.println();
                 stmt.close();
         }	//query2();

        /*
        * Method: query3
        * Purpose: print a list all upcoming events that have available capacity
        * Parameters: Connection dbconn - the connection to the database
        * Returns: void
        * Throws: SQLException - if a database error occurs
        */
        public static void query3(Connection dbconn) throws SQLException {
                Statement stmt = null; // statement to execute SQL query
                ResultSet answer = null; // result set returned by the query
                // list all upcoming events that have available capacity
                String query = """
                                select Event.*,Room.location,(select count(*)
                                                from EventBooking
                                                where EventBooking.eventID = Event.eventID)
                                                as registered
                                from Event,Room
                                where Event.roomID = Room.roomID

                                """;
                stmt = dbconn.createStatement();
                answer = stmt.executeQuery(query);

                System.out.println("The following events have available capacity\n");
                // String toPrint = "";
                while (answer.next()) {
                        System.out.println("Event Name: " + answer.getString("eventName")
                                        + " | Date: " + answer.getDate("eventDate")
                                        + " | Time: " + answer.getString("time")
                                        + " | Room Location: " + answer.getString("location")
                                        + " | Registered Attendees: " + answer.getInt("registered")
                                        + " | Maximum Capacity: " + answer.getInt("maxCapacity")
                                        + " | Coordinator: " + answer.getString("description"));
                }
                System.out.println();
                stmt.close();
        }

	    /*
         * Method: query4
         * Purpose: print a list of pet information per employee's adoption apps
         * Parameters: Connection dbconn - the connection to the database
		 *             stream, a scanner object
         * Returns: void
         * Throws: SQLException - if a database error occurs
         * 
         * Query: For a given employee, list the pet information for all pending 
		 *    adoption application(s) assigned to that employee
         */
        public static void query4(Connection dbconn, Scanner stream) throws SQLException {
                System.out.println("Enter the employee's first name");
                String empFName = stream.nextLine();
                System.out.println("Enter the employee's last name");
                String empLName = stream.nextLine();
                String query =
                        "select Pet.name,Pet.species,Pet.breed "+
                        "from Pet,Employee,Adoption "+
                        "where Employee.firstName = '"+empFName+
                                "' and Employee.lastName = '"+empLName+
                                "' and Employee.empID = Adoption.empID"+
                                " and Adoption.status = 'pending'"+
                                " and Adoption.petID = Pet.petID";

                Statement stmt = dbconn.createStatement();
                ResultSet answer = stmt.executeQuery(query);
                                                
                boolean hasNext = answer.next();
                if (!hasNext){
                        System.out.println("The employee "+empFName+" "+empLName+" has no pending pet applications.\n");
                        return;
                }

                System.out.println("The following pets have a pending application assigned to "+empFName+" "+empLName);
                while (hasNext){
                        System.out.println("Pet's Name: "+answer.getString("name")
                                                +" | species: "+answer.getString("species")
                                                +" | breed: "+answer.getString("breed"));
                        hasNext = answer.next();
                }
                System.out.println();
                stmt.close();
        }
}





