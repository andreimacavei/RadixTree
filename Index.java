import java.util.TreeSet;

import radix.RadixTree;

/**
 * This execution entry point class handles the parsing, indexing and lookup of words in a text.
 */
public class Index {

	/**
	 * Execution entry point.
	 *
	 * @param args two strings representing the name of the file that contains the text to index,
	 * and the name of the file containing words to lookup in the text.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java -cp <classpath> Index <text file> <word file>");
			System.exit(1);
		}
		
		String word;

		FileParser textFile = new FileParser(args[0]);
		textFile.open();
		
		int index = 0;
		RadixTree tree = new RadixTree();
		
		while ((word = textFile.getNextWord()) != null) {
			tree.indexPrefix(word, index);
			index++;
		}
		textFile.close();
		
		FileParser prefixFile = new FileParser(args[1]);
		prefixFile.open();
		while ((word = prefixFile.getNextWord()) != null) {
			TreeSet<Integer> pos = tree.getAllPosition(word, tree.getRoot());
			System.out.print("" +pos.size());
			for (Integer i : pos) {
				System.out.print(" " + i);
			}
			System.out.print("\n");
		}
		prefixFile.close();
	}
}
