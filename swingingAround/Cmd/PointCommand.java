package swingingAround.Cmd;

import swingingAround.Entities.PointEntity;
import swingingAround.ThreeD.Vec3;

import java.awt.*;

/**
 * Pontok létrehozására képes parancs.
 */
public class PointCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {

        if (cmd.length >= 4) {
            Vec3 p = new Vec3(Double.parseDouble(cmd[1]),Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]));
            conf.addPoint(new PointEntity(p, Color.GREEN));
        }

        return conf;
    }
}
