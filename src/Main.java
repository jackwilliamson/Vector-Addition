import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * Vector must have constant units
		 * Directions must be in cardinal values and degrees, 
		 *  surrounded by square brackets
		 *  
		 *  Ex.
		 *  20m[N50W]
		 */
		
		Scanner sc = new Scanner(System.in);
		
		ArrayList<Vectors> vectors = new ArrayList<Vectors>();
		
		String response = "";
		do{
			response = sc.next();
			if(!response.equals("done")){
				vectors.add(new Vectors(response));
			}
		}
		while(!response.equals("done"));
		
		System.out.println(Vectors.calculate(vectors));
		
		
	}

}
