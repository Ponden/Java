import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Text_to_XML 
{
	public static HashMap<String, Integer> getNodes (String inFile) throws Exception
	{	
		int ID = 1; //Set initial identification number to 1 in reference to root
		BufferedReader readFile = new BufferedReader(new FileReader(inFile));
		
		String fileLine = "";
		String [] sampleData;		
		String [] headerInfo = readFile.readLine().split("\t"); //Get headers by saving first line and splitting on tab
		
		HashMap<String, Integer> findUniqueNodes = new HashMap<>();		
		
		while((fileLine = readFile.readLine())!= null) //while the entry is not blank
		{  
			sampleData = fileLine.split("\t");	//split on tab
			
			for(int i=0;i<5;i++)	// cycle through the five column entries for each line
			{				
				if(!sampleData[i].equals("") && !findUniqueNodes.containsKey(sampleData[i] + "\t"+headerInfo[i]))
				{
					findUniqueNodes.put(sampleData[i] + "\t"+headerInfo[i], ID);
					ID++;
				}
			}
		}
		readFile.close();
		return findUniqueNodes;
	}
	
	public static HashMap<String,Integer> getEdges (String txtFile, HashMap<String,Integer> map) throws Exception
	{	
		String nextLine = "";
		
		BufferedReader lineReader = new BufferedReader(new FileReader(txtFile));
		HashMap<String,Integer> edgesMap = new HashMap<>();
		
		String [] allNodes;
		String [] fileHeader = lineReader.readLine().split("\t"); //Identify header for second read through
		
		while((nextLine = lineReader.readLine()) != null)
		{
			allNodes = nextLine.split("\t");
			int reference = 1; //set root ID first 

			for(int x=0;x<5;x++) //cycle through each of the five columns again to create the connections 
			{	
				if(!allNodes[x].equals(""))
				{
					String colHeader = fileHeader[x];
					
					int nextEdge = map.get(allNodes[x] +"\t"+colHeader); 
					
					String relationship = reference +"\t" + nextEdge;
					
					edgesMap.put(relationship,1);
					
					reference = nextEdge; //move to next column by resetting reference to current edge
				}
			}
		}
		
		lineReader.close();
		return edgesMap;
	}
	
	public static void main(String[] args) throws Exception 
	{		
		String inFile = "/Users/Ponden/Documents/Fall 2015/Programming 3/someRdpOut.txt";
		String outFile = "/Users/Ponden/Documents/Fall 2015/Programming 3/Lab_8_Output.xml";
		
		HashMap<String, Integer> nodes =  getNodes(inFile);	
		HashMap<String,Integer> edges = getEdges(inFile,nodes);
		
		BufferedWriter newXMLFile = new BufferedWriter(new FileWriter(outFile));
		
		//add XML info and root info 
		newXMLFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!-- taxonomic data  -->\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n<graph edgedefault=\"undirected\">\n<!-- data schema -->\n<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>\n<key id=\"level\" for=\"node\" attr.name=\"level\" attr.type=\"string\"/>\n<!-- nodes -->\n");
		newXMLFile.write("<node id=\"1\">\n<data key=\"name\">root</data>\n<data key=\"level\">root</data>\n</node>\n");
				
		//write node info to XML file
		for (Entry<String, Integer> writeNode: nodes.entrySet())
		{
			String nodeInfo = writeNode.getKey();
			String [] name_Level = nodeInfo.split("\t");
			
			String name = name_Level[0];
			String level = name_Level[1];
			
			Integer nodeID = writeNode.getValue();
			
			newXMLFile.write("<node id=\""+ nodeID+"\">\n<data key=\"name\">"+name+"</data>\n<data key=\"level\">"+ level +"</data>\n</node>\n");

		}
		
		//write edge info to XML file
		for(Entry<String, Integer> writeEdge: edges.entrySet())
		{
			String edgeInfo = writeEdge.getKey();
			String [] edge_source_target = edgeInfo.split("\t");
			
			String edge_source = edge_source_target[0];
			String target = edge_source_target[1];

			newXMLFile.write("<edge source=\""+ edge_source + "\" target=\""+ target + "\"></edge>\n");

		}
		
		newXMLFile.write("</graph>\n</graphml>");
		newXMLFile.close();
	}
}



