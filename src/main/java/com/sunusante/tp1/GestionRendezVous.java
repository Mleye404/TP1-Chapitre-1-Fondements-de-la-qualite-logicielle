package com.sunusante.tp1;

public class GestionRendezVous {

    private final RendezVousRepository repository;
    private final CalculateurTarif calculateurTarif;
    private final RendezVousAffichage affichage;

    public GestionRendezVous() {
        this.repository = new RendezVousRepository();
        this.calculateurTarif = new CalculateurTarif();
        this.affichage = new RendezVousAffichage();
    }

    public double ajouterRendezVous(
            String patient,
            String type,
            String date,
            boolean estVip) {

        // Validation
        if (patient == null || patient.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom du patient est obligatoire");
        }

        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La date est obligatoire");
        }

        // Conversion du type
        TypeConsultation typeConsultation;

        try {
            typeConsultation = TypeConsultation.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Type de consultation inconnu: " + type);
        }

        // Nombre de rendez-vous déjà pris ce jour-là
        int nombreDejaPris = repository.compter(patient, date);

        // Calcul du tarif
        double prix = calculateurTarif.calculer(
                typeConsultation,
                date,
                estVip,
                nombreDejaPris);

        // Stockage
        repository.ajouter(
                patient,
                type,
                date,
                estVip,
                prix);

        // Affichage
        affichage.afficherAjout(
                patient,
                type,
                date,
                prix);

        return prix;
    }

    public void annulerRendezVous(
            String patient,
            String date) {

        // Validation
        if (patient == null || patient.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom du patient est obligatoire");
        }

        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La date est obligatoire");
        }

        // Suppression
        repository.supprimer(patient, date);

        // Affichage
        affichage.afficherAnnulation(patient, date);
    }

    public double calculerTotalFacture(String patient) {

        double total = 0;

        for (String[] r : repository.trouverParPatient(patient)) {

            String type = r[1];
            boolean vip = Boolean.parseBoolean(r[3]);
            String date = r[2];

            TypeConsultation typeConsultation =
                    TypeConsultation.valueOf(type);

            /*
             * On conserve le comportement caractérisé des tests :
             * le calcul de facture ne réapplique pas la réduction
             * dégressive.
             */
            double prix = calculateurTarif.calculer(
                    typeConsultation,
                    date,
                    vip,
                    0);

            total = total + prix;
        }

        return total;
    }

    public void afficherRendezVous() {
        affichage.afficher(repository.trouverTous());
    }

    public int nombreRendezVous(
            String patient,
            String date) {

        return repository.compter(patient, date);
    }
}