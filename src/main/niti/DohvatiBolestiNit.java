package main.niti;

import main.java.sample.Main;
import model.Bolest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DohvatiBolestiNit implements Runnable {

    List<Bolest> bolesti;

    @Override
    public void run() {
        try{
            bolesti = Main.getBazaPodataka().dohvatiBolesti();
        }
        catch (SQLException | IOException ex) {
            Main.getLogger().error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public static List<Bolest> dohvatiBolesti() {

        DohvatiBolestiNit dohvatiBolestiNit = new DohvatiBolestiNit();
        Thread nit = new Thread(dohvatiBolestiNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiBolestiNit.bolesti;
    }
}
