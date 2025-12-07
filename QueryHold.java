import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.lang.Math;
import java.time.LocalDate;

public class QueryHold {
        
        // method for query 1
        private static void QueryOne(Connection dbconn, String pet_name) {
        System.out.println();
        String query = 
                        "select a.appID \"Application ID\", m.firstName || \' \' || m.lastName \"Applicant Name\", " +
                        "a.appDate \"Date\", a.status \"Status\", e.firstName || \' \' || e.lastName \"Adoption Coordinator\" " +
                        "from alexanderxhaufe.Member m, alexanderxhaufe.Adoption a, alexanderxhaufe.Employee e, alexanderxhaufe.Pet p " +
                        "where a.petID = p.petID " +
                        "and a.empID = e.empID " +
                        "and a.custID = m.memberID " +
                        "and p.name = \'" + pet_name + "\'";
        Statement stmt = null;
        ResultSet answer = null;
        try {
                stmt = dbconn.createStatement();
                answer = stmt.executeQuery(query);

                if (answer != null) {

                ResultSetMetaData answermetadata = answer.getMetaData(); // get result set metadata

                // print column headers
                for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                        System.out.print(answermetadata.getColumnName(i) + "\t\t");
                }
                System.out.println();
                // print rows of the result set
                while (answer.next()) {
                        int appID = answer.getInt("Application ID");
                        String applicantName = answer.getString("Applicant Name");
                        Date appDate = answer.getDate("Date");
                        String status = answer.getString("Status");
                        String coordinatorName = answer.getString("Adoption Coordinator");
                        System.out.println(appID + "\t" + applicantName + "\t" + appDate + "\t" + status + "\t" + coordinatorName);
                }
                }
                System.out.println();
                stmt.close();
                } catch (SQLException e) {
                        System.err.println("*** SQLException:  "
                                + "Could not fetch query results.");
                        System.err.println("\tMessage:   " + e.getMessage());
                        System.err.println("\tSQLState:  " + e.getSQLState());
                        System.err.println("\tErrorCode: " + e.getErrorCode());
                        System.exit(-1);
                }
        }

        public static void main (String[] args) {
                final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

                String query = "";

                String username = "alexanderxhaufe";
                String password = "a7394";

                if (args.length == 2) {
                        username = args[0];
                        password = args[1];
                }

                try {
                        Class.forName("oracle.jdbc.OracleDriver");

                } catch (ClassNotFoundException e) {
                        System.err.println("*** ClassNotFoundException, error loading JDBC driver\n");
                        System.exit(-1);
                }

                Connection dbconn = null;

                try {
                        dbconn = DriverManager.getConnection(oracleURL,username,password);
                } catch (SQLException e) {
                        System.err.println("**** SQLException: could not open connection.");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }
                //BELOW HERE the db is connected
                Statement stmt = null;
                ResultSet answer = null;

                Scanner stream = new Scanner(System.in);
                try {
                        while (true) {
                                System.out.println("\n This is a placeholder file to test queries\n"+
                                                "Type 3 for query 3 (upcoming events with openings)\n"+
                                                "Type 5 to exit");
                                int selected = stream.nextInt();
                                stream.nextLine();
                                switch(selected) {
                                        case 1:
                                                QueryOne(dbconn, "mittens");
                                                break;
                                        case 2:
                                                break;
                                        case 3:
                                                // CASE 3 is to list all upcoming events that have available capacity
                                                query = """
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
                                                //String toPrint = "";
                                                while(answer.next()){
                                                        System.out.println("Event Name: "+answer.getString("eventName")
                                                                        +" | Date: "+answer.getDate("eventDate")
                                                                        +" | Time: "+answer.getString("time")
                                                                        +" | Room Location: "+answer.getString("location")
                                                                        +" | Registered Attendees: "+answer.getInt("registered")
                                                                        +" | Maximum Capacity: "+answer.getInt("maxCapacity")
                                                                        +" | Coordinator: "+answer.getString("description")
                                                                        );
                                                }
                                                System.out.println();
                                                break;
                                        case 4:
                                                break;
                                        case 5:
                                                stream.close();
                                                stmt.close();
                                                dbconn.close();
                                                return;

                                        default:
                                                System.out.println("only enter 1-5");
                                                continue;
                                }
                                }
                        } catch (SQLException e) {
                                System.err.println("*** SQLException:");
                                System.err.println("\tMessage:  "+e.getMessage());
                                System.err.println("\tSQLState: "+e.getSQLState());
                                System.err.println("\tErrorCode:        "+e.getErrorCode());
                                System.exit(-1);
                        }
                }
        }

