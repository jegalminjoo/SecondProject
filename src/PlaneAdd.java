import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class PlaneAdd extends JDialog {
	private JLabel lblname1;
	private PlaneName dialog = null;
	private JComboBox<String> comboV;
	private JComboBox<String> comboG;
	private JComboBox<String> comboS;
	private JRadioButton rbArea1;
	private JRadioButton rbArea2;
	private JRadioButton rbArea3;
	private JPanel pnlArea1;
	private JPanel pnlArea2;
	private JPanel pnlArea3;
	private JLabel lblname2;
	private JLabel lblname3;
	private JLabel lblname4;
	private JLabel lblname5;
	static int planeseat;
	static String planename;
	private JLabel lbltext;
	Plane plane;

	public PlaneAdd(Plane tempPlane) {
		plane = tempPlane;
		JPanel pnl = new JPanel();

		// 설명줄
		JPanel pnllbl = new JPanel();
		lbltext = new JLabel("↓ 첫번째 박스를 클릭하여 기종을 선택해주세요");
		pnllbl.add(lbltext);

		// 이름 구역
		JPanel pnl1 = new JPanel();
		lblname1 = new JLabel();
		lblname2 = new JLabel();
		lblname3 = new JLabel();
		lblname4 = new JLabel();
		lblname5 = new JLabel();
//		setlblname(lblname1);
//		setlblname(lblname2);
//		setlblname(lblname3);
//		setlblname(lblname4);
//		setlblname(lblname5);
		setlblname(lblname1, 1);
		setlblname(lblname2, 2);
		setlblname(lblname3, 3);
		setlblname(lblname4, 4);
		setlblname(lblname5, 5);

		lblname1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (dialog == null)
					dialog = new PlaneName(lblname1, lblname2, rbArea1, rbArea2, rbArea3);
				else
					dialog.show();
			}
		});
		pnl1.add(lblname1);
		pnl1.add(lblname2);
		pnl1.add(lblname3);
		pnl1.add(lblname4);
		pnl1.add(lblname5);

		// 라디오버튼
		JPanel pnl2 = new JPanel();
		ButtonGroup group = new ButtonGroup();
		rbArea1 = new JRadioButton("1구역");
		rbArea1.addActionListener(new radioActionlis()); // radioActionlis 클래스로 찾아가라
		rbArea1.addItemListener(new radioItemlis());
		rbArea2 = new JRadioButton("2구역");
		rbArea2.addActionListener(new radioActionlis());
		rbArea2.addItemListener(new radioItemlis());
		rbArea3 = new JRadioButton("3구역");
		rbArea3.addActionListener(new radioActionlis());
		rbArea3.addItemListener(new radioItemlis());
		if (lblname1 == null || lblname1.getText().trim().equals("")) {
			rbArea1.setEnabled(false);
			rbArea2.setEnabled(false);
			rbArea3.setEnabled(false);
		}
		group.add(rbArea1);
		group.add(rbArea2);
		group.add(rbArea3);
		pnl2.add(rbArea1);
		pnl2.add(rbArea2);
		pnl2.add(rbArea3);

		// 콤보박스
		JPanel pnl3 = new JPanel();
		String[] num = { "선택", "2", "4", "6" };
		JLabel lblCountV = new JLabel("V좌석");
		comboV = new JComboBox<String>(num);
		comboV.setEnabled(false);
		comboV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int section = 0; // 구역
				if (rbArea1.isSelected()) {
					section = 1;
				} else if (rbArea2.isSelected()) {
					section = 2;
				} else if (rbArea3.isSelected()) {
					section = 3;
				}
				String selectV = (String) comboV.getSelectedItem(); // 콤보 선택된 값
				if (selectV.equals("선택")) { // 콤보박스의'선택'버튼이면
					return; // action 끝냄
				}
				switch (selectV) {
				case "2":
					seatPaintRemove(tempPlane, section, "V");
					JPanel pnlseatVAll1 = new JPanel(); // seat2개 panel
					for (int i = 0; i < 2; i++) {
						JPanel pnlseatV = new JPanel(); // seat 개당 panel
						JButton seatV = new JButton("V" + (i + 1));
						tempPlane.seatV[section - 1].add(seatV); // 만든좌석 ( [section-1] : plane ) 넣는다
						setSeatV(seatV);
						pnlseatV.add(seatV);
						pnlseatVAll1.add(pnlseatV);
					}
					pnlseatVAll1.setLayout(new BoxLayout(pnlseatVAll1, BoxLayout.Y_AXIS));
					seatPaint(pnlseatVAll1);
					break;
				case "4":
					seatPaintRemove(tempPlane, section, "V");
					for (int j = 0; j < 2; j++) {
						JPanel pnlseatVAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatV = new JPanel();
							JButton seatV = new JButton("V" + (j * 2 + i + 1));
							tempPlane.seatV[section - 1].add(seatV);
							setSeatV(seatV);
							pnlseatV.add(seatV);
							pnlseatVAll.add(pnlseatV);
						}
						pnlseatVAll.setLayout(new BoxLayout(pnlseatVAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatVAll);
					}
					break;
				case "6":
					seatPaintRemove(tempPlane, section, "V");
					for (int j = 0; j < 3; j++) {
						JPanel pnlseatVAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatV = new JPanel();
							JButton seatV = new JButton("V" + (j * 2 + i + 1));
							tempPlane.seatV[section - 1].add(seatV);
							setSeatV(seatV);
							pnlseatV.add(seatV);
							pnlseatVAll.add(pnlseatV);
						}
						pnlseatVAll.setLayout(new BoxLayout(pnlseatVAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatVAll);
					}
					break;
				}
				airseatcount(tempPlane);
				comboG.setEnabled(false);
				comboS.setEnabled(false);
				repainting();
			}
		});
		JLabel lblCountG = new JLabel("G좌석");
		comboG = new JComboBox<String>(num);
		comboG.setEnabled(false);
		comboG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int section = 0; // 구역
				if (rbArea1.isSelected()) {
					section = 1;
				} else if (rbArea2.isSelected()) {
					section = 2;
				} else if (rbArea3.isSelected()) {
					section = 3;
				}
				String selectG = (String) comboG.getSelectedItem(); // 콤보 선택된 값
				if (selectG.equals("선택")) { // 콤보박스의'선택'버튼이면
					return; // action 끝냄
				}
				switch (selectG) {
				case "2":
					seatPaintRemove(tempPlane, section, "G");
					JPanel pnlseatGAll1 = new JPanel(); // seat2개 panel
					for (int i = 0; i < 2; i++) {
						JPanel pnlseatG = new JPanel(); // seat 개당 panel
						JButton seatG = new JButton("G" + (i + 1));
						tempPlane.seatG[section - 1].add(seatG); // 만든좌석 ( tempPlane.seatV[section-1] : plane ) 넣는다
						setSeatG(seatG);
						pnlseatG.add(seatG);
						pnlseatGAll1.add(pnlseatG);
					}
					pnlseatGAll1.setLayout(new BoxLayout(pnlseatGAll1, BoxLayout.Y_AXIS));
					seatPaint(pnlseatGAll1);
					break;
				case "4":
					seatPaintRemove(tempPlane, section, "G");
					for (int j = 0; j < 2; j++) {
						JPanel pnlseatGAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatG = new JPanel();
							JButton seatG = new JButton("G" + (j * 2 + i + 1));
							tempPlane.seatG[section - 1].add(seatG);
							setSeatG(seatG);
							pnlseatG.add(seatG);
							pnlseatGAll.add(pnlseatG);
						}
						pnlseatGAll.setLayout(new BoxLayout(pnlseatGAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatGAll);
					}
					break;
				case "6":
					seatPaintRemove(tempPlane, section, "G");
					for (int j = 0; j < 3; j++) {
						JPanel pnlseatGAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatG = new JPanel();
							JButton seatG = new JButton("G" + (j * 2 + i + 1));
							tempPlane.seatG[section - 1].add(seatG);
							setSeatG(seatG);
							pnlseatG.add(seatG);
							pnlseatGAll.add(pnlseatG);
						}
						pnlseatGAll.setLayout(new BoxLayout(pnlseatGAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatGAll);
					}
					break;
				}
				airseatcount(tempPlane);
				comboV.setEnabled(false);
				comboS.setEnabled(false);
				repainting();
			}
		});
		JLabel lblCountS = new JLabel("S좌석");
		comboS = new JComboBox<String>(num);
		comboS.setEnabled(false);
		comboS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int section = 0; // 구역
				if (rbArea1.isSelected()) {
					section = 1;
				} else if (rbArea2.isSelected()) {
					section = 2;
				} else if (rbArea3.isSelected()) {
					section = 3;
				}
				String selectS = (String) comboS.getSelectedItem(); // 콤보 선택된 값
				if (selectS.equals("선택")) { // 콤보박스의'선택'버튼이면
					return; // action 끝냄
				}
				switch (selectS) {
				case "2":
					seatPaintRemove(tempPlane, section, "S");
					JPanel pnlseatSAll1 = new JPanel(); // seat2개 panel
					for (int i = 0; i < 2; i++) {
						JPanel pnlseatS = new JPanel(); // seat 개당 panel
						JButton seatS = new JButton("S" + (i + 1));
						tempPlane.seatS[section - 1].add(seatS); // 만든좌석 ( tempPlane.seatV[section-1] : plane ) 넣는다
						setSeatS(seatS);
						pnlseatS.add(seatS);
						pnlseatSAll1.add(pnlseatS);
					}
					pnlseatSAll1.setLayout(new BoxLayout(pnlseatSAll1, BoxLayout.Y_AXIS));
					seatPaint(pnlseatSAll1);
					break;
				case "4":
					seatPaintRemove(tempPlane, section, "S");
					for (int j = 0; j < 2; j++) {
						JPanel pnlseatSAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatS = new JPanel();
							JButton seatS = new JButton("S" + (j * 2 + i + 1));
							tempPlane.seatS[section - 1].add(seatS);
							setSeatS(seatS);
							pnlseatS.add(seatS);
							pnlseatSAll.add(pnlseatS);
						}
						pnlseatSAll.setLayout(new BoxLayout(pnlseatSAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatSAll);
					}
					break;
				case "6":
					seatPaintRemove(tempPlane, section, "S");
					for (int j = 0; j < 3; j++) {
						JPanel pnlseatSAll = new JPanel();
						for (int i = 0; i < 2; i++) {
							JPanel pnlseatS = new JPanel();
							JButton seatS = new JButton("S" + (j * 2 + i + 1));
							tempPlane.seatS[section - 1].add(seatS);
							setSeatS(seatS);
							pnlseatS.add(seatS);
							pnlseatSAll.add(pnlseatS);
						}
						pnlseatSAll.setLayout(new BoxLayout(pnlseatSAll, BoxLayout.Y_AXIS));
						seatPaint(pnlseatSAll);
					}
					break;
				}
				airseatcount(tempPlane);
				comboV.setEnabled(false);
				comboG.setEnabled(false);
				repainting();
			}
		});
		pnl3.add(lblCountV); // "적용수량"
		pnl3.add(comboV); // "콤보"
		pnl3.add(lblCountG); // "적용수량"
		pnl3.add(comboG); // "콤보"
		pnl3.add(lblCountS); // "적용수량"
		pnl3.add(comboS); // "콤보"

		// 구역
		JPanel pnl4 = new JPanel();
		pnlArea1 = new JPanel();
		pnlArea1.setPreferredSize(new Dimension(205, 120));
		pnlArea1.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "1구역"));
		pnlArea2 = new JPanel();
		pnlArea2.setPreferredSize(new Dimension(205, 120));
		pnlArea2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "2구역"));
		pnlArea3 = new JPanel();
		pnlArea3.setPreferredSize(new Dimension(205, 120));
		pnlArea3.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "3구역"));
		pnl4.add(pnlArea1);
		pnl4.add(pnlArea2);
		pnl4.add(pnlArea3);

		// 확인버튼
		JPanel pnl5 = new JPanel();
		JButton btnOK = new JButton("확인");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 다 안채웠을떄
				if (lblname1.getText().trim().equals("") || lblname2.getText().trim().equals("")
						|| pnlArea1.getComponentCount() == 0 || pnlArea2.getComponentCount() == 0
						|| pnlArea3.getComponentCount() == 0) {
					lbltext.setText("비행기 기종과 좌석을 채워주세요");
					lbltext.setForeground(Color.red);
				} else {
					tempPlane.planeName = new String(lblname1.getText() + lblname2.getText() + lblname3.getText()
							+ lblname4.getText() + lblname5.getText());
					planename = lblname1.getText() + lblname2.getText() + lblname3.getText() + lblname4.getText()
							+ lblname5.getText();
					airseatcount(tempPlane);
					dispose();
				}
			}
		});
		pnl5.add(btnOK);

		setpnlrepaint();// 수정 눌렸을때 다이얼로그 그리기

		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnllbl);
		pnl.add(pnl1);
		pnl.add(pnl2);
		pnl.add(pnl3);
		pnl.add(pnl4);
		pnl.add(pnl5);
		add(pnl);

		pack();
//		setSize(280, 300);
		setTitle("PLANE ADD");
		setLocation(450, 400);
		setModal(true);
		setVisible(true);
	}

	protected void airseatcount(Plane tempPlane) {
		int countV = 0;
		for (int i = 0; i < 3; i++) { // 3 = 구역수 :tempPlane.seatV.lentgh();
			if (tempPlane.seatV[i] != null) {
				countV += tempPlane.seatV[i].size();
			}
		}
		lblname3.setText(String.valueOf(countV));
		int countG = 0;
		for (int i = 0; i < 3; i++) { // 3 = 구역수 :tempPlane.seatV.lentgh();
			if (tempPlane.seatG[i] != null) {
				countG += tempPlane.seatG[i].size();
			}
		}
		lblname4.setText(String.valueOf(countG));
		int countS = 0;
		for (int i = 0; i < 3; i++) { // 3 = 구역수 :tempPlane.seatV.lentgh();
			if (tempPlane.seatS[i] != null) {
				countS += tempPlane.seatS[i].size();
			}
		}
		lblname5.setText(String.valueOf(countS));
		planeseat = countV + countG + countS;
	}

	static void setSeatS(JButton seatbtn) {
		seatbtn.setEnabled(false);
		seatbtn.setPreferredSize(new Dimension(30, 30));
		seatbtn.setBackground(new Color(180, 250, 255));
		seatbtn.setMargin(new Insets(0, 0, 0, 0));
	}

	static void setSeatG(JButton seatbtn) {
		seatbtn.setEnabled(false);
		seatbtn.setPreferredSize(new Dimension(40, 30));
		seatbtn.setBackground(new Color(255, 205, 170));
		seatbtn.setMargin(new Insets(0, 0, 0, 0));
	}

	static void setSeatV(JButton seatbtn) {
		seatbtn.setEnabled(false);
		seatbtn.setPreferredSize(new Dimension(50, 30));
		seatbtn.setBackground(new Color(246, 244, 177));
		seatbtn.setMargin(new Insets(0, 0, 0, 0));
	}

	protected void seatPaintRemove(Plane tempPlane, int section, String seat) { // 라디오 다시 선택할때 기존선택된걸 지움(그림그린거지워)
		if (rbArea1.isSelected()) {
			pnlArea1.removeAll();
		} else if (rbArea2.isSelected()) {
			pnlArea2.removeAll();
		} else if (rbArea3.isSelected()) {
			pnlArea3.removeAll();
		}
		if (seat.equals("V")) {
			tempPlane.seatV[section - 1] = new ArrayList<JButton>();
			tempPlane.seatG[section - 1] = null; // 1구역에 v좌석을 넣을때 g,s좌석의 리스트는 버린다
			tempPlane.seatS[section - 1] = null;
		} else if (seat.equals("G")) {
			tempPlane.seatV[section - 1] = null;
			tempPlane.seatG[section - 1] = new ArrayList<JButton>();
			tempPlane.seatS[section - 1] = null;
		} else if (seat.equals("S")) {
			tempPlane.seatV[section - 1] = null;
			tempPlane.seatG[section - 1] = null;
			tempPlane.seatS[section - 1] = new ArrayList<JButton>();
		}
	}

	protected void seatPaint(JPanel pnlseatAll) { // 콤보박스로 개수 선택했을때 좌석 그려줌
		if (rbArea1.isSelected()) {
			pnlArea1.add(pnlseatAll);
		} else if (rbArea2.isSelected()) {
			pnlArea2.add(pnlseatAll);
		} else if (rbArea3.isSelected()) {
			pnlArea3.add(pnlseatAll);
		}
	}

	private void setlblname(JLabel lblname, int index) { // lblname 셋팅 : 비행기 이름 5자리
		// lblname 셋팅 : 비행기 이름 5자리의 모양
		lblname.setPreferredSize(new Dimension(60, 60));
		lblname.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 2)));
		lblname.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		lblname.setFont(new Font(getName(), Font.BOLD, 25));
		// 수정버튼 눌릴시 이름 적어주기
		if (plane.planeName != null) {
			lblname.setText(plane.planeName.substring(index - 1, index));
		}
	}

	private void setpnlrepaint() { // 수정 눌렸을때 다이얼로그 그리기
		for (int i = 0; i < 3; i++) {
			if (plane.seatV[i] != null) {
				makeSeatVPnl(plane.seatV[i], i);
			} else if (plane.seatG[i] != null) {
				makeSeatGPnl(plane.seatG[i], i);
			} else if (plane.seatS[i] != null) {
				makeSeatSPnl(plane.seatS[i], i);
			}
		}
	}

	private void makeSeatVPnl(List<JButton> seat, int index) {
		for (int j = 0; j < seat.size() / 2; j++) { // 패널2개가 한개셋투
			JPanel tempPnl = new JPanel(); // seat2개 panel
			for (int i = 0; i < 2 ; i++) {
				JPanel pnlseatV = new JPanel(); // seat 개당 panel
				JButton seatV = new JButton("V" + ((j * 2)+i+1));
				setSeatV(seatV);
				pnlseatV.add(seatV);
				tempPnl.add(pnlseatV);
			}
			tempPnl.setLayout(new BoxLayout(tempPnl, BoxLayout.Y_AXIS));
			if (index == 0) {
				pnlArea1.add(tempPnl);
			} else if (index == 1) {
				pnlArea2.add(tempPnl);
			} else if (index == 2) {
				pnlArea3.add(tempPnl);
			}
		}
	}
	
	private void makeSeatGPnl(List<JButton> seat, int index) {
		for (int j = 0; j < seat.size() / 2; j++) { // 패널2개가 한개셋투
			JPanel tempPnl = new JPanel(); // seat2개 panel
			for (int i = 0; i < 2 ; i++) {
				JPanel pnlseatG = new JPanel(); // seat 개당 panel
				JButton seatG = new JButton("V" + ((j * 2)+i+1));
				setSeatG(seatG);
				pnlseatG.add(seatG);
				tempPnl.add(pnlseatG);
			}
			tempPnl.setLayout(new BoxLayout(tempPnl, BoxLayout.Y_AXIS));
			if (index == 0) {
				pnlArea1.add(tempPnl);
			} else if (index == 1) {
				pnlArea2.add(tempPnl);
			} else if (index == 2) {
				pnlArea3.add(tempPnl);
			}
		}
	}
	
	private void makeSeatSPnl(List<JButton> seat, int index) {
		for (int j = 0; j < seat.size() / 2; j++) { // 패널2개가 한개셋투
			JPanel tempPnl = new JPanel(); // seat2개 panel
			for (int i = 0; i < 2 ; i++) {
				JPanel pnlseatS = new JPanel(); // seat 개당 panel
				JButton seatS = new JButton("V" + ((j * 2)+i+1));
				setSeatS(seatS);
				pnlseatS.add(seatS);
				tempPnl.add(pnlseatS);
//				pnlseatV.setLayout(new BoxLayout(pnlseatV, BoxLayout.Y_AXIS));
			}
			tempPnl.setLayout(new BoxLayout(tempPnl, BoxLayout.Y_AXIS));
			if (index == 0) {
				pnlArea1.add(tempPnl);
			} else if (index == 1) {
				pnlArea2.add(tempPnl);
			} else if (index == 2) {
				pnlArea3.add(tempPnl);
			}
		}
	}

	protected void repainting() {
		pnlArea1.revalidate();
		pnlArea1.repaint();
		pnlArea2.revalidate();
		pnlArea2.repaint();
		pnlArea3.revalidate();
		pnlArea3.repaint();
	}

	class radioActionlis implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			comboV.setEnabled(true);
			comboG.setEnabled(true);
			comboS.setEnabled(true);
		}
	}

	class radioItemlis implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JRadioButton tempRad = (JRadioButton) e.getSource();
			if (tempRad.isSelected() == true) {
				comboV.setSelectedIndex(0);
				comboG.setSelectedIndex(0);
				comboS.setSelectedIndex(0);
			}
		}
	}
}