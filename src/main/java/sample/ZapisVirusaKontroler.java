package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.niti.DohvatiBolestiNit;
import main.niti.SpremiBolestNit;
import main.util.QuickAlert;
import model.Bolest;
import model.Simptom;
import model.Virus;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ZapisVirusaKontroler implements Initializable {

    @FXML
    private TextField nazivVirusa;

    @FXML
    private Label izabraniSimptomiLabela;

    private List<Simptom> izabraniSimptomi;

    public void dodaj() {

        if (nazivVirusa.getText().length() <= 0) {
            QuickAlert.error("Unesite naziv!");
            return;
        }

        Optional<Bolest> vir = DohvatiBolestiNit.dohvatiBolesti()
                .stream()
                .filter(b -> b.getNaziv().toLowerCase().equals(nazivVirusa.getText().toLowerCase()))
                .findAny();

        if (vir.isPresent()) {
            QuickAlert.error("Već postoji virus ili bolest s istim imenom!");
            return;
        }

        if (izabraniSimptomi == null || izabraniSimptomi.size() == 0) {
            QuickAlert.error("Izaberite bar jedan simptom!");
            return;
        }

        SpremiBolestNit.spremiBolest(new Virus(nazivVirusa.getText(), izabraniSimptomi, 1L));

        QuickAlert.success("Virus dodan.");
    }

    public void izaberiSimptome() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("odabirSimptoma.fxml"));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("app.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            OdabirSimptomaKontroler kontroler = loader.getController();
            kontroler.setKontroler(this);
            kontroler.setStage(stage);

            Main.getMainStage().hide();
        } catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    public void setIzabraniSimptomi(List<Simptom> simptomi) {

        izabraniSimptomi = simptomi;

        izabraniSimptomiLabela.setText(izabraniSimptomi.toString());

        Main.getMainStage().show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
