import Binary.BinaryTree;
import java.util.List;

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
        Integer [] massiv = getArrayInteger(10);
        BinaryTree tree = new BinaryTree(massiv);
        System.out.println("Size:" + tree.getSize());
        System.out.println("Min:" + tree.getMinimum());
        System.out.println("Max:" + tree.getMaximum());
        List<Integer> listok = tree.getAsList();
        for (Integer i:listok) {
            System.out.println(i);
        }
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
