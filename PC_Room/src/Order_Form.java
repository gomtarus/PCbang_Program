import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Order_Form extends DAO {
	DecimalFormat format = new DecimalFormat("#,###");
	String font_name = "배달의민족 주아";

	String user_name;
	String user_id;
	String place;

	int order_count = 0;

	// ---------------------------------------------- 설정 관련
	ArrayList<String> menu_name_array = new ArrayList<String>(); // 이름
	ArrayList<String> menu_price_array = new ArrayList<String>(); // 가격
	ArrayList<String> menu_type_array = new ArrayList<String>(); // 타입
	ArrayList<String> menu_img_array = new ArrayList<String>(); // 이미지

	// ---------------------------------------------- 메인 메뉴 배열
	ArrayList<String> search_name = new ArrayList<String>(); // 이름
	ArrayList<String> search_price = new ArrayList<String>(); // 가격
	ArrayList<String> search_img = new ArrayList<String>(); // 이미지

	// ---------------------------------------------- 검색 메뉴 배열
	LinkedHashMap<String, Integer> table_list = new LinkedHashMap();
	ArrayList<String> table_list_temp = new ArrayList<String>(); // 순서 추출용

	// ---------------------------------------------- 메뉴
	JFrame order_main = new JFrame("상품 주문");

	// ---------------------------------------------- 메인 프레임
	JPanel top_menu = new JPanel();
	JPanel menu_btn_group = new JPanel();

	String menu_btn_arr[] = { "밥류", "면류", "튀김류", "과자류", "음료류", "기타" };
	JButton menu_btn[] = new JButton[menu_btn_arr.length];
	JPanel menu_item_group[] = new JPanel[menu_btn.length + 1];// 메뉴 + 검색창 한개 추가

	JPanel order_search_group = new JPanel(); // 검색 창
	JTextField order_search_tb = new JTextField("검색");
	JButton order_search_btn = new JButton(reSize_Img("Img/Src/search_icon.png", 35, 35));

	// ---------------------------------------------- 메뉴 영역
	JPanel order_list_main = new JPanel();

	String order_list_lb_arr[] = { "", "", "주문 목록", "총 금액 : 0원", "결제방식", "요청사항" };
	JLabel order_list_lb[] = new JLabel[order_list_lb_arr.length];

	// ---------------------------------------------- 기본 틀
	String order_row[] = { "이름", "수량", "가격" };
	DefaultTableModel order_model = new DefaultTableModel(order_row, 0) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	JTable order_table = new JTable(order_model);
	JScrollPane order_scrollpane = new JScrollPane(order_table);

	// ---------------------------------------------- 주문 리스트 테이블
	int order_total = 0;

	JPanel order_calc_group = new JPanel();
	JLabel order_calc_count = new JLabel();
	JButton order_calc_plus = new JButton("+");
	JButton order_clac_minus = new JButton("-");

	// ---------------------------------------------- 주문 목록
	ButtonGroup order_cost_type_1 = new ButtonGroup(); // 현금 & 카드
	ButtonGroup order_cost_type_2 = new ButtonGroup(); // 선불 & 후불

	String order_radio_arr[] = { "현금", "카드", "선불", "후불", "", "" };
	JRadioButton[] order_radio_btn = new JRadioButton[order_radio_arr.length];

	String order_radio_1 = "";
	String order_radio_2 = "";

	// ---------------------------------------------- 결제방식 선택
	JTextArea order_send_text = new JTextArea();

	// ---------------------------------------------- 요청 사항
	String order_btn_arr[] = { "삭제", "초기화", "주문" };
	JButton order_btn[] = new JButton[order_btn_arr.length];
	JPanel order_btn_group = new JPanel();

	// ---------------------------------------------- 삭제, 초기화, 주문 버튼
	JFrame order_chk_main = new JFrame("주문내역");
	JButton order_list_btn = new JButton("주문내역");

	String order_chk_row[] = { "주문순서", "주문목록", "총 금액", "지불방식", "요청사항", "주문시간", "상태" };
	DefaultTableModel order_chk_model = new DefaultTableModel(order_chk_row, 0) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	JPanel order_chk_btn_group = new JPanel();
	JButton order_canel_btn = new JButton();

	JTable order_chk_table = new JTable(order_chk_model);
	JScrollPane order_chk_scrollpane = new JScrollPane(order_chk_table);

	// ----------------------------------------------
	String order_Date() {
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));
		return formatedNow;
	}

	void Noti(String value, String title) {
		JOptionPane.showMessageDialog(null, value, title, JOptionPane.PLAIN_MESSAGE);
	}

	ImageIcon reSize_Img(String src, int x, int y) {
		ImageIcon icon = new ImageIcon(src);
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		return changeIcon;
	}

	void set_Border(JTextField tb, Color color) {
		Border border = BorderFactory.createLineBorder(color, 2);
		tb.setBorder(border);
		tb.setBorder(BorderFactory.createCompoundBorder(tb.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	void set_Font(JLabel lb, int size, Color color, int horizon) {
		lb.setFont(new Font(font_name, Font.PLAIN, size));
		lb.setForeground(color);
		lb.setHorizontalAlignment(horizon);
	}

	void menu_layout(JPanel pa) {
		pa.setBounds(0, 60, 935, 625);
		pa.setLayout(new FlowLayout(FlowLayout.LEFT));
		pa.setBackground(new Color(240, 240, 240));
	}

	void set_Btn(JButton btn, int size, Color bg_color, Color font_color, Dimension dm) {
		btn.setBackground(bg_color);
		btn.setFont(new Font(font_name, Font.PLAIN, 15));
		btn.setForeground(font_color);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setPreferredSize(dm);
	}

	void set_TextField(JTextField tb, Color br_line, Color font_color) {
		Border border = BorderFactory.createLineBorder(br_line, 2);
		tb.setBorder(border);
		tb.setBorder(BorderFactory.createCompoundBorder(tb.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		tb.setFont(new Font(font_name, Font.PLAIN, 15));
		tb.setForeground(font_color);
	}

	void table_Center(JTable tb, int count) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	// ---------------------------------------------- UI설정
	void order_clean() { // 주문 초기화
		order_total = 0;
		order_model.setNumRows(0);
		table_list.clear();
		table_list_temp.clear();
		order_send_text.setText("");
		order_radio_btn[5].setSelected(true);
		order_radio_btn[4].setSelected(true);
		order_radio_btn[2].setEnabled(false);
		order_radio_btn[3].setEnabled(false);
		order_calc_count.setText("0");
		order_list_lb[3].setText("총 금액 : " + format.format(order_total) + "원");
	}

	// ---------------------------------------------- 초기화

	public void menu_sw(int n) {
		for (int i = 0; i < menu_btn_arr.length; i++) {
			set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(90, 35));
			menu_item_group[i].setVisible(false);
		}
		menu_item_group[6].setVisible(false);
		set_Btn(menu_btn[n], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(90, 35));
		// 메뉴 버튼
		set_Btn(order_search_btn, 15, new Color(200, 200, 200), new Color(200, 200, 200), new Dimension(35, 35));
		set_TextField(order_search_tb, new Color(200, 200, 200), new Color(200, 200, 200));
		menu_item_group[n].setVisible(true);
		order_search_btn.setEnabled(false);
	}

	// ---------------------------------------------- 메뉴 탭

	public Order_Form() {
		order_main.setSize(1280, 720);
		order_main.setDefaultCloseOperation(order_main.DISPOSE_ON_CLOSE);
		order_main.setLocationRelativeTo(null);
		order_main.setResizable(false);
		order_main.getContentPane().setBackground(Color.white);
		order_main.setLayout(null);

		// ---------------------------------------------- 메인 프레임
		table_Center(order_table, 3); // 테이블 가운데 정렬
		table_Center(order_chk_table, 6);
		top_menu(); // 탭 생성
		order_list_main(); // 주문 목록 생성
		menu_DB(); // 메뉴 DB 불러옴과 동시에 생성
	}

	public void top_menu() { // 메뉴 목록
		top_menu.setBounds(0, 0, 935, 60);
		top_menu.setBackground(new Color(230, 230, 230));
		top_menu.setLayout(null);

		menu_btn_group.setBounds(0, 5, 935, 50);
		menu_btn_group.setBackground(new Color(230, 230, 230));
		menu_btn_group.setLayout(new FlowLayout());

		for (int i = 0; i < menu_btn_arr.length; i++) { // 메뉴 버튼
			menu_btn[i] = new JButton(menu_btn_arr[i]);
			set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(90, 35));
			menu_btn_group.add(menu_btn[i]);
		}

		for (int i = 0; i < menu_item_group.length; i++) { // 메뉴 페이지
			menu_item_group[i] = new JPanel();
			menu_layout(menu_item_group[i]);
			menu_item_group[i].setVisible(false);
			order_main.add(menu_item_group[i]);
		}

		order_search_group.setPreferredSize(new Dimension(214, 35)); // 검색창
		order_search_group.setLayout(null);

		order_search_tb.setBounds(0, 0, 179, 35);
		set_TextField(order_search_tb, new Color(200, 200, 200), new Color(200, 200, 200));

		order_search_btn.setBounds(179, 0, 35, 35);
		set_Btn(order_search_btn, 15, new Color(200, 200, 200), new Color(200, 200, 200), new Dimension(35, 35));
		order_search_btn.setEnabled(false);

		menu_item_group[0].setVisible(true); // 초기 첫 페이지만 보이게함
		set_Btn(menu_btn[0], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(90, 35));

		for (int i = 0; i < menu_btn_arr.length; i++) { // 페이지 전환
			int num = i;
			menu_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					menu_sw(num);
				}
			});
		}

		order_search_tb.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (order_search_tb.getText().equals("검색")) {
					order_search_tb.setText("");
				}

				for (int i = 0; i < menu_btn_arr.length; i++) {
					set_Btn(menu_btn[i], 15, new Color(240, 240, 240), new Color(0, 120, 215), new Dimension(90, 35));
					menu_item_group[i].setVisible(false);
				}
				menu_item_group[6].setVisible(true);

				order_search_btn.setEnabled(true);
				set_TextField(order_search_tb, new Color(0, 120, 215), new Color(0, 0, 0));
				set_Btn(order_search_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(35, 35));

			}
		});

		int menu_count = 24; // 메뉴 생성
		JPanel[] menu_panel = new JPanel[menu_count];
		JLabel[] menu_name = new JLabel[menu_count];
		JLabel[] menu_price = new JLabel[menu_count];
		JLabel[] menu_img = new JLabel[menu_count];
		JButton[] menu_btn = new JButton[menu_count];

		// ----------------------------------------------------- 음식 리스트

		for (int i = 0; i < menu_count; i++) { // 음식 리스트 생성
			menu_panel[i] = new JPanel();
			menu_panel[i].setPreferredSize(new Dimension(150, 150));
			menu_panel[i].setBackground(Color.white);
			menu_panel[i].setLayout(null);
			menu_panel[i].setVisible(false);
			// ----------------------------------------------------- 메인 패널
			menu_img[i] = new JLabel();
			menu_img[i].setBounds(0, 5, 150, 105);
			menu_img[i].setHorizontalAlignment(JLabel.CENTER);
			// ----------------------------------------------------- 이미지
			menu_name[i] = new JLabel();
			menu_name[i].setBounds(0, 98, 150, 50);
			set_Font(menu_name[i], 15, new Color(0, 0, 0), JLabel.CENTER);
			// ----------------------------------------------------- 이름
			menu_price[i] = new JLabel();
			menu_price[i].setBounds(0, 114, 150, 50);
			set_Font(menu_price[i], 13, new Color(0, 0, 0), JLabel.CENTER);
			// ----------------------------------------------------- 가격
			menu_btn[i] = new JButton();
			menu_btn[i].setSize(150, 150);
			menu_btn[i].setBorderPainted(false);
			menu_btn[i].setFocusPainted(false);
			menu_btn[i].setContentAreaFilled(false);
			// ----------------------------------------------------- 버튼
			menu_panel[i].add(menu_img[i]);
			menu_panel[i].add(menu_name[i]);
			menu_panel[i].add(menu_price[i]);
			menu_panel[i].add(menu_btn[i]);
			// ----------------------------------------------------- 분류
			menu_item_group[6].add(menu_panel[i]);
		}

		order_search_btn.addMouseListener(new MouseAdapter() { // 검색버튼
			public void mouseClicked(MouseEvent e) {
				if (order_search_tb.getText().equals("검색 ") || order_search_tb.getText().equals("")) {
					Noti("검색단어를 입력해주세요.", "알림");
				} else {
					search_menu_DB(order_search_tb.getText(), menu_panel, menu_img, menu_name, menu_price);
				}
			}
		});

		for (int i = 0; i < 24; i++) { // 검색 창에서 버튼
			int n = i;
			menu_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					String name = search_name.get(n);
					int price = Integer.parseInt(search_price.get(n));
					add_list(name, price);
				}
			});
		}

		order_main.add(top_menu);
		top_menu.add(menu_btn_group);
		menu_btn_group.add(order_search_group);
		order_search_group.add(order_search_tb);
		order_search_group.add(order_search_btn);

	}

	public void order_list_main() { // 주문 목록
		order_list_main.setBounds(935, 0, 333, 720);
		order_list_main.setLayout(null);
		order_list_main.setBackground(new Color(220, 220, 220));

		// ------------------------------------------------

		for (int i = 0; i < order_list_lb_arr.length; i++) {
			order_list_lb[i] = new JLabel(order_list_lb_arr[i]);
			set_Font(order_list_lb[i], 20, new Color(0, 0, 0), JLabel.LEFT);
			order_list_main.add(order_list_lb[i]);
		}

		order_list_lb[0].setBounds(5, 0, 323, 30); // ??번자리
		set_Font(order_list_lb[0], 15, new Color(0, 0, 0), JLabel.LEFT);

		order_list_lb[1].setBounds(5, 25, 323, 30); // ~~님
		set_Font(order_list_lb[1], 15, new Color(0, 0, 0), JLabel.LEFT);

		order_list_lb[2].setBounds(5, 55, 323, 30); // 주문리스트

		order_list_lb[3].setBounds(5, 375, 323, 30); // 주문금액 : 0원
		order_list_lb[4].setBounds(5, 405, 323, 30); // 결제방식

		// ---------------------------------------------- 기본 틀
		JFrame order_chk_main = new JFrame("주문내역");
		JButton order_list_btn = new JButton("주문내역");

		order_chk_main.setSize(800, 400);
		order_chk_main.setDefaultCloseOperation(order_main.DISPOSE_ON_CLOSE);
		order_chk_main.setLocationRelativeTo(null);
		order_chk_main.setResizable(false);
		order_chk_main.getContentPane().setBackground(new Color(240, 240, 240));
		order_chk_main.setLayout(null);

		order_list_btn.setBounds(85, 60, 85, 20); // 주문 내역
		set_Btn(order_list_btn, 2, new Color(192, 0, 0), new Color(255, 255, 255), new Dimension(100, 30));

		order_chk_table.getTableHeader().setReorderingAllowed(false);
		order_chk_table.getTableHeader().setResizingAllowed(false);

		order_chk_scrollpane.getViewport().setBackground(Color.white);
		order_chk_scrollpane.setBounds(0, 0, 800, 300);

		order_chk_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		order_chk_btn_group.setBounds(0, 305, 800, 100);
		order_chk_btn_group.setBackground(new Color(240, 240, 240));
		order_chk_btn_group.setLayout(new FlowLayout());

		order_canel_btn.setText("주문 취소요청");
		set_Btn(order_canel_btn, 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(120, 40));

		order_list_btn.addMouseListener(new MouseAdapter() { // 주문 내역 버튼
			public void mouseClicked(MouseEvent e) {
				order_chk_main.setVisible(true);
				order_list_DB(user_name + "(" + user_id + ")");
			}
		});

		order_canel_btn.addMouseListener(new MouseAdapter() { // 주문 취소 요청
			public void mouseClicked(MouseEvent e) {
				int row = order_chk_table.getSelectedRow();
				if (row == -1) {
					Noti("주문내역이 없습니다.", "알림");
				} else {
					int result = JOptionPane.showConfirmDialog(null, "주문 취소요청을 하겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						Noti("주문취소가 접수되었습니다. 카운터에서 승인 후 취소됩니다.", "알림");
					}
				}
			}
		});

		order_chk_table.addMouseListener(new MouseAdapter() { // 완료
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = order_chk_table.getSelectedRow();
					TableModel data = order_chk_table.getModel();

					String list = (String) data.getValueAt(row, 1);
					String cost = (String) data.getValueAt(row, 2);
					String pay = (String) data.getValueAt(row, 3);
					String mag = (String) data.getValueAt(row, 4);
					
					if(mag.length() == 0) {
						mag = "없음";
					}

					Object[] arr = { "*주문목록*", list, "*총 금액*", cost, "*지불방식*", pay, "*요청사항*", mag, "*상태*", "접수완료" };
					JOptionPane.showMessageDialog(order_chk_main, arr, "내 주문내역", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		// -----------------------------------------
		order_chk_main.add(order_chk_scrollpane);
		order_chk_main.add(order_chk_btn_group);
		order_chk_btn_group.add(order_canel_btn);

		// ---------------------------------------------- 주문 내역
		order_table.getTableHeader().setReorderingAllowed(false);
		order_table.getTableHeader().setResizingAllowed(false);

		order_scrollpane.getViewport().setBackground(Color.white);
		order_scrollpane.setBounds(0, 88, 333, 250);

		Border border = BorderFactory.createLineBorder(new Color(220, 220, 220), 5);
		order_scrollpane.setBorder(border);

		order_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// ---------------------------------------------- 주문 리스트 테이블
		order_calc_group.setBounds(0, 336, 333, 40);
		order_calc_group.setBackground(new Color(220, 220, 220));

		set_Btn(order_calc_plus, 20, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(45, 30));
		set_Btn(order_clac_minus, 20, new Color(192, 0, 0), new Color(255, 255, 255), new Dimension(45, 30));

		order_calc_count.setText("0");
		set_Font(order_calc_count, 20, new Color(0, 0, 0), JLabel.CENTER);
		order_calc_count.setPreferredSize(new Dimension(60, 30));

		// ---------------------------------------------- 주문 목록 계산(더하기, 뺴기) 버튼
		for (int i = 0; i < order_radio_arr.length; i++) { // 라디오 박스
			order_radio_btn[i] = new JRadioButton(order_radio_arr[i]);
			order_radio_btn[i].setBackground(new Color(220, 220, 220));
			order_radio_btn[i].setFont(new Font(font_name, Font.PLAIN, 15));
			order_radio_btn[i].setForeground(Color.black);
			order_list_main.add(order_radio_btn[i]);
		}

		order_cost_type_1.add(order_radio_btn[0]); // 카드
		order_cost_type_1.add(order_radio_btn[1]);
		order_cost_type_1.add(order_radio_btn[4]); // 가짜버튼

		order_cost_type_2.add(order_radio_btn[2]); // 현금
		order_cost_type_2.add(order_radio_btn[3]);
		order_cost_type_2.add(order_radio_btn[5]); // 가짜버튼

		order_radio_btn[0].setBounds(3, 435, 50, 20);
		order_radio_btn[1].setBounds(63, 435, 50, 20);
		order_radio_btn[2].setBounds(123, 435, 50, 20);
		order_radio_btn[3].setBounds(183, 435, 50, 20);
		order_radio_btn[2].setEnabled(false);
		order_radio_btn[3].setEnabled(false);

		order_radio_btn[0].addActionListener(event -> { // 현금
			order_radio_1 = order_radio_btn[0].getText();
			order_radio_btn[2].setEnabled(true);
			order_radio_btn[3].setEnabled(true);
		});

		order_radio_btn[1].addActionListener(event -> { // 카드
			order_radio_1 = order_radio_btn[1].getText();
			order_radio_btn[2].setEnabled(false);
			order_radio_btn[3].setEnabled(false);
			order_radio_btn[5].setSelected(true);
		});

		order_radio_btn[2].addActionListener(event -> {
			order_radio_2 = order_radio_btn[2].getText();
		});

		order_radio_btn[3].addActionListener(event -> {
			order_radio_2 = order_radio_btn[3].getText();
		});

		// ---------------------------------------------- 결제 방식
		order_list_lb[5].setBounds(5, 455, 323, 30); // 요청사항

		order_send_text.setLineWrap(true); // 자동 줄바꿈
		JScrollPane order_send_scroll = new JScrollPane(order_send_text);

		order_send_scroll.setBounds(0, 485, 333, 130);
		order_send_scroll.setBorder(border);

		// ---------------------------------------------- 요청 사항
		order_btn_group.setBounds(0, 620, 333, 50);
		order_btn_group.setBackground(new Color(220, 220, 220));
		order_btn_group.setLayout(new FlowLayout());

		for (int i = 0; i < order_btn_arr.length; i++) {
			order_btn[i] = new JButton(order_btn_arr[i]);
			set_Btn(order_btn[i], 15, new Color(0, 120, 215), new Color(255, 255, 255), new Dimension(105, 40));
			order_btn_group.add(order_btn[i]);
		}

		order_btn[0].setBackground(new Color(192, 0, 0));
		order_btn[1].setBackground(new Color(192, 0, 0));

		order_btn[0].addMouseListener(new MouseAdapter() { // 삭제
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();

				if (row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					int count = (int) order_table.getValueAt(row, 1);
					String price_temp = ((String) order_table.getValueAt(row, 2)).replaceAll("[^0-9]", "");
					int price = (Integer.parseInt(price_temp));
					String name = (String) order_table.getValueAt(row, 0);

					int result = JOptionPane.showConfirmDialog(null, name + "를 삭제하겠습니까?", "알림",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						order_model.removeRow(row);
						table_list.remove(name);
						table_list_temp.remove(name);

						order_total -= price;
						order_calc_count.setText("0");
						order_list_lb[3].setText("총 금액 : " + format.format(order_total) + "원");
					}
				}
			}
		});

		order_btn[1].addMouseListener(new MouseAdapter() { // 초기화
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "현재 주문 목록이 초기화됩니다.", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					order_clean();
					Noti("모든 내용이 삭제되었습니다.", "알림");
				}
			}
		});

		order_btn[2].addMouseListener(new MouseAdapter() { // 주문하기
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getRowCount();

				if (row == 0) {
					Noti("선택된 메뉴가 없습니다.", "알림");
				} else {
					if (order_count == 0) {
						String[] array = new String[row];
						String temp = "";
						String temp1 = ""; // 주문종류 및 갯수

						for (int i = 0; i < row; i++) {
							String name = (String) order_table.getValueAt(i, 0);
							int count = (int) order_table.getValueAt(i, 1);
							array[i] = name + "(" + count + ")";
							temp1 = array[i] + ", ";
							temp += temp1;
						}

						// ---------------------------------
						String massage = order_send_text.getText();
						String cost = format.format(order_total) + "원";
						String order_payment = "";

						if (order_radio_btn[0].isSelected() == false && order_radio_btn[1].isSelected() == false) {
							Noti("결제방식을 선택해 주세요.", "알림");
						} else {
							if (order_radio_btn[0].isSelected() == true) { // 현금을 선택했을 때
								if (order_radio_btn[2].isSelected() == false
										&& order_radio_btn[3].isSelected() == false) {
									Noti("지불방식을 선택해 주세요.", "알림");
								} else {
									int result = JOptionPane.showConfirmDialog(null, "주문하시겠습니까?", "알림",
											JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
									if (result == JOptionPane.YES_OPTION) {
										order_count = 1;
										if (order_radio_btn[2].isSelected() == true) {
											order_payment = "현금" + "(" + order_radio_btn[2].getText() + ")";
										} else if (order_radio_btn[3].isSelected() == true) {
											order_payment = "현금" + "(" + order_radio_btn[3].getText() + ")";
										}
										order_DB(user_name + "(" + user_id + ")", place,
												temp.substring(0, temp.length() - 2), cost, order_payment, massage,
												order_Date());
									}
								}
							} else { // 카드를 선택했을 때
								int result = JOptionPane.showConfirmDialog(null, "주문하시겠습니까?", "알림",
										JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									order_count = 1;
									order_payment = order_radio_btn[0].getText();
									order_DB(user_name + "(" + user_id + ")", place,
											temp.substring(0, temp.length() - 2), cost, order_payment, massage,
											order_Date());
								}
							}
						}
					} else {
						Noti("주문내역이 존재합니다.", "알림");
					}
				}
			}
		});

		order_main.addWindowListener(new java.awt.event.WindowAdapter() { // 상위로 닫았을 때 초기화
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				order_clean();
			}

		});

		// ---------------------------------------------- 버튼
		order_main.add(order_list_main);

		order_list_main.add(order_scrollpane);
		order_list_main.add(order_calc_group);
		order_list_main.add(order_send_scroll);
		order_list_main.add(order_btn_group);
		order_list_main.add(order_list_btn);

		order_calc_group.add(order_clac_minus);
		order_calc_group.add(order_calc_count);
		order_calc_group.add(order_calc_plus);
	}

	// ---------------------------------------------- UI

	public void search_menu_list() {
		int menu_count = menu_name_array.size(); // 메뉴 생성
		JPanel[] menu_panel = new JPanel[menu_count];
		JLabel[] menu_name = new JLabel[menu_count];
		JLabel[] menu_price = new JLabel[menu_count];
		JLabel[] menu_img = new JLabel[menu_count];
		JButton[] menu_btn = new JButton[menu_count];

		// ----------------------------------------------------- 음식 리스트

		for (int i = 0; i < menu_count; i++) { // 음식 리스트 생성
			menu_panel[i] = new JPanel();
			menu_panel[i].setPreferredSize(new Dimension(150, 150));
			menu_panel[i].setBackground(Color.white);
			menu_panel[i].setLayout(null);
			menu_panel[i].setVisible(false);
			// ----------------------------------------------------- 메인 패널
			menu_img[i] = new JLabel();
			menu_img[i].setBounds(0, 5, 150, 105);
			menu_img[i].setHorizontalAlignment(JLabel.CENTER);
			// ----------------------------------------------------- 이미지
			menu_name[i] = new JLabel();
			menu_name[i].setBounds(0, 98, 150, 50);
			set_Font(menu_name[i], 15, new Color(0, 0, 0), JLabel.CENTER);
			// ----------------------------------------------------- 이름
			menu_price[i] = new JLabel();
			menu_price[i].setBounds(0, 114, 150, 50);
			set_Font(menu_price[i], 13, new Color(0, 0, 0), JLabel.CENTER);
			// ----------------------------------------------------- 가격
			menu_btn[i] = new JButton();
			menu_btn[i].setSize(150, 150);
			menu_btn[i].setBorderPainted(false);
			menu_btn[i].setFocusPainted(false);
			menu_btn[i].setContentAreaFilled(false);
			// ----------------------------------------------------- 버튼
			menu_panel[i].add(menu_img[i]);
			menu_panel[i].add(menu_name[i]);
			menu_panel[i].add(menu_price[i]);
			menu_panel[i].add(menu_btn[i]);
			// ----------------------------------------------------- 분류
			menu_item_group[6].add(menu_panel[i]);
		}
	}

	public void order_DB(String od_name, String od_place, String od_menuList, String od_cost, String od_payment,
			String od_mag, String od_Date) {
		try {
			String url = "jdbc:mysql://localhost/data?allowMultiQueries=true";
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "insert into menu_order(od_name, od_place, od_menuList, od_cost, od_payment, od_mag, od_orderDate) values(?, ?, ?, ?, ?, ?, ?);"
					+ " set @count=0; update menu_order set od_idx =@count:=@count+1;";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, od_name); // 이름
			pstmt.setString(2, od_place + "번 자리"); // 자리
			pstmt.setString(3, od_menuList); // 주문 종류
			pstmt.setString(4, od_cost); // 총금액
			pstmt.setString(5, od_payment); // 지불방식
			pstmt.setString(6, od_mag); // 요청사항
			pstmt.setString(7, od_Date); // 주문시간
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			Noti("주문이 완료되었습니다.", "알림");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void search_menu_DB(String menu, JPanel jp[], JLabel img[], JLabel name[], JLabel price[]) {
		try {
			search_name.clear();
			search_price.clear();
			search_img.clear();
			// 시작전에 배열 초기화
			for (int i = 0; i < 24; i++) {
				jp[i].setVisible(false);
			}

			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from menu where m_name like '%" + menu + "%'";
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				search_name.add(rs.getString("m_name"));
				search_price.add(rs.getString("m_price"));
				search_img.add(rs.getString("m_img"));
			}
			if (search_name.size() > 0) {
				for (int i = 0; i < search_name.size(); i++) {
					jp[i].setVisible(true);
					img[i].setIcon(reSize_Img(search_img.get(i), 105, 105));
					name[i].setText(search_name.get(i));
					price[i].setText(format.format(Integer.parseInt(search_price.get(i))) + "원");
				}
			} else {
				Noti("검색된 메뉴가 없습니다.", "알림");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// ---------------------------------------------- 검색된 음식 목록

	public void menu_DB() { // 메뉴 불러오기
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "select * from menu";
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menu_name_array.add(rs.getString("m_name"));
				menu_type_array.add(rs.getString("m_type"));
				menu_price_array.add(rs.getString("m_price"));
				menu_img_array.add(rs.getString("m_img"));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ---------------------------------------------------
		int menu_count = menu_name_array.size(); // 메뉴 생성
		JPanel[] menu_panel = new JPanel[menu_count];
		JLabel[] menu_name = new JLabel[menu_count];
		JLabel[] menu_price = new JLabel[menu_count];
		JLabel[] menu_img = new JLabel[menu_count];
		JButton[] menu_btn = new JButton[menu_count];

		// ----------------------------------------------------- 음식 리스트
		for (int i = 0; i < menu_count; i++) { // 음식 리스트 생성
			menu_panel[i] = new JPanel();
			menu_panel[i].setPreferredSize(new Dimension(150, 150));
			menu_panel[i].setBackground(Color.white);
			menu_panel[i].setLayout(null);

			// ----------------------------------------------------- 메인 패널
			menu_img[i] = new JLabel(reSize_Img(menu_img_array.get(i), 105, 105));
			menu_img[i].setBounds(0, 5, 150, 105);
			menu_img[i].setHorizontalAlignment(JLabel.CENTER);

			// ----------------------------------------------------- 이미지
			menu_name[i] = new JLabel(menu_name_array.get(i));
			menu_name[i].setBounds(0, 98, 150, 50);
			set_Font(menu_name[i], 15, new Color(0, 0, 0), JLabel.CENTER);

			// ----------------------------------------------------- 이름
			menu_price[i] = new JLabel(format.format(Integer.parseInt(menu_price_array.get(i))) + "원");
			menu_price[i].setBounds(0, 114, 150, 50);
			set_Font(menu_price[i], 13, new Color(0, 0, 0), JLabel.CENTER);

			// ----------------------------------------------------- 가격
			menu_btn[i] = new JButton();
			menu_btn[i].setSize(150, 150);
			menu_btn[i].setBorderPainted(false);
			menu_btn[i].setFocusPainted(false);
			menu_btn[i].setContentAreaFilled(false);

			// ----------------------------------------------------- 버튼
			menu_panel[i].add(menu_img[i]);
			menu_panel[i].add(menu_name[i]);
			menu_panel[i].add(menu_price[i]);
			menu_panel[i].add(menu_btn[i]);

			// ----------------------------------------------------- 분류
			if (menu_type_array.get(i).equals("밥류")) {
				menu_item_group[0].add(menu_panel[i]);
			} else if (menu_type_array.get(i).equals("면류")) {
				menu_item_group[1].add(menu_panel[i]);
			} else if (menu_type_array.get(i).equals("튀김류")) {
				menu_item_group[2].add(menu_panel[i]);
			} else if (menu_type_array.get(i).equals("과자류")) {
				menu_item_group[3].add(menu_panel[i]);
			} else if (menu_type_array.get(i).equals("음료류")) {
				menu_item_group[4].add(menu_panel[i]);
			} else if (menu_type_array.get(i).equals("기타")) {
				menu_item_group[5].add(menu_panel[i]);
			}
		}

		for (int i = 0; i < menu_name_array.size(); i++) { // 일반 창에서 선택
			int n = i;
			menu_btn[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					String name = menu_name_array.get(n);
					int price = Integer.parseInt(menu_price_array.get(n));
					add_list(name, price);
				}
			});
		}

		order_clac_minus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();

				if (row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					int count = (int) order_table.getValueAt(row, 1);
					String price_temp = ((String) order_table.getValueAt(row, 2)).replaceAll("[^0-9]", "");
					int price = (Integer.parseInt(price_temp) / count);
					String name = (String) order_table.getValueAt(row, 0);

					order_calc_count.setText(String.valueOf(count - 1));
					order_table.setValueAt((count - 1), row, 1); // 수량 수정
					order_table.setValueAt(format.format(price * (count - 1)) + "원", row, 2); // 가격 수정

					order_total -= price;
					order_list_lb[3].setText("총 금액 : " + format.format(order_total) + "원");

					if (count == 1) {
						order_model.removeRow(row);
						table_list.remove(name);
						table_list_temp.remove(name);
						// 0가 되어 삭제가되면 배열에서도 제거
					}
				}
			}
		});

		order_calc_plus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();

				if (row == -1) {
					Noti("선택된 값이 없습니다.", "알림");
				} else {
					int count = (int) order_table.getValueAt(row, 1);
					String price_temp = ((String) order_table.getValueAt(row, 2)).replaceAll("[^0-9]", "");
					int price = (Integer.parseInt(price_temp) / count);
					String name = (String) order_table.getValueAt(row, 0);

					order_calc_count.setText(String.valueOf(count + 1));
					order_table.setValueAt((count + 1), row, 1); // 수량 수정
					order_table.setValueAt(format.format(price * (count + 1)) + "원", row, 2); // 가격 수정

					order_total += price;
					order_list_lb[3].setText("총 금액 : " + format.format(order_total) + "원");
				}
			}
		});

		order_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = order_table.getSelectedRow();

				int count = (int) order_table.getValueAt(row, 1);
				order_calc_count.setText(String.valueOf(count));
			}
		});
	}

	// ---------------------------------------------- 주문 리스트
	public void add_list(String name, int num) {
		int row = table_list_temp.indexOf(name);
		order_table.clearSelection();
		if (table_list.get(name) == null) {
			table_list.put(name, num);
			table_list_temp.add(name);
			order_model.addRow(new Object[] { name, 1, format.format(num) + "원" });
		} else {
			int count = (int) order_table.getValueAt(row, 1);
			order_calc_count.setText(String.valueOf(count + 1));
			order_table.setValueAt((count + 1), row, 1); // 수량 수정
			order_table.setValueAt(format.format(num * (count + 1)) + "원", row, 2); // 가격 수정
		}
		order_total += num;
		order_list_lb[3].setText("총 금액 : " + format.format(order_total) + "원");
	}

	// ---------------------------------------------- DB

	void order_list_DB(String user_id) { // 주문DB 불러오기
		order_chk_model.setNumRows(0);
		try {
			conn = DriverManager.getConnection(url, id, pw);
			String SQL = "Select * from menu_order where od_name=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order_chk_row[0] = String.valueOf(rs.getInt("od_idx"));
				order_chk_row[1] = rs.getString("od_menuList");
				order_chk_row[2] = rs.getString("od_cost");
				order_chk_row[3] = rs.getString("od_payment");
				order_chk_row[4] = rs.getString("od_mag");
				order_chk_row[5] = rs.getString("od_orderDate");
				order_chk_row[6] = "접수완료";
				order_chk_model.addRow(order_chk_row);
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
