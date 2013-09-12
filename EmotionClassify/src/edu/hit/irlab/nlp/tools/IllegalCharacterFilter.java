package edu.hit.irlab.nlp.tools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.hit.irlab.util.io.IOFileReader;

/**
 * 由于分词模块无法处理如阿拉伯文这些非中日韩英文字符，所以需要对待处理的句子
 * 进行编码过滤
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
