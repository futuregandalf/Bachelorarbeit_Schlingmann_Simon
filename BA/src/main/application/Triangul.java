package application;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Triangul {


    ArrayList<LinienSegment> DelaunayK = new ArrayList<LinienSegment>();



    public void executeForDij(ArrayList<Punkt> p, ArrayList<LinienSegment> polKanten) {

        createForLine(p,polKanten);
        CorrectionForLine(DelaunayK, polKanten, p);

    }

    public ArrayList<LinienSegment> finalEdges(){

        return DelaunayK;
    }


    private void createForLine(ArrayList<Punkt> pList, ArrayList<LinienSegment> pKanten){

        ArrayList<LinienSegment> toDel = new ArrayList<LinienSegment>();

        for (int i = 0; i < pList.size(); i++) {
            for (int j = 1; j < pList.size(); j++) {

                LinienSegment lin = new LinienSegment(pList.get(i), pList.get(j));
                DelaunayK.add(lin);

                for(LinienSegment pline : pKanten){


                    if (lin.crossing(pline)){

                        //if((lin.sameEdge(pline)) == false) {


                        LinienSegment dele = new LinienSegment(pList.get(i), pList.get(j));
                        toDel.add(dele);
                        System.out.println("Schnitt gefunden");

                        // }
                    }
                }
            }
        }
        DelaunayK.removeAll(toDel);
    }





    public void CorrectionForLine(ArrayList<LinienSegment> lines, ArrayList<LinienSegment> polKanten, ArrayList<Punkt> pl) {

        ArrayList<LinienSegment> delete = new ArrayList<LinienSegment>();
        double anglepos = 0;
        double angleneg = 0;
        double anglesumleft = 0;
        double anglesumright = 0;
        for (LinienSegment p : polKanten) {

            if (polKanten.indexOf(p) > 0) {

                if (p.angleBetween2Lines(polKanten.get(polKanten.indexOf(p) - 1)) >= 0) {

                    anglepos += 1;
                    anglesumright += p.angleBetween2Lines(polKanten.get(polKanten.indexOf(p) - 1));

                } else {

                    angleneg += 1;
                    anglesumleft += p.angleBetween2Lines(polKanten.get(polKanten.indexOf(p) - 1));
                }

            }

        }
        System.out.println("winkelsumme rechts: " + anglesumright + " , links: " + anglesumleft);
        for (LinienSegment l : lines) {

            int count = 0;
            double x = (l.getEndpkt1().getX() + l.getEndpkt2().getX()) / 2;
            double y = (l.getEndpkt1().getY() + l.getEndpkt2().getY()) / 2;

            Punkt m = new Punkt(x, y);
            int left = 0;
            int right = 0;


            Punkt d;
            if (Math.abs(anglesumright) > Math.abs(anglesumleft)) {
                d = new Punkt(10000, y);
            } else {
                d = new Punkt(-10000, y);
            }

            LinienSegment lt = new LinienSegment(m, d);
            for (LinienSegment lp : polKanten) {

                if (lt.crossing(lp)) {

                    if (!(l.sameEdge(lp))) {

                        count += 1;
                    }
                }
            }
            //if (((count & 1) == 0)){
            if ((count % 2) == 0) {

                if (count == 0) {

                    for (LinienSegment p : polKanten) {

                        if (l.sameEdge(p)) {

                            break;
                        }
                    }

                }
                delete.add(l);

            }
        }
        DelaunayK.removeAll(delete);

    }
}




