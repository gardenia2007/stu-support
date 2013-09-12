package edu.hit.irlab.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 写出到文件
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class IOFileWriter 
{
  private PrintWriter outs;

  /**
   * Initial the file path Because user may write many lines within one file so
   * we should not recreate a class for using every time.
   * 
   * @param path
   *          file path
   */
  public IOFileWriter(String path) 
  {
    File file = new File(path);
    if (file.exists()) 
    {
      file.delete();
    }
    try 
    {
      String encoding = "UTF-8";
      outs = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(path), encoding)));
    } catch (IOException e) 
    {
      e.printStackTrace();
    }
  }

  /**
   * Write a line in a outs.
   * 
   * @param linenum
   * @return line content
   */
  public void writeLine(String line) 
  {
    outs.println(line);
  }

  /**
   * Close the input stream
   */
  public void close() 
  {
    if (outs != null)
      outs.close();
    outs = null;
  }

  public static void main(String[] args) 
  {
    IOFileWriter fw = new IOFileWriter("F:\\temp\\test.txt");
    fw.writeLine("a");
    fw.close();
  }

}
