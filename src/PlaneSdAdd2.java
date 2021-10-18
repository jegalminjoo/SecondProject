import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlaneSdAdd2 extends JDialog {
	private JComboBox<String> comboArrPlace;
	private String StringcomboDep;
	private JComboBox<String> comboPlaneName;
	private JComboBox<String> comboDepPlace;
	private JComboBox<String> comboDepTime;

	public PlaneSdAdd2(String selectDay) {

		JPanel pnl = new JPanel();

		// 처음 날짜 출발지 등등
		JPanel pnlInfor = new JPanel();
		JLabel lblDay = new JLabel("스케줄날짜");
		JLabel lblDay2 = new JLabel(selectDay);
		lblDay2.setForeground(new Color(60, 137, 117));

		// 수정 눌렀을때
//		remakeName(btnname);
//		remakeCombo(btnname);

		//비행기 기종
		JLabel lblPlaneName = new JLabel("출발지");
		comboPlaneName = new JComboBox<String>();
		comboPlaneName.addItem("선택");
		for (int i = 0; i < Main.planes.size(); i++) {
			String planeName = Main.planes.get(i).planeName;
			comboPlaneName.addItem(planeName);
		}
		comboPlaneName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selPlanename = (String) comboPlaneName.getSelectedItem(); // 콤보 선택된 값
				if (!selPlanename.equals("선택")) {
					comboDepPlace.setEnabled(true);
					comboDepPlace.setSelectedIndex(0);
					comboArrPlace.setEnabled(false);
					comboArrPlace.setSelectedIndex(0);
					comboDepTime.setEnabled(false);
					comboDepTime.setSelectedIndex(0);
					//같은날짜 같은시간 비행기 추가 금지
					for (Plane pp : Main.planes) {
						if (pp.planeName.equals(selPlanename)) { //비행기 기종이 같으면
							for (int i = 0; i < pp.schedules.size(); i++) {
								if(pp.schedules.get(i).getDeptDay().trim().equals(selectDay.trim())) { //같은날짜에
								comboDepTime.removeItem(pp.schedules.get(i).getDeptTime()); //같은시간 삭제
								}
							}
						}
					}
				} else if (selPlanename.equals("선택")) {
					comboDepPlace.setEnabled(false);
					comboDepPlace.setSelectedIndex(0);
					comboArrPlace.setEnabled(false);
					comboArrPlace.setSelectedIndex(0);
					comboDepTime.setEnabled(false);
					comboDepTime.setSelectedIndex(0);
				}
			}
		});
		// 출발지
		JLabel lblDepPlace = new JLabel("출발지");
		String[] place = { "선택", "서울", "부산", "제주" };
		comboDepPlace = new JComboBox<String>(place);
		comboDepPlace.setEnabled(false);
		StringcomboDep = "";
		comboDepPlace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String depPlace = (String) comboDepPlace.getSelectedItem(); // 콤보 선택된 값
				if (!depPlace.equals("선택")) {
					if (!StringcomboDep.equals("")) {
						switch (StringcomboDep) {
						case ("서울"):
							comboArrPlace.insertItemAt(StringcomboDep, 1);
							break;
						case ("부산"):
							comboArrPlace.insertItemAt(StringcomboDep, 2);
							break;
						case ("제주"):
							comboArrPlace.insertItemAt(StringcomboDep, 3);
							break;
						}
						StringcomboDep = depPlace; // 다음에 선택한것
					}
					StringcomboDep = depPlace;
					comboArrPlace.setEnabled(true);
					comboArrPlace.removeItem(depPlace);
					comboArrPlace.setSelectedIndex(0);
					comboDepTime.setEnabled(false);
					comboDepTime.setSelectedIndex(0);
				} else if (depPlace.equals("선택")) {
					comboArrPlace.setEnabled(false);
					comboArrPlace.setSelectedIndex(0);
					comboDepTime.setEnabled(false);
					comboDepTime.setSelectedIndex(0);
				}
			}
		});
		// 도착지
		JLabel lblArrPlace = new JLabel("도착지");
		comboArrPlace = new JComboBox<String>(place);
		comboArrPlace.setEnabled(false);
		comboArrPlace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String arrPlace = (String) comboArrPlace.getSelectedItem(); // 콤보 선택된 값
				if (!arrPlace.equals("선택")) {
					comboDepTime.setEnabled(true);
					comboDepTime.setSelectedIndex(0);
				} else if (arrPlace.equals("선택")) {
					comboDepTime.setEnabled(false);
					comboDepTime.setSelectedIndex(0);
				}
			}
		});
		// 출발시간
		JLabel lblDepTime = new JLabel("출발시간");
		String[] time = { "선택", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
				"18:00", "19:00" };
		comboDepTime = new JComboBox<String>(time);
		comboDepTime.setEnabled(false);

		pnlInfor.add(lblDay);
		pnlInfor.add(lblDay2);
		pnlInfor.add(lblPlaneName);
		pnlInfor.add(comboPlaneName);
		pnlInfor.add(lblDepPlace);
		pnlInfor.add(comboDepPlace);
		pnlInfor.add(lblArrPlace);
		pnlInfor.add(comboArrPlace);
		pnlInfor.add(lblDepTime);
		pnlInfor.add(comboDepTime);

		// 확인 취소 버튼
		JPanel pnlbtn = new JPanel();
		JButton btnOk = new JButton("확인");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String depPla = (String) comboDepPlace.getSelectedItem();
				String arrPla = (String) comboArrPlace.getSelectedItem();
				String depTi = (String) comboDepTime.getSelectedItem();
				if (!comboDepTime.getSelectedItem().equals("선택")) {
					for (Plane pp : Main.planes) {
						if (pp.schedules == null) {
							pp.schedules = new ArrayList<PlaneSchedule>();
						}
						if (pp.planeName.equals(comboPlaneName.getSelectedItem())) {
							pp.schedules.add(new PlaneSchedule(selectDay, depPla, arrPla, depTi, pp.seatV,
									pp.seatG, pp.seatS));
//							Main.pnlrv.removeAll();
							PlaneIO.save(Main.planes);
						}
					}
					dispose();
				}
			}
		});
		JButton btnDe = new JButton("취소");
		btnDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlbtn.add(btnOk);
		pnlbtn.add(btnDe);

		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnlInfor);
		pnl.add(pnlbtn);
		add(pnl);

		pack();
//		setSize(280, 300);
		setTitle("ADD ACHEDULE");
		setLocation(450, 400);
		setModal(true);
		setVisible(true);
	}

	private void remakeCombo(String btnname) {
		for (Plane pp : Main.planes) {
			if (pp.planeName.equals(btnname)) {
				if (pp.schedules != null) {
					comboDepPlace.setEnabled(true);
					Iterator<PlaneSchedule> iter = pp.schedules.iterator();
					while (iter.hasNext()) { // hasNext : 주머니에 손을 넣어서 물건이 있는지 없는지 알려줌(boolean)
						PlaneSchedule pSd = iter.next();
						String[] lblplaneSdDepPla1 = Main.lblshowSd.getText().trim().split(",");
						String[] lblplaneSdDepPla2 = lblplaneSdDepPla1[1].trim().split(":");
						String[] lblplaneSdDepPla3 = lblplaneSdDepPla2[1].trim().split("→");
						if (lblplaneSdDepPla3[0].trim().equals(pSd.getDeptPlace().trim())) {
							switch (pSd.getDeptPlace().trim()) {
							case ("서울"):
								comboDepPlace.setSelectedIndex(1);
								break;
							case ("부산"):
								comboDepPlace.setSelectedIndex(2);
								break;
							case ("제주"):
								comboDepPlace.setSelectedIndex(3);
								break;
							}
						}
					}
				}
			}
		}
	}

	private void remakeName(String btnname) {
//		for (Plane pp : Main.planes) {
//			if (pp.planeName.equals(btnname)) {
//				for (int i = 0; i < pp.schedules.size(); i++) {
//					if (pp.schedules.get(i) != null) {
//						String[] lblplaneSdDay1 = Main.lblshowSd.getText().trim().split(",");
//						String[] lblplaneSdDay2 = lblplaneSdDay1[0].trim().split(":");
//						lblselDay.setText(pp.schedules.get(i).getDeptDay());
//						lblselDay.setForeground(new Color(60, 137, 117));
////							}
////						}
//					}
//				}
//			}
//		}
//	}
//
	}
}