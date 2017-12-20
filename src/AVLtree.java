import java.util.*;


public class AVLtree<T extends Comparable<T>> implements Set<T> {

    private class Node<T> {
        private T value;
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

    private Node<T> min(Node<T> current){  //минимальное значение дерева
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

    private int maxHeight(Node<T> x){   //макс высота из правого и левого
        if (x.left == null && x.right == null) return 0;
        if (x.left == null) return x.right.height;
        if (x.right == null) return x.left.height;
        return Math.max(x.right.height,x.left.height);
    }

    private int balancefactor(Node<T> x , Node<T> y) {
        if (x == null && y == null) return 0;
        if (x == null) return -y.height;
        if (y == null) return x.height;
        return x.height - y.height;
    }


    private Node<T> find(Node<T> start, T value) {   //найти точку
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

    public Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> SmallRotationToLeft(Node<T> current) {
        if (current.right.left == null || current.right.left.height <= current.right.right.height) {
            Node<T> node = current.right;
            node.parent = current.parent;
            current.right = node.left;
            if (current.right != null) current.right.parent = current;
            current.height = maxHeight(current) + 1;
            current.parent = node;
            node.left = current;
            current = node;
            current.height = maxHeight(current) + 1;
        }
        return current;

    }

    private Node<T> SmallRotationToRight(Node<T> current){
        if (current.left.right == null || current.left.right.height <= current.left.left.height) {
            Node<T> node = current.left;
            node.parent = current.parent;
            current.left = node.right;
            if (current.left != null)
                current.left.parent = current;
            current.height = maxHeight(current) + 1;
            current.parent = node;
            node.right = current;
            current = node;
            current.height = maxHeight(current) + 1;
        }
        return current;
    }

    private Node<T> BigRotationToLeft(Node<T> current) {
        if (current.right.right == null && current.right.left != null || !(current.right.left == null || current.right.left.height <= current.right.right.height)) {
            current.right = SmallRotationToRight(current.right);
            current = SmallRotationToLeft(current);
        }
        return current;
    }
    private Node<T> BigRotationToRight(Node<T> current){
        if (current.left.left == null && current.left.right != null  || !(current.left.right == null || current.left.right.height <= current.left.left.height)) {
            current.left = SmallRotationToLeft(current.left);
            current = SmallRotationToRight(current);
        }
        return current;
    }

    private Node balance(Node<T> current) {
        if (balancefactor(current.left,current.right) == 2) {  //если левый выше правого на 2 , то ротация вправо
            current = BigRotationToRight(current);
            current = SmallRotationToRight(current);
        }
        if (balancefactor(current.left,current.right) == -2) { //если левый ниже правого на 2 , то ротация влево
            current = BigRotationToLeft(current);
            current = SmallRotationToLeft(current);
        }
        return current;
    }

    private Node input(Node<T> current , T value , Node<T> parent){ //Проверить если вставлять такой же элем
        if (current == null){
            size++;
            return new Node<T>(value, parent);
        }
        int comparison = value.compareTo(current.value);
        if (comparison > 0) {
            current.right = input(current.right , value , current);  //если значение больше "рута"
            current.height = maxHeight(current) + 1;
        }
        if (comparison < 0){
            current.left = input(current.left , value , current);   //если значение меньше "рута"
            current.height = maxHeight(current) + 1;
        }
        if (!isBalanced()) {   //вынести в др функцию
            current = balance(current);
        }
        return current;
    }

    public void input(T value){
        root = input(root,value,null);
    }


    private Node<T> delete(Node<T> current,T value){
        if (current == null) return null;
        int compareResult = value.compareTo(current.value);
        if (compareResult > 0)
            current.right = delete(current.right,value);
        else if (compareResult < 0)
            current.left = delete(current.left,value);
        else{
            if (current.right == null && current.left == null)
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
                    current.left.parent = current.right;
                    current.right.parent = current.parent;
                    current = current.right;
                } else {
                    Node<T> result = min(current.right);
                    current.value = result.value;
                    delete(current.right,current.value);
                }
            }
        }
        if (current != null){
            current.height = maxHeight(current)+1;
            current = balance(current);
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
    public boolean containsAll(Collection<?> c) {
        for (Object it : c)
            if (!contains(it))
                return false;
        return true;
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
    public boolean remove(Object o) {
        T t = (T) o;
        if (find(t) == null) return false;
        delete(t);
        return true;
    }

    @Override
        public boolean removeAll(Collection<?> c) {
            int counter = this.size;
            for (Object it : c)
                if (contains(it))
                    remove((T) it);
            return this.size < counter; //если размер после удаления меньше размера начального дерева , значит удалилось
    }

    @Override
    public boolean retainAll(Collection<?> c){
        int counter = this.size;
        ArrayList list = new ArrayList();
        for (T it : this) {
            if (!c.contains(it)) list.add(it);
        }
        removeAll(list);
        return this.size < counter;
    }

    @Override
    public boolean add(T t) {
        int size = this.size;
        input(t);
        return this.size > size;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int counter = this.size;
        for (Object it : c)
            add((T) it);
        return this.size > counter;   //если размер после вставки больше размера пустого дерева , значит вставилось
    }

    @Override
    public void clear() {
        for (Object it : this)
                remove((T) it);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            int x = 0;
            for (T i : this) {
                a[x] = (T1) i;
                x++;
            }
            do{
                a[x] = null;
                x++;
            } while (x < size);
            return a;
        } else {
            T1[] b = (T1[]) new Object[size];
            int x = 0;
            for (T i : this) {
                b[x] = (T1) i;
                x++;
            }
            return b;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (T it : this) {
            array[i] = it;
            i++;
        }
        return array;
    }
    

    @Override
    public iterator iterator() {
        return new iterator();
    }


    public class iterator implements java.util.Iterator<T> {

        private Node<T> it = null;
        private Stack<Node<T>> stack = new Stack<>();

        public iterator() {
            it = root;
            if (it == null) return;
            stack.push(null);
            while (it.left != null) {
                stack.push(it);
                it = it.left;
            }
        }


        @Override
        public boolean hasNext() {
            return it != null;
        }


        @Override
        public T next() {
            T value;
            if (hasNext()) value = it.value;
            else throw new NoSuchElementException();
            if (it.right != null) {
                it = it.right;
                while (it.left != null) {
                    stack.push(it);
                    it = it.left;
                }
            } else it = stack.pop();
            return value;
        }

        @Override
        public void remove() {AVLtree.this.remove(it);} //добавить

    }

}

