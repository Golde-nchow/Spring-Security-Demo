package com.qq.login.holder;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.UserIdSource;

/**
 * @author laijiaxiang
 *
 */
public class CurrentUserHolder implements UserIdSource {

	/**
	 * 获取用户 id
	 * 由于没有 SocialAutoConfigurerAdapter，所以只能自己生成该方法
	 */
	@Override
	public String getUserId() {
		String userId = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if(context != null) {
			Object principal = context.getAuthentication().getPrincipal();
			if(principal instanceof UserDetails) {
				userId = ((UserDetails)principal).getUsername();
			}
		}
		if(userId == null || "".equals(userId)) {
			userId = "1234567890";
		}
		return userId;
	}

}
