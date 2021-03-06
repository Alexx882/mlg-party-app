package at.aau.ase.mlg_party_app.game_setup.networking;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;


public class HelloGameRequest extends GameBaseRequest {

    public HelloGameRequest(String lobbyId, String playerId) {
        super(MessageType.HelloGame, lobbyId, playerId);
    }

}
