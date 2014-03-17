package radix;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The class for a node in the radix tree
 * 
 * @author Macavei Andrei-Gabriel
 *
 */
public class RadixTreeNode implements Iterable<RadixTreeNode> {

	private String prefix;
	
	/**
	 * The indexes where this node's prefix appear. Note, these will be
	 * sorted by using {@link TreeSet}.
	 */
	private TreeSet<Integer> position = new TreeSet<Integer>();
	
	/**
	 * Whether or not this node's prefix is an indexed word and has
	 * position.
	 */
	private boolean isTerminal;
	
	/**
	 * The children for this node.
	 */
	private ArrayList<RadixTreeNode> childNodes;
	
	/**
	 * Constructs a node from the given prefix
	 * 
	 * @param prefix  the prefix
	 */
	public RadixTreeNode(String prefix) {
		this(prefix, -1);
		this.isTerminal = false;
	}
	
	/**
	 * Constructs a node from the given prefix and the position
	 * index.
	 * 
	 * @param prefix  the prefix
	 * @param position  the only position index inside text
	 */
	public RadixTreeNode(String prefix, int position) {
		this.prefix = prefix;
		if (position != -1) {
			this.position.add(position);
			this.isTerminal = true;
		}
	}
	
	/**
	 * Constructs a node from the given prefix and an ordered set
	 * of position indexes.
	 * 
	 * @param prefix  the prefix
	 * @param position  the set of position indexes in the text
	 */
	public RadixTreeNode(String prefix, TreeSet<Integer> position) {
		this.prefix = prefix;
		this.position.addAll(position);
		this.isTerminal = true;
	}
	
	/**
	 * Gets the position indexes atached to this prefix.
	 * 
	 * @return the set of position indexes
	 */
	public TreeSet<Integer> getPosition() {
		return this.position;
	}
	
	/**
	 * Sets one position index to this prefix
	 * 
	 * @param position the position index
	 */
	public void setPosition(int position) {
		this.position.add(position);
	}
	
	/**
	 * Sets a set of position indexes to this prefix
	 * 
	 * @param position the set of position indexes
	 */
	public void setPosition(TreeSet<Integer> position) {
		this.position.addAll(position);
	}
	
	/**
	 * Gets the prefix associated with this node
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return this.prefix;
	}
	
	/**
	 * Sets the prefix associated with this node
	 * 
	 * @param prefix  the prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Whether or not this node has a position attached to it.
	 * 
	 * @return whether or not this node has a position
	 */
	public boolean isTerminal() {
		return this.isTerminal;
	}
	
	/**
	 * Sets whether or not this node has a position attached to it
	 * 
	 * @param isTerminal  <code>true</code> if this node will havea 
	 * 										position,
	 * 					  <code>false</code> otherwise.
	 */
	public void setTerminal(boolean isTerminal) {
		this.isTerminal = isTerminal;
	}
	
	/**
	 * Get the child nodes of this node
	 * 
	 * @return the list of children
	 */
	public ArrayList<RadixTreeNode> getChildNodes() {
		if (childNodes == null)
			childNodes = new ArrayList<RadixTreeNode>();
		return childNodes;
	}
	
	@Override
	public Iterator<RadixTreeNode> iterator() {
		if (childNodes == null) {
			Iterator<RadixTreeNode> iterator = new Iterator<RadixTreeNode>() {
								
								@Override
								public boolean hasNext() {
									return false;
								}
								
								@Override
								public RadixTreeNode next() {
									return null;
								}
								
								@Override
								public void remove(){
								}			
					};
			return iterator;
		}
		return childNodes.iterator();
	}
	
}
