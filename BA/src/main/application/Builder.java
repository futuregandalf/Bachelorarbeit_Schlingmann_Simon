package application;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;

public class Builder {

    Pane world;

    public ArrayList<Point> punkte;

    public Builder(Pane world, int punktmenge) {				//erzeugt die Punktmenge

        punkte = new ArrayList<Point>();

        for (int i = 0; i < punktmenge; i++ ) {

            punkte.add(new Point(world));
        }
        //draw();
    }

    public ArrayList<Point> getPunkte(){

        return punkte;

    }


    public void draw() {

        for (Point p : punkte) {

            p.draw();

        }

    }







}
