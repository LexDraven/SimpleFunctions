import java.util.ArrayList;
import java.util.LinkedList;

public class BitwiseSorter {
    ArrayList<String> list;
    LinkedList <String> [] digits = new LinkedList[10];
    int [] mainArray;
    private int lengthDidits=0;

    public BitwiseSorter(int[] mainArray) {
        this.mainArray = mainArray;
        list = new ArrayList<>(mainArray.length);
        toList();
        for (int i=0; i<10; i++){
            digits[i] = new LinkedList();
        }
    }

    public void setNewArray(int[] newArray) {
        mainArray = newArray;
        list = new ArrayList<>(mainArray.length);
        toList();
    }

    private void toList(){
        for (int i=0; i<mainArray.length; i++){
            String value = Integer.toString(mainArray[i]);
            if (value.length()>lengthDidits){
                lengthDidits = value.length();
            }
            list.add(value);
        }
        for (int i=0; i<list.size(); i++){
            String temp = list.get(i);
            if (temp.length()<lengthDidits) {
                while (temp.length()<lengthDidits){
                    temp = "0"+temp;
                }
                list.set(i,temp);
            }
        }
    }

    public int[] getArray(){
        backToInt();
        return mainArray;
    }

    public int [] getSortedArray() {
        bitwiseSort();
        return mainArray;
    }

    private void backToInt() {

        for (int i=0; i<list.size(); i++) {
            mainArray[i]=0;
            int value = Integer.parseInt(list.get(i));
            mainArray[i] = value;
        }
    }

    public void bitwiseSort(){
        for (int position = lengthDidits-1; position>-1; position--) {
            for (String st: list){
                String oneDigit = st.substring(position,position+1);
                int index = Integer.parseInt(oneDigit);
                digits[index].addLast(st);
            }
            list.clear();
            for (int pos = 0; pos<10;pos++){
                if (digits[pos].size() > 0) {
                    while (digits[pos].iterator().hasNext()){
                        String val = digits[pos].pollFirst();
                        list.add(val);
                    }
                }
            }
        }
        backToInt();
    }

    public void printArray() {
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
