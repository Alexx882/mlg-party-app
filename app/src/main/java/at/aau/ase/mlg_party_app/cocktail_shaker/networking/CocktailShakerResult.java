package at.aau.ase.mlg_party_app.cocktail_shaker.networking;


import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class CocktailShakerResult extends GameBaseRequest {
    public CocktailShakerResult(){
        super(MessageType.CocktailShakerResult, Game.getInstance().getLobbyId(),Game.getInstance().getPlayerId());
    }

    public float max;
    public float avg;

}
