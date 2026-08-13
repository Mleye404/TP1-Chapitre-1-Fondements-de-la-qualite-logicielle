package com.sunusante.tp1;

import java.util.ArrayList;
import java.util.List;

public class RendezVousRepository {

    private final List<String[]> rendezVous = new ArrayList<>();

    public void ajouter(String patient, String type, String date,
                        boolean estVip, double prix) {

        rendezVous.add(new String[]{
                patient,
                type,
                date,
                String.valueOf(estVip),
                String.valueOf(prix)
        });
    }

    public void supprimer(String patient, String date) {
        rendezVous.removeIf(
                r -> r[0].equals(patient) && r[2].equals(date)
        );
    }

    public int compter(String patient, String date) {
        int count = 0;

        for (String[] r : rendezVous) {
            if (r[0].equals(patient) && r[2].equals(date)) {
                count++;
            }
        }

        return count;
    }

    public List<String[]> trouverParPatient(String patient) {
        List<String[]> resultats = new ArrayList<>();

        for (String[] r : rendezVous) {
            if (r[0].equals(patient)) {
                resultats.add(r);
            }
        }

        return resultats;
    }

    public List<String[]> trouverTous() {
        return new ArrayList<>(rendezVous);
    }
}