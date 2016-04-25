package Binary;


import java.util.Collection;

public class BinaryTree <T extends Comparable> {
    private Branch root;
    private int size=0;

    public BinaryTree (T [] array) {
        for (T temp : array) {
            insertElement(temp);
            size++;
        }
    }

    public BinaryTree (Collection <T> collection) {
        while (collection.iterator().hasNext()) {
            insertElement(collection.iterator().next());
            size++;
        }
    }

    public void clear(){
        root = null;
        size=0;
    }

    public boolean isEmpty (){
        return (root==null);
    }

    public Branch findElement (T element) {
        Branch current = root;
        while (current.getElement().compareTo(element)!=0){
            if (element.compareTo(current.getElement())<0){
                current = current.getLeftChild();
            }
            else{
                current = current.getRightChild();
            }
            if (current==null) {
                return null;
            }
        }
        return current;
    }

    public boolean contains (T element){
        return (findElement(element)!=null);
    }

    public void insertElement (T element){
        Branch newBranch = new Branch(element);
        if (root == null) {
            root = newBranch;
        }
        else {
            Branch current = root;
            Branch parent;
            while (true) {
                parent = current;
                if (element.compareTo(current.getElement())<0){
                    current = current.getLeftChild();
                    if (current == null) {
                        parent.setLeftChild(newBranch);
                        return;
                    }
                }
                else {
                    current = current.getRightChild();
                    if (current == null) {
                        parent.setRightChild(newBranch);
                        return;
                    }
                }
            }
        }
    }

    public void visitAllInOrder(Branch mainRoot){
        if (mainRoot != null) {
            visitAllInOrder(mainRoot.getLeftChild());
            visitAllInOrder(mainRoot.getRightChild());
        }
    }

    public Branch getMinimum() {
        return getMinimumOrMaximum(true);
    }

    public Branch getMaximum() {
        return getMinimumOrMaximum(false);
    }

    private Branch getMinimumOrMaximum (boolean isMinimumNeed){
        Branch current, last;
        last = null;
        current = root;
        while (current!=null){
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
        while(current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }
        if(successor != deleteElement.getRightChild())  {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(deleteElement.getRightChild());
        }
        return successor;
    }

    public int getSize() {
        return size;
    }

    public Branch getRoot() {
        return root;
    }
}
