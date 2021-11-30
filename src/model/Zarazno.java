package model;

/**
 * Predstavlja sucelje za zarazne bolesti
 */
public interface Zarazno {
    /**
     * Bolesti koje implementiraju ovo sucelje definiraju metodu za prelazak bolesti na osobu
     * @param osoba Osoba na koju bolest prelazi
     */
    void prelazakZarazeNaOsobu(Osoba osoba);
}
