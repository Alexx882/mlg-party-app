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
    private String otherPlayerId;
    /**
     * The global id  of the lobby.
     */
    private String lobbyId;

    /**
     * True if the user started the game and owns the lobby.
     */
    private boolean isLobbyOwner;

    /**
     *
     * Score of players
     */
    private int playerScore;
    private int otherPlayerScore;

    public String getPlayerId() {
        return playerId;
    }
    public String getOtherPlayerId() {
        return otherPlayerId;
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

    public void setOtherPlayerId(String otherPlayerId) {
        this.otherPlayerId = otherPlayerId;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getOtherPlayerScore() {
        return otherPlayerScore;
    }

    public void setOtherPlayerScore(int otherPlayerScore) {
        this.otherPlayerScore = otherPlayerScore;
    }
}
