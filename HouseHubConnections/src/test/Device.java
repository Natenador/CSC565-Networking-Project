package test;

//This class will be used as a filler for the app's device class for testing
public class Device {

	private String deviceName;
	private String ipAddress;
	private int portNumber;
	
	public Device(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
}
