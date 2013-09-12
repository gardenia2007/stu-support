package edu.hit.irlab.nlp.tools;

import java.util.Vector;

import edu.hit.irlab.nlp.ner.NamedEntity;

/**
 * 词法分析和命名实体识别统一处理接口
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public interface WordSegPosNERec {

	/**
	 * The main function of the tool that does WordSegment, Pos-Tagging
     * and NameEntity Recognizing together, you need to implement this 
     * function if you want to use your own tool.<br>
	 * The words, PosTag and NeTag vectors are empty before call this 
	 * function, while at the end of this function, the three vectors
	 * will be filled with the words and their tags
	 *  
	 * @param text
	 * 			the original text
	 * @param words
	 * 			the words after segment of the text
	 * @param posTags
	 * 			the POSTags of the words
	 * @param entitys
	 * 			the NamedEntity vector
	 */
	public void wordSegPosNERec(String text,
									 Vector<String> words,
									 Vector<String> posTags,
									 Vector<NamedEntity> entitys);
	/**
	 * The main function of the tool that does WordSegment, Pos-Tagging
     * and NameEntity Recognizing together, you need to implement this 
     * function if you want to use your own tool.<br>
	 * The words and PosNeTag vectors are empty before call this 
	 * function, while at the end of this function, the two vectors
	 * will be filled with the words and their tags(combine pos with ne,
	 * neTag is prior to posTag)
	 *  
	 * @param text
	 * 			the original text
	 * @param words
	 * 			the words after segment of the text
	 * @param tags
	 * 			the POSTags and NETags of the words
	 */
	public void wordSegPosNERec(String text,
			 Vector<String> words,
			 Vector<String> tags);

}
