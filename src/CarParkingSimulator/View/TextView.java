package CarParkingSimulator.View;

import CarParkingSimulator.Controller.SimulatorEngine;
import CarParkingSimulator.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by wout on 4/11/2016.
 */
public class TextView extends AbstractView
{
    public ArrayList<Car> normalCars;
    public ArrayList<Car> passHolderCars;

    private Garage garage;
    private SimulatorEngine simulatorEngine;

    public TextView(Garage garage, SimulatorEngine simulatorEngine)
    {
        super(garage);
        this.simulatorEngine = simulatorEngine;
        this.garage = garage;
        normalCars = new ArrayList<Car>();
        passHolderCars = new ArrayList<Car>();
    }

    public void updateView()
    {
        checkAllLocations();
        repaint();
    }

    public void checkAllLocations()
    {
        normalCars = new ArrayList<Car>();
        passHolderCars = new ArrayList<Car>();
        for (int floor = 0; floor < garage.getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < garage.getNumberOfRows(); row++)
            {
                for (int place = 0; place < garage.getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    if (garage.getCarAt(location) != null)
                    {
                        Car car = garage.getCarAt(location);
                        if(car instanceof NormalCar)
                        {
                            normalCars.add(car);
                        }
                        else if (car instanceof PassHolderCar)
                        {
                            passHolderCars.add(car);
                        }
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawString("Total amount of normal cars: " + normalCars.size(), 20, 40);
        g2.drawString("Total amount of pass holder cars: " + passHolderCars.size(),20, 60);
        g2.drawString("Total Amount of cars: " + (normalCars.size() + passHolderCars.size()), 20 ,20);
        g2.drawString("People in queue at the entrance: " + simulatorEngine.getEntranceCarQueue().getQueue().size(),20, 80);
        g2.drawString("People in queue at the ticket machine: " + simulatorEngine.getPaymentCarQueue().getQueue().size() ,20, 100);
        g2.drawString("People in queue at the exit: " + simulatorEngine.getExitCarQueue().getQueue().size() ,20, 120);
    }
}
