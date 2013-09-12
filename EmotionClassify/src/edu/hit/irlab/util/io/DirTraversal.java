package edu.hit.irlab.util.io;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 遍历目录，得到目录下的所有文件.
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class DirTraversal 
{

  /**
   * Run.
   * 
   * @param dir
   *          the dir
   * 
   * @return the list< string>
   */
  public static List<String> run(String dir) 
  {
    List<String> filelist = new LinkedList<String>();
    File file = new File(dir);
    run(file, filelist);
    return filelist;
  }

  /**
   * Run.
   * 
   * @param file
   *          the file
   * @param filelist
   *          the filelist
   */
  private static void run(File file, List<String> filelist) {
    if (file.canRead()) 
    {
      if (file.isDirectory()) 
      {
        String[] files = file.list();
        // an IO error could occur
        if (files != null) 
        {
          for (int i = 0; i < files.length; i++) 
          {
            run(new File(file, files[i]), filelist);
          }
        }
      } 
      else 
      {
        filelist.add(file.getAbsolutePath());
      }
    }
  }

  /**
   * The main method.
   * 
   * @param args
   *          the args
   */
  public static void main(String[] args) {
    String dir = "tmp";
    List<String> filelist = DirTraversal.run(dir);
    for (int i = 0; i < filelist.size(); i++) {
      System.out.println(filelist.get(i));
    }
  }

}
