package gest_etudiant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import panel.Panneau;
import data_base.DataBase;
import image.LoadImage;
import panel.PanelBourseR;
import table.TableModel;
import image.ImageBonneQualite;


public class BourseMathsR {

	public static Panneau  panelPrincipale1[];
	public static JLabel logo2 [];
	public static JPanel  panelPrincipale2[];
	public static JPanel panel[];
	public static JPanel panelList[];
	public static JLabel titre[];
	public static JLabel titre1[];
	public static JPanel panelTable[];
	public static JPanel titlePanel[];

	
	public static JScrollPane scrollPane1;
	
	

	
	public static JButton acttcm[];
	public static JButton actmiss[];
	public static JButton actme[];
	public static JButton actmf[];
	
	
	public static JTable table[];
	public static JTable tableParc[];
	
	
	public static String semestre[]= {"L1","L2","L3","M1","M2"};
	
	public static DefaultTableModel modeltable[];
	public static DefaultTableModel modelParc[];

	
	public static Font font=new Font("arial",Font.BOLD,24);
	public static JTabbedPane panelPrincipale= new JTabbedPane();
	public static JScrollPane scrollPane;
	
	public static String titreTable[]= {"N°","N° Carte","Nom et Prenom "};
	public static String titleParc[]= {"PARCOUR"};
		
		
	public static void ajouter() {
			
			acttcm=new JButton[1];
			actmiss=new JButton[5];
			actme=new JButton[5];
			actmf=new JButton[5];
			
			table=new JTable[5];
			tableParc= new JTable[5];
			modelParc= new DefaultTableModel[5];
			modeltable= new DefaultTableModel[5];
			
			panelPrincipale1=new Panneau[5];
			panelPrincipale2=new JPanel[5];
			panel=new JPanel[5];
			
			panelTable=new JPanel[5];
			panelList=new JPanel[5];
			titre=new JLabel[5];
			titre1=new JLabel[5];
			titlePanel=new JPanel[5];
			logo2=new JLabel[5];
			
		for(int i=0;i<5;i++) {
			
			actmiss[i]=new JButton();
			panelPrincipale1[i]=new Panneau("/fac.jpg");
			panelPrincipale2[i]=new JPanel();
			panel[i]=new JPanel();
			panelList[i]=new JPanel();
			titlePanel[i]=new JPanel();
			
			
			titre[i]=new JLabel("LISTE DES ETUDIANTS BOURSSIER REDOUBLANT EN L"+(i+1)+" ");
			titre1[i]=new JLabel("LISTE DES ETUDIANTS BOURSSIER REDOUBLANT EN M"+(i-2)+" ");
			titre[i].setFont(font);
			titre1[i].setFont(font);
            logo2[i]= new JLabel();
			logo2[i].setIcon(ImageBonneQualite.image("C:/Program Files (x86)/JDev/DataSciences/image/logoFac.png",40));
			logo2[i].setPreferredSize(new Dimension(40,40));


			modelParc[i]=new TableModel(titleParc);
			tableParc[i]=new JTable(modelParc[i]);
			tableParc[i].setPreferredScrollableViewportSize(new Dimension(230,300));
			tableParc[i].setFillsViewportHeight(true);

			
			
				if(i<3) {
				//actmiss[i]=new JButton( LoadImage.transformeb(100, 25, "/actualiser.jpg"));	
				modeltable[i]=new TableModel(titreTable);
				//modelmiss[i]=DataBase.importDonneBourse(modelmiss[i], "MATHEMATIQUES ET APPLICATIONS", semestre[i],"M.I.S.S");
			    table[i]=new JTable(modeltable[i]);
			    
				panelTable[i]= new PanelBourseR(semestre[i]+" M.I.S.S ",table[i],actmiss[i],modeltable[i]);
			    panelList[i].add(panelTable[i]); 
				actmiss[i].setVisible(false);
				
			
				titlePanel[i].add(logo2[i]);
				titlePanel[i].add(titre[i]);
				
				}else {
					actmiss[i]=new JButton( LoadImage.transformeb(100, 25, "/actualiser.jpg"));	
					modeltable[i]=new TableModel(titreTable);
					//modelmiss[i]=DataBase.importDonneBourse(modelmiss[i], "MATHEMATIQUES ET APPLICATIONS", semestre[i],"M.I.S.S");
				    table[i]=new JTable(modeltable[i]);
					actmiss[i].setVisible(false);
				    
					panelTable[i]= new PanelBourseR(semestre[i]+" M.I.S.S ",table[i],actmiss[i],modeltable[i]);
					//actmiss[i].addActionListener(new Actualiser(modelmiss[i],semestre[i],"M.I.S.S"));
				    panelList[i].add(panelTable[i]); 
				    
				    
					titlePanel[i].add(logo2[i]);
					titlePanel[i].add(titre1[i]);

				}
			
			
			
				panel[i].setPreferredSize(new Dimension(300,630));
				panelList[i].setPreferredSize(new Dimension(600,630));
				titlePanel[i].setPreferredSize(new Dimension(910,45));
				titlePanel[i].setBackground(Color.white);	
				panelPrincipale2[i].setPreferredSize(new Dimension(910,680));
				panelPrincipale2[i].setBackground(Color.gray);

				panel[i].add(new JScrollPane(tableParc[i]));
				panelPrincipale2[i].add(titlePanel[i]);
				panelPrincipale1[i].add(panelPrincipale2[i]);
				panelPrincipale2[i].add(panel[i]);
				panelPrincipale2[i].add(panelList[i]);
			
			
		
		}
		
		JPanel tPan[]= panelPrincipale1;
		scrollPane = new JScrollPane(panelPrincipale);
		int i = 0;
		for(JPanel pan : tPan){
			if(i<3)
		        panelPrincipale.add("Niveau L"+(++i), pan);
			else
				panelPrincipale.add("Niveau M"+((++i)-3), pan);
	}
	}
	
	public static class Visible implements ActionListener {
		int i;
		JPanel pan;
		Visible(int i,JPanel pan){
			this.pan=pan;
			this.i=i;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			pan.setVisible(true);
		}
		
	}
	
	public static class Actualiser implements ActionListener {
		int i;
		DefaultTableModel model;
		String sems;
		String parc;
		Actualiser(DefaultTableModel model,String sems,String parc){
			this.model=model;
			this.sems=sems;
			this.parc=parc;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			model=DataBase.importDonneBourse(model, "MATHEMATIQUES ET APPLICATIONS", sems, parc);
		}
		
	}
	
}
