import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//Поразрядная сортировка, медленнее быстрых сортировок, но быстрее чем тривиальные сортировки
//работает только для положительных цифровых массивов int и long
public class BitwiseSorter {
    private ArrayList<String> list;
    private Queue<String>[] digits = new Queue[10];
    private long [] mainArray;
    private int lengthBits = 0;

    public BitwiseSorter(int[] mainArray) {
        convertIntToLong(mainArray);
        init();
    }

    public BitwiseSorter(long[] mainArray) {
        this.mainArray = mainArray;
        init();
    }

    private  void  init() {
        list = new ArrayList<>(mainArray.length);
        toStringList();
        for (int i=0; i<10; i++){
            digits[i] = new LinkedList();
        }
    }

    public void setNewArray(int[] newArray) {
        convertIntToLong(newArray);
        list = new ArrayList<>(mainArray.length);
        toStringList();
    }

    private void toStringList(){
        for (long arrayElement : mainArray) {
            if (Long.toString(arrayElement).length() > lengthBits) {
                lengthBits = Long.toString(arrayElement).length();
            }
        }
        for (long aMainArray : mainArray) {
            String temp = Long.toString(aMainArray);
            if (temp.length() < lengthBits) {
                while (temp.length() < lengthBits) {
                    temp = "0" + temp;
                }
            }
            list.add(temp);
        }
    }

    public int[] getIntArray(){
        backToLong();
        return convertLongToInt();
    }

    public long[] getLongArray(){
        backToLong();
        return mainArray;
    }

    public long [] getSortedLongArray() {
        bitwiseSort();
        return getLongArray();
    }

    private void backToLong() {
        for (int i=0; i<list.size(); i++) {
            mainArray[i] = Long.parseLong(list.get(i));
        }
    }

    public void bitwiseSort(){
        for (int position = lengthBits -1; position>-1; position--) {
            for (String st: list){
                String oneDigit = st.substring(position,position+1);
                int index = Integer.parseInt(oneDigit);
                digits[index].offer(st);
            }
            list.clear();
            for (int pos = 0; pos<10;pos++){
                if (digits[pos].size() > 0) {
                    while (digits[pos].iterator().hasNext()){
                        String val = digits[pos].poll();
                        list.add(val);
                    }
                }
            }
        }
    }

    public void printArray() {
        backToLong();
        int max = 100;
        for (long t : mainArray) {
            System.out.print(t + ", ");
            max--;
            if (max <= 0) {
                break;
            }
        }
        System.out.println();
    }

    private void convertIntToLong(int [] array) {
        mainArray = new long[array.length];
        for (int i=0; i<array.length-1;i++){
            mainArray[i] = (long) array[i];
        }
    }

    private int[] convertLongToInt() {
        int [] tempArray = new int[mainArray.length];
        for (int i=0; i<mainArray.length-1;i++){
            tempArray[i] = (int) mainArray[i];
        }
        return tempArray;
    }
}
