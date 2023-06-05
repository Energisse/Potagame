package Config;

public record ConfigMouette(
        String[] images,
        int max,
        float chanceApparition,
        int taille,
        float chanceDeManger,
        float vitesseDeMange,
        float vitesseDeDeplacement
) {}
