# PC방 프로그램
● [Java]Swing, MySql로 구현한 GUI 프로그램입니다.<br>
● 로그인, 회원가입, 회원정보찾기, 음식주문, 관리자 페이지(주문관리, 좌석관리, 회원관리) 등 핵심기능을 구현하였습니다.<br>
● 유저, 관리자 2개의 시스템으로 구성되어있습니다.<br>
# 화면 설명(유저) #
![Untitled-1](https://user-images.githubusercontent.com/118651919/218269352-ade93e89-72b7-4678-93ef-325c4641f339.png)
### 메인 <br>
- 회원, 비회원 로그인 및 ID/PW 찾기, 회원 가입으로 구성되어있습니다.
- 회원의 경우 생성한 아이디 비회원의 경우 카드 번호를 입력합니다.

![회원가입](https://user-images.githubusercontent.com/118651919/218270129-31dfc722-3742-4bc7-a309-b941a321bb0c.png)
### 회원가입 <br>
- 해당 폼에서 이름, 아이디, 비밀번호, 휴대폰 정보를 입력하여 가입할 수 있습니다.
- 최종적으로 가입하기를 진행 시 DB에서 중복된 값이 있는지 판별하거나 입력 자릿 수 체크를 합니다. 
- 아이디와 휴대폰 번호는 중복이 불가합니다.<br><br>

![소스](https://user-images.githubusercontent.com/118651919/219654784-d29b4768-c3fd-41ff-8834-3c017321426d.png)
### 비밀번호 생성 방법 <br>
- 보안을 위해 비밀번호는 sh256 + salt 암호화로 보호되어 저장됩니다.
- 아이디 생성 시 임의의 salt를 생성한 후 유저 데이터에 넣습니다. 
- 이렇게 생성 된 salt는 유저가 입력한 비밀번호 평문과 섞습니다.<br>
ex. salt = /9UFR5IJObK0t9QQ9oPaYQ== 비밀번호 = 1234 ====> /9UFR5IJObK0t9QQ9oPaYQ==1234<br>
- 합쳐진 문자열에 SHA256 암호화를 합니다. SHA(/9UFR5IJObK0t9QQ9oPaYQ==1234)<br><br>

![Untitled-1](https://user-images.githubusercontent.com/118651919/219654308-d8495061-ad80-4b9c-b56d-e4bf1900a6ca.png)
### ID/PW 찾기 <br>
- ID는 이름과 휴대폰 번호로 앞 2자리까지만 알 수 있습니다.
- 비밀번호의 경우 보안을 위해 재발급만 허용합니다.
- 비밀번호 재발급 시 다시 유저 데이터에 새로운 m_salt값이 갱신됩니다.<br><br>

![1234](https://user-images.githubusercontent.com/118651919/219657915-4ccee8bf-e6cc-4afb-8888-42b952c53a87.png)
### 상태 창 <br>
- 상단에 남은시간, 사용시간, 사용자 이름, 자리가 표시되고 주문하기, 카운터 연락, 사용종료 메뉴가 있습니다.

![Layer 11](https://user-images.githubusercontent.com/118651919/219660475-6bfb60f8-7dbd-4ee3-8332-d4576e3565d9.png)<br>
![라면 검색 시](https://user-images.githubusercontent.com/118651919/219660722-3d40559c-c08c-4887-b220-b497371ee691.png)<br>
![Layer 13](https://user-images.githubusercontent.com/118651919/219660737-f137d581-f994-499a-9cf9-02889fa8afbd.png)<br>
![Layer 14](https://user-images.githubusercontent.com/118651919/219660744-878131e7-ef72-4f40-9b33-9ce89d4ce372.png)<br>

### 주문하기 <br>
- 각 탭에 있는 메뉴를 통해 음식을 주문 할 수 있습니다.
- 메뉴 클릭 시 주문목록에 총 가격과 갯수가 입력됩니다.
- 또는 아래 -, + 버튼을 통해서도 추가, 뺴기가 가능합니다.
- 최종적으로 메뉴를 고른 후 결제방식과 요청사항(선택)를 입력시 주문이 됩니다.
- 주문한 내역은 주문내역을 통해 확인 가능합니다.<br><br>

# 화면 설명(관리자) #
![Layer 23](https://user-images.githubusercontent.com/118651919/219673617-c8bc304e-992d-4c26-b6ca-3f3678aa4fff.png)<br>
![Layer 22](https://user-images.githubusercontent.com/118651919/219674952-0a8295b7-18b0-4ad8-8e7d-7049a1895aef.png)

### 좌석관리 <br>
- 좌석을 추가 및 삭제 할 수 있습니다.
- 삭제 및 추가 상태에서 자리를 누르면 삭제 및 추가가 가능합니다.
- 요청된 주문을 확인 할 수 있습니다.<br><br>

![Layer 25](https://user-images.githubusercontent.com/118651919/219674462-dddcef45-3024-4a6f-8ab4-4625e4913724.png)<br>
![Layer 24](https://user-images.githubusercontent.com/118651919/219674524-3ac1ce4d-9c8f-4b7d-a024-6e2ba23bce49.png)<br>
![Layer 26](https://user-images.githubusercontent.com/118651919/219674531-193b017b-1bbd-4bb7-a66e-d36870264c62.png)

### 회원관리 <br>
- 회원을 추가, 검색, 제거, 수정을 할 수 있습니다.
- 회원 비밀번호는 변경이 불가합니다.<br><br>

![Layer 17](https://user-images.githubusercontent.com/118651919/219674805-1f045ba1-0e4c-4173-a78a-e4cb136cd472.png)<br>
![Layer 18](https://user-images.githubusercontent.com/118651919/219674815-423ccb5f-87b0-4c95-baca-5be2388e1ad1.png)<br>
![Layer 19](https://user-images.githubusercontent.com/118651919/219674821-a2908f92-0813-4271-8bb3-a115d4ff4d1d.png)<br>
![Layer 20](https://user-images.githubusercontent.com/118651919/219674825-31e7dc37-f21e-41d3-9487-0b386bdd2058.png)

### 메뉴관리 <br>
- 메뉴를 추가, 검색, 제거, 수정을 할 수 있습니다.
 
