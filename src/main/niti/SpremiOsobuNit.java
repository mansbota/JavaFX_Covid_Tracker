package main.niti;

import main.java.sample.Main;
import model.Osoba;
import java.io.IOException;
import java.sql.SQLException;

public class SpremiOsobuNit implements Runnable {

    Osoba osoba;

    public SpremiOsobuNit(Osoba osoba) {
        this.osoba = osoba;
    }

    @Override
    public void run() {
        try {
            Main.getBazaPodataka().spremiOsobu(osoba);
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static void spremiOsobu(Osoba osoba) {

        SpremiOsobuNit spremiOsobuNit = new SpremiOsobuNit(osoba);
        Thread nit = new Thread(spremiOsobuNit);
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
