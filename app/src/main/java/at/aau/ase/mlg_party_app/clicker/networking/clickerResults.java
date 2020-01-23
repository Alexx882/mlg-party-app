package at.aau.ase.mlg_party_app.clicker.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class clickerResults extends GameBaseRequest {

    public clickerResults() {
        type = MessageType.ClickerResults;
    }

    public float max;

}
