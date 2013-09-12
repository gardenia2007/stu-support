package nlp;

import io.IO;

import java.util.ArrayList;
import java.util.Vector;

import util.Config;

//import edu.hit.irlab.nlp.irlas.IRLAS;
/**
 * @detail 字符串分词、词性标注
 * 
 * @author Shi Qiuhui(qhshi@ir.hit.edu.cn)
 * @date 2012.10.7 
 */
public class SplitWords 
{	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try
		{
			String encoding = "gbk";
			
			String infile = Config.getDataDir() + "test.cmt.cont.ft";
			String segfile = infile + ".seg";
//			SplitWords.irlas(infile, segfile, encoding);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

//	public static void irlas(String srcFile, String segFile, String encoding)
//	{
//		/** 加载LTP分词 */
//		IRLAS irlas = new IRLAS();
//		irlas.loadResource();
//		
//		Vector<String> segResult = new Vector<String>(); //放分词的结果
//		Vector<String> posResult = new Vector<String>(); //放词性标注的结果
//		
//		/** 待输出的特征文本列表 */
//		ArrayList<String> featureList = new ArrayList<String>();
//		
//		ArrayList<String> srcList = IO.readFile(srcFile, encoding);
//		for(String srcLine: srcList)
//		{
//			if(!srcLine.equals(""))
//			{
////				String[] splitSet = srcInputLine.split("\t");
////				
////				/** 当前句子的label */
////				String label = splitSet[0];
//				
//				/** 对输入的文本分词和词性标注 */
//				segResult.clear();
//				posResult.clear();
////				if(splitSet.length < 1)
////				{
////					System.err.println(srcInputLine);
////					continue;
////				}
//				/** 使用irlas进行分词、词性标注*/
//				irlas.wordSegment(srcLine, segResult, posResult); 
//				
//				/** 遍历句子中的每个词*/
//				/** 于此处将label后的 "\t\t" 变成了 " "空格 */
//				String featureStr = ""; /** 使用的时候注意修改 */
//				for(int i = 0; i< segResult.size(); i++)
//				{
//					featureStr = featureStr + segResult.get(i) + "/" + posResult.get(i) + " ";
////					featureStr = featureStr + segResult.get(i) + " ";
//				}
//				featureStr = featureStr.trim();
//				featureList.add(featureStr);
//			} else {
//				featureList.add("");
//			}
//		}
//		/** 输出特征文件到文本 */
//		IO.writeFile(segFile, featureList, encoding);
//		
//		/** 释放LTP分词资源 */
//		irlas.releaseResource();
//	}

}
