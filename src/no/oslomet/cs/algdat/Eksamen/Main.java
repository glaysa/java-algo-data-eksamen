package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {10, 6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        int[] b = {10,10,2,10,10};
        for (int verdi : a) tre.leggInn(verdi);

        Oppgave<Integer> print = new Oppgave<Integer>() {
            @Override
            public void utførOppgave(Integer integer) {
                System.out.print(integer + " ");
            }
        };

        System.out.println("Postorden(oppgave)");
        tre.postorden(print);

        System.out.println();

        System.out.println("Postorden(rot,oppgave)");
        tre.postordenRecursive(print);
    }
}
