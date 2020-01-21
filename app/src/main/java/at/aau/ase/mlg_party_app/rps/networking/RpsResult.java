package at.aau.ase.mlg_party_app.rps.networking;


import at.aau.ase.mlg_party_app.networking.dtos.game.BaseRequest;
import at.aau.ase.mlg_party_app.rps.RpsLogic;

public class RpsResult extends BaseRequest {

    public RpsLogic.Option option;

}
