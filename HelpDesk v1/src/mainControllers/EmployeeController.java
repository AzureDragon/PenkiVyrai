package mainControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import model.Clasifiers;
import model.Employee;
import model.Task;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;

import services.EmployeeService;
import services.EmployeeServiceImpl;



public class EmployeeController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmployeeService employeeService;
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	@SuppressWarnings("rawtypes")
	private EventQueue eq;
	@Wire
	private Combobox darbuotojas;
	@Wire
	private Datebox isprestiIkiDateBox;

	Task kreipinys;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		eq = EventQueues.lookup("interactive", EventQueues.DESKTOP, true);
		eq.subscribe(new EventListener() {
			public void onEvent(Event event) throws Exception {
				employeeService = new EmployeeServiceImpl();
				kreipinys = (Task) event.getData();
				new ListModelList<Employee>(employeeService.getEmployeeList());
				darbuotojas.setModel(new ListModelList<Employee>(employeeService.getEmployeeList()));
			}
		});

	}

	@Listen("onClick = #priskirtiDarbuotojaButton")
	public void priskirtiKreipiniDarbuotjui() throws Exception {
		if (!darbuotojas.getValue().equals("")) {
			SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd");
			Class.forName("com.mysql.jdbc.Driver");
			connect = Clasifiers.getConnection();
			preparedStatement = connect
					.prepareStatement("UPDATE task SET ReceiverId ="
							+ Clasifiers
									.getEmployeeIdByNameAndSurname(darbuotojas
											.getValue())
							+ ", SolveUntil='"
							+ formatedDate.format(isprestiIkiDateBox.getValue())
							+ "' WHERE ID =" + kreipinys.getId() + ";");
			System.out.println("UPDATE task SET ReceiverId ="
					+ Clasifiers.getEmployeeIdByNameAndSurname(darbuotojas
							.getValue()) + ", SolveUntil="
					+ formatedDate.format(isprestiIkiDateBox.getValue())
					+ " WHERE ID =" + kreipinys.getId() + ";");
			preparedStatement.executeUpdate();
			System.out.println("pakomkitinau");

			System.out.println("Priskyriau");
			Event closeEvent = new Event("onClose", this.getSelf(), null);
			Events.postEvent(closeEvent);

		} else {
			darbuotojas.setErrorMessage("Pasirinkite darbuotoją.");
			darbuotojas.getErrorMessage();
		}

		System.out.println("Kreipinio idas" + kreipinys.getId());
		;
		System.out.println(darbuotojas.getValue());
		System.out.println(darbuotojas.getValue());

	}

}
