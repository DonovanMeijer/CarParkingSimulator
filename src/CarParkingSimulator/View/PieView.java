package CarParkingSimulator.View;

import CarParkingSimulator.Model.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by redfox on 8-4-16.
 */
public class PieView extends AbstractView
{
    public ArrayList<Car> normalCars;
    public ArrayList<Car> passHolderCars;

    public PieView(Garage garage)
    {
        super(garage);
    }

    public void updateView()
    {
        normalCars = new ArrayList<Car>();
        passHolderCars = new ArrayList<Car>();
        checkAllLocations();
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);


        /*int[][][] cars =  new int[1][1][1];

        float count=0;

        for(int i=0;i<cars.length;i++)
        {
            for(int j=0;j<cars[i].length;j++)
            {
                for(int k=0;k<cars[j].length;k++)
                {
                    if (cars[i][j][k]==1) count++;

                }
            }
        }*/

        if(passHolderCars.size() > 0 || normalCars.size() > 0) {

            int passHolder = passHolderCars.size();
            int normal = normalCars.size();
            int totalPlaces = garage.getTotalAmountOfParkingPlaces();
            int totalAmount = passHolder + normal + totalPlaces;
            System.out.println(totalAmount + " : " + normal + " : " + passHolder);

            int passHolderAngle = (int) ((360 * passHolder) / totalAmount);
            int normalAngle = (int) ((360 * normal) / totalAmount);
            g.setColor(Color.WHITE);
            g.fillArc(10, 10, 180, 180, 0, 360);
            g.setColor(Color.RED);
            int angle = 0;
            g.fillArc(10, 10, 180, 180, 0, normalAngle);
            angle += normalAngle;
            g.setColor(Color.BLUE);
            g.fillArc(10, 10, 180, 180, angle, (angle + passHolderAngle));
            g.setColor(Color.BLACK);
            g.drawOval(10,10,180,180);
        }
    }

    public void checkAllLocations()
    {
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

}
