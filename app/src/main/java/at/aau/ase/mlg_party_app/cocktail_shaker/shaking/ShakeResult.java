package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

/**
 * The final result of the shaking process recorded by the ShakeHandler.
 */
public class ShakeResult {

    public ShakeResult(float max, float avg) {
        this.max = max;
        this.avg = avg;
    }

    public float max;
    public float avg;

}
