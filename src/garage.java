import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class garage {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		String nbr2=null;
		nbr2="";
		double d2;
		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr2.equals(""))
            d2=0;
        else
            d2=Double.parseDouble(nbr2);
		
		System.out.println(d2+"et .-"+(String)null);
		
		PrinterJob pj=PrinterJob.getPrinterJob();
		PrintService[] ps=PrintServiceLookup.lookupPrintServices(null, null);
		System.out.println("nombre de print "+ps.length);
		for(PrintService print:ps) {
			System.out.println(print.getName());
		}
	}

}
