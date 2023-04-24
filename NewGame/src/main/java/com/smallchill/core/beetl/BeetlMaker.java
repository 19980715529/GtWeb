package com.smallchill.core.beetl;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.FileKit;

/**
 * Beetl静态化生成工具类
 */
public class BeetlMaker {

	/**
	 * 生成文件
	 * 
	 * @param tlPath 模板路径
	 * @param paras 参数
	 * @param filePath  文件保存路径
	 */
	public static void makeFile(String tlPath, Map<String, Object> paras, String filePath) {
		makeFile(tlPath, paras, filePath, CharsetKit.UTF_8);
	}
	
	/**
	 * 生成文件
	 * 
	 * @param tlPath 模板路径
	 * @param paras 参数
	 * @param filePath  文件保存路径
	 * @param charsetName  编码
	 */
	public static void makeFile(String tlPath, Map<String, Object> paras, String filePath, String charsetName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), charsetName));
			BeetlTemplate.buildTo(FileKit.readString(tlPath, charsetName), paras, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
