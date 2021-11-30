package main.niti;

import main.java.sample.Main;
import model.Osoba;

import java.io.IOException;
import java.sql.SQLException;

public class DohvatiZadnjeUnesenuOsobuNit implements Runnable {

    private Osoba osoba;

    @Override
    public void run() {
        try {
            osoba = Main.getBazaPodataka().dohvatiZadnjeUnesenuOsobu();
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static Osoba dohvatiZadnjeUnesenuOsobu() {

        DohvatiZadnjeUnesenuOsobuNit dohvatiZadnjeUnesenuOsobuNit = new DohvatiZadnjeUnesenuOsobuNit();
        Thread nit = new Thread(dohvatiZadnjeUnesenuOsobuNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiZadnjeUnesenuOsobuNit.osoba;
    }
}
