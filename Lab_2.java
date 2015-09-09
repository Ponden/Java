package lab2;
import java.util.Random;

//Author:Laken Shumate
//Date: 9-4-2015
//BINF6380 Programming 3 Lab 2 Exercise 

public class CodonGenerator 
{
	public static void main(String[] args)
	{	
		int Codon_AAA_Count_OBS = 0;
		for(int a=0; a<1000; a++)
		{	
			Random random = new Random();
			String codon = "";
			
			for(int b=0; b<3; b++)
			{	
				int c;
				c = random.nextInt(4);
				if (c == 0)
					codon = codon + "A";
				else if (c == 1)
					codon = codon + "T";
				else if (c == 2)
					codon = codon + "G";
				else if (c == 3)
					codon = codon + "C";
			}
			if (codon.equals("AAA"))
					Codon_AAA_Count_OBS ++;
				System.out.println(codon);
		}
		System.out.println(Codon_AAA_Count_OBS);
	}
}
