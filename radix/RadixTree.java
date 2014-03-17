package radix;

import java.util.TreeSet;


/**
 * The class for the radix tree.
 * 
 * @author Macavei Andrei-Gabriel
 *
 */
public class RadixTree {
	
	/**
	 * The root node for this tree.
	 */
	public RadixTreeNode root;
	
	/**
	 * The default constructor.
	 */
	public RadixTree() {
		this.root = new RadixTreeNode("");
	}
	
	/**
	 * Gets this tree's root node
	 * 
	 * @return  the root node of this tree
	 */
	public RadixTreeNode getRoot() {
		return this.root;
	}
	
	/**
	 * Finds the length of the longest prefix for two strings
	 * 
	 * @param a  first string
	 * @param b  second string
	 * @return  the length of the longest prefix of <code>a</code>
	 * and <code>b</code>
	 */
	public int longestPrefix(String a, String b) {
		int len = 0;
		for(int i=0; i < Math.min(a.length(), b.length()); i++) {
			if(a.charAt(i) != b.charAt(i))
				break;
			len++;
		}
		return len;
	}
	
	/**
	 * Indexes the given prefix into this tree.
	 * 
	 * @param prefix the prefix to be indexed
	 * @param pos the position where this prefix was found
	 * @return the position associated with the given prefix
	 */
	public int indexPrefix(String prefix, int pos) {
		return indexPrefix(prefix, pos, root);
	}
	
	/**
	 * Indexes the given prefix into this tree/subtree
	 * 
	 * @param prefix  the prefix to be indexed
	 * @param pos  the position where this prefix was found
	 * @param node  the node to start searching from
	 * @return the old position associated with the given prefix
	 */
	public int indexPrefix(String prefix, int pos, RadixTreeNode node) {
		int retPos = -1;
		int longestPrefix = longestPrefix(prefix, node.getPrefix());
		
		if(longestPrefix == node.getPrefix().length() && longestPrefix == prefix.length()) {
			// Avem un nod cu acelasi prefix (duplicat)
			
			if(!node.isTerminal())
				// Retin prima pozitie a cuvantului din text
				retPos = pos;
			node.setPosition(pos);
			node.setTerminal(true);
			
		} else if(longestPrefix == 0
					|| (longestPrefix < prefix.length() && longestPrefix >= node.getPrefix().length()))
		{
			// Prefixul cautat este mai mare decat prefixul acestui nod, atunci cautam
			// un copil care sa aiba in continuare acelasi prefix cu cel cautat,
			// daca nu adaugam un nod nou ca si copil al acestui nod
			String remainedPrefix = prefix.substring(longestPrefix);
			boolean found = false;
			
			for(RadixTreeNode child: node) {
				if(child.getPrefix().charAt(0) == remainedPrefix.charAt(0)) {
					found = true;
					retPos = indexPrefix(remainedPrefix, pos, child);
					break;
				}
			}
			if(!found) {
				// Nu am gasit niciun copil care sa aiba vreun prefix cu cel cautat
				// atunci adaugam un copil nou
				RadixTreeNode newNode = new RadixTreeNode(remainedPrefix, pos);
				node.getChildNodes().add(newNode);
			}
		} else if(longestPrefix < node.getPrefix().length()) {
			// Prefixul cautat si cel al nodului curent au in comun o parte din prefix
			// atunci il impartim in doua noduri
			String remainedPrefix = node.getPrefix().substring(longestPrefix);
			RadixTreeNode newNode = new RadixTreeNode(remainedPrefix, node.getPosition());
			newNode.setTerminal(node.isTerminal());
			newNode.getChildNodes().addAll(node.getChildNodes());
			
			node.setPrefix(node.getPrefix().substring(0, longestPrefix));
			node.getChildNodes().clear();
			node.getChildNodes().add(newNode);
			
			if(longestPrefix == prefix.length()) {
				// Daca cel mai lung prefix este egal cu prefixul cautat atunci
				// acest nod este terminal (are pozitie asociata)
				
				node.getPosition().clear();
				node.setPosition(pos);
				node.setTerminal(true);
			} else {
				// Exista un sufix ramas si atunci adaugam un nou copil
				String remainedSufix = prefix.substring(longestPrefix);
				RadixTreeNode sufixNode = new RadixTreeNode(remainedSufix, pos);
				node.getChildNodes().add(sufixNode);
				node.setTerminal(false);
			}
		} else {
			// Prefixul nodului curent se gaseste in totalitate in prefixul cautat
			// si atunci adaugam un nod
			String remainedSufix = prefix.substring(longestPrefix);
			RadixTreeNode newNode = new RadixTreeNode(remainedSufix, pos);
			node.getChildNodes().add(newNode);
		}
		
		return retPos;
	}
	
	/**
	 * Retrieve the position indexes of the given prefix, from the tree/subtree
	 * with the root at node
	 * 
	 * @param prefix  the prefix
	 * @param node  the node to start searching from
	 * @return	the positions associated with the given prefix
	 */
	public TreeSet<Integer> getAllPosition(String prefix, RadixTreeNode node) {
		
		TreeSet<Integer> foundPos = new TreeSet<Integer>();
		
		for(RadixTreeNode child : node) {
			int longestPrefix = longestPrefix(prefix, child.getPrefix());
			
			if(longestPrefix == child.getPrefix().length() && longestPrefix == prefix.length()) {
				// Am gasit nod cu acelasi prefix cu cel cautat
				if(child.getChildNodes().size() == 0) {
					// Nodul gasit este terminal atunci apare o singura data in text
					foundPos.addAll(child.getPosition());
					break;
				}
				else{
					// Adaugam si pozitia curenta daca nodul are pozitie
					if (child.isTerminal()) {
						foundPos.addAll(child.getPosition());
					}
					// Cuvantul cautat este un prefix al altor cuvinte
					for (RadixTreeNode radixChildNode : child) {
						// Pentru fiecare copil din subarbore adaugam pozitiile in lista
						String childNodePrefix = radixChildNode.getPrefix();
						foundPos.addAll(getAllPosition(childNodePrefix, child));
					}
					break;
				}
			}
			else if(longestPrefix > 0 && longestPrefix < prefix.length() && 
					longestPrefix == child.getPrefix().length()) {
				// Nodul este de tip intern 
				String remainingPrefix = prefix.substring(longestPrefix);
				foundPos.addAll(getAllPosition(remainingPrefix, child));
				break;
			}
			else if (longestPrefix > 0 && longestPrefix == prefix.length()){
				// Prefixul cautat nu este indexat dar face parte din prefixul unui nod
				if (child.isTerminal())
					foundPos.addAll(child.getPosition());
				for (RadixTreeNode radixChildNode : child) {
					// Adaugam si pozitiile copiilor nodului gasit
					String childNodePrefix = radixChildNode.getPrefix();
					foundPos.addAll(getAllPosition(childNodePrefix, child));
				}
				break;
			}
		}
		// In cazul in care copii nodului curent sunt zero, acesta nu a fost
		// adaugat in recursia precedenta si trebuie facut aici
		if(node.getChildNodes().size() == 0 && prefix.equals(node.getPrefix())) {
			foundPos.addAll(node.getPosition());			
		}
		
		return foundPos;
	}
	
	
}
