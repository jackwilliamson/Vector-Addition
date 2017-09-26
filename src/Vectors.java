import java.util.ArrayList;

public class Vectors {
	double magnitude = 0;
	char primaryDir;
	char secondaryDir;
	double angle = 0;
	double displacementEW;
	double displacementNS;
	String units;
	boolean startNeg = false;
	
	static double totalDisplacement;
	static double totalDisplacementEW = 0;
	static double totalDisplacementNS = 0;
	static double finalAngle;
	static char finalPrimaryDir;
	static char finalSecondaryDir;
	static String finalUnits;
	static String answer;
	
	
	Vectors(String response){
		//50m[N50E]
		
		int index = 0;
		
		if(response.charAt(0) == '-'){
			index++;
			startNeg = true;
		}
		
		
		char letter = response.charAt(index);
		while(letter >= '0' && letter <= '9'){
			magnitude = 10 * magnitude + letter-'0';
			index++;
			letter = response.charAt(index);
		}
		//index--;
		
		if(startNeg){
			magnitude *= -1;
		}
		
		int firstBracketIndex = response.indexOf("[");
		
		//System.out.println(index + "    " + firstBracketIndex);
		units = response.substring(index, firstBracketIndex);
		
		//Fix later
		finalUnits = units;
		
		index = firstBracketIndex +1;
		primaryDir = response.charAt(index);
		secondaryDir = response.charAt(response.length()-2);
		
		index++;
		letter = response.charAt(index);
		while(letter >= '0' && letter <= '9'){
			angle = 10 * angle + letter-'0';
			index++;
			letter = response.charAt(index);
		}
	}
	
	
	public double getMagnitude(){
		return magnitude;
	}
	
	public char getPrimaryDir(){
		return primaryDir;
	}
	
	public char getSecondaryDir(){
		return secondaryDir;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void negNorthSouth(){
		displacementNS = -displacementNS;
	}
	
	public void negEastWest(){
		displacementEW = -displacementEW;
	}
	
	public static void negDisplacementEW(){
		totalDisplacementEW = -totalDisplacementEW;
	}
	public static void negDisplacementNS(){
		totalDisplacementNS = -totalDisplacementNS;
	}
	
	public static String calculate(ArrayList<Vectors> vectors){
		for(int i = 0; i < vectors.size(); i++){
			
			if(vectors.get(i).primaryDir =='N' || vectors.get(i).primaryDir=='S'){
				vectors.get(i).displacementNS = Math.cos(Math.toRadians(vectors.get(i).angle))
						*vectors.get(i).magnitude;
				vectors.get(i).displacementEW = Math.sin(Math.toRadians(vectors.get(i).angle))
						*vectors.get(i).magnitude;
				if(vectors.get(i).primaryDir=='S'){
					vectors.get(i).negNorthSouth();
				}
				if(vectors.get(i).secondaryDir == 'W'){
					vectors.get(i).negEastWest();
				}
			}
			else{
				vectors.get(i).displacementNS = Math.sin(Math.toRadians(vectors.get(i).angle))
						*vectors.get(i).magnitude;
				vectors.get(i).displacementEW = Math.cos(Math.toRadians(vectors.get(i).angle))
						*vectors.get(i).magnitude;
				if(vectors.get(i).primaryDir=='W'){
					vectors.get(i).negEastWest();
				}
				if(vectors.get(i).secondaryDir == 'S'){
					vectors.get(i).negNorthSouth();
				}
			}
			
			totalDisplacementEW += vectors.get(i).displacementEW;
			totalDisplacementNS += vectors.get(i).displacementNS;
		}
		
		
		if(totalDisplacementNS < 0){
			finalPrimaryDir = 'S';
			negDisplacementNS();
		}
		else{
			finalPrimaryDir = 'N';
		}
		
		if(totalDisplacementEW < 0){
			finalSecondaryDir = 'W';
			negDisplacementEW();
			
		}
		else{
			finalSecondaryDir = 'E';
		}
		
		totalDisplacement = Math.sqrt(Math.pow(totalDisplacementEW,2) + Math.pow(totalDisplacementNS,2));
		finalAngle = Math.toDegrees(Math.atan(totalDisplacementEW/totalDisplacementNS));
		
		finalAngle = Math.round(finalAngle*100) /100.0;
		totalDisplacement = Math.round(totalDisplacement*1000) /1000.0;
		
		return ""+totalDisplacement+finalUnits+"["+finalPrimaryDir+finalAngle+finalSecondaryDir+"]";
	}
	
	public static String answer(){
		
		return answer;
	}
}
