package edu.hit.irlab.nlp.irlas;

import java.io.UnsupportedEncodingException;//异常
import java.util.Vector;//向量

import edu.hit.irlab.nlp.tools.FMMProcessor;
import edu.hit.irlab.nlp.tools.LoadException;
import edu.hit.irlab.nlp.tools.WordSegPos;

/**
 * 该词法分析采用哈尔滨工业大学信息检索研究中心的分词和词性标注模块 <br />
 * 模块简介：词法分析系统IRLAS具有分词、时间数词识别、未登录词识别和词性标注等功能，
 * 支持多线程，具有良好的可配置性和健壮性。该系统参加了第二届SIGHAN分词评测，
 * 获北大语料封闭测试第3名（共16家机构23个结果，和第一名的F值相差0.1％）。
 * 获北大语料开放测试第4名（共14家机构17个结果，和第一名的F值相差0.4％）。 <br />
 * 方法简介：以Unigram和Bigram为基础，利用规则的方法识别时间和数词， 以基于HMM的角色标注方法识别未登陆词，以HMM进行词性标注，
 * 最后寻优得到最佳的结果。 系统利用人民日报6月份语料测试，准确率、召回率、F值分别达到97.2%、97.7%、97.4%。 <br />
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
	private boolean hasload = false;//加载资源判断

	private String configFileName = "config.ini";//配置文件名
	private String resPathName = "resource/nlp_data/irlas";//资源路径名

	private static FMMProcessor dict = null;//采用 "正向最大匹配" 识别 "扩展词表" 中词的边界

	public IRLAS()//构造方法一
	{
		if (hasextendDict && dict == null)
		{
			dict = new FMMProcessor();
		}
	}

	public IRLAS(String resPathName)//构造方法二
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
	public IRLAS(String configFileName, String resPathName)//构造方法三
	{
		this.configFileName = configFileName;
		this.resPathName = resPathName;
	}

	/**
	 * load the resource
	 */
	public void loadResource()
	{
		if (hasload) return;//已加载，返回

		int loadResult = IRLAS_Interface 
				.loadSegRes(configFileName, resPathName);//没加载，则调函数加载
		if (loadResult != 1) try //没有加载成功，抛异常
		{
			throw new LoadException("IRLAS load error!");
		}
		catch (LoadException e)
		{
			e.printStackTrace();
		}
		setoption();//加载成功，设置选择项
		hasload = true;
	}

	/**
	 * release the resource
	 */
	public void releaseResource()//释放资源
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

		// text = IllegalCharacterFilter.filter(text); // 过滤非法字符（非中文和英文）
		text = text.replaceAll("　", " ").trim();
		if (hasextendDict)
		{
			text = dict.preProcess(text);
			/**利用 "扩展词表" 对句子进行预处理，并返回处理过的新句子. 
			 * 主要操作就是在"扩展词"两边加上空格
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

		if (hasextendDict)//进行后处理
		{
			dict.postPrecess(words, posTags);
		}
	}

}
