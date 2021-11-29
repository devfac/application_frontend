package loading;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import data_base.DataBase;
import login.LoginPrincipale;

public class JBackupController {

    public JBackupController() {
     }
    
public static void executeCommand(String databaseName, String databasePassword, String type) {
	
	
	String line1 = null;
	 try {
		 line1=Files.readAllLines(Paths.get("C:\\Program Files (x86)\\JDev\\DataSciences\\package\\adress.txt")).get(2);
		 System.out.println(line1);
	} catch (Exception e) {
		JOptionPane.showMessageDialog(Main.main,"Impossible de trouver le fichier adress" , "",
				JOptionPane.ERROR_MESSAGE);
	}
	 
	 File chemin=new File(line1+databaseName + "_" + String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
				String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9));
      if (!chemin.exists()) {             
         chemin.mkdir(); 
        }
        
//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");         
String backupFileName = databaseName + ".backup";
        
List<String> commands = getPgComands(databaseName, chemin, backupFileName, type);         
		if (!commands.isEmpty()) {             
				try {
				               ProcessBuilder pb = new ProcessBuilder(commands); 
				               pb.environment().put("postgres", "postgres");
				                
				               Process process = pb.start();
				                try (BufferedReader buf = new BufferedReader(new InputStreamReader(process.getErrorStream()))) { 
				                    String line = buf.readLine();
				                    while (line != null) { 
				                        System.err.println(line); 
				                        line = buf.readLine();  
				                   }
				                 }
				              try {
								process.waitFor();
				              } catch (InterruptedException e) {
								e.printStackTrace();
				              }
				              process.destroy();
				              switch(type) {
				              case "backup":
				            	  JOptionPane.showMessageDialog(null, "La base des données "+databaseName+" est bien sauvegardée !!!");
				            	  break;
				              case "":
				            	  JOptionPane.showMessageDialog(null, "La base des données "+databaseName+" est restauree !!!");
				            	  break;
				              }
				              System.out.println("===> Success on " + type + " process.");
				             } catch (IOException ex) {
				                 System.out.println("Exception: " + ex);
				                 JOptionPane.showMessageDialog(null, "Erreur de Sauvegarde !!!");
				             }         
				} 
		else { 
	            System.out.println("Error: Invalid params."); 
		        }
     }
    
private static List<String> getPgComands(String databaseName, File backupFilePath, String backupFileName, String type) {
	String line1 = null;
	 try {
		 line1=Files.readAllLines(Paths.get("C:\\Program Files (x86)\\JDev\\DataSciences\\package\\base.txt")).get(0);
		 System.out.println(line1);
	} catch (Exception e) {
		JOptionPane.showMessageDialog(Main.main,"Impossible de trouver le fichier base" , "",
				JOptionPane.ERROR_MESSAGE);
	}
        ArrayList<String> commands = new ArrayList<>();
		switch (type) {             
			case "backup": 
				commands.add(line1+"pg_dump.exe");
				commands.add("-i");
				commands.add("-h");
				commands.add(DataBase.port);
				commands.add("-p");
				commands.add("5432");
				commands.add("-U");
				commands.add("postgres");
				commands.add("-F");
				commands.add("c");
				commands.add("-b");
				commands.add("-v");
				commands.add("-f");
				commands.add(backupFilePath.getAbsolutePath()+  File.separator + backupFileName);
				commands.add(databaseName);          
				break;
				
			case "restore":   
				
				commands.add(line1+"pg_restore.exe");
				commands.add("-i");
				commands.add("-h");
				commands.add(DataBase.port);
				commands.add("-p");
				commands.add("5432");
				commands.add("-U");
				commands.add("postgres");
				commands.add("-d");
				commands.add(databaseName);
				commands.add("-v");
				commands.add(backupFilePath.getAbsolutePath()+  File.separator + backupFileName);               
				break; 
				
			default:                 
				return Collections.emptyList();         
		}    
		return commands;
	}
}
