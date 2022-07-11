package application;

import java.lang.reflect.Array;
import java.util.*;

public class Dijkstra {


    public ArrayList<Punkt> base = new ArrayList<Punkt>();
    public ArrayList<Punkt> exec = new ArrayList<Punkt>();
    public ArrayList<Pfad> paths = new ArrayList<Pfad>();
    public ArrayList<Pfad> minPaths = new ArrayList<Pfad>();




    public void executeAll(ArrayList<Punkt> p, ArrayList<LinienSegment> triKanten, ArrayList<LinienSegment> polyKanten){

        for (Punkt pt : p){

            System.out.println(pt.getLabel());
        }
        base = p;

        Fill(base, triKanten, polyKanten);
        exec = base;
        allPaths(exec);
        uncross(minPaths);
        uncrossbrute(minPaths);
        printlen(minPaths);



    }

    public void printlen(ArrayList<Pfad> p){        //Gibt die Matchingkosten an


        double len = 0;

     for (Pfad pf : p){

         len += pf.getLength();

     }
     System.out.println("Gesamtkosten des Matchings: " + len);
    }

    public void allPaths(ArrayList<Punkt> ps) {                     //Berechnet alle Pfade und erzeugt das initiale Matching
        ArrayList<Punkt> ex = new ArrayList<Punkt>();
        for (Punkt j : ps) {

            Punkt t = j;
            ex.add(t);
        }

        for (Punkt i : ps) {

            init(ex, i);
            run(ex);
            for (Punkt j : ps) {

                if (i.equals(j) == false) {
                    getpath(i, j);
                }
            }
        }

        ArrayList<Pfad> finalPaths = new ArrayList<Pfad>();
        ArrayList<Pfad> finalPaths2 = new ArrayList<Pfad>();

        for (Punkt p : base){

            Collections.sort(p.wege);
            if (base.size() > 5) {
                for (int i = 0; i < 5; i++) {

                    System.out.println(p.getLabel() + " -> " + p.wege.get(i).getDest().getLabel() + ", Länge: " + p.wege.get(i).getLength());
                }
            }
        }

        double gesamtlen = 0;
       for (Punkt p : base) {


           if(p.isMatched() == false) {



               for (Pfad t : base.get(base.indexOf(p)).wege) {

                   if (base.get(base.indexOf(t.getDest())).isMatched() == false) {                                                      // PROBLEM: AUCH GEMATCHTE MÜSSEN GEPRÜFT WERDEN


                       finalPaths.add(t);

                       gesamtlen += t.getLength();
                       base.get(base.indexOf(p)).setMatched(true);
                       base.get(base.indexOf(p)).setPartner(t.getDest());
                       base.get(base.indexOf(t.getDest())).setMatched(true);
                       base.get(base.indexOf(t.getDest())).setPartner(p);




                       System.out.println("Match: " + t.getStart().getLabel() + " | " + t.getDest().getLabel() + " , Länge: " + t.getLength());
                       break;
                   }

               }
               }

       }

       for (Punkt p : base){

           Pfad t = p.wege.get(p.wege.indexOf(findPath(p,p.getPartner())));
           System.out.println("Prüfe Punkt " + p.getLabel() + " Prüfe Pfad -> " + t.getDest().getLabel());

           if (t != p.wege.get(0)) {


               System.out.println("Optimaler Partner für " + p.getLabel() + " ist " +p.wege.get(0).getDest().getLabel() + " , ist aber schon gematcht. Prüfe obe es besseren Partner als " + t.getDest().getLabel() + " gibt.");
               Punkt p_pt = t.getDest();
               Pfad rematch = null;

               for (int a = 0; a < p.wege.indexOf(t); a++) {


                   //p->a + t->a_partner < a->a_partner + p->t?


                   Pfad p_opt = findPath(p, base.get(base.indexOf(p.wege.get(a).getDest())));
                   Pfad p_curr = t;
                   Pfad p_opt_to_x = findPath(base.get(base.indexOf(p.wege.get(a).getDest())), base.get(base.indexOf(p.wege.get(a).getDest().getPartner())));
                   Pfad curr_to_x = findPath(base.get(base.indexOf(t.getDest())), base.get(base.indexOf(p.wege.get(a).getDest().getPartner())));

                   if (p_opt.getLength() + curr_to_x.getLength() < findPath(p, p_pt).getLength() + p_opt_to_x.getLength()) {

                       p_pt = base.get(base.indexOf(p.wege.get(a).getDest()));
                       rematch = curr_to_x;


                   }

               }                                // ist t_t-p + t-op_x < t_t-op + t-p_x ?

               if (rematch != null && (findPath(p, p_pt).getLength() + rematch.getLength()  - (findPath(rematch.getDest(), rematch.getDest().getPartner()).getLength() + t.getLength())) < 0) {


                   finalPaths.remove(findPath(rematch.getDest(), rematch.getDest().getPartner()));
                   finalPaths.remove(t);

                   Pfad w = findPath(p, p_pt);
                   finalPaths.add(w);
                   finalPaths.add(rematch);

                   double price = (w.getLength() + rematch.getLength()) - (findPath(rematch.getDest(), rematch.getDest().getPartner()).getLength() + t.getLength());

                   base.get(base.indexOf(p)).setMatched(true);
                   base.get(base.indexOf(p)).setPartner(base.get(base.indexOf(w.getDest())));

                   base.get(base.indexOf(p_pt)).setMatched(true);
                   base.get(base.indexOf(p_pt)).setPartner(base.get(base.indexOf(p)));

                   base.get(base.indexOf(rematch.getStart())).setMatched(true);
                   base.get(base.indexOf(rematch.getStart())).setPartner(base.get(base.indexOf(rematch.getDest())));

                   base.get(base.indexOf(rematch.getDest())).setMatched(true);
                   base.get(base.indexOf(rematch.getDest())).setPartner(base.get(base.indexOf(rematch.getStart())));

                   System.out.println("Statt " + p.getLabel() + " | " + rematch.getDest().getPartner().getLabel() + " wird zu " + w.getStart().getLabel() + " | " + w.getDest().getLabel() + " und " + rematch.getStart().getLabel() + " | " + rematch.getDest().getLabel() + " umgematcht. Kostenänderung: " + price);




               }

           }
       }



        //System.out.println("Gesamtgröße Matching: " + gesamtlen);
       minPaths=finalPaths;
    }

    public void uncross( ArrayList<Pfad> p){                    //entkreuzt suboptimale Pfade


        for( int i = 0; i<p.size(); i++){

            for(int j = 1; j<p.size(); j++){

                if (p.get(i).pathCuts(p.get(j))){

                    Pfad a = p.get(i);
                    Pfad b = p.get(j);

                    double curr_len = a.getLength() + b.getLength();

                    Pfad a_1_b1 = findPath(a.getStart(),b.getStart());
                    Pfad a_1_b2 = findPath(a.getStart(),b.getDest());
                    Pfad a_2_b1 = findPath(a.getDest(), b.getStart());
                    Pfad a_2_b2 = findPath(a.getDest(), b.getDest());

                    Pfad neu_a = a_1_b1;
                    Pfad neu_b = a_2_b2;

                    if(a_1_b1.getLength() + a_2_b2.getLength() < curr_len){

                        curr_len = a_1_b1.getLength() + a_2_b2.getLength();

                        neu_a = a_1_b1;
                        neu_b = a_2_b2;

                    }

                    if(a_1_b2.getLength() + a_2_b1.getLength() < curr_len){

                        curr_len = a_1_b2.getLength() + a_2_b1.getLength();

                        neu_a = a_1_b2;
                        neu_b = a_2_b1;

                    }

                    if (a.getLength() + b.getLength() > neu_a.getLength() + neu_b.getLength() ) {
                        p.remove(a);
                        p.remove(b);
                        p.add(neu_a);
                        p.add(neu_b);

                        System.out.println("Kreuzung behoben: " + a.getStart().getLabel() + ", " + a.getDest().getLabel() + " schnitt " + b.getStart().getLabel() + ", " + b.getDest().getLabel());
                        System.out.println("Kostenänderung: " + ((neu_a.getLength()+neu_b.getLength())-(a.getLength()+b.getLength())));
                    }
                }
            }
        }

    }


        public void uncrossbrute(ArrayList<Pfad> p){                    //entkreuzt direkt schneidende suboptimale Kanten

            ArrayList<Pfad> newp = new ArrayList<Pfad>();
            ArrayList<Pfad> remv = new ArrayList<Pfad>();

            for (int i = 0; i< p.size();i++){


                LinienSegment wl = new LinienSegment(p.get(i).getStart(), p.get(i).getDest());

                for (int j = 1; j<p.size(); j++){


                    LinienSegment wi = new LinienSegment(p.get(j).getStart(), p.get(j).getDest());

                    //System.out.println(" Prüfe " + wl.getEndpkt1().getDelay() + " , " + wl.getEndpkt2().getDelay() + " X " + wi.getEndpkt1().getDelay() + " , " + wi.getEndpkt2().getDelay());
                    if (wl.crossing(wi)) {




                        Pfad a = p.get(i);
                        Pfad b = p.get(j);

                        double curr_len = a.getLength() + b.getLength();

                        Pfad a_1_b1 = findPath(a.getStart(), b.getStart());
                        Pfad a_1_b2 = findPath(a.getStart(), b.getDest());
                        Pfad a_2_b1 = findPath(a.getDest(), b.getStart());
                        Pfad a_2_b2 = findPath(a.getDest(), b.getDest());

                        Pfad neu_a = a_1_b1;
                        Pfad neu_b = a_2_b2;

                        if (a_1_b1.getLength() + a_2_b2.getLength() < curr_len) {

                            curr_len = a_1_b1.getLength() + a_2_b2.getLength();

                            neu_a = a_1_b1;
                            neu_b = a_2_b2;

                        }

                        if (a_1_b2.getLength() + a_2_b1.getLength() < curr_len) {

                            curr_len = a_1_b2.getLength() + a_2_b1.getLength();

                            neu_a = a_1_b2;
                            neu_b = a_2_b1;

                        }

                        if (a.getLength() + b.getLength() > neu_a.getLength() + neu_b.getLength() ) {

                            System.out.println("Schnitt wird behoben: " + p.get(i).getStart().getLabel() + " ->" + p.get(i).getDest().getLabel() + " kreuzt " + p.get(j).getStart().getLabel() + " -> " + p.get(j).getDest().getLabel());
                            System.out.println("Kostenänderung: " + ((neu_a.getLength()+neu_b.getLength())-(a.getLength()+b.getLength())));
                            p.remove(a);
                            p.remove(b);
                            p.add(neu_a);
                            p.add(neu_b);
                        }
                    }
                }


            }

            //p.removeAll(remv);
           // p.addAll(newp);

        }





    public Pfad findPath(Punkt s, Punkt d){             //Gibt den kürzesten Pfad zwischen zwei Punkten an



        for(Pfad w : s.wege){

            if (w.getDest().equals(d)){

                return w;
            }

        }
        return null;
    }

    public void Fill(ArrayList<Punkt> p, ArrayList<LinienSegment> delK, ArrayList<LinienSegment> polyK) {       //initialisiert die Erreichbarkeiten der Punkte


        for (LinienSegment l : delK) {


            if (!(base.get(base.indexOf(l.getEndpkt1())).reachable.contains(base.get(base.indexOf(l.getEndpkt2()))))) {


                base.get(base.indexOf(l.getEndpkt1())).addReachable(l.getEndpkt2());
            }

            if (!(base.get(base.indexOf(l.getEndpkt2())).reachable.contains(base.get(base.indexOf(l.getEndpkt1()))))) {

                base.get(base.indexOf(l.getEndpkt2())).addReachable(l.getEndpkt1());

            }


        }

        System.out.println("ERREICHBARKEITEN DER PUNKTE____________");
        for (Punkt pt : p){


            int s = p.size();

            if ((p.indexOf(pt) > 0) && !(pt.getReachable().contains(p.get(p.indexOf(pt)-1)))){pt.addReachable(p.get(p.indexOf(pt)-1));}
            if (!(p.get(p.indexOf(pt)) ==p.get(s-1)) && !(pt.getReachable().contains(p.get(p.indexOf(pt)+1)))){pt.addReachable(p.get(p.indexOf(pt)+1));}

            for(Punkt a : pt.getReachable()){

                System.out.println(pt.getLabel() + " -> " + a.getLabel());
            }
        }

    }


    public void init(ArrayList<Punkt> p, Punkt s){              //initialisiert für dijkstra

        for (Punkt pt : p){

            pt.setVorgaenger(null);
            pt.setTotalDist(Double.MAX_VALUE);
            pt.visited = false;

        }

        p.get(p.indexOf(s)).setVorgaenger(p.get(p.indexOf(s)));
        p.get(p.indexOf(s)).setTotalDist(0);

    }


    public void run (ArrayList<Punkt> p){               //Dijkstra
        Collections.sort(p);
        int c = 0;

        while (c < p.size()) {
            for (int i = 0; i < p.size(); i++) {

                if (p.get(i).getvisited() == false) {

                    for (Punkt pn : p.get(i).getReachable()) {

                        if (p.get(i).getTotalDist() + p.get(i).getDistDelayed(pn) < p.get(p.indexOf(pn)).getTotalDist()) {


                            p.get(p.indexOf(pn)).setTotalDist(p.get(i).getTotalDist() + p.get(i).getDistDelayed(pn));
                            p.get(p.indexOf(pn)).setVorgaenger(p.get(i));
                        }
                    }
                    System.out.println("Punkt untersucht: " + p.get(i).getDelay() + " , VG: " + p.get(i).getVorgaenger().getDelay() );
                    p.get(i).setvisited();
                    break;
                }

            }

            Collections.sort(p);
            c += 1;
        }

        for (Punkt t : p){

            System.out.println(t.getDelay() + " , Vorgaenger: " + t.getVorgaenger().getDelay() + " , Gesamtdistanz: " + t.getTotalDist());
        }


    }

    public ArrayList<LinienSegment> getpath(Punkt s, Punkt d){                          //erzeugt die von dijkstra generierten Pfade


        System.out.println("__________________________________________________________________");
        System.out.println("Finde Pfad von " + s.getLabel() + " zu " + d.getLabel());

            Punkt dest = d;
                ArrayList<LinienSegment> path = new ArrayList<LinienSegment>();





            do {
                System.out.print(dest.getLabel() + " -> " + dest.getVorgaenger().getLabel() + ", ");


                LinienSegment lp = new LinienSegment(dest, dest.getVorgaenger());
                path.add(lp);
                dest = dest.getVorgaenger();


            }
            while (!dest.equals(s));


            Pfad pf = new Pfad(s,d,path);

            double len = 0;

            for(LinienSegment l : path){

                len += l.getLen();
            }
            pf.setLength(len);
            if (!(path.contains(pf))) {
                paths.add(pf);
            }
            base.get(base.indexOf(s)).wege.add(pf);
            return path;


    }






}
