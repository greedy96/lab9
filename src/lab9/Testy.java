package lab9;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class Testy {

	@Test
	public void test() throws Exception {
		
			String json = new UrlReader().readUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=8");	
			List<Integer> list = new LinkedList<>();
			JSONObject obj = new JSONObject(json);
			String lastUrl=obj.getJSONObject("Links").optString("last");
			String nextUrl=obj.getJSONObject("Links").optString("next");
			while (!lastUrl.equals(nextUrl)){
				 obj = new JSONObject(json);
				JSONArray arr = obj.getJSONArray("Dataobject");
				for (int i = 0; i < arr.length(); i++){
					list.add(arr.getJSONObject(i).getInt("id"));
				}
				nextUrl=obj.optJSONObject("Links").optString("next");
				json = new UrlReader().readUrl(nextUrl);
			}
			
			
	}

}
