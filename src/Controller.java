/**
 * This is the controller which is used to create a main form object
 * @author Ryan Fan-Massey - 14047957
 */

public class Controller {

	/**
	 * The main method which runs only to generate the main frame
	 * @param args
	 */
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				MainForm2 m = new MainForm2();
			}
		});
	}

}
