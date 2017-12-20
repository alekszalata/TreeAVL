import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Tests {
    private AVLtree<Integer> tree = new AVLtree<>();
    private ArrayList<Integer> list = new ArrayList<>();

    @Test
    public void isBalanced(){
        tree.input(20);
        tree.input(13);
        tree.input(12);
        tree.input(23);
        assert (tree.isBalanced());
    }

    @Test
    public void contains(){
        tree.input(5);
        assert (tree.contains(5));
        tree.input(2);
        assert (tree.contains(2));
        tree.input(4);
        assert (tree.contains(4));
        tree.input(7);
        assert (tree.contains(7));
        tree.input(10);
        assert (tree.contains(10));
    }

    @Test
    public void containsAll(){
        tree.clear();
        tree.input(2);
        tree.input(4);
        tree.input(5);
        tree.input(7);
        tree.input(8);
        tree.input(10);
        list.add(2);
        list.add(5);
        list.add(4);
        list.add(7);
        assert (tree.containsAll(list));
    }

    @Test
    public void input(){
        tree.input(1);
        tree.input(3);
        tree.input(5);
        tree.input(4);
        tree.input(2);
        tree.input(10);
        assert (tree.contains(1));
        assert (tree.contains(3));
        assert (tree.contains(5));
        assert (tree.contains(4));
        assert (tree.contains(2));
        assert (tree.contains(10));
    }

    @Test
    public void clear() {
        tree.clear();
        assert (!tree.contains(1));
        assert (!tree.contains(3));
        assert (!tree.contains(5));
        assert (!tree.contains(4));
        assert (!tree.contains(2));
        assert (!tree.contains(10));
        assert (tree.size() == 0);

    }

    @Test
    public void delete() {
        tree.delete(4);
        assert (!tree.contains(4));
        tree.delete(10);
        assert (!tree.contains(10));
    }

    @Test
    public void size() {
        tree.clear();
        tree.input(3);
        tree.input(5);
        tree.input(4);
        tree.input(2);
        tree.input(7);
        assert (tree.size() == 5 );
    }

    @Test
    public void isEmpty(){
        tree.clear();
        assert (tree.isEmpty());
    }

    @Test
    public void add(){
        tree.clear();
        assert (tree.add(4));
        assert (tree.add(5));
        assert (tree.add(6));
        assert (!tree.add(4));
        assert (!tree.add(5));
        assert (!tree.add(6));
    }

    @Test
    public void addAll(){
        tree.clear();
        list.clear();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assert (tree.addAll(list));
        assert (tree.contains(2));
        assert (tree.contains(3));
        assert (tree.contains(4));
        assert (tree.contains(5));

    }

    @Test
    public void remove(){
        tree.clear();
        tree.input(1);
        tree.input(2);
        tree.input(3);
        tree.input(4);
        assert (tree.remove(1));
        assert (tree.remove(2));
        assert (tree.remove(3));
        assert (tree.remove(4));
        assert (!tree.remove(1));
        assert (!tree.remove(2));
        assert (!tree.remove(3));
        assert (!tree.remove(4));
    }

    @Test
    public void removeAll(){
        tree.clear();
        list.clear();
        tree.input(1);
        tree.input(2);
        tree.input(3);
        tree.input(4);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        assert (tree.removeAll(list));
        assert (!tree.contains(1));
        assert (!tree.contains(2));
        assert (!tree.contains(3));
        assert (!tree.contains(4));
    }

    @Test
    public void retainAll(){
        tree.clear();
        list.clear();
        tree.input(1);
        tree.input(2);
        tree.input(3);
        tree.input(4);
        tree.input(5);
        tree.input(6);
        list.add(1);
        list.add(2);
        assert (tree.retainAll(list));
        assert (tree.contains(1));
        assert (tree.contains(2));
        assert (!tree.contains(3));
        assert (!tree.contains(4));
        assert (!tree.contains(5));
        assert (!tree.contains(6));
    }

    @Test
    public void toArray(){
        tree.clear();
        Object[] answer = {0,2,5,17};
        tree.input(0);
        tree.input(5);
        tree.input(2);
        tree.input(17);
        Object[] result = tree.toArray();
        Assert.assertArrayEquals(answer,result);

    }




}
