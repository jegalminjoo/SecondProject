import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class PlaneSchedule implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String deptDay; //비행날짜
	private String deptPlace; //출발지
	private String arrvPlace; //도착지
	private String deptTime; //출발시간
	
	List<JButton>[] seatV = new List[3]; //G좌석을 담을 리스트의 배열
	List<JButton>[] seatG = new List[3]; //S좌석을 담을 리스트의 배열
	List<JButton>[] seatS = new List[3]; //D좌석을 담을 리스트의 배열
	
	List<Boolean>[] getSeatSelectedV = new List[3]; //G좌석의 예약 유무를 담을 리스트의 배열
	List<Boolean>[] getSeatSelectedG = new List[3]; //S좌석의 예약 유무를 담을 리스트의 배열
	List<Boolean>[] getSeatSelectedS = new List[3]; //D좌석의 예약 유무를 담을 리스트의 배열
	public PlaneSchedule(String deptDay, String deptPlace, String arrvPlace, String deptTime, List<JButton>[] seatV,
			List<JButton>[] seatG, List<JButton>[] seatS) {
		super();
		this.deptDay = deptDay;
		this.deptPlace = deptPlace;
		this.arrvPlace = arrvPlace;
		this.deptTime = deptTime;
		this.seatV = seatV;
		this.seatG = seatG;
		this.seatS = seatS;
		this.getSeatSelectedV = null;
		this.getSeatSelectedG = null;
		this.getSeatSelectedS = null;
	}


	public String getDeptDay() {
		return deptDay;
	}
	public void setDeptDay(String deptDay) {
		this.deptDay = deptDay;
	}
	public String getDeptPlace() {
		return deptPlace;
	}
	public void setDeptPlace(String deptPlace) {
		this.deptPlace = deptPlace;
	}
	public String getArrvPlace() {
		return arrvPlace;
	}
	public void setArrvPlace(String arrvPlace) {
		this.arrvPlace = arrvPlace;
	}
	public String getDeptTime() {
		return deptTime;
	}
	public void setDeptTime(String deptTime) {
		this.deptTime = deptTime;
	}
	public List<JButton>[] getSeatV() {
		return seatV;
	}
	public void setSeatV(List<JButton>[] seatV) {
		this.seatV = seatV;
	}
	public List<JButton>[] getSeatG() {
		return seatG;
	}
	public void setSeatG(List<JButton>[] seatG) {
		this.seatG = seatG;
	}
	public List<JButton>[] getSeatS() {
		return seatS;
	}
	public void setSeatS(List<JButton>[] seatS) {
		this.seatS = seatS;
	}
	public List<Boolean>[] getGetSeatSelectedV() {
		return getSeatSelectedV;
	}
	public void setGetSeatSelectedV(List<Boolean>[] getSeatSelectedV) {
		this.getSeatSelectedV = getSeatSelectedV;
	}
	public List<Boolean>[] getGetSeatSelectedG() {
		return getSeatSelectedG;
	}
	public void setGetSeatSelectedG(List<Boolean>[] getSeatSelectedG) {
		this.getSeatSelectedG = getSeatSelectedG;
	}
	public List<Boolean>[] getGetSeatSelectedS() {
		return getSeatSelectedS;
	}
	public void setGetSeatSelectedS(List<Boolean>[] getSeatSelectedS) {
		this.getSeatSelectedS = getSeatSelectedS;
	}
	
	
	
	
}
