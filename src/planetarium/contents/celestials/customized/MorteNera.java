/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.celestials.customized;

import planetarium.console.GeneralFormatter;
import planetarium.contents.celestials.Moon;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.Registry;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.utils.AudioPlayer;
import planetarium.contents.system.utils.NamePicker;

/**
 * E' proprio lei. Inclusi gli effetti audio!
 *
 * @author TTT
 */
public class MorteNera extends Moon {

    private final static MorteNera istance = new MorteNera(NamePicker.getIstance().getName(CelestialType.MORTE_NERA), 147, GestioneSistema.getRandomPosition());

    final AudioPlayer ap = new AudioPlayer();
    private static boolean destroyed = false;

    private MorteNera(String name, double mass, Position position) {
        super(name, mass, position);
    }

    public static MorteNera getIstance() {
        return destroyed ? null : istance;
    }

    private ICelestial parent;

    @Override
    public void setParent(ICelestial ic) {
        if (ic != null) {
            parent = ic;
        }
    }

    @Override
    public ICelestial getParent() {
        return parent;
    }

    @Override
    public CelestialType getType() {
        return CelestialType.MORTE_NERA;
    }

    @Override
    public void destroy() {
        GeneralFormatter.printOut("Abbandonare la stazione!", true, true);
        ap.playAudioJoined("/planetarium/resources/audio/dtds.wav");
        destroyed = true;
        Registry.removeEntry(this);
    }

    public void toDeath(ICelestial toDestroy) {
        if (toDestroy != null) {
            ap.playAudioJoined("/planetarium/resources/audio/dsdoa.wav");
            toDestroy.destroy();
        } else {
            GeneralFormatter.printOut("Nessun oggetto agganciato: impossibile iniziare la sequenza!", true, true);
        }
    }

}
