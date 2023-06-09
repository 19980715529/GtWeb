package com.smallchill.core.toolbox.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.ImageKit;
import com.smallchill.system.model.Attach;

public class DefaultFileProxyFactory implements IFileProxy {

	@Override
	public File rename(File f, String path) {
		File dest = new File(path);
		f.renameTo(dest);
		return dest;
	}

	@Override
	public String [] path(File f, String dir) {
		//避免网络延迟导致时间不同步
		long time = System.currentTimeMillis();
		
		StringBuilder uploadPath = new StringBuilder()
		.append(getFileDir(dir, Cst.me().getUploadRealPath()))
		.append(time)
		.append(getFileExt(f.getName()));
		
		StringBuilder virtualPath = new StringBuilder()
		.append(getFileDir(dir, Cst.me().getUploadCtxPath()))
		.append(time)
		.append(getFileExt(f.getName()));
		
		return new String [] {BladeFileKit.formatUrl(uploadPath.toString()), BladeFileKit.formatUrl(virtualPath.toString())};
	}
	
	@Override
	public Object getFileId(BladeFile bf) {
		Attach attach = new Attach();
		ShiroUser user = ShiroKit.getUser();
		Object creater = (null == user) ? 0 : user.getId();
		attach.setCreater(Func.toInt(creater));
		attach.setCreatetime(new Date());
		attach.setName(bf.getOriginalFileName());
		attach.setStatus(1);
		attach.setUrl(bf.getUploadVirtualPath());
		return Blade.create(Attach.class).saveRtStrId(attach);
	}
	
	/**
	 * 获取文件后缀
	 */
	public static String getFileExt(String fileName) {
		if (fileName.indexOf(".") == -1)
			return ".jpg";
		else 
			return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	/**
	 * 获取文件保存地址
	 * @param saveDir
	 * @return
	 */
	public static String getFileDir(String dir, String saveDir) {
		StringBuilder newFileDir = new StringBuilder();
		newFileDir.append(saveDir)
				.append(File.separator).append(dir).append(File.separator).append(DateKit.getDays())
				.append(File.separator);
		return newFileDir.toString();
	}


	/**
	 * 图片压缩
	 * @param path 文件地址
	 * @return
	 */
	public void compress(String path) {
		try {
			ImageKit.zoomScale(ImageKit.readImage(path), new FileOutputStream(new File(path)), null, Cst.me().getCompressScale().doubleValue(), Cst.me().isCompressFlag());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
