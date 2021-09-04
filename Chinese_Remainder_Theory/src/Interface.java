
public interface Interface {
	public int size(); //returns the number of congruences in the system
	
	public int[][] system(); //returns the system as an array for other methods to use
	
	public void add(int r, int n); //add congruences to the system 
	
	public int[] get(int index); //returns the specified congruence from the system
	
	public void remove(int index); //removes the specified congruence from the system
	
	
	
	//key methods
	public int inverse(int a, int n); //takes two ints a and n, returns inv(a)(mod n)
	
	public int product(int n); //returns the product of a congruence in the final total
	
	public int solveSystem(int[][] system); //final method, uses all other methods for the CRT to solve system     


	
	
	
	//extra methods to solve systems with modulus that are not co-prime
	public int gcd(int a, int b); //returns gcd(a,b)
	
	public void simplify(int index, int gcd); //simplifies a congruence if it's co-prime
	
	public void eliminate(int index); //eliminates a redundant congruence, different from remove()
	
	public void validSystem(); //checks whether system is valid for CRT to work, modifies system otherwise   
}
