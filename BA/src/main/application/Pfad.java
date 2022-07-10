package application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pfad implements Comparable<Pfad>{


    private Punkt start;
    private Punkt dest;
    private ArrayList<LinienSegment> path;
    private double length;
    public Pfad(Punkt s, Punkt d, ArrayList<LinienSegment> p){


        this.start = s;
        this.dest = d;

        this.path = p;

    }
    public void setLength(double d){

        this.length = d;


    }

    public boolean containsPunkt(Punkt x){

        for(LinienSegment l : this.path){

            if (l.getEndpkt1().equals(x) || l.getEndpkt2().equals(x)){

                return true;
            }
        }

        return  false;
    }

    public boolean pathCuts(Pfad x){

        for(LinienSegment l : x.getPath()){

            if (this.containsPunkt(l.getEndpkt2()) || this.containsPunkt(l.getEndpkt1())){

                if(!(this.equals(x))) {
                    return true;
                }
            }

        }

        return false;
    }

    public double getLength(){

        return length;
    }
    public Punkt getStart(){

        return start;
    }

    public Punkt getDest(){

        return dest;
    }

    public ArrayList<LinienSegment> getPath(){

        return path;
    }

    @Override
    public boolean equals(Object l)
    {
        if (l == this)
            return true;

        if (l == null || !(l instanceof Pfad))
            return false;

        Pfad li = (Pfad) l;

        if (li.getStart().equals(this.getStart()) && li.getDest().equals(this.getDest())){return true;}
        if (li.getStart().equals(this.getDest()) && li.getDest().equals(this.getStart())){return true;}
        if (li.getPath().equals(this.getPath())) {return true;}

        return false;
    }

    @Override
    public int compareTo(Pfad o) {
        if (this.getLength() < o.getLength()) {return -1;}
        else if (this.getLength() > o.getLength()) {return 1;}
        return 0;
    }
}
