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
import main.niti.DohvatiBolestiNit;
import model.Virus;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class VirusiKontroler implements Initializable {

    @FXML
    private TextField nazivVirusa;

    @FXML
    private TableView<Virus> tablicaVirusa;

    @FXML
    private TableColumn<Virus, String> stupacImena;

    @FXML
    private TableColumn<Virus, String> stupacSimptoma;

    private FilteredList<Virus> filtriraniVirusi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Virus> originalniVirusi = DohvatiBolestiNit.dohvatiBolesti()
                .stream()
                .filter(b -> b instanceof Virus)
                .map(v -> (Virus) v)
                .collect(Collectors.toList());

        ObservableList<Virus> observableVirusi = FXCollections.observableArrayList(originalniVirusi);

        filtriraniVirusi = new FilteredList<>(observableVirusi);

        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacSimptoma.setCellValueFactory(new PropertyValueFactory<>("simptomi"));

        tablicaVirusa.setItems(filtriraniVirusi);
    }

    public void pretraga() {
        filtriraniVirusi.setPredicate(v -> v.getNaziv().toLowerCase().contains(nazivVirusa.getText().toLowerCase()));
    }
}