package swingingAround.Cmd;

import swingingAround.LineEntity;
import swingingAround.Vec3;

import java.awt.*;

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

            //LineEntity entity = new LineEntity(new Vec3(x1,y1,z1), new Vec3(x2,y2,z2), Color.GREEN);

            //conf.addEntity(new LineEntity(new Vec3(x1,y1,z1), new Vec3(x2,y2,z2), Color.GREEN));
            conf.addLine(new LineEntity(new Vec3(x1,y1,z1), new Vec3(x2,y2,z2), Color.GREEN));
            //conf.addLine((LineEntity) conf.getEntities().get(conf.getEntities().size()-1));
        }

        return conf;
    }
}
