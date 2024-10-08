package view;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundBorder implements Border {    //Classe permettant de consevoir les bordure arrondi

    private int arc;
    private Color color;

    //constructeur
    public RoundBorder(int arc, Color color) {
        this.arc = arc;
        this.color = color;
    }

    //méthodes permetant de créer les bordures lors de l'affichage GUI
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        ((Graphics2D) g).draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, arc, arc));
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(arc / 2, arc / 2, arc / 2, arc / 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}