package main.niti;

import main.java.sample.Main;
import model.Simptom;
import java.io.IOException;
import java.sql.SQLException;

public class SpremiSimptomNit implements Runnable {

    Simptom simptom;

    public SpremiSimptomNit(Simptom simptom) {
        this.simptom = simptom;
    }

    @Override
    public void run() {
        try {
            Main.getBazaPodataka().spremiSimptom(simptom);
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static void spremiSimptom(Simptom simptom) {

        SpremiSimptomNit spremiSimptomNit = new SpremiSimptomNit(simptom);
        Thread nit = new Thread(spremiSimptomNit);
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
