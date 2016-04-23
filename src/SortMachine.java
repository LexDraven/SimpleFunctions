import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SortMachine<T extends Comparable> {
    private T[] tempArray = null;
    private int count = 0;
    private boolean showTimeResults = false;
    private long startTime = 0;

    public T[] getAsArray() {
        return tempArray;
    }

    public int getSize() {
        return count;
    }

    public List getAsList() {
        return Arrays.asList(tempArray);
    }


    public boolean isShowTimeResults() {
        return showTimeResults;
    }

    public void setNewArray(T[] temparray) {
        tempArray = temparray;
        if (!isComparable()) {
            System.out.println("Cant be sorted!");
        } else {
            count = temparray.length;
            if (count == 0) {
                System.out.println("Empty array!");
            }
        }
    }

    public void setShowTimeResults(boolean showTimeResults) {
        this.showTimeResults = showTimeResults;
    }

    public SortMachine(T[] newArray) {
        setNewArray(newArray);
    }

    public SortMachine(T[] firstArray, T[] secondArray) {
        tempArray = firstArray;
        mergeArrays(firstArray, secondArray);
        count = tempArray.length;
    }

    public SortMachine(Collection<T> collection) {
        tempArray = (T[]) collection.toArray();
        count = tempArray.length;
    }

    private boolean isComparable() {
        T temp = tempArray[0];
        return temp instanceof Comparable;
    }

    private void changePlaces(int index1, int index2) {
        T temp = tempArray[index1];
        tempArray[index1] = tempArray[index2];
        tempArray[index2] = temp;
    }

    public void bubbleSort() {
        showTimeResults("bubble", true);
        int begin, end;
        for (begin = count - 1; begin > 1; begin--) {
            for (end = 0; end < begin; end++) {
                if (tempArray[end].compareTo(tempArray[end + 1]) == 1) {
                    changePlaces(end, end + 1);
                }
            }
        }
        showTimeResults("bubble", false);
    }

    public void selectedSort() {
        int begin, end, min;
        showTimeResults("select", true);
        for (begin = 0; begin < count - 1; begin++) {
            min = begin;
            for (end = begin + 1; end < count; end++) {
                if (tempArray[end].compareTo(tempArray[min]) == -1) {
                    min = end;
                }
            }
            changePlaces(begin, min);
        }
        showTimeResults("select", false);
    }

    private void showTimeResults(String nameSortType, boolean isBegin) {
        if (isShowTimeResults()) {
            if (isBegin) {
                System.out.println("Start " + nameSortType + " sort");
                startTime = System.currentTimeMillis();
            } else {
                long totalTime = System.currentTimeMillis() - startTime;
                System.out.println("Ends " + nameSortType + " sort, time - " + totalTime);
            }
        }
    }

    public void insertionSort() {
        int begin, end;
        showTimeResults("insertion", true);
        for (begin = 0; begin < count; begin++) {
            T temp = tempArray[begin];
            end = begin;
            while (end > 0 && (tempArray[end - 1].compareTo(temp) >= 0)) {
                tempArray[end] = tempArray[end - 1];
                --end;
            }
            tempArray[end] = temp;
        }
        showTimeResults("insertion", false);
    }

    public void mergeArrays(T[] first, T[] second) {
        ArrayList<T> last = new ArrayList<>(first.length + second.length);
        int aDex, bDex, cDex;
        aDex = bDex = cDex = 0;
        while (aDex < first.length && bDex < second.length) {
            if (first[aDex].compareTo(second[bDex]) == -1) {
                last.add(cDex++, first[aDex++]);
            } else {
                last.add(cDex++, second[bDex++]);
            }
        }
        while (aDex < first.length) {
            last.add(cDex++, first[aDex++]);
        }
        while (bDex < second.length) {
            last.add(cDex++, second[bDex++]);
        }
        tempArray = last.toArray(tempArray);
    }

    private void recMergeSort(T[] array, int lowerBorder, int upperBorder) {
        if (lowerBorder == upperBorder) {
            return;
        } else {
            int middle = (lowerBorder + upperBorder) / 2;
            recMergeSort(array, lowerBorder, middle);
            recMergeSort(array, middle + 1, upperBorder);
            merge(array, lowerBorder, middle + 1, upperBorder);
        }

    }

    public void mergeSort() {
        T[] newArray = tempArray.clone();
        showTimeResults("merge",true);
        recMergeSort(newArray, 0, count - 1);
        showTimeResults("merge",false);
    }

    private void merge(T[] array, int lower, int middle, int upper) {
        int j = 0;
        int mid = middle - 1;
        int lowerBorder = lower;
        int n = upper - lowerBorder + 1;
        while (lower <= mid && middle <= upper) {
            if (tempArray[lower].compareTo(tempArray[middle]) == -1) {
                array[j++] = tempArray[lower++];
            } else {
                array[j++] = tempArray[middle++];
            }
        }
        while (lower <= mid) {
            array[j++] = tempArray[lower++];
        }
        while (middle <= upper) {
            array[j++] = tempArray[middle++];
        }
        for (j = 0; j < n; j++) {
            tempArray[lowerBorder + j] = array[j];
        }
    }



    public void print() {
        int max = 100;
        for (T t : tempArray) {
            System.out.print(t + ", ");
            max--;
            if (max <= 0) {
                break;
            }
        }
        System.out.println();
    }

}

