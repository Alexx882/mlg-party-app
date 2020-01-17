package at.aau.ase.mlg_party_app.game_setup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import at.aau.ase.mlg_party_app.cocktail_shaker.CocktailShakerActivity;

public class MiniGameManager {

    public static HashMap<String, Class<? extends AppCompatActivity>> getGameMap() {
        HashMap<String, Class<? extends AppCompatActivity>> games = new HashMap<>();

        games.put("/game/shaker", CocktailShakerActivity.class);

        return games;
    }

}
