package gest_etudiant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data_base.DataBase;
import image.LoadImage;
import loading.Main;
import panel.FenetreListeAuExamen;
import panel.PanelList;
import table.TableModel;
import panel.Panneau;
import image.ImageBonneQualite;

public class ListeMaths {

	//public static JPanel panelPrincipale1[];
	public static JLabel logo2 [];
	public static Panneau  panelPrincipale1[];
	public static JPanel  panelPrincipale2[];
	public static JPanel panel[];
	public static JPanel titlePanel[];
	public static JPanel panelList[];
	public static JLabel titre[];
	public static JPanel panelTabletcm[];
	
	public static JScrollPane scrollPane1;
	public static JButton tcm[];
	public static JButton acttcm[];		
	public static JTable tabletcm[];
	public static JTable tableParc[];
	public static DefaultTableModel modelParc[];
	
	static String mention;
	static String type;
	static String sems[];
	static String parcours;
	static int nbrSems;
	static int t;
	static int debutSems;
	static String post;
	
	public static String semestre[]= {"Semestre1","Semestre2","Semestre3","Semestre4","Semestre5",
									"Semestre6","Semestre7","Semestre8","Semestre9","Semestre10"};
	
	public static DefaultTableModel modeltcm[]= new DefaultTableModel[10];
	
	public static Font font=new Font("arial",Font.BOLD,32);
	public static JTabbedPane panelPrincipale= new JTabbedPane();
	public static JScrollPane scrollPane;
	
	public static String titreTable[]= {"N°","N° Carte","Nom et Prenom "};
	public static String titleParc[]= {"PARCOUR"};
		
	public static void ajouter() {
			tcm=new JButton[10];		
			acttcm=new JButton[10];
			tabletcm=new JTable[10];
			tableParc= new JTable[10];
			modelParc= new DefaultTableModel[10];
			panelPrincipale1=new Panneau[10];
			panelPrincipale2=new JPanel[10];
			panel=new JPanel[10];
			titlePanel=new JPanel[10];
			panelTabletcm=new JPanel[10];
			panelList=new JPanel[10];
			titre=new JLabel[10];
			logo2=new JLabel[10];
			
		for(int i=0;i<10;i++) {
			//panelPrincipale1[i]=new JPanel();
			panelPrincipale1[i]=new Panneau("/fac.jpg");
			panelPrincipale2[i]=new JPanel();
			panel[i]=new JPanel();
			panelList[i]=new JPanel();
			titlePanel[i]=new JPanel();
			titre[i]=new JLabel("LISTE  DES ETUDIANTS INSCRIT EN S"+(i+1)+" ");
			titre[i].setFont(font);
            logo2[i]= new JLabel();
			logo2[i].setIcon(ImageBonneQualite.image("C:/Program Files (x86)/JDev/DataSciences/image/logoFac.png",40));
			logo2[i].setPreferredSize(new Dimension(40,40));

			modelParc[i]=new TableModel(titleParc);
			tableParc[i]=new JTable(modelParc[i]);
			tableParc[i].setPreferredScrollableViewportSize(new Dimension(230,300));
		//	tableParc[i].getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			tableParc[i].setFillsViewportHeight(true);
		//	tableParc[i].getColumnModel().getColumn(0).setPreferredWidth(190);
		//  tableParc[i].addMouseListener(new actue(i));
			
		
			acttcm[i]=new JButton( LoadImage.transformeb(100, 25, "/actualiser.jpg"));
			modeltcm[i]=new TableModel(titreTable);
			modeltcm[i]=DataBase.importDonneTable(modeltcm[i], "MATHEMATIQUES ET APPLICATIONS", "S"+(i+1),"T.C.M");
			tabletcm[i]=new JTable(modeltcm[i]);
			panelTabletcm[i]= new PanelList("S"+(i+1)+" T.C.M",tabletcm[i],acttcm[i],modeltcm[i]);


			panelList[i].add(panelTabletcm[i]);
			acttcm[i].setPreferredSize(new Dimension(100,25));
			acttcm[i].setVisible(false);
				
			
			panelPrincipale2[i].setPreferredSize(new Dimension(910,680));
			panelPrincipale2[i].setBackground(Color.gray);
			titlePanel[i].setPreferredSize(new Dimension(910,45));
			titlePanel[i].setBackground(Color.white);
			panel[i].setPreferredSize(new Dimension(300,630));
			//panel[i].setOpaque(false);
			panelList[i].setPreferredSize(new Dimension(600,630));
			//panelList[i].setOpaque(false);
			
			titlePanel[i].add(logo2[i]);
			titlePanel[i].add(titre[i]);
			panel[i].add(new JScrollPane(tableParc[i]));
			
			//panelList[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			//panelPrincipale1[i].setBackground(Color.white);
			panelPrincipale2[i].add(titlePanel[i]);
			panelPrincipale1[i].add(panelPrincipale2[i]);
			panelPrincipale2[i].add(panel[i]);
			panelPrincipale2[i].add(panelList[i]);
		
			
			
		
		}
		
		
		
		
		JPanel tPan[]= panelPrincipale1;
		scrollPane = new JScrollPane(panelPrincipale);
		int i = 0;
		for(JPanel pan : tPan){
		//M�thode d'ajout d'onglet
		panelPrincipale.add("SEMESTRE "+(++i), pan);
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
			model=DataBase.importDonneTable(model, "MATHEMATIQUES ET APPLICATIONS", sems, parc);
		}
		
	}
	
	public static class ImprimerListGroup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new FenetreListeAuExamen(Main.main," RESULTAT ", true,mention,t,type,parcours,nbrSems,debutSems,sems,post).setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}

	public  class actue implements MouseListener{
		int i;
		actue(int i){
			this.i=i;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
		 if(tableParc[i].getRowCount()>0 && tableParc[i].getSelectedRow()!=-1) {
				
			if(e.getClickCount()==2) {
			
				//ACTION IMPORT DONNER
			}
		 }
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
}
