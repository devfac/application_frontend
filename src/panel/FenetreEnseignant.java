package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import data_base.DataBase;
import image.ImageBonneQualite;
import image.LoadImage;
import loading.Methode;
import table.TableModel;

@SuppressWarnings("serial")
public class FenetreEnseignant extends JDialog{
	 
	
	public  JLabel nom=new JLabel("NOM: ");
	public  JLabel prenom=new JLabel("Prénom: ");
	public  JLabel num=new JLabel("Numero d'inscription: ");
	public JLabel mdp=new JLabel("Mot de passe actuelle");
	public JLabel nouvmdp=new JLabel(" Nouveau mot de passe");
	public JLabel confmdp=new JLabel("Confirmer le mot de passe");
	public static JLabel avert0=new JLabel("INSERER LE PASSE INCORRECTE ");
	public static JLabel avert=new JLabel("MOT DE PASSE INCORRECTE ");
	public static JLabel avert1=new JLabel("MOT DE PASSE NON CONFIRME  ");
	
	public  JLabel tabLab []= {nom,prenom,mdp,nouvmdp,confmdp};
	public   JLabel lab[];
	
	public  JTextField  numTxt=new JTextField ();
	public  JTextField  nomTxt=new JTextField ();
	public  JTextField  prenomTxt=new JTextField ();
	public JPasswordField mdpTxt=new JPasswordField("");
	public JPasswordField nouvmdpTxt=new JPasswordField("");
	public JPasswordField confmdpTxt=new JPasswordField("");
	
	public  JTextField txtTab1[]= {nomTxt,prenomTxt};
	public  JTextField txtTab2[]= {numTxt,nomTxt,prenomTxt};
	public JLabel passTabLab[]= {mdp,nouvmdp,confmdp};
	public JPasswordField passTab1[]= {mdpTxt,nouvmdpTxt,confmdpTxt};
	public  JLabel txtTabLab []= {nom,prenom};
	public  JTextField field[];
	

	
	public  JPanel panelPrincipale=new JPanel();
	public  JPanel panelInscription=new JPanel();
	public  JPanel panelTable=new JPanel();
	public  JPanel ligne1=new JPanel();
	public  JPanel pan=new JPanel();
	public  JPanel ligne2=new JPanel();
	public  JPanel ligne3=new JPanel();
	public  JPanel ligne4=new JPanel();
	public  JPanel ligne5=new JPanel();
	public  JPanel ligne6=new JPanel();
	public  JPanel ligne7=new JPanel();
	public  JPanel ligne8=new JPanel();
	public  JPanel ligne10=new JPanel();
	public  JPanel panTete=new JPanel();
	public  JPanel panmessage=new JPanel();
	
	public  JLabel entete=new JLabel(" UNIVERSITE DE FIANARANTSOA ");
	public  JLabel entete1=new JLabel(" FACULTE DES SCIENCES ");
	public  JLabel entete2=new JLabel("CONNAISSANCE-EXCELLENCE-RIGUEUR ");
	
	public  JLabel logo1= new JLabel();
	public  JLabel logo2= new JLabel();
	public  JLabel image= new JLabel();
	
	public  JLabel titreInscription= new JLabel("INSCRIPTION");
	public  JLabel titreTable= new JLabel(" LISTE DES ENSEIGNANTS");
	
	public  Font police1= new Font("arial",Font.BOLD,23);
	public  Font police2= new Font("arial",Font.BOLD,18);
	public  Font police3= new Font("arial",Font.BOLD,12);
	public  Font police4= new Font("arial",Font.BOLD,14);
	
	public  JButton enreg  =new JButton( LoadImage.transformeb(100, 25, "/save.jpg"));
	public  JButton annule =new JButton( LoadImage.transformeb(100, 25, "/annul.jpg"));
	public  JButton enregmod =new JButton( LoadImage.transformeb(100, 25, "/Ok.jpg"));

	public  JButton mod=new JButton (LoadImage.transformeb(100, 25, "/Modif.jpg"));
	public  JButton supp=new JButton(LoadImage.transformeb(100, 25, "/Supprimer.jpg"));
	public  JButton home=new JButton( LoadImage.transformeb(100, 25, "/home.jpg"));
	//public  JButton print=new JButton( LoadImage.transformeb(100, 25, "/imprimer.jpg"));
	public  JButton actual=new JButton( LoadImage.transformeb(100, 25, "/actualiser.jpg"));
	//public  JButton term=new JButton( LoadImage.transformeb(100, 25, "/terminer.jpg"));
	public  JButton reset=new JButton(LoadImage.transformeb(30, 20, "/suppcin.jpg"));
	
	public  JScrollPane scrollPane;
	public  int k=0;

	public  JButton retour=new JButton("Retour");
	public  DefaultTableModel model =new DefaultTableModel();
	public  String title[]= {"N°"," Nom et Prenom "};
	public  String mention="MATHEMATIQUES ET APPLICATIONS";
	public JTable table;
	public static JCheckBox aff=new JCheckBox("Afficher le Mot De Passe");
	 
	 JLayeredPane layeredPane = new JLayeredPane();
	@SuppressWarnings("rawtypes")
	 JComboBox combo[];
	
	 JScrollPane scrol=new JScrollPane();
	 Font font=new Font("arial",Font.BOLD,16);
	
	@SuppressWarnings("deprecation")
	public  FenetreEnseignant(JFrame parent, String tete){
		
		
		this.setTitle( tete );
		this.setResizable(false);
		this.setSize(900,550);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(parent);
	
		titreTable= new JLabel(" LISTE DES ENSEIGNANTS");
		panelTable.add(titreTable);
		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		model=new TableModel(title);
		/*switch(mention) {
		case "MATHEMATIQUES ET APPLICATIONS":
			break;
		}*/
		//model=new DataBase().importDonneIns(model,mention);
		table =new JTable(model);
		for (int c=0; c<2;c++) {
			if(c!=0)
			table.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
		}
		
		table.setPreferredScrollableViewportSize(new Dimension(370,350));
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		panelTable.add(new JScrollPane(table));
		/*panelTable.add(term);
		panelTable.add(print);
		print.setPreferredSize(new Dimension(100,25));
		term.setPreferredSize(new Dimension(100,25));
		*/
		annule.setPreferredSize(new Dimension(100,25));
		actual.setPreferredSize(new Dimension(100,25));
		reset.setPreferredSize(new Dimension(30,20));
		mod.setPreferredSize(new Dimension(100,25));
		supp.setPreferredSize(new Dimension(100,25));
		enreg.setPreferredSize(new Dimension(100,25));
		enregmod.setPreferredSize(new Dimension(100,25));
		logo1.setIcon(ImageBonneQualite.image("C:/Program Files (x86)/JDev/DataSciences/image/logoUniv.png",80));
		logo2.setIcon(ImageBonneQualite.image("C:/Program Files (x86)/JDev/DataSciences/image/logoFac.png",90));
		logo1.setPreferredSize(new Dimension(80,100));
		logo2.setPreferredSize(new Dimension(80,80));
		
		panTete.setPreferredSize(new Dimension(500,140));
		ligne1.setPreferredSize(new Dimension(300,80));
		ligne2.setPreferredSize(new Dimension(620,1));
		ligne3.setPreferredSize(new Dimension(500,1));
		ligne4.setPreferredSize(new Dimension(500,1));
		ligne5.setPreferredSize(new Dimension(72,2));

		ligne6.setPreferredSize(new Dimension(500,50));
		panmessage.setPreferredSize(new Dimension(500,20));
		ligne8.setPreferredSize(new Dimension(500,1));
		ligne10.setPreferredSize(new Dimension(500,1));
		
		
		entete.setPreferredSize(new Dimension(400,15));
		entete1.setPreferredSize(new Dimension(400,28));
		entete2.setPreferredSize(new Dimension(400,10));
		
		entete.setHorizontalAlignment(JLabel.CENTER);
		entete1.setHorizontalAlignment(JLabel.CENTER);
		entete2.setHorizontalAlignment(JLabel.CENTER);
		
		
		entete.setFont(police4);
		entete1.setFont(police1);
		entete2.setFont(police4);
		
		logo1.setOpaque(false);
		logo2.setOpaque(false);
		
		entete.setForeground(Color.green);
		entete2.setForeground(Color.green);
		
		panelPrincipale.setBackground(Color.white);
		panelInscription.setBackground(Color.white);
		
		ligne1.setBackground(Color.white);
		ligne2.setBackground(Color.white);
		ligne7.setBackground(Color.white);
		ligne6.setOpaque(false);
		panmessage.setOpaque(false);
		panTete.setBackground(Color.white);
		
		titreInscription.setFont(police2);
		titreTable.setFont(police2);
		
		
		panelInscription.setPreferredSize(new Dimension(500,500));
		panelTable.setPreferredSize(new Dimension(380,500));
		panelTable.setOpaque(false);
		
		for(int i=0;i<txtTab1.length;i++)
			txtTab1[i].setPreferredSize(new Dimension(400,20));
		for(int i=0;i<passTab1.length;i++)
			passTab1[i].setPreferredSize(new Dimension(310,20));
		for(int i=0;i<txtTab1.length;i++)
			txtTabLab[i].setPreferredSize(new Dimension(80,20));
		for(int i=0;i<passTab1.length;i++)
			passTabLab[i].setPreferredSize(new Dimension(150,20));
		
		for(int i=0;i<tabLab.length;i++)
			tabLab[i].setFont(police3);		
		   numTxt.setPreferredSize(new Dimension(200,20));
		
		
		
		ligne8.setOpaque(false);
		ligne3.setOpaque(false);
		ligne10.setOpaque(false);
		ligne4.setOpaque(false);
		aff.setOpaque(false);
		
		panTete.add(logo1);
		panTete.add(ligne1);
		
		
		ligne1.add(entete);
		ligne1.add(entete1);
		ligne1.add(entete2);
		
		panTete.add(logo2);
		panelInscription.add(panTete);
		panelInscription.add(num);
		panelInscription.add(numTxt);
		panelInscription.add(ligne2);
		panelInscription.add(nom);
		panelInscription.add(nomTxt);
		panelInscription.add(ligne10);
		panelInscription.add(prenom);
		panelInscription.add(prenomTxt);
		panelInscription.add(ligne8);
		panelInscription.add(mdp);
		panelInscription.add(mdpTxt);
		panelInscription.add(ligne3);
		panelInscription.add(nouvmdp);
		panelInscription.add(nouvmdpTxt);
		panelInscription.add(ligne4);
		panelInscription.add(confmdp);
		panelInscription.add(confmdpTxt);
		mdp.setVisible(false);
		mdpTxt.setVisible(false);
		
		
		
		panelInscription.add(ligne6);
		ligne6.add(aff);
		panelInscription.add(panmessage);
		panmessage.add(avert);
		panmessage.add(avert1);
		avert.setVisible(false);
		avert1.setVisible(false);
		avert1.setForeground(Color.red);
		avert.setForeground(Color.red);
		
		panelInscription.add(annule);
		panelInscription.add(annule);
		panelInscription.add(enreg);
		panelInscription.add(mod);
		panelInscription.add(supp);
		panelInscription.add(enregmod);
		panelInscription.add(actual);
		
		enregmod.setVisible(false);
		mod.setVisible(false);
		supp.setVisible(false);
		
		panelPrincipale.add(panelInscription);
		panelPrincipale.add(panelTable);
		model=new DataBase().importDonneInsEnseignant(model,mention);
		
		
		
		
		/*********************************ACTION BOUTTON********************************/
		
		
		actual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model=new DataBase().importDonneInsEnseignant(model,mention);
			}
		});
		
		annule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Methode.renitialDonner( txtTab1,passTab1,mod,supp,enregmod,enreg);
				numTxt.setText("");
			}
		});
		
		mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numTxt.setEnabled(true);
				nomTxt.setEnabled(true);
				prenomTxt.setEnabled(true);
				mdp.setVisible(true);
				mdpTxt.setVisible(true);
				mod.setVisible(false);
				supp.setVisible(false);
				enregmod.setVisible(true);
				nouvmdpTxt.setEnabled(true);
				confmdpTxt.setEnabled(true);
				
			}
		});
		
		enregmod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((!nouvmdpTxt.getText().equals(""))&&(!nouvmdpTxt.getText().equals(""))) {
				String mdpens=DataBase.recuperMotDePasseEnseignant(numTxt.getText());
				if(mdpTxt.getText().equals(mdpens)) {
					if(nouvmdpTxt.getText().equals(confmdpTxt.getText())) {
						int option = JOptionPane.showConfirmDialog(null,
								"Voulez-vous appliquer la modification de "
										+ "?","ATTENTION!",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
					if(option == JOptionPane.OK_OPTION){
						
						if(Methode.valideEns1(mention, numTxt,nomTxt,prenomTxt)){
								DataBase.modif_mdp_enseignant(numTxt.getText(),nomTxt.getText(),prenomTxt.getText(),nouvmdpTxt.getText(), "MATHEMATIQUES ET APPLICATIONS");
								mod.setVisible(false);
								supp.setVisible(false);
								enregmod.setVisible(false);
								enreg.setVisible(true);
								mdp.setVisible(false);
								mdpTxt.setVisible(false);
								Methode.renitialDonner( txtTab1,passTab1,mod,supp,enregmod,enreg);
								numTxt.setText("");
								
								model=new DataBase().importDonneInsEnseignant(model,mention);
								
							}
						}
					}else {
						mdpTxt.setText("");
						nouvmdpTxt.setText("");
						confmdpTxt.setText("");
						avert1.setVisible(true);
					}
					
				}else {
					mdpTxt.setText("");
					nouvmdpTxt.setText("");
					confmdpTxt.setText("");
					avert.setVisible(true);
					}
			}else {
				JOptionPane.showMessageDialog(null, "LE MOT DE PASSE EST VIDE","ERREUR",0);
		
			}
			}
		});
		
		

		
		
		
		
		
		
		mdpTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avert1.setVisible(false);
				avert.setVisible(false);
			}
		});
		nouvmdpTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avert.setVisible(false);
				avert1.setVisible(false);
			}
		});
		confmdpTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avert.setVisible(false);
				avert1.setVisible(false);
			}
		});
	
enreg.addActionListener(new ActionListener() {
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent arg0) {
	if((!nouvmdpTxt.getText().equals(""))&&(!nouvmdpTxt.getText().equals(""))) {
	String mdputil=DataBase.recuperMotDePasseEnseignant("MATHEMATIQUES ET APPLICATIONS");
		if(nouvmdpTxt.getText().equals(confmdpTxt.getText())) {
			if (Methode.valideEns1(mention, numTxt,nomTxt,prenomTxt)){
			DataBase.ajout_enseignant(numTxt.getText(),nomTxt.getText().toUpperCase(),Methode.finitText(prenomTxt.getText()),
					nouvmdpTxt.getText(),mention);
			Methode.renitialDonner( txtTab1,passTab1,mod,supp,enregmod,enreg);
			numTxt.setText("");
			model=new DataBase().importDonneInsEnseignant(model,mention);
			JOptionPane.showMessageDialog(null, "ENSEIGNANT BIEN AJOUTER","SUCCES",JOptionPane.INFORMATION_MESSAGE);}
		}else {
			mdpTxt.setText("");
			nouvmdpTxt.setText("");
			confmdpTxt.setText("");
			avert1.setVisible(true);
		}		
	}
	else {
		JOptionPane.showMessageDialog(null, "LE MOT DE PASSE EST VIDE","ERREUR",0);
		}
	}
	});
	
	supp.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int option = JOptionPane.showConfirmDialog(null,"Voulez-vous Supprimer "
							+ "?","ATTENTION!",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE);
			if(option == JOptionPane.OK_OPTION){
				mod.setVisible(false);
				supp.setVisible(false);
				enregmod.setVisible(false);
				enreg.setVisible(true);
				DataBase.supprimerEns(Methode.getNomIns(table));
				Methode.renitialDonner( txtTab1,passTab1,mod,supp,enregmod,enreg);
				numTxt.setText("");
				
				model=new DataBase().importDonneInsEnseignant(model,mention);
			}
		}
	});
	
		
		aff.addActionListener(new ActionListener(){
			private final char defaultChar=mdpTxt.getEchoChar();
			public void actionPerformed(ActionEvent e) {	
			if(aff.isSelected()) {
				mdpTxt.setEchoChar('\0');
				confmdpTxt.setEchoChar('\0');
			}
			else {
				mdpTxt.setEchoChar(defaultChar);
				confmdpTxt.setEchoChar(defaultChar);
			  }
		}
	 });
		
		aff.addActionListener(new ActionListener(){
			private final char defaultChar=nouvmdpTxt.getEchoChar();
			public void actionPerformed(ActionEvent e) {	
			if(aff.isSelected()) {
				nouvmdpTxt.setEchoChar('\0');
			}
			else {
				nouvmdpTxt.setEchoChar(defaultChar);
			}
			}
			});	
				
					
		
		
		/*********************FIN ACTION BOUTTON********************/
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(table.getRowCount()>0 && table.getSelectedRow()!=-1) {
				if(e.getClickCount()==1) { 
					Methode.renitialDonner( txtTab1,passTab1,mod,supp,enregmod,enreg);
				}
				if(e.getClickCount()==2) {
					DataBase.recuperInformationEnseignant(Methode.getNomIns(table),txtTab2);
					annule.setVisible(true);
					supp.setVisible(true);
					mod.setVisible(true);
					enreg.setVisible(false);
					nomTxt.setEnabled(false);
					prenomTxt.setEnabled(false);
					numTxt.setEnabled(false);
					nouvmdpTxt.setEnabled(false);
					confmdpTxt.setEnabled(false);
				}
			   }
			}
		});
		
		
		
			this.addWindowListener(new WindowAdapter() {
	  			public void windowClosing(WindowEvent e){
	  				int option = JOptionPane.showConfirmDialog(null,"Voulez-vous Terminer ?","ATTENTION!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
						if(option == JOptionPane.OK_OPTION){
							dispose();
							}
	  				}
	  			});
		
		this.getContentPane().add(panelPrincipale, BorderLayout.CENTER);
		this.setVisible(false);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	}





