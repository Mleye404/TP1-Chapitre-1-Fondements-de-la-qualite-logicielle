package com.sunusante.tp1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Filet de tests de caractérisation : ils décrivent le comportement ACTUEL
 * du code (avant tout refactoring) pour que vous puissiez le modifier en
 * confiance. Ne les supprimez pas, ne changez pas leurs attentes : s'ils
 * cassent après un refactoring, c'est que le comportement a changé, pas
 * seulement le code.
 *
 * Rappel des dates utilisées : le 21/07/2026 est un mardi (jour de semaine),
 * le 25/07/2026 est un samedi (weekend).
 */
class GestionRendezVousTest {

    @Test
    void ajouterRendezVous_generaliste_semaine_tarifDeBase() {
        GestionRendezVous g = new GestionRendezVous();

        double prix = g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        assertEquals(5000, prix);
    }

    @Test
    void ajouterRendezVous_specialiste_weekend_majore() {
        GestionRendezVous g = new GestionRendezVous();

        double prix = g.ajouterRendezVous(
                "Awa Ndiaye",
                "SPECIALISTE",
                "2026-07-25",
                false
        );

        assertEquals(12000, prix); // 10000 + 20% de majoration weekend
    }

    @Test
    void ajouterRendezVous_urgence_vip_reduit() {
        GestionRendezVous g = new GestionRendezVous();

        double prix = g.ajouterRendezVous(
                "Moussa Fall",
                "URGENCE",
                "2026-07-21",
                true
        );

        assertEquals(13500, prix); // 15000 - 10% de réduction VIP
    }

    @Test
    void calculerTotalFacture_sommeLesRendezVousDuPatient() {
        GestionRendezVous g = new GestionRendezVous();

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "SPECIALISTE",
                "2026-07-21",
                false
        );

        assertEquals(15000, g.calculerTotalFacture("Awa Ndiaye"));
    }

    @Test
    void ajouterRendezVous_deuxiemeRendezVousMemePatientMemeDate_tarifReduit() {
        GestionRendezVous g = new GestionRendezVous();

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        double prixDeuxieme = g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        assertEquals(4250, prixDeuxieme);
    }

    @Test
    void ajouterRendezVous_patientInvalide_lanceException() {
        GestionRendezVous g = new GestionRendezVous();

        assertThrows(
                IllegalArgumentException.class,
                () -> g.ajouterRendezVous(
                        "",
                        "GENERALISTE",
                        "2026-07-21",
                        false
                )
        );
    }

    @Test
    void ajouterRendezVous_dateInvalide_lanceException() {
        GestionRendezVous g = new GestionRendezVous();

        assertThrows(
                IllegalArgumentException.class,
                () -> g.ajouterRendezVous(
                        "Awa Ndiaye",
                        "GENERALISTE",
                        "",
                        false
                )
        );
    }

    @Test
    void annulerRendezVous_supprimeLeRendezVous() {
        GestionRendezVous g = new GestionRendezVous();

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        assertEquals(
                1,
                g.nombreRendezVous("Awa Ndiaye", "2026-07-21")
        );

        g.annulerRendezVous(
                "Awa Ndiaye",
                "2026-07-21"
        );

        assertEquals(
                0,
                g.nombreRendezVous("Awa Ndiaye", "2026-07-21")
        );
    }

    @Test
    void nombreRendezVous_compteUniquementPatientEtDate() {
        GestionRendezVous g = new GestionRendezVous();

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-21",
                false
        );

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "SPECIALISTE",
                "2026-07-21",
                false
        );

        g.ajouterRendezVous(
                "Awa Ndiaye",
                "GENERALISTE",
                "2026-07-25",
                false
        );

        assertEquals(
                2,
                g.nombreRendezVous("Awa Ndiaye", "2026-07-21")
        );
    }
}