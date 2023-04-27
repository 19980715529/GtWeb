package com.smallchill.system.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.system.model.User;
import com.smallchill.system.model.google.GoogleDTO;

public interface UserService extends ConstCache {

    /**
     * 获取缓存中的数据
     * @return
     */
    public AjaxResult getData();

    public void setData(String keyword,Map<String,Object> data);

    /**
     * 生成Google 密钥
     * secret：密钥
     * secretQrCode：Google Authenticator 扫描条形码的内容
     * @param user
     * @return
     */
    public AjaxResult generateGoogleSecret(User user);
    public AjaxResult generateGoogleSecretWithoutPassword(String account);


    /**
     * 绑定Google
     * @param dto
     * @param user
     * @return
     */
    public AjaxResult bindGoogle(GoogleDTO dto, User user, HttpServletRequest request);
    /**
     * Google登录
     * @param code
     * @param user
     * @return
     */
    public AjaxResult googleLogin(Long code,User user,HttpServletRequest request);
}
