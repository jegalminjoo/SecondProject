import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable { // 자바가 객체를 다 읽고 쓸수있게 만들어주는것
    //Serializable  : CustomerIO에 ObjectOutputStream와 ObjectInputStream를 하기위해 넣어주는것
	private static final long serialVersionUID = 1L; // long값 아무거나 넣으면된다

	private String name; // 이름
	private String birth; // 생년월일
	private String cellNum; // 휴대폰번호
	private String uid; // id
	private String upwd; // pw
	private int grade; // 등급, 0:실버, 1:골드, 2:다이아
	private int point; // 포인트
	private int star; // 스티커 0 ~ 12
	private List<UserSchedule> schedules; // 예약했던 목록

	public Customer(String name, String birth, String cellNum, String uid, String upwd) {
		this.name = name;
		this.birth = birth;
		this.cellNum = cellNum;
		this.uid = uid;
		this.upwd = upwd;
		this.grade = 0;
		this.point = 1000;
		this.star = 0;
		this.schedules = new ArrayList<UserSchedule>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCellNum() {
		return cellNum;
	}

	public void setCellNum(String cellNum) {
		this.cellNum = cellNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public List<UserSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<UserSchedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", birth=" + birth + ", cellNum=" + cellNum + ", uid=" + uid + ", upwd="
				+ upwd + ", grade=" + grade + ", point=" + point + ", star=" + star + ", schedules=" + schedules + "]";
	}

}
