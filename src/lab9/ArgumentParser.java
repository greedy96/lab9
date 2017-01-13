package lab9;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArgumentParser {
	
	private int kadencja;
	private String [] args;
	private boolean error=false;
	private String nazwaPosla;
	private boolean[] argumenty = new boolean[8]; 
	
	public void parse(String [] args){
		this.args=args;
		if (args.length==0){
			System.out.println("Brak argument�w w lini polece�.\n"
					+ "Aby poprawnie wy�wieti� infromacje o okre�lonym po�le\n"
					+ "u�yj sygnatury \"numer_kadencji -iwb imi� nazwisko\"\n"
					+ "gdzie w polu numer_kadencji wpisz 7, 8 lub -, je�li chcesz wy�wietli� informacje dla obu kadencji\n"
					+ "nast�pnie wyierz jedn� lub wi�cej opcji -iwb, gdzie\n"
					+ "-i oznacza niekt�re informacje o danym po�le\n"
					+ "-w suma wydatk�w pos�a\n"
					+ "-b wysoko�� wydatk�w na \'drobne naprawy i remonty bura poselskiego\'\n"
					+ "potem podaj imi� i nazwisko pos�a\n"
					+ "mo�esz jednocze�nie wy�wietli� dodatkowe informacje o pos�ach:\n"
					+ "-srednia �rednia warto�� sumy wydatk�w wszystkich pos��w w podanej kadencji\n"
					+ "-podroze wy�wietla imi� i nazwisko pos�a/pos�anki kt�ry wykona� najwi�cej podr�y zagranicznych\n"
					+ "-dlugosc pose�/pos�anka kt�ry najd�u�ej przebywa� po za granic�\n"
					+ "-cena pose�/pos�anka, kt�ry odby� najdro�sz� podr� zagraniczn�\n"
					+ "-wlochy lista wszystkich pos��w kt�rzy odwiedzili w�ochy"
					+ "Przyk�adowe wywo�anie programu to: \"8 -wb Jan Kowalski -srednia -wlochy\"");}
			else if (args.length<2){
				System.out.println("Za ma�o argument�w w lini polece�");
				
			}
			else kadencja();
		}

	private void kadencja() {
		switch (args[0]) {
        case "7":
            this.kadencja=7;
        	parseArgs();
            break;
        case "8":
            this.kadencja=8;
        	parseArgs();
            break;
        case "-":
             this.kadencja=-1;
        	parseArgs();
            break;
        default:
            System.out.print("Podano niepoprawny numer kadencji.\n" +
                    "Dost�pne kadencj�: 7, 8 lub - dla obu kadencji.\n");
    }
	}

	private void parseArgs() {
		for (int i=1; i<args.length; i++){
			switch (args[i]){
			case "-srednia":
				argumenty[3]=true;
				break;
			case "-podroze":
				argumenty[4]=true;
				break;
			case "-dlugosc":
				argumenty[5]=true;
				break;
			case "-cena":
				argumenty[6]=true;
				break;
			case "-wlochy":
				argumenty[7]=true;
				break;
			default:
			{
				if (args[i].toString().startsWith("-")){
					for (int j=1; j<args[i].length(); j++)
						switch (args[i].charAt(j)){
						case 'i':
							argumenty[0]=true;
							break;
						case 'w':
							argumenty[1]=true;
							break;
						case 'b':
							argumenty[2]=true;
							break;
						default:
							this.error=true;	
						}
					if (i+2<args.length){
						nazwaPosla=args[i+1]+args[i+2];
						i+=2;
					}
					else this.error=true;
				}
				else {
					this.error=true;
				}
			}
			}
		}
		if (error==true) System.out.println("B��dne argumenty polece�");
		else unsubscribe();
	}

	private void unsubscribe() {
		Map<String, Posel> poslowie;
		try {
			poslowie = (new PoselMapBuilder()).parse(this.kadencja);
			if (nazwaPosla != null && poslowie.containsKey(nazwaPosla)){
				Posel posel =poslowie.get(nazwaPosla);
				System.out.println(posel.getImiePierwsze()+" "+posel.getNazwisko()+":");
				if(argumenty[0]) System.out.println("\t-ID: "+posel.getId());
				if(argumenty[1]) System.out.println("\t-Suma wszystkich wydatk�w pos�a/pos�anki: "+posel.getSumaWydatkow()+"z�");
				if(argumenty[2]) System.out.println("\t-Wysoko�� wydatk�w na \'drobne naprawy i remonty bura poselskiego\': "+posel.getBiuroWydatki()+"z�");
				System.out.println("\n");
			}
			Posel[] poslowieArray=new Posel[poslowie.size()];
			poslowie.values().toArray(poslowieArray);
			if(argumenty[3]) {
				float wydatkiPoslow=0;
				
				for (int i=0; i<poslowieArray.length; i++){
					wydatkiPoslow+=poslowieArray[i].getSumaWydatkow();
				}
				wydatkiPoslow/=poslowieArray.length;
				System.out.println("�rednia warto�� sumy wydatk�w wszystkich pos��w w podanej kadencji: "+wydatkiPoslow+"z�");
			}
			if(argumenty[4]){
				int x=-1, liczba=0;
				for (int i=0; i<poslowieArray.length; i++){
					if (poslowieArray[i].getWyjazdy().size()>liczba) {x=i; liczba=poslowieArray[i].getWyjazdy().size();}
				}
				System.out.println("pos�e�/pos�anka kt�ry wykona� najwi�cej podr�y zagranicznych: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
			}
			if(argumenty[5]){
				int x=-1, liczba=0,dni;
				for (int i=0; i<poslowieArray.length; i++){
					dni=0;
					for (int j=0; j<poslowieArray[i].getWyjazdy().size(); j++){
						dni+=poslowieArray[i].getWyjazdy().get(j).getLiczba_dni();
					}
					if (dni>liczba) {liczba=dni; x=i;}
				}
				System.out.println("Pose�/pos�anka, kt�ry odby� najdro�sz� podr� zagraniczn�: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
			}
			if(argumenty[6]){
				int x=-1;
				float liczba=0;
				for (int i=0; i<poslowieArray.length; i++){
					for (int j=0; j<poslowieArray[i].getWyjazdy().size(); j++){
						if (poslowieArray[i].getWyjazdy().get(j).getKoszt_suma()>liczba) {x=i; liczba=poslowieArray[i].getWyjazdy().get(j).getKoszt_suma();}
					}
				}
				System.out.println("Pose�/pos�anka, kt�ry odby� najdro�sz� podr� zagraniczn�: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
			}
			if(argumenty[7]){
				List<Integer> lista = new LinkedList<>();
				for (int i=0; i<poslowieArray.length; i++){
					for (int j=0; j<poslowieArray[i].getWyjazdy().size(); j++){
						if (poslowieArray[i].getWyjazdy().get(j).getKraj().equals("W�ochy") && !lista.contains(i)) lista.add(i);
					}
				}
				System.out.println("lista wszystkich pos��w kt�rzy odwiedzili w�ochy: "+lista.size());
				for (Integer list:lista){
					System.out.print(poslowieArray[list].getImiePierwsze()+" "+poslowieArray[list].getNazwisko()+", ");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Wyst�pi� problem z po��czeniem");
		}
		
		
	}
}
