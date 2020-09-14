package cs228hw2;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Simeon Steward
 * A circularly linked list where even values have a previous value which points to the previous even element
 * @param <E> the type that this list will store
 */
public class AmusingLinkedList<E> implements List<E> {
    private int size;
    private Node head;

    public class Node {
        private Node next;
        private Node prev;
        private E data;
        private int index;

        Node(E o) {
            data = o;
        }

        public E getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }


    }

    /**
     * @author Simeon Steward
     * An Iterator that iterates through this list
     */
    public class AmusingListIterator implements ListIterator<E> {
        Node n;
        Node prev;
        int i;
        public AmusingListIterator() {
            n = head;
            prev = null;
        }

        public AmusingListIterator(int i) {
            this.i=i;

            if(i==size())
                n = null;
            else
                n = getNodeAtIndex(i);

            if(i==0)
                prev = null;
            else
                prev = getNodeAtIndex(i-1);
        }

        @Override
        public boolean hasNext() {
            return n!=null;
        }

        @Override
        public E next() {
            if (n==null) {
                throw new NoSuchElementException();
            }
            i++;
            E retVal = n.data;
            prev = n;
            if (n.next!=head)
                n=n.next;
            else
                n=null;
            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            return (prev!=null);
        }

        @Override
        public E previous() {
            if(!hasPrevious())
                throw new NoSuchElementException();
            i--;
            if(!isEven(prev)){
                prev=n.prev;
                n = prev.next;
                return n.data;
            } else {
                n = prev;
                if(prev.index==0){
                    prev=null;
                }else {
                    prev = prev.prev.next;
                }
                return n.data;
            }
        }

        @Override
        public int nextIndex() {
            return i;
        }

        @Override
        public int previousIndex() {
            return i-1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {

        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * @param n the node to be examined
     * @return this node's index
     */
    private int getIndex(Node n) {
        if (n == null){
            return -1;
        }
        return n.index;
    }

    /**
     * AmusingLinkedList's default constructor
     */
    public AmusingLinkedList() {
        size=0;
        head = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return(indexOf(o)>=0);
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] out = new Object[size];
        Node cur = head;
        for (int i = 0; i < size; i++) {
            out[i] = cur.data;
            cur = cur.next;
        }
        return out;
    }

    @Override
    public boolean add(E o) {
        Node newNode = new Node(o);
        newNode.index=size();
        if (head == null){
            head = newNode;
            head.prev = newNode;
            head.next = newNode;
            size++;
            return true;
        }
        if (isEven()) {
            //Even case
            head.prev.next = newNode;
            newNode.next = head;
        } else {
            //Odd case
            newNode.prev = head.prev;
            head.prev.next.next = newNode;
            head.prev = newNode;
            newNode.next = head;
        }

        size++;
        return true;
    }

    /**
     * @return whether this list has an even number of elements
     */
    private boolean isEven() {
        return (head==null || head.prev.next == head);
    }

    /**
     * @return this node has an even index
     */
    private boolean isEven(Node n) {
        return n.prev != null;
    }

    /**
     * @return last Node in this list
     */
    private Node lastNode() {
        if (head == null){
            return null;
        }
        if (isEven())
            return head.prev;
        else
            return head.prev.next;
    }

    /**
     * Removes the node n from the list
     *
     * @param n the node to be removed
     */
    private E remove(Node n) {
        if (size()<=1){
            head =null;
            size=0;
            return n.data;
        }
        else if(n == head) {
            Node last = lastNode();
            head = n.next;
            last.next=head;

        }
        else if (isEven(n)) {
            n.prev.next.next = n.next;
        } else {
            n.next.prev.next = n.next;
        }
        size--;
        fixOddEven();
        return n.data;
    }

    @Override
    public boolean remove(Object o) {
        Node cur = head;
        if(head!=null) {
            do {
                if (Objects.equals(cur.data, o)) {
                    remove(cur); //Removes said node and fixes odd even
                    return true;
                }
                cur = cur.next;
            } while ((cur != null) && cur != head);
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return (addAll(size(),collection));

    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        int index = i;
        Iterator<? extends E> iter = collection.iterator();
        if(!iter.hasNext())
            return false;
        if (index == 0){
            Node last = lastNode();
            Node next = head;
            head = new Node(iter.next());
            head.next = next;

            if(last!=null)
                last.next = head;
            index++;
            size++;
        }
        Node prev = getNodeAtIndex(index-1);
        while (iter.hasNext()) {
        add(prev, iter.next());
        prev =prev.next;
        }
        fixOddEven();
        return true;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int i) {
        Node cur = head;
        for (int j = 0; j < i; j++) {
            cur = cur.next;
        }
        return cur.getData();
    }

    /**
     * Returns the node at the given index
     *
     * @param index - index of the node to return
     * @return the node at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public Node getNodeAtIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node cur = head;
        for (int j = 0; j < index; j++) {
            cur = cur.next;
        }
        return cur;
    }

    @Override
    public E set(int i, E o) {
        Node n = getNodeAtIndex(i);
        E retval = n.getData();
        n.data = o;
        return retval;
    }


    @Override
    public void add(int i, E o) {
        if(i == 0){
            Node last = lastNode();
            Node next = head;
            head = new Node(o);
            head.next = next;
            if(last!=null)
                last.next = head;
            else
                head.next = head;
            size++;
        }
        else {
            add(getNodeAtIndex(i - 1), o);
        }
        fixOddEven();

    }

    /**
     * A helper method to help several add methods. Adds a node after the prev node with the data o
     * @param prev the node before this new node
     * @param o the data in this new node
     */
    private void add(Node prev, E o) {
        Node toAdd = new Node(o);
        toAdd.next = prev.next;
        prev.next = toAdd;
        size++;
    }

    /**
     * Fixes the entire list's amusing properties
     */
    private void fixOddEven() {
        Node cur = head;
        Node prev = null;
        int i = 0;
        while (cur.next != null && cur.next != head && cur.next.next != null && cur.next.next != head ) {
            cur.index=i++;
            cur.prev = prev; //0
            cur.next.prev = null;//1
            cur.next.index=i++;
            prev = cur;//0
            cur = cur.next.next;//2
        }

        if(cur.next == null || cur.next == head){
            cur.next = head;
        } else{
            cur.index=i++;
            cur.next.next = head;
            cur.next.prev = null;
        }


        cur.prev = prev;
        head.prev = cur;
    }

    @Override
    public E remove(int i) {
        return remove(getNodeAtIndex(i));
    }

    @Override
    public int indexOf(Object o) {
        if(head==null)
            return -1;
        Node cur = head;
        int i = 0;
        while (cur.next != null && cur.next != head) {
            if (Objects.equals(cur.data, o))
                return i;
            i++;
            cur = cur.next;
        }
        if (Objects.equals(o,cur.data))
            return i;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node cur = head;
        int i = 0;
        int index = -1;
        while (cur.next != null&&cur.next != head) {
            if (Objects.equals(o,cur.data))
                index = i;
            i++;
            cur = cur.next;
        }

        if (Objects.equals(o,cur.data))
            index = i;

        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new AmusingListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new AmusingListIterator(i);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex){
            throw new IndexOutOfBoundsException();
        }
        return new AmusingSubList<>(fromIndex,toIndex);
    }

    @Override
    public boolean retainAll(Collection collection) {
        //TODO
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        for (Object o : collection) {
            while(remove(o));
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection collection) {
        Iterator collectionIter = collection.iterator();
        while (collectionIter.hasNext()) {
            if(!contains(collectionIter.next()))
                return false;
        }
        return true;
    }

    @Override
    public <T> T[] toArray(T[] objects) {
        Node cur = head;
        T[] out;
        if (objects.length < size) {
            out = (T[]) new Object[size()];
        } else {
            out = objects;
        }
        int i = 0;
        while (cur.next != null&&cur.next!=head) {
            out[i++] = (T) cur.getData();
            cur = cur.next;
        }
        out[i] = (T) cur.getData();


        return out;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node temp = head;
        int i = 0;

        do{
            builder.append(getIndex(temp)+" ");
            builder.append(getIndex(temp.prev)+" ");
            builder.append(getIndex(temp.next)+" ");
            if(temp.data!=null) {
                builder.append(temp.getData());
            }else {
                builder.append("null");
            }
            if(temp.next!=head)
                builder.append("\n");
            temp = temp.next;
        }while (temp!=head);

        return builder.toString();
    }
}
