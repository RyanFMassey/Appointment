import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SearchWindow class is used to create the SearchWindow object. The object then creates the elements used for the interface, adds them to the frame
 * and sets the frame to visible. The search window allows the user to search the appointment file using three different constraints, the date of the
 * appointments, the month of the appointments and the type of the appointments.
 */
public class SearchWindow extends JFrame{
	//Declare variables and swing objects
	JButton sd = null;
	JButton sm = null;
	JButton st = null;
	JButton cancel = null;
	JLabel s1 = null;
	JLabel s2 = null;
	JLabel s3 = null;
	JComboBox month = null;
	JTextField days = null;
	JTextField months = null;
	JTextField years = null;
	JTextField type = null;
	public static boolean search = false;
	
	/**
	 * SearchWindow default constructor
	 */
	public SearchWindow(){
		
		super("Search");
		String[] monthsArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //create array containing months
		
				
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);
		
		s1 = new JLabel("Please enter valid numeric values for a date: ");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(s1, c);
		
		
		days = new JTextField(2);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(days, c);
		
		
		months = new JTextField(2);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(months, c);
		
		
		years = new JTextField(4);
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(years, c);
		
		
		sd = new JButton("Search by Date");
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(sd, c);
		
		
		s2 = new JLabel("Please select a month: ");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(s2, c);
		
		
		month = new JComboBox(monthsArray); //create combo box containing month array
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		this.getContentPane().add(month, c);
		
		
		sm = new JButton("Search by Month");
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(sm, c);
		
		
		s3 = new JLabel("Please enter a type of appointment: ");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		this.getContentPane().add(s3, c);
		
		
		type = new JTextField(15);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		this.getContentPane().add(type, c);
		
		
		st = new JButton("Search by Type");
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 1;
		this.getContentPane().add(st, c);
	
		cancel = new JButton("Cancel");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 5;
		c.insets = new Insets(8, 2, 2, 2);
		this.getContentPane().add(cancel, c);
		
		myEventHandler h = new myEventHandler();
		sd.addActionListener(h);
		sm.addActionListener(h);
		st.addActionListener(h);
		cancel.addActionListener(h);
	
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(640, 175);
		
		
	}
	
	
	/**
	 * Event class handler used to handle each of the button presses
	 */
	public class myEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Search by date
			if (e.getSource() == sd){
				try {
					int day = Integer.parseInt(days.getText());
					int month = Integer.parseInt(months.getText());
					int year = Integer.parseInt(years.getText());
					
					GregorianCalendar searchDate = new GregorianCalendar(year, month, day); //create gregorian calendar object using the inputted information
					
					String line = null;
					
					AppointmentBook results = new AppointmentBook();
					FileReader fr = new FileReader("appointments.csv");
					BufferedReader br = new BufferedReader(fr);
					
					while ((line = br.readLine()) != null) {
						String[] apps = line.split(","); //split the line when a comma is reached and put into a string array
						GregorianCalendar data = new GregorianCalendar(Integer.parseInt(apps[3]), Integer.parseInt(apps[2]), Integer.parseInt(apps[1])); //create a new gregorian calendar
						if (searchDate.compareTo(data) == 0) {  //if the searched date and a stored date match
							results.add(MainForm2.toAppointment(line)); //convert the current line to an appointment and add it to the results AppointmentBook
						}
					}
					br.close(); //close readers
					fr.close();
					
					if (results.getAllAppointments().isEmpty()) {
						JOptionPane.showMessageDialog(new JFrame(), "There were no results from your search");  //alert the user when their search returned no results
					} else {
						MainForm2.appBook = results; //assign results to global variable appBook
						search = true; //set search to true so the main form knows that a search has been used
						MainForm2 m = new MainForm2();
						dispose();
					}
					
				} catch (FileNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), "There was an error with your search, please make sure that the details you entered are correct.");
				}
				

				
			//Search by month
			} else if (e.getSource() == sm){
				try {
					int searchMonth = month.getSelectedIndex() + 1;
					
					AppointmentBook results = new AppointmentBook();
					FileReader fr = new FileReader("appointments.csv");
					BufferedReader br = new BufferedReader(fr);
					
					String line = null;
					while ((line = br.readLine()) != null) {
						String[] apps = line.split(",");
						if (searchMonth == Integer.parseInt(apps[2])) {  //if the index of selected month of the combobox is equal to the month of the stored appointments
							results.add(MainForm2.toAppointment(line)); //add the current appointment to the results appointment book
						}
					}
					br.close();
					fr.close();
					
					if (results.getAllAppointments().isEmpty()) {
						JOptionPane.showMessageDialog(new JFrame(), "There were no results from your search");
					} else {
						MainForm2.appBook = results;
						search = true;
						MainForm2 m = new MainForm2();
						dispose();
					}
					
				} catch (FileNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				} 
				
				
			//Search by type
			} else if (e.getSource() == st){
				try {
					String searchType = type.getText();
					
					AppointmentBook results = new AppointmentBook();
					FileReader fr = new FileReader("appointments.csv");
					BufferedReader br = new BufferedReader(fr);
					
					String line = null;
					while ((line = br.readLine()) != null) {
						String[] apps = line.split(",");
						if (apps[0].toLowerCase().contains(searchType.toLowerCase())) { //convert the type of the stored appointment to lower case and check if it contains the searched type
							results.add(MainForm2.toAppointment(line)); //if it does, add it to the results AppointmentBook
						}
					}
					br.close();
					fr.close();
					
					if (results.getAllAppointments().isEmpty()) {
						JOptionPane.showMessageDialog(new JFrame(), "There were no results from your search");
					} else {
						MainForm2.appBook = results;
						search = true;
						MainForm2 m = new MainForm2();
						dispose();
					}
					
				} catch (FileNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					System.out.println("INVALID");
				}
			} else if (e.getSource() == cancel) {
				MainForm2 m = new MainForm2();
				dispose();
			}
		}
	}
	
	
	
}
