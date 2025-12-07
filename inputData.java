import java.io.*;
import java.sql.*;

public class inputData {
        private static void myDouble(PreparedStatement stat, int idx, String toParse) throws SQLException {
                if (toParse == null || toParse.trim().isEmpty()) {
                        stat.setNull(idx, java.sql.Types.DOUBLE);
                } else {
                        stat.setDouble(idx,Double.parseDouble(toParse));
                }
        }

        private static void myInt(PreparedStatement stat, int idx, String toParse) throws SQLException {
                if (toParse == null || toParse.trim().isEmpty()) {
                        stat.setNull(idx, java.sql.Types.INTEGER);
                } else {
                        stat.setInt(idx,Integer.parseInt(toParse));
                }
        }

        public static void main (String[] args) {
                final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";


                String username = "alexanderxhaufe";
                String password = "a7394";


                try {
                        Class.forName("oracle.jdbc.OracleDriver");

                } catch (ClassNotFoundException e) {
                        System.err.println("*** ClassNotFoundException, error loading JDBC drivr\n");
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

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into TotalOrder values (?,?,?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'))";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("TotalOrder.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        myInt(toAdd,3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        myDouble(toAdd,5,splitRow[4]);
                                        myInt(toAdd,6,splitRow[5]);
                                        toAdd.setString(7,splitRow[6]);


                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("TotalOrder worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into MenuItem values (?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("MenuItem.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        myDouble(toAdd,4,splitRow[3]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("MenuItem worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }


                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Member values (?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Member.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        System.out.println(splitRow);
                                        myInt(toAdd,1,splitRow[0]);
                                        toAdd.setString(2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        toAdd.setString(5,splitRow[4]);
                                        myInt(toAdd,6,splitRow[5]);
                                        myInt(toAdd,7,splitRow[6]);
                                        myInt(toAdd,8,splitRow[7]);
                                        myInt(toAdd,9,splitRow[8]);
                                        myInt(toAdd,10,splitRow[9]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Member worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }


                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into EventBooking values (?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("EventBooking.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        myInt(toAdd,4,splitRow[3]);
                                        myInt(toAdd,5,splitRow[4]);
                                        myInt(toAdd,6,splitRow[5]);
                                        myInt(toAdd,7,splitRow[6]);


                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("EventBooking worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }


                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Reservation values (?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Reservation.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        myInt(toAdd,3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        toAdd.setString(5,splitRow[4]);
                                        toAdd.setString(6,splitRow[5]);;
                                        myInt(toAdd,7,splitRow[6]);
                                        myInt(toAdd,8,splitRow[7]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Reservation worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }


                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Room values (?,?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Room.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        toAdd.setString(5,splitRow[4]);


                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Room worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Event values (?,?,?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Event.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        toAdd.setString(2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        myInt(toAdd,4,splitRow[3]);
                                        myInt(toAdd,5,splitRow[4]);
                                        myInt(toAdd,6,splitRow[5]);
                                        toAdd.setString(7,splitRow[6]);
                                        toAdd.setString(8,splitRow[7]);
                                        myDouble(toAdd,9,splitRow[8]);
                                        myInt(toAdd,10,splitRow[9]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Event worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Employee values (?,?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Employee.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        toAdd.setString(2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Employee worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into HealthRecord values (?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,TO_DATE(?, 'YYYY-MM-DD'))";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("HealthRecord.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        myInt(toAdd,3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        toAdd.setString(5,splitRow[4]);
                                        toAdd.setString(6,splitRow[5]);
                                        toAdd.setString(7,splitRow[6]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("HealthRecord worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Pet values (?,?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Pet.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        toAdd.setString(2,splitRow[1]);
                                        toAdd.setString(3,splitRow[2]);
                                        toAdd.setString(4,splitRow[3]);
                                        myInt(toAdd,5,splitRow[4]);
                                        toAdd.setString(6,splitRow[5]);
                                        toAdd.setString(7,splitRow[6]);
                                        toAdd.setString(8,splitRow[7]);
                                        toAdd.setString(9,splitRow[8]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Pet worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }


                try {

                        Statement stmt = null;
                        ResultSet answer = null;
                        PreparedStatement toAdd = null;
                        String baseAdd = "insert into Adoption values (?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?)";
                        toAdd = dbconn.prepareStatement(baseAdd);

                        try (BufferedReader stream = new BufferedReader(new FileReader("Adoption.csv"))) {
                                String row = null;
                                while ((row = stream.readLine()) != null) {
                                        String[] splitRow = row.split(",",-1);
                                        myInt(toAdd,1,splitRow[0]);
                                        myInt(toAdd,2,splitRow[1]);
                                        myInt(toAdd,3,splitRow[2]);
                                        myInt(toAdd,4,splitRow[3]);
                                        toAdd.setString(5,splitRow[4]);
                                        toAdd.setString(6,splitRow[5]);
                                        myDouble(toAdd,7,splitRow[6]);

                                        toAdd.addBatch();
                                }
                                toAdd.executeBatch();
                                System.out.println("Adoption worked!");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                        dbconn.close();
                } catch (SQLException e) {
                        System.err.println("*** SQLException:");
                        System.err.println("\tMessage:  "+e.getMessage());
                        System.err.println("\tSQLState: "+e.getSQLState());
                        System.err.println("\tErrorCode:        "+e.getErrorCode());
                        System.exit(-1);
                }

                System.out.println("All tables added to successfully!");
        }
}


