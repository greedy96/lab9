package lab9;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

public class PoselMapBuilder {
	public Map<String,Posel> parse(int kadencja) throws Exception{	
		String json;
		if(kadencja==-1)
			{json = new UrlReader().readUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");}
		else
			{json = new UrlReader().readUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+kadencja);}
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
		Map<String,Posel> poslowie = new LinkedHashMap<>();
		ExecutorService ex = Executors.newFixedThreadPool(50);
        for (int ID:list) {
            Runnable worker = new PoselRunner(poslowie, ID);
            ex.execute(worker);
        }
        ex.shutdown();

        while (!ex.isTerminated()) {
        }
		
	return poslowie;
	}
}
