package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * it conatains all input parameter and their getter methods
 * 
 * @author mahi7
 *
 */
public class Input {
	weather w = new weather();

	double carbonDioxideConcentration; // Input Variables
	String cem_type;
	double[] temparray;
	double[] RHarray;
	double porosity; // initial porosity of concrete
	double timeStep;
	double stepSize;
	double endTime; // in years
	double startmonth; // 1 for jan and 12 for dec;
	double reserveAlkalinity;
	double fP; // Final porosity of concrete
	double coverDepth;
	double initial_diffcoeff_water;

	/**
	 * in this constructor ,all input parameters are initialized by input
	 * file(trial.txt)
	 * 
	 */
	public Input() {
		try {

			File inputFile = new File(
					"C:\\\\Users\\\\mahi7\\\\Desktop\\\\Carbonation\\\\Carbonation\\\\src\\\\trial.txt"); // File from
																											// which
																											// input is
																											// called

			Scanner inputScanner = new Scanner(inputFile); // Command to read the input from the file

			String nextLine = inputScanner.nextLine();
			String[] inputComponents = nextLine.split(","); // Data from file is split using ,

			String strFirst = inputComponents[0]; // cement type
			String strSecond = inputComponents[1]; // Carbon dioxide concentration 1% =0.44
			String strThird = inputComponents[2]; // Initial drying diffusion coefficient
			String strFourth = inputComponents[3]; // total time (in years)
			String strFifth = inputComponents[4]; // starting month
			String strSixth = inputComponents[5]; // cover depth
			String strSeventh = inputComponents[6]; // Time Step(in second)
			String strEighth = inputComponents[7]; // Step Size(in metre)
			inputScanner.close();

			cem_type = strFirst;
			carbonDioxideConcentration = Double.parseDouble(strSecond); // Assigning values
			initial_diffcoeff_water = Double.parseDouble(strThird);
			endTime = Double.parseDouble(strFourth);
			startmonth = Double.parseDouble(strFifth);
			coverDepth = Double.parseDouble(strSixth);
			timeStep = Double.parseDouble(strSeventh);
			stepSize = Double.parseDouble(strEighth);

		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}

	}

	/**
	 * getter method to get CO2 concentration
	 * 
	 * @return CO2 conc.
	 */
	public double getcarbonDioxideConcentration() {
		return carbonDioxideConcentration;
	}

	/**
	 * getter method for cover depth
	 * 
	 * @return cover depth(in mm)
	 */

	public double getcoverDepth() {
		return coverDepth;
	}

	/**
	 * getter method for initail diffusion coeff. of water
	 * 
	 * @return initial diff. coeff. of water
	 */
	public double get_Intialdiffcoff_water() {
		return initial_diffcoeff_water;
	}

	/**
	 * getter method for monthly temperature array
	 * 
	 * @return monthly temperature array
	 */
	public double[] getavgMonthlytemp() {
		temparray = w.getTempArray();
		for (int j = 0; j < 12; j++) {
			temparray[j] += 273;
		}
		return temparray;
	}

	/**
	 * getter method for avg. monthly Relative Humidity Array
	 * 
	 * @return monhly avg. RH array
	 */

	public double[] getavgMonthlyRH() {
		RHarray = w.getRHArray();
		return RHarray;
	}

	/**
	 * getter method for start month of carbonation
	 * 
	 * @return start month
	 */
	public double getstartmonth() {
		return startmonth;
	}

	/**
	 * getter method for initial porosity
	 * 
	 * @return initial porosity
	 */
	public double getporosity() {
		porosity = w.get_initial_Porosity(cem_type);
		return porosity;
	}

	/**
	 * getter method for time step
	 * 
	 * @return time step
	 */
	public double gettimeStep() {
		return timeStep;
	}

	/**
	 * getter method for step size
	 * 
	 * @return step size
	 */
	public double getstepSize() {
		return stepSize;
	}

	/**
	 * getter method for end time of carbonation
	 * 
	 * @return endTime
	 */
	public double getendTime() {
		return endTime;
	}

	/**
	 * getter method for reserve Alkalinity
	 * 
	 * @return reserve Alkalinity
	 */
	public double getreserveAlkalinity() {
		reserveAlkalinity = w.get_reservealkalinity(cem_type);
		return reserveAlkalinity;
	}

	/**
	 * getter method for final porsity(after carbonation)
	 * 
	 * @return final porosity
	 */
	public double getfP() {
		fP = w.get_final_Porosity(cem_type);
		return fP;
	}

	/**
	 * getter method for cement type
	 * 
	 * @return cement type
	 */
	public String getcementtype() {
		return cem_type;

	}

}
