package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class TicTacToeMoveRequest extends GameBaseRequest {

    int x;
    int y;

    public TicTacToeMoveRequest(int x, int y) {
        super(MessageType.TicTacToeMove);
        this.x = x;
        this.y = y;
    }
}
