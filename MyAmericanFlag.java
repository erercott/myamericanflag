import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyAmericanFlag extends JPanel {

    private final int blockSize = 20;
    private final int rows = 13;
    private final int cols = 30;

    private final int starRows = 7;
    private final int starCols = 12;

    private List<Star> stars = new ArrayList<>();
    private List<RedStripe> redStripes = new ArrayList<>();
    private int starStep = 0;

    public MyAmericanFlag() {
        setPreferredSize(new Dimension(cols * blockSize, rows * blockSize));

        // Stars initialization
        for (int r = 0; r < starRows; r++) {
            for (int c = 0; c < starCols; c++) {
                if ((r + c) % 2 == 0) {
                    stars.add(new Star(c, r));
                }
            }
        }

        // Red stripes initialization
        for (int r = 0; r < rows; r += 2) { // even rows are red
            redStripes.add(new RedStripe(r));
        }

        Timer timer = new Timer(600, e -> {  // faster for smoother animation
            starStep = (starStep + 1) % 2;
            updateStars();
            updateRedStripes();
            repaint();
        });
        timer.start();
    }

    private void updateStars() {
        for (Star s : stars) {
            if (s.baseY == 0 || s.baseY == starRows - 1) 
                s.offsetY = (starStep == 1) ? -1 : 0;
            else 
                s.offsetY = 0;

            if (s.baseY != 0 && s.baseY != starRows - 1) {
                if ((s.baseX + s.baseY) % 2 == 0) 
                    s.offsetX = (starStep == 1) ? -1 : 0;
                else 
                    s.offsetX = (starStep == 1) ? 1 : 0;
            } else s.offsetX = 0;
        }
    }

    private void updateRedStripes() {
        for (RedStripe stripe : redStripes) {
            stripe.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw white background first
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw red stripes with offsets
        g.setColor(Color.RED);
        for (RedStripe stripe : redStripes) {
            for (int c = 0; c < cols; c++) {
                int y = stripe.baseRow * blockSize + stripe.offsets[c] * blockSize;
                g.fillRect(c * blockSize, y, blockSize, blockSize);
            }
        }

        // Blue rectangle
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, starCols * blockSize, starRows * blockSize);

        // Stars
        g.setColor(Color.WHITE);
        for (Star s : stars) {
            g.fillRect((s.baseX + s.offsetX) * blockSize + 3,
                       (s.baseY + s.offsetY) * blockSize + 3,
                       blockSize - 6, blockSize - 6);
        }
    }

    class Star {
        int baseX, baseY;
        int offsetX = 0, offsetY = 0;

        public Star(int x, int y) {
            baseX = x;
            baseY = y;
        }
    }

    class RedStripe {
        int baseRow;
        int[] offsets;

        public RedStripe(int row) {
            baseRow = row;
            offsets = new int[cols];
        }

        public void update() {
            // Keep the original flip-flop per-column movement
            for (int c = 0; c < offsets.length; c++) {
                offsets[c] = (offsets[c] == 0) ? -1 : 0;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My American Flag");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MyAmericanFlag());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
// Thank you to all you ugly americans. 