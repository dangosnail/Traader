

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

            HashMap<String, Subsekvens> flettetHashMap = new HashMap<String, Subsekvens>();
            ArrayList<HashMap<String, Subsekvens>> hashmaps = monitor.hentUtTo();

            if (hashmaps == null){
                return;
            }

            for (HashMap<String, Subsekvens> hashmap : hashmaps){

                for (HashMap.Entry<String, Subsekvens> data : hashmap.entrySet()){
                    String str = data.getKey();
                    Subsekvens s = data.getValue();
                    if (!flettetHashMap.containsKey(str)){
                        flettetHashMap.put(str, s);
                    
                    } else {
                        //Hvis subsekvensen allerede har forekommet før, så finner vi ant forekomster begge steder og endrer
                        //ant forekomster i beholderen til de to verdiene tilsammen
                        for (HashMap.Entry<String, Subsekvens> hData : flettetHashMap.entrySet()){
                            String strData = hData.getKey();
                            Subsekvens sData = hData.getValue();    
                            if (strData.equals(str)){
                                int nyAnt = sData.hentAntForekomster() + s.hentAntForekomster(); //ikke +1
                                sData.endreAntForekomster(nyAnt);
                                //System.out.println("heeereee " + strData + " " + nyAnt);
                            
                            }
                        }
                    }
                }
            } 
            monitor.settInnFlettet(flettetHashMap);
            antTraader.countDown(); //counter ned
            System.out.println("SETTET INN FLETTET");
        }
        System.out.println("fletter ferdig");
    }
    
}