package at.aau.ase.mlg_party_app.networking;

/**
 * Message types the server understands.
 */
public enum MessageType {
    CreateLobby
    , JoinLobby
    , PlayerJoined
    , StartGame

    // cocktail shaker
    , CocktailShakerResult

    // Rps
    , RpsResult

    // Quiz
    , QuizResult
}