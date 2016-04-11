package CarParkingSimulator.View;

import java.awt.*;

/**
 * Created by redfox on 8-4-16.
 */
public class PieView
{
    public PieView()
    {
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);

        int[][][] cars =  new int[1][1][1];

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
        }

        int angle=(int)(360*(count/(cars.length*cars.length)));
        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, angle);
        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(10, 10, 180, 180, angle, 360-angle);
    }
}
