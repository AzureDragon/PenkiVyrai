package addtionalControllers;

import java.io.Serializable;

import model.Authentication;
import model.Client;
import model.Employee;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.UserInfoService;
import services.UserInfoServiceImpl;



public class ProfileViewModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// services
	AuthenticationService authService = new AuthenticationServiceImpl();
	UserInfoService userInfoService = new UserInfoServiceImpl();

	// data for the view
	Employee currentEmployee;
	Client currentClient;

	/**
	 * Retrieve all known country names.
	 * 
	 * @return a list of country name
	 */
	
	@Init
	public void init() throws Exception {
		Authentication cre = authService.getUserCredential();
		if (cre.getEmployeeId() > 0)
			currentEmployee = userInfoService.findEmployee(cre);
		else
			currentClient = userInfoService.findClient(cre);

		if (currentEmployee == null && currentClient == null) {
			return;
		}
	}

	@Command
	// @Command annotates a command method
	@NotifyChange("currentUser")
	// @NotifyChange annotates data changed notification after calling this
	// method
	public void save() {
		Clients.showNotification("Jusų profilis atnaujintas");
	}

	@Command
	@NotifyChange("currentUser")
	public void reload() throws Exception {
		Authentication cre = authService.getUserCredential();
		if (cre.getEmployeeId() > 0)
			currentEmployee = userInfoService.findEmployee(cre);
		else
			currentClient = userInfoService.findClient(cre);
	}
}