package main.java.sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.niti.DohvatiBolestiNit;
import main.niti.DohvatiOsobeNit;
import main.niti.DohvatiZupanijeNit;
import main.niti.SpremiOsobuNit;
import main.util.QuickAlert;
import model.Bolest;
import model.Osoba;
import model.Zupanija;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZapisOsobeKontroler {

    @FXML
    private TextField nazivOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private TextField dobOsobe;

    @FXML
    private TextField zupanijaOsobe;

    @FXML
    private TextField bolestOsobe;

    @FXML
    private TextField konOsobe;

    public void dodaj() {

        try {

            if (nazivOsobe.getText().length() <= 0) {
                QuickAlert.error("Unesite naziv!");
                return;
            }

            if (prezimeOsobe.getText().length() <= 0) {
                QuickAlert.error("Unesite prezime!");
                return;
            }

            String zupanija = zupanijaOsobe.getText();

            Optional<Zupanija> zup = DohvatiZupanijeNit.dohvatiZupanije()
                    .stream()
                    .filter(z -> z.getNaziv().toLowerCase().equals(zupanija.toLowerCase()))
                    .findAny();

            if (zup.isEmpty()) {
                QuickAlert.error("Ne postoji županija s tim imenom!");
                return;
            }

            String bolest = bolestOsobe.getText();

            Optional<Bolest> bol = DohvatiBolestiNit.dohvatiBolesti()
                    .stream()
                    .filter(z -> z.getNaziv().toLowerCase().equals(bolest.toLowerCase()))
                    .findAny();

            if (bol.isEmpty()) {
                QuickAlert.error("Ne postoji bolest s tim imenom!");
                return;
            }

            String kontaktirani = konOsobe.getText();

            List<String> konOsobeIdevi = Arrays.asList(kontaktirani.split(","));

            List<Long> listaIdeva = konOsobeIdevi.stream().map(Long::parseLong).collect(Collectors.toList());

            List<Osoba> kontaktiraneOsobe = new ArrayList<>();

            List<Osoba> osobe = DohvatiOsobeNit.dohvatiOsobe();

            if (!listaIdeva.get(0).equals(0L)) {

                for (Long id : listaIdeva) {

                    Optional<Osoba> osoba = osobe.stream().filter(o -> o.getId().equals(id)).findAny();

                    if (osoba.isEmpty()) {
                        QuickAlert.error("Unesen neispravan ID kontaktirane osobe!");
                        return;
                    }

                    kontaktiraneOsobe.add(osoba.get());
                }
            }

            Osoba osoba = new Osoba.Builder(nazivOsobe.getText(), prezimeOsobe.getText(), 1L)
                    .inZupanija(zup.get())
                    .withBolest(bol.get())
                    .withKontaktiraneOsobe(kontaktiraneOsobe)
                    .withStarost(LocalDate.parse(dobOsobe.getText()))
                    .build();

            SpremiOsobuNit.spremiOsobu(osoba);

            QuickAlert.success("Osoba dodana.");

        }
        catch (NumberFormatException ex) {
            QuickAlert.error("Neispravan unos ID-eva kontaktiranih osoba!" +
                    " Unesite u formatu 1,2... ili 0 ako ih nema.");
        }
        catch (DateTimeParseException ex) {
            QuickAlert.error("Neispravan unos datuma rodjena. Unesite u formatu yyyy-mm-dd.");
        }
    }
}
