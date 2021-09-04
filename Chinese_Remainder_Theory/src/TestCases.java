import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCases {

	Interface s;
	
	@Before
	public void setUp() {
		s = new System_Of_Congruences();
	}
	
	
	
	
	
	@Test
	public void testAddAndSize() {
		//tests basic functionality of add and size
		assertEquals(0, s.size());
		s.add(1,2);
		assertEquals(1, s.size());
		s.add(2,3);
		assertEquals(2, s.size());
	}

	@Test
	public void testAddALot() {
		//tests that we can add a bunch of things (so array expands)
		for(int i=0; i < 10000; i++)
			s.add(i,i+2);
		assertEquals(10000, s.size());
	}
	
	@Test
	public void testGet() {
		s.add(1,2);
		s.add(2,3);
		assertArrayEquals(new int[] {1,2}, s.get(0));
		assertArrayEquals(new int[] {2,3}, s.get(1));
		
		try {
			s.get(2);
			fail();
		} catch(IndexOutOfBoundsException ex) {
			//do nothing; exception should have been thrown
		}
		
		try {
			s.get(-1);
			fail();
		} catch(IndexOutOfBoundsException ex) {
			//do nothing; exception should have been thrown
		}
	}
	
	@Test
	public void testRemove() {
		s.add(1,2);
		s.add(2,3);
		s.add(3,4);
		
		s.remove(0);
		assertEquals(2,s.size());
		assertArrayEquals(new int[] {2,3}, s.get(0));
		assertArrayEquals(new int[] {3,4}, s.get(1));
		
		s.remove(1);
		assertEquals(1,s.size());
		assertArrayEquals(new int[] {2,3}, s.get(0));
		
		s.add(4,5);
		assertEquals(2,s.size());
		assertArrayEquals(new int[] {4,5}, s.get(1));
		
		try {
			s.get(2);
			fail();
		} catch(IndexOutOfBoundsException ex) {
			//do nothing; exception should have been thrown
		}
		
		try {
			s.get(-1);
			fail();
		} catch(IndexOutOfBoundsException ex) {
			//do nothing; exception should have been thrown
		}
	}
	
	
	
	
	
	@Test
	public void testInverse() {  
		//example from CRT video
		assertEquals(2,s.inverse(35, 3));
		assertEquals(1,s.inverse(21, 5));
		assertEquals(1,s.inverse(15, 7));
	}
	
	@Test
	public void testProduct() {
		//example from CRT video
		s.add(2, 3);
		s.add(1, 5);
		s.add(4, 7);
		
		assertEquals(140,s.product(0));
		assertEquals(21,s.product(1));
		assertEquals(60,s.product(2));
	}
	
	@Test
	public void testSolveSystem() {
		//example from CRT video
		s.add(2, 3);
		s.add(1, 5);
		s.add(4, 7);
		
		assertEquals(11,s.solveSystem(s.system()));
	}
	
	@Test
	public void testSolveSystem_Problem3() {
		//with valid inputs
		s.add(4, 5);
		s.add(5, 6);
		s.add(0, 7);
		
		assertEquals(119,s.solveSystem(s.system()));
	}
	
	@Test
	public void testSolveSystem_Problem4() {
		//with valid inputs
		s.add(32, 83);
		s.add(4, 22);
		s.add(30, 135);
		
		assertEquals(24600,s.solveSystem(s.system()));
	}
	
	
	
	
	
	@Test
	public void testGCD() {
		assertEquals(1,s.gcd(2, 3));
		assertEquals(1,s.gcd(3, 5));
		assertEquals(2,s.gcd(2, 4));
		assertEquals(5,s.gcd(110, 135));
	}
	
	@Test
	public void testSimplify() {
		//from Problem 4, where both the 2nd and 3rd congruences can be simplified (but not at the same time)
		s.add(32, 83);
		s.add(70, 110);
		s.add(30, 135);
		
		s.simplify(1, s.gcd(110,135));
		assertArrayEquals(new int[] {4,22},s.system()[1]);
		
		s.simplify(2, s.gcd(110,135));
		assertArrayEquals(new int[] {3,27},s.system()[2]);
	}
	
	@Test
	public void testValidSystemStage1() {
		//problem 4, good example to test simplify()
		s.add(32, 83);
		s.add(70, 110);
		s.add(30, 135);
		
		s.validSystem();
		assertArrayEquals(new int[] {32,83},s.system()[0]);
		assertArrayEquals(new int[] {4,22},s.system()[1]);
		assertArrayEquals(new int[] {30,135},s.system()[2]);
		
		assertEquals(24600,s.solveSystem(s.system()));
	}
	
	@Test
	public void testValidSystemStage2() {
		//problem 3, good example to test eliminate()
		s.add(1, 2);
		s.add(2, 3);
		s.add(3, 4);
		s.add(4, 5);
		s.add(5, 6);
		s.add(0, 7);
		
		s.validSystem();
		assertArrayEquals(new int[] {0,1},s.system()[0]);
		assertArrayEquals(new int[] {0,1},s.system()[1]);
		assertArrayEquals(new int[] {0,1},s.system()[2]);
		assertArrayEquals(new int[] {4,5},s.system()[3]);
		assertArrayEquals(new int[] {5,6},s.system()[4]);
		assertArrayEquals(new int[] {0,7},s.system()[5]);
		
		assertEquals(119,s.solveSystem(s.system()));
	}

	
	
	
	//PROJECT 3
	
	@Test
	public void Problem3() {
		s.add(1, 2);
		s.add(2, 3);
		s.add(3, 4);
		s.add(4, 5);
		s.add(5, 6);
		s.add(0, 7);
		
		s.validSystem();
		assertEquals(119,s.solveSystem(s.system()));
	}
	
	@Test
	public void Problem4() {
		s.add(32, 83);
		s.add(70, 110);
		s.add(30, 135);
		
		s.validSystem();
		assertEquals(24600,s.solveSystem(s.system()));
	}
	
	@Test
	public void Problem5() {
		s.add(1, 2);
		s.add(2, 3);
		s.add(5, 6);
		s.add(5, 12);
		
		s.validSystem();
		assertEquals(5,s.solveSystem(s.system()));
	}
	
	@Test
	public void Problem6() {
		s.add(3, 17);
		s.add(10, 16);
		s.add(0, 15);
		
		s.validSystem();
		assertEquals(3930,s.solveSystem(s.system()));
	}
	
	@Test
	public void Problem7() {
		s.add(3, 5);
		s.add(5, 6);
		s.add(4, 7);
		s.add(8, 11);
		
		s.validSystem();
		assertEquals(2153,s.solveSystem(s.system()));
	}
	
}





