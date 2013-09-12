package edu.hit.irlab.nlp.tools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.hit.irlab.util.io.IOFileReader;

/**
 * ���ڷִ�ģ���޷������簢��������Щ�����պ�Ӣ���ַ���������Ҫ�Դ�����ľ���
 * ���б������
 * 
 * @author Fandy Wang
 * @date 2010.07.18
 * @version 0.1
 */
public class IllegalCharacterFilter {
  
  public static String filter(String content) {
    StringBuilder result = new StringBuilder();
    String regEx = "[\\u3000-\\u9fff]|[\\u0000-\\u00ff]";
    Pattern p = Pattern.compile(regEx);
    for( int i=0; i < content.length(); i++) {
      Matcher m = p.matcher(content.substring(i, i+1));
      if( m.find()) {
        result.append(content.charAt(i));
      }
    }
    return result.toString();
  }
 
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    List<String> lines = IOFileReader.readlines("test.txt", "utf-8");
    for( String line : lines ) {
      System.out.println(IllegalCharacterFilter.filter(line));
    }
   }

}
