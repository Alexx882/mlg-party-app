package at.aau.ase.mlg_party_app.spacepirate.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class spacepirateResults extends GameBaseRequest {
    public String lobbyId;

    public spacepirateResults(String lobbyId, String playerId, int max) {
        super(MessageType.ClickerResults, lobbyId, playerId);
        this.max = max;
    }

    public float max;

}
