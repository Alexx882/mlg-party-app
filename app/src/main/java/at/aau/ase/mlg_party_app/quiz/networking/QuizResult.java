package at.aau.ase.mlg_party_app.quiz.networking;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;

public class QuizResult extends GameBaseRequest {

    public boolean won;

    public QuizResult(String lobbyId, String playerId, boolean won) {
        super(MessageType.QuizResult, lobbyId, playerId);
        this.won = won;
    }

}
