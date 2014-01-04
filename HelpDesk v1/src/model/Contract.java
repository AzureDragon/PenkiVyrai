package model;

import java.sql.Date;

public class Contract {

	int id;
	String contractNumber;
	String name;
	int clientId;
	Date beginTime;
	Date endTime;

	public Contract(String contractNumber, String name, int clientId,
			Date beginTime, Date endTime) {
		super();
		this.contractNumber = contractNumber;
		this.name = name;
		this.clientId = clientId;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
