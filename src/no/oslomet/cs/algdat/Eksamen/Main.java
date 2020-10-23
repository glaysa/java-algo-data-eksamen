package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {12,5,3,6,15,13,16,19};
        int[] b = {10,10,2,10,10};
        for (int verdi : a) tre.leggInn(verdi);

        System.out.println(tre.fjern(16));
        System.out.println(tre.inneholder(16));
    }
}
