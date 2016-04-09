package CarParkingSimulator.Controller;

import CarParkingSimulator.Model.*;

import java.util.*;

public class SimulatorEngine
{
    private Garage garage;

    private Timer timer;
    private SimulationTimerTask simulationTimerTask;

    private SimulatorState currentState;

    private int currentStep = 0;

    public enum SimulatorState
    {
        NotActive,
        Running,
        Paused,
        Finished
    }

    private class SimulationTimerTask extends TimerTask
    {
        private int currentStep = 0;
        private int steps = 0;

        public void setAmountOfSteps(int amountOfSteps)
        {
            steps = amountOfSteps;
        }

        public void run()
        {
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
        }
    }

    private Random randomNumberGenerator;

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    public SimulatorEngine(Garage garage)
    {
        this.garage = garage;

        currentState = SimulatorState.NotActive;

        randomNumberGenerator = new Random();

        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        timer = new Timer();
        simulationTimerTask = new SimulationTimerTask();
    }

    public void runSimulation(int steps)
    {
        runSimulation(steps, 1);
    }

    public void runSimulation(int steps, int tickPause)
    {
        if(simulationTimerTask != null)
        {
            simulationTimerTask.cancelTask();
        }

        timer = new Timer();
        simulationTimerTask = new SimulationTimerTask();

        simulationTimerTask.setAmountOfSteps(steps);

        timer.scheduleAtFixedRate(simulationTimerTask, 0, tickPause);
    }

    private void incrementTime()
    {
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
    }

    private void generateVisitors()
    {
        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5 ? weekDayArrivals : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + randomNumberGenerator.nextGaussian() * standardDeviation;

        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++)
        {
            Car car;

            if((((int)(Math.random() * 11)) % 2) == 0)
            {
                car = new NormalCar();
            }
            else
            {
                car = new PassHolderCar();
            }

            entranceCarQueue.addCar(car);
        }
    }

    private void advanceEntranceQueue()
    {

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++)
        {
            Car car = entranceCarQueue.removeCar();

            if (car == null)
            {
                break;
            }

            // Find a space for this car.
            Location freeLocation = garage.getFirstFreeLocation();

            if (freeLocation != null)
            {
                garage.setCarAt(freeLocation, car);

                int stayMinutes = (int) (15 + randomNumberGenerator.nextFloat() * 10 * 60);

                car.setMinutesLeft(stayMinutes);
            }
        }
    }

    private void advancePaymentQueue()
    {
        // Add leaving cars to the exit queue.
        while (true)
        {
            Car car = garage.getFirstLeavingCar();

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

            if(car instanceof NormalCar)
            {
                garage.getFinances().pay(currentStep - car.getTimeEntered(), currentStep);

            }
            else if(car instanceof PassHolderCar)
            {
                garage.getFinances().payPassHolder(currentStep - car.getTimeEntered(), currentStep);
            }

            garage.removeCarAt(car.getLocation());

            exitCarQueue.addCar(car);
        }
    }

    private void letCarsExit()
    {
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
    }

    private void tick()
    {
        currentStep += 1;

        incrementTime();

        generateVisitors();

        advanceEntranceQueue();

        // Perform car park tick.
        garage.tick();

        advancePaymentQueue();

        letCarsExit();

        //Trigger all associated event listeners.
        for (UpdateListener listener : eventListeners)
        {
            listener.DataUpdated(currentStep);
        }
    }

    //region Simulator events
    private List<UpdateListener> eventListeners = new ArrayList<UpdateListener>();

    public void addListener(UpdateListener listenerToAdd)
    {
        eventListeners.add(listenerToAdd);
    }

    public interface UpdateListener
    {
        void DataUpdated(int currentStep);
    }
    //endregion
}
