package lab3b;
import javax.swing.*;	//Trying to make a pop-up window for the quiz.
import java.util.*;

public class Amino_Acid_Quiz
{
	private static final Random RANDOM = new Random();
	
	public static final String SHORT_NAMES[] = 
		{"A","R","N","D","C","Q","E", 
		"G","H","I","L","K","M","F", 
		"P","S","T","W","Y","V" 
		};

	public static final String FULL_NAMES[] = 
		{
		"alanine","arginine","asparagine", 
		"aspartic acid","cysteine",
		"glutamine","glutamic acid",
		"glycine","histidine","isoleucine",
		"leucine","lysine","methionine", 
		"phenylalanine","proline", 
		"serine","threonine","tryptophan", 
		"tyrosine","valine"
		};
	
	public static final float FREQUENCY[] =
		{
			0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
			0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
			0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
			0.05f, 0.05f, 0.05f, 0.05f, 0.05f
		};
	 
	public static void main(String[] args)   //Robert suggested using a hash table to store the names as keys and the codes as values.
		{
			HashMap <String, String> aminoacid_Hash = new HashMap <String, String>();
			
			for( int x=0; x<SHORT_NAMES.length; x++)
			{
				aminoacid_Hash.put(SHORT_NAMES[x], FULL_NAMES[x]);
			}	
			
		//Sort by keys using an ArrayList and print out HashMap to check pairs.		
/*			
			Set <String> set = aminoacid_Hash.keySet();
			ArrayList <String> list = new ArrayList <String>();
			list.addAll(set);
			Collections.sort(list);   

			for (String key : list) 
			{
			    System.out.println(key + ": " + aminoacid_Hash.get(key));
			}
*/			
		//Begin user dialog box. 	
			
			String begin_quest_ansr = "";
			begin_quest_ansr = JOptionPane.showInputDialog("This is a 30 second test to determine the number of amino acid codes you know."+"\n"+"\n"+"Are you ready to begin?"+"\n"+"(Please enter yes or no)");
		// JOptionPane.showConfirmDialog might be a better choice here. Not sure yet.	
			
			if (begin_quest_ansr.equalsIgnoreCase("yes")) {
					;
				} else if (begin_quest_ansr.equalsIgnoreCase("no")) {
					JOptionPane.showMessageDialog(null,"Ok, please try again later."); 
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null,"It was a yes or no question."+"\n"+"It's not that hard."+"\n"+"Please restart program until I can figure out how to restart this "+"\n"+"stupid loop because you can't answer a simple question. /endrant");
					System.exit(0);	
				} 
			
		  
		//Should only execute if user entered "yes" to the above question.
			long startquiz = System.currentTimeMillis();
			long endquiz = startquiz+30000;
			
			while(System.currentTimeMillis() < endquiz)
			{
				int correct = 0;
				int incorrect = 0;
				
				for( int a=0; a<2; a++ )
				{
					float x, sum;
					x = RANDOM.nextFloat();
					sum = 0.0f;		
					for( int b=0; b<SHORT_NAMES.length; b++ )
					{
						sum+=FREQUENCY[b];	
						
						if( x<=sum )
						{
							String answer = "";
							answer = JOptionPane.showInputDialog("What is the single letter code for: "+FULL_NAMES[b]+"?");		
							String answer_upper = answer.toUpperCase();
							
							if( FULL_NAMES[b].equals(aminoacid_Hash.get(answer_upper)))
							{
								JOptionPane.showMessageDialog(null, "That is correct!");
								correct++;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "That is incorrect. The single letter code for "+FULL_NAMES[b]+" is "+SHORT_NAMES[b]);
								incorrect++;
							}
						}
					}

				}
				int score = (correct/(correct+incorrect)*100);
				JOptionPane.showMessageDialog(null,"Your score is:"+score+"%");
				System.exit(0);
			}
		}
}
