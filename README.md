# Appointment_Management_System

The Appointment Management System program provides a user interface for adding, updating and deleting customer records and appointments stored in a connected SQL Database. The program provides alerts and prompts when met with certain parameters, and tracks user login attempts. 

Author:
Erik Scovin
escovin@wgu.edu
V1.5.5.2021

IntelliJ Community 2020.3
JDK 11.0.10
JavaFX-SDK-11.0.2

How to Run Program:

Login with proper credentials on Login page - username and password are 'test'.

Choose between weekly/monthly appointment views via radio buttons.

Add a customer by filling out the Add Customer Form found through the "Add Customer" page button.

Modify an existing customer by clicking the "Modify Customer" page button, selecting a customer to modify, and updating with valid form entries. Click "Delete Customer" to remove customer record. 

Add an appointment by clicking the "Add Appointment" page button and filling out the form with valid entries.

Modify an exisiting appointment by clicking the "Modify Appointment" button, selecting an appointment, and updating the from with valid entries. Click "Cancel Appointment" to delete an existing appointment.

Click "Reports" button on the main screen to navigate to the reports page. 
*For appointment count by month/type, select a Month from the month combo menu, followed by a type from the type combo menu, and the appointment count will populate under the "Count" label.
*For appointment schedules by contact, select a contact from the contact combo menu and the table will populate with a schedule for the selected contact.

--ADDITIONAL REPORT--
******For an appointment list by associated customer, select a customer from the customer combo meny and the table will populate with all apointments for that customer*******

MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver"




