import java.util.ArrayList;


public class Run {
	static int storyLength = 500;
	static int depth = 2;//one less than intended depth

	//static String fileName = "harrySherlockCaseybook.txt";
	//static String fileName = "sixthBook.txt";
	//static String fileName1 = "Harry-Potter.txt";
	static String fileName = "sherlock_holmes.txt";

	/* 1.read document into list
	 * 2.create WordEntry for each thing
	 * 3.loop through again, creating subentries for each word
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		AndrewSimpleFile file = new AndrewSimpleFile(fileName);
		WordTable table = new WordTable(0);
		ArrayList<String> corpusList = new ArrayList<String>();
		WordEntry entry;
		
		int adCorpusLength;//adjusted length to loop through list
		
		for(String text : file) {							//for each line in the file
			String[] line = text.split(" +|\"+|--+|\\(|\\)"); 		//split text w/ this regExp      {0,1,2,3,4,5}

			//add each top level word
			for(String word : line) {						//for each word in the line
				if(!word.equals(""))
					corpusList.add(word);						//a list of all the words in the book (for the subLevels)					
			}
		}
		file.stopReading();
	
		adCorpusLength = corpusList.size() - (depth + 1);//calculates length
		
		//this counts the effect of nGrams (loops through subtables and adds to those lists)
		for(int wordsDone = 0; wordsDone < adCorpusLength; wordsDone++) {
			entry = table.recordWord(corpusList.get(wordsDone));
			
			for(int z = 0; z < depth; z++) {
				if(entry.subTable == null) {
					entry.subTable = new WordTable(z+1);
				}
				entry = entry.subTable.recordWord(corpusList.get(wordsDone + z + 1));
			}
		}
		
		//other functions
		table.processEntries();
		table.rankWords(3);
		System.out.println("\n\n");
		table.displayStory(storyLength);
		
		System.out.print("\n\n" + "Time taken: " + (System.nanoTime() - startTime)/1000000 + "ms");
	}
}

