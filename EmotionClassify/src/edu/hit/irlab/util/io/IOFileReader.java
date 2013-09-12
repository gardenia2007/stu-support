package edu.hit.irlab.util.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * 读取文件
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class IOFileReader 
{
  /**
   * 按照文件指定的字符编码读取文件内容(按字节读入)，避免因编码不同导致的乱码问题.
   * 
   * @param filepath
   *          欲读取的文件
   * @param encoding
   *          编码方式，如：UTF-8
   * 
   * @return 存储文件内容的ArrayList<String>
   * 
   * @see http
   *      ://hi.baidu.com/fandywang_jlu/blog/item/e202c31962679372dab4bdb8. html
   */
  public static ArrayList<String> readlines(String filepath, String encoding) 
  {
    ArrayList<String> veclines = new ArrayList<String>();
    try 
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(filepath), encoding));

      String aline = null;
      while ((aline = br.readLine()) != null) 
      {
        veclines.add(aline);
      }

      br.close();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }

    return veclines;
  }

  /**
   * The main method.
   * 
   * @param args
   *          the arguments
   */
  public static void main(String[] args) 
  {
    String filepath = "./data/traindata.string.txt";
    ArrayList<String> lines = IOFileReader.readlines(filepath, "UTF-8");

    Iterator<String> iter = lines.iterator();
    while (iter.hasNext()) 
    {
      String sent = iter.next();
      ArrayList<String[]> sents = new ArrayList<String[]>();

      StringBuilder s = new StringBuilder();
      for (int i = 0; i < sent.length(); ++i) 
      {
        s.append(sent.charAt(i));
        if (sent.charAt(i) == '，' && i > 0 && sent.charAt(i - 1) == ' '
            && i + 1 < sent.length() && sent.charAt(i + 1) == ' ') 
        {
          sents.add(s.toString().split(" "));
          // System.out.println("$$" + s.toString());
          s = new StringBuilder();
          ++i;
        }
      }
      if (s.length() > 0) 
      {
        // System.out.println("$$" + s.toString());
        sents.add(s.toString().split(" "));
      }

      System.out.println("##" + sents.toString());
    }
  }

}
