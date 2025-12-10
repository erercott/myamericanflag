import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyAmericanFlag extends JPanel {

    private final int blockSize = 20;
    private final int rows = 13;  // stripes
    private final int cols = 30;  // width in blocks

    private final int starRows = 7;
    private final int starCols = 12;

    private List<Star> stars = new ArrayList<>();
    private int animationStep = 0;

    public MyAmericanFlag() {
        setPreferredSize(new Dimension(cols * blockSize, rows * blockSize));

        // Initialize stars at fixed grid positions
        for (int r = 0; r < starRows; r++) {
            for (int c = 0; c < starCols; c++) {
                if ((r + c) % 2 == 0) { // staggered checkerboard pattern
                    stars.add(new Star(c, r));
                }
            }
        }

        Timer timer = new Timer(800, e -> { // Jasper Johns eat your heart out.
            updateStars();
            repaint();
        });
        timer.start();
    }

    private void updateStars() {
        // Cycle animation step for all stars
        animationStep = (animationStep + 1) % 2; // 0 = base, 1 = offset

        for (Star s : stars) {
            // Vertical shift: top and bottom rows
            if (s.baseY == 0 || s.baseY == starRows - 1) {
                s.offsetY = (animationStep == 1) ? -1 : 0; // move up one block then back
            } else {
                s.offsetY = 0;
            }

            // Horizontal shift: alternate stars left/right
            if (s.baseY != 0 && s.baseY != starRows - 1) {
                if ((s.baseX + s.baseY) % 2 == 0) {
                    s.offsetX = (animationStep == 1) ? -1 : 0; // move left then back
                } else {
                    s.offsetX = (animationStep == 1) ? 1 : 0; // move right then back
                }
            } else {
                s.offsetX = 0;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Draw stripes (static)
        for (int row = 0; row < rows; row++) {
            Color stripeColor = (row % 2 == 0) ? Color.RED : Color.WHITE;
            g.setColor(stripeColor);
            g.fillRect(0, row * blockSize, cols * blockSize, blockSize);
        }

        // Draw blue rectangle
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, starCols * blockSize, starRows * blockSize);

        // Draw stars with offsets
        g.setColor(Color.WHITE);
        for (Star s : stars) {
            g.fillRect((s.baseX + s.offsetX) * blockSize + 3,
                       (s.baseY + s.offsetY) * blockSize + 3,
                       blockSize - 6, blockSize - 6);
        }
    }

    class Star {
        int baseX, baseY;
        int offsetX = 0;
        int offsetY = 0;

        public Star(int x, int y) {
            baseX = x;
            baseY = y;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My American Flag - Star Pulse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MyAmericanFlag());
        frame.pack();
        frame.setVisible(true);
    }
}
