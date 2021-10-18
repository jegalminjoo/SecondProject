import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

public class Login extends JDialog {
	private JTextField tfname;
	private JFormattedTextField tfbirth;
	private JFormattedTextField tfcellNum;
	private JTextField tfuid;
	private JPasswordField tfupwd;
	private JPasswordField tfupwd2;
	private JLabel lbluid2;
	private JPanel pnlJoin;
	private JLabel lblupwderr;
	private JLabel lbluiderr;
	private JLabel lblbirtherr;
	private boolean chkbir;
	static JTextField tfLogid;
//	static String userID = tfLogid.getText();
	private JButton btnLog;
	private JButton btnJoin2;

	public Login(List <Customer> customers) { //mainㅇㅔ 있는거 들구옴
		// 회원가입은 카드레이아웃으로 바꾸기
		CardLayout card = new CardLayout();
		JPanel pnl = new JPanel(card);

		// ----------------------------------------------------------------------

		// ------ 로그인 ------
		JPanel pnlLog = new JPanel(new GridLayout(0, 1)); // 전체

		URL lbliconlogo = Main.class.getClassLoader().getResource("logo.png"); // 로고
		ImageIcon iconImage = new ImageIcon(lbliconlogo);
		JLabel lblImage = new JLabel(iconImage);

		JPanel pnlLog2 = new JPanel(new GridLayout(0, 1));
		pnlLog2.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(60, 137, 117), 2), "로그인 해주세요."));

		JPanel pnlLogid = new JPanel();
		JLabel lblLogid = new JLabel("아이디");
		lblLogid.setPreferredSize(new Dimension(65, 12));
		lblLogid.setHorizontalAlignment(JLabel.CENTER);
		tfLogid = new JTextField(15);
		tfLogid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char team = e.getKeyChar();
				if (team==KeyEvent.VK_ENTER) {
					btnLog.doClick();
				}
			}
		});
		pnlLogid.add(lblLogid);
		pnlLogid.add(tfLogid);

		JPanel pnlLogpw = new JPanel();
		JLabel lblLogpw = new JLabel("비밀번호");
		lblLogpw.setPreferredSize(new Dimension(65, 12));
		lblLogpw.setHorizontalAlignment(JLabel.CENTER);
		JPasswordField tfLogpw = new JPasswordField(15);
		tfLogpw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char team = e.getKeyChar();
				if (team==KeyEvent.VK_ENTER) {
					btnLog.doClick();
				}
			}
		});
		pnlLogpw.add(lblLogpw);
		pnlLogpw.add(tfLogpw);

		JPanel pnlButton = new JPanel();
		btnLog = new JButton("로그인");
		btnLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = tfLogid.getText();
				String userPassword = tfLogpw.getText();

				boolean loginchk = false;
				for (Customer user : customers) {
					if (userID.equals(user.getUid()) && userPassword.equals(user.getUpwd())) {
						loginchk = true;
						dispose();
						break;
					}
				}
				if (loginchk == false) {
					JOptionPane.showMessageDialog(pnlLog, "비밀번호와 아이디를 확인해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					if(userID.equals("admin")) { //관리자가 로그인될때
						Main.chk=1;
					} else {
						Main.chk=2;
					}
				}
				
			}
		});
		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(pnl, "회원가입");
				pack();
			}
		});
		pnlButton.add(btnLog);
		pnlButton.add(btnJoin);

		pnlLog2.add(pnlLogid);
		pnlLog2.add(pnlLogpw);
		pnlLog2.add(pnlButton);

		pnlLog.add(lblImage);
		pnlLog.add(pnlLog2);

		add(pnlLog);

		// ------------------------------------------------------------------------------

		// ------ 회원가입 ------
		pnlJoin = new JPanel();

		// 이름
		JPanel pnlname = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblname = new JLabel("이름");
		lblname.setPreferredSize(new Dimension(100, 12));
		lblname.setHorizontalAlignment(JLabel.CENTER);
		tfname = new JTextField(20);
		tfname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char team = e.getKeyChar();
				if(team == 32) { // 32:space 
					e.consume();
				}
			}
		});
		pnlname.add(lblname);
		pnlname.add(tfname);

		MaskFormatter formatterBirth = null;
		MaskFormatter formatterCellNum = null;
		try {
			// (" ") : "" 안 개수만큼 적을수있음
			formatterBirth = new MaskFormatter("######"); // 숫자
			formatterBirth.setPlaceholderCharacter('_'); // ###밑에 ' ' 안에 그림으로 그려줌(글(숫자)를 쓸수있는 공간을 표시해줌)
			formatterCellNum = new MaskFormatter("010-####-####");
			formatterCellNum.setPlaceholderCharacter('_'); // ###밑에 ' ' 안에 그림으로 그려줌(글(숫자)를 쓸수있는 공간을 표시해줌)
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 생년월일
		JPanel pnlbirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblbirth = new JLabel("생년월일");
		lblbirth.setPreferredSize(new Dimension(100, 12));
		lblbirth.setHorizontalAlignment(JLabel.CENTER);
		JPanel pnlbirtherr = new JPanel();
		lblbirtherr = new JLabel("              ex) 910101");
		lblbirtherr.setForeground(new Color(60, 137, 117));
		lblbirtherr.setFont(new Font(lblbirtherr.getFont().getName(), Font.PLAIN, 11));
		lblbirtherr.setPreferredSize(new Dimension(200, 11));
		pnlbirtherr.setBorder(BorderFactory.createEmptyBorder(-5 , -5, -5, -5));
		tfbirth = new JFormattedTextField(formatterBirth);
		tfbirth.setColumns(20);
		chkbir = false;
		tfbirth.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int temp = Integer.parseInt(tfbirth.getText());
				int year = temp / 10000; //6글자중 앞에 2글자
				int month = temp / 100 % 100; // temp / 100 : 6글자중 앞에 4글자 , % 100 : 4글자를 100으로 나눈 나머지값
				int day = temp % 100; // temp % 100 : 6글자를 100으로 나누면(0000.00)이니까 나머지값
				if(month>=13||month==0||day>=32||day==0) {
					lblbirtherr.setForeground(Color.red);
					lblbirtherr.setText("              생년월일 확인해주세요");
					tfbirth.setForeground(Color.red);
					chkbir = false;
				}
				else {
					tfbirth.setForeground(Color.black);
					lblbirtherr.setForeground(Color.black);
					lblbirtherr.setText("              ex) 910101");
					lblbirtherr.setForeground(new Color(60, 137, 117));
					chkbir = true;
				}
			}
		});
		pnlbirth.add(lblbirth);
		pnlbirth.add(tfbirth);
		pnlbirtherr.add(lblbirtherr);
		
		//전화번호
		JPanel pnlcellNum = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblcellNum = new JLabel("전화번호");
		lblcellNum.setPreferredSize(new Dimension(100, 12));
		lblcellNum.setHorizontalAlignment(JLabel.CENTER);
		tfcellNum = new JFormattedTextField(formatterCellNum);
		tfcellNum.setColumns(20);
		pnlcellNum.add(lblcellNum);
		pnlcellNum.add(tfcellNum);

		// 아이디
		JPanel pnluid = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbluid = new JLabel("아이디");
		lbluid.setPreferredSize(new Dimension(100, 11));
		lbluid.setHorizontalAlignment(JLabel.CENTER);
		JPanel pnluiderr = new JPanel();
		lbluiderr = new JLabel();
		lbluiderr.setForeground(Color.red);
		lbluiderr.setFont(new Font(lbluiderr.getFont().getName(), Font.PLAIN, 11));
		lbluiderr.setPreferredSize(new Dimension(200, 11));
		pnluiderr.setBorder(BorderFactory.createEmptyBorder(-5 , -5, -5, -5));
		tfuid = new JTextField(20);
		tfuid.setText("소문자, 숫자, 10자 이하");
		tfuid.setForeground(Color.gray);
		tfuid.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) { // 다른 tf로 넘어갈ㄸㅐ
				if (tfuid.getText() == null || tfuid.getText().trim().equals("")) {
					tfuid.setText("소문자, 숫자, 10자 이하");
					tfuid.setForeground(Color.gray);
					lbluid2.setText("중복확인을 눌러주세요");
				}
				lbluiderr.setText("");
			}
			@Override
			public void focusGained(FocusEvent e) { // tf를 클릭했을때
				if (tfuid.getText().trim().equals("소문자, 숫자, 10자 이하")) {
					tfuid.setText("");
					tfuid.setForeground(Color.black);
				}
			}
		});
		tfuid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char team = e.getKeyChar();
				if(team == KeyEvent.VK_SPACE) { //  KeyEvent.VK_SPACE = 32 :space 
					e.consume();
				}
				if (!(Character.isLowerCase(team) || Character.isDigit(team) || team == 127 || team == 8 || team ==32)) { // 127:delete, 8:backspace
					lbluiderr.setText("              숫자와 소문자만 입력가능");
					e.consume(); // 안써지게 하는것
				} else if(Character.isLowerCase(team) || Character.isDigit(team) || team == 127 || team == 8) {
					lbluiderr.setText("");
				}
				if (tfuid.getText().length() > 9) {
					lbluiderr.setText("              10자 이내로 가능합니다 :D");
					e.consume(); // 안써지게 하는것
				} 
			}
		});
		JPanel pnluid2 = new JPanel();
		JButton btnidchk = new JButton("중복확인");
		btnidchk.setBackground(new Color(109, 198, 175));
		btnidchk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = tfuid.getText();

				boolean idchk = true;
				for (Customer user : customers) {
					if (userID.equals(user.getUid())) {
						idchk = false;
						lbluid2.setText("이미 사용된 아이디입니다");
						break;
					}
					if(idchk==true) {
						lbluid2.setText("사용가능한 아이디입니다");
					}
					if(tfuid.getText().trim().equals("소문자, 숫자, 10자 이하")) {
						lbluid2.setText("중복확인을 눌러주세요");
					}
				}
			}
		});
		lbluid2 = new JLabel("중복확인을 눌러주세요");
		lbluid2.setForeground(new Color(60, 137, 117));
		JLabel lblemp = new JLabel("               ");
		lbluid2.setFont(new Font(lbluid2.getFont().getName(), Font.PLAIN, 11));
		btnidchk.setMargin(new Insets(0, 0, 0, 0)); // 버튼안에 기본으로 있는 마진을 0으로 만듬
		// btnidchk.setFont(btnidchk.getFont().deriveFont(11)); //버튼안에 폰트사이즈
		// new Font(폰트종류, 폰트설정(기울게,밑줄,바로 등), 폰트크기)
		btnidchk.setFont(new Font(btnidchk.getFont().getName(), Font.PLAIN, 11)); // btnidchk.getFont().getName() : 원래
		pnluid.add(lbluid);
		pnluid.add(tfuid);
		pnluiderr.add(lbluiderr);
		pnluid2.add(lblemp);
		pnluid2.add(btnidchk);
		pnluid2.add(lbluid2);
		
		// 비밀번호
		JPanel pnlupwd = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblupwd = new JLabel("비밀번호");
		lblupwd.setPreferredSize(new Dimension(100, 12));
		lblupwd.setHorizontalAlignment(JLabel.CENTER);
		JPanel pnlupwderr = new JPanel();
		lblupwderr = new JLabel();
		lblupwderr.setForeground(Color.red);
		lblupwderr.setFont(new Font(lbluiderr.getFont().getName(), Font.PLAIN, 11));
		lblupwderr.setPreferredSize(new Dimension(200, 11));
		pnlupwderr.setBorder(BorderFactory.createEmptyBorder(-5 , -5, -5, -5));
		pnlupwderr.add(lblupwderr);
		tfupwd = new JPasswordField(20);
		tfupwd.setText("특수문자 (! @ # ^) 중 1개이상, 10자 이하");
		tfupwd.setForeground(Color.gray);
		tfupwd.setEchoChar((char) 0); // 처음에는 한글로 써지고
		tfupwd.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (tfupwd.getText() == null || tfupwd.getText().trim().equals("")) {
					tfupwd.setText("특수문자 (! @ # ^) 중 1개이상, 10자 이하");
					tfupwd.setForeground(Color.gray);
					tfupwd.setEchoChar((char) 0);
				} 
				if (check()==false) {  //특수문자 없을때
					lblupwderr.setText("              특수문자(! @ # ^)를 사용하세요");
					tfupwd.setForeground(Color.red);
					if(check()==true) {
						lblupwderr.setText("");
					}
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (tfupwd.getText().trim().equals("특수문자 (! @ # ^) 중 1개이상, 10자 이하")) {
				tfupwd.setText("");
				}
				tfupwd.setForeground(Color.black);
				tfupwd.setEchoChar((char) UIManager.get("PasswordField.echoChar")); // 입력할때는 JPasswordField로 써지기
			}
		});
		tfupwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char team = e.getKeyChar();
				if(team == 32) { // 32:space 
					e.consume();
				}
				if (tfupwd.getText().length() > 9) {
					lblupwderr.setText("              10자 이내로 가능합니다");
					e.consume(); // 안써지게 하는것
				} else {
					lblupwderr.setText("");
				}
			}
		});
		pnlupwd.add(lblupwd);
		pnlupwd.add(tfupwd);

		// 비밀번호 확인
		JPanel pnlupwd2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblupwd2 = new JLabel("비밀번호 확인");
		lblupwd2.setPreferredSize(new Dimension(100, 12));
		lblupwd2.setHorizontalAlignment(JLabel.CENTER);
		tfupwd2 = new JPasswordField(20);
		tfupwd2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char team = e.getKeyChar();
				if(team == 32) { // 32:space 
					e.consume();
				}
				if (team==KeyEvent.VK_ENTER) {
					btnJoin2.doClick();
				}
			}
		});
		pnlupwd2.add(lblupwd2);
		pnlupwd2.add(tfupwd2);

		//회원가입 버튼
		JPanel pnlbtn = new JPanel();
		btnJoin2 = new JButton("회원가입");
		btnJoin2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chkemp()==true && chkUpwd()==true && chkbtn()==true && chkbir==true) {
					JOptionPane.showMessageDialog(pnlJoin, tfname.getText()+" 님 가입을 축하드립니다.\n1000 java 적립되었습니다 :D",
							"congratulations",JOptionPane.INFORMATION_MESSAGE);
					card.show(pnl, "로그인");
					setSize(280, 300);
					tfLogid.setText(tfuid.getText());
					tfLogid.selectAll();
					customers.add(new Customer(tfname.getText(),tfbirth.getText(),tfcellNum.getText(),tfuid.getText(), tfupwd.getText()));
					CustomerIO.save(customers);
					joinlblRM();
				}
			}
		});
		JButton btncc = new JButton("돌아가기");
		btncc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(pnl, "로그인");
				setSize(280, 300);
			}
		});
		JButton btnrs = new JButton("RESET");
		btnrs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinlblRM();
			}
		});
		pnlbtn.add(btnJoin2);
		pnlbtn.add(btnrs);
		pnlbtn.add(btncc);

		pnlJoin.setLayout(new BoxLayout(pnlJoin,BoxLayout.Y_AXIS));
		pnlJoin.add(pnlname);
		pnlJoin.add(pnlbirth);
		pnlJoin.add(pnlbirtherr);
		pnlJoin.add(pnlcellNum);
		pnlJoin.add(pnluid);
		pnlJoin.add(pnluiderr);
		pnlJoin.add(pnluid2);
		pnlJoin.add(pnlupwd);
		pnlJoin.add(pnlupwderr);
		pnlJoin.add(pnlupwd2);
		pnlJoin.add(pnlbtn);

		// ----------------------------------------------------------------

		pnl.add(pnlLog, "로그인");
		pnl.add(pnlJoin, "회원가입");

		add(pnl);

//		pack();
		setSize(280, 300);
		setTitle("LOGIN");
		setLocation(450, 400);
		setModal(true);
		setVisible(true);
//	pnlLog2.setPreferredSize(new Dimension(350, 300));
//	pnlLog2.setMaximumSize(new Dimension(350, 300));
//	pnlLog2.setMinimumSize(new Dimension(350, 300));
//	pnlLog2.setOpaque(true);
//	pnlLog2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.RED, 1), "사용자 선택 결과창"));

	}

	private boolean chkemp() {
		if (tfname.getText() == null || tfname.getText().trim().equals("") || tfbirth.getText() == null
				|| tfbirth.getText().trim().equals("") || tfcellNum.getText() == null
				|| tfcellNum.getText().trim().equals("") || tfuid.getText() == null || tfuid.getText().trim().equals("")
				|| tfupwd.getText() == null || tfupwd.getText().trim().equals("") || tfupwd2.getText() == null
				|| tfupwd2.getText().trim().equals("") || tfuid.getText().trim().equals("소문자, 숫자, 10자 이하")
				|| tfupwd.getText().trim().equals("특수문자 (! @ # ^) 중 1개이상, 10자 이하")) {
			JOptionPane.showMessageDialog(pnlJoin, "빈칸없이 적어주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true; // 빈곳없음
	}

	private boolean chkUpwd() {
		if (tfupwd.getText().trim().equals(tfupwd2.getText().trim()) && tfupwd.getForeground() == Color.black) {
			return true; // 비밀번호, 확인 일치
		}
		JOptionPane.showMessageDialog(pnlJoin, "비밀번호을 다시 확인해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private boolean chkbtn() {
		if (lbluid2.getText().equals("사용가능한 아이디입니다")) {
			return true; // 중복확인후 사용가능한 아이디일때
		}
		JOptionPane.showMessageDialog(pnlJoin, "아이디 중복확인을 해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void joinlblRM() { // 다시 회원가입눌렸을때 정보지우기
		tfname.setText("");
		tfbirth.setValue("");
		tfcellNum.setValue("");
		tfuid.setText("소문자, 숫자, 10자 이하");
		tfuid.setForeground(Color.gray);
		lbluiderr.setText("");
		tfupwd.setText("특수문자 (! @ # ^) 중 1개이상, 10자 이하");
		tfupwd.setForeground(Color.gray);
		lblupwderr.setText("");
		tfupwd.setEchoChar((char) 0);
		tfupwd2.setText("");
	}

	protected boolean check() {
		for (int i = 0; i < tfupwd.getText().length(); i++) {
			char temp = tfupwd.getText().charAt(i);
			if (temp == '!' || temp == '@' || temp == '#' || temp == '^') {
				return true;
			}
		}
		return false; // 특수문자 없을때
	}
}
