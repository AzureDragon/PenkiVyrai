package model;

import java.util.Date;

public class Task {
	private Integer id;
	private String clientId;
	private String subject;
	private String description;

	private String type;
	private String status;
	private Date registered;
	private Date solveUntil;
	private Date solved; // i�spr�sta
	private int previousTask;
	private int serviceType; // paslaugos tipas
	private int taskEvaluation; // vertinimas
	private int receiveSource; // gavimo b�das
	private int delegateId; // atstovas
	private String solution;
	private int assigneeId; // paskyr�s darbuotojas
	private int receiverId; // priskirtas darbuotojas

	public Task(Integer id, String subject, String description,
			String type, String status, Date registered, Date solveUntil,
			Date solved, int previousTask, int serviceType, int taskEvaluation,
			int receiveSource, int delegateId, String solution, int assigneeId,
			int receiverId) {
		super();
		this.id = id;
		this.subject = subject;
		this.description = description;
		this.type = type;
		this.status = status;
		this.registered = registered;
		this.solveUntil = solveUntil;
		this.solved = solved;
		this.previousTask = previousTask;
		this.serviceType = serviceType;
		this.taskEvaluation = taskEvaluation;
		this.receiveSource = receiveSource;
		this.delegateId = delegateId;
		this.solution = solution;
		this.assigneeId = assigneeId;
		this.receiverId = receiverId;
	}

	public Task(int id, String subject, String description, String type,
			String status, String clientId, Date registered, int receiverId,
			Date solveUntil, int assigneeId) {
				
			this.id=id;
			this.subject=subject;
			this.description = description;
			this.type=type;
			this.status=status;
			this.setClientId(clientId);
			this.registered =registered;
			this.receiverId = receiverId;
			this.solveUntil=solveUntil;
			this.assigneeId = assigneeId;
		
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getSolveUntil() {
		return solveUntil;
	}

	public void setSolveUntil(Date solveUntil) {
		this.solveUntil = solveUntil;
	}

	public Date getSolved() {
		return solved;
	}

	public void setSolved(Date solved) {
		this.solved = solved;
	}

	public int getPreviousTask() {
		return previousTask;
	}

	public void setPreviousTask(int previousTask) {
		this.previousTask = previousTask;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getTaskEvaluation() {
		return taskEvaluation;
	}

	public void setTaskEvaluation(int taskEvaluation) {
		this.taskEvaluation = taskEvaluation;
	}

	public int getReceiveSource() {
		return receiveSource;
	}

	public void setReceiveSource(int receiveSource) {
		this.receiveSource = receiveSource;
	}

	public int getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(int delegateId) {
		this.delegateId = delegateId;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
