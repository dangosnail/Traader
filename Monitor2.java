
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class Monitor2 {

    Lock laas = new ReentrantLock();
    Condition tomBeholder = laas.newCondition();
    int teller = 0;
    int forventetFlettinger;

    private SubsekvensRegister subsekvensRegister;
    public Monitor2(SubsekvensRegister subsekvensRegister){
        this.subsekvensRegister = subsekvensRegister;
    }

    //Metode som tar ut to subsekvenser og legger til i egen beholder som skal returneres
    public ArrayList<HashMap<String, Subsekvens>> hentUtTo(){
        laas.lock();
        try {
            while (antHashMap() < 2){
                System.out.println("venter");
                if (ferdig()){
                    return null;
                }
                //if ferdig() //
                // return null også har du en if i trad som hvis henttout returnerer null stopper tråden (return;)
                tomBeholder.await();
            }
            System.out.println("venter ikke");
            ArrayList<HashMap<String, Subsekvens>> hentUtTo = new ArrayList<HashMap<String, Subsekvens>>();
            hentUtTo.add(subsekvensRegister.taUtSubsekvenser());
            hentUtTo.add(subsekvensRegister.taUtSubsekvenser());
            teller++;
            return hentUtTo;
       
        } catch (InterruptedException e){
            System.out.println(e);
            return null;
        } finally {
            laas.unlock();
        }
    }

    public boolean ferdig(){
        return forventetFlettinger == teller;
    }
    //ferdig ()
    //if (teller == forventet antall flettinger) //dette antallet kan sendes inn i monitor

    public void settAntFlettinger(int ant){
        forventetFlettinger = ant;
    }
    
    //Monitor2 to sin 'settInnSubsekvenser()' metode
    public void settInnFlettet(HashMap<String, Subsekvens> hashmap){
        laas.lock();
        try {
            subsekvensRegister.settInnSubsekvenser(hashmap);
            if (antHashMap() > 1 || ferdig()){
                tomBeholder.signalAll();
            } 
       
        } finally {
            laas.unlock();
        }
    }

    //Monitor1 to sin 'settInnSubsekvenser()' metode
    public void settInn(HashMap<String, Subsekvens> hashmap){
        laas.lock();
        try {
            subsekvensRegister.settInnSubsekvenser(hashmap);
            tomBeholder.signalAll(); //join();

        } finally {
            laas.unlock();
        }
    } 

    public HashMap<String, Subsekvens> taUtSubsekvenser(){
        laas.lock();
        try {
            while (antHashMap() < 1){
                tomBeholder.await();
            }
            return subsekvensRegister.taUtSubsekvenser();

        } catch (InterruptedException e){
            System.out.println(e);
            return null;
        } finally {
            laas.unlock();
        }
       
    }
    public int antHashMap(){
        laas.lock();
        try {
            return subsekvensRegister.antHashMap();
        } finally {
            laas.unlock();
        }
    }
}