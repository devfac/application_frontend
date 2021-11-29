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
	URL url = new URL("http://localhost/api/v1/anne_univ/?skip=0&limit=100");
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
}
