import java.util.*;
import java.io.*;

public class FastaFileParser 
		//initialize header and sequence
{		private String header;
		private String sequence;
		
		//Runs first to set header and sequence
		public FastaFileParser(String header,String sequence) 
		{
			this.header=header;
			this.sequence=sequence;
		}

		public static List <FastaFileParser> readFile(String file) throws Exception
		{	//parse the headers and sequence information into an arrayList 		
			List<FastaFileParser> getList = new ArrayList<FastaFileParser>();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			//Initialize strings because apparently you can't do that in the while loop in Java, go figure.
			String readLine="";
			String fileHeader="";
			String fileSequence="";
			
			while ((readLine = reader.readLine()) !=null){
				if (readLine.startsWith(">"))
				{	fileHeader = readLine.substring(1);
				}else 
				{	fileSequence = readLine;
					getList.add(new FastaFileParser(fileHeader,fileSequence));
				}
			}
			reader.close(); //gotta close bc reasons.
			return getList;
		}

		public String getHeader() {
			return this.header;
		}

		public String getSequence(){
			return this.sequence;
		}

		public float getLength(){
			return this.sequence.length();
		}		
		
		public float getGCRatio()
		{	int length = sequence.length();
			int gcContent = 0;
			for (int i = 0; i < sequence.length(); i++) 
			{	if (sequence.charAt(i) == 'G' || sequence.charAt(i) == 'C')
					{gcContent++;}
			}
			return (float)gcContent/(float)length;
		}

		public static void main(String[] args) throws Exception{
			
			List <FastaFileParser> fastaList = FastaFileParser.readFile("/Users/Ponden/Documents/Fall 2015/Programming 3/samplefasta.fa");
			
			for (FastaFileParser seq : fastaList)
			{
				System.out.println(seq.getHeader());
				System.out.println(seq.getSequence());
				System.out.println(seq.getGCRatio());
				System.out.println(seq.getLength());
			}
		}

}
