import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroovingLines extends JPanel {

    private SnakeLine redLine;
    private SnakeLine blueLine;
    private SnakeLine greenLine;
    private final int width = 400;
    private final int height = 200;
    private final int blockSize = 20; // grid cell size

    public GroovingLines() {
        redLine = new SnakeLine(Color.RED, 1);
        blueLine = new SnakeLine(Color.BLUE, 4);
        greenLine = new SnakeLine(Color.GREEN, 7);

        Timer timer = new Timer(150, e -> updateAndRepaint()); // slower for blocky feel
        timer.start();
    }

    private void updateAndRepaint() {
        redLine.update(width / blockSize, height / blockSize);
        blueLine.update(width / blockSize, height / blockSize);
        greenLine.update(width / blockSize, height / blockSize);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        redLine.draw(g);
        blueLine.draw(g);
        greenLine.draw(g);
    }

    // Snake-like line
    static class SnakeLine {
        private List<Point> segments = new ArrayList<>();
        private Color color;
        private int length = 10; // number of segments
        private boolean goingDown = true; // vertical direction for the head
        private Random rand = new Random();

        public SnakeLine(Color color, int startYGrid) {
            this.color = color;
            // initialize segments horizontally offscreen
            for (int i = 0; i < length; i++) {
                segments.add(new Point(20 + i, startYGrid));
            }
        }

        public void update(int gridWidth, int gridHeight) {
            // save previous positions
            List<Point> oldPositions = new ArrayList<>();
            for (Point p : segments) oldPositions.add(new Point(p));

            // move head
            Point head = segments.get(0);
            head.x--; // move left by 1 block

            // wrap around horizontally
            if (head.x < -length) head.x = gridWidth;

            // move head up/down every few blocks
            if (head.x % 5 == 0) {
                if (goingDown) {
                    if (head.y < gridHeight - 1) head.y++;
                    else goingDown = false;
                } else {
                    if (head.y > 0) head.y--;
                    else goingDown = true;
                }
            }

            // move body segments to previous segment's old position
            for (int i = 1; i < segments.size(); i++) {
                segments.get(i).x = oldPositions.get(i - 1).x;
                segments.get(i).y = oldPositions.get(i - 1).y;
            }
        }

        public void draw(Graphics g) {
            g.setColor(color);
            for (Point p : segments) {
                g.fillRect(p.x * 20, p.y * 20, 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grooving Lines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(new GroovingLines());
        frame.setVisible(true);
    }
}
