package at.aau.ase.mlg_party_app.networking;

import android.os.Message;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;

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
}
