package main.niti;

import main.java.sample.Main;
import model.Zupanija;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DohvatiZupanijeNit implements Runnable {

    private List<Zupanija> zupanije;

    @Override
    public void run() {
        try {
            zupanije = Main.getBazaPodataka().dohvatiZupanije();
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static List<Zupanija> dohvatiZupanije() {

        DohvatiZupanijeNit dohvatiZupanijeNit = new DohvatiZupanijeNit();
        Thread nit = new Thread(dohvatiZupanijeNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiZupanijeNit.zupanije;
    }
}
