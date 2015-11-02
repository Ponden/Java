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

		public static List <FastaFileParser> readFile(File file) throws Exception
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
			reader.close();
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

		public static void writeUnique(File inFile, File outFile) throws Exception
		{
			List<FastaFileParser> fastaList = FastaFileParser.readFile(inFile);
		
			//Storing sequences and their respective counts in a hashmap
			HashMap<String, Integer> uniqSeqs = new HashMap<String, Integer>();
			
			for(FastaFileParser fs : fastaList)
			{			
				Integer count = uniqSeqs.get(fs.getSequence());
				if( count == null )
				{
					count = 0;
				}
				count++;
				uniqSeqs.put(fs.getSequence(), count);
			}
			
			Map<String, Integer> uniSeqsSorted = sortMap(uniqSeqs);
			BufferedWriter writeFile = new BufferedWriter(new FileWriter(outFile));
			for( Map.Entry<String, Integer> x : uniSeqsSorted.entrySet())
			{
				String key = x.getKey();
				Integer value = x.getValue();
				writeFile.write(">" + value + "\n" + key + "\n");
				writeFile.flush();
			}
			writeFile.close();
		}
		
		private static HashMap sortMap(HashMap map)
		{
			List newList = new LinkedList(map.entrySet());
			Collections.sort
			(
				newList, new Comparator()
				{
					public int compare(Object o1, Object o2)
					{
						return ( (Comparable) ((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
					}
				}
			);
			HashMap sortedHashMap = new LinkedHashMap();
			for( Iterator x = newList.iterator(); x.hasNext(); )
			{
				Map.Entry entry = (Map.Entry)x.next();
				sortedHashMap.put(entry.getKey(), entry.getValue());
			}
			return sortedHashMap;	
			
		}
		
		public static void main(String[] args) throws Exception{
			
			File inFile = new File ("/Users/Ponden/Documents/Fall 2015/Programming 3/samplefasta.fa");
			File outFile = new File ("/Users/Ponden/Documents/Fall 2015/Programming 3/unique_seq_count.fa");

			List <FastaFileParser> fastaList = FastaFileParser.readFile(inFile);
			
			for (FastaFileParser seq : fastaList)
			{
				System.out.println(seq.getHeader());
				System.out.println(seq.getSequence());
				System.out.println(seq.getGCRatio());
				System.out.println(seq.getLength());	
			} 
			
			writeUnique(inFile, outFile);
		}

}
