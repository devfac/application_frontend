package gest_etudiant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

import data_base.DataBase;
import image.LoadImage;
import loading.Methode;
import panel.Panneau9;
import table.TableModel;

public class Droit {
	
	public static JFrame DroitFrame = new JFrame();
	
	public static JPanel panelPrincipale = new JPanel();
	public static JPanel panelCE = new JPanel();
	public static JPanel panelInfo = new JPanel();
	public static JScrollPane scroll;
	
	public static JLabel info= new JLabel("Liste Droit");
	public static JLabel vide= new JLabel();
	public static JLabel labDroit= new JLabel("Droit :");
	public static JLabel labNiveau = new JLabel("Niveau :");
	public static JLabel labParcours = new JLabel("Parcours :");
	public  static String typefilMaths[]= {"T.C.M","M.I.S.S","M.E","M.F"};
	public static  JComboBox<String> typefilCombMaths=new JComboBox<String>(typefilMaths);
	public static String niv[]= {"L1","L2","L3","M1","M2"};
	public static  JComboBox<String> comboNiveau=new JComboBox<String>(niv);
	
	public static JTextField txtDroit= new JTextField();
	
	public static JButton ajouter= new JButton(LoadImage.transformeb(100, 25, "/ajouter.jpg"));
	public static JButton actualiser= new JButton(LoadImage.transformeb(100, 25, "/actualiser.jpg"));
	public static JButton supprimer= new JButton(LoadImage.transformeb(100, 25, "/supprimer.jpg"));
	public static JButton modifier= new JButton(LoadImage.transformeb(100, 25, "/Modif.jpg"));
	public static JButton enregistrer= new JButton(LoadImage.transformeb(100, 25, "/save.jpg"));
	public static JButton annuler= new JButton(LoadImage.transformeb(100, 25, "/annul.jpg"));
	public static Panneau9 content = new  Panneau9("/fac3.jpg");
	
	public static String mention ; 

	public static String titreInfo[]= {"Parcours","Mention","Droit"};
	public static JTable tableInfo;
	public static DefaultTableModel model=new DefaultTableModel();

	public static void action() {

		panelCE.setPreferredSize(new Dimension(400,500));
		info.setPreferredSize(new Dimension(500,25));
		info.setHorizontalAlignment(JLabel.CENTER);
		panelInfo.setPreferredSize(new Dimension(400,480));
		content.setPreferredSize(new Dimension(500,500));
		panelPrincipale.setPreferredSize(new Dimension(500,600));		
		vide.setPreferredSize(new Dimension(170,1));
		txtDroit.setPreferredSize(new Dimension(100,20));
		comboNiveau.setPreferredSize(new Dimension(60,20));
		typefilCombMaths.setPreferredSize(new Dimension(60,20));
		
		ajouter.setPreferredSize(new Dimension(80,20));
		actualiser.setPreferredSize(new Dimension(90,20));
		supprimer.setPreferredSize(new Dimension(90,20));
		modifier.setPreferredSize(new Dimension(90,20));
		enregistrer.setPreferredSize(new Dimension(90,20));
		annuler.setPreferredSize(new Dimension(90,20));
		
		
       
		
		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		//model=new TableModel(titreInfo);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	  		
		model=new DefaultTableModel();
		model=new TableModel(titreInfo);
		tableInfo=new JTable(model);
		for (int c=0; c<3;c++) 
			
			tableInfo.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
		
			tableInfo.setPreferredScrollableViewportSize(new Dimension(400,300));
			tableInfo.setFillsViewportHeight(true);
			tableInfo.getColumnModel().getColumn(0).setPreferredWidth(40);
			tableInfo.getColumnModel().getColumn(1).setPreferredWidth(130);
			tableInfo.getColumnModel().getColumn(2).setPreferredWidth(40);
			scroll = new JScrollPane(tableInfo);
			

		panelCE.setBackground(Color.pink);
		panelCE.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelCE.add(labDroit);
		panelCE.add(txtDroit);		
		panelCE.add(labNiveau);
		panelCE.add(comboNiveau);
		panelCE.add(labParcours);
		panelCE.add(typefilCombMaths);
		panelCE.add(vide);
		
		
		panelCE.add(ajouter);
		panelCE.add(info);		
		panelCE.add(scroll);
		panelCE.add(actualiser);
		panelCE.add(supprimer);
		panelCE.add(modifier);
		panelCE.add(enregistrer);
		panelCE.add(annuler);
		//panelCE.add(panelInfo);
		supprimer.setVisible(false);
		enregistrer.setVisible(false);
		annuler.setVisible(false);
		panelPrincipale.setOpaque(false);

		panelCE.setOpaque(false);
		panelInfo.setOpaque(false);
		content.add(panelCE);
		panelPrincipale.add(content);
		
		
		
			
		
		ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (txtDroit.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez entrer le montant Droit s'il vous plaît!!!","ERREUR",0);

					
				}else {
				String nom=comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString();
				
				if(comboNiveau.getSelectedItem().equals("L1")&&(!typefilCombMaths.getSelectedItem().equals("T.C.M"))) {
				DataBase.ajout_droit("L1_T.C.M",mention,Double.parseDouble(txtDroit.getText()));
				}else {
					if((typefilCombMaths.getSelectedItem().equals("T.C.M"))&&(!comboNiveau.getSelectedItem().equals("L1"))) {
						
						JOptionPane.showMessageDialog(null, "T.C.M est seulement pour le L1 ","ERREUR",0);
					}else {
				
					DataBase.ajout_droit(nom,mention,Double.parseDouble(txtDroit.getText()));
				}}
			model=DataBase.importDonneDroit(model,comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString(),mention);
			txtDroit.setText("");
			}}
		});
		
       actualiser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				model=DataBase.importDonneDroit(model,
						comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString(),mention);
			}
		});
       supprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null,"Voulez-vous Supprimer "
						+Methode.getNom(tableInfo)+" "
								+ "?","ATTENTION!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
				if(option == JOptionPane.OK_OPTION){
					DataBase.delete_droit(Methode.getNom(tableInfo));
					model=DataBase.importDonneDroit(model,comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString(),mention);
					supprimer.setVisible(true);
					enregistrer.setVisible(false);
					annuler.setVisible(true);
					modifier.setVisible(true);
					actualiser.setVisible(true);
				
			}
				}
		});
       modifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				txtDroit.setEditable(true);
				enregistrer.setVisible(true);
				modifier.setVisible(false);
				annuler.setVisible(true);
				actualiser.setVisible(false);
				supprimer.setVisible(false);
				DataBase.recuperInfo_droit(Methode.getNom(tableInfo),txtDroit);
				
				
				
			}
		});
       annuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				tableInfo.clearSelection();
				txtDroit.setText("");
				model=DataBase.importDonneDroit(model,
						comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString(),mention);
				supprimer.setVisible(true);
				enregistrer.setVisible(false);
				modifier.setVisible(true);
				annuler.setVisible(false);
				actualiser.setVisible(true);
			}
		});
       enregistrer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null,"Voulez-vous modifier "
						+Methode.getNom(tableInfo)+" "
								+ "?","ATTENTION!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
				if(option == JOptionPane.OK_OPTION){
				DataBase.modifier_droit(Methode.getNom(tableInfo),Integer.parseInt( txtDroit.getText()));
				model=DataBase.importDonneDroit(model,
						comboNiveau.getSelectedItem().toString()+"_"+typefilCombMaths.getSelectedItem().toString(),mention);			
				txtDroit.setEditable(true);
				txtDroit.setText("");				
				enregistrer.setVisible(false);
				actualiser.setVisible(true);
				modifier.setVisible(true);
				annuler.setVisible(false);
				supprimer.setVisible(true);
				tableInfo.clearSelection();
				txtDroit.setEditable(true);
				}}
		});
       /*Droit.DroitFrame.setIconImage((Image)ImageBonneQualite.scaleImage(30,30,"/logo_fac.jpg"));
		Droit.DroitFrame.setTitle("DROIT");
		Droit.DroitFrame.setContentPane(Droit.panelPrincipale);
		Droit.DroitFrame.setSize(new Dimension(500,550));
		Droit.DroitFrame.setLocationRelativeTo(null);
		Droit.DroitFrame.setVisible(true);*/	
		
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Droit();
		panelPrincipale.removeAll();
		DroitFrame.setIconImage((Image)ImageBonneQualite.scaleImage(30,30,"/logo_fac.jpg"));
		DroitFrame.setTitle("DROIT");
		DroitFrame.setContentPane(panelPrincipale);
		DroitFrame.setSize(new Dimension(500,550));
		DroitFrame.setLocationRelativeTo(null);
		DroitFrame.setVisible(true);

	}*/
	

}
