package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        // Koden er kopiert fra kompendiet 5.2 3a
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot;
        Node<T> q = null;
        int cmp = 0;

        while (p != null){
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi,q);
        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        Node<T> q = null;

        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }

        if (p == null) return false;   // finner ikke verdi

        // Tilfelle 1 og 2
        if (p.venstre == null || p.høyre == null) {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            if(b != null) b.forelder = q;
        }

        // Tilfelle 3
        else {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null){
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
            s.forelder = p.forelder;
        }

        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        if (verdi == null) return 0;
        int antall = 0;

        while(antall(verdi) > 0){
            fjern(verdi);
            antall++;
        }
        return antall;
    }

    public int antall(T verdi) {
        Objects.requireNonNull(verdi,"Ulovlig med nullverdier");
        Node<T> p = rot;
        int antall = 0;

        while(p != null){
            int cmp = comp.compare(verdi,p.verdi);
            if(cmp == 0){
                p = p.høyre;
                antall++;
            } else if(cmp < 0){
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }
        return antall;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p,"Ulovlig med nullverdier");
        Node<T> q = p;
        if(q.venstre != null){
            while (q.venstre != null) {
                q = q.venstre;
                if(q.venstre == null && q.høyre != null){
                    q = q.høyre;
                }
            }
        } else {
            while(q.høyre != null){
                q = q.høyre;
                if(q.venstre != null){
                    q = førstePostorden(q);
                }
            }
        }

        return q;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Objects.requireNonNull(p, "Olovlig med nullverdier");
        if(p.forelder == null) return null;

        Node<T> f = p.forelder;
        Node<T> fh = f.høyre;

        if (fh == null || fh == p) return f;

        Node<T> curr = f.høyre;
        if(curr.venstre != null) {
            while (curr.venstre != null) curr = curr.venstre;
            return curr;
        } else if(curr.høyre != null) {
            return førstePostorden(curr.høyre);
        }

        return curr;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> r = førstePostorden(rot);
        while(r != null){
            oppgave.utførOppgave(r.verdi);
            r = nestePostorden(r);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p == null) return;
        postordenRecursive(p.venstre,oppgave);
        postordenRecursive(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T> list = new ArrayList<>();
        if(rot == null) return null;

        Queue<Node<T>> ko = new ArrayDeque<>();
        ko.add(rot);
        while(!ko.isEmpty()){
            Node<T> item = ko.poll();
            list.add(item.verdi);
            if(item.venstre != null) ko.add(item.venstre);
            if(item.høyre != null) ko.add(item.høyre);
        }
        return list;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);
        for (K datum : data) tre.leggInn(datum);
        return tre;
    }


} // ObligSBinTre
