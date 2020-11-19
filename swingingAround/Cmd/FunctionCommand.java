package swingingAround.Cmd;

import swingingAround.FunctionEntity;

public class FunctionCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {
        if (cmd.length >= 3) {
            //System.out.println("YEA BOI");
            conf.addEntity(new FunctionEntity(cmd));
        }

        return conf;
    }
}
