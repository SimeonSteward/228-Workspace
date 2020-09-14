package cs228hw2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Simeon Steward
 * Partially implemented sublist class
 * @param <E>
 */
public class AmusingSubList<E> extends AmusingLinkedList<E> {
    int beg;
    int end;

    AmusingSubList(int beg, int end){
        this.beg=beg;
        this.end=end;
    }

    /**
     * Checks index out of bounds
     * @param i index
     */
    private void checkIndexOutOfBounds(int i){
        if(i < 0 || i >= size()){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean isEmpty() {
        if(beg ==end)
            return true;
        else
            return false;
    }

    @Override
    public boolean contains(Object o) {
        Node cur = super.getNodeAtIndex(beg);
        do {
            if (cur.getData().equals(o))
                return true;
            cur = cur.getNext();
        }
        while (cur.getNext() != super.getNodeAtIndex(beg));
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //TODO Make iterator end
        return new AmusingListIterator(beg);
    }

    @Override
    public Object[] toArray() {
        int size=size();
        Object[] out = new Object[size];
        Node cur = getNodeAtIndex(0);
        for (int i = 0; i < size - 1; i++) {
            out[i] = cur;
            cur = cur.getNext();
        }
        out[size - 1] = cur;
        return out;
    }

    @Override
    public boolean add(E o) {
        super.add(end,o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node cur = getNodeAtIndex(0);

        for (int i = beg; i < end; i++) {
            if (cur.getData().equals(o)) {
                remove(cur); //Removes said node and fixes odd even
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return super.addAll(end,collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        return super.addAll(beg+i, collection);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            remove(i--);
            end--;
        }
    }

    @Override
    public E get(int i) {
        return super.get(i+beg);
    }

    @Override
    public Node getNodeAtIndex(int i) {
        return super.getNodeAtIndex(i+beg);
    }

    @Override
    public E set(int i, E o) {
        checkIndexOutOfBounds(i);
        return super.set(i+beg, o);
    }

    @Override
    public void add(int i, E o) {
        checkIndexOutOfBounds(i);
        super.add(i+beg, o);
        end++;
    }

    @Override
    public E remove(int i) {
        checkIndexOutOfBounds(i);
        end--;
        return super.remove(i+beg);

    }

    @Override
    public int indexOf(Object o) {
        Node cur = getNodeAtIndex(0);
        int i = 0;
        while (i<size()) {
            if (cur.getData().equals(o))
                return i;
            i++;
            cur = cur.getNext();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        //TODO
        return super.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        //TODO
        return super.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        //TODO
        return super.listIterator(i);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return super.subList(fromIndex+beg, toIndex+beg);
    }

    @Override
    public boolean retainAll(Collection collection) {
        //TODO
        return super.retainAll(collection);
    }

    @Override
    public boolean removeAll(Collection collection) {
        //TODO
        return super.removeAll(collection);
    }

    @Override
    public boolean containsAll(Collection collection) {
        //TODO
        return super.containsAll(collection);
    }

    @Override
    public <T> T[] toArray(T[] objects) {
        //TODO
        return super.toArray(objects);
    }

    @Override
    public int size(){
        return end-beg;
    }

}
