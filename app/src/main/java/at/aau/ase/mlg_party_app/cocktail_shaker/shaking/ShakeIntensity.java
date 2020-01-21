package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

public enum ShakeIntensity {
    LOW,
    MEDIUM,
    CRAZY,
    FAST,
    DEACENT,
    NON_EXISTENT;

    public static ShakeIntensity convertFromFloat(float f) {
        ShakeIntensity res;

        if (f >= 12)
            res = ShakeIntensity.CRAZY;
        else if (f >= 8)
            res = ShakeIntensity.FAST;
        else if (f >= 6)
            res = ShakeIntensity.DEACENT;
        else if (f >= 4)
            res = ShakeIntensity.MEDIUM;
        else if (f >= 2)
            res = ShakeIntensity.LOW;
        else
            res = ShakeIntensity.NON_EXISTENT;

        return res;
    }


}
