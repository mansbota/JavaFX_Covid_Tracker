package main.java.sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import main.niti.DohvatiZupanijeNit;
import main.niti.NajviseZarazenihNit;
import model.Zupanija;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZupanijeKontroler implements Initializable {

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TableView<Zupanija> tablicaZupanija;

    @FXML
    private TableColumn<Zupanija, String> stupacImena;

    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojaStanovnika;

    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojaZarazenih;

    private FilteredList<Zupanija> filtriraneZupanije;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Zupanija> originalneZupanije = DohvatiZupanijeNit.dohvatiZupanije();

        ObservableList<Zupanija> observableZupanije
                = FXCollections.observableArrayList(originalneZupanije);

        filtriraneZupanije = new FilteredList<>(observableZupanije);

        stupacImena.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacBrojaStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        stupacBrojaZarazenih.setCellValueFactory(new PropertyValueFactory<>("brojZarazenih"));

        tablicaZupanija.setItems(filtriraneZupanije);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new NajviseZarazenihNit());

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            Zupanija najvisiPostotakZarazenihZupanija = DohvatiZupanijeNit.dohvatiZupanije()
                    .stream()
                    .max(Comparator.comparing(Zupanija::getPostotakZarazenih))
                    .get();

            Main.getMainStage().setTitle(najvisiPostotakZarazenihZupanija.getNaziv());

        }), new KeyFrame(Duration.seconds(10)));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void pretrazi() {
        filtriraneZupanije
                .setPredicate(z -> z.getNaziv().toLowerCase().contains(nazivZupanije.getText().toLowerCase()));
    }
}
