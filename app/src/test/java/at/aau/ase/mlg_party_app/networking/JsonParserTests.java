package at.aau.ase.mlg_party_app.networking;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeErrorResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveRequest;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveResponse;

/**
 * Tests if the request and response classes are as defined in the wiki.
 */
public class JsonParserTests {
    private JsonParser parser;

    @Before
    public void before() {
        parser = new JsonParser();
    }

    @Test
    public void toJson_null_toNull() {
        Assert.assertEquals("null", parser.toJson(null));
    }

    @Test
    public void toJson_Object_emptyJson() {
        Object o = new Object();
        Assert.assertEquals("{}", parser.toJson(o));
    }

    @Test
    public void toJson_EnumToString_validJson() {
        CreateLobbyRequest req = new CreateLobbyRequest();
        req.type = MessageType.CreateLobby;

        Assert.assertEquals("{\"type\":\"CreateLobby\"}", parser.toJson(req));
    }

    @Test
    public void toJson_CreateLobbyResponse_validJson() {
        CreateLobbyResponse res = new CreateLobbyResponse();
        res.lobbyId = "nice lobby";

        Assert.assertEquals("{\"lobbyId\":\"nice lobby\"}", parser.toJson(res));
    }

    @Test(expected = JsonSyntaxException.class)
    public void fromJson_invalidJson_Exception() {
        String json = "{\"type\":";

        Object req = parser.fromJson(json, Object.class);
    }

    @Test
    public void fromJson_StringToJson_BaseRequest() {
        String json = "{\"type\":\"JoinLobby\"}";
        BaseRequest req = parser.fromJson(json, BaseRequest.class);

        Assert.assertNotEquals(null, req);
        Assert.assertEquals(MessageType.JoinLobby, req.type);
    }

    @Test
    public void fromJson_StringToJson_GameFinishedResponse() {
        String json = "{\"type\":\"GameFinished\", \"winnerId\":\"String\", \"ranking\":[{\"id\":\"123\",\"name\":\"Alex\",\"points\":1}]}";
        GameFinishedResponse req = parser.fromJson(json, GameFinishedResponse.class);

        assertResponseContainsSinglePlayer("Alex", 1, req);
    }

    private void assertResponseContainsSinglePlayer(String expectedPlayerName, int expectedPlayerPoints, GameFinishedResponse req)
            throws AssertionError {
        Assert.assertNotEquals(null, req);
        Assert.assertEquals(MessageType.GameFinished, req.type);
        Assert.assertEquals(1, req.ranking.size());
        Assert.assertEquals(expectedPlayerName, req.ranking.get(0).name);
        Assert.assertEquals(expectedPlayerPoints, req.ranking.get(0).points);
    }

    //TICTACTOE PARSER TESTS
    /*
    @Test
    public void toJson_TicTacToeMoveRequest_validJson() {
        TicTacToeMoveRequest req = new TicTacToeMoveRequest( 1, 2);

        Assert.assertEquals("{\"playerId\":\"PID\",\"x\":1,\"y\":2,\"type\":\"TicTacToeMove\"}", parser.toJson(req));
    }
    @Test
    public void toJson_TicTacToeMoveResponse_validJson(){
        TicTacToeMoveResponse res = new TicTacToeMoveResponse() ;
        res.playerId="PID";
        res.x=1;
        res.y=2;

        Assert.assertEquals("{\"playerId\":\"PID\",\"x\":1,\"y\":2,\"type\":\"TicTacToeMove\"}", parser.toJson(res));
    }
    @Test
    public void toJson_TicTacToeErrorResponse_validJson(){
        TicTacToeErrorResponse res = new TicTacToeErrorResponse() ;
        res.errorMessage="A MESSAGE!";

        Assert.assertEquals("{\"errorMessage\":\"A MESSAGE!\",\"type\":\"TicTacToeError\"}", parser.toJson(res));
    }*/

}
