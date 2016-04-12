package CarParkingSimulator.View;

import java.awt.*;

import CarParkingSimulator.Model.*;

/**
 * Class for making a Histogram for the daily revenues.
 * Created by wout on 4/11/2016.
 *
 */
public class HistogramView extends AbstractView
{
    private Color[] colors = new Color[7];
    private int[] revenue = new int[7];
    private String[] days = new String[7];

    private int amountOfBars;
    private double maxValue;
    private int pointWidth = 4;

    private int padding = 15;
    private int labelPadding = 25;
    private int numberYDivisions;

    private Color gridColor = new Color(200, 200, 200, 200);
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);

    /** Sourceting up the Histogram with the amount of revenue, colors and name of days.
     */
    public HistogramView(Garage garage)
    {
        super(garage);

        revenue[0] = 100;
        revenue[1] = 200;
        revenue[2] = 300;
        revenue[3] = 600;
        revenue[4] = 500;
        revenue[5] = 322;
        revenue[6] = 300;

        colors[0] = Color.red;
        colors[1] = Color.blue;
        colors[2] = Color.yellow;
        colors[3] = Color.green;
        colors[4] = Color.magenta;
        colors[5] = Color.pink;
        colors[6] = Color.gray;

        days[0] = "Monday";
        days[1] = "Tuesday";
        days[2] = "Wednesday";
        days[3] = "Thursday";
        days[4] = "Friday";
        days[5] = "Saturday";
        days[6] = "Sunday";

        amountOfBars = revenue.length;
        maxValue = 0;
        for (int i = 0; i < revenue.length; i++) {
            if (revenue[i] > maxValue) {
                maxValue = revenue[i];
            }
        }
        numberYDivisions = 10;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        int interval = (width - 40) / revenue.length;
        int individualWidth = (int)(((width - 40) / 12) * 0.60);

        int maxCount = 0;
        for (int i = 0; i < revenue.length; i++)
        {
            if (maxCount < revenue[i])
                maxCount = revenue[i];
        }

        // x is the start position for the first bar in the histogram
        int x = 60;

        // Draw a horizontal base line
        g2.drawLine(40, height - 45, width - 10, height - 45);
        //g.drawLine(40, height - 45, 40 , -height + 45);

        for (int i = 0; i < revenue.length; i++) {
            // Find the bar height
            int barHeight = (int)(((double)revenue[i] / (double)maxCount) * (height - 55));

            // Display a bar (i.e. rectangle)
            g2.drawRect(x, height - 45 - barHeight, individualWidth, barHeight);
            g2.setColor(colors[i]);
            g2.fillRect(x, height - 45 - barHeight, individualWidth, barHeight);
            g2.setColor(Color.black);

            String label = "";
            if(days[i] != null) {
                label = days[i];
            }

            g2.drawString(label, x, height - 30);
            // Move x for displaying the next character
            x += interval;
        }

        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (revenue.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((0 + (7 - 0) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
    }

    public void updateView()
    {
        repaint();
    }
}
