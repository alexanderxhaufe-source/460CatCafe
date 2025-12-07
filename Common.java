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

/*
Purpose: This class is meant to contain common methods used across multiple
files in the project.
Class Methods: inputNumber(Scanner, String)
               inputString(Scanner, String)
               inputPrice(Scanner, String)
All these class methods are used to get valid user input, further validation
should be handled by either the database or the calling method.
There could possibly be more methods added here as this project continues.
*/
public class Common {
    /*
	 * Method: inputNumber
	 * Purpose: To get a valid integer input from the user
	 * Parameters: Scanner scanner - the scanner to read user input
	 * String prompt - the prompt to display to the user
	 * Returns: A String representation of the integer input by the user
	 */
	public static String inputNumber(Scanner scanner, String prompt) {
		int number = 0;
		while (true) {
			try {
				System.out.print(prompt);
				number = scanner.nextInt();
				scanner.nextLine(); // move scanner past newline
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid: Must be an integer\n");
				scanner.next();
			}
		}
		return Integer.toString(number);
	}

	/*
	 * Method: inputString
	 * Purpose: To get a valid string input from the user
	 * Parameters: Scanner scanner - the scanner to read user input
	 * String prompt - the prompt to display to the user
	 * Returns: A String input by the user
	 */
	public static String inputString(Scanner scanner, String prompt) {
		String input = "";
		while (true) {
			try {
				System.out.print(prompt);
				input = scanner.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid: Must be a string\n");
				scanner.next();
			}
		}
		return input;
	}

	/*
	 * Method: inputPrice
	 * Purpose: To get a valid price input from the user
	 * Parameters: Scanner scanner - the scanner to read user input
	 * String prompt - the prompt to display to the user
	 * Returns: A String representation of the price input by the user
	 */
	public static String inputPrice(Scanner scanner, String prompt) {
		double price = 0.0;
		while (true) {
			try {
				System.out.print(prompt);
				price = scanner.nextDouble();
				scanner.nextLine(); // move scanner past newline
				// check if precicely two decimal places
				if (Math.round(price * 100) != price * 100) {
					System.out.println("Invalid: Must be a valid price\n");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid: Must be a valid price\n");
				scanner.next(); // move scanner past invalid input
			}
		}
		return Double.toString(price);
	}
}