package Config;

public record ConfigLac(
        String image,
        String nom,
        String raccourci,
        int prixAchat,
        int prixVente,
        float humidite,
        float disipation
) {}
