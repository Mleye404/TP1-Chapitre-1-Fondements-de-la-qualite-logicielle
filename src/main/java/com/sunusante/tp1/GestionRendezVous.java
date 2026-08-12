package com.sunusante.tp1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * TP1 - Version fournie aux étudiants pour SunuSanté, l'appli de gestion de
 * rendez-vous de la clinique.
 *
 * Ce code FONCTIONNE : la qualité externe (ce que voit l'utilisateur) est
 * correcte. Mais la qualité interne laisse à désirer : une seule classe fait
 * la validation, le calcul du tarif, le stockage ET l'affichage (violation du
 * principe de responsabilité unique), et la logique de tarification est
 * recopiée à deux endroits (violation DRY).
 *
 * NE MODIFIEZ PAS CE FICHIER avant d'avoir lu le README de ce dossier :
 * vous avez d'abord besoin d'un filet de tests (voir GestionRendezVousTest).
 */
public class GestionRendezVous {

    // Chaque rendez-vous est stocké comme : [patient, type, date, vip, prix]
    private final List<String[]> rendezVous = new ArrayList<>();

    public double ajouterRendezVous(
            String patient,
            String type,
            String date,
            boolean estVip) {

        if (patient == null || patient.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom du patient est obligatoire");
        }

        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La date est obligatoire");
        }

        int nombreDejaPris = nombreRendezVous(patient, date);

        double prix = calculerTarif(
                type,
                date,
                estVip,
                nombreDejaPris);

        rendezVous.add(new String[]{
                patient,
                type,
                date,
                String.valueOf(estVip),
                String.valueOf(prix)
        });

        System.out.println(
                "Rendez-vous ajouté pour " + patient
                        + " (" + type + ") le " + date
                        + " - " + prix + " FCFA");

        return prix;
    }

    /**
     * Calcule le tarif d'un rendez-vous.
     *
     * Ordre des règles :
     * 1. Tarif de base
     * 2. Majoration weekend
     * 3. Réduction VIP
     * 4. Réduction dégressive
     */
    private double calculerTarif(
            String type,
            String date,
            boolean estVip,
            int nombreDejaPris) {

        double prix;

        if (type.equals("GENERALISTE")) {
            prix = 5000;
        } else if (type.equals("SPECIALISTE")) {
            prix = 10000;
        } else if (type.equals("URGENCE")) {
            prix = 15000;
        } else {
            throw new IllegalArgumentException(
                    "Type de consultation inconnu: " + type);
        }

        LocalDate d = LocalDate.parse(date);

        // Majoration weekend
        if (d.getDayOfWeek() == DayOfWeek.SATURDAY
                || d.getDayOfWeek() == DayOfWeek.SUNDAY) {
            prix = prix + prix * 0.2;
        }

        // Réduction VIP
        if (estVip) {
            prix = prix - prix * 0.1;
        }

        // Réduction dégressive à partir du 2e rendez-vous
        if (nombreDejaPris >= 1) {
            prix = prix - prix * 0.15;
        }

        return prix;
    }

    public void annulerRendezVous(String patient, String date) {

        if (patient == null || patient.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom du patient est obligatoire");
        }

        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La date est obligatoire");
        }

        rendezVous.removeIf(
                r -> r[0].equals(patient) && r[2].equals(date));

        System.out.println(
                "Rendez-vous annulé pour " + patient + " le " + date);
    }

    public double calculerTotalFacture(String patient) {

        double total = 0;

        for (String[] r : rendezVous) {

            if (r[0].equals(patient)) {

                String type = r[1];
                boolean vip = Boolean.parseBoolean(r[3]);
                String date = r[2];

                /*
                 * On réutilise la logique centralisée de calcul du tarif.
                 *
                 * Le nombreDejaPris vaut 0 ici afin de conserver le
                 * comportement caractérisé par les tests existants.
                 */
                double prix = calculerTarif(
                        type,
                        date,
                        vip,
                        0);

                total = total + prix;
            }
        }

        return total;
    }

    public void afficherRendezVous() {

        for (String[] r : rendezVous) {

            System.out.println(
                    r[0] + " | "
                            + r[1] + " | "
                            + r[2] + " | VIP="
                            + r[3] + " | "
                            + r[4] + " FCFA");
        }
    }

    public int nombreRendezVous(String patient, String date) {

        int count = 0;

        for (String[] r : rendezVous) {

            if (r[0].equals(patient) && r[2].equals(date)) {
                count++;
            }
        }

        return count;
    }
}