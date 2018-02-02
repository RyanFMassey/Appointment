import java.util.ArrayList;

/**
 * The AppointmentBook class is used to create the AppointmentBook object which can then create and store an
 * array list of appointments. This class can also show all appointments in the appointment book object to the system.
 * 
 * 
 * @author MMU
 *
 */
public class AppointmentBook {
	//NOTFOUND int constant
	public static final int NOTFOUND = -1;
	
	private ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
	
	/**
	 * Adds the selected appointment to the appointment array list
	 * @param a - appointment being added to appointment book
	 */
	public void add(Appointment a){
		appointmentList.add(a);
	}
	
	
	/**
	 * This method will return an array list containing all of the appointments
	 * @return all - array list of appointments
	 */
	public ArrayList<Appointment> getAllAppointments(){
		ArrayList<Appointment> all = new ArrayList<Appointment>(appointmentList);
		return all;
	}
	
	
	/**
	 * This method prints out all of the appointments in the appointment array list to the console
	 */
	public void ShowAllAppointments(){
		ArrayList<Appointment> all = new ArrayList<Appointment>(appointmentList);
		System.out.println();
		System.out.println("All appointments");
		
		for (Appointment a: all) {
			System.out.println(a);
			System.out.println();
		}
	}

	
	/**
	 * This method is used to see whether an appointment is already inside the appointment array list
	 * @param toFind - the appointment the user wants to find
	 * @return NOTFOUND which is set to -1 if the none of the appointments in the list match, else the index of the matching appointment is returned
	 */
	private int find(Appointment toFind) {
		int i = 0;
		for (Appointment a: appointmentList) {
			if (a.equals(toFind))
				return i;
			i++;
		}
		return NOTFOUND;
	}
	
	/**
	 * This method is used to remove the passed in appointment from the appointment array list
	 * @param toRemove - the appointment being removed
	 */
	public void remove(Appointment toRemove) {
		int location = find(toRemove);
		if (location != NOTFOUND) {
			appointmentList.remove(location);
		} else
			throw new IllegalArgumentException("Appointment not found");
	}
	
	public boolean isInBook(Appointment a){
		return find(a) != NOTFOUND;
	}
}
