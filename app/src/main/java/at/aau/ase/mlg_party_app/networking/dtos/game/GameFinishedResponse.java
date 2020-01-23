package at.aau.ase.mlg_party_app.networking.dtos.game;

import java.util.List;

import at.aau.ase.mlg_party_app.PlayerInfo;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;

public class GameFinishedResponse extends BaseResponse {
    public String winnerId;
    public List<PlayerInfo> ranking;
}
