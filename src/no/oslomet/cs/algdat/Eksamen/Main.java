package no.oslomet.cs.algdat.Eksamen;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {12,5,3,6,15,13,16,19};
        int[] b = {1, 5, 3, 6, 8, 8, 7, 11, 10, 9, 6};
        for (int verdi : b) tre.leggInn(verdi);

        System.out.println(tre.toStringPostOrder());
        System.out.println(tre.fjern(6));
        System.out.println(tre.toStringPostOrder());
    }
}
