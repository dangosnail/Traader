
import java.util.ArrayList;
import java.util.HashMap;

class FletteTrad implements Runnable {
    private Monitor2 monitor;
    public FletteTrad(Monitor2 m){
        monitor = m;
    }

    @Override
    public void run(){
        while (!monitor.ferdig()){
            ArrayList<HashMap<String, Subsekvens>> hashmaps = monitor.hentUtTo();
            if (hashmaps == null){
                System.out.println("Fletter ferdig");
                return;
            }
            HashMap<String, Subsekvens> flettetHashMap = monitor.flettSammen(hashmaps.get(0), hashmaps.get(1));
            monitor.settInnFlettet(flettetHashMap);
        }
    }  
}