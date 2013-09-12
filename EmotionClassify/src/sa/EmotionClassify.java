package sa;

import java.util.HashSet;
import java.util.Vector;

import edu.hit.irlab.nlp.irlas.IRLAS;
import preprocess.Filter;

public class EmotionClassify {
	private IRLAS las;
	private Vector<String> word;
	private Vector<String> pos;
	
	public EmotionClassify() {
		word = new Vector<String>();
		pos = new Vector<String>();
		try {
			las = new IRLAS();
			las.loadResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对输入的微博进行情绪分析
	 * 返回结果有6种情况：happy、sad、angry、surprise、fear、none(无情绪)
	 */
	public String getEmo(String text) {
		String emo = "NONE";
		text = Filter.filterWeibo(text);
		if(text.equals("")) {
			return emo;
		} else {
			word.clear();
			pos.clear();
			las.wordSegment(text, word, pos);
			
			HashSet<String> emotionWordSet = new HashSet<String>();
			emo = Rule.getEmotionFromSeg(word, emotionWordSet);
			System.out.println("text: " + text);
			System.out.println("emoword: " + emotionWordSet + "\n");
			return emo;
		}
	}
	
	public static void main(String[] args) {
		String text = "神马，引体向上要做5个才及格？你让老衲引体蹦上做5个都没那体力。。。[愤怒]";
		EmotionClassify ec = new EmotionClassify();
		String emo = ec.getEmo(text);
		System.out.println(emo);
	}

}
