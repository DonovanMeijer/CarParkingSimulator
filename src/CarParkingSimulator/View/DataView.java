package CarParkingSimulator.View;

import CarParkingSimulator.Model.DataSources.DataSource;
import CarParkingSimulator.Model.Garage;

/**
 * Abstract class describing a basic form for a data view.
 * @author Donovan Meijer
 * @version 1.0
 */
public abstract class DataView extends AbstractView
{
    protected DataSource dataSource;

    /**
     * @param garage The garage model.
     * @param dataSource The data source.
     */
    public DataView(Garage garage, DataSource dataSource)
    {
        super(garage);

        this.dataSource = dataSource;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public abstract void updateView();
}
