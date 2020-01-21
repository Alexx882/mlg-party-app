package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;

public class TicTacToeEndGameResponse extends BaseResponse {
    public TicTacToeEndGameResponse(){
        type= MessageType.TicTacToeEndGame;
    }
    public int endgameScenarioId;
}
