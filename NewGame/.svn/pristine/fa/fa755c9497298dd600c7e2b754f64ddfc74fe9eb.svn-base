package com.smallchill.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.google.CacheKey;
import com.smallchill.core.toolbox.kit.google.GoogleAuthenticator;
import com.smallchill.system.model.User;
import com.smallchill.system.model.google.GoogleDTO;
import com.smallchill.system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 获取缓存中的数据
     * @return
     */
	@Override
    public AjaxResult getData(){
        Map<String,Object> data = new HashMap<>();
        setData(CacheKey.REGISTER_USER_KEY,data);
        setData(CacheKey.TOKEN_KEY_LOGIN_KEY,data);
        return new AjaxResult().success(data);
    }

	@Override
    public void setData(String keyword,Map<String,Object> data){
    	CacheKit.put(SYS_CACHE, keyword, data);
    }

    /**
     * 生成Google 密钥
     * secret：密钥
     * secretQrCode：Google Authenticator 扫描条形码的内容
     * @param user
     * @return
     */
	@Override
    public AjaxResult generateGoogleSecret(User user){
        //Google密钥
        String randomSecretKey = GoogleAuthenticator.getRandomSecretKey();
        String googleAuthenticatorBarCode = GoogleAuthenticator.getGoogleAuthenticatorBarCode(randomSecretKey, user.getAccount(), ConstConfig.GOOGLE_URL);
        Map<String,Object> data = new HashMap<>();
        if(user.getGooglesecret()==null || user.getGooglesecret().equals("")) {
        	user.setGooglesecret(randomSecretKey);
    		Integer version = user.getVersion() != null ? user.getVersion() : 0;
    		user.setVersion(version + 1);
        	Blade.create(User.class).update(user);
        }
        //Google密钥
        data.put("secret",randomSecretKey);
        //用户二维码内容
        data.put("secretQrCode",googleAuthenticatorBarCode);
        return new AjaxResult().success(data);
    }


    /**
     * 绑定Google
     * @param dto
     * @param user
     * @return
     */
	@Override
    public AjaxResult bindGoogle(GoogleDTO dto, User user, HttpServletRequest request){
        if(!StringUtils.isEmpty(user.getGooglesecret())){
        	AjaxResult error = new AjaxResult();
        	error.setCode(10);
        	 return error.addError("Google已绑定，不能重复绑定");
        }
        boolean isTrue = GoogleAuthenticator.check_code(dto.getSecret(), dto.getCode(), System.currentTimeMillis());
        if(!isTrue){
        	 return new AjaxResult().addError("Google验证码不匹配");
        }
        user.setGooglesecret(dto.getSecret());
        Blade.create(User.class).update(user);
        return new AjaxResult().success("绑定成功");
    }

	@Override
	public AjaxResult googleLogin(Long code, User user, HttpServletRequest request) {
		if(StringUtils.isEmpty(user.getGooglesecret())){
			return new AjaxResult().addError("Google未绑定，请先进行绑定");
        }
        boolean isTrue = GoogleAuthenticator.check_code(user.getGooglesecret(), code, System.currentTimeMillis());
        if(!isTrue){
        	return new AjaxResult().addError("Google验证码不匹配");
        }
        return new AjaxResult().success("验证成功");
	}

	@Override
	public AjaxResult generateGoogleSecretWithoutPassword(String account) {
        //Google密钥
        String randomSecretKey = GoogleAuthenticator.getRandomSecretKey();
        String googleAuthenticatorBarCode = GoogleAuthenticator.getGoogleAuthenticatorBarCode(randomSecretKey, account, ConstConfig.GOOGLE_URL);
        Map<String,Object> data = new HashMap<>();
        //Google密钥
        data.put("secret",randomSecretKey);
        //用户二维码内容
        data.put("secretQrCode",googleAuthenticatorBarCode);
        return new AjaxResult().success(data);
	}
}
