package at.aau.ase.mlg_party_app.tictactoe.networking;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;

public class TicTacToeErrorResponse extends BaseResponse {
   public TicTacToeErrorResponse(){
      type= MessageType.TicTacToeError;
   }
   public String errorMessage;
}
