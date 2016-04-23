
public class StartClass {

    public static void main(String[] args) {
        Integer [] mass = getArray(20000);
        SortMachine sortMachine = new SortMachine(mass);
        sortMachine.setShowTimeResults(true);
        sortMachine.print();
        sortMachine.methodShellSort();
        sortMachine.print();
        sortMachine.setNewArray(getArray(20000));
        sortMachine.print();
        sortMachine.mergeSort();
        sortMachine.print();
        sortMachine.setNewArray(getArray(20000));
        sortMachine.print();
        sortMachine.bubbleSort();
        sortMachine.print();
        sortMachine.setNewArray(getArray(20000));
        sortMachine.print();
        sortMachine.selectedSort();
        sortMachine.print();
        sortMachine.setNewArray(getArray(20000));
        sortMachine.print();
        sortMachine.insertionSort();
        sortMachine.print();
    }

    public static Integer [] getArray (int size){
        Integer [] mass = new Integer[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(size*3));
        }
        return mass;
    }
}
