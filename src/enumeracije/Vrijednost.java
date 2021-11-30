package enumeracije;

/**
 * Enumeracija za ucestalost simptoma
 */
public enum Vrijednost {

    Produktivni ( "Produktivni"),
    Intenzivno ("Intenzivno"),
    Visoka ("Visoka"),
    Jaka ("Jaka");

    private final String ucestalost;

    Vrijednost(String ucestalost){
        this.ucestalost = ucestalost;
    }

    public String getUcestalost() {
        return ucestalost;
    }
}
