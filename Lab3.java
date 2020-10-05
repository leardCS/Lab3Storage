import java.io.*;
import java.util.*;

public class Lab3 {

	public static void main(String[] args) throws FileNotFoundException {
		
		String line = "";
		BufferedReader br = null;
		String cvsSplitBy = ",";
		String[] temp = new String[200];
		String temp2;
		String temp3;
		int[] counter = new int[200];
		ArrayList<String> sorted = new ArrayList<String>();
		
		//Reading data from CSV file to an array to avoid losing data in transfer process
		//Transferring data from Array to ArrayList to be able to remove duplicate entries
		int x = 0;
		try {
			br = new BufferedReader(new FileReader("spotifyDataLog.csv"));
				while ((line = br.readLine()) != null) {
						String[] name = line.split(cvsSplitBy);
						temp[x] = name[2];
						sorted.add(temp[x]);
						x++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						}
					}
				}
		//Removing duplicate entries in the ArrayList
		Set<String> set = new HashSet<>(sorted);
		sorted.clear();
		sorted.addAll(set);
		String[] sortedArr = sorted.toArray(new String[sorted.size()]);
		
		//Alphabetizing artists from A-Z with numbers and special characters appearing first
		for(int i=0; i<120; i++) {
			for(int y=1; y<120; y++) {
				if(sortedArr[i].compareToIgnoreCase(sortedArr[y])<0) {
					temp2 = sortedArr[i];
					sortedArr[i] = sortedArr[y];
					sortedArr[y] = temp2;
					}
				}
			}
			
		//Special case of moving an artist with a last index standing in terms of alphabet from the first index
		for(int e=0; e<3; e++) {
			temp3 = sortedArr[0];
			for(int i=0; i<119; i++) {
		    	temp2 = sortedArr[i];
		    	sortedArr[i] = sortedArr[i+1];
		    	sortedArr[i+1] = temp2;
		    	}
			sortedArr[119] = temp3;
			}

		//Counting how many times an artist appears on the Top 200, not including features
		for(int i=0; i<120; i++) {
			for(int y=0; y<200; y++) {
					if(temp[y].equals(sortedArr[i])) counter[i]++;
				}
			}
		
		//Writing data to .txt file
	    try {
	        FileWriter Output = new FileWriter("Report.txt");
	        Output.write("Weekly Report of Top 200 Artists in the United States (alphabetized A-Z with special characters & numbers last) - Week of 10/01/2020\n" 
	        			+"------------------------------------------------------------------------------------------------------------------------------------\n");
	        for(int i=0; i<sortedArr.length; i++) {
	        	Output.write(sortedArr[i] + " appears " + counter[i] + " time(s) on the Top 200\n");
	        }
	        Output.close();
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    }
	}