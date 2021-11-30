package main.java.sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.niti.DohvatiZupanijeNit;
import main.niti.SpremiZupanijuNit;
import main.util.QuickAlert;
import model.Zupanija;
import java.util.Optional;

public class ZapisZupanijeKontroler {

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TextField brojStanovnika;

    @FXML
    private TextField brojZarazenih;

    public void dodaj() {

        if (nazivZupanije.getText().length() <= 0) {
            QuickAlert.error("Unesite naziv!");
            return;
        }

        try {

            Integer brojStan = Integer.parseInt(brojStanovnika.getText());
            Integer brojZaraz = Integer.parseInt(brojZarazenih.getText());

            Optional<Zupanija> zup = DohvatiZupanijeNit.dohvatiZupanije()
                    .stream()
                    .filter(z -> z.getNaziv().equals(nazivZupanije.getText()))
                    .findAny();

            if (zup.isPresent()) {
                QuickAlert.error("Već postoji županija s istim imenom!");
                return;
            }

            if (brojStan < brojZaraz) {
                QuickAlert.error("Zaraženih mora biti manje od stanovnika!");
                return;
            }

            SpremiZupanijuNit.spremiZupaniju(new Zupanija(nazivZupanije.getText(), brojStan, brojZaraz, 1L));

            QuickAlert.success("Županija dodana.");

        } catch (NumberFormatException ex) {
            QuickAlert.error("Unesite broj u polje!");
        }
    }
}
