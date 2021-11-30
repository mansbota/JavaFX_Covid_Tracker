package main.niti;

import main.java.sample.Main;
import model.Simptom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DohvatiSimptomeNit implements Runnable {

    List<Simptom> simptomi;

    @Override
    public void run() {
        try{
            simptomi = Main.getBazaPodataka().dohvatiSimptome();
        }
        catch (SQLException | IOException ex) {
            Main.getLogger().error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public static List<Simptom> dohvatiSimptome() {

        DohvatiSimptomeNit dohvatiSimptomeNit = new DohvatiSimptomeNit();
        Thread nit = new Thread(dohvatiSimptomeNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiSimptomeNit.simptomi;
    }
}
