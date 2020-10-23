package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {12,5,3,6,15,13,16,19};
        int[] b = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        int[] c = {2, 4, 5, 7, 9, 8, 6, 11, 13, 12, 14, 10};
        for (int verdi : b) tre.leggInn(verdi);

        System.out.println(tre.toStringPostOrder());
        System.out.println(tre.fjern(2));
        System.out.println(tre.toStringPostOrder());
        System.out.println(tre.fjern(4));
        System.out.println(tre.toStringPostOrder());
        System.out.println(tre.fjern(6));

        EksamenSBinTre<Integer> tre2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] d = {1, 5, 3, 6, 8, 8, 7, 11, 10, 9, 6};
        for (int verdi : d) tre2.leggInn(verdi);
        tre2.fjern(6);
        System.out.println(tre2.toStringPostOrder());
        System.out.println(tre.antall());
    }
}
