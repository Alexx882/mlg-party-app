package at.aau.ase.mlg_party_app.spacepirate.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class spacepirateResults extends GameBaseRequest {


    public spacepirateResults() {
        type = MessageType.SpacePiratesResults;
    }

    public float max;

}
