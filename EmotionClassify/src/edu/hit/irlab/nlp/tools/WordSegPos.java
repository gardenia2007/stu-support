/* 
 * Copyright 2009 HIT-CIR (HIT Center for Information Retrieval). 
 * 
 * author: chuter
 * mail:   lliu@ir.hit.edu.cn
 */
package edu.hit.irlab.nlp.tools;

import java.util.Vector;

/**
 * 词法分析接口
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public interface WordSegPos {
	
	/**
	 * The main function of the Word Segment and PosTagging tool, you need to
	 * implement this function if you want to use your own Word Segment and Pos
	 * Tagging tool.<br>
	 * The words and tags vector is empty before call this function, while at
	 * the end of this function, the two vectors will hold the words and their
	 * tags
	 *  
	 * @param text
	 * 			the original text
	 * @param words
	 * 			the words after segment of the text
	 * @param tags
	 * 			the tags of the words
	 */
	public abstract void wordSegment(String text, Vector<String> words,
									 Vector<String> tags);
	
}
