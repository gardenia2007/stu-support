package edu.hit.irlab.nlp.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import edu.hit.irlab.util.ds.tree.RadixTree;
import edu.hit.irlab.util.ds.tree.RadixTreeImpl;
import edu.hit.irlab.util.io.DirTraversal;
import edu.hit.irlab.util.io.IOFileReader;
import edu.hit.irlab.util.io.IOUtils;

/**
 * ���� "�������ƥ��" ʶ�� "��չ�ʱ�" �дʵı߽�.
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class FMMProcessor
{

	/** �ʱ�. */
	private RadixTree<String> extendDictTrie = new RadixTreeImpl<String>();
	/** �ʱ�߽磺<��ʼλ�ã��ַ���> */
	private HashMap<Integer, String> dictBound;

	/** һ���ʵ�����ַ��� */
	private static int kLetterMaxLength = 16;

	private String resPathName = "e:/stu-support/EmotionClassify/resource/nlp_data/extendDict/";

	public FMMProcessor()
	{
		initDict();
	}

	public FMMProcessor(String extendDictPathName)
	{
		this.resPathName = extendDictPathName;
		initDict();
	}

	private void initDict()
	{
		List<String> fileList = DirTraversal.run(this.resPathName);
		for (String file : fileList)
		{
//			List<String> dict = IOFileReader.readlines(file, "UTF-8");
			List<String> dict = null;
			try {
				dict = (List<String>) IOUtils.readObjectFromFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (String term : dict)
			{
				int pos = term.lastIndexOf(' ');
				// System.out.println(term);
				if (pos == -1)
				{
					if (!extendDictTrie.contains(term) && term.length() > 1)
					{
						extendDictTrie.insert(term, "n");
					}
					// System.err.println(term);
					// continue;
				}
				else
				{
					String w = term.substring(0, pos);
					if (!extendDictTrie.contains(w) && w.length() > 1)
					{
						extendDictTrie.insert(w, term.substring(pos + 1));
					}
				}
			}
		}
		// extendDictTrie.print();
	}

	/**
	 * ������չ�ʱ�Ծ��ӽ���Ԥ���������ش�������¾���. ��Ҫ������������չ�����߼��Ͽո�.
	 * 
	 * @param sentence
	 *            the sentence
	 * 
	 * @return the string
	 */
	public String preProcess(String sentence)
	{
		sentence = sentence.replaceAll("/|\\s+", " "); // ɾ������Ŀո�
		dictBound = new HashMap<Integer, String>();

		StringBuilder newSentence = new StringBuilder();
		int nospaceID = 0;
		for (int i = 0; i < sentence.length(); ++i)
		{
			// if( isLetterOrDigit(sentence.charAt(i)) ) {
			// int j;
			// for( j=i; j < sentence.length() &&
			// isLetterOrDigit(sentence.charAt(j));
			// j++ ) {
			// newSentence.append(sentence.charAt(j));
			// if (sentence.charAt(j) != ' ')
			// nospaceID++;
			// }
			// i = j-1;
			// continue;
			// }

			int end = i + kLetterMaxLength;
			if (end > sentence.length()) end = sentence.length();
			String curSent = sentence.substring(i, end);
			String tmp = extendDictTrie.searchLongestMatch(curSent);
			if (tmp != null && tmp.length() > 0 && !isLetterOrDigits(tmp))
			{
				i += tmp.length() - 1;
				dictBound.put(nospaceID, tmp);
				nospaceID += tmp.replaceAll(" ", "").length();

				newSentence.append(" ");
				newSentence.append(tmp);
				newSentence.append(" ");
			}
			else
			{
				newSentence.append(sentence.charAt(i));
				if (sentence.charAt(i) != ' ') nospaceID++;
			}
		}

		return newSentence.toString().replaceAll("\\s+", " ");
	}

	/**
	 * �ַ����Ƿ���ȫ��Ӣ����ĸ���������
	 * 
	 * @param str
	 * @return
	 */
	private boolean isLetterOrDigits(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || ch == '.' || ch == '-' || ch == ' '))
				return false;
		}
		return true;
	}

	/**
	 * �����ϲ����зֵ���չ�ʣ����޸����ǩ.
	 * 
	 * @param words
	 *            the words
	 * @param posTags
	 *            the pos
	 */
	public void postPrecess(Vector<String> words, Vector<String> posTags)
	{
		if (dictBound.size() == 0) return; // ������û�г�����չ�ʱ��еĴ�

		Vector<String> tmpWords = new Vector<String>(words);
		Vector<String> tmpPos = new Vector<String>(posTags);
		words.clear();
		posTags.clear();

		int id = 0;
		for (int i = 0, j; i < tmpWords.size(); ++i)
		{
			String tmp = dictBound.get(id);
			if (tmp != null)
			{
				int len = tmp.replaceAll(" ", "").length();
				for (j = 0; len > 0; len -= tmpWords.get(i + j).length(), id += tmpWords
						.get(i + j).length(), ++j)
				{
				}

				words.add(tmp);
				// TODO:
				if (j > 1)
				{
					posTags.add(extendDictTrie.find(tmp));
				}
				else
				{
					posTags.add(tmpPos.get(i));
				}

				i += j - 1;
			}
			else
			{
				words.add(tmpWords.get(i));
				posTags.add(tmpPos.get(i));
				id += tmpWords.get(i).length();
			}
		}
	}

	/**
	 * �����ϲ����зֵ���չ��.
	 * 
	 * @param words
	 *            the words
	 */
	public void postPrecess(Vector<String> words)
	{
		if (dictBound.size() == 0) return; // ������û�г�����չ�ʱ��еĴ�
		// System.out.println("post:" + words);
		Vector<String> tmpWords = new Vector<String>(words);
		words.clear();

		int id = 0;
		for (int i = 0, j; i < tmpWords.size(); ++i)
		{
			String tmp = dictBound.get(id);
			if (tmp != null)
			{
				int len = tmp.replaceAll(" ", "").length();
				for (j = 0; len > 0; len -= tmpWords.get(i + j).length(), id += tmpWords
						.get(i + j).length(), ++j)
				{
				}

				words.add(tmp);
				i += j - 1;
			}
			else
			{
				words.add(tmpWords.get(i));
				id += tmpWords.get(i).length();
			}
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the args
	 */
	public static void main(String[] args)
	{
		FMMProcessor fmm = new FMMProcessor();
		System.out
				.println(fmm
						.preProcess("һ���������������ó�����R&B���ʿ�֣����ֳ����ͳ�����Janson Mraz�ġ�I��m yours������������������ҥ���������µ�����Ҳ����һ��ζ����"));

		System.out.println(fmm.preProcess("������-���������������ʲɲ�������˶�ѧУ��ͬ��ʦ��� ��"));
	}

}
