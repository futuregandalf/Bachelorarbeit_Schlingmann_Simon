package application;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Comparator;

public class LinienSegment implements Comparable<LinienSegment>{
    Punkt endpkt1;
    Punkt endpkt2;
    public double gewicht;




    public LinienSegment(Punkt x, Punkt y) {
        this.endpkt1 = x;
        this.endpkt2 = y;
        this.setGewicht(Laenge(x,y));
    }

    @Override
    public boolean equals(Object l)             //prüft auf gleichheit
    {
        if (l == this)
            return true;

        if (l == null || !(l instanceof LinienSegment))
            return false;

        LinienSegment li = (LinienSegment) l;

        if (li.getEndpkt1().equals(this.getEndpkt1()) && li.getEndpkt2().equals(this.getEndpkt2())){return true;}
        if (li.getEndpkt1().equals(this.getEndpkt2()) && li.getEndpkt2().equals(this.getEndpkt1())){return true;}

        return false;
    }

    public Punkt Mittelpunkt() {                    //Mittelpunkt eines Liniensegments
        double a = (this.endpkt1.getX() + this.endpkt2.getX()) *0.5;
        double b = (this.endpkt1.getY() + this.endpkt2.getY()) *0.5;
        Punkt m = new Punkt(a,b);
        return m;
    }

    public boolean crossing(LinienSegment l){                       //prüft zwei Liniensegmente auf Schnitt

        Line2D l1 = new Line2D.Double((double)this.getEndpkt1().getX(),(double)this.getEndpkt1().getY(),(double)this.getEndpkt2().getX(),(double)this.getEndpkt2().getY());
        Line2D l2 = new Line2D.Double((double)l.getEndpkt1().getX(),(double)l.getEndpkt1().getY(),(double)l.getEndpkt2().getX(),(double)l.getEndpkt2().getY());

        if (l1.intersectsLine(l2)){

            if ((l1.getP1().getX() != l2.getP1().getX() && l1.getP1().getY() != l2.getP1().getY()) && (l1.getP1().getX() != l2.getP2().getX() && l1.getP1().getY() != l2.getP2().getY()) && (l1.getP2().getX() != l2.getP1().getX() && l1.getP2().getY() != l2.getP1().getY()) && (l1.getP2().getX() != l2.getP2().getX() && l1.getP2().getY() != l2.getP2().getY())) {


                return true;


            }
        }

        return false;

    }

    public double angleBetween2Lines(LinienSegment other)               //winkel zwier Liniensegmente
    {
        double angle1 = Math.toDegrees(Math.atan2(this.getEndpkt1().getY() - this.getEndpkt2().getY(),
                this.getEndpkt1().getX() - this.getEndpkt2().getX()));


        double angle2 = Math.toDegrees(Math.atan2(other.getEndpkt1().getY() - other.getEndpkt2().getY(),
                other.getEndpkt1().getX() - other.getEndpkt2().getX()));

        double d = angle1-angle2;
        if (d<0){

            d+=360;
        }

        if(d > 180){
            d -= 360;
        }
        return d;


    }


    public double Laenge(Punkt a , Punkt b) {

        double temp1= Math.pow((a.getX() - b.getX()), 2);
        double temp2= Math.pow((a.getY() - b.getY()), 2);

        double laenge = Math.sqrt(temp1 + temp2);
        return laenge;
    }

    public Punkt getEndpkt1(){
        return endpkt1;
    }

    public Punkt getEndpkt2(){
        return endpkt2;
    }

    public boolean sameEdge(LinienSegment l) {
        if ((this.endpkt1.getX() == l.endpkt1.getX()) &&
                (this.endpkt2.getX() == l.endpkt2.getX()) ) {
            if ((this.endpkt1.getY() == l.endpkt1.getY()) &&
                    (this.endpkt2.getY() == l.endpkt2.getY())) {
                return true;
            }
        }
        else if((this.endpkt1.getX() == l.endpkt2.getX()) &&
                (this.endpkt2.getX() == l.endpkt1.getX()) ) {
            if ((this.endpkt1.getY() == l.endpkt2.getY()) &&
                    (this.endpkt2.getY() == l.endpkt1.getY())) {
                return true;
            }
        }
        else return false;


        return null != null;
    }




    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    @Override
    public int compareTo(LinienSegment c) {
        if(this.getGewicht()<c.getGewicht())
            return -1;
        else if(c.getGewicht()<this.getGewicht())
            return 1;
        return 0;
    }


    public double getLen(){

        return this.getEndpkt1().getDistDelayed(this.getEndpkt2());

    }


}
