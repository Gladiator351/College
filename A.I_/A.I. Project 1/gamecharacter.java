import java.util.Arrays;
import java.util.Random;

/*
 * John Weaks
 * 09/16/17
 */
public class gamecharacter
{
	static double momentum;
	static String function;
	
	public static void main(String[] args)
	{
		/* if no args are entered, fall back to defaults,
		 * those being 0 momentum & sigmoid function
		 */
		if( args.length == 0)
		{
			momentum = 0.0;
			function = "sigmoid";

			System.out.println(momentum);
			System.out.println(function);
			
			ANN();
		}
		
		/* if only a momentum value is entered,
		 * check that value for validity, then
		 * use the default function
		 */
		else if(args.length == 1)
		{
			if(args[0].equals("0.0") || args[0].equals("0") || 
					args[0].equals("0.95") || args[0].equals(".95"))
			{
				momentum = Double.valueOf(args[0]);
				function = "sigmoid";
				
				System.out.println(momentum);
				System.out.println(function);
				
				ANN();
			}
			else
			{
				System.out.println("Sorry! You entered an invalid momentum constant."
						+ " Try again with a valid value.");
			}
		}
		
		/* if both args are entered, check the
		 * momentum value first, and then the
		 * function for validity
		 */
		else
		{
			if(args[0].equals("0.0") || args[0].equals("0") || 
					args[0].equals("0.95") || args[0].equals(".95"))
			{
				momentum = Double.valueOf(args[0]);
				
				if(args[1].equalsIgnoreCase("sigmoid") || args[1].equals(null))
				{
					function = "sigmoid";
					
					System.out.println(momentum);
					System.out.println(function);
					
					ANN();
				}
				else
				{
					System.out.println("Sorry! You entered an invalid activation function."
							+ " Try again with a valid value.");
				}
			}
			else
			{
				System.out.println("Sorry! You entered an invalid momentum constant."
						+ " Try again with a valid value.");
			}
		}
	}
	
	public static void ANN()
	{
		double[] inputs = {2.0, 0.0, 0.0, 0.0};
		double[] desired_output = {0.0, 0.0, 1.0, 0.0};
		double[] ith_weights = new double[12];
		double[] hidden_sum = new double[3];
		double[] hidden_result = new double[3];
		double[] hto_weights = new double[3];
		double[] output_sum = new double[1];
		double[] output_result = new double[1];
		double[] delta_output_sum = new double[1];
		double[] delta_hto_weights = new double[3];
		double[] delta_hidden_sum = new double[3];
		double[] delta_ith_weights = new double[12];
		
		
		//produces weights
		for(int x = 0; x < 12; x++)
		{
			double rand = Math.random();
			ith_weights[x] = rand;
		}
		System.out.println("ITH Weights\n" + Arrays.toString(ith_weights));
		
		//produces 2nd set of weights
		for(int x = 0; x < 3; x++)
		{
			double rand = Math.random();
			hto_weights[x] = rand;
		}
		System.out.println("HTO Weights\n" + Arrays.toString(hto_weights));
		
		//produces function inputs
		int func_input_tmp = 0;
		for(int x = 0; x < 3; x++)
		{
			double tmp = 0;
			
			for(int y = 0; y < 4; y++)
			{
				tmp+= inputs[y] * ith_weights[func_input_tmp + y];
				//System.out.println(func_input_tmp);
			}

			func_input_tmp+= 4;
			
			hidden_sum[x] = tmp;
		}
		System.out.println("Hidden Sums\n" + Arrays.toString(hidden_sum));
		
		//produces function outputs
		for(int x = 0; x < 3; x++)
		{
			hidden_result[x] = 1/(1+(Math.pow(Math.E, -(hidden_sum[x]))));
		}
		System.out.println("Hidden Results\n" + Arrays.toString(hidden_result));
		
		//produces final inputs for output neurons
		int final_input_tmp = 0;
		for(int x = 0; x < 1; x++)
		{
			double tmp = 0;
			
			for(int y = 0; y < 3; y++)
			{
				tmp+= hidden_result[x] * ith_weights[x];
			}
			
			output_sum[x] = tmp;
		}
		System.out.println("Output Sums\n" + Arrays.toString(output_sum));
		
		//produces final outputs for output neurons
		for(int x = 0; x < 1; x++)
		{
			output_result[x] = 1/(1+(Math.pow(Math.E, -(output_sum[x]))));
		}
		System.out.println("Output Results\n" + Arrays.toString(output_result));
		
		//calculates delta output sum
		//should have several
		for(int x = 0; x < 1; x++)
		{
			double sig_deriv = output_sum[x] * (1 - output_sum[x]);
			delta_output_sum[x] = sig_deriv * (desired_output[x] - output_result[x]);
		}
		System.out.println("Delta Output Sum\n" + Arrays.toString(delta_output_sum));
		
		//calculates delta weights hidden-to-output
		for(int x = 0; x < 1; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				delta_hto_weights[y] = delta_output_sum[x] / hidden_result[y];
			}
		}
		System.out.println("HTO Weight Changes\n" + Arrays.toString(delta_hto_weights));
		
		//updates hto weights
		for(int x = 0; x < 3; x++)
		{
			hto_weights[x]= hto_weights[x] + delta_hto_weights[x];
		}
		System.out.println("Updated HTO Weights\n" + Arrays.toString(hto_weights));
		
		//calculates the delta sums for the hidden neurons
		for(int x = 0; x < 1; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				double sig_deriv = hidden_sum[y] * (1 - hidden_sum[y]);
				delta_hidden_sum[x] = delta_output_sum[x] / (hto_weights[y] * sig_deriv);
			}
		}
		System.out.println("Delta Hidden Sums\n" + Arrays.toString(delta_hidden_sum));
		
		//calculates delta for ith weights
		int delta_ith_weights_tmp = 0;
		for(int x = 0; x < 4; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				delta_ith_weights[delta_ith_weights_tmp + y] = delta_hidden_sum[y] / inputs [x];
			}
			delta_ith_weights_tmp+= 3;
		}
		System.out.println("Delta ITH Weights\n" + Arrays.toString(delta_hidden_sum));
	}
	
}
