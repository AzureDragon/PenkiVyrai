package model;

public class Service {
	
	public Service(String name, String serviceCode, int incidentTime,
			int requestTime) {
		super();
		this.name = name;
		this.serviceCode = serviceCode;
		this.incidentTime = incidentTime;
		this.requestTime = requestTime;
	}
	public Service(String name) {
		super();
		this.name = name;
	
	}
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
