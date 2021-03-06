import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Calendar extends JDialog {
	private LocalDate nextMonth;
	private LocalDate lastMonth;
	private JLabel click;
	static JLabel jcb;
	static String selectDay;
	private LocalDate selectDate;
	private JLabel lastDateLbl;
	static boolean booleanJcb;

	public Calendar(String selectedDate) {
		booleanJcb = false;
		LocalDate date = LocalDate.now();

//		String[] selectDate1 = selectedDate.trim().split("/");

		if (selectedDate.trim().equals("____________")) {
			makeCalendar(date);

//		LocalDate selectDate = LocalDate.of(Integer.parseInt(selectDate1[0]), Integer.parseInt(selectDate1[1]), Integer.parseInt(selectDate1[2]));
			// date.getYear() +" / "+date.getMonth().getValue()+" / "+jcb.getText() + " ";
		} else if (!selectedDate.trim().equals("____________")) {
			selectDate = LocalDate.parse(selectedDate.trim(), DateTimeFormatter.ofPattern("yyyy / M / d"));
			makeCalendar(selectDate);
		}

		pack();
		setTitle("PLANE ADD");
		setLocation(450, 400);
		setModal(true);
		setVisible(true);
	}

	protected void makeCalendar(LocalDate date) {
		JPanel pnl = new JPanel();
		LocalDate todayDate = LocalDate.now();
		nextMonth = date.plusMonths(1);
		lastMonth = date.plusMonths(-1);

		JPanel pnlMonth = new JPanel();
		JLabel lblLastMonth = new JLabel("    <<     ");
		lblLastMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lastMonth.getYear() != todayDate.getYear() || lastMonth.getMonthValue() >= todayDate.getMonthValue()) {
					makeCalendar(lastMonth);
				}
			}
		});
		JLabel lblMonth = new JLabel(date.getYear() + "??? " + date.getMonthValue() + "???");
		lblMonth.setFont(new Font(lblMonth.getFont().getName(), Font.BOLD, 15));
		lblMonth.setForeground(new Color(60, 137, 117));
		JLabel lblNextMonth = new JLabel("     >>    ");
		lblNextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				makeCalendar(nextMonth);
			}

		});
		pnlMonth.add(lblLastMonth);
		pnlMonth.add(lblMonth);
		pnlMonth.add(lblNextMonth);

		JPanel pnlWeek = new JPanel();
		for (int i = 0; i < 7; i++) {
			String day = "";
			switch (i) {
			case (0): day = "???"; break;
			case (1): day = "???"; break;
			case (2): day = "???"; break;
			case (3): day = "???"; break;
			case (4): day = "???"; break;
			case (5): day = "???"; break;
			case (6): day = "???"; break;
			}
			JLabel lblWeek = new JLabel(day);
			lblWeek.setPreferredSize(new Dimension(30, 20));
			lblWeek.setHorizontalAlignment(JLabel.CENTER);
			if (i == 0) {
				lblWeek.setForeground(new Color(255, 110, 110));
			} else if (i == 6) {
				lblWeek.setForeground(new Color(75, 175, 255));
			}
			pnlWeek.add(lblWeek);
		}

		// ??????????????? ????????? ????????????
		JPanel pnlDay = new JPanel(new GridLayout(0, 7));
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			int day = LocalDate.of(date.getYear(), date.getMonth(), i) // ????????? ??????
					.getDayOfWeek().getValue(); // ??????(getDayOfWeek)??? ????????? ?????????(getValue)
			if (day != 7 && i == 1) { // i?????? ?????????(7)??? ???????????? 1?????? 1?????? ????????????
				for (int j = 0; j < day; j++) {
					JLabel lbldayemp = new JLabel(); // ???????????? ??????
					lbldayemp.setPreferredSize(new Dimension(30, 30));
					lbldayemp.setHorizontalAlignment(JLabel.CENTER);
					pnlDay.add(lbldayemp);
				}
			}
			// ?????????????????? ???????????? ???????????????
			if(date.getYear()==todayDate.getYear() && date.getMonthValue()==todayDate.getMonthValue()) {
				if (todayDate.getDayOfMonth()>i) {
					JLabel lblPassday = new JLabel(String.valueOf(i));
					lblPassday.setForeground(Color.LIGHT_GRAY);
					lblPassday.setHorizontalAlignment(JLabel.CENTER);
					lblPassday.setPreferredSize(new Dimension(30, 30));
					pnlDay.add(lblPassday);
				} else if(todayDate.getDayOfMonth()<=i) {
					JLabel lblday = new JLabel(String.valueOf(i));
					lblday.setHorizontalAlignment(JLabel.CENTER);
					lblday.setPreferredSize(new Dimension(30, 30));
					pnlDay.add(lblday);
					click = null;
					if (date.equals(selectDate)) {
						if (i == date.getDayOfMonth()) {
							lblday.setOpaque(true);
							lblday.setBackground(new Color(200, 230, 220));
							lastDateLbl = lblday;
						}
					}
					lblday.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							jcb = (JLabel) e.getSource(); // ?????????
							if (click != null) {
								Color c = UIManager.getColor("Panel.background");
								if (click == lastDateLbl) {
									click.setBackground(new Color(200, 230, 220));
								} else {
									click.setBackground(c);
									pnlDay.revalidate();
									pnlDay.repaint();
								}
							}
							jcb.setOpaque(true);
							jcb.setBackground(new Color(110, 200, 175));
							click = jcb; // click??? ?????? ?????????
							selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + jcb.getText() + "  ";
						}
					});
				}
		    // ????????????????????? ???????????? ????????????
			} else if(date.getYear()!=todayDate.getYear() || date.getMonthValue()!=todayDate.getMonthValue()) {
				JLabel lblday = new JLabel(String.valueOf(i));
				lblday.setHorizontalAlignment(JLabel.CENTER);
				lblday.setPreferredSize(new Dimension(30, 30));
				pnlDay.add(lblday);
				click = null;
				if (date.equals(selectDate)) {
					if (i == date.getDayOfMonth()) {
						lblday.setOpaque(true);
						lblday.setBackground(new Color(200, 230, 220));
						lastDateLbl = lblday;
					}
				}
				lblday.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						jcb = (JLabel) e.getSource(); // ?????????
						if (click != null) {
							Color c = UIManager.getColor("Panel.background");
							if (click == lastDateLbl) {
								click.setBackground(new Color(200, 230, 220));
							} else {
								click.setBackground(c);
								pnlDay.revalidate();
								pnlDay.repaint();
							}
						}
						jcb.setOpaque(true);
						jcb.setBackground(new Color(110, 200, 175));
						click = jcb; // click??? ?????? ?????????
						selectDay = date.getYear() + " / " + date.getMonth().getValue() + " / " + jcb.getText() + "  ";
					}
				});
			}
		}

		JPanel pnlbtn = new JPanel();
		JButton btnOk = new JButton("??????");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jcb != null) {
					booleanJcb = true;
					dispose();
				}
			}
		});
		JButton btnDe = new JButton("??????");
		btnDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booleanJcb = false;
				dispose();
			}
		});
		pnlbtn.add(btnOk);
		pnlbtn.add(btnDe);

		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnlMonth);
		pnl.add(pnlWeek);
		pnl.add(pnlDay);
		pnl.add(pnlbtn);

		getContentPane().removeAll();
		add(pnl);

		pack();

	}
}
