package swingingAround.Cmd;

import swingingAround.Entities.Entity;
import swingingAround.Entities.LineEntity;
import swingingAround.Entities.PointEntity;
import swingingAround.ThreeD.Vec3;

import java.awt.*;
import java.util.ArrayList;

/**
 * Az egyenesek létrehozására képes parancs.
 *
 * <p>
 *     Két féle parancsot ismer fel, az egyikhez 7 "szó" kell,
 *     míg a másikhoz csak 3.
 *
 *     Az első parancs az a "line x1 y1 z1 x2 y2 z2", más szavakkal
 *     az a helyzet amikor az egyenest úgy adjuk meg, hogy az két
 *     ponton megy át, és nem a pontok nevét, hanem a pontok
 *     koordinátáit adjuk át.
 *
 *     A második opció az a "line p1 p2", ahol p1, és p2 már létező
 *     ezekkel a nevekkel (p1, p2) ellátott pontok.
 * </p>
 */
public class LineCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {
        if (cmd.length == 7) {
            double x1 = Double.parseDouble(cmd[1]);
            double y1 = Double.parseDouble(cmd[2]);
            double z1 = Double.parseDouble(cmd[3]);
            double x2 = Double.parseDouble(cmd[4]);
            double y2 = Double.parseDouble(cmd[5]);
            double z2 = Double.parseDouble(cmd[6]);

            conf.addLine(new LineEntity(new Vec3(x1,y1,z1), new Vec3(x2,y2,z2), Color.GREEN));
        }

        if (cmd.length == 3) {
            ArrayList<Entity> entities = conf.getEntities();
            PointEntity p1 = null;
            PointEntity p2 = null;

            for (Entity n: entities) {
                if (n.getName().equals(cmd[1])) {
                    p1 = (PointEntity) n;
                } else if (n.getName().equals(cmd[2])) {
                    p2 = (PointEntity) n;
                }
            }

            if ((p1 != null) && (p2 != null)) {
                conf.addLine(new LineEntity(p1.getPoint(), p2.getPoint(), Color.GREEN));
            }
        }

        return conf;
    }
}
