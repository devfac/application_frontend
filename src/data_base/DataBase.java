package data_base;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;

import gest_note.Calcule;
import image.ImageBonneQualite;
import impression.FenetreImpressionCertificat;
import loading.Main;
import loading.Methode;
import login.LoginPrincipale;
import table.TableModel;
import table.TableRec;
import zdialog.Fenetre;

import request.request_api;
public class DataBase{
	public static String parcMaths[]= {"tcm","miss","me","mf"};
	public static String database= "";

	public static String databaseNoteMaths= "";

	public static String databaseDroit = "droit_maths";
	public static String databaseMatiereMaths= "";
	public static String databaseMatiereMathsPrinc= "matiere_maths";

	public static String databaseUtilisateur = "utilisateur_mats";
	public static String databasePersonnel = "Personnel";
	public static String databaseEnseignant = "enseignant_math";


	public static String username = "postgres";     
	public static String password = "postgres"; 
	public static String port = ""; 
	public static String url = "jdbc:postgresql://"+port+":5432/";    
	public static Connection connection = null;     
	public static Statement statement = null;  
	public static Connection connection1 = null;
	public static Connection connection2 = null;     
	public static Statement statement1 = null;       
	public static Statement statement2 = null;   
	public static ResultSet rset = null;  
	public static ResultSet rset1 = null; 
	public static ResultSet rset2 = null;  
	public boolean databaseListChanged = false;     
	public static int result = -1;
	public static String semestre []= {"S1","S2","S3","S4","S5","S6","S7","S8","S9","S10"};
	public static String [] querry;
	public static  JLabel mess=new JLabel();
	public static boolean impr=true;
	public static DefaultTableModel model1=new DefaultTableModel();



	public static void connexion(){
		url = "jdbc:postgresql://"+port+":5432/";
		try {      
			Class.forName("org.postgresql.Driver");
			mess.setText("DRIVER OK");

			try {
				DataBase.creationBDUtil("2019-2020");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Main.main, "Impossible de connecter au serveur ", "ERREUR",
						JOptionPane.ERROR_MESSAGE);  
				System.exit(0);
				e.printStackTrace();
			} 

		}catch(ClassNotFoundException e) {  
			JOptionPane.showMessageDialog(Main.main, "Impossible de trouver le pilote", "ERREUR",
					JOptionPane.ERROR_MESSAGE);  
			System.exit(0);

		} 
	}

	public static boolean test() {
		String l = null;
		boolean t=false;
		try {    
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+database+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		if((String.valueOf(l)).equals(database))
			t=true;
		return t; 
	}

	public static boolean testNoteMath() {
		String l = null;
		boolean t=false;
		try {    
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseNoteMaths+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		if((String.valueOf(l)).equals(databaseNoteMaths))
			t=true;
		return t; 
	}


	public static boolean testUtil() {
		String l = null;
		boolean t=false;
		try { 

			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseUtilisateur+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals(databaseUtilisateur))
			t=true;
		return t; 
	}



	/***************************************************************
	 * METHODE POUR TESTER SI LA DATABASE MATIERE EXIST OU PAS *
	 ***************************************************************/	

	public static boolean testMatiereMaths() {
		String l = null;
		boolean t=false;
		try { 

			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseMatiereMaths+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals(databaseMatiereMaths))
			t=true;
		return t; 
	}

	public static boolean testDroit() {
		String l = null;
		boolean t=false;
		try { 

			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseDroit+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals(databaseDroit))
			t=true;
		return t; 
	}

	public static void creation_table_Droit() {
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaseDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE table_droit" + 
					"(\r\n" + 
					"  nom character varying NOT NULL,\r\n" + 
					"  mention character varying,\r\n" +
					"  droit numeric NOT NULL,\r\n" +
					"  CONSTRAINT \"tab_droit_pkey\" PRIMARY KEY (nom)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table Droit par niveau cr�er avec succ�es ");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}


	public static  int importCalculReste(String nom) {
		mess.setText("Importation des Tables DROIT "+nom);
		String databaDroit="";  
		databaDroit=databaseDroit;
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT droit FROM table_droit WHERE nom='"+nom+"' ";
			rset=statement.executeQuery(querry);
			while(rset.next()) {
				result=rset.getInt("droit");		        	}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unused")
	public static  DefaultTableModel importDonneDroitEt(DefaultTableModel model,String mention,String niveau,
			String filier,String nom) {
		int anne=Integer.parseInt(LoginPrincipale.anneField.getText().substring(5, 9));
		model.setRowCount(0);
		String clas1="";
		String clas2="";
		switch(niveau) {
		case "L1":
			clas1="S1";
			clas2="S2";
			break;
		case "L2":
			clas1="S3";
			clas2="S4";
			break;
		case "L3":
			clas1="S5";
			clas2="S6";
			break;
		case "M1":
			clas1="S7";
			clas2="S8";
			break;
		case "M2":
			clas1="S9";
			clas2="S10";
			break;
		}

		if(niveau.equals("L1")&& nom.equals("L1_T.C.M")) {
			try {
				int a=importCalculReste("L1_T.C.M");
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ "AND table_sems.mention='"+mention+"'"
						+ " ORDER BY table_maths.nom_et ";
				rset=statement.executeQuery(querry);

				while(rset.next()) {


					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("montant"),a-rset.getInt("montant")});
				}

				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}


		}else {
			try {
				if(!nom.equals("L1_T.C.M"));
				int a=importCalculReste(nom);
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ " AND table_sems.mention='"+mention+"' "
						+ "AND table_maths.filier_et='"+filier+"'"
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("montant"),a-Integer.parseInt(rset.getString("montant"))});
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public static  DefaultTableModel importDonneDroit(DefaultTableModel model,String mention,String nom	) {
		mess.setText("Importation des Tables DROIT "+mention+" "+nom);
		String databaDroit="";

		mention= "MATHEMATIQUES ET APPLICATIONS";
		databaDroit=databaseDroit;

		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_droit ORDER BY nom";
			rset=statement.executeQuery(querry);
			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("nom"), rset.getString("mention")
						, rset.getString("droit")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "T.C.M est seulement pour le L1 ","ERREUR",0);
		}

		return model;
	}
	public static void ajout_droit(String nom,String mention, double droit) {
		String databaDroit="";

		mention= "MATHEMATIQUES ET APPLICATIONS";
		databaDroit=databaseDroit;

		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_droit ( "
					+ " nom,mention,droit) "
					+ "VALUES ('"+nom+"','"+mention+"','"+droit+"')";

			statement.executeUpdate(query);
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(null, "Droit bien ajouter","DATABASE",1);							
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "DROIT DEJA AJOUTER ","ERREUR",0);
		}
	}
	public static void recuperInfo_droit(String nom,JTextField droit) {
		String databaDroit="";
		databaDroit=databaseDroit;
		String l []= new String[2];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_droit "
					+ "WHERE nom='"+nom+"'" );
			while(rset.next()) {
				l[1]=String.valueOf(rset.getInt("droit"));
			}
			droit.setText(l[1]);
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void modifier_droit(String nom,int droit) {
		String databaDroit="";

		databaDroit=databaseDroit;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rset = statement.executeQuery(" SELECT * FROM table_droit "
					+ "WHERE nom='"+nom+"'" );
			rset.first();
			rset.updateInt("droit", droit);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();



			JOptionPane.showMessageDialog(null, "DROIT BIEN MODIFIER ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"ERREUR LORS DE LA MODIFICATION DU DROIT", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public static void delete_droit(String nom) {
		String databaDroit="";
		databaDroit=databaseDroit;

		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaDroit;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="DELETE FROM table_droit WHERE "
					+ " nom='"+nom+"'";

			statement.executeUpdate(query);
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(null, "Droit bien supprimer","DATABASE",1);							
		}catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static boolean testMatiereMathsPrinc() {
		String l = null;
		boolean t=false;
		try { 

			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseMatiereMathsPrinc+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals(databaseMatiereMathsPrinc))
			t=true;
		return t; 
	}


	public static void creationBDUtil(String anne) {

		if(!testUtil()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(""
						+ " Create DATABASE "+databaseUtilisateur);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+databaseUtilisateur+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
			creation_table_utilisateur();
			creation_table_personel();
			creation_table_anne();
			ajout_Au(anne);
			for(int i=1;i<4;i++) {
				//ajoutPers(i);
			}
		}
	}

	public static void creationBD() {

		if(!test()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" Create DATABASE "+database);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+database+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			creation_table_etuduant();
			creation_table();
			creation_table_sems();
			creation_table_valid_sems();
		}

		if(!testNoteMath()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" Create DATABASE "+databaseNoteMaths);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+databaseNoteMaths+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}

		}

		if(!testMatiereMaths()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
				statement.executeUpdate(" Create DATABASE "+databaseMatiereMaths);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+databaseMatiereMaths+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
			for(int i=0;i<parcMaths.length;i++) {
				creation_table_Ue("MATHEMATIQUES ET APPLICATIONS",i,databaseMatiereMaths);
				creation_table_Ec("MATHEMATIQUES ET APPLICATIONS",i,databaseMatiereMaths);
			}
			if(!testMatiereMathsPrinc()) {
				try {  
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					statement.executeUpdate(" Create DATABASE "+databaseMatiereMathsPrinc);
					System.out.println("base de donner creer avec succes");
					mess.setText("Base de donner "+databaseMatiereMathsPrinc+" creer avec succes");
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				} 
				for(int i=0;i<parcMaths.length;i++) {
					creation_table_Ue("MATHEMATIQUES ET APPLICATIONS",i,databaseMatiereMathsPrinc);
					creation_table_Ec("MATHEMATIQUES ET APPLICATIONS",i,databaseMatiereMathsPrinc);
				}

			}
			copyUe("MATHEMATIQUES ET APPLICATIONS");
			copyEc("MATHEMATIQUES ET APPLICATIONS");
		}
		if(!testDroit()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" Create DATABASE "+databaseDroit);
				System.out.println("base de donn�e creer avec succes");
				mess.setText("Base de donn�e "+databaseDroit+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
			creation_table_Droit();
		}

		if(!testEnseignant()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(""
						+ " Create DATABASE "+databaseEnseignant);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+databaseEnseignant+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
			creation_table_enseignant();

		}
	}

	public static void copyUe(String mention) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		for(int i=0;i<parc.length;i++) {
			try {  

				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
				statement.executeUpdate("INSERT INTO uni_enseign_"+parc[i]+" (nom_ue,credit,semestre,parcours) SELECT nom_ue,credit,semestre,parcours "
						+ "FROM dblink('host=localhost user=postgres  password=postgres dbname="+databaseMatiereMathsPrinc+"',"
						+ "'SELECT nom_ue,credit,semestre,parcours FROM uni_enseign_"+parc[i]+"') "
						+ "AS uni_enseign_"+parc[i]+"("
						+ "  nom_ue character varying," 
						+ "  credit integer ," 
						+ "  semestre character varying,"
						+ "  parcours character varying)");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
		}
	}

	public static void copyEc(String mention) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		for(int i=0;i<parc.length;i++) {
			try {  

				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
				statement.executeUpdate("INSERT INTO el_cons_"+parc[i]+" (nom_ec,nom_ue,semestre,poids,ensei) "
						+ "SELECT nom_ec,nom_ue,semestre,poids,ensei "
						+ "FROM dblink('host=localhost user=postgres  password=postgres dbname="+databaseMatiereMathsPrinc+"',"
						+ "'SELECT nom_ec,nom_ue,semestre,poids,ensei FROM el_cons_"+parc[i]+"') "
						+ "AS el_cons_"+parc[i]+"("
						+ "nom_ec character varying,"
						+ "nom_ue character varying ," 
						+ "semestre character varying ,"
						+ "poids numeric," 
						+ "ensei character varying )" );
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
		}
	}

	public static void creation_table_utilisateur() {
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE utilisateur " + 
					"(\r\n" + 
					"  type character(4),\r\n" + 
					"  nom_utilisateur character varying,\r\n" + 
					"  mot_de_passe character varying,\r\n" + 
					"  photo character varying,\r\n" +
					"  mention character varying,\r\n" +
					"  CONSTRAINT \"utilisateur_pkey\" PRIMARY KEY (mention)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table utilisateur cr�er aves succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}



	/*********************DEBUT BASE ENSEIGNANT********************/

	public DefaultTableModel importDonneInsEnseignant(DefaultTableModel model,String mention) {
		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM enseignant ORDER BY enseignant.nom_enseignant ";

			rset=statement.executeQuery(querry);

			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("num_insc"),
						rset.getString("nom_enseignant")+" "+rset.getString("prenom_enseignant")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}


	public static void ajout_enseignant(String num,String nom,String prenom,String mdp,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO enseignant ( "
					+ " num_insc,nom_enseignant,prenom_enseignant, mot_de_passe,mention ) "
					+ "VALUES ('"+num+"','"+nom+"','"+prenom+"','"+mdp+"','"+mention+"');";
			statement.executeUpdate(query);
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String recuperMotDePasseEnseignant(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			String querry="SELECT mot_de_passe FROM enseignant WHERE num_insc='"+mention+"'";

			rset=statement.executeQuery(querry);
			while(rset.next()) {
				l=rset.getString("mot_de_passe");
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;

	}

	public static void modif_mdp_enseignant(String num,String nom, String prenom,String mdp,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT * FROM enseignant WHERE num_insc='"+num+"'";
			rset = statement.executeQuery(query);
			rset.first();
			rset.updateString("nom_enseignant",nom);
			rset.updateString("prenom_enseignant",prenom);
			rset.updateString("mention",mention);
			rset.updateString("mot_de_passe",mdp);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String recuperNomEns(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection1 = DriverManager.getConnection(url, username, password);      
			statement1 = connection1.createStatement(); 
			String querry="SELECT nom_enseignant  FROM enseignant WHERE mention='"+mention+"'";

			rset1=statement1.executeQuery(querry);
			while(rset1.next()) {
				l=rset1.getString("nom_enseignant");
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static String recuperPrenomEns(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection1 = DriverManager.getConnection(url, username, password);      
			statement1 = connection1.createStatement(); 
			String querry="SELECT prenom_enseignant  FROM enseignant WHERE mention='"+mention+"'";

			rset1=statement1.executeQuery(querry);
			while(rset1.next()) {
				l=rset1.getString("prenom_enseignant");
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	public static boolean testExistance1(String mention) {
		String l = null;
		boolean t=false;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM enseignant WHERE type='true'"
					+ "AND  mention='"+mention+"'" );
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals("true")) 
			t=true;

		return t; 
	}


	public static void recuperInformationEnseignant(String num,JTextField tab[]) {
		String l []= new String[4];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM enseignant WHERE num_insc='"+num+"'" );
			while(rset.next()) {
				l[1]=String.valueOf(rset.getInt(1));
				l[2]=rset.getString(2);
				l[3]=rset.getString(3);

			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int k=0;k<tab.length;k++) {
			switch(k) {
			case 0:
				tab[k].setText(l[1]);
				break;
			case 1:
				tab[k].setText(l[2]);
				break;
			case 2:
				tab[k].setText(l[3]);
				break;
			}

		}

	}
	public static boolean testEnseignant() {
		String l = null;
		boolean t=false;
		try { 

			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT datname FROM pg_database WHERE datname='"+databaseEnseignant+"'");
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals(databaseEnseignant))
			t=true;
		return t; 
	}

	public static void creationBDEnseignant(String anne) {

		if(!testEnseignant()) {
			try {  
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(""
						+ " Create DATABASE "+databaseEnseignant);
				System.out.println("base de donner creer avec succes");
				mess.setText("Base de donner "+databaseEnseignant+" creer avec succes");
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
			creation_table_enseignant();

		}
	}


	/***CREATION  DE LA TABLE ENSEIGNANT*****/

	public static void creation_table_enseignant() {
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query=""
					+ "CREATE TABLE enseignant"+ 
					"(\r\n" + 
					"  num_insc character varying NOT NULL,\r\n" +  
					"  nom_enseignant character varying,\r\n" +
					"  prenom_enseignant character varying,\r\n" +
					"  mot_de_passe character varying,\r\n" + 
					"  mention character varying,\r\n" +
					"  CONSTRAINT \"enseignant_pkey\" PRIMARY KEY (num_insc)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table enseignant cr�er aves succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public static void supprimerEns( String num ) {

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseEnseignant;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate(" DELETE  FROM enseignant WHERE  num_insc='"+num+"'");
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(null,"Etudiant bien Supprimer  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/*********************FIN BASE ENSEIGNANT********************/

	/***********************************************************
	 * 			CREATION DE LA TABLE D'UTILISAATEUR			  *
	 ***********************************************************/ 

	public static void creation_table_personel(){
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE personnel " + 
					"(\r\n" +
					"  nom_personel character varying,\r\n" + 
					"  prenom_personel character varying,\r\n" +
					"  poste character varying,\r\n" +
					"  photo character varying,\r\n" +
					"  mention character varying,\r\n" +
					"  mot_de_passe character varying,\r\n" +
					"  CONSTRAINT \"personnel_pkey\" PRIMARY KEY (poste)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table personnel cr�er avec succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/***********************************************************
	 * 			CREATION DE LA TABLE ANNEE UNIVERSITAIRE			  *
	 ***********************************************************/ 

	public static void creation_table_anne() {
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE anne_univ " + 
					"(\r\n" + 
					"  anne character varying,\r\n" + 
					"  carte numeric,\r\n" + 
					"  CONSTRAINT \"anne_univ_pkey\" PRIMARY KEY (anne)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table ann�e universitaire cr�er avec succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void creation_table_etuduant() {
		try {	
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query=""
					+ "CREATE TABLE tab_etudiant"+ 
					"(\r\n" + 
					"  num_insc numeric NOT NULL,\r\n" + 

	        			"  nom_et character varying,\r\n" + 
	        			"  prenom_et character varying,\r\n" + 

	        			"  date_naiss character varying,\r\n" + 
	        			"  lieu_naiss character varying,\r\n"+

						"  nationalite character varying,\r\n"+

						"  sexe character varying,\r\n"+
						"  situation character varying,\r\n"+
						"  num_tel character varying,\r\n"+

						"  num_cin character varying,\r\n" + 
						"  date_cin character varying,\r\n" + 
						"  lieu_cin character varying,\r\n" +

						"  serie_bacc character varying,\r\n" + 
						"  anne_bacc character varying,\r\n" + 
						"  num_bacc character varying,\r\n" + 
						"  centre_bacc character varying,\r\n" +

	        			"  adre_et character varying,\r\n" + 
	        			"  prof_et character varying,\r\n"+

	        			"  nom_pere character varying,\r\n" + 
	        			"  prof_pere character varying,\r\n" +
	        			"  nom_mere character varying,\r\n" + 
	        			"  prof_mere character varying,\r\n" + 

						"  adres_pare character varying,\r\n" + 
						"  photo character varying,\r\n" +

						"  montant character varying,\r\n" + 
						"  num_quit character varying,\r\n" + 
						"  dat_quit character varying,\r\n" + 

						"  niveau character varying,\r\n" + 
						"  parcours character varying,\r\n" + 

						"  mention character varying,\r\n" + 

	        			"  CONSTRAINT \"tab_Etudiant_pkey\" PRIMARY KEY (num_insc)\r\n" + 
	        			")";

			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table nouveau etudiant cr�er aves succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void creation_table() {

		String querry= new String();
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			querry="CREATE TABLE table_maths" + 
					"(\r\n" + 
					"  num_carte character varying NOT NULL,\r\n" + 

			       "  nom_et character varying,\r\n" + 
			       "  prenom_et character varying,\r\n" + 

			       "  date_naiss character varying,\r\n" + 
			       "  lieu_naiss character varying,\r\n"+

			       "  adre_et character varying,\r\n" + 
			       "  filier_et character varying,\r\n"+

				   "  sexe_et character varying,\r\n" + 
				   "  nation_et character varying,\r\n"+

			       "  num_cin character varying,\r\n" + 
			       "  date_cin character varying,\r\n" + 
			       "  lieu_cin character varying,\r\n" +

			       "  montant numeric,\r\n" + 
			       "  num_quit character varying,\r\n" +
			       "  dat_quit character varying,\r\n" + 

			       "  etat character varying,\r\n" +
			       "  photo character varying,\r\n" +
			       "  bacc numeric,\r\n" +

			       "  CONSTRAINT \"table_pc_pkey\" PRIMARY KEY (num_carte)\r\n" + 
			       ")";
			statement.executeUpdate(querry);
			statement.close();
			connection.close();
			mess.setText("Table etudiant cr�er avec succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void creation_table_sems() {
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE table_sems" + 
					"(\r\n" + 
					"  num_carte character varying,\r\n" + 
					"  choix_1 character varying,\r\n" + 
					"  choix_2 character varying,\r\n" + 
					"  mention character varying,\r\n" +
					"  diplome character varying,\r\n" +
					"  CONSTRAINT \"tab_sems_pkey\" PRIMARY KEY (num_carte)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table Semsetre etudiant_sems cr�er aves succ�es ");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public static void creation_table_valid_sems() {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String query="CREATE TABLE table_valid_sems" + 
					"(\r\n" + 
					"  num_carte character varying,\r\n" + 
					"  sems_1 character varying,\r\n" + 
					"  sems_2 character varying,\r\n" + 
					"  sems_3 character varying,\r\n" + 
					"  sems_4 character varying,\r\n" + 
					"  sems_5 character varying,\r\n" + 
					"  sems_6 character varying,\r\n" + 
					"  sems_7 character varying,\r\n" + 
					"  sems_8 character varying,\r\n" + 
					"  sems_9 character varying,\r\n" + 
					"  sems_10 character varying,\r\n"+ 
					"  CONSTRAINT \"tab_valid_sems_pkey\" PRIMARY KEY (num_carte)\r\n" + 
					")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			mess.setText("Table Semsetre etudiant_sems cr�er aves succ�es ");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static  DefaultTableModel importDonne(DefaultTableModel model,String mention,String sems) {
		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
					+ "table_maths.num_carte=table_sems.num_carte WHERE "
					+ "(table_sems.choix_1='"+sems+"' OR table_sems.choix_2='"+sems+"')"
					+ " AND table_sems.mention='"+mention+"'"
					+ " ORDER BY table_maths.num_carte ";

			rset=statement.executeQuery(querry);

			while(rset.next()) {
				model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
						rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("filier_et")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	public static  DefaultTableModel importDonneTable(DefaultTableModel model,String mention,String sems,
			String filier) {
		mess.setText("Importation des liste des �tudiants "+mention+" "+sems+" "+filier);
		model.setRowCount(0);
		if(sems.equals("S1")) {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "table_sems.choix_1='S1' AND table_sems.mention='"+mention+"'"
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et")});
				}

				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}


		}else {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_1='"+sems+"' OR table_sems.choix_2='"+sems+"')"
						+ " AND table_sems.mention='"+mention+"' AND table_maths.filier_et='"+filier+"'"
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et")});
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public static  DefaultTableModel importDonneBourse(DefaultTableModel model,String mention,String niveau,
			String filier) {
		int anne=Integer.parseInt(LoginPrincipale.anneField.getText().substring(5, 9));
		mess.setText(" liste des �tudiants boursier en "+mention+" "+niveau+" "+filier+" "+anne);
		model.setRowCount(0);
		String clas1="";
		String clas2="";
		switch(niveau) {
		case "L1":
			clas1="S1";
			clas2="S2";
			break;
		case "L2":
			clas1="S3";
			clas2="S4";
			break;
		case "L3":
			clas1="S5";
			clas2="S6";
			break;
		case "M1":
			clas1="S7";
			clas2="S8";
			break;
		case "M2":
			clas1="S9";
			clas2="S10";
			break;
		}
		if(niveau.equals("L1")) {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ "AND table_sems.mention='"+mention+"'"
						+ "AND table_maths.etat='Passant'"
						+ "AND (table_maths.bacc=0 OR table_maths.bacc="+(anne-1)+")"
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et")});
				}

				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}


		}else {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ " AND table_sems.mention='"+mention+"' "
						+ "AND table_maths.filier_et='"+filier+"'"
						+ "AND table_maths.etat='Passant'"
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et")});
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public static  DefaultTableModel importDonneBourseR(DefaultTableModel model,String mention,String niveau,
			String filier) {
		int anne=Integer.parseInt(LoginPrincipale.anneField.getText().substring(5, 9));
		mess.setText(" liste des �tudiants boursier en "+mention+" "+niveau+" "+filier+" "+anne);
		model.setRowCount(0);
		String clas1="";
		String clas2="";
		switch(niveau) {
		case "L1":
			clas1="S1";
			clas2="S2";
			break;
		case "L2":
			clas1="S3";
			clas2="S4";
			break;
		case "L3":
			clas1="S5";
			clas2="S6";
			break;
		case "M1":
			clas1="S7";
			clas2="S8";
			break;
		case "M2":
			clas1="S9";
			clas2="S10";
			break;
		}
		if(niveau.equals("L1")) {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ " AND table_sems.mention='"+mention+"' "
						+ "AND table_maths.etat='Redoublant'"
						+ "AND table_maths.bacc >= 08.50 "
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("bacc")});
				}

				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {	
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_2='"+clas1+"' OR table_sems.choix_2='"+clas2+"')"
						+ " AND table_sems.mention='"+mention+"' "
						+ "AND table_maths.filier_et='"+filier+"'"
						+ "AND table_maths.etat='Redoublant'"
						+ "AND table_maths.bacc >= 08.50 "
						+ " ORDER BY table_maths.nom_et ";

				rset=statement.executeQuery(querry);

				while(rset.next()) {
					model.addRow(new Object[] {model.getRowCount()+1,rset.getString("num_carte"),
							rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("bacc")});
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}


	public static void ajout_etudiant_math(JFrame frame,String num,String nom,String prenom,String dateNaiss,
			String lieuNaiss,String adresse,String numCin,String dateCin,String lieuCin,String montant,
			String numQuit,String dateQuit,String etat, String filiere, String img,String sexe,String nation, double re) {

		String str="'C:\\Photo_Sciences\\"+database+"\\"+img+".jpg'";


		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_maths ( "
					+ "num_carte, nom_et, prenom_et, date_naiss, lieu_naiss, adre_et, sexe_et,nation_et,"
					+ "filier_et, num_cin ,date_cin,lieu_cin ,montant ,"
					+ "num_quit ,dat_quit ,etat,photo,bacc ) "
					+ "VALUES ('"+num+"','"+nom.replaceAll("'", "''")+"','"+prenom.replaceAll("'", "''")+"','"+dateNaiss+"','"+lieuNaiss+"',"
					+ "'"+adresse+"','"+sexe+"','"+nation+"','"+filiere+"','"+numCin+"','"+dateCin+"','"+lieuCin+"',"
					+ "'"+montant+"','"+numQuit+"','"+dateQuit+"','"+etat+"', "
					+ str+","+re+")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(frame, "�tudiant bien ajout� ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,"Numero de carte dej� inscrit ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void ajout_etudiant_math(String num,String nom,String prenom,String dateNaiss,
			String lieuNaiss,String adresse,String numCin,String dateCin,String lieuCin,String montant,
			String numQuit,String dateQuit,String etat, String filiere, String img,String sexe,String nation, double re) {

		String str="'C:\\Photo_Sciences\\"+database+"\\"+img+".jpg'";


		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_maths ( "
					+ "num_carte, nom_et, prenom_et, date_naiss, lieu_naiss, adre_et, sexe_et,nation_et,"
					+ "filier_et, num_cin ,date_cin,lieu_cin ,montant ,"
					+ "num_quit ,dat_quit ,etat,photo,bacc ) "
					+ "VALUES ('"+num+"','"+nom.replaceAll("'", "''")+"','"+prenom.replaceAll("'", "''")+"','"+dateNaiss+"','"+lieuNaiss+"',"
					+ "'"+adresse+"','"+sexe+"','"+nation+"','"+filiere+"','"+numCin+"','"+dateCin+"','"+lieuCin+"',"
					+ "'"+montant+"','"+numQuit+"','"+dateQuit+"','"+etat+"', "
					+ str+","+re+")";
			statement.executeUpdate(query);
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean verificNum(String i) {
		String k="";
		boolean rep=false;
		String l[];

		try {   
			l=new String[4];
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_sems WHERE "
					+ "num_carte='"+i+"'");
			while(rset.next()) {
				k=rset.getString(1);
				l[0]=rset.getString(2);
				l[1]=rset.getString(3);
				l[2]=rset.getString(4);

			}
			rset.close();
			statement.close();
			connection.close();
			if(k=="")
				rep=true;
			else
				JOptionPane.showMessageDialog(Main.main, "Numero de Carte d�j� inscrit en"+l[0]+" et"
						+ " "+l[1]+" en "+l[2], "ERREUR",
						JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rep;

	}

	public static boolean verificNumImport(String i) {
		String k="";
		boolean rep=false;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths WHERE "
					+ "num_carte='"+i+"'");
			while(rset.next()) {
				k=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
			if(k=="")
				rep=true;
			else
				rep=false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rep;

	}


	public static boolean verificNumNouv(String i){
		String k="";
		boolean rep=false; 
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM tab_etudiant WHERE num_insc='"+i+"'");
			while(rset.next()) {
				k=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
			if(k!="")
				rep=true;
			else
				JOptionPane.showMessageDialog(Main.main, "invalide numero d'inscription", "ERREUR",
						JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

	public static boolean verificNumIns (String i) {
		String k="";
		boolean rep=false;
		String l[];

		try {   
			l=new String[4];
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM tab_etudiant WHERE "
					+ "num_insc='"+i+"'");
			while(rset.next()) {
				k=rset.getString("num_insc");
				l[0]=rset.getString("mention");
			}
			rset.close();
			statement.close();
			connection.close();
			if(k=="")
				rep=true;
			else
				JOptionPane.showMessageDialog(Main.main, "Numero d'inscription  d�ja inscrit en "+l[0]+"", "ERREUR",
						JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rep;

	}


	public static void ajout_etudiant_math_parcours(String num ,String sems1,String sems2,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_sems ( "
					+ "num_carte,choix_1,choix_2,mention,diplome) "
					+ "VALUES ('"+num+"','"+sems1+"','"+sems2+"','"+mention+"','false');";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void modif_etudiant_math_parcours(String num ,String sems1,String sems2) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT num_carte,choix_1,choix_2 FROM table_sems WHERE num_carte='"+num+"'";
			rset=statement.executeQuery(query);
			rset.first();
			rset.updateString("num_carte", num);
			rset.updateString("choix_1", sems1);
			rset.updateString("choix_2", sems2);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String select_choix1(String num ) {
		String rep="";
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			String  query="SELECT * FROM table_sems WHERE num_carte='"+num+"'";
			rset=statement.executeQuery(query);
			while(rset.next()){
				rep=rset.getString(2);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

	public static String select_choix2(String num ) {
		String rep="";
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement(); 
			String  query="SELECT * FROM table_sems WHERE num_carte='"+num+"'";
			rset=statement.executeQuery(query);
			while(rset.next()){
				rep=rset.getString(3);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}


	public static void ajout_etudiant_fac(String num,String nom,String prenom,String dateNaiss,String lieuNaiss,
			String nation ,String sexe,String situation,String numTel,String numCin,String dateCin,
			String lieuCin,String serieBacc,String anneBacc ,String numBacc, String centreBacc,
			String adreEt,String profEt,String nomPere,String profPere,String nomMere,String profMere,
			String adresParent,String img,String montant,String numQuit,String dateQuit,String niveau,
			String parcours,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO tab_etudiant ("
					+ " num_insc,  nom_et, prenom_et, date_naiss, lieu_naiss ,"
					+ "nationalite, sexe , situation ,num_tel , num_cin ,"  
					+ "date_cin ,lieu_cin ,serie_bacc ,anne_bacc ,num_bacc,centre_bacc ,adre_et , "
					+ "prof_et,nom_pere,prof_pere, nom_mere ,prof_mere, adres_pare,photo,montant,num_quit,"
					+ "dat_quit,niveau,parcours,mention ) "
					+ "VALUES ('"+num+"','"+nom.replaceAll("'", "''")+"','"+prenom.replaceAll("'", "''")+"','"+dateNaiss+"','"+lieuNaiss+"','"+nation+"',"
					+ "'"+sexe+"','"+situation+"','"+numTel+"','"+numCin+"','"+dateCin+"','"+lieuCin+"',"
					+ "'"+serieBacc+"','"+anneBacc+"','"+numBacc+"','"+centreBacc+"','"+adreEt+"','"+profEt+"',"
					+ "'"+nomPere+"','"+profPere+"','"+nomMere+"','"+profMere+"','"+adresParent+"'"
					+ ",'C:\\Photo_Sciences\\"+DataBase.database+"\\T.C.M\\"+img+".jpg','"+montant+"',"
					+ "'"+numQuit+"','"+dateQuit+"','"+niveau+"','"+parcours+"','"+mention+"')";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean recherche(String num) {
		boolean reponse=false;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths WHERE num_carte='"+num+"'" );
			while(rset.next()) {
				reponse=true;
			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reponse;
	}

	public static void recuperInformation(String num,JTextField tab[],JComboBox<?> combo,
			JCheckBox bout[],JLabel pic,JComboBox<String> sexe,JComboBox<String> nation, JTextField semsPTxt) {
		String l []= new String[19];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths WHERE num_carte='"+num+"'" );
			while(rset.next()) {
				l[1]=String.valueOf(rset.getString(1));
				for(int j=2; j<19; j++) {
					l[j]=rset.getString(j);
				}

			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(l[1]==null) {
			JOptionPane.showMessageDialog(Main.main, num+" n'a pas trouv� ", "ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}else {
			for(int k=0;k<12;k++) {
				switch(k) {
				case 0:
					tab[k].setText(l[1]);
					break;
				case 1:
					tab[k].setText(l[2]);
					break;
				case 2:
					tab[k].setText(l[3]);
					break;
				case 3:
					tab[k].setText(Methode.dateTextInt(l[4]));
					break;
				case 4:
					tab[k].setText(l[5]);
					break;
				case 5:
					tab[k].setText(l[6]);
					break;
				case 6:
					tab[k].setText(l[10]);
					break;
				case 7:
					tab[k].setText(Methode.dateTextInt(l[11]));
					break;
				case 8:
					tab[k].setText(l[12]);
					break;
				case 9:
					tab[k].setText(l[13]);
					break;
				case 10:
					tab[k].setText(l[14]);
					break;
				case 11:
					tab[k].setText(Methode.dateTextInt(l[15]));
				}

			}
			if(l[16].equals("Passant"))
				bout[0].setSelected(true);
			if(l[16].equals("Redoublant"))
				bout[1].setSelected(true);
			if(l[16].equals("Triplant"))
				bout[2].setSelected(true);

			combo.setSelectedItem(l[7]);
			pic.setIcon(ImageBonneQualite.image(l[17],120));	
			sexe.setSelectedItem(l[8]);
			nation.setSelectedItem(l[9]);
			semsPTxt.setText(l[18]);
		} 
	}



	public static void recuperInformation1(String num,JTextField tab[],JComboBox<?> combo,JCheckBox S[],
			JComboBox<String> sexe,JComboBox<String> nation, JLabel vide1) {
		String l []= new String[16];
		String niv="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM tab_etudiant WHERE num_insc='"+num+"'" );
			while(rset.next()) {
				vide1.setText(rset.getString("anne_bacc"));
				l[1]=rset.getString(1);
				niv=rset.getString("niveau");
				sexe.setSelectedItem(rset.getString("sexe"));
				nation.setSelectedItem(rset.getString("nationalite"));
				for(int j=2; j<16; j++) {
					if(j<6)
						l[j]=rset.getString(j);
					else {
						switch(j) {
						case 6:
							l[j]=rset.getString("adre_et");
							break;
						case 7:
							l[j]=rset.getString("parcours");
							break;
						case 8:
							l[j]=rset.getString("num_cin");
							break;
						case 9:
							l[j]=rset.getString("date_cin");
							break;
						case 10:
							l[j]=rset.getString("lieu_cin");
							break;
						case 11:
							l[j]=rset.getString("montant");
							break;
						case 12:
							l[j]=rset.getString("num_quit");
							break;
						case 13:
							l[j]=rset.getString("dat_quit");
							break;
						}
					}

				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int k=0;k<12;k++) {
			switch(k) {
			case 0:
				tab[k].setText(l[2]);
				break;
			case 1:
				tab[k].setText(l[3]);
				break;
			case 2:
				tab[k].setText(Methode.dateTextInt(l[4]));
				break;
			case 3:
				tab[k].setText(l[5]);
				break;
			case 4:
				tab[k].setText(l[5]);
				break;
			case 5:
				tab[k].setText(l[6]);
				break;
			case 6:
				tab[k].setText(l[8]);
				break;
			case 7:
				tab[k].setText(Methode.dateTextInt(l[9]));
				break;
			case 8:
				tab[k].setText(l[10]);
				break;
			case 9:
				tab[k].setText(l[11]);
				break;
			case 10:
				tab[k].setText(l[12]);
				break;
			case 11:
				tab[k].setText(Methode.dateTextInt(l[13]));
			}

		}

		switch(niv) {
		case "T.C.M":
			S[0].setSelected(true);
			S[1].setSelected(true);
			break;
		case "Master one":
			S[6].setSelected(true);
			S[7].setSelected(true);
			break;
		case "Master two":
			S[8].setSelected(true);
			S[9].setSelected(true);
			break;

		}
		//bout[1].setSelected(true);
		combo.setSelectedItem(l[7]);
		//pic.setIcon(new ImageIcon((Image)ImageBonneQualite.scaleImage2(120, 120, l[15])));	

	}

	public static void recuperInformationNouveau(String num,JTextField tab[],JComboBox<?> combo[], JLabel pic) {
		String l []= new String[30];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM tab_etudiant WHERE num_insc='"+num+"'" );
			while(rset.next()) {
				l[1]=String.valueOf(rset.getInt(1));
				for(int j=2; j<30; j++) {
					l[j]=rset.getString(j);
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int k=0;k<tab.length;k++) {
			switch(k) {
			case 0:
				tab[k].setText(l[1]);
				break;
			case 1:
				tab[k].setText(l[2]);
				break;
			case 2:
				tab[k].setText(l[3]);
				break;
			case 3:
				tab[k].setText(Methode.dateTextInt(l[4]));
				break;
			case 4:
				tab[k].setText(l[5]);
				break;
			case 5:
				tab[k].setText(l[9]);
				break;
			case 6:
				tab[k].setText(l[10]);
				break;
			case 7:
				tab[k].setText(Methode.dateTextInt(l[11]));
				break;
			case 8:
				tab[k].setText(l[12]);
				break;
			case 9:
				tab[k].setText(l[15]);
				break;
			case 10:
				tab[k].setText(l[16]);
				break;
			case 11:
				tab[k].setText(l[17]);
				break;
			case 12:
				tab[k].setText(l[18]);
				break;
			case 13:
				tab[k].setText(l[19]);
				break;
			case 14:
				tab[k].setText(l[20]);
				break;
			case 15:
				tab[k].setText(l[21]);
				break;
			case 16:
				tab[k].setText(l[22]);
				break;
			case 17:
				tab[k].setText(l[23]);
				break;
			case 18:
				tab[k].setText(l[25]);
				break;
			case 19:
				tab[k].setText(l[26]);
				break;
			case 20:
				tab[k].setText(Methode.dateTextInt(l[27]));
				break;
			}
		}
		combo[0].setSelectedItem(l[6]);
		combo[1].setSelectedItem(l[7]);
		combo[2].setSelectedItem(l[8]);
		combo[3].setSelectedItem(l[13]);
		combo[4].setSelectedItem(l[14]);
		combo[5].setSelectedItem(l[28]);
		combo[6].setSelectedItem(l[29]);
		pic.setIcon(new ImageIcon((Image)ImageBonneQualite.scaleImage2(120, 120, l[24])));		

	}

	public static void recuperClasse(String num,JCheckBox sems[]) {

		String[] l=new String[2];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_sems WHERE num_carte='"+num+"'");
			while(rset.next()) {
				l[0]=rset.getString(2);
				l[1]=rset.getString(3);
			}
			for (int i=0;i<sems.length;i++) {
				if(sems[i].getText().equals(l[0]) || sems[i].getText().equals(l[1]))
					sems[i].setSelected(true);


			}

			for (int i=0;i<sems.length;i++) {
				sems[i].setEnabled(false);

			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}

	public DefaultTableModel importDonneIns(DefaultTableModel model,String mention) {
		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM tab_etudiant ORDER BY tab_etudiant.num_insc";

			rset=statement.executeQuery(querry);

			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("num_insc"),Methode.numCarte(
						recuper_carte(LoginPrincipale.anneField.getText()), model.getRowCount()),
						rset.getString("nom_et")+" "+rset.getString("prenom_et"),rset.getString("niveau")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	/****************************************
	 *  COMPTER LE NOMBRE D'A.U A LA BASE    *
	 *****************************************/

	public static int  compteAU () {
		int k=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM anne_univ ");
			while(rset.next()) {
				k++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	}

	public static void modifier_etudiant_math(String num,String nom,String prenom,String dateNaiss,
			String lieuNaiss,String adresse,String numCin,String dateCin,String lieuCin,int montant,
			String numQuit,String dateQuit,String etat, String filiere,String sexe,String nation,double re) {


		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT num_carte, nom_et, prenom_et, date_naiss, lieu_naiss, adre_et,sexe_et,nation_et,"
					+ "filier_et, num_cin ,date_cin,lieu_cin ,montant ,"
					+ "num_quit ,dat_quit ,etat,bacc  FROM table_maths " 
					+ "WHERE num_carte='"+num+"'";
			rset = statement.executeQuery(query);
			rset.first();
			rset.updateString("num_carte", num);
			rset.updateString("nom_et", nom);
			rset.updateString("prenom_et", prenom);
			rset.updateString("date_naiss", dateNaiss);
			rset.updateString("lieu_naiss", lieuNaiss);
			rset.updateString("adre_et", adresse);
			rset.updateString("filier_et", filiere);
			rset.updateString("num_cin", numCin);
			rset.updateString("date_cin", dateCin);
			rset.updateString("lieu_cin", lieuCin);
			rset.updateInt("montant", montant);
			rset.updateString("num_quit", numQuit);
			rset.updateString("dat_quit", dateQuit);
			rset.updateString("etat", etat);
			rset.updateString("sexe_et", sexe);
			rset.updateString("nation_et", nation);
			rset.updateDouble("bacc", re);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(null, "ETUDIANT BIEN MODIFIER ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"ERREUR LORS DE LA MODOFICATION  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void modifier_etudiant_fac(String num,String nom,String prenom,String dateNaiss,String lieuNaiss,
			String nation ,String sexe,String situation,String numTel,String numCin,String dateCin,
			String lieuCin,String serieBacc,String anneBacc ,String numBacc, String centreBacc,
			String adreEt,String profEt,String nomPere,String profPere,String nomMere,String profMere,
			String adresParent,String img,String montant,String numQuit,String dateQuit,String niveau,
			String parcours) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT num_insc, nom_et, prenom_et, date_naiss, lieu_naiss,nationalite,sexe, "
					+ "situation,num_tel,adre_et,num_cin ,date_cin,lieu_cin , serie_bacc,anne_bacc,num_bacc,"
					+ "centre_bacc,adre_et,prof_et,nom_pere,prof_pere,nom_mere,prof_mere,adres_pare,montant ,"
					+ "num_quit,dat_quit,niveau,parcours  FROM tab_etudiant " 
					+ "WHERE num_insc='"+num+"'";
			rset = statement.executeQuery(query);
			rset.first();
			rset.updateInt("num_insc", Integer.parseInt(num));
			rset.updateString("nom_et", nom);
			rset.updateString("prenom_et", prenom);
			rset.updateString("date_naiss", dateNaiss);
			rset.updateString("lieu_naiss", lieuNaiss);
			rset.updateString("adre_et", adreEt);
			rset.updateString("nationalite", nation);
			rset.updateString("sexe", sexe);
			rset.updateString("situation", situation);
			rset.updateString("num_tel", numTel);
			rset.updateString("num_cin", numCin);
			rset.updateString("date_cin", dateCin);
			rset.updateString("lieu_cin", lieuCin);
			rset.updateString("serie_bacc", serieBacc);
			rset.updateString("anne_bacc", anneBacc);
			rset.updateString("num_bacc", numBacc);
			rset.updateString("centre_bacc", centreBacc);
			rset.updateString("prof_et",profEt);
			rset.updateString("nom_pere", nomPere);
			rset.updateString("prof_pere", profPere);
			rset.updateString("nom_mere", nomMere);
			rset.updateString("prof_mere", profMere);
			rset.updateString("adres_pare", adresParent);
			rset.updateString("montant", montant);
			rset.updateString("num_quit", numQuit);
			rset.updateString("dat_quit", dateQuit);
			rset.updateString("niveau", niveau);
			rset.updateString("parcours", parcours);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main, "ETUDIANT BIEN MODIFIER ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Main.main,"ERREUR LORS DE LA MODOFICATION  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void supprimer(String num ) {

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate(" DELETE  FROM table_maths WHERE num_carte='"+num+"'");
			statement.executeUpdate(" DELETE  FROM table_sems WHERE num_carte ='"+num+"'");
			statement.executeUpdate(" DELETE  FROM table_valid_sems WHERE num_carte ='"+num+"'");
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main,"Etudiant bien Supprimer  ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void reset() {

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate(" DELETE  FROM table_maths");
			statement.executeUpdate(" DELETE  FROM table_sems");
			statement.executeUpdate(" DELETE  FROM table_valid_sems ");
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main,"Etudiant bien Supprimer  ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void resetInsc() {

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate(" DELETE  FROM tab_etudiant");
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main,"Etudiant bien Supprimer  ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void supprimer1( String num ) {

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate(" DELETE  FROM tab_etudiant WHERE  num_insc='"+num+"'");
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main,"Etudiant bien Supprimer  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/****************************************
	 *  RECUPERATION NON DE SCOLARITER      *
	 *****************************************/

	public static String nomScola () {
		String k="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste= "
					+ "'Chef de service de scolarit�' ");
			while(rset.next()) {
				k=rset.getString(1)+" "+rset.getString(2);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	} 

	/**************************************************
	 *  CREATION D'UN COMBOBOX A PARTIR DE LA BASE     *
	 ***************************************************/	

	public static JComboBox<String>   recuperAU () {
		JComboBox<String> combo=new JComboBox<>();
		JSONArray list_anne = null;
		try {
			list_anne = request_api.get_anne_univ();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String l[]=new String[list_anne.length()];
		for (int i=0; i< l.length;i++){
			l[i]=list_anne.getJSONObject(i).getString("title");
		}
			combo=new JComboBox<String>(l);
			
		return combo;
	}


	public static JComboBox<String>   recuperMention (){
		JComboBox<String> combo=new JComboBox<>();
		String l[]=new String[Main.listUuidMention.length()];
		for (int i=0; i< l.length;i++){
			try {
				l[i]=request_api.get_Mention_by_uuid(Main.listUuidMention.getString(i)).getString("title");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			combo=new JComboBox<String>(l);
			
		return combo;
	}

	public static void creation_table_Ue(String mention,int i,String databaMatiere) {
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			parc=parcMaths;
			break;
		}
		querry=new String[parc.length];
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			querry[i]="CREATE TABLE uni_enseign_"+parc[i] + 
					"(\r\n" + 
					"  nom_ue character varying,\r\n" + 
					"  credit integer ,\r\n" + 
					"  semestre character varying,\r\n" +
					"  parcours character varying,\r\n" +
					"  CONSTRAINT \"uni_enseign_"+parc[i]+"_pkey\"PRIMARY KEY (nom_ue)\r\n" + 
					")";
			statement.executeUpdate(querry[i]);
			statement.close();
			connection.close();
			mess.setText("Table unit� d'enseingement"+parc[i]+" cr�er avec succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/***********************************************************
	 * 			CREATION DE LA TABLE ELEMENT CONSTITUTIF	*
	 ***********************************************************/ 

	public static void creation_table_Ec(String mention,int i,String databaMatiere) {
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			parc=parcMaths;
			break;
		}
		querry=new String[parc.length];
		try { 

			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			querry[i]="CREATE TABLE el_cons_"+parc[i] + 
					"(\r\n" + 
					"  nom_ec character varying,\r\n" + 
					"  nom_ue character varying ,\r\n" + 
					"  semestre character varying ,\r\n" + 
					"  poids numeric,\r\n" +
					"  ensei character varying ,\r\n" + 
					"  CONSTRAINT \"el_cons_"+parc[i]+"_pkey\" PRIMARY KEY (nom_ec)\r\n" + 
					")";
			statement.executeUpdate( querry[i]);
			statement.close();
			connection.close();
			mess.setText("Table matiere ec "+parc[i]+" cr�er avec succ�es");
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void ajout_ue(String mention,String nom,int crdt,String sems,int i) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};
		try { 
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String  query="INSERT INTO uni_enseign_"+parc[i]+"( "
						+ " nom_ue,credit,semestre ) "
						+ "VALUES ('"+nom+"',"+crdt+","
						+ "'"+sems+"');";
				statement.executeUpdate(query);
				statement.close();
				connection.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void ajout_ec(String mention,String nom,String ue ,String sems,double poids,int i,String ensei) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};
		try { 
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String  query="INSERT INTO el_cons_"+parc[i]+" ( "
						+ " nom_ec,nom_ue,semestre,poids,ensei ) "
						+ "VALUES ('"+nom+"','"+ue+"','"+sems+"',"+poids+",'"+ensei+"')";
				statement.executeUpdate(query);
				statement.close();
				connection.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int  compteUe (String mention,int i,String sems) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		int k=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[i]+" WHERE "
					+ "semestre='"+sems+"'");
			while(rset.next()) {
				k++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	}

	public static int  compteEc (String mention,int i,String sems) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		int k=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" WHERE "
					+ "Semestre='"+sems+"'");
			while(rset.next()) {
				k++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	}
	public static JComboBox<String>   recuperUe (String mention,String sems,int i) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		JComboBox<String> combo=new JComboBox<>();
		String l[]=new String[compteUe(mention,i,sems)+1];
		int j=1;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[i]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				l[j]=rset.getString(1);
				j++;
			}
			combo=new JComboBox<String>(l);
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return combo;
	}

	public static  DefaultTableModel importDonneUe(DefaultTableModel model,String mention,String sems,
			String filier,int i) {

		mess.setText("Importation des Tables U.E "+mention+" "+sems+" "+filier);
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}

		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM uni_enseign_"+parc[i]+" WHERE semestre='"+sems+"'ORDER BY nom_ue";
			rset=statement.executeQuery(querry);

			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("nom_ue"), rset.getString("credit")});

			}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	public static  DefaultTableModel importDonneEC(DefaultTableModel model,String mention,String sems,
			String filier,int i) {
		mess.setText("Importation des Tables E.C "+mention+" "+sems+" "+filier);
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}

		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM el_cons_"+parc[i]+" WHERE semestre='"+sems+"'ORDER BY nom_ue";
			rset=statement.executeQuery(querry);
			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("nom_ec"), rset.getString("poids")
						, rset.getString("ensei")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	public static void recuperInformationEc(String mention,int i,String nom,JTextField 
			tab1,JTextField tab2,JTextField tab3,JComboBox<?> combo) {
		String l []= new String[4];
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" WHERE nom_ec='"+nom+"'" );
			while(rset.next()) {
				l[0]=rset.getString(1);
				l[1]=rset.getString(2);
				l[2]=rset.getString(4);
				l[3]=rset.getString(5);
			}
			tab1.setText(l[0]);
			tab2.setText(l[2]);
			tab3.setText(l[3]);
			combo.setSelectedItem(l[1]);
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void recuperInformationUe(String mention,int i,String nom,JTextField tab1,JTextField tab2) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		String l []= new String[4];
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[i]+" "
					+ "WHERE nom_ue='"+nom+"'" );
			while(rset.next()) {
				l[0]=rset.getString(1);
				l[1]=String.valueOf(rset.getInt(2));
			}
			tab1.setText(l[0]);
			tab2.setText(l[1]);
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerUe(String mention,int i, String nom ) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};

		try {   
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" DELETE  FROM  uni_enseign_"+parc[i]+" WHERE  nom_ue='"+nom+"'" );
				statement.close();
				connection.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void supprimerEc_Ue(String mention,int i, String nom ) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};
		try {   
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" DELETE  FROM  el_cons_"+parc[i]+" WHERE  nom_ue='"+nom+"'");
				statement.close();
				connection.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerEc(String mention,int i, String nom ) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};

		try {   
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" DELETE  FROM el_cons_"+parc[i]+" WHERE  nom_ec='"+nom+"'" );
				statement.close();
				connection.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void modifierUe(String mention,int i,String nom,int crdt) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};

		try {   
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[i]+" "
						+ "WHERE nom_ue='"+nom+"'" );
				rset.first();
				rset.updateString("nom_ue", nom);
				rset.updateInt("credit", crdt);
				rset.updateRow();
				rset.close();			
				statement.close();
				connection.close();

			}

			JOptionPane.showMessageDialog(null, "UE BIEN MODIFIER ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"ERREUR LORS DE LA MODOFICATION  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void modifierEc(String mention,int i,String nom,Double poids,String ensei) {
		String databaMatiere="";
		String databaMatierePrinc="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaMatierePrinc=databaseMatiereMathsPrinc;
			parc=parcMaths;
			break;
		}
		String baseMatier[]= {databaMatierePrinc,databaMatiere};
		try {   
			for(int k=0;k<baseMatier.length;k++) {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier[k];
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" "
						+ "WHERE nom_ec='"+nom+"'" );
				rset.first();
				rset.updateString("nom_ec", nom);
				rset.updateDouble("poids", poids);
				rset.updateString("ensei", ensei);
				rset.updateRow();
				rset.close();			
				statement.close();
				connection.close();
			}	
			JOptionPane.showMessageDialog(null, "EC BIEN MODIFIER ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"ERREUR LORS DE LA MODOFICATION  ", "Erreur",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void creation_table_note(JPanel pan,String mention,int i,String sems,int k) {
		String base="";
		String a="";
		String b="";
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String l[]=new String[compteEc(mention,k,sems)];
		


		String type[]= {"normal","rattrapage","final"};	 
		
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
						+ "tablename ='s"+i+"_"+parc[k]+"_ue_validation' " );
				while(rset.next()) {
					b=rset.getString("tablename");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(b=="") {
				String querry3= new String();
				try { 
					String url = "jdbc:postgresql://"+port+":5432/"+base;
					connection = DriverManager.getConnection(url, username, password); 
					statement = connection.createStatement();
					statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
					querry3="CREATE TABLE IF NOT EXISTS S"+i+"_"+parc[k]+"_ue_validation" + 
							"(\r\n" + 
							"  num_carte character varying NOT NULL,\r\n" + 
							"  CONSTRAINT \"S"+i+"_"+parc[k]+"_ue_validation_pkey\" PRIMARY KEY (num_carte)\r\n" + 
							")";
					statement.executeUpdate(querry3);
					statement.close();
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				} 
				int s=0;
				String g[]=new String[compteUe(mention,k,sems)+1];
				try {
					String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
							+ " semestre='"+sems+"'ORDER BY nom_ue");
					while(rset.next()) {
						g[s]=rset.getString(1)+"";
						s++;
					}
					rset.close();
					statement.close();
					connection.close();
					g[s]="valid";
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try {        	
					for(int h=0;h<g.length;h++) {
						String url = "jdbc:postgresql://"+port+":5432/"+base;
						connection = DriverManager.getConnection(url, username, password);      
						statement = connection.createStatement(); 
						statement.executeUpdate(" ALTER  TABLE S"+i+"_"+parc[k]+"_ue_validation ADD COLUMN "
								+ ""+g[h]+" character varying ");
						statement.close();
						connection.close();
					}
					JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_ue_validation cre�e avec succ�s ",
							"",JOptionPane.DEFAULT_OPTION);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}else {
				JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_ue_validation d�j� cr�ee ",
						"",JOptionPane.DEFAULT_OPTION);
			}
		
		
		for(int p=0;p<type.length;p++) {
		
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
						+ "tablename ='s"+i+"_"+parc[k]+"_"+type[p]+"' " );
				while(rset.next()) {
					a=rset.getString("tablename");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}

			if(a=="") {
				String querry= new String();
				String querry2= new String();
				try { 
					String url = "jdbc:postgresql://"+port+":5432/"+base;
					connection = DriverManager.getConnection(url, username, password); 
					statement = connection.createStatement();
					statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
					querry="CREATE TABLE IF NOT EXISTS S"+i+"_"+parc[k]+"_"+type[p]+"" + 
							"(\r\n" + 
							"  num_exam integer NOT NULL,\r\n" + 
							"  num_carte character varying NOT NULL,\r\n" + 
							"  CONSTRAINT \"S"+i+"_"+parc[k]+"_"+type[p]+"_pkey\" PRIMARY KEY (num_carte)\r\n" + 
							")";

					querry2="CREATE TABLE IF NOT EXISTS S"+i+"_"+parc[k]+"_ue_"+type[p]+"" + 
							"(\r\n" + 
							"  num_carte character varying NOT NULL,\r\n" + 
							"  CONSTRAINT \"S"+i+"_"+parc[k]+"_ue_"+type[p]+"_pkey\" PRIMARY KEY (num_carte)\r\n" + 
							")";
					statement.executeUpdate(querry);
					statement.executeUpdate(querry2);
					statement.close();
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				} 
				int j=0;  
				try {
					String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
							+ " semestre='"+sems+"'ORDER BY nom_ue");
					while(rset.next()) {
						l[j]=rset.getString("nom_ec")+"";
						j++;
					}
					rset.close();
					statement.close();
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
				try {        	
					for(int h=0;h<l.length;h++) {
						String url = "jdbc:postgresql://"+port+":5432/"+base;
						connection = DriverManager.getConnection(url, username, password);      
						statement = connection.createStatement(); 
						statement.executeUpdate(" ALTER  TABLE S"+i+"_"+parc[k]+"_"+type[p]+" ADD COLUMN "
								+ ""+l[h]+" character varying ");
						statement.close();
						connection.close();
					}
					JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_"+type[p]+" cre�e avec succ�s ",
							"",JOptionPane.DEFAULT_OPTION);
				}catch (Exception e) {
					e.printStackTrace();
				} 
				 int s=0;
				String g[]=new String[compteUe(mention,k,sems)+2];
				try {
					String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
							+ " semestre='"+sems+"'ORDER BY nom_ue");
					while(rset.next()) {
						g[s]=rset.getString(1)+"";
						s++;
					}
					rset.close();
					statement.close();
					connection.close();

					g[s]="credit";
					g[s+1]="moyenne";
				}catch (Exception e) {
					e.printStackTrace();
				}

				try {        	
					for(int h=0;h<g.length;h++) {
						String url = "jdbc:postgresql://"+port+":5432/"+base;
						connection = DriverManager.getConnection(url, username, password);      
						statement = connection.createStatement(); 
						statement.executeUpdate(" ALTER  TABLE S"+i+"_"+parc[k]+"_ue_"+type[p]+" ADD COLUMN "
								+ ""+g[h]+" character varying ");
						statement.close();
						connection.close();
					}
					JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_ue_"+type[p]+" cre�e avec succ�s ",
							"",JOptionPane.DEFAULT_OPTION);
				}catch (Exception e) {
					e.printStackTrace();
				} 
					
			}else {
				JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_"+type[p]+" est d�j� cre�e ",
						"",JOptionPane.DEFAULT_OPTION);
			}
		 
		}	/*
		int s=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
						+ " semestre='"+sems+"'ORDER BY nom_ue");
				while(rset.next()) {
					g[s]=rset.getString(1)+"";
					s++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {        	
				for(int h=0;h<g.length;h++) {
					String url = "jdbc:postgresql://"+port+":5432/"+base;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					statement.executeUpdate(" ALTER  TABLE S"+i+"_"+parc[k]+"_ue_validation ADD COLUMN "
							+ ""+g[h]+" character varying ");
					statement.close();
					connection.close();
				}
				JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_ue_validation cre�e avec succ�s ",
						"",JOptionPane.DEFAULT_OPTION);
			}catch (Exception e) {
				e.printStackTrace();
			} 
*/
			}

	public static int  compteEc (String mention,int i,String ue,String sems) {

		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		int k=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" WHERE "
					+ "nom_ue='"+ue+"'AND Semestre='"+sems+"'");
			while(rset.next()) {
				k++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	}

	public static DefaultTableModel crerTable (String mention,int i,String sems,String ue,int k
			,JPanel pan,String type,DefaultTableModel model ) {

		int n=compteEc(mention,k,ue,sems);
		String l[]=new String[n];
		String u[]=new String[n+3];
		String base="";
		String a="";
		if(ue!=null) {
			String databaMatiere="";
			String parc[]= {};
			switch(mention) {
			case "MATHEMATIQUES ET APPLICATIONS":
				databaMatiere=databaseMatiereMaths;
				base=databaseNoteMaths;;
				parc=parcMaths;
				break;
			}
			int j=0;
			try {   
				mess.setText("Importation de la table note "+parc[k].toUpperCase()+" Session "+type);
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
						+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
				while(rset.next()) {
					l[j]=rset.getString(1);
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
				model.setColumnCount(0);

				model.addColumn("N�Examen");
				model.addColumn("N�Carte");
				for (int p=0; p<l.length;p++) 
					model.addColumn(l[p]);
				model.addColumn("Moyenne");
				model.setRowCount(0);
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
						+ "tablename ='s"+i+"_"+parc[k]+"_"+type+"' " );
				while(rset.next()) {
					a=rset.getString("tablename");
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(a!="") {
				try {  
					String url1 = "jdbc:postgresql://"+DataBase.port+":5432/"+base;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery("SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" INNER JOIN "
							+ " s"+i+"_"+parc[k]+"_ue_"+type+" On "
							+ "s"+i+"_"+parc[k]+"_"+type+".num_carte=S"+i+"_"+parc[k]+"_ue_"+type+".num_carte "
							+ "ORDER BY  s"+i+"_"+parc[k]+"_"+type+".num_exam ");

					while(rset.next()) {
						u[0]=rset.getString(1);
						u[1]=rset.getString(2);
						for(int g=2;g<l.length+2;g++) {
							String val=rset.getString(l[g-2]);
							if(val==null || String.valueOf(val).equals("null"))
								u[g]="Abs";
							else
								u[g]=val;

						}
						try {  
							connection1 = DriverManager.getConnection(url1, username, password);      
							statement1 = connection.createStatement(); 
							rset1 = statement1.executeQuery("SELECT * FROM  s"+i+"_"+parc[k]+"_ue_"+type+""
									+ " WHERE num_carte='"+u[1]+"'");

							while(rset1.next()) {
								u[model.getColumnCount()-1]=String.valueOf(rset1.getDouble(ue));
								model.addRow(u);
							}

							rset1.close();
							statement1.close();
							connection1.close();
						}catch(Exception e) {
							e.printStackTrace();
						}

					}

					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else
				JOptionPane.showMessageDialog(pan,  " Creer dabord la Table S"+i+"_"+parc[k]+"_"+type+" ",
						"",JOptionPane.DEFAULT_OPTION);
		}
		return model;
	}

	public static boolean testTable(String mention,int i, int k,String type) {
		String base="";
		boolean test=false;
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
					+ "tablename ='s"+i+"_"+parc[k]+"_"+type+"' " );
			while(rset.next()) {
				test=true;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return test;
	}

	public static DefaultTableModel modifTable (String mention,DefaultTableModel model,String sems,String ue,int k) {
		int n=compteEc(mention,k,ue,sems);
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;

		}
		String l[]=new String[n];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
			while(rset.next()) {
				l[j]=rset.getString("nom_ec");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
			model.setColumnCount(0);
			model.addColumn("N�Examen");
			model.addColumn("N�Carte");
			for (int i=0; i<l.length;i++) 
				model.addColumn(l[i]);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public static DefaultTableModel crerTable (String mention,DefaultTableModel model,int i,int k,String type) {
		// table notes des etudiants complet
		String a="";
		String base="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;
			parc=parcMaths;
			break;
		}
		String l[]=new String[0];
		String u[]=new String[0];
		int j=0;
		int t=0;
		model=new TableModel(l,"",0);
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_"+type+"' ");
			while(rset.next()) {
				a=rset.getString("tablename");

			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(!a.equalsIgnoreCase("")) {
			mess.setText("Importation de la table note "+a.toUpperCase());
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte' " );
				while(rset.next()) {
					t++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}

			l=new String[t];
			u=new String[t+2];
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte'" );
				while(rset.next()) {
					l[j]=rset.getString("column_name");
					j++;
				}

				model=new TableModel(l,"",0);
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM S"+i+"_"+parc[k]+"_"+type+" ORDER BY num_exam");
				model.setRowCount(0);
				while(rset.next()) {
					u[0]=rset.getString(1);
					u[1]=rset.getString(2);
					for(int g=0;g<l.length;g++) {
						String val=rset.getString(l[g]);
						if(val==null || String.valueOf(val).equals("null"))
							u[g+2]="Abs";
						else
							u[g+2]=val;
					}
					model.addRow(u);
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}



	public static DefaultTableModel crerTableFinal(String mention,DefaultTableModel model,int i,int k) {
		// table notes des etudiants complet
		String a="";
		String b="";
		String base="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;
			parc=parcMaths;
			break;
		}
		String l[]=new String[0];
		String u[]=new String[0];
		int j=0;
		int t=0;
		model=new TableModel(l,"",0);
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_normal' ");
			while(rset.next()) {
				a=rset.getString("tablename");
			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_rattrapage' ");
			while(rset.next()) {
				b=rset.getString("tablename");
			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(!(a.equalsIgnoreCase("")||b.equalsIgnoreCase(""))) {
			mess.setText("Importation de  note finale s"+i+"_"+parc[k]);
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte' " );
				while(rset.next()) {
					t++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}

			l=new String[t];
			u=new String[t+2];
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte'" );
				while(rset.next()) {
					l[j]=rset.getString("column_name");
					j++;
				}

				model=new TableModel(l,"",0);
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM S"+i+"_"+parc[k]+"_normal ORDER BY num_exam");
				model.setRowCount(0);
				while(rset.next()) {
					u[0]=rset.getString(1);
					u[1]=rset.getString(2);
					for(int g=0;g<l.length;g++) {
						u[g+2]=rset.getString(l[g]);
					}
					model.addRow(u);
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public static DefaultTableModel actualiser(String mention,DefaultTableModel model,int i,int k,String type) {
		String a="";
		String base="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		int t=0;
		int j=0;
		String l[]=new String[0];
		String u[]=new String[0];

		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_"+type+"' ");
			while(rset.next()) {
				a=rset.getString("tablename");
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(a!="") {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte' " );
				while(rset.next()) {
					t++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}

			l=new String[t];
			u=new String[t+2];
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte'" );
				while(rset.next()) {
					l[j]=rset.getString("column_name");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
				if(model.getColumnCount()==2)
					TableModel.addCol(l, model);
			}catch(Exception e) {
				e.printStackTrace();
			}

			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM S"+i+"_"+parc[k]+"_"+type+" ORDER BY num_exam");
				model.setRowCount(0);
				while(rset.next()) {
					u[0]=rset.getString(1);
					u[1]=rset.getString(2);
					for(int g=0;g<l.length;g++) {
						String val=rset.getString(l[g]);
						if(val==null || String.valueOf(val).equals("null"))
							u[g+2]="Abs";
						else
							u[g+2]=val;
					}
					model.addRow(u);
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}


	public static void supprimerTable(JPanel pan, String mention,int i,int k) {
		String a="";
		String b="";
		String c="";
		String base="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		int option = JOptionPane.showConfirmDialog(pan,
				"Voulez-vous supprimer ce table?","ATTENTION!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(option == JOptionPane.OK_OPTION){
			
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
						+ " tablename='s"+i+"_"+parc[k]+"_ue_validation' ");
				while(rset.next()) {
					c=rset.getString("tablename");
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(c!="") {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" DROP TABLE "+c );
				statement.close();
				connection.close();
				JOptionPane.showMessageDialog(pan, "Table note "+c+" bien supprim� ", "",
						JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
			String type[]= {"normal","rattrapage","final"};
			for(int p=0;p<type.length;p++) {
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+base;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
							+ " tablename='s"+i+"_"+parc[k]+"_"+type[p]+"' ");
					while(rset.next()) {
						a=rset.getString("tablename");
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}

				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+base;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
							+ " tablename='s"+i+"_"+parc[k]+"_ue_"+type[p]+"' ");
					while(rset.next()) {
						b=rset.getString("tablename");
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}				

				if(a!="") {
					try {
						String url = "jdbc:postgresql://"+port+":5432/"+base;
						connection = DriverManager.getConnection(url, username, password);      
						statement = connection.createStatement(); 
						statement.executeUpdate(" DROP TABLE "+a );

						statement.close();
						connection.close();
						JOptionPane.showMessageDialog(pan, "Table note "+a+" bien supprim� ", "",
								JOptionPane.INFORMATION_MESSAGE);
					}catch(Exception e) {
						e.printStackTrace();
					}
					try {
						String url = "jdbc:postgresql://"+port+":5432/"+base;
						connection = DriverManager.getConnection(url, username, password);      
						statement = connection.createStatement(); 
						statement.executeUpdate(" DROP TABLE "+b );
						statement.close();
						connection.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}

		} 
	}

	public static DefaultTableModel crerTable (String mention,String sems,String ue,int k,JPanel pan1, DefaultTableModel model) {
		int n=compteEc(mention,k,ue,sems);
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		String l[]=new String[n];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
			while(rset.next()) {
				l[j]=rset.getString(1);
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
			model=new TableRec(l, pan1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public static boolean verificNum (String mention,int i,String num,String filiere) {
		String k="";
		boolean rep=false;
		String l[];
		try {   
			l=new String[4];
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON table_maths.num_carte ="
					+ "table_sems.num_carte WHERE table_sems.num_carte='"+num+"'");
			while(rset.next()) {
				k=rset.getString("num_carte");
				l[0]=rset.getString("choix_1");
				l[1]=rset.getString("choix_2");
				l[2]=rset.getString("filier_et");
				l[3]=rset.getString("mention");
			}
			rset.close();
			statement.close();
			connection.close();
			if(k=="") {
				JOptionPane.showMessageDialog(null, "Ce numero de Carte n'est pas encore inscrit ","ERREUR",
						JOptionPane.ERROR_MESSAGE);
			}else {
				if(!("S"+i).equals("S1")) {
					if((!l[0].equals("S"+i) && !l[1].equals("S"+i)) || !l[2].equals(filiere)  )
						JOptionPane.showMessageDialog(null, " Cette numero est inscrit en "+l[0]+" et "+l[1]+" "
								+ ""+l[2]+" \n Mais vous aviez le rentrez en S"+i+" "+filiere, 
								"ERREUR",JOptionPane.ERROR_MESSAGE);
					else
						rep=true;
				}else {
					if(l[3].equals(mention)) {
						if((!l[0].equals("S"+i) && !l[1].equals("S"+i)) )
							JOptionPane.showMessageDialog(null, " Cette numero est inscrit en "+l[0]+" et "+l[1]+" "
									+ ""+l[2]+" \n Mais vous avez le rentrez en S"+i+" "+filiere, 
									"ERREUR",JOptionPane.ERROR_MESSAGE);
						else
							rep=true;
					}else 
						JOptionPane.showMessageDialog(null, " Cette numero est inscrit en "+l[0]+" et "+l[1]+" "
								+ ""+l[2]+" mention  "+mention+"\n Mais vous avez le rentrez en S"+i+" "+filiere, 
								"ERREUR",JOptionPane.ERROR_MESSAGE);
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rep;

	}

	public static String max(String nbr1,String nbr2) {

		double rep1,rep2;
		if(nbr1.equals("null") || nbr1.equals("Abs") || nbr1.equals(""))
			rep1=0;
		else
			rep1=Double.parseDouble(nbr1);

		if(nbr2.equals("null") || nbr2.equals("Abs") || nbr1.equals(""))
			rep2=0;
		else
			rep2=Double.parseDouble(nbr2);

		if(rep1>=rep2)
			return nbr1;
		else
			return nbr2;
	}

	public static void ajoutNoteFinal(String mention,DefaultTableModel model,int i,int k,String sems,String ue) {
		boolean test=false;
		int nbr=0;
		int n=compteEc(mention,k,ue,sems);
		String l[]=new String[n];
		String d[]=new String[model.getColumnCount()];
		String d1[]=new String[model.getColumnCount()];
		String v[]=new String[model.getColumnCount()];
		String base="";
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String typ1[]= {"normal"};
		String typ[]= {"final"};
		String numC=(String)model.getValueAt(0,1);
		int numE=Integer.parseInt((String)model.getValueAt(0,0));

		for(int r=2;r<model.getColumnCount();r++) {
			if(String.valueOf(model.getValueAt(0,r)).equals(null)|| String.valueOf(model.getValueAt(0,r)).equals(""))
				d[r]="null";
			else
				d[r]=String.valueOf(model.getValueAt(0,r));
			v[r]=model.getColumnName(r);
		}

		for(int r=2;r<model.getColumnCount();r++) {
			d1[r]=(String)null;
		}
		for(int r=2;r<model.getColumnCount();r++) {
			try {   
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_"+typ1[0]+"  WHERE "
						+ "num_carte='"+numC+"'");
				while(rset.next()) {
					d1[r]=String.valueOf(rset.getString(v[r]));

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}


		for(int x=0;x<typ.length;x++) {
			for(int r=2;r<model.getColumnCount();r++) {
				d[r]=max(d[r],d1[r]);

			}

			try {   
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_"+typ[x]+"  WHERE "
						+ "num_carte='"+numC+"'");
				while(rset.next()) {
					nbr++;
				}
				if(nbr!=0)
					test=true;
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			int j=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
						+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
				while(rset.next()) {
					l[j]=rset.getString(1);
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			try {String moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
			String credit=calcule_credit(mention, sems, i, k, typ[x], numC);
			
			String url= "jdbc:postgresql://"+DataBase.port+":5432/"+base;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			if(!test) {
				switch(n) {
				case 1:
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
							+ "num_exam,num_carte,"+l[0]+") VALUES ("+numE+",'"+numC+"',"+d[2]+")");
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule1(d[2], 
									getPoids(mention,k, l[0]))+")");
						
					break;
				case 2:
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
							+ "num_exam,num_carte,"+l[0]+","+l[1]+") "
							+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+")");
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,"+ue+")VALUES ('"+numC+"',"+Calcule.Calcule2(d[2], d[3], 
									getPoids(mention,k, l[0]),getPoids(mention,k, l[1]))+")");
					
						break;
				case 3:
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
							+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+") "
							+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+")");
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule3(d[2], d[3], 
									d[4], getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
									getPoids(mention,k, l[2]))+")");
					break;
				case 4:
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
							+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+") "
							+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+")");
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,"+ue+") "
							+ "VALUES ('"+numC+"',"+Calcule.Calcule4(d[2], d[3], d[4], d[5],
									getPoids(mention,k, l[0]), getPoids(mention,k, l[1]), 
									getPoids(mention,k, l[2]),getPoids(mention,k, l[3]))+")");
					break;
				case 5:
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
							+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+","+l[4]+") "
							+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+","+d[6]+")");
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule5(d[2], d[3], 
									d[4], d[5], d[6], getPoids(mention,k, l[0]), 
									getPoids(mention,k, l[1]), getPoids(mention,k, l[2]), 
									getPoids(mention,k, l[3]), getPoids(mention,k, l[4]))+")");
					break;

				}
				statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
						+ "num_carte,credit,moyenne) VALUES ('"+numC+"',"+calcule_credit(mention, sems, i, k, typ[x], numC)+","
								+calcule_moyenne(mention, sems, i, k, typ[x], numC)+ ")");

			}else {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				switch(n) {
				case 1:

					rset = statement.executeQuery("SELECT num_carte,num_exam,"+l[0]+" FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateInt("num_exam", numE);
					rset.updateString(l[0], d[2]);
					rset.updateRow();

					rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateDouble(ue, Calcule.Calcule1(d[2],getPoids(mention,k, l[0])));
					rset.updateRow();
					
					
					break;
				case 2:
					rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+" "
							+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateInt("num_exam", numE);
					rset.updateString(l[0], d[2]);
					rset.updateString(l[1], d[3]);
					rset.updateRow();

					rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateDouble(ue, Calcule.Calcule2(d[2], d[3], 
							getPoids(mention,k, l[0]),getPoids(mention,k, l[1])));
					rset.updateRow();
					
					break;
				case 3:
					rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+" "
							+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateInt("num_exam", numE);
					rset.updateString(l[0], d[2]);
					rset.updateString(l[1], d[3]);
					rset.updateString(l[2], d[4]);
					rset.updateRow();

					rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateDouble(ue,Calcule.Calcule3(d[2], d[3],d[4], 
							getPoids(mention,k, l[0]),  getPoids(mention,k, l[1]),
							getPoids(mention,k, l[2])));
					rset.updateRow();
					
					break;
				case 4:
					rset = statement.executeQuery(" SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+" "
							+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateInt("num_exam", numE);
					rset.updateString(l[0], d[2]);
					rset.updateString(l[1], d[3]);
					rset.updateString(l[2], d[4]);
					rset.updateString(l[3], d[5]);
					rset.updateRow();

					rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateDouble(ue, Calcule.Calcule4(d[2], d[3], d[4], d[5],
							getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
							getPoids(mention,k, l[2]), getPoids(mention,k, l[3])));
					rset.updateRow();
					
					break;
				case 5:
					rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+","
							+ ""+l[4]+"  FROM S"+i+"_"+parc[k]+"_"+typ[x]+" WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateInt("num_exam", numE);
					rset.updateString(l[0], d[2]);
					rset.updateString(l[1], d[3]);
					rset.updateString(l[2], d[4]);
					rset.updateString(l[3], d[5]);
					rset.updateString(l[4], d[6]);
					rset.updateRow();

					rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateDouble(ue, Calcule.Calcule5(d[2], d[3], 
							d[4], d[5], d[6], getPoids(mention,i, l[0]),
							getPoids(mention,k, l[1]),getPoids(mention,k, l[2]), 
							getPoids(mention,k, l[3]), getPoids(mention,k, l[4])));
					rset.updateRow();
					
				break;
				}
				 moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
				 credit=calcule_credit(mention, sems, i, k, typ[x], numC);
				
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				rset = statement.executeQuery("SELECT num_carte,credit,moyenne FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
						+ "WHERE num_carte='"+numC+"'");
				rset.first();
				rset.updateString("num_carte", numC);
				rset.updateString("credit", credit);
				rset.updateString("moyenne", moyenne);
				rset.updateRow();
				

			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
			}
		}	

	}

	public static void ajoutNoteRat(String mention,DefaultTableModel model,int i,int k,String sems,String ue,JPanel pan) {
		boolean test=false;
		int nbr=0;
		int n=compteEc(mention,k,ue,sems);
		String l[]=new String[n];
		String d[]=new String[model.getColumnCount()];
		String base="";
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String typ[]= {"rattrapage"};
		String numC=(String)model.getValueAt(0,1);
		int numE=Integer.parseInt((String)model.getValueAt(0,0));

		for(int r=2;r<model.getColumnCount();r++) {
			try {
				if(String.valueOf(model.getValueAt(0,r)).equals(null)||String.valueOf(model.getValueAt(0,r)).equals("") )
					d[r]="null";
				else
					d[r]=String.valueOf(model.getValueAt(0,r));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(pan, "Invalide valeur au colonne"+r,
						"ERREUR",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
		for(int x=0;x<typ.length;x++) {
			try {   
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_"+typ[x]+"  WHERE "
						+ "num_carte='"+numC+"'");
				while(rset.next()) {
					nbr++;
				}
				if(nbr!=0)
					test=true;
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			int j=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
						+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
				while(rset.next()) {
					l[j]=rset.getString(1);
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			try {   
				String moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
				String credit=calcule_credit(mention, sems, i, k, typ[x], numC);
				
				String url= "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				if(!test) {
					switch(n) {
					case 1:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+") VALUES ("+numE+",'"+numC+"',"+d[2]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule1(d[2], 
										getPoids(mention,k, l[0]))+")");
						break;
					case 2:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+")VALUES ('"+numC+"',"+Calcule.Calcule2(d[2], d[3], 
										getPoids(mention,k, l[0]),getPoids(mention,k, l[1]))+")");
							break;
					case 3:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule3(d[2], d[3], 
										d[4], getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
										getPoids(mention,k, l[2]))+")");
						break;
					case 4:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") "
								+ "VALUES ('"+numC+"',"+Calcule.Calcule4(d[2], d[3], d[4], d[5],
										getPoids(mention,k, l[0]), getPoids(mention,k, l[1]), 
										getPoids(mention,k, l[2]),getPoids(mention,k, l[3]))+")");
						break;
					case 5:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+","+l[4]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+","+d[6]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule5(d[2], d[3], 
										d[4], d[5], d[6], getPoids(mention,k, l[0]), 
										getPoids(mention,k, l[1]), getPoids(mention,k, l[2]), 
										getPoids(mention,k, l[3]), getPoids(mention,k, l[4]))+")");
						break;

					}
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,credit,moyenne) VALUES ('"+numC+"',"+calcule_credit(mention, sems, i, k, typ[x], numC)+","
									+calcule_moyenne(mention, sems, i, k, typ[x], numC)+ ")");

				}else {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					switch(n) {
					case 1:

						rset = statement.executeQuery("SELECT num_carte,num_exam,"+l[0]+" FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule1(d[2],getPoids(mention,k, l[0])));
						rset.updateRow();
						
						
						break;
					case 2:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule2(d[2], d[3], 
								getPoids(mention,k, l[0]),getPoids(mention,k, l[1])));
						rset.updateRow();
						
						break;
					case 3:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue,Calcule.Calcule3(d[2], d[3],d[4], 
								getPoids(mention,k, l[0]),  getPoids(mention,k, l[1]),
								getPoids(mention,k, l[2])));
						rset.updateRow();
						
						break;
					case 4:
						rset = statement.executeQuery(" SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateString(l[3], d[5]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule4(d[2], d[3], d[4], d[5],
								getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
								getPoids(mention,k, l[2]), getPoids(mention,k, l[3])));
						rset.updateRow();
						
						break;
					case 5:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+","
								+ ""+l[4]+"  FROM S"+i+"_"+parc[k]+"_"+typ[x]+" WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateString(l[3], d[5]);
						rset.updateString(l[4], d[6]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule5(d[2], d[3], 
								d[4], d[5], d[6], getPoids(mention,i, l[0]),
								getPoids(mention,k, l[1]),getPoids(mention,k, l[2]), 
								getPoids(mention,k, l[3]), getPoids(mention,k, l[4])));
						rset.updateRow();
						
					break;
					}
					 moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
					 credit=calcule_credit(mention, sems, i, k, typ[x], numC);
					
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					
					rset = statement.executeQuery("SELECT num_carte,credit,moyenne FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateString("credit", credit);
					rset.updateString("moyenne", moyenne);
					rset.updateRow();
					

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}	

	}

	public static void ajoutNoteNorm(String mention,DefaultTableModel model,int i,int k,String sems,String ue,JPanel pan) {
		boolean test=false;
		int nbr=0;
		int n=compteEc(mention,k,ue,sems);
		String l[]=new String[n];
		String d[]=new String[model.getColumnCount()];
		String base="";
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			base=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String typ[]= {"normal"};
		String numC=(String)model.getValueAt(0,1);
		int numE=Integer.parseInt((String)model.getValueAt(0,0));

		for(int r=2;r<model.getColumnCount();r++) {
			try {
				if(String.valueOf(model.getValueAt(0,r)).equals(null)||String.valueOf(model.getValueAt(0,r)).equals("") )
					d[r]="null";
				else
					d[r]=String.valueOf(model.getValueAt(0,r));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(pan, "Invalide valeur au colonne"+r,
						"ERREUR",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
		for(int x=0;x<typ.length;x++) {
			try {
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_"+typ[x]+"  WHERE "
						+ "num_carte='"+numC+"'");
				while(rset.next()) {
					nbr++;
				}
				if(nbr!=0)
					test=true;
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			int j=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
						+ "nom_ue='"+ue+"'AND semestre='"+sems+"'ORDER BY nom_ec");
				while(rset.next()) {
					l[j]=rset.getString(1);
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}


			try {  
				   
				String moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
				String credit=calcule_credit(mention, sems, i, k, typ[x], numC);
				
				String url= "jdbc:postgresql://"+DataBase.port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				if(!test) {
					switch(n) {
					case 1:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+") VALUES ("+numE+",'"+numC+"',"+d[2]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule1(d[2], 
										getPoids(mention,k, l[0]))+")");	
						break;
					case 2:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+")VALUES ('"+numC+"',"+Calcule.Calcule2(d[2], d[3], 
										getPoids(mention,k, l[0]),getPoids(mention,k, l[1]))+")");
						break;
					case 3:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule3(d[2], d[3], 
										d[4], getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
										getPoids(mention,k, l[2]))+")");
						break;
					case 4:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") "
								+ "VALUES ('"+numC+"',"+Calcule.Calcule4(d[2], d[3], d[4], d[5],
										getPoids(mention,k, l[0]), getPoids(mention,k, l[1]), 
										getPoids(mention,k, l[2]),getPoids(mention,k, l[3]))+")");
						break;
					case 5:
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
								+ "num_exam,num_carte,"+l[0]+","+l[1]+","+l[2]+","+l[3]+","+l[4]+") "
								+ "VALUES ("+numE+",'"+numC+"',"+d[2]+","+d[3]+","+d[4]+","+d[5]+","+d[6]+")");
						statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
								+ "num_carte,"+ue+") VALUES ('"+numC+"',"+Calcule.Calcule5(d[2], d[3], 
										d[4], d[5], d[6], getPoids(mention,k, l[0]), 
										getPoids(mention,k, l[1]), getPoids(mention,k, l[2]), 
										getPoids(mention,k, l[3]), getPoids(mention,k, l[4]))+")");
						break;

					}
					statement.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"("
							+ "num_carte,credit,moyenne) VALUES ('"+numC+"',"+calcule_credit(mention, sems, i, k, typ[x], numC)+","
									+calcule_moyenne(mention, sems, i, k, typ[x], numC)+ ")");

				}else {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					switch(n) {
					case 1:

						rset = statement.executeQuery("SELECT num_carte,num_exam,"+l[0]+" FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule1(d[2],getPoids(mention,k, l[0])));
						rset.updateRow();
						
						break;
					case 2:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule2(d[2], d[3], 
								getPoids(mention,k, l[0]),getPoids(mention,k, l[1])));
						rset.updateRow();
						
						break;
					case 3:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue,Calcule.Calcule3(d[2], d[3],d[4], 
								getPoids(mention,k, l[0]),  getPoids(mention,k, l[1]),
								getPoids(mention,k, l[2])));
						rset.updateRow();
						
						break;
					case 4:
						rset = statement.executeQuery(" SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+" "
								+ "FROM S"+i+"_"+parc[k]+"_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateString(l[3], d[5]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte, "+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule4(d[2], d[3], d[4], d[5],
								getPoids(mention,k, l[0]), getPoids(mention,k, l[1]),
								getPoids(mention,k, l[2]), getPoids(mention,k, l[3])));
						rset.updateRow();
						
						break;
					case 5:
						rset = statement.executeQuery("SELECT num_carte,num_exam, "+l[0]+","+l[1]+","+l[2]+","+l[3]+","
								+ ""+l[4]+"  FROM S"+i+"_"+parc[k]+"_"+typ[x]+" WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateInt("num_exam", numE);
						rset.updateString(l[0], d[2]);
						rset.updateString(l[1], d[3]);
						rset.updateString(l[2], d[4]);
						rset.updateString(l[3], d[5]);
						rset.updateString(l[4], d[6]);
						rset.updateRow();

						rset = statement.executeQuery("SELECT num_carte,"+ue+" FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
								+ "WHERE num_carte='"+numC+"'");
						rset.first();
						rset.updateString("num_carte", numC);
						rset.updateDouble(ue, Calcule.Calcule5(d[2], d[3], 
								d[4], d[5], d[6], getPoids(mention,i, l[0]),
								getPoids(mention,k, l[1]),getPoids(mention,k, l[2]), 
								getPoids(mention,k, l[3]), getPoids(mention,k, l[4])));
						rset.updateRow();
						
					break;
					}
					 moyenne=calcule_moyenne(mention, sems, i, k, typ[x], numC);
					 credit=calcule_credit(mention, sems, i, k, typ[x], numC);
					
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					
					rset = statement.executeQuery("SELECT num_carte,credit,moyenne FROM S"+i+"_"+parc[k]+"_ue_"+typ[x]+" "
							+ "WHERE num_carte='"+numC+"'");
					rset.first();
					rset.updateString("num_carte", numC);
					rset.updateString("credit", credit);
					rset.updateString("moyenne", moyenne);
					rset.updateRow();
					

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}	

	}
	
	public static String calcule_credit (String mention,String sems,int i,int k,String type,String num) {
		String databaMatiere="";
		double cred=0;
		String databaseNote="";
		String parc[]= {};
		
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection= DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" WHERE num_carte ='"+num+"'");
			while(rset.next()) {
				double val=0;
				for(int p=0;p<matier.length;p++) {
					val =rset.getDouble(matier[p]);
					if(val>10 || val==10) {
						cred+=credit[p];
					}
				}

			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(cred);
	}
	
	public static String calcule_moyenne (String mention,String sems,int i,int k,String type,String num) {
		String databaMatiere="";
		double moyen=0;
		String databaseNote="";
		String parc[]= {};
		
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" WHERE num_carte ='"+num+"'");
			while(rset.next()) {
				double val=0;
				for(int p=0;p<matier.length;p++) {
					val +=rset.getDouble(matier[p])*credit[p];
				}
				moyen=(double)Math.round((val/30)*1000)/1000;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(moyen);
	}

	public static double  getPoids (String mention,int i,String nom) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		double k=0;

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection1 = DriverManager.getConnection(url, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" WHERE "
					+ "nom_ec='"+nom+"'");
			while(rset1.next()) {
				k=rset1.getDouble("poids");
			}

			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return k;

	}

	public static void ajout_Au(String anne) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO anne_univ (anne,carte) VALUES ('"+anne+"',1);";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int recuper_carte(String anne) {
		int k=0;
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection1 = DriverManager.getConnection(url, username, password);      
			statement1 = connection1.createStatement();
			rset1=statement1.executeQuery("SELECT * FROM  anne_univ WHERE anne='"+anne+"'");
			while(rset1.next()) {
				k=Integer.parseInt(rset1.getString("carte"));
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return k;
	}


	public static void modif_carte(String anne,int nbr) {
		int k=0;
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="SELECT * FROM anne_univ WHERE anne='"+anne+"'";
			rset=statement.executeQuery(query);
			while(rset.next()) {
				k=Integer.parseInt(rset.getString("carte"));
			}
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(k==0) {
			JOptionPane.showMessageDialog(Main.main,"l'anne "+anne+"n'est pas encore cr�er ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			try {   

				String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				rset = statement.executeQuery(" SELECT * FROM anne_univ WHERE anne='"+anne+"'" );
				rset.first();
				rset.updateString("anne", anne);
				rset.updateInt("carte", nbr);
				rset.updateRow();
				rset.close();			
				statement.close();
				connection.close();
				JOptionPane.showMessageDialog(Main.main,"Op�ration termin� avec succes", "",
						JOptionPane.INFORMATION_MESSAGE);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void modifierPers(int i,String nom,String prenom,String mention) {
		String l="";
		String pic="";
		boolean test=false;
		switch(i) {
		case 1: 
			l="Doyen";
			pic="C:\\Photo_Sciences\\utilisateur\\doyen.jpg";
			break;
		case 2:
			l="Chef de service de scolarit�";
			pic="C:\\Photo_Sciences\\utilisateur\\chef_sco.jpg";
			break;
		case 3:
			l="Chef de la Mention Maths";
			pic="C:\\Photo_Sciences\\utilisateur\\chef_ment_maths.jpg";
			break;
		case 4:
			l="Secretaire principale";
			pic="C:\\Photo_Sciences\\utilisateur\\sec_pal.jpg";
			break;
		}

		if(i==3) {
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery("SELECT * FROM personnel WHERE mention='"+mention+"'");		
				while(rset.next()) {
					test=true;
				}

				rset.close();
				statement.close();
				connection.close();
			}catch(Exception h) {
				System.out.println(h.getMessage());
			}
			if(!test) {
				try { 

					String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement();
					String  query="INSERT INTO personnel (nom_personel,prenom_personel,poste,photo,mention) VALUES ('"+nom+"'"
							+ ",'"+prenom+"','"+l+"','"+pic+"','"+mention+"');";
					statement.executeUpdate(query);
					statement.close();
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else {

				try {   
					String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste='"+l+"'" );
					rset.first();
					rset.updateString("nom_personel", nom);
					rset.updateString("prenom_personel", prenom);
					rset.updateRow();
					rset.close();			
					statement.close();
					connection.close();

					JOptionPane.showMessageDialog(Main.main, l+" BIEN MODIFIER ", "",
							JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(Main.main,"ERREUR LORS DE LA MODOFICATION  ", "Erreur",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}else {

			try {
				String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery("SELECT * FROM personnel WHERE poste='"+l+"'");		
				while(rset.next()) {
					test=true;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception h) {
				System.out.println(h.getMessage());
			}
			if(!test) {
				try { 

					String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement();
					String  query="INSERT INTO personnel (nom_personel,prenom_personel,poste,photo) VALUES ('"+nom+"'"
							+ ",'"+prenom+"','"+l+"','"+pic+"');";
					statement.executeUpdate(query);
					statement.close();
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else {

				try {   
					String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste='"+l+"'" );
					rset.first();
					rset.updateString("nom_personel", nom);
					rset.updateString("prenom_personel", prenom);
					rset.updateRow();
					rset.close();			
					statement.close();
					connection.close();

					JOptionPane.showMessageDialog(Main.main, l+" bien modifi� ", "",
							JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(Main.main,"Erreur lors de la modification  ", "Erreur",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}

	public static void nomPers (int i, JTextField nom,JTextField prenom,JLabel pic) {
		String k="";
		switch(i) {
		case 1: k="Doyen";
		break;
		case 2:k="Chef de service de scolarit�";
		break;
		case 3:k="Chef de la Mention Maths";
		break;
		case 4:k="Secretaire principale";
		break;
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste='"+k+"'");
			while(rset.next()) {
				nom.setText(rset.getString(1));
				prenom.setText(rset.getString(2));
				pic.setIcon(ImageBonneQualite.image( rset.getString(4),90));
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void crerCert(int j,String num,String clas,String an,String numAt) {
		String a="";String b="";String c="";String d="";String f="";String e="";
		String mention="";
		try {
			String url = "jdbc:postgresql://"+DataBase.port+":5432/"+DataBase.database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery("SELECT * FROM table_maths INNER JOIN table_sems ON table_maths.num_carte ="
					+ "table_sems.num_carte WHERE table_sems.num_carte='"+num+"'");		
			while(rset.next()) {
				f=rset.getString(1);
				a=rset.getString(2);
				b=rset.getString(3);
				c=rset.getString(4);
				d=rset.getString(5);
				e=rset.getString(7);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception h) {
			System.out.println(h.getMessage());
		}
		mention="MATHEMATIQUES&nbsp;ET&nbsp;APPLICATIONS";
		if(e.equals("T.C.M"))
			e="Tronc&nbsp;Commun&nbsp;Math�matiques ";
		if(e.equals("M.I.S.S"))
			e="Math�matiques&nbsp;et&nbsp;Informatiques&nbsp;pour&nbsp;les&nbsp;Sciences&nbsp;Scociales ";
		if(e.equals("M.E"))
			e="Math�matiques&nbsp;Economiques ";
		if(e.equals("M.F"))
			e="Math�matiques&nbsp;Fondamentales ";
		if(f=="")
			Fenetre.mess.setVisible(true);
		else {
			switch(j) {
			case 1:
				if(an.equals("____-____")) {
					Fenetre.mess1.setVisible(true);
				}else {
					if(numAt.length()==0)
						Fenetre.mess4.setVisible(true);
					else {
						Fenetre.dial.setVisible(false);
						new FenetreImpressionCertificat(mention,j,an,a,b,c,d,num,e,clas,numAt).setVisible(true);
						Fenetre.reset();
					}
				}
				break;
			case 2:
				if(an.equals("__ /__ /____")) {
					Fenetre.mess2.setVisible(true);
				}else {
					if(Methode.dateText(an).equals("invalide date")) {
						Fenetre.mess3.setVisible(true);
					}else {
						if(numAt.length()==0)
							Fenetre.mess4.setVisible(true);
						else {
							Fenetre.dial.setVisible(false);
							new FenetreImpressionCertificat(mention,j,an,a,b,c,d,num,e,clas,numAt).setVisible(true);
							Fenetre.reset();
						}
					}
				}
				break;

			case 3:
				if(an.equals("____-____")) {
					Fenetre.mess1.setVisible(true);
				}else {
					if(numAt.length()==0)
						Fenetre.mess4.setVisible(true);
					else {
						Fenetre.dial.setVisible(false);
						new FenetreImpressionCertificat(mention,j,an,a,b,c,d,num,e,clas,numAt).setVisible(true);
						Fenetre.reset();
					}
				}
				break;
			case 4:
				if(an.equals("____-____")) {
					Fenetre.mess1.setVisible(true);
				}else {
					if(numAt.length()==0)
						Fenetre.mess4.setVisible(true);
					else {
						Fenetre.dial.setVisible(false);
						new FenetreImpressionCertificat(mention,j,an,a,b,c,d,num,e,clas,numAt).setVisible(true);
						Fenetre.reset();
					}
				}
				break;
			}
		}

	}

	public static boolean testExistance(String mention) {
		String l = null;
		boolean t=false;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM utilisateur WHERE type='true'"
					+ "AND  mention='"+mention+"'" );
			while(rset.next()) {
				l=rset.getString(1);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}
		if((String.valueOf(l)).equals("true")) 
			t=true;

		return t; 
	}

	public static String recuperNomUtil(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection1 = DriverManager.getConnection(url, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM utilisateur WHERE type='true'"
					+ " AND mention='"+mention+"'" );
			while(rset1.next()) {
				l=rset1.getString(2);
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static String recuperPhotoUtil() {
		String l = "";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM utilisateur WHERE type='true'" );
			while(rset.next()) {
				l=rset.getString(4);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static String recuperMotDePasseUtil(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM utilisateur WHERE type='true'"
					+ " AND mention='"+mention+"'"  );
			while(rset.next()) {
				l=rset.getString(3);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;

	}

	public static String recuperMotDePasseMent(String mention) {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM personnel WHERE  mention='"+mention+"'"  );
			while(rset.next()) {
				l=rset.getString("mot_de_passe");
			}
			if(l==null)
				l="";
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;

	}

	public static String recuperNomSco() {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste='Chef de service de scolarit�'" );
			while(rset.next()) {
				l=rset.getString(1)+" "+rset.getString(2);
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static void ajout_utilisateur(String nom,String mdp,String img,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO utilisateur ( "
					+ " type,nom_utilisateur, mot_de_passe, photo,mention ) "
					+ "VALUES ('true','"+nom+"','"+mdp+"',"
					+ "'C:\\Photo_Sciences\\utilisateur\\"+img+".jpg','"+mention+"');";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void modif_mdp_utilisateur(String mdp,String mention) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT nom_utilisateur, mot_de_passe,mention FROM utilisateur WHERE mention='"+mention+"'";
			rset = statement.executeQuery(query);
			rset.first();
			rset.updateString("nom_utilisateur", recuperNomUtil(mention));
			rset.updateString("mention", mention);
			rset.updateString("mot_de_passe", mdp);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void modif_mdp_chef_mention(String mdp,String poste) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT  mot_de_passe,poste FROM personnel WHERE poste='"+poste+"'";
			rset=statement.executeQuery(query);
			rset.first();
			rset.updateString("poste", poste);
			rset.updateString("mot_de_passe", mdp);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void modif_mdp_chef_sco(String mdp) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT  mot_de_passe,poste FROM personnel WHERE poste='Chef de service de scolarit�'";
			rset=statement.executeQuery(query);
			rset.first();
			rset.updateString("poste", "Chef de service de scolarit�");
			rset.updateString("mot_de_passe", mdp);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String recuperMdpSco() {
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM personnel WHERE poste='Chef de service de scolarit�'" );
			while(rset.next()) {
				l=rset.getString("mot_de_passe");
			}
			if(l==null)
				l="";
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static boolean test_anne_univ(String anne) {
		boolean rep=false;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseUtilisateur;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM anne_univ WHERE anne='"+anne+"'" );
			while(rset.next()) {
				rep=true;
			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		return rep;
	}

	public static  DefaultTableModel importSemsetre(DefaultTableModel model,String num) {
		model.setRowCount(0);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_valid_sems WHERE num_carte='"+num+"'";
			rset=statement.executeQuery(querry);
			while(rset.next()) {
				model.addRow(new Object[] {Boolean.valueOf(rset.getString("sems_1")), Boolean.valueOf(rset.getString("sems_2"))
						, Boolean.valueOf(rset.getString("sems_3")),Boolean.valueOf(rset.getString("sems_4")),
						Boolean.valueOf(rset.getString("sems_5")),
						Boolean.valueOf( rset.getString("sems_6")),Boolean.valueOf(rset.getString("sems_7")),
						Boolean.valueOf(rset.getString("sems_8")),
						Boolean.valueOf( rset.getString("sems_9")),Boolean.valueOf(rset.getString("sems_10"))});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	public static void ajout_Semsetre(String num,String sems[]) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_valid_sems(num_carte,sems_1,sems_2,sems_3,sems_4,sems_5,sems_6,sems_7,"
					+ "sems_8,sems_9,sems_10 ) "
					+ "VALUES ('"+num+"','"+sems[0]+"','"+sems[1]+"','"+sems[2]+"','"+sems[3]+"','"+sems[4]+"','"+sems[5]+"'"
					+ ",'"+sems[6]+"','"+sems[7]+"','"+sems[8]+"','"+sems[9]+"')";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
			JOptionPane.showMessageDialog(Main.main, "mis � jours effectu�", "",
					JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(Main.main, "numero d�ja � jours", "ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void import_Semsetre(String num,String sems[]) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String  query="INSERT INTO table_valid_sems(num_carte,sems_1,sems_2,sems_3,sems_4,sems_5,sems_6,sems_7,"
					+ "sems_8,sems_9,sems_10 ) "
					+ "VALUES ('"+num+"','"+sems[0]+"','"+sems[1]+"','"+sems[2]+"','"+sems[3]+"','"+sems[4]+"','"+sems[5]+"'"
					+ ",'"+sems[6]+"','"+sems[7]+"','"+sems[8]+"','"+sems[9]+"')";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Main.main, "numero d�ja � jours", "ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}

	}


	public static void donne_resultat_ue(String mention,DefaultTableModel model1,DefaultTableModel model2,
			String ue,int k,int i,String type) {
		model1.setRowCount(0);
		model2.setRowCount(0);

		String databaseNote="";
		String parc[] = null;
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaseNote=databaseNoteMaths;
			parc=parcMaths;
			break;
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte WHERE "	        		
					+ "s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS NOT null OR "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS null "
					+ "ORDER BY s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" DESC ");
			while(rset.next()) {
				String ue1=rset.getString(ue);
				if(ue1!=null) {
					if(ue1.equals("null")) {
						ue1="Abs";
						model2.addRow(new Object[] {rset.getString("num_carte"),"<html>"+rset.getString("nom_et")+"&nbsp;"
								+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ue1)});
					}
					else {
						if (Double.parseDouble(ue1)>=10) {
							model1.addRow(new Object[] {rset.getString("num_carte"),"<html>"+Methode.finitTextLab(rset.getString("nom_et")).toUpperCase()+"&nbsp;"
									+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ue1)});
						}else {
							model2.addRow(new Object[] {rset.getString("num_carte"),"<html>"+rset.getString("nom_et")+"&nbsp;"
									+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ue1)});

						}
					}
				}else {
					ue1="Abs";
					model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+Methode.finitTextLab(rset.getString("prenom_et")),ue1});	        		
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void donne_resultat_ec(String mention,DefaultTableModel model1,DefaultTableModel model2,
			String ec,int k,int i,String type) {
		model1.setRowCount(0);
		model2.setRowCount(0);

		String databaseNote="";
		String parc[] = null;
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaseNote=databaseNoteMaths;
			parc=parcMaths;
			break;
		}


		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			statement.executeUpdate("CREATE EXTENSION  IF NOT EXISTS dblink;");
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_"+type+".num_carte = table_maths.num_carte WHERE "
					+  "(s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS NOT null OR "
					+  "s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS null )"
					+ " ORDER BY s"+i+"_"+parc[k]+"_"+type+"."+ec+" DESC ");
			while(rset.next()) {
				String ec1=rset.getString(ec) ;
				if(ec1!=null) {
					if(ec1.equals("null")||ec1.equals("")) {
						ec1="Abs";
						model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
								+rset.getString("prenom_et"),ec1});	
					}
					else {
						if (Double.parseDouble(ec1)>=10) {
							model1.addRow(new Object[] {rset.getString("num_carte"),"<html>"+Methode.finitTextLab(rset.getString("nom_et"))+"&nbsp; "
									+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ec1)});
						}else {
							model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
									+rset.getString("prenom_et"),Double.parseDouble(ec1)});	
						}
					}
				}else {
					ec1="Abs";
					model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),ec1});
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void donne_resultat_ue_ec_echec(String mention,DefaultTableModel model1,DefaultTableModel model2,
			String ec,String ue,int k,int i,String type) {
		model1.setRowCount(0);
		model2.setRowCount(0);

		String databaseNote="";
		String parc[] = null;
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaseNote=databaseNoteMaths;
			parc=parcMaths;
			break;
		}
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" INNER JOIN "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+" ON s"+i+"_"+parc[k]+"_"+type+".num_carte="
					+ "s"+i+"_"+parc[k]+"_ue_"+type+".num_carte INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_"+type+".num_carte = table_maths.num_carte WHERE "
					+ "(s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS NOT null OR "					
					+ " s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS  null  )"
					+ " AND ( s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS null OR "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS NOT null) "
					+ "ORDER BY s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" DESC");
			while(rset.next()) {

				String ue1=rset.getString(ue);
				String ec1=rset.getString(ec);
				if(ec1!=null) {
					if(ec1.equals("null")) {
						ec1="Abs";
						model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
								+rset.getString("prenom_et"),ec1});	
					}
					else {
						if (Double.parseDouble(ec1)<10) {
							model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
									+rset.getString("prenom_et"),Double.parseDouble(ec1)});
						}
					}
				}else {
					ec1="Abs";
					model2.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),ec1});
				}
				
				
				if(ue1!=null) {
					
						if (Double.parseDouble(ue1)<10) {
							model1.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
									+rset.getString("prenom_et"),Double.parseDouble(ue1)});
						
					}
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void  donne_resultat_ue_ec_succe(String mention,DefaultTableModel model1,DefaultTableModel model2,
			String ec,String ue,int k,int i,String type) {
		model1.setRowCount(0);
		model2.setRowCount(0);

		String databaseNote="";
		String parc[] = null;
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaseNote=databaseNoteMaths;
			parc=parcMaths;
			break;
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" INNER JOIN "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+" ON s"+i+"_"+parc[k]+"_"+type+".num_carte="
					+ "s"+i+"_"+parc[k]+"_ue_"+type+".num_carte INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_"+type+".num_carte = table_maths.num_carte WHERE "
					+ "(s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS NOT null OR "					
					+ " s"+i+"_"+parc[k]+"_"+type+"."+ec+" IS  null )"
					+ " AND ( s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS null OR "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" IS NOT null) "
					+ "ORDER BY s"+i+"_"+parc[k]+"_ue_"+type+"."+ue+" DESC");
			while(rset.next()) {
				String ue1=rset.getString(ue);
				String ec1=rset.getString(ec);
				if(ue1!=null) {	
					if(!ue1.equals("null")) {	        				
						if ((Double.parseDouble(ue1)>=10)&&(Double.parseDouble(ec1)>=10)) {
							model1.addRow(new Object[] {rset.getString("num_carte"),"<html>"+Methode.finitTextLab(rset.getString("nom_et"))+" "
									+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ue1)});
							model2.addRow(new Object[] {rset.getString("num_carte"),"<html>"+rset.getString("nom_et")+" "
									+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",Double.parseDouble(ec1)});

						}
					}
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void selectUe (String mention,String sems,int i,JTextField combo,String ec) {
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		String l="";
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[i]+" WHERE "
					+ "semestre='"+sems+"' AND nom_ec='"+ec+"'");
			while(rset.next()) {
				l=rset.getString("nom_ue");
			}

			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		combo.setText(l);;
	}

	public static void statBach(String mention,DefaultTableModel model ) {
		int nbr=0;
		int ligne=1;
		int col=1;
		int total=0;
		String serie[]= {"A1","A2","C","D","Tech.G�nie civile","t�chnique","Tech.Tertiaire","Tech.Agricole","Technique","Autre","Ensemble"};
		String niveau="T.C.M";


		for(int h=0;h<serie.length;h++) {
			nbr=0;
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM tab_etudiant  WHERE "
						+ "tab_etudiant.niveau='"+niveau+"'"
						+ "AND tab_etudiant.serie_bacc='"+serie[h]+"'");
				while(rset.next()) {
					nbr++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {

			}
			total=total+nbr;
			if (h==serie.length-1) {
				model.setValueAt(total, ligne, col);
			}
			else {
				model.setValueAt(nbr, ligne, col);
			}

			col++;

		}		

	}

	public static void resultCreditinf (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,int cred) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				int S=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						S+=credit[p];
				}
				if(S<cred)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),S});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}

	public static void resultCreditegal (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,int cred) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				int S=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						S+=credit[p];
				}
				if(S==cred)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),S});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}
	public static void resultCreditsup (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,int cred) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				int S=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						S+=credit[p];
				}
				if(S>cred)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),S});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void resultMoyeninf (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,double moyen) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				if(moy<moyen)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}

	public static void resultMoyenegal (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,double moyen) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				if(moy==moyen)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}
	public static void resultMoyensup (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,double moyen) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				if(moy>moyen)
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void resultMoyenCredtinf (String mention,String sems,int i,int k,DefaultTableModel model,
			DefaultTableModel model1, String type,double moyen,int cred) {
		model.setRowCount(0);
		model1.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {

				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				int C=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						C+=credit[p];
				}
				if(moy>moyen && C<cred) {
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),C});
					model1.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}

	public static void resultMoyenCredtegal (String mention,String sems,int i,int k,DefaultTableModel model,
			DefaultTableModel model1, String type,double moyen,int cred) {
		model.setRowCount(0);
		model1.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {

				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				int C=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						C+=credit[p];
				}
				if(moy>moyen && C==cred) {
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),C});
					model1.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void resultMoyenCredtsup (String mention,String sems,int i,int k,DefaultTableModel model,
			DefaultTableModel model1, String type,double moyen,int cred) {
		model.setRowCount(0);
		model1.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {

				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				int C=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						C+=credit[p];
				}
				if(moy>moyen && C>cred) {
					model.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),C});
					model1.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "
							+rset.getString("prenom_et"),moy});
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}



	public static void resultEtudiant (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,JLabel moyen,JLabel cred,JLabel nom,JLabel prenom,String num,JLabel numT,JLabel img,JCheckBox chek) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		String credt="";
		String moy="";
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()){
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths WHERE num_carte='"+num+"'");
			while(rset.next()){
				nom.setText(rset.getString("nom_et"));
				prenom.setText(rset.getString("prenom_et"));
				numT.setText(rset.getString("num_carte"));
				img.setIcon(ImageBonneQualite.image(rset.getString("photo"),90));
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" WHERE num_carte='"+num+"'";
	
			rset = statement.executeQuery(querry);
			while(rset.next()) {
				for(int t=0;t<matier.length;t++) {
					model.addRow(new Object[] {"*"+Methode.finit(matier[t],i)," ",rset.getString(matier[t])});
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
					connection1 = DriverManager.getConnection(url1, username, password);      
					statement1 = connection1.createStatement(); 
					rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
							+ "semestre='"+sems+"' AND nom_ue='"+matier[t]+"'ORDER BY nom_ec");
					while(rset1.next()){
						 	try {
								connection2 = DriverManager.getConnection(url, username, password);      
								statement2 = connection2.createStatement();
								String querry1="SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" WHERE num_carte='"+num+"'";
								rset2 = statement2.executeQuery(querry1);
								while(rset2.next()){
									model.addRow(new Object[] {"",Methode.finit(rset1.getString("nom_ec"),i),
											rset2.getString(rset1.getString("nom_ec"))});
								  }
								rset2.close();
								statement2.close();
								connection2.close();
							}catch(Exception e){
								e.printStackTrace();
							}
					}
					rset1.close();
					statement1.close();
					connection1.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				}
				credt=rset.getString("credit");
				moy=rset.getString("moyenne");
				
			}
			
			String val="";
			try { 
				String url1 = "jdbc:postgresql://"+port+":5432/"+database;
				connection1 = DriverManager.getConnection(url1, username, password);      
				statement1 = connection1.createStatement(); 
				rset1 = statement1.executeQuery(" SELECT * FROM table_valid_sems WHERE "
						+ "num_carte='"+num+"'");
				while(rset1.next()) {
					val=rset1.getString("sems_"+i);
				}
				rset1.close();
				statement1.close();
				connection1.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			cred.setText(""+credt);
			moyen.setText(""+moy);
			chek.setSelected(Boolean.valueOf(val));
			
			rset.close();
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deliberation (String mention,String sems,int i,int k,DefaultTableModel model,
			String type,double moyen,int cred) {
		model.setRowCount(0);
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteUe(mention,k,sems)];
		int credit[]=new int[compteUe(mention,k,sems)];
		int j=0;
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'ORDER BY nom_ue");
			while(rset.next()) {
				credit[j]=rset.getInt("credit");
				matier[j]=rset.getString("nom_ue");
				j++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" INNER JOIN ("
					+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
					+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
					+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
					+ "ON s"+i+"_"+parc[k]+"_ue_"+type+".num_carte = table_maths.num_carte ORDER BY table_maths.num_carte");
			while(rset.next()) {
				String val="";
				double S=0;
				double moy=0;
				for(int p=0;p<matier.length;p++) {
					S +=rset.getDouble(matier[p])*credit[p];
				}
				moy=(double)Math.round((S/30)*1000)/1000;
				int C=0;
				for(int p=0;p<matier.length;p++) {
					if(rset.getDouble(matier[p])>=10)
						C+=credit[p];
				}

				if(moy>=moyen && C>=cred) {
					try { 
						String url1 = "jdbc:postgresql://"+port+":5432/"+database;
						connection1 = DriverManager.getConnection(url1, username, password);      
						statement1 = connection1.createStatement(); 
						rset1 = statement1.executeQuery(" SELECT * FROM table_valid_sems WHERE "
								+ "num_carte='"+rset.getString("num_carte")+"'");
						while(rset1.next()) {
							val=rset1.getString("sems_"+i);
						}
						rset1.close();
						statement1.close();
						connection1.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
					if(cred==30)
						val="true";
					model.addRow(new Object[] {rset.getString("num_carte"),"<html>"+rset.getString("nom_et")+" "
							+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>",C,moy,Boolean.valueOf(val)});
				}
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void deliberationFinal(JPanel pan,String num,int i,String val) {
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rset = statement.executeQuery(" SELECT num_carte,sems_"+i+" FROM table_valid_sems "
					+ "WHERE num_carte='"+num+"'" );
			rset.first();
			rset.updateString("num_carte", num);
			rset.updateString("sems_"+i, val);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static String typeResult(String mention,String sems,int i,int k,String num) {
		String val="";
		String rep="";
		try { 
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM table_valid_sems WHERE "
					+ "num_carte='"+num+"'");
			while(rset1.next()) {
				val=rset1.getString("sems_"+i);
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(!Boolean.valueOf(val)) {
			rep="Non Valid�";

		}else {
			String databaMatiere="";
			String databaseNote="";
			String parc[]= {};
			switch(mention) {
			case "MATHEMATIQUES ET APPLICATIONS":
				databaMatiere=databaseMatiereMaths;
				databaseNote=databaseNoteMaths;;
				parc=parcMaths;
				break;
			}
			String matier[]=new String[compteUe(mention,k,sems)];
			int credit[]=new int[compteUe(mention,k,sems)];
			int j=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
						+ "semestre='"+sems+"'ORDER BY nom_ue");
				while(rset.next()) {
					credit[j]=rset.getInt("credit");
					matier[j]=rset.getString("nom_ue");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			int S=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(""
						+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_normal INNER JOIN ("
						+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
						+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
						+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
						+ "ON s"+i+"_"+parc[k]+"_ue_normal.num_carte = table_maths.num_carte "
						+ " WHERE table_maths.num_carte='"+num+"'");
				while(rset.next()) {
					for(int p=0;p<matier.length;p++) {
						if(rset.getDouble(matier[p])>=10)
							S+=credit[p];
					}

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(S==30) {
				rep="Valid�-S.N-D�finitive"; 
			}else {
				S=0;
				try {   
					String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(""
							+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_final INNER JOIN ("
							+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
							+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
							+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
							+ "ON s"+i+"_"+parc[k]+"_ue_final.num_carte = table_maths.num_carte "
							+ " WHERE table_maths.num_carte='"+num+"'");
					while(rset.next()) {
						for(int p=0;p<matier.length;p++) {
							if(rset.getDouble(matier[p])>=10)
								S+=credit[p];
						}

					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				if(S==30)
					rep="Valid�-S.R-D�finitive";
				else
					rep="Valid�-S.R-Compens�";
			}

		}
		return rep;
	}

	public static void typeResult(String mention,String sems,int i,int k,DefaultTableModel modelNorm,
			DefaultTableModel modelRat,DefaultTableModel modelComp) {
		modelNorm.setRowCount(0);
		modelRat.setRowCount(0);
		modelComp.setRowCount(0);


		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		int r=0;
		try { 

			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM table_valid_sems WHERE sems_"+i+"='true'");
			while(rset1.next()) {
				r++;
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		String num[]=new String[r];

		try { 
			int s=0;
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM table_valid_sems WHERE sems_"+i+"='true'");
			while(rset1.next()) {
				num[s]=String.valueOf(rset1.getString("num_carte"));
				s++;
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int h=0;h<r;h++) {
			switch(mention) {
			case "MATHEMATIQUES ET APPLICATIONS":
				databaMatiere=databaseMatiereMaths;
				databaseNote=databaseNoteMaths;;
				parc=parcMaths;
				break;
			}
			String matier[]=new String[compteUe(mention,k,sems)];
			int credit[]=new int[compteUe(mention,k,sems)];
			int j=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
						+ "semestre='"+sems+"'ORDER BY nom_ue");
				while(rset.next()) {
					credit[j]=rset.getInt("credit");
					matier[j]=rset.getString("nom_ue");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			int S=0;
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(""
						+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_normal INNER JOIN ("
						+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
						+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
						+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
						+ "ON s"+i+"_"+parc[k]+"_ue_normal.num_carte = table_maths.num_carte "
						+ " WHERE table_maths.num_carte='"+num[h]+"'");
				while(rset.next()) {
					for(int p=0;p<matier.length;p++) {
						if(rset.getDouble(matier[p])>=10)
							S+=credit[p];
					}
					if(S==30)
						modelNorm.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+
								rset.getString("prenom_et")});
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(S==30) {

			}else {
				S=0;
				try {   
					String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
					connection = DriverManager.getConnection(url, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(""
							+ "SELECT * FROM s"+i+"_"+parc[k]+"_ue_final INNER JOIN ("
							+ "SELECT * FROM  dblink('host=localhost user=postgres password=postgres dbname="+database+"',"
							+ "'SELECT num_carte,nom_et,prenom_et FROM table_maths') AS table_maths("
							+ "num_carte character varying, nom_et character varying,prenom_et character varying)) AS table_maths "
							+ "ON s"+i+"_"+parc[k]+"_ue_final.num_carte = table_maths.num_carte "
							+ " WHERE table_maths.num_carte='"+num[h]+"'");
					while(rset.next()) {
						for(int p=0;p<matier.length;p++) {
							if(rset.getDouble(matier[p])>=10)
								S+=credit[p];
						}
						if(S==30)
							modelRat.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+
									rset.getString("prenom_et")});
						else
							modelComp.addRow(new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+
									rset.getString("prenom_et")});

					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	public static DefaultTableModel resultParUe (String mention,String sems,int i,int k,DefaultTableModel model,String type,String ue,
			JPanel pan) {

		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		pan.removeAll();
		pan.setVisible(false);
		model=new DefaultTableModel();
		String databaMatiere="";
		String databaseNote="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			databaseNote=databaseNoteMaths;;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteEc(mention,k,ue,sems)];
		double poids[]=new double[compteEc(mention,k,ue,sems)];

		int credit=0;
		int j=0;


		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"'AND nom_ue='"+ue+"'");
			while(rset.next()) {
				credit=rset.getInt("credit");
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {   
			String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"' AND nom_ue='"+ue+"'ORDER BY nom_ec");
			while(rset1.next()) {
				matier[j]=rset1.getString("nom_ec");
				poids[j]=rset1.getDouble("poids");
				j++;
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


		model.addColumn("N�Examen");
		model.addColumn("N�Carte");
		for(int p=0;p<matier.length;p++) {
			model.addColumn(Methode.finit(matier[p],i));
			model.addColumn(poids[p]);
		}

		model.addColumn("Note");
		model.addColumn("Credit");
		int n=model.getColumnCount();
		JTable table=new JTable(model);
		JScrollPane scroll=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setPreferredScrollableViewportSize(new Dimension(800,500));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(n-2).setPreferredWidth(60);
		table.getColumnModel().getColumn(n-1).setPreferredWidth(60);
		for(int p=0;p<model.getColumnCount();p++)
			table.getColumnModel().getColumn(p).setCellRenderer(centerRenderer);
		if (type.equals("normal")){
			
		}else {
			if (type.equals("rattrapage")) {}
		}
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+databaseNote;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" INNER JOIN "
					+ "s"+i+"_"+parc[k]+"_ue_"+type+" ON s"+i+"_"+parc[k]+"_"+type+".num_carte="
					+ "s"+i+"_"+parc[k]+"_ue_"+type+".num_carte ORDER BY s"+i+"_"+parc[k]+"_"+type+".num_exam");
			while(rset.next()) {
				switch(matier.length) {
				case 1:
					model.addRow(new Object[] {rset.getString("num_exam"),rset.getString("num_carte"),
							reponse(rset.getString(matier[0])),calcul(rset.getString(matier[0]),poids[0]),
							rset.getDouble(ue),testCred(rset.getDouble(ue),credit)});
					break;
				case 2:
					model.addRow(new Object[] {rset.getString("num_exam"),rset.getString("num_carte"),
							reponse(rset.getString(matier[0])),calcul(rset.getString(matier[0]),poids[0]),
							reponse(rset.getString(matier[1])),calcul(rset.getString(matier[1]),poids[1]),
							rset.getDouble(ue),testCred(rset.getDouble(ue),credit)});
					break;
				case 3:
					model.addRow(new Object[] {rset.getString("num_exam"),rset.getString("num_carte"),
							reponse(rset.getString(matier[0])),calcul(rset.getString(matier[0]),poids[0]),
							reponse(rset.getString(matier[1])),calcul(rset.getString(matier[1]),poids[1]),
							reponse(rset.getString(matier[2])),calcul(rset.getString(matier[2]),poids[2]),
							rset.getDouble(ue),testCred(rset.getDouble(ue),credit)});
					break;
				case 4:
					model.addRow(new Object[] {rset.getString("num_exam"),rset.getString("num_carte"),
							reponse(rset.getString(matier[0])),calcul(rset.getString(matier[0]),poids[0]),
							reponse(rset.getString(matier[1])),calcul(rset.getString(matier[1]),poids[1]),
							reponse(rset.getString(matier[2])),calcul(rset.getString(matier[2]),poids[2]),
							reponse(rset.getString(matier[3])),calcul(rset.getString(matier[3]),poids[3]),
							rset.getDouble(matier[3]),(double)Math.round((rset.getDouble(matier[3])*poids[3])*1000)/1000,
							rset.getDouble(ue),testCred(rset.getDouble(ue),credit)});
					break;
				case 5:
					model.addRow(new Object[] {rset.getString("num_exam"),rset.getString("num_carte"),
							reponse(rset.getString(matier[0])),calcul(rset.getString(matier[0]),poids[0]),
							reponse(rset.getString(matier[1])),calcul(rset.getString(matier[1]),poids[1]),
							reponse(rset.getString(matier[2])),calcul(rset.getString(matier[2]),poids[2]),
							reponse(rset.getString(matier[3])),calcul(rset.getString(matier[3]),poids[3]),
							reponse(rset.getString(matier[4])),calcul(rset.getString(matier[4]),poids[4]),
							rset.getDouble(ue),testCred(rset.getDouble(ue),credit)});
					break;
				}

			}


			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		pan.add(scroll);
		pan.setVisible(true);
		return model;
	}

	public static String calcul(String nbr,double pds) {
		if(nbr!=null)
			if(nbr.equals("null"))
				return "Abs";
			else
				return String.valueOf((double)Math.round((Double.parseDouble(nbr)*pds)*1000)/1000);
		else
			return "Abs";
	}

	public static String reponse(String matier) {
		if(matier!=null)
			if(matier.equals("null"))
				return "Abs";
			else
				return matier;
		else
			return "Abs";

	}

	public static int testCred(double note, int credit) {
		if(note<10)
			return 0;
		else
			return credit;
	}

	public static void creerRelever(String mention,int i,int k,String type,String num,DefaultTableModel modelUe,
			DefaultTableModel modelEc,JLabel nom,JLabel prenom,JLabel naiss,JLabel lieu,JLabel parcours) {
		modelUe.setRowCount(0);
		modelEc.setRowCount(0);
		String base="";
		String baseMatier="";

		String parc[]={};
		String a="";
		String b="";
		String l[],u[],noteUe[],noteEc[],credit[],poids[];
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			base=databaseNoteMaths;
			baseMatier=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}


		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_"+type+"' ");
			while(rset.next()) {
				a=rset.getString("tablename");
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE"
					+ " tablename='s"+i+"_"+parc[k]+"_ue_"+type+"' ");
			while(rset.next()) {
				b=rset.getString("tablename");
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(a!="") {
			int t=0;

			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte' " );
				while(rset.next()) {
					t++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
			l=new String[t];
			poids=new String[t];
			noteEc=new String[t];
			t=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+b+"' AND column_name!='num_exam' AND column_name!='num_carte' " );
				while(rset.next()) {
					t++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}


			u=new String[t];
			credit=new String[t];
			noteUe=new String[t];
			int j=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+a+"' AND column_name!='num_exam' AND column_name!='num_carte'" );
				while(rset.next()) {
					l[j]=rset.getString("column_name");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			j=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT column_name from information_schema.columns "
						+ "where table_name='"+b+"' AND column_name!='num_exam' AND column_name!='num_carte'" );
				while(rset.next()) {
					u[j]=rset.getString("column_name");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			j=0;
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" WHERE num_carte='"+num+"'");
				while(rset.next()) {
					for(int r=0;r<l.length;r++) 
						noteEc[r]=String.valueOf(rset.getDouble(l[r]));
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			j=0;
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" WHERE num_carte='"+num+"'");
				while(rset.next()) {
					for(int r=0;r<u.length;r++) 
						noteUe[r]=String.valueOf(rset.getDouble(u[r]));
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			j=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM uni_enseign_"+parc[k]+" WHERE semestre='Semestre"+i+"'ORDER BY nom_ue";
				rset=statement.executeQuery(querry);
				while(rset.next()) {
					credit[j]=rset.getString("credit");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}

			j=0;
			try {
				String url = "jdbc:postgresql://"+port+":5432/"+baseMatier;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement();
				String querry="SELECT * FROM el_cons_"+parc[k]+" WHERE semestre='Semestre"+i+"'ORDER BY nom_ue";
				rset=statement.executeQuery(querry);
				while(rset.next()) {
					poids[j]=rset.getString("poids");
					j++;
				}
				rset.close();
				statement.close();
				connection.close();

			}catch(Exception e) {
				e.printStackTrace();
			}

			j=0;
			for(int r=0;r<l.length;r++) {
				modelEc.addRow(new Object[] {l[r],poids[r],noteEc[r]});
			}

			for(int r=0;r<u.length;r++) {
				modelUe.addRow(new Object[] {u[r],credit[r],noteUe[r]});
			}
			String fil="";
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM table_maths WHERE num_carte='"+num+"' ");
				while(rset.next()) {
					nom.setText("<html>"+Methode.finitTextLab(rset.getString("nom_et")).toUpperCase()+"</html>");
					prenom.setText("<html>"+Methode.finitTextLab(rset.getString("prenom_et"))+"</html>");
					naiss.setText("<html>"+Methode.finitTextLab(rset.getString("date_naiss"))+"</html>");
					lieu.setText("<html>"+Methode.finitTextLab(rset.getString("lieu_naiss"))+"</html>");
					fil=rset.getString("filier_et");
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(fil.equals("T.C.M"))
				parcours.setText("<html>Tronc&nbsp;Commun&nbsp;Math�matiques </html>");
			if(fil.equals("M.I.S.S"))
				parcours.setText("<html>Math�matiques&nbsp;et&nbsp;Informatiques&nbsp;pour&nbsp;les&nbsp;"
						+ "Sciences&nbsp;Scociales</html>");
			if(fil.equals("M.E"))
				parcours.setText("<html>Math�matiques&nbsp;Economiques</html> ");
			if(fil.equals("M.F"))
				parcours.setText("<html>Math�matiques&nbsp;Fondamentales</html>");

		}
	}

	public static void statistique(String mention, DefaultTableModel model) {
		int ligne=1;
		int col=0;
		int nbr=0;
		String parc[]= {"M.I.S.S","M.E","M.F"};
		String type[]= {"Passant","Redoublant","Triplant"};
		String sexe[]= {"Masculin","Feminin"};
		String semsI[]= {"S3","S5","S7","S9"};
		String semsP[]= {"S4","S6","S8","S10"};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			break;
		}
		col=1;
		mess.setText("importation des tables des effectif des etudiants tronc commun mathematiques");
		for(int k=0;k<sexe.length;k++) {
			for(int h=0;h<type.length;h++) {
				nbr=0;
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+database;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
							+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='S1'"
							+ "OR table_sems.choix_2='S2') AND table_maths.sexe_et='"+sexe[k]+"'"
							+ "AND table_maths.etat='"+type[h]+"'");
					while(rset.next()) {
						nbr++;
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				model.setValueAt(nbr, ligne, col);
				col++;
			}
		}

		for(int k=0;k<sexe.length;k++) {
			nbr=0;
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url1, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
						+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='S1'"
						+ "OR table_sems.choix_2='S2') AND table_maths.sexe_et='"+sexe[k]+"'"
						+ "");
				while(rset.next()) {
					nbr++;
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			model.setValueAt(nbr, ligne, col);
			col++;
		}
		nbr=0;
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
					+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='S1'"
					+ "OR table_sems.choix_2='S2') "
					+ "");
			while(rset.next()) {
				nbr++;
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.setValueAt(nbr, ligne, col);
		col++;
		col=1;
		for(int i=0;i<semsI.length;i++) {
			for(int j=0;j<parc.length;j++) {
				mess.setText("importation des tables des effectif des etudiants "+semsI[i]+" et "+ semsP[i]+" "+parc[j]);
				ligne++; 

				for(int k=0;k<sexe.length;k++) {
					for(int h=0;h<type.length;h++) {
						nbr=0;
						try {
							String url1 = "jdbc:postgresql://"+port+":5432/"+database;
							connection = DriverManager.getConnection(url1, username, password);      
							statement = connection.createStatement(); 
							rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
									+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='"+semsI[i]+"'"
									+ " OR table_sems.choix_2='"+semsP[i]+"')"
									+ "AND table_maths.filier_et='"+parc[j]+"'AND table_maths.sexe_et='"+sexe[k]+"'"
									+ "AND table_maths.etat='"+type[h]+"'");
							while(rset.next()) {
								nbr++;
							}
							rset.close();
							statement.close();
							connection.close();
						}catch(Exception e) {

						}
						model.setValueAt(nbr, ligne, col);
						col++;

					}
					/*	*/

				}

				for(int k=0;k<sexe.length;k++) {
					nbr=0;
					try {
						String url1 = "jdbc:postgresql://"+port+":5432/"+database;
						connection = DriverManager.getConnection(url1, username, password);      
						statement = connection.createStatement(); 
						rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
								+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='"+semsI[i]+"'"
								+ " OR table_sems.choix_2='"+semsP[i]+"')"
								+ "AND table_maths.filier_et='"+parc[j]+"'AND table_maths.sexe_et='"+sexe[k]+"'"
								+ "");
						while(rset.next()) {
							nbr++;
						}
						rset.close();
						statement.close();
						connection.close();
					}catch(Exception e) {

					}
					model.setValueAt(nbr, ligne, col);
					col++;
				}
				nbr=0;
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+database;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
							+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='"+semsI[i]+"' OR "
							+ "table_sems.choix_2='"+semsP[i]+"')"
							+ "AND table_maths.filier_et='"+parc[j]+"'"
							+ "");
					while(rset.next()) {
						nbr++;
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {

				}
				model.setValueAt(nbr, ligne, col);
				col++;
				col=1;
			}
		}
	}

	public static void statEtrange(String mention,DefaultTableModel model) {
		int nbr=0;
		int ligne=0;
		int col=0;
		String sexe[]= {"Masculin","Feminin"};
		String nation[]= {"Africaine","Asiatique","Comorienne","Europeenne","Autres"};
		String semsI[]= {"S1","S3","S5","S7","S9"};
		String semsP[]= {"S2","S4","S6","S8","S10"};
		for(int i=0;i<semsI.length;i++) {
			ligne ++;
			col=1;
			mess.setText("importation des tables des effectif des etudiants etranger "+semsI[i]+" et "+ semsP[i]);
			for(int h=0;h<nation.length;h++) {
				for(int k=0;k<sexe.length;k++) {
					nbr=0;
					try {
						String url1 = "jdbc:postgresql://"+port+":5432/"+database;
						connection = DriverManager.getConnection(url1, username, password);      
						statement = connection.createStatement(); 
						rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
								+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='"+semsI[i]+"'"
								+ " OR table_sems.choix_2='"+semsP[i]+"')"
								+ "AND table_maths.sexe_et='"+sexe[k]+"'"
								+ "AND table_maths.nation_et='"+nation[h]+"'");
						while(rset.next()) {
							nbr++;
						}
						rset.close();
						statement.close();
						connection.close();
					}catch(Exception e) {

					}
					model.setValueAt(nbr, ligne, col);
					col++;

				}
				/*	*/

			}
		}
	}

	public static void statDate(String mention,int dateActuel,String niveau,String filiere, DefaultTableModel model) {
		String sems1="";
		String sems2="";

		String type[]= {"Passant","Redoublant","Triplant"};
		String sexe[]= {"Masculin","Feminin"};

		int ligne=1;
		int col=1;
		int nbr=0;
		switch(niveau) {

		case "L1":
			sems1="S1";
			sems2="S2";
			break;
		case "L2":
			sems1="S3";
			sems2="S4";
			break;
		case "L3":
			sems1="S5";
			sems2="S6";
			break;
		case "M1":
			sems1="S7";
			sems2="S8";
			break;
		case "M2":
			sems1="S9";
			sems2="S10";
			break;
		}
		ligne=1;
		mess.setText("importation des tables des effectif des etudiants moins de 16 ans ");
		for(int k=0;k<sexe.length;k++) {
			for(int h=0;h<type.length;h++) {
				nbr=0;
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+database;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 

					rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
							+ "table_maths.num_carte=table_sems.num_carte WHERE "
							+ "( table_sems.choix_2='"+sems1+"' OR table_sems.choix_2='"+sems2+"')"
							+ "AND table_maths.sexe_et='"+sexe[k]+"'"
							+ "AND table_maths.etat='"+type[h]+"'"
							+ "AND table_maths.filier_et='"+filiere+"'"
							+ "AND CAST(right(table_maths.date_naiss,4) AS INTEGER)>("+dateActuel+"-16)");
					while(rset.next()) {
						nbr++;
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {

				}
				model.setValueAt(nbr, ligne, col);
				col++;
			}
			if(sexe[k]=="Masculin") {
				model.setValueAt(sommeTriple(ligne,1,2,3,model), ligne, col);
				col++;
			}else
			{
				model.setValueAt(sommeTriple(ligne,5,6,7,model), ligne, col);
				col++;
			}
		}
		for(int h=0;h<type.length;h++) {
			model.setValueAt(sommeDouble(ligne,h+1,5+h,model), ligne, col);
			col++;
		}
		model.setValueAt(sommeTriple(ligne,9,10,11,model), ligne, col);


		for(int i=16;i<29;i++)	{
			mess.setText("importation des tables des effectif des etudiants a l'age de "+i+" ans");
			ligne++;
			col=1;
			for(int k=0;k<sexe.length;k++) {
				for(int h=0;h<type.length;h++) {
					nbr=0;
					try {
						String url1 = "jdbc:postgresql://"+port+":5432/"+database;
						connection = DriverManager.getConnection(url1, username, password);      
						statement = connection.createStatement(); 

						rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
								+ "table_maths.num_carte=table_sems.num_carte WHERE "
								+ "(table_sems.choix_2='"+sems1+"' OR table_sems.choix_2='"+sems2+"')"
								+ "AND table_maths.sexe_et='"+sexe[k]+"'"
								+ "AND table_maths.etat='"+type[h]+"'"
								+ "AND table_maths.filier_et='"+filiere+"'"
								+ "AND CAST(right(table_maths.date_naiss,4) AS INTEGER)=("+dateActuel+"-"+i+")");
						while(rset.next()) {
							nbr++;
						}
						rset.close();
						statement.close();
						connection.close();
					}catch(Exception e) {

					}
					model.setValueAt(nbr, ligne, col);
					col++;
				}
				if(sexe[k]=="Masculin") {
					model.setValueAt(sommeTriple(ligne,1,2,3,model), ligne, col);
					col++;
				}else{
					model.setValueAt(sommeTriple(ligne,5,6,7,model), ligne, col);
					col++;
				}
			}
			for(int h=0;h<type.length;h++) {
				model.setValueAt(sommeDouble(ligne,h+1,5+h,model), ligne, col);
				col++;
			}
			model.setValueAt(sommeTriple(ligne,9,10,11,model), ligne, col);
		}
		ligne++;
		col=1;
		mess.setText("importation des tables des effectif des etudiants plus de 29 ans");
		for(int k=0;k<sexe.length;k++) {
			for(int h=0;h<type.length;h++) {
				nbr=0;
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+database;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 

					rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
							+ "table_maths.num_carte=table_sems.num_carte WHERE (table_sems.choix_2='"+sems1+"'"
							+ " OR table_sems.choix_2='"+sems2+"')"
							+ "AND table_maths.sexe_et='"+sexe[k]+"'"
							+ "AND table_maths.etat='"+type[h]+"'"
							+ "AND table_maths.filier_et='"+filiere+"'"
							+ "AND CAST(right(table_maths.date_naiss,4) AS INTEGER)<=("+dateActuel+"-29)");
					while(rset.next()) {
						nbr++;
					}
					rset.close();
					statement.close();
					connection.close();
				}catch(Exception e) {

				}
				model.setValueAt(nbr, ligne, col);
				col++;
			}
			if(sexe[k]=="Masculin") {
				model.setValueAt(sommeTriple(ligne,1,2,3,model), ligne, col);
				col++;
			}else
			{
				model.setValueAt(sommeTriple(ligne,5,6,7,model), ligne, col);
				col++;
			}
		}
		for(int h=0;h<type.length;h++) {
			model.setValueAt(sommeDouble(ligne,h+1,5+h,model), ligne, col);
			col++;
		}
		model.setValueAt(sommeTriple(ligne,9,10,11,model), ligne, col);
		ligne++;
		for(int i=1;i<model.getColumnCount();i++) {
			model.setValueAt(sommeMax(i, model), ligne, i);
		}
	}


	public static int sommeTriple(int ligne,int col1,int col2,int col3,DefaultTableModel model) {
		return  Integer.parseInt(String.valueOf(model.getValueAt(ligne, col1)))+
				Integer.parseInt(String.valueOf(model.getValueAt(ligne, col2)))+
				Integer.parseInt(String.valueOf(model.getValueAt(ligne, col3)));
	}

	public static int sommeDouble(int ligne,int col1,int col2,DefaultTableModel model) {
		return  Integer.parseInt(String.valueOf(model.getValueAt(ligne, col1)))+
				Integer.parseInt(String.valueOf(model.getValueAt(ligne, col2)));
	}
	public static int sommeMax(int col1,DefaultTableModel model) {
		int total=0;
		for(int i=1;i<model.getRowCount()-1;i++)
			total+=Integer.parseInt(String.valueOf(model.getValueAt(i, col1)));
		return total;
	}

	public static void main(String[] argv) {
		//	statDate();
	}	
	public static void licence(String mention,DefaultTableModel model) {
		model.setRowCount(0);
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
					+ "table_maths.num_carte=table_sems.num_carte INNER JOIN table_valid_sems ON "
					+ "table_sems.num_carte=table_valid_sems.num_carte WHERE "
					+ "table_sems.mention='"+mention+"'"
					+ "AND table_valid_sems.sems_1='true'"
					+ "AND table_valid_sems.sems_2='true'"
					+ "AND table_valid_sems.sems_3='true'"
					+ "AND table_valid_sems.sems_4='true'"
					+ "AND table_valid_sems.sems_5='true'"
					+ "AND table_valid_sems.sems_6='true'");
			while(rset.next()) {
				model.addRow (new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+rset.getString("prenom_et")
				,rset.getBoolean("diplome")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {

		}
	}

	public static void Master(String mention,DefaultTableModel model) {
		model.setRowCount(0);
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
					+ "table_maths.num_carte=table_sems.num_carte INNER JOIN table_valid_sems ON "
					+ "table_maths.num_carte=table_valide_sems.num_carte WHERE "
					+ "table_sems.mention='"+mention+"'"
					+ "AND table_valid_sems.sems_1='true'"
					+ "AND table_valid_sems.sems_2='true'"
					+ "AND table_valid_sems.sems_3='true'"
					+ "AND table_valid_sems.sems_4='true'"
					+ "AND table_valid_sems.sems_5='true'"
					+ "AND table_valid_sems.sems_6='true'"
					+ "AND table_valid_sems.sems_7='true'"
					+ "AND table_valid_sems.sems_8='true'"
					+ "AND table_valid_sems.sems_9='true'"
					+ "AND table_valid_sems.sems_10='true'");
			while(rset.next()) {
				model.addRow (new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+rset.getString("prenom_et"),
						rset.getBoolean("diplome")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {

		}
	}

	public static void act_diplome(String mention,DefaultTableModel model,String sems1,String sems2) {
		model.setRowCount(0);
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url1, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
					+ "table_maths.num_carte=table_sems.num_carte WHERE "
					+ "table_sems.mention='"+mention+"' AND "
					+ "(table_sems.choix_1='"+sems1+"' OR table_sems.choix_2='"+sems1+"' OR "
					+ " table_sems.choix_1='"+sems2+"' OR table_sems.choix_2='"+sems2+"' )"
					+ "AND table_sems.diplome='true'");
			while(rset.next()) {
				model.addRow (new Object[] {rset.getString("num_carte"),rset.getString("nom_et")+" "+rset.getString("prenom_et")
				,rset.getBoolean("diplome")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {

		}
	}

	public static void modif_diplome(String num,String valeur ) {
		try { 
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String  query="SELECT num_carte,diplome FROM table_sems WHERE num_carte='"+num+"'";
			rset=statement.executeQuery(query);
			rset.first();
			rset.updateString("num_carte", num);
			rset.updateString("diplome", valeur);
			rset.updateRow();
			rset.close();			
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void statDiplome(String mention,DefaultTableModel model ) {
		String sexe[]= {"Masculin","Feminin"};
		String semsP[]= {"S6","S10"};
		String semsI[]= {"S5","S9"};
		int ligne=1;
		int col=1;
		int nbr=0;
		for(int i=0;i<sexe.length;i++) {
			col=1;
			for(int j=0;j<semsP.length;j++) {
				nbr=0;
				try {
					String url1 = "jdbc:postgresql://"+port+":5432/"+database;
					connection = DriverManager.getConnection(url1, username, password);      
					statement = connection.createStatement(); 
					rset = statement.executeQuery(" SELECT * FROM table_maths INNER JOIN table_sems ON "
							+ "table_maths.num_carte=table_sems.num_carte WHERE "
							+ "table_sems.mention='"+mention+"' AND "
							+ "table_maths.sexe_et='"+sexe[i]+"' AND "
							+ "(table_sems.choix_1='"+semsP[j]+"' OR table_sems.choix_2='"+semsP[j]+"'OR "
							+ "table_sems.choix_1='"+semsI[j]+"' OR table_sems.choix_2='"+semsI[j]+"' )"
							+ "AND table_sems.diplome='true'");
					while(rset.next()) {
						nbr++;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				model.setValueAt(nbr, ligne, col);
				col++;
			}
			ligne++;
		}
		model.setValueAt(sommeLigne(model,1,1,2), 1,3);	
		model.setValueAt(sommeLigne(model,2,1,2), 2, 3);

		model.setValueAt(sommeColonne(model,1,2,1), 3,1);	
		model.setValueAt(sommeColonne(model,1,2,2), 3,2);

		model.setValueAt(sommeColonne(model,1,2,3), 3,3);

	}

	public static int sommeLigne(DefaultTableModel model,int ligne,int col1,int col2) {
		return Integer.parseInt(String.valueOf(model.getValueAt(ligne, col1)))+
				Integer.parseInt(String.valueOf(model.getValueAt(ligne, col2)));
	}

	public static int sommeColonne(DefaultTableModel model,int ligne1,int ligne2,int col) {
		return Integer.parseInt(String.valueOf(model.getValueAt(ligne1, col)))+
				Integer.parseInt(String.valueOf(model.getValueAt(ligne2, col)));
	}

	@SuppressWarnings("serial")
	public static DefaultTableModel liste (String mention,String sems,int i,int k,DefaultTableModel model,
			JPanel pan,int x,int y) {

		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		pan.removeAll();
		pan.setVisible(false);
		model=new DefaultTableModel() {
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		model.addColumn("N�");
		model.addColumn("N�Carte");
		model.addColumn("Nom et pr�nom");
		JTable table=new JTable(model);
		model.setRowCount(0);
		JScrollPane scroll=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setPreferredScrollableViewportSize(new Dimension(700,500));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		for(int p=0;p<model.getColumnCount();p++)
			table.getColumnModel().getColumn(p).setCellRenderer(centerRenderer);
		try {   
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset = statement.executeQuery(""
					+ "SELECT * FROM table_maths INNER JOIN "
					+ "table_sems ON table_maths.num_carte=table_sems.num_carte WHERE "
					+ "(table_sems.choix_1='S"+i+"' OR table_sems.choix_2='S"+i+"') AND "
					+ "table_sems.mention='"+mention+"' "
					
					+ " ORDER BY table_maths.nom_et "
					+ " OFFSET "+x+" ROWS FETCH FIRST "+(y-x)+" ROW ONLY;");
			while(rset.next()) {
				model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
						rset.getString("nom_et")+" "+rset.getString("prenom_et"),
						" "," "});
			}
			rset.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		pan.add(scroll);
		pan.setVisible(true);
		return model;
	}

	@SuppressWarnings("serial")
	public static DefaultTableModel liste (String mention,String parcours,String sems,int i,int k,DefaultTableModel model,String ue,
			JPanel pan,int x,int y) {

		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		pan.removeAll();
		pan.setVisible(false);
		model=new DefaultTableModel() {
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		String databaMatiere="";
		String parc[]= {};
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			databaMatiere=databaseMatiereMaths;
			parc=parcMaths;
			break;
		}
		String matier[]=new String[compteEc(mention,k,ue,sems)];
		int j=0;
		try {   
			String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"' AND nom_ue='"+ue+"'ORDER BY nom_ec");
			while(rset1.next()) {
				matier[j]=rset1.getString("nom_ec");
				j++;
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addColumn("N�");
		model.addColumn("N�Carte");
		model.addColumn("Nom et pr�nom");
		for(int p=0;p<matier.length;p++) {
			model.addColumn(Methode.finit(matier[p],k));
			model.addColumn("");
		}
		JTable table=new JTable(model);
		JScrollPane scroll=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setPreferredScrollableViewportSize(new Dimension(700,500));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		for(int p=0;p<model.getColumnCount();p++)
			table.getColumnModel().getColumn(p).setCellRenderer(centerRenderer);
		
		if(i==1) {
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(""
						+ "SELECT * FROM table_maths INNER JOIN "
						+ "table_sems ON table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_sems.choix_1='S"+i+"' OR table_sems.choix_2='S"+i+"') AND "
						+ "table_sems.mention='"+mention+"' "
						+ " ORDER BY table_maths.nom_et "
						+ " OFFSET "+x+" ROWS FETCH FIRST "+(y-x)+" ROW ONLY;");
				while(rset.next()) {
					switch(matier.length) {
					case 1:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "});
						break;
					case 2:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "," "," "});
						break;
					case 3:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," ","","","",""});
						break;
					case 4:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," ","","","","","",""});
						break;
					case 5:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "," "," ","","","","","",""});
						break;
					}

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else {
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery(""
						+ "SELECT * FROM table_maths INNER JOIN "
						+ "table_sems ON table_maths.num_carte=table_sems.num_carte WHERE "
						+ "(table_maths.filier_et='"+parcours+"') AND"
						+ "(table_sems.choix_1='S"+i+"' OR table_sems.choix_2='S"+i+"') AND "
						+ "table_sems.mention='"+mention+"' "
						+ " ORDER BY table_maths.nom_et "
						+ " OFFSET "+x+" ROWS FETCH FIRST "+(y-x)+" ROW ONLY;");
				while(rset.next()) {
					switch(matier.length) {
					case 1:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "});
						break;
					case 2:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "," "," "});
						break;
					case 3:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," ","","","",""});
						break;
					case 4:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," ","","","","","",""});
						break;
					case 5:
						model.addRow(new Object[] {x+model.getRowCount()+1,rset.getString("num_carte"),
								rset.getString("nom_et")+" "+rset.getString("prenom_et"),
								" "," "," "," ","","","","","",""});
						break;
					}

				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		pan.add(scroll);
		pan.setVisible(true);
		return model;
	}

	public static DefaultTableModel  statRenseignement(DefaultTableModel model,String mention ) {
		mess.setText("Importation des Nombres des Etudiants Admis en"+mention+"");
		int nbr=0;
		model.setRowCount(1);
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM tab_etudiant WHERE "
					+ "tab_etudiant.mention='"+mention+"' AND tab_etudiant.niveau='T.C.S.V'";

			rset=statement.executeQuery(querry);

			while(rset.next()) {
				nbr++;
			}
			model.addRow(new Object[] {"<html><p text-align:center>MATHEMATIQUES&nbsp;<br/>ET&nbsp;<br/> APPLICATION</p><html>","L1",nbr,nbr,"100000"});
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	/**
	 *Sauvegarde de done vers excel 
	 **/

	public static Map<String ,Object[]> sauvegarde() {
		Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_maths ";
			int i=2;
			rset=statement.executeQuery(querry);
			data.put("1",new Object[] {"Num Carte","Nom","Pr�nom","Date de Naissance","Lieu de naissance","Adresse",
					"Fili�re","Sexe","Nationalit�","CIN/Passport","date C.I.N","Lieu C.I.N","Montant","Num Quintance",
					"Date Quintance","Etat","Photo","bacc"
			});
			while(rset.next()) {
				data.put(""+i+"",new Object[] {rset.getString(1),rset.getString(2),rset.getString(3),
						Methode.dateTextInt(rset.getString(4)),
						rset.getString(5),rset.getString(6),rset.getString(7),rset.getString(8),
						rset.getString(9),rset.getString(10),Methode.dateTextInt(rset.getString(11)),rset.getString(12),
						rset.getString(13),rset.getString(14),Methode.dateTextInt(rset.getString(15)),rset.getString(16),
						rset.getString(17),rset.getString(18)});
				i++;
			}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Map<String ,Object[]> sauvegardeInsc() {
		Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM tab_etudiant ";
			int i=2;
			rset=statement.executeQuery(querry);
			data.put("1",new Object[] {"Num Insc","Nom","Pr�nom","Date de Naissance","Lieu de naissance","Nationalit�",
					"Sexe","situation","num tel","CIN/Passport","date C.I.N","Lieu C.I.N","serie Bacc","Anne Bacc","Num Bacc",
					"Centre Bacc","Addres etudiant","Profession","Nom Pere","Profession","Nom mere","Profession",
					"Adress parent","Photo","Montant","num quit","Date Quintance","Niveau","Parcours","Mention"
			});
			while(rset.next()) {
				data.put(""+i+"",new Object[] {rset.getString(1),rset.getString(2),rset.getString(3)
						,Methode.dateTextInt(rset.getString(4)),
						rset.getString(5),rset.getString(6),rset.getString(7),rset.getString(8),
						rset.getString(9),rset.getString(10),Methode.dateTextInt(rset.getString(11)),rset.getString(12),
						rset.getString(13),rset.getString(14),rset.getString(15),rset.getString(16),
						rset.getString(17),rset.getString(18),rset.getString(19),rset.getString(20)
						,rset.getString(21),rset.getString(22),rset.getString(23),rset.getString(24)
						,rset.getString(25),rset.getString(26),Methode.dateTextInt(rset.getString(27)),rset.getString(28)
						,rset.getString(29),rset.getString(23)});
				i++;
			}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Map<String ,Object[]> sauvegarde_sems() {
		Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_sems ";
			int i=2;
			rset=statement.executeQuery(querry);
			data.put("1",new Object[] {"Num Carte","choix_1","choix_2","Mention","diplome"});
			while(rset.next()) {
				data.put(""+i+"",new Object[] {rset.getString(1),rset.getString(2),rset.getString(3),rset.getString(4)
						,rset.getString(5)});
				i++;
			}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Map<String ,Object[]> sauvegarde_validations() {
		Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_valid_sems ";
			int i=2;
			rset=statement.executeQuery(querry);
			data.put("1",new Object[] {"Num Carte","Semestre1","Semestre2","Semestre3","Semestre4",
					"Semestre5","Semestre6","Semestre7","Semestre8","Semestre9","Semestre10"});
			while(rset.next()) {
				data.put(""+i+"",new Object[] {rset.getString(1),rset.getString(2),rset.getString(3),rset.getString(4)
						,rset.getString(5),rset.getString(6),rset.getString(7),rset.getString(8)
						,rset.getString(9),rset.getString(10),rset.getString(11)});
				i++;
			}

			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	public static DefaultTableModel importDonneCarteParticulier(String num,DefaultTableModel model) {
		try {
			String url = "jdbc:postgresql://"+port+":5432/"+database;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement();
			String querry="SELECT * FROM table_maths  WHERE num_carte='"+num+"'";

			rset=statement.executeQuery(querry);

			while(rset.next()) {
				model.addRow(new Object[] {rset.getString("num_carte"),
						rset.getString("nom_et")+" "+rset.getString("prenom_et")});
			}
			rset.close();
			statement.close();
			connection.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public static  void ajoutEt(String mention,int i,String filiere,int k) {
		String typ[]= {"normal","rattrapage","final"};	 
		String parc[]= {};
		String base="";
		switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			parc=parcMaths;
			base=databaseNoteMaths;
			break;
		}
		int p=0;
		if(i!=1) {
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery("SELECT table_maths.num_carte FROM table_maths INNER JOIN table_sems ON"
						+ " table_maths.num_carte = table_sems.num_carte WHERE"
						+ "(table_sems.choix_1='S"+i+"' OR table_sems.choix_2='S"+i+"') AND "
						+ "table_sems.mention='"+mention+"' AND "
						+ "table_maths.filier_et='"+filiere+"'"
						+ "ORDER BY table_maths.nom_et ");
				while(rset.next()) {
					p++;
					try {
						for(int x=0;x<typ.length;x++) {
							String url1= "jdbc:postgresql://"+DataBase.port+":5432/"+base;
							connection1 = DriverManager.getConnection(url1, username, password);      
							statement1 = connection1.createStatement(); 
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
									+ "num_exam,num_carte) VALUES ("+p+",'"+rset.getString("num_carte")+"')");
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"(num_carte)"
									+ " VALUES ('"+rset.getString("num_carte")+"')");
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_validation (num_carte)"
									+ " VALUES ('"+rset.getString("num_carte")+"')");
							statement1.close();
							connection1.close();
						}
					} catch (Exception e) {

					}
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			try {   
				String url = "jdbc:postgresql://"+port+":5432/"+database;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				rset = statement.executeQuery("SELECT table_maths.num_carte FROM table_maths INNER JOIN table_sems ON"
						+ " table_maths.num_carte = table_sems.num_carte WHERE"
						+ "(table_sems.choix_1='S"+i+"' OR table_sems.choix_2='S"+i+"') AND "
						+ "table_sems.mention='"+mention+"'"
						+ "ORDER BY table_maths.nom_et ");
				while(rset.next()) {
					p++;
					try {
						for(int x=0;x<typ.length;x++) {
							String url1= "jdbc:postgresql://"+DataBase.port+":5432/"+base;
							connection1 = DriverManager.getConnection(url1, username, password);      
							statement1 = connection1.createStatement(); 
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_"+typ[x]+"("
									+ "num_exam,num_carte) VALUES ("+p+",'"+rset.getString("num_carte")+"')");
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_"+typ[x]+"(num_carte)"
									+ " VALUES ('"+rset.getString("num_carte")+"')");
							statement1.executeUpdate(" INSERT INTO S"+i+"_"+parc[k]+"_ue_validation(num_carte)"
									+ " VALUES ('"+rset.getString("num_carte")+"')");
							statement1.close();
							connection1.close();
						}
					} catch (Exception e) {

					}
				}
				rset.close();
				statement.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
public static String ajout_SN(double a) {
	if(a>=10) {
		return "SN";
	}
	else {
		return "null";
	}
	
}
public static String ajout_SR(double a) {
	if(a>=10) {
		return "SR";
	}
	else {
		return "null";
	}
	
}
public static void ajoutColonne(JPanel pan,String mention,int i,int k,String type) {
	String base="";
	String parc[]= {};
	String column="";
	switch(mention) {
	case "MATHEMATIQUES ET APPLICATIONS":
		base=databaseNoteMaths;;
		parc=parcMaths;
		break;
	}
	try {
			String url = "jdbc:postgresql://"+port+":5432/"+base;
			connection = DriverManager.getConnection(url, username, password);      
			statement = connection.createStatement(); 
			rset=statement.executeQuery("SELECT * FROM S"+i+"_"+parc[k]+"_ue_"+type+"");
			ResultSetMetaData rsm=rset.getMetaData();
			int col=rsm.getColumnCount();
			column=rsm.getColumnName(col);
			rset.close();
			statement.close();
			connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	if(!column.equals("moyenne")) {
		try {  
			String g[]= {"credit","moyenne"};
			for(int h=0;h<g.length;h++) {
				String url = "jdbc:postgresql://"+port+":5432/"+base;
				connection = DriverManager.getConnection(url, username, password);      
				statement = connection.createStatement(); 
				statement.executeUpdate(" ALTER  TABLE S"+i+"_"+parc[k]+"_ue_"+type+" ADD COLUMN "
						+ ""+g[h]+" character varying ");
				statement.close();
				connection.close();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}else
		JOptionPane.showMessageDialog(pan, "Table S"+i+"_"+parc[k]+"_ue_"+type+" d�j� modifier ",
				"",JOptionPane.DEFAULT_OPTION);
	
}

public static Map<String ,Object[]> sauvegarde_note_ue(String mention,int i,int k,String type,String sems) {
	Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
	String databaMatiere="";
	String base="";
	String parc[] = null;
	String a="";
	switch(mention) {
	case "MATHEMATIQUES ET APPLICATIONS":
		databaMatiere=databaseMatiereMaths;
		base=databaseNoteMaths;;
		parc=parcMaths;
		break;
	}
	try {   
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
				+ "tablename ='s"+i+"_"+parc[k]+"_ue_"+type+"' " );
		while(rset.next()) {
			a=rset.getString("tablename");
		}
	}catch(Exception e) {
		e.printStackTrace();
	}

	if(!a.equals("")) {
	int s=0;
	String g[]=new String[compteUe(mention,k,sems)];
	ArrayList<String>mat=new ArrayList<String>();
	ArrayList<String>mat1=new ArrayList<String>();
	String note[]=new String [g.length];
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(); 
		rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
				+ " semestre='"+sems+"'ORDER BY nom_ue");
		mat.add("num_carte");
		while(rset.next()) {
			g[s]=rset.getString(1)+"";
			mat.add(rset.getString(1)+"");
			s++;
		}
		rset.close();
		statement.close();
		connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}

	mat.add("Credit");
	mat.add("Moyenne");	
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement();
		String querry="SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" ORDER BY num_carte ";
		int j=2;
		rset=statement.executeQuery(querry);
		
		Object[] dat= mat.toArray();
		data.put("1",dat);

		while(rset.next()) {
			String num=rset.getString("num_carte");
			String crdt=rset.getString("credit");
			String mye=rset.getString("moyenne");;
			mat1.add(num);
			for(int r=0;r<note.length;r++) {
				mat1.add(rset.getString(g[r]));
			}
			mat1.add(crdt);
			mat1.add(mye);
			Object []dat1=mat1.toArray();
				data.put(""+j+"",dat1);
			j++;
			mat1.clear();
		}

		rset.close();
		statement.close();
		connection.close();

	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	return data;
}


public static Map<String ,Object[]> sauvegarde_note_ec(String mention,int i,int k,String type,String sems){
	Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
	String databaMatiere="";
	String base="";
	String parc[] = null;
	String a="";
	switch(mention) {
	case "MATHEMATIQUES ET APPLICATIONS":
		databaMatiere=databaseMatiereMaths;
		base=databaseNoteMaths;;
		parc=parcMaths;
		break;
	}
	try {   
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
				+ "tablename ='s"+i+"_"+parc[k]+"_"+type+"' " );
		while(rset.next()) {
			a=rset.getString("tablename");
		}
	}catch(Exception e) {
		e.printStackTrace();
	}

	if(!a.equals("")) {
	int s=0;
	String g[]=new String[compteEc(mention,k,sems)];
	ArrayList<String>mat=new ArrayList<String>();
	ArrayList<String>mat1=new ArrayList<String>();
	String note[]=new String [g.length];
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(); 
		rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
				+ " semestre='"+sems+"'ORDER BY nom_ue");
		mat.add("num_carte");
		while(rset.next()) {
			g[s]=rset.getString(1)+"";
			mat.add(rset.getString(1)+"");
			s++;
		}
		rset.close();
		statement.close();
		connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement();
		String querry="SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" ORDER BY num_carte ";
		int j=2;
		rset=statement.executeQuery(querry);
		
		Object[] dat= mat.toArray();
		data.put("1",dat);

		while(rset.next()) {
			String num=rset.getString("num_carte");
			mat1.add(num);
			for(int r=0;r<note.length;r++) {
				mat1.add(rset.getString(g[r]));
			}
			Object []dat1=mat1.toArray();
				data.put(""+j+"",dat1);
			j++;
			mat1.clear();
		}
		rset.close();
		statement.close();
		connection.close();

	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	return data;
}

public static Map<String ,Object[]> sauvegarde_note(String mention,int i,int k,String type,String sems){
	Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
	String databaMatiere="";
	String base="";
	String parc[] = null;
	String a="";
	switch(mention) {
	case "MATHEMATIQUES ET APPLICATIONS":
		databaMatiere=databaseMatiereMaths;
		base=databaseNoteMaths;;
		parc=parcMaths;
		break;
	}
	try {   
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rset = statement.executeQuery(" SELECT * FROM pg_catalog.pg_tables WHERE  "
				+ "tablename ='s"+i+"_"+parc[k]+"_"+type+"' " );
		while(rset.next()) {
			a=rset.getString("tablename");
		}
	}catch(Exception e) {
		e.printStackTrace();
	}

	if(!a.equals("")) {
	int s=0;
	String h[]=new String[compteUe(mention,k,sems)];
	String f[]=new String[compteEc(mention,k,sems)];
	ArrayList<String>mat=new ArrayList<String>();
	ArrayList<String>mat1=new ArrayList<String>();
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(); 
		rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
				+ " semestre='"+sems+"'ORDER BY nom_ue");
		mat.add("NUMERO CARTE");
		while(rset.next()) {
			h[s]=rset.getString("nom_ue")+"";
			s++;
		}
		rset.close();
		statement.close();
		connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	String num="";
	s=0;
	int j=2;
	for(int t=0;t<h.length;t++){
		mat.add("UE_"+Methode.finit(h[t],i)+"");
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"' AND nom_ue='"+h[t]+"'ORDER BY nom_ec");
			while(rset1.next()){
				 mat.add("EC_"+Methode.finit(rset1.getString("nom_ec"),i));
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		}
	mat.add("CREDIT");
	mat.add("MOYENNE");
	Object []dat=mat.toArray();
	data.put("1",dat);
	mat.clear();
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+base;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement();
		String querry="SELECT * FROM s"+i+"_"+parc[k]+"_ue_"+type+" ORDER BY num_carte ";
		rset = statement.executeQuery(querry);
		while(rset.next()) {
			num=rset.getString("num_carte");
			mat1.add(rset.getString("num_carte")+"");
			for(int t=0;t<h.length;t++) {
			mat1.add(rset.getString(h[t])+"");
			try {
				String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
				connection1 = DriverManager.getConnection(url1, username, password);      
				statement1 = connection1.createStatement(); 
				rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
						+ "semestre='"+sems+"' AND nom_ue='"+h[t]+"'ORDER BY nom_ec");
				while(rset1.next()){
					 f[s]=rset1.getString("nom_ec");
					 	try {
							connection2 = DriverManager.getConnection(url, username, password);      
							statement2 = connection2.createStatement();
							String querry1="SELECT * FROM s"+i+"_"+parc[k]+"_"+type+" WHERE num_carte='"+num+"'";
							rset2 = statement2.executeQuery(querry1);
							while(rset2.next()){
								mat1.add(rset2.getString(f[s])+"");
							  }
							rset2.close();
							statement2.close();
							connection2.close();
						}catch(Exception e){
							e.printStackTrace();
						}
				}
				rset1.close();
				statement1.close();
				connection1.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			}

			mat1.add(rset.getString("credit")+"");
			mat1.add(rset.getString("moyenne")+"");
			Object []dat1=mat1.toArray();
			data.put(""+j+"",dat1);
			j++;
			mat1.clear();
		}
		rset.close();
		statement.close();
		connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
		return data;
}

public static Map<String ,Object[]> sauvegarde_matier(String mention,int i,int k,String sems){
	Map<String ,Object[]> data=new TreeMap<String ,Object[]> ();
	String databaMatiere="";
	String parc[] = null;
	switch(mention) {
	case "MATHEMATIQUES ET APPLICATIONS":
		databaMatiere=databaseMatiereMaths;
		parc=parcMaths;
		break;
	}
	
	int s=0;
	String h[]=new String[compteUe(mention,k,sems)];
	ArrayList<String>mat=new ArrayList<String>();
	try {
		String url = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
		connection = DriverManager.getConnection(url, username, password);      
		statement = connection.createStatement(); 
		rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE "
				+ " semestre='"+sems+"'ORDER BY nom_ue");
		while(rset.next()) {
			h[s]=rset.getString("nom_ue")+"";
			s++;
		}
		rset.close();
		statement.close();
		connection.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	int j=2;
	for(int t=0;t<h.length;t++){
		mat.add(h[t]+"");
		try {
			String url1 = "jdbc:postgresql://"+port+":5432/"+databaMatiere;
			connection1 = DriverManager.getConnection(url1, username, password);      
			statement1 = connection1.createStatement(); 
			rset1 = statement1.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE "
					+ "semestre='"+sems+"' AND nom_ue='"+h[t]+"'ORDER BY nom_ec");
			while(rset1.next()){
				 mat.add(rset1.getString("nom_ec"));
			}
			rset1.close();
			statement1.close();
			connection1.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		Object []dat=mat.toArray();
		data.put(""+j+"",dat);
		j++;
		mat.clear();
		}
		return data;
	}
}



