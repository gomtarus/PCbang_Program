import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Admin_Form extends DAO {
	DecimalFormat format = new DecimalFormat("#,###");
	String font_name = "배달의민족 주아";
	String admin_salt;

	String cell_chk_temp; // 전화번호 중복 체크
	int user_left_time;

	String admin_pw_temp = "";

	// --------------------------------------------------------------- 설정 관련 변수
	// ---------------------------------------------------------------------------
	JFrame admin_frame = new JFrame("관리 시스템");
	JPanel admin_form_bg = new JPanel();

	JPanel main_top_menu = new JPanel();
	JPanel main_top_btn_group = new JPanel();

	String main_set_btn_arr[] = { "좌석관리", "회원관리", "메뉴관리" };
	JButton main_set_btn[] = new JButton[main_set_btn_arr.length];
	JLabel main_title[] = new JLabel[main_set_btn_arr.length];
	JPanel main_item_group[] = new JPanel[main_set_btn_arr.length];

	JPanel aside_menu[] = new JPanel[main_set_btn_arr.length];
	JPanel main_panel[] = new JPanel[main_set_btn_arr.length];

	JButton logout_btn = new JButton("로그아웃");

	// ----------------------------------------------------------------------- 메인
	// ---------------------------------------------------------------------------
	ArrayList<String> place_add = new ArrayList<String>(); // 제거한 자리
	ArrayList<String> place_del = new ArrayList<String>(); // 추가한 자리
	// 제거 시 del 배열에 제거된 자리가 들어가고 추가되면 다시 제거된 자리가 제거된다.

	int place_add_del_count = 0; // 추가, 제거 상태 판별
	int Place_n = 105; // 좌석 갯수

	int place_set_count = 0; // 자리 설정 카운트
	int p_count = 104; // 자리 갯수
	int place_use_count = 0; // 사용 중인 자리 카운트

	JPanel Place_Frame[] = new JPanel[Place_n]; // 좌석 프레임
	JPanel Place[] = new JPanel[Place_n]; // 좌석 그룹
	JPanel Place_num_line[] = new JPanel[Place_n];
	JLabel Place_Num[] = new JLabel[Place_n]; // 좌석번호
	JLabel Place_Order[] = new JLabel[Place_n]; // 주문 여부
	JLabel Place_Name[] = new JLabel[Place_n]; // 이름
	JLabel Place_Use_Time[] = new JLabel[Place_n]; // 사용 시간

	JPanel place_count_group = new JPanel();
	JLabel place_count = new JLabel(p_count + "/" + place_use_count);

	String place_set_arr[] = { "추가", "삭제", "저장" };
	JButton place_set_btn[] = new JButton[place_set_arr.length];

	JButton order_mgt = new JButton("주문관리");
	JButton place_mgt = new JButton("좌석설정");

	// ------------------------------------------------------------- 좌석 생성 및 설정
	// ---------------------------------------------------------------------------
	JFrame order_frame = new JFrame("주문 관리");
	JPanel order_btn_group = new JPanel();
	JButton order_chk_btn = new JButton("배달완료");
	JButton order_del_btn = new JButton("주문삭제");

	String order_row[] = { "번호", "이름", "자리", "주문목록", "총 금액", "지불수단", "요청사항", "주문시간" };

	DefaultTableModel order_model = new DefaultTableModel(order_row, 0) { // 테이블
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	JTable order_table = new JTable(order_model);
	JScrollPane order_scrollpane = new JScrollPane(order_table);
	// -------------------------------------------------------------------- 주문관리
	// -------------------------------------------------------------------- 좌석관리
	int user_set_count = 0;
	int user_DB_count = 0;

	String admin_pw; // 관리자 salt 저장
	JCheckBox pw_edit = new JCheckBox("비밀번호 변경"); // 변경 여부

	JFrame user_set_frame = new JFrame("회원추가");
	JPanel user_set_frame_bg = new JPanel();
	JPanel user_set_group = new JPanel();

	JPanel user_set_btn_group = new JPanel();
	JButton user_set_ok_btn = new JButton("확인");
	JButton user_set_cen_btn = new JButton("취소");
	JLabel user_set_noti = new JLabel();

	String user_set_arr[] = { "회원추가", "이름(10자)", "아이디(4~10자)", "비밀번호(4~10자)", "비밀번호 확인", "휴대폰 번호", "-", "남은시간", "시",
			"분", "초" };
	JLabel user_set_lb[] = new JLabel[user_set_arr.length];

	String user_set_tb_arr[] = { "이름", "아이디", "비밀번호", "비밀번호 확인", "010", "휴대폰 번호", "0", "0", "0" };
	JTextField user_set_tb[] = new JTextField[user_set_tb_arr.length];

	JPasswordField pw_tb = new JPasswordField(); // 비밀번호
	JPasswordField pw_chk_tb = new JPasswordField(); // 비밀번호

	String user_btn_arr[] = { "카드관리", "추가", "수정", "삭제", "초기화" };
	JButton user_set_btn[] = new JButton[user_btn_arr.length];

	// -------------------------------------------------------------- 회원 추가, 수정
	// ---------------------------------------------------------------------------
	int user_select_row;
	int user_select_col;

	int user_idx_temp;
	String user_pw_temp;
	String user_join_temp;

	String user_row[] = { "번호", "이름", "아이디", "휴대폰 번호", "남은시간", "가입일", "최근접속날짜" };
	DefaultTableModel user_model = new DefaultTableModel(user_row, 0) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	JTable user_table = new JTable(user_model);
	JScrollPane user_scrollpane = new JScrollPane(user_table);

	// -------------------------------------------------------------- 회원 관리 테이블
	// ---------------------------------------------------------------------------
	JPanel user_search_group = new JPanel();
	JTextField user_search_tb = new JTextField("검색");
	JButton user_search_btn = new JButton(food_Img("Img/Src/search_icon.png", 35, 35));

	// ------------------------------------------------------------------ 회원 검색
	// --------------------------------------------------------------------------
	int menu_search_count = 0;
	int menu_set_count = 0;
	int menu_DB_count = 0;

	int menu_select_row;
	int menu_select_col;

	int row;

	JFrame menu_set_frame = new JFrame("음식 추가");
	JPanel menu_set_frame_bg = new JPanel();

	String menu_set_arr[] = { "품명", "종류", "가격", "원", "재고", "개", "확인", "취소" };
	JLabel menu_set_lb[] = new JLabel[menu_set_arr.length];

	JPanel menu_btn_group = new JPanel();
	String menu_set_btn_arr[] = { "이미지", "확인", "취소" };
	JButton menu_set_btn[] = new JButton[3];

	String menu_set_tb_arr[] = { "품명", "가격", "재고" };
	JTextField menu_set_tb[] = new JTextField[3];

	String menu_set_combo_arr[] = { "밥류", "면류", "튀김류", "과자류", "음료류", "기타" };
	JComboBox menu_set_combo = new JComboBox(menu_set_combo_arr);

	JPanel menu_img_frame = new JPanel();
	JLabel menu_img = new JLabel();

	String[] menu_arr = { "번호", "이름", "종류", "가격", "재고", "이미지" };
	Object[][] menu_row;

	DefaultTableModel menu_model = new DefaultTableModel(menu_arr, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	JTable menu_table = new JTable(menu_model) {
		@Override
		public Class<?> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
	};

	JScrollPane menu_scrollpane = new JScrollPane(menu_table);

	// ----------------------------------------------------------- 메뉴 추가, 수정 창
	// --------------------------------------------------------------------------
	String menu_search_arr[] = { "전체", "밥류", "면류", "튀김류", "과자류", "음료류", "기타" };
	JComboBox menu_search_combo = new JComboBox(menu_search_arr);

	JPanel menu_search_group = new JPanel();
	JTextField menu_search_tb = new JTextField("검색");
	JButton menu_search_btn = new JButton(food_Img("Img/Src/search_icon.png", 35, 35));

	String menu_btn_arr[] = { "추가", "수정", "삭제", "초기화" };
	JButton menu_btn[] = new JButton[menu_btn_arr.length];

	// ------------------------------------------------------------------ 메뉴 검색
	// ------------------------------------------------------------------ 메뉴 관리
	// --------------------------------------------------------------------------
	void page_Sw(int n) { // 각 페이지 전환
		for (int i = 0; i < main_set_btn_arr.length; i++) {
			set_Btn(main_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(110, 40));
			main_item_group[i].setVisible(false);
			aside_menu[i].setVisible(false);
			main_title[i].setVisible(false);
		}
		if (n == 1) { // 회원 관리
			user_DB();
		} else if (n == 2) { // 메뉴 관리
			menu_DB();
		}
		set_Btn(user_search_btn, 15, new Color(200, 200, 200), new Color(200, 200, 200), new Dimension(35, 35));
		set_TextField(user_search_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		user_search_tb.setText("검색");
		user_search_btn.setEnabled(false);
		// 회원 검색
		set_Btn(menu_search_btn, 15, new Color(200, 200, 200), new Color(200, 200, 200), new Dimension(35, 35));
		set_TextField(menu_search_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		menu_search_tb.setText("검색");
		menu_search_btn.setEnabled(false);
		// 메뉴 검색
		set_Btn(main_set_btn[n], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(110, 40));
		main_item_group[n].setVisible(true);
		aside_menu[n].setVisible(true);
		main_title[n].setVisible(true);
	}

	// ----------------------------------------------------------------- 메인 페이지
	// --------------------------------------------------------------------------
	void place_Set_btn_sw(int n) { // 각 버튼 색 전환
		for (int i = 0; i < place_set_btn.length; i++) {
			set_Btn(place_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		if (n == 0) { // 추가
			set_Btn(place_set_btn[0], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 1) { // 삭제
			set_Btn(place_set_btn[1], 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 2) { // 초기화
			set_Btn(place_set_btn[2], 15, new Color(255, 188, 0), new Color(255, 255, 255), new Dimension(214, 45));
		}
	}

	void place_Set_btn_hide_show() { // (추가, 삭제, 저장)버튼 숨기기,보이기
		if (place_set_count == 0) {
			place_set_count = 1;
			set_Btn(place_mgt, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(110, 40));
			place_set_btn[0].setVisible(true);
			place_set_btn[1].setVisible(true);
			place_set_btn[2].setVisible(true);
		} else {
			set_Btn(place_mgt, 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(110, 40));
			place_set_count = 0;
			place_set_btn[0].setVisible(false);
			place_set_btn[1].setVisible(false);
			place_set_btn[2].setVisible(false);
		}
	}

	// ------------------------------------------------------------------ 좌석 관리
	// --------------------------------------------------------------------------
	void user_Set_btn_sw(int n) { // 각 버튼 색 전환
		for (int i = 0; i < user_btn_arr.length; i++) {
			set_Btn(user_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		if (n == 0 || n == 1) { // 카드관리, 추가
			set_Btn(user_set_btn[n], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 2) { // 수정
			set_Btn(user_set_btn[n], 15, new Color(255, 188, 0), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 3 || n == 4) { // 삭제, 초기화
			set_Btn(user_set_btn[n], 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(214, 45));
		}
	}

	void user_Set_clean() { // 회원 설정 창 초기화
		for (int i = 0; i < user_set_tb.length; i++) {
			set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			user_set_tb[i].setText(user_set_tb_arr[i]);
			user_set_tb[i].setEnabled(true);
			user_set_tb[i].setEditable(true);
		}
		user_set_tb[4].setEnabled(false);
		user_set_tb[4].setEditable(false);

		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		set_TextField(pw_chk_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		// pw_tb.setVisible(false);
		// pw_chk_tb.setVisible(false);

		for (int i = 0; i < user_set_btn.length; i++) {
			set_Btn(user_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		pw_edit.setSelected(false);
		pw_edit.setVisible(false);
		user_set_noti.setText("");
	}

	// ------------------------------------------------------------------ 회원 관리
	// --------------------------------------------------------------------------
	void menu_btn_sw(int n) { // 각 버튼 색 전환
		for (int i = 0; i < menu_btn_arr.length; i++) {
			set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		if (n == 0) { // 추가
			set_Btn(menu_btn[n], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 1) { // 수정
			set_Btn(menu_btn[n], 15, new Color(255, 188, 0), new Color(255, 255, 255), new Dimension(214, 45));
		} else if (n == 2 || n == 3) { // 삭제
			set_Btn(menu_btn[n], 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(214, 45));
		}
	}

	void menu_Set_form_clean() { // 메뉴 폼 초기화
		for (int i = 0; i < menu_set_tb.length; i++) {
			set_TextField(menu_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			menu_set_tb[i].setText(menu_set_tb_arr[i]);
		}
		for (int i = 0; i < menu_btn.length; i++) {
			set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		menu_img.setIcon(null);
		menu_set_combo.setSelectedIndex(0);
	}

	int menu_Combo(String type) { // type에 따라 num값 리턴
		int num = 0;
		if (type.equals("밥류")) {
			num = 0;
		} else if (type.equals("면류")) {
			num = 1;
		} else if (type.equals("튀김류")) {
			num = 2;
		} else if (type.equals("과자류")) {
			num = 3;
		} else if (type.equals("음료류")) {
			num = 4;
		} else {
			num = 5;
		}
		return num;
	}

	// -------------------------------------------------------------------- 메뉴관리
	// ------------------------------------------------------------------ 버튼 스위치
	// ---------------------------------------------------------------------------
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

	// ------------------------------------------------------- Sha_256, Salt 암호화
	// ---------------------------------------------------------------------------
	String Date(int num) {
		String formatedNow;
		LocalDateTime now = LocalDateTime.now();
		if (num == 0) {
			formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));
		} else {
			formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss."));
		}
		return formatedNow;
	}

	// ----------------------------------------------------------- 날짜, 시간 가져오기
	// ---------------------------------------------------------------------------
	void re_count_idx(String tb, String idx) {
		try {
			String url = "jdbc:mysql://localhost/data?allowMultiQueries=true";
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "SET @count=0; update " + tb + " set " + idx + "=@count:=@count+1;";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------ Increment 재정렬
	// ---------------------------------------------------------------------------
	void table_Center(JTable tb, int num, int count) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		if (num == 0) {
			for (int i = 0; i < tb.getColumnCount(); i++) {
				tb.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		} else {
			for (int i = 0; i < count; i++) {
				tb.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}
	}

	// ------------------------------------------------------------ 테이블 가운데 정렬
	// ---------------------------------------------------------------------------
	void order_DB() { // 주문DB 불러오기
		order_model.setNumRows(0);
		re_count_idx("member", "m_idx");
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from menu_order";
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order_row[0] = rs.getString("od_idx");
				order_row[1] = rs.getString("od_name");
				order_row[2] = rs.getString("od_place");
				order_row[3] = rs.getString("od_menuList");
				order_row[4] = rs.getString("od_cost");
				order_row[5] = rs.getString("od_payment");
				order_row[6] = rs.getString("od_mag");
				order_row[7] = rs.getString("od_orderDate");
				order_model.addRow(order_row);
			}
			pstmt.close();
			conn.close();
			table_Center(order_table, 0, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void order_Del(String place, String time) { // 주문 제거
		try {
			String url = "jdbc:mysql://localhost/data?allowMultiQueries=true";
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "delete from menu_order where od_place = ? and od_orderDate = ?;"
					+ " set @count=0; update menu_order set od_idx =@count:=@count+1;";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, place);
			pstmt.setString(2, time);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			order_DB();
			Noti(place + " 주문이 삭제되었습니다.", "알림");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- 주문 DB
	// ---------------------------------------------------------------------------
	int[] conver_Time(int time) { // 시, 분, 초 변환
		int h = time / 3600;
		int m = (time / 60) % 60;
		int s = time % 60;
		return new int[] { h, m, s };
	}

	ArrayList<String> user_last_temp = new ArrayList<String>();

	void user_DB() { // 회원 정보 불러오기
		user_model.setNumRows(0);
		re_count_idx("member", "m_idx");
		user_last_temp.clear();
		user_last_temp.add("temp");
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from member";
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user_last_temp.add(rs.getString("m_lastTime"));
				int[] time = conver_Time(rs.getInt("m_leftTime"));
				user_model.addRow(new Object[] { rs.getString("m_idx"), rs.getString("m_name"), rs.getString("m_id"),
						rs.getString("m_cell"), time[0] + "시 " + time[1] + "분 " + time[2] + "초",
						rs.getString("m_joinDate"), rs.getString("m_lastTime") });
			}
			pstmt.close();
			conn.close();
			table_Center(user_table, 0, 0);
			user_set_noti.setText("");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원 DB불러오기 실패");
		}
	}

	// ------------------------------------------------------------- 회원 DB 불러오기
	// ---------------------------------------------------------------------------
	void user_DB_del(String user_id, String user_name, String user_cell) {
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "delete from member where m_id = ? and m_cell = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_cell);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti(user_name + "(" + user_id + ")회원을 삭제했습니다.", "알림");
			user_DB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------- 회원 DB 제거
	// ---------------------------------------------------------------------------
	void user_Edit_DB(int user_idx, String user_name, String user_salt, String user_pw, String user_cell,
			int user_leftTime, String user_id) {
		try {
			conn = DriverManager.getConnection(url, id, pw);
			if (user_set_count == 2 || user_set_count == 3) {
				String SQL = "update member set m_name =?, m_salt =?, m_pw =?, m_cell =?, m_leftTime =? where m_id = ? ";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_name);
				pstmt.setString(2, user_salt);
				pstmt.setString(3, user_pw);
				pstmt.setString(4, user_cell);
				pstmt.setInt(5, user_leftTime);
				pstmt.setString(6, user_id);
				pstmt.executeUpdate();
			} else if (user_set_count == 1) {
				String SQL = "update member set m_name =?, m_cell =?, m_leftTime =? where m_id = ? ";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user_name);
				pstmt.setString(2, user_cell);
				pstmt.setInt(3, user_leftTime);
				pstmt.setString(4, user_id);
				pstmt.executeUpdate();
			}
			pstmt.close();
			conn.close();
			if (user_set_count == 2 || user_set_count == 3) {
				Noti(user_name + "(" + user_id + ")를 수정하였습니다.", "알림");
			} else {
				Noti(user_name + "(" + user_id + ")회원을 수정하였습니다.", "알림");
			}
			user_model.setNumRows(0);
			int[] time = conver_Time(user_leftTime);
			user_model.addRow(new Object[] { user_idx, user_name, user_id, user_cell,
					time[0] + "시 " + time[1] + "분 " + time[2] + "초", user_join_temp, user_last_temp.get(user_idx) });
			user_set_frame.setVisible(false);
			user_Set_clean();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void user_Edit_chk_DB(int user_idx, String user_name, String user_salt, String user_pw, String user_cell,
			int user_leftTime, String user_id) { // 중복체크
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "Select * from member where m_cell=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_cell);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("m_id").equals(user_id)) {
					user_Edit_DB(user_idx, user_name, user_salt, user_pw, user_cell, user_leftTime, user_id);
				} else {
					user_set_noti.setText("휴대폰 번호가 존재합니다.");
				}
			} else {
				user_Edit_DB(user_idx, user_name, user_salt, user_pw, user_cell, user_leftTime, user_id);
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("회원 생성오류");
		}
	}

	// ---------------------------------------------------------------- 회원 DB 수정
	// ---------------------------------------------------------------------------
	void user_Reg_DB(String m_name, String m_id, String m_salt, String m_pw, String m_cell, int m_leftTime,
			String m_joinDate) { // 회원 추가 DB
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "insert into member(m_name, m_id, m_salt, m_pw, m_cell, m_leftTime, m_joinDate) values(?, ?, ?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, m_name);
			pstmt.setString(2, m_id);
			pstmt.setString(3, m_salt);
			pstmt.setString(4, m_pw);
			pstmt.setString(5, m_cell);
			pstmt.setInt(6, m_leftTime);
			pstmt.setString(7, m_joinDate);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			user_set_noti.setText("");
			Noti(m_name + "(" + m_id + ")회원을 추가하였습니다.", "알림");
			user_set_frame.setVisible(false);
			user_Set_clean();
			user_DB();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원 추가실패");
		}
	}

	void user_Reg_chk_DB(String m_name, String m_id, String m_salt, String m_pw, String m_cell, int m_leftTime,
			String m_joinDate) { // 중복 여부 체크
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "Select * from member where m_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, m_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user_set_noti.setText("아이디가 존재합니다.");
			} else {
				String SQL1 = "Select * from member where m_cell=?";
				pstmt = conn.prepareStatement(SQL1);
				pstmt.setString(1, m_cell);
				ResultSet rs1 = pstmt.executeQuery();
				if (rs1.next()) {
					user_set_noti.setText("휴대폰 번호가 존재합니다.");
				} else {
					user_Reg_DB(m_name, m_id, m_salt, m_pw, m_cell, m_leftTime, Date(0));
				}
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("회원 생성오류");
		}
	}

	// ---------------------------------------------------------------- 회원 DB 추가
	// ---------------------------------------------------------------------------
	void user_Search_DB(String name) { // 회원 검색
		try {
			user_model.setNumRows(0);
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from member where m_name = ? or m_id =? or m_cell =? and m_name not in('관리자')";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, name);
			pstmt.setString(2, name);
			pstmt.setString(3, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("m_name").equals("")) {
					Noti("검색결과가 없습니다.", "알림");
				} else {
					int[] time = conver_Time(rs.getInt("m_leftTime"));
					user_model.addRow(new Object[] { rs.getString("m_idx"), rs.getString("m_name"),
							rs.getString("m_id"), rs.getString("m_cell"),
							time[0] + "시 " + time[1] + "분 " + time[1] + "초", rs.getString("m_joinDate"),
							rs.getString("m_lastTime") });
				}
			}
			pstmt.close();
			conn.close();
			table_Center(user_table, 0, 0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원검색 오류");
		}
	}

	// ---------------------------------------------------------------- 회원 DB 검색
	// ---------------------------------------------------------------------------
	ArrayList<String> menu_Img_link = new ArrayList<String>();

	void menu_DB() {
		File_path = null;
		re_count_idx("menu", "m_idx");
		menu_model.setNumRows(0);
		menu_Img_link.clear();
		menu_Img_link.add("temp");
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from menu";
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				menu_Img_link.add(rs.getString("m_img"));
				menu_model.addRow(new Object[] { rs.getInt("m_idx"), rs.getString("m_name"), rs.getString("m_type"),
						format.format(rs.getInt("m_price")) + "원", rs.getString("m_stock") + "개",
						food_Img(rs.getString("m_img"), 80, 80) });
			}
			pstmt.close();
			conn.close();
			table_Center(menu_table, 1, 5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void menu_add_DB(String name, String type, int price, int stock, String img, String Date) {
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "insert into menu(m_name, m_type, m_stock, m_price, m_img, m_addTime) values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setInt(3, price);
			pstmt.setInt(4, stock);
			pstmt.setString(5, img);
			pstmt.setString(6, Date);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti(name + "를 추가했습니다.", "알림");
			menu_set_frame.setVisible(false);
			menu_Set_form_clean();
			menu_DB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void menu_del_DB(int num, String name) {
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "delete from menu where m_idx = ? and m_name = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, num);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti(num + "번 " + name + "를 삭제하였습니다.", "알림");
			menu_DB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void menu_edit_DB(String menu_name, String menu_type, int menu_price, int menu_stock, String menu_img,
			int menu_num) { // 메뉴 수정
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "update menu set m_name=?, m_type =?, m_price =?, m_stock =?, m_img =? where m_idx = ? ";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, menu_name);
			pstmt.setString(2, menu_type);
			pstmt.setInt(3, menu_price);
			pstmt.setInt(4, menu_stock);
			pstmt.setString(5, menu_img);
			pstmt.setInt(6, menu_num);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti(Integer.toString(menu_num) + "번 " + menu_name + "를 수정하였습니다.", "알림");
			menu_model.setNumRows(0);
			menu_model.addRow(new Object[] { menu_num, menu_name, menu_type, format.format(menu_price) + "원",
					menu_stock + "개", food_Img(menu_img, 80, 80) });
			menu_set_frame.setVisible(false);
			menu_Set_form_clean();
			System.out.println("수정되는 DB 이미지 링크 : " + menu_img);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("오류");
		}
	}

	void menu_Search_DB(String name, String type) { // 메뉴 검색
		String SQL;
		if (menu_search_count == 0 || type.equals("전체")) {
			menu_DB();
		} else {
			try {
				menu_model.setNumRows(0);
				conn = DriverManager.getConnection(url, id, pw);
				if (name.equals("")) {
					SQL = "select * from menu where m_type in " + "('" + type + "')";
					pstmt = conn.prepareStatement(SQL);
				} else {
					SQL = "select * from menu where m_type in ('" + type + "') and m_name like '%" + name + "%'";
					pstmt = conn.prepareStatement(SQL);
				}
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					menu_model.addRow(new Object[] { rs.getString("m_idx"), rs.getString("m_name"),
							rs.getString("m_type"), format.format(rs.getInt("m_price")) + "원",
							rs.getString("m_stock") + "개", food_Img(rs.getString("m_img"), 80, 80) });
				}
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// -------------------------------------------------------------------- 메뉴 DB
	// ---------------------------------------------------------------------------
	void user_form_clean() { // 초기화
		for (int i = 0; i < user_set_tb.length; i++) { // 입력 창 초기화
			set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			user_set_tb[i].setText(user_set_tb_arr[i]);
			user_set_tb[i].setEditable(true);
		}
		for (int i = 0; i < user_set_btn.length; i++) { // 버튼 선택 초기화
			set_Btn(user_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		}
		user_set_noti.setText("");
	}

	void user_form_tb_set(int n) {
		if (n == 0) { // 추가

		} else if (n == 1) { // 수정

		}
	}

	// ------------------------------------------------------------------ 좌석 관리
	// --------------------------------------------------------------------------
	String File_path; // 디렉토리 + 파일이름
	// --------------------------------- 파일
	String Extension; // 확장자
	String file_Dir;
	int file_Src_count = 0;

	void Open_File() {
		String Dir = System.getProperty("user.home");
		JFileChooser fc = new JFileChooser(Dir + "/Desktop"); // 기본 위치 바탕화면
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
		fc.addChoosableFileFilter(filter);

		int result = fc.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile(); // 디렉토리
			File_path = selectedFile.getAbsolutePath(); // 디렉토리 + 파일이름
			Extension = File_path.substring(File_path.length() - 3); // 확장자
			if (Extension.equals("jpg") || Extension.equals("png")) {
				menu_img.setIcon(food_Img(File_path, 218, 218));
			} else {
				Noti("잘못된 확장자 입니다.", "알림");
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
		}
	}

	void Copy_File(String file) { // 파일 복사
		try {
			File in_File = new File(File_path);
			File out_File = new File(file_Dir + file);
			Files.copy(in_File.toPath(), out_File.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		}
	}

	void Del_File(String file) {
		Path filePath = Paths.get(file);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
		}
	}

	String type_link(String type) {
		String link;
		if (type.equals("밥류")) {
			link = "Img\\Menu\\Rice\\";
		} else if (type.equals("면류")) {
			link = "Img\\Menu\\Noodle\\";
		} else if (type.equals("튀김류")) {
			link = "Img\\Menu\\Fried\\";
		} else if (type.equals("과자류")) {
			link = "Img\\Menu\\Snack\\";
		} else if (type.equals("음료류")) {
			link = "Img\\Menu\\Drink\\";
		} else {
			link = "Img\\Menu\\Other\\";
		}
		return link;
	}

	// -------------------------------------------------------------- 메뉴 이미지 처리
	// ---------------------------------------------------------------------------
	ImageIcon food_Img(String src, int x, int y) {
		ImageIcon icon = new ImageIcon(src);
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		return changeIcon;
	}

	void Noti(String value, String title) {
		JOptionPane.showMessageDialog(null, value, title, JOptionPane.PLAIN_MESSAGE);
	}

	void set_TextField(JTextField tb, Color color, int size, Color font) {
		Border border = BorderFactory.createLineBorder(color, 2);
		tb.setBorder(border);
		tb.setBorder(BorderFactory.createCompoundBorder(tb.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		tb.setFont(new Font(font_name, Font.PLAIN, size));
		tb.setForeground(font);
	}

	void set_Btn(JButton btn, int size, Color bg_color, Color font_color, Dimension dm) {
		btn.setBackground(bg_color);
		btn.setFont(new Font(font_name, Font.PLAIN, size));
		btn.setForeground(font_color);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setPreferredSize(dm);
	}

	void set_Font(JLabel lb, int size, Color color) {
		lb.setFont(new Font(font_name, Font.PLAIN, size));
		lb.setForeground(color);
		lb.setHorizontalAlignment(JLabel.CENTER);
	}

	void set_Place_lb(JLabel lb, int size, Color color) {
		lb.setFont(new Font(font_name, Font.PLAIN, size));
		lb.setForeground(color);
		lb.setHorizontalAlignment(JLabel.LEFT);
	}

	void set_Board(JPanel jp, Color color) {
		Border border = BorderFactory.createLineBorder(color, 2);
		jp.setBorder(border);
		jp.setBorder(BorderFactory.createCompoundBorder(jp.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	// ---------------------------------------------------------------------- 메서드
	// ---------------------------------------------------------------------------

	public Admin_Form() {
		admin_frame.setSize(1280, 720);
		admin_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		admin_frame.setLocationRelativeTo(null);
		admin_frame.setResizable(false);
		admin_frame.setLayout(null);

		// ------------------------------------------------------- 관리자 폼
		main_top_menu.setBounds(224, 0, 1044, 60);
		main_top_menu.setBackground(new Color(230, 230, 230));
		main_top_menu.setLayout(null);

		main_top_menu.add(logout_btn);
		set_Btn(logout_btn, 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(110, 40));
		logout_btn.setBounds(925, 10, 110, 40);

		main_top_btn_group.setBounds(200, 5, 644, 50);
		main_top_btn_group.setBackground(new Color(230, 230, 230));
		main_top_btn_group.setLayout(new FlowLayout());

		for (int i = 0; i < main_set_btn_arr.length; i++) {
			main_set_btn[i] = new JButton(main_set_btn_arr[i]);
			set_Btn(main_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(110, 40));
			// ----------------------------------------------
			aside_menu[i] = new JPanel();
			aside_menu[i].setBounds(0, 0, 244, 685);
			aside_menu[i].setBackground(new Color(220, 220, 220));
			aside_menu[i].setLayout(null);
			aside_menu[i].setVisible(false);
			// ----------------------------------------------
			main_title[i] = new JLabel(main_set_btn_arr[i]);
			set_Font(main_title[i], 25, new Color(0, 0, 0));
			main_title[i].setBounds(0, 0, 244, 60);
			aside_menu[i].add(main_title[i]);
			// ----------------------------------------------
			main_item_group[i] = new JPanel();
			main_item_group[i].setBounds(244, 60, 1024, 625);
			main_item_group[i].setLayout(null);
			main_item_group[i].setBackground(new Color(240, 240, 240));
			main_item_group[i].setVisible(false);
			// ----------------------------------------------
			main_top_btn_group.add(main_set_btn[i]);
			admin_frame.add(main_item_group[i]);
			admin_frame.add(aside_menu[i]);
		}

		aside_menu[0].setVisible(true);
		main_item_group[0].setVisible(true);

		set_Btn(main_set_btn[0], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(110, 40));

		for (int i = 0; i < main_set_btn_arr.length; i++) {
			int num = i;
			main_set_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					page_Sw(num);
				}
			});
		}

		// -------------------------------------------------------

		logout_btn.addMouseListener(new MouseAdapter() { // 삭제

			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					Main m = new Main();
					m.main_frame.setVisible(true);
					admin_frame.setVisible(false);
				} else {
					System.out.println("로그아웃 취소");
				}
			}
		});

		admin_frame.addWindowListener(new java.awt.event.WindowAdapter() { // 상위로 닫았을 때 초기화
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					Main m = new Main();
					m.main_frame.setVisible(true);
					admin_frame.setVisible(false);
				} else {
					System.out.println("로그아웃 취소");
				}
			}
		});

		// ------------------------------------------------------- 로그아웃
		place_count_group.setBounds(5, 60, 234, 45);
		place_count_group.setLayout(null);
		place_count_group.setBackground(Color.white);

		set_Board(place_count_group, new Color(0, 120, 215));

		place_count.setBounds(0, 0, 234, 45);
		set_Font(place_count, 30, new Color(0, 0, 0));

		// ------------------------------------------------------- 잔여 좌석
		order_frame.setSize(1100, 650);
		order_frame.setDefaultCloseOperation(order_frame.DISPOSE_ON_CLOSE);
		order_frame.setLocationRelativeTo(null);
		order_frame.setResizable(false);
		order_frame.setVisible(false);
		order_frame.setLayout(null);

		order_table.getTableHeader().setReorderingAllowed(false);
		order_table.getTableHeader().setResizingAllowed(false);

		order_scrollpane.getViewport().setBackground(Color.white);
		order_scrollpane.setBounds(0, 0, 1090, 565);

		order_btn_group.setBounds(0, 564, 1100, 80);

		order_chk_btn = new JButton("배달완료");
		set_Btn(order_chk_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(110, 40));
		order_del_btn = new JButton("주문삭제");
		set_Btn(order_del_btn, 15, new Color(238, 77, 77), new Color(255, 255, 255), new Dimension(110, 40));

		order_chk_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();
				if (row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					String place = String.valueOf(order_table.getValueAt(row, 1));
					int result = JOptionPane.showConfirmDialog(null, place + " 주문을 완료합니다.", "알림",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						Noti(place + "에 배달완료 되었습니다.", "알림");
					} else {
						Noti("완료가 취소되었습니다.", "알림");
					}
				}
			}
		});

		order_del_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();
				if (row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					String place = String.valueOf(order_table.getValueAt(row, 2));
					String time = String.valueOf(order_table.getValueAt(row, 7));
					int result = JOptionPane.showConfirmDialog(null, place + " 주문을 삭제합니다.", "알림",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						order_Del(place, time);
					} else {
						Noti("삭제가 취소되었습니다.", "알림");
						user_Set_btn_sw(5); // 더미
					}
				}
			}
		});

		order_table.addMouseListener(new MouseAdapter() { // 완료
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = order_table.getSelectedRow();
					TableModel data = order_table.getModel();

					String place = (String) data.getValueAt(row, 1);
					String name = (String) data.getValueAt(row, 2);
					String ord = (String) data.getValueAt(row, 3);
					String cost = (String) data.getValueAt(row, 4);
					String pay = (String) data.getValueAt(row, 5);
					String mag = (String) data.getValueAt(row, 6);

					Object[] arr = { "*좌석*", place + "(" + name + ")", "*주문목록*", ord, "*총 금액*", cost + "원", "*지불방식*",
							pay, "*요청사항*", mag };
					JOptionPane.showMessageDialog(order_frame, arr, place + " 주문내역", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		order_frame.add(order_scrollpane);
		order_frame.add(order_btn_group);
		order_btn_group.add(order_chk_btn);
		order_btn_group.add(order_del_btn);

		// ------------------------------------------------------- 주문 관리
		place_count_group.setBounds(5, 60, 234, 45);
		place_count_group.setLayout(null);
		place_count_group.setBackground(Color.white);

		set_Board(place_count_group, new Color(0, 120, 215));

		place_count.setBounds(0, 0, 234, 45);
		set_Font(place_count, 30, new Color(0, 0, 0));

		order_mgt.setBounds(5, 110, 234, 45);
		set_Btn(order_mgt, 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
		place_mgt.setBounds(5, 160, 234, 45);
		set_Btn(place_mgt, 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));

		for (int i = 0; i < place_set_arr.length; i++) {
			place_set_btn[i] = new JButton(place_set_arr[i]);
			set_Btn(place_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
			place_set_btn[i].setVisible(false);
			// ------------------------------------------------
			aside_menu[0].add(place_set_btn[i]);
		}

		place_set_btn[0].setBounds(5, 210, 234, 45);
		place_set_btn[1].setBounds(5, 260, 234, 45);
		place_set_btn[2].setBounds(5, 310, 234, 45);

		order_mgt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				set_Btn(order_mgt, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(110, 40));
				order_DB();
				order_frame.setVisible(true);
			}
		});

		place_mgt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				place_Set_btn_hide_show();
			}
		});

		main_item_group[0].setLayout(new FlowLayout(FlowLayout.LEFT, 10, 9));

		for (int i = 1; i < 105; i++) {
			Place_Frame[i] = new JPanel();
			Place_Frame[i].setLayout(null);
			Place_Frame[i].setPreferredSize(new Dimension(68, 68));
			Place_Frame[i].setBackground(new Color(240, 240, 240));
			// ------------------------------------------------------- 프레임
			Place[i] = new JPanel();
			Place[i].setLayout(null);
			Place[i].setBounds(0, 0, 68, 68);
			Place[i].setBackground(Color.white);
			set_Board(Place[i], new Color(64, 64, 64));
			// --------------------------------------------------------
			Place_num_line[i] = new JPanel();
			Place_num_line[i].setBackground(Color.DARK_GRAY);
			Place_num_line[i].setLayout(null);
			Place_num_line[i].setBounds(2, 2, 25, 15);

			Place_Num[i] = new JLabel(Integer.toString(i));
			set_Font(Place_Num[i], 12, new Color(255, 255, 255));
			Place_Num[i].setBounds(0, 0, 25, 15);

			Place_Order[i] = new JLabel(); // 주문 여부
			set_Place_lb(Place_Order[i], 12, new Color(192, 0, 0));
			Place_Order[i].setBounds(45, 2, 40, 15);
			Place_Order[i].setVisible(true);

			Place_Name[i] = new JLabel(); // 사용유저
			set_Place_lb(Place_Name[i], 12, new Color(0, 0, 0));
			Place_Name[i].setBounds(4, 17, 68, 15);
			Place_Name[i].setVisible(true);

			Place_Use_Time[i] = new JLabel(); // 남은시간
			set_Place_lb(Place_Use_Time[i], 12, new Color(0, 0, 0));
			Place_Use_Time[i].setBounds(4, 31, 68, 15);
			Place_Use_Time[i].setVisible(true);

			// -------------------------------------------------------- 좌석 번호
			Place_Frame[i].add(Place[i]);
			Place[i].add(Place_num_line[i]);
			Place_num_line[i].add(Place_Num[i]);

			Place[i].add(Place_Order[i]);
			Place[i].add(Place_Name[i]);
			Place[i].add(Place_Use_Time[i]);

			main_item_group[0].add(Place_Frame[i]);
		}

		for (int i = 1; i < Place_n; i++) {
			int n = i;
			Place_Frame[i].addMouseListener(new MouseAdapter() { // 추가
				public void mouseClicked(MouseEvent e) {
					if (place_add_del_count == 0) {
						Place[n].setVisible(true);
						place_add.add(Integer.toString(n));
						place_del.remove(Integer.toString(n));
						p_count++;
						place_count.setText(p_count + "/" + place_use_count);
					} else {
						System.out.println("x");
					}
				}
			});
		}

		for (int i = 1; i < Place_n; i++) {
			int n = i;
			Place[i].addMouseListener(new MouseAdapter() { // 삭제
				public void mouseClicked(MouseEvent e) {
					if (place_add_del_count == 1) {
						Place[n].setVisible(false);
						place_del.add(Integer.toString(n));
						place_add.remove(Integer.toString(n));
						p_count--;
						place_count.setText(p_count + "/" + place_use_count);
					} else {
						System.out.println("x");
					}
				}
			});
		}

		place_set_btn[0].addMouseListener(new MouseAdapter() { // 좌석설정-추가
			public void mouseClicked(MouseEvent e) {
				if (place_del.size() == 0) {
					Noti("추가 가능한 자리가 없습니다", "알림");
				} else {
					place_Set_btn_sw(0);
					place_add_del_count = 0;
				}
			}
		});

		place_set_btn[1].addMouseListener(new MouseAdapter() { // 좌석설정-삭제
			public void mouseClicked(MouseEvent e) {
				place_add_del_count = 1;
				place_Set_btn_sw(1);
			}
		});

		place_set_btn[2].addMouseListener(new MouseAdapter() { // 좌석설정-저장
			public void mouseClicked(MouseEvent e) {
				place_Set_btn_sw(2);
				if (place_add_del_count == 0) {
					if (place_add.size() == 0) {
						Noti("설정된 자리가 없습니다.", "알림");
						place_Set_btn_sw(4);
					} else {
						int result = JOptionPane.showConfirmDialog(null, place_add + " 번 자리를 추가합니다.", "알림",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							Noti(place_add + " 번 자리를 추가하였습니다.", "알림");
							place_add.clear();
							place_Set_btn_sw(4);
						} else {
							Noti("취소 되었습니다.", "알림");
						}
					}
				} else {
					if (place_del.size() == 0) {
						Noti("설정된 자리가 없습니다.", "알림");
						place_Set_btn_sw(4);
					} else {
						int result = JOptionPane.showConfirmDialog(null, place_del + " 번 자리를 삭제합니다.", "알림",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							Noti(place_del + " 번 자리를 삭제하였습니다.", "알림");
							place_Set_btn_sw(4);
						} else {
							Noti("취소 되었습니다.", "알림");
						}
					}
				}
			}
		});

		order_frame.addWindowListener(new java.awt.event.WindowAdapter() { // 상위로 닫았을 때 초기화
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				set_Btn(order_mgt, 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
			}
		});

		// -------------------------------------------------------
		user_search_group.setBounds(5, 60, 234, 35);
		user_search_group.setBackground(Color.pink);
		user_search_group.setLayout(null);

		user_search_tb.setBounds(0, 0, 199, 35);
		set_TextField(user_search_tb, new Color(240, 240, 240), 15, new Color(200, 200, 200));
		user_search_btn.setBounds(199, 0, 35, 35);
		set_Btn(user_search_btn, 15, new Color(240, 240, 240), new Color(255, 255, 255), new Dimension(35, 35));
		user_search_btn.setEnabled(false);

		user_search_group.add(user_search_tb);
		user_search_group.add(user_search_btn);

		// -------------------------------------------------------
		user_set_frame.setSize(301, 515);
		user_set_frame.setDefaultCloseOperation(user_set_frame.DISPOSE_ON_CLOSE);
		user_set_frame.setLocationRelativeTo(null);
		user_set_frame.setResizable(false);
		user_set_frame.setVisible(false);
		user_set_frame.setLayout(null);

		user_set_frame_bg.setBounds(0, 0, 292, 500);
		user_set_frame_bg.setLayout(null);
		user_set_frame_bg.setBackground(Color.white);

		user_set_group.setBounds(0, -10, 292, 500);
		user_set_group.setLayout(null);
		user_set_group.setBackground(Color.white);

		user_set_btn_group.setBounds(0, 500, 308, 50);
		user_set_btn_group.setBackground(Color.white);
		user_set_btn_group.setLayout(new FlowLayout());

		set_Btn(user_set_ok_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(125, 40));
		set_Btn(user_set_cen_btn, 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(125, 40));

		pw_edit.setBounds(145, 170, 130, 30);
		pw_edit.setBackground(Color.white);
		pw_edit.setFont(new Font(font_name, Font.PLAIN, 15));
		pw_edit.setVisible(false);

		for (int i = 0; i < user_set_arr.length; i++) {
			user_set_lb[i] = new JLabel(user_set_arr[i]);
			set_Font(user_set_lb[i], 18, new Color(0, 0, 0));
			user_set_lb[i].setHorizontalAlignment(JLabel.LEFT);
			// ------------------------------------------------
			user_set_group.add(user_set_lb[i]);
		}

		for (int i = 0; i < 9; i++) {
			user_set_tb[i] = new JTextField(user_set_tb_arr[i]);
			set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			user_set_tb[i].setDisabledTextColor(new Color(200, 200, 200));
			// ------------------------------------------------
			user_set_group.add(user_set_tb[i]);
		}

		user_set_lb[0].setBounds(0, 10, 292, 50); // 타이틀
		set_Font(user_set_lb[0], 25, new Color(0, 0, 0));

		user_set_lb[1].setBounds(16, 40, 248, 50); // 이름
		user_set_tb[0].setBounds(16, 80, 257, 30);

		user_set_lb[2].setBounds(16, 100, 248, 50); // 아이디
		user_set_tb[1].setBounds(16, 140, 257, 30);

		user_set_lb[3].setBounds(16, 160, 248, 50); // 비밀번호
		user_set_tb[2].setBounds(16, 200, 257, 30);

		pw_tb.setBounds(16, 200, 257, 30);
		set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		pw_tb.setEchoChar('*');
		pw_tb.setVisible(false);

		user_set_lb[4].setBounds(16, 220, 249, 50); // 비밀번호 확인
		user_set_tb[3].setBounds(16, 260, 257, 30);

		pw_chk_tb.setBounds(16, 260, 257, 30);
		set_TextField(pw_chk_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
		pw_chk_tb.setEchoChar('*');
		pw_chk_tb.setVisible(false);

		user_set_lb[5].setBounds(16, 280, 248, 50); // 핸드폰
		user_set_tb[4].setBounds(16, 320, 70, 30); // 010
		user_set_tb[4].setText("010");
		user_set_tb[4].setHorizontalAlignment(JLabel.CENTER);
		user_set_tb[4].setEnabled(false);
		user_set_tb[4].setEditable(false);

		user_set_lb[6].setBounds(94, 310, 248, 50); // -
		user_set_tb[5].setBounds(111, 320, 162, 30); // 번호

		user_set_lb[7].setBounds(16, 340, 248, 50); // 남은 시간
		user_set_tb[6].setBounds(16, 380, 60, 30); // 시
		user_set_lb[8].setBounds(82, 370, 248, 50);

		user_set_tb[7].setBounds(101, 380, 60, 30); // 분
		user_set_lb[9].setBounds(167, 370, 248, 50);

		user_set_tb[8].setBounds(187, 380, 60, 30); // 초
		user_set_lb[10].setBounds(253, 370, 248, 50);

		user_set_noti.setBounds(0, 405, 292, 40);
		set_Font(user_set_noti, 18, new Color(238, 77, 77));

		user_set_btn_group.setBounds(0, 436, 292, 60);

		user_set_frame.add(user_set_frame_bg);
		user_set_frame_bg.add(user_set_group);

		user_set_group.add(pw_edit);
		user_set_group.add(user_set_noti);
		user_set_group.add(user_set_btn_group);

		user_set_group.add(pw_tb);
		user_set_group.add(pw_chk_tb);

		user_set_btn_group.add(user_set_ok_btn);
		user_set_btn_group.add(user_set_cen_btn);

		for (int i = 0; i < user_btn_arr.length; i++) {
			user_set_btn[i] = new JButton(user_btn_arr[i]);
			set_Btn(user_set_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
			user_set_btn[i].setVisible(true);
			// ------------------------------------------------
			aside_menu[1].add(user_set_btn[i]);
		}

		user_set_btn[0].setBounds(5, 100, 234, 45);
		user_set_btn[1].setBounds(5, 150, 234, 45);
		user_set_btn[2].setBounds(5, 200, 234, 45);
		user_set_btn[3].setBounds(5, 250, 234, 45);
		user_set_btn[4].setBounds(5, 300, 234, 45);

		user_table.getTableHeader().setReorderingAllowed(false);
		user_table.getTableHeader().setResizingAllowed(false);

		user_scrollpane.getViewport().setBackground(Color.white);
		user_scrollpane.setBounds(0, 0, 1024, 625);

		user_select_row = user_table.getSelectedRow();

		user_set_btn[0].addMouseListener(new MouseAdapter() { // 회원관리-카드관리
			public void mouseClicked(MouseEvent e) {
				user_Set_btn_sw(0);
			}
		});

		user_set_btn[1].addMouseListener(new MouseAdapter() { // 회원관리-추가
			public void mouseClicked(MouseEvent e) {
				user_set_count = 0;
				user_Set_btn_sw(1);
				user_Set_clean();
				user_set_lb[0].setText("회원 추가");
				user_set_frame.setTitle("회원 추가");
				user_set_frame.setVisible(true);
			}
		});

		user_set_btn[2].addMouseListener(new MouseAdapter() { // 회원관리-수정
			public void mouseClicked(MouseEvent e) {

				user_select_row = user_table.getSelectedRow();
				if (user_select_row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					user_Set_btn_sw(2);
					user_set_lb[0].setText("회원 수정");
					user_set_frame.setTitle("회원 수정");

					for (int i = 0; i < 5; i++) { // 1~4번 비활성화
						user_set_tb[i].setEnabled(false);
						user_set_tb[i].setEditable(false);
					}

					user_idx_temp = Integer.parseInt(String.valueOf(user_table.getValueAt(user_select_row, 0)));
					user_join_temp = String.valueOf(user_table.getValueAt(user_select_row, 5));

					String user_name = String.valueOf(user_table.getValueAt(user_select_row, 1)); // 구분
					String user_cell = String.valueOf(user_table.getValueAt(user_select_row, 3)).substring(3, 11); // 휴대폰

					String user_left_time = String.valueOf(user_table.getValueAt(user_select_row, 4)); // 시간
					String time[] = user_left_time.split("시 |분 |초"); // 시, 분, 초로 구분

					user_set_tb[0].setText(user_name); // 이름
					user_set_tb[1].setText(String.valueOf(user_table.getValueAt(user_select_row, 2))); // 아이디
					user_set_tb[2].setText("변경 불가"); // 비밀번호
					user_set_tb[3].setText("변경 불가"); // 비밀번호 확인
					user_set_tb[5].setText(user_cell); // 휴대폰 번호
					user_set_tb[6].setText(time[0]); // 시
					user_set_tb[7].setText(time[1]); // 분
					user_set_tb[8].setText(time[2]); // 초

					if (!user_name.equals("관리자")) { // 일반 유저
						user_set_count = 1;
						user_set_tb[0].setEnabled(true);
						user_set_tb[0].setEditable(true);
						user_set_frame.setVisible(true);
					} else { // 관리자 일때
						String admin_salt = "";
						String admin_pw = "";
						try {
							conn = DriverManager.getConnection(url, id, pw);
							String SQL = "Select * from member where m_id=?";
							pstmt = conn.prepareStatement(SQL);
							pstmt.setString(1, "admin");
							ResultSet rs = pstmt.executeQuery();
							if (rs.next()) {
								admin_salt = rs.getString("m_salt");
								admin_pw = rs.getString("m_pw");
							}
							pstmt.close();
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						// ------------------------------------
						JPasswordField pw = new JPasswordField();

						Object[] arr = { "관리자 암호", pw };

						int result = JOptionPane.showConfirmDialog(admin_frame, arr, "관리자 변경",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							String get_pw = new String(pw.getPassword());
							if (admin_pw.equals(sha(admin_salt + get_pw))) {
								for (int i = 6; i < 9; i++) { // 1~4번 비활성화
									user_set_tb[i].setEnabled(false);
									user_set_tb[i].setEditable(false);
								}
								user_set_count = 2;
								pw_edit.setVisible(true);
								user_set_frame.setVisible(true);
							} else {
								Noti("암호가 일치하지 않습니다.", "알림");
								System.out.println(get_pw);
							}
						} else {
							Noti("취소 되었습니다.", "알림");
						}
					}
				}
			}
		});

		pw_edit.addItemListener((ItemListener) new ItemListener() { // 관리자 비밀번호 변경 체크
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					user_set_count = 3;
					for (int i = 2; i < 4; i++) { // 변경 가능
						user_set_tb[i].setEnabled(true);
						user_set_tb[i].setEditable(true);
						user_set_tb[i].setText("");
					}
					user_set_tb[2].setVisible(false);
					user_set_tb[3].setVisible(false);
					pw_tb.setVisible(true);
					pw_chk_tb.setVisible(true);
				} else {
					user_set_count = 2;
					for (int i = 2; i < 4; i++) { // 변경 불가능
						user_set_tb[i].setEnabled(false);
						user_set_tb[i].setEditable(false);
						user_set_tb[i].setText("변경 불가");
						set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
					}
				}
			}
		});

		user_set_ok_btn.addMouseListener(new MouseAdapter() { // 회원관리 - 확인
			public void mouseClicked(MouseEvent e) {

				String user_name = user_set_tb[0].getText(); // 이름
				String user_id = user_set_tb[1].getText(); // 아이디
				String user_pw = user_set_tb[2].getText(); // 비밀번호
				String user_pw_chk = user_set_tb[3].getText(); // 비밀번호 확인
				String user_cell = user_set_tb[5].getText(); // 휴대폰 번호
				int[] time = new int[3];

				if (user_set_count == 0) { // 추가
					if (user_name.equals("") || user_name.equals("이름") || user_name.length() > 10) {
						user_set_noti.setText("이름을 정확하게 입력하세요.");
					} else if (user_id.equals("") || user_id.equals("아이디") || user_id.length() > 10
							|| 4 > user_id.length()) {
						user_set_noti.setText("아이디를 정확하게 입력하세요.");
					} else if (user_pw.equals("") || user_pw.equals("비밀번호") || user_pw.length() > 10
							|| 4 > user_pw.length()) {
						user_set_noti.setText("비밀번호를 정확하게 입력하세요.");
					} else if (user_cell.equals("") || user_cell.length() != 8) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else if (user_cell != null && user_cell.matches("[-+]?\\d*\\.?\\d+") == false) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else {
						try {
							time[0] = Integer.parseInt(user_set_tb[6].getText());
							time[1] = Integer.parseInt(user_set_tb[7].getText());
							time[2] = Integer.parseInt(user_set_tb[8].getText());
							if (!user_pw.equals(user_pw_chk)) {
								user_set_noti.setText("비밀번호가 일치하지 않습니다.");
							} else {
								int result = JOptionPane.showConfirmDialog(null,
										user_name + "(" + user_id + ")회원을 추가하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
										JOptionPane.PLAIN_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									String salt = salt_temp();
									String new_pw = sha(salt + user_pw);
									int total_time = (time[0] * 3600) + (time[1] * 60) + time[2];
									user_Reg_chk_DB(user_name, user_id, salt, new_pw, ("010" + user_cell), total_time,
											Date(0));
								} else {
									System.out.println("추가취소");
								}
							}
						} catch (NumberFormatException num) {
							user_set_noti.setText("시간을 정확하게 입력하세요.");
						}
					}
				} else if (user_set_count == 1) { // 회원 수정
					if (user_name.equals("") || user_name.equals("이름") || user_name.length() > 10) {
						user_set_noti.setText("이름을 정확하게 입력하세요.");
					} else if (user_cell.equals("") || user_cell.length() != 8) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else if (user_cell != null && user_cell.matches("[-+]?\\d*\\.?\\d+") == false) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else {
						try {
							time[0] = Integer.parseInt(user_set_tb[6].getText());
							time[1] = Integer.parseInt(user_set_tb[7].getText());
							time[2] = Integer.parseInt(user_set_tb[8].getText());
							int total_time = (time[0] * 3600) + (time[1] * 60) + time[2];

							int result = JOptionPane.showConfirmDialog(null,
									user_name + "(" + user_id + ")회원을 수정하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
									JOptionPane.PLAIN_MESSAGE);
							if (result == JOptionPane.YES_OPTION) {
								user_Edit_chk_DB(user_idx_temp, user_name, "", "", ("010" + user_cell), total_time,
										user_id);
							} else {
								System.out.println("수정 취소");
							}
						} catch (NumberFormatException num) {
							user_set_noti.setText("시간을 정확하게 입력하세요.");
						}
					}
				} else if (user_set_count == 2) { // 관리자 휴대폰 번호만 수정
					if (user_cell.equals("") || user_cell.length() != 8) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else if (user_cell != null && user_cell.matches("[-+]?\\d*\\.?\\d+") == false) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else {
						int result = JOptionPane.showConfirmDialog(null, user_name + "(" + user_id + ")를 수정하시겠습니까?",
								"알림", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							user_Edit_chk_DB(user_idx_temp, user_name, admin_pw, user_pw, ("010" + user_cell), 0,
									user_id);
							System.out.println(user_cell);
						} else {
							System.out.println("수정 취소");
						}
					}
				} else if (user_set_count == 3) { // 관리자 비밀번호도 수정
					String get_pw = new String(pw_tb.getPassword());
					String get_chk_pw = new String(pw_chk_tb.getPassword());
					if (get_pw.equals("") || get_pw.length() > 10 || 4 > get_pw.length()) {
						user_set_noti.setText("비밀번호를 정확하게 입력하세요.");
					} else if (user_cell.equals("") || user_cell.length() != 8) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else if (user_cell != null && user_cell.matches("[-+]?\\d*\\.?\\d+") == false) {
						user_set_noti.setText("휴대폰 번호를 정확하게 입력하세요.");
					} else {
						if (!get_pw.equals(get_chk_pw)) {
							user_set_noti.setText("비밀번호가 일치하지 않습니다.");
						} else {
							String salt = salt_temp();
							String new_pw = sha(salt + get_pw);
							int result = JOptionPane.showConfirmDialog(null, user_name + "(" + user_id + ")를 수정하시겠습니까?",
									"알림", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
							if (result == JOptionPane.YES_OPTION) {
								user_Edit_chk_DB(user_idx_temp, user_name, salt, new_pw, ("010" + user_cell), 0,
										user_id);
							} else {
								System.out.println("수정 취소");
							}
						}
					}
				}
			}
		});

		user_set_cen_btn.addMouseListener(new MouseAdapter() { // 회원관리 - 취소
			public void mouseClicked(MouseEvent e) {
				user_set_frame.dispose();
				user_Set_clean();
			}
		});

		user_set_frame.addWindowListener(new java.awt.event.WindowAdapter() { // 회원관리 - 취소
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				user_Set_clean();
			}
		});

		user_set_btn[3].addMouseListener(new MouseAdapter() { // 회원관리-삭제
			public void mouseClicked(MouseEvent e) {
				user_Set_btn_sw(3);
				user_select_row = user_table.getSelectedRow();
				String user_name = String.valueOf(user_table.getValueAt(user_select_row, 1)); // 이름
				String user_id = String.valueOf(user_table.getValueAt(user_select_row, 2)); // 아이디
				String user_cell = String.valueOf(user_table.getValueAt(user_select_row, 4)); // 휴대폰 번호

				int result = JOptionPane.showConfirmDialog(null, user_name + "(" + user_id + ") 회원을 제거 하시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					if (user_id.equals("admin")) {
						Noti("관리자는 제거가 불가능합니다.", "알림");
					} else {
						user_DB_del(user_id, user_name, user_cell);
					}
				} else {
					System.out.println("삭제 취소");
				}
				user_Set_clean();
			}
		});

		user_set_btn[4].addMouseListener(new MouseAdapter() { // 회원관리-초기화
			public void mouseClicked(MouseEvent e) {
				user_Set_btn_sw(4);
				int result = JOptionPane.showConfirmDialog(null, "회원 정보 테이블을 초기화합니다.", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					user_DB();
					Noti("회원 정보 테이블이 초기화되었습니다.", "알림");
				} else {
					Noti("회원 정보 테이블 초기화가 취소되었습니다.", "알림");
				}
				user_Set_clean();
			}
		});

		user_search_tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (user_search_tb.getText().equals("검색")) {
					user_search_tb.setText("");
				}
				set_Btn(user_search_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(35, 35));
				set_TextField(user_search_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
				user_search_btn.setEnabled(true);
			}
		});

		user_search_btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (user_search_tb.getText().equals("")) {
					Noti("검색어를 입력해주세요.", "알림");
				} else {
					user_Search_DB(user_search_tb.getText());
				}
			}
		});

		for (int i = 0; i < user_set_tb.length; i++) {
			int n = i;
			user_set_tb[n].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < user_set_tb.length; i++) {
						set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
					}

					set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
					set_TextField(pw_chk_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));

					user_set_tb[n].requestFocus();

					if (user_set_count == 0) { // 추가 일때
						if (user_set_tb[n].getText().equals("이름") || user_set_tb[n].getText().equals("아이디")
								|| user_set_tb[n].getText().equals("휴대폰 번호") || user_set_tb[n].getText().equals("0")) {
							user_set_tb[n].setText("");
						}
						if (n != 4) {
							set_TextField(user_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
						}
						if (n == 2 || n == 3) {
							user_set_tb[n].setVisible(false);
							if (n == 2) {
								pw_tb.setVisible(true);
								set_TextField(pw_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
							} else {
								pw_chk_tb.setVisible(true);
								set_TextField(pw_chk_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
							}
						}
					} else if (user_set_count == 1) { // 수정 일때
						if (n == 0 || n == 5 || n == 6 || n == 7 || n == 8) {
							set_TextField(user_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
						}
					} else if (user_set_count == 2) { // 관리자 수정 일때(휴대폰 번호만 변경 상태)
						if (n == 5) {
							set_TextField(user_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
						}
					} else if (user_set_count == 3) { // 관리자 수정 일때(비밀번호 변경가능 상태)
						if (n == 2 || n == 3 || n == 5) {
							set_TextField(user_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
						}
					}
				}
			});
		}

		pw_tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (int i = 0; i < user_set_tb.length; i++) {
					set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
				}
				set_TextField(pw_chk_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
				set_TextField(pw_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));

				pw_tb.requestFocus();
			}
		});

		pw_chk_tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (int i = 0; i < user_set_tb.length; i++) {
					set_TextField(user_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
				}
				set_TextField(pw_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
				set_TextField(pw_chk_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));

				pw_chk_tb.requestFocus();
			}
		});

		main_item_group[1].add(user_scrollpane);

		// ------------------------------------------------------- 회원 관리
		menu_set_frame.setSize(485, 309); // 폼의 크기
		menu_set_frame.setDefaultCloseOperation(menu_set_frame.DISPOSE_ON_CLOSE); // 프로세스 종료
		menu_set_frame.setLocationRelativeTo(null); // 폼 가운데 배치
		menu_set_frame.setResizable(false);// 창크기 조절 불가
		menu_set_frame.setVisible(false);// 폼을 보여줌

		menu_set_frame_bg.setBounds(0, 0, 485, 309);
		menu_set_frame_bg.setLayout(null);
		menu_set_frame_bg.setBackground(Color.white);

		menu_img_frame.setBackground(new Color(240, 240, 240));
		menu_img_frame.setLayout(null);
		menu_img_frame.setBounds(5, 5, 218, 218);

		menu_img.setBounds(0, 0, 218, 218);

		menu_btn_group.setBounds(0, 223, 485, 50);
		menu_btn_group.setLayout(new FlowLayout());
		menu_btn_group.setBackground(Color.white);

		for (int i = 0; i < menu_set_arr.length; i++) {
			menu_set_lb[i] = new JLabel(menu_set_arr[i]);
			menu_set_lb[i].setFont(new Font(font_name, Font.PLAIN, 18));
			menu_set_frame_bg.add(menu_set_lb[i]);
		}

		for (int i = 0; i < menu_set_tb.length; i++) {
			menu_set_tb[i] = new JTextField(menu_set_tb_arr[i]);
			set_TextField(menu_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
			menu_set_frame_bg.add(menu_set_tb[i]);
		}

		for (int i = 0; i < menu_set_btn.length; i++) {
			menu_set_btn[i] = new JButton(menu_set_btn_arr[i]);
		}

		menu_set_btn[0].setBounds(0, 0, 218, 218);
		set_Btn(menu_set_btn[0], 15, new Color(240, 240, 240), new Color(0, 0, 0), new Dimension(218, 218));
		menu_set_btn[0].setBorderPainted(false);
		menu_set_btn[0].setFocusPainted(false);
		menu_set_btn[0].setContentAreaFilled(false);

		set_Btn(menu_set_btn[1], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(125, 40));
		set_Btn(menu_set_btn[2], 15, new Color(220, 0, 0), new Color(255, 255, 255), new Dimension(125, 40));

		menu_set_lb[0].setBounds(228, 0, 234, 30); // 품명
		menu_set_tb[0].setBounds(228, 25, 234, 30);

		menu_set_lb[1].setBounds(228, 55, 234, 30); // 종류
		menu_set_combo.setBounds(228, 80, 234, 30);
		menu_set_combo.setFont(new Font(font_name, Font.PLAIN, 15));

		menu_set_lb[2].setBounds(228, 110, 234, 30); // 가격
		menu_set_tb[1].setBounds(228, 135, 100, 30);
		menu_set_lb[3].setBounds(333, 135, 234, 30); // 원

		menu_set_lb[4].setBounds(228, 165, 234, 30); // 재고
		menu_set_tb[2].setBounds(228, 190, 100, 30);
		menu_set_lb[5].setBounds(333, 190, 234, 30); // 개

		// -------------------------------------------------------- 재고 관리 프레임

		menu_set_btn[0].addMouseListener(new MouseAdapter() { // 추가, 수정 폼 이미지 선택
			public void mouseClicked(MouseEvent e) {
				Open_File();
			}
		});

		menu_set_btn[1].addMouseListener(new MouseAdapter() { // 메뉴 설정 확인
			public void mouseClicked(MouseEvent e) {
				String menu_name = menu_set_tb[0].getText(); // 이름
				String menu_price = menu_set_tb[1].getText(); // 가격
				String menu_type = menu_set_combo.getSelectedItem().toString(); // 타입
				String menu_stock = menu_set_tb[2].getText(); // 재고
				String file_src = ""; // 이미지 파일 링크

				if (menu_type.equals("밥류")) {
					file_src = "Img\\Menu\\Rice\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Rice\\";
				} else if (menu_type.equals("면류")) {
					file_src = "Img\\Menu\\Noodle\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Noodle\\";
				} else if (menu_type.equals("튀김류")) {
					file_src = "Img\\Menu\\Fried\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Fried\\";
				} else if (menu_type.equals("과자류")) {
					file_src = "Img\\Menu\\Snack\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Snack\\";
				} else if (menu_type.equals("음료류")) {
					file_src = "Img\\Menu\\Drink\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Drink\\";
				} else {
					file_src = "Img\\Menu\\Other\\";
					file_Dir = System.getProperty("user.dir") + "\\Img\\Menu\\Other\\";
				}

				if (menu_name.equals("") || menu_name.equals("품명")) {
					Noti("품명을 정확하게 입력해주세요", "알림");
				} else if (menu_price.equals("") || menu_price.equals("가격")) {
					Noti("가격을 정확하게 입력해주세요.", "알림");
				} else if (menu_price != null && menu_price.matches("[-+]?\\d*\\.?\\d+") == false) {
					Noti("가격은 숫자만 입력가능합니다.", "알림");
				} else if (menu_stock.equals("") || menu_stock.equals("재고")) {
					Noti("재고을 정확하게 입력해주세요.", "알림");
				} else if (menu_stock != null && menu_stock.matches("[-+]?\\d*\\.?\\d+") == false) {
					Noti("재고는 숫자만 입력가능합니다.", "알림");
				} else {
					if (menu_set_count == 0) { // 메뉴 추가
						int result = JOptionPane.showConfirmDialog(null, menu_name + "를 추가합니다.", "알림",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							if (File_path == null) {
								menu_add_DB(menu_name, menu_type, Integer.parseInt(menu_stock),
										Integer.parseInt(menu_price), "", Date(0));
							} else {
								String file_name = menu_name + "_" + Date(1) + Extension;
								Copy_File(file_name);
								menu_add_DB(menu_name, menu_type, Integer.parseInt(menu_stock),
										Integer.parseInt(menu_price), file_src + file_name, Date(0));
							}
						} else {
							Noti("생성이 취소되었습니다.", "알림");
						}
					} else if (menu_set_count == 1) { // 메뉴 수정
						int result = JOptionPane.showConfirmDialog(null, menu_name + "를 수정합니다.", "알림",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							if (File_path == null) {
								File_path = menu_Img_link.get(row);
								Extension = "jpg";
							}
							String file_name = menu_name + "_" + Date(1) + Extension;
							Copy_File(file_name);
							Del_File(menu_Img_link.get(row));
							menu_Img_link.set(row, file_src + file_name);
							File_path = menu_Img_link.get(row);
							System.out.println("새로운 이미지 위치 : " + menu_Img_link.get(row));
							menu_edit_DB(menu_name, menu_type, Integer.parseInt(menu_price),
									Integer.parseInt(menu_stock), file_src + file_name, row);
						} else {
							Noti("수정이 취소되었습니다.", "알림");
						}
					}
				}
			}
		});

		menu_set_btn[2].addMouseListener(new MouseAdapter() { // 메뉴 설정 취소
			public void mouseClicked(MouseEvent e) {
				menu_set_frame.setVisible(false);
				menu_Set_form_clean();
			}
		});

		for (int i = 0; i < menu_set_tb.length; i++) {
			int n = i;
			menu_set_tb[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < menu_set_tb.length; i++) {
						set_TextField(menu_set_tb[i], new Color(200, 200, 200), 15, new Color(200, 200, 200));
					}
					set_TextField(menu_set_tb[n], new Color(0, 120, 215), 15, new Color(0, 0, 0));
					if (menu_set_tb[n].getText().equals("품명") || menu_set_tb[n].getText().equals("가격")
							|| menu_set_tb[n].getText().equals("재고")) {
						menu_set_tb[n].setText("");
					}
				}
			});
		}

		// -------------------------------------------------------- 버튼
		menu_set_frame.add(menu_set_frame_bg);
		menu_set_frame_bg.add(menu_btn_group);
		menu_set_frame_bg.add(menu_set_combo);
		menu_set_frame_bg.add(menu_img_frame);
		menu_img_frame.add(menu_img);
		menu_img_frame.add(menu_set_btn[0]);
		menu_btn_group.add(menu_set_btn[1]);
		menu_btn_group.add(menu_set_btn[2]);

		// --------------------------------------------------------
		menu_search_group.setBounds(75, 60, 164, 35);
		menu_search_group.setLayout(null);

		menu_search_tb.setBounds(0, 0, 129, 35);
		set_TextField(menu_search_tb, new Color(240, 240, 240), 15, new Color(200, 200, 200));
		menu_search_btn.setBounds(129, 0, 35, 35);
		set_Btn(menu_search_btn, 15, new Color(240, 240, 240), new Color(255, 255, 255), new Dimension(35, 35));
		menu_search_btn.setEnabled(false);
		// ------------------------------------------------------- 메뉴 검색

		menu_search_combo.setBounds(5, 60, 65, 35);
		menu_search_combo.setFont(new Font(font_name, Font.PLAIN, 15));

		for (int i = 0; i < menu_btn_arr.length; i++) {
			menu_btn[i] = new JButton(menu_btn_arr[i]);
			set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(214, 45));
			// ------------------------------------------------
			aside_menu[2].add(menu_btn[i]);
		}
		menu_btn[0].setBounds(5, 100, 234, 45);
		menu_btn[1].setBounds(5, 150, 234, 45);
		menu_btn[2].setBounds(5, 200, 234, 45);
		menu_btn[3].setBounds(5, 250, 234, 45);

		// ------------------------------------------------------
		menu_table.setRowHeight(80);

		menu_table.getTableHeader().setReorderingAllowed(false);
		menu_table.getTableHeader().setResizingAllowed(false);

		menu_scrollpane.getViewport().setBackground(Color.white);
		menu_scrollpane.setBounds(0, 0, 1024, 625);

		// -------------------------------------------------------
		menu_search_tb.addMouseListener(new MouseAdapter() { // 검색 입력
			public void mouseClicked(MouseEvent e) {
				if (menu_search_tb.getText().equals("검색")) {
					menu_search_tb.setText("");
				}
				set_Btn(menu_search_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(35, 35));
				set_TextField(menu_search_tb, new Color(0, 120, 215), 15, new Color(0, 0, 0));
				menu_search_btn.setEnabled(true);
			}
		});

		menu_search_btn.addMouseListener(new MouseAdapter() { // 검색 버튼
			public void mouseClicked(MouseEvent e) {
				menu_search_count = 1;
				String name = menu_search_tb.getText();
				String type = menu_search_combo.getSelectedItem().toString();
				menu_Search_DB(name, type);
			}
		});

		// ------------------------------------------------------- 검색 버튼
		menu_btn[0].addMouseListener(new MouseAdapter() { // 메뉴추가
			public void mouseClicked(MouseEvent e) {
				menu_btn_sw(0);
				menu_set_count = 0;
				menu_set_frame.setTitle("메뉴추가");
				menu_set_frame.setVisible(true);
			}
		});

		menu_btn[1].addMouseListener(new MouseAdapter() { // 메뉴수정
			public void mouseClicked(MouseEvent e) {
				menu_select_row = menu_table.getSelectedRow();
				if (menu_select_row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					menu_btn_sw(1);
					menu_set_count = 1;
					menu_set_frame.setTitle("메뉴수정");
					row = Integer.parseInt(String.valueOf(menu_table.getValueAt(menu_select_row, 0)));
					String name = String.valueOf(menu_table.getValueAt(menu_select_row, 1));
					String type = String.valueOf(menu_table.getValueAt(menu_select_row, 2));
					String price = String.valueOf(menu_table.getValueAt(menu_select_row, 3));
					String stock = String.valueOf(menu_table.getValueAt(menu_select_row, 4));

					menu_img.setIcon(food_Img(menu_Img_link.get(row), 218, 218));
					menu_set_tb[0].setText(name);
					menu_set_combo.setSelectedIndex(menu_Combo(type));
					menu_set_tb[1].setText(price.replaceAll("[^0-9]", ""));
					menu_set_tb[2].setText(stock.replaceAll("[^0-9]", ""));
					menu_set_frame.setVisible(true);
					System.out.println("선택 이미지 위치 : " + menu_Img_link.get(row));
				}
			}
		});

		menu_btn[2].addMouseListener(new MouseAdapter() { // 메뉴삭제
			public void mouseClicked(MouseEvent e) {
				menu_select_row = menu_table.getSelectedRow();
				if (menu_select_row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					menu_btn_sw(2);
					row = Integer.parseInt(String.valueOf(menu_table.getValueAt(menu_select_row, 0)));
					String name = String.valueOf(menu_table.getValueAt(menu_select_row, 1));
					int result = JOptionPane.showConfirmDialog(null, row + "번 " + name + "을 삭제합니다.", "알림",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						Del_File(menu_Img_link.get(row));
						menu_del_DB(row, name);
					} else {
						Noti("삭제가 취소되었습니다.", "알림");
					}
					menu_Set_form_clean();
				}
			}
		});

		menu_btn[3].addMouseListener(new MouseAdapter() { // 초기화
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "테이블을 초기화합니다.", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					menu_DB();
					Noti("초기화되었습니다.", "알림");
					set_Btn(menu_search_btn, 15, new Color(200, 200, 200), new Color(200, 200, 200),
							new Dimension(35, 35));
					set_TextField(menu_search_tb, new Color(200, 200, 200), 15, new Color(200, 200, 200));
					menu_search_tb.setText("검색");
					menu_search_btn.setEnabled(false);
					menu_search_combo.setSelectedItem("전체");
				} else {
					Noti("초기화가 취소되었습니다.", "알림");
				}
			}
		});

		menu_set_frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				menu_Set_form_clean();
			}
		});

		// -------------------------------------------------------
		menu_search_group.add(menu_search_tb);
		menu_search_group.add(menu_search_btn);
		aside_menu[2].add(menu_search_combo);
		aside_menu[2].add(menu_search_group);
		main_item_group[2].add(menu_scrollpane);

		// -------------------------------------------------------
		place_count_group.add(place_count);
		aside_menu[0].add(order_mgt);
		aside_menu[0].add(place_mgt);
		aside_menu[0].add(place_count_group);
		admin_frame.add(main_top_menu);
		main_top_menu.add(main_top_btn_group);
		aside_menu[1].add(user_search_group);
		// -------------------------------------------------------
	}
}