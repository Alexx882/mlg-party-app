package at.aau.ase.mlg_party_app.networking.dtos.game;

import at.aau.ase.mlg_party_app.networking.MessageType;

public class GameBaseRequest extends at.aau.ase.mlg_party_app.networking.dtos.BaseRequest {

    public String lobbyId;
    public String playerId;

    public GameBaseRequest(){

    }

    public GameBaseRequest(MessageType type, String lobbyId, String playerId){
        super(type);
        this.lobbyId = lobbyId;
        this.playerId = playerId;
    }

}
