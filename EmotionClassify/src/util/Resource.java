package util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import edu.hit.irlab.util.io.IOUtils;


/**
 * 设置所有情感资源
 */
public class Resource {
	//公用的否定词典
	public static HashSet<String> negations = null;
	
	//表达5类情绪的资源{happy、sad、angry、surprise、fear}
	public static HashSet<String> happyEmoticons = null;
	public static HashSet<String> happyLexicons = null;
	
	public static HashSet<String> sadEmoticons = null;
	public static HashSet<String> sadLexicons = null;
	
	public static HashSet<String> angryEmoticons = null;
	public static HashSet<String> angryLexicons = null;
	
	public static HashSet<String> surpriseEmoticons = null;
	public static HashSet<String> surpriseLexicons = null;
	
	public static HashSet<String> fearEmoticons = null;
	public static HashSet<String> fearLexicons = null;
	
	public static String[] emotions = {"happy", "sad", "angry", "surprise", "fear"};
	public static HashMap<String, HashSet<String>> emoticonMap = null;
	public static HashMap<String, HashSet<String>> emotionlexiconMap = null;
	public static HashSet<String> allEmoticons = null;
	//-------------------------------------------------------------------
	
	//加载情绪资源
	@SuppressWarnings("unchecked")
	public static void loadEmotionResource() {
		
		try {
			happyEmoticons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emoticon/happy_emoticon.dat"));
			sadEmoticons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emoticon/sad_emoticon.dat"));
			angryEmoticons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emoticon/angry_emoticon.dat"));
			surpriseEmoticons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emoticon/surprise_emoticon.dat"));
			fearEmoticons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emoticon/fear_emoticon.dat"));
			
			happyLexicons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emotionlexicon/happy_lexicon.dat"));
			sadLexicons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emotionlexicon/sad_lexicon.dat"));
			angryLexicons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emotionlexicon/angry_lexicon.dat"));
			surpriseLexicons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emotionlexicon/surprise_lexicon.dat"));
			fearLexicons = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "emotionlexicon/fear_lexicon.dat"));
			
			negations = (HashSet<String>) IOUtils.readObjectFromFile(
					new File(Config.getResourceDir() + "negations.dat"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		emoticonMap = new HashMap<String, HashSet<String>>();
		emoticonMap.put("happy", happyEmoticons);
		emoticonMap.put("sad", sadEmoticons);
		emoticonMap.put("angry", angryEmoticons);
		emoticonMap.put("surprise", surpriseEmoticons);
		emoticonMap.put("fear", fearEmoticons);
		
		emotionlexiconMap = new HashMap<String, HashSet<String>>();
		emotionlexiconMap.put("happy", happyLexicons);
		emotionlexiconMap.put("sad", sadLexicons);
		emotionlexiconMap.put("angry", angryLexicons);
		emotionlexiconMap.put("surprise", surpriseLexicons);
		emotionlexiconMap.put("fear", fearLexicons);
		
		allEmoticons = new HashSet<String>();
		allEmoticons.addAll(happyEmoticons);
		allEmoticons.addAll(sadEmoticons);
		allEmoticons.addAll(angryEmoticons);
		allEmoticons.addAll(surpriseEmoticons);
		allEmoticons.addAll(fearEmoticons);
				
		System.err.println("emotion resource load ok!");
	}
}
