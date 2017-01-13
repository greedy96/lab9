package lab9;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PoselFromJoin {
	public void parse(Map<String,Posel> poslowie, int ID) throws Exception{
		
			String json = new UrlReader().readUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+ID+".json?layers[]=wyjazdy&layers[]=wydatki");	
			
			JSONObject obj = new JSONObject(json);
			Posel posel = new Posel();
			posel.setId(obj.getInt("id"));
			JSONObject obj2 = obj.getJSONObject("data");
			posel.setImiePierwsze(obj2.getString("poslowie.imie_pierwsze"));
			posel.setImieDrugie(obj2.getString("poslowie.imie_drugie"));
			posel.setNazwisko(obj2.getString("poslowie.nazwisko"));
			JSONArray jsonArray = obj2.getJSONArray("poslowie.kadencja");
			int [] kadencja = new int[jsonArray.length()];
			for(int j=0; j<jsonArray.length();j++){
				kadencja[j]=jsonArray.getInt(j);
			}
			posel.setKadencja(kadencja);
			setWyjazdy(posel,obj.getJSONObject("layers"));
			setWydatki(posel,obj.getJSONObject("layers"));
			poslowie.put(posel.getImiePierwsze()+posel.getNazwisko(), posel);
		
	}

	private void setWydatki(Posel posel, JSONObject obj) {
		obj=obj.getJSONObject("wydatki");
		JSONArray jsonArray = obj.getJSONArray("roczniki");
		for (int i=0;i<jsonArray.length();i++){
			JSONArray jsonPola = jsonArray.getJSONObject(i).getJSONArray("pola");
			float[] pola = new float[jsonPola.length()];
			for (int j=0;j<jsonPola.length();j++){
				pola[j]=(float)jsonPola.getDouble(j);
			}
			Wydatki wydatki = new Wydatki(pola,jsonArray.getJSONObject(i).getInt("dokument_id"),jsonArray.getJSONObject(i).getInt("rok"));
			posel.setWydatki(wydatki);
		}
	}

	private void setWyjazdy(Posel posel, JSONObject obj) {
		JSONArray jsonArray = obj.optJSONArray("wyjazdy");
		
		if (jsonArray != null) 
			for (int i=0;i<jsonArray.length();i++){
				Wyjazdy wyjazd = new Wyjazdy(jsonArray.getJSONObject(i).getString("kraj"),jsonArray.getJSONObject(i).getInt("liczba_dni"),(float) jsonArray.getJSONObject(i).getDouble("koszt_suma"));
				posel.setWyjazdy(wyjazd);
			}
	}
	
}
