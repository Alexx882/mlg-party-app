package at.aau.ase.mlg_party_app.networking;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.EOFException;

import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyResponse;

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
    public void toJson_CreateLobbyRequest_validJson(){
        CreateLobbyRequest req = new CreateLobbyRequest() ;
        req.type = "test";

        Assert.assertEquals("{\"type\":\"test\"}", parser.toJson(req));
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
    public void fromJson_validJson_BaseRequest(){
        String json = "{\"type\":\"lol 123\"}";
        BaseRequest req = parser.fromJson(json, BaseRequest.class);

        Assert.assertNotEquals(null, req);
        Assert.assertEquals("lol 123", req.type);
    }


}
