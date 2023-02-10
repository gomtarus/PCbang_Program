import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

public class Main extends DAO {
	DecimalFormat format = new DecimalFormat("#,###");
	Order_Form of = new Order_Form();
	String font_name = "배달의민족 주아";
	Random ren = new Random(); // 자리생성용
	Timer timer;
	Timer sava_time_DB;

	int place; // 자리
	String user; // 이름
	String charge_id; // 충전 할 아이디

	// ----------------------------------------------
	JFrame main_frame = new JFrame("욱PC방");

	// ---------------------------------------------- 메인
	JPanel login_main = new JPanel();
	JLabel login_title = new JLabel("욱PC방");

	// ---------------------------------------------- 로그인 메인
	int login_count = 0;

	JPanel login_tap_group = new JPanel();
	JPanel user_tap = new JPanel();
	JPanel non_user_tap = new JPanel();

	JLabel user_tap_lb = new JLabel("회원");
	JLabel non_user_tap_lb = new JLabel("비회원");

	// ------------------------------------------------------- 회원/비회원 탭
	String login_tb_arr[] = { "아이디", "비밀번호", "카드번호" };
	JTextField login_tb[] = new JTextField[3]; // 아이디
	JPasswordField pw_tb = new JPasswordField(); // 비밀번호

	// ---------------------------------------------- 아이디, 비밀번호, 카드번호 입력창
	JLabel login_noti = new JLabel();

	// ---------------------------------------------- 상태 알림
	JButton login_btn = new JButton("로그인");

	JPanel user_find_reg_group = new JPanel();
	JLabel user_find_reg_lb = new JLabel("ID/PW 찾기 | 회원가입");
	JButton user_reg_btn = new JButton("");
	JButton user_find_btn = new JButton("");

	// ---------------------------------------------- 로그인, ID/PW 찾기, 회원가입 버튼
	// ---------------------------------------------- 여기까지가 메인 로그인 화면 구성
	int find_count = 0;

	JPanel find_main = new JPanel();
	JPanel find_btn_group = new JPanel();

	String find_lb_arr[] = { "ID/PW찾기", "아이디 찾기", "이름     :", "휴대폰 :", "-", "비빌번호 찾기", "아이디 :", "휴대폰 :", "-" };
	String find_tb_arr[] = { "이름", "010", "휴대폰 번호", "아이디", "010", "휴대폰 번호" };
	String find_btn_arr[] = { "아이디 찾기", "비밀번호 찾기", "취소" };
	JButton find_btn[] = new JButton[find_btn_arr.length];
	JTextField find_tb[] = new JTextField[find_tb_arr.length];
	JLabel find_lb[] = new JLabel[find_lb_arr.length];

	JLabel find_noti = new JLabel();

	// ---------------------------------------------- 아이디, 비밀번호 찾기
	JPanel reg_main = new JPanel();
	JPanel reg_btn_group = new JPanel();

	String reg_lb_arr[] = { "회원가입", "이름(10자)", "아이디(4~10자)", "비밀번호(4~10자)", "비밀번호 확인", "휴대폰", "-" };
	String reg_tb_arr[] = { "이름", "아이디", "비밀번호", "비밀번호 확인", "010", "휴대폰 번호" };
	String reg_btn_arr[] = { "가입하기", "취소" };
	JLabel reg_lb[] = new JLabel[reg_lb_arr.length];
	JTextField reg_tb[] = new JTextField[reg_tb_arr.length];

	JPasswordField reg_pw = new JPasswordField(); // 비밀번호
	JPasswordField reg_pw_chk = new JPasswordField(); // 비밀번호 확인

	JButton reg_btn[] = new JButton[reg_btn_arr.length];

	JLabel reg_noti = new JLabel();

	// ---------------------------------------------- 회원가입
	int left_time; // 남은시간
	int use_time;// 사용시간
	int start_time; // 시작시간

	int order_form_cnt = 0;

	JFrame user_main = new JFrame(); // 메인프레임

	JPanel user_form_bg = new JPanel();
	JPanel user_play_info = new JPanel(); // 사용시간, 이름 등
	JPanel user_place_group = new JPanel(); // 자리
	JPanel user_btn_group = new JPanel(); // 버튼 그룹

	JLabel user_place = new JLabel(); // 좌석 번호
	JLabel user_name = new JLabel(); // 접속 유저 이름

	JLabel user_start_time = new JLabel(); // 시작시간
	JLabel user_left_time = new JLabel(); // 남은시간
	JLabel user_use_time = new JLabel(); // 사용시간

	JButton user_set_btn = new JButton(reSize_Img("Img\\Src\\set_icon.png", 20, 20));

	String user_btn_arr[] = { "주문하기", "카운터 연락", "사용종료" };
	JButton user_btn[] = new JButton[user_btn_arr.length];

	// ------------------------------------------------------- 사용 화면
	int charge_page_count = 0; // 버튼 카운트
	int charge_time = 0;
	int item_time = 0;
	int item_price = 0;

	JFrame Charge_main = new JFrame("욱PC방 충전");

	// ---------------------------------------------
	JFrame Qr_main = new JFrame();
	JLabel pay_logo = new JLabel();
	JLabel pay_qr = new JLabel();
	JPanel pay_main = new JPanel();

	JLabel pay_info = new JLabel("결제 금액");
	JLabel pay_price = new JLabel();

	JPanel pay_btn_group = new JPanel();
	String pay_arr[] = { "결제하기", "취소" };
	JButton pay_btn[] = new JButton[2];

	// ---------------------------------------------

	JLabel charge_title = new JLabel();
	JLabel charge_place = new JLabel();

	JPanel charge_item_main = new JPanel();
	JPanel charge_list_group = new JPanel();
	JPanel charge_btn_group = new JPanel();

	String charge_btn_arr[] = { "이전", "다음" };
	JButton charge_btn[] = new JButton[2];

	String charge_item_time_arr[] = { "1", "2", "3", "4", "5", "10", "20", "30", "40" };

	int itme_len = charge_item_time_arr.length;

	JPanel charge_item[] = new JPanel[itme_len];
	JLabel charge_item_name[] = new JLabel[itme_len];
	JLabel charge_item_time[] = new JLabel[itme_len];
	JLabel charge_item_price[] = new JLabel[itme_len];
	JButton charge_item_btn[] = new JButton[itme_len];

	// ------------------------------
	JPanel payment_item[] = new JPanel[9];
	JLabel payment_img[] = new JLabel[9];
	JButton payment_btn[] = new JButton[9];

	// ------------------------------------------------------- 충전 화면
	JFrame user_edit_main = new JFrame("개인정보 수정");
	JPasswordField user_edit_pw = new JPasswordField();
	JLabel user_set_noti = new JLabel(); // 상태

	JPanel user_set_group = new JPanel();

	JPanel user_set_btn_group = new JPanel();
	JButton user_set_ok_btn = new JButton("확인");
	JButton user_set_cen_btn = new JButton("취소");

	String user_set_arr[] = { "개인정보 수정", "이름(10자)", "아이디(변경불가)", "비밀번호(4~10자)", "비밀번호 확인", "휴대폰 번호", "-" };
	JLabel user_set_lb[] = new JLabel[user_set_arr.length];

	String user_set_tb_arr[] = { "이름", "아이디", "비밀번호", "비밀번호 확인", "010", "휴대폰 번호" };
	JTextField user_set_tb[] = new JTextField[user_set_tb_arr.length];

	JPasswordField user_set_pw = new JPasswordField(); // 비밀번호
	JPasswordField user_set_pw_chk = new JPasswordField(); // 비밀번호 확인

	// ----------------------------------------------- 정보 수정
	private String salt() throws NoSuchAlgorithmException { // salt
		String salt;
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		return salt = new String(Base64.getEncoder().encode(bytes));
	}

	private String SHA_256(String m_pw) throws NoSuchAlgorithmException { // sha256
		String hex;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(m_pw.getBytes());
		return hex = String.format("%064x", new BigInteger(1, md.digest()));
	}

	String sha(String m_pw) {
		String temp = "";
		try {
			temp = SHA_256(m_pw);
		} catch (NoSuchAlgorithmException e) {
		}
		return temp;
	}

	String salt_temp() {
		String temp = "";
		try {
			temp = salt();
		} catch (NoSuchAlgorithmException e) {
		}
		return temp;
	}

	// ------------------------------------------------------- 암호화\
	void Noti(String value, String title) {
		JOptionPane.showMessageDialog(null, value, title, JOptionPane.PLAIN_MESSAGE);
	}

	void set_Font(JLabel lb, int size, Color font_color, int horizontalAlignment) { // 폰트
		lb.setFont(new Font(font_name, Font.PLAIN, size));
		lb.setForeground(font_color);
		lb.setHorizontalAlignment(horizontalAlignment);
	}

	void set_Panel(JPanel pn, Color bg_color) { // 패널
		pn.setBackground(bg_color);
		pn.setLayout(null);
	}

	void set_TextField(JTextField tb, Color boradr_color, int size, Color font_color) { // 텍스트 입력
		Border border = BorderFactory.createLineBorder(boradr_color, 2);
		tb.setBorder(border);
		tb.setBorder(BorderFactory.createCompoundBorder(tb.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		tb.setFont(new Font(font_name, Font.PLAIN, size));
		tb.setForeground(font_color);
	}

	void set_Btn(JButton btn, Color bg_color, int size, Color font_color) { // 버튼
		btn.setBackground(bg_color);
		btn.setFont(new Font(font_name, Font.PLAIN, size));
		btn.setForeground(font_color);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
	}

	void set_Btn(JButton btn) { // 틀만 남기기
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
	}

	void set_Btn(JButton btn, int size, Color bg_color, Color font_color, Dimension dm) { // 버튼 설정
		btn.setBackground(bg_color);
		btn.setFont(new Font(font_name, Font.PLAIN, size));
		btn.setForeground(font_color);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setPreferredSize(dm);
	}

	void hide_btn(JButton btn) { // 투명버튼
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
	}

	ImageIcon reSize_Img(String src, int x, int y) {
		ImageIcon icon = new ImageIcon(src);
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		return changeIcon;
	}

	void set_Font(JLabel lb, int size, Color color) {
		lb.setFont(new Font(font_name, Font.PLAIN, size));
		lb.setForeground(color);
		lb.setHorizontalAlignment(JLabel.CENTER);
	}

	// ---------------------------------------------- UI설정
	void login_succ(int place) {
		for (int i = 0; i < login_tb_arr.length; i++) {
			set_TextField(login_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			login_tb[i].setText(login_tb_arr[i]);
			login_tb[i].setEnabled(false);
		}

		pw_tb.setVisible(false);

		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));

		pw_tb.setText("");
		login_title.requestFocus(); // 포커스 해제를 위해 임시로 지정
		login_noti.setText("");

		of.order_list_lb[0].setText(String.valueOf(place) + "번 자리");
		if (login_count == 1) {
			login_tb[1].setVisible(false);
			of.order_list_lb[1].setText(user + "님");
			of.user_name = user;
			of.place = String.valueOf(place);
		} else {
			login_tb[1].setVisible(true);
			of.order_list_lb[1].setText(user);
			of.user_name = user;
			of.place = String.valueOf(place);
		}
	}

	void tap_swap(JPanel act_jp, JLabel act_lb, JPanel dis_jp, JLabel dis_lb) {
		for (int i = 0; i < login_tb_arr.length; i++) {
			set_TextField(login_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			if (login_tb[i].getText().equals("")) {
				login_tb[i].setText(login_tb_arr[i]);
			}
			login_tb[i].setEnabled(false);
		}
		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		// 텍스트필드 초기화
		set_Panel(act_jp, new Color(0, 120, 215));
		set_Font(act_lb, 16, Color.white, JLabel.CENTER);
		set_Panel(dis_jp, new Color(240, 240, 240));
		set_Font(dis_lb, 16, new Color(0, 120, 215), JLabel.CENTER);

		login_title.requestFocus(); // 포커스 해제를 위해 임시로 지정
		login_noti.setText("");
	}

	// ---------------------------------------------- 회원, 비회원 탭
	// ---------------------------------------------- 메인 로그인 화면
	void login_tb_reset() {
		for (int i = 0; i < login_tb_arr.length; i++) {
			set_TextField(login_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			login_tb[i].setEnabled(false);
		}
	}

	void login_tb_sw(int n) {
		for (int i = 0; i < login_tb_arr.length; i++) {
			set_TextField(login_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			login_tb[i].setEnabled(false);
		}
		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		set_TextField(login_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));

		login_noti.setText("");
		login_tb[n].setEnabled(true);
		login_tb[n].requestFocus();

		if (n == 1) {
			set_TextField(pw_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
		}
		if (login_tb[n].getText().equals("아이디")) {
			login_tb[n].setText("");
		} else if (login_tb[n].getText().equals("비밀번호")) {
			login_tb[n].setText("");
		} else if (login_tb[n].getText().equals("카드번호")) {
			login_tb[n].setText("");
		}
	}

	// ---------------------------------------------- 아이디, 비밀번호, 카드번호 입력
	void find_tb_sw(int n) {
		for (int i = 0; i < find_tb_arr.length; i++) {
			set_TextField(find_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			find_tb[i].setEnabled(false);
		}
		if (find_tb[n].getText().equals("이름") || find_tb[n].getText().equals("휴대폰 번호")
				|| find_tb[n].getText().equals("아이디") || find_tb[n].getText().equals("휴대폰 번호")) {
			find_tb[n].setText("");
		}
		set_TextField(find_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
		find_tb[n].setEnabled(true);
		find_tb[n].requestFocus();
		if (n == 1 || n == 4) {
			set_TextField(find_tb[n], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			find_tb[n].setEnabled(false);
		}
	}

	// ----------------------------------------------------------- ID/PW 찾기
	void reg_tb_sw(int n) {
		for (int i = 0; i < reg_tb_arr.length; i++) {
			set_TextField(reg_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			reg_tb[i].setEnabled(false);
		}
		if (reg_tb[n].getText().equals("이름") || reg_tb[n].getText().equals("아이디") || reg_tb[n].getText().equals("비밀번호")
				|| reg_tb[n].getText().equals("비밀번호 확인") || reg_tb[n].getText().equals("휴대폰 번호")) {
			reg_tb[n].setText("");
		}
		set_TextField(reg_pw, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		set_TextField(reg_pw_chk, new Color(200, 200, 200), 15, new Color(200, 200, 200));

		set_TextField(reg_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
		reg_tb[n].setEnabled(true);
		reg_tb[n].requestFocus();

		if (n == 2) {
			reg_tb[2].setVisible(false);
			reg_pw.setVisible(true);
			reg_pw.requestFocus();
			set_TextField(reg_pw, new Color(0, 120, 215), 15, new Color(0, 0, 0));
		}
		if (n == 3) {
			reg_tb[3].setVisible(false);
			reg_pw_chk.setVisible(true);
			reg_pw_chk.requestFocus();
			set_TextField(reg_pw_chk, new Color(0, 120, 215), 15, new Color(0, 0, 0));
		}

		if (n == 4) {
			set_TextField(reg_tb[n], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			reg_tb[n].setEnabled(false);
			reg_tb[n].setText("010");
		}

	}

	// ----------------------------------------------------------- 회원가입

	void edit_tb_sw() {
		for (int i = 0; i < user_set_tb.length; i++) {
			set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
		}
		set_TextField(user_set_pw, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		set_TextField(user_set_pw_chk, new Color(200, 200, 200), 15, new Color(200, 200, 200));
	}

	// ----------------------------------------------------------- 개인 정보 수정
	public Main() {
		main_frame.setSize(1280, 720);
		main_frame.setDefaultCloseOperation(main_frame.EXIT_ON_CLOSE);
		main_frame.setLocationRelativeTo(null);
		main_frame.setResizable(false);
		main_frame.setVisible(true);
		main_frame.getContentPane().setBackground(new Color(240, 240, 240));
		main_frame.setLayout(null);

		login_frame();
	}

	public void user_edit_frame(String user_id) {
		String salt = "";
		String user_pw = "";
		// 암호를 위해서

		String user_name = "";
		String user_phone = "";
		// 아이디, 이름 가져오기

		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "Select * from member where m_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 정보가 존재할 때
				salt = rs.getString("m_salt");
				user_name = rs.getString("m_name");
				user_pw = rs.getString("m_pw");
				user_phone = rs.getString("m_cell");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ----------------------salt 가져오기

		user_edit_main.setSize(301, 460);
		user_edit_main.setLocationRelativeTo(null);
		user_edit_main.setResizable(false);
		user_edit_main.getContentPane().setBackground(new Color(240, 240, 240));
		user_edit_main.setLayout(null);

		user_set_group.setBounds(0, -10, 292, 500);
		user_set_group.setLayout(null);
		user_set_group.setBackground(Color.white);

		for (int i = 0; i < user_set_arr.length; i++) {
			user_set_lb[i] = new JLabel(user_set_arr[i]);
			set_Font(user_set_lb[i], 18, new Color(0, 0, 0));
			user_set_lb[i].setHorizontalAlignment(JLabel.LEFT);
			// ------------------------------------------------
			user_set_group.add(user_set_lb[i]);
		}

		for (int i = 0; i < user_set_tb_arr.length; i++) {
			user_set_tb[i] = new JTextField(user_set_tb_arr[i]);
			set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			user_set_tb[i].setDisabledTextColor(new Color(200, 200, 200));
			// ------------------------------------------------
			user_set_group.add(user_set_tb[i]);
		}

		user_set_tb[0].setText(user_name); // 이름
		user_set_tb[1].setText(user_id); // 아이디
		user_set_tb[5].setText(user_phone.substring(3, 11)); // 핸드폰 번호

		user_set_lb[0].setBounds(0, 10, 292, 50); // 타이틀
		set_Font(user_set_lb[0], 25, new Color(0, 0, 0));

		user_set_lb[1].setBounds(16, 40, 248, 50); // 이름
		user_set_tb[0].setBounds(16, 80, 257, 30);

		user_set_lb[2].setBounds(16, 100, 248, 50); // 아이디
		user_set_tb[1].setBounds(16, 140, 257, 30);
		user_set_tb[1].setEnabled(false);

		user_set_lb[3].setBounds(16, 160, 248, 50); // 비밀번호
		user_set_tb[2].setBounds(16, 200, 257, 30);

		user_set_pw.setBounds(16, 200, 257, 30);
		set_TextField(user_set_pw, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		user_set_pw.setEchoChar('*');
		user_set_pw.setVisible(false);

		user_set_lb[4].setBounds(16, 220, 249, 50); // 비밀번호 확인
		user_set_tb[3].setBounds(16, 260, 257, 30);

		user_set_pw_chk.setBounds(16, 260, 257, 30);
		set_TextField(user_set_pw_chk, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		user_set_pw_chk.setEchoChar('*');
		user_set_pw_chk.setVisible(false);

		user_set_lb[5].setBounds(16, 280, 248, 50); // 핸드폰
		user_set_tb[4].setBounds(16, 320, 70, 30); // 010
		user_set_tb[4].setText("010");
		user_set_tb[4].setHorizontalAlignment(JLabel.CENTER);
		user_set_tb[4].setEnabled(false);
		user_set_tb[4].setEditable(false);

		user_set_lb[6].setBounds(94, 310, 248, 50); // -
		user_set_tb[5].setBounds(111, 320, 162, 30); // 번호

		user_set_noti.setBounds(0, 350, 292, 40);
		set_Font(user_set_noti, 18, new Color(238, 77, 77));

		user_set_btn_group.setBounds(0, 385, 292, 50);
		user_set_btn_group.setBackground(Color.white);
		user_set_btn_group.setLayout(new FlowLayout());

		set_Btn(user_set_ok_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(125, 40));
		set_Btn(user_set_cen_btn, 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(125, 40));

		// -------------

		for (int i = 0; i < user_set_tb.length; i++) {
			int n = i;
			if (n == 0 || n == 2 || n == 3 || n == 5) {
				user_set_tb[n].addMouseListener(new MouseAdapter() { // 결제하기
					public void mouseClicked(MouseEvent e) {
						edit_tb_sw();

						user_set_tb[n].requestFocus();

						set_TextField(user_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));

						if (n == 2 || n == 3) {
							user_set_tb[n].setVisible(false);
							if (n == 2) {
								user_set_pw.setVisible(true);
								set_TextField(user_set_pw, new Color(0, 120, 215), 15, new Color(0, 0, 0));
							} else {
								user_set_pw_chk.setVisible(true);
								set_TextField(user_set_pw_chk, new Color(0, 120, 215), 15, new Color(0, 0, 0));
							}
						}
					}
				});
			}
		}

		user_set_pw.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				edit_tb_sw();
				user_set_pw.requestFocus();
				set_TextField(user_set_pw, new Color(0, 120, 215), 15, new Color(0, 0, 0));
			}
		});

		user_set_pw_chk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				edit_tb_sw();
				user_set_pw_chk.requestFocus();
				set_TextField(user_set_pw_chk, new Color(0, 120, 215), 15, new Color(0, 0, 0));
			}
		});

		user_set_ok_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				final String NUM = "[0-9]+"; // 숫자판별

				String user_name = user_set_tb[0].getText();
				String pw = new String(user_set_pw.getPassword());
				String pw_chk = new String(user_set_pw_chk.getPassword());
				String user_phone = user_set_tb[5].getText();

				edit_tb_sw();

				if (pw.length() > 0) { // 비밀번호까지 바꿀라 할때
					if (user_name.equals("") || user_name.length() > 10) {
						user_set_noti.setText("이름을 올바르게 입력해주세요");
						set_TextField(user_set_tb[0], new Color(220, 0, 0), 15, new Color(0, 0, 0));
					} else {
						if (pw.equals("") || pw.length() > 10 || pw.length() < 4) {
							user_set_noti.setText("비밀번호를 정확하게 입력하세요.");
							set_TextField(user_set_pw, new Color(220, 0, 0), 15, new Color(0, 0, 0));
						} else {
							if (!pw.equals(pw_chk)) {
								user_set_noti.setText("비밀번호가 일치하지 않습니다.");
								set_TextField(user_set_pw_chk, new Color(220, 0, 0), 15, new Color(0, 0, 0));
							} else {
								if (!user_phone.matches(NUM)) {
									user_set_noti.setText("휴대폰 번호는 숫자만 가능합니다.");
									set_TextField(user_set_tb[5], new Color(220, 0, 0), 15, new Color(0, 0, 0));
								} else {
									if (user_phone.length() > 8 || user_phone.length() < 8) {
										user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
										set_TextField(user_set_tb[5], new Color(220, 0, 0), 15, new Color(0, 0, 0));
									} else {
										int result = JOptionPane.showConfirmDialog(null, "변경하시겠습니까?", "알림",
												JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
										if (result == JOptionPane.YES_OPTION) {
											String salt = salt_temp();
											String salt_pw = sha(salt + pw);
											edit_DB(charge_id, user_name, salt, salt_pw, "010" + user_phone);
										}
									}
								}
							}
						}
					}
				} else { // 비밀번호가 원래일시
					if (user_name.equals("") || user_name.length() > 10) {
						user_set_noti.setText("이름을 올바르게 입력해주세요");
						set_TextField(user_set_tb[0], new Color(220, 0, 0), 15, new Color(0, 0, 0));
					} else {
						if (!user_phone.matches(NUM)) {
							user_set_noti.setText("휴대폰 번호는 숫자만 가능합니다.");
							set_TextField(user_set_tb[5], new Color(220, 0, 0), 15, new Color(0, 0, 0));
						} else {
							if (user_phone.length() > 8 || user_phone.length() < 8) {
								user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
								set_TextField(user_set_tb[5], new Color(220, 0, 0), 15, new Color(0, 0, 0));
							} else {
								int result = JOptionPane.showConfirmDialog(null, "변경하시겠습니까?", "알림",
										JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									edit_DB(charge_id, user_name, "", "", "010" + user_phone);
								}
							}
						}
					}
				}
			}
		});

		user_set_cen_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				user_edit_main.dispose();
			}
		});

		// -------------------------------------
		user_edit_main.add(user_set_group);
		user_set_group.add(user_set_noti);
		user_set_group.add(user_set_btn_group);

		user_set_group.add(user_set_pw);
		user_set_group.add(user_set_pw_chk);

		user_set_btn_group.add(user_set_ok_btn);
		user_set_btn_group.add(user_set_cen_btn);

		// -------------------------------------

		String getPw;

		Object[] arr = { "암호를 입력해주세요.", user_edit_pw };
		int result = JOptionPane.showConfirmDialog(null, arr, "개인정보 수정", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			getPw = new String(user_edit_pw.getPassword());

			if (sha(salt + (getPw)).equals(user_pw)) {
				user_edit_main.setVisible(true);
			} else {
				Noti("암호가 일치하지않습니다.", "알림");
			}
		}
		user_edit_pw.setText("");
	}

	public void edit_DB(String user_id, String user_name, String salt, String user_pw, String user_phone) {
		String SQL;
		// -------
		try {
			conn = DriverManager.getConnection(url, id, pw);
			if (salt.length() > 0) {
				SQL = "update member set m_name=?, m_salt =?, m_pw =?, m_cell =? where m_id = ? ";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_name);
				pstmt.setString(2, salt);
				pstmt.setString(3, user_pw);
				pstmt.setString(4, user_phone);
				pstmt.setString(5, user_id);
				pstmt.executeUpdate();
			} else {
				SQL = "update member set m_name=?, m_cell =? where m_id = ? ";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_name);
				pstmt.setString(2, user_phone);
				pstmt.setString(3, user_id);
				pstmt.executeUpdate();
			}
			pstmt.close();
			conn.close();
			Noti("변경이 완료되었습니다.", "알림");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Charge_frame(String name, int place) {
		Charge_main.setTitle("요금충전");
		Charge_main.setSize(587, 475);
		Charge_main.setLocationRelativeTo(null);
		Charge_main.setResizable(false);
		Charge_main.getContentPane().setBackground(new Color(240, 240, 240));
		Charge_main.setLayout(null);

		// ------------------------------
		Qr_main.setSize(300, 392);
		Qr_main.setLocationRelativeTo(null);
		Qr_main.setResizable(false);
		Qr_main.getContentPane().setBackground(new Color(240, 240, 240));
		Qr_main.setUndecorated(true);
		Qr_main.setLayout(null);

		pay_logo.setIcon(reSize_Img("Img\\\\Src\\\\네이버페이.png", 120, 62));
		pay_logo.setBounds(0, 0, 300, 62);
		set_Font(pay_logo, 25, Color.black, JLabel.CENTER);

		pay_qr.setIcon(reSize_Img("Img\\\\Src\\\\qr.png", 200, 200));
		pay_qr.setBounds(0, 15, 300, 300);
		set_Font(pay_qr, 25, Color.black, JLabel.CENTER);

		pay_main.setBounds(0, 0, 300, 340);
		pay_main.setLayout(null);
		pay_main.setBackground(new Color(1, 199, 60));

		pay_info.setBounds(0, 260, 300, 50);
		set_Font(pay_info, 25, Color.white, JLabel.CENTER);

		pay_price.setText("3,000원");
		pay_price.setBounds(0, 290, 300, 50);
		set_Font(pay_price, 25, Color.white, JLabel.CENTER);

		pay_btn_group.setBounds(0, 340, 300, 52);
		pay_btn_group.setBackground(new Color(1, 199, 60));

		for (int i = 0; i < 2; i++) {
			pay_btn[i] = new JButton(pay_arr[i]);
			set_Btn(pay_btn[i], new Color(0, 120, 215), 20, new Color(255, 255, 255));
			pay_btn[i].setPreferredSize(new Dimension(100, 40));

			// -------------------------------------
			pay_btn_group.add(pay_btn[i]);
		}

		pay_btn[1].setBackground(new Color(192, 0, 0));

		pay_btn[0].addMouseListener(new MouseAdapter() { // 결제하기
			public void mouseClicked(MouseEvent e) {
				charge_DB(charge_id, item_time);
				Noti("결제가 완료되었습니다.", "알림");
				Charge_main.dispose();
				Qr_main.dispose();
				item_time = 0;
			}
		});

		pay_btn[1].addMouseListener(new MouseAdapter() { // 취소
			public void mouseClicked(MouseEvent e) {
				Qr_main.dispose();
			}
		});

		// ---------------------------------------
		Qr_main.add(pay_main);
		pay_main.add(pay_logo);
		pay_main.add(pay_qr);
		pay_main.add(pay_info);
		pay_main.add(pay_price);
		Qr_main.add(pay_btn_group);

		// -----------------------------

		user_place.setText(String.valueOf(place) + "번 자리 " + "(" + name + "님)");
		user_place.setBounds(10, 2, 200, 30);
		user_place.setFont(new Font(font_name, Font.PLAIN, 20));

		charge_title.setBounds(0, 10, 565, 30);
		charge_title.setText("요금 선택");
		set_Font(charge_title, 25, Color.black, JLabel.CENTER);

		charge_item_main.setBounds(5, 35, 565, 350);
		charge_item_main.setLayout(null);
		charge_item_main.setBackground(new Color(230, 230, 230));

		charge_list_group.setBounds(0, 40, 565, 310);
		charge_list_group.setBackground(new Color(230, 230, 230));
		charge_list_group.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		charge_btn_group.setBounds(0, 388, 587, 50);
		charge_btn_group.setBackground(new Color(240, 240, 240));
		charge_btn_group.setLayout(new FlowLayout());

		for (int i = 0; i < 2; i++) {
			charge_btn[i] = new JButton(charge_btn_arr[i]);
			set_Btn(charge_btn[i], new Color(0, 120, 215), 20, new Color(255, 255, 255));
			charge_btn[i].setPreferredSize(new Dimension(100, 40));
			// --------------
			charge_btn_group.add(charge_btn[i]);
		}

		for (int i = 0; i < itme_len; i++) {
			charge_item[i] = new JPanel();
			charge_item[i].setPreferredSize(new Dimension(175, 90));
			charge_item[i].setBackground(Color.white);
			charge_item[i].setLayout(null);

			// ---------------------------------------------------------------
			charge_time = Integer.parseInt(charge_item_time_arr[i]);

			charge_item_name[i] = new JLabel(charge_time + "시간");
			set_Font(charge_item_name[i], 20, Color.black, JLabel.CENTER);

			if (charge_time < 10) {
				charge_item_time[i] = new JLabel("(0" + charge_time + ":00)");
			} else {
				charge_item_time[i] = new JLabel("(" + charge_time + ":00)");
			}
			set_Font(charge_item_time[i], 20, Color.black, JLabel.CENTER);

			charge_item_price[i] = new JLabel(format.format((charge_time * 1000)) + "원");
			set_Font(charge_item_price[i], 25, Color.black, JLabel.CENTER);

			charge_item_name[i].setBounds(0, 11, 175, 15);
			charge_item_time[i].setBounds(0, 35, 175, 15);
			charge_item_price[i].setBounds(0, 53, 175, 30);

			// ---------------------------------------------------------------
			charge_item_btn[i] = new JButton();
			charge_item_btn[i].setSize(175, 90);
			charge_item_btn[i].setBorderPainted(false);
			charge_item_btn[i].setFocusPainted(false);
			charge_item_btn[i].setContentAreaFilled(false);

			// ---------------------------------------------------------------
			charge_list_group.add(charge_item[i]);
			charge_item[i].add(charge_item_name[i]);
			charge_item[i].add(charge_item_time[i]);
			charge_item[i].add(charge_item_price[i]);
			charge_item[i].add(charge_item_btn[i]);
		}

		// -----------------------------
		for (int i = 0; i < itme_len; i++) {
			int n = i;
			int time = Integer.parseInt(charge_item_time_arr[n]);
			charge_item_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					item_time = time * 3600;
				}
			});
		}

		charge_btn[0].setVisible(false);

		charge_btn[0].addMouseListener(new MouseAdapter() { // 이전
			public void mouseClicked(MouseEvent e) {
				item_time = 0;
				charge_page_count = 0;
				charge_page(charge_page_count);
				charge_btn[0].setVisible(false);
				charge_btn[1].setVisible(true);
			}
		});

		charge_btn[1].addMouseListener(new MouseAdapter() { // 다음
			public void mouseClicked(MouseEvent e) {
				if (item_time == 0) {
					Noti("충전할 요금을 선택해주세요.", "알림");
				} else {
					int result = JOptionPane
							.showConfirmDialog(
									null, "충전할 시간은 " + (item_time / 3600) + "시간("
											+ format.format((item_time / 3600) * 1000) + "원) 입니다.",
									"알림", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						charge_page_count = 1;
						charge_btn[0].setVisible(true);
						charge_page(charge_page_count);
						charge_btn[1].setVisible(false);
					}
				}
			}
		});

		Charge_main.add(user_place);
		Charge_main.add(user_name);
		Charge_main.add(charge_item_main);
		charge_item_main.add(charge_title);
		charge_item_main.add(charge_list_group);
		Charge_main.add(charge_btn_group);

		// -----------------------------

	}

	public void charge_page(int count) {
		String arr[] = { "Img\\Src\\현금결제.png", "Img\\Src\\카드결제.png", "Img\\Src\\카카오페이.png", "", "",
				"Img\\Src\\네이버페이.png", "", "", "Img\\Src\\토스페이.png" };
		if (count == 1) {
			charge_title.setText("결제 방식");
			for (int i = 0; i < 9; i++) {
				payment_item[i] = new JPanel();
				payment_item[i].setPreferredSize(new Dimension(175, 90));
				payment_item[i].setBackground(new Color(230, 230, 230));
				payment_item[i].setLayout(null);

				// --------------------------------------
				payment_img[i] = new JLabel(reSize_Img(arr[i], 175, 90));
				payment_img[i].setBounds(0, 0, 175, 90);

				// --------------------------------------
				payment_btn[i] = new JButton();
				payment_btn[i].setSize(175, 90);
				payment_btn[i].setBorderPainted(false);
				payment_btn[i].setFocusPainted(false);
				payment_btn[i].setContentAreaFilled(false);

				// --------------------------------------
				charge_item[i].setVisible(false);
				charge_list_group.add(payment_item[i]);
				payment_item[i].add(payment_img[i]);
				payment_item[i].add(payment_btn[i]);

			}
			// ----------------------------------
			payment_img[3].setIcon(reSize_Img("", 175, 90));
			payment_img[4].setIcon(reSize_Img("", 175, 90));
			payment_img[6].setIcon(reSize_Img("", 175, 90));
			payment_img[7].setIcon(reSize_Img("", 175, 90));

			// -----------------------------------
		} else {
			charge_title.setText("요금 선택");
			for (int i = 0; i < 9; i++) {
				payment_item[i].setVisible(false);
				charge_item[i].setVisible(true);
			}
		}

		for (int i = 0; i < itme_len; i++) {
			int n = i;
			payment_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (n == 0 || n == 1) {
						Noti("현금 및 카드 결제는 카운터에서 가능합니다.", "알림");
					} else if (n == 2 || n == 5 || n == 8) {
						if (n == 2) {
							pay_logo.setIcon(reSize_Img("Img\\\\Src\\\\카카오페이.png", 120, 62));
							pay_main.setBackground(new Color(255, 223, 0));
							pay_btn_group.setBackground(new Color(255, 223, 0));
						} else if (n == 5) {
							pay_logo.setIcon(reSize_Img("Img\\\\Src\\\\네이버페이.png", 120, 62));
							pay_main.setBackground(new Color(1, 199, 60));
							pay_btn_group.setBackground(new Color(1, 199, 60));
						} else {
							pay_logo.setIcon(reSize_Img("Img\\\\Src\\\\토스페이.png", 120, 62));
							pay_main.setBackground(new Color(0, 80, 255));
							pay_btn_group.setBackground(new Color(0, 80, 255));
						}
						pay_price.setText(format.format((item_time / 3600) * 1000) + "원");
						Qr_main.setVisible(true);
					}
				}
			});
		}

		Charge_main.addWindowListener(new java.awt.event.WindowAdapter() { // 상위로 닫았을 때 초기화
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				item_time = 0;
			}

		});
	}

	public void login_frame() {
		// ---------------------------------------------- 메인 프레임
		login_main.setBounds(484, 140, 300, 330);
		login_main.setBackground(Color.white);
		login_main.setLayout(null);
		login_title.setBounds(0, 5, 300, 50);
		set_Font(login_title, 35, new Color(0, 0, 0), JLabel.CENTER);

		// ---------------------------------------------- 로그인 메인
		login_tap_group.setBounds(20, 60, 260, 40);
		login_tap_group.setLayout(null);

		user_tap.setBounds(0, 0, 130, 40); // 회원 탭
		user_tap_lb.setBounds(0, 0, 130, 40);
		set_Panel(user_tap, new Color(0, 120, 215));
		set_Font(user_tap_lb, 16, Color.white, JLabel.CENTER);

		non_user_tap.setBounds(130, 0, 130, 40); // 비회원 탭
		non_user_tap_lb.setBounds(0, 0, 130, 40);
		set_Panel(non_user_tap, new Color(240, 240, 240));
		set_Font(non_user_tap_lb, 16, new Color(0, 120, 215), JLabel.CENTER);

		// ---------------------------------------------- 회원, 비회원 탭

		for (int i = 0; i < login_tb_arr.length; i++) {
			login_tb[i] = new JTextField(login_tb_arr[i]);
			set_TextField(login_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			login_tb[i].setEnabled(false);
			login_tb[i].setDisabledTextColor(new Color(200, 200, 200));
			login_main.add(login_tb[i]);
		}

		login_tb[0].setBounds(20, 110, 260, 40); // 아이디 입력
		login_tb[1].setBounds(20, 160, 260, 40); // 비밀번호 입력
		login_tb[2].setBounds(20, 110, 260, 40); // 카드번호 입력
		login_tb[2].setVisible(false);

		pw_tb.setBounds(20, 160, 260, 40); // 입력
		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		pw_tb.setEchoChar('*');
		pw_tb.setVisible(false);

		login_tb[1].addMouseListener(new MouseAdapter() { // 취소
			public void mouseClicked(MouseEvent e) {
				login_tb[1].setVisible(false); // 비밀번호 숨김
				pw_tb.setVisible(true);
				pw_tb.requestFocus();
			}
		});

		// ---------------------------------------------- 아이디, 비밀번호, 카드번호 입력
		login_noti.setBounds(0, 200, 300, 40);
		set_Font(login_noti, 18, new Color(220, 0, 0), JLabel.CENTER);

		// ---------------------------------------------- 상태 알림
		login_btn.setBounds(20, 240, 260, 40);
		set_Btn(login_btn, new Color(0, 120, 215), 15, new Color(255, 255, 255));

		user_find_reg_group.setBounds(20, 290, 260, 30);
		user_find_reg_group.setLayout(null);
		user_find_reg_group.setBackground(Color.white);

		user_find_reg_lb.setBounds(0, -5, 260, 40);
		set_Font(user_find_reg_lb, 15, Color.black, JLabel.CENTER);

		user_find_btn.setBounds(63, 5, 71, 20);
		hide_btn(user_find_btn);
		user_reg_btn.setBounds(145, 5, 53, 20);
		hide_btn(user_reg_btn);

		// ---------------------------------------------- 로그인, ID/PW 찾기, 회원가입 버튼
		for (int i = 0; i < login_tb_arr.length; i++) {
			int n = i;
			login_tb[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					login_tb_sw(n);
				}
			});
		}

		pw_tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_tb_sw(2);
				set_TextField(pw_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
			}
		});

		// ---------------------------------------------- 입력
		user_tap.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_count = 0;
				char[] pw = pw_tb.getPassword();
				tap_swap(user_tap, user_tap_lb, non_user_tap, non_user_tap_lb);
				login_tb[0].setVisible(true);
				pw_tb.setVisible(true);
				if (pw.length == 0) {
					pw_tb.setVisible(false);
					login_tb[1].setVisible(true);
				} else {
					login_tb[1].setVisible(false);
				}
				login_tb[2].setVisible(false);
			}
		});

		non_user_tap.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_count = 1;
				tap_swap(non_user_tap, non_user_tap_lb, user_tap, user_tap_lb);
				login_tb[0].setVisible(false);
				login_tb[1].setVisible(false);
				pw_tb.setVisible(false);
				login_tb[2].setVisible(true);
			}
		});

		// ----------------------------------------------
		login_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				char[] pw = pw_tb.getPassword();
				if (login_count == 0) {
					if (login_tb[0].getText().equals("") || login_tb[0].getText().equals("아이디")
							|| String.valueOf(pw).length() == 0) {
						login_noti.setText("아이디를 또는 비밀번호를 입력해주세요.");
					} else {
						login(login_tb[0].getText(), String.valueOf(pw));
					}
				} else {
					if (login_tb[2].getText().equals("") || login_tb[2].getText().equals("카드번호")) {
						login_noti.setText("카드번호를 입력해주세요.");
					} else {
						login(login_tb[2].getText(), "");
					}
				}
			}
		});
		// ---------------------------------------------- 로그인

		user_find_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_main.setVisible(false);
				reg_main.setVisible(false);
				find_frame();
				find_main.setVisible(true);
			}
		});

		user_reg_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_main.setVisible(false);
				find_main.setVisible(false);
				reg_frame();
				reg_main.setVisible(true);
			}
		});

		// ---------------------------------------------- 아이디찾기, 회원가입
		main_frame.add(login_main);
		login_main.add(login_title);
		login_main.add(login_tap_group);
		login_main.add(pw_tb);
		login_main.add(login_noti);
		login_main.add(login_btn);
		login_main.add(user_find_reg_group);

		// ----------------------------------------------
		user_find_reg_group.add(user_find_reg_lb);
		user_find_reg_group.add(user_find_btn);
		user_find_reg_group.add(user_reg_btn);

		// ----------------------------------------------
		login_tap_group.add(user_tap);
		login_tap_group.add(non_user_tap);
		user_tap.add(user_tap_lb);
		non_user_tap.add(non_user_tap_lb);
		// ----------------------------------------------
	}

	// ---------------------------------------------- 로그인

	public void find_frame() {
		find_main.setBounds(484, 140, 300, 390);
		find_main.setBackground(Color.white);
		find_main.setLayout(null);
		find_main.setVisible(false);

		find_btn_group.setBounds(20, 295, 260, 100);
		find_btn_group.setBackground(Color.white);
		find_btn_group.setLayout(null);

		for (int i = 0; i < find_lb_arr.length; i++) {
			find_lb[i] = new JLabel(find_lb_arr[i]);
			set_Font(find_lb[i], 18, Color.black, JLabel.LEFT);
			find_main.add(find_lb[i]);
		}

		for (int i = 0; i <= 5; i++) {
			find_tb[i] = new JTextField();
			find_tb[i].setText(find_tb_arr[i]);
			set_TextField(find_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			find_tb[i].setDisabledTextColor(new Color(200, 200, 200));
			find_main.add(find_tb[i]);
		}

		for (int i = 0; i <= 2; i++) {
			find_btn[i] = new JButton(find_btn_arr[i]);
			set_Btn(find_btn[i], new Color(0, 120, 215), 15, new Color(255, 255, 255));
			find_btn_group.add(find_btn[i]);
		}

		find_lb[0].setBounds(20, 0, 260, 50);
		set_Font(find_lb[0], 25, new Color(0, 0, 0), JLabel.CENTER);

		find_lb[1].setBounds(20, 40, 260, 50); // 아이디 찾기

		find_lb[2].setBounds(20, 75, 260, 50); // 이름 :
		find_tb[0].setBounds(85, 85, 195, 30);

		find_lb[3].setBounds(20, 115, 260, 50); // 핸드폰 :
		find_tb[1].setBounds(85, 125, 60, 30);
		find_tb[1].setHorizontalAlignment(JLabel.CENTER);
		find_tb[1].setEnabled(false);

		find_lb[4].setBounds(150, 115, 260, 50); // -
		find_tb[2].setBounds(165, 125, 115, 30);

		find_lb[5].setBounds(20, 150, 260, 50); // 아이디 찾기

		find_lb[6].setBounds(20, 185, 260, 50); // 아이디 :
		find_tb[3].setBounds(85, 195, 195, 30);

		find_lb[7].setBounds(20, 225, 260, 50); // 핸드폰 :
		find_tb[4].setBounds(85, 235, 60, 30);
		find_tb[4].setHorizontalAlignment(JLabel.CENTER);
		find_tb[4].setEnabled(false);

		find_lb[8].setBounds(150, 225, 260, 50); // 핸드폰 :
		find_tb[5].setBounds(165, 235, 115, 30);

		find_btn[0].setBounds(0, 0, 125, 40);
		find_btn[1].setBounds(135, 0, 125, 40);
		find_btn[2].setBounds(0, 45, 260, 40);
		set_Btn(find_btn[2], new Color(220, 0, 0), 15, new Color(255, 255, 255));

		find_noti.setBounds(20, 260, 260, 40);
		set_Font(find_noti, 18, new Color(220, 0, 0), JLabel.CENTER);
		// ---------------------------------------------- 입력 및 버튼

		for (int i = 0; i < find_tb_arr.length; i++) {
			int n = i;
			find_tb[i].addMouseListener(new MouseAdapter() { // 입력
				public void mouseClicked(MouseEvent e) {
					find_tb_sw(n);
				}
			});
		}

		find_btn[0].addMouseListener(new MouseAdapter() { // 아이디 찾기
			public void mouseClicked(MouseEvent e) {
				find_count = 0;
				String name = find_tb[0].getText();
				String phone = "010" + find_tb[2].getText();

				for (int i = 0; i < 3; i++) {
					set_TextField(find_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
				}
				find_noti.setText("");
				if (name.equals("") || name.equals("이름") || name.length() > 10) {
					find_noti.setText("이름을 정확하게 입력하세요.");
					set_TextField(find_tb[0], new Color(220, 0, 0), 15, new Color(200, 200, 200));
				} else if (find_tb[2].getText().equals("") || find_tb[2].getText().equals("휴대폰 번호")
						|| find_tb[2].getText().length() < 8 || find_tb[2].getText().length() > 8) {
					find_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					set_TextField(find_tb[2], new Color(220, 0, 0), 15, new Color(200, 200, 200));
				} else {
					find_DB(name, phone);
				}
			}
		});

		find_btn[1].addMouseListener(new MouseAdapter() { // 비밀번호 찾기
			public void mouseClicked(MouseEvent e) {
				find_count = 1;
				String id = find_tb[3].getText();
				String phone = "010" + find_tb[5].getText();

				for (int i = 3; i < find_tb.length; i++) {
					set_TextField(find_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
				}
				find_noti.setText("");
				if (id.equals("") || id.equals("아이디") || id.length() > 10 || id.length() < 4) {
					find_noti.setText("아이디를 정확하게 입력하세요.");
					set_TextField(find_tb[3], new Color(220, 0, 0), 15, new Color(200, 200, 200));
				} else if (find_tb[5].getText().equals("") || find_tb[5].getText().equals("휴대폰 번호")
						|| find_tb[5].getText().length() < 8 || find_tb[5].getText().length() > 8) {
					find_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					set_TextField(find_tb[5], new Color(220, 0, 0), 15, new Color(200, 200, 200));
				} else {
					find_DB(id, phone);
				}
			}
		});

		find_btn[2].addMouseListener(new MouseAdapter() { // 취소
			public void mouseClicked(MouseEvent e) {
				find_main.setVisible(false);
				find_noti.setText("");
				login_main.setVisible(true);
			}
		});

		// ---------------------------------------------- 버튼
		main_frame.add(find_main);
		find_main.add(find_noti);
		find_main.add(find_btn_group);
	}

	public void reg_frame() {
		reg_main.setBounds(484, 140, 300, 430);
		reg_main.setBackground(Color.white);
		reg_main.setLayout(null);
		reg_main.setVisible(false);

		reg_btn_group.setBounds(0, 380, 300, 40);
		reg_btn_group.setBackground(Color.white);
		reg_btn_group.setLayout(null);

		for (int i = 0; i < reg_lb_arr.length; i++) {
			reg_lb[i] = new JLabel(reg_lb_arr[i]);
			set_Font(reg_lb[i], 18, Color.black, JLabel.LEFT);
			reg_main.add(reg_lb[i]);
		}

		for (int i = 0; i <= 5; i++) {
			reg_tb[i] = new JTextField();
			reg_tb[i].setText(reg_tb_arr[i]);
			set_TextField(reg_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			reg_tb[i].setDisabledTextColor(new Color(200, 200, 200));
			reg_main.add(reg_tb[i]);
		}

		for (int i = 0; i < 2; i++) {
			reg_btn[i] = new JButton(reg_btn_arr[i]);
			set_Btn(reg_btn[i], new Color(0, 120, 215), 15, Color.white);
			reg_btn_group.add(reg_btn[i]);
		}

		reg_lb[0].setBounds(0, 0, 300, 50);

		set_Font(reg_lb[0], 25, Color.black, JLabel.CENTER);

		reg_lb[1].setBounds(20, 40, 260, 50); // 이름(5자)
		reg_tb[0].setBounds(20, 80, 260, 30);

		reg_lb[2].setBounds(20, 100, 260, 50); // 아이디(4~10자)
		reg_tb[1].setBounds(20, 140, 260, 30);

		reg_lb[3].setBounds(20, 160, 260, 50); // 비밀번호(4~10자)
		reg_tb[2].setBounds(20, 200, 260, 30);

		reg_pw.setBounds(20, 200, 260, 30);
		reg_pw.setEchoChar('*');
		reg_pw.setVisible(false);

		reg_lb[4].setBounds(20, 220, 260, 50); // 비밀번호 확인
		reg_tb[3].setBounds(20, 260, 260, 30);

		reg_pw_chk.setBounds(20, 260, 260, 30);
		reg_pw_chk.setEchoChar('*');
		reg_pw_chk.setVisible(false);

		reg_lb[5].setBounds(20, 280, 260, 50); // 비밀번호 확인
		reg_tb[4].setBounds(20, 320, 100, 30);
		reg_tb[4].setHorizontalAlignment(JLabel.CENTER);
		reg_tb[4].setEnabled(false);

		reg_lb[6].setBounds(130, 310, 260, 50); // -
		reg_tb[5].setBounds(150, 320, 130, 30);

		reg_noti.setBounds(0, 345, 300, 40);
		set_Font(reg_noti, 18, new Color(220, 0, 0), JLabel.CENTER);

		reg_btn[0].setBounds(20, 0, 125, 40);
		reg_btn[1].setBounds(155, 0, 125, 40);
		reg_btn[1].setBackground(new Color(220, 0, 0));

		// ------------------------------------------------------- 입력 및 버튼

		for (int i = 0; i < reg_tb_arr.length; i++) {
			int n = i;
			reg_tb[i].addMouseListener(new MouseAdapter() { // 입력
				public void mouseClicked(MouseEvent e) {
					reg_tb_sw(n);
				}
			});
		}

		reg_btn[0].addMouseListener(new MouseAdapter() { // 확인
			public void mouseClicked(MouseEvent e) {
				char[] get_pw = reg_pw.getPassword();
				char[] get_pw_chk = reg_pw_chk.getPassword();

				final String NUM = "[0-9]+"; // 숫자판별
				final String KOR = "^[ㄱ-ㅎ가-힣]*$"; // 숫자판별

				final String chk = "^[0-9a-zA-Z]*$";

				String name = reg_tb[0].getText();
				String id = reg_tb[1].getText();
				String pw = String.valueOf(get_pw);
				String pw_chk = String.valueOf(get_pw_chk);
				String phone = reg_tb[5].getText();
				String salt = "";
				String sha_pw = "";

				for (int i = 0; i < reg_tb.length; i++) {
					set_TextField(reg_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
				}
				reg_noti.setText("");
				set_TextField(reg_pw, new Color(200, 200, 200), 15, new Color(200, 200, 200));
				set_TextField(reg_pw_chk, new Color(200, 200, 200), 15, new Color(200, 200, 200));

				if (name.equals("") || name.equals("이름")) {
					reg_noti.setText("이름을 입력해주세요.");
					set_TextField(reg_tb[0], new Color(220, 0, 0), 15, new Color(200, 200, 200));
				} else {
					if (id.equals("") || id.equals("아이디")) {
						reg_noti.setText("아이디를 입력해주세요.");
						set_TextField(reg_tb[1], new Color(220, 0, 0), 15, new Color(200, 200, 200));
					} else if (reg_tb[2].getText().equals("비밀번호") || pw.length() == 0) {
						reg_noti.setText("비밀번호를 입력해주세요.");
						set_TextField(reg_tb[2], new Color(220, 0, 0), 15, new Color(200, 200, 200));
						set_TextField(reg_pw, new Color(220, 0, 0), 15, new Color(200, 200, 200));
					} else if (reg_tb[5].getText().equals("") || reg_tb[5].getText().equals("휴대폰 번호")) {
						reg_noti.setText("휴대폰 번호를 입력해주세요.");
						set_TextField(reg_tb[5], new Color(220, 0, 0), 15, new Color(200, 200, 200));
					} else {
						if (!id.matches(chk)) {
							reg_noti.setText("아이디는 영문, 숫자만 가능합니다.");
							set_TextField(reg_tb[1], new Color(220, 0, 0), 15, new Color(200, 200, 200));
						} else if (!pw.matches(chk)) {
							reg_noti.setText("비밀번호는 영문, 숫자, 특수문자만 가능합니다.");
							set_TextField(reg_pw, new Color(220, 0, 0), 15, new Color(200, 200, 200));
						} else if (!reg_tb[5].getText().matches(NUM)) {
							reg_noti.setText("휴대폰 번호는 숫자만 가능합니다.");
							set_TextField(reg_tb[5], new Color(220, 0, 0), 15, new Color(200, 200, 200));
						} else {
							if (name.length() > 10 || name.equals("이름")) {
								reg_noti.setText("이름을 정확하게 입력하세요.");
								set_TextField(reg_tb[0], new Color(220, 0, 0), 15, new Color(200, 200, 200));
							} else {
								if (id.length() > 10 || 4 > id.length() || id.equals("아이디")) {
									reg_noti.setText("아이디를 정확하게 입력하세요.");
									set_TextField(reg_tb[1], new Color(220, 0, 0), 15, new Color(200, 200, 200));
								} else if (pw.length() > 10 || 4 > pw.length() || reg_tb[2].equals("비밀번호")) {
									reg_noti.setText("비밀번호를 정확하게 입력하세요.");
									set_TextField(reg_pw, new Color(220, 0, 0), 15, new Color(200, 200, 200));
								} else if (!pw.equals(pw_chk)) {
									reg_noti.setText("비밀번호가 일치하지 않습니다");
									set_TextField(reg_pw_chk, new Color(220, 0, 0), 15, new Color(200, 200, 200));
								} else if (phone.length() != 8 || phone.equals("휴대폰 번호")) {
									reg_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
									set_TextField(reg_tb[5], new Color(220, 0, 0), 15, new Color(200, 200, 200));
								} else {
									reg_noti.setText("");
									int result = JOptionPane.showConfirmDialog(null, "가입하시겠습니까?", "알림",
											JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
									if (result == JOptionPane.YES_OPTION) {
										salt = salt_temp();
										sha_pw = sha(salt + pw.toLowerCase());
										reg_chk_DB(name, id.toLowerCase(), salt, sha_pw, ("010" + phone), join_Date());
									}
								}
							}
						}
					}
				}
			}
		});

		reg_pw.addMouseListener(new MouseAdapter() { // 비밀번호
			public void mouseClicked(MouseEvent e) {
				set_TextField(reg_pw, new Color(0, 120, 215), 15, new Color(0, 0, 0));
				set_TextField(reg_pw_chk, new Color(200, 200, 200), 15, new Color(200, 200, 200));
				reg_pw.requestFocus();
			}
		});

		reg_pw_chk.addMouseListener(new MouseAdapter() { // 비밀번호 확인
			public void mouseClicked(MouseEvent e) {
				set_TextField(reg_pw, new Color(200, 200, 200), 15, new Color(200, 200, 200));
				set_TextField(reg_pw_chk, new Color(0, 120, 215), 15, new Color(0, 0, 0));
				reg_pw_chk.requestFocus();
			}
		});

		reg_btn[1].addMouseListener(new MouseAdapter() { // 취소
			public void mouseClicked(MouseEvent e) {
				reg_main.setVisible(false);
				reg_noti.setText("");
				login_main.setVisible(true);
				reg_pw.setText("");
				reg_pw_chk.setText("");
			}
		});

		// ----------------------------------------------------------- 버튼
		main_frame.add(reg_main);
		reg_main.add(reg_btn_group);
		reg_main.add(reg_noti);
		reg_main.add(reg_pw); // 비밀번호
		reg_main.add(reg_pw_chk); // 비밀번호 확인
		// ----------------------------------------------------------- 추가
	}

	public void user_frame() {
		user_main.setTitle("욱PC방");
		user_main.setSize(345, 165);
		user_main.setLocationRelativeTo(null);
		user_main.setResizable(false);
		user_main.setUndecorated(true);
		user_main.getContentPane().setBackground(Color.white);
		user_main.setVisible(true);
		user_main.setLayout(null);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - user_main.getWidth();
		int y = 0;
		user_main.setLocation(x, y); // 오른쪽 구석으로 창 열기

		user_place_group.setBounds(5, 5, 100, 100);
		user_place_group.setLayout(null);
		user_place_group.setBackground(new Color(220, 220, 220));

		user_place.setBounds(0, 10, 100, 50);
		user_place.setForeground(Color.white);
		set_Font(user_place, 50, new Color(0, 0, 0), JLabel.CENTER);

		user_name.setBounds(0, 45, 100, 50);
		user_name.setForeground(Color.white);
		set_Font(user_name, 15, new Color(0, 0, 0), JLabel.CENTER);

		user_play_info.setBounds(110, 5, 180, 100);
		user_play_info.setLayout(null);
		user_play_info.setBackground(Color.white);

		user_btn_group.setBounds(-5, 110, 350, 60);
		user_btn_group.setLayout(new FlowLayout());
		user_btn_group.setBackground(Color.white);

		user_set_btn.setBounds(318, 2, 20, 20);
		set_Btn(user_set_btn);

		if (login_count == 0) {
			user_set_btn.setVisible(true);
		} else {
			user_set_btn.setVisible(false);
		}

		for (int i = 0; i < user_btn_arr.length; i++) {
			user_btn[i] = new JButton(user_btn_arr[i]);
			user_btn[i].setPreferredSize(new Dimension(100, 45));
			set_Btn(user_btn[i], new Color(0, 120, 215), 15, Color.white);
			user_btn[i].setFont(new Font(font_name, Font.PLAIN, 14));
			user_btn_group.add(user_btn[i]);
		}

		user_btn[2].setBackground(new Color(220, 0, 0));

		user_start_time.setBounds(0, -40, 250, 100);
		set_Font(user_start_time, 15, new Color(0, 0, 0), JLabel.LEFT);

		user_left_time.setBounds(0, -20, 250, 100);
		set_Font(user_left_time, 15, new Color(0, 0, 0), JLabel.LEFT);

		user_use_time.setBounds(0, 0, 250, 100);
		set_Font(user_use_time, 15, new Color(0, 0, 0), JLabel.LEFT);

		// ----------------------------------------------

		user_set_btn.addMouseListener(new MouseAdapter() { // 설정 화면
			public void mouseClicked(MouseEvent e) {
				user_edit_frame(charge_id);
			}
		});

		user_btn[0].addMouseListener(new MouseAdapter() { // 주문하기
			public void mouseClicked(MouseEvent e) {
				of.order_main.setVisible(true);
			}
		});

		user_btn[1].addMouseListener(new MouseAdapter() { // 카운트연락(임시)
			public void mouseClicked(MouseEvent e) {
			}
		});

		user_btn[2].addMouseListener(new MouseAdapter() { // 종료하기
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					user_main.dispose();
					main_frame.setVisible(true);
					timer.stop();
					sava_time_DB.stop();
					of.order_main.setVisible(false);
					of.order_clean();
				}
			}
		});

		// ----------------------------------------------
		user_main.add(user_place_group);
		user_main.add(user_play_info);
		user_main.add(user_btn_group);
		user_main.add(user_set_btn);

		user_place_group.add(user_place);
		user_place_group.add(user_name);

		user_play_info.add(user_start_time);
		user_play_info.add(user_left_time);
		user_play_info.add(user_use_time);
	}

	// ---------------------------------------------- 프레임
	public void login(String user_id, String user_pw) { // 로그인
		try {
			String SQL;
			conn = DriverManager.getConnection(url, id, pw);
			if (login_count == 0) {
				SQL = "Select * from member where m_id=?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_id);
			} else {
				SQL = "Select * from non_member where nm_card =?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_id);
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 정보가 존재할 때
				place = (int) (Math.random() * 99) + 1;
				if (login_count == 0) {
					if (rs.getString("m_id").equals("admin")) {
						if (rs.getString("m_pw").equals(sha(rs.getString("m_salt") + user_pw))) {
							Admin_Form af = new Admin_Form();
							af.admin_frame.setVisible(true);
							main_frame.dispose();
						} else {
							login_noti.setText("아이디 또는 비밀번호를 다시 입력해주세요.");
						}
					} else if (rs.getString("m_id").equals(user_id)) {
						if (rs.getString("m_pw").equals(sha(rs.getString("m_salt") + user_pw))) {
							user = rs.getString("m_name");
							user_name.setText(user);
							left_time = rs.getInt("m_leftTime");
							charge_id = user_id;
							of.user_id = charge_id;
							if (left_time < 1) {
								int result = JOptionPane.showConfirmDialog(null, "사용가능한 시간이 없습니다. 충전하시겠습니까?", "알림",
										JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									Charge_frame(user, place);
									Charge_main.setVisible(true);
								}
							} else {
								main_frame.dispose();
								user_place.setText(String.valueOf(place));
								user_start_time.setText(start_Time());
								user_frame();
								Time(left_time);
								save_Time(user_id);
								login_succ(place);
							}
						} else {
							login_noti.setText("아이디 또는 비밀번호를 다시 입력해주세요.");
						}
					}
				} else {
					if (rs.getString("nm_card").equals(user_id)) {
						main_frame.dispose();
						user = rs.getString("nm_card") + "번 카드";
						user_name.setText(user);
						user_place.setText(String.valueOf(place));
						user_start_time.setText(start_Time());
						user_frame();
						Time(0); // 카드는 후불
						save_Time(user_id);
						login_succ(place);
					}
				}
			} else { // 정보가 존재하지않을 때
				if (login_count == 0) {
					login_noti.setText("아이디 또는 비밀번호를 다시 입력해주세요.");
				} else {
					login_noti.setText("카드번호를 다시 입력해주세요.");
					set_TextField(login_tb[2], new Color(220, 0, 0), 15, new Color(0, 0, 0));
				}
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------- 로그인 DB

	public void find_DB(String m_name, String m_cell) { // 아이디 & 비밀번호 찾기
		String SQL;
		String star = "********";
		try {
			conn = DriverManager.getConnection(url, id, pw);
			if (find_count == 0) {
				SQL = "select * from member where m_name = ? and m_cell =? and m_name not in('관리자')";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, m_name);
				pstmt.setString(2, m_cell);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					String m_id = rs.getString("m_id");
					find_noti.setText(
							m_name + "님의 아이디는 " + m_id.substring(0, 2) + star.substring(0, m_id.length() - 2) + "입니다.");
				} else {
					find_noti.setText("일치하는 아이디가 없습니다.");
				}
			} else {
				SQL = "select * from member where m_id = ? and m_cell =? and m_name not in('admin')";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, m_name);
				pstmt.setString(2, m_cell);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int result = JOptionPane.showConfirmDialog(null, "임시 비밀번호를 발급하겠습니까?", "알림",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						temp_pw(rs.getString("m_id"));
						find_noti.setText("");
					} else {
						find_noti.setText("");
					}
				} else {
					find_noti.setText("일치하는 정보가 없습니다.");
				}
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void temp_pw(String m_id) {
		try {
			String salt = salt_temp();
			String temp_pw = salt_temp().substring(0, 10);
			String sha_pw = sha(salt + temp_pw);

			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "update member set m_salt = ?, m_pw = ? where m_id = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, salt);
			pstmt.setString(2, sha_pw);
			pstmt.setString(3, m_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti(m_id + "님의 임시 비밀번호는 " + temp_pw + "입니다.", "알림");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------- ID,PW 찾기 DB, 임시 비밀번호 발급
	void reg_DB(String m_name, String m_id, String m_salt, String m_pw, String m_cell, String m_joinDate) { // 아이디 추가
		try {
			String url = "jdbc:mysql://localhost/data?allowMultiQueries=true";
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "insert into member(m_name, m_id, m_salt, m_pw, m_cell, m_joinDate) values(?, ?, ?, ?, ?, ?); set @count=0;"
					+ " update member set m_idx =@count:=@count+1;";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, m_name);
			pstmt.setString(2, m_id);
			pstmt.setString(3, m_salt);
			pstmt.setString(4, m_pw);
			pstmt.setString(5, m_cell);
			pstmt.setString(6, m_joinDate);
			pstmt.executeUpdate();
			reg_noti.setText("가입에 성공하였습니다.");
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void reg_chk_DB(String m_name, String m_id, String m_salt, String m_pw, String m_cell, String m_joinDate) { // 중복체크
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "Select * from member where m_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, m_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				reg_noti.setText("존재하는 아이디입니다.");
			} else {
				String SQL1 = "Select * from member where m_cell=?";
				pstmt = conn.prepareStatement(SQL1);
				pstmt.setString(1, m_cell);
				ResultSet rs1 = pstmt.executeQuery();
				if (rs1.next()) {
					reg_noti.setText("존재하는 휴대폰 번호입니다.");
				} else {
					reg_DB(m_name, m_id, m_salt, m_pw, m_cell, join_Date());
				}
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------- 회원가입
	void charge_DB(String m_id, int time) {
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "update member set m_leftTime = ? where m_id = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, (left_time + time));
			pstmt.setString(2, m_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------- DB
	public String join_Date() { // DB에 입력될 사용자 접속 시간
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));
		return formatedNow;
	}

	public String start_Time() { // 시작 시간
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("시작 시간 : H시 mm분 ss초");
		String formatedNow = now.format(formatter);
		return formatedNow;
	}

	int[] time(int n) { // 시, 분, 초 변환
		int h = n / 3600;
		int m = (n / 60) % 60;
		int s = n % 60;
		return new int[] { h, m, s };
	}

	public void Time(int n) { // 타이머
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int start_t[] = time(use_time);
				if (login_count == 0) {
					left_time--;
					int time[] = time(left_time);
					user_left_time.setText("남은 시간 : " + time[0] + "시 " + time[1] + "분 " + time[2] + "초 ");
					// --------
					use_time++;
					user_use_time.setText("사용 시간 : " + start_t[0] + "시 " + start_t[1] + "분 " + start_t[2] + "초");
				} else if (login_count == 1) {
					use_time++;
					user_left_time.setText("사용 시간 : " + start_t[0] + "시 " + start_t[1] + "분 " + start_t[2] + "초");
				}
			}
		};
		timer = new Timer(1000, taskPerformer);
		timer.setInitialDelay(0);
		if (left_time > 0 || login_count == 1) {
			timer.start();
		} else {
			timer.stop();
		}
	}

	public void save_use_time_DB(int m_t, String m_id) { // 시간 저장
		String SQL;
		try {
			conn = DriverManager.getConnection(url, id, pw);
			if (login_count == 0) {
				SQL = "update member set m_lefttime = ? where m_id = ?";
				pstmt = conn.prepareStatement(SQL);
			} else {
				SQL = "update non_member set nm_useTime = ? where nm_card = ?";
				pstmt = conn.prepareStatement(SQL);
			}
			pstmt.setInt(1, m_t);
			pstmt.setString(2, m_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save_Time(String m_id) { // 10초에 한번 DB에 저장
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (login_count == 0) {
					save_use_time_DB(left_time, m_id);
				} else {
					save_use_time_DB(use_time, m_id);
				}
			}
		};
		sava_time_DB = new Timer(10000, taskPerformer);
		sava_time_DB.setInitialDelay(0);
		sava_time_DB.start();
	}

	// ---------------------------------------------- 시간 & 타이머 관련

	public static void main(String[] args) {
		Main m = new Main();
	}
}
