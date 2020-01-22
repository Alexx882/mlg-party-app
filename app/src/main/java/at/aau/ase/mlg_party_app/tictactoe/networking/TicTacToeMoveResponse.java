package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;

public class TicTacToeMoveResponse extends BaseResponse{
    public TicTacToeMoveResponse(){
        type= MessageType.TicTacToeMove;
    }
    public String playerId;
    public String lobbyId;
    public int x, y;
}
