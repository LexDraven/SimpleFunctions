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

        Integer [] massiv = getArrayInteger(10000);
        long begin = System.currentTimeMillis();
        BinaryTree tree = new BinaryTree(massiv);
        long end = System.currentTimeMillis() - begin;
        System.out.println("Create "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<10000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            tree.insertElement(int1);
        }
        end = System.currentTimeMillis() - begin;
        System.out.println("Add "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<1000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            tree.contains(int1);
        }
        end = System.currentTimeMillis() - begin;
        System.out.println("Contains "+end);
        System.out.println(tree.isEmpty());
        System.out.println(tree.getMaximum());
        System.out.println(tree.getMinimum());
        tree.clear();
        System.out.println(tree.isEmpty());


        Integer [] massiv1 = getArrayInteger(10000);
        begin = System.currentTimeMillis();
        ArrayList <Integer> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(massiv1));
        end = System.currentTimeMillis() - begin;
        System.out.println("Create al "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<10000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            arrayList.add(arrayList.size()-1000,int1);
        }
        end = System.currentTimeMillis() - begin;
        System.out.println("Add al "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<1000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            arrayList.contains(int1);
        }
        System.out.println("Contains al "+end);

        Integer [] massiv2 = getArrayInteger(10000);
        begin = System.currentTimeMillis();
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addAll(Arrays.asList(massiv1));
        end = System.currentTimeMillis() - begin;
        System.out.println("Create lilist "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<10000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            linkedList.add(linkedList.size()-1000,int1);
        }
        end = System.currentTimeMillis() - begin;
        System.out.println("Add lilist "+end);
        begin = System.currentTimeMillis();
        for (int i = 0; i<1000; i++) {
            Integer int1 = (int)(Math.random()*(1000));
            linkedList.contains(int1);
        }
        System.out.println("Contains lilist "+end);

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
