package Binary;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class BinaryTree<T extends Comparable>  {
    private Branch root;
    private int size = 0;
    private List<T> returnList;

    public BinaryTree(T[] array) {
        addAll(array);
    }

    public BinaryTree(Collection<T> collection) {
        T[] newArray = (T[]) collection.toArray();
        addAll(newArray);
    }

    public BinaryTree(T element) {
        insertElement(element);
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void addAll(T[] array) {
        for (T temp : array) {
            insertElement(temp);
        }
    }

    public List<T> getAsList(){
        returnList = new LinkedList<T>();
        countAllElements();
        List<T> tempList = returnList;
        returnList = null;
        return tempList;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public Branch findElement(T element) {
        Branch current = root;
        while (current.getElement().compareTo(element) != 0) {
            if (element.compareTo(current.getElement()) < 0) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    public boolean contains(T element) {
        return (findElement(element) != null);
    }

    public void insertElement(T element) {
        Branch newBranch = new Branch(element);
        size++;
        if (root == null) {
            root = newBranch;
        } else {
            Branch current = root;
            Branch parent;
            while (true) {
                parent = current;
                if (element.compareTo(current.getElement()) < 0) {
                    current = current.getLeftChild();
                    if (current == null) {
                        parent.setLeftChild(newBranch);
                        return;
                    }
                } else {
                    current = current.getRightChild();
                    if (current == null) {
                        parent.setRightChild(newBranch);
                        return;
                    }
                }
            }
        }
    }

    private void visitAllInOrder(Branch mainRoot) {
        if (mainRoot != null) {
            if (returnList!=null) {
                returnList.add((T) mainRoot.getElement());
            }
            size++;
            visitAllInOrder(mainRoot.getLeftChild());
            visitAllInOrder(mainRoot.getRightChild());
        }
    }

    private void countAllElements() {
        size = 0;
        visitAllInOrder(root);
    }

    public Branch getMinimum() {
        return getMinimumOrMaximum(true);
    }

    public Branch getMaximum() {
        return getMinimumOrMaximum(false);
    }

    private Branch getMinimumOrMaximum(boolean isMinimumNeed) {
        Branch current, last;
        last = null;
        current = root;
        while (current != null) {
            last = current;
            if (!isMinimumNeed) {
                current = current.getRightChild();
            } else {
                current = current.getLeftChild();
            }
        }
        return last;
    }

    private Branch getSuccessor(Branch deleteElement) {
        Branch successorParent = deleteElement;
        Branch successor = deleteElement;
        Branch current = deleteElement.getRightChild();
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }
        if (successor != deleteElement.getRightChild()) {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(deleteElement.getRightChild());
        }
        return successor;
    }

    public boolean deleteElement(T element) {
        Branch current = root;
        Branch parent = root;
        boolean isLeftChild = true;
        while (current.getElement().compareTo(element) != 0) {
            parent = current;
            if (element.compareTo(current.getElement()) < 0) {
                isLeftChild = true;
                current = current.getLeftChild();
            } else {
                isLeftChild = false;
                current = current.getRightChild();
            }
            if (current == null) {
                return false;
            }
        }
        if (current.getLeftChild() == null && current.getRightChild() == null) {
            setAllToDelete(current, parent, null, isLeftChild);
            return true;
        }
        if (current.getRightChild() == null) {
            setAllToDelete(current, parent, current.getLeftChild(), isLeftChild);
            return true;
        }
        if (current.getLeftChild() == null) {
            setAllToDelete(current, parent, current.getRightChild(), isLeftChild);
            return true;
        }
        Branch successor = getSuccessor(current);
        setAllToDelete(current, parent, successor, isLeftChild);
        successor.setLeftChild(current.getLeftChild());
        return true;
    }

    private void setAllToDelete(Branch current, Branch parent, Branch element, boolean isLeftChild) {
        if (current == root) {
            root = element;
        } else {
            if (isLeftChild) {
                parent.setLeftChild(element);
            } else {
                parent.setRightChild(element);
            }
        }
    }

    public int getSize() {
        countAllElements();
        return size;
    }

    public Branch getRoot() {
        return root;
    }

}


class Branch <T extends Comparable> implements Comparable, Cloneable{
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

    public  Branch clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Branch newBranch = new Branch(element);
        newBranch.setLeftChild(this.getLeftChild());
        newBranch.setRightChild(this.getRightChild());
        return newBranch;
    }
}
