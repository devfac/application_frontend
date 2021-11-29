package carte;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class DeriereCarte implements TableCellRenderer{ 
	
	public static JTextArea titre;
	public static JTextArea text1;
	
	public static JPanel paneTab;
	
	public static Border line= new LineBorder(Color.black);
	public static Border  tite1= new TitledBorder(line,"",TitledBorder.DEFAULT_POSITION,
    		TitledBorder.DEFAULT_POSITION,new Font("arial",Font.BOLD,8),Color.black);
 
	public static Font police=new Font("arial",Font.BOLD,10);
	public static Font police1=new Font("arial",Font.ITALIC,8);
	
	public static JLabel qr=new JLabel();
	
	public static JButton imp=new JButton("IMPRIMER");
	public static JFrame main= new JFrame();
	
	public static JScrollPane scrollPane ;
	public String mention;
	public  DeriereCarte(String mention) {
		this.mention=mention;
	}
	
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) { 
		     RecuperInfo feed = (RecuperInfo)value;    	     
		     titre=new JTextArea("\n         "
					   + "FACULTE DES SCIENCES\n"
                 + "                "
                 + "VISITE MEDICALE \n"
                 + "          "
                 + "MEDECINE PREVENTIVE");
			
			text1=new JTextArea("             "
					+ "Nul ne peut se présenter à l'examen s'il n'a pas subi la visite\n"
					+ "        "
                  + "médicale organisée par le Service de la médecine préventive,\n\n"
                  + "                                    "
                  + "     Signature du Médecin \n\n\n\n\n"
                  + "               "
                  + "NB: il n'est delivré qu'une seule carte pendant l'année,\n"
                  + "l'interessé doit faire une déclaration auprès de la police en cas de perte");	
			//paneBas[i].add(nomSco[i]);
			
			paneTab=new JPanel();
			paneTab.setBorder(tite1);
			//qr.setBorder(tite1);
			paneTab.setBackground(Color.WHITE);
			paneTab.setLayout(new FlowLayout(FlowLayout.LEFT));
			qr.setPreferredSize(new Dimension(40,55));
			titre.setPreferredSize(new Dimension(200,55));
			text1.setPreferredSize(new Dimension(245,100));
			titre.setFont(police);
			text1.setFont(police1);
			paneTab.setPreferredSize(new Dimension(255,175));
			paneTab.add(qr);
			paneTab.add(titre);
			paneTab.add(text1);
			
		     String textValue=feed.nume+"/"+mention+"/M.I.S.S/"
		     +feed.choix_1+"/"+feed.choix_2+"/2019-2020/facSciences/.";
		     BitMatrix bitMatrix;
		    	BufferedImage bitmap = null;
		    	QRCodeWriter write=new QRCodeWriter();
		    	try {
		    		
		    		Map<EncodeHintType, Object> hints=new HashMap<EncodeHintType,Object>(1);
		    		/*hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		    		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		    		hints.put(EncodeHintType.MARGIN, 1);
		    		*/
		        	int bitMatrixWidth = 40;
		        	int bitMatrixHeight= 40;
					bitMatrix = write.encode(textValue, 
							BarcodeFormat.QR_CODE, bitMatrixWidth, bitMatrixHeight,hints);
					bitmap=MatrixToImageWriter.toBufferedImage(bitMatrix);
					 qr.setIcon(new ImageIcon(bitmap));
		    	} catch (Exception e) {
				}
			
		    paneTab.setLayout(new FlowLayout(FlowLayout.LEFT));
		      // panel.setBackground(Color.cyan);  
		       //panel.add(new JLabel(" " + feed.name+ " " + feed.url + "Articles " + feed.articles.length + ""));     
		      //panel.add(showButton);   
		   return paneTab;   
		} 
		}