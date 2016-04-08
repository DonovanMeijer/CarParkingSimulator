package CarParkingSimulator.View;

import javax.swing.*;

import CarParkingSimulator.Model.Garage;

public abstract class AbstractView extends JPanel
{
    protected Garage garage;

    public AbstractView(Garage garage)
    {
        this.garage = garage;
    }

    public Garage getModel()
    {
        return garage;
    }

    public void updateView()
    {
        repaint();
    }
}

