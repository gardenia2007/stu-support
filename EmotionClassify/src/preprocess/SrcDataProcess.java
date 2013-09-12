package preprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Config;

/**
 * 新项目的新数据初步处理，可自己定义 
 */
public class SrcDataProcess {
	
	public static void main(String[] args) {
		String srcfile = Config.getDataDir() + "comment_34.out.rg";
		String indexfile = Config.getDataDir() + "cmt.index";
		String commentfile = Config.getDataDir() + "cmt.cont";
		getCont(srcfile, commentfile, indexfile);
	}
	
	private static void normalize(String srcfile, String normalfile) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcfile), Config.getEncoding()));
			writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(normalfile), Config.getEncoding()));
			String line = null;
			String cmt = null;
			String indexStr = "cid:";
			while((line = reader.readLine()) != null) {
				int index = line.indexOf(indexStr);
				while(index != -1) {
					String restline = line.substring(indexStr.length());
					int restindex = restline.indexOf(indexStr);
					if(restindex == -1) {
						writer.println(line);
						break;
					} else {
						String fstline = line.substring(index, restindex + indexStr.length());
						writer.println(fstline);
						line = line.substring(restindex + indexStr.length());
						index = 0;
					}
				}
			}
			reader.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getCont(String srcfile, String commentfile, String indexfile) {
		BufferedReader reader = null;
		PrintWriter writer_cont = null;
		PrintWriter writer_index = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcfile), Config.getEncoding()));
			writer_cont = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(commentfile), Config.getEncoding()));
			writer_index = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(indexfile), Config.getEncoding()));
			String line = null;
			String cmt = null;
			String indexStr = "con:";
			String sidp = "sid:([0-9]+),v:";
			Pattern p = Pattern.compile(sidp);
			while((line = reader.readLine()) != null) {
				if(!line.startsWith("cid:")) {
					System.err.println(line);
					continue;
				}
				
				Matcher matcher = p.matcher(line);
				String sid = null;
				while(matcher.find()) {
					sid = matcher.group(1); //get the last sid.
				}
				
				cmt = line.substring(line.lastIndexOf(indexStr) + indexStr.length()); //get the last cmt.
				cmt = cmt.replaceAll("\\s+", " ").trim();
				writer_index.println("sid:" + sid);
				writer_cont.println(cmt);
			}
			reader.close();
			writer_cont.close();
			writer_index.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
