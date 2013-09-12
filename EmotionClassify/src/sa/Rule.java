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
	 * @param segResult 微博的分词结果
	 * @param resource 某类情感所对应的资源
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
	 * @param word 一个词语
	 * @return 该词语的情感{happy\sad\angry\surprise\fear\none}
	 */
	public static String getEmo_Emoticon(String word, HashSet<String> emotionWordSet) {
		if(Resource.happyEmoticons == null) {
			Resource.loadEmotionResource();
		}
		//遍历5种情感
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
		
		//遍历5种情感
		String result = "none";
		for(String emotion: Resource.emotions) {
			if(Resource.emotionlexiconMap.get(emotion).contains(word)) {
				result = emotion;
				if(notflag == 0) { //该词前面无否定
					emotionWordSet.add(word);
				} else { //该词前面有否定(否定后只有happy仍有情绪，其余情绪均无)
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
	 * 否定词典+情感词典
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
			if(Resource.negations.contains(segResult.get(i))) { //包含否定词
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
			} else { //不含否定词
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
	 * @param firstMap 第一个map
	 * @param secondMap 第二个map，与第一个map的结构一样
	 * @param mergeMap 将两个map的结果融合
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
	 * 使用规则对分词以后的句子判断情感，emotionWordSet中存放句子里匹配到的情感表情符、词语
	 */
	public static String getEmotionFromSeg(Vector<String> segResult, 
			HashSet<String> emotionWordSet) {
		String emotion = null;
		
		//get emotion according to emoticon
		emotionWordSet.clear();
		HashMap<String, Integer> distri_emoticon = new HashMap<String, Integer>(); //存放5种情绪的对应分布
		rule_Emoticons(segResult, distri_emoticon, emotionWordSet); //根据emoticon判断情感
		
		int flag = 0;
		for(String emo: Resource.emotions) {
			int num = distri_emoticon.get(emo);
			if(num != 0) {
				flag = 1;
				break;
			}
		}
		if(flag == 1) { //含有表情符，可以判断情感了
			emotion = chooseMax(distri_emoticon);
			return emotion; //返回，不再接着执行
		}
		
		//不含表情符，使用词典来判断情绪
		emotionWordSet.clear();
		HashMap<String, Integer> distri_emotionlexicon = new HashMap<String, Integer>();
		rule_NegationemotionLexicon(segResult, distri_emotionlexicon, emotionWordSet);
		
		emotion = chooseMax(distri_emotionlexicon);
		return emotion;
	}
	
	/**
	 * @param emoMap 结果map
	 * @return 返回值最大的那个情感，若最大值对应多个情感，则返回none.
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
