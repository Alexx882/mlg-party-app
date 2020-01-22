package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;

public class TicTacToeMoveRequest extends BaseRequest {

    String playerId;
    String lobbyId;
    int x;
    int y;

    public TicTacToeMoveRequest(String playerId, int x, int y) {
        type= MessageType.TicTacToeMove;
        this.playerId = playerId;
        this.lobbyId= Game.getInstance().getLobbyId();
        this.x = x;
        this.y = y;
    }
}
