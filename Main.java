import java.util.*;

//-----------------------------------------------------
//Title: Main Class
//Author: Ayda Nil Özyürek
//Description:   In this class, we were asked to perform search, 
//               prefix counting, and reverse finding using a trie data structure.
//------------------------------------------------
public class Main {

	static String[] inputStrings;

	// Each node contains references for its child nodes, which indicate if it
	// represent the end of a phrase and record how often one word has been entered.
	static class TrieNode {
		Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
		boolean isEndOfWord;
		int count = 0;

	}

	static class Trie {
		TrieNode root = new TrieNode();

		// This implementation makes it possible to insert words into the trie and
		// provides a structure for efficient retrieval and manipulation of the string
		// of data.
		public void insert(String word) {
			TrieNode current = root;

			for (char ch : word.toCharArray()) {
				current.children.putIfAbsent(ch, new TrieNode());
				current = current.children.get(ch);
				current.count++;
			}
		}

		// This method displays true or false whether the word entered by the user
		// exists in the trie.
		public boolean search(String arg) {
			TrieNode current = root;

			for (char ch : arg.toCharArray()) {
				if (!current.children.containsKey(ch)) {
					return false;
				}
				current = current.children.get(ch);
			}

			return true;
		}

		// This method counts the number of times the specified word appears as the
		// prefix of words in the input. Each input word's count is displayed.
		public int countPrefix(String prefix) {
			TrieNode current = root;
			for (char ch : prefix.toCharArray()) {
				if (!current.children.containsKey(ch)) {
					return 0;
				}
				current = current.children.get(ch);
			}
			return current.count - 1;
		}

		// This method searches all the words in the input that end with a suffix the
		// user enters. The words are then sorted and printed in lexicographic order.
		public void reverseFind(String suffix) {
			Map<String, Integer> countMap = new HashMap<>();
			List<String> result = new ArrayList<>();

			for (String word : inputStrings) {
				if (word.endsWith(suffix)) {
					countMap.put(word, countMap.getOrDefault(word, 0) + 1);
				}
			}

			for (String word : inputStrings) {
				if (countMap.containsKey(word)) {
					for (int i = 0; i < countMap.get(word); i++) {
						result.add(word);
					}
					countMap.remove(word);
				}
			}

			Collections.sort(result);

			if (result.isEmpty()) {
				System.out.println("No result");
			} else {
				for (String word : result) {
					System.out.println(word);
				}
			}
		}
	}

	public static void main(String[] args) {

		// The Main Method creates an trie and inserts strings, provides a menu driven
		// interface for the user to perform searches, counts prefix or finds reverse
		// operations in stored string using the trie.
		Trie trie = new Trie();
		Scanner scanner = new Scanner(System.in);

		int numStrings = scanner.nextInt();
		scanner.nextLine();

		inputStrings = new String[numStrings];

		for (int i = 0; i < numStrings; i++) {
			inputStrings[i] = scanner.nextLine();
			trie.insert(inputStrings[i]);
		}

		System.out.println("Choose the function you want to use:");
		System.out.println("1) Search");
		System.out.println("2) Count Prefix");
		System.out.println("3) Find Reverse");

		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			String searchString = scanner.nextLine();
			boolean found = trie.search(searchString);
			System.out.println(found ? "True" : "False");
			break;
		case 2:
			for (String inputString : inputStrings) {
				int count = trie.countPrefix(inputString);
				System.out.print(count + " ");
			}
			System.out.println();
			break;
		case 3:
			String suffix = scanner.next();
			trie.reverseFind(suffix);
			break;
		}
	}

}
