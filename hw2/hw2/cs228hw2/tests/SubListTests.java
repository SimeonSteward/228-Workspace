	package cs228hw2.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs228hw2.AmusingLinkedList;

/**
 * Tests of SubLists. Test cases run on just about every method in List and
 * ListIterator, so if I happen to run tests on on a method you didn't implement, you
 * can disable the test using the "@Disabled" decorator, or delete it. I tried to
 * localize tests to each individual method as best as possible, however the most basic
 * methods like add(), remove(), size() etc. show up frequently. Note... I am not doing
 * any pointer testing here. I neglected too because I am assuming you are implementing
 * your sublist such that you aren't reinventing the wheel. In other words, if your remove
 * works on your cs228hw2.AmusingLinkedList, I would hope that it works the same here. The main
 * thing to test with sublists is that changes to the sublist impact the parent list. 
 * 
 * @author Braedon Giblin (bgiblin@iastate.edu)
 *
 */
class SubListTests {
	public AmusingLinkedList<Integer> list;
	
	@BeforeEach
	void setUpBefore(){
		list = new AmusingLinkedList<Integer>();
		list.add(0);
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(40);
		list.add(50);
		list.add(60);
		list.add(70);
		list.add(80);
		list.add(90);
		list.add(null);
	}
	
	@AfterEach
	void teardown() {
		list.clear();
	}

	@Test
	void testAddE() {
		List<Integer> a = list.subList(2, 4);
		
		Assertions.assertEquals(20, a.get(0));
		Assertions.assertEquals(30, a.get(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(2);});
		
		Assertions.assertTrue(a.add(99));
		Assertions.assertEquals(12, list.size());
		Assertions.assertEquals(0, list.get(0));
		Assertions.assertEquals(10, list.get(1));
		Assertions.assertEquals(20, list.get(2));
		Assertions.assertEquals(30, list.get(3));
		Assertions.assertEquals(99, list.get(4));
		Assertions.assertEquals(40, list.get(5));
		
		Assertions.assertEquals(3, a.size());
		Assertions.assertEquals(20, a.get(0));
		Assertions.assertEquals(30, a.get(1));
		Assertions.assertEquals(99, a.get(2));
		
		Assertions.assertTrue(a.add(null));
		Assertions.assertEquals(4, a.size());
		Assertions.assertEquals(13, list.size());
		Assertions.assertEquals(null, a.get(3));
		
	}

	@Test
	void testAddIntE() {
		List<Integer> a = list.subList(0, 4);
		
		Assertions.assertEquals(0, a.get(0));
		Assertions.assertEquals(10, a.get(1));
		Assertions.assertEquals(4, a.size());
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(4);});
		
		
		a.add(0, 99);
		Assertions.assertEquals(12, list.size());
		Assertions.assertEquals(99, list.get(0));
		Assertions.assertEquals(00, list.get(1));
		Assertions.assertEquals(10, list.get(2));
		Assertions.assertEquals(20, list.get(3));
		Assertions.assertEquals(30, list.get(4));
		Assertions.assertEquals(40, list.get(5));
		
		Assertions.assertEquals(5, a.size());
		Assertions.assertEquals(99, a.get(0));
		Assertions.assertEquals(00, a.get(1));
		Assertions.assertEquals(10, a.get(2));
		Assertions.assertEquals(30, a.get(4));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(5);});
		
		a.add(5, null);
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(13, list.size());
		Assertions.assertEquals(null, a.get(5));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(6);});
		
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.add(7, 90);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.add(-1, 90);});
	}

	@Test
	void testAddAllCollectionOfQextendsE() {
		Integer[] data = {99, null, 50};
		Integer[] dataB = {};
		List<Integer> c = Arrays.asList(data);
		List<Integer> cB = Arrays.asList(dataB);
		List<Integer> a = list.subList(10, 11);
		
		Assertions.assertEquals(null, a.get(0));
		Assertions.assertEquals(1, a.size());
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(4);});
		
		
		a.addAll(c);
		Assertions.assertEquals(14, list.size());
		Assertions.assertEquals(0, list.get(0));
		Assertions.assertEquals(null, list.get(10));
		Assertions.assertEquals(99, list.get(11));
		Assertions.assertEquals(null, list.get(12));
		Assertions.assertEquals(50, list.get(13));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {list.get(14);});
		
		Assertions.assertEquals(4, a.size());
		Assertions.assertEquals(null, a.get(0));
		Assertions.assertEquals(99, a.get(1));
		Assertions.assertEquals(null, a.get(2));
		Assertions.assertEquals(50, a.get(3));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(4);});
		
		
		a.addAll(cB);
		Assertions.assertEquals(4, a.size());
		Assertions.assertEquals(14, list.size());
		Assertions.assertEquals(50, a.get(3));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(6);});
	}

	@Test
	void testAddAllIntCollectionOfQextendsE() {
		Integer[] data = {99, null, 50};
		Integer[] dataB = {};
		List<Integer> c = Arrays.asList(data);
		List<Integer> cB = Arrays.asList(dataB);
		List<Integer> a = list.subList(0, 11);
		
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.addAll(-1, c);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(12);});
		
		Assertions.assertEquals(0, a.get(0));
		Assertions.assertEquals(11, a.size());
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(11);});
		
		
		Assertions.assertTrue(a.addAll(0, c));
		Assertions.assertEquals(14, list.size());
		Assertions.assertEquals(99, list.get(0));
		Assertions.assertEquals(50, list.get(2));
		Assertions.assertEquals(70, list.get(10));
		Assertions.assertEquals(80, list.get(11));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {list.get(14);});
		
		Assertions.assertEquals(14, a.size());
		Assertions.assertEquals(99, a.get(0));
		Assertions.assertEquals(null, a.get(1));
		Assertions.assertEquals(50, a.get(2));
		Assertions.assertEquals(00, a.get(3));
		Assertions.assertEquals(null, a.get(13));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(14);});
		
		Assertions.assertFalse(a.addAll(14, cB));
		Assertions.assertEquals(14, a.size());
		Assertions.assertEquals(14, list.size());
		Assertions.assertEquals(50, a.get(2));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(14);});
		
		Assertions.assertTrue(a.addAll(14, c));
		Assertions.assertEquals(17, a.size());
		Assertions.assertEquals(17, list.size());
		
		
	}

	@Test
	void testClear() {
		List<Integer> a = list.subList(0, 4);
		
		a.clear();
		Assertions.assertEquals(0, a.size());
		Assertions.assertEquals(7, list.size());
		Assertions.assertEquals(40, list.get(0));
		
		Assertions.assertTrue(a.add(314));
		Assertions.assertEquals(314, a.get(0));
		Assertions.assertEquals(314, list.get(0));
		Assertions.assertEquals(1, a.size());
		Assertions.assertEquals(8, list.size());
	}

	@Test
	void testContains() {
		List<Integer> a = list.subList(8, 11);
		
		Assertions.assertTrue(a.contains(80));
		Assertions.assertFalse(a.contains(70));
		Assertions.assertTrue(a.contains(null));
		a.remove(null);
		Assertions.assertFalse(a.contains(null));
		
	}

	@Test
	void testContainsAll() {
		List<Integer> a = list.subList(8, 11);
		Integer[] data = {90, null};
		List<Integer> li = Arrays.asList(data);
		Integer[] dataB = {70, null};
		List<Integer> liB = Arrays.asList(dataB);
		Integer[] dataC = {98, 99};
		List<Integer> liC = Arrays.asList(dataC);
		
		Assertions.assertTrue(a.containsAll(li));
		Assertions.assertFalse(a.containsAll(liB));
		a.remove(null);
		Assertions.assertFalse(a.containsAll(li));
		Assertions.assertFalse(a.containsAll(liC));
	}

	@Test
	void testGet() {
		List<Integer> a = list.subList(8, 11);
		
		Assertions.assertEquals(80, a.get(0));
		Assertions.assertEquals(90, a.get(1));
		Assertions.assertEquals(null, a.get(2));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(3);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(-1);});
	}

	@Test
	void testIndexOf() {
		list.add(90);
		List<Integer> a = list.subList(8, 12);
		
		Assertions.assertEquals(-1, a.indexOf(50));
		Assertions.assertEquals(1, a.indexOf(90));
		Assertions.assertEquals(2, a.indexOf(null));
		
		
	}

	@Test
	void testIsEmpty() {
		List<Integer> a = list.subList(8, 11);
		List<Integer> b = list.subList(11, 11);
		
		Assertions.assertFalse(a.isEmpty());
		Assertions.assertTrue(b.isEmpty());
		
		a.clear();
		Assertions.assertTrue(a.isEmpty());
		
		a.add(5);
		Assertions.assertFalse(a.isEmpty());
		a.remove(0);
		Assertions.assertTrue(a.isEmpty());
	}

	@Test
	void testIterator() {
		List<Integer> a = list.subList(1, 4);
		
		Iterator<Integer> i = a.iterator();
		
		Assertions.assertTrue(i.hasNext());
		Assertions.assertEquals(10, i.next());
		Assertions.assertTrue(i.hasNext());
		Assertions.assertEquals(20, i.next());
		Assertions.assertTrue(i.hasNext());
		Assertions.assertEquals(30, i.next());
		Assertions.assertFalse(i.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, () -> {i.next();});
	}

	@Test
	void testLastIndexOf() {
		list.add(90);
		List<Integer> a = list.subList(8, 12);
		
		Assertions.assertEquals(-1, a.lastIndexOf(50));
		Assertions.assertEquals(3, a.lastIndexOf(90));
		Assertions.assertEquals(2, a.lastIndexOf(null));
	}

	@Test
	void testListIterator() {
		List<Integer> a = list.subList(0, 3);
		ListIterator<Integer> li = a.listIterator();
		ListIterator<Integer> liB;
		
		Assertions.assertTrue(li.hasNext());
		Assertions.assertEquals(0, li.next());
		Assertions.assertTrue(li.hasNext());
		Assertions.assertEquals(10, li.next());
		Assertions.assertTrue(li.hasNext());
		Assertions.assertEquals(20, li.next());
		Assertions.assertFalse(li.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, () -> {li.next();});
		Assertions.assertTrue(li.hasPrevious());
		Assertions.assertEquals(20, li.previous());
		Assertions.assertTrue(li.hasPrevious());
		Assertions.assertEquals(10, li.previous());
		Assertions.assertTrue(li.hasPrevious());
		Assertions.assertEquals(0, li.previous());
		Assertions.assertFalse(li.hasPrevious());
		Assertions.assertThrows(NoSuchElementException.class, () -> {li.previous();});
		
		liB = a.listIterator();
		
		Assertions.assertEquals(0, liB.nextIndex());
		Assertions.assertEquals(-1, liB.previousIndex());
		Assertions.assertThrows(IllegalStateException.class, () -> {liB.set(10);});
		Assertions.assertThrows(IllegalStateException.class, () -> {liB.remove();});
		liB.add(-10);
		Assertions.assertEquals(-10, a.get(0));
		Assertions.assertEquals(-10, list.get(0));
		Assertions.assertEquals(0, liB.next());
		Assertions.assertEquals(12, list.size());
		liB.remove();
		Assertions.assertEquals(-10, a.get(0));
		Assertions.assertEquals(-10, list.get(0));
		Assertions.assertThrows(IllegalStateException.class, () -> {liB.set(10);});
		Assertions.assertThrows(IllegalStateException.class, () -> {liB.remove();});
		Assertions.assertEquals(10, liB.next());
		liB.set(50);
		Assertions.assertEquals(50, a.get(1));
		liB.set(60);
		Assertions.assertEquals(60, a.get(1));
		
		List<Integer> b = list.subList(3, 10);
		ListIterator<Integer> liC = b.listIterator();
		Assertions.assertEquals(30, liC.next());
		liC.remove();
		Assertions.assertEquals(10, list.size());
		Assertions.assertEquals(6, b.size());
		Assertions.assertEquals(40, list.get(3));
		Assertions.assertEquals(40, b.get(0));
	}

	@Test
	void testListIteratorInt() {
		// Assuming that if regular list iterator works, then a list iterator at a new index will also work.
		// Therefore, we will just smoke test the basic operations to ensure the positioning is correct
		
		List<Integer> a = list.subList(2, 6);
		ListIterator<Integer> li;
		
		li = a.listIterator(2);
		Assertions.assertEquals(2, li.nextIndex());
		Assertions.assertEquals(1, li.previousIndex());
		Assertions.assertTrue(li.hasNext());
		Assertions.assertEquals(40, li.next());
		Assertions.assertTrue(li.hasPrevious());
		Assertions.assertEquals(40, li.previous());
		
		li = a.listIterator(4);
		Assertions.assertFalse(li.hasNext());
		Assertions.assertTrue(li.hasPrevious());
		Assertions.assertEquals(50, li.previous());
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.listIterator(5);});
	}

	@Test
	void testRemoveObject() {
		list.add(90);
		list.add(90);
		
		List<Integer> a = list.subList(9, 13);
		
		Assertions.assertEquals(4, a.size());
		Assertions.assertEquals(13, list.size());
		
		Assertions.assertEquals(90, a.get(0));
		Assertions.assertEquals(90, list.get(9));
		a.remove(Integer.valueOf(90));
		Assertions.assertEquals(null, a.get(0));
		Assertions.assertEquals(null, list.get(9));
		Assertions.assertEquals(3, a.size());
		Assertions.assertEquals(12, list.size());
		Assertions.assertFalse(a.remove(Integer.valueOf(10)));
	}

	@Test
	void testRemoveInt() {
		List<Integer> a = list.subList(8, 11);
		
		Assertions.assertEquals(3, a.size());
		Assertions.assertEquals(11, list.size());
		
		Assertions.assertEquals(80, a.get(0));
		Assertions.assertEquals(80, list.get(8));
		a.remove(1);
		Assertions.assertEquals(null, a.get(1));
		Assertions.assertEquals(null, list.get(9));
		Assertions.assertEquals(2, a.size());
		Assertions.assertEquals(10, list.size());
		
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.remove(2);});
	}

	@Test
	void testRemoveAll() {
		list.add(null);
		list.add(20);
		list.add(0);
		list.add(20);
		list.add(30);
		list.add(10);
		Integer[] data = {0, 20, null, 99};
		
		List<Integer> a = list.subList(0, 14);
		
		Assertions.assertEquals(17, list.size());
		Assertions.assertEquals(14, a.size());
		
		Assertions.assertTrue(a.removeAll(Arrays.asList(data)));
		Assertions.assertEquals(11, list.size());
		Assertions.assertEquals(8, a.size());
		
		a.removeAll(null);
		Assertions.assertEquals(11, list.size());
		Assertions.assertEquals(8, a.size());
		
	}

	@Test
	void testRetainAll() {
		list.add(null);
		list.add(20);
		list.add(0);
		list.add(20);
		list.add(30);
		list.add(10);
		Integer[] data = {0, 20, null, 99};
		Integer[] dataB = {1, 21, 2, 99};
		
		List<Integer> a = list.subList(0, 14);
		
		Assertions.assertEquals(17, list.size());
		Assertions.assertEquals(14, a.size());
		
		Assertions.assertTrue(a.retainAll(Arrays.asList(data)));
		Assertions.assertEquals(9, list.size());
		Assertions.assertEquals(6, a.size());
		
		Assertions.assertEquals(0, a.get(0));
		Assertions.assertEquals(20, a.get(1));
		Assertions.assertEquals(null, a.get(2));
		Assertions.assertEquals(null, a.get(3));
		Assertions.assertEquals(20, a.get(4));
		Assertions.assertEquals(0, a.get(5));
		
		Assertions.assertEquals(0, list.get(0));
		Assertions.assertEquals(20, list.get(1));
		Assertions.assertEquals(null, list.get(2));
		Assertions.assertEquals(null, list.get(3));
		Assertions.assertEquals(20, list.get(4));
		Assertions.assertEquals(0, list.get(5));
		Assertions.assertEquals(20, list.get(6));
		Assertions.assertEquals(30, list.get(7));
		Assertions.assertEquals(10, list.get(8));
		
		Assertions.assertTrue(a.retainAll(Arrays.asList(dataB)));
		Assertions.assertEquals(0, a.size());
		Assertions.assertEquals(3, list.size());
		
		Assertions.assertFalse(a.retainAll(list));
	}

	@Test
	void testSet() {
		List<Integer> a = list.subList(0, 6);
		
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(11, list.size());
		
		Assertions.assertEquals(30, a.set(3, 33));
		Assertions.assertEquals(33, a.get(3));
		Assertions.assertEquals(33, list.get(3));
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(11, list.size());
		
		a = list.subList(3, 6);
		
		Assertions.assertEquals(40, a.set(1, 44));
		Assertions.assertEquals(44, a.get(1));
		Assertions.assertEquals(44, list.get(4));
		Assertions.assertEquals(3, a.size());
		Assertions.assertEquals(11, list.size());
	}

	@Test
	void testSize() {
		List<Integer> a = list.subList(0, 6);
		
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(11, list.size());
		
		a.add(10);
		Assertions.assertEquals(7, a.size());
		Assertions.assertEquals(12, list.size());
		
		a.remove(3);
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(11, list.size());
		
		a = list.subList(6, 6);
		Assertions.assertEquals(0, a.size());
		
		a.add(0, 10);
		a.add(1, 10);
		Assertions.assertEquals(2, a.size());
		Assertions.assertEquals(13, list.size());
		a.addAll(list);
		Assertions.assertEquals(15, a.size());
		Assertions.assertEquals(26, list.size());
	}

	@Test
	void testSubList() {
		List<Integer> a = list.subList(0, 6);
		List<Integer> b = a.subList(0, 3);
		
		Assertions.assertNotNull(a.subList(0, 6));
		Assertions.assertNotNull(a.subList(5, 5));
		
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.subList(-1, 5);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.subList(0, 7);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.subList(6, 5);});
		
		b.add(0, 5);
		Assertions.assertEquals(5, list.get(0));
		Assertions.assertEquals(5, a.get(0));
		Assertions.assertEquals(5, b.get(0));
		
		list.set(0, 3);
		Assertions.assertEquals(3, list.get(0));
		Assertions.assertEquals(3, a.get(0));
		Assertions.assertEquals(3, b.get(0));
		
		Assertions.assertEquals(12, list.size());
		Assertions.assertEquals(7, a.size());
		Assertions.assertEquals(4, b.size());
		
		b.remove(0);
		Assertions.assertEquals(11, list.size());
		Assertions.assertEquals(6, a.size());
		Assertions.assertEquals(3, b.size());
		Assertions.assertEquals(0, list.get(0));
		Assertions.assertEquals(0, a.get(0));
		Assertions.assertEquals(0, b.get(0));
		
		List<Integer> c = b.subList(0, 2);
		c.add(22);
		Assertions.assertEquals(12, list.size());
		Assertions.assertEquals(7, a.size());
		Assertions.assertEquals(4, b.size());
		Assertions.assertEquals(3, c.size());
		Assertions.assertEquals(22, list.get(2));
		Assertions.assertEquals(22, a.get(2));
		Assertions.assertEquals(22, b.get(2));
		Assertions.assertEquals(22, c.get(2));
		
		// Assuming if we can make 3 sublists that all effect each other, we can make infinite
	}

	@Test
	void testToArray() {
		List<Integer> a = list.subList(0, 5);
		Integer[] expected = {0, 10, 20, 30, 40};
		Object[] out;

		out = a.toArray();
		Assertions.assertArrayEquals(expected, out);
		a.remove(0);
		Assertions.assertArrayEquals(expected, out);
	}

	@Test
	void testToArrayTArray() {
		List<Integer> a = list.subList(8, 11);
		
		Integer[] out = new Integer[3];
		a.toArray(out);
		Integer[] expected = {80, 90, null};
		Assertions.assertArrayEquals(expected, out);
		a.remove(2);
		Assertions.assertArrayEquals(expected, out);
		a.add(null);
		out = new Integer[1];
		out = a.toArray(out);
		Assertions.assertArrayEquals(expected, out);
		a.add(99);
		Integer[] expectedB = {80, 90, null, 99, null, null, null, null, null, null};
		out = new Integer[10];
		Integer[] outB = a.toArray(out);
		Assertions.assertArrayEquals(expectedB, out);
		Assertions.assertArrayEquals(expectedB, outB);
	}

	@Test
	void testToString() {
		// Not entirely sure how toString() should work here... the returned sublist
		// does not necessarily need to be a linked list, this perhaps the another
		// implementation of toString() would suffice... however I decided to implement
		// sublist such that it mimics our linked list... and thus returns a result
		// in the same format (even though the node links are only theoretical, and not
		// actually the indicated values at all. However, this gives the best impression
		// that this is a sublist of our linked list (as otherwise, the printed indexes 
		// would not relate to our sublist at all).
		
		List<Integer> a = list.subList(0, 7);
		String expected = "0 6 1 0\n"
						+ "1 -1 2 10\n"
						+ "2 0 3 20\n"
						+ "3 -1 4 30\n"
						+ "4 2 5 40\n"
						+ "5 -1 6 50\n"
						+ "6 4 0 60";


		Assertions.assertEquals(expected, a.toString());
		a.add(null);
		
		expected = "0 6 1 0\n"
			  	 + "1 -1 2 10\n"
				 + "2 0 3 20\n"
				 + "3 -1 4 30\n"
				 + "4 2 5 40\n"
				 + "5 -1 6 50\n"
				 + "6 4 7 60\n"
				 + "7 -1 0 null";
		
		
		Assertions.assertEquals(expected, a.toString());
		
	}

}
