package at.aau.ase.mlg_party_app.networking.dtos;

public class TicTacToeMoveRequest extends BaseRequest {

    String playerId;
    int x,y;

    public TicTacToeMoveRequest(String playerId, int x, int y) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }
}
