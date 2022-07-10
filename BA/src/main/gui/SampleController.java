package gui;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;


import application.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
//import test.Punkt;


public class SampleController {



    @FXML
    Pane world;


    Builder graphs;
    Group lines = new Group();
    Group ccenters = new Group();
    //ArrayList<Line> edges = new ArrayList<Line>();
    Group delEdges = new Group();
    Group pathEdges = new Group();
    Group matchingEdges = new Group();
    Group points = new Group();
    Group mstEdges = new Group();
    Group globalMst = new Group();
    Group tspEdges = new Group();
    Group finalTsp = new Group();
    Group higherOrderEdges = new Group();
    Group polLines = new Group();
    Group labels = new Group();
    public int test = 10;
    boolean tourmod = false;
    boolean kantenmod = false;
    ArrayList<LinienSegment> finalTour = new ArrayList<LinienSegment>();
    ArrayList<double[][]> globalTspPerfect = new ArrayList<double[][]>();
    List<LinkedList<Punkt>> globalTspMst = new ArrayList<LinkedList<Punkt>>();
    ArrayList<LinienSegment> globalerMst = new ArrayList<LinienSegment>();
    List<List<Point>> globalCluster = new ArrayList<List<Point>>();
    ArrayList <Punkt> trav = new ArrayList<Punkt>();
    ArrayList<LinienSegment> polEdges = new ArrayList<LinienSegment>();

    double toursize;
    @FXML
    public void initialize() {																	//initialisiert Das Feld


        world.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        world.getChildren().clear();															//löscht vorher eventuell vorhandene Punktmenge
        //delEdges.setScaleX(1);
        //delEdges.setScaleX(1);
        graphs = new Builder(world, 0);


    }



    @FXML
    public void load() {

        graphs.punkte.clear();
        world.getChildren().remove(graphs.punkte);
        world.getChildren().clear();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open Resource File");

        boolean kette = false;
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            clear();
            Scanner scanner;
            try {

                scanner = new Scanner((selectedFile), "UTF-8");
                scanner.useLocale(Locale.GERMANY);
                double x, y;

                while (scanner.hasNext()) {

                    if (scanner.hasNext()) {

                        String s_x = scanner.next();
                        if (s_x.equals("k")){break;}
                        x = Double.parseDouble(s_x);
                        if (scanner.hasNext()) {

                            String s_y = scanner.next();
                            y = Double.parseDouble(s_y);


                        } else {

                            throw (new FileNotFoundException());
                        }
                    } else {

                        throw (new FileNotFoundException());

                    }
                    Point p = new Point(world);
                    p.setPos(x, y);        // stern_acht: 6 , sechsech: 60 , stern_sechs: 100
                    graphs.punkte.add(p);
                    graphs.draw();

                }

                scanner.close();

            } catch (FileNotFoundException e) {

                e.printStackTrace();
                System.out.println("Datei nicht gefunden oder Koordinaten ung�ltig.");
            }

            Scanner scanner2;
            try {

                scanner2 = new Scanner((selectedFile), "UTF-8");
                scanner2.useLocale(Locale.GERMANY);
                String a;

                while (scanner2.hasNext()) {

                    if (scanner2.hasNext()) {


                        a = scanner2.next();
                        if (a.equals("k")){

                            kette = true;

                        }

                    }
                    else {

                        throw (new FileNotFoundException());

                    }


                }

                scanner2.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for (Point x : graphs.punkte) {

                System.out.println(" Punkt " + graphs.punkte.indexOf(x) + " bei " + x.getX() + ", " + x.getY());
            }


            //ArrayList<Punkt> pnts = new ArrayList<Punkt>();
            Delays aux = new Delays();

            Punkt m = aux.median(graphs.punkte);                                                                               // Delay Vergabe
            Point mp = new Point(world);
            mp.setPos(m.getX(), m.getY());
            Circle circle = new Circle(mp.getX(), mp.getY(), 5);
            circle.setStroke(Color.RED);
            world.getChildren().add(circle);
            System.out.println("Mittelpunkt bei: " + mp.getX() + ", " + mp.getY());

            for (Point pt : graphs.punkte) {


                Punkt ps = new Punkt(pt.getX(), pt.getY());

                double dist = ps.getDist(m);                                                                                //Vergabe nach Schwerpunkt Abstand
                System.out.println("MP ABSTAND: " + dist);


               //double dist;                                                                                                   // in Reihenfolge
                //if (graphs.punkte.indexOf(pt) == 0) {
                //    dist = 0;
                //} else {
                //     dist = graphs.punkte.get(graphs.punkte.indexOf(pt) - 1).getDelay() + pt.getDistance(graphs.punkte.get(graphs.punkte.indexOf(pt) - 1));
                //}


                //double dist;
                //dist = pt.getX();                                                                   //Nach X Koordinate


                //double dist;
                //dist = pt.getY();                                                                  //Nach Y Koordinate


                int t = (int) Math.round(dist);
                pt.setDelay(t);
                String label = Integer.toString(t) + " , " + graphs.punkte.indexOf(pt);
                Text text = new Text(label);
                text.setBoundsType(TextBoundsType.VISUAL);
                text.setX(pt.getX() + 8);
                text.setY(pt.getY() - 8);
                world.getChildren().add(text);
                if (graphs.punkte.indexOf(pt) > 0) {

                    Point p1 = graphs.punkte.get(graphs.punkte.indexOf(pt) - 1);
                    Punkt pp1 = new Punkt(p1.getX(), p1.getY());
                    Punkt pp2 = new Punkt(pt.getX(), pt.getY());
                    Line l = new Line(pp1.getX(), pp1.getY(), pp2.getX(), pp2.getY());
                    LinienSegment edge = new LinienSegment(pp1, pp2);
                    polEdges.add(edge);
                    polLines.getChildren().add(l);
                    world.getChildren().add(l);


                }

                if (graphs.punkte.indexOf(pt) == graphs.punkte.size() - 1 || kette == false) {


                    Point p1 = graphs.punkte.get(0);
                    Punkt pp1 = new Punkt(pt.getX(), pt.getY());
                    Punkt pp2 = new Punkt(p1.getX(), p1.getY());
                    Line l = new Line(pp1.getX(), pp1.getY(), pp2.getX(), pp2.getY());
                    LinienSegment edge = new LinienSegment(pp1, pp2);


                    polEdges.add(edge);
                    polLines.getChildren().add(l);
                    world.getChildren().add(l);


                }
            }

            ArrayList<Punkt> pts = new ArrayList<Punkt>();
            for (Point p : graphs.punkte) {
                Punkt ps = new Punkt(p.getX(), p.getY());
                ps.setDelay(p.getDelay());
                ps.setLabel(graphs.punkte.indexOf(p));
                pts.add(ps);
            }

            for (Punkt x : pts) {

                System.out.println("Punkt " + pts.indexOf(x) + " bei " + x.getX() + ", " + x.getY() + ", Delay: " + x.getDelay());
            }


            Triangul del = new Triangul();
            ArrayList<LinienSegment> polygon = polEdges;
            del.executeForDij(pts, polEdges);
            ArrayList<LinienSegment> edges = del.finalEdges();


            for (LinienSegment l : polygon) {

                LinienSegment lp = new LinienSegment(pts.get(pts.indexOf(l.getEndpkt1())), pts.get(pts.indexOf(l.getEndpkt2())));

                edges.add(lp);

            }


            for (int i = 0; i < edges.size(); i++) {

                Punkt x = edges.get(i).getEndpkt1();
                Punkt y = edges.get(i).getEndpkt2();

                Line l = new Line(x.getX(), x.getY(), y.getX(), y.getY());
                l.setStroke(Color.BLUE);
                if (!(delEdges.getChildren().contains(l))) {
                    delEdges.getChildren().add(l);

                }
            }


            world.getChildren().addAll(delEdges);
            Dijkstra dj = new Dijkstra();
            ArrayList<Punkt> trs = new ArrayList<Punkt>();

            for (Punkt a : pts) {

                Punkt z = a;
                z.setDelay(a.getDelay());
                trs.add(z);
            }

            if (pts.size() % 2 == 0) {
                dj.executeAll(trs, edges, polygon);

                for (Pfad pf : dj.minPaths) {

                    Line l = new Line(pf.getStart().getX(), pf.getStart().getY(), pf.getDest().getX(), pf.getDest().getY());


                    l.setStroke(Color.GREEN);
                    l.setStrokeWidth(6.0);

                    if (!(pathEdges.getChildren().contains(l))) {
                        pathEdges.getChildren().add(l);

                    }
                }


                world.getChildren().addAll(pathEdges);

            }


        }
    }

    @FXML
    public void handleOnMouseClicked(MouseEvent event) {

        //einzelnen Punkt manuell einfügen am Mauscursor
        delEdges.getChildren().clear();
        world.getChildren().remove(delEdges);
        delEdges.getChildren().clear();

        world.getChildren().remove(labels);
        //labels.getChildren().clear();

        pathEdges.getChildren().clear();
        world.getChildren().remove(pathEdges);
        pathEdges.getChildren().clear();


        if (!event.isControlDown() && (!event.isAltDown())) {

            //world.getChildren().remove(labels);
            //labels.getChildren().clear();
            delEdges.getChildren().clear();
            world.getChildren().remove(delEdges);
            delEdges.getChildren().clear();

            Point p = new Point(world);
            p.setPos(event.getX(), event.getY());
            graphs.punkte.add(p);
            graphs.draw();
            System.out.println(p.getPos().getX() + ", " + p.getPos().getY());
            if (graphs.punkte.size() >= 2) {

                Point p1 = graphs.punkte.get(graphs.punkte.indexOf(p) - 1);
                Punkt pp1 = new Punkt(p1.getX(), p1.getY());
                Punkt pp2 = new Punkt(p.getX(), p.getY());
                Line l = new Line(pp1.getX(), pp1.getY(), pp2.getX(), pp2.getY());
                LinienSegment edge = new LinienSegment(pp1, pp2);
                polEdges.add(edge);
                polLines.getChildren().add(l);
                world.getChildren().add(l);


            }


/**
            Delays aux = new Delays();

            Punkt m = aux.median(graphs.punkte);                                                                               // Delay Vergabe
            Point mp = new Point(world);
            mp.setPos(m.getX(), m.getY());
            Circle circle = new Circle(mp.getX(), mp.getY(), 5);
            //circle.setStroke(Color.RED);
            //world.getChildren().add(circle);
            System.out.println("Mittelpunkt bei: " + mp.getX() + ", " + mp.getY());

            for (Point pr : graphs.punkte){

                //double del = pr.getDistance(mp);
                double del;
                if (graphs.punkte.size() == 1){ del = 0;}
                else {del = graphs.punkte.get(graphs.punkte.indexOf(p)-1).getDelay() + p.getDistance(graphs.punkte.get(graphs.punkte.indexOf(p)-1));}

                int x = (int) del;
                pr.setDelay(x);
                String label = Integer.toString(x)+" , "+graphs.punkte.indexOf(pr)  ;
                Text text = new Text(label);
                text.setBoundsType(TextBoundsType.VISUAL);
                text.setX(pr.getX() + 8);
                text.setY(pr.getY() - 8);
                labels.getChildren().add(text);

            }
            world.getChildren().addAll(labels);
 **/


            double d = 0;
            if (graphs.punkte.size() == 1){ d = 0;}
            else {d = graphs.punkte.get(graphs.punkte.indexOf(p)-1).getDelay() + p.getDistance(graphs.punkte.get(graphs.punkte.indexOf(p)-1));}
            //else {d = graphs.punkte.get(graphs.punkte.indexOf(p)-1).getDelay() + 1 ;}

            int x = (int) d;
            p.setDelay(x);
            String label = Integer.toString(x)+" , "+(graphs.punkte.size())  ;
            Text text = new Text(label);
            text.setBoundsType(TextBoundsType.VISUAL);
            text.setX(p.getX() + 8);
            text.setY(p.getY() - 8);
            labels.getChildren().add(text);
            world.getChildren().addAll(labels);


        }

        else if (event.isControlDown() == true) {
                //world.getChildren().remove(labels);
                //labels.getChildren().clear();
                if (graphs.punkte.size() > 2) {
                    Point p1 = graphs.punkte.get(graphs.punkte.size()-1);
                    Point p0 = graphs.punkte.get(0);
                    Punkt pp1 = new Punkt(p1.getX(), p1.getY());
                    Punkt pp2 = new Punkt(p0.getX(), p0.getY());
                    Line l = new Line(pp1.getX(), pp1.getY(), pp2.getX(), pp2.getY());
                    LinienSegment edge = new LinienSegment(pp1, pp2);
                    if (!(polEdges.contains(edge))){

                        polEdges.add(edge);
                        polLines.getChildren().add(l);
                        world.getChildren().add(l);
                        world.getChildren().addAll(labels);
                    }



                }

        }



        ArrayList<Punkt> pts = new ArrayList<Punkt>();
        for (int i = 0; i < graphs.punkte.size(); i++) {
            Punkt ps = new Punkt(graphs.punkte.get(i).getX(), graphs.punkte.get(i).getY());
            ps.setDelay(graphs.punkte.get(i).getDelay());
            ps.setLabel(i+1);
            pts.add(ps);
        }


        Triangul del = new Triangul();
        ArrayList<LinienSegment> polygon = polEdges;
        del.executeForDij(pts, polygon);
        ArrayList<LinienSegment> edges = del.finalEdges();




        for (LinienSegment l : polygon){

           LinienSegment lp = new LinienSegment(pts.get(pts.indexOf(l.getEndpkt1())),pts.get(pts.indexOf(l.getEndpkt2())));

                edges.add(lp);

        }


        for (int i = 0; i < edges.size(); i++) {

            Punkt x = edges.get(i).getEndpkt1();
            Punkt y = edges.get(i).getEndpkt2();

            Line l = new Line(x.getX(), x.getY(), y.getX(), y.getY());
            l.setStroke(Color.BLUE);
            if(!(delEdges.getChildren().contains(l))) {
                delEdges.getChildren().add(l);

            }
        }


        world.getChildren().addAll(delEdges);
        Dijkstra dj = new Dijkstra();
        ArrayList<Punkt> trs = new ArrayList<Punkt>();

        for(Punkt a : pts){

            Punkt z = a;
            trs.add(z);
        }

        if (pts.size() % 2 == 0) {
            dj.executeAll(trs, edges, polygon);

            for (Pfad pf : dj.minPaths) {

                Line l = new Line(pf.getStart().getX(), pf.getStart().getY(), pf.getDest().getX(), pf.getDest().getY());


                l.setStroke(Color.GREEN);
                l.setStrokeWidth(6.0);

                if (!(pathEdges.getChildren().contains(l))) {
                    pathEdges.getChildren().add(l);

                }
            }


            world.getChildren().addAll(pathEdges);

        }

    }


    @FXML
    public void clear() {	//Alle Punkte löschen, liste leeren
        world.getChildren().remove(higherOrderEdges);
        higherOrderEdges.getChildren().clear();
        world.getChildren().clear();
        lines.getChildren().clear();
        ccenters.getChildren().clear();
        graphs.punkte.clear();
        delEdges.getChildren().clear();
        globalMst.getChildren().clear();
        finalTour.clear();
        lines.getChildren().clear();
        delEdges.getChildren().clear();
        //edges.clear();
        globalerMst.clear();
        trav.clear();
        finalTour.clear();
        globalTspPerfect.clear();
        globalTspMst.clear();
        globalerMst.clear();
        globalCluster.clear();
        trav.clear();
        polEdges.clear();
        world.getChildren().remove(higherOrderEdges);
        higherOrderEdges.getChildren().clear();
        lines.getChildren().clear();
        world.getChildren().remove(lines);
        ccenters.getChildren().clear();
        world.getChildren().remove(ccenters);
        delEdges.getChildren().clear();
        world.getChildren().remove(delEdges);
        globalMst.getChildren().clear();
        world.getChildren().remove(globalMst);
        finalTour.clear();
        finalTsp.getChildren().clear();
        world.getChildren().remove(finalTsp);
        world.getChildren().remove(matchingEdges);
        matchingEdges.getChildren().clear();


    }








}







