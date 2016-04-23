import java.util.Arrays;

public class StartClass {

    public static void main(String[] args) {
        Integer [] mass = getArrayInteger(10000);
        Integer [] mass2 = mass.clone();
        Integer [] mass3 = mass.clone();
        Integer [] mass4 = mass.clone();
        Integer [] mass5 = mass.clone();
        Integer [] mass6 = mass.clone();

        int [] massiv = getArray(10000);
        BitwiseSorter bitwiseSorter = new BitwiseSorter(massiv);
        long begin = System.currentTimeMillis();
        bitwiseSorter.bitwiseSort();
        long result = System.currentTimeMillis() - begin;
        System.out.println("Bitwise sorting  - "+result + " millisec");
        bitwiseSorter.printArray();

        SortMachine sortMachine = new SortMachine(mass);
        sortMachine.setShowTimeResults(true);
        sortMachine.print();
        sortMachine.quickSort();
        sortMachine.print();
        sortMachine.setNewArray(mass3);
        sortMachine.methodShellSort();
        sortMachine.print();
        sortMachine.setNewArray(mass2);
        sortMachine.mergeSort();
        sortMachine.print();
        sortMachine.setNewArray(mass4);
        sortMachine.bubbleSort();
        sortMachine.print();
        sortMachine.setNewArray(mass5);
        sortMachine.selectedSort();
        sortMachine.print();
        sortMachine.setNewArray(mass6);
        sortMachine.insertionSort();
        sortMachine.print();
    }

    public static int [] getArray (int size){
        int [] mass = new int[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(30000));
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
