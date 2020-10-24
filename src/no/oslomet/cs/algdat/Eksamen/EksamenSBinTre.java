package no.oslomet.cs.algdat.Eksamen;

import java.util.*;

public class EksamenSBinTre<T> {

    // Node klasse
    private static final class Node<T> {
        private T verdi;
        private Node<T> venstre, høyre;
        private Node<T> forelder;

        // Node konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder) {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    }

    private Node<T> rot;
    private int antall;
    private int endringer;
    private final Comparator<? super T> comp;

    // Konstruktør
    public EksamenSBinTre(Comparator<? super T> c) {
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

        Node<T> p = førstePostorden(rot);
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

        p = new Node<>(verdi,q); // Satt på riktig foreldre referanse
        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        endringer++;
        return true;
    }

    public boolean fjern(T verdi) {
        // Koden er kopiert fra kompendiet 5.2 8d
        if (verdi == null) return false;

        Node<T> p = rot;
        Node<T> q = rot.forelder;

        // Finner verdien som skal fjernes
        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) { q = p; p = p.venstre; }
            else if (cmp > 0) { q = p; p = p.høyre; }
            else break;
        }

        if (p == null) return false;

        // Tilfelle 1 og 2
        if (p.venstre == null || p.høyre == null) {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            if(b != null) b.forelder = q; // Satt på riktig forelde referanse
        }

        // Tilfelle 3
        else {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null){
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;
            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {
        if (verdi == null) return 0;
        int teller = 0;

        // Så lenge antall forekomster av gitt verdi er større enn null
        // fjern verdien
        while(antall(verdi) > 0){
            fjern(verdi);
            teller++;
            endringer++;
        }

        return teller;
    }

    public int antall(T verdi) {
        Objects.requireNonNull(verdi,"Ulovlig med nullverdier");
        Node<T> p = rot;
        int antall = 0;

        while(p != null){
            int cmp = comp.compare(verdi,p.verdi);

            // Hvis verdien har en duplikat gå ned til høyre barn og økt antall
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
        if(tom()) return;
        Node<T> r = førstePostorden(rot);

        // Setter alle nodens verdi til null
        while(r != null){
            r.verdi = null;
            antall--;
            endringer++;
            r = nestePostorden(r);
        }
        rot = null;
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        if (p.venstre != null){
            return førstePostorden(p.venstre);
        } else if (p.høyre != null){
            return førstePostorden(p.høyre);
        } else {
            return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        // Hvis treet har 0 eller 1 node
        if (p.forelder == null) return null;

        // Hvis p er høyre barn
        if (p.forelder.høyre == p){
            return p.forelder;

        // Hvis p er venstre barn og sin foreldre har ikke en høyre barn
        } else if (p.forelder.høyre == null){
            return p.forelder;

        // Hvis p er har et subtre
        } else {
            return førstePostorden(p.forelder.høyre);
        }
    }

    public void postorden(Oppgave<? super T> oppgave) {
        // Finn rot noden til postorden
        Node<T> r = førstePostorden(rot);

        // Traverserer ved hjelp av en while-loop og nestePostorden metoden
        // samtidig printer ut nodenes verdi
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

        Queue<Node<T>> kø = new ArrayDeque<>();
        kø.add(rot);
        while(!kø.isEmpty()){
            // Fjern noden men også lagre den i en variabel
            Node<T> node = kø.poll();

            // Legg fjernet nodens verdi i arraylisten
            list.add(node.verdi);

            // Hvis nodens.venstre er ikke null, legg den i køen
            if(node.venstre != null) kø.add(node.venstre);

            // Hvis nodens.høyre er ikke null, legg den i køen
            if(node.høyre != null) kø.add(node.høyre);
        }
        return list;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);
        for (K datum : data) tre.leggInn(datum);
        return tre;
    }


} // ObligSBinTre
