package CarParkingSimulator.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CarParkingSimulator.Controller.*;

public class SimulatorWindow extends JFrame
{
    //Code elements
    private GarageHelper garageHelper;
    private SimulatorEngine simulatorEngine;

    //GUI elements
    private JButton oneStepButton;
    private JButton hundredStepButton;
    private ParkingView parkingGarageView;
    private JLabel stepLabel;

    public SimulatorWindow(int numberOfFloors, int numberOfRows, int numberOfPlaces)
    {
        garageHelper = new GarageHelper(numberOfFloors, numberOfRows, numberOfPlaces);
        simulatorEngine = new SimulatorEngine(garageHelper);

        simulatorEngine.addListener(new SimulatorEngine.UpdateListener()
        {
            @Override
            public void DataUpdated(int currentStep)
            {
                updateView(currentStep);
            }
        });

        parkingGarageView = new ParkingView(garageHelper);
        stepLabel = new JLabel();

        oneStepButton = new JButton("One step");
        hundredStepButton = new JButton("Hundred step");

        oneStepButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                simulatorEngine.runSimulation(1);
            }
        });

        hundredStepButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                simulatorEngine.runSimulation(100);
            }
        });

        setupGUI();

        updateView(0);

        simulatorEngine.runSimulation(100);
    }

    private void setupGUI()
    {
        Container elementWindow = getContentPane();

        JPanel centreGridPanel = new JPanel();
        JPanel rightGridPanel = new JPanel();
        JPanel bottomGridPanel = new JPanel();

        elementWindow.setLayout(new BorderLayout());
        rightGridPanel.setLayout(new BoxLayout(rightGridPanel, BoxLayout.Y_AXIS));
        centreGridPanel.setLayout(new GridLayout(1, 1));
        bottomGridPanel.setLayout(new GridLayout(1, 1));

        centreGridPanel.add(parkingGarageView);
        rightGridPanel.add(oneStepButton);
        rightGridPanel.add(hundredStepButton);
        bottomGridPanel.add(stepLabel);

        elementWindow.add(rightGridPanel, BorderLayout.WEST);
        elementWindow.add(centreGridPanel, BorderLayout.CENTER);
        elementWindow.add(bottomGridPanel, BorderLayout.SOUTH);

        pack();

        setVisible(true);
    }

    public void updateView(int currentStep)
    {
        stepLabel.setText(Integer.toString(currentStep));

        parkingGarageView.updateView();
    }
}
