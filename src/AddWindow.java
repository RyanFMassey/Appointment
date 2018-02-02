import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * AddWindow is the class that allows the user to add and store appointments to the file by creating a 
 * JFrame and adding several swing elements. 
 * 
 * @author Ryan Fan-Massey 14047957
 * @version 1.0
 */

public class AddWindow extends JFrame{
	//Declare variables and swing objects
	JButton addAppointment = null;
	JButton cancel = null;
	JTextField days = null;
	JTextField months = null;
	JTextField years = null;
	JTextField type = null;
	JTextField hour = null;
	JTextField minute = null;
	JTextField length = null;
	JLabel datel = null; //date label
	JLabel typel = null; //type label
	JLabel timel = null; //time label
	JLabel lengthl = null; //length label
	
	/**
	 * Default and only constructor used when creating the class, takes no parameters
	 * This constructor assigns a new element to the already declared swing elements and adds them to the frame. The constructor also
	 * creates and event handler and adds listeners to the buttons.
	 */
	public AddWindow(){
		
		super("Add");
		
		JPanel buttonContainer = new JPanel(); //create new JPanel, used to contain the buttons
		
		addAppointment = new JButton("Add Appointment"); //creates new button and assigns it to addAppointment
		cancel = new JButton("Cancel"); //creates new button and assigns it to cancel
		
		buttonContainer.add(addAppointment); //adds the buttons to the button container
		buttonContainer.add(cancel);
		
		setLayout(new GridBagLayout()); //sets the layout of the frame to grid bag layout
		GridBagConstraints c = new GridBagConstraints();  //creates a new grid bag constraints object, c
		
		c.fill = GridBagConstraints.BOTH; //sets the fill constraint 
		c.insets = new Insets(2, 2, 2, 2);  //sets the insets
		
		datel = new JLabel("Please enter the date of the appointment: "); //creates new label4
		c.gridx = 0;  //sets the grid x position to 0
		c.gridy = 0; //sets the grid y position to 0
		c.gridwidth = 1; //sets the width to 1 cell
		this.getContentPane().add(datel, c); //adds the element to the frame with constraints c
		
		//repeat for each element
		days = new JTextField(3);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(days, c);
		
		
		months = new JTextField(3);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(months, c);
		
		
		years = new JTextField(6);
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(years, c);
		
		
		timel = new JLabel("Please enter the time of the appointment: ");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(timel, c);
		
		
		hour = new JTextField(3);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(hour, c);
		
		
		minute = new JTextField(3);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(minute, c);
		
		
		lengthl = new JLabel("Please enter the length of the appointment, in minutes: ");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		this.getContentPane().add(lengthl, c);
		
		
		length = new JTextField(3);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		this.getContentPane().add(length, c);
		
		typel = new JLabel("Please enter the type of the appointment: ");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		this.getContentPane().add(typel, c);
		
		
		type = new JTextField();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		this.getContentPane().add(type, c);
		
		//buttonContainer
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		this.getContentPane().add(buttonContainer, c);
		
		//set frame properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(570, 200);
		
		//creates new event handler
		myEventHandler h = new myEventHandler();
		addAppointment.addActionListener(h); //add listeners to the buttons
		cancel.addActionListener(h);

	}
	
	/**
	 * Event class handler used to handle each of the button presses
	 */
	public class myEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addAppointment){
				try {
					int day = Integer.parseInt(days.getText()); //get the entered information from the textboxes and assign it to the corresponding variable
					int month = Integer.parseInt(months.getText());
					int year = Integer.parseInt(years.getText());
					int hrs = Integer.parseInt(hour.getText());
					int min = Integer.parseInt(minute.getText());
					int len = Integer.parseInt(length.getText());
					String app = type.getText();
					GregorianCalendar startDate = new GregorianCalendar(year, month, day, hrs, min);  //create a start and end date using the entered data
					GregorianCalendar endDate = new GregorianCalendar(year, month, day, hrs, min+len);
					Appointment temp = new Appointment(startDate, endDate, app); //create a new appointment
					
					
					try {
						if (checkValidDate(day, month, year) && checkValidTime(hrs, min)) { //if the entered date is valid
							if (!checkAppointmentOverlap(startDate, endDate)) { //if the appointment does not overlap with any already stored appointment
								FileWriter fw = new FileWriter("appointments.csv", true);  //write to file appointments but append, not overwrite
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter pw = new PrintWriter(bw);
						
								pw.println(temp);

						
								pw.close(); //close writers
								bw.close();
								fw.close();
							
								JOptionPane.showMessageDialog(new JFrame(), "Appointment Successfully Added!"); //alert user that appointment was added
						
								days.setText(""); //Reset form
								months.setText("");
								years.setText("");
								hour.setText("");
								minute.setText("");
								length.setText("");
								type.setText("");
										
								MainForm2 m = new MainForm2(); //Reload main form after it is disposed so that the new data appears
								dispose(); //dispose of window	
							} else {
								JOptionPane.showMessageDialog(new JFrame(), "You already have an appointment at the given start time or you have tried to make an appointment during an already stored appointment.\nAppointment was not added.");
							}
						} else if (!checkValidDate(day, month, year)) {
							JOptionPane.showMessageDialog(new JFrame(), "You have not entered a valid date. Please try again.");
						} else if (!checkValidTime(hrs, min)) {
							JOptionPane.showMessageDialog(new JFrame(), "You have not entered a valid time. Please try again.");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(new JFrame(), "There was an error with the file.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), "There was an error when adding this appointment.\nPlease check the values you have entered.");
				}
				
			}
			else if (e.getSource() == cancel){
				MainForm2 m = new MainForm2(); //create new main form
				dispose(); //dispose of this form
			}
		}
	}
	
	
	/**
	 * This method is used to check the validity of the date that the user has tried to add to the file. Depending on 
	 * the month that is being added, it checks to see if the date entered is within the valid range of dates.
	 * 
	 * @param day day of appointment that the user wants to add
	 * @param month month of appointment that the user wants to add
	 * @param year year of appointment that the user wants to add
	 * @return true if the date is valid
	 */
	public boolean checkValidDate(int day, int month, int year) {
		
		switch (month) { //depending on the month
		case 1: if (day <= 31 && day > 0) { //check that the date is valid for that specific month
					return true; //return true if valid
				} else {
					return false; //return false if invalid
				}
		case 2: if (year%4 == 0 && day <= 29 && day > 0) { //year%4 is used to check for valid date on leap year
					return true;
				} else if (day <= 28 && day > 0) {
					return true;
				} else {
					return false;
				}
		case 3: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}

		case 4: if (day <= 30 && day > 0) {
					return true;
				} else {
					return false;
				}

		case 5: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}

		case 6: if (day <= 30 && day > 0) {
					return true;
				} else {
					return false;
				}
		
		case 7: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}
		
		case 8: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}
		
		case 9: if (day <= 30 && day > 0) {
					return true;
				} else {
					return false;
				}
		
		case 10: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}
		
		case 11: if (day <= 30 && day > 0) {
					return true;
				} else {
					return false;
				}
			
		case 12: if (day <= 31 && day > 0) {
					return true;
				} else {
					return false;
				}
		default: return false;
		}
	}
	
	/**
	 * This method is used to check whether the user has entered a valid time by checking that the hours are between 0 and 23
	 * and whether the minutes are between 0 and 59
	 * 
	 * @param hours the hours of the time of the appointment that the user wants to add
	 * @param mins the minutes of the time of the appointment that the user wants to add
	 * @return true if the time is valid
	 */
	public boolean checkValidTime(int hours, int mins) {
		if (hours <= 23 && hours >=0 && mins <= 59 && mins >= 0) { //makes sure that the entered hours and mniutes are valid
			return true; //return true if it is valid
		} else
			return false; //return false if invalid
	}
	
	/**
	 * This method is used to check if the user is trying to add an appointment at a time where there is already
	 * an appointment scheduled. If the start time or end time is between an already stored appointment then the
	 * method returns true
	 * 
	 * @param startTime start time of the appointment being added
	 * @param endTime end time of the appointment being added
	 * @return true if there is an overlap
	 * @throws IOException
	 */
	public boolean checkAppointmentOverlap(GregorianCalendar startTime, GregorianCalendar endTime) throws IOException {
		
		boolean overlap = false;
		FileReader fr = new FileReader("appointments.csv");
		BufferedReader br = new BufferedReader(fr);
		
		startTime.add(Calendar.MONTH, -1); //User months start at 1 where as gregorian calendar months start at 0
		endTime.add(Calendar.MONTH, -1);
		String line = null;
		while ((line = br.readLine()) != null) {  //while line is not null i.e. there is a line to be read
			Appointment temp = MainForm2.toAppointment(line);  //convert appointment as string to Appointment object using toAppointment method
			GregorianCalendar tempStart = temp.getStartDateTime(); //get the start time as gregorian calendar
			GregorianCalendar tempEnd = temp.getEndDateTime(); //get the end time as gregorian calendar
			
			if (startTime.compareTo(tempStart) >= 0 && startTime.compareTo(tempEnd) < 0) { //compare the start time of appointment being added to start and end time of stored appointment
				overlap = true; //if it lies between a set of start and end times then return true
			}
			if (endTime.compareTo(tempStart) >= 0 && endTime.compareTo(tempEnd) < 0) { //compare the end time of appointment being added to start and end time of stored appointment
				overlap = true; //if the end time lies between the start and end of an already store appointment then return true
			}
		}
		br.close();
		fr.close();
		
		startTime.add(Calendar.MONTH, 1); //add month back so that no error in information when it is added as another month would be taken away
		endTime.add(Calendar.MONTH, 1);
		return overlap;
	}
	
}
