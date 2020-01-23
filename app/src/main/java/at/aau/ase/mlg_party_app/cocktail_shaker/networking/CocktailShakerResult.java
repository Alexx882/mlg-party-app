package at.aau.ase.mlg_party_app.cocktail_shaker.networking;


import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class CocktailShakerResult extends GameBaseRequest {
    public CocktailShakerResult(String lobbyId, String playerId) {
        super(MessageType.CocktailShakerResult, lobbyId, playerId);
    }

    public float max;
    public float avg;

}
