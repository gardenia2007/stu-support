package edu.hit.irlab.nlp.irlas;

/**
 * IRLAS DLL Interface Declaration
 * @author chuter
 * @version 1.0
 */
public class IRLAS_Interface 
{
	/**
	 * ����DLL�ļ�
	 */
	static 
	{
		System.load("e:/stu-support/EmotionClassify/resource/nlp_data/irlas");
	}
	
	/**
	 * ���������ļ�������Դ
	 * @return ���سɹ����Ľ����1��ʾ���سɹ�
	 */	
	public native static int loadSegRes(String configFileName, String resPathName);
	
	/**
	 * ��̬���÷ִ�����ѡ��
	 */
	public native static void setOption(int isPER, int isLOC, int isPOS);
	
	/**
	 * �ͷŷִ�������Դ
	 */
	public native static void releaseSegRes();
	
	/**
	 * �ִʵ�dll�ӿ�
	 * @return ���طִʽ��
	 */
	public native static byte[] wordSegment_dll(byte[] str);
}
