package lab9;

public class Wyjazdy {
	private String kraj;
	private int liczba_dni;
	private float koszt_suma;
	
	Wyjazdy(String kraj, int liczba_dni, float koszt_suma){
		this.kraj=kraj;
		this.liczba_dni=liczba_dni;
		this.koszt_suma=koszt_suma;
	}
	
	public String getKraj(){
		return kraj;
	}
	
	public int getLiczba_dni(){
		return liczba_dni;
	}
	
	public float getKoszt_suma(){
		return koszt_suma;
	}
}
