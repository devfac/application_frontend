package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import data_base.DataBase;
import loading.Main;
import loading.Methode;
import table.TableModel;
import zdialog.OuvrirNote;

@SuppressWarnings("serial")
public class FenetreNote extends JDialog{
	 
	 String username = "postgres";     
	 String password = "postgres";     
	 String url = "jdbc:postgresql://localhost:5432/";     
	 Connection connection = null;     
	 Statement statement = null;  
	 Connection connection2 = null;     
	 ResultSet rset = null; 
	
	 JTabbedPane pan=new JTabbedPane();
	 JPanel panPrincipale[];
	 JPanel panNote[];
	 JPanel panTableNote[];
	 JPanel panRecNote[];
	
	 JPanel panMatiere[];
	 JLabel titre[];
	
	 DefaultTableModel modelEc[];
	 DefaultTableModel modelUe[];
	 DefaultTableModel modelNote[];
	 DefaultTableModel modelRecNote[];
	 
	 JTable tableAffiche[];
	 JTable tableRecNote[];
	
	
	 JButton creer[];
	 JButton val[];
	 JButton sup[];
	 JButton add[];
	 JButton ajout[];
	 JButton sauv[];
	
	
	 String titleEc[]= {"E.C","Poids"};
	 String titleUe[]= {"U.E","Credit"};
	 String title[][];
	
	 String semestre[];
	 String mention;
	 String parcour;
	 String type;
	 String post;
	 int nbrSems, debutSems;
	 int k=0;

		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
	 JLayeredPane layeredPane = new JLayeredPane();
	@SuppressWarnings("rawtypes")
	 JComboBox combo[];
	
	 JScrollPane scrol=new JScrollPane();
	 Font font=new Font("arial",Font.BOLD,16);
	 
	
	@SuppressWarnings("unchecked")
	public  FenetreNote(JFrame parent, String title, boolean modal,String mention
			,int k,String type,String parcour,int nbrSems,int debutSems,String semestre[],String  post){
		
		this.mention=mention;
		this.type=type;
		this.k=k;
		this.parcour=parcour;
		this.nbrSems=nbrSems;
		this.debutSems=debutSems;
		this.semestre=semestre;
		this.post=post;
		
		this.setTitle( title );
		this.setResizable(false);
		this.setSize(1000,700);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(parent);

		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	
		panPrincipale=new JPanel[nbrSems];
		panMatiere=new JPanel[nbrSems];
		panNote=new JPanel[nbrSems];
		panRecNote=new JPanel[nbrSems];
		panTableNote=new JPanel[nbrSems];
		modelEc= new DefaultTableModel[nbrSems];
		modelUe= new DefaultTableModel[nbrSems];
		modelNote= new DefaultTableModel[nbrSems];
		modelRecNote= new DefaultTableModel[nbrSems];
		titre=new JLabel[nbrSems];
		
		creer=new JButton[nbrSems];
		val=new JButton[nbrSems];
		add=new JButton[nbrSems];
		ajout=new JButton[nbrSems];
		sauv=new JButton[nbrSems];
		sup=new JButton[nbrSems];
		combo=new JComboBox[nbrSems];
		
		tableAffiche=new JTable[nbrSems];
		tableRecNote=new JTable[nbrSems];
		
		for(int i=0;i<nbrSems;i++) {
			panPrincipale[i]=new JPanel();
			panMatiere[i]=new JPanel();
			panNote[i]=new JPanel();
			panRecNote[i]=new JPanel();
			panTableNote[i]=new JPanel();
			titre[i]=new JLabel("NOTE SESSION "+type.toUpperCase()+" S"+(i+debutSems+1)+" "+parcour);
			titre[i].setFont(font);
			panNote[i].add(titre[i]);
			
			modelUe[i]=new TableModel(titleUe,panMatiere[i],230,200);
			modelEc[i]=new TableModel(titleEc,panMatiere[i],230,200);
			//modelNote[i]=new TableModel(titleEc,panTableNote[i]);
			
			creer[i]=new JButton("Créer table note");
			val[i]=new JButton("Ajouter");
			add[i]=new JButton("Ajouter etudiants");
			ajout[i]=new JButton("Ajout Column");
			sauv[i]=new JButton("Sauvegarder");
			sup[i]=new JButton("Supprimer la table ");
			
			panPrincipale[i].setBackground(Color.cyan);
			panMatiere[i].setBackground(Color.cyan);
			panNote[i].setBackground(Color.cyan);
			

			panMatiere[i].setPreferredSize(new Dimension(250,630));
			panNote[i].setPreferredSize(new Dimension(720,630));
			panTableNote[i].setPreferredSize(new Dimension(710,520));
			panRecNote[i].setPreferredSize(new Dimension(710,60));
			
			
			modelNote[i]=new DefaultTableModel() {
				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
			modelRecNote[i]=new DefaultTableModel() {
				public boolean isCellEditable(int row, int column) {
					if(column!=0 && column!=1)
						return true;
					else
						return false;
				};
			};

			modelNote[i].addColumn("N°Examen");
			modelNote[i].addColumn("N°Carte");
			
			modelRecNote[i].addColumn("N°Examen");
			modelRecNote[i].addColumn("N°Carte");
			
			tableAffiche[i]=new JTable(modelNote[i]);
			tableRecNote[i]=new JTable(modelRecNote[i]);
			
			
				
			tableAffiche[i].setPreferredScrollableViewportSize(new Dimension(700,490));
			tableAffiche[i].setFillsViewportHeight(true);
			panTableNote[i].add(new JScrollPane(tableAffiche[i]));
			
			
				
			tableRecNote[i].setPreferredScrollableViewportSize(new Dimension(600,30));
			tableRecNote[i].setFillsViewportHeight(true);
			panRecNote[i].add(new JScrollPane(tableRecNote[i]));
			
			panRecNote[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			
			combo[i]= DataBase.recuperUe(mention,semestre[i],k);
			modelNote[i]=DataBase.crerTable(mention,(i+debutSems+1),semestre[i], (String)combo[i].getSelectedItem(), 
					k,panTableNote[i],type,modelNote[i]);
			combo[i].addActionListener(new NouvTable(i));
			modelRecNote[i]=DataBase.crerTable(mention,semestre[i], (String)combo[i].getSelectedItem(), k, panRecNote[i],modelRecNote[i]);
			

			for (int n=0; n<modelNote[i].getColumnCount();n++) {
				tableAffiche[i].getColumnModel().getColumn(n).setCellRenderer(centerRenderer);
			}
			
			
			//panPrincipale[i].setPreferredSize(new Dimension(800,680));
			
			val[i].addActionListener(new ajoutNote(i));
			add[i].addActionListener(new ajout(i));
			ajout[i].addActionListener(new ajoutCol(i));
			sauv[i].addActionListener(new Sauve(i));
			creer[i].addActionListener(new creerTable(i));
			sup[i].addActionListener(new Supprime(i,k));
			creer[i].setEnabled(false);
			sup[i].setEnabled(false);
			combo[i].setEnabled(false);
			tableAffiche[i].addMouseListener(new Deplace( tableAffiche[i], modelRecNote[i]));
			
			 String databaMatiere="";
			 String parc[]= {};
			 switch(mention) {
			 case "MATHEMATIQUES ET APPLICATIONS":
				 databaMatiere=DataBase.databaseMatiereMaths;
				 parc=DataBase.parcMaths;
				 break;
			 }
			DataBase.mess.setText("Importation de la table de saisie note s"+(i+debutSems+1)+"_"+parc[k]+"_"+type+"");
			try {
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+databaMatiere;
	        	connection = DriverManager.getConnection(url, username, password);      
	        	statement = connection.createStatement(); 
	        	rset = statement.executeQuery(" SELECT * FROM uni_enseign_"+parc[k]+" WHERE semestre='"+semestre[i]+"'ORDER BY nom_ue");
	        	while(rset.next()) {
	        		modelUe[i].addRow(new Object[] {rset.getString(1),rset.getString(2)});
	        		}
	        	rset.close();
	        	statement.close();
	        	connection.close();
	        	}catch(Exception e) {
	        		System.out.println(e.getMessage());
	        	}
			
			try {
				String url = "jdbc:postgresql://"+DataBase.port+":5432/"+databaMatiere;
	        	connection = DriverManager.getConnection(url, username, password);      
	        	statement = connection.createStatement(); 
	        	rset = statement.executeQuery(" SELECT * FROM el_cons_"+parc[k]+" WHERE semestre='"+semestre[i]+"'ORDER BY nom_ue");
	        	while(rset.next()) {
	        		modelEc[i].addRow(new Object[] {rset.getString(1),rset.getString("poids")});
	        		}
	        	rset.close();
	        	statement.close();
	        	connection.close();
	        	}catch(Exception e) {
	        		System.out.println(e.getMessage());
	        	}
			
		}
		
		for(int i=0;i<nbrSems;i++) {
			panMatiere[i].add(creer[i]);
			panMatiere[i].add(sup[i]);
			panMatiere[i].add(add[i]);
			panMatiere[i].add(ajout[i]);
			panMatiere[i].add(sauv[i]);
			panNote[i].add(combo[i]);
			panNote[i].add(panRecNote[i]);
			panNote[i].add(panTableNote[i]);
			panPrincipale[i].add(panMatiere[i]);
			panPrincipale[i].add(panNote[i]);
			if(type=="normal") {
				creer[i].setVisible(true);
				sup[i].setVisible(true);
			}else {
				creer[i].setVisible(false);
				sup[i].setVisible(false);
			}
		}
		
		
		int i = debutSems;
		for(JPanel paneau : panPrincipale){
		pan.add("Semestre "+(++i), paneau);
		}JPanel paneau=new OuvrirNote(this, "", post,creer,sup,combo,nbrSems).pane;
		paneau.setBounds(2, 2,990,668);
		pan.setBounds(0, 0, 1000, 700);
		paneau.setBackground(new Color(249,0,0,80));
		
		layeredPane.add(pan,new Integer(0), 0);
		layeredPane.add(paneau,new Integer(1), 0);
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
		this.getContentPane().add(layeredPane, BorderLayout.CENTER);
		this.setVisible(false);
		
	}
	
	public static void main(String[] args) {
		
	}
	
	
	public   class NouvTable implements ActionListener{
		int i;
		NouvTable(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent e) {
			String l=(String) combo[i].getSelectedItem();
			modelNote[i]=DataBase.crerTable(mention,(i+debutSems+1),semestre[i], l, k, panTableNote[i],type,modelNote[i]);
			DataBase.modifTable(mention,modelRecNote[i], semestre[i], l, k);
			panRecNote[i].add(val[i]);

			//val[i].setVisible(false);
			setVisible(true);
			}
		}
	
	
	public  class ajoutNote implements ActionListener{
		int i;
		ajoutNote(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			boolean ok=true;
			for(int t=2;t<modelRecNote[i].getColumnCount();t++) {
				if(!test(String.valueOf(modelRecNote[i].getValueAt(0,t))))
					ok=false;
			}
			if(ok) {
				if(DataBase.verificNum(mention,(i+debutSems+1),(String)modelRecNote[i].getValueAt(0,1)
						, parcour)) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			String l=(String) combo[i].getSelectedItem();
			if(type.equals("normal")) {
				DataBase.ajoutNoteNorm(mention,modelRecNote[i],(i+debutSems+1), k, semestre[i],l,panNote[i]);
				DataBase.ajoutNoteFinal(mention,modelRecNote[i],(i+debutSems+1), k, semestre[i],l);
			}
			if(type.equals("rattrapage")) {
				DataBase.ajoutNoteRat(mention,modelRecNote[i],(i+debutSems+1), k, semestre[i],l,panNote[i]);
				DataBase.ajoutNoteFinal(mention,modelRecNote[i],(i+debutSems+1), k, semestre[i],l);
			}
			modelNote[i]=DataBase.crerTable(mention,(i+debutSems+1),semestre[i], l, k, panTableNote[i],type,modelNote[i]);
			DataBase.modifTable(mention,modelRecNote[i], semestre[i], l, k);
			setVisible(true);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			Toolkit.getDefaultToolkit().beep();
			
				}
			}else {
				JOptionPane.showMessageDialog(panPrincipale[i], "Invalide valeur du Tableau ", "ERREUR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public  class creerTable implements ActionListener{
		int i;
		creerTable(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DataBase.creation_table_note(panPrincipale[i],mention,(i+debutSems+1), semestre[i], k);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	
	public  class ajout implements ActionListener{
		int i;
		ajout(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DataBase.ajoutEt(mention,(i+debutSems+1), parcour, k); 
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	
	public  class ajoutCol implements ActionListener{
		int i;
		ajoutCol(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DataBase.ajoutColonne(panPrincipale[i],mention,(i+debutSems+1), k,type); 
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	
	public  class Sauve implements ActionListener{
		int i;
		Sauve(int i){
			this.i=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			try {
				Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Methode.sauvegarde_note(mention,i, k,type,debutSems,nbrSems);
				Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} catch (IOException e) {
				e.printStackTrace();
			} 
			}
		}
	
	public  class Supprime implements ActionListener{
		int i;
		int k;
		Supprime(int i,int k){
			this.i=i;
			this.k=k;
		}
		public void actionPerformed(ActionEvent arg0) {
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DataBase.supprimerTable(panPrincipale[i],mention,(i+debutSems+1),k);
			Main.main.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		} 
	
	public class Deplace extends MouseAdapter{
		JTable table1, table2;
		DefaultTableModel model;
		public Deplace(JTable table1,DefaultTableModel model) {
			this.table1=table1;
			this.model=model;
		}
		public void mouseClicked(MouseEvent e) {
			if(table1.getRowCount()>0 && table1.getSelectedRow()!=-1) {
				if(e.getClickCount()==2) { 
					int k=table1.getColumnCount();
					int h=table1.getSelectedRow();
					if(model.getColumnCount()!=2)
					for(int j=0;j<k-1;j++) {
						model.setValueAt(null, 0,j);
						if(!String.valueOf(table1.getValueAt(h, j)).equals("Abs"))
							model.setValueAt(table1.getValueAt(h, j), 0, j);
					}
				}		
			}
		}
	}
	
	public boolean test(String value) {
		boolean rep=true;
		if(!value.equals("null") && !value.equals("")) {
					try {
						Double.parseDouble(value);
						rep=true;
					} catch (Exception e) {
						rep=false;
					}
				}		
		return rep;
	}
	}





