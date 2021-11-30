package main.java.sample;

import enumeracije.Vrijednost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.niti.DohvatiSimptomeNit;
import model.Simptom;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SimptomiKontroler implements Initializable {

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> stupacImena;

    @FXML
    private TableColumn<Simptom, Vrijednost> stupacVrijednosti;

    private FilteredList<Simptom> filtriraniSimptomi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Simptom> originalniSimptomi = DohvatiSimptomeNit.dohvatiSimptome();

        ObservableList<Simptom> observableSimptomi = FXCollections.observableArrayList(originalniSimptomi);

        filtriraniSimptomi = new FilteredList<>(observableSimptomi);

        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacVrijednosti.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));

        tablicaSimptoma.setItems(filtriraniSimptomi);
    }

    public void pretrazi() {
        filtriraniSimptomi.setPredicate(
                s -> s.getNaziv().toLowerCase().contains(nazivSimptoma.getText().toLowerCase()));
    }
}
