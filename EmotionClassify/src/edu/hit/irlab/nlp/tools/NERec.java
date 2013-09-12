package edu.hit.irlab.nlp.tools;

import java.util.Vector;

import edu.hit.irlab.nlp.ner.NamedEntity;

/**
 * 命名实体识别接口
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public interface NERec {
	
	/**
	 * The main function of the Name Entity Recognizing tool, you need to
	 * implement this function if you want to use your own Name Entity Recognizing.<br>
	 * The words and tags vector this function needs is the result of the
	 * WordSegment and PosTagging, while at the end of this function, 
	 * the entitys will be filled with the Name Entities which be
	 * recognized by this tool
	 *  
	 * @param words
	 * 			the String vector of the words
	 * @param posTags
	 * 			the String vector of the tags of the words
	 * @param entitys
	 * 			the Name Entity Vector
	 */
	public abstract void namedEntityRec(Vector<String> words,
									 	 Vector<String> posTags,
									 	 Vector<NamedEntity> entitys
									 	 );
	
}
