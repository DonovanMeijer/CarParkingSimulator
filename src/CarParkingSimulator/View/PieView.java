package CarParkingSimulator.View;

import CarParkingSimulator.Model.DataSources.*;
import CarParkingSimulator.Model.*;

import java.awt.*;

/**
 * Class containing logic for drawing a pie chart.
 * @author Thom Boer, Wout Feringa, Donovan Meijer
 * @version 1.0
 */
public class PieView extends DataView
{
    private Dimension size;

    private int totalAmountOfSpaces;

    public PieView(Garage garage, DataSource dataSource)
    {
        super(garage, dataSource);

        size = new Dimension(0, 0);

        totalAmountOfSpaces = garage.getTotalAmountOfParkingPlaces();
    }

    public void updateView()
    {
        if (!size.equals(getSize()))
        {
            size.getSize();
        }

        repaint();
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(800, 500);
    }

    public void paintComponent(Graphics g)
    {
        double[] data = dataSource.getDataSource();

        Dimension currentSize = getSize();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, currentSize.width, currentSize.height);

        if(data[0] > 0 || data[1] > 0)
        {
            int totalAmount = totalAmountOfSpaces;
            int circleDiameter = 0;

            if(currentSize.height > currentSize.width)
            {
                circleDiameter = (int)(currentSize.width * 0.9);
            }
            else
            {
                circleDiameter = (int)(currentSize.height * 0.9);
            }

            int normalAngle = (int)((360 * data[0]) / totalAmount);
            int passHolderAngle = (int)((360 * data[1]) / totalAmount);
            int angle = 0;

            angle += normalAngle;

            g.setColor(Color.WHITE);

            g.fillArc(10, 10, circleDiameter, circleDiameter, 0, 360);

            g.setColor(Color.RED);

            g.fillArc(10, 10, circleDiameter, circleDiameter, 0, normalAngle);

            g.setColor(Color.BLUE);

            g.fillArc(10, 10, circleDiameter, circleDiameter, angle, passHolderAngle);

            g.setColor(Color.BLACK);

            g.drawOval(10,10,circleDiameter, circleDiameter);
        }
    }
}
