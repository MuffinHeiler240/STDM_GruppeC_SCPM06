// path: backend/src/test/java/com/sportverein/entity/StuermerTest.java
package com.sportverein.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StuermerTest {

    @Test
    void testSpielerstatistikAusgeben() {
        // Testdaten vorbereiten
        LocalDate geburtsdatum = LocalDate.of(1980, 1, 1);
        LocalDate vereinsbeitritt = LocalDate.of(2005, 1, 1);

        Stuermer stuermer = new Stuermer();
        stuermer.setGeschosseneTore(10);
        stuermer.setSchussgenauigkeit(0.7);
        stuermer.setChancenverwertung(0.5);
        stuermer.setNachname("Mueller");
        stuermer.setVorname("Thomas");
        stuermer.setGeburtsdatum(geburtsdatum);
        stuermer.setGespielteSpiele(20);
        stuermer.setGesperrt(false);
        stuermer.setVereinsbeitritt(vereinsbeitritt);
        stuermer.setRoteKarten(1);
        stuermer.setGelbeKarten(2);

        // Test der Statistik-Ausgabe
        String statistik = stuermer.spielerstatistikAusgeben();
        assertNotNull(statistik);
        assertTrue(statistik.contains("Name: Thomas Mueller"));
        assertTrue(statistik.contains("Position: Stürmer"));
        assertTrue(statistik.contains("Spiele: 20"));
        assertTrue(statistik.contains("Gelbe Karten: 2"));
        assertTrue(statistik.contains("Rote Karten: 1"));
        assertTrue(statistik.contains("Tore: 10"));
        assertTrue(statistik.contains("Schussgenauigkeit: 0.7"));
        assertTrue(statistik.contains("Chancenverwertung: 0.5"));
    }

    @Test
    void testSpielerBewertung() {
        // Testdaten vorbereiten
        Stuermer stuermer = new Stuermer();
        stuermer.setGeschosseneTore(10);
        stuermer.setSchussgenauigkeit(0.7);
        stuermer.setChancenverwertung(0.5);
        stuermer.setGespielteSpiele(20);

        // Test der Bewertungsberechnung
        double bewertung = stuermer.spielerBewertung();
        assertTrue(bewertung >= 0 && bewertung <= 100, "Bewertung sollte zwischen 0 und 100 liegen");
        
        // Test mit 0 gespielte Spiele
        stuermer.setGespielteSpiele(0);
        bewertung = stuermer.spielerBewertung();
        assertTrue(bewertung >= 0 && bewertung <= 100, "Bewertung sollte auch bei 0 Spielen gültig sein");
    }
}
