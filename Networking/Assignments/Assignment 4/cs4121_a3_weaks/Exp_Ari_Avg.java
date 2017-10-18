import java.io.File;  
import java.io.PrintWriter;
import java.io.FileNotFoundException;  
import java.util.Scanner;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
//import java.util.ArrayList;  
import java.io.IOException;  
import java.util.Random;

public class Exp_Ari_Avg{  	 
	public static double expAvg(double preExpAvg, int newSample)
	{
		double EstRTT1 = (1 - 0.125) * preExpAvg + (0.125 * newSample);
		return EstRTT1;
	}
	public static double ariAvg(double preAriAvg, int newSample, int n)
	{
		if(n == 0)
		{
			return newSample;
		}
		else
		{
			double EstRTT2 = ((n-1 / n) * preAriAvg) + ((1/n) * newSample);
			return EstRTT2;
		}
	}
	public static void sampleRTT2EstimatedRTT(Scanner in, PrintWriter out)throws IOException
	{ 
		//creates a random number to use the first time for the formulas
		double preExpAvg = Math.round(Math.random()*100);
		double preAriAvg = preExpAvg;
		int iterations = 0;
		
		//reads through each line in file
		while (in.hasNext())
		{
			//stores line in a buffer
			String buffer = in.nextLine();

			//checks if there's anything in the line
			if(buffer.length() == 0)
			{

			}
			//if there's something there, we check what it is
			else
			{
				//get the validating part of the buffer
				String validation = buffer.substring(0, 10);

				//check if the line is valid
				if(validation.equals("Reply from"))
				{
					//get the RTT
					String sampleRTT = buffer.substring(39, 41);
					int SampleRTT = (int)Math.round(Double.valueOf(sampleRTT));

					//get the estimates from both formulas
					double EstRTT1 = Math.round(expAvg(preExpAvg, SampleRTT));
					double EstRTT2 = Math.round(ariAvg(preAriAvg, SampleRTT, iterations));
					
					//writes numbers to file
					out.append(SampleRTT + "\t" + EstRTT1 + "\t" + EstRTT2 + "\n");

					//update the previous estimates and iterations
					preExpAvg = EstRTT1;
					preAriAvg = EstRTT2;
					iterations++;
				}
				else
				{

				}
			}
		}
	}
	public static void main (String[] args) throws IOException
	{ 
		Scanner fileScanner = new Scanner(new File("data.txt")); 
		PrintWriter filePrinter = new PrintWriter(new File("output.txt"));

		//prints headers for info
		filePrinter.print("SampleRTTs" + "\t" + "ExpAvgs" + "\t" + "AriAvgs");
		filePrinter.println();

		//calls body formula
		sampleRTT2EstimatedRTT(fileScanner, filePrinter);

		fileScanner.close();
		filePrinter.close();
	}
}  