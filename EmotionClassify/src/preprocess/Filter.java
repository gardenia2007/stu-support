package preprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Config;

public class Filter {

	public static Pattern hashtagP = Pattern.compile(Config.hashtagRegex);
	
	private static int hashtagCount(String tweet)
	{
		int hashtagCount = 0;
		Matcher m = hashtagP.matcher(tweet);
		while(m.find())
		{
			hashtagCount++;
		}
		
		return hashtagCount;
	}
	
	public static int atCount(String tweet)
	{
		int atCount = 0;
		int index = tweet.indexOf("@");
		String postTweet = tweet;
		while(index != -1)
		{
			postTweet = postTweet.substring(index + 1);
			index = postTweet.indexOf("@");
			
			atCount++;
		}
		return atCount;
	}
	
	/**
	 * 对单条微博进行过滤
	 * 返回过滤后的微博，过滤后的微博可能是空串、较短串、全英文串
	 */
	public static String filterWeibo(String text)
	{
		text = text + " ";
		
		text = replaceRetweet(text); //转发
		text = replaceReply(text); //回复
		text = replaceAtUser(text); //at某人
		text = replaceURL(text); //网址
		text = replaceHalfURL(text); //半网址
		
		int retweetIndex = text.indexOf("RT"); //只要原文
		if(retweetIndex != -1) {
			text = text.substring(0, retweetIndex);
		}
		
		text = Filter.processPunctuation(text);
		text = text.replaceAll("REPLY", " "); //回复
		text = text.replaceAll("ATUSER", " "); //at
		text = text.replaceAll("\\[[0-9a-zA-Z]*\\]", "");
		text = text.replaceAll("【】", " ");
		text = text.replaceAll("《》", " ");
		text = text.replaceAll("<>", " ");
		text = text.replaceAll("\\{\\}", " ");
		text = text.replaceAll("“”", " ");
		text = text.replaceAll("转发微博", " ");
		text = text.replaceAll("null", " ");
		text = text.replaceAll("NULL", " ");
		text = text.replaceAll("&quot", " ");
		text = text.replaceAll("&#183;", " ");
		text = text.replaceAll("&gt;", " ");
		text = text.replaceAll("&lsquo;", " ");
		text = text.replaceAll("&#176;", " ");
		text = text.replaceAll("&hellip;", " ");
		text = text.replaceAll("&amp;", " ");
		text = text.replaceAll("&nbsp;", " ");
		text = text.replaceAll("[`\\\\]*r[\\\\]*n", " ");
		text = text.replaceAll("[　]+", " ");
		text = text.replaceAll("\\s+", " ");
		text = text.trim();
		
		return text;
	}
	
	public static String processPunctuation(String line) {
		Pattern nousep = Pattern.compile(Config.nousePunctuation); //处理无用标点，留一个
		Matcher nousem = nousep.matcher(line);
		while(nousem.find()) {
			String nousepunc = nousem.group();
			line = line.replace(nousepunc, Character.toString(nousepunc.charAt(0)));
		}
//		System.out.println(line);
		
		Pattern usefulp = Pattern.compile(Config.usefulPunctuation); //处理有用标点，留两个
		Matcher usefulm = usefulp.matcher(line);
		while(usefulm.find()) {
			String usepunc = usefulm.group();
//			System.out.println(usepunc);
			String replaceStr = "" + usepunc.charAt(0) + usepunc.charAt(1);
			line = line.replace(usepunc, replaceStr);
		}
//		System.out.println(line);
		return line;
	}
	
	public static String replaceAtUser(String tweet)
	{
		String repAtUser = tweet.replaceAll(Config.atUser, " ATUSER ");
		repAtUser = repAtUser.replaceAll(Config.atUser, " ATUSER ");
		return repAtUser;
	}
	
	private static String replaceRetweet(String tweet) {
		String repRetweet = tweet.replaceAll(Config.retweet, " RT ");
		return repRetweet;
	}

//	private static String replaceHashtag(String tweet) {
//		String repHashtag = tweet.replaceAll(Config.hashtagRegex, " HASHTAG ");
//		return repHashtag;
//	}
	
	private static String replaceURL(String tweet) {
		String repURL = tweet.replaceAll(Config.url, " URL ");
		return repURL;
	}
	
	private static String replaceHalfURL(String tweet) {
		String repHALFURL = tweet.replaceAll(Config.halfurl, " HALFURL ");
		return repHALFURL;
	}
	
	private static String replaceReply(String tweet) {
		String repReply = tweet.replaceAll(Config.reply, " REPLY ");
		return repReply;
	}
	
	public static void main(String[] args) {
		String weibofile = Config.getDataDir() + "cmt.cont";
		String outfile = weibofile + ".ft";
		filterWeiboFile(weibofile, outfile);
	}

	/**
	 * 对大量微博进行过滤
	 * 即使过滤后微博为空串、较短串、全英文串也会输出
	 * 若要将它们滤掉，在if中加上continue即可
	 */
	private static void filterWeiboFile(String weibofile, String outfile) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(weibofile), Config.getEncoding()));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(outfile), Config.getEncoding()));
			String line = null;
			int num = 0;
			while ((line = reader.readLine()) != null) {
				num++;
				String weibo = line;
//				String[] temp = line.split("\t");
//				if(temp.length!=2) {
//					System.err.println(line);
//					break;
//				}
//				String tag = temp[0];
//				String cont = temp[1];
				weibo = filterWeibo(weibo);
				if(weibo.length()==weibo.getBytes().length) {
					System.err.println(line);
					writer.println("");
//					continue;
				} else {
					writer.println(weibo);
				}
			}
			System.out.println(num);
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
