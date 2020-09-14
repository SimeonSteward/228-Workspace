package cs228hw2.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cs228hw2.AmusingPreciseNumber;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * 
 * @author John King
 *
 */
@SuppressWarnings("unused")
class PreciseNumberTests 
{

	@Test
	void testToString() 
	{
		AmusingPreciseNumber x = new AmusingPreciseNumber(512);
		assertEquals("512", x.toString());
		AmusingPreciseNumber y = new AmusingPreciseNumber(10000);
		assertEquals("10000", y.toString());
		x = new AmusingPreciseNumber(000001);
		assertEquals("1", x.toString());
		x = new AmusingPreciseNumber("-50000");
		assertEquals("-50000", x.toString());
		x = new AmusingPreciseNumber("-500319300.34");
		assertEquals("-500319300.34", x.toString());
		x = new AmusingPreciseNumber(0);
		assertEquals("0", x.toString());

		x = new AmusingPreciseNumber("-50000.0");
		assertEquals("-50000", x.toString());

		x = new AmusingPreciseNumber("+50000");
		assertEquals("50000", x.toString());

		x = new AmusingPreciseNumber("50000.0044100");
		assertEquals("50000.00441", x.toString());

		x = new AmusingPreciseNumber("50000.00");
		assertEquals("50000", x.toString());




	}
	/*
	 * Note: before uncommenting, ensure the backing list is public (obviously change this back once done)
	 */
	@Test
	void testDeepCopy()
	{
		AmusingPreciseNumber x = new AmusingPreciseNumber(573);
		AmusingPreciseNumber y = new AmusingPreciseNumber(x);
		//this is the list... I know I could have tested this in a "better" way, but this is
			//really straightforward in my case
		y.add(new AmusingPreciseNumber("1.2"));
		//since the 0th index is the Least Significant Bit in my case
		assertNotEquals(x,y);
		
	}
	
	@Test
	void testAddSubtract()
	{
		AmusingPreciseNumber x = new AmusingPreciseNumber(512);
		AmusingPreciseNumber y = new AmusingPreciseNumber(12);
		x.add(y);
		assertEquals("524", x.toString());
		
		x = new AmusingPreciseNumber(500);
		y = new AmusingPreciseNumber(390);
		x.add(y);
		assertEquals("890", x.toString());
		
		//Basic adding tests (may also test subtract, depending on how you did it)
		x = new AmusingPreciseNumber(99847);
		y = new AmusingPreciseNumber(99910083);
		y.add(x);
		assertEquals("100009930", y.toString());
		x = new AmusingPreciseNumber(-99847);
		y = new AmusingPreciseNumber(99910083);
		y.add(x);
		assertEquals("99810236", y.toString());
		x = new AmusingPreciseNumber(99847);
		y = new AmusingPreciseNumber(-99910083);
		y.add(x);
		assertEquals("-99810236", y.toString());
		x = new AmusingPreciseNumber(-99847);
		y = new AmusingPreciseNumber(-99910083);
		y.add(x);
		assertEquals("-100009930", y.toString());
		
		//Subtraction tests
		x = new AmusingPreciseNumber(99847);
		y = new AmusingPreciseNumber(99910083);
		y.subtract(x);
		assertEquals("99810236", y.toString());
		x = new AmusingPreciseNumber(-99847);
		y = new AmusingPreciseNumber(99910083);
		y.subtract(x);
		assertEquals("100009930", y.toString());
		x = new AmusingPreciseNumber(99847);
		y = new AmusingPreciseNumber(-99910083);
		y.subtract(x);
		assertEquals("-100009930", y.toString());
		x = new AmusingPreciseNumber(-99847);
		y = new AmusingPreciseNumber(-99910083);
		y.subtract(x);
		assertEquals("-99810236", y.toString());
		
		//A few zero cases, if you think you need more due to your implementation, you might want to
		x = new AmusingPreciseNumber(0);
		y = new AmusingPreciseNumber(0);
		x.add(y);
		assertEquals("0", x.toString());
		x.subtract(y);
		assertEquals("0", x.toString());
		//sorry about this
		x.negate();
		assertEquals("0", x.toString());
		x = new AmusingPreciseNumber(800);
		x.add(y);
		assertEquals("800", x.toString());
		x.subtract(y);
		assertEquals("800", x.toString());
		y = AmusingPreciseNumber.negate(x);
		x.add(y);
		assertEquals("0", x.toString());

		Random rand = new Random();

		BigInteger bigInt1 = new BigInteger(430011,rand);
		BigInteger bigInt2 = new BigInteger(430011,rand);

		x = new AmusingPreciseNumber(bigInt1.toString());
		y = new AmusingPreciseNumber(bigInt2.toString());
		x.add(y);
		assertEquals((bigInt1.add(bigInt2)).toString(),x.toString());
	}

	//also, I was lazy, so this also tests the non-static negate method and non-static abs method
	@Test
	void staticTests()
	{
		//same as non-static add method tests, but if you want to see some other tests, let me know
		AmusingPreciseNumber x = new AmusingPreciseNumber(99847);
		AmusingPreciseNumber y = new AmusingPreciseNumber(99910083);
		AmusingPreciseNumber z = AmusingPreciseNumber.add(x, y);
		assertEquals("100009930", z.toString());
		assertEquals("99847", x.toString());
		assertEquals("99910083", y.toString());
		x.negate();
		z = AmusingPreciseNumber.add(x, y);
		assertEquals("99810236", z.toString());
		y.negate();
		z = AmusingPreciseNumber.add(x, y);
		assertEquals("-100009930", z.toString());
		x.negate();
		z = AmusingPreciseNumber.add(x, y);
		assertEquals("-99810236", z.toString());

		x = new AmusingPreciseNumber("65");
		y = new AmusingPreciseNumber("31.23");
		z = new AmusingPreciseNumber("9.009");

		assertEquals("96.23",AmusingPreciseNumber.add(x,y).toString());
		assertEquals("87.221", AmusingPreciseNumber.subtract(AmusingPreciseNumber.add(x,y),z).toString());

		x = new AmusingPreciseNumber(600);
		y = new AmusingPreciseNumber(8000);
		z = AmusingPreciseNumber.add(x, y);
		assertEquals("8600", z.toString());
		assertEquals("600", x.toString());
		assertEquals("8000", y.toString());
		//here we take a small break to test abs and negate
		z.negate();
		assertEquals("-8600", z.toString());
		z.abs();
		assertEquals("8600", z.toString());
		z.abs();
		assertEquals("8600", z.toString());
		//the static versions
		z = AmusingPreciseNumber.negate(x);
		assertEquals("-600", z.toString());
		z = AmusingPreciseNumber.abs(AmusingPreciseNumber.negate(x));
		assertEquals("600", z.toString());
		z = AmusingPreciseNumber.abs(z);
		assertEquals("600", z.toString());
		z = AmusingPreciseNumber.negate(AmusingPreciseNumber.negate(x));
		assertEquals(x.toString(), z.toString());
		
		//back to static subtraction
		z = AmusingPreciseNumber.subtract(x, y);
		assertEquals("-7400", z.toString());
		x.negate();
		z = AmusingPreciseNumber.subtract(x, y);
		assertEquals("-8600", z.toString());
		y.negate();
		z = AmusingPreciseNumber.subtract(x, y);
		assertEquals("7400", z.toString());
		x.negate();
		z = AmusingPreciseNumber.subtract(x, y);
		assertEquals("8600", z.toString());
		
	}

	@Test
	void reader() throws IOException {
		String s = "+3829374.142098";
		StringReader reader = new StringReader(s);
		AmusingPreciseNumber x = new AmusingPreciseNumber(reader);
		AmusingPreciseNumber y = new AmusingPreciseNumber(s);
		assertEquals(y.toString(),x.toString());

		s = "-3829374.142098";
		 reader = new StringReader(s);
		 x = new AmusingPreciseNumber(reader);
		y = new AmusingPreciseNumber(s);
		assertEquals(y.toString(),x.toString());

		s = "-3829374";
		reader = new StringReader(s);
		x = new AmusingPreciseNumber(reader);
		y = new AmusingPreciseNumber(s);
		assertEquals(y.toString(),x.toString());

		s = ".142098";
		reader = new StringReader(s);
		x = new AmusingPreciseNumber(reader);
		y = new AmusingPreciseNumber(s);
		assertEquals(y.toString(),x.toString());

		Random rand = new Random();
		BigInteger bigInt1 = new BigInteger(4000110,rand);

		s = bigInt1.toString();
		reader = new StringReader(s);
		x = new AmusingPreciseNumber(reader);
		y = new AmusingPreciseNumber(s);
		assertEquals(y.toString(),x.toString());

		s =  " .142098";
		reader = new StringReader(s);
		x = new AmusingPreciseNumber(reader);
		y = new AmusingPreciseNumber(".142098");
		assertEquals(y.toString(),x.toString());


	}
}
