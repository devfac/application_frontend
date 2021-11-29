
package loading;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import request.request_api;
import org.json.JSONObject;


import data_base.DataBase;
import image.ImageBonneQualite;
import panel.Panneau8; 


	public  class  Loading {
		public static JFrame dial= new JFrame();
		public JPanel control = new  JPanel();
		public JPanel control_vide = new  JPanel();
		public JPanel pan = new  JPanel();
		public Panneau8 content = new  Panneau8("/profil_fac.jpg");

		
		public JTextField nom = new  JTextField();;
		public JPasswordField mdp=new JPasswordField();
		public JPasswordField mdpF=new JPasswordField();
		public JPasswordField confMdp=new JPasswordField();

		public JButton connectBouton = new  JButton("Connecter");
		public JButton quitBouton = new  JButton("Annuler");
		public JCheckBox aff=new JCheckBox("Afficher le Mot De Passe");

	public  Loading(){
		dial.setTitle("");
		dial.setUndecorated(true);
		dial.setResizable(false);
		
		dial.setSize(600,300);
		dial.setLocationRelativeTo(null);
		dial.setContentPane(content);
		dial.setIconImage((Image)ImageBonneQualite.scaleImage(30,30,"/logo_fac.jpg"));
		
		initComponent();
		dial.setVisible(true);
		DataBase.port="localhost";
	}
	
	
	public void initComponent(){
		JLabel nomLabel,mdpLabelF;
		JPanel ligne_1 = new JPanel();
		JPanel ligne_3 = new JPanel();
		JPanel ligne_2 = new JPanel();
		
		JPanel top = new JPanel();
		ligne_1.setPreferredSize(new Dimension(500, 1));
		ligne_1.setOpaque(false);
		ligne_2.setPreferredSize(new Dimension(500, 1));
		ligne_2.setOpaque(false);
		top.setPreferredSize(new Dimension(500, 80));
		top.setOpaque(false);
		ligne_3.setPreferredSize(new Dimension(500, 1));
		ligne_3.setOpaque(false);
		
		
		
		connectBouton.setPreferredSize(new  Dimension(130, 20));
		quitBouton.setPreferredSize(new  Dimension(130, 20));

		nom.setPreferredSize(new  Dimension(300, 20));
		nomLabel = new  JLabel("Email:");
		nomLabel.setPreferredSize(new  Dimension(90, 20));
		nomLabel.setFont(new Font("arial",Font.BOLD,12));
		nomLabel.setHorizontalAlignment(JLabel.RIGHT);

		mdpF.setPreferredSize(new  Dimension(300, 20));
		mdpLabelF = new  JLabel("Password:");
		mdpLabelF.setPreferredSize(new  Dimension(90, 20));
		mdpLabelF.setFont(new Font("arial",Font.BOLD,12));
		mdpLabelF.setHorizontalAlignment(JLabel.RIGHT);
		aff.setFont(new Font("arial",Font.BOLD,12));


		JProgressBar bar=new JProgressBar();
		bar.setBackground(new Color(192,192,192));
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		bar.setString("Chargement...");
		bar.setForeground(new Color(139,31,31));
		control.setOpaque(false);
		aff.setOpaque(false);
		control.setPreferredSize(new Dimension(590,247));
		control_vide.setOpaque(false);
		control_vide.setVisible(false);
		control_vide.setPreferredSize(new Dimension(590,247));
		content.setLayout(new FlowLayout(FlowLayout.CENTER));

		
		pan.setPreferredSize(new Dimension(400,130));
		pan.setBackground(new Color(255,255,255));

		
		control.add(top);
		pan.add(nomLabel);
		pan.add(nom);
		pan.add(ligne_1);
		pan.add(mdpLabelF);
		pan.add(mdpF);
		pan.add(ligne_2);
		pan.add(aff);
		pan.add(ligne_3);
		pan.add(quitBouton);
		pan.add(connectBouton);
		control.add(pan);


		bar.setPreferredSize(new Dimension(200,14));		
		content.setBackground(new Color(0,0,0,15));
		content.add(control_vide);
		content.add(control);
		DataBase.mess.setPreferredSize(new Dimension(480,20));
		DataBase.mess.setFont(new Font("arial",Font.BOLD,9));
		DataBase.mess.setText("chargement...");
		DataBase.mess.setOpaque(false);
		DataBase.mess.setVisible(false);
		bar.setVisible(false);
		DataBase.mess.setForeground(Color.black);
		content.add( DataBase.mess);
		content.add(bar,BorderLayout.SOUTH);
		dial.setLayout(new FlowLayout(FlowLayout.LEFT));

		connectBouton.addActionListener(new  ActionListener(){
			public  void actionPerformed(ActionEvent arg0){
				String request = "http://"+Main.host+"/api/v1/login/access-token";
				String username = nom.getText();
				String password = mdpF.getText();
				if (username.length() > 0 && password.length() > 0){
				try {
				JSONObject response = request_api.login(request, username, password);
					if (response.has("detail")){
						JOptionPane.showMessageDialog(Main.main, response.get("detail"), "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}else{
						DataBase.mess.setVisible(true);
						bar.setVisible(true);
						control.setVisible(false);
						control_vide.setVisible(true);
						Main.token = response.getString("access_token");
						Main.role = response.getString("role");
						launch_app();
						request_api.get_anne_univ();
						
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(Main.main,"Empty email or password ", "",
					JOptionPane.ERROR_MESSAGE);
				}
		}
		});
		
		quitBouton.addActionListener(new  ActionListener(){
			public  void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		}
		
		public static void main(String [] argv){
			new Loading();
			//new Main();
			//Main.main.setVisible(true);
			//Loading.dial.dispose();
			//new ZDialog(Main.main, "CONNEXION", true,DataBase.testExistance("MATHEMATIQUES ET APPLICATIONS"));
			//new Fenetre(Main.main,"Attestation de validation de credit",true);
			//new Bureau(Main.main,"",true);
			//new Parametre(Main.main,"",true);
			//Loading.dial.dispose();
			//ZDialog.dial.setVisible(true);
		}
		
		@SuppressWarnings("resource")
		public static String createFile(){
			String serv[]= new String[5];
			File file=new File("C:/Program Files (x86)/JDev/DataSciences/package/serveur.txt");
			if(file.exists()) {
				try {
					BufferedReader reader=new BufferedReader(new FileReader(file));
					String ligne;
					int i=0;
					while((ligne=reader.readLine())!= null){
						serv[i]=ligne;
						i++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(Main.main, "Impossible de trouver le fichier du serveur",
						"ERREUR",JOptionPane.ERROR_MESSAGE);  
				System.exit(0);
		}
			return serv[0];
	}	

		public void launch_app(){
			Timer t = new Timer();
			t.schedule(
				new TimerTask() {

					@Override
					public void run() {
						new Main();
						Loading.dial.dispose();
						Main.main.setVisible(true);

						t.cancel();
					}
					
				}, 1000);
		}	
}