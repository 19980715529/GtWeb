package com.smallchill.core.toolbox.file;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.smallchill.core.constant.Cst;
import com.smallchill.core.toolbox.kit.DateKit;

public class BladeFile {
	/**
	 * 上传文件在附件表中的id
	 */
	private Object fileId;
	
	/**
	 * 上传文件
	 */
	private MultipartFile file;
	
	/**
	 * 上传分类文件夹
	 */
	private String dir;
	
	/**
	 * 上传物理路径
	 */
	private String uploadPath;
	
	/**
	 * 上传虚拟路径
	 */
	private String uploadVirtualPath;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 真实文件名
	 */
	private String originalFileName;

	public BladeFile() {
		
	}

	public BladeFile(MultipartFile file, String dir) {
		this.dir = dir;
		this.file = file;
		this.fileName = file.getName();
		this.originalFileName = file.getOriginalFilename();
		this.uploadPath = BladeFileKit.formatUrl(File.separator + Cst.me().getUploadRealPath() + File.separator + dir + File.separator + DateKit.getDays() + File.separator + this.originalFileName);
		this.uploadVirtualPath = BladeFileKit.formatUrl(Cst.me().getUploadCtxPath().replace(Cst.me().getContextPath(), "") + File.separator + dir + File.separator + DateKit.getDays() + File.separator + this.originalFileName);
	}

	public BladeFile(MultipartFile file, String dir, String uploadPath, String uploadVirtualPath) {
		this(file, dir);
		if (null != uploadPath){
			this.uploadPath = BladeFileKit.formatUrl(uploadPath);
			this.uploadVirtualPath = BladeFileKit.formatUrl(uploadVirtualPath);
		}
	}

	/**   
	 * 图片上传
	*/
	public void transfer() {
		transfer(Cst.me().isCompress());
	}

	/**   
	 * 图片上传
	 * @param compress 是否压缩
	*/
	public void transfer(boolean compress) {
		IFileProxy fileFactory = FileProxyManager.me().getDefaultFileProxyFactory();
		this.transfer(fileFactory, compress);
	}
	
	/**   
	 * 图片上传
	 * @param fileFactory 文件上传工厂类
	 * @param compress 是否压缩
	*/
	public void transfer(IFileProxy fileFactory, boolean compress) {
		try {
			File file = new File(uploadPath);
			
			if(null != fileFactory){
				String [] path = fileFactory.path(file, dir);
				this.uploadPath = path[0];
				this.uploadVirtualPath = path[1].replace(Cst.me().getContextPath(), "");
				file = fileFactory.rename(file, path[0]);
			}
			
			File pfile = file.getParentFile();
			if (!pfile.exists()) {
				pfile.mkdirs();
			}
			
			this.file.transferTo(file);
			
			if (compress) {
				fileFactory.compress(this.uploadPath);				
			}
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public Object getFileId() {
		if(null == this.fileId) {
			IFileProxy fileFactory = FileProxyManager.me().getDefaultFileProxyFactory();
			this.fileId = fileFactory.getFileId(this);
		}
		return fileId;
	}
	
	public void setFileId(Object fileId) {
		this.fileId = fileId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadVirtualPath() {
		return uploadVirtualPath;
	}

	public void setUploadVirtualPath(String uploadVirtualPath) {
		this.uploadVirtualPath = uploadVirtualPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

}
