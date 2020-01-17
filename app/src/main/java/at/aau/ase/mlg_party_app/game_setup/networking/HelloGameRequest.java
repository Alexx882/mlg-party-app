package at.aau.ase.mlg_party_app.game_setup.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.BaseRequest;

public class HelloGameRequest extends BaseRequest {

    public String lobbyName;

    public  HelloGameRequest(String lobbyName, String playerId){
        super(MessageType.HelloGame, playerId);
        this.lobbyName = lobbyName;
    }

}
