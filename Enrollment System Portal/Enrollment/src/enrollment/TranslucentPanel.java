import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * A JPanel that paints itself as a semi-transparent, rounded-corner
 * "glass" card instead of a solid rectangle. Use this in place of a
 * regular JPanel wherever you want the frosted-glass login-card look.
 *
 * Usage in NetBeans GUI Builder:
 *   1. Design your login card normally as a JPanel (add labels, text
 *      fields, buttons, use the layout editor as usual).
 *   2. Select that panel, open the Properties window, go to the
 *      "Code" tab, and set "Custom Creation Code" to:
 *          new TranslucentPanel()
 *   3. Make sure this panel sits inside a container that already has
 *      your background image painted behind it (e.g. a JLayeredPane,
 *      or a parent panel using null/absolute layout).
 */
public class TranslucentPanel extends JPanel {

    // Alpha (0-255): lower = more see-through, higher = more solid
    private Color panelColor = new Color(15, 25, 45, 160);
    private int cornerRadius = 20;

    public TranslucentPanel() {
        // Tell Swing NOT to paint a normal opaque background for us -
        // we'll paint our own translucent one in paintComponent().
        setOpaque(false);
    }

    /** Change the fill color / alpha at runtime, e.g. new Color(0,0,0,120) */
    public void setPanelColor(Color color) {
        this.panelColor = color;
        repaint();
    }

    public Color getPanelColor() {
        return panelColor;
    }

    /** Change how rounded the corners are. */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Semi-transparent rounded rectangle fill
            g2.setColor(panelColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Optional: thin light border to sell the "glass edge" look
            g2.setColor(new Color(255, 255, 255, 60));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        } finally {
            g2.dispose();
        }
        // Let child components (labels, fields, buttons) paint on top
        super.paintComponent(g);
    }
}
