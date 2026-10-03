package enrollment;   // must match your project's package (your imports said "carrental")

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class UIStyleHelper {

    // Draws a filled rounded rectangle using the component's own background color
    private static void fillRound(Graphics g, Component c, int radius) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getBackground());
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
        g2.dispose();
    }

    // Draws the rounded outline
    private static void outlineRound(Graphics g, Component c, int radius, Color color) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
        g2.dispose();
    }

    // ---------- Rounded panel (children are clipped to the rounded shape) ----------
    public static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private Color borderColor;   // null = no outline

        public RoundedPanel(int radius) {
            this(radius, null);
        }

        public RoundedPanel(int radius, Color borderColor) {
            super();
            this.cornerRadius = radius;
            this.borderColor = borderColor;
            setOpaque(false);
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            fillRound(g, this, cornerRadius);          // fills with the panel's background color
        }

        @Override
        protected void paintChildren(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.clip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            super.paintChildren(g2);                   // child panels can't poke out of the corners
            g2.dispose();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (borderColor != null) {
                outlineRound(g, this, cornerRadius, borderColor);
            }
        }
    }

    // ---------- Rounded text field ----------
    public static class RoundedTextField extends JTextField {
        private final int radius;

        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));   // inner padding
        }

        @Override
        protected void paintComponent(Graphics g) {
            fillRound(g, this, radius);
            super.paintComponent(g);                   // draws the text and caret
            outlineRound(g, this, radius, new Color(180, 180, 180));
        }
    }

    // ---------- Rounded password field ----------
    public static class RoundedPasswordField extends JPasswordField {
        private final int radius;

        public RoundedPasswordField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            fillRound(g, this, radius);
            super.paintComponent(g);
            outlineRound(g, this, radius, new Color(180, 180, 180));
        }
    }

    // ---------- Rounded button ----------
    public static class RoundedButton extends JButton {
        private final int radius;

        public RoundedButton(int radius) {
            this("", radius);
        }

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? getBackground().darker() : getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);                   // draws the button text
        }
    }
}
