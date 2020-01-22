package at.aau.ase.mlg_party_app.clicker.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class clickerResults extends GameBaseRequest {
    public String lobbyId;

    public clickerResults(String lobbyId, String playerId, int max) {
        super(MessageType.ClickerResults, lobbyId, playerId);
        this.max = max;
    }

    public float max;

}
