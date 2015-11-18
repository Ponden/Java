import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.DecimalFormat;

public class Amino_Acid_Quiz extends JFrame
{
	private void display() {
        this.setTitle("Amino Acid Quiz");
        this.setLayout(new GridLayout(0, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
	
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
	 
	public static void main(String[] args)   
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
			
			String begin_quest_ansr = "This is a 30 second test to determine the number of amino acid codes you know."+"\n"+"\n"+"Are you ready to begin?"+"\n"+"(Please enter yes or no)";
			
			int reply = JOptionPane.showConfirmDialog(null,begin_quest_ansr, "Choose One", JOptionPane.YES_NO_OPTION);
			
			if (reply == JOptionPane.YES_OPTION){
					;
				} else if (reply == JOptionPane.NO_OPTION){
					JOptionPane.showMessageDialog(null,"Ok, please try again later."); 
					System.exit(0);
				} 
			
				double correct = 0;
				double incorrect = 0;
				
				for( int a=0; a<1; a++ )
				{
					float f = RANDOM.nextFloat();
					float sum = 0.0f;		
					for( int b=0; b<SHORT_NAMES.length; b++ )
					{
						sum+=FREQUENCY[b];	
						if( f<=sum )
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
					double score = (correct/(correct+incorrect)*100);
					DecimalFormat df = new DecimalFormat("#.00");
					String roundedscore = df.format(score);
					JOptionPane.showMessageDialog(null,"Your score is: "+roundedscore+"%");				
				}
		}
}
