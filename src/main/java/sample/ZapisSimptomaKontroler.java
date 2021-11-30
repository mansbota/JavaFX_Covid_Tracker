package main.java.sample;

import enumeracije.Vrijednost;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.niti.DohvatiSimptomeNit;
import main.niti.SpremiSimptomNit;
import main.util.QuickAlert;
import model.Simptom;
import java.util.Optional;

public class ZapisSimptomaKontroler {

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TextField ucestalostSimptoma;

    public void dodaj() {

        try {

            if (nazivSimptoma.getText().length() <= 0) {
                QuickAlert.error("Unesite naziv!");
                return;
            }

            Optional<Simptom> sim = DohvatiSimptomeNit.dohvatiSimptome()
                    .stream()
                    .filter(s -> s.getNaziv().equals(nazivSimptoma.getText()))
                    .findAny();

            if (sim.isPresent()) {
                QuickAlert.error("Već postoji simptom s istim imenom!");
                return;
            }

            Vrijednost ucestalost = Vrijednost.valueOf(ucestalostSimptoma.getText());

            SpremiSimptomNit.spremiSimptom(new Simptom(nazivSimptoma.getText(), ucestalost, 1L));

            QuickAlert.success("Simptom dodan.");

        }
        catch (IllegalArgumentException ex) {
            QuickAlert.error("Kriva vrijednost!");
        }
    }
}
