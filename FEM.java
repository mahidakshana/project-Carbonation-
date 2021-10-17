package Classes;

/**
 * Finite element method It contains diffrent methods of FEM for diffrent
 * component at diffrent boundary conditions
 * 
 * @author mahi7
 *
 */
public class FEM {
	/**
	 * FEM for Reserve Alkalinity and Temperature at all node except zeroth and last
	 * 
	 * @param k     kth node(0,1,2...etc.)
	 * @param lamda k/h^2
	 * @param Al    Input array(RA or temperature)
	 * @param DAlk  Diffusion coeff.
	 * @return final value after FEM
	 */
	public double fem_alkali_general(int k, double lamda, double[][] Al, double DAlk) {
		double d = Al[k][0] + lamda * DAlk * (Al[k + 1][0] - 2 * Al[k][0] + Al[k - 1][0]);
		return d;

	}

	/**
	 * FEM for Reserve Alkalinity and Temperature for Zeroth Node
	 * 
	 * @param lamda k/h^2
	 * @param Al    Input array(RA or temperature)
	 * @param DAlk  diffusion ceffiecient
	 * @return final value after FEM
	 */
	public double fem_alkali_zeroth(double lamda, double[][] Al, double DAlk) {
		double d = (2 * Al[1][0] * lamda * DAlk) + Al[0][0] - Al[0][0] * (2 * lamda * DAlk);
		return d;

	}

	/**
	 * FEM for Reserve Alkalinity and Temperature for last Node
	 * 
	 * @param k     kth node(0,1,2...etc.)
	 * @param lamda k/h^2
	 * @param Al    Input array(RA or temperature)
	 * @param DAlk  diffusion coefficient
	 * @return final value after FEM
	 */
	public double fem_alkali_last(int k, double lamda, double[][] Al, double DAlk) {
		double d = Al[k][0] + (2 * Al[k - 1][0] * lamda * DAlk) - Al[k][0] * 2 * lamda * DAlk;
		return d;

	}

	/**
	 * FEM for CO2, Relative Humidity for all nodes except last.
	 * 
	 * @param k     kth node(0,1,2...etc.)
	 * @param lamda k/h^2
	 * @param u     2-D input array( CO2 or RH array)
	 * @param diff  2-d diifusion coeff. Array
	 * @return final value after FEM
	 */
	public double fem_general(int k, double lamda, double u[][], double diff[][]) {
		// double d=u[k][0] + lamda*diff[k][0]*(u[k+1][0] - 2*u[k][0] + u[k-1][0]) +
		// 0.25*lamda* (diff[k+1][0]-diff[k-1][0]) * (u[k+1][0]-u[k-1][0]) ;
		double d = u[k][0] + lamda * u[k + 1][0] * diff[k + 1][0] - 2 * lamda * u[k][0] * diff[k][0]
				+ lamda * diff[k - 1][0] * u[k - 1][0]
				+ 0.25 * lamda * (diff[k + 1][0] - diff[k - 1][0]) * (u[k + 1][0] - u[k - 1][0]);
		return d;

	}

	/**
	 * FEM for CO2 ,Relative Humidity for Last Node
	 * 
	 * @param k     kth node(0,1,2...etc.)
	 * @param lamda k/h^2
	 * @param u     2-D input array(CO2 or RH array)
	 * @param diff  2-D diffusion coeff. array
	 * @return final value of input at last node afater FEM
	 */
	public double fem_general_lastnode(int k, double lamda, double u[][], double diff[][]) {
		double d = u[k][0] - u[k][0] * 2 * lamda * diff[k][0] + 2 * u[k - 1][0] * lamda * diff[k - 1][0];
		return d;
	}

}
