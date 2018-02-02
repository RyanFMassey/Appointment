import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * This is the class that creates the DeleteWindow object which generates the delete window that the
 * user sees and can then chose to delete the selected appointment
 * 
 * @author Ryan Fan-Massey
 */
public class DeleteWindow extends JFrame{
	//Declare variables and swing objects
	JLabel text = null;
	JLabel appointment = null;
	JButton delete = null;
	JButton cancel = null;
	JPanel buttonContainer = null;
	String deleteData = null;
	
	
	/**
	 * Default and only constructor for the DeleteWindow object, which is used to create the window that
	 * allows the user to confirm that they want to delete the selected record.
	 */
	public DeleteWindow(){
		super("Delete");
		
		deleteData = MainForm2.toDelete.toString();
		String[] sd = deleteData.split(",");  //split data
		String deleteApp = sd[0] + " on " + sd[1] + "/" + sd[2] + "/" + sd[3] + " at " + sd[4] + ":" + sd[5];
	
		delete = new JButton("Yes");
		cancel = new JButton("No");
		buttonContainer = new JPanel();
		buttonContainer.add(delete);
		buttonContainer.add(cancel);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(2, 2, 2, 2);
		
		text = new JLabel("Are you sure you want to delete this appointment?");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.getContentPane().add(text, c);
		
		appointment = new JLabel(deleteApp);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.getContentPane().add(appointment, c);
		
		//buttonContainer
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		this.getContentPane().add(buttonContainer, c);

		myEventHandler h = new myEventHandler();
		delete.addActionListener(h);
		cancel.addActionListener(h);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(375, 130);
		
	}
	
	
	/**
	 * Event class handler used to handle each of the button presses
	 */
	public class myEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete){
				try {
					File temp = new File("tempAppointments.csv");
					File original = new File("appointments.csv");
					
					FileWriter fw = new FileWriter(temp);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					
					FileReader fr = new FileReader(original);
					BufferedReader br = new BufferedReader(fr);
				
					String line = null;
					while ((line = br.readLine()) != null) {
						if (!line.equals(deleteData)) { //if the current line does not equal the appointment being deleted
							pw.println(line); //write the line to the file
						}
					}
					
					pw.close(); //close writers and readers
					bw.close();
					fw.close();
					br.close();
					fr.close();
					
					original.delete(); //delete old file
					temp.renameTo(original); //rename new file to original
					
					MainForm2 m = new MainForm2();
					dispose();
				} catch (FileNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
			}
			else if (e.getSource() == cancel){
				MainForm2 m = new MainForm2();
				dispose();
			}
		}
	}
	
	
	
	
}
