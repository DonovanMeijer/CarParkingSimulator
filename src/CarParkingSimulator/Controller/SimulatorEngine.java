package CarParkingSimulator.Controller;

import CarParkingSimulator.Model.*;
import CarParkingSimulator.View.GraphView;

import java.util.*;

public class SimulatorEngine
{
    public enum SimulatorState
    {
        NotActive,
        Running,
        Paused,
        Finished
    }

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private GarageHelper garageHelper;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private Timer timer;
    private TimerTask simulationTask;

    private int currentStep = 0;

    private SimulatorState currentState;


    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    private Finance finance;

    public SimulatorEngine(GarageHelper garageHelper)
    {
        this.garageHelper = garageHelper;
        this.finance = new Finance();

        currentState = SimulatorState.NotActive;

        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        timer = new Timer();
        simulationTask = new SimulationTask();
    }

    public void runSimulation(int steps)
    {
        runSimulation(steps, 100);
    }

    public void runSimulation(int steps, int tickPause)
    {
        SimulationTask task = (SimulationTask)simulationTask;

        if (task.isRunning)
        {
            System.out.println("t");
            task.cancelTask();
        }

        timer = new Timer();

        task = new SimulationTask();

        task.currentStep = 0;
        task.steps = steps;

        timer.scheduleAtFixedRate(task, 0, tickPause);
    }

    public class SimulationTask extends TimerTask
    {
        public boolean isRunning = false;

        public int currentStep = 0;
        public int steps = 0;

        public void run()
        {
            isRunning = true;

            tick();

            currentStep += 1;

            if(currentStep >= steps)
            {
                cancelTask();
            }
        }

        public void cancelTask()
        {
            timer.cancel();

            isRunning = false;
        }
    }

    private void tick()
    {
        currentStep += 1;

        // Advance the time by one minute.
        minute++;

        while (minute > 59)
        {
            minute -= 60;
            hour++;
        }

        while (hour > 23)
        {
            hour -= 24;
            day++;
        }

        while (day > 6)
        {
            day -= 7;
        }

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5 ? weekDayArrivals : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;

        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++)
        {
            Car car = new AdHocCar();

            entranceCarQueue.addCar(car);
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++)
        {
            Car car = entranceCarQueue.removeCar();

            if (car == null)
            {
                break;
            }

            // Find a space for this car.
            Location freeLocation = garageHelper.getFirstFreeLocation();

            if (freeLocation != null)
            {
                garageHelper.setCarAt(freeLocation, car);

                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);

                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        garageHelper.tick();

        // Add leaving cars to the exit queue.
        while (true)
        {
            Car car = garageHelper.getFirstLeavingCar();

            if (car == null)
            {
                break;
            }

            car.setIsPaying(true);

            paymentCarQueue.addCar(car);
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++)
        {
            Car car = paymentCarQueue.removeCar();

            if (car == null)
            {
                break;
            }

            // TODO Handle payment.
            finance.pay(car.getTotalTime());
            System.out.println(finance.dailyRevenue());

            garageHelper.removeCarAt(car.getLocation());

            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++)
        {
            Car car = exitCarQueue.removeCar();

            if (car == null)
            {
                break;
            }
            // Bye!
        }

        // Update the car park view.
        for (UpdateListener listener : eventListeners)
        {
            listener.DataUpdated(currentStep);
        }
    }

    private List<UpdateListener> eventListeners = new ArrayList<UpdateListener>();

    public void addListener(UpdateListener listenerToAdd)
    {
        eventListeners.add(listenerToAdd);
    }

    public interface UpdateListener
    {
        void DataUpdated(int currentStep);
    }
}
