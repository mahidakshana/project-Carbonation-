package Classes;


/**
 * Diffusion Coefficient class
 * It contains all getter methods to
 * get diffusion coefficient of water, CO2 and Alkali
 * @author mahi7
 *
 */
public class Diffusion_coeff {
	Input input= new Input();
	
/**
 * Diffusion coefficient of Alkali is constant 
 * @return diffusion coefficient of Alkali
 */
	public double get_diffAlkali()
	{
		double a=Math.pow(10,-12);
		return a;
	}
	/**
	 * based on the value of stauration index and porosity
	 * it calculates and return diffusion coefficient of water
	 * @param p porosity
	 * @param s saturation index
	 * @return diffusion coefficient of water
	 */
	public double get_diffWater(double p, double s)
	{
		double k= Math.pow((p*s), 1.73);
		//double a=0.0000000282;
		double a=input.get_Intialdiffcoff_water();
		return(k*a);
	}
	/**
	 * based on value of porosity , stauration index and tempearture
	 * it calculates diffusion coefficient of CO2 and return it.
	 * @param p porosity
	 * @param s saturation index
	 * @param t temperature(in kelvin)
	 * @return diffusion coeffiecient of carbon-di-oxide
	 */
	public double get_diffCO2(double p, double s,double t)
	{
		
		
		double k= Math.pow((p*(1-s)), 1.73);
		double x=(39000/8.3145)*((1/(double)298)-(1/t));
		double g=(0.0000164*Math.pow((p*(1-s)),1.73)*Math.exp(x)*0.5);
		double a=5.74*Math.pow(10,-7)*Math.exp(x);
		//return(k*a);
		return g;
	}
	
	/**
	 * for Temperature FEM we've to calculate value of Alpha
	 * and for Alpha we need value of thermal conductivity of concerte
	 * @return thermal conductivity of concerte
	 */
	public double get_thermalcond() {
		
		return 0.9;
	}
	
	
	
	

}
