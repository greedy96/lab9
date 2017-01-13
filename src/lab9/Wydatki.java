package lab9;

public class Wydatki {

	private float[] pola;
	private int dokument_id;
	private int rok;
	
	Wydatki(float[] pola, int dokument_id, int rok){
		this.pola=pola;
		this.dokument_id=dokument_id;
		this.rok=rok;
	}
	
	public float[] getPola(){
		return pola;
	}
	
	public int getDokument_id(){
		return dokument_id;
	}
	
	public int getRok(){
		return rok;
	}

}
