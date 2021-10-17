package Classes;

/**
 * Constant class It contains all getter method to get constants used in
 * carbonation process.
 * 
 * @author mahi7
 *
 */
public class constant {

	double R = 0.000082; // universal gas constant
	double k = 8.3;; // rate constnat of nuetralization recation
	double DAl; // diff. coeff. of Alkali
	double Href = 34.2; // henry's constant at 25 degree celcius;
	double density = 2400; // kg/m^3
	double spHeatCapacity = 1000; // J/Kg K
	double D_water = 0.0000000282;

	/**
	 * getter for universal gas constant
	 * 
	 * @return universal gas constant
	 */
	public double getgasconst() {
		return R;
	}

	/**
	 * getter for rate constant
	 * 
	 * @return rate constant of neutrilization reaction
	 */
	public double getrateconst() {
		return k;
	}

	/**
	 * getter for henry's constant at ref. temperature
	 * 
	 * @return henry's constant at room temperture
	 */
	public double getHref() {
		return Href;
	}

	/**
	 * getter for Diffuion coeffficient of Alkali
	 * 
	 * @return Diffusion coefficient of Alkali
	 */
	public double getDAl() {
		return DAl;
	}

	/**
	 * getter for diffusion coefficient of water
	 * 
	 * @return Diffusion Coefficient of Water
	 */
	public double getD_water() {
		return D_water;
	}

	/**
	 * getter for density of concerte
	 * 
	 * @return Density Of concrete
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * getter for specific heat capacity of concrete
	 * 
	 * @return specific heat capacity of concrete
	 */

	public double getSpHeatcap() {
		return spHeatCapacity;
	}

}
