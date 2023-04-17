
class Subsekvens {

    private final String SUBSEKVENS; //konstant f.eks. ABC
    public int antForekomster = 1; //forekomst blir automatisk 1 når objekt opprettes
    public Subsekvens(String subsekvens){
        SUBSEKVENS = subsekvens;

    }
    //Henter int
    public int hentAntForekomster(){
        return antForekomster;
    }
    //Endrer int
    public void endreAntForekomster(int nyAnt){
        antForekomster = nyAnt;
    }
    //toString
    public String toString(){
        return SUBSEKVENS + "," + antForekomster;
    }
}