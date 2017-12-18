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




}
