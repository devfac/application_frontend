package zdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import impression.FenetreImpressionCarteParticulier;
import loading.Main;

@SuppressWarnings("serial")
public  class  CarteParticulier extends  JDialog {
	
public static  JDialog dial= new JDialog();	

public static JPanel control = new  JPanel();
public static JPanel content = new  JPanel();

public  JTable table;
public DefaultTableModel model=new DefaultTableModel();
public String titre[]= {"Num Carte","Nom et Prénom"}; 
public String titreBout[]= {"L1","L2","L3","M1","M2"}; 
public JTextField boutTxt=new JTextField();
public JLabel boutLab=new JLabel("Numéro Carte:");
public JPanel ligne=new JPanel();
public  JButton [] boutNiveauF;
public  JButton [] boutNiveauD;
public JButton boutAjout=new JButton( LoadImage.transformeb(100, 25, "/Ok.jpg"));
public JButton boutAnnul=new JButton( LoadImage.transformeb(100, 25, "/Supprimer.jpg"));
String mention;
public static int k=0;

public  CarteParticulier(JFrame parent){
	super(parent);
}

	public  CarteParticulier(JFrame parent, String title, boolean modal,String mention){
		this.mention=mention;
		dial.setTitle( title );
		dial.setResizable(false);
		dial.setSize(350,480);
		dial.setModalityType(ModalityType.APPLICATION_MODAL);
		dial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dial.setLocationRelativeTo(parent);
		dial.getContentPane().add(content, BorderLayout.CENTER);
		dial.getContentPane().add(control, BorderLayout.SOUTH);
		
		initComponent();
		dial.setVisible(true);
	}

	public  void initComponent(){
		DefaultTableCellRenderer centerRenderer= new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		boutNiveauF=new JButton[titreBout.length];
		boutNiveauD=new JButton[titreBout.length];
		boutTxt.setPreferredSize(new Dimension(100,20));
		ligne.setPreferredSize(new Dimension(360,1));
		boutAjout.setPreferredSize(new Dimension(100,25));
		boutAnnul.setPreferredSize(new Dimension(100,25));

		control.setPreferredSize(new Dimension(100,50));
		for(int i=0;i<titreBout.length;i++) {
			boutNiveauF[i]=new JButton(titreBout[i]+"F");
			boutNiveauF[i].setPreferredSize(new Dimension(60,20));
			control.add(boutNiveauF[i]);
		}
		
		for(int i=0;i<titreBout.length;i++) {
			boutNiveauD[i]=new JButton(titreBout[i]+"D");
			boutNiveauD[i].setPreferredSize(new Dimension(60,20));
			control.add(boutNiveauD[i]);
		}
		
		content.add(boutLab);
		content.add(boutTxt);
		content.add(ligne);
		content.add(boutAjout);
		content.add(boutAnnul);
		for(int i=0;i<titre.length;i++)
			model.addColumn(titre[i]);
		table=new JTable(model);
			table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			table.setPreferredScrollableViewportSize(new Dimension(320,322));
			table.setFillsViewportHeight(true);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(230);
			content.add(new JScrollPane(table));
			
			boutAjout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!DataBase.verificNumImport(boutTxt.getText().trim())) {
						model=DataBase.importDonneCarteParticulier(boutTxt.getText().trim(),model);
					}else {
						JOptionPane.showMessageDialog(Main.main, "Numéro n'est pas encore inscrit ", 
								 "ERREUR",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
			
			boutAnnul.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int k=table.getSelectedRow();
					if(k>=0)
					model.removeRow(k);
				}
			});
			
			boutAnnul.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int k=table.getSelectedRow();
					if(k>=0)
					model.removeRow(k);
				}
			});
			for(int i=0;i<boutNiveauF.length;i++)
				boutNiveauF[i].addActionListener(new imprimeCarteF(i));
			for(int i=0;i<boutNiveauF.length;i++)
					boutNiveauD[i].addActionListener(new imprimeCarteD(i));
			
	}
		
	
	public static void reset() {
		
	}
	
	public  class imprimeCarteF implements ActionListener{
		int i;
		public imprimeCarteF(int i) {
			this.i=i;
		}
		public void actionPerformed(ActionEvent e) {
		new FenetreImpressionCarteParticulier(mention,titreBout[i],1,model).setVisible(true);
			
		}		
	}
	
	public  class imprimeCarteD implements ActionListener{
		int i;
		public imprimeCarteD(int i) {
			this.i=i;
		}
		public void actionPerformed(ActionEvent e) {
		new FenetreImpressionCarteParticulier(mention,titreBout[i],2,model).setVisible(true);
			
		}		
	}
	
	public static void main(String[] argv) {
		new CarteParticulier(null,"",true,"Mathematiques et Applications");
	}

}
