

import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import java.util.HashMap;

class FletteTrad implements Runnable {

    CountDownLatch antTraader; //CountDownLatch objekt
    private Monitor2 monitor;
    public FletteTrad(Monitor2 m, CountDownLatch ant){
        monitor = m;
        antTraader = ant;
    }

    @Override
    public void run(){

        while (antTraader.getCount() > 0){

            ArrayList<HashMap<String, Subsekvens>> hashmaps = monitor.hentUtTo();

            if (hashmaps == null){
                return;
            }

            HashMap<String, Subsekvens> flettetHashMap = monitor.flettSammen(hashmaps.get(0), hashmaps.get(1));
            monitor.settInnFlettet(flettetHashMap);
            antTraader.countDown(); //counter ned
            System.out.println("SETTET INN FLETTET");
        }
        System.out.println("fletter ferdig");
    }
    
}