package lab_9;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.HashMap;
import java.util.StringTokenizer;
 
public class Needleman_Wunsch 
{
	private static final int[][] BLOSUM50 = new int[20][20];
	private static final HashMap<String, Integer> Value = new HashMap<>();
	private static final int left = -1;
	private static final int up = 1;
	private static final int diagonal = 0;
	private static final int gap_penalty = -8;

	public static void initialize() 
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("/Users/Ponden/Documents/Fall 2015/Programming 3/Blosum50.txt"));
		
			String line = reader.readLine();
			
			for (int i = 0; (line = reader.readLine()) != null; i++) 
			{
				StringTokenizer token = new StringTokenizer(line);
				
				if (token.hasMoreTokens())
				{	
					Value.put(token.nextToken(), i);
				}
				
				for (int j = 0; token.hasMoreTokens(); ++j)
				{
					BLOSUM50[i][j] = Integer.parseInt(token.nextToken());
				}	
			}
			reader.close();
		}
		catch (Exception ex)
			{
				ex.printStackTrace();
			}
	}

	public static void main(String[] args)
	{
		initialize();

		try {
			
			File inFile = new File ("/Users/Ponden/Documents/Fall 2015/Programming 3/twoSeqs.txt");
			List<FastaFileParser> seqs = FastaFileParser.readFile(inFile);
			String sequence1 = seqs.get(0).getSequence();
			String sequence2 = seqs.get(1).getSequence();
			int[][] score = new int[sequence1.length()][sequence2.length()];
			int[][] pointer = new int[sequence1.length()][sequence2.length()];

			long begin_time = System.currentTimeMillis();
			score[0][0] = 0;
			pointer[0][0] = 0;

			for (int i = 0; i < sequence1.length(); ++i) 
			{
				for (int j = 0; j < sequence2.length(); ++j) 
				{
					if (i == 0) 
					{
						if (j != 0) 
						{
							score[i][j] = score[i][j - 1] + gap_penalty;
							pointer[i][j] = left;
						}
					} 
					else if (j == 0) 
					{
						score[i][j] = score[i - 1][j] + gap_penalty;
						pointer[i][j] = up;
					}
					else 
						{
						pointer[i][j] = diagonal;
						score[i][j] = score[i - 1][j - 1] + BLOSUM50[Value.get("" + sequence1.charAt(i))][Value.get("" + sequence2.charAt(j))];
						
						if (score[i][j] <= score[i - 1][j] + gap_penalty) 
						{
							score[i][j] = score[i - 1][j] + gap_penalty;
							pointer[i][j] = up;
						}
						
						if (score[i][j] <= score[i][j - 1] + gap_penalty) 
						{
							score[i][j] = score[i][j - 1] + gap_penalty;
							pointer[i][j] = left;
						}
					}
				}
			}

			StringBuffer seq1_aligned = new StringBuffer();
			StringBuffer seq2_aligned = new StringBuffer();
			int x, y;
			for (x = pointer.length - 1, y = pointer[0].length - 1; x != 0 && y != 0;) {
				if (pointer[x][y] == up) {
					seq1_aligned.append("" + sequence1.charAt(x));
					seq2_aligned.append("-", 0, 1);
					--x;
				} else if (pointer[x][y] == left) {
					seq1_aligned.append("-", 0, 1);
					seq2_aligned.append("" + sequence2.charAt(y));
					--y;
				} else {
					seq1_aligned.append("" + sequence1.charAt(x));
					seq2_aligned.append("" + sequence2.charAt(y));
					--x;
					--y;
				}
			}
			seq1_aligned.append("" + sequence1.charAt(x), 0, 1);
			seq2_aligned.append("" + sequence2.charAt(y), 0, 1);
			

			System.out.println("Alignment:\n" + seq1_aligned.reverse().toString() + "\n" + seq2_aligned.reverse().toString());
			System.out.println("Score =" + score[score.length - 1][score[0].length - 1]);
			System.out.println("Calculation time = " + (System.currentTimeMillis() - begin_time) / 1000f + " seconds");

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}
