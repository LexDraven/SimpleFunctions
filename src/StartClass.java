import Binary.BinaryTree;
import Binary.Branch;
import sun.misc.GC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class StartClass {

    public static void main(String[] args) {
//        Integer [] mass = getArrayInteger(20000);
//        Integer [] mass2 = mass.clone();
//        Integer [] mass3 = mass.clone();
//        Integer [] mass4 = mass.clone();
//        Integer [] mass5 = mass.clone();
//        Integer [] mass6 = mass.clone();
//
//        long [] massiv = getLongArray(100000);
//        BitwiseSorter bitwiseSorter = new BitwiseSorter(massiv);
//        long begin = System.currentTimeMillis();
//        bitwiseSorter.bitwiseSort();
//        long result = System.currentTimeMillis() - begin;
//        System.out.println("Bitwise sorting  - "+result + " millisec");
//
//        SortMachine sortMachine = new SortMachine(mass);
//        sortMachine.setShowSortingSpeed(true);
//        sortMachine.quickSort();
//        sortMachine.setNewArray(mass3);
//        sortMachine.mergeSort();
//        sortMachine.setNewArray(mass2);
//        sortMachine.methodShellSort();
//        sortMachine.setNewArray(mass4);
//        sortMachine.insertionSort();
//        sortMachine.setNewArray(mass5);
//        sortMachine.selectedSort();
//        sortMachine.setNewArray(mass6);
//        sortMachine.bubbleSort();
        Integer [] massiv = getArrayInteger(1000);
        BinaryTree tree = new BinaryTree(massiv);
        System.out.println("Size:" + tree.getSize());
        System.out.println("Min:" + tree.getMinimum());
        System.out.println("Max:" + tree.getMaximum());
        long begin = System.currentTimeMillis();
        for (int i=100; i<500; i++){
            Integer inter = i;
            tree.deleteElement(inter);
        }
        long end = System.currentTimeMillis()-begin;
        System.out.println("Time to delete "+end);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(massiv));
        begin = System.currentTimeMillis();
        for (int i=100; i<500; i++){
            Integer inter = i;
            arrayList.remove(inter);
        }
        end = System.currentTimeMillis()-begin;
        System.out.println("Time to delete al "+end);




    }

    public static int [] getArray (int size){
        int [] mass = new int[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(30000));
        }
        return mass;
    }

    public static long [] getLongArray (int size){
        long [] mass = new long[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (long) (Math.random()*(size*3));
        }
        return mass;
    }

    public static Integer [] getArrayInteger (int size){
        Integer [] mass = new Integer[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(size*3));
        }
        return mass;
    }
}
