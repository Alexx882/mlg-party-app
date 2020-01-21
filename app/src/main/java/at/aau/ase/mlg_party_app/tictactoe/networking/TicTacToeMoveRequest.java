package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;

public class TicTacToeMoveRequest extends BaseRequest {

    String playerId;
    int x,y;

    public TicTacToeMoveRequest(String playerId, int x, int y) {
        type= MessageType.TicTacToeMove;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }
}
