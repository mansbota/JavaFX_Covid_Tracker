package model;

import java.util.List;
import java.util.Objects;

/**
 * Predstavlja bolest definiranu nazivom i simptomima
 */
public class Bolest extends ImenovaniEntitet {
    protected List<Simptom> simptomi;

    /**
     * Inicijalizira bolest nazivom bolesti i listom simptoma
     * @param naziv Naziv bolesti
     * @param simptomi Lista simptoma bolesti
     */
    public Bolest(String naziv, List<Simptom> simptomi, Long id) {
        super(naziv, id);
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return  naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bolest bolest = (Bolest) o;
        return Objects.equals(simptomi, bolest.simptomi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), simptomi);
    }

    public List<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(List<Simptom> simptomi) {
        this.simptomi = simptomi;
    }
}
