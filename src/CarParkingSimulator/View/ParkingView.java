package CarParkingSimulator.View;

import CarParkingSimulator.Model.*;

import javax.swing.*;
import java.awt.*;

public class ParkingView extends AbstractView
{
    private Dimension size;
    private Image carParkImage;

    /**
     * Constructor for objects of class CarPark
     */
    public ParkingView(Garage garage)
    {
        super(garage);

        size = new Dimension(0, 0);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g)
    {
        if (carParkImage == null)
        {
            return;
        }

        Dimension currentSize = getSize();

        if (size.equals(currentSize))
        {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else
        {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView()
    {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize()))
        {
            size = getSize();

            carParkImage = createImage(size.width, size.height);
        }

        Graphics graphics = carParkImage.getGraphics();

        for(int floor = 0; floor < garage.getNumberOfFloors(); floor++)
        {
            for(int row = 0; row < garage.getNumberOfRows(); row++)
            {
                for(int place = 0; place < garage.getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    Car car = garage.getCarAt(location);
                    Color color = Color.white;

                    //if(car instanceof CarParkingSimulator.Model.NormalCar)
                        color = car == null ? Color.white : Color.red;
                    //else if(car instanceof CarParkingSimulator.Model.PassHolderCar)
                        //color = car == null ? Color.white : Color.blue;



                    newDrawPlace(graphics, location, color);
                }
            }
        }

        repaint();
    }

    private void newDrawPlace(Graphics graphics, Location location, Color color)
    {
        //TODO: Add dynamic width.
        Dimension currentSize = getSize();

        graphics.setColor(color);

        double places = garage.getNumberOfPlaces();
//(1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 25
        double x = location.getFloor() * ((1 + (int)Math.floor(garage.getNumberOfRows() * 0.5)) * 75 + (garage.getNumberOfRows() % 2) * 25) + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 25;
        double y = location.getPlace() * ((double)currentSize.height / places);
        double width = 20;
        double height = (((double)currentSize.height / places) / 100 * 75);

        graphics.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    private double caliculateAspectRatio()
    {
        //TODO: Implement method.

        return 1;
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color)
    {
        graphics.setColor(color);

        graphics.fillRect(location.getFloor() * 500 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20, 60 + location.getPlace() * 10, 20 - 1, 10 - 1); // TODO use dynamic size or constants
    }
}