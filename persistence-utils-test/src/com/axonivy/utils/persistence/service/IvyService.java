package com.axonivy.utils.persistence.service;

import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;

import com.axonivy.utils.persistence.Logger;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.runtime.IvyRuntime;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.workflow.IWorkflowSession;


/**
 * Services to support working with Ivy functions.
 */
public class IvyService {
	private static final Logger LOG = Logger.getLogger(IvyService.class);

	private IvyService() {
	}


	/**
	 * Just a shortcut to run code as system.
	 * 
	 * @param <T>
	 * @param callable
	 * @return
	 * @throws Exception
	 */
	public static <T> T executeAsSystem(Callable<T> callable) throws Exception {
		return Sudo.call(callable);
	}

	/**
	 * Get the Id of the current application.
	 * 
	 * @return
	 */
	public static long getApplicationId() {
		
		return IApplication.current().getId();
	}

	/**
	 * Find an ivy role.
	 * 
	 * @param rolename
	 * @return
	 */
	public static IRole findRole(String rolename) {
		return Ivy.wf().getSecurityContext().roles().find(rolename);
	}

	/**
	 * Find an ivy user.
	 * 
	 * @param username
	 * @return
	 */
	public static IUser findUser(String username) {
		return Ivy.wf().getSecurityContext().users().find(username);
	}

	/**
	 * Get a non-blank Ivy global variable as a String.
	 * 
	 * If the variable is not set or blank, then return default value. If the
	 * variable is set, then it is trimmed before returning.
	 * 
	 * @param name
	 * @param defValue
	 * @return
	 */
	public static String getNonBlankGlobalVariable(String name, String defValue) {
		String value = Ivy.var().get(name);
		if (StringUtils.isBlank(value)) {
			value = defValue;
		} else {
			value = value.trim();
		}
		return value;
	}

	/**
	 * Get the current session user.
	 * 
	 * @return
	 */
	public static IUser getSessionUser() {
		IUser user = null;
		IWorkflowSession session = Ivy.session();
		if(session != null) {
			user = session.getSessionUser();
		}
		if(user == null) {
			LOG.error("Could not determine current user for session {0}", session);
		}

		return user;
	}

	/**
	 * Get the current session username.
	 * 
	 * @return
	 */
	public static String getSessionUserName() {
		String userName = null;
		IWorkflowSession session = Ivy.session();
		if(session != null) {
			userName = session.getSessionUserName();
		}
		if(userName == null) {
			LOG.error("Could not determine current user for session {0}", session);
		}

		return userName;
	}

	/**
	 * Get the system user.
	 * 
	 * @return
	 */
	public static IUser getSystemUser() {
		IUser user = null;
		IWorkflowSession session = Ivy.session();
		if(session != null) {
			try {
				user = executeAsSystem(() -> session.getSecurityContext().users().system());
			} catch (Exception e) {
				LOG.error("Could not determine the system user.", e);
			}
		}
		return user;
	}

	/**
	 * Is the current session user the system user?
	 * 
	 * @return
	 */
	public static boolean isSystemUser() {
		return isSystemUser(getSessionUser());
	}

	/**
	 * Is the given user the system user?
	 * 
	 * @param user
	 * @return
	 */
	public static boolean isSystemUser(IUser user) {
		boolean result = false;
		if(user != null) {
			IUser systemUser = getSystemUser();

			if(systemUser != null) {
				result = systemUser.getSecurityMemberId() == user.getSecurityMemberId();
			}
		}
		return result;
	}

	/**
	 * Are we running in Designer?
	 * 
	 * Note: this function might be called by Logger so do not log here... :-)
	 * 
	 * @return
	 */
	public static boolean isDesigner() {
		return IvyRuntime.isDesigner();
	}
}
