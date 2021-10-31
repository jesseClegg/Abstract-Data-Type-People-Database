/**
 * Representing a generic binary tree extending comparable interface to establish precedence of node keys
 * @author Jesse Clegg
 * @version 1.0
 */
import java.util.ArrayList;

public class Bst<T extends Comparable<T>> {
	/*
	 * Generic type node
	 */
	private Node<T> root;

	Bst() {
		this.root = null;
	}

	/**
	 * 
	 * @param node the root to set
	 */
	Bst(Node<T> node) {
		this.root = node;
	}

	/**
	 * @return the root
	 */
	public Node<T> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Node<T> root) {
		this.root = root;
	}

	/**
	 * Recursively traverse tree inorder
	 * 
	 * @param root     this node will be root of the tree or subtree to be traversed
	 * @param contains the traversal, passed along due to recursion
	 * @return list of nodes in same order as inorder traversal
	 */
	public ArrayList<Node> inorder(Node<T> root, ArrayList<Node> list) {
		if (root == null) {
			return list;
		}
		inorder(root.getLeftChild(), list);
		list.add(root);
		inorder(root.getRightChild(), list);
		return list;
	}

	/**
	 * Iterative binary search for any node
	 * 
	 * @param nodeToFind node to be located if present
	 * @return node if found, else null
	 */
	public Node<T> binarySearch(Node<T> nodeToFind) {
		Node<T> current = this.root;
		int compared;
		if (current != null) {
			compared = current.getKey().compareTo((T) nodeToFind.getKey());
		} else {
			return null;
		}
		while (current != null) {
			compared = current.getKey().compareTo((T) nodeToFind.getKey());
			if (compared < 0) {
				current = current.getRightChild();
			} else if (compared > 0) {
				current = current.getLeftChild();
			} else {
				return current;
			}
		}
		return null;
	}

	/**
	 * add node iteratively to preserve bst properties
	 * 
	 * @param nodeToAdd node to be added
	 */
	public void insert(Node<T> nodeToAdd) {
		if (this.root == null) {
			this.root = nodeToAdd;
			return;
		}
		Node<T> currentNode;
		Node<T> prevNode;
		currentNode = root;
		prevNode = null;
		int compared;
		while (currentNode != null) {
			compared = nodeToAdd.getKey().compareTo(currentNode.getKey());// -1=less, 0=same node, 1=greater
			if (compared > 0) {//go right
				prevNode = currentNode;
				currentNode = currentNode.getRightChild();
			} else if (compared < 0) {//go left
				prevNode = currentNode;
				currentNode = currentNode.getLeftChild();
			}
		}
		compared = nodeToAdd.getKey().compareTo(prevNode.getKey());
		if (compared > 0) {
			prevNode.setRightChild(nodeToAdd);
		} else {
			prevNode.setLeftChild(nodeToAdd);
		}
	}

	/**
	 * Iteratively search tree for node, delete using common algorithms special case
	 * for root and for node with 2 children replace node to delete with the values
	 * of inorder successor, delete that successor
	 * 
	 * @param nodeToDelete node deleted if present
	 * @return true on success, else false
	 */
	public boolean delete(Node<T> nodeToDelete) {
		Node<T> current = this.root;
		Node<T> parent = new Node<T>();
		parent = null;
		int compared = current.getKey().compareTo((T) nodeToDelete.getKey());// -1=less, 0=same node, 1=greater
		while (current != null) {
			compared = current.getKey().compareTo((T) nodeToDelete.getKey());
			if (compared < 0) {// if negative, means current<nodeToDelete, so go right child
				parent = current;
				current = current.getRightChild();
			} else if (compared > 0) {// if positive, current>nodeToDelete so go left child
				parent = current;
				current = current.getLeftChild();
			} else if (compared == 0) {// current is a match for 'nodeToDelete'
				int numChildren = current.getNumChildren();
				if (parent == null) {// perform protocols to root delete
					if (numChildren == 0) {
						this.setRoot(null);
					} else if (numChildren == 1) {
						if (current.getLeftChild() == null) {
							this.setRoot(current.getRightChild());
						}
						if (current.getRightChild() == null) {
							this.setRoot(current.getLeftChild());
						}
					}
					return true;
				}
				if (numChildren == 0 && parent != null) {
					int compareParentToCurrent = parent.getKey().compareTo(current.getKey());
					if (compareParentToCurrent < 0) {// go right child
						current = null;
						parent.setRightChild(null);
						return true;
					} else if (compareParentToCurrent > 0) {// go left child
						current = null;
						parent.setLeftChild(null);
						return true;
					}
				} else if (numChildren == 1 && parent != null) {
					int compareParentToCurrent = parent.getKey().compareTo(current.getKey());
					Node<T> child = new Node<T>();
					if (current.getLeftChild() != null) {// find which child contains the subtree
						child = current.getLeftChild();
					} else if (current.getRightChild() != null) {
						child = current.getRightChild();
					}
					if (compareParentToCurrent < 0) {// go right
						parent.setRightChild(child);
						current = null;
						return true;
					} else if (compareParentToCurrent > 0) {// go left
						parent.setLeftChild(child);
						current = null;
						return true;
					}
				} else if (numChildren == 2) {// delete by moving inorder successor into current nodes position, then
												// delete successor
					ArrayList<Node> inorderTraversal = new ArrayList<Node>();
					inorderTraversal = this.inorder(this.root, inorderTraversal);
					Node<T> successor = new <T>Node();
					int compareSuccessor;
					for (int i = 0; i < inorderTraversal.size(); i++) {
						compareSuccessor = current.getKey().compareTo((T) inorderTraversal.get(i).getKey());// locate
																											// successor
						if (compareSuccessor == 0) {
							successor = inorderTraversal.get(i + 1);
						}
					}
					Node<T> copySuccessor = new <T>Node();
					copySuccessor = successor;
					this.delete(successor);// delete before copying to avoid 2 copies in tree at once
					Node<T> transfer = new <T>Node();
					transfer.setKey(copySuccessor.getKey());
					return true;
				}
			}
		}
		return false;
	}
}
