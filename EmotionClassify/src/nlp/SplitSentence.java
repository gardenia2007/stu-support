package nlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import util.Config;

/**
 * 
 * @author shiqiuhui
 * @detail 分句通用(分句符号可以自己修改)
 *
 */
public class SplitSentence 
{
	public static void main(String[] args) 
	{
		try {
			String infile = "data/test7500_digital.ft.seg.postwsg.lb";
			String outfile = infile + ".subsegsent";
//			String outfile = infile + ".suball";
			String encoding = "gbk";
			splitSegFile(infile, outfile, encoding);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void test() {
		String s = "比如 日本 车 给 人 感觉 不 坚固 ， 但 车头 钢架 结构 部分 ……";
		ArrayList<String> list = splitSegSentence(s);
		for(String subline: list) {
			System.out.println(subline);
		}
	}
	
	public static void splitSegFile(String infile, String outfile,
			String encoding) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(infile),encoding));		
			PrintWriter writer = new PrintWriter(new BufferedWriter(new 
					OutputStreamWriter(new FileOutputStream(outfile), encoding)));
			
			String line = null;
			ArrayList<String> subpossents = new ArrayList<String>();
			while((line = reader.readLine()) != null) {
				subpossents.clear();
				String[] temp = line.split("\t");
				String doctag = temp[0];
				String segpossent = temp[1].replaceAll("\\s+", " ").trim();
				
				subpossents = splitSegSentence(segpossent);
				for(String sub: subpossents) {
					writer.println(doctag + "\t" + sub);
				}
				writer.println("aaabccc");
			}
			reader.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> splitSegSentence(Vector<String> segResult, Vector<String> posResult) {
		ArrayList<String> subsents = new ArrayList<String>();
		int beginindex = 0;
		int subsentnum = 0;
		
		for(int i=0; i<segResult.size(); i++) {
			String word = segResult.get(i);
			for(String punc: Config.splitCharacters) {
				if(word.equals(punc)) {
					String subtxt = "";
					for(int j=beginindex; j<=i; j++) {
						subtxt += segResult.get(j) + "/" + posResult.get(j) + " ";
					}
					subtxt = subtxt.replaceAll("\\s+", " ").trim();
					subsents.add(subtxt);
					subsentnum ++;
					beginindex = i + 1;
					break;
				}
			}
		}
		if(beginindex < segResult.size()) {
//			System.err.println("last:" + beginindex);
			String last = "";
			for(int i=beginindex; i<segResult.size(); i++) {
				last += segResult.get(i) + "/" + posResult.get(i) + " ";
			}
			last = last.replaceAll("\\s+", " ").trim();
			subsents.add(last);
		}
		return subsents;
	}
	
	public static ArrayList<String> splitSegSentence(String segpossent) {
		
		Vector<String> segResult = new Vector<String>();
		Vector<String> posResult = new Vector<String>();
		
		System.out.println(segpossent);
		String[] pairs = segpossent.split(" ");
		for(String pair: pairs) {
			String[] onepair = pair.split("/");
			segResult.add(onepair[0]);
			posResult.add(onepair[1]);
		}
		ArrayList<String> subsents = new ArrayList<String>();
		int beginindex = 0;
		int subsentnum = 0;
		
		for(int i=0; i<segResult.size(); i++) {
			String word = segResult.get(i);
			for(String punc: Config.splitCharacters) {
				if(word.equals(punc)) {
					String subtxt = "";
					for(int j=beginindex; j<=i; j++) {
						subtxt += segResult.get(j) + "/" + posResult.get(j) + " ";
					}
					subtxt = subtxt.replaceAll("\\s+", " ").trim();
					subsents.add(subtxt);
					subsentnum ++;
					beginindex = i + 1;
					break;
				}
			}
		}
		if(beginindex < segResult.size()) {
			System.err.println("last:" + beginindex);
			String last = "";
			for(int i=beginindex; i<segResult.size(); i++) {
				last += segResult.get(i) + "/" + posResult.get(i) + " ";
			}
			last = last.replaceAll("\\s+", " ").trim();
			subsents.add(last);
		}
		return subsents;
	}
}
