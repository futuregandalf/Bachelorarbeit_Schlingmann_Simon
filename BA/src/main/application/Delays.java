package application;

import java.util.ArrayList;

public class Delays {

    public Punkt median(ArrayList<Point> pts){      //berechnet den Polygonschwerpunkt

        double x = 0;
        double y = 0;

        for(Point p : pts){

            x+=p.getX();
            y+=p.getY();

        }

        double sz = pts.size();
        double px = 1/sz * x;
        double py = 1/sz * y;

        Punkt m = new Punkt(px, py);

        return m;
    }
}


