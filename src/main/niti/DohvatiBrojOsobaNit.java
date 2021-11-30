package main.niti;

import main.java.sample.Main;
import java.io.IOException;
import java.sql.SQLException;

public class DohvatiBrojOsobaNit implements Runnable {

    private Integer brojOsoba;

    @Override
    public void run() {
        try {
            brojOsoba = Main.getBazaPodataka().dohvatiBrojOsoba();
        } catch (SQLException | IOException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }
    }

    public static Integer dohvatiBrojOsoba() {

        DohvatiBrojOsobaNit dohvatiBrojOsobaNit = new DohvatiBrojOsobaNit();
        Thread nit = new Thread(dohvatiBrojOsobaNit);
        nit.start();

        try {
            nit.join();
        }
        catch (InterruptedException throwables) {
            Main.getLogger().error(throwables.getMessage(), throwables);
            throwables.printStackTrace();
        }

        return dohvatiBrojOsobaNit.brojOsoba;
    }
}
