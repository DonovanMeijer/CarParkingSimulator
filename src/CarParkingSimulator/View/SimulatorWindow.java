package CarParkingSimulator.View;

import CarParkingSimulator.Controller.*;

import javax.swing.*;

public class SimulatorWindow extends JFrame
{
    private ParkingView parkingGarageView;

    private GarageHelper garageHelper;

    private SimulatorEngine simulatorEngine;

    public SimulatorWindow(int numberOfFloors, int numberOfRows, int numberOfPlaces)
    {
        garageHelper = new GarageHelper(numberOfFloors, numberOfRows, numberOfPlaces);

        parkingGarageView = new ParkingView(garageHelper);

        //Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        add(parkingGarageView);
        //contentPane.add(population, BorderLayout.SOUTH);


        simulatorEngine = new SimulatorEngine(garageHelper);

        simulatorEngine.addListener(new SimulatorEngine.UpdateListener()
        {
            @Override
            public void DataUpdated()
            {
                updateView();
            }
        });

        pack();

        setVisible(true);

        updateView();

        simulatorEngine.run(0);
    }

    public void updateView()
    {
        parkingGarageView.updateView();
    }
}
