package at.aau.ase.mlg_party_app.networking.dtos.game;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;

public class GameBaseRequest extends at.aau.ase.mlg_party_app.networking.dtos.BaseRequest {

    public String lobbyId;
    public String playerId;

    public GameBaseRequest(){
        this.lobbyId = Game.getInstance().getLobbyId();
        this.playerId =  Game.getInstance().getPlayerId();

    }

    public GameBaseRequest(MessageType type){
        super(type);
        this.lobbyId = Game.getInstance().getLobbyId();
        this.playerId =  Game.getInstance().getPlayerId();
    }

}
