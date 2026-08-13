package com.sunusante.tp1;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalculateurTarif {

    public double calculer(
            TypeConsultation type,
            String date,
            boolean estVip,
            int nombreDejaPris) {

        double prix = type.getTarifBase();

        LocalDate d = LocalDate.parse(date);

        // Majoration de 20 % le weekend
        if (d.getDayOfWeek() == DayOfWeek.SATURDAY
                || d.getDayOfWeek() == DayOfWeek.SUNDAY) {
            prix = prix + prix * 0.2;
        }

        // Réduction VIP de 10 %
        if (estVip) {
            prix = prix - prix * 0.1;
        }

        // Réduction de 15 % à partir du 2e rendez-vous
        if (nombreDejaPris >= 1) {
            prix = prix - prix * 0.15;
        }

        return prix;
    }
}