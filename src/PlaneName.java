import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PlaneName extends JDialog {

	private String btnname;

	public PlaneName(JLabel lblname1, JLabel lblname2, JRadioButton rbArea1, JRadioButton rbArea2,
			JRadioButton rbArea3) {
		// 첫번째 설명텍스트
		JPanel pnl1 = new JPanel();
		JLabel lbltext = new JLabel("기종을 선택해주세요.");
		pnl1.add(lbltext);

		JPanel pnl2 = new JPanel(new GridLayout(0, 5));
		// 알파벳 라디오 버튼
		JRadioButton[] alpabat = new JRadioButton[26];
		char[] alpabat2 = new char[26];
		ButtonGroup group = new ButtonGroup();
		for (int j = 0; j < alpabat2.length; j++) {
			alpabat2[j] = (char) ('A' + j);
			alpabat[j] = new JRadioButton(String.valueOf(alpabat2[j])); // String.valueOf(i + 1
			group.add(alpabat[j]);
			pnl2.add(alpabat[j]);
			alpabat[j].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JRadioButton jcb = (JRadioButton) e.getSource();
					btnname = jcb.getActionCommand();
					lbltext.setForeground(Color.black);
				}
			});
		}
		// 공백
		JLabel[] lbl = new JLabel[3];
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new JLabel();
			pnl2.add(lbl[i]);
		}
		// 확인버튼
		JButton btnok = new JButton("확인");
		btnok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnname == null || btnname.trim().equals("")) {
					lbltext.setForeground(Color.red);
				} else if (btnname != null || !btnname.trim().equals("")) {
					lblname1.setText(btnname);
					rbArea1.setEnabled(true);
					rbArea2.setEnabled(true);
					rbArea3.setEnabled(true);

//					char last = 65; // A
//					for (int i = 0; i < Main.planes.size(); i++) {
//						if (btnname.equals(String.valueOf(Main.planes.get(i).planeName.charAt(0)))) { // 1째 자리랑 같을때
//							if (last == Main.planes.get(i).planeName.charAt(1)) { // 2번째 자리랑 같을때
//								last++;
//							} else {
//								break;
//							}
//						}
//					}
//					String name2 = String.valueOf(last);
//					lblname2.setText(name2);
					List<Character> alp = new ArrayList<Character>();
					for (int i = 0; i < 26; i++) {
						alp.add((char) ('A' + i));
					}

					Iterator<Plane> iter = Main.planes.iterator();
					while (iter.hasNext()) { // hasNext : 주머니에 손을 넣어서 물건이 있는지 없는지 알려줌(boolean)
						Plane p1 = iter.next(); // 더 남아있으면 다음꺼 꺼내고...
						String pname = String.valueOf((p1.planeName).charAt(0)); // 꺼낸p1의 이름중 1번째 글짜
						Character pname2 = (p1.planeName).charAt(1); // 꺼낸p1의 이름중 2번째 글짜
						if (btnname.equals(pname)) { // 내가 눌린값(btnname)이 p1의1번째글자와 같다면
							alp.remove(pname2); // 알파벳리스트에서 p1의2째글자를 지워 (
						}
					}
					lblname2.setText(String.valueOf(alp.get(0)));  //리스트는 지워지면 앞으로 땡겨지니 다지우고 남은거중 젤 첫번째

					dispose();
				}
			}
		});
		btnok.setMargin(new Insets(0, 0, 0, 0));
		pnl2.add(btnok);

		add(pnl1, "North");
		add(pnl2, "South");
		pack();
		setTitle("PLANE NAME");
		setLocation(235, 400);
		setModal(true);
		setVisible(true);
	}
}
