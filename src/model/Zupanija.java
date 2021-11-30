package model;

import java.util.Objects;

/**
 * Predstavlja zupaniju definiranu brojem stanovnika, zarazenih i imenom
 */
public class Zupanija extends ImenovaniEntitet {
    private Integer brojStanovnika;
    private Integer brojZarazenih;

    /**
     * Inicijalizira zupaniju
     * @param naziv Naziv zupanije
     * @param brojStanovnika Broj stanovnika zupanije
     * @param brojZarazenih Broj zarazenih u zupaniji
     */
    public Zupanija(String naziv, Integer brojStanovnika, Integer brojZarazenih, Long id) {
        super(naziv, id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Zupanija zupanija = (Zupanija) o;
        return brojStanovnika.equals(zupanija.brojStanovnika) &&
                brojZarazenih.equals(zupanija.brojZarazenih);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brojStanovnika, brojZarazenih);
    }

    public Float getPostotakZarazenih() { return brojZarazenih.floatValue() / brojStanovnika.floatValue() * 100; }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public Integer getBrojZarazenih() { return brojZarazenih; }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public void setBrojZarazenih(Integer brojZarazenih) { this.brojZarazenih = brojZarazenih; }
}

