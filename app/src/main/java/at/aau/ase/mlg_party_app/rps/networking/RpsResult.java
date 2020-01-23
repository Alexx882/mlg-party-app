package at.aau.ase.mlg_party_app.rps.networking;


import at.aau.ase.mlg_party_app.networking.dtos.game.GameBaseRequest;
import at.aau.ase.mlg_party_app.rps.RpsLogic;

public class RpsResult extends GameBaseRequest {

    public RpsLogic.Option option;

}
