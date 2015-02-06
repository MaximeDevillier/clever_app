package clever_app;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.json.*;

public abstract class CleverRequest {
	
	public static void main(String[] args){
		try {
			String resp = getResponse();
			JSONArray JSONresp = Parsing(resp);
			double answer = getAverageNumberOfStudentsPerSection(JSONresp);
			System.out.println("\nTherefore the average number of students per section is " + answer);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the string response from the API (sections list) 
	 */
	public static String getResponse() throws ClientProtocolException, IOException{
		String body = Request.Get("https://api.clever.com/v1.1/sections?limit=1000")
				.addHeader("Authorization", "Bearer DEMO_TOKEN").execute().returnContent().asString();
		//System.out.println(body);
		return body;
	}
	
	/**
	 * Converts the response text into a JSONArray
	 */
	public static JSONArray Parsing(String searchResponseJSON) {
		JSONObject response = null;
		try {
			response = new JSONObject(searchResponseJSON);
			JSONArray JA = (JSONArray) response.get("data");
			
			return JA;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	/**
	 * Gets the Average Number Of Students Per Section
	 * @param jsonArray   Sections JsonArray
	 */
	public static double getAverageNumberOfStudentsPerSection(JSONArray jsonArray) {		
		
		int studentsCount=0;
		int SectionsCount = jsonArray.length();
		System.out.println("\nThere are currently " + SectionsCount + " sections");

	      for (int i=0; i < SectionsCount; i++) {
	    	  
	          JSONObject data = null;
	          try {
 
	        	  data = jsonArray.getJSONObject(i); 
	        	  //students is an array of all the student IDs
	        	  //we are gonna count those IDs
	        	  studentsCount += data.getJSONObject("data").getJSONArray("students").length();
	          	
	          } catch (Exception e) {
	              e.printStackTrace();
	              continue;
	          }
	      }
	      System.out.println("\nThere are currently " + studentsCount + " students who joined a section.");
	      
	      return studentsCount/SectionsCount;
	  }

}
