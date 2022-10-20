import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.*; 
import java.lang.Math;

public class OKC{

	public static float effectiveFG(int threeMade, int fieldGoalMade, int totalShots){
		return (float)((threeMade*0.5)+(float)fieldGoalMade)/(float)totalShots;
	}

	public static boolean isCorner(float x, float y){
		return (y<=7.8 && Math.abs(x)>=22);
	}

	public static boolean isNonCThree(float x, float y){
		float distance = (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		return (distance>=23.75);
	}

	public static void shotDistribution(ArrayList<float[]> team){

		int cornerThrees = 0;
		int madeCornerThrees = 0;

		int nonCThrees = 0;
		int madeNonCThrees = 0;

		int insideArc = 0;
		int madeInsideArc = 0;

		int totalShots = team.size();

		for(int i = 0; i<team.size(); i++){

			float x = team.get(i)[0];
			float y = team.get(i)[1];
			int made = (int)team.get(i)[2];

			if(isCorner(x,y)){
				cornerThrees++;
				if(made == 1)
					madeCornerThrees++;
			}
			else if(isNonCThree(x,y)){
				nonCThrees++;
				if(made == 1)
					madeNonCThrees++;
			}
			else{
				insideArc++;
				if(made == 1)
					madeInsideArc++;
			}
		}



		System.out.println();
		System.out.println("2P%: " + (float)insideArc/totalShots);
		System.out.println("C3%: " + (float)cornerThrees/totalShots);
		System.out.println("NC3%: " + (float)nonCThrees/totalShots);
		System.out.println();
		System.out.println("EFG 2P%: " + effectiveFG(0, madeInsideArc, insideArc));
		System.out.println("EFG C3%: " + effectiveFG(madeCornerThrees, madeCornerThrees, cornerThrees));
		System.out.println("EFG NC3%: " + effectiveFG(madeNonCThrees, madeNonCThrees, nonCThrees));
		System.out.println();





	}


	public static void main(String[] args) {
		
		ArrayList<float[]> teamA = new ArrayList<float[]>();
		ArrayList<float[]> teamB = new ArrayList<float[]>();
		String input;

		try{
        	BufferedReader in = new BufferedReader(new FileReader("shots_data.csv"));
        	in.readLine();
        	while ((input = in.readLine()) != null){
        		String[] data = input.split(",");
        	
        		float[] addShot = new float[]{Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])};
	        	if("Team A".equals(data[0])){
	        		teamA.add(addShot);
	        	}
	        	else{
	        		teamB.add(addShot);
	        	} 	
       		}      			
        }
        catch(FileNotFoundException e){
        	e.printStackTrace();
        }
        catch(IOException e){
        	e.printStackTrace();
        }
	
		System.out.println();
		System.out.println("TEAM A");
		shotDistribution(teamA);
		System.out.println();
		System.out.println("TEAM B");
		shotDistribution(teamB);


	}
}
