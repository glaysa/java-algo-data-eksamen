package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args){
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {1, 5, 3, 6, 8, 8, 7, 11, 10, 9, 6};
        for (int verdi : a) tre.leggInn(verdi);
        tre.fjern(6);
        System.out.println("Fjernet 6 " + tre.toStringPostOrder());
        tre.fjern(8);
        System.out.println("Fjernet 8 " + tre.toStringPostOrder());

    }
}
