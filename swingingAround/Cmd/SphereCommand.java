package swingingAround.Cmd;

import swingingAround.*;

import java.awt.*;
import java.util.ArrayList;

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

        if (cmd.length == 3) {
            ArrayList<Entity> entities = conf.getEntities();
            PointEntity p = null;

            for (Entity n: entities) {
                if (n.getName().equals(cmd[1]))
                    p = (PointEntity) n;
            }

            if (p != null) {
                conf.addEntity(new SphereEntity(p.getPoint(),Float.parseFloat(cmd[2]),Color.GREEN));
            }
        }

        return conf;
    }
}
