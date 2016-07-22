package com.tasly.gxx.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tasly.gxx.domain.Permission;
import com.tasly.gxx.domain.Role;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String currentUserName = (String) super
				.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		// 从数据库中获取当前登录用户的详细信息
		User user = this.userService.findUserByLoginName(currentUserName);
		if (null != user) {
			// 实体类User中包含有用户角色的实体类信息
			if (!CollectionUtils.isEmpty(user.getRoleList())) {
				// 获取当前登录用户的角色
				for (Role role : user.getRoleList()) {
					roleList.add(role.getRolename());
					// 实体类Role中包含有角色权限的实体类信息
					if (!CollectionUtils.isEmpty(role.getPermissionList())) {
						// 获取权限
						for (Permission pmss : role.getPermissionList()) {
							if (!StringUtils.isEmpty(pmss.getPermissionname())) {
								permissionList.add(pmss.getPermissionname());
							}
						}
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);

		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		User user = userService.findUserByLoginName(token.getUsername());
		if (null != user) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
					user.getUsername(), user.getPassword(), this.getName());
			this.setSession("currentUser", user);
			doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
			return authcInfo;
		} else {
			return null;
		}

	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out
					.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
