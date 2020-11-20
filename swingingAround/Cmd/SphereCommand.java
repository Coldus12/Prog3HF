package swingingAround.Cmd;

import swingingAround.SphereEntity;
import swingingAround.Vec3;

import java.awt.*;

public class SphereCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {
        if (cmd.length == 5) {
            double x = Double.parseDouble(cmd[1]);
            double y = Double.parseDouble(cmd[2]);
            double z = Double.parseDouble(cmd[3]);
            double r = Double.parseDouble(cmd[4]);

            conf.addEntity(new SphereEntity(new Vec3(x,y,z),(float) r, Color.GREEN));
        }

        return conf;
    }
}
