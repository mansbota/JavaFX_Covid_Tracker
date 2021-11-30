package main.niti;

import main.java.sample.Main;
import model.Bolest;
import java.io.IOException;
import java.sql.SQLException;

public class SpremiBolestNit implements Runnable {

    Bolest bolest;

    public SpremiBolestNit(Bolest bolest) {
        this.bolest = bolest;
    }

    @Override
    public void run() {
        try {
            Main.getBazaPodataka().spremiBolest(bolest);
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static void spremiBolest(Bolest bolest) {

        SpremiBolestNit spremiBolestNit = new SpremiBolestNit(bolest);
        Thread nit = new Thread(spremiBolestNit);
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
