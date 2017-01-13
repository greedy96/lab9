package lab9;

public class MainFuntion {

	public static void main(String[] args) throws Exception {
		try{
		(new ArgumentParser()).parse(args);
		}
		catch (Exception ex){
			System.out.println(ex);
		}
		}
	}


