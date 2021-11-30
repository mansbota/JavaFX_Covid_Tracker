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
import model.Bolest;
import model.Virus;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class BolestiKontroler implements Initializable {

    @FXML
    private TextField nazivBolesti;

    @FXML
    private TableView<Bolest> tablicaBolesti;

    @FXML
    private TableColumn<Bolest, String> stupacImena;

    @FXML
    private TableColumn<Bolest, String> stupacSimptoma;

    private FilteredList<Bolest> filtriraneBolesti;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Bolest> originalneBolesti = DohvatiBolestiNit.dohvatiBolesti()
                .stream()
                .filter(b -> !(b instanceof Virus))
                .collect(Collectors.toList());

        ObservableList<Bolest> observableBolesti = FXCollections.observableArrayList(originalneBolesti);

        filtriraneBolesti = new FilteredList<>(observableBolesti);

        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacSimptoma.setCellValueFactory(new PropertyValueFactory<>("simptomi"));

        tablicaBolesti.setItems(filtriraneBolesti);

    }

    public void pretraga() {
        filtriraneBolesti.setPredicate(b -> b.getNaziv().toLowerCase().contains(nazivBolesti.getText().toLowerCase()));
    }
}
