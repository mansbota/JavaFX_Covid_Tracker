package main.niti;

import main.java.sample.Main;
import model.Zupanija;
import java.io.IOException;
import java.sql.SQLException;

public class SpremiZupanijuNit implements Runnable {

    Zupanija zupanija;

    public SpremiZupanijuNit(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    @Override
    public void run() {
        try {
            Main.getBazaPodataka().spremiZupaniju(zupanija);
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static void spremiZupaniju(Zupanija zupanija) {

        SpremiZupanijuNit spremiZupanijuNit = new SpremiZupanijuNit(zupanija);
        Thread nit = new Thread(spremiZupanijuNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }
}
