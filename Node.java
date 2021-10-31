/**
 * Generic node class for use in generic binary search tree
 * Extends Comparable to allow key objects to be ordered in the tree
 * @author Jesse Clegg
 * @version 1.0
 */
public class Node<T extends Comparable<T>> {
	
	private T key;
	
	private Node<T> leftChild;
	
	private Node<T> rightChild;

	public Node() {
		this.key = null;
		this.leftChild = null;
		this.rightChild = null;
	}

	/**
	 * 
	 * @param person key to be stored in this node
	 */
	public Node(T person) {
		this.key = person;
		this.leftChild = null;
		this.rightChild = null;
	}

	/**
	 * @param key
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(T key, Node<T> leftChild, Node<T> rightChild) {
		this.key = key;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * @return number of child nodes
	 */
	public int getNumChildren() {
		int num;
		if (this.leftChild == null && this.rightChild == null) {
			num = 0;
			return num;
		} else if (this.leftChild != null && this.rightChild != null) {
			num = 2;
			return num;
		} else {
			num = 1;
			return num;
		}
	}

	/**
	 * @return the key
	 */
	public T getKey() {
		return key;
	}

	/**
	 * @param key the key to be set
	 */
	public void setKey(T key) {
		this.key = key;
	}

	/**
	 * @return the leftChild
	 */
	public Node<T> getLeftChild() {
		return leftChild;
	}

	/**
	 * @param leftChild the leftChild to be set
	 */
	public void setLeftChild(Node<T> leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * @return the rightChild
	 */
	public Node<T> getRightChild() {
		return rightChild;
	}

	/**
	 * @param rightChild the rightChild to be set
	 */
	public void setRightChild(Node<T> rightChild) {
		this.rightChild = rightChild;
	}
}
