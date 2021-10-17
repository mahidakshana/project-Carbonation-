package Classes;

/**
 * it contains diffrent method to generate temperature and Relative Humidity
 * profiles, based on given monthly average data. also contain getprofile method
 * which directly out value of temp. and RH at given time
 * 
 * @author mahi7
 *
 */
public class profile {

	// T(t)= A*sin(B*t-C)+D

	double A;
	double B;
	double C;
	double D;

	/**
	 * it generate profile(equation of Sine curve) for given data
	 * 
	 * @param temparr monthly avarage temperture array
	 * @param num     = 1 for temperature and 0 for Relative Humidity
	 */
	profile(double[] temparr, int num) {
		double max = -10000;
		double min = 10000;
		for (int i = 0; i < temparr.length; i++) {
			if (temparr[i] > max) {
				max = temparr[i];
			}
			if (temparr[i] < min) {
				min = temparr[i];
			}
		}

		A = (max - min) / 2;
		D = (max + min) / 2;
		if (num == 1)
			B = Math.PI / 6;
		else
			B = Math.PI / 3;

		double t = (temparr[0] - D) / A;
		if (t < -1)
			t = -1;
		if (t > 1)
			t = 1;

		double x = Math.asin(t);
		C = B - x;

	}

	/**
	 * 
	 * @param time        input time(in second) till which we want to get
	 *                    temperature or RH
	 * @param start_month starting time when carbonation start(in month)
	 * @return value of Tempearture or RH
	 */

	public double getprofile(int time, double start_month) {
		// T= A*sin(B*t-C)+D t in month 1 for jan 12 for dec

		// time is in second
		double x = (time / (30 * 24 * 3600)); // convert second into month
		double t = start_month + x; // t is in month
		if (t >= 13) {
			if (t % 12 != 0)
				t = t % 12;
			else {
				t = 12;
			}
		}

		double temp = A * Math.sin(B * t - C) + D;
		return temp;

	}

}
