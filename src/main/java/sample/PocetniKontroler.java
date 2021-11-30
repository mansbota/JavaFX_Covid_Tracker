package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Osoba;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PocetniKontroler implements Initializable {

    @FXML
    private Label brojOsoba;

    @FXML
    private Label zadnjaOsoba;

    public void setBrojOsoba(Integer broj) {

        LocalDateTime localDateTime = LocalDateTime.now();

        brojOsoba.setText(broj.toString() + ", " + localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void setZadnjaOsoba(Osoba osoba) {

        LocalDateTime localDateTime = LocalDateTime.now();

        zadnjaOsoba.setText(osoba.getNaziv() + " " + osoba.getPrezime() + ", " +
                localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    public void prikaziEkranZaPretraguZupanija() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pretragaZupanija.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaZapisZupanije() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zapisZupanije.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaPretraguSimptoma() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pretragaSimptoma.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaZapisSimptoma() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zapisSimptoma.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaPretraguBolesti() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pretragaBolesti.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaZapisBolesti() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zapisBolesti.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaPretraguVirusa() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pretragaVirusa.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaZapisVirusa() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zapisVirusa.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaPretraguOsoba() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pretragaOsoba.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @FXML
    public void prikaziEkranZaZapisOsoba() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zapisOsoba.fxml"));

            Main.setView(loader.load());
        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);

            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
