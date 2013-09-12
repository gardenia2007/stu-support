package edu.hit.irlab.util.io;

import java.io.File;

/**
 * ���Ŀ¼�ĺϷ���
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.07.01
 * @version 0.1
 */
public class DirValidate 
{

  /**
   * ����ļ�Ŀ¼�ĺϷ���
   * 
   * @param dirPath
   */
  public static boolean validate(String dirPath) 
  {
    File dir = new File(dirPath);
    if (!dir.exists() || !dir.canRead()) 
    {
      System.err
          .println("Oops! The directory '"
              + dir.getAbsolutePath()
              + "' does not exist or is not readable, please check the path.");
      return false;
    }
    if (!dir.isDirectory()) 
    {
      System.err.println("Oops! The path '" + dir.getAbsolutePath()
          + "' is not a directory.");
      return false;
    }
    
    return true;
  }
}
