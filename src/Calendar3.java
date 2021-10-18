import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class Calendar3 {

	static LocalDate nextMonth;
	static LocalDate lastMonth;
	static JPanel click;
	static JPanel jcb;
	static String selectDay;
	static boolean booleanJcb;
	static JPanel pnlSdInfor;

	public Calendar3(JPanel pnlsdCal, JPanel pnlsdDayInfor) {
		booleanJcb = false;
		LocalDate date = LocalDate.now();
		makeCalendar(pnlsdCal, pnlsdDayInfor, date);
	}

	public static void makeCalendar(JPanel pnlsdCal, JPanel pnlsdDayInfor, LocalDate date) {
		pnlsdCal.removeAll();
		pnlsdDayInfor.removeAll();
		
		JPanel pnl = new JPanel();
		pnl.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(180, 180, 180), 1)));
		LocalDate todayDate = LocalDate.now();
		nextMonth = date.plusMonths(1);
		lastMonth = date.plusMonths(-1);

		JPanel pnlMonth = new JPanel();
		JLabel lblLastMonth = new JLabel("              <<               ");
		lblLastMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlsdDayInfor.removeAll();
				makeCalendar(pnlsdCal, pnlsdDayInfor, lastMonth);
			}
		});
		JLabel lblMonth = new JLabel(date.getYear() + "년 " + date.getMonthValue() + "월");
		lblMonth.setFont(new Font(lblMonth.getFont().getName(), Font.BOLD, 15));
		lblMonth.setForeground(new Color(60, 137, 117));
		JLabel lblNextMonth = new JLabel("               >>              ");
		lblNextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlsdDayInfor.removeAll();
				makeCalendar(pnlsdCal, pnlsdDayInfor, nextMonth);
			}

		});
		pnlMonth.add(lblLastMonth);
		pnlMonth.add(lblMonth);
		pnlMonth.add(lblNextMonth);

		JPanel pnlWeek = new JPanel();
		for (int i = 0; i < 7; i++) {
			String day = "";
			switch (i) {
			case (0): day = "일";
				break;
			case (1): day = "월";
				break;
			case (2): day = "화";
				break;
			case (3): day = "수";
				break;
			case (4): day = "목";
				break;
			case (5): day = "금";
				break;
			case (6): day = "토";
				break;
			}
			JLabel lblWeek = new JLabel(day);
			lblWeek.setPreferredSize(new Dimension(100, 11));
			lblWeek.setMaximumSize(new Dimension(100, 11));
			lblWeek.setHorizontalAlignment(JLabel.CENTER);
			lblWeek.setAlignmentX(JLabel.TOP_ALIGNMENT);
			if (i == 0) {
				lblWeek.setForeground(new Color(255, 110, 110));
			} else if (i == 6) {
				lblWeek.setForeground(new Color(75, 175, 255));
			}
			pnlWeek.add(lblWeek);
		}

		// 처음날부터 하나씩 증가한다
		JPanel pnlDay = new JPanel(new GridLayout(0, 7));
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			int day = LocalDate.of(date.getYear(), date.getMonth(), i) // 날짜에 대한
					.getDayOfWeek().getValue(); // 요일(getDayOfWeek)을 숫자로 바꿔죠(getValue)
			if (day != 7 && i == 1) { // i일이 일요일(7)이 아니면서 1이면 1앞에 공백넣기
				for (int j = 0; j < day; j++) {
					JLabel lbldayemp = new JLabel(); // 비어있는 버튼
					lbldayemp.setPreferredSize(new Dimension(90, 32));
					lbldayemp.setHorizontalAlignment(JLabel.CENTER);
					pnlDay.add(lbldayemp);
				}
			}
			// 저번달인거는 보기만 가능
			if(date.getYear() <= todayDate.getYear() && date.getMonthValue() < todayDate.getMonthValue()) {
				JPanel pnlPassdayall = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JLabel lblPassday = new JLabel(String.valueOf(i));
				lblPassday.setForeground(Color.LIGHT_GRAY);
				lblPassday.setHorizontalAlignment(JLabel.CENTER);
				lblPassday.setPreferredSize(new Dimension(90, 15));
				lblPassday.setMaximumSize(new Dimension(90, 15));
				lblPassday.setMinimumSize(new Dimension(90, 15));
				// 스케줄 count
				int count = 0;
				for (Plane pp : Main.planes) {
					for (int j = 0; j < pp.schedules.size(); j++) {
						selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + i;
						if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
							count++;
						}
					}
				}
				JLabel lblPassdayInfor = new JLabel("스케줄 : "+count);
				lblPassdayInfor.setPreferredSize(new Dimension(90, 17));
				lblPassdayInfor.setMaximumSize(new Dimension(90, 17));
				lblPassdayInfor.setHorizontalAlignment(JLabel.CENTER);
				lblPassdayInfor.setVerticalAlignment(SwingConstants.TOP);
				lblPassdayInfor.setForeground(new Color(190, 190, 190));
				JPanel pnlPassday = new JPanel();
				pnlPassday.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(165,165,165), 1)));
				pnlPassday.setLayout(new BoxLayout(pnlPassday, BoxLayout.Y_AXIS));
				pnlPassday.add(lblPassday);
				pnlPassday.add(lblPassdayInfor);
				pnlPassdayall.add(pnlPassday);
				pnlDay.add(pnlPassdayall);
				click = null;
				pnlPassday.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						jcb = (JPanel) e.getSource(); // 눌른값
						JLabel lblday = (JLabel) jcb.getComponent(0);
						if (click != null) {
							Color c = UIManager.getColor("Panel.background");
							click.setBackground(c);
							pnlDay.revalidate();
							pnlDay.repaint();
						}
						jcb.setOpaque(true);
						jcb.setBackground(new Color(205, 205, 205));
						click = jcb; // click는 전에 눌린값
						selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + lblday.getText();
						// 스케줄정보 그리기
						pnlsdDayInfor.removeAll();
						// ---스케줄 정보 
						for (Plane pp : Main.planes) {
							for (int j = 0; j < pp.schedules.size(); j++) {
								if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
								pnlSdInfor = new JPanel();
								pnlSdInfor.setLayout(new BoxLayout(pnlSdInfor, BoxLayout.Y_AXIS));
								pnlSdInfor.setPreferredSize(new Dimension(760, 47));
								pnlSdInfor.setMaximumSize(new Dimension(760, 47));
								JPanel pnlLbl = new JPanel();
								JLabel lblRe = new JLabel();
								lblRe.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(180,180,180), 1)));
								lblRe.setAlignmentX(0.5F);
								lblRe.setPreferredSize(new Dimension(740, 40));
								lblRe.setMaximumSize(new Dimension(740, 40));
								// 레이블에 정보쓰기
								lblRe.setText(" 비행기 편명 : " +pp.planeName +
										"   /   출도착지 : "+ pp.schedules.get(j).getDeptPlace() + " ▷ " + pp.schedules.get(j).getArrvPlace()
										+ "   /   출발시간 : "+pp.schedules.get(j).getDeptTime()
										+ "   /   예약현황");
								pnlLbl.add(lblRe);
								pnlSdInfor.add(pnlLbl);
								pnlsdDayInfor.add(pnlSdInfor);
								}
							}
						}
						pnlsdDayInfor.revalidate();
						pnlsdDayInfor.repaint();
					}
				});
			}
			// 이번달인거는 지난날짜 보기만 가능
			// pnlPassdayall > pnlPassday > lblPassday + lblPassdayInfor
			else if(date.getYear()==todayDate.getYear() && date.getMonthValue()==todayDate.getMonthValue()) {
				if (todayDate.getDayOfMonth()>i) { // 오늘 날짜 전
					JPanel pnlPassdayall = new JPanel(new FlowLayout(FlowLayout.CENTER));
					JLabel lblPassday = new JLabel(String.valueOf(i));
					lblPassday.setForeground(Color.LIGHT_GRAY);
					lblPassday.setHorizontalAlignment(JLabel.CENTER);
					lblPassday.setPreferredSize(new Dimension(90, 15));
					lblPassday.setMaximumSize(new Dimension(90, 15));
					lblPassday.setMinimumSize(new Dimension(90, 15));
					// 스케줄 count
					int count = 0;
					for (Plane pp : Main.planes) {
						for (int j = 0; j < pp.schedules.size(); j++) {
							selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + i;
							if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
								count++;
							}
						}
					}
					JLabel lblPassdayInfor = new JLabel("스케줄 : "+count);
					lblPassdayInfor.setPreferredSize(new Dimension(90, 17));
					lblPassdayInfor.setMaximumSize(new Dimension(90, 17));
					lblPassdayInfor.setHorizontalAlignment(JLabel.CENTER);
					lblPassdayInfor.setVerticalAlignment(SwingConstants.TOP);
					lblPassdayInfor.setForeground(new Color(190, 190, 190));
					JPanel pnlPassday = new JPanel();
					pnlPassday.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(140,140,140), 1)));
					pnlPassday.setLayout(new BoxLayout(pnlPassday, BoxLayout.Y_AXIS));
					pnlPassday.add(lblPassday);
					pnlPassday.add(lblPassdayInfor);
					pnlPassdayall.add(pnlPassday);
					pnlDay.add(pnlPassdayall);
					click = null;
					pnlPassday.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							jcb = (JPanel) e.getSource(); // 눌른값
							JLabel lblday = (JLabel) jcb.getComponent(0);
							if (click != null) {
								Color c = UIManager.getColor("Panel.background");
								click.setBackground(c);
								pnlDay.revalidate();
								pnlDay.repaint();
							}
							jcb.setOpaque(true);
							jcb.setBackground(new Color(205, 205, 205));
							click = jcb; // click는 전에 눌린값
							selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + lblday.getText();
							// 스케줄정보 그리기
							pnlsdDayInfor.removeAll();
							// ---스케줄 정보 
							for (Plane pp : Main.planes) {
								for (int j = 0; j < pp.schedules.size(); j++) {
									if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
									pnlSdInfor = new JPanel();
									pnlSdInfor.setLayout(new BoxLayout(pnlSdInfor, BoxLayout.Y_AXIS));
									pnlSdInfor.setPreferredSize(new Dimension(760, 47));
									pnlSdInfor.setMaximumSize(new Dimension(760, 47));
									JPanel pnlLbl = new JPanel();
									JLabel lblRe = new JLabel();
									lblRe.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(180,180,180), 1)));
									lblRe.setAlignmentX(0.5F);
									lblRe.setPreferredSize(new Dimension(740, 40));
									lblRe.setMaximumSize(new Dimension(740, 40));
									// 레이블에 정보쓰기
									lblRe.setText(" 비행기 편명 : " +pp.planeName +
											"   /   출도착지 : "+ pp.schedules.get(j).getDeptPlace() + " ▷ " + pp.schedules.get(j).getArrvPlace()
											+ "   /   출발시간 : "+pp.schedules.get(j).getDeptTime()
											+ "   /   예약현황");
									pnlLbl.add(lblRe);
									pnlSdInfor.add(pnlLbl);
									pnlsdDayInfor.add(pnlSdInfor);
									}
								}
							}
							pnlsdDayInfor.revalidate();
							pnlsdDayInfor.repaint();
						}
					});
			    // pnldayall > pnloneday > lblday + lbldayInfor	
				} else if(todayDate.getDayOfMonth()<=i) { //오늘부터~
					JPanel pnldayall = new JPanel(new FlowLayout(FlowLayout.CENTER));
					JLabel lblday = new JLabel(String.valueOf(i));
					lblday.setHorizontalAlignment(JLabel.CENTER);
					lblday.setPreferredSize(new Dimension(90, 15));
					lblday.setMaximumSize(new Dimension(90, 15));
					// 스케줄 count
					int count = 0;
					for (Plane pp : Main.planes) {
						for (int j = 0; j < pp.schedules.size(); j++) {
							selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + i;
							if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
								count++;
							}
						}
					}
					JLabel lbldayInfor = new JLabel("스케줄 : "+count);
					lbldayInfor.setPreferredSize(new Dimension(90, 17));
					lbldayInfor.setMaximumSize(new Dimension(90, 17));
					lbldayInfor.setHorizontalAlignment(JLabel.CENTER);
					lbldayInfor.setVerticalAlignment(SwingConstants.TOP);
					if (count ==0 ) {
						lbldayInfor.setForeground(new Color(190, 190, 190));
					} else {
						lbldayInfor.setForeground(new Color(60, 137, 117));
					}
					JPanel pnloneday = new JPanel();
					pnloneday.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
					pnloneday.setLayout(new BoxLayout(pnloneday, BoxLayout.Y_AXIS));
					pnloneday.add(lblday);
					pnloneday.add(lbldayInfor);
					pnldayall.add(pnloneday);
					pnlDay.add(pnldayall);
					click = null;
					pnloneday.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							jcb = (JPanel) e.getSource(); // 눌른값
							JLabel lblday = (JLabel) jcb.getComponent(0);
							if (click != null) {
								Color c = UIManager.getColor("Panel.background");
								click.setBackground(c);
								pnlDay.revalidate();
								pnlDay.repaint();
							}
							jcb.setOpaque(true);
							jcb.setBackground(new Color(110, 200, 175));
							click = jcb; // click는 전에 눌린값
							selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + lblday.getText();
							// 스케줄정보 그리기
							pnlsdDayInfor.removeAll();
							// ---스케줄추가
							JPanel pnlAddSdBtn = new JPanel();
							pnlAddSdBtn.setPreferredSize(new Dimension(600,35));
							pnlAddSdBtn.setMaximumSize(new Dimension(600,35));
							JLabel lblAddSdDay = new JLabel(selectDay + "    ");
							lblAddSdDay.setFont(new Font(lblAddSdDay.getFont().getName(), Font.BOLD, 16));
							JButton btnAddSd = new JButton("스케줄추가");
							btnAddSd.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									new PlaneSdAdd2(selectDay);
									makeCalendar(pnlsdCal, pnlsdDayInfor, date);
									
									LocalDate date = LocalDate.now();
									Calendar2.makeCalendar(Main.pnlReCal, Main.pnlRe, date);
								}
							});
							pnlAddSdBtn.add(lblAddSdDay);
							pnlAddSdBtn.add(btnAddSd);
							pnlsdDayInfor.add(pnlAddSdBtn);
							// ---스케줄 정보 
							for (Plane pp : Main.planes) {
								for (int j = 0; j < pp.schedules.size(); j++) {
									if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
									pnlSdInfor = new JPanel();
									pnlSdInfor.setLayout(new BoxLayout(pnlSdInfor, BoxLayout.Y_AXIS));
									pnlSdInfor.setPreferredSize(new Dimension(760, 47));
									pnlSdInfor.setMaximumSize(new Dimension(760, 47));
									JPanel pnlLblBtn = new JPanel();
									JLabel lblRe = new JLabel();
									lblRe.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
									lblRe.setAlignmentX(0.5F);
									lblRe.setPreferredSize(new Dimension(600, 40));
									lblRe.setMaximumSize(new Dimension(600, 40));
									JButton btnMd = new JButton("수정");
									btnMd.setPreferredSize(new Dimension(60,40));
									JButton btnDe = new JButton("삭제");
									btnDe.setPreferredSize(new Dimension(60,40));
									// 레이블에 정보쓰기
									lblRe.setText(" 비행기 편명 : " +pp.planeName +
											"   /   출도착지 : "+ pp.schedules.get(j).getDeptPlace() + " ▷ " + pp.schedules.get(j).getArrvPlace()
											+ "   /   출발시간 : "+pp.schedules.get(j).getDeptTime()
											+ "   /   예약현황");
									pnlLblBtn.add(lblRe);
									pnlLblBtn.add(btnMd);
									pnlLblBtn.add(btnDe);
									pnlSdInfor.add(pnlLblBtn);
									pnlsdDayInfor.add(pnlSdInfor);
									}
								}
							}
							pnlsdDayInfor.revalidate();
							pnlsdDayInfor.repaint();
						}
					});
				} 
			//다음달꺼
			// 달력에 날짜 만들기
			// pnldayall > pnlPasspnlonedayday > lblday + lbldayInfor	
			} else if(date.getYear()!=todayDate.getYear() || date.getMonthValue()!=todayDate.getMonthValue()) {
				JPanel pnldayall = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JLabel lblday = new JLabel(String.valueOf(i));
				lblday.setHorizontalAlignment(JLabel.CENTER);
				lblday.setPreferredSize(new Dimension(90, 15));
				lblday.setMaximumSize(new Dimension(90, 15));

				//			LocalDate planeSdDay =  LocalDate.of(date.getYear(), date.getMonthValue(), i);
				int count = 0;
				for (Plane pp : Main.planes) {
					for (int j = 0; j < pp.schedules.size(); j++) {
						selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + i;
						if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
							count++;
						}
					}
				}
				JLabel lbldayInfor = new JLabel("스케줄 : "+count);
				lbldayInfor.setPreferredSize(new Dimension(90, 17));
				lbldayInfor.setMaximumSize(new Dimension(90, 17));
				lbldayInfor.setHorizontalAlignment(JLabel.CENTER);
				lbldayInfor.setVerticalAlignment(SwingConstants.TOP);
				if (count ==0 ) {
					lbldayInfor.setForeground(new Color(190, 190, 190));
				} else {
					lbldayInfor.setForeground(new Color(60, 137, 117));
				}
				JPanel pnloneday2 = new JPanel();
				pnloneday2.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
				pnloneday2.setLayout(new BoxLayout(pnloneday2, BoxLayout.Y_AXIS));
				pnloneday2.add(lblday);
				pnloneday2.add(lbldayInfor);
				pnldayall.add(pnloneday2);
				pnlDay.add(pnldayall);
				click = null;
				pnloneday2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						jcb = (JPanel) e.getSource(); // 눌른값
						JLabel lblday = (JLabel) jcb.getComponent(0);
						if (click != null) {
							Color c = UIManager.getColor("Panel.background");
							click.setBackground(c);
							pnlDay.revalidate();
							pnlDay.repaint();
						}
						jcb.setOpaque(true);
						jcb.setBackground(new Color(110, 200, 175));
						click = jcb; // click는 전에 눌린값
						selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + lblday.getText();
						// 스케줄정보 그리기
						pnlsdDayInfor.removeAll();
						// ---스케줄추가
						JPanel pnlAddSdBtn = new JPanel();
						pnlAddSdBtn.setPreferredSize(new Dimension(600,35));
						pnlAddSdBtn.setMaximumSize(new Dimension(600,35));
						JLabel lblAddSdDay = new JLabel(selectDay + "    ");
						lblAddSdDay.setFont(new Font(lblAddSdDay.getFont().getName(), Font.BOLD, 16));
						JButton btnAddSd = new JButton("스케줄추가");
						btnAddSd.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								new PlaneSdAdd2(selectDay);
								makeCalendar(pnlsdCal, pnlsdDayInfor, date);

								LocalDate date = LocalDate.now();
								Calendar2.makeCalendar(Main.pnlReCal, Main.pnlRe, date);
							}
						});
						pnlAddSdBtn.add(lblAddSdDay);
						pnlAddSdBtn.add(btnAddSd);
						pnlsdDayInfor.add(pnlAddSdBtn);
						// ---스케줄 정보 
						for (Plane pp : Main.planes) {
							for (int j = 0; j < pp.schedules.size(); j++) {
								if (pp.schedules.get(j).getDeptDay().trim().equals(selectDay.trim())) {
									pnlSdInfor = new JPanel();
									pnlSdInfor.setLayout(new BoxLayout(pnlSdInfor, BoxLayout.Y_AXIS));
									pnlSdInfor.setPreferredSize(new Dimension(760, 47));
									pnlSdInfor.setMaximumSize(new Dimension(760, 47));
									JPanel pnlLblBtn = new JPanel();
									JLabel lblRe = new JLabel();
									lblRe.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
									lblRe.setAlignmentX(0.5F);
									lblRe.setPreferredSize(new Dimension(600, 40));
									lblRe.setMaximumSize(new Dimension(600, 40));
									JButton btnMd = new JButton("수정");
									btnMd.setPreferredSize(new Dimension(60,40));
									JButton btnDe = new JButton("삭제");
									btnDe.setPreferredSize(new Dimension(60,40));
									// 레이블에 정보쓰기
									lblRe.setText(" 비행기 편명 : " +pp.planeName +
											"   /   출도착지 : "+ pp.schedules.get(j).getDeptPlace() + " ▷ " + pp.schedules.get(j).getArrvPlace()
											+ "   /   출발시간 : "+pp.schedules.get(j).getDeptTime()
											+ "   /   예약현황");
									pnlLblBtn.add(lblRe);
									pnlLblBtn.add(btnMd);
									pnlLblBtn.add(btnDe);
									pnlSdInfor.add(pnlLblBtn);
									pnlsdDayInfor.add(pnlSdInfor);
								}
							}
						}
						pnlsdDayInfor.revalidate();
						pnlsdDayInfor.repaint();
					}
				});
			}
		}

		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnlMonth);
		pnl.add(pnlWeek);
		pnl.add(pnlDay);

		pnlsdDayInfor.removeAll();;
		pnlsdDayInfor.revalidate();
		pnlsdDayInfor.repaint();
		pnlsdCal.removeAll();
		pnlsdCal.revalidate();
		pnlsdCal.repaint();
		pnlsdCal.add(pnl);

	}
	
}
