package at.aau.ase.mlg_party_app;

public class Game {

    private Game() {

    }

    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /**
     * The unique player id for the current lobby.
     */
    private String playerId;

    /**
     * The global id  of the lobby.
     */
    private String lobbyId;

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
}
