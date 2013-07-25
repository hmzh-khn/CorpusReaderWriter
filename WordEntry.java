
public class WordEntry implements Comparable<WordEntry> {

	public String word;					//word name
	public WordTable subTable;  		//words after it, for nGRAMS
	private int freq = 0;   			//how many times it is in the text
	private double prob;				//probability in text
	private double cumuProb;			//used to calculate random word
	
	//constructor sets word field to given word
	WordEntry(String word) {
		this.word = word;
	}
	
	/*compares current WordEntry to other WordEntry
	//+#: greater value
	//0: equal value
	-#: less value
	*/
	public int compareTo(WordEntry comparedEntry) {
		return (this.freq - comparedEntry.freq);
	}

	//return data as a string to display
	public String toString() {
		String summary = this.word + ": " + this.freq + " times, " + String.format("%.4f", this.prob) + ", " + String.format("%.4f", this.cumuProb);
		return summary;
	}

	//If this has a cumuProb greater than the randEntry, then this will not be the word
	//otherwise, its the word we want
	public boolean checkCumuProb(double currentProb) {
		return (currentProb < this.cumuProb)? true:false;
	}
	
	//allows for incrementation of frequency
	public void INCR() {
		this.freq++;
	}

	//gets the wordCount and current cumuProb, then calculates prob and cumuProb and returns new cumuProb
	public double calcProbs(int wordCount) {
		this.prob = this.freq/(double) wordCount;
		return this.prob;
	}
	
	/********
	 * GETTERS AND SETTERS
	 *******/
	public void setCumuProb(double cumuProb) {
		this.cumuProb = cumuProb;
	}
	
	public int getFreq() {
		return this.freq;
	}

}
