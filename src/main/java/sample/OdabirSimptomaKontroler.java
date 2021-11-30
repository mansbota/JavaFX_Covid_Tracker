package main.java.sample;

import enumeracije.Vrijednost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.niti.DohvatiSimptomeNit;
import model.Simptom;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OdabirSimptomaKontroler implements Initializable {

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> stupacImena;

    @FXML
    private TableColumn<Simptom, Vrijednost> stupacVrijednosti;

    private FilteredList<Simptom> filtriraniSimptomi;

    Initializable kontroler;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Simptom> originalniSimptomi = DohvatiSimptomeNit.dohvatiSimptome();

        ObservableList<Simptom> observableSimptomi = FXCollections.observableArrayList(originalniSimptomi);

        filtriraniSimptomi = new FilteredList<>(observableSimptomi);

        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacVrijednosti.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));

        tablicaSimptoma.setItems(filtriraniSimptomi);
        tablicaSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void pretrazi() {
        filtriraniSimptomi.
                setPredicate(s -> s.getNaziv().toLowerCase().contains(nazivSimptoma.getText().toLowerCase()));
    }

    public void izaberi() {
        if (kontroler instanceof ZapisBolestiKontroler zapisBolestiKontroler)
            zapisBolestiKontroler.setIzabraniSimptomi(tablicaSimptoma.getSelectionModel().getSelectedItems());
        else {
            ZapisVirusaKontroler zapisVirusaKontroler = (ZapisVirusaKontroler) kontroler;
            zapisVirusaKontroler.setIzabraniSimptomi(tablicaSimptoma.getSelectionModel().getSelectedItems());
        }

        stage.close();
    }

    public void setKontroler(Initializable kontroler) {
        this.kontroler = kontroler;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
