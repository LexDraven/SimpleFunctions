
public class StartClass {

    public static void main(String[] args) {
        Integer [] mass = new Integer[10000];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*10000);
        }
        SortMachine sortMachine = new SortMachine(mass);
        sortMachine.setShowTimeResults(true);
        sortMachine.print();
        sortMachine.mergeSort();
        sortMachine.print();
        sortMachine.setNewArray(mass);
        sortMachine.insertionSort();
        sortMachine.print();
        sortMachine.setNewArray(mass);
        sortMachine.selectedSort();
        sortMachine.print();
        sortMachine.setNewArray(mass);
        sortMachine.bubbleSort();
        sortMachine.print();


    }
}
