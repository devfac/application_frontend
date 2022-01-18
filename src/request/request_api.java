package request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import loading.Main;
public class request_api {
    
    public static JSONObject login(String request,String username, String password) throws IOException, URISyntaxException, InterruptedException {
		String urlParameters  = "username="+username+"&password="+password;

		byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int postDataLength = postData.length;
		URL url = new URL( request );
		InputStream inputStream;
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
		conn.setUseCaches(false);
		try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
		wr.write( postData );
		}
		int responseCode = conn.getResponseCode();
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = conn.getInputStream();
		} else {
			inputStream = conn.getErrorStream();
		}
	
		BufferedReader in = new BufferedReader(
			new InputStreamReader(
				inputStream));
	
		StringBuilder response = new StringBuilder();
		String currentLine;
	
		while ((currentLine = in.readLine()) != null) 
			response.append(currentLine);
	
		in.close();
		JSONObject jsonObject = new JSONObject(response.toString());
	return jsonObject;
}

public static JSONArray get_anne_univ() throws IOException{
	URL url = new URL("http://"+Main.host+"/api/v1/anne_univ/?skip=0&limit=100");
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	conn.setRequestProperty("Authorization","Bearer "+Main.token);
	conn.setRequestProperty("Content-Type","application/json");
	conn.setRequestMethod("GET");


	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String output;

	StringBuffer response = new StringBuffer();
	while ((output = in.readLine()) != null) {
		response.append(output);
	}

	in.close();
	JSONArray jsonObject = new JSONArray(response.toString());
	return jsonObject;
}


public static JSONObject get_Mention_by_uuid(String uuid) throws IOException{
	URL url = new URL("http://"+Main.host+"/api/v1/mentions/by_uuid?uuid="+uuid);
	HttpURLConnection conn= (HttpURLConnection) url.openConnection(); 

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization","Bearer "+Main.token);
		conn.setRequestProperty("Content-Type","application/json"); 

		BufferedReader in =new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String output;
		StringBuffer response= new StringBuffer();
		while((output=in.readLine()) !=null){
			response.append(output); 
		}
		in.close();
		JSONObject jsonObject = new JSONObject (response.toString());
		return jsonObject;	
		
}


///////////////////////////////////////////INSCRIPTION////////////////////////////////////////////

//POST//

public static JSONObject enregInsription(String request,String num_insc, String nom,String prenom,
String date_naiss,String lieu_naiss,String nation,String sexe,String situation,String num_tel,
String num_cin,String date_cin,String lieu_cin,String bacc_serie,String bacc_anne,String bacc_num,String bacc_centre,
String adress,String proffession,String nom_pere,String prof_pere,String nom_mere,String prof_mere,
String adress_parent,String photo,String montant,String num_quitance,String date_quitance,
String niveau,String parcours,String mention) throws IOException, URISyntaxException, InterruptedException {
	String urlParameters  = "num_insc="+num_insc+"&nom="+nom+"&prenom="+prenom+"&date_naiss="+date_naiss+"&lieu_naiss="+lieu_naiss+"&nation="+nation
	+"&sexe="+sexe+"&situation="+situation+"&num_tel="+num_tel+"&num_cin="+num_cin+"&date_cin="+date_cin+"&lieu_cin="+lieu_cin+"&bacc_serie="+bacc_serie+"&bacc_anne="+bacc_anne+"&bacc_num="+bacc_num
	+"&bacc_centre="+bacc_centre+"&adress="+adress+"&proffession="+proffession+"&nom_pere="+nom_pere+"&prof_pere="+prof_pere+"&nom_mere="+nom_mere+"&prof_mere="+prof_mere+"&adress_parent="+adress_parent
	+"&photo="+photo+"&montant="+montant+"&num_quitance="+num_quitance+"&date_quitance="+date_quitance+"&niveau="+niveau+"&parcours="+parcours+"&mention="+mention;

	byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
	URL url = new URL( request );
	InputStream inputStream;
	HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
	conn.setDoOutput(true);
	conn.setInstanceFollowRedirects(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
	conn.setRequestProperty("charset", "utf-8");
	try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
	wr.write( postData );
	}
	
	inputStream = conn.getErrorStream();

	BufferedReader in = new BufferedReader(
		new InputStreamReader(
			inputStream));

	StringBuilder response = new StringBuilder();
	String currentLine;

	while ((currentLine = in.readLine()) != null) 
		response.append(currentLine);

	in.close();
	JSONObject jsonObject = new JSONObject(response.toString());
return jsonObject;
}

//GET//

public static JSONObject get_etudiantIns_by_num_ins(String num_insc) throws IOException{
	URL url = new URL("http://"+Main.host+"/api/v1/nouveau_etudiants/by_num_insc?num_insc="+num_insc);
	HttpURLConnection conn= (HttpURLConnection) url.openConnection(); 

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization","Bearer "+Main.token);
		conn.setRequestProperty("Content-Type","application/json"); 

		BufferedReader in =new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String output;
		StringBuffer response= new StringBuffer();
		while((output=in.readLine()) !=null){
			response.append(output); 
		}
		in.close();
		JSONObject jsonObject = new JSONObject(response.toString());
		return jsonObject;	
		
}





//////////////////////////////////////////////REINSCRIPTION//////////////////////////////////////////

public static JSONObject enregRensription(String request,String num_insc, String nom,String prenom,
String date_naiss,String lieu_naiss,String nation,String sexe,String situation,String num_tel,
String num_cin,String date_cin,String lieu_cin,String adress,String montant,String num_quitance,String date_quitance,
String etat,String photo,String parcours,String mention,String moyenne,String bacc) throws IOException, URISyntaxException, InterruptedException {
	String urlParameters  = "num_insc="+num_insc+"&nom="+nom+"&prenom="+prenom+"&date_naiss="+date_naiss+"&lieu_naiss="+lieu_naiss+"&nation="+nation
	+"&sexe="+sexe+"&situation="+situation+"&num_tel="+num_tel+"&num_cin="+num_cin+"&date_cin="+date_cin+"&lieu_cin="+lieu_cin
	+"&adress="+adress+"&photo="+photo+"&montant="+montant+"&num_quitance="+num_quitance+"&date_quitance="+date_quitance
	+"&parcours="+parcours+"&mention="+mention+"&moyenne"+moyenne+"&bacc"+bacc;

	byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
	URL url = new URL( request );
	InputStream inputStream;
	HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
	conn.setDoOutput(true);
	conn.setInstanceFollowRedirects(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
	conn.setRequestProperty("charset", "utf-8");
	try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
	wr.write( postData );
	}
	
	inputStream = conn.getErrorStream();

	BufferedReader in = new BufferedReader(
		new InputStreamReader(
			inputStream));

	StringBuilder response = new StringBuilder();
	String currentLine;

	while ((currentLine = in.readLine()) != null) 
		response.append(currentLine);

	in.close();
	JSONObject jsonObject = new JSONObject(response.toString());
return jsonObject;
}

}
