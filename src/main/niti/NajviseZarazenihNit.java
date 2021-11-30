package main.niti;

import model.Zupanija;
import java.util.Comparator;

public class NajviseZarazenihNit implements Runnable {

    @Override
    public void run() {

        while (true) {

            Zupanija najvisiPostotakZarazenihZupanija = DohvatiZupanijeNit.dohvatiZupanije()
                    .stream()
                    .max(Comparator.comparing(Zupanija::getPostotakZarazenih))
                    .get();

            System.out.println("Najveci postotak zarazenih ima zupanija "+ najvisiPostotakZarazenihZupanija.getNaziv() +
                    " i to " + najvisiPostotakZarazenihZupanija.getPostotakZarazenih() + "%%");

            try {

                Thread.sleep(10000);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
