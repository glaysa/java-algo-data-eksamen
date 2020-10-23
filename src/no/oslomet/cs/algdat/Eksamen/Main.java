package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {12,5,3,6,15,13,16,19};
        int[] b = {2, 4, 5, 3, 1, 7, 9, 8, 6, 11, 13, 12, 14, 10};
        for (int verdi : b) tre.leggInn(verdi);

        String s = tre.toStringPostOrder();
        System.out.println(s);
    }
}
