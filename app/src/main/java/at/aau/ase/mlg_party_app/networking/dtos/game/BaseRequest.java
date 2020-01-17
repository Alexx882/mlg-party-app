package at.aau.ase.mlg_party_app.networking.dtos.game;

import at.aau.ase.mlg_party_app.networking.MessageType;

public class BaseRequest extends at.aau.ase.mlg_party_app.networking.dtos.BaseRequest {

    public String playerId;

    public BaseRequest(){

    }

    public BaseRequest(MessageType type, String playerId){
        super(type);
        this.playerId = playerId;
    }

}
