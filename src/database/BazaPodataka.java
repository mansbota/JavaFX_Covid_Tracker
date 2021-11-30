package database;

import enumeracije.Vrijednost;
import main.java.sample.Main;
import model.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BazaPodataka {

    private final String propertiesFile;

    private Boolean aktivnaVezaSBazomPodataka;

    private Connection connection;

    private final Object lock;

    public BazaPodataka(String propertiesFile) {

        this.propertiesFile = propertiesFile;

        this.aktivnaVezaSBazomPodataka = false;

        lock = new Object();
    }

    private void openConnection() throws SQLException, IOException {

        synchronized (lock) {

            if (aktivnaVezaSBazomPodataka) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Main.getLogger().error(ex.getMessage(), ex);
                    ex.printStackTrace();
                }
            }

            Properties properties = new Properties();

            properties.load(new FileReader(propertiesFile));

            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("ime"),
                    properties.getProperty("sifra"));

            aktivnaVezaSBazomPodataka = true;
        }
    }

    private void closeConnection() throws SQLException {

        synchronized (lock) {

            connection.close();

            aktivnaVezaSBazomPodataka = false;

            lock.notifyAll();
        }
    }

    public List<Simptom> dohvatiSimptome() throws SQLException, IOException {

        openConnection();

        List<Simptom> simptomi = new ArrayList<>();

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM SIMPTOM")) {

                    while (rs.next()) {

                        Long id = rs.getLong("ID");
                        String naziv = rs.getString("NAZIV");
                        String vrijednost = rs.getString("VRIJEDNOST");

                        simptomi.add(new Simptom(naziv, Vrijednost.valueOf(vrijednost), id));
                    }
                }
            }

            closeConnection();

            return simptomi;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    private Optional<Simptom> dohvatiSimptom(Long id) throws SQLException {

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SIMPTOM WHERE ID = ?")) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String naziv = rs.getString("NAZIV");
                    String vrijednost = rs.getString("VRIJEDNOST");

                    return Optional.of(new Simptom(naziv, Vrijednost.valueOf(vrijednost), id));
                }
            }
        }

        return Optional.empty();
    }

    public void spremiSimptom(Simptom simptom) throws SQLException, IOException {

        openConnection();

        try {

            try (PreparedStatement stmt =
                         connection.prepareStatement("INSERT INTO SIMPTOM (NAZIV, VRIJEDNOST) VALUES(?, ?)")) {

                stmt.setString(1, simptom.getNaziv());

                stmt.setString(2, simptom.getVrijednost().getUcestalost());

                stmt.executeUpdate();
            }

            closeConnection();

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    public List<Bolest> dohvatiBolesti() throws SQLException, IOException {

        openConnection();

        List<Bolest> bolesti = new ArrayList<>();

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM BOLEST")) {

                    while (rs.next()) {

                        Long id = rs.getLong("ID");
                        String naziv = rs.getString("NAZIV");
                        boolean jeVirus = rs.getBoolean("VIRUS");

                        if (jeVirus) {
                            bolesti.add(new Virus(naziv, dohvatiSimptomeBolesti(id), id));
                        } else {
                            bolesti.add(new Bolest(naziv, dohvatiSimptomeBolesti(id), id));
                        }
                    }
                }
            }

            closeConnection();

            return bolesti;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    private Optional<Bolest> dohvatiBolest(Long id) throws SQLException {

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?")) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String naziv = rs.getString("NAZIV");
                    boolean jeVirus = rs.getBoolean("VIRUS");

                    if (jeVirus) {
                        return Optional.of(new Virus(naziv, dohvatiSimptomeBolesti(id), id));
                    } else {
                        return Optional.of(new Bolest(naziv, dohvatiSimptomeBolesti(id), id));
                    }
                }
            }
        }

        return Optional.empty();
    }

    private List<Simptom> dohvatiSimptomeBolesti(Long id) throws SQLException {

        List<Simptom> simptomi = new ArrayList<>();

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM BOLEST_SIMPTOM" +
                " WHERE BOLEST_ID = ?")) {

            pStmt.setLong(1, id);

            try (ResultSet rsSimptoma = pStmt.executeQuery()) {

                while (rsSimptoma.next()) {

                    Long idSimptoma = rsSimptoma.getLong("SIMPTOM_ID");

                    simptomi.add(dohvatiSimptom(idSimptoma).orElse(null));
                }
            }
        }

        return simptomi;
    }

    public void spremiBolest(Bolest bolest) throws SQLException, IOException {

        openConnection();

        try {

            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO BOLEST (NAZIV," +
                            " VIRUS) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, bolest.getNaziv());

                stmt.setBoolean(2, bolest instanceof Virus);

                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {

                    if (keys.next()) {

                        long id = keys.getLong(1);

                        for (Simptom simptom : bolest.getSimptomi()) {

                            try (PreparedStatement simStmt =
                                         connection.prepareStatement("INSERT INTO BOLEST_SIMPTOM (BOLEST_ID, " +
                                                 "SIMPTOM_ID) VALUES(?, ?)")) {

                                simStmt.setLong(1, id);

                                simStmt.setLong(2, simptom.getId());

                                simStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            closeConnection();

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    public List<Zupanija> dohvatiZupanije() throws SQLException, IOException {

        openConnection();

        List<Zupanija> zupanije = new ArrayList<>();

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM ZUPANIJA")) {

                    while (rs.next()) {

                        Long id = rs.getLong("ID");
                        String naziv = rs.getString("NAZIV");
                        Integer brojSt = rs.getInt("BROJ_STANOVNIKA");
                        Integer brojZar = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");

                        zupanije.add(new Zupanija(naziv, brojSt, brojZar, id));
                    }
                }
            }

            closeConnection();

            return zupanije;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    private Optional<Zupanija> dohvatiZupaniju(Long id) throws SQLException {

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID = ?")) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String naziv = rs.getString("NAZIV");
                    Integer brojSt = rs.getInt("BROJ_STANOVNIKA");
                    Integer brojZar = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");

                    return Optional.of(new Zupanija(naziv, brojSt, brojZar, id));
                }
            }
        }

        return Optional.empty();
    }

    public void spremiZupaniju(Zupanija zupanija) throws SQLException, IOException {

        openConnection();

        try {

            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO ZUPANIJA (NAZIV," +
                    " BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) VALUES(?, ?, ?)")) {

                stmt.setString(1, zupanija.getNaziv());

                stmt.setInt(2, zupanija.getBrojStanovnika());

                stmt.setInt(3, zupanija.getBrojZarazenih());

                stmt.executeUpdate();
            }

            closeConnection();

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    private Optional<Osoba> dohvatiOsobu(Long id) throws SQLException {

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM OSOBA WHERE ID = ?")) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String ime = rs.getString("IME");
                    String prezime = rs.getString("PREZIME");
                    Date datumRod = rs.getDate("DATUM_RODJENJA");
                    Long zupanijaId = rs.getLong("ZUPANIJA_ID");
                    Long bolestId = rs.getLong("BOLEST_ID");

                    Instant instant = Instant.ofEpochMilli(datumRod.getTime());
                    LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());

                    return Optional.of(new Osoba.Builder(ime, prezime, id)
                            .inZupanija(dohvatiZupaniju(zupanijaId).orElse(null))
                            .withBolest(dohvatiBolest(bolestId).orElse(null))
                            .withKontaktiraneOsobe(null)
                            .withStarost(localDate)
                            .build());
                }
            }
        }

        return Optional.empty();
    }

    public List<Osoba> dohvatiOsobe() throws SQLException, IOException {

        openConnection();

        List<Osoba> osobe = new ArrayList<>();

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM OSOBA")) {

                    while (rs.next()) {

                        long id = rs.getLong("ID");

                        String ime = rs.getString("IME");
                        String prezime = rs.getString("PREZIME");
                        Date datumRod = rs.getDate("DATUM_RODJENJA");
                        Long zupanijaId = rs.getLong("ZUPANIJA_ID");
                        Long bolestId = rs.getLong("BOLEST_ID");

                        Instant instant = Instant.ofEpochMilli(datumRod.getTime());
                        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());

                        List<Osoba> kontaktiraneOsobe = new ArrayList<>();

                        try (PreparedStatement konStmt = connection.prepareStatement(
                                "SELECT * FROM KONTAKTIRANE_OSOBE" +
                                " WHERE OSOBA_ID = ?")) {

                            konStmt.setLong(1, id);

                            try (ResultSet konSet = konStmt.executeQuery()) {

                                while (konSet.next()) {

                                    Long idOsobe = konSet.getLong("KONTAKTIRANA_OSOBA_ID");

                                    kontaktiraneOsobe.add(dohvatiOsobu(idOsobe).orElse(null));
                                }

                                Osoba osoba = new Osoba.Builder(ime, prezime, id)
                                        .withBolest(dohvatiBolest(bolestId).orElse(null))
                                        .inZupanija(dohvatiZupaniju(zupanijaId).orElse(null))
                                        .withStarost(localDate)
                                        .withKontaktiraneOsobe(kontaktiraneOsobe)
                                        .build();

                                osobe.add(osoba);
                            }
                        }
                    }
                }
            }

            closeConnection();

            return osobe;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    public void spremiOsobu(Osoba osoba) throws SQLException, IOException {

        openConnection();

        try {

            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO OSOBA (IME, PREZIME," +
                    "DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, osoba.getNaziv());

                stmt.setString(2, osoba.getPrezime());

                stmt.setDate(3, Date.valueOf(osoba.getStarost()));

                stmt.setLong(4, osoba.getZupanija().getId());

                stmt.setLong(5, osoba.getZarazenBolescu().getId());

                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {

                    if (keys.next()) {

                        long id = keys.getLong(1);

                        for (Osoba konOsoba : osoba.getKontaktiraneOsobe()) {

                            try (PreparedStatement konStmt =
                                         connection.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE (OSOBA_ID, " +
                                                 "KONTAKTIRANA_OSOBA_ID) VALUES(?, ?)")) {

                                konStmt.setLong(1, id);

                                konStmt.setLong(2, konOsoba.getId());

                                konStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            closeConnection();

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    // zadatak 1
    public Integer dohvatiBrojOsoba() throws SQLException, IOException {

        openConnection();

        Integer brojOsoba = null;

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet keys = stmt.executeQuery("SELECT COUNT(*) AS broj FROM osoba;")) {

                    if (keys.next()) {

                        brojOsoba = keys.getInt("broj");
                    }
                }
            }

            closeConnection();

            return brojOsoba;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }

    // zadatak 2
    public Osoba dohvatiZadnjeUnesenuOsobu() throws SQLException, IOException {

        openConnection();

        Osoba osoba = null;

        try {

            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM osoba ORDER BY id DESC LIMIT 1;")) {

                    if (rs.next()) {

                        long id = rs.getLong("ID");

                        String ime = rs.getString("IME");
                        String prezime = rs.getString("PREZIME");
                        Date datumRod = rs.getDate("DATUM_RODJENJA");
                        Long zupanijaId = rs.getLong("ZUPANIJA_ID");
                        Long bolestId = rs.getLong("BOLEST_ID");

                        Instant instant = Instant.ofEpochMilli(datumRod.getTime());
                        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());

                        osoba = new Osoba.Builder(ime, prezime, id)
                                .withBolest(dohvatiBolest(bolestId).orElse(null))
                                .inZupanija(dohvatiZupaniju(zupanijaId).orElse(null))
                                .withStarost(localDate)
                                .withKontaktiraneOsobe(null)
                                .build();
                    }
                }
            }

            closeConnection();

            return osoba;

        } catch (SQLException ex) {

            closeConnection();

            throw ex;
        }
    }
}
