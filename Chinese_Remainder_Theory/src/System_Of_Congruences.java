import java.util.Scanner;

public class System_Of_Congruences implements Interface {

	private int[][] system;   //two dimensional array for storing remainder and modulo
	private int num;          //number of data items
	
	
	//Object methods
	public System_Of_Congruences() {
		//constructor that creates a system
		system = new int[2][2]; //a 2-D array that is comprised of two arrays
								//since a system needs at least two congruences
		num = 0;	//number of congruences 
	}

	public int size() {
		return num;
	}
	
	public int[][] system(){
		return system;
	}
	
	
	
	//Utility methods
 	public void add(int r, int n) {
		if(num == system.length) {  
			//expands array if full
			//by creating new array and copying all values over
			int[][] temp = new int[system.length*2][2];
			for(int i=0; i < num; i++) {
				temp[i][0] = system[i][0];
				temp[i][1] = system[i][1];
			}
			system = temp;
		}
		
		system[num][0] = r;
		system[num][1] = n;
		num++;
	}
	
	public int[] get(int index) {
		if((index < 0) || (index >= num))  //throws exception if index out of range
			throw new IndexOutOfBoundsException();
		return system[index]; //this returns {r,n} of congruence at index
	}
	
	public void remove(int n) {
		if((n < 0) || (n >= num))  //throws exception if index out of range
			throw new IndexOutOfBoundsException();
		for(int i = n; i < num-1; i++) {  //removes by shifting the array over 
			system[i]=system[i+1];
		}
		num--;
	}
	
	
	
	
	//Key methods
	public int inverse(int a, int n) {
		a = a%n;
		for(int i = 1; i <= n-1; i++) {
			if((a*i)%n==1)
				return i;
		}
		return -1;
	}
	
	public int product(int n) {
		int r = system[n][0];  //the remainder of the congruence
		
		int a = 1;
		for(int i = 0; i < num; i++) {
			a = a*system[i][1];
		}
		a = a/system[n][1];  //product of all modulus except the modulo of this congruence
		
		int b = inverse(a,system[n][1]);  //inverse of a where the modulo is the same as this congruence
		
		return r*a*b;
	}
	
	public int solveSystem(int[][] system) {
		int x = 0;  //the solution that satisfies all congruences
		
		for(int i = 0; i < num; i++) {
			x = x + product(i);  //adding the products of all congruences in the system
		}
		
		int n = 1;
		for(int i = 0; i < num; i++) {
			n = n*system[i][1];  //the solution x is unique to this modulo
		}
		
		return x%n;  //final solution
	}

	
	
	
	//Extra methods
	public int gcd(int a, int b) {
		int gcd = 1; //assumes gcd is 1, then updates it later if necessary
		
		for(int i = 2; i <= a; i++) { //trying to find a common factor in a and b
			if(a%i==0 && b%i==0)
				gcd = i;
		}
		return gcd;
	}
	
	public void simplify(int index, int gcd) {
		system[index][1] /= gcd;  //changes the modulo
		system[index][0] %= system[index][1];  //changes the remainder accordingly
	}
	
	public void eliminate(int index) {
		//changing a congruence {r,n} to {0,1}
		//use this instead of remove() to prevent a specific bug
		//Ex: It is possible to remove all but one congruence, which will no longer be a system
		system[index][0] = 0;
		system[index][1] = 1;
	}
	
	public void validSystem() {
		//Stage 1: simplify congruences (if necessary)
		for(int i = 0; i < num; i++) {
			for(int j = i+1; j < num; j++) {
				int n1 = system[i][1];
				int n2 = system[j][1];
				if(gcd(n1,n2)!=1) {		//checks whether the modulus are co-prime
					if(n1<n2)  			//if yes, simplify congruence with smaller modulo
						simplify(i,gcd(n1,n2));
					else
						simplify(j,gcd(n1,n2));
				}
			}
		}
		
		//Stage 2: eliminates congruences (if necessary)
		for(int i = 0; i < num; i++) {
			for(int j = i+1; j < num; j++) {
				int r1 = system[i][0];
				int n1 = system[i][1];
				int r2 = system[j][0];
				int n2 = system[j][1];
				
				if(gcd(n1,n2)!=1) { //eliminates a congruence if one of them already implies the other one
					if(n1<n2 && r2%n1==r1)
						eliminate(i);
					else if(n1>=n2 && r1%n2==r2)
						eliminate(j);
				}
			}
		}
	}
	
	
	
	
	//Main method
	public static void main(String[] args) {
		//creating a system of congruences
		System_Of_Congruences s = new System_Of_Congruences();
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in); //reads input from user
		
		System.out.println("Welcome.");
		
		
		//reads input until done
		while(true) {  
			System.out.println("Enter the remainder and modulo of a congruence.");
			System.out.println("Enter 0 and 0 when done.");
			System.out.print("r = ");
			int r = input.nextInt();  //remainder
			System.out.print("n = ");
			int n = input.nextInt();  //modulo
			if(r==0 && n==0) //user is done entering inputs
				break;
			else if(n==0)  //invalid input
				System.out.println("Error: Modulo cannot be 0!");
			else if(r<0 || n<0)  //invalid input
				System.out.println("Please do not enter negative numbers.");
			else
				s.add(r,n);  //adds congruence to system if input {r,n} is valid
		}
		
		//solves system
		s.validSystem();  //checks whether system is valid for CRT to work, modifies it otherwise
		
		System.out.println();
		System.out.println("The solution is: " + s.solveSystem(s.system()));
	}
	
	
	
}