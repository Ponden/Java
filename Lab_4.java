package lab4;
import java.util.*;

public class RandomProteinGenerator
	{
	char[] amino_acids = { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y' };
	float [] nonuniform_freqs = {0.072658f, 0.024692f, 0.050007f, 0.061087f, 0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f, 0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f, 0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f, 0.032955f};
	float [] uniform_freqs = { 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f};
//	private float[] amino_acid_freqs = new float[amino_acids.length];
//	private static Random RANDOM = new Random();
	boolean use_uniform_freqs;	
	int length;
	float [] frequency;
	public RandomProteinGenerator(boolean use_uniform_freqs)
	{
		this.use_uniform_freqs = use_uniform_freqs; 
		if(this.use_uniform_freqs == true){
			this.frequency = uniform_freqs;
		}else{
			this.frequency = nonuniform_freqs; 
		}
	}
	public String getRandomProtein(int length)
	{
		Random RANDOM = new Random();
		String random_peptide = new String();
		this.length = length;
		for(int a=0; a < length; a++)
		{
			float f = RANDOM.nextFloat();
			float sum = 0.0f;
			for(int b=0; b < amino_acids.length; b++)
			{
				sum += this.frequency[b];
				if(f <= sum)
				{
					random_peptide += Character.toString(amino_acids[b]);
					break;
				}
			}
		}
			return random_peptide;
	}
	public double getExpectedFrequency(String protSeq) 
	{
		double expected_freq = 1.0;
		if (use_uniform_freqs == true){
			return Math.pow((1.0 / 20.0), protSeq.length());
		} else if (use_uniform_freqs == false) 
		{	for (int c = 0; c < protSeq.length(); c++){
				char tempcharindex = protSeq.charAt(c);
				int char_index = 0;
				while (true){
					if (amino_acids[char_index] == tempcharindex){
						expected_freq *= nonuniform_freqs[char_index];
						break;
					}
				char_index++;
				}
			}
		}
		return expected_freq;
	}
	public double getFrequencyFromSimulation(String protSeq, int numIterations) {
		int count = 0;
		for (int i = 0; i < numIterations; i++) {
			String temp = getRandomProtein(protSeq.length());
			if (temp.equals(protSeq)) {
				count++;
			}
		}
		return (double) count / (double) numIterations;
	}
	public static void main(String[] args) throws Exception
	{
			String testProtein = "ACD";
			int numIterations =  10000000;
			
			RandomProteinGenerator realisticGen = new RandomProteinGenerator(false);
			
			System.out.println(realisticGen.getExpectedFrequency(testProtein));
			System.out.println(realisticGen.getFrequencyFromSimulation(testProtein,numIterations));	
			
			RandomProteinGenerator uniformGen = new RandomProteinGenerator(true);
			
			System.out.println(uniformGen.getExpectedFrequency(testProtein));  
			System.out.println(uniformGen.getFrequencyFromSimulation(testProtein,numIterations));
		}
}	
