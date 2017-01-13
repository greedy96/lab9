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
			System.out.println("Brak argumentów w lini poleceñ.\n"
					+ "Aby poprawnie wyœwietiæ infromacje o okreœlonym poœle\n"
					+ "u¿yj sygnatury \"numer_kadencji -iwb imiê nazwisko\"\n"
					+ "gdzie w polu numer_kadencji wpisz 7, 8 lub -, jeœli chcesz wyœwietliæ informacje dla obu kadencji\n"
					+ "nastêpnie wyierz jedn¹ lub wiêcej opcji -iwb, gdzie\n"
					+ "-i oznacza niektóre informacje o danym poœle\n"
					+ "-w suma wydatków pos³a\n"
					+ "-b wysokoœæ wydatków na \'drobne naprawy i remonty bura poselskiego\'\n"
					+ "potem podaj imiê i nazwisko pos³a\n"
					+ "mo¿esz jednoczeœnie wyœwietliæ dodatkowe informacje o pos³ach:\n"
					+ "-srednia œrednia wartoœæ sumy wydatków wszystkich pos³ów w podanej kadencji\n"
					+ "-podroze wyœwietla imiê i nazwisko pos³a/pos³anki który wykona³ najwiêcej podró¿y zagranicznych\n"
					+ "-dlugosc pose³/pos³anka który najd³u¿ej przebywa³ po za granic¹\n"
					+ "-cena pose³/pos³anka, który odby³ najdro¿sz¹ podró¿ zagraniczn¹\n"
					+ "-wlochy lista wszystkich pos³ów którzy odwiedzili w³ochy"
					+ "Przyk³adowe wywo³anie programu to: \"8 -wb Jan Kowalski -srednia -wlochy\"");}
			else if (args.length<2){
				System.out.println("Za ma³o argumentów w lini poleceñ");
				
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
                    "Dostêpne kadencjê: 7, 8 lub - dla obu kadencji.\n");
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
		if (error==true) System.out.println("B³êdne argumenty poleceñ");
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
				if(argumenty[1]) System.out.println("\t-Suma wszystkich wydatków pos³a/pos³anki: "+posel.getSumaWydatkow()+"z³");
				if(argumenty[2]) System.out.println("\t-Wysokoœæ wydatków na \'drobne naprawy i remonty bura poselskiego\': "+posel.getBiuroWydatki()+"z³");
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
				System.out.println("Œrednia wartoœæ sumy wydatków wszystkich pos³ów w podanej kadencji: "+wydatkiPoslow+"z³");
			}
			if(argumenty[4]){
				int x=-1, liczba=0;
				for (int i=0; i<poslowieArray.length; i++){
					if (poslowieArray[i].getWyjazdy().size()>liczba) {x=i; liczba=poslowieArray[i].getWyjazdy().size();}
				}
				System.out.println("pos³e³/pos³anka który wykona³ najwiêcej podró¿y zagranicznych: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
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
				System.out.println("Pose³/pos³anka, który odby³ najdro¿sz¹ podró¿ zagraniczn¹: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
			}
			if(argumenty[6]){
				int x=-1;
				float liczba=0;
				for (int i=0; i<poslowieArray.length; i++){
					for (int j=0; j<poslowieArray[i].getWyjazdy().size(); j++){
						if (poslowieArray[i].getWyjazdy().get(j).getKoszt_suma()>liczba) {x=i; liczba=poslowieArray[i].getWyjazdy().get(j).getKoszt_suma();}
					}
				}
				System.out.println("Pose³/pos³anka, który odby³ najdro¿sz¹ podró¿ zagraniczn¹: " +poslowieArray[x].getImiePierwsze()+" "+poslowieArray[x].getNazwisko());
			}
			if(argumenty[7]){
				List<Integer> lista = new LinkedList<>();
				for (int i=0; i<poslowieArray.length; i++){
					for (int j=0; j<poslowieArray[i].getWyjazdy().size(); j++){
						if (poslowieArray[i].getWyjazdy().get(j).getKraj().equals("W³ochy") && !lista.contains(i)) lista.add(i);
					}
				}
				System.out.println("lista wszystkich pos³ów którzy odwiedzili w³ochy: "+lista.size());
				for (Integer list:lista){
					System.out.print(poslowieArray[list].getImiePierwsze()+" "+poslowieArray[list].getNazwisko()+", ");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Wyst¹pi³ problem z po³¹czeniem");
		}
		
		
	}
}
