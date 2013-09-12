package sa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import nlp.SplitSentence;

import util.Resource;

public class Rule {
	
	/**
	 * @param segResult ΢���ķִʽ��
	 * @param resource ĳ���������Ӧ����Դ
	 * @return
	 */
	public static boolean hasList(Vector<String> segResult, List<String> resource) {
		boolean isContain = false;
		for(String emo: resource) {
			if(segResult.contains(emo)) {
				isContain = true;
				break;
			}
		}
		return isContain;
	}
	
	/**
	 * @param word һ������
	 * @return �ô�������{happy\sad\angry\surprise\fear\none}
	 */
	public static String getEmo_Emoticon(String word, HashSet<String> emotionWordSet) {
		if(Resource.happyEmoticons == null) {
			Resource.loadEmotionResource();
		}
		//����5�����
		String result = "none";
		for(String emotion: Resource.emotions) {
			if(Resource.emoticonMap.get(emotion).contains(word)) {
				result = emotion;
				emotionWordSet.add(word);
				break;
			}
		}
		return result;
	}
	
	public static String getEmo_emotionLexicon(String word, HashSet<String> emotionWordSet, 
			int notflag) {
		if(Resource.happyLexicons == null) {
			Resource.loadEmotionResource();
		}
		
		//����5�����
		String result = "none";
		for(String emotion: Resource.emotions) {
			if(Resource.emotionlexiconMap.get(emotion).contains(word)) {
				result = emotion;
				if(notflag == 0) { //�ô�ǰ���޷�
					emotionWordSet.add(word);
				} else { //�ô�ǰ���з�(�񶨺�ֻ��happy����������������������)
					if(emotion.equals("happy")) {
						emotionWordSet.add("not_" + word);
					}
				}
				break;
			}
		}
		return result;
	}
	
	public static void rule_Emoticons(Vector<String> segResult, HashMap<String, Integer> emoMap, 
			HashSet<String> emotionWordSet) {
		if(Resource.happyEmoticons == null) {
			Resource.loadEmotionResource(); //load resource
		}
		
		emoMap.clear();
		for(String emotion: Resource.emotions) {
			emoMap.put(emotion, 0); //initialize the result object
		}
		
		for(String word: segResult) {
			String emotion = getEmo_Emoticon(word, emotionWordSet);
			if(!emotion.equals("none")) {
				updateEmoMap(emotion, emoMap);
			}
		}
	}
	
	/**
	 * �񶨴ʵ�+��дʵ�
	 */
	public static void rule_NegationemotionLexicon(Vector<String> segResult, HashMap<String, Integer> emoMap, 
			HashSet<String> emotionWordSet) {
		if(Resource.happyLexicons == null) {
			Resource.loadEmotionResource(); //load resource
		}
		
		emoMap.clear();
		for(String emotion: Resource.emotions) {
			emoMap.put(emotion, 0); //initialize the result object
		}
		
		for(int i=0; i<segResult.size(); i++) {
			int notflag;
			if(Resource.negations.contains(segResult.get(i))) { //�����񶨴�
				notflag = 1;
				int j = i + 1;
				for(; j<i+3 && j<segResult.size(); j++) {
					//decide if it is emolexicon
					String emotion = getEmo_emotionLexicon(segResult.get(j), emotionWordSet, notflag);
					if(emotion.equals("none"))
						continue;
					else {
						if(emotion.equals("happy")) {
							int value = emoMap.get("sad") + 1;
							emoMap.put("sad", value);
						}
					}
				}
				i = j - 1;
			} else { //�����񶨴�
				notflag = 0;
				String emotion = getEmo_emotionLexicon(segResult.get(i), emotionWordSet, notflag);
				if(emotion.equals("none"))
					continue;
				else {
					updateEmoMap(emotion, emoMap);
				}
			}
		}
	}
	
	/**
	 * @param firstMap ��һ��map
	 * @param secondMap �ڶ���map�����һ��map�Ľṹһ��
	 * @param mergeMap ������map�Ľ���ں�
	 */
	public static void merge(HashMap<String, Integer> firstMap,
			HashMap<String, Integer> secondMap, HashMap<String, Integer> mergeMap) {
		for(String key: firstMap.keySet()) {
			int value = firstMap.get(key);
			int value1 = secondMap.get(key);
			int value2 = value + value1;
			mergeMap.put(key, value2);
		}
//		System.out.println(mergeMap);
	}

	/**
	 * ʹ�ù���Էִ��Ժ�ľ����ж���У�emotionWordSet�д�ž�����ƥ�䵽����б����������
	 */
	public static String getEmotionFromSeg(Vector<String> segResult, 
			HashSet<String> emotionWordSet) {
		String emotion = null;
		
		//get emotion according to emoticon
		emotionWordSet.clear();
		HashMap<String, Integer> distri_emoticon = new HashMap<String, Integer>(); //���5�������Ķ�Ӧ�ֲ�
		rule_Emoticons(segResult, distri_emoticon, emotionWordSet); //����emoticon�ж����
		
		int flag = 0;
		for(String emo: Resource.emotions) {
			int num = distri_emoticon.get(emo);
			if(num != 0) {
				flag = 1;
				break;
			}
		}
		if(flag == 1) { //���б�����������ж������
			emotion = chooseMax(distri_emoticon);
			return emotion; //���أ����ٽ���ִ��
		}
		
		//�����������ʹ�ôʵ����ж�����
		emotionWordSet.clear();
		HashMap<String, Integer> distri_emotionlexicon = new HashMap<String, Integer>();
		rule_NegationemotionLexicon(segResult, distri_emotionlexicon, emotionWordSet);
		
		emotion = chooseMax(distri_emotionlexicon);
		return emotion;
	}
	
	/**
	 * @param emoMap ���map
	 * @return ����ֵ�����Ǹ���У������ֵ��Ӧ�����У��򷵻�none.
	 */
	public static String chooseMax(HashMap<String, Integer> emoMap) {
		Iterator<String> iter = emoMap.keySet().iterator();
		String maxloc = iter.next();
		int max = emoMap.get(maxloc);
		
		while(iter.hasNext()) {
			String s = iter.next();
			int v = emoMap.get(s);
			if(v > max) {
				max = v;
				maxloc = s;
			}else {
				continue;
			}
		}
		
		ArrayList<String> maxlist = new ArrayList<String>();
		for(String key: emoMap.keySet()) {
			if(emoMap.get(key)==max) {
				maxlist.add(key);
			}
		}
		
		if(maxlist.size() != 1) {
			maxlist.clear();
			return "none";
		}else {
			maxlist.clear();
			return maxloc;
		}
	}
	
	private static void updateEmoMap(String emotion,
			HashMap<String, Integer> emoMap) {
		if(emotion.equals("happy")) {
			int value = emoMap.get(emotion) + 1;
			emoMap.put(emotion, value);
		}else if(emotion.equals("sad")) {
			int value = emoMap.get(emotion) + 1;
			emoMap.put(emotion, value);
		}else if(emotion.equals("angry")) {
			int value = emoMap.get(emotion) + 1;
			emoMap.put(emotion, value);
		}else if(emotion.equals("surprise")) {
			int value = emoMap.get(emotion) + 1;
			emoMap.put(emotion, value);
		}else if(emotion.equals("fear")) {
			int value = emoMap.get(emotion) + 1;
			emoMap.put(emotion, value);
		}
	}

	public static void main(String[] args) throws Exception {
	
	}

}
