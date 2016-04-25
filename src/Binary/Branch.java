package Binary;


public class Branch <T extends Comparable> implements Comparable{
    private T element;
    private Branch leftChild;
    private Branch rightChild;

    public Branch(T element) {
        this.element = element;
    }

    @Override
    public int compareTo(Object o) {
        Branch newBranch = (Branch) o;
        return this.element.compareTo(newBranch.element);
    }

    public T getElement() {
        return element;
    }

    public Branch getLeftChild() {
        return leftChild;
    }

    public Branch getRightChild() {
        return rightChild;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setLeftChild(Branch leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Branch rightChild) {
        this.rightChild = rightChild;
    }

    public String toString(){
        return element.toString();
    }
}

