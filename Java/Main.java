import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	public static void main(String[] args) throws IOException {
	
		
        try
        {
        	
		String filepath="C:\\Users\\vidhy\\Bill-Splitter\\Files\\filename.txt";
		

		
		// Save original out stream.
        PrintStream originalOut = System.out;
        // Save original err stream.
        PrintStream originalErr = System.err;

        // Create a new file output stream.
        PrintStream fileOut = new PrintStream("D:\\BillSpliter\\Files\\out.txt");
        // Create a new file error stream. 
        PrintStream fileErr = new PrintStream("D:\\BillSpliter\\Files\\err.txt");

        // Redirect standard out to file.
        System.setOut(fileOut);
        // Redirect standard err to file.
        System.setErr(fileErr);
		
		ArrayList<Map> splitwise = Main.getObject(filepath);
		for(int i =0; i<splitwise.size(); i++) {
			Map group = splitwise.get(i);
			double totalExpense = (Double) group.get("TotalExpense");
			Double totalPersons =  (Double) group.get("TotalPersons");
			double eachPersonShare = totalExpense/totalPersons; 
			for(int j =1; j<=totalPersons; j++) {
				double personPaid =   (double) group.get(String.valueOf(j));
				double personLentOrBorrow = eachPersonShare-personPaid;
				double PostAmount=Math.abs(personLentOrBorrow);
				double RoundedAmount=(PostAmount*1000)/1000;
				if(personLentOrBorrow > 0)
				{
					String FinalLentAmount=String.format("($%.3f)",RoundedAmount);
					System.out.println(FinalLentAmount);
					
				}
				else {
					String FinalOweAmount=String.format("$%.3f",RoundedAmount);
					System.out.println(FinalOweAmount);
				}
			}
			System.out.println("\n");
		}
	 }
        
	catch(FileNotFoundException ex)
     {
         ex.printStackTrace();
     }
 
		
	}

	
	public static ArrayList<Map> getObject(String path) throws IOException {
		
		File file = new File(path); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String line;
		ArrayList<Map> splitwise = new ArrayList<Map>(); 
		int groupPersons = 0;
		double groupTotal = 0;
		int iterPerson = 0;
		double personTotal = 0;
		int personProducts = 0;
		int iterProducts = 0;
		HashMap<String, Double> group = new HashMap<String, Double>();
		while((line = br.readLine()) != null) {
			//System.out.println(Integer.parseInt(line));
				if(Main.isInt(line)) {// && Integer.parseInt(line)>0) {
					int val = Integer.parseInt(line);
					if(groupPersons  == 0) {
						groupPersons = val;
						groupTotal = 0;
					}
					else {
						personProducts = val;
						if(iterPerson != 0) {
							group.put(String.valueOf(iterPerson), personTotal);
													}
						iterPerson++ ;
						personTotal = 0;
						iterProducts = 0;
					}
				}
				else {
						Double val = Double.parseDouble(line);
						personTotal +=val;
						groupTotal += val;
						iterProducts++;	
				}
				if(groupPersons == iterPerson && iterProducts == personProducts ) {
					group.put(String.valueOf(iterPerson), personTotal);
					group.put("TotalPersons", Double.valueOf(iterPerson));
					groupPersons = 0;
					iterPerson = 0;
					group.put("TotalExpense", groupTotal);
					splitwise.add((Map) group.clone());
					group.clear();
				}
			}
		return splitwise;
	}
	
	public static boolean isInt(String value) {
		try {
			Integer.parseInt(value);
		    return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
		    return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
}
