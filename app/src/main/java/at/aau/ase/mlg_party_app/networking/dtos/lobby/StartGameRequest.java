package at.aau.ase.mlg_party_app.networking.dtos.lobby;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;

public class StartGameRequest extends BaseRequest {

    public String lobbyId;

    public StartGameRequest() {
    }

    public StartGameRequest(String lobbyId) {
        super(MessageType.StartGame);
        this.lobbyId = lobbyId;
    }

}
