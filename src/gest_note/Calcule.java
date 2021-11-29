package gest_note;

public class Calcule {
	
	public static double  Calcule1(String nbr1,double nom1){
		double d1;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals(""))
			d1=0;
		else
			d1=Double.parseDouble(nbr1);
		
			double nbr[]= {d1};
			double nom[]= {nom1};
			
			double S=0;
			for(int j=0;j<nbr.length;j++) {
			S=S+nbr[j]*nom[j];
			}
			
			S=(double)Math.round((S)*1000)/1000;
			
			return S;
}
	
	public static double  Calcule2(String nbr1, String nbr2,
										 double nom1, double nom2){
		
		double d1,d2;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals("")) {
            d1=0;
		}else {
            d1=Double.parseDouble(nbr1);
		}
		
		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr2.equals("")) {
            d2=0;
		}else {
            d2=Double.parseDouble(nbr2);
		}
		
		double nbr[]= {d1,d2};
		double nom[]= {nom1,nom2};
		double S=0;
		for(int j=0;j<nbr.length;j++) {
			S=S+nbr[j]*nom[j];
		}
		S=(double)Math.round((S)*1000)/1000;
		return S;
	}
	
	public static double  Calcule3(String nbr1, String nbr2,String nbr3,
										double nom1, double nom2,double nom3){
		
		
		double d1,d2,d3;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals(""))
            d1=0;
        else
            d1=Double.parseDouble(nbr1);
		

		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr2.equals(""))
            d2=0;
        else
            d2=Double.parseDouble(nbr2);
		
		if(nbr3.equals("null") || nbr3.equals("Abs") || nbr3.equals(""))
            d3=0;
        else
            d3=Double.parseDouble(nbr3);
		
		
		double nbr[]= {d1,d2,d3};
		double nom[]= {nom1,nom2,nom3};
		
		double S=0;
		for(int j=0;j<nbr.length;j++) {
			S=S+nbr[j]*nom[j];
		}
		S=(double)Math.round((S)*1000)/1000;
		
		return S;
		}
	
	public static double  Calcule4(String nbr1, String nbr2,String nbr3,String nbr4,
										  double nom1, double nom2,double nom3,double nom4){
		
		double d1,d2,d3,d4;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals(""))
            d1=0;
        else
            d1=Double.parseDouble(nbr1);
		
		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr2.equals(""))
            d2=0;
        else
            d2=Double.parseDouble(nbr2);
		
		if(nbr3.equals("null") || nbr3.equals("Abs") || nbr3.equals(""))
            d3=0;
        else
            d3=Double.parseDouble(nbr3);
		
		if(nbr4.equals("null") || nbr4.equals("Abs") || nbr4.equals(""))
            d4=0;
        else
            d4=Double.parseDouble(nbr4);
		
		
		double nbr[]= {d1,d2,d3,d4};
		double nom[]= {nom1,nom2,nom3,nom4};
		double S=0;
		for(int j=0;j<nbr.length;j++) {
			S=S+nbr[j]*nom[j];
		}
		S=(double)Math.round((S)*1000)/1000;
		
		return S;
		}	
	
	public static double  Calcule5(String nbr1, String nbr2,String nbr3,String nbr4,String nbr5,
										 double nom1, double nom2,double nom3,double nom4,double nom5){
		double d1,d2,d3,d4,d5;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals(""))
            d1=0;
        else
            d1=Double.parseDouble(nbr1);
		
		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr2.equals(""))
            d2=0;
        else
            d2=Double.parseDouble(nbr2);
		
		if(nbr3.equals("null") || nbr3.equals("Abs") || nbr3.equals(""))
            d3=0;
        else
            d3=Double.parseDouble(nbr3);
		
		if(nbr4.equals("null") || nbr4.equals("Abs") || nbr4.equals(""))
            d4=0;
        else
            d4=Double.parseDouble(nbr4);
		
		if(nbr5.equals("null") || nbr5.equals("Abs")|| nbr5.equals(""))
            d5=0;
        else
            d5=Double.parseDouble(nbr5);
		
		
		
		double nbr[]= {d1,d2,d3,d4,d5};
			double nom[]= {nom1,nom2,nom3,nom4,nom5};
			
			double S=0;
			for(int j=0;j<nbr.length;j++) {
			S=S+nbr[j]*nom[j];
		  }
			S=(double)Math.round((S)*1000)/1000;
			
			return S;
		}	

}
