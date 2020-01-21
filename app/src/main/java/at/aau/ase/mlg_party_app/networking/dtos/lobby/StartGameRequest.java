package at.aau.ase.mlg_party_app.networking.dtos.lobby;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;

public class StartGameRequest extends BaseRequest {

    public String lobbyName;

    public StartGameRequest() {
    }

    public StartGameRequest(String lobbyName) {
        super(MessageType.StartGame);
        this.lobbyName = lobbyName;
    }

}
