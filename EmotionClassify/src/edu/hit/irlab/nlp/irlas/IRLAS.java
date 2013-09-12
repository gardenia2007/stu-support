package edu.hit.irlab.nlp.irlas;

import java.io.UnsupportedEncodingException;//�쳣
import java.util.Vector;//����

import edu.hit.irlab.nlp.tools.FMMProcessor;
import edu.hit.irlab.nlp.tools.LoadException;
import edu.hit.irlab.nlp.tools.WordSegPos;

/**
 * �ôʷ��������ù�������ҵ��ѧ��Ϣ�����о����ĵķִʺʹ��Ա�עģ�� <br />
 * ģ���飺�ʷ�����ϵͳIRLAS���зִʡ�ʱ������ʶ��δ��¼��ʶ��ʹ��Ա�ע�ȹ��ܣ�
 * ֧�ֶ��̣߳��������õĿ������Ժͽ�׳�ԡ���ϵͳ�μ��˵ڶ���SIGHAN�ִ����⣬
 * �񱱴����Ϸ�ղ��Ե�3������16�һ���23��������͵�һ����Fֵ���0.1������
 * �񱱴����Ͽ��Ų��Ե�4������14�һ���17��������͵�һ����Fֵ���0.4������ <br />
 * ������飺��Unigram��BigramΪ���������ù���ķ���ʶ��ʱ������ʣ� �Ի���HMM�Ľ�ɫ��ע����ʶ��δ��½�ʣ���HMM���д��Ա�ע��
 * ���Ѱ�ŵõ���ѵĽ���� ϵͳ���������ձ�6�·����ϲ��ԣ�׼ȷ�ʡ��ٻ��ʡ�Fֵ�ֱ�ﵽ97.2%��97.7%��97.4%�� <br />
 * 
 * @see <a href="http://ir.hit.edu.cn">HIT-CIR</a>
 * 
 * @author chuter, Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class IRLAS implements WordSegPos
{

	private boolean hasextendDict = true;
	private boolean hasload = false;//������Դ�ж�

	private String configFileName = "config.ini";//�����ļ���
	private String resPathName = "resource/nlp_data/irlas";//��Դ·����

	private static FMMProcessor dict = null;//���� "�������ƥ��" ʶ�� "��չ�ʱ�" �дʵı߽�

	public IRLAS()//���췽��һ
	{
		if (hasextendDict && dict == null)
		{
			dict = new FMMProcessor();
		}
	}

	public IRLAS(String resPathName)//���췽����
	{
		this.resPathName = resPathName;
		if (hasextendDict)
		{
			dict = new FMMProcessor();
		}
	}

	/**
	 * the path of the configure file and the date
	 * 
	 * @param configFileName
	 *            the configure file name of the IRLAS
	 * @param resPathName
	 *            the path of the resource which needed by the IRLAS
	 */
	public IRLAS(String configFileName, String resPathName)//���췽����
	{
		this.configFileName = configFileName;
		this.resPathName = resPathName;
	}

	/**
	 * load the resource
	 */
	public void loadResource()
	{
		if (hasload) return;//�Ѽ��أ�����

		int loadResult = IRLAS_Interface 
				.loadSegRes(configFileName, resPathName);//û���أ������������
		if (loadResult != 1) try //û�м��سɹ������쳣
		{
			throw new LoadException("IRLAS load error!");
		}
		catch (LoadException e)
		{
			e.printStackTrace();
		}
		setoption();//���سɹ�������ѡ����
		hasload = true;
	}

	/**
	 * release the resource
	 */
	public void releaseResource()//�ͷ���Դ
	{
		if (hasload)
		{
			IRLAS_Interface.releaseSegRes();
			hasload = false;
		}
	}

	/**
	 * set the options
	 */
	public void setoption()
	{
		IRLAS_Interface.setOption(1, 1, 1);
	}

	/**
	 * set the hasextendDict(default true)
	 */
	public void setExtendDict(boolean hasextendDict)
	{
		this.hasextendDict = hasextendDict;
	}

	/**
	 * segment the giving string into words return null if some errors occur
	 * 
	 * @param str
	 *            the original text
	 */
	private synchronized String run(String str)
	{
		str += " ";
		String result = null;
		try
		{
			result = new String(IRLAS_Interface.wordSegment_dll(str
					.getBytes("GB18030")));
			return result.replaceAll("\\t\\t", " ");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see edu.hit.irlab.nlp.tools.WordSegPos
	 */
	public void wordSegment(String text, Vector<String> words,
			Vector<String> posTags)
	{
		if (words == null || posTags == null)
		{
			System.err.println(this.getClass().getName() + "IRLAS: "
					+ "vector is null");
			return;
		}
		words.clear();
		posTags.clear();

		// text = IllegalCharacterFilter.filter(text); // ���˷Ƿ��ַ��������ĺ�Ӣ�ģ�
		text = text.replaceAll("��", " ").trim();
		if (hasextendDict)
		{
			text = dict.preProcess(text);
			/**���� "��չ�ʱ�" �Ծ��ӽ���Ԥ���������ش�������¾���. 
			 * ��Ҫ����������"��չ��"���߼��Ͽո�
			 * */
		}

		String result = run(text);
		if (result != null && result.replace("\\s*", "").length() > 0)
		{
			String[] items = result.split(" ");
			for (String item : items)
			{
				// the item is in the form like: "word/pos"
				String[] word_add_pos = item.split("/");
				words.add(word_add_pos[0]);
				posTags.add(word_add_pos[1]);
			}
		}

		if (hasextendDict)//���к���
		{
			dict.postPrecess(words, posTags);
		}
	}

}
