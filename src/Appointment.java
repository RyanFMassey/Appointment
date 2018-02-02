import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This is class used to creating the Appointment object, getting and setting the values and also
 * overriding the toString method so that writing an appointment to a file is made easier
 * 
 *@author MMU, Ryan Fan-Massey
 *@version 1.0
 */
public class Appointment {
	private GregorianCalendar startDateTime;
	private GregorianCalendar endDateTime;
	private String eventTitle;
	
	
	/**
	 * Default constructor used when no arguments are passed
	 * sets all values to null and empty
	 */
	public Appointment(){
		this.startDateTime = null;
		this.endDateTime = null;
		this.eventTitle = "";
	}
	
	/**
	 * The main constructor
	 * 
	 * @param startDate gregorian calendar variable used to identify the start of the appointment
	 * @param endDate gregorian calendar variable used to identify the start of the appointment
	 * @param eventTitle the title/type of the appointment
	 */
	public Appointment(GregorianCalendar startDate, GregorianCalendar endDate, String eventTitle){
		this.startDateTime = startDate;
		this.endDateTime = endDate;
		this.eventTitle = eventTitle;
	}
	
	/**
	 * @return startDateTime - the date and time of the start of the appointment
	 */
	public GregorianCalendar getStartDateTime() {
		return startDateTime;
	}
	
	/**
	 * @param startDateTime - the date and time of the start of the appointment
	 */
	public void setStartDateTime(GregorianCalendar startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	/**
	 * @return endDateTime - the date and time of the end of the appointment
	 */
	public GregorianCalendar getEndDateTime() {
		return endDateTime;
	}
	
	/**
	 * @param endDateTime - the date and time of the end of the appointment
	 */
	public void setEndDateTime(GregorianCalendar endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	/**
	 * @return eventTitle - the name/type of the appointment
	 */
	public String getEventTitle() {
		return eventTitle;
	}
	
	/**
	 * @param eventTitle - the name/type of the appointment
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	
	
	/**
	 * Overriding the toString method so that it prints in the same format as the data
	 * being stored in my file.
	 * 
	 */
	public String toString() {
		int sDay = getStartDateTime().get(Calendar.DAY_OF_MONTH);
		int sMon = getStartDateTime().get(Calendar.MONTH);
		int sYear = getStartDateTime().get(Calendar.YEAR);
		int sHour = getStartDateTime().get(Calendar.HOUR_OF_DAY);
		int sMin = getStartDateTime().get(Calendar.MINUTE);
		int eDay = getEndDateTime().get(Calendar.DAY_OF_MONTH);
		int eMon = getEndDateTime().get(Calendar.MONTH);
		int eYear = getEndDateTime().get(Calendar.YEAR);
		int eHour = getEndDateTime().get(Calendar.HOUR_OF_DAY);
		int eMin = getEndDateTime().get(Calendar.MINUTE);		
	
		String s = getEventTitle() + "," + sDay + "," + sMon + "," + sYear + "," + sHour + "," + sMin + "," + eDay + "," + eMon + "," + eYear + "," + eHour + "," + eMin;
		return s;
	}
	
}
