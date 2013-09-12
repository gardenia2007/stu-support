package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import util.Config;
import util.Resource;

public class IO {
	private static BufferedReader reader = null;
	private static PrintWriter writer = null;
	
	public static ArrayList<String> readFile(String inPath, String encoding)
	{
		ArrayList<String> list = new ArrayList<String>();
		try{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inPath) , encoding));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				list.add(line);
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static String readFileStr(String inPath, String encoding)
	{
		String content = "";
		try{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inPath) , encoding));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				content = content + line + "\n";
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return content;
	}
	
	public static HashSet<String> readFileSet(String inPath, String encoding)
	{
		ArrayList<String> list = readFile(inPath, encoding);
		HashSet<String> set = new HashSet<String>();
		set.addAll(list);
		return set;
	}
	
	/**
	 * The input format is => word + "\t" + index(frequency)
	 * @param inPath
	 * @param encoding
	 * @return
	 */
	public static HashMap<String, String> readMap(String inPath, String encoding)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		ArrayList<String> tmpList = readFile(inPath, encoding);
		for(String tmpLine: tmpList)
		{
			String[] set = tmpLine.split("\t");
			map.put(set[0], set[1]);
		}
		return map;
	}
	
	public static HashMap<String, Integer> readVocabulary(String inPath, String encoding)
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		ArrayList<String> tmpList = readFile(inPath, encoding);
		int index = 1;
		for(String tmpLine: tmpList)
		{
			String[] set = tmpLine.split("\t");
			map.put(set[0], index);
			index++;
		}
		return map;
	}
	
	public static void writeFile(String outPath, Collection<String> list, String encoding)
	{
		try{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(outPath), encoding)));
			for(String line: list)
			{
				writer.write(line + "\n");
			}
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writeIntFile(String outPath, Collection<Integer> list, String encoding)
	{
		try{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(outPath), encoding)));
			for(Integer line: list)
			{
				writer.write(line + "\n");
			}
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writeMap(String outPath, String encoding, HashMap<String, String> inListEmo) {
		try{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(outPath), encoding)));
			for(String text: inListEmo.keySet()) {
				String emotion = inListEmo.get(text);
				writer.println(text + "\t" + emotion);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	public static void appendFile(String outPath, Collection<String> list)
	{
		try{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(outPath, true), Config.getEncoding())));
			for(String line: list)
			{
				writer.println(line);
			}
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取dir文件夹下所有文件列表
	 * @param dir
	 */
	public static ArrayList<String> getFileNames(String dir){
		ArrayList<String> fileNames = new ArrayList<String>();
		File dirFile = new File(dir);
		File[] _files = dirFile.listFiles();
		for(File file: _files){
			if(file.isFile()){
				try {
					fileNames.add(file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileNames;
	}
	
	public static ArrayList<String> getDirNames(String dir){
		ArrayList<String> fileNames = new ArrayList<String>();
		File dirFile = new File(dir);
		File[] _files = dirFile.listFiles();
		for(File file: _files){
			if(file.isDirectory()){
				try {
					fileNames.add(file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileNames;
	}
	
	public static TreeMap<Integer, HashSet<String>> getOrderTreeMap(HashMap<String, Integer> hashMap){
		TreeMap<Integer, HashSet<String>> orderTreeMap = new TreeMap<Integer, HashSet<String>>();
		
		for(String key: hashMap.keySet()) {
			int value = hashMap.get(key);
			if(orderTreeMap.containsKey(value)) {
				HashSet<String> valueset = orderTreeMap.get(value);
				valueset.add(key);
			}else {
				HashSet<String> valueset = new HashSet<String>();
				valueset.add(key);
				orderTreeMap.put(value, valueset);
			}
		}
		
		return orderTreeMap;
	}
}
