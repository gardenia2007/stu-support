package edu.hit.irlab.nlp.ner;

/**
 * The description of the <code>Name Entity</code><br>
 * The information includes the start position of the
 * <code>Name Entity</code> in the sentence, the tag
 * of the Entity and it's content
 * 
 * @author chuter
 * @date 2010.04.22
 * @version 0.1
 */
public class NamedEntity {
	//the position in the sentence
	public int startSentPos = -1;
	//the content of the Name Entity
	public String content = null;
	//the tag of the Name Entity
	public String tag = null;
	
	public NamedEntity(int startSentPos,
			String content,
			String tag) {
		this.startSentPos = startSentPos;
		this.content = content;
		this.tag = tag;
	}
	
	public String toString() {
		return String.format("[%s %d %s]", content, startSentPos, tag);
	}
}

