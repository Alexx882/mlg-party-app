package at.aau.ase.mlg_party_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    private Game() {
        playerRanking = new ArrayList<>(2);
    }

    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public static void setInstance(Game game){
        instance = game;
    }

    /**
     * The unique player id for the current lobby.
     */
    private String playerId;

    /**
     * The global id  of the lobby.
     */
    private String lobbyId;

    /**
     * True if the user started the game and owns the lobby.
     */
    private boolean isLobbyOwner;

    private String lastWinnerId;

    private List<PlayerInfo> playerRanking;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public boolean isLobbyOwner() {
        return isLobbyOwner;
    }

    public void setLobbyOwner(boolean lobbyOwner) {
        isLobbyOwner = lobbyOwner;
    }

    public List<PlayerInfo> getPlayerRanking() {
        return playerRanking;
    }

    public void setPlayerRanking(List<PlayerInfo> playerRanking) {
        this.playerRanking = playerRanking;
    }

    public String getLastWinnerId() {
        return lastWinnerId;
    }

    public void setLastWinnerId(String lastWinnerId) {
        this.lastWinnerId = lastWinnerId;
    }


}
