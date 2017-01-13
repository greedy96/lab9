package lab9;

import java.util.LinkedList;
import java.util.List;

public class Posel {

    private int id;
    private String imiePierwsze;
    private String imieDrugie;
    private String nazwisko;
    private int[] kadencja;
    private List<Wyjazdy> wyjazdy = new LinkedList<>();
    private List<Wydatki> wydatki = new LinkedList<>();
    

    public float getBiuroWydatki(){
    	float wydatek=0;
    	for(int i=0; i<wydatki.size(); i++){
    		wydatek+=wydatki.get(i).getPola()[12];
    	}
    	return wydatek;
    }
    
    public float getSumaWydatkow(){
    	float wydatek=0;
    	for (int i=0; i<wydatki.size(); i++){
    		float[] pola=wydatki.get(i).getPola();
    		for (int j=0; j<pola.length; j++){
    			wydatek+=pola[j];
    		}
    	}
    	return wydatek;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Wyjazdy> getWyjazdy() {
        return wyjazdy;
    }

    public void setWyjazdy(Wyjazdy wyjazdy) {
        this.wyjazdy.add(wyjazdy);
    }

    public List<Wydatki> getWydatki() {
        return wydatki;
    }

    public void setWydatki(Wydatki wydatki) {
        this.wydatki.add(wydatki);
    }

    public String getImiePierwsze(){
        return this.imiePierwsze;
    }

    public String getImieDrugie(){
        return this.imieDrugie;
    }

    public String getNazwisko(){
        return this.nazwisko;
    }

    public void setImiePierwsze(String imiePierwsze) {
        this.imiePierwsze = imiePierwsze;
    }

    public void setImieDrugie(String imieDrugie) {
        this.imieDrugie = imieDrugie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    public void setKadencja(int[] kadencja){
    	this.kadencja=kadencja;
    }
    
    public int[] getKadencja(){
    	return kadencja;
    }
}
