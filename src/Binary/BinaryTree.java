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
            if (needDisplay) {
                System.out.print(mainRoot+", ");
                size++;
            }
            visitAllInOrder(mainRoot.getLeftChild(),needDisplay);
            visitAllInOrder(mainRoot.getRightChild(),needDisplay);
        }
    }

    public void displayAll(){
        size = 0;
        visitAllInOrder(root,true);
    }

    private void countAllElements(){
        size = 0;
        visitAllInOrder(root,false);
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
        if (!contains(element)){
            return false;
        }
        Branch current = root;
        Branch parent;
        boolean isLeftChild = true;
        while (current.getElement().compareTo(element) != 0) {
            if (element.compareTo(current.getElement()) < 0) {
                isLeftChild = true;
                current = current.getLeftChild();
            } else {
                isLeftChild = false;
                current = current.getRightChild();
            }
            parent = current;
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                System.out.println("null null if");
                if (current == root) {
                    root = null;
                } else {
                    if (isLeftChild) {
                        parent.setLeftChild(null);
                    }
                    else {
                        parent.setRightChild(null);
                    }
                }
            }
            else {
                if (current.getRightChild()==null){
                    if (current == root) {
                        root = current.getLeftChild();
                    } else {
                        if (isLeftChild) {
                            parent.setLeftChild(current.getLeftChild());
                        }
                        else {
                            parent.setRightChild(current.getLeftChild());
                        }
                    }
                }
                else {
                    if (current.getLeftChild()==null){
                        if (current == root) {
                            root = current.getRightChild();
                        } else {
                            if (isLeftChild) {
                                parent.setLeftChild(current.getRightChild());
                            }
                            else {
                                parent.setRightChild(current.getRightChild());
                            }
                        }
                    }
                    else {
                        Branch successor = getSuccessor(current);
                        if (current == root) {
                            root = successor;
                        } else {
                            if (isLeftChild) {
                                parent.setLeftChild(successor);
                            }
                            else {
                                parent.setRightChild(successor);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void setAllToDelete(Branch current,Branch parent, Branch element, boolean isLeftChild) {
        if (current == root) {
            root = element;
        } else {
            if (isLeftChild) {
                parent.setLeftChild(element);
            }
            else {
                parent.setRightChild(element);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Branch getRoot() {
        return root;
    }
}
