import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BitwiseSorter {
    private ArrayList<String> list;
    private Queue<String>[] digits = new Queue[10];
    private int [] mainArray;
    private int lengthBits = 0;

    public BitwiseSorter(int[] mainArray) {
        this.mainArray = mainArray;
        list = new ArrayList<>(mainArray.length);
        toStringList();
        for (int i=0; i<10; i++){
            digits[i] = new LinkedList();
        }
    }

    public void setNewArray(int[] newArray) {
        mainArray = newArray;
        list = new ArrayList<>(mainArray.length);
        toStringList();
    }

    private void toStringList(){
        for (int arrayElement : mainArray) {
            if (Integer.toString(arrayElement).length() > lengthBits) {
                lengthBits = Integer.toString(arrayElement).length();
            }
        }
        for (int aMainArray : mainArray) {
            String temp = Integer.toString(aMainArray);
            if (temp.length() < lengthBits) {
                while (temp.length() < lengthBits) {
                    temp = "0" + temp;
                }
            }
            list.add(temp);
        }
    }

    public int[] getArray(){
        backToInt();
        return mainArray;
    }

    public int [] getSortedArray() {
        bitwiseSort();
        return getArray();
    }

    private void backToInt() {
        for (int i=0; i<list.size(); i++) {
            mainArray[i] = Integer.parseInt(list.get(i));
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
        backToInt();
        int max = 100;
        for (int t : mainArray) {
            System.out.print(t + ", ");
            max--;
            if (max <= 0) {
                break;
            }
        }
        System.out.println();
    }
}
