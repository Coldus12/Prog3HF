package swingingAround.Cmd;

import swingingAround.Entities.Entity;
import swingingAround.Entities.PointEntity;
import swingingAround.Entities.SphereEntity;
import swingingAround.ThreeD.Vec3;

import java.awt.*;
import java.util.ArrayList;

/**
 * A gömbök létrehozására képes parancs.
 *
 * <p>
 *     Két féle parancsot ismer fel, az egyikhez 5 "szó" kell,
 *     míg a másikhoz csak 3.
 *
 *     Az első parancs az a "sphere x1 y1 z1 r", más szavakkal
 *     az a helyzet amikor egy gömböt úgy adjuk meg, hogy megadjuk
 *     a középpontjának a három koordinátáját, és ezen kívűl megadjuk
 *     a sugarának hosszát.
 *
 *     A második opció az a "sphere p1 r", ahol p1 már létező, ezzel
 *     a névvel ellátott pont (ez lesz a gömb középpontja), és r pedig
 *     a gömb sugara.
 * </p>
 */
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
