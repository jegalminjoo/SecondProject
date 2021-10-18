import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;

public class Main extends JFrame {
	static List<Customer> customers = new ArrayList<Customer>();
	static List<Plane> planes = new ArrayList<Plane>();

	static int chk = 0;
	private JDialog dialog;
	private JPanel pnlCenter = new JPanel(); // 비행기리스트가 써지는 페널;
	private String usname;
	private int usgrade;
	private int usstar;
	private JLabel lblusinforname;
	private JLabel lblusgrade;
	private JLabel lblusinfor4;
	private JLabel lblgrade2;
	private JPanel pnlmystar1;
	private JPanel pnlmy2;
	private JPanel pnlmyreser;
	private CardLayout card;
	private JPanel pnl;
	private JButton btnplane;
	private JPanel pnlbtn;
	static JLabel lblshowSd;
	static JPanel pnlrv;
	static JPanel pnlReCal;
	static JPanel pnlRe;
	private JPanel pnlsdPlane;
	static JPanel pnlsdCal;
	static JPanel pnlsdDayInfor;
	private JComboBox<Integer> comboPeople;
	private JLabel lblDepDay;
	private JLabel lblDepDay2;
	private JButton btnCal;
	private JLabel lblBackDay;
	private JButton btnCal2;
	private JLabel lblBackDay2;
	private JComboBox<String> comboDepPlace;
	private JComboBox<String> comboArrPlace;
	private JLabel lblDepPlace;
	private JLabel lblArrPlace;
	private String StringcomboDep;
	private JButton btnSearch;
	private JPanel pnlGo;
	private JPanel pnlBack;
	private JRadioButton rdRound;
	private JRadioButton rdOneway;
	private JLabel lblGoInfor;
	private JLabel lblBackInfor;
	private JLabel click;
	private JLabel jcb;
	private JLabel click2;
	private JLabel jcb2;
	private JButton btnGoNext;
	private boolean selectGo;
	private boolean selectBack;
	private JLabel lblselectPlaneInfor;
	private JLabel lblselectPlaneInfor2;
	private JPanel pnlPay;
	private JLabel lblGoSeat2;
	private JLabel lblBackSeat;
	private JLabel lblBackSeat2;
	private JPanel pnlArea1;
	private JPanel pnlArea2;
	private JPanel pnlArea3;
	private JPanel pnlArea6;
	private JPanel pnlArea7;
	private JPanel pnlArea8;
	private JPanel pnlPlaneSeatBack;
	private JButton clickSeatBtn = null;
	private JButton jcbSeatBtn;
	private JLabel lblGoSeat1;
	private JLabel lblMoney2;
	private JLabel lblMyPoint;
	private int allPrice;
	private JTextField tfPoint;
	private JLabel lblGoSeat;
	private JLabel lblBackSeat1;
	private JLabel lblGoSeat3;
	private JLabel lblBackSeat3;
	private boolean usepoint;
	private boolean lookUp;
	private PlaneSchedule deptPlaneSched;
	private PlaneSchedule arrvPlaneSched;

	public Main() {
		// 회원들 불러오기(저장된거)
		if (CustomerIO.USER_FILE.exists())
			customers = CustomerIO.load();
		else
			customers = new ArrayList<Customer>();

		// 비행기추가한거 불러오기(저장된거)
		if (PlaneIO.PLANE_LIST.exists()) {
			planes = PlaneIO.load();
		} else
			planes = new ArrayList<Plane>();

		firstmakeplanelbl(pnlCenter);
		// 관리자 등록 (등록이 안되어있을때만 등록해라 제발)
		boolean chkAdmin = true;
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getUid().equals("admin")) {
				chkAdmin = false;
			}
		}
		if (chkAdmin == true) {
			customers.add(new Customer("관리자", "", "", "admin", "admin#"));

		}
		card = new CardLayout();
		pnl = new JPanel(card);

//==============================================================================================================

		// ------ 첫화면 ------
		URL lblicon = Main.class.getClassLoader().getResource("mainimage.jpg");
		ImageIcon iconImage = new ImageIcon(lblicon);
		JLabel lblImage = new JLabel(iconImage);
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Login(customers);
				if (chk == 1) {
					if (!(Login.tfLogid.getText() == null || Login.tfLogid.getText().trim().equals(""))) {
						card.show(pnl, "관리자");
					}
				} else if (chk == 2) {
					if (!(Login.tfLogid.getText() == null || Login.tfLogid.getText().trim().equals(""))) {
						card.show(pnl, "사용자");
						userMypage();
					}
				}
			}
		});

//==============================================================================================================

		// ------ 관리자 페이지 ------
		JTabbedPane tabPaneAd = new JTabbedPane();

		// ---비행기관리
		JPanel pnlplane = new JPanel(); // 비행기관리
		pnlplane.setLayout(new BorderLayout());

//		pnlCenter = new JPanel(); // 비행기리스트가 써지는 페널 (맨위에 선언되어있음)
		JScrollPane scrl = new JScrollPane(pnlCenter); // 스크롤
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));

		JPanel pnlplbtn = new JPanel();
		JPanel pblBtnLodout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pblBtnLodout.setPreferredSize(new Dimension(380, 32));
		JButton btnlogout = new JButton("로그아웃");
		pblBtnLodout.add(btnlogout);
		btnlogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Main();
			}
		});
		JPanel pblBtnAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pblBtnAdd.setPreferredSize(new Dimension(380, 32));
		JButton btnadd = new JButton("비행기 추가");
		pblBtnAdd.add(btnadd);
		btnadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Plane tempPlane = new Plane();
				new PlaneAdd(tempPlane);

				if (tempPlane.planeName != null) {
					planes.add(tempPlane); // 비행기리스트에 추가
					PlaneIO.save(planes);
					// 새로 추가한거 스케줄 관리페이지에 버튼새로 그려줌
					makepnlsdPlane(pnlsdPlane);
					// 비행기 레이블 만들기!
					int j = planes.size() - 1;
					makeplanelbl(pnlCenter, j);
				}
			}
		});
		pnlplbtn.add(pblBtnLodout, "West");
		pnlplbtn.add(pblBtnAdd, "East");

//		pnlplane.setLayout(new BoxLayout(pnlplane, BoxLayout.Y_AXIS));
		pnlplane.add(pnlplbtn, "North");
		pnlplane.add(scrl);

		// ---스케줄 관리
		JPanel pnlsd = new JPanel(); // 스케줄 관리
		JTabbedPane tabPaneSd = new JTabbedPane();

		// --------------기종별
		pnlsdPlane = new JPanel(new BorderLayout());
		JScrollPane scrlSdPlane = new JScrollPane(pnlsdPlane);
		makepnlsdPlane(pnlsdPlane);
		// --------------날짜별
		JPanel pnlsdDay = new JPanel(new BorderLayout());
		JScrollPane scrlSdDay = new JScrollPane(pnlsdDay);
		pnlsdCal = new JPanel();
		pnlsdDayInfor = new JPanel();
		pnlsdDayInfor.setLayout(new BoxLayout(pnlsdDayInfor, BoxLayout.Y_AXIS));
		new Calendar3(pnlsdCal, pnlsdDayInfor);
		pnlsdDay.add(pnlsdCal, "North");
		pnlsdDay.add(pnlsdDayInfor, "Center");

		tabPaneSd.add(scrlSdPlane, "비행기별");
		tabPaneSd.add(scrlSdDay, "날짜별");

		// ---회원 관리
		JPanel pnlcus = new JPanel();
		pnlcus.setLayout(new BoxLayout(pnlcus, BoxLayout.Y_AXIS));
		JScrollPane scrlCus = new JScrollPane(pnlcus);
		for (Customer cus : customers) {
			if (!cus.getUid().trim().equals("admin")) {
				JPanel pnlCusAll = new JPanel();
				pnlCusAll.setPreferredSize(new Dimension(750, 60));
				pnlCusAll.setMaximumSize(new Dimension(750, 60));
				JPanel pnlCus = new JPanel();
				pnlCus.setPreferredSize(new Dimension(600, 48));
				pnlCus.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
				// 회원이름
				JPanel pnlCus1 = new JPanel();
				JLabel lblCus = new JLabel(" 회원이름  :  " + cus.getName());
				lblCus.setPreferredSize(new Dimension(160, 30));
				pnlCus1.add(lblCus);
				// 등급 콤보
				JPanel pnlCus2 = new JPanel();
				JLabel lblGrade = new JLabel("/           등급 : ");
				String[] grade = { "실버", "골드", "다이아" };
				JComboBox<String> comboGrade = new JComboBox<String>(grade);
				if (cus.getGrade() == 0) {
					comboGrade.setSelectedItem("실버");
				} else if (cus.getGrade() == 1) {
					comboGrade.setSelectedItem("골드");
				} else if (cus.getGrade() == 2) {
					comboGrade.setSelectedItem("다이아");
				}
				comboGrade.setEnabled(false);
				pnlCus2.add(lblGrade);
				pnlCus2.add(comboGrade);
				// 마일리지
				JPanel pnlCus3 = new JPanel();
				JLabel lblPoint = new JLabel("      /           마일리지  :  ");
				NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
				formatter.setAllowsInvalid(false); // 허용이 유효하지 않음(false) -> ↑을허용해라!
				JTextField tfPoint = new JFormattedTextField(formatter);
				tfPoint.setColumns(5);
				tfPoint.setText(String.valueOf(cus.getPoint()));
				tfPoint.setEnabled(false);
				pnlCus3.add(lblPoint);
				pnlCus3.add(tfPoint);

				JButton btnCusMd = new JButton("수정");
				btnCusMd.setPreferredSize(new Dimension(60, 48));
				btnCusMd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboGrade.setEnabled(true);
						tfPoint.setEnabled(true);
					}
				});
				JButton btnCusOk = new JButton("확인");
				btnCusOk.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboGrade.setEnabled(false);
						tfPoint.setEnabled(false);
						if (comboGrade.getSelectedIndex() == 0) {
							cus.setGrade(0);
						} else if (comboGrade.getSelectedIndex() == 1) {
							cus.setGrade(1);
							cus.setStar(12);
						} else if (comboGrade.getSelectedIndex() == 2) {
							cus.setGrade(2);
							cus.setStar(24);
						}
						String[] point = tfPoint.getText().split(",");
						cus.setPoint(Integer.parseInt(point[0] + point[1]));
						CustomerIO.save(customers);
					}
				});
				btnCusOk.setPreferredSize(new Dimension(60, 48));
				pnlCus.add(pnlCus1);
				pnlCus.add(pnlCus2);
				pnlCus.add(pnlCus3);
				pnlCusAll.add(pnlCus);
				pnlCusAll.add(btnCusMd);
				pnlCusAll.add(btnCusOk);
				pnlcus.add(pnlCusAll);
			}
		}

		// ---예약 관리
		pnlrv = new JPanel(new BorderLayout());
		JScrollPane scrlRe = new JScrollPane(pnlrv);
		pnlReCal = new JPanel();
		pnlRe = new JPanel();
		pnlRe.setLayout(new BoxLayout(pnlRe, BoxLayout.Y_AXIS));

		new Calendar2(pnlReCal, pnlRe);

		pnlrv.add(pnlReCal, "North");
		pnlrv.add(pnlRe, "Center");

		tabPaneAd.add(pnlplane, "비행기 관리");
		tabPaneAd.add(tabPaneSd, "스케줄 관리");
		tabPaneAd.add(scrlCus, "회원 관리");
		tabPaneAd.add(scrlRe, "예약 관리");

//==============================================================================================================

		// ------ 사용자 페이지 ------
		JTabbedPane tabPaneUs = new JTabbedPane();
		CardLayout card2 = new CardLayout();
		JPanel pnlUsRv = new JPanel(card2);

		// 예약페이지
		JPanel pnlUsRvPage = new JPanel();
		pnlUsRvPage.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 3)));
		URL lbllogo = Main.class.getClassLoader().getResource("logo2.png");
		ImageIcon iconlogo = new ImageIcon(lbllogo);
		JLabel lbllogo2 = new JLabel(iconlogo);
		pnlUsRvPage.add(lbllogo2);
		JPanel pnlUsRvPage2 = new JPanel();
		// --- 예약스케줄 지정하는곳
		JPanel pnlUsReser = new JPanel();
		pnlUsReser.setLayout(new BoxLayout(pnlUsReser, BoxLayout.Y_AXIS));
		pnlUsReser.setPreferredSize(new Dimension(250, 250));
		pnlUsReser.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 2)));
		JPanel pnlUsReser1 = new JPanel(); // 왕복편도
		rdRound = new JRadioButton("왕복");
		rdRound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboPeople.setEnabled(true);
				lblDepDay.setForeground(Color.black);
				lblDepDay2.setForeground(Color.black);
				lblDepDay2.setText("____________");
				btnCal.setEnabled(true);
				lblBackDay.setForeground(Color.black);
				lblBackDay2.setForeground(Color.black);
				lblBackDay2.setText("____________");
				btnCal2.setEnabled(true);
				lblDepPlace.setForeground(Color.black);
				comboDepPlace.setForeground(Color.black);
				comboDepPlace.setEnabled(false);
				comboDepPlace.setSelectedIndex(0);
				lblArrPlace.setForeground(Color.black);
				comboArrPlace.setForeground(Color.black);
				comboArrPlace.setEnabled(false);
				comboArrPlace.setSelectedIndex(0);
				comboPeople.setSelectedIndex(0);
				btnSearch.setEnabled(true);
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
			}
		});
		rdOneway = new JRadioButton("편도");
		rdOneway.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboPeople.setEnabled(true);
				lblDepDay.setForeground(Color.black);
				lblDepDay2.setForeground(Color.black);
				lblDepDay2.setText("____________");
				btnCal.setEnabled(true);
				lblBackDay.setForeground(new Color(140, 140, 140));
				lblBackDay2.setForeground(new Color(140, 140, 140));
				lblBackDay2.setText("____________");
				btnCal2.setEnabled(false);
				lblDepPlace.setForeground(Color.black);
				comboDepPlace.setForeground(Color.black);
				comboDepPlace.setEnabled(false);
				comboDepPlace.setSelectedIndex(0);
				lblArrPlace.setForeground(Color.black);
				comboArrPlace.setForeground(Color.black);
				comboArrPlace.setEnabled(false);
				comboArrPlace.setSelectedIndex(0);
				comboPeople.setSelectedIndex(0);
				btnSearch.setEnabled(true);
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
			}
		});
		ButtonGroup group = new ButtonGroup();
		group.add(rdRound);
		group.add(rdOneway);
		JLabel lblUsPeople = new JLabel("인원");
		Integer[] people = { 0, 1, 2 };
		comboPeople = new JComboBox<Integer>(people);
		comboPeople.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
			}
		});
		comboPeople.setEnabled(false);
		pnlUsReser1.add(rdRound);
		pnlUsReser1.add(rdOneway);
		pnlUsReser1.add(lblUsPeople);
		pnlUsReser1.add(comboPeople);
		JPanel pnlUsReser2 = new JPanel(); // 출발하는 날자
		lblDepDay = new JLabel(" 출발하는 날짜 ");
		lblDepDay.setForeground(new Color(140, 140, 140));
		btnCal = new JButton();
		btnCal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
				new Calendar(lblDepDay2.getText());
				if (Calendar.booleanJcb == true) {
					lblDepDay2.setText(Calendar.selectDay);
					lblDepDay2.setForeground(new Color(60, 137, 117));
					lblBackDay2.setText("____________");
					if (rdRound.isSelected()) {
						comboDepPlace.setEnabled(false);
						comboDepPlace.setSelectedIndex(0);
						comboArrPlace.setEnabled(false);
						comboArrPlace.setSelectedIndex(0);
					} else if (rdOneway.isSelected()) {
						comboDepPlace.setEnabled(true);
						comboDepPlace.setSelectedIndex(0);
						comboArrPlace.setEnabled(false);
						comboArrPlace.setSelectedIndex(0);
					}
				}
			}
		});
		btnCal.setEnabled(false);
		btnCal.setPreferredSize(new Dimension(15, 15));
		btnCal.setBackground(new Color(60, 137, 117));
		lblDepDay2 = new JLabel("____________");
		lblDepDay2.setForeground(new Color(140, 140, 140));
		pnlUsReser2.add(lblDepDay);
		pnlUsReser2.add(btnCal);
		pnlUsReser2.add(lblDepDay2);
		JPanel pnlUsReser3 = new JPanel(); // 돌아오는 날자
		lblBackDay = new JLabel(" 돌아오는 날짜 ");
		lblBackDay.setForeground(new Color(140, 140, 140));
		btnCal2 = new JButton();
		btnCal2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
				if (!lblDepDay2.getText().equals("____________")) {
					new Calendar4(lblDepDay2.getText());
				} else if (lblDepDay2.getText().equals("____________")) {
					JOptionPane.showMessageDialog(btnCal2, "출발하는 날짜 먼저 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				if (Calendar4.booleanJcb == true) {
					lblBackDay2.setText(Calendar4.selectDay);
					lblBackDay2.setForeground(new Color(60, 137, 117));
					comboDepPlace.setEnabled(true);
				}
			}
		});
		btnCal2.setEnabled(false);
		btnCal2.setPreferredSize(new Dimension(15, 15));
		btnCal2.setBackground(new Color(60, 137, 117));
		lblBackDay2 = new JLabel("____________");
		lblBackDay2.setForeground(new Color(140, 140, 140));
		pnlUsReser3.add(lblBackDay);
		pnlUsReser3.add(btnCal2);
		pnlUsReser3.add(lblBackDay2);
		JPanel pnlUsReser4 = new JPanel(); // 출발지 콤보
		lblDepPlace = new JLabel("출 발");
		lblDepPlace.setForeground(new Color(140, 140, 140));
		String[] place = { "선택", "서울", "부산", "제주" };
		comboDepPlace = new JComboBox<String>(place);
		StringcomboDep = "";
		comboDepPlace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
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
					comboArrPlace.removeItem(depPlace);
					comboArrPlace.setEnabled(true);
					comboArrPlace.setSelectedIndex(0);

				} else if (depPlace.equals("선택")) {
					comboArrPlace.setEnabled(false);
					comboArrPlace.setSelectedIndex(0);
				}
			}
		});
		comboDepPlace.setForeground(new Color(140, 140, 140));
		comboDepPlace.setEnabled(false);
		lblArrPlace = new JLabel("도 착");
		lblArrPlace.setForeground(new Color(140, 140, 140));
		comboArrPlace = new JComboBox<String>(place);
		comboArrPlace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlGoBackRemoveallRepaint();
				btnGoNext.setEnabled(false);
			}
		});
		comboArrPlace.setForeground(new Color(140, 140, 140));
		comboArrPlace.setEnabled(false);
		pnlUsReser4.add(lblDepPlace);
		pnlUsReser4.add(comboDepPlace);
		pnlUsReser4.add(lblArrPlace);
		pnlUsReser4.add(comboArrPlace);
		JPanel pnlUsReser5 = new JPanel(); // 조회하기 버튼
		btnSearch = new JButton("조회하기");
		btnSearch.setEnabled(false);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnGoNext.setEnabled(false);
				if (rdRound.isSelected()) {
					if (comboPeople.getSelectedIndex() != 0 && comboArrPlace.getSelectedIndex() != 0) {
						makeSelectedPlaneSd();
					} else {
						JOptionPane.showMessageDialog(btnCal2, "조회 하고싶은 정보를 선택해주세요", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (rdOneway.isSelected()) {
					if (comboPeople.getSelectedIndex() != 0 && comboArrPlace.getSelectedIndex() != 0) {
						makeSelectedPlaneSd();
					} else {
						JOptionPane.showMessageDialog(btnCal2, "조회 하고싶은 정보를 선택해주세요", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		pnlUsReser5.add(btnSearch);

		pnlUsReser.add(pnlUsReser1);
		pnlUsReser.add(pnlUsReser2);
		pnlUsReser.add(pnlUsReser3);
		pnlUsReser.add(pnlUsReser4);
		pnlUsReser.add(pnlUsReser5);

		// --- 조회한 스케줄 보는곳
		JPanel pnlUsReSd = new JPanel();
		pnlUsReSd.setLayout(new BoxLayout(pnlUsReSd, BoxLayout.Y_AXIS));
		JTabbedPane tabPaneShowSd = new JTabbedPane();
		// -----가는편
		pnlGo = new JPanel();
		pnlGo.setLayout(new BoxLayout(pnlGo, BoxLayout.Y_AXIS));
		JScrollPane scrlGo = new JScrollPane(pnlGo);
		scrlGo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrlGo.setPreferredSize(new Dimension(500, 250));
		lblGoInfor = new JLabel("가는편 스케줄이 없습니다. 다른날짜를 선택해주세요");
		// -----오는편
		pnlBack = new JPanel();
		pnlBack.setLayout(new BoxLayout(pnlBack, BoxLayout.Y_AXIS));
		JScrollPane scrlBack = new JScrollPane(pnlBack);
		scrlBack.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrlBack.setPreferredSize(new Dimension(500, 250));
		lblBackInfor = new JLabel("오는편 스케줄이 없습니다. 다른날짜를 선택해주세요");
		// -----좌석선택하러 가기 버튼
		JPanel pnlGoNext = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlGoNext.setPreferredSize(new Dimension(500, 38));
		btnGoNext = new JButton("좌석 선택하러 가기");
		btnGoNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card2.show(pnlUsRv, "결제");
			}
		});
		btnGoNext.setEnabled(false);
		pnlGoNext.add(btnGoNext);

		tabPaneShowSd.add(scrlGo, "가는편");
		tabPaneShowSd.add(scrlBack, "오는편");
		pnlUsReSd.add(tabPaneShowSd);
		pnlUsReSd.add(pnlGoNext);
		pnlUsRvPage2.add(pnlUsReser);
		pnlUsRvPage2.add(pnlUsReSd);
		pnlUsRvPage.add(pnlUsRvPage2);

		// --- 좌석 지정하는곳
		JPanel pnlUsSeatPayAll = new JPanel();
		URL lbllogo3 = Main.class.getClassLoader().getResource("logo2.png");
		ImageIcon iconlogo2 = new ImageIcon(lbllogo3);
		JLabel lbllogo4 = new JLabel(iconlogo2);
		pnlUsSeatPayAll.add(lbllogo4);

		JPanel pnlUsSeatPayAll2 = new JPanel();
		JPanel pnlUsSeat = new JPanel();
//		pnlUsPay.setLayout(new BoxLayout(pnlUsPay, BoxLayout.Y_AXIS));
		JTabbedPane tabPaneShowPlaneSeat = new JTabbedPane();
		// ----- 비행기좌석 (가는거)
		JPanel pnlPlaneSeatGo = new JPanel(new BorderLayout());
		pnlPlaneSeatGo.setPreferredSize(new Dimension(590, 250));
		pnlPlaneSeatGo.setLayout(new BoxLayout(pnlPlaneSeatGo, BoxLayout.Y_AXIS));
		JPanel pnlSeatInfor = new JPanel();
		JLabel lblSeatSPrice = new JLabel("  S좌석 : 20,000원  ");
		JButton btnSeatS = new JButton();
		btnSeatS.setPreferredSize(new Dimension(30, 30));
		btnSeatS.setBackground(new Color(180, 250, 255));
		JLabel lblSeatGPrice = new JLabel("  G좌석 : 30,000원  ");
		JButton btnSeatG = new JButton();
		btnSeatG.setPreferredSize(new Dimension(40, 30));
		btnSeatG.setBackground(new Color(255, 205, 170));
		JLabel lblSeatVPrice = new JLabel("  V좌석 : 40,000원  ");
		JButton btnSeatV = new JButton();
		btnSeatV.setPreferredSize(new Dimension(50, 30));
		btnSeatV.setBackground(new Color(246, 244, 177));
		pnlSeatInfor.add(btnSeatS);
		pnlSeatInfor.add(lblSeatSPrice);
		pnlSeatInfor.add(btnSeatG);
		pnlSeatInfor.add(lblSeatGPrice);
		pnlSeatInfor.add(btnSeatV);
		pnlSeatInfor.add(lblSeatVPrice);
		JPanel pnlArea = new JPanel();
		pnlArea1 = new JPanel();
		pnlArea1 = new JPanel();
		pnlArea1.setPreferredSize(new Dimension(190, 120));
		pnlArea1.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "1구역"));
		pnlArea.add(pnlArea1);
		pnlArea2 = new JPanel();
		pnlArea2 = new JPanel();
		pnlArea2.setPreferredSize(new Dimension(190, 120));
		pnlArea2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "2구역"));
		pnlArea.add(pnlArea2);
		pnlArea3 = new JPanel();
		pnlArea3 = new JPanel();
		pnlArea3.setPreferredSize(new Dimension(190, 120));
		pnlArea3.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "3구역"));
		pnlArea.add(pnlArea3);
		pnlPlaneSeatGo.add(pnlSeatInfor);
		pnlPlaneSeatGo.add(pnlArea);
		pnlPlaneSeatBack = new JPanel();
		pnlPlaneSeatBack.setPreferredSize(new Dimension(500, 250));
		pnlPlaneSeatBack.setLayout(new BoxLayout(pnlPlaneSeatBack, BoxLayout.Y_AXIS));
		JPanel pnlSeatInforBack = new JPanel();
		JLabel lblSeatSPriceBack = new JLabel("  S좌석 : 20,000원  ");
		JButton btnSeatSBack = new JButton();
		btnSeatSBack.setPreferredSize(new Dimension(30, 30));
		btnSeatSBack.setBackground(new Color(180, 250, 255));
		JLabel lblSeatGPriceBack = new JLabel("  G좌석 : 30,000원  ");
		JButton btnSeatGBack = new JButton();
		btnSeatGBack.setPreferredSize(new Dimension(40, 30));
		btnSeatGBack.setBackground(new Color(255, 205, 170));
		JLabel lblSeatVPriceBack = new JLabel("  V좌석 : 40,000원  ");
		JButton btnSeatVBack = new JButton();
		btnSeatVBack.setPreferredSize(new Dimension(50, 30));
		btnSeatVBack.setBackground(new Color(246, 244, 177));
		pnlSeatInforBack.add(btnSeatSBack);
		pnlSeatInforBack.add(lblSeatSPriceBack);
		pnlSeatInforBack.add(btnSeatGBack);
		pnlSeatInforBack.add(lblSeatGPriceBack);
		pnlSeatInforBack.add(btnSeatVBack);
		pnlSeatInforBack.add(lblSeatVPriceBack);
		JPanel pnlArea5 = new JPanel();
		pnlArea6 = new JPanel();
		pnlArea6 = new JPanel();
		pnlArea6.setPreferredSize(new Dimension(190, 120));
		pnlArea6.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "1구역"));
		pnlArea5.add(pnlArea6);
		pnlArea7 = new JPanel();
		pnlArea7 = new JPanel();
		pnlArea7.setPreferredSize(new Dimension(190, 120));
		pnlArea7.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "2구역"));
		pnlArea5.add(pnlArea7);
		pnlArea8 = new JPanel();
		pnlArea8 = new JPanel();
		pnlArea8.setPreferredSize(new Dimension(190, 120));
		pnlArea8.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray, 1), "3구역"));
		pnlArea5.add(pnlArea8);
		pnlPlaneSeatBack.add(pnlSeatInforBack);
		pnlPlaneSeatBack.add(pnlArea5);

		// --- 결제하는곳
		pnlPay = new JPanel();
		pnlPay.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
		pnlPay.setPreferredSize(new Dimension(190, 300));
		JPanel pnlPay1 = new JPanel(); // 선택한 비행기정보 (편도)
		lblselectPlaneInfor = new JLabel();
		pnlPay1.add(lblselectPlaneInfor);
		JPanel pnlPay2 = new JPanel(); // 선택한 비행기정보 (왕복시)
		lblselectPlaneInfor2 = new JLabel();
		pnlPay2.add(lblselectPlaneInfor2);
		JPanel pnlPayLine = new JPanel(); // 선택한 비행기정보 (왕복시)
		JLabel lblPayLine = new JLabel("-----------------------------------------------");
		pnlPayLine.add(lblPayLine);
		pnlPay2.add(lblselectPlaneInfor2);
		JPanel pnlPay3 = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 가는편좌석
		lblGoSeat = new JLabel(" 가는편-");
		lblGoSeat1 = new JLabel("");
		lblGoSeat2 = new JLabel(" / ");
		lblGoSeat3 = new JLabel("");
		pnlPay3.add(lblGoSeat);
		pnlPay3.add(lblGoSeat1);
		pnlPay3.add(lblGoSeat2);
		pnlPay3.add(lblGoSeat3);
		JPanel pnlPay4 = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 오는편좌석
		lblBackSeat = new JLabel(" 오는편-");
		lblBackSeat1 = new JLabel();
		lblBackSeat2 = new JLabel(" / ");
		lblBackSeat3 = new JLabel();
		pnlPay4.add(lblBackSeat);
		pnlPay4.add(lblBackSeat1);
		pnlPay4.add(lblBackSeat2);
		pnlPay4.add(lblBackSeat3);
		JPanel pnlPayLine2 = new JPanel(); // 선택한 비행기정보 (왕복시)
		JLabel lblPayLine2 = new JLabel("-----------------------------------------------");
		pnlPayLine2.add(lblPayLine2);
		JPanel pnlPay5 = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 결제금액
		JLabel lblMoney = new JLabel("  결제금액 : ");
		lblMoney2 = new JLabel();
		pnlPay5.add(lblMoney);
		pnlPay5.add(lblMoney2);
		JPanel pnlPay6 = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 포인트 조회
		JLabel lblPoint = new JLabel("  포인트 사용");
		JButton btnLookUp = new JButton("조회");
		lookUp = false;
		btnLookUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Customer cus : customers) {
					if (Login.tfLogid.getText().equals(cus.getUid())) {
						lblMyPoint.setText(cus.getPoint() + "");
						tfPoint.setEnabled(true);
						lookUp = true;
					}
				}
			}
		});
		btnLookUp.setPreferredSize(new Dimension(50, 20));
		btnLookUp.setMargin(new Insets(0, 0, 0, 0));
		pnlPay6.add(lblPoint);
		pnlPay6.add(btnLookUp);
		JPanel pnlPay7 = new JPanel(); // 포인트 입력
		NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		formatter.setAllowsInvalid(false); // 허용이 유효하지 않음(false) -> ↑을허용해라!
		tfPoint = new JFormattedTextField(formatter);
		tfPoint.setColumns(6);
		tfPoint.setEnabled(false);
		lblMyPoint = new JLabel("_______");
		allPrice = 0;
		JButton btnUsePoint = new JButton("사용");
		usepoint = false;
		btnUsePoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] Stringpoint = tfPoint.getText().split(",");
				String[] StringtotalMoney = lblMoney2.getText().trim().split("원");
				if (Stringpoint.length < 2) {
					JOptionPane.showMessageDialog(lblMoney2, "1000점이상 사용가능합니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Integer.parseInt(lblMyPoint.getText()) >= Integer.parseInt(Stringpoint[0] + Stringpoint[1])
						&& Integer.parseInt(Stringpoint[0] + Stringpoint[1]) <= Integer.parseInt(StringtotalMoney[0])) { // 전체금액
					allPrice = allPrice - Integer.parseInt(Stringpoint[0] + Stringpoint[1]);
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					btnUsePoint.setEnabled(false);
					tfPoint.setEnabled(false);
					btnLookUp.setEnabled(false);
					usepoint = true;
				} else if (Integer.parseInt(lblMyPoint.getText()) < Integer.parseInt(Stringpoint[0] + Stringpoint[1])) {
					JOptionPane.showMessageDialog(lblMoney2, "포인트를 초과하셨습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
					tfPoint.setText("0");
				} else if (Integer.parseInt(Stringpoint[0] + Stringpoint[1]) > Integer.parseInt(StringtotalMoney[0])) {
					JOptionPane.showMessageDialog(lblMoney2, "총 금액보다 많이 사용하실수 없습니다.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					tfPoint.setText("0");
				}
			}
		});
		btnUsePoint.setPreferredSize(new Dimension(40, 20));
		btnUsePoint.setMargin(new Insets(0, 0, 0, 0));
		pnlPay7.add(tfPoint);
		pnlPay7.add(lblMyPoint);
		pnlPay7.add(btnUsePoint);
		JPanel pnlPay8 = new JPanel(); // 결제하기
		JButton btnPay = new JButton("             결제하기              ");

		btnPay.addActionListener(new ActionListener() {
			private String planeNoBack;
			private String depPlaceBack;
			private String arrPlaceBack;
			private String depTimeBack;

			@Override
			public void actionPerformed(ActionEvent e) {
				String person1DepSeat = lblGoSeat1.getText();
				String person1ArrSeat = null;
				String person2DepSeat = null;
				String person2ArrSeat = null;
				List<UserSchedule> usSdList = null;
				for (Customer cus : customers) {
					if (cus.getUid().trim().equals(Login.tfLogid.getText())) {
						if (cus.getSchedules() == null) {
							usSdList = new ArrayList<UserSchedule>();
							cus.setSchedules(usSdList);
						} else {
							usSdList = cus.getSchedules();
						}
					}
				}
				UserSchedule usSd = new UserSchedule();
				String[] planeFinal = lblselectPlaneInfor.getText().trim().split("/");
				String[] planeFinal2 = planeFinal[1].trim().split("▷"); // 출발지[0] ▷ 도착지[1]
				String planeNo = planeFinal[0].trim();
				String depPlace = planeFinal2[0].trim().substring(0);
				String arrPlace = planeFinal2[1].trim().substring(0);
				String depTime = planeFinal[2].trim().substring(0);
				List<String> selectedSeat = new ArrayList<String>();

				// 결제하기 조건 & 선택한 좌석
				if (lblGoSeat1.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(lblMoney2, "좌석을 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (rdRound.isSelected()) { // 왕복일때
					person1ArrSeat = lblBackSeat1.getText();
					if (lblBackSeat1.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(lblMoney2, "돌아오는편 좌석을 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (comboPeople.getSelectedIndex() == 2) { // 2명일때
					if (rdOneway.isSelected()) { // 편도일때
						person2DepSeat = lblGoSeat3.getText();
						if (lblGoSeat3.getText().trim().equals("")) {
							JOptionPane.showMessageDialog(lblMoney2, "좌석을 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else if (rdRound.isSelected()) { // 왕복일때
						person2DepSeat = lblGoSeat3.getText();
						person2ArrSeat = lblBackSeat3.getText();
						if (lblBackSeat3.getText().trim().equals("")) {
							JOptionPane.showMessageDialog(lblMoney2, "돌아오는편 좌석을 선택하세요", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				if (lookUp == false) {
					System.out.println("노상관");
				} else if (lookUp == true && !tfPoint.getText().equals("0") && !tfPoint.getText().equals("")
						&& usepoint == false) {
					JOptionPane.showMessageDialog(lblMoney2, "포인트 사용하기를 누르세요", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 유저 스케줄 리스트에 저장하기
				if (rdOneway.isSelected()) { // 편도
					usSd.setPlaneNo(planeNo);
					usSd.setDeptPlace(depPlace);
					usSd.setArrvPlace(arrPlace);
					usSd.setDeptTime(depTime);
					if (comboPeople.getSelectedIndex() == 1) { // 1명
						selectedSeat.add(person1DepSeat);
					} else { // 2명
						selectedSeat.add(person1DepSeat);
						selectedSeat.add(person2DepSeat);
					}
					usSd.setSelectedSeat(selectedSeat);
					usSdList.add(usSd);
				} else if (rdRound.isSelected()) { // 왕복
					// 가는 여정
					usSd.setPlaneNo(planeNo);
					usSd.setDeptPlace(depPlace);
					usSd.setArrvPlace(arrPlace);
					usSd.setDeptTime(depTime);
					if (comboPeople.getSelectedIndex() == 1) { // 1명
						selectedSeat.add(person1DepSeat);
					} else { // 2명
						selectedSeat.add(person1DepSeat);
						selectedSeat.add(person2DepSeat);
					}
					usSd.setSelectedSeat(selectedSeat);
					usSdList.add(usSd);
					// 돌아오는 여정
					UserSchedule usSd2 = new UserSchedule();
					String[] planeFinalBack = lblselectPlaneInfor2.getText().trim().split("/");
					String[] planeFinalBack2 = planeFinalBack[1].trim().split("▷"); // 출발지[0] ▷ 도착지[1]
					planeNoBack = planeFinalBack[0].trim();
					depPlaceBack = planeFinalBack2[0].trim().substring(0);
					arrPlaceBack = planeFinalBack2[1].trim().substring(0);
					depTimeBack = planeFinalBack[2].trim().substring(0);
					List<String> selectedSeatBack = new ArrayList<String>();
					usSd2.setPlaneNo(planeNoBack);
					usSd2.setDeptPlace(depPlaceBack);
					usSd2.setArrvPlace(arrPlaceBack);
					usSd2.setDeptTime(depTimeBack);
					if (comboPeople.getSelectedIndex() == 1) { // 1명
						selectedSeatBack.add(person1ArrSeat);
					} else { // 2명
						selectedSeatBack.add(person1ArrSeat);
						selectedSeatBack.add(person2ArrSeat);
					}
					usSd2.setSelectedSeat(selectedSeatBack);
					usSdList.add(usSd2);
				}
				// 포인트 사용했으면 포인트 삭제
				if (!tfPoint.getText().equals("0") && !tfPoint.getText().equals("")) {
					for (Customer cus : customers) {
						if (cus.getUid().trim().equals(Login.tfLogid.getText())) {
							// 포인트접립
							String[] usepoint = tfPoint.getText().trim().split(",");
							String[] totalMoney = lblMoney2.getText().trim().split("원");
							if (cus.getGrade() == 0) {
								int givepoint = Integer.parseInt(totalMoney[0]) / 10000;
								int totalpoint = cus.getPoint() - Integer.parseInt(usepoint[0] + usepoint[1])
										+ givepoint;
								cus.setPoint(totalpoint);
							} else if (cus.getGrade() == 1) {
								int givepoint = Integer.parseInt(totalMoney[0]) / 5000;
								int totalpoint = cus.getPoint() - Integer.parseInt(usepoint[0] + usepoint[1])
										+ givepoint;
								cus.setPoint(totalpoint);
							} else if (cus.getGrade() == 2) {
								int givepoint = Integer.parseInt(totalMoney[0]) / 1000;
								int totalpoint = cus.getPoint() - Integer.parseInt(usepoint[0] + usepoint[1])
										+ givepoint;
								cus.setPoint(totalpoint);
							}

						}
					}
				}
				// 스티커 주고
				int newstar = 0;
				if (rdOneway.isSelected()) {
					if (selectedSeat.size() == 1) {
						newstar += 1;
					} else if (selectedSeat.size() == 2) {
						newstar += 2;
					}
				} else if (rdRound.isSelected()) {
					if (selectedSeat.size() == 1) {
						newstar += 2;
					} else if (selectedSeat.size() == 2) {
						newstar += 4;
					}
				}
				for (Customer cus : customers) {
					if (cus.getUid().trim().equals(Login.tfLogid.getText())) {
						int totalStar = cus.getStar() + newstar;
						cus.setStar(totalStar);
					}
				}
				
				int section = Integer.parseInt(person1DepSeat.substring(0, 1)) - 1;
				String seatGrade = person1DepSeat.substring(4, 5);
				int seatNo = Integer.parseInt(person1DepSeat.substring(5));
				if (seatGrade.equals("V")) {
					deptPlaneSched.getSeatSelectedV[section].set(seatNo - 1, true);
				} else if (seatGrade.equals("G")) {
					deptPlaneSched.getSeatSelectedG[section].set(seatNo - 1, true);
				} else if (seatGrade.equals("S")) {
					deptPlaneSched.getSeatSelectedS[section].set(seatNo - 1, true);
				}
				if (person2DepSeat != null) {
					section = Integer.parseInt(person2DepSeat.substring(0, 1)) - 1;
					seatGrade = person2DepSeat.substring(4, 5);
					seatNo = Integer.parseInt(person2DepSeat.substring(5));
					if (seatGrade.equals("V")) {
						deptPlaneSched.getSeatSelectedV[section].set(seatNo - 1, true);
					} else if (seatGrade.equals("G")) {
						deptPlaneSched.getSeatSelectedG[section].set(seatNo - 1, true);
					} else if (seatGrade.equals("S")) {
						deptPlaneSched.getSeatSelectedS[section].set(seatNo - 1, true);
					}
				}
				
				JOptionPane.showMessageDialog(null, "예약이 완료되었습니다.", "Thank you", JOptionPane.INFORMATION_MESSAGE);
				CustomerIO.save(customers);
				card2.show(pnlUsRv, "예약");
				PlaneIO.save(planes);
				// 마이페이지 다시그리기
				tabPaneUs.setSelectedIndex(1);
				userMypage();
			}
		});
		pnlPay8.add(btnPay);

		pnlPay.setLayout(new BoxLayout(pnlPay, BoxLayout.Y_AXIS));
		pnlPay.add(pnlPay1);
		pnlPay.add(pnlPay2);
		pnlPay.add(pnlPayLine);
		pnlPay.add(pnlPay3);
		pnlPay.add(pnlPay4);
		pnlPay.add(pnlPayLine2);
		pnlPay.add(pnlPay5);
		pnlPay.add(pnlPay6);
		pnlPay.add(pnlPay7);
		pnlPay.add(pnlPay8);

		tabPaneShowPlaneSeat.add(pnlPlaneSeatGo, "가는편");
		tabPaneShowPlaneSeat.add(pnlPlaneSeatBack, "오는편");
		pnlUsSeat.add(tabPaneShowPlaneSeat);
		pnlUsSeatPayAll2.add(pnlUsSeat);
		pnlUsSeatPayAll2.add(pnlPay);
		pnlUsSeatPayAll.add(pnlUsSeatPayAll2);

		pnlUsRv.add(pnlUsRvPage, "예약");
		pnlUsRv.add(pnlUsSeatPayAll, "결제");

		// 마이페이
		JPanel pnlUsmy = new JPanel();
		JScrollPane scrlUs = new JScrollPane(pnlUsmy);
		JPanel pnlmy1 = new JPanel();
		pnlmy1.setPreferredSize(new Dimension(470, 100));
		pnlmy1.setMaximumSize(new Dimension(470, 100));
		JPanel pnlmyinf1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblusinforname = new JLabel();
		lblsetcolor(lblusinforname);
		JLabel lblusinfor1 = new JLabel(" 회원님 반갑습니다. ");
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Main();
			}
		});
		btnLogout.setMargin(new Insets(0, 0, 0, 0));
		btnLogout.setBackground(new Color(220, 240, 235));
		btnLogout.setForeground(new Color(60, 137, 117));
		pnlmyinf1.add(lblusinforname);
		pnlmyinf1.add(lblusinfor1);
		pnlmyinf1.add(btnLogout);
		JPanel pnlmyinf2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblusinfor2 = new JLabel("회원님의 등급은 ");
		lblusgrade = new JLabel();
		lblsetcolor(lblusgrade);
		JLabel lblusinfor3 = new JLabel(" 입니다.");
		pnlmyinf2.add(lblusinfor2);
		pnlmyinf2.add(lblusgrade);
		pnlmyinf2.add(lblusinfor3);
		JPanel pnlmyinf3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblusinfor4 = new JLabel("나의 Stamp : " + "" + " / 다음등급까지 " + "개 남았습니다. (편도당 1개)");
		pnlmyinf3.add(lblusinfor4);
		pnlmy1.setLayout(new BoxLayout(pnlmy1, BoxLayout.Y_AXIS));
		pnlmy1.add(pnlmyinf1);
		pnlmy1.add(pnlmyinf2);
		pnlmy1.add(pnlmyinf3);

		pnlmy2 = new JPanel();
		pnlmystar1 = new JPanel(new GridLayout(0, 6));

		JPanel pnlmy3 = new JPanel();
		JLabel lblgrade1 = new JLabel("나의 마일리지 : ");
		lblgrade2 = new JLabel("______");
		JLabel lblgrade3 = new JLabel(" java");
		lblgrade3.setForeground(Color.darkGray);
		JLabel lblgrade4 = new JLabel("        * 적립금 : 실버 0.01% / 골드 0.05% / 다이아 0.1%");
		lblgrade4.setForeground(Color.gray);
		lblgrade4.setFont(new Font(lblgrade4.getFont().getName(), Font.ITALIC, 12));
		lblsetcolor(lblgrade2);
		pnlmy3.add(lblgrade1);
		pnlmy3.add(lblgrade2);
		pnlmy3.add(lblgrade3);
		pnlmy3.add(lblgrade4);

		JPanel pnlmy4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlmy4.setPreferredSize(new Dimension(600, 25));
		pnlmy4.setMaximumSize(new Dimension(600, 25));
		JLabel lblreser = new JLabel("예약 목록 보기");
		pnlmy4.add(lblreser);

		JPanel pnlmy5 = new JPanel();
		pnlmyreser = new JPanel();
		pnlmyreser.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 2)));
		pnlmyreser.setLayout(new BoxLayout(pnlmyreser, BoxLayout.Y_AXIS));
		pnlmy5.add(pnlmyreser);

		pnlUsmy.setLayout(new BoxLayout(pnlUsmy, BoxLayout.Y_AXIS));
		pnlUsmy.add(pnlmy1);
		pnlUsmy.add(pnlmy2);
		pnlUsmy.add(pnlmy3);
		pnlUsmy.add(pnlmy4);
		pnlUsmy.add(pnlmy5);

		tabPaneUs.add(pnlUsRv, "예약");
		tabPaneUs.add(scrlUs, "마이페이지");

//==============================================================================================================

		pnl.add(lblImage, "메인");
		pnl.add(tabPaneUs, "사용자");
		pnl.add(tabPaneAd, "관리자");

		add(pnl);

		showGuI();

//***************************************************************************************************************
//***************************************************************************************************************
//***************************************************************************************************************
	}

	protected void pnlGoBackRemoveallRepaint() {
		pnlGo.removeAll();
		pnlGo.revalidate();
		pnlGo.repaint();
		pnlBack.removeAll();
		pnlBack.revalidate();
		pnlBack.repaint();
	}

	private void makeSelectedPlaneSd() {
		// 수정했음
		// 버튼 계속 추가되는거 방지
		pnlArea1.removeAll();
		pnlArea2.removeAll();
		pnlArea3.removeAll();
		pnlArea6.removeAll();
		pnlArea7.removeAll();
		pnlArea8.removeAll();
		pnlGo.removeAll();
		pnlBack.removeAll();
		if (rdRound.isSelected()) { // 왕복일때
			int countYes = 0;
			int countYes2 = 0;
			for (Plane pp : planes) {
				for (int i = 0; i < pp.schedules.size(); i++) {
					// 출발하는거
					if (pp.schedules.get(i).getDeptDay().trim().equals(lblDepDay2.getText().trim())
							&& pp.schedules.get(i).getDeptPlace().trim().equals(comboDepPlace.getSelectedItem())
							&& pp.schedules.get(i).getArrvPlace().trim().equals(comboArrPlace.getSelectedItem())) {
						
						// 수정했음
						// 잔여좌석이 실제로 반영되도록 잔여좌석 구하는 코드 추가
						int seatCnt = 0;
						for (int j = 0; j < 3; j++) {
							if (pp.seatV[j] != null) {
								seatCnt += pp.seatV[j].size();
							} else if (pp.seatG[j] != null) {
								seatCnt += pp.seatG[j].size();
							} else if (pp.seatS[j] != null) {
								seatCnt += pp.seatS[j].size();
							}
						}
						for (int j = 0; j < pp.schedules.size(); j++) {
							if (pp.schedules.get(j).getSeatSelectedV == null)
								pp.schedules.get(j).getSeatSelectedV = new ArrayList[3];
							if (pp.schedules.get(j).getSeatSelectedG == null)
								pp.schedules.get(j).getSeatSelectedG = new ArrayList[3];
							if (pp.schedules.get(j).getSeatSelectedS == null)
								pp.schedules.get(j).getSeatSelectedS = new ArrayList[3];
						}
						for (int j = 0; j < 3; j++) {
							if (pp.schedules.get(i).getSeatSelectedV[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedV[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedV[j].get(k)) {
										seatCnt--;
									}
								}
							} else if (pp.schedules.get(i).getSeatSelectedG[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedG[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedG[j].get(k)) {
										seatCnt--;
									}
								}
							} else if (pp.schedules.get(i).getSeatSelectedS[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedS[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedS[j].get(k)) {
										seatCnt--;
									}
								}
							}
						}
						JPanel pnlPlaneSd = new JPanel();
						JLabel lblPlaneSd = new JLabel("비행기 편명   :   " + pp.planeName + "     /      출발시간   :   "
								+ pp.schedules.get(i).getDeptTime() + "     /      잔여좌석   :   " + seatCnt);
						lblPlaneSd.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117))));
						lblPlaneSd.setHorizontalAlignment(JLabel.CENTER);
						pnlPlaneSd.setPreferredSize(new Dimension(480, 50));
						pnlPlaneSd.setMaximumSize(new Dimension(480, 50));
						pnlPlaneSd.setMinimumSize(new Dimension(480, 50));
						lblPlaneSd.setPreferredSize(new Dimension(450, 35));
						lblPlaneSd.setMaximumSize(new Dimension(450, 35));
						lblPlaneSd.setMinimumSize(new Dimension(450, 35));
						pnlPlaneSd.add(lblPlaneSd);
						click = null;
						selectGo = false;
						lblPlaneSd.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								jcb = (JLabel) e.getSource(); // 눌른값
								if (click != null) {
									Color c = UIManager.getColor("Panel.background");
									click.setBackground(c);
									pnlGo.revalidate();
									pnlGo.repaint();
								}
								jcb.setOpaque(true);
								jcb.setBackground(new Color(60, 137, 117));
								click = jcb; // click는 전에 눌린값
								selectGo = true;
								// 결제창에 정보적어줌
								String[] Deptime = lblPlaneSd.getText().trim().split("/");
								String[] Deptime2 = Deptime[1].trim().split("  ");
								lblselectPlaneInfor.setText(pp.planeName + " / " + comboDepPlace.getSelectedItem()
										+ " ▷ " + comboArrPlace.getSelectedItem() + " / " + Deptime2[2]);
								lblselectPlaneInfor.setForeground(new Color(60, 137, 117));
								if (comboPeople.getSelectedIndex() == 1) { // 1명일때
									lblGoSeat.setText("    가는편-");
									lblBackSeat.setText("    오는편-");
									lblGoSeat2.setText("");
									lblBackSeat2.setText("");
								}

								String[] deptTime = lblPlaneSd.getText().trim().split("/");
								String[] deptTime2 = deptTime[1].trim().split("   ");
								String realDepTime = deptTime2[2];
								deptPlaneSched = null;
								for (Plane p1 : planes) {
									if (p1.planeName.equals(pp.planeName)) {
										if (p1.schedules != null) {
											for (int i = 0; i < p1.schedules.size(); i++) {
												if (p1.schedules.get(i).getDeptDay().trim()
														.equals(lblDepDay2.getText().trim())
														&& p1.schedules.get(i).getDeptTime().equals(realDepTime)) {
													deptPlaneSched = p1.schedules.get(i);
												}
											}
										}
									}
								}
								//--------------------------------
								
								// 선택한 좌석 값주기
								if (deptPlaneSched.getSeatSelectedV == null)
									deptPlaneSched.getSeatSelectedV = new ArrayList[3];
								if (deptPlaneSched.getSeatSelectedG == null)
									deptPlaneSched.getSeatSelectedG = new ArrayList[3];
								if (deptPlaneSched.getSeatSelectedS == null)
									deptPlaneSched.getSeatSelectedS = new ArrayList[3];
								for (int i = 0; i < 3; i++) {
									if (deptPlaneSched.seatV[i] != null) {
										if (deptPlaneSched.getSeatSelectedV[i] == null) {
											deptPlaneSched.getSeatSelectedV[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatV[i].size(); j++) {
												deptPlaneSched.getSeatSelectedV[i].add(false);
											}
										}
									} else if (deptPlaneSched.seatG[i] != null) {
										if (deptPlaneSched.getSeatSelectedG[i] == null) {
											deptPlaneSched.getSeatSelectedG[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatG[i].size(); j++) {
												deptPlaneSched.getSeatSelectedG[i].add(false);
											}
										}
									} else if (deptPlaneSched.seatS[i] != null) {
										if (deptPlaneSched.getSeatSelectedS[i] == null) {
											deptPlaneSched.getSeatSelectedS[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatS[i].size(); j++) {
												deptPlaneSched.getSeatSelectedS[i].add(false);
											}
										}
									}
								}
								
								//----------------------------------
								// pnlArea1에 좌석 그려주기
								String[] planeName = lblPlaneSd.getText().trim().split("   ");
								for (Plane pp : planes) {
									if (planeName[2].equals(pp.planeName)) {
										pnlAreaSeatVPaint(pp, pnlArea1, pnlArea2, pnlArea3);
										pnlAreaSeatGPaint(pp, pnlArea1, pnlArea2, pnlArea3);
										pnlAreaSeatSPaint(pp, pnlArea1, pnlArea2, pnlArea3);
									}
								}
								if (selectGo && selectBack) { // selectGo ==true
									btnGoNext.setEnabled(true);
								}
							}
						});
						pnlGo.add(pnlPlaneSd);
						countYes++;
					}
					// 돌아오는거
					if (pp.schedules.get(i).getDeptDay().trim().equals(lblBackDay2.getText().trim())
							&& pp.schedules.get(i).getDeptPlace().trim().equals(comboArrPlace.getSelectedItem())
							&& pp.schedules.get(i).getArrvPlace().trim().equals(comboDepPlace.getSelectedItem())) {
						// 수정했음
						// 잔여좌석이 실제로 반영되도록 잔여좌석 구하는 코드 추가
						int seatCnt = 0;
						for (int j = 0; j < 3; j++) {
							if (pp.seatV[j] != null) {
								seatCnt += pp.seatV[j].size();
							} else if (pp.seatG[j] != null) {
								seatCnt += pp.seatG[j].size();
							} else if (pp.seatS[j] != null) {
								seatCnt += pp.seatS[j].size();
							}
						}
						for (int j = 0; j < pp.schedules.size(); j++) {
							if (pp.schedules.get(j).getSeatSelectedV == null)
								pp.schedules.get(j).getSeatSelectedV = new ArrayList[3];
							if (pp.schedules.get(j).getSeatSelectedG == null)
								pp.schedules.get(j).getSeatSelectedG = new ArrayList[3];
							if (pp.schedules.get(j).getSeatSelectedS == null)
								pp.schedules.get(j).getSeatSelectedS = new ArrayList[3];
						}
						for (int j = 0; j < 3; j++) {
							if (pp.schedules.get(i).getSeatSelectedV[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedV[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedV[j].get(k)) {
										seatCnt--;
									}
								}
							} else if (pp.schedules.get(i).getSeatSelectedG[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedG[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedG[j].get(k)) {
										seatCnt--;
									}
								}
							} else if (pp.schedules.get(i).getSeatSelectedS[j] != null) {
								for (int k = 0; k < pp.schedules.get(i).getSeatSelectedS[j].size(); k++) {
									if (pp.schedules.get(i).getSeatSelectedS[j].get(k)) {
										seatCnt--;
									}
								}
							}
						}
						
						JPanel pnlPlaneSd = new JPanel();
						JLabel lblPlaneSd = new JLabel("비행기 편명   :   " + pp.planeName + "     /      출발시간   :   "
								+ pp.schedules.get(i).getDeptTime() + "     /      잔여좌석   :   " + seatCnt);
						lblPlaneSd.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117))));
						lblPlaneSd.setHorizontalAlignment(JLabel.CENTER);
						pnlPlaneSd.setPreferredSize(new Dimension(480, 50));
						pnlPlaneSd.setMaximumSize(new Dimension(480, 50));
						pnlPlaneSd.setMinimumSize(new Dimension(480, 50));
						lblPlaneSd.setPreferredSize(new Dimension(450, 35));
						lblPlaneSd.setMaximumSize(new Dimension(450, 35));
						lblPlaneSd.setMinimumSize(new Dimension(450, 35));
						pnlPlaneSd.add(lblPlaneSd);
						click2 = null;
						selectBack = false;
						lblPlaneSd.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								jcb2 = (JLabel) e.getSource(); // 눌른값
								if (click2 != null) {
									Color c = UIManager.getColor("Panel.background");
									click2.setBackground(c);
									pnlBack.revalidate();
									pnlBack.repaint();
								}
								jcb2.setOpaque(true);
								jcb2.setBackground(new Color(60, 137, 117));
								click2 = jcb2; // click는 전에 눌린값
								selectBack = true;
								// 결제창에 정보적어줌
								String[] Deptime = lblPlaneSd.getText().trim().split("/");
								String[] Deptime2 = Deptime[1].trim().split("  ");
								lblselectPlaneInfor2.setText(pp.planeName + " / " + comboArrPlace.getSelectedItem()
										+ " ▷ " + comboDepPlace.getSelectedItem() + " / " + Deptime2[2]);
								lblselectPlaneInfor2.setForeground(new Color(60, 137, 117));

								// 오는편 비행기 스케줄 찾기 //이거찾아라
								String[] deptTime = lblPlaneSd.getText().trim().split("/");
								String[] deptTime2 = deptTime[1].trim().split("   ");
								String realDepTime = deptTime2[2];
								for (Plane p1 : planes) {
									if (p1.planeName.equals(pp.planeName)) {
										if (p1.schedules != null) {
											for (int i = 0; i < p1.schedules.size(); i++) {
												if (p1.schedules.get(i).getDeptDay().trim()
														.equals(lblBackDay2.getText().trim())
														&& p1.schedules.get(i).getDeptTime().equals(realDepTime)) {
													arrvPlaneSched = p1.schedules.get(i);
												}
											}
										}
									}
								}
								// 수정했음
								// 버튼 계속 추가되는거 방지
								pnlArea6.removeAll();
								pnlArea7.removeAll();
								pnlArea8.removeAll();

								//-----------------------------

								// 수정했음
								// 빠져있어서 추가
								if (arrvPlaneSched.getSeatSelectedV == null)
									arrvPlaneSched.getSeatSelectedV = new ArrayList[3];
								if (arrvPlaneSched.getSeatSelectedG == null)
									arrvPlaneSched.getSeatSelectedG = new ArrayList[3];
								if (arrvPlaneSched.getSeatSelectedS == null)
									arrvPlaneSched.getSeatSelectedS = new ArrayList[3];
								for (int i = 0; i < 3; i++) {
									if (arrvPlaneSched.seatV[i] != null) {
										if (arrvPlaneSched.getSeatSelectedV[i] == null) {
											arrvPlaneSched.getSeatSelectedV[i] = new ArrayList<Boolean>();
											for (int j = 0; j < arrvPlaneSched.seatV[i].size(); j++) {
												arrvPlaneSched.getSeatSelectedV[i].add(false);
											}
										}
									} else if (arrvPlaneSched.seatG[i] != null) {
										if (arrvPlaneSched.getSeatSelectedG[i] == null) {
											arrvPlaneSched.getSeatSelectedG[i] = new ArrayList<Boolean>();
											for (int j = 0; j < arrvPlaneSched.seatG[i].size(); j++) {
												arrvPlaneSched.getSeatSelectedG[i].add(false);
											}
										}
									} else if (arrvPlaneSched.seatS[i] != null) {
										if (arrvPlaneSched.getSeatSelectedS[i] == null) {
											arrvPlaneSched.getSeatSelectedS[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatS[i].size(); j++) {
												arrvPlaneSched.getSeatSelectedS[i].add(false);
											}
										}
									}
								}
								
								//-----------------------------
								// pnlArea6에 좌석 그려주기
								String[] planeName = lblPlaneSd.getText().trim().split("   ");
								for (Plane pp : planes) {
									if (planeName[2].equals(pp.planeName)) {
										pnlAreaSeatVPaint(pp, pnlArea6, pnlArea7, pnlArea8);
										pnlAreaSeatGPaint(pp, pnlArea6, pnlArea7, pnlArea8);
										pnlAreaSeatSPaint(pp, pnlArea6, pnlArea7, pnlArea8);
									}
								}
								if (selectGo && selectBack) { // selectGo ==true
									btnGoNext.setEnabled(true);
								}
							}
						});
						pnlBack.add(pnlPlaneSd);
						countYes2++;
					}
				}
			}
			if (countYes == 0) {
				lblGoInfor.setPreferredSize(new Dimension(500, 250));
				lblGoInfor.setMaximumSize(new Dimension(500, 250));
				lblGoInfor.setMinimumSize(new Dimension(500, 250));
				lblGoInfor.setHorizontalAlignment(JLabel.CENTER);
				pnlGo.add(lblGoInfor);
			}
			if (countYes2 == 0) {
				System.out.println(countYes2);
				lblBackInfor.setPreferredSize(new Dimension(500, 250));
				lblBackInfor.setMaximumSize(new Dimension(500, 250));
				lblBackInfor.setMinimumSize(new Dimension(500, 250));
				lblBackInfor.setHorizontalAlignment(JLabel.CENTER);
				pnlBack.add(lblBackInfor);
			}
		} else if (rdOneway.isSelected()) { // 편도일때
			int countYes = 0;
			for (Plane pp : planes) {
				for (int i = 0; i < pp.schedules.size(); i++) {
					// 출발하는거
					if (pp.schedules.get(i).getDeptDay().trim().equals(lblDepDay2.getText().trim())
							&& pp.schedules.get(i).getDeptPlace().trim().equals(comboDepPlace.getSelectedItem())
							&& pp.schedules.get(i).getArrvPlace().trim().equals(comboArrPlace.getSelectedItem())) {
						JPanel pnlPlaneSd = new JPanel();
						JLabel lblPlaneSd = new JLabel("비행기 편명   :   " + pp.planeName + "     /      출발시간   :   "
								+ pp.schedules.get(i).getDeptTime() + "     /      잔여좌석   :   " + 12);
						lblPlaneSd.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117))));
						lblPlaneSd.setHorizontalAlignment(JLabel.CENTER);
						pnlPlaneSd.setPreferredSize(new Dimension(480, 50));
						pnlPlaneSd.setMaximumSize(new Dimension(480, 50));
						pnlPlaneSd.setMinimumSize(new Dimension(480, 50));
						lblPlaneSd.setPreferredSize(new Dimension(450, 35));
						lblPlaneSd.setMaximumSize(new Dimension(450, 35));
						lblPlaneSd.setMinimumSize(new Dimension(450, 35));
						pnlPlaneSd.add(lblPlaneSd);
						click = null;
						selectGo = false;
						lblPlaneSd.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								jcb = (JLabel) e.getSource(); // 눌른값
								if (click != null) {
									Color c = UIManager.getColor("Panel.background");
									click.setBackground(c);
									pnlGo.revalidate();
									pnlGo.repaint();
								}
								jcb.setOpaque(true);
								jcb.setBackground(new Color(60, 137, 117));
								click = jcb; // click는 전에 눌린값
								selectGo = true;
								// 결제창에 정보적어줌
								String[] Deptime = lblPlaneSd.getText().trim().split("/");
								String[] Deptime2 = Deptime[1].trim().split("   ");
								lblselectPlaneInfor.setText(pp.planeName + " / " + comboDepPlace.getSelectedItem()
										+ " ▷ " + comboArrPlace.getSelectedItem() + " / " + Deptime2[2]);
								lblselectPlaneInfor.setForeground(new Color(60, 137, 117));
								lblselectPlaneInfor2.setText("돌아오는 여정은 없습니다");
								lblselectPlaneInfor2.setForeground(new Color(165, 165, 165));
								lblBackSeat.setText("    돌아오는 여정은 없습니다");
								lblBackSeat.setForeground(new Color(165, 165, 165));
								lblBackSeat2.setText("");
								if (comboPeople.getSelectedIndex() == 1) { // 1명일때
									lblGoSeat.setText("    가는편-");
									lblGoSeat2.setText("");
									lblBackSeat2.setText("");
								}

								
								String[] deptTime = lblPlaneSd.getText().trim().split("/");
								String[] deptTime2 = deptTime[1].trim().split("   ");
								String realDepTime = deptTime2[2];
								deptPlaneSched = null;
								for (Plane p1 : planes) {
									if (p1.planeName.equals(pp.planeName)) {
										if (p1.schedules != null) {
											for (int i = 0; i < p1.schedules.size(); i++) {
												if (p1.schedules.get(i).getDeptDay().trim()
														.equals(lblDepDay2.getText().trim())
														&& p1.schedules.get(i).getDeptTime().equals(realDepTime)) {
													deptPlaneSched = p1.schedules.get(i);
												}
											}
										}
									}
								}
								//---------------------------------
								// 선택한 좌석 값주기
								if (deptPlaneSched.getSeatSelectedV == null)
									deptPlaneSched.getSeatSelectedV = new ArrayList[3];
								if (deptPlaneSched.getSeatSelectedG == null)
									deptPlaneSched.getSeatSelectedG = new ArrayList[3];
								if (deptPlaneSched.getSeatSelectedS == null)
									deptPlaneSched.getSeatSelectedS = new ArrayList[3];
								for (int i = 0; i < 3; i++) {
									if (deptPlaneSched.seatV[i] != null) {
										if (deptPlaneSched.getSeatSelectedV[i] == null) {
											deptPlaneSched.getSeatSelectedV[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatV[i].size(); j++) {
												deptPlaneSched.getSeatSelectedV[i].add(false);
											}
										}
									} else if (deptPlaneSched.seatG[i] != null) {
										if (deptPlaneSched.getSeatSelectedG[i] == null) {
											deptPlaneSched.getSeatSelectedG[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatG[i].size(); j++) {
												deptPlaneSched.getSeatSelectedG[i].add(false);
											}
										}
									} else if (deptPlaneSched.seatS[i] != null) {
										if (deptPlaneSched.getSeatSelectedS[i] == null) {
											deptPlaneSched.getSeatSelectedS[i] = new ArrayList<Boolean>();
											for (int j = 0; j < deptPlaneSched.seatS[i].size(); j++) {
												deptPlaneSched.getSeatSelectedS[i].add(false);
											}
										}
									}
								}
								
								//---------------------------------
								// pnlArea1에 좌석 그려주기
								String[] planeName = lblPlaneSd.getText().trim().split("   ");
								for (Plane pp : planes) {
									if (planeName[2].equals(pp.planeName)) {
										pnlAreaSeatVPaint(pp, pnlArea1, pnlArea2, pnlArea3);
										pnlAreaSeatGPaint(pp, pnlArea1, pnlArea2, pnlArea3);
										pnlAreaSeatSPaint(pp, pnlArea1, pnlArea2, pnlArea3);
										pnlPlaneSeatBack.removeAll(); // 오는편 판넬 지우기 (편도니까)
										JLabel lbltext = new JLabel("편도를 선택하여 선택할 좌석이 없습니다.");
										lbltext.setHorizontalAlignment(JLabel.CENTER);
										lbltext.setPreferredSize(new Dimension(500, 250));
										lbltext.setMaximumSize(new Dimension(500, 250));
										lbltext.setMinimumSize(new Dimension(500, 250));
										pnlPlaneSeatBack.add(lbltext);
									}
								}
								if (selectGo == true) {
									btnGoNext.setEnabled(true);
								}
							}
						});
						pnlGo.add(pnlPlaneSd);
						countYes++;
					}
				}
			}
			if (countYes == 0) {
				lblGoInfor.setPreferredSize(new Dimension(500, 250));
				lblGoInfor.setMaximumSize(new Dimension(500, 250));
				lblGoInfor.setMinimumSize(new Dimension(500, 250));
				lblGoInfor.setHorizontalAlignment(JLabel.CENTER);
				pnlGo.add(lblGoInfor);
			}
			JPanel pnlPlaneSd = new JPanel();
			JLabel lblPlaneSd = new JLabel("편도를 선택하여 돌아오는 비행기는 조회되지 않았습니다.");
			lblPlaneSd.setHorizontalAlignment(JLabel.CENTER);
			lblPlaneSd.setPreferredSize(new Dimension(500, 250));
			lblPlaneSd.setMaximumSize(new Dimension(500, 250));
			lblPlaneSd.setMinimumSize(new Dimension(500, 250));
			pnlPlaneSd.add(lblPlaneSd);
			pnlBack.add(pnlPlaneSd);
		}
		pnlGo.revalidate();
		pnlGo.repaint();
		pnlBack.revalidate();
		pnlBack.repaint();

	}

	protected void SeatBtnActiom1(ActionEvent e, JPanel Area, int a, int b, int c, int money) {
		jcbSeatBtn = (JButton) e.getSource();
		String[] SelectSeatName = lblGoSeat1.getText().split(" ");
		String[] SelectSeatName2 = lblGoSeat3.getText().split(" ");
		if (comboPeople.getSelectedIndex() == 1) {
			if (lblGoSeat1.getText().trim().equals("")) { // 처음 눌렸을때
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea1)
					lblGoSeat1.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea2)
					lblGoSeat1.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea3)
					lblGoSeat1.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else if (!lblGoSeat1.getText().trim().equals("")) { // 2번째 누를때
				if (jcbSeatBtn.getText().trim().equals(SelectSeatName[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea1)
						lblGoSeat1.setText("");
					if (Area == pnlArea2)
						lblGoSeat1.setText("");
					if (Area == pnlArea3)
						lblGoSeat1.setText("");
				} else {
				}
			}
		}
		if (comboPeople.getSelectedIndex() == 2) { // 2명
			if (lblGoSeat1.getText().trim().equals("")) { // 1번째 탑승자
				if (!lblGoSeat3.getText().equals("")
						&& ("구역 " + jcbSeatBtn.getText()).equals(lblGoSeat3.getText().substring(1))) { // 1탑승자 2탑승자 같은좌석
																										// 선택시
					System.out.println("같은좌석노노");
					return;
				}
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea1)
					lblGoSeat1.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea2)
					lblGoSeat1.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea3)
					lblGoSeat1.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else if (lblGoSeat3.getText().trim().equals("")) { // 1번째 탑승자
				if (!lblGoSeat1.getText().equals("")
						&& ("구역 " + jcbSeatBtn.getText()).equals(lblGoSeat1.getText().substring(1))) { // 1탑승자 2탑승자 같은좌석
																										// 선택시
					System.out.println("같은좌석노노");
					return;
				}
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea1)
					lblGoSeat3.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea2)
					lblGoSeat3.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea3)
					lblGoSeat3.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else {
				if (jcbSeatBtn.getText().trim().equals(SelectSeatName2[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea1)
						lblGoSeat3.setText("");
					if (Area == pnlArea2)
						lblGoSeat3.setText("");
					if (Area == pnlArea3)
						lblGoSeat3.setText("");
				} else if (jcbSeatBtn.getText().trim().equals(SelectSeatName[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea1)
						lblGoSeat1.setText("");
					if (Area == pnlArea2)
						lblGoSeat1.setText("");
					if (Area == pnlArea3)
						lblGoSeat1.setText("");
				} else {
				}
			}
		}
	}

	protected void SeatBtnActiom2(ActionEvent e, JPanel Area, int a, int b, int c, int money) {
		jcbSeatBtn = (JButton) e.getSource();
		String[] SelectSeatName = lblBackSeat1.getText().split(" ");
		String[] SelectSeatName2 = lblBackSeat3.getText().split(" ");
		if (comboPeople.getSelectedIndex() == 1) {
			if (lblBackSeat1.getText().trim().equals("")) { // 처음 눌렸을때
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea6)
					lblBackSeat1.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea7)
					lblBackSeat1.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea8)
					lblBackSeat1.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else if (!lblBackSeat1.getText().trim().equals("")) { // 2번째 누를때
				if (jcbSeatBtn.getText().trim().equals(SelectSeatName[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea6)
						lblBackSeat1.setText("");
					if (Area == pnlArea7)
						lblBackSeat1.setText("");
					if (Area == pnlArea7)
						lblBackSeat1.setText("");
				} else {
				}
			}
		}
		if (comboPeople.getSelectedIndex() == 2) { // 2명
			if (lblBackSeat1.getText().trim().equals("")) { // 1번째 탑승자
				if (!lblBackSeat3.getText().equals("")
						&& ("구역 " + jcbSeatBtn.getText()).equals(lblBackSeat3.getText().substring(1))) { // 1탑승자 2탑승자
																											// 같은좌석 선택시
					System.out.println("같은좌석노노");
					return;
				}
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea6)
					lblBackSeat1.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea7)
					lblBackSeat1.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea8)
					lblBackSeat1.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else if (lblBackSeat3.getText().trim().equals("")) { // 1번째 탑승자
				if (!lblBackSeat1.getText().equals("")
						&& ("구역 " + jcbSeatBtn.getText()).equals(lblBackSeat1.getText().substring(1))) { // 1탑승자 2탑승자
																											// 같은좌석 선택시
					System.out.println("같은좌석노노");
					return;
				}
				jcbSeatBtn.setOpaque(true);
				jcbSeatBtn.setBackground(new Color(200, 200, 200));
				allPrice += money;
				if (Area == pnlArea6)
					lblBackSeat3.setText("1구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea7)
					lblBackSeat3.setText("2구역 " + jcbSeatBtn.getText());
				if (Area == pnlArea8)
					lblBackSeat3.setText("3구역 " + jcbSeatBtn.getText());
				lblMoney2.setText(allPrice + "원");
				lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
				lblMoney2.setForeground(new Color(60, 137, 117));
			} else {
				if (jcbSeatBtn.getText().trim().equals(SelectSeatName2[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea6)
						lblBackSeat3.setText("");
					if (Area == pnlArea7)
						lblBackSeat3.setText("");
					if (Area == pnlArea8)
						lblBackSeat3.setText("");
				} else if (jcbSeatBtn.getText().trim().equals(SelectSeatName[1])) {
					jcbSeatBtn.setBackground(new Color(a, b, c));
					allPrice -= money;
					lblMoney2.setText(allPrice + "원");
					lblMoney2.setFont(new Font(lblMoney2.getFont().getName(), Font.BOLD, 18));
					lblMoney2.setForeground(new Color(60, 137, 117));
					if (Area == pnlArea6)
						lblBackSeat1.setText("");
					if (Area == pnlArea7)
						lblBackSeat1.setText("");
					if (Area == pnlArea8)
						lblBackSeat1.setText("");
				} else {
				}
			}
		}
	}

	protected void pnlAreaSeatSPaint(Plane pp, JPanel Area1, JPanel Area2, JPanel Area3) {
		// 이거찾아라
		if (pp.seatS[0] != null) {
			for (int j = 0; j < pp.seatS[0].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("S" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area1 == pnlArea1)
								SeatBtnActiom1(e, Area1, 180, 250, 255, 20000);
							if (Area1 == pnlArea6)
								SeatBtnActiom2(e, Area1, 180, 250, 255, 20000);
						}
					});
					PlaneAdd.setSeatS(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedS != null && deptPlaneSched.getSeatSelectedS[0].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area1.add(pnlbtn);
			}
			Area1.revalidate();
			Area1.repaint();
		}
		if (pp.seatS[1] != null) {
			for (int j = 0; j < pp.seatS[1].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("S" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area2 == pnlArea2)
								SeatBtnActiom1(e, Area2, 180, 250, 255, 20000);
							if (Area2 == pnlArea7)
								SeatBtnActiom2(e, Area2, 180, 250, 255, 20000);
						}
					});
					PlaneAdd.setSeatS(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedS != null && deptPlaneSched.getSeatSelectedS[1].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area2.add(pnlbtn);
			}
			Area2.revalidate();
			Area2.repaint();
		}
		if (pp.seatS[2] != null) {
			for (int j = 0; j < pp.seatS[2].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("S" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area3 == pnlArea3)
								SeatBtnActiom1(e, Area3, 180, 250, 255, 20000);
							if (Area3 == pnlArea8)
								SeatBtnActiom2(e, Area3, 180, 250, 255, 20000);
						}
					});
					PlaneAdd.setSeatS(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedS != null && deptPlaneSched.getSeatSelectedS[2].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area3.add(pnlbtn);
			}
			Area3.revalidate();
			Area3.repaint();
		}
	}

	protected void pnlAreaSeatGPaint(Plane pp, JPanel Area1, JPanel Area2, JPanel Area3) {
		if (pp.seatG[0] != null) {
			for (int j = 0; j < pp.seatG[0].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("G" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area1 == pnlArea1)
								SeatBtnActiom1(e, Area1, 255, 205, 170, 30000);
							if (Area1 == pnlArea6)
								SeatBtnActiom2(e, Area1, 255, 205, 170, 30000);
						}
					});
					PlaneAdd.setSeatG(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedG != null && deptPlaneSched.getSeatSelectedG[0].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}

					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area1.add(pnlbtn);
			}
			Area1.revalidate();
			Area1.repaint();
		}
		if (pp.seatG[1] != null) {
			for (int j = 0; j < pp.seatG[1].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("G" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area2 == pnlArea2)
								SeatBtnActiom1(e, Area2, 255, 205, 170, 30000);
							if (Area2 == pnlArea7)
								SeatBtnActiom2(e, Area2, 255, 205, 170, 30000);
						}
					});
					PlaneAdd.setSeatG(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedG != null && deptPlaneSched.getSeatSelectedG[1].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}

					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area2.add(pnlbtn);
			}
			Area2.revalidate();
			Area2.repaint();
		}
		if (pp.seatG[2] != null) {
			for (int j = 0; j < pp.seatG[2].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("G" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area3 == pnlArea3)
								SeatBtnActiom1(e, Area3, 255, 205, 170, 30000);
							if (Area3 == pnlArea8)
								SeatBtnActiom2(e, Area3, 255, 205, 170, 30000);
						}
					});
					PlaneAdd.setSeatG(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedG != null && deptPlaneSched.getSeatSelectedG[2].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area3.add(pnlbtn);
			}
			Area3.revalidate();
			Area3.repaint();
		}
	}

	protected void pnlAreaSeatVPaint(Plane pp, JPanel Area1, JPanel Area2, JPanel Area3) {
		if (pp.seatV[0] != null) {
			for (int j = 0; j < pp.seatV[0].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				pnlbtn.setPreferredSize(new Dimension(55, 80));
				pnlbtn.setMaximumSize(new Dimension(55, 80));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("V" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area1 == pnlArea1)
								SeatBtnActiom1(e, Area1, 246, 244, 177, 40000);
							if (Area1 == pnlArea6)
								SeatBtnActiom2(e, Area1, 246, 244, 177, 40000);
						}
					});
					PlaneAdd.setSeatV(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedV != null && deptPlaneSched.getSeatSelectedV[0].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area1.add(pnlbtn);
			}
			Area1.revalidate();
			Area1.repaint();
		}
		if (pp.seatV[1] != null) {
			for (int j = 0; j < pp.seatV[1].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				pnlbtn.setPreferredSize(new Dimension(55, 80));
				pnlbtn.setMaximumSize(new Dimension(55, 80));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("V" + (j * 2 + k + 1));

					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area2 == pnlArea2)
								SeatBtnActiom1(e, Area2, 246, 244, 177, 40000);
							if (Area2 == pnlArea7)
								SeatBtnActiom2(e, Area2, 246, 244, 177, 40000);
						}
					});
					PlaneAdd.setSeatV(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						System.out.println("8. " + deptPlaneSched);
						System.out.println(deptPlaneSched.getSeatSelectedV);
						if (deptPlaneSched.getSeatSelectedV != null && deptPlaneSched.getSeatSelectedV[1].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area2.add(pnlbtn);
			}
			Area2.revalidate();
			Area2.repaint();
		}
		if (pp.seatV[2] != null) {
			for (int j = 0; j < pp.seatV[2].size() / 2; j++) {
				JPanel pnlbtn = new JPanel();
				pnlbtn.setLayout(new BoxLayout(pnlbtn, BoxLayout.Y_AXIS));
				pnlbtn.setPreferredSize(new Dimension(55, 80));
				pnlbtn.setMaximumSize(new Dimension(55, 80));
				for (int k = 0; k < 2; k++) {
					JPanel pnlbtn1 = new JPanel();
					JButton btn = new JButton("V" + (j * 2 + k + 1));
					btn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (Area3 == pnlArea3)
								SeatBtnActiom1(e, Area3, 246, 244, 177, 40000);
							if (Area3 == pnlArea8)
								SeatBtnActiom2(e, Area3, 246, 244, 177, 40000);
						}
					});
					PlaneAdd.setSeatV(btn);
					btn.setEnabled(true);
					int index = -1;
					for (int i = 0; i < pp.schedules.size(); i++) {
						if (pp.schedules.get(i).equals(deptPlaneSched)) {
							index = j * 2 + k;
						}
					}
					if (index != -1) {
						if (deptPlaneSched.getSeatSelectedV != null && deptPlaneSched.getSeatSelectedV[2].get(index)) {
							btn.setText("X");
							btn.setEnabled(false);
						}
					}
					pnlbtn1.add(btn);
					pnlbtn.add(pnlbtn1);
				}
				Area3.add(pnlbtn);
			}
			Area3.revalidate();
			Area3.repaint();
		}

	}

	private void makepnlsdPlane(JPanel pnlsdPlane) {
		pnlsdPlane.removeAll();
		pnlbtn = new JPanel(new GridLayout(0, 7));
		pnlbtn.setBorder(BorderFactory.createLineBorder(new Color(165, 165, 165), 2));
		JPanel pnlshowsd = new JPanel(new BorderLayout()); // 비행기별 스케줄 보여주는 패널
		pnlshowsd.setLayout(new BoxLayout(pnlshowsd, BoxLayout.Y_AXIS));
		for (int i = 0; i < planes.size(); i++) {
			btnplane = new JButton(planes.get(i).planeName);
			pnlbtn.add(btnplane);
			btnplane.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton jcb = (JButton) e.getSource();
					String btnname = jcb.getActionCommand();
					pnlshowsd.removeAll();
					JPanel pnlshow1 = new JPanel();
					// 비행기 이름 & 스케줄추가 버튼
					makeSdNameBtn(pnlshowsd, btnname);
					// 스케줄과 삭제 수정 버튼
					makeSd(btnname, pnlshowsd);
				}
			});
		}
		pnlsdPlane.add(pnlbtn, "North");
		pnlsdPlane.add(pnlshowsd, "Center");
	}

	private void makeSdNameBtn(JPanel pnlshowsd, String btnname) { // 비행기 이름 & 스케줄추가 버튼 다지워져서 다시만듬
		JPanel pnlshow1 = new JPanel(); // 비행기 이름 & 스케줄추가 버튼
		pnlshow1.setPreferredSize(new Dimension(250, 50));
		pnlshow1.setMinimumSize(new Dimension(250, 50));
		pnlshow1.setMaximumSize(new Dimension(250, 50));
		JLabel lblshowPlane = new JLabel(btnname);
		lblshowPlane.setFont(new Font(lblshowPlane.getFont().getName(), Font.BOLD, 20));
		lblshowPlane.setForeground(new Color(60, 137, 117));
		JButton btnAddSd = new JButton("스케줄 추가");
		btnAddSd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlrv.removeAll();
				new PlaneSdAdd1(btnname);
				for (Plane pp : planes) {
					if (pp.planeName.equals(btnname)) {
						pnlshowsd.removeAll();
						makeSdNameBtn(pnlshowsd, btnname);
						makeSd(btnname, pnlshowsd);
					}
				}
				// 예약보기 다시그리기
				new Calendar2(pnlReCal, pnlRe);
				pnlrv.add(pnlReCal, "North");
				pnlrv.add(pnlRe, "Center");

				pnlshowsd.revalidate();
				pnlshowsd.repaint();
			}
		});
		pnlshow1.add(lblshowPlane);
		pnlshow1.add(btnAddSd);
		pnlshowsd.add(pnlshow1);
		pnlshowsd.revalidate();
		pnlshowsd.repaint();
	}

	protected void makeSd(String btnname, JPanel pnlshowsd) {
		for (Plane pp : planes) {
			if (pp.planeName.equals(btnname)) {
				if (pp.schedules == null || pp.schedules.size() == 0) {
					JPanel pnlshow2 = new JPanel();
					JLabel lblshowSd0 = new JLabel(btnname + " 비행기의 예정된 스케줄 정보가 없습니다");
					lblshowSd0.setPreferredSize(new Dimension(630, 50));
					lblshowSd0.setMaximumSize(new Dimension(630, 50));
					lblshowSd0.setHorizontalAlignment(JLabel.CENTER);
					pnlshow2.add(lblshowSd0);
					pnlshowsd.add(pnlshow2);
					break;
				}
				for (int j = 0; j < pp.schedules.size(); j++) {
					LocalDate now = LocalDate.now();
					String today = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth())
							.format(DateTimeFormatter.BASIC_ISO_DATE); // 20201212
					int today2 = Integer.parseInt(today);
					int year = today2 / 10000;
					int month = (today2 % 10000) / 100;
					int day = today2 % 100;
					// pp.schedules.get(j).getDeptDay() 의
					// dataDate[0].trim() : 년 // dataDate[1].trim() : 월 //// dataDate[2].trim() : 일
					String[] dataDate = pp.schedules.get(j).getDeptDay().trim().split("/"); // year[0] : 오늘날짜의 년도
					// 오늘 날짜 이후꺼만 보여줌
					if (Integer.parseInt(dataDate[0].trim()) >= year && Integer.parseInt(dataDate[1].trim()) >= month
							&& Integer.parseInt(dataDate[2].trim()) >= day) {

						JPanel pnlshow2 = new JPanel();
						pnlshow2.setPreferredSize(new Dimension(780, 55));
						pnlshow2.setMaximumSize(new Dimension(780, 55));
						lblshowSd = new JLabel("  비행날짜 : " + pp.schedules.get(j).getDeptDay() + "    ,    출도착지 : "
								+ pp.schedules.get(j).getDeptPlace() + "  ▷  " + pp.schedules.get(j).getArrvPlace()
								+ "    ,    출발시간 : " + pp.schedules.get(j).getDeptTime() + "    ,    예약현황 : ");
						lblshowSd.setPreferredSize(new Dimension(630, 40));
						lblshowSd.setBorder(
								BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
						JButton btnshowMd = new JButton("수정");
						btnshowMd.setPreferredSize(new Dimension(60, 40));
						btnshowMd.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

							}
						});
						JButton btnshowDl = new JButton("삭제");
						btnshowDl.setPreferredSize(new Dimension(60, 40));
						pnlshow2.add(lblshowSd);
						pnlshow2.add(btnshowMd);
						pnlshow2.add(btnshowDl);
						pnlshowsd.add(pnlshow2);
					}
				}
			}
		}
	}

	protected void userMypage() { // 로그인시 마이페이지 리셋
		pnlmyreser.removeAll();
		for (Customer user : customers) {
			if (Login.tfLogid.getText().equals(user.getUid())) {
				lblusinforname.setText(user.getName());
				lblusinfor4.setText("나의 Stamp : " + user.getStar() + "  /  다음등급까지 " + (12 - (user.getStar() % 12))
						+ "개 남았습니다. (편도당 1개)");
				lblgrade2.setText(String.valueOf(user.getPoint()));
				// 스탬프 찍기
				if (user.getStar() / 12 == 0) {
					addsteap(user);
				} else if (user.getStar() / 12 == 1) {
					user.setGrade(1);
					addsteap(user);
				} else if (user.getStar() / 12 >= 2) {
					user.setGrade(2);
					addsteap(user);
				}
				CustomerIO.save(customers);
				// 등급
				if (user.getGrade() == 0) {
					lblusgrade.setText("Silver");
				} else if (user.getGrade() == 1) {
					lblusgrade.setText("Gold");
				} else if (user.getGrade() == 2) {
					lblusgrade.setText("Diamond");
				}
				// 예약한 스케줄 보여줌
				if (user.getSchedules().size() == 0 || user.getSchedules() == null) {
					JPanel pnl = new JPanel();
					JLabel lblmyreser = new JLabel("예약한 비행정보가 없습니다.");
					lblmyreser.setAlignmentX(0.5F);
					lblmyreser.setHorizontalAlignment(JLabel.CENTER);
					lblmyreser.setPreferredSize(new Dimension(600, 50));
					lblmyreser.setMaximumSize(new Dimension(600, 50));
					lblmyreser.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
					pnl.add(lblmyreser);
					pnlmyreser.add(pnl);
					break;
				}
				for (int i = 0; i < user.getSchedules().size(); i++) {
					if (user.getSchedules().get(i).getSelectedSeat().size() == 1) {
						JPanel pnl = new JPanel();
						JLabel lblmyreser = new JLabel("비행기 편명 : " + user.getSchedules().get(i).getPlaneNo()
								+ " / 출발지 : " + user.getSchedules().get(i).getDeptPlace() + " / 도착지 : "
								+ user.getSchedules().get(i).getArrvPlace() + " / 출발시간 : "
								+ user.getSchedules().get(i).getDeptTime() + " / 선택한좌석 : "
								+ user.getSchedules().get(i).getSelectedSeat().get(0));
						lblmyreser.setPreferredSize(new Dimension(600, 50));
						lblmyreser.setMaximumSize(new Dimension(600, 50));
						lblmyreser.setBorder(
								BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
						pnl.add(lblmyreser);
						pnlmyreser.add(pnl);
					} else if (user.getSchedules().get(i).getSelectedSeat().size() == 2) {
						JPanel pnl = new JPanel();
						JLabel lblmyreser = new JLabel("비행기 편명 : " + user.getSchedules().get(i).getPlaneNo()
								+ " / 출발지 : " + user.getSchedules().get(i).getDeptPlace() + " / 도착지 : "
								+ user.getSchedules().get(i).getArrvPlace() + " / 출발시간 : "
								+ user.getSchedules().get(i).getDeptTime() + " / 선택한좌석 : "
								+ user.getSchedules().get(i).getSelectedSeat().get(0) + " , "
								+ user.getSchedules().get(i).getSelectedSeat().get(1));
						lblmyreser.setPreferredSize(new Dimension(600, 50));
						lblmyreser.setMaximumSize(new Dimension(600, 50));
						lblmyreser.setBorder(
								BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));
						pnl.add(lblmyreser);
						pnlmyreser.add(pnl);
					}
				}
			}
		}
	}

	protected void addsteap(Customer user) {
		pnlmystar1.removeAll();
		pnlmy2.removeAll();
		for (int i = 0; i < (user.getStar() % 12); i++) {
			URL star = Main.class.getClassLoader().getResource("stamp1.png"); // 로고
			ImageIcon starImage = new ImageIcon(star);
			JLabel lblsar = new JLabel(starImage);
			pnlmystar1.add(lblsar);
			pnlmy2.add(pnlmystar1);
		}
		for (int i = 0; i < 12 - (user.getStar() % 12); i++) {
			URL star = Main.class.getClassLoader().getResource("stamp2.png"); // 로고
			ImageIcon starImage = new ImageIcon(star);
			JLabel lblsar = new JLabel(starImage);
			pnlmystar1.add(lblsar);
			pnlmy2.add(pnlmystar1);
		}
	}

	private void lblsetcolor(JLabel lbl) {
		lbl.setFont(new Font(lbl.getFont().getName(), Font.BOLD, 15));
		lbl.setForeground(new Color(60, 137, 117));
	}

	private void firstmakeplanelbl(JPanel pnlCenter) { // 처음킬때 비행기저장된거 판넬에 그리게 하기
		for (int j = 0; j < planes.size(); j++) {
			makeplanelbl(pnlCenter, j);
		}
	}

	private void makeplanelbl(JPanel pnlCenter, int j) {
		// 비행기 레이블
		JPanel imsi = new JPanel();
		JLabel lblplane = new JLabel();
		imsi.setPreferredSize(new Dimension(800, 35));
		imsi.setMaximumSize(new Dimension(800, 35));
		JPanel pnllblplane = new JPanel();
		lblplane.setPreferredSize(new Dimension(630, 25));
		lblplane.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 1)));

		int allseatcount = Character.getNumericValue(planes.get(j).planeName.charAt(2)) // Character.getNumericValue :
																						// char를 int
				+ Character.getNumericValue(planes.get(j).planeName.charAt(3))
				+ Character.getNumericValue(planes.get(j).planeName.charAt(4));
		lblplane.setText("비행기 이름    :    " + planes.get(j).planeName + "       /       총 좌석수    :    " + allseatcount
				+ "       /       잡아놓은 스케줄수    :    " + planes.get(j).schedules.size());
		lblplane.setHorizontalAlignment(JLabel.CENTER);
		JButton btnmf = new JButton("수정");
		btnmf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < planes.size(); i++) {
					if (planes.get(i).schedules.size() != 0) {
						if (((JPanel) pnlCenter.getComponent(i)).getComponent(1).equals((JButton) e.getSource())) {
							JOptionPane.showMessageDialog(imsi, "진행중인 스케줄이 존재하여 수정하지 못합니다.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				Iterator<Plane> iter = planes.iterator();
				while (iter.hasNext()) { // hasNext : 주머니에 손을 넣어서 물건이 있는지 없는지 알려줌(boolean)
					Plane p = iter.next(); // next() : 한개를 꺼내옴
					String pName = p.planeName;
					if (p.schedules.size() == 0) {
						if (planes.get(j).planeName.equals(pName)) {
							new PlaneAdd(p); // dialog에서 planes안에 이름과 좌석을 이미 바꿈
							makeplanelbl(pnlCenter, j); // 새로 그려주기만 하면됨
							pnlCenter.remove(imsi); // 예전에 그려놓은거 지우기
							// 스케줄관리에 버튼 다시 그려주기
							makepnlsdPlane(pnlsdPlane);
						}
					}
				}
				pnlCenter.revalidate();
				pnlCenter.repaint();
				PlaneIO.save(planes);
			}
		});
		JButton btnde = new JButton("삭제");
		btnde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				for (int i = 0; i < planes.size(); i++) {
//					if (((JPanel) pnlCenter.getComponent(i)).getComponent(2).equals((JButton) e.getSource())) {
//						// (JPanel) pnlplane.getComponent(i):imsi패널을 처음부터 끝까지 봄
//						// .getComponent(2) : imsi에 있는 두번째 컴포넌트(삭제버튼)
//						// (JButton) e.getSource()) : 삭제버튼을 눌린거
//						planes.remove(i);
//					}
//				}
				for (int i = 0; i < planes.size(); i++) {
					if (planes.get(i).schedules.size() != 0) {
						if (((JPanel) pnlCenter.getComponent(i)).getComponent(2).equals((JButton) e.getSource())) {
							JOptionPane.showMessageDialog(imsi, "진행중인 스케줄이 존재하여 삭제하지 못합니다.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				Iterator<Plane> iter = planes.iterator();
				while (iter.hasNext()) { // hasNext : 주머니에 손을 넣어서 물건이 있는지 없는지 알려줌(boolean)
					Plane p = iter.next();
					String pName = p.planeName;
					String[] splitName = lblplane.getText().trim().split(":");
					String[] splitName2 = splitName[1].trim().split("/");
					if (p.schedules.size() == 0) {
						if (splitName2[0].trim().equals(pName)) {
							iter.remove();
							pnlCenter.remove(imsi);
							// 스케줄관리에 버튼 다시 그려주기
							makepnlsdPlane(pnlsdPlane);
						}
					}
				}
				pnlCenter.revalidate();
				pnlCenter.repaint();
				PlaneIO.save(planes);

			}
		});
		imsi.add(lblplane);
		imsi.add(btnmf);
		imsi.add(btnde);
		pnlCenter.add(imsi);
		pnlCenter.revalidate();
		pnlCenter.repaint();

	}

	private void showGuI() {
		setSize(830, 492);
//		pack();
		setTitle("Welcome to AIR JAVA :D");
		setLocation(400, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

}
