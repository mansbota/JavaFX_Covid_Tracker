package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja osobu definiranu imenom, prezimenom i drugim podacima
 */
public class Osoba extends ImenovaniEntitet {
    private String prezime;
    private LocalDate datumRodjenja;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;

    /**
     * Koristi se za kreirenje instance osobe (builder pattern)
     */
    public static class Builder {
        private String ime;
        private String prezime;
        private Long id;
        private LocalDate datumRodjenja;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;

        /**
         * Inicijalizira builder sa imenom i prezimenom u konstruktoru jer su to potrebni podaci
         * @param ime Ime osobe
         * @param prezime Prezime osobe
         */
        public Builder(String ime, String prezime, Long id) {
            this.ime = ime;
            this.prezime = prezime;
            this.id = id;
        }

        /**
         * Specificira starost osobe
         * @param datumRodjenja Datum rodjenja osobe
         * @return Vraca referencu na sebe za buduce koristenje
         */
        public Builder withStarost(LocalDate datumRodjenja) {
            this.datumRodjenja = datumRodjenja;
            return this;
        }

        /**
         * Specificira bolest osobe
         * @param bolest Bolest osobe
         * @return Vraca referencu na sebe za buduce koristenje
         */
        public Builder withBolest(Bolest bolest) {
            this.zarazenBolescu = bolest;
            return this;
        }

        /**
         * Specificira zupaniju prebivalista osobe
         * @param zupanija Zupanija prebivalista osobe
         * @return Vraca referencu na sebe za buduce koristenje
         */
        public Builder inZupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        /**
         * Specificira listu kontaktiranih osoba osobe
         * @param kontaktiraneOsobe Lista kontaktiranih osoba osobe
         * @return Vraca referencu na sebe za buduce koristenje
         */
        public Builder withKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        /**
         * Inicijalizira osobu sa specificiranim podacima. Ako je osoba zarazena virusom, virus prelazi na kontaktirane osobe.
         * @return Vraca referencu na inicijaliziranu osobu
         */
        public Osoba build() {
            Osoba osoba             = new Osoba(ime, id);

            osoba.zarazenBolescu    = zarazenBolescu;
            osoba.prezime           = prezime;
            osoba.datumRodjenja     = datumRodjenja;
            osoba.kontaktiraneOsobe = kontaktiraneOsobe;
            osoba.zupanija          = zupanija;

            if (kontaktiraneOsobe != null && zarazenBolescu instanceof Virus virus) {
                for (Osoba konOsoba : kontaktiraneOsobe) {
                    virus.prelazakZarazeNaOsobu(konOsoba);
                }
            }

            return osoba;
        }
    }

    public Osoba(String ime, Long id) {
        super(ime, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Osoba osoba = (Osoba) o;
        return Objects.equals(prezime, osoba.prezime) &&
                Objects.equals(datumRodjenja, osoba.datumRodjenja) &&
                Objects.equals(zupanija, osoba.zupanija) &&
                Objects.equals(zarazenBolescu, osoba.zarazenBolescu) &&
                Objects.equals(kontaktiraneOsobe, osoba.kontaktiraneOsobe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prezime, datumRodjenja, zupanija, zarazenBolescu, kontaktiraneOsobe);
    }

    @Override
    public String toString() {
        return getNaziv() + " " + getPrezime();
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getStarost() {
        return datumRodjenja;
    }

    public void setStarost(LocalDate starost) {
        this.datumRodjenja = datumRodjenja;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }
}