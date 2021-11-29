package panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data_base.DataBase;
import image.LoadImage;
import loading.Main;
import loading.Methode;
import resultat.FenetreResultat;

@SuppressWarnings("serial")
public class PanelImport extends JTabbedPane {
	DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
	public static JFileChooser filechooser = new JFileChooser();

	File file=null;
	String mention;
	String type;
	String sems[];
	String titreValidation[]= {"Num Carte","S1","S2","S3","S4","S5","S6","S7","S8","S9","S10"};
	String parcours;
	String titreTable[]= {"N°Carte","Nom ","prénom","Date de Naissance","Lieu de Naissance","Adresse","Filiére","Sexe",
						  "Nationalité","CIN","Date CIN","Lieu CIN","Montant","Num quit","Date quit","Etat","Photo","Bacc"};
	
	String titreSemsetre[]= {"N°Carte","choix_1 ","choix_2","Mention","Diplome",};
	int nbrSems;
	int t;
	int debutSems;
	JPanel panelDonne[]=new JPanel[1];
	JPanel panelSemestre=new JPanel();
	JPanel panelTable=new JPanel();
	JPanel panelFerme;
	JTable table;
	JTable tableSemstre;
	JTable tableValidations;
	DefaultTableModel model;
	DefaultTableModel modelSemestre;
	DefaultTableModel modelValidation;
	JButton imports;
	JButton fen;
	JButton sauv;

	JButton donne;
	JButton semstre;
	
	JButton liste;
	JDialog dial;
	String post;
	public PanelImport(String mention,int nbrSems,int t,int debutSems,String type,String sems[],
			String parcours,String post) {
		this.mention=mention;
		this.nbrSems=nbrSems;
		this.t=t;
		this.debutSems=debutSems;
		this.type=type;
		this.sems=sems;
		this.parcours=parcours;
		this.post=post;
		
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		
			imports=new JButton( LoadImage.transformeb(100, 25, "/importer.jpg"));
			fen=new JButton( LoadImage.transformeb(130, 25, "/fen.jpg"));
			sauv=new JButton(LoadImage.transformeb(130, 25, "/sauvegarder.jpg"));
			donne=new JButton(LoadImage.transformeb(130, 25, "/donnee.jpg"));
			semstre=new JButton(LoadImage.transformeb(130, 25, "/semestre.jpg"));
			
			imports.setPreferredSize(new Dimension(130,25));
			fen.setPreferredSize(new Dimension(130,25));
			sauv.setPreferredSize(new Dimension(130,25));
			donne.setPreferredSize(new Dimension(130,25));
			semstre.setPreferredSize(new Dimension(130,25));
			
			liste=new JButton("List.Examen");
			panelFerme=new JPanel();
			panelFerme.setBackground(Color.cyan);
			panelFerme.setPreferredSize(new Dimension(150,490));
			panelFerme.add(imports);
			panelFerme.add(fen);
			panelFerme.add(sauv);
			panelFerme.add(donne);
			panelFerme.add(semstre);
			panelFerme.add(liste);
			
			model=new DefaultTableModel();
			for (int j=0; j<titreTable.length;j++) 
				model.addColumn(titreTable[j]);
			
			modelSemestre=new DefaultTableModel();
			for (int j=0; j<titreSemsetre.length;j++) 
				modelSemestre.addColumn(titreSemsetre[j]);
			
			modelValidation=new DefaultTableModel(){
				public Class <?>getColumnClass(int Column) {
					if(Column!=0)
						return Boolean.class;
					else
						return String.class;
				}
				public boolean isCellEditable(int row, int col){
				return true;
				}
			};
			for (int j=0; j<titreValidation.length;j++) 
				modelValidation.addColumn(titreValidation[j]);
			
			panelFerme.add(liste);
			imports.setPreferredSize(new Dimension(100,25));
			table = new JTable(model);
			tableSemstre = new JTable(modelSemestre);
			tableValidations= new JTable(modelValidation);
			
			if(type=="normal" || type=="rattrapage") {
				fen.setVisible(true);
				liste.setVisible(true);
			}else {
				fen.setVisible(false);
				liste.setVisible(false);
			}
			
			table.setPreferredScrollableViewportSize(new Dimension(100*model.getColumnCount(),470));
			table.setFillsViewportHeight(true);
			
			tableSemstre.setPreferredScrollableViewportSize(new Dimension(100*modelSemestre.getColumnCount(),470));
			tableSemstre.setFillsViewportHeight(true);
			
			tableValidations.setPreferredScrollableViewportSize(new Dimension(80*modelValidation.getColumnCount(),470));
			tableValidations.setFillsViewportHeight(true);
			
			
			panelFerme.setLayout(new FlowLayout(FlowLayout.LEADING));
			panelDonne[0]=new JPanel();
			panelDonne[0].add(panelFerme);
			
			for (int h=0; h<model.getColumnCount();h++)
				if(h!=1)
				table.getColumnModel().getColumn(h).setCellRenderer(centerRenderer);
			
			panelTable.add(new JScrollPane(table));
			panelSemestre.add(new JScrollPane(tableSemstre));
			panelSemestre.add(new JScrollPane(tableValidations));
			panelDonne[0].add(panelTable);
			panelDonne[0].add(panelSemestre);
			panelSemestre.setVisible(false);
			panelDonne[0].setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		int i = debutSems;
		for(JPanel pan : panelDonne){
		//Méthode d'ajout d'onglet
		this.add("SEMESTRE "+(++i), pan);
		}
		for(int j=0;j<nbrSems;j++) {
		
		imports.addActionListener(new Actualiser(model));
		fen.addActionListener(new Saisie());
		sauv.addActionListener(new sauvegarde(j));
		liste.addActionListener(new Liste());
		
		donne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelTable.setVisible(true);
				panelSemestre.setVisible(false);
			}
		});
		
		semstre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelTable.setVisible(false);
				panelSemestre.setVisible(true);
			}
		});
	}
	}	
	
	public class Actualiser implements ActionListener{
        int i;
        DefaultTableModel model;
        
       Actualiser(DefaultTableModel model){
        	this.model=model;
        }
		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.setRowCount(0);
			modelSemestre.setRowCount(0);
			modelValidation.setRowCount(0);
			try {
				filechooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return "fichier xlsx";
					}
					@Override
					public boolean accept(File f) {
						return f.getName().toLowerCase().endsWith(".xlsx") || f.isDirectory();
					}
				});
				int retval = filechooser.showOpenDialog(Main.main);
				if (retval == JFileChooser.APPROVE_OPTION) {
					Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					file = filechooser.getSelectedFile();
				}
				String input = file.getAbsolutePath();
				FileInputStream inputStream = new FileInputStream(file);
				  Workbook workbook = null;
				// Find the file extension by splitting file name in substring and getting only extension name
				  String fileExtensionName = input.substring(input.indexOf("."));
				// Check condition if the file is xlsx file
				  if (fileExtensionName.equals(".xlsx")) {
				// If it is xlsx file then create object of XSSFWorkbook class
				   workbook = new XSSFWorkbook(inputStream);
				}
				// Check condition if the file is xls file
				  else if (fileExtensionName.equals(".xls")) {
				// If it is xls file then create object of HSSFWorkbook class
				   workbook = new HSSFWorkbook(inputStream);
				}
				// Read sheet inside the workbook by its name
				  Sheet sheet1 = workbook.getSheet("Donnée");
				  Sheet sheet2 = workbook.getSheet("Semestre");
				  Sheet sheet3 = workbook.getSheet("Validation");
				// Find number of rows in excel file
				  int rowCount = sheet1.getLastRowNum() - sheet1.getFirstRowNum();
				  int rowCount2 = sheet2.getLastRowNum() - sheet2.getFirstRowNum();
				  int rowCount3 = sheet3.getLastRowNum() - sheet2.getFirstRowNum();
				// Create a loop over all the rows of excel file to read it
				  DataFormatter fmt=new DataFormatter();
				  for (int i = 0; i < rowCount + 1; i++) {

				   Row row = sheet1.getRow(i);
				   if(i>0) {
						   model.addRow(new Object[] {
							fmt.formatCellValue(row.getCell(0)),fmt.formatCellValue(row.getCell(1)).toUpperCase(),
						    Methode.finitText(fmt.formatCellValue(row.getCell(2))),
						    fmt.formatCellValue(row.getCell(3)),fmt.formatCellValue(row.getCell(4)),fmt.formatCellValue(row.getCell(5)),
							fmt.formatCellValue(row.getCell(6)),fmt.formatCellValue(row.getCell(7)),fmt.formatCellValue(row.getCell(8)),
							fmt.formatCellValue(row.getCell(9)),fmt.formatCellValue(row.getCell(10)),fmt.formatCellValue(row.getCell(11)),
							fmt.formatCellValue(row.getCell(12)),fmt.formatCellValue(row.getCell(13)),fmt.formatCellValue(row.getCell(14)),
							fmt.formatCellValue(row.getCell(15)),fmt.formatCellValue(row.getCell(16)),fmt.formatCellValue(row.getCell(17))});
				 
				   }
				 }
				  
				  for (int i = 0; i < rowCount2 + 1; i++) {

					   Row row = sheet2.getRow(i);
					   if(i>0) {
							   modelSemestre.addRow(new Object[] {
										fmt.formatCellValue(row.getCell(0)),fmt.formatCellValue(row.getCell(1)),
									    fmt.formatCellValue(row.getCell(2)),fmt.formatCellValue(row.getCell(3)),
									    fmt.formatCellValue(row.getCell(4)),fmt.formatCellValue(row.getCell(5))
									    });
					   }
					 }
				  
				  for (int i = 0; i < rowCount3 + 1; i++) {

					   Row row = sheet3.getRow(i);
					   if(i>0) {
							   try {
								   modelValidation.addRow(new Object[] {
											fmt.formatCellValue(row.getCell(0)),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(1))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(2))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(3))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(4))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(5))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(6))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(7))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(8))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(9))),
											Boolean.parseBoolean(fmt.formatCellValue(row.getCell(10)))
										    
										    });
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panelDonne[0], "Invalide valeur du Tableau ", "ERREUR",
										JOptionPane.ERROR_MESSAGE);
							}
							  
					   }
					 }
			} catch (Exception e) {
			}
			  Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	
		}
		
	}
	
	public class Saisie implements ActionListener{ 
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new FenetreNote(Main.main," NOTE ", true,mention,t,type,parcours,nbrSems,debutSems,sems,post).setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	
	public class Result implements ActionListener{ 
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new FenetreResultat(Main.main," RESULTAT ", true,mention,t,type,parcours,nbrSems,debutSems,sems,post).setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	public class sauvegarde implements ActionListener{
		int k;
		public sauvegarde(int k) {
			this.k=k;
		}
	
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try {
				DataBase.reset();
					  for (int i = 0; i < model.getRowCount(); i++) {
							   DataBase.ajout_etudiant_math(
								(String)model.getValueAt(i, 0),
								((String)model.getValueAt(i, 1)).toUpperCase(),
							    Methode.finitText((String)model.getValueAt(i, 2)),
								Methode.dateText((String)model.getValueAt(i, 3)),
								(String)model.getValueAt(i, 4),
								(String)model.getValueAt(i, 5),
								(String)model.getValueAt(i, 9),
								Methode.dateText((String)model.getValueAt(1, 10)),
								(String)model.getValueAt(i, 11),
								(String)model.getValueAt(i, 12),
								(String)model.getValueAt(i, 13),
								Methode.dateText((String)model.getValueAt(1, 14)),
								(String)model.getValueAt(i, 15),
								(String)model.getValueAt(i, 6),
								(String)model.getValueAt(i, 0),
								(String)model.getValueAt(i, 7),
								(String)model.getValueAt(i, 8),
								Double.valueOf((String)model.getValueAt(1, 17)));
							   
							   DataBase.ajout_etudiant_math_parcours((String)modelSemestre.getValueAt(i, 0), 
									   (String)modelSemestre.getValueAt(i, 1),
									   (String)modelSemestre.getValueAt(i, 2),
									   mention);
							   
							   String sems[]= {
									   String.valueOf(modelValidation.getValueAt(i, 1)),
									   String.valueOf(modelValidation.getValueAt(i, 2)),
									   String.valueOf(modelValidation.getValueAt(i, 3)),
									   String.valueOf(modelValidation.getValueAt(i, 4)),
									   String.valueOf(modelValidation.getValueAt(i, 5)),
									   String.valueOf(modelValidation.getValueAt(i, 6)),
									   String.valueOf(modelValidation.getValueAt(i, 7)),
									   String.valueOf(modelValidation.getValueAt(i, 8)),
									   String.valueOf(modelValidation.getValueAt(i, 9)),
									   String.valueOf(modelValidation.getValueAt(i, 10)),
							   };
							   DataBase.import_Semsetre((String)modelSemestre.getValueAt(i, 0),sems);
					  }
					  
					  JOptionPane.showMessageDialog(Main.main, "les étudiants sont bien ajoutés ", "",
								JOptionPane.INFORMATION_MESSAGE);
					  
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(Main.main, "Erreur Inconnue", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
					
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	
	public String semsPetit(String num,int k){
		String sems1=DataBase.select_choix1(num);
		String sems;
		int petit=0;
		String valeur=sems1.substring(sems1.lastIndexOf("S")+1,sems1.length());
		if(sems1.equals("")) {
	
		}else {
			petit=Integer.parseInt(valeur);
		}
		if(petit!=0){
			if(petit<k){
				sems="S"+petit;
			}else {
				sems="S"+k;
			}
		}else {
			sems="";
		}
		//System.out.println(sems);
		return sems;	
	}
	
	public String semsGrand(String num,int k){
		String sems1=DataBase.select_choix2(num);
		String sems;
		
		int petit=0;
		if(sems1.equals("")){
	
		}else {
			String valeur=sems1.substring(sems1.lastIndexOf("S")+1,sems1.length());
			petit=Integer.parseInt(valeur);
		}
		if(petit!=0) {
			if(petit>k){
				sems="S"+petit;
			}else{
				sems="S"+k;
			}
		}else {
			sems="S"+k;
		}
		System.out.println(sems);
		return sems;		
	}
	
	public class Liste implements ActionListener{ 
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new FenetreListeAuExamen(Main.main," RESULTAT ", true,mention,t,type,parcours,nbrSems,debutSems,sems,post).setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}

}
