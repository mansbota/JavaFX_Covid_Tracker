package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.niti.DohvatiOsobeNit;
import model.Bolest;
import model.Osoba;
import model.Zupanija;
import java.net.URL;
import java.util.*;

public class OsobeKontroler implements Initializable {

    @FXML
    private TextField nazivOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private TableView<Osoba> tablicaOsoba;

    @FXML
    private TableColumn<Osoba, Long> stupacId;

    @FXML
    private TableColumn<Osoba, String> stupacImena;

    @FXML
    private TableColumn<Osoba, String> stupacPrezimena;

    @FXML
    private TableColumn<Osoba, Integer> stupacStarosti;

    @FXML
    private TableColumn<Osoba, Zupanija> stupacZupanije;

    @FXML
    private TableColumn<Osoba, Bolest> stupacBolesti;

    private FilteredList<Osoba> filtriraneOsobe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Osoba> originalneOsobe = DohvatiOsobeNit.dohvatiOsobe();

        ObservableList<Osoba> observableOsobe = FXCollections.observableArrayList(originalneOsobe);

        filtriraneOsobe = new FilteredList<>(observableOsobe);

        stupacId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacPrezimena.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        stupacStarosti.setCellValueFactory(new PropertyValueFactory<>("starost"));
        stupacZupanije.setCellValueFactory(new PropertyValueFactory<>("zupanija"));
        stupacBolesti.setCellValueFactory(new PropertyValueFactory<>("zarazenBolescu"));

        tablicaOsoba.setItems(filtriraneOsobe);
    }

    public void pretraga() {
        filtriraneOsobe.setPredicate(o -> o.getNaziv().toLowerCase().contains(nazivOsobe.getText().toLowerCase())
                && o.getPrezime().toLowerCase().contains(prezimeOsobe.getText().toLowerCase()));
    }
}
