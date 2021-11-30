package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predsavlja abstrakciju za klase koje koriste naziv
 */
public abstract class ImenovaniEntitet implements Serializable, Comparable<ImenovaniEntitet> {
    protected String naziv;
    protected Long id;

    @Override
    public int compareTo(ImenovaniEntitet o) {
        return id.compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImenovaniEntitet that = (ImenovaniEntitet) o;
        return Objects.equals(naziv, that.naziv) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, id);
    }

    @Override
    public String toString() {
        return naziv;
    }

    /**
     * Inicijalizira entitet nazivom
     * @param naziv Naziv entiteta
     */
    protected ImenovaniEntitet(String naziv, Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() { return naziv; }

    public void setNaziv(String naziv) { this.naziv = naziv; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
