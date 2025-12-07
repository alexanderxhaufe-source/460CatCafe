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
        //TODO fix operning comment
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
         * Returns: A String query that holds a ready SQL query. This query will
         * specifically be for deleting one or many items from a specific table. If an
         * invalid input is given, query will be an empty String
         *
         * Purpose: This function is meant to act as a hub for all delete
         * statements in the program! It works with switch statements, each int
         * representing a different Table in our DB (see above, in Parameters).
         * Once a query is crafted, it gets returned so that the query can be
         * sent to Oracle.
         *
         *
         * Some of the variables have names that end in numbers. Due to the way
         * switch statements work (special fellas, aren't they?), I either would
         * have to declare all of the variables before entering the switch
         * statement, or would have to give all vars unique names; I chose the
         * latter option :/
         * -----------------------------------------------------------------------
         */

        public static String deleteFromTable(int tableValue, Scanner scanner) {
                String query = "";
                String userString = "";
                int userChoice = 0;
                int userChoice2 = 0;
                switch (tableValue) {
                        case 1:
                                //delete from Adoption
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by adoptionID");
                                        System.out.println("2. Delete all applications for a pet by petID");
                                        System.out.println("3. Delete all applications from a customer by custID");
                                        System.out.println("4. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 4) {
                                                return;
                                        } else if (userChoice >0 && userChoice <4){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter adoptionID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Adoption where adoptionID = '"+userChoice2+"'";
                                                break;
                                        case 2:
                                                System.out.println("Enter petID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Adoption where petID = '"+userChoice2+"'";
                                                break;
                                        case 3:
                                                System.out.println("Enter custID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Adoption where custID = '"+userChoice2+"'";
                                                break;
                                }
                                return query;
                                break;

                        case 2:
                                // delete from EventBooking
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by bookingID");
                                        System.out.println("2. Delete all bookings for an event by eventID");
                                        System.out.println("3. Delete all bookings from a customer by custID");
                                        System.out.println("4. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 4) {
                                                return;
                                        } else if (userChoice >0 && userChoice <4){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter bookingID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from EventBooking where bookingID = '"+userChoice2+"'";
                                        case 2:
                                                System.out.println("Enter eventID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from EventBooking where eventID = '"+userChoice2+"'";
                                                break;
                                        case 3:
                                                System.out.println("Enter custID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from EventBooking where custID = '"+userChoice2+"'";
                                                break;
                                }


                                return query;
                                break;

                        case 3:
                                //Delete from HealthRecord
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by recordID");
                                        System.out.println("2. Delete all records for a pet by petID");
                                        System.out.println("3. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 3) {
                                                return;
                                        } else if (userChoice >0 && userChoice <3){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter adoptionID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from HealthRecord where recordID = '"+userChoice2+"'";
                                                break;
                                        case 2:
                                                System.out.println("Enter petID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from HealthRecord where petID = '"+userChoice2+"'";
                                                break;
                                }
                                return query;
                                break;

                        case 4:
                                // Delete From Member
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by memberID");
                                        System.out.println("2. Delete a member by name");
                                        System.out.println("3. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 3) {
                                                return;
                                        } else if (userChoice >0 && userChoice <3){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter memberID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Member where memberID = '"+userChoice2+"'";
                                                break;
                                        case 2:
                                                System.out.println("Enter full name or -1 to cancel");
                                                try {
                                                        userString = scanner.nextLine().trim();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userString == "-1"){
                                                        return;
                                                }
                                                String[] name = userString.split(" ");
                                                if (name.length != 2){
                                                        System.out.println("invalid input\n");
                                                }
                                                query = "delete from Member where firstName = '"+name[0]+"'"
                                                        +" and lastName = '"+name[1]+"'";
                                }
                                return query;
                                break;

                        case 5:
                                // delete from Pet
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by petID");
                                        System.out.println("2. Delete a pet by name");
                                        System.out.println("3. Delete all pets of a species by name");
                                        System.out.println("4. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 4) {
                                                return;
                                        } else if (userChoice >0 && userChoice <4){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter petID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Pet where petID = '"+userChoice2+"'";
                                                break;
                                        case 2:
                                                System.out.println("Enter the pet's name or -1 to cancel");
                                                try {
                                                        userString = scanner.nextLine().trim();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userString == "-1"){
                                                        return;
                                                }

                                                query = "delete from Pet where name = '"+userString+"'";
                                                break;
                                        case 3:
                                                System.out.println("Enter the species being removed or -1 to cancel");
                                                try {
                                                        userString = scanner.nextLine().trim();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userString == "-1"){
                                                        return;
                                                }

                                                query = "delete from Pet where species = '"+userString+"'";
                                                break;
                                }
                                return query;
                                break;

                        case 6:
                                // Delete from Reservation
                                while (true){
                                        System.out.println("Enter the number of what to delete by:");
                                        System.out.println("1. Delete single entry by reservationID");
                                        System.out.println("2. Delete all reservations from a customer by custID");
                                        System.out.println("3. Delete all reservations for a room by roomID");
                                        System.out.println("4. return without deleting");

                                        try {
                                                userChoice = scanner.nextInt();
                                        } catch(InputMismatchException e) {
                                                System.out.println("Invalid input\n");
                                                scanner.next();
                                        }
                                        System.out.println();
                                        if (userChoice == 4) {
                                                return;
                                        } else if (userChoice >0 && userChoice <4){
                                                break;
                                        }
                                        System.out.println("Invalid input\n");
                                }
                                scanner.nextLine(); // move scanner
                                switch(userChoice){
                                        case 1:
                                                System.out.println("Enter reservationID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Reservation where reservationID = '"+userChoice2+"'";
                                                break;
                                        case 2:
                                                System.out.println("Enter custID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Reservation where custID = '"+userChoice2+"'";
                                                break;
                                        case 3:
                                                System.out.println("Enter roomID or -1 to cancel");
                                                try {
                                                        userChoice2 = scanner.nextInt();
                                                } catch (InputMismatchException e){
                                                        System.out.println("Invalid input\n");
                                                        scanner.next();
                                                        return;
                                                }
                                                if (userChoice2 == -1){
                                                        return;
                                                }

                                                query = "delete from Reservation where roomID = '"+userChoice2+"'";
                                                break;
                                }
                                return query;
                                break;


