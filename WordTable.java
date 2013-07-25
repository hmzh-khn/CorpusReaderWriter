import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class WordTable {

	private HashMap<String,WordEntry> map;	//Hashmap: key:value, word:WordEntry
	private ArrayList<WordEntry> entries;	/*this will be sorted once filled
											  iterable*/
	private Random numGen;					//used to find a random word
	private int depth;
	public int wordCount = 0;				//passed to WordEntries to calculate probability

	//constructor
	WordTable(int depth) {
		this.map = new HashMap<String, WordEntry>();
		this.entries = new ArrayList<WordEntry>();
		this.numGen = new Random();
		this.depth = depth;
	}

	/* 1.Create a WordEntry
	 * 2.Save it to the local hashmap
	 * 3.save it to the local arraylist
	 */
	public WordEntry recordWord(String word) {
		WordEntry entry = findEntry(word);
		this.wordCount++;

		if(entry == null) {
			entry = new WordEntry(word);
			entry.INCR();
			this.map.put(word, entry);	//save to hashmap
			this.entries.add(entry);	//save to arraylist
		}
		else {
			entry.INCR();
		}
		return entry;
	}

	//finds and returns the required wordEntry
	public WordEntry findEntry(String word) {
		return map.get(word);
	}

	/**********
	 * COMPLICATED FUNCTIONS
	 **********/
	/* 1.sort tables
	 * 2.calculate probability
	 * 3.calculate cumuProb
	 */
	public void processEntries() {
		QuickSort1.sort(this.entries);
		calcProbs();
		
		for(WordEntry entry: this.entries) {
			if(entry.subTable != null) {
				entry.subTable.processEntries();
			}
		}
	}
	
		//recursive probabilities
		private void calcProbs() {
			double cumuProb = 0;

			for(WordEntry entry: this.entries) {
				cumuProb += entry.calcProbs(this.wordCount);
				entry.setCumuProb(cumuProb);
			}
		}
		
	/* 1.loop through top #
	 * 2.loop through their subtables
	 * 3.display the stats
	 * */
	public void rankWords(int numToDisplay) {
		int thisNumToDisplay = (numToDisplay > entries.size()) ? entries.size(): numToDisplay; //sets this so that there's no nullpoint exception
		WordEntry entry;
		
		for(int displayed = 0; displayed < thisNumToDisplay; displayed++) {
			entry = this.entries.get(displayed);
			
			for(int indents = 0; indents < this.depth; indents++) {
				System.out.print("   ");
			}
			
			System.out.println(entry.toString());
			
			if(entry.subTable != null) {
				entry.subTable.rankWords(numToDisplay);
			}
		}
	}

	/* 1.get a random number
	 * 2.loop through list to find first cumuProb that is less than random number
	 * */
	private WordEntry randEntry() {
		double randProb = numGen.nextDouble();
		int wordIndex = 0;
		WordEntry entry = entries.get(wordIndex);
		
		while(true) {
			if(entry.checkCumuProb(randProb)) {
				return entry;
			}

			if(wordIndex < entries.size()) {
				entry = entries.get(wordIndex);
				wordIndex++;
			}
			else {
				return entry;
			}
		}
	}

	/* 1.calculate random number
	 * 2.choose top level word
	 * 3.choose next words
	 * 4.
	 */
	public void displayStory(int storyLength) {
		LinkedList<WordEntry> list = new LinkedList<WordEntry>();

		WordEntry entry = this.randEntry();
		System.out.print(entry.word + " ");
		
		for(int initListSize = 0; initListSize < Run.depth; initListSize++) {
			list.add(entry);
			entry = entry.subTable.randEntry();
		}
		
		for(int gramsDisplayed = 0; gramsDisplayed < storyLength; gramsDisplayed++) {
			list.removeFirst();
			entry = map.get(entry.word).subTable.randEntry();
			list.add(entry);
			
			if(gramsDisplayed > 0 && gramsDisplayed % 20 == 0) {
				System.out.println("\n");
			}
			
			System.out.print(entry.word + " ");
		}
	}
}

