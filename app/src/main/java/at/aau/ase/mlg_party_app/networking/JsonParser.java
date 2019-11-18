package at.aau.ase.mlg_party_app.networking;

import com.google.gson.Gson;

public class JsonParser {
    private Gson gson = new Gson();

    public String toJson(Object o) {
        return gson.toJson(o);
    }

    public <T> T fromJson(String json, Class<T> c) {
        return gson.fromJson(json, c);
    }
}
