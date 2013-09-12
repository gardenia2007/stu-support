package edu.hit.irlab.util.ds.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Implementation for Radix tree {@link RadixTree}
 * 
 * @author Tahseen Ur Rehman (tahseen.ur.rehman {at.spam.me.not} gmail.com)
 * @author Javid Jamae
 * @author Dennis Heidsiek
 * @author Fandy Wang
 */
public class RadixTreeImpl<T> implements RadixTree<T> {

	protected RadixTreeNode<T> root;

	protected long size;

	/**
	 * Create a Radix Tree with only the default node root.
	 */
	public RadixTreeImpl() {
		root = new RadixTreeNode<T>();
		root.setKey("");
		size = 0;
	}

	public T find(String key) {
		Visitor<T, T> visitor = new VisitorImpl<T, T>() {

			public void visit(String key, RadixTreeNode<T> parent,
			    RadixTreeNode<T> node) {
				if (node.isReal())
					result = node.getValue();
			}
		};

		visit(key, visitor);

		return visitor.getResult();
	}

	public boolean delete(String key) {
		Visitor<T, Boolean> visitor = new VisitorImpl<T, Boolean>(Boolean.FALSE) {
			public void visit(String key, RadixTreeNode<T> parent,
			    RadixTreeNode<T> node) {
				result = node.isReal();

				// if it is a real node
				if (result) {
					// If there no children of the node we need to
					// delete it from the its parent children list
					if (node.getChildren().size() == 0) {
						Iterator<RadixTreeNode<T>> it = parent.getChildren().iterator();
						while (it.hasNext()) {
							if (it.next().getKey().equals(node.getKey())) {
								it.remove();
								break;
							}
						}

						// if parent is not real node and has only one child
						// then they need to be merged.
						if (parent.getChildren().size() == 1 && parent.isReal() == false) {
							mergeNodes(parent, parent.getChildren().get(0));
						}
					} else if (node.getChildren().size() == 1) {
						// we need to merge the only child of this node with
						// itself
						mergeNodes(node, node.getChildren().get(0));
					} else { // we just need to mark the node as non real.
						node.setReal(false);
					}
				}
			}

			/**
			 * Merge a child into its parent node. Operation only valid if it is only
			 * child of the parent node and parent node is not a real node.
			 * 
			 * @param parent
			 *          The parent Node
			 * @param child
			 *          The child Node
			 */
			private void mergeNodes(RadixTreeNode<T> parent, RadixTreeNode<T> child) {
				parent.setKey(parent.getKey() + child.getKey());
				parent.setReal(child.isReal());
				parent.setValue(child.getValue());
				parent.setChildren(child.getChildren());
			}

		};

		visit(key, visitor);

		if (visitor.getResult()) {
			size--;
		}
		return visitor.getResult().booleanValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ds.tree.RadixTree#insert(java.lang.String, java.lang.Object)
	 */
	public void insert(String key, T value) throws DuplicateKeyException {
		try {
		  if( key.length() == 0 ) return ;
			insert(key, root, value);
		} catch (DuplicateKeyException e) {
			// re-throw the exception with 'key' in the message
			throw new DuplicateKeyException("Duplicate key: '" + key + "'");
		}
		size++;
	}

	/**
	 * Recursively insert the key in the radix tree.
	 * 
	 * @param key
	 *          The key to be inserted
	 * @param node
	 *          The current node
	 * @param value
	 *          The value associated with the key
	 * @throws DuplicateKeyException
	 *           If the key already exists in the database.
	 */
	private void insert(String key, RadixTreeNode<T> node, T value)
	    throws DuplicateKeyException {
		int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);

		// we are either at the root node
		// or we need to go down the tree
		// TODO: numberOfMatchingCharacters == 0 ? Fandy Wang
		if (node.getKey().equals("") == true
		    || numberOfMatchingCharacters == 0
		    || (numberOfMatchingCharacters < key.length() && numberOfMatchingCharacters >= node
		        .getKey().length())) {
			boolean flag = false;
			String newText = key.substring(numberOfMatchingCharacters, key.length());
			
			RadixTreeNode<T> child = bSearchChild(node.getChildren(), newText);
      if( child != null ) {
        insert(newText, child, value);
        flag = true;
      }

			// just add the node as the child of the current node
			if (flag == false) {
				RadixTreeNode<T> n = new RadixTreeNode<T>();
				n.setKey(newText);
				n.setReal(true);
				n.setValue(value);

        node.getChildren().add(upper_bound(node.getChildren(), n.getKey()), n);
			}
		}
		// there is a exact match just make the current node as data node
		else if (numberOfMatchingCharacters == key.length()
		    && numberOfMatchingCharacters == node.getKey().length()) {
			if (node.isReal() == true) {
				throw new DuplicateKeyException("Duplicate key");
			}

			node.setReal(true);
			node.setValue(value);
		}
		// This node need to be split as the key to be inserted
		// is a prefix of the current node key
		else if (numberOfMatchingCharacters > 0
		    && numberOfMatchingCharacters < node.getKey().length()) {
			RadixTreeNode<T> n1 = new RadixTreeNode<T>();
			n1.setKey(node.getKey().substring(numberOfMatchingCharacters,
			    node.getKey().length()));
			n1.setReal(node.isReal());
			n1.setValue(node.getValue());
			n1.setChildren(node.getChildren());

			node.setKey(key.substring(0, numberOfMatchingCharacters));
			node.setReal(false);
			node.setChildren(new ArrayList<RadixTreeNode<T>>());
			//node.getChildren().add(n1);
      node.getChildren().add(upper_bound(node.getChildren(), n1.getKey()), n1);

			if (numberOfMatchingCharacters < key.length()) {
				RadixTreeNode<T> n2 = new RadixTreeNode<T>();
				n2.setKey(key.substring(numberOfMatchingCharacters, key.length()));
				n2.setReal(true);
				n2.setValue(value);

				//node.getChildren().add(n2);
        node.getChildren().add(upper_bound(node.getChildren(), n2.getKey()), n2);
			} else {
				node.setValue(value);
				node.setReal(true);
			}
		}
		// TODO: comment added by Fandy Wang
		// // this key need to be added as the child of the current node
		// else {
		// RadixTreeNode<T> n = new RadixTreeNode<T>();
		// n.setKey(node.getKey().substring(numberOfMatchingCharacters,
		// node.getKey().length()));
		// n.setChildren(node.getChildren());
		// n.setReal(node.isReal());
		// n.setValue(node.getValue());
		//
		// node.setKey(key);
		// node.setReal(true);
		// node.setValue(value);
		// // TODO:
		// node.setChildren(new ArrayList<RadixTreeNode<T>>()); // add by Fandy Wang
		//			
		// node.getChildren().add(n);
		// }
	}

	public ArrayList<T> searchPrefix(String prefix, int recordLimit) {
		ArrayList<T> keys = new ArrayList<T>();
		RadixTreeNode<T> node = searchPrefix(prefix, root, new int[1]);

		if (node != null) {
			if (node.isReal()) {
				keys.add(node.getValue());
			}
			if (recordLimit > 1)
				getNodes(node, keys, recordLimit);
		}

		return keys;
	}

	private void getNodes(RadixTreeNode<T> parent, ArrayList<T> keys, int limit) {
		Queue<RadixTreeNode<T>> queue = new LinkedList<RadixTreeNode<T>>();
		queue.addAll(parent.getChildren());

		while (!queue.isEmpty()) {
			RadixTreeNode<T> node = queue.remove();
			if (node.isReal() == true) {
				keys.add(node.getValue());
			}
			if (keys.size() >= limit) {
				return;
			}
			queue.addAll(node.getChildren());
		}
	}

	private RadixTreeNode<T> searchPrefix(String key, RadixTreeNode<T> node,
	    int[] rest) {
		RadixTreeNode<T> result = null;
		int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);

		if (numberOfMatchingCharacters == key.length()
		    && numberOfMatchingCharacters <= node.getKey().length()) {
			result = node;
			rest[0] = numberOfMatchingCharacters;
		} else if (node.getKey().equals("") == true
		    || (numberOfMatchingCharacters < key.length() && numberOfMatchingCharacters >= node
		        .getKey().length())) {
			String newText = key.substring(numberOfMatchingCharacters, key.length());
			
			RadixTreeNode<T> child = bSearchChild(node.getChildren(), newText);
			if( child != null ) {
        result = searchPrefix(newText, child, rest);
			}
		}

		return result;
	}

	public Map<String, T> searchPrefixNodes(String prefix) {
		// Index rest = new Index(-1);
		int[] rest = new int[1];
		RadixTreeNode<T> node = searchPrefix(prefix, root, rest);
		Map<String, T> keys = new HashMap<String, T>();

		if (node != null) {
			StringBuilder k = new StringBuilder(prefix);
			if (rest[0] >= 0)
				k.append(node.getKey().substring(rest[0]));
			if (node.isReal())
				keys.put(k.toString(), node.getValue());
			bfsNodes(k.toString(), node, keys);
		}

		return keys;
	}

	/**
	 * Bfs nodes.
	 * 
	 * @param prefix
	 *          the prefix
	 * @param parent
	 *          the parent
	 * @param keys
	 *          the keys
	 */
	private void bfsNodes(String prefix, RadixTreeNode<T> parent,
	    Map<String, T> keys) {
		for (RadixTreeNode<T> children : parent.getChildren()) {
			StringBuilder k = new StringBuilder();
			k.append(prefix).append(children.getKey());

			if (children.isReal())
				keys.put(k.toString(), children.getValue());

			bfsNodes(k.toString(), children, keys);
		}
	}

	public String searchLongestMatch(String key) {
		StringBuilder prefix = new StringBuilder();
		String[] result = new String[1];
		searchLongestMatch(key, root, prefix, result);
		// System.out.println("Result:" + prefix + ":" + result[0]);
		return result[0];
	}

	private void searchLongestMatch(String key, RadixTreeNode<T> node,
	    StringBuilder prefix, String[] curResult) {
		int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);

		if (numberOfMatchingCharacters >= node.getKey().length()) {
			prefix.append(node.getKey());
			if (node.isReal())
				curResult[0] = prefix.toString();

			if (numberOfMatchingCharacters < key.length()) {
				String newText = key
				    .substring(numberOfMatchingCharacters, key.length());
				
				RadixTreeNode<T> child = bSearchChild(node.getChildren(), newText);
	      if( child != null ) {
          searchLongestMatch(newText, child, prefix, curResult);
	      }
			}
		}
	}

	public boolean contains(String key) {
		Visitor<T, Boolean> visitor = new VisitorImpl<T, Boolean>(Boolean.FALSE) {
			public void visit(String key, RadixTreeNode<T> parent,
			    RadixTreeNode<T> node) {
				result = node.isReal();
			}
		};

		visit(key, visitor);

		return visitor.getResult().booleanValue();
	}

	/**
	 * visit the node those key matches the given key
	 * 
	 * @param key
	 *          The key that need to be visited
	 * @param visitor
	 *          The visitor object
	 */
	public <R> void visit(String key, Visitor<T, R> visitor) {
		if (root != null) {
			visit(key, visitor, null, root);
		}
	}

	/**
	 * recursively visit the tree based on the supplied "key". calls the Visitor
	 * for the node those key matches the given prefix
	 * 
	 * @param prefix
	 *          The key o prefix to search in the tree
	 * @param visitor
	 *          The Visitor that will be called if a node with "key" as its key is
	 *          found
	 * @param node
	 *          The Node from where onward to search
	 */
	private <R> void visit(String prefix, Visitor<T, R> visitor,
	    RadixTreeNode<T> parent, RadixTreeNode<T> node) {
		int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(prefix);

		// if the node key and prefix match, we found a match!
		if (numberOfMatchingCharacters == prefix.length()
		    && numberOfMatchingCharacters == node.getKey().length()) {
			visitor.visit(prefix, parent, node);
		} else if (node.getKey().equals("") == true // either we are at the
		    // root
		    || (numberOfMatchingCharacters < prefix.length() && numberOfMatchingCharacters >= node
		        .getKey().length())) { // OR we need to
			// traverse the children
			String newText = prefix.substring(numberOfMatchingCharacters, prefix
			    .length());
			
			RadixTreeNode<T> child = bSearchChild(node.getChildren(), newText);
      if( child != null ) {
        visit(newText, visitor, node, child);        
      }
		}
	}

	public long getSize() {
		return size;
	}

	public void print() {
		print(this.root, 0);
	}

	private void print(RadixTreeNode<T> curNode, int depth) {
		if (curNode == null)
			return;

		for (int i = 0; i < depth; i++) {
			System.out.print("----");
		}

//		for( int i=0; i < curNode.getChildren().size()-1; i++) {
//		  if( curNode.getChildren().get(i).getKey().charAt(0) >= 
//		    curNode.getChildren().get(i+1).getKey().charAt(0) )
//		    System.err.println(curNode.getKey() + ", " + curNode.getValue());
//
//		}
		
		System.out.println(curNode.getKey() + ", " + curNode.getValue());
		for (RadixTreeNode<T> node : curNode.getChildren()) {
			print(node, depth + 1);
		}
	}
	
//>=
  private int upper_bound(List<RadixTreeNode<T>> children,
      String newText) {
    if (children == null || children.size() == 0)
      return 0;

    if (newText.charAt(0) <= children.get(0).getKey().charAt(0) )
      return 0;
    if (newText.charAt(0) > children.get(children.size()-1).getKey().charAt(0))
      return children.size();
    
    int low = 0;
    int high = children.size() - 1;
    int mid;
    while (low <= high) {
      mid = (low + high) >> 1;
      //System.out.println(children.get(mid).getKey() + "TTTT" +newText);

      int cmp = children.get(mid).getKey().charAt(0) - newText.charAt(0);
      if (cmp < 0) {
        if (mid < high && children.get(mid+1).getKey().charAt(0) >= newText.charAt(0))
          return mid + 1;
        low = mid + 1;
      } else high = mid;
    }

    return low;
  }
  
  // ==
  private RadixTreeNode<T> bSearchChild(List<RadixTreeNode<T>> children,
      String newText) {
    if (children == null || children.size() == 0)
      return null;

    int low = 0;
    int high = children.size() - 1;
    int mid;

    while (low <= high) {
      mid = (low + high) >> 1;
//      System.out.println(children.get(mid).getKey() + "ttt" + newText);
      int cmp = children.get(mid).getKey().charAt(0) - newText.charAt(0);
      if( cmp == 0 ) 
        return children.get(mid);
      else if (cmp < 0) 
        low = mid + 1;
      else high = mid-1;
    }

    return null;
  }

}