package mainControllers;

import model.Task;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;


public class AssignEmployeeController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private EventQueue eq;
	@Wire
	private Menuitem priskirtiKreipiniDarbuotojuiButton;
	@Wire
	public Listbox taskListbox;

	@SuppressWarnings("unchecked")
	@Listen("onClick = #priskirtiKreipiniDarbuotojuiButton")
	public void priskirtiKreipini() {

		Task selected = taskListbox.getSelectedItem().getValue();
		eq = EventQueues.lookup("interactive", EventQueues.DESKTOP, false);
		eq.publish(new Event("onButtonClick",
				priskirtiKreipiniDarbuotojuiButton, selected));

	}

}
