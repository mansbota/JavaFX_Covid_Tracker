package model;

import java.util.List;

/**
 * Predstavlja podtip bolesti (Virus). Virus je zarazan.
 */
public class Virus extends Bolest implements Zarazno {
    /**
     * Inicijalizira virus imenom i simptomima
     * @param naziv Ime virusa
     * @param simptomi Simptomi virusa
     */
    public Virus(String naziv, List<Simptom> simptomi, Long id) {
        super(naziv, simptomi, id);
    }

    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(this);
    }
}
