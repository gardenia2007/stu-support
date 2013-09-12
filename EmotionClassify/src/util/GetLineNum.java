package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GetLineNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String infile = Config.getDataDir() + "cmt.emo";
		String encoding = "utf8";
		getLineNum(infile, encoding);
	}

	private static void getLineNum(String infile, String encoding) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(infile), encoding));
			String line = null;
			int num = 0;
			while(null != reader.readLine()) {
				++num;
			}
			System.out.println("文档条目数为：" + num);
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
