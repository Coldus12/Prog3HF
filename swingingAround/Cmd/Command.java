package swingingAround.Cmd;

/**
 * Parancsok számára készített interface
 * <p>
 *     Az interfacenek egyetlen függvénye az "execute",
 *     mely a parancsot "szavakra" tördelve fogadja be, és
 *     a neki átadott Config-on végez el változtatásokat, ha
 *     van rájuk szükség.
 * </p>
 */
public interface Command {

    /**
     * A parancsok végrehajtandó/hajtható függvénye.
     *
     * @param cmd a parancs "szavakra" tördelve
     * @param conf a konfigurációs fájl
     * @return a megváltoztatott konfigurációs fájl
     */
    public Config execute(String[] cmd, Config conf);

}
