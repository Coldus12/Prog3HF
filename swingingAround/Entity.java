package swingingAround;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Entity {

    public abstract Mathfunction[] getMathfunctions();

    public abstract String getFormula();
    public abstract void setFormula(String string);

    public abstract Color getColor();
    public abstract void setColor(Color newColor);

    public abstract String getName();
    public abstract void setName(String name);

    public abstract boolean stillExists();
    public abstract void delete();
}
