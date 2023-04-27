package com.smallchill.core.toolbox.file;

import java.io.File;

public interface IFileProxy {
	
	/**
	 * 返回路径[物理路径][虚拟路径]
	 * @param dir
	 * @param file
	 * @return
	 */
	String [] path(File file, String dir);

	/**
	 * 文件重命名策略
	 * @param path
	 * @param file
	 * @return
	 */
	File rename(File f, String path);
	
	/**
	 * 获取入库id
	 * @return
	 */
	Object getFileId(BladeFile bf);
	
	/**   
	 * 图片压缩
	*/
	void compress(String path);
	
}
