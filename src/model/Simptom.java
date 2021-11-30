package model;

import enumeracije.Vrijednost;

import java.util.Objects;

/**
 * Predstavlja simptom definiran imenom, ucestalosti
 */
public class Simptom extends ImenovaniEntitet {
    private Vrijednost vrijednost;

    /**
     * Inicijalizira simptom imenom, ucestalosti
     * @param naziv Naziv simptoma
     * @param vrijednost Vrijednost simptoma
     */
    public Simptom(String naziv, Vrijednost vrijednost, Long id) {
        super(naziv, id);
        this.vrijednost = vrijednost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Simptom simptom = (Simptom) o;
        return vrijednost == simptom.vrijednost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vrijednost);
    }

    public Vrijednost getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(Vrijednost vrijednost) {
        this.vrijednost = vrijednost;
    }
}
