package toggle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public abstract class ItemBlank extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(230, 230, 230));
        g2.drawLine(0, 0, 0, getHeight() - 1);
        g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        g2.dispose();
    }

   
    /*public static abstract class SubItemBlank extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(230, 230, 230));
            g2.setColor(new Color(230, 230, 230));
            g2.drawLine(0, 0, 0, getHeight() - 1);
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            g2.dispose();
        }
    }*/
}
