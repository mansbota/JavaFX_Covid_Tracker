package main.niti;

import main.java.sample.Main;
import model.Osoba;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DohvatiOsobeNit implements Runnable {

    List<Osoba> osobe;

    @Override
    public void run() {
        try{
            osobe = Main.getBazaPodataka().dohvatiOsobe();
        }
        catch (SQLException | IOException ex) {
            Main.getLogger().error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public static List<Osoba> dohvatiOsobe() {

        DohvatiOsobeNit dohvatiOsobeNit = new DohvatiOsobeNit();
        Thread nit = new Thread(dohvatiOsobeNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiOsobeNit.osobe;
    }
}
