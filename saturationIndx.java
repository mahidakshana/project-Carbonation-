package Classes;

/**
 * saturation index class:- It contains one saturation array and method to get
 * saturation index at particular value of Relative Humidity
 * 
 * @author mahi7
 *
 */
public class saturationIndx {
	weather w = new weather();
	Input in = new Input();
	double[] satArray;

	/**
	 * based on given cement type it construct a saturation array with the help of
	 * weather class
	 */
	public saturationIndx() {
		satArray = new double[101];
		w.saturation();
		satArray = w.getSA(in.getcementtype());

	}

	/**
	 * use linear interpolation to give saturation index for any value of Relative
	 * Humidity
	 * 
	 * @param RH Relative Humidity
	 * @return saturation index
	 */
	public double getsatindx(double RH) {
		int idx = (int) RH;
		double sa = satArray[idx] + (satArray[idx + 1] - satArray[idx]) * (RH - idx);
		return sa;

	}

}