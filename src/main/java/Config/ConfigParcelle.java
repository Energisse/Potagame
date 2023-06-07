package Config;

public record ConfigParcelle(
        int taille,
        float chanceHerbe,
        float chanceFleure,
        float chanceRocher,
        String imageHerbe,
        String[] imagesFleures,
        String imageRocher,
        String imageTerre,
        String imageTerreHumide,
        String imageCrame,
        String imagePourriture

) {}
