package application;

import java.util.ArrayList;
import java.util.List;

public class Punkt implements Comparable<Punkt>{
    private double x;
    private double y;
    ArrayList <Punkt> reachable = new ArrayList<Punkt>();
    public boolean visited;
    private ArrayList<Punkt> nb = new ArrayList<Punkt>();
    int label;
    private double delay;
    private boolean matched;
    private Punkt partner;
    private Punkt vorgaenger;
    private double totalDist;
    ArrayList<Pfad> wege = new ArrayList<Pfad>();
    public Punkt(double x, double y){
        this.x=x;
        this.y=y;
    }


    public Punkt getPartner(){

        return partner;
    }

    public void setPartner(Punkt x){

        this.partner = x;
    }


    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getReachableSize() {return reachable.size();}


    public void setDelay(double d){

        this.delay = d;

    }
    public double getDelay() {
        return delay;
    }

    public void printPunkt() {
        System.out.println("("+this.getX() +", "+ this.getY()+")");
        System.out.println("------");
    }


    public ArrayList<Pfad> getWege(){

        return wege;
    }
    public Punkt getVorgaenger(){

        return vorgaenger;
    }

    public void setVorgaenger(Punkt x){

        this.vorgaenger = x;
    }

    public double getTotalDist(){

        return totalDist;
    }

    public void setTotalDist(double x){

        this.totalDist = x;
    }


    @Override
    public boolean equals(Object p)
    {
        if (p == this)
            return true;

        if (p == null || !(p instanceof Punkt))
            return false;

        Punkt op = (Punkt) p;

        if (op.getX() != this.getX())       return false;
        if (op.getY() != this.getY())     return false;

        return true;
    }


    public void addReachable(Punkt p) {

        this.reachable.add(p);

    }

    public ArrayList<Punkt> getReachable(){

        return reachable;
    }


    public boolean getvisited() {

        return visited;
    }

    public void setvisited() {

        visited = true;
    }

    public double getAngle(Punkt other){

        return (double) Math.toDegrees(Math.atan2(other.getX() - this.x, other.getY() - this.y));

    }


    public double getDistDelayed (Punkt other) {
        double dist = Math.sqrt(Math.pow(this.x - other.x,2) + 		//x
                Math.pow(this.y - other.y,2)) + Math.abs(this.getDelay() - other.getDelay());

        return dist;

    }



public double getDist(Punkt other){
    double dist = Math.sqrt(Math.pow(this.x - other.x,2) + 		//x
            Math.pow(this.y - other.y,2));

    return dist;

}

    public void setLabel(int i) {
        this.label = i;

    }
    public int getLabel() {
        return label;

    }


    /**
     @Override
    public int compareTo(Punkt o) {
        if(this.getReachableSize()<o.getReachableSize())
            return -1;
        else if(o.getReachableSize()<this.getReachableSize())
            return 1;
        return 0;
    }
     **/

    @Override
    public int compareTo(Punkt o) {
        if(this.getTotalDist()<o.getTotalDist())
            return -1;
        else if(o.getTotalDist()<this.getTotalDist())
            return 1;
        return 0;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
