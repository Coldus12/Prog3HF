package swingingAround.Cmd;

import swingingAround.FunctionEntity;

public class FunctionCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {
        if (cmd.length >= 2) {
            conf.addEntity(new FunctionEntity(cmd));
        }

        return conf;
    }
}
