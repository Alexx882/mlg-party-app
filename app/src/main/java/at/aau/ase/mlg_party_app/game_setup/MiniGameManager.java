package at.aau.ase.mlg_party_app.game_setup;

import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.clicker.ClickerGame;
import at.aau.ase.mlg_party_app.cocktail_shaker.CocktailShakerActivity;
import at.aau.ase.mlg_party_app.tictactoe.TicTacToeActivity;
import at.aau.ase.mlg_party_app.quiz.QuizGame;

public class MiniGameManager {

    private MiniGameManager(){}

    public static Map<String, Class<? extends BasicGameActivity>> getGameMap() {
        HashMap<String, Class<? extends BasicGameActivity>> games = new HashMap<>();
        games.put("/game/shaker",CocktailShakerActivity.class );
        games.put("/game/tictactoe", TicTacToeActivity.class);
        games.put("/game/quiz", QuizGame.class);
        games.put("/game/clicker", ClickerGame.class);

        return games;
    }

}
