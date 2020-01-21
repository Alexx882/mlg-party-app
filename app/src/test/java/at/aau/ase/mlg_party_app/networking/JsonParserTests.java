package at.aau.ase.mlg_party_app.networking;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeErrorResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveRequest;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveResponse;

public class JsonParserTests {
    private JsonParser parser;

    @Before
    public void before(){
        parser = new JsonParser();
    }

    @Test
    public void toJson_null_toNull(){
        Assert.assertEquals("null", parser.toJson(null));
    }

    @Test
    public void toJson_Object_emptyJson(){
        Object o = new Object();
        Assert.assertEquals("{}", parser.toJson(o));
    }

    @Test
    public void toJson_EnumToString_validJson(){
        CreateLobbyRequest req = new CreateLobbyRequest() ;
        req.type = MessageType.CreateLobby;

        Assert.assertEquals("{\"type\":\"CreateLobby\"}", parser.toJson(req));
    }

    @Test
    public void toJson_CreateLobbyResponse_validJson(){
        CreateLobbyResponse res = new CreateLobbyResponse() ;
        res.lobbyName = "nice lobby";

        Assert.assertEquals("{\"lobbyName\":\"nice lobby\"}", parser.toJson(res));
    }

    @Test(expected = JsonSyntaxException.class)
    public void fromJson_invalidJson_Exception(){
        String json = "{\"type\":";

        Object req = parser.fromJson(json, Object.class);
    }

    @Test
    public void fromJson_StringToJson_BaseRequest(){
        String json = "{\"type\":\"JoinLobby\"}";
        BaseRequest req = parser.fromJson(json, BaseRequest.class);

        Assert.assertNotEquals(null, req);
        Assert.assertEquals(MessageType.JoinLobby, req.type);
    }

    //TICTACTOE PARSER TESTS
    @Test
    public void toJson_TicTacToeMoveRequest_validJson() {
        TicTacToeMoveRequest req = new TicTacToeMoveRequest("PID", 1, 2);

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
    }

}
