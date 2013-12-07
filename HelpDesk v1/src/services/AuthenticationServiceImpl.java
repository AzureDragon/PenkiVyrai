package services;

import java.io.Serializable;

import model.Authentication;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;


public class AuthenticationServiceImpl implements AuthenticationService,
		Serializable {
	private static final long serialVersionUID = 1L;

	UserInfoService userInfoService = new UserInfoServiceImpl();

	public Authentication getUserCredential() {
		
		Session sess = Sessions.getCurrent();
		Authentication cre = (Authentication) sess.getAttribute("Authentication");

		return cre;
	}

	@Override
	public boolean login(String nm, String pd) throws Exception {
		
		Authentication u = userInfoService.findLogin(nm);
		if (u == null || !u.getPassword().equals(pd)) {
			return false;
		}

		Session sess = Sessions.getCurrent();
		sess.setAttribute("Authentication", u);

		return true;

	}

	@Override
	public void logout() {
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("Authentication");
	}

	public boolean isLoggedIn() {
		Session sess = Sessions.getCurrent();
		Authentication cre = (Authentication) sess.getAttribute("Authentication");

		if (cre != null)
			return true;
		else
			return false;
	}
}