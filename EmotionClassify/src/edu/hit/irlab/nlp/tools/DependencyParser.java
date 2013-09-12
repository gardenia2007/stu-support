package edu.hit.irlab.nlp.tools;

import java.util.Vector;

/**
 * The interface of Dependency Parser
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.05.08
 * @version 0.1
 */
public interface DependencyParser {
	
	/**
	 * The main function of the Dependency Parser, you need to implement this
	 * function if you want to use your own Dependency Parser.<br>
	 * You have to give the words and the tags of them, the result of the Parser
	 * is a dependency tree, which is expressed by a father-id list and a relat-
	 * ion list, each word as a node in the tree, and the relation is the lable
	 * of the verge between a node and it's father node, the id is the index of
	 * the word in the words list<br>
	 * PS. the words list and the relation list should have the same length
	 * 
	 * @param word_vec
	 * 			the words
	 * @param tag_vec
	 * 			the tags of the words
	 * @param fatherId_vec
	 * 			father id list 
	 * @param fatherId_vec
	 * 			the relation list
	 */
	public abstract void parse(Vector<String> word_vec,
									 Vector<String> tag_vec,
									 Vector<Integer> fatherId_vec,
									 Vector<String> relation_vec);
	
}
