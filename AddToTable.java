public class AddToTable {
	
	/* ------------------------------------------------------------------------
	 * addToTable(int)
	 * Requires the DB to already contain the tables listed in Parameters, each
	 *    with the proper attributes in their given order. THIS WILL NOT WORK 
	 *    OTHERWISE.
	 *    
	 * Parameters: tableValue - an int that represents one of the many tables
	 *    for our program! Here are the values:
	 *         1. Adoption
	 *         2. Employee
	 *         3. Event
	 *         4. EventBooking
	 *         5. HealthRecord
	 *         6. Member
	 *         7. MenuItem
	 *         8. Pet
	 *         9. Reservation
	 *         10. Room
	 *         11. TotalOrder
	 *         Else: nothing! why would you try to put in something else?
	 *         
	 * Returns: A String query that holds a ready SQL query. This query will
	 *    specifically be for inserting an item into a specific table. If an
	 *    invalid int is given as the tableValue, query will be an empty String
	 *    
	 * Purpose: This function is meant to act as a hub for all insert 
	 *    statements in the program! It works with switch statements, each int 
	 *    representing a different Table in our DB (see above, in Parameters). 
	 *    Once a query is crafted, it gets returned so that the query can be 
	 *    sent to Oracle.
	 * 
	 * 
	 * THIS FUNCTION IS NOT COMPLETE
	 * Currently, it is set up where all of the inputs stay as empty strings
	 * Input options will have to be added, either as an extension of the
	 * 	switch statement, or as an entirely new function ( addMember(), for
	 *  example)
	 *  
	 * A print statement has been added and commented-out at the end of each
	 *  statement just for testing. It does not guarentee the actual adding of
	 *  an object to a table.
	 *  
	 * Some of the variables have names that end in numbers. Due to the way 
	 *  switch statements work (special fellas, aren't they?), I either would
	 *  have to declare all of the variables before entering the switch 
	 *  statement, or would have to give all vars unique names; I chose the 
	 *  latter option :/
	 *-----------------------------------------------------------------------*/
	String addToTable(int tableValue) {
		String query = "";
		switch (tableValue) {
		case 1:
			// add Adoption
			String appID="", custID="", petID="", empID="", appDate="", 
				status="", price="";
			query = "INSERT INTO Adoption (appID, custID, petID, empID, appDate"
					+ ", status, price) "
					+ "VALUES (" + appID + ", " + custID + ", " + petID + ", " 
					+ empID + ", " + appDate + ", " + status + ", " + price 
					+ ");";
			//System.out.println("Successfully added new Adoption to datatable");
		break;
		
		case 2:
			// add Employee
			String empID1="", firstName="", lastName="", position="";
			query = "INSERT INTO Employee (empID, firstName, lastName, position"
					+ ") "
					+ "VALUES (" + empID1 + ", " + firstName + ", " + lastName 
					+ ", " + position + ");";
			//System.out.println("Successfully added new Employee to datatable");
		break;
		
		case 3:
			// add Event
			String eventId="", eventName="", description="", roomID="", 
				empID2="", maxCapacity="", eventDate="", time="", 
				attendanceFee="", regCust="";
			query = "INSERT INTO Event (eventId, eventName, description, roomID"
					+ ", empID, maxCapacity, eventDate, time, attendanceFee, "
					+ "regCust) "
					+ "VALUES (" + eventId + ", " + eventName + ", " + 
					description + ", " + roomID + ", " + empID2 + ", " + 
					maxCapacity + ", " + eventDate + ", " + time + ", " + 
					attendanceFee + ", " + regCust + ");";
			//System.out.println("Successfully added new Event to datatable");
		break;
		
		case 4:
			// add EventBooking
			String bookingID="", custID1="", bookDate="", eventID="", 
				attendanceStatus="", paymentStatus="";
			query = "INSERT INTO EventBooking (bookingID, custID, bookDate, "
					+ "eventID, attendanceStatus, paymentStatus) "
					+ "VALUES (" + bookingID + ", " + custID1 + ", " + bookDate 
					+ ", " + eventID + ", " + attendanceStatus + ", " 
					+ paymentStatus + ");";
			//System.out.println("Successfully added new EventBooking to datatable");
		break;
		
		case 5:
			// add HealthRecord
			String recordID="", petID1="", employeeID="", recordDate="", 
				recordType="", description1="", nextDueDate="";
			query = "INSERT INTO HealthRecord (recordID, petID, employeeID, "
					+ "recordDate, recordType, description, nextDueDate) "
					+ "VALUES (" + recordID + ", " + petID1 + ", " + employeeID 
					+ ", " + recordDate + ", " + recordType + ", " + 
					description1 + ", " + nextDueDate + ");";
			//System.out.println("Successfully added new HealthRecord to datatable");
		break;
		
		case 6:
			// add Member
			String memberID="", lastName1="", firstName1="", DoB="", email="", 
				membershipTier="", emergencyContactAreaCode="", 
				emergencyContact="", phoneNoAreaCode="", phoneNo="";
			query = "INSERT INTO Member (memberID, lastName, firstName, DoB, "
					+ "email, membershipTier," + " emergencyContactAreaCode, "
					+ "emergencyContact, phoneNoAreaCode, phoneNo) "
					+ "VALUES (" + memberID + ", " + lastName1 + ", " + 
					firstName1 + ", " + DoB + ", " + email + ", " + 
					membershipTier + ", " + emergencyContactAreaCode + ", " + 
					emergencyContact + ", " + phoneNoAreaCode + ", " + phoneNo 
					+ ");";
			//System.out.println("Successfully added new Member to datatable");
		break;
		
		case 7:
			// add MenuItem
			String menuID="", orderID="", name="", price1="";
			query = "INSERT INTO MenuItem (menuID, orderID, name, price) "
					+ "VALUES (" + menuID + ", " + orderID + ", " + name + ", "
					+ price1 +");";
			//System.out.println("Successfully added new MenuItem to datatable");
		break;
		
		case 8:
			// add Pet
			String petID2="", name1="", species="", breed="", age="", 
				arrivalDate="", temperament="", spNeeds="", currStat="";
			query = "INSERT INTO Pet (petID, name, species, breed, age, "
					+ "arrivalDate, temperament, spNeeds, currStat) "
					+ "VALUES (" + petID2 + ", " + name1 + ", " + species + ", " 
					+ breed + ", " + age + ", " + arrivalDate + ", " 
					+ temperament + ", " + spNeeds + ", " + currStat + ");";
			//System.out.println("Successfully added new Pet to datatable");
		break;
		
		case 9:
			// add Reservation
			String reservationID="", customerID="", roomID1="", resDate="", 
				startTime="", duration="", inStatus="";
			query = "INSERT INTO Reservation (reservationID, customerID, roomID"
					+ ", resDate, startTime, duration, inStatus) "
					+ "VALUES (" + reservationID + ", " + customerID + ", " + 
					roomID1 + ", " + resDate + ", " + startTime + ", " + 
					duration + ", " + inStatus + ");";
			//System.out.println("Successfully added new Reservation to datatable");
		break;
		
		case 10:
			// add Room
			String roomID3="", maxCapacity1="", petType="", purpose="";
			query = "INSERT INTO Room (roomID, maxCapacity, petType, purpose) "
					+ "VALUES (" + roomID3 + ", " + maxCapacity1 + ", " + petType
					+ ", " + purpose + ");";
			//System.out.println("Successfully added new Room to datatable");
		break;
		
		case 11:
			// add TotalOrder
			String orderID1="", memberID1="", reservationID1="", orderTime="", 
				totalPrice="", paymentStatus1="";
			query = "INSERT INTO TotalOrder (orderID, memberID, reservationID,"
					+ " orderTime, totalPrice, paymentStatus) "
					+ "VALUES (" + orderID1 + ", " + memberID1 + ", " + 
					reservationID1 + ", " + orderTime + ", " + totalPrice + ", "
					+ paymentStatus1 + " );";
			//System.out.println("Successfully added new TotalOrder to datatable");
		break;
		
		default:
			// invalid number is given, do nothing? not really sure where we
			//   want to go with this one.. for now, query will be "".
			System.out.println("Invalid Value in addToTable()");
		break;
		}
		
		return query;
	}	// addToTable();

}
