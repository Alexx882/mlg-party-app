package at.aau.ase.mlg_party_app.game_setup.networking;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;


public class HelloGameRequest extends GameBaseRequest {

    public  HelloGameRequest(){
        super(MessageType.HelloGame, Game.getInstance().getLobbyId(),Game.getInstance().getPlayerId());
    }

}
