package Classes;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

/**
 * It contains important data used in carbonation like:- saturation Array,
 * Temperature Array, RH Array, initial Porosity, final Porosity, Reserve
 * Alkalinity for all cement
 * 
 * @author mahi7
 *
 */
public class weather {
	double[][] SA = new double[10][101];

	public void saturation() {

		// Input for temperature

		try {

			File saturationArray = new File(
					"C:\\Users\\mahi7\\Desktop\\Carbonation\\Carbonation\\src\\saturationArray.csv"); // File from which
																										// input is
																										// called

			Scanner inputScanner = new Scanner(saturationArray); // Command to read the input from the file
			for (int i = 0; i < 10; i++) {
				String data = inputScanner.nextLine();
				String[] arraySaturation = data.split(","); // Data from file is split using ,

				for (int j = 0; j <= 100; j++) {
					SA[i][j] = Double.parseDouble(arraySaturation[j]);
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
	}

	/**
	 * generate saturation array for given cement type
	 * 
	 * @param s cement type in form of string eg. "U1(0.35)"
	 * @return saturation array
	 */

	public double[] getSA(String s) {
		if (s.equalsIgnoreCase("U1(0.35)"))
			return SA[0];
		if (s.equalsIgnoreCase("B1(0.35)"))
			return SA[1];
		if (s.equalsIgnoreCase("T1(0.35)"))
			return SA[2];
		if (s.equalsIgnoreCase("T2(0.35)"))
			return SA[3];
		if (s.equalsIgnoreCase("T3(0.35)"))
			return SA[4];
		if (s.equalsIgnoreCase("B2(0.35)"))
			return SA[5];
		if (s.equalsIgnoreCase("U1(0.45)"))
			return SA[6];
		if (s.equalsIgnoreCase("B1(0.45)"))
			return SA[7];
		if (s.equalsIgnoreCase("T1(0.45)"))
			return SA[8];
		if (s.equalsIgnoreCase("T2(0.45)"))
			return SA[9];
		// not include in real code
		if (s.equalsIgnoreCase("T3(0.45)"))
			return SA[4];

		double[] arr = { 0 };

		return arr;

	}

	/**
	 * generate monthly avaerage temperature array
	 * 
	 * @return tempearture array
	 */
	public double[] getTempArray() {
		double[] temparray = { 14.2, 16.9, 22.7, 28.6, 33.5, 34.3, 31.1, 29.8, 29.2, 25.8, 20.1, 15.6 };
		return temparray;
	}

	/**
	 * generate monthly average Relative Humidity Array
	 * 
	 * @return Relative Humidity Array
	 */
	public double[] getRHArray() {
		double[] RHarray = { 63, 55, 47, 34, 33, 46, 70, 73, 62, 52, 55, 62 };
		return RHarray;
	}

	/**
	 * getter method to get initial porosity for particular cement type
	 * 
	 * @param cem_type cement type
	 * @return initial porosity(before carbonation)
	 */

	public double get_initial_Porosity(String cem_type) {
		double porosity;
		if (cem_type.equals("U1(0.35)"))
			porosity = 0.0863;
		else if (cem_type.equals("U1(0.45)"))
			porosity = 0.142;
		else if (cem_type.equals("B1(0.35)"))
			porosity = 0.0694;
		else if (cem_type.equals("B1(0.45)"))
			porosity = 0.21;
		else if (cem_type.equals("B2(0.35)"))
			porosity = 0.0491;
		else if (cem_type.equals("T1(0.35)"))
			porosity = 0.1215;
		else if (cem_type.equals("T1(0.45)"))
			porosity = 0.2617;
		else if (cem_type.equals("T2(0.35)"))
			porosity = 0.1598;
		else if (cem_type.equals("T2(0.45)"))
			porosity = 0.3696;
		else if (cem_type.equals("T3(0.35)"))
			porosity = 0.0708;
		else if (cem_type.equals("T3(0.45)"))
			porosity = 0.1935;
		else
			porosity = 0.491;
		return porosity;
	}

	/**
	 * getter method to get final porosity for particular cement type
	 * 
	 * @param cem_type cement type
	 * @return final porosity(after carbonation)
	 */

	public double get_final_Porosity(String cem_type) {
		double fP;
		if (cem_type.equals("U1(0.35)"))
			fP = 0.0814;
		else if (cem_type.equals("U1(0.45)"))
			fP = 0.143;
		else if (cem_type.equals("B1(0.35)"))
			fP = 0.1136;
		else if (cem_type.equals("B1(0.45)"))
			fP = 0.2157;
		else if (cem_type.equals("B2(0.35)"))
			fP = 0.111;
		else if (cem_type.equals("T1(0.35)"))
			fP = 0.1433;
		else if (cem_type.equals("T1(0.45)"))
			fP = 0.2445;
		else if (cem_type.equals("T2(0.35)"))
			fP = 0.2157;
		else if (cem_type.equals("T2(0.45)"))
			fP = 0.3126;
		else if (cem_type.equals("T3(0.35)"))
			fP = 0.1395;
		else if (cem_type.equals("T3(0.45)"))
			fP = 0.3663;
		else
			fP = 0.111;
		return fP;
	}

	/**
	 * getter method to get Reserve Alkalinity for given cement type
	 * 
	 * @param cem_type cement type
	 * @return reserve Alkalinity
	 */

	public double get_reservealkalinity(String cem_type) {
		double reserveAlkalinity;
		if (cem_type.equals("U1(0.35)"))
			reserveAlkalinity = 3270;
		else if (cem_type.equals("U1(0.45)"))
			reserveAlkalinity = 2492;
		else if (cem_type.equals("B1(0.35)"))
			reserveAlkalinity = 2235;
		else if (cem_type.equals("B1(0.45)"))
			reserveAlkalinity = 1654;
		else if (cem_type.equals("B2(0.35)"))
			reserveAlkalinity = 1977;
		else if (cem_type.equals("T1(0.35)"))
			reserveAlkalinity = 1841;
		else if (cem_type.equals("T1(0.45)"))
			reserveAlkalinity = 1281;
		else if (cem_type.equals("T2(0.35)"))
			reserveAlkalinity = 1584;
		else if (cem_type.equals("T2(0.45)"))
			reserveAlkalinity = 1260;
		else if (cem_type.equals("T3(0.35)"))
			reserveAlkalinity = 1791;
		else if (cem_type.equals("T3(0.45)"))
			reserveAlkalinity = 1388;
		else
			reserveAlkalinity = 1842;

		return reserveAlkalinity;
	}

}
