package swingingAround.Cmd;

public class SetSizeCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {

        if (cmd.length >= 3) {
            double size = 0;

            if (!(cmd[2].equals("true") || cmd[2].equals("false")))
                size = Double.parseDouble(cmd[2]);

            if (cmd[1].equals("width"))
                conf.setNumberOfTrianglesX((int) size);
            else if (cmd[1].equals("height"))
                conf.setNumberOfTrianglesY((int) size);
            else if (cmd[1].equals("length"))
                conf.setNumberOfTrianglesZ((int) size);
            else if (cmd[1].equals("stepSize"))
                conf.setStepSize(size);
            else if (cmd[1].equals("filled")) {
                if (cmd[2].equals("true")) {
                    conf.setGraphingTrianglesFilled(true);
                } else if (cmd[2].equals("false")) {
                    conf.setGraphingTrianglesFilled(false);
                }
            }
        }

        return conf;
    }
}
