package at.aau.ase.mlg_party_app.clicker.networking;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class clickerResults extends GameBaseRequest {
    public String lobbyId;

    public clickerResults() {
        type = MessageType.ClickerResults;
        this.lobbyId = Game.getInstance().getLobbyId();
    }

    public float max;

}
