
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.io.FileNotFoundException;

class LeseTrad implements Runnable {
    
    private String filnavn;
    private Monitor2 monitor;
    public LeseTrad(String fil, Monitor2 m){
        monitor = m;
        filnavn = fil;
    }

    //Run metode som skal lese fra fil og legge til resulterende hashmap
    @Override
    public void run(){

        try {
            File nyFil = new File(filnavn);
            Scanner sc = new Scanner(nyFil); //Lese fil

            HashMap<String, Subsekvens> hashmap = new HashMap<>();

            while (sc.hasNextLine()){
                String linje = sc.nextLine();
    
                for (int i = 0; i < linje.length() - 2; i++){ 
                    String str = linje.substring(i, i + 3);
                    Subsekvens subsekvens = new Subsekvens(str);
                    hashmap.put(str, subsekvens);
                    //System.out.println("LESFIL: " + str + " " + subsekvens);
                }
            } monitor.settInn(hashmap); sc.close();

        } catch (FileNotFoundException e){
            System.out.println("Fil ikke funnet " + e);
        }
    }
}