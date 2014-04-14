import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a small implementation of the Trie structure to predict a set of 
 * records which begins with a given input. 
 * Its base records are of the format- mobile_number,sex
 * It accepts an input of numbers and returns a set of records which begins with the numbers
 * 18 records are statically input by the program and can be searched by providing proper inputs.
 * Examples of proper input - 9, 999, 9998, 999888777 etc.
 * @author Darshan R
 * @version 2014-04-14
 */
class TrieNode {
	
	char digit;
	TrieNode [] children;
	
	TrieNode(char digit){
		
		this.digit= digit;
		children = new TrieNode[10];
 		
	}
	
}


public class PredictSearch {

	
    StringBuffer branch = new StringBuffer();
	private String res;
	
	/**
	 * Function to create the initial Trie with root as \0 (null)
	 * @return
	 */
	public TrieNode createTree(){
		
		return new TrieNode('\0');
		
	}
	
	/**
	 * Function to insert data into the Trie. It uses an offset variable to compensate for the ASCII value of the characters
	 * @param root
	 * @param str
	 */
	public void insert(TrieNode root, String str){
		
		char [] digits = str.toCharArray();
		TrieNode currnode = root;
		int offset; 
	
		for(int i=0;i<digits.length;i++){
		
			if(digits[i] == ',')
				offset=44;
			else if (digits[i]=='F')
				offset=70;
			else if (digits[i]=='M')
				offset=77;
			else
				offset=48;

			if(currnode.children[digits[i]-offset]== null)
				currnode.children[digits[i]-offset] = new TrieNode(digits[i]);
			currnode = currnode.children[digits[i]-offset];
		}
		
	}
	
	/**
	 * Function to output the content of the entire Trie except the null character.
	 * @param root
	 * @param level
	 * @param branch
	 */
	public void print(TrieNode root,int level,StringBuffer branch) {
			
		 if (root == null)
	            return;
	       
	        for (int i = 0; i < root.children.length; i++)
	        {
	            
                  branch.insert(level, root.digit);
	              print(root.children[i], level+1, branch);   
	        }
	       
	        if (root.digit=='F' || root.digit=='M')
	        {
	            for (int j = 1; j <= level; j++)
	                System.out.print(branch.charAt(j));
	            System.out.println();
	        }
		
		
	}

	/**
	 * Function to print the predicted search results
	 * @param root
	 * @param level
	 * @param branch
	 */
	public void printRest(TrieNode root,int level,StringBuffer branch) { 
			
		 if (root == null)
	            return;

	        for (int i = 0; i < root.children.length; i++)
	        {
	            
	            branch.insert(level, root.digit);
	            printRest(root.children[i], level+1, branch);   
	        }
	       
	        if (root.digit=='F' || root.digit=='M')
	        {
	        	
	        	System.out.print(res);
	             for (int j = 1; j <= level; j++)
	               System.out.print(branch.charAt(j));
	             System.out.println();
	        }
		
		
	}


	/**
	 * Function to perform search operation. This function finds the deepest node whose path
	 *  matches the maximum with the search string and then calls printRest() to output the result
	 *  It uses an offset variable to compensate for the ASCII values of the characters.
	 * @param root
	 * @param searchStr
	 */
	public void search(TrieNode root,String searchStr){
		char [] searchArray = searchStr.toCharArray();
		TrieNode currNode = root;
		int offset = 0;
		char [] resArr = new char[searchArray.length];
		
		int i=0;
		try
		{
		while(currNode.children != null && i<searchArray.length){
			
			if(currNode.digit==searchArray[i]){
				resArr[i]=currNode.digit;
				i++;
			}
			
			if(i != searchArray.length) {
			
			if(searchArray[i] == ',')
				offset=44;
			else if (searchArray[i]=='F')
				offset=70;
			else if (searchArray[i]=='M')
				offset=77;
			else if(Character.isDigit(searchArray[i]))
				offset=48;
			else if(Character.isLowerCase(searchArray[i]))
				offset=97;
			else if(Character.isUpperCase(searchArray[i]))
				offset=65;
			else if (searchArray[i]>= '!' && searchArray[i] <= '/')
				offset=33;
			else if (searchArray[i]>= ':' && searchArray[i] <= '@')
				offset=58;
			else if (searchArray[i]>= '[' && searchArray[i] <= '`')
				offset=91;
			else if (searchArray[i]>= '{' && searchArray[i] <= '~')
				offset=123;
			else if (Character.isSpaceChar(searchArray[i]))
				offset=32;
				
			 currNode = currNode.children[searchArray[i]-offset];
				if (currNode == null)
				{	
					System.out.println("No match found");
					return;
				}
			
			}
			
		}
		}
		catch(Exception e)
		{
			System.out.println("Couldn't perform search. Sorry for the Inconvenience.");
			return;
		}
		res=new String(resArr);
		System.out.println("The records corresponding to search term are-");
		printRest(currNode, 0, branch);
	
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		char gender;
		List<String> data=new ArrayList<String>();
		PredictSearch tfs = new PredictSearch();
		String searchStr;
		TrieNode tree = tfs.createTree();
		
		
		for (int i=0;i<9;i++){
			if(i%2==0) 
				gender='F';
			else 
				gender='M';
		
			data.add("999"+i+"88777"+i+","+gender);
			data.add("999"+i+"88777"+(i+1)+","+gender);
			
		}
		
		for(int i=0;i<data.size();i++)
		{
			System.out.println(data.get(i));
			tfs.insert(tree,data.get(i));
		}
	   

	
		System.out.println("The data in the trie is-");
		tfs.print(tree,0,tfs.branch);
		
		System.out.println("Enter the value to be searched-");
		Scanner scn = new Scanner(System.in);
		searchStr= scn.nextLine();
		System.out.println("Entered value is-"+searchStr);

		tfs.search(tree,searchStr);
	
	}
	
}
