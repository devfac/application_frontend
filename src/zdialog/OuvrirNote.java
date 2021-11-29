package zdialog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data_base.DataBase;

@SuppressWarnings("serial")
public class OuvrirNote extends JPanel{
	public JPanel pane=new JPanel();
	public JDialog dial=new JDialog();
	public JPanel panelPrincipale= new JPanel();
	public JPanel panel= new JPanel();
	
	public JLabel info=new JLabel("<html><p>Veuillez remplir sans faute le case ci-dessous<br/> "
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pour continuer dans la saisie du note</u></p></html>");
	public JLabel LabProf=new JLabel("N° Prof :");
	public JLabel labPsProf=new JLabel("Mot de passe :");
	public static JTextField numProf = new JTextField();
	public JPasswordField profPsTxt=new JPasswordField();
	
	public JLabel mess1=new JLabel("Mot de passe invalide ");
	public JLabel mess2=new JLabel("Mot de passe invalide ");
	
	public JButton valide=new JButton("Valider");
	public JButton annul=new JButton("Annuler");
	public Font police=new Font("arial",Font.BOLD,12);
	public Font police1=new Font("calibri",Font.ITALIC,12);
	String postmention;
	JButton pan[];
	JButton pan1[];
	JComboBox<String> box[];
	int k;
	public JDialog parent;
	@SuppressWarnings("deprecation")
	public OuvrirNote(JDialog parent,String title,String postmention,JButton pan[],JButton pan1[],JComboBox<String> box[],int k)  {
	
	this.postmention=postmention;
	this.parent=parent;
	this.pan=pan;
	this.pan1=pan1;
	this.box=box;
	this.k=k;
		
		panel.add(valide);
		panel.add(annul);
		LabProf.setFont(police);
		labPsProf.setFont(police);
		info.setFont(police1);
		info.setHorizontalAlignment(JLabel.CENTER);
		valide.setForeground(Color.black);
		annul.setForeground(Color.black);
		valide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annul.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		info.setPreferredSize(new Dimension(275,50));
		numProf.setPreferredSize(new Dimension(160,20));
		profPsTxt.setPreferredSize(new Dimension(125,20));
		panelPrincipale.add(info);
		panelPrincipale.add(LabProf);
		panelPrincipale.add(numProf);
		panelPrincipale.add(mess1);
		
		mess1.setVisible(false);
		mess2.setVisible(false);
		
		mess1.setForeground(Color.red);
		mess2.setForeground(Color.red);
		
		panelPrincipale.add(labPsProf);
		panelPrincipale.add(profPsTxt);
		panelPrincipale.add(mess2);
		
		panelPrincipale.add(panel);
		
		panelPrincipale.setOpaque(true);
		
		
		profPsTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mess1.setVisible(false);
				mess2.setVisible(false);
			}
		});
		
		valide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((!numProf.getText().equals(""))&&(!profPsTxt.getText().equals(""))) {
					String mdpProf=DataBase.recuperMotDePasseEnseignant(numProf.getText());
					if(profPsTxt.getText().equals(mdpProf)) {
						
							new Move().start();
							for(int i=0;i<k;i++) {
							pan[i].setEnabled(true);
							pan1[i].setEnabled(true);
							box[i].setEnabled(true);
							}
						}else {
							mess2.setVisible(true);
							}									
				}else {
					JOptionPane.showMessageDialog(null, "Veuillez remplir la case!!!","ERREUR",0);
						}
			}	
		});
		
		
		annul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.dispose();
				dial.dispose();
				
			}
		});
		JPanel vide=new JPanel();
		vide.setPreferredSize(new Dimension(900,200));
		vide.setOpaque(false);
		pane.add(vide);
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelPrincipale.setPreferredSize(new Dimension(235,165));
		
		pane.add(panelPrincipale);
	}
	
	class Move extends Thread{
		public void run() {
				for(int i=0;i<991;i++) {
					try {
						Thread.sleep(1);
					}catch(InterruptedException ex) {
						//Logger.getLogger(TobbleBtn.class.getName());
					}
					pane.setBounds(i, i, 990-2*i, 690-2*i);
					panelPrincipale.setBounds(panelPrincipale.getX()-i, panelPrincipale.getY()-i, 235-i/5, 165-i/5);
				}
		}
		}
}
