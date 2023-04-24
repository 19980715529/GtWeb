package com.smallchill.core.meta;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.toolbox.ajax.AjaxResult;

/**
 * 增删改查aop
 */
public interface ICURD {
	/**
	 * 主表新增前操作
	 * 
	 * @param ac
	 */
	void saveBefore(AopContext ac);

	/**
	 * 主表新增后操作(事务内)
	 * 
	 * @param ac
	 */
	boolean saveAfter(AopContext ac);

	/**
	 * 新增全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	AjaxResult saveSucceed(AopContext ac);

	/**
	 * 主表修改前操作
	 * 
	 * @param ac
	 */
	void updateBefore(AopContext ac);

	/**
	 * 主表修改后操作(事务内)
	 * 
	 * @param ac
	 */
	boolean updateAfter(AopContext ac);

	/**
	 * 修改全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	AjaxResult updateSucceed(AopContext ac);

	/**
	 * 物理删除前操作
	 * 
	 * @param ac
	 */
	void removeBefore(AopContext ac);

	/**
	 * 物理删除后操作(事务内)
	 * 
	 * @param ac
	 */
	boolean removeAfter(AopContext ac);

	/**
	 * 物理删除全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	AjaxResult removeSucceed(AopContext ac);
	
	/**
	 * 逻辑删除前操作
	 * 
	 * @param ac
	 */
	void delBefore(AopContext ac);

	/**
	 * 逻辑删除后操作(事务内)
	 * 
	 * @param ac
	 */
	boolean delAfter(AopContext ac);
	
	/**
	 * 逻辑删除后操作(事务外)
	 * 
	 * @param ac
	 */
	AjaxResult delSucceed(AopContext ac);
	
	/**
	 * 主表还原前操作
	 * 
	 * @param ac
	 */
	void restoreBefore(AopContext ac);

	/**
	 * 主表还原后操作(事务内)
	 * 
	 * @param ac
	 */
	boolean restoreAfter(AopContext ac);
	
	/**
	 * 还原全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	AjaxResult restoreSucceed(AopContext ac);
}
