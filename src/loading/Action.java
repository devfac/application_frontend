package loading;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import data_base.DataBase;
import gest_etudiant.AfficheDroit;
import gest_etudiant.BourseMathsP;
import gest_etudiant.BourseMathsR;
import gest_etudiant.Droit;
import gest_etudiant.GestImport;
import gest_etudiant.ListeMaths;
import gest_matiere.MatiereMaths;
import gest_note.GestNoteMathsFinales;
import gest_note.GestNoteMathsNormal;
import gest_note.GestNoteMathsRattrapage;
import image.ImageBonneQualite;
import impression.FenetreImpressionCarte;
import inscription.Inscription;
import login.LoginPrincipale;
import panel.FenetreEnseignant;
import reinscription.Reinscription;
import statisique.FenetreEffectif;
import zdialog.Bureau;
import zdialog.CarteParticulier;
import zdialog.Fenetre;
import zdialog.Parametre;
import zdialog.ZDialog;

public class Action {
	static JSplitPane pan1;
	static JSplitPane pan2;
	static JSplitPane pan3;
	
	static JScrollPane panIns;
	static JScrollPane panIns2;
	static JScrollPane panIns3;
	
	static int mention;
	
@SuppressWarnings("rawtypes")
public static void action() {
	
	 DataBase.connexion();
	 
	 LoginPrincipale.ajoutAu();
	 DataBase.database="anne_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
				String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";
	 DataBase.databaseNoteMaths="note_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
				String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";
	 DataBase.databaseMatiereMaths="matier_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
				String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";
		
	
	 DataBase.creationBD();
		 
	 Menu.ajouter();
	 ListeMaths.ajouter();
	 BourseMathsP.ajouter();
	 BourseMathsR.ajouter();
	 MatiereMaths.ajouter();
	 FenetreEffectif.ajouter();
	 GestNoteMathsNormal.ajouter();
	 GestNoteMathsRattrapage.ajouter();
	 GestNoteMathsFinales.ajouter();
	 GestImport.ajouter();
	 LoginPrincipale.ajouter();
	 Methode.creerReprrtoire();
	 Droit.action();
	 
	AfficheDroit.ajouter();
	CliqueSouris.action();
	

	LoginPrincipale.gestEtDroit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				Main.main.setContentPane(AfficheDroit.scrollPane);
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestDroit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Droit.DroitFrame.setIconImage((Image)ImageBonneQualite.scaleImage(30,30,"/logo_fac.jpg"));
			Droit.DroitFrame.setTitle("GESTION DE DROIT PAR NIVEAU");
			Droit.DroitFrame.setContentPane(Droit.panelPrincipale);
			Droit.DroitFrame.setSize(new Dimension(500,550));
			Droit.DroitFrame.setLocationRelativeTo(null);
			Droit.DroitFrame.setVisible(true);
		}
	});
	
	LoginPrincipale.enseignant.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new FenetreEnseignant(Main.main,"AJOUT DES ENSEIGNANTS").setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});
	
	
	ZDialog.connectBouton.addActionListener(new  ActionListener(){
		@SuppressWarnings("deprecation")
		public  void actionPerformed(ActionEvent arg0) {
			if(!DataBase.testExistance("MATHEMATIQUES ET APPLICATIONS") ) {
				
				if(ZDialog.mdp.getText() .equals(ZDialog.confMdp.getText())) {
					DataBase.ajout_utilisateur(ZDialog.nom.getText(),
					ZDialog.mdp.getText(), "utilisateur","MATHEMATIQUES ET APPLICATIONS");
					//Main.main.setVisible(true);
					Main.main.setVisible(true);
				}else {
					ZDialog.mdp.setText("");
					ZDialog.confMdp.setText("");
				}
				
			}else {
				if(ZDialog.mdpF.getText().equals(DataBase.recuperMotDePasseUtil("MATHEMATIQUES ET APPLICATIONS"))) {
					//Main.main.setContentPane(GestNote.split);
				    Main.main.setVisible(true);
				   }
			       else {
			    	   ZDialog.mdpF.setText("");
			    }
			}
			
		}
	});
	
	ZDialog.aff.addActionListener(new ActionListener(){
		private final char defaultChar= ZDialog.mdp.getEchoChar();
		public void actionPerformed(ActionEvent e) {	
		if(ZDialog.aff.isSelected()) {
			ZDialog.mdp.setEchoChar('\0');
			ZDialog.confMdp.setEchoChar('\0');
		}
		else {
			ZDialog.mdp.setEchoChar(defaultChar);
			ZDialog.confMdp.setEchoChar(defaultChar);
		  }
	}
 });
	
	ZDialog.aff.addActionListener(new ActionListener(){
		private final char defaultChar= ZDialog.mdpF.getEchoChar();
		public void actionPerformed(ActionEvent e) {	
		if(ZDialog.aff.isSelected()) {
			ZDialog.mdpF.setEchoChar('\0');
		}
		else {
			ZDialog.mdpF.setEchoChar(defaultChar);
		}
		}
		});
	
	
	
	LoginPrincipale.valide.addActionListener(new  ActionListener(){
		public  void actionPerformed(ActionEvent arg0) {
			if(Methode.testAnne()) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DataBase.ajout_Au(LoginPrincipale.anneTxt.getText().substring(0,9));
			
			LoginPrincipale.panAnne.removeAll();
			LoginPrincipale.ajoutAu();
			
			 DataBase.database="anne_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
						String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";
			 
			 DataBase.databaseNoteMaths="note_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
						String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";
			 
			 DataBase.databaseMatiereMaths="matier_"+String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(0,4)+"_"+
						String.valueOf(LoginPrincipale.typeAn.getSelectedItem()).substring(5,9)+"_maths";

			 DataBase.creationBD();
			 
			 GestNoteMathsNormal.ajouter();
			 GestNoteMathsRattrapage.ajouter();
			 GestNoteMathsFinales.ajouter();
			 FenetreEffectif.panelPrincipale.removeAll();
			 FenetreEffectif.ajouter();
			LoginPrincipale.anneTxt.setText("");
			LoginPrincipale.panAnne1.setVisible(false);
			Toolkit.getDefaultToolkit().beep();
			Main.main.setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			else
				JOptionPane.showMessageDialog(Main.main, " Ann� unversitaire invalide ", "ERREUR",
						JOptionPane.ERROR_MESSAGE);
		}
	});
	
	
	
	pan1=new Reinscription("MATHEMATIQUES ET APPLICATIONS").split;
	panIns=new Inscription("MATHEMATIQUES ET APPLICATIONS").scrollPane;
	LoginPrincipale.mentPA.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			LoginPrincipale.pan1.setVisible(false);
			LoginPrincipale.pan11.setVisible(true);
			mention=0;
			
		}
	});
	
	LoginPrincipale.mentPC.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			LoginPrincipale.pan1.setVisible(false);
			LoginPrincipale.pan11.setVisible(true);
			mention=1;
			
		}
	});
	
	LoginPrincipale.mentGH.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			LoginPrincipale.pan1.setVisible(false);
			LoginPrincipale.pan11.setVisible(true);
			mention=2;
			
		}
	});
	
	LoginPrincipale.inscription.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			switch(mention) {
			case 0:
				Main.main.setContentPane(panIns);
				Main.main.setVisible(true);
				break;
			case 1:
				Main.main.setContentPane(panIns);
				Main.main.setVisible(true);
				break;
			case 2:
				Main.main.setContentPane(panIns);
				Main.main.setVisible(true);
				break;
			}
			
			Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.reinscription.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			switch(mention) {
			case 0:
				Main.main.setContentPane(pan1);
				Main.main.setVisible(true);
				break;
			case 1:
				Main.main.setContentPane(pan1);
				Main.main.setVisible(true);
				break;
			case 2:
				Main.main.setContentPane(pan1);
				Main.main.setVisible(true);
				break;
			}
			Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.stat.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			switch(mention) {
			case 0:
				Main.main.setContentPane(FenetreEffectif.scrollpane);
				Main.main.setVisible(true);
				break;
			case 1:
				Main.main.setContentPane(FenetreEffectif.scrollpane);
				Main.main.setVisible(true);
				break;
			case 2:
				Main.main.setContentPane(FenetreEffectif.scrollpane);
				Main.main.setVisible(true);
				break;
			}
			Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestEt.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				Main.main.setContentPane(ListeMaths.scrollPane);
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestEtBoursP.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				Main.main.setContentPane(BourseMathsP.scrollPane);
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	LoginPrincipale.gestEtBoursR.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				Main.main.setContentPane(BourseMathsR.scrollPane);
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestMat.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {

				Main.main.setContentPane(MatiereMaths.scrollpane);
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	LoginPrincipale.gestNoteNorm.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
				Main.main.setContentPane(new JScrollPane(GestNoteMathsNormal.split));
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestNoteRat.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
				Main.main.setContentPane(new JScrollPane(GestNoteMathsRattrapage.split));
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestNoteFin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
				Main.main.setContentPane(new JScrollPane(GestNoteMathsFinales.split));
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.gestImport.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
				Main.main.setContentPane(new JScrollPane(GestImport.split2));
				Main.main.setVisible(true);
				Menu.prec.setEnabled(true);
		}
	});
	
	LoginPrincipale.sco.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Fenetre.title="Certificat de Scolarit�";
			Fenetre.content.removeAll();
			Fenetre.initComponent();
			Fenetre.dial.setVisible(true);
		}
	});
	LoginPrincipale.assi.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Fenetre.title="Certificat d'Assuidite";
			Fenetre.content.removeAll();
			Fenetre.initComponent();
			Fenetre.dial.setVisible(true);
		}
	});
	
	LoginPrincipale.insc.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Fenetre.title="Attestation d'Inscription";
			Fenetre.content.removeAll();
			Fenetre.initComponent();
			Fenetre.dial.setVisible(true);
		}
	});
	LoginPrincipale.valCred.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Fenetre.title="Attestation de validation de cr�dit";
			Fenetre.content.removeAll();
			Fenetre.initComponent();
			Fenetre.dial.setVisible(true);
		}
	});
	LoginPrincipale.gestPers.addActionListener(new  ActionListener(){
		public  void actionPerformed(ActionEvent arg0) {
			Bureau.bur.setVisible(true);
		}
	});
	
	LoginPrincipale.gestCartPart.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new CarteParticulier(null,"",true,"Mathematiques et Applications");
			
		}
	});
	
	LoginPrincipale.param.addActionListener(new  ActionListener(){
		public  void actionPerformed(ActionEvent arg0) {
			
			Parametre.bur.setVisible(true);
			Menu.prec.setEnabled(true);
		}
	});
	
	Fenetre.creer.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String an="";
			if(Fenetre.k==1 || Fenetre.k==3 || Fenetre.k==4 )
				an=Fenetre.anC.getText();
			else
				an=Fenetre.rentreF.getText();
			DataBase.crerCert(Fenetre.k,Fenetre.numC.getText(),(String)Fenetre.nivC.getSelectedItem(),an,Fenetre.ligne2.getText());
			
		}
	});
	LoginPrincipale.mast2F.addActionListener(new imprimeCarteM2F());
	LoginPrincipale.mast1F.addActionListener(new imprimeCarteM1F());
	LoginPrincipale.l1F.addActionListener(new imprimeCarteL1F());
	LoginPrincipale.l2F.addActionListener(new imprimeCarteL2F());
	LoginPrincipale.l3F.addActionListener(new imprimeCarteL3F());
	
	LoginPrincipale.mast2D.addActionListener(new imprimeCarteM2D());
	LoginPrincipale.mast1D.addActionListener(new imprimeCarteM1D());
	LoginPrincipale.l1D.addActionListener(new imprimeCarteL1D());
	LoginPrincipale.l2D.addActionListener(new imprimeCarteL2D());
	LoginPrincipale.l3D.addActionListener(new imprimeCarteL3D());
	
	
}

public static class imprimeCarteL1F implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) {
			 DataBase.impr=false;
		 new FenetreImpressionCarte("Math�matiques et Applications","L1",1).setVisible(true);
		}
	}
}
public static class imprimeCarteM1F implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) {
			DataBase.impr=false;
		 new FenetreImpressionCarte("Math�matiques et Applications","M1",1).setVisible(true);
		 
		}
	}
}
public static class imprimeCarteL2F implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) {
			DataBase.impr=false;
			new FenetreImpressionCarte("Math�matiques et Applications","L2",1).setVisible(true);
			
		}
	}
}
public static class imprimeCarteL3F implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) {
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","L3",1).setVisible(true);
		
		}
	}
}
public static class imprimeCarteM2F implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","M2",1).setVisible(true);
		
		}
	}
}

/*
 * IMPRESION CARTE DERIERE
 * */
public static class imprimeCarteL1D implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","L1",2).setVisible(true);
		
	}
	}
}
public static class imprimeCarteM1D implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;		
		new FenetreImpressionCarte("Math�matiques et Applications","M1",2).setVisible(true);
		
		}
	}
}
public static class imprimeCarteL2D implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","L2",2).setVisible(true);
		
	}
	}
}
public static class imprimeCarteL3D implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","L3",2).setVisible(true);
		
	}
	}
}
public static class imprimeCarteM2D implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(DataBase.impr) { 
			DataBase.impr=false;
		new FenetreImpressionCarte("Math�matiques et Applications","M2",2).setVisible(true);
		
	}
	}		
}
	
}
