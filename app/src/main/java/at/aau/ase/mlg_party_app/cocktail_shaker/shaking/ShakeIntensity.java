package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

public enum ShakeIntensity {
    Low,
    Medium,
    Crazy,
    Fast,
    Deacent,
    NonExistent;

    public static ShakeIntensity convertFromFloat(float f) {
        ShakeIntensity res;

        if (f >= 12)
            res = ShakeIntensity.Crazy;
        else if (f >= 8)
            res = ShakeIntensity.Fast;
        else if (f >= 6)
            res = ShakeIntensity.Deacent;
        else if (f >= 4)
            res = ShakeIntensity.Medium;
        else if (f >= 2)
            res = ShakeIntensity.Low;
        else
            res = ShakeIntensity.NonExistent;

        return res;
    }


}
