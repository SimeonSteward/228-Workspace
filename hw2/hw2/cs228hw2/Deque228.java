package cs228hw2;

import java.util.*;

/**
 * @author Simeon Steward
 * Class which implements the deque interface using an Arraylist
 * @param <E>
 */
public class Deque228<E> implements Deque<E> {
    private ArrayList<E> list;

    /**
     * Default Constructor
     */
    public Deque228() {
        list = new ArrayList<>();
    }

    @Override
    public void addFirst(E e) {
        list.add(0,e);
    }

    @Override
    public void addLast(E e) {
        list.add(e);
    }

    @Override
    public boolean offerFirst(E e) {
        list.add(0,e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        list.add(list.size(),e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (size()==0) throw new NoSuchElementException();
        return list.remove(0);
    }

    @Override
    public E removeLast() {
        if (size()==0) throw new NoSuchElementException();
        return list.remove(list.size()-1);
    }

    @Override
    public E pollFirst() {
        if (size()==0) return null;
        return list.remove(0);
    }

    @Override
    public E pollLast() {
        if (size()==0) return null;
        return list.remove(list.size()-1);
    }

    @Override
    public E getFirst() {
        if (size()==0) throw new NoSuchElementException();
        return list.get(0);
    }

    @Override
    public E getLast() {
        if (size()==0) throw new NoSuchElementException();
        return list.get(size()-1);
    }

    @Override
    public E peekFirst() {
        if (size()==0) return null;
        return list.get(0);
    }

    @Override
    public E peekLast() {
        if (size()==0) return null;
        return list.get(size()-1);
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        int initial = size();
        list.remove(o);
        return size()+1 == initial;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        int initial = size();
        list.remove(list.lastIndexOf(o));
        return size()+1 == initial;
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (size()==0) throw new NoSuchElementException();
        return list.remove(0);
    }

    @Override
    public E poll() {
        if (size()==0) return null;
        return list.remove(0);
    }

    @Override
    public E element() {
        if (size()==0) throw new NoSuchElementException();
        return list.get(0);
    }

    @Override
    public E peek() {
        if (size()==0) return null;
        return list.get(0);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return list.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return list.iterator();
    }
}
