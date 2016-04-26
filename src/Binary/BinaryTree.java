package Binary;


import java.util.Collection;

public class BinaryTree<T extends Comparable> {
    private Branch root;
    private int size = 0;

    public BinaryTree(T[] array) {
        addAll(array);
    }

    public BinaryTree(Collection<T> collection) {
        T[] newArray = (T[]) collection.toArray();
        addAll(newArray);
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

    private void visitAllInOrder(Branch mainRoot, boolean needDisplay) {
        if (mainRoot != null) {
            size++;
            if (needDisplay) {
                System.out.print(mainRoot + ", ");
            }
            visitAllInOrder(mainRoot.getLeftChild(), needDisplay);
            visitAllInOrder(mainRoot.getRightChild(), needDisplay);
        }
    }

    public void displayAll() {
        size = 0;
        visitAllInOrder(root, true);
    }

    private void countAllElements() {
        size = 0;
        visitAllInOrder(root, false);
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
