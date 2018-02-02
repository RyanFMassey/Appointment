import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This is the main form class which is used to create the main form object which creates the window where the user can see all of the appointments.
 * This window also acts as the main navigation menu where they can go to other windows such as add and delete where they can manipulate
 * the appointments.
 * 
 * @author Ryan Fan-Massey
 *
 */
public class MainForm2 extends JFrame{
	//Declare variables and swing objects
	JTable tb = null;  //table
	JButton a = null;  //add
	JButton d = null;  //delete
	JButton s = null;  //search
	JButton cs = null;  //clear search
	AddWindow aw = null;
	DeleteWindow dw = null;
	SearchWindow sw = null;
	public static AppointmentBook appBook = new AppointmentBook();
	public static Appointment toDelete = null;
	
	/**
	 * MainForm2 default constructor, used to create the window and elements and add the elements to the frame. This is also where the data
	 * is put in to the table so the user can easily see the appointments
	 */
	public MainForm2() {
		super ("Appointment System Assignment");
		this.setLayout(new BorderLayout());
		myEventHandler h = new myEventHandler();
		
		String line = null;
		
		if (SearchWindow.search != true) {
		try {
			FileReader fr = new FileReader("appointments.csv");
			BufferedReader br = new BufferedReader(fr);
			appBook = new AppointmentBook(); //reset appBook to stop repeating data
			while ((line = br.readLine()) != null) {
				try {
				String[] apps = line.split(",");
				String type = apps[0];
				int sDay = Integer.parseInt(apps[1]);
				int sMon = Integer.parseInt(apps[2]);
				int sYear = Integer.parseInt(apps[3]);
				int sHour = Integer.parseInt(apps[4]);
				int sMin = Integer.parseInt(apps[5]);
				int eDay = Integer.parseInt(apps[6]);
				int eMon = Integer.parseInt(apps[7]);
				int eYear = Integer.parseInt(apps[8]);
				int eHour = Integer.parseInt(apps[9]);
				int eMin = Integer.parseInt(apps[10]);
				
				Appointment appointment = new Appointment(new GregorianCalendar(sYear, sMon-1, sDay, sHour, sMin), new GregorianCalendar(eYear, eMon-1, eDay, eHour, eMin), type);
				appBook.add(appointment);
				} catch (Exception e){
					System.out.println("Error in file for one or more record");
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		}
		String[] headers = {"Appointment", "Date", "Start Time", "End Time"};
		ArrayList<Appointment> appointments = appBook.getAllAppointments();

		//Creates table model with headers being the array of headers and making it so every cell is non editable
		DefaultTableModel tm = new DefaultTableModel(headers, 0) {
			    public boolean isCellEditable(int row, int column) {
			    	return false;
			    }
		};
				
		a = new JButton("Add");
		d = new JButton("Delete");
		s = new JButton("Search");
		cs = new JButton("Clear Search");
		if (!SearchWindow.search) {
			cs.setEnabled(false);
		} else {
			cs.setEnabled(true);
		}
		
		tb = new JTable(tm);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i = 0; i < appointments.size(); i++) {
			
			String type = appointments.get(i).getEventTitle();
			GregorianCalendar startDate = appointments.get(i).getStartDateTime();
			GregorianCalendar endDate = appointments.get(i).getEndDateTime();
			
			int hours = startDate.get(Calendar.HOUR_OF_DAY);
			int mins = startDate.get(Calendar.MINUTE);
			String hrs = String.format("%02d", hours);
			String min = String.format("%02d", mins);
			String startTime = hrs + ":" + min;
			
			hours = endDate.get(Calendar.HOUR_OF_DAY);
			mins = endDate.get(Calendar.MINUTE);
			hrs = String.format("%02d", hours);
			min = String.format("%02d", mins);
			String endTime = hrs + ":" + min;
					
			Object[] row = {type, sdf.format(startDate.getTime()), startTime, endTime};
			tm.addRow(row);
		}
		
		JPanel buttonContainer = new JPanel();
		
		buttonContainer.add(a);
		buttonContainer.add(d);
		buttonContainer.add(s);
		buttonContainer.add(cs);
		
		a.addActionListener(h);
		d.addActionListener(h);
		s.addActionListener(h);
		cs.addActionListener(h);
		
		this.add(buttonContainer, BorderLayout.NORTH);
		this.add(new JScrollPane(tb), BorderLayout.CENTER);

		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	/**
	 * Event class handler used to handle each of the button presses
	 */
	public class myEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == a){
				if (aw == null) {
					aw = new AddWindow();
				}
				aw.setVisible(true);
				MainForm2.this.dispose();
			}
					
			else if (e.getSource() == s){
				if (sw == null) {
					sw = new SearchWindow();
				}
				sw.setVisible(true);
				MainForm2.this.dispose();
			}
			else if (e.getSource() == d){
				try {
					int selectedRow = tb.getSelectedRow(); //get index of selected row
					
					String selectedType = tb.getValueAt(selectedRow, 0).toString(); //get value of each cell in selected row
					String selectedDate = tb.getValueAt(selectedRow, 1).toString();
					String selectedStart = tb.getValueAt(selectedRow, 2).toString();
					String selectedEnd = tb.getValueAt(selectedRow, 3).toString();
				
					String[] splitDate = selectedDate.split("/"); //split the date string when ever there is a /
					String[] splitStart = selectedStart.split(":"); //split the start and end time when ever there is a :
					String[] splitEnd = selectedEnd.split(":");
				
					GregorianCalendar startDate = new GregorianCalendar(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[0]), Integer.parseInt(splitStart[0]), Integer.parseInt(splitStart[1])); //create new gregorian calendar using the data from the selected row
					GregorianCalendar endDate = new GregorianCalendar(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[0]), Integer.parseInt(splitEnd[0]), Integer.parseInt(splitEnd[1]));
					toDelete = new Appointment(startDate, endDate, selectedType); //create a new appointment and assign it to the toDelete global variable so that the delete window knows which appointment is being deleted
				
					if (dw == null) {
						dw = new DeleteWindow();
					}
				
					dw.setVisible(true);
					MainForm2.this.dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), "You have not selected an appointment to delete.");
				}
			}
			else if (e.getSource() == cs){
				cs.setEnabled(false); //disable clear search button
				SearchWindow.search = false; //set global search variable to false
				MainForm2 m = new MainForm2(); //reload form
				MainForm2.this.dispose();
				
			}
		}
	}

	/**
	 * The toAppointment class is used to take in a string, which will be in the format that it is stored in the appointments file. This string
	 * is then split up and each piece is assigned to the corresponding variable that piece of data represents. These pieces are then passed into 
	 * a new appointment and the method returns this appointment.
	 * 
	 * @param data - appointment in string format
	 * @return Appointment - a new appointment
	 */
	public static Appointment toAppointment(String data) {
		String[] apps = data.split(",");  //split the string when ever there is a comma and store it into the apps array of strings
		String type = apps[0]; //assign each element of the array to the corresponding variable
		int sDay = Integer.parseInt(apps[1]);
		int sMon = Integer.parseInt(apps[2]);
		int sYear = Integer.parseInt(apps[3]);
		int sHour = Integer.parseInt(apps[4]);
		int sMin = Integer.parseInt(apps[5]);
		int eDay = Integer.parseInt(apps[6]);
		int eMon = Integer.parseInt(apps[7]);
		int eYear = Integer.parseInt(apps[8]);
		int eHour = Integer.parseInt(apps[9]);
		int eMin = Integer.parseInt(apps[10]);
		
		return new Appointment(new GregorianCalendar(sYear, sMon-1, sDay, sHour, sMin), new GregorianCalendar(eYear, eMon-1, eDay, eHour, eMin), type); //return a new appointment using the variables
		
	}
}
