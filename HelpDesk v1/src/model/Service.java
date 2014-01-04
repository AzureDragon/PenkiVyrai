package model;

public class Service {
	
	int id;
	String name;
	String serviceCode;
	String description;
	int incidentTime;
	int requestTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIncidentTime() {
		return incidentTime;
	}
	public void setIncidentTime(int incidentTime) {
		this.incidentTime = incidentTime;
	}
	public int getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}


}
