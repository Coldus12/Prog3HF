package swingingAround.Cmd;

import swingingAround.Entities.FunctionEntity;

/**
 * A térbeli függvények létrehozására képes parancs.
 * <p>
 *     A térbeli függvények egy adott (x,y,z) tengelyre kell,
 *     hogy kilegyenek fejezve. Példa: y=3*e^(-(x^2/4+z^2/4))
 * </p>
 */
public class FunctionCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {
        if (cmd.length >= 2) {
            conf.addEntity(new FunctionEntity(cmd));
        }

        return conf;
    }
}
