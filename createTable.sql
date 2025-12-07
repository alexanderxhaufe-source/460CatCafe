/* added some FK constraints here, im not sure if they work or not so i left dropCreateTable unchanged*/
CREATE TABLE TotalOrder (
        orderID number(4),
        memberID number(4) references Member(memberID),
        reservationID number(4) references Reservation(reservationID),
        orderTime varChar2(8),
        totalPrice number(6,2),
        paymentStatus number(1),
        orderDate date,
        primary key (orderID)
);
CREATE TABLE MenuItem (
        menuID number(4),
        orderID number(4) references TotalOrder(orderID),
        name varChar2(30),
        price number(6,2),
        primary key (menuID)
);
CREATE TABLE Member (
        memberID number(4),
        lastName varChar2(20),
        firstName varChar2(20),
        DoB date,
        email varChar2(50),
        membershipTier number(1),
        emergencyContactAreaCode number(3),
        emergencyContact number(7),
        phoneNoAreaCode number(3),
        phoneNo number(7),
        primary key (memberID)
);
CREATE TABLE EventBooking (
        bookingID number(4),
        custID number(4) references Member(memberID),
        bookDate date,
        eventID number(4) references Event(eventID),
        attendanceStatus number(1),
        paymentStatus number(1),
        membershipTier number(1),
        primary key (bookingID)
);
CREATE TABLE Reservation (
        reservationID number(4),
        customerID number(4) references Member(memberID),
        roomID number(4) references Room(roomID),
        resDate date,
        startTime varChar2(8),
        duration varChar2(5),
        inStatus number(1),
        membershipTier number(1),
        primary key (reservationID)
);
CREATE TABLE Room (
        roomID number(4),
        maxCapacity number(3),
        petType varChar2(20),
        purpose varChar2(20),
        location varChar2(120),
        primary key (roomID)
);
CREATE TABLE Event (
        eventID number(4),
        eventName varChar2(20),
        description varChar2(120),
        roomID number(4) references Room(roomID),
        empID number(4) references Employee(empID),
        maxCapacity number(3),
        eventDate date,
        time varChar2(8),
        attendanceFee number(6,2),
        regCust number(3),
        primary key (eventID)
);
CREATE TABLE Employee (
        empID number(4),
        firstName varChar2(20),
        lastName varChar2(20),
        position varChar2(30),
        primary key (empID)
);
CREATE TABLE HealthRecord (
        recordID number(4),
        petID number(4) references Pet(petID),
        employeeID number(4) references Employee(empID),
        recordDate date,
        recordType varChar2(20),
        description varChar2(120),
        nextDueDate date,
        primary key (recordID)
);
CREATE TABLE Pet (
        petID number(4),
        name varChar2(40),
        species varChar2(20),
        breed varChar2(20),
        age number(2),
        arrivalDate date,
        temperament varChar2(20),
        spNeeds varChar2(120),
        currStat varChar2(30),
        primary key (petID)
);
CREATE TABLE Adoption (
        appID number(4),
        custID number(4) references Member(memberID),
        petID number(4) references Pet(petID),
        empID number(4) references Employee(empID),
        appDate date,
        status varChar2(20),
        price number(6,2),
        primary key (appID)
        constraint chk_status check (status in ('pending', 'approved', 'rejected', 'withdrawn', 'recieved')
);
