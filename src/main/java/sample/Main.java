package main.java.sample;

import database.BazaPodataka;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.niti.DohvatiBrojOsobaNit;
import main.niti.DohvatiZadnjeUnesenuOsobuNit;
import model.Osoba;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class Main extends Application {

    private static BorderPane appRoot;
    private static Stage mainStage;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static BazaPodataka bazaPodataka;
    private static PocetniKontroler kontroler;

    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pocetniEkran.fxml"));

            appRoot = loader.load();
            kontroler = loader.getController();
            Scene scene = new Scene(appRoot, 600, 425);
            scene.getStylesheets().add("app.css");
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
            mainStage = primaryStage;

            bazaPodataka = new BazaPodataka("resources/database.properties");

            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

                Integer brojOsoba = DohvatiBrojOsobaNit.dohvatiBrojOsoba();

                kontroler.setBrojOsoba(brojOsoba);

            }), new KeyFrame(Duration.seconds(2)));

            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();

            Timeline clock2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {

                Osoba zadnjaOsoba = DohvatiZadnjeUnesenuOsobuNit.dohvatiZadnjeUnesenuOsobu();

                kontroler.setZadnjaOsoba(zadnjaOsoba);

            }), new KeyFrame(Duration.seconds(5)));

            clock2.setCycleCount(Animation.INDEFINITE);
            clock2.play();

        }
        catch (IOException ex) {

            Main.getLogger().error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public static void setView(BorderPane newView){

        appRoot.setCenter(newView);

        appRoot.getCenter().setScaleY(0.9);
    }

    public static Stage getMainStage() { return mainStage; }

    public static Logger getLogger() { return logger; }

    public static BazaPodataka getBazaPodataka() { return bazaPodataka; }

    public static void main(String[] args) {
        launch(args);
    }
}
