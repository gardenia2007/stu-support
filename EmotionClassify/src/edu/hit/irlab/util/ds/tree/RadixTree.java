package edu.hit.irlab.util.ds.tree;

import java.util.ArrayList;
import java.util.Map;

/**
 * This interface represent the operation of a radix tree. A radix tree,
 * Patricia trie/tree, or crit bit tree is a specialized set data structure
 * based on the trie that is used to store a set of strings. In contrast with a
 * regular trie, the edges of a Patricia trie are labelled with sequences of
 * characters rather than with single characters. These can be strings of
 * characters, bit strings such as integers or IP addresses, or generally
 * arbitrary sequences of objects in lexicographical order. Sometimes the names
 * radix tree and crit bit tree are only applied to trees storing integers and
 * Patricia trie is retained for more general inputs, but the structure works
 * the same way in all cases.
 * 
 * @author Tahseen Ur Rehman email: tahseen.ur.rehman {at.spam.me.not} gmail.com
 * @author Fandy Wang: ofandywang@gmail.com
 */
public interface RadixTree<T> {
	/**
	 * Insert a new string key and its value to the tree.
	 * 
	 * @param key
	 *            The string key of the object
	 * @param value
	 *            The value that need to be stored corresponding to the given
	 *            key.
	 * @throws DuplicateKeyException
	 */
	public void insert(String key, T value) throws DuplicateKeyException;

	/**
	 * Delete a key and its associated value from the tree.
	 * 
	 * @param key
	 *            The key of the node that need to be deleted
	 * @return
	 */
	public boolean delete(String key);

	/**
	 * Find a value based on its corresponding key.
	 * 
	 * @param key
	 *            The key for which to search the tree.
	 * @return The value corresponding to the key. null if it can not find the
	 *         key
	 */
	public T find(String key);

	/**
	 * Check if the tree contains any entry corresponding to the given key.
	 * 
	 * @param key
	 *            The key that needto be searched in the tree.
	 * @return retun true if the key is present in the tree otherwise false
	 */
	public boolean contains(String key);

	/**
	 * Search for all the keys that start with given prefix. limiting the
	 * results based on the supplied limit.
	 * 
	 * @param prefix
	 *            The prefix for which keys need to be search
	 * @param recordLimit
	 *            The limit for the results
	 * @return The list of values those key start with the given prefix
	 */
	public ArrayList<T> searchPrefix(String prefix, int recordLimit);

	/**
	 * Search for the nodes that start with given prefix.
	 * 
	 * @param prefix
	 *            The prefix for which keys need to be search
	 * @return The nodes of values those key start with the given prefix
	 */
	public Map<String, T> searchPrefixNodes(String prefix);
	
	/**
	 * Search for the longest common prefix and legal node string
	 * between key and tries.
	 * 
	 * @param key
	 * @return
	 */
	public String searchLongestMatch(String key);

	/**
	 * Return the size of the Radix tree
	 * 
	 * @return the size of the tree
	 */
	public long getSize();
	
	/**
	 * Display the RadixTree structure
	 * 
	 * @return the size of the tree
	 */
	public void print();

}
