package at.aau.ase.mlg_party_app.networking.dtos;

import at.aau.ase.mlg_party_app.networking.MessageType;

public class BaseRequest {

    public MessageType type;

    public BaseRequest() {
    }

    public BaseRequest(MessageType type) {
        super();
        this.type = type;
    }
}
