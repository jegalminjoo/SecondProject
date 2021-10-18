import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class Plane implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String planeName; // 비행기 이름
	List<JButton>[] seatV = new List[3]; //V좌석을 담을 리스트의 배열
	List<JButton>[] seatG = new List[3]; //G좌석을 담을 리스트의 배열
	List<JButton>[] seatS = new List[3]; //S좌석을 담을 리스트의 배열
	//seatV[0] => 1구역의 V좌석 리스트가 들어있음
	//seatV[0] : 1구역,  seatV[1] : 2구역,  seatV[2] : 3구역
	//seatV[0].get(0) => 1구역의 V등급의 첫번째 좌석인 버튼
	//seatV[0].add(new JButton("V1")) => 1구역에 V등급의 좌석1개 추가
	//seatV[0] == null
	List<PlaneSchedule> schedules = new ArrayList<PlaneSchedule>(); //현 비행기의 스케쥴정보
	
	@Override
	public String toString() {
		return "Plane [planeName=" + planeName + ", seatV=" + Arrays.toString(seatV) + ", seatG="
				+ Arrays.toString(seatG) + ", seatS=" + Arrays.toString(seatS) + ", schedules=" + schedules + "]";
	}
	
 
}