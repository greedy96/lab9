package lab9;

import java.util.Map;

public class PoselRunner implements Runnable{

	private Map<String, Posel> poslowie;
    private int ID;

    public PoselRunner(Map<String, Posel> poslowie, int ID) {
        this.poslowie = poslowie;
        this.ID=ID;
    }

    @Override
    public void run(){
        try {
            new PoselFromJoin().parse(poslowie, ID);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
