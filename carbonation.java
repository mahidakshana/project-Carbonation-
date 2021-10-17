package Classes;


/**
 *this is carbonation class
 * @author mahi7
 *
 */
public class carbonation {
	
	
	
	//object initailization of diffrent classes 
	constant cons=new constant();
	saturationIndx SAidx= new saturationIndx();
	Input input=new Input();
	FEM fem=new FEM();
	Diffusion_coeff diffcoeff= new Diffusion_coeff();
	profile tempprofile;  //object for genertaing temperature profile
	profile RHprofile;    //object for generating Rel. humidity profile

	
	//diffrent constants used in carbonation 
	double R;   //universal gas constant
	double Href;  //refrence henry's constant
	double density; //density of concrete
	double spHeatCapacity; //specific heat capacity of concrete
	double thermalCond;  //thermal conductivity of concrete
	
	
	
	
	double h;   //henry's constant 
	double[][] C;       //solid CO2 conc.
	double [][] Caq;    //aqueous carbondioxide conc.
	double[][] RA;      //aqueous Reserve Alkanity
	double[][] RAsolid;           //solid concentration of RA
	double[][] RAdissolution;     //reserve alkalinity obtained from dissolution 
	double[][] sink;              //sink term total amount of ractant consumed 
	double[][] sinkTotal;
	double[][] RH;           //Relative humidity matrix
	double[][] temp;           //array storing temperature of each day
	double[] relHum;         //array storing relative humidity of each day
	double[][] porosity;     //matrix for storing porosity
	double[][] diffcoeff_co2;   //matrix for storing diff coeff of CO2
	double[][] diffcoeff_water;  //matrix for storing diff coeff of water 
	double lamda; 
	int n,t;
	double numberOfNodes;
	double totalTimeStep;
	double starting_month;
	double alpha;  //temp variation coeff.
	double DAl; //diffusion coeff. of alkali
	
	
	
	
	
	


	/**
	 * method for initilization all variables  
	 */
	public void initialize() {
		 
		 starting_month=input.getstartmonth(); 
		 lamda = (input.gettimeStep()/(input.getstepSize()*input.getstepSize()));   //lamda=k/(h^2)
		 double coverdepth=input.getcoverDepth()/1000;                  //convert cover depth in meter from mm
		 numberOfNodes = (coverdepth/input.getstepSize()); 
		 double noOfYears=input.getendTime();
	     totalTimeStep = (noOfYears*365*24*3600/input.gettimeStep());    //total time steps in second
	        
	        n = (int) numberOfNodes;
	        t = (int) totalTimeStep;
	        
	        double[] avgTemp_monthly=input.getavgMonthlytemp();    //monthly average temperature array from input
	    	double[] avgRH_monthly=input.getavgMonthlyRH();        //monthly avarage relative hymidity array from input
	    	
	    	//1 for temp. profile and 0 for RH profile 
	    	
	    	tempprofile= new profile(avgTemp_monthly,1);
	        RHprofile =new profile(avgRH_monthly,0);
	    	
	       
	        
	        //assign value of constant from constant class
	    	R= cons.getgasconst();
	    	Href=cons.getHref();
	        density=cons.getDensity();
	    	spHeatCapacity=cons.getSpHeatcap();
	    	thermalCond= diffcoeff.get_thermalcond();
	    	DAl=diffcoeff.get_diffAlkali();
	    	
	        
	    	//gardient for temepertaure variation 
	        alpha= thermalCond/(density*spHeatCapacity);
	        //alpha=0.9/(2400*1000);
	       
	       
	       /*here we initilize 2-d matrix for diffrent parameters.  
	        *no of rows=no of nodes
	        *no of columns=3
	        *rows denote diffrent nodes and column denote time 
	        *here we only consider 3 time step 
	        *1 stores present time value 0 is past and 2 is dummy which is used for swapping in FEM
	        **/
	        Caq =              new double[n+1][3];
	        C =                new double[n+1][3];
	        RA =               new double[n+1][3];
	        RAsolid =          new double[n+1][3]; 
	        RAdissolution =    new double[n+1][3];
	        sink =             new double[n+1][3];
	        sinkTotal =        new double[n+1][3];
	        temp=              new double[n+1][3];
	        RH =               new double[n+1][3];
	        diffcoeff_water =  new double[n+1][3];
	        porosity =          new double[n+1][3];
	        diffcoeff_co2 =     new double[n+1][3];
	       
	        
	       
	        
	        
	         
	        for(int i=0; i<=n; i++) {
	        	 Caq[i][0]=0;
	        	 C[i][0]=0;
	        	 porosity[i][0]=input.getporosity();
	        	 RAdissolution[i][0] =    0;
	             RH[i][0]=                100;   //initailly all nodes are fully saturated so RH=100 for all node
	             //temp[i][0]=              tempprofile.getprofile(0,starting_month);
	             temp[i][0]=300;
	             diffcoeff_water[i][0] =input.get_Intialdiffcoff_water();
	             sink[i][0] =             0;
	             sinkTotal[i][0] =        0;
	             }
	        
	      
	        
	        preConditioning();
	       
	        	       
	        
	        for (int j = 0; j <=n; j++){
	        	double sa=SAidx.getsatindx(RH[j][0]);
	        	//Calculating the amount of reserve alkalinity available which can react at each node at time t=0
	        	diffcoeff_co2[j][0]=diffcoeff.get_diffCO2(porosity[j][0],sa,temp[j][0]);
	            RAsolid[j][0]=input.getreserveAlkalinity()-22*(sa)*porosity[j][0];
	            RA[j][0]=22*(sa)*porosity[j][0];
	            //System.out.println(RAsolid[j][0]);
	            }
	      System.out.println(input.getreserveAlkalinity()+"   "+input.get_Intialdiffcoff_water()+"  "+input.getporosity()+"  "+input.getfP());
		
	}
	
	
	
	
	


     /**
      * main Reactor method
      * carbonation reaction, diffusion with the help of FEM is performed here for given time 
      * at all nodes.
      * finally print carbonation depth at diffrent time interval
      */
	public void Reactor() {
		
		
		for(int i=1; i<=t; i++) {
			 
			for(int j=0; j<=n; j++) {
				
				//here we perform finite element method for calculating conc. of diffrent things due to diffusion 
				//fem_general is used for rel. humidity, co2 and fem_alkali is used for temperature and alkali 
				
				if(j==0) {
					     C[0][1]  = input.getcarbonDioxideConcentration();  //zeroth node is expose to enviroment                                                           //Assigning boundary conditions 
	                      //temp[0][1]=tempprofile.getprofile(i,starting_month);
					     temp[0][1]=temp[0][0];
	                     //RH[0][1] =RHprofile.getprofile(i,starting_month);
					     RH[0][1]=RH[0][0];
	                   
				}
				else if(j==n) {
					    C[n][1]= fem.fem_general_lastnode(j,lamda,C,diffcoeff_co2);
					    //temp[n][1]=fem.fem_alkali_last(j,lamda,temp,alpha);
					    temp[n][1]=temp[n][0];
					    RH[n][1]=  fem.fem_general_lastnode(j,lamda,RH,diffcoeff_water);
				}
				else {
					   C[j][1]= fem.fem_general(j,lamda,C,diffcoeff_co2);
					   //temp[j][1]=fem.fem_alkali_general(j,lamda,temp,alpha);
					   temp[j][1]=temp[j][0];
					   RH[j][1]=fem.fem_general(j,lamda,RH,diffcoeff_water);
				}
				
				   
				    //calculation of henry's constant 
				    double Arh = ((0.003355704697986577)-(1.0/temp[j][1]));                  //(1/Treference)-(1/T) term of arrhenius equation
	                h = Href*Math.exp(Arh); 
	               
	                //assign value of stauration index by saturation array for RH 
	                double sa=SAidx.getsatindx(RH[j][1]);
	                
	               
		             
	                
	                
	             //update concentarion of co2 and reserve alkalinity due to carbonation reaction
	                Caq[j][1] = h*R*temp[j][1]*C[j][1]*(sa)*porosity[j][0];       //Aqueous carbon dioxide concentration 
	                C[j][1] = C[j][1]-Caq[j][1]+Caq[j][0];                        //Updating amount of gaseous carbon dioxide present  
	                sink[j][1] = Math.min(Caq[j][1], RA[j][1]);                   //Amount of carbon dioxide and reserve alkalinity consumed
	                RA[j][0] = RA[j][0]-sink[j][1];                             //Updating amount of alkalis consumed
	                Caq[j][1] -= sink[j][1];
	                sinkTotal[j][1] = sinkTotal[j][0]+sink[j][1]; //Summation of sink term at each time step
	                
	                
	                //FEM for alkali(in previous step some alkali consumed in carbonation so this fem is resposnsible for diffusion between diffrent nodes
	                if(j==0) {                                                          //Assigning boundary conditions 
	                    RA[0][1] = fem.fem_alkali_zeroth(lamda,RA,DAl);   //Explicit FDM scheme for zeroth node
	                    }
				    else if(j==n) {
					    RA[n][1]= fem.fem_alkali_last(j,lamda,RA,DAl);
				        }
				   else {
					RA[j][1]= fem.fem_alkali_general(j,lamda,RA,DAl);
				      }
	               
	                double sa1=SAidx.getsatindx(RH[j][0]);
	                RAdissolution[j][1] = ((porosity[j][0]*(sa1)*22)-RA[j][1])/100.0;
	                if (RAdissolution[j][1]>RAsolid[j][0]){
	                RAdissolution[j][1] = RAsolid[j][0];
	                }
	               
	                
	                RA[j][1]=((RA[j][1]+RAdissolution[j][1]));
	                RAsolid[j][1]=RAsolid[j][0]-(RAdissolution[j][1]);
	                porosity[j][1]= ((input.porosity)   +  (input.fP-input.porosity)*((input.getreserveAlkalinity()-RAsolid[j][1])/input.getreserveAlkalinity()));
	                
	                diffcoeff_co2[j][1]= diffcoeff.get_diffCO2(porosity[j][1],sa,temp[j][1]);
	                diffcoeff_water[j][1]=diffcoeff.get_diffWater(porosity[j][1],sa);
	                if(diffcoeff_water[j][1]*lamda>0.5||diffcoeff_co2[j][1]*lamda>0.5||alpha*lamda>0.5) {System.out.println("ERROR!-------This FEM is not stable please change time step or space step");  return ;}
	                
	              
	                
	                
	                swap(j,2,1);
				
			}
			
		
			int count=0;//counter for computing carbonation depth
			for(int k=0; k<=n; k++) {
				swap(k,0,2);
				if(RAsolid[k][1]==0) count++;
				
				 //if(i==1*24*3600||i==3*24*3600||i==7*24*3600||i==14*24*3600||i==21*24*3600||i==28*24*3600||i==60*24*3600||i==90*24*3600||i==120*24*3600||i==300*24*3600){
	                 //System.out.println(k+" "+i/(24*3600)+"  "+sa);
				/*if(i==1*24*3600||i==3*24*3600||i==7*24*3600||i==14*24*3600||i==21*24*3600||i==28*24*3600||i==60*24*3600||i==90*24*3600||i==120*24*3600||i==150*24*3600||i==180*24*3600||i==210*24*3600||i==240*24*3600||i==300*24*3600||i==330*24*3600){
		                 System.out.println(k+" "+i/(24*3600)+"     "+RAsolid[k][1]);
		                } */
				/*if(i==7*24*3600||i==14*24*3600||i==28*24*3600||i==60*24*3600||i==120*24*3600) {
					System.out.println(RAsolid[k][1]);
				}*/
					 
	                 }
			
			count=count*(int)(input.getstepSize()*1000);
			if(i==1*24*3600||i==3*24*3600||i==7*24*3600||i==14*24*3600||i==21*24*3600||i==28*24*3600||i==60*24*3600||i==90*24*3600||i==120*24*3600||i==150*24*3600||i==180*24*3600||i==210*24*3600||i==240*24*3600||i==300*24*3600||i==330*24*3600){
                System.out.println("time= "+(i/(24*3600)+"  depth= "+count));
                //System.out.println(temp[3][0]+" "+temp[3][1]+" "+RH[3][0]+" "+RH[3][1]);
               } 
			
	             
			}
		
         }
		
			
	
	/**
	 * method for preconditioning .
	 * it updates relative humidity and diffusion coeff of water
	 * at all nodes for period of 15 days;
	 */
	 
	private void preConditioning(){
        
        for(int l = 1; l<1296000; l++){
        
                for (int k = 0; k<=n; k++){

                    if (k==0){
                        //RH[0][1]=RHprofile.getprofile(l,starting_month-0.5); 
                    	RH[0][1]=60;
                        //temp[0][1]=tempprofile.getprofile(l,starting_month-0.5);
                      
                    
                    }
                    
                    else if (k==n){
                         RH[k][1] = fem.fem_general_lastnode(k,lamda,RH,diffcoeff_water); 
                        //temp[k][1] = fem.fem_alkali_last(k,lamda,temp,alpha); 
                       
                    }
                    else {
                        RH[k][1] =fem.fem_general(k,lamda,RH,diffcoeff_water);   //Explicit FDM scheme     
                        //temp[k][1] = fem.fem_alkali_general(k,lamda,temp,alpha); 
                    }
                
                    
                    if(RH[k][1]>99.9){
                        RH[k][1]=99.9;
                    }
                    
                    double sa=SAidx.getsatindx(RH[k][1]);
                    diffcoeff_water[k][1]=diffcoeff.get_diffWater(porosity[k][0],sa);
                    RH[k][2]=RH[k][1];
                    diffcoeff_water[k][2]=diffcoeff_water[k][1];
                    //temp[k][2]=temp[k][1];

                   
                }
                
                for(int x=0; x<=n; x++) {
                	RH[x][0]=RH[x][2];
                	diffcoeff_water[x][0]=diffcoeff_water[x][2];
                	//temp[x][0]=temp[x][2];
                }
               
            }
        
    }
	
	/**
	 * used for swapping 
	 * @param j jth node
	 * @param pre value at present time
	 * @param past value at previous time
	 */
	  public void swap(int j, int pre, int past) {
		C[j][pre]  =               C[j][past];
        RH[j][pre] =               RH[j][past];
        temp[j][pre]=             temp[j][past];
        sinkTotal[j][pre] =        sinkTotal[j][past];
        RAsolid[j][pre]=           RAsolid[j][past];
        RA[j][pre]=                RA[j][past];
        porosity[j][pre]=          porosity[j][past];   
        diffcoeff_co2[j][pre]=     diffcoeff_co2[j][past];   
        sink[j][pre]=              sink[j][past];   
        diffcoeff_water[j][pre]=   diffcoeff_water[j][past];   
	}
	
	
}
