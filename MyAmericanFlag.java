import javax.swing.*;
import java.awt.*;

public class MyAmericanFlag extends JPanel {

    private final int blockSize = 20;
    private final int rows = 13; // 13 stripes
    private final int cols = 30; // width of flag in blocks

    public MyAmericanFlag() {
        setPreferredSize(new Dimension(cols * blockSize, rows * blockSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Draw stripes
        for (int row = 0; row < rows; row++) {
            Color stripeColor = (row % 2 == 0) ? Color.RED : Color.WHITE;
            g.setColor(stripeColor);
            g.fillRect(0, row * blockSize, cols * blockSize, blockSize);
        }

        // Draw blue rectangle (stars area)
        int starRows = 7;   // approx rows for stars
        int starCols = 12;  // width in blocks
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, starCols * blockSize, starRows * blockSize);

        // Draw simplified stars as white blocks
        g.setColor(Color.WHITE);
        int starSpacingX = 2;
        int starSpacingY = 1;
        for (int r = 0; r < starRows; r++) {
            for (int c = 0; c < starCols; c++) {
                if ((r + c) % 2 == 0) { // stagger pattern
                    g.fillRect(c * blockSize + 3, r * blockSize + 3, blockSize - 6, blockSize - 6);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My American Flag");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MyAmericanFlag());
        frame.pack();
        frame.setVisible(true);
    }
}
