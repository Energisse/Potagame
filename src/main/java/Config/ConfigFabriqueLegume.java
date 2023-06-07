package Config;

public record ConfigFabriqueLegume(
        String nom,
        String image,
        String raccourci,
        String description,
        int prixAchat,
        int prixVente,
        float temperature,
        float humidite,
        float tempsDePousse
) { }
