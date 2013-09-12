package nlp;

import io.IO;

import java.util.ArrayList;
import java.util.Vector;

import util.Config;

//import edu.hit.irlab.nlp.irlas.IRLAS;
/**
 * @detail �ַ����ִʡ����Ա�ע
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
//		/** ����LTP�ִ� */
//		IRLAS irlas = new IRLAS();
//		irlas.loadResource();
//		
//		Vector<String> segResult = new Vector<String>(); //�ŷִʵĽ��
//		Vector<String> posResult = new Vector<String>(); //�Ŵ��Ա�ע�Ľ��
//		
//		/** ������������ı��б� */
//		ArrayList<String> featureList = new ArrayList<String>();
//		
//		ArrayList<String> srcList = IO.readFile(srcFile, encoding);
//		for(String srcLine: srcList)
//		{
//			if(!srcLine.equals(""))
//			{
////				String[] splitSet = srcInputLine.split("\t");
////				
////				/** ��ǰ���ӵ�label */
////				String label = splitSet[0];
//				
//				/** ��������ı��ִʺʹ��Ա�ע */
//				segResult.clear();
//				posResult.clear();
////				if(splitSet.length < 1)
////				{
////					System.err.println(srcInputLine);
////					continue;
////				}
//				/** ʹ��irlas���зִʡ����Ա�ע*/
//				irlas.wordSegment(srcLine, segResult, posResult); 
//				
//				/** ���������е�ÿ����*/
//				/** �ڴ˴���label��� "\t\t" ����� " "�ո� */
//				String featureStr = ""; /** ʹ�õ�ʱ��ע���޸� */
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
//		/** ��������ļ����ı� */
//		IO.writeFile(segFile, featureList, encoding);
//		
//		/** �ͷ�LTP�ִ���Դ */
//		irlas.releaseResource();
//	}

}
