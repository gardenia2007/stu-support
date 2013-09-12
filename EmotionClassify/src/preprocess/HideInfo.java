package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;

import edu.hit.irlab.util.io.IOUtils;

import util.Config;

public class HideInfo {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws IOException {
//		String dirpath = Config.getResourceDir() + "emoticon/";
//		hideDir(dirpath);
		
		File f = new File("resource/nlp_data/extendDict/extendDict.dat");
		hideFile(f);
	}
	
	public static void hideDir(String dirpath) {
		try {
			File dir = new File(dirpath);
			BufferedReader br = null;
			ArrayList<String> temp = null;
			if(dir.isDirectory()) { //is directory.
				File[] files = dir.listFiles();
				for(File f: files) {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"));
					temp = new ArrayList<String>();
					String line = null;
					while((line = br.readLine()) != null) {
						temp.add(line);
					}
					br.close();
					System.err.println(f);
					System.out.println(temp);
					IOUtils.writeObjectToFile(temp, f.getName() + ".dat");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void hideFile(File f) {
		try {
			BufferedReader br = null;
			ArrayList<String> temp = null;
	
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"));
			temp = new ArrayList<String>();
			String line = null;
			while((line = br.readLine()) != null) {
				temp.add(line);
			}
			br.close();
			System.err.println(f);
			System.out.println(temp);
			IOUtils.writeObjectToFile(temp, f.getName() + ".dat");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
