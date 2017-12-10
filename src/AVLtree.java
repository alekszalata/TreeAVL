import java.util.AbstractSet;
import java.util.Set;



public class AVLtree<T extends Comparable<T>> extends AbstractSet<T> implements Set<T> {

    private class Node<T> {
        private T value;
        private int bfactor;
        private int height;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;


        public Node(T newValue, Node<T> newParent) {
            this.value = newValue;
            this.parent = newParent;
            this.left = null;
            this.right = null;
            this.height = 1;
            this.bfactor = 0;
            }
        }

    private int size = 0;
    private Node<T> root;

    public AVLtree(){
        this.root = null;
        this.size = 0;
    }

    private int maxDepth(Node<T> value) {     //макс глубина ветки
    if (value == null) return -1;
    return 1 + Math.max(maxDepth(value.left) , maxDepth(value.right));
    }

    private int minDepth(Node<T> value) { //мин глубина ветки
        if (value == null) return -1;
        return 1 + Math.min(minDepth(value.left) , minDepth(value.right));
    }

    private Node<T> min(Node<T> current){  //минимальное значение
        if (current.left == null) return current;
        return min(current.left);
    }

    public T min(){
        return min(root).value;
    }

    private Node<T> max(Node<T> current){  //максимальное значение
        if (current.right == null) return current;
        return max(current.right);
    }

    public T max() {
        return  max(root).value;
    }

    private boolean isBalanced(Node<T> value) {      //сбаланшено ли дерево
        return maxDepth(value) - minDepth(value) <= 1;
    }

    public boolean isBalanced(){
        return isBalanced(root);
    }

    private int maxHeight(Node<T> x, Node<T> y){   //макс высота
        if (x == null && y == null) return 0;
        if (x == null) return y.height;
        if (y == null) return x.height;
        return Math.max(x.height,y.height);
    }

    private int distance(Node<T> x , Node<T> y) { //расстояние между точками x и y
        if (x == null && y == null) return 0;
        if (x == null) return -y.height;
        if (y == null) return x.height;
        return x.height - y.height;
    }


    public Node<T> find(Node<T> start, T value) {   //найти точку
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node input(Node<T> current , T value , Node<T> parent){
        if (current == null){
            size++;
            return new Node<T>(value, parent);
        }
        int comparison = value.compareTo(current.value);
        if (comparison > 0) {
            current.right = input(current.right , value , current);  //если значение больше "рута"
            current.height = maxHeight(current.left, current.right) + 1;
        }
        if (comparison < 0){
            current.left = input(current.left , value , current);   //если значение меньше "рута"
            current.height = maxHeight(current.left, current.right) + 1;
        }
        if (!isBalanced()) {
            if (distance(current.left,current.right) == 2) {
                current = rotationToRight(current);
            }
            if (distance(current.left,current.right) == -2) {
                current = rotationToLeft(current);
            }
        }
        return current;
    }

    public void input(T value){
        root = input(root,value,null);
    }


    private Node<T> rotationToLeft(Node<T> current){
        if (current.right.right == null && current.right.left != null){
            current.right = rotationToRight(current.right);
            current = rotationToLeft(current.right);
        } else if (current.right.left == null ||
                current.right.left.height <= current.right.right.height){
            Node<T> node = current.right;
            node.parent = current.parent;
            current.right = node.left;
            if (current.right != null)
                current.right.parent = current;
            current.height = maxHeight(current.left,current.right)+1;
            current.parent = node;
            int distance = distance(current.left,current.right);
            node.left = current;
            current = node;
            distance = distance(current.left,current.right);
            current.height = maxHeight(current.left,current.right)+1;
        } else{
            current.right = rotationToRight(current.right);
            current = rotationToLeft(current);
        }
        return current;
    }
    private Node<T> rotationToRight(Node<T> current){
        if (current.left.right != null &&
                current.left.left == null){
            current.left = rotationToLeft(current.left);
            current = rotationToRight(current);
        } else if (current.left.right == null ||
                current.left.right.height <= current.left.left.height){
            Node<T> node = current.left;
            node.parent = current.parent;
            current.left = node.right;
            if (current.left != null)
                current.left.parent = current;
            current.height = maxHeight(current.left,current.right)+1;
            current.parent = node;
            int distance = distance(current.left,current.right);
            node.right = current;
            current = node;
            distance = distance(current.left,current.right);
            current.height = maxHeight(current.left,current.right)+1;
        } else {
            current.left = rotationToLeft(current.left);
            current = rotationToRight(current);
        }
        return current;
    }

    private Node<T> delete(Node<T> current,T value){
        if (current == null) return null;
        int compareResult = value.compareTo(current.value);
        if (compareResult > 0)
            current.right = delete(current.right,value);
        else if (compareResult < 0)
            current.left = delete(current.left,value);
        else{
            if (current.right == null &&current.left == null)
                current = null;
            else if (current.right == null){
                current.left.parent = current.parent;
                current = current.left;
            } else if (current.left == null){
                current.right.parent = current.parent;
                current = current.right;
            } else {
                if (current.right.left == null){
                    current.right.left = current.left;
                    current.right.parent = current.parent;
                    current.right.parent = current.parent;
                    current.left.parent = current.right;
                    current = current.right;
                } else {
                    Node<T> result = min(current.right);
                    current.value = result.value;
                    delete(current.right,current.value);
                }
            }
        }
        if (current != null){
            current.height = maxHeight(current.left,current.right)+1;
            current.height = distance(current.left,current.right);
            if (distance(current.left , current.right) == -2)
                current = rotationToLeft(current);
            else if (distance(current.left , current.right) == 2)
                current = rotationToRight(current);
        }
        return current;
    }

    public void delete(T value){
        root = delete(root, value);
        size--;
    }

    private void print(Node<T> current, int level){
        if (current != null){
            print(current.right,level+1);
            for (int i = 0; i < level; ++i)
                System.out.print("\t");
            System.out.println(current.value);
            print(current.left,level+1);
        }
    }
    public void print(){
        print(root,0);
    }


    @Override
    public boolean contains(Object o) {
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
      return size() == 0 ? true : false;
    }

    @Override
    public iterator iterator() {
        return new iterator();
    }


    public class iterator implements java.util.Iterator<T> {

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException();
        }


        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

    }
    }

