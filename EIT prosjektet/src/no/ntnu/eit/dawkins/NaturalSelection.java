package no.ntnu.eit.dawkins;

import java.io.*;
import java.util.Arrays;

public class NaturalSelection {
	
	private static final String FILENAME = "./files/values.txt";
	private static final String FILENAME_BEST = "./files/best.txt";
	private static final int NUMBER_OF_LINES = 20;
	//TODO: READ A FILE OF PREFERRED VALUES,CHOOSE WHICH TO USE, MODYFY THEM AND SAVE TO FILE. SIMULATE.
	// FORMAT P I D R numbers
	public float[][] valueList = new float[NUMBER_OF_LINES][4];
	
	public NaturalSelection() {
		try {
			// GET INPUT FROM TERMINAL
//			BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
//			System.out.println("Enter number of values to have:");
//			int numberOfValues = Integer.parseInt(in.readLine());
//			int[] values = new int[numberOfValues];
//			for (int i = 0; i < numberOfValues; i++) {
//				System.out.println("Got + " + numberOfValues + ". Now enter number " + i+1);
//				values[i] = Integer.parseInt(in.readLine())- 1;
//			}	
			
			BufferedReader in;
			int numberOfValues = 1;
			int[] values = new int[numberOfValues];
			values[0] = getLastBest();
			
			// READ THE FILE
			float[][] oldValues = new float[NUMBER_OF_LINES][4];
			in = new BufferedReader(new FileReader(FILENAME));
			for (int i = 0; i < NUMBER_OF_LINES; i++) {
				String line = in.readLine();
				String[] lines = line.split(" ");
				for (int j = 0; j < lines.length; j++) {
					oldValues[i][j] = Float.parseFloat(lines[j]);
				}
			}
			in.close();
			if(numberOfValues == 0){
				valueList = Arrays.copyOf(oldValues, NUMBER_OF_LINES);
				return;
			}
			for (int i = 0; i < valueList.length; i++) {
				valueList[i] = dawkinsRandomizer(oldValues[values[i%numberOfValues]]);
			}

			//
			// WRITE TO FILE
			BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
			for (int i = 0; i < valueList.length; i++) {
				for (int j = 0; j < valueList[i].length; j++) {
					out.write(valueList[i][j] + " ");
				}
				out.write("\n");
			}
			//Close the output stream
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private float[] dawkinsRandomizer(float[] values){
		
		float[] toReturn = java.util.Arrays.copyOf(values, 4);
		// VELGE HVEM SOM SKAL ENDRES
		float random = toReturn[3];
		int who = (int)(Math.random()*4);
//		System.out.println(who);
		toReturn[who] = (float) (toReturn[who]+(0.5-Math.random())*random);
		
		return toReturn;
	}
	
	public void saveBest(int bestRace){
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(FILENAME_BEST));
			out.write("" + bestRace);
			//Close the output stream
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int getLastBest(){
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(FILENAME_BEST));
			String line = in.readLine();
			return Integer.parseInt(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) {
		new NaturalSelection();
	}
}
