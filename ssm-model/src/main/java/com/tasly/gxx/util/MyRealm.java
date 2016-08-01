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
		User user = this.userService.findUserByLoginName(currentUserName);
		if (null != user) {
			if (!CollectionUtils.isEmpty(user.getRoleList())) {
				for (Role role : user.getRoleList()) {
					roleList.add(role.getRolename());
					if (!CollectionUtils.isEmpty(role.getPermissionList())) {
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

		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);

		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		AuthenticationInfo authcInfo = null;
		User user = userService.findUserByLoginName(token.getUsername());
		if (null != user) {
			String password = new String(token.getPassword());
			if (password.equals(user.getPassword())) {
				authcInfo = new SimpleAuthenticationInfo(user.getUsername(),
						user.getPassword(), this.getName());
				this.setSession("currentUser", user);
//				doGetAuthorizationInfo(SecurityUtils.getSubject()
//						.getPrincipals());
				return authcInfo;
			}

		}
		return authcInfo;
	}


	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out
					.println("Session超时时间[" + session.getTimeout() + "]");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
