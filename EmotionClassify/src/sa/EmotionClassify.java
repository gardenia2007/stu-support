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
	 * �������΢��������������
	 * ���ؽ����6�������happy��sad��angry��surprise��fear��none(������)
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
		String text = "������������Ҫ��5���ż��������������������5����û������������[��ŭ]";
		EmotionClassify ec = new EmotionClassify();
		String emo = ec.getEmo(text);
		System.out.println(emo);
	}

}
