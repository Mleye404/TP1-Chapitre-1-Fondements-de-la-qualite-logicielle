package com.sunusante.tp1;

import java.util.List;

public class RendezVousAffichage {

    public void afficher(List<String[]> rendezVous) {

        for (String[] r : rendezVous) {

            System.out.println(
                    r[0] + " | "
                            + r[1] + " | "
                            + r[2] + " | VIP="
                            + r[3] + " | "
                            + r[4] + " FCFA"
            );
        }
    }

    public void afficherAjout(
            String patient,
            String type,
            String date,
            double prix) {

        System.out.println(
                "Rendez-vous ajouté pour " + patient
                        + " (" + type + ") le " + date
                        + " - " + prix + " FCFA"
        );
    }

    public void afficherAnnulation(String patient, String date) {

        System.out.println(
                "Rendez-vous annulé pour " + patient
                        + " le " + date
        );
    }
}