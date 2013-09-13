package edu.hit.irlab.nlp.irlas;

/**
 * IRLAS DLL Interface Declaration
 * @author chuter
 * @version 1.0
 */
public class IRLAS_Interface 
{
	/**
	 * 加载DLL文件
	 */
	static 
	{
		System.load("e:/stu-support/EmotionClassify/resource/nlp_data/irlas");
	}
	
	/**
	 * 根据配置文件加载资源
	 * @return 返回成功与否的结果，1表示加载成功
	 */	
	public native static int loadSegRes(String configFileName, String resPathName);
	
	/**
	 * 动态设置分词器的选项
	 */
	public native static void setOption(int isPER, int isLOC, int isPOS);
	
	/**
	 * 释放分词器的资源
	 */
	public native static void releaseSegRes();
	
	/**
	 * 分词的dll接口
	 * @return 返回分词结果
	 */
	public native static byte[] wordSegment_dll(byte[] str);
}
