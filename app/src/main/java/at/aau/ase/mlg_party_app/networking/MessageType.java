package at.aau.ase.mlg_party_app.networking;

/**
 * Message types the server understands.
 */
public enum MessageType {
    CreateLobby
    , JoinLobby
    , PlayerJoined
    , StartGame

    // generic game
    , GameFinished
    , HelloGame

    // cocktail shaker
    , CocktailShakerResult

    //TicTacToe
    , TicTacToeMove
    , TicTacToeError

    // Rps
    , RpsResult

    // Quiz
    , QuizResult
}