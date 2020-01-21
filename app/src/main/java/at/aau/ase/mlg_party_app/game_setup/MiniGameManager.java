package at.aau.ase.mlg_party_app.game_setup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.cocktail_shaker.CocktailShakerActivity;
import at.aau.ase.mlg_party_app.tictactoe.TicTacToeActivity;

public class MiniGameManager {

    private MiniGameManager(){}

    public static Map<String, Class<? extends AppCompatActivity>> getGameMap() {
        HashMap<String, Class<? extends AppCompatActivity>> games = new HashMap<>();
        games.put("/game/shaker",CocktailShakerActivity.class );
        games.put("/game/tictactoe", TicTacToeActivity.class);
        return games;
    }

}
