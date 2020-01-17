package at.aau.ase.mlg_party_app.cocktail_shaker.networking;


import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.BaseRequest;

public class CocktailShakerResult extends BaseRequest {

    public CocktailShakerResult(){
        type = MessageType.CocktailShakerResult;
    }

    public float max;
    public float avg;

}
