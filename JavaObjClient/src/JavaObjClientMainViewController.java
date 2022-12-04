
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Object.User;
import Object.Room;
import Object.Chat;
import Object.DivString;

import javax.swing.*;


public class JavaObjClientMainViewController {
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
    private Socket socket; // 연결소켓
    private ObjectInputStream ois; // 입력용 객체
    private ObjectOutputStream oos; // 출력용 객체
    private User user; // 접속하고 있는 User 본인
    private Map<Integer, User> UserList = new HashMap<>(); // 현재 접속 중인 UserList
    private Map<Integer, Room> RoomList = new HashMap<>(); // 현재 존재 하는 RoomList
    private LoginView loginView; // LoginView
    private App appView; // AppView(MainView)
    private Map<Integer, ChatRoomView> chatRoomViewList = new HashMap<Integer, ChatRoomView>(); // ChatRoomViewList Key: Rid Value: ChatRoomView
    private String ip_addr;
    private String port_no;
    //private ListenNetwork net ;
    private static JavaObjClientMainViewController controller; // Singleton Pattern 적용

    // Singleton pattern 시작 (생성자)
    private JavaObjClientMainViewController() {
    }

    public static JavaObjClientMainViewController getInstance() {
        if (controller == null) {
            controller = new JavaObjClientMainViewController(); // Main에서 처음 생성됨
        }
        return controller;
    }
    // Singleton pattern 끝

    // Getter & Setter 시작
    public User getUser() {
        return user;
    }

    public Map<Integer, ChatRoomView> getChatRoomViewList() {
        return chatRoomViewList;
    }

    public String getPort_no() {
        return port_no;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public Map<Integer, User> getUserList() {
        return UserList;
    }

    public Map<Integer, Room> getRoomList() {
        return RoomList;
    }

//    public ListenNetwork getNet() {
//        return net;
//    }

    public void setUserUserName(String username) {
        user = new User.UserBuilder().setUserName(username).build();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setOIS(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setOOS(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void setUserList(Map<Integer, User> userList) {
        UserList = userList;
    }

    public void setRoomList(Map<Integer, Room> roomList) {
        RoomList = roomList;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setAppView(App appView) {
        this.appView = appView;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public void setPort_no(String port_no) {
        this.port_no = port_no;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChatRoomViewList(Map<Integer, ChatRoomView> chatRoomViewList) {
        this.chatRoomViewList = chatRoomViewList;
    }

    //    public void setNet(ListenNetwork net) {
//        this.net = net;
//    }

    // Getter & Setter 끝

    //Controller setting methods 시작


    /**
     * chatRoomView를 받아서 controller의 chatRoomViewList 맨 끝에 추가하는 method
     *
     * @param chatRoomView 추가할 View
     */
    public void addChatRoomView(Integer rid,ChatRoomView chatRoomView) {
        this.chatRoomViewList.put(rid, chatRoomView);
    }


    public void increaseChatRoomIndex(int data) {
    }

    public void increaseMakeChatRoomIndex(int data) {
    }

    //Controller setting methods 끝

    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
        try {
            ChatMsg cm = (ChatMsg) ob;
            System.out.println("client send " + cm.getCode() + " " + cm.getId() + " " + cm.getData());
            oos.writeObject(ob);
        } catch (IOException e) {
            // textArea.append("메세지 송신 에러!!\n");
            //AppendText("SendObject Error");
        }
    }

    /**
     * Controller가 실행됨
     */
    public void activate() {
        LoginView loginView = new LoginView();
        setLoginView(loginView);
        loginView.setVisible(true);// 처음 실행되면 Login 창을 생성함, userName,ip_Addr,portNo 설정
    }

    // Server Message를 수신해서 화면에 표시
    class ListenNetwork extends Thread {
        public void run() {
            while (true) {
                try {
                    Object obcm = null;
                    String msg = null;
                    ChatMsg cm;
                    try {
                        obcm = ois.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        break;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatMsg) {
                        cm = (ChatMsg) obcm;
                        msg = String.format("[%s] %s", cm.getId(), cm.getData());
                    } else
                        continue;
                    switch (cm.getCode()) {
                        case "110":
                            System.out.println("Client received 110 " + msg);
                            dataSetUser(msg);
                            break;
                        case "120":
                            System.out.println("Client received 120 " + msg);
                            break;
                        case "200": // chat message
                            //AppendText(msg);
                            System.out.println("Client received 200 " + msg);
                            break;
                        case "230":
                            System.out.println("Client received 230 " + msg);
                            break;
                        case "300": // Image 첨부
                            //AppendText("[" + cm.getId() + "]");
                            //AppendImage(cm.img);
                            System.out.println("Client received 300 " + msg);
                            break;
                        case "600":
                            System.out.println("Client received 600 " + msg);
                            dataReformat(cm.getCode(), msg);
                            reGenerateAppView();
                            break;
                        case "610":
                            System.out.println("Client received 610" + msg);
                            dataReformat(cm.getCode(), msg);
                            reGenerateAppView();
                            break;
                        case "620":
                            System.out.println("Client received 620 " + msg);
                            dataReformat(cm.getCode(), msg);
                            reGenerateAppView();
                            break;
                        case "710":
                            System.out.println("Client received 710 " + msg);
                            ChatRoomView c = initChatRoomView(msg);
                            int rid = getRidfromData(msg);
                            controller.addChatRoomView(rid,c);
                            c.setVisible(true);
                            break;

                    }
                } catch (IOException e) {
                    //AppendText("ois.readObject() error");
                    try {
//						dos.close();
//						dis.close();
                        ois.close();
                        oos.close();
                        socket.close();

                        break;
                    } catch (Exception ee) {
                        break;
                    } // catch문 끝
                } // 바깥 catch문끝

            }
        }

    }



//	// keyboard enter key 치면 서버로 전송
//	class TextSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Send button을 누르거나 메시지 입력하고 Enter key 치면
//			if (e.getSource() == btnSend || e.getSource() == txtInput) {
//				String msg = null;
//				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
//				msg = txtInput.getText();
//				SendMessage(msg);
//				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
//				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
//				if (msg.contains("/exit")) // 종료 처리
//					System.exit(0);
//			}
//		}
//	}

//	class ImageSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
//			if (e.getSource() == imgBtn) {
//				frame = new Frame("이미지첨부");
//				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
//				// frame.setVisible(true);
//				// fd.setDirectory(".\\");
//				fd.setVisible(true);
//				//System.out.println(fd.getDirectory() + fd.getFile());
//				ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
//				ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
//				obcm.setImg(img);
//				SendObject(obcm);
//			}
//		}
//	}
//
//	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
//
//	public void AppendIcon(ImageIcon icon) {
//		int len = textArea.getDocument().getLength();
//		// 끝으로 이동
//		textArea.setCaretPosition(len);
//		textArea.insertIcon(icon);
//	}
//
//	// 화면에 출력
//	public void AppendText(String msg) {
//		// textArea.append(msg + "\n");
//		//AppendIcon(icon1);
//		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
//		int len = textArea.getDocument().getLength();
//		// 끝으로 이동
//		textArea.setCaretPosition(len);
//		textArea.replaceSelection(msg + "\n");
//	}
//
//	public void AppendImage(ImageIcon ori_icon) {
//		int len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len); // place caret at the end (with no selection)
//		Image ori_img = ori_icon.getImage();
//		int width, height;
//		double ratio;
//		width = ori_icon.getIconWidth();
//		height = ori_icon.getIconHeight();
//		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
//		if (width > 200 || height > 200) {
//			if (width > height) { // 가로 사진
//				ratio = (double) height / width;
//				width = 200;
//				height = (int) (width * ratio);
//			} else { // 세로 사진
//				ratio = (double) width / height;
//				height = 200;
//				width = (int) (height * ratio);
//			}
//			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//			ImageIcon new_icon = new ImageIcon(new_img);
//			textArea.insertIcon(new_icon);
//		} else
//			textArea.insertIcon(ori_icon);
//		len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len);
//		textArea.replaceSelection("\n");
//		// ImageViewAction viewaction = new ImageViewAction();
//		// new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
//	}

    // Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
    public byte[] MakePacket(String msg) {
        byte[] packet = new byte[BUF_LEN];
        byte[] bb = null;
        int i;
        for (i = 0; i < BUF_LEN; i++)
            packet[i] = 0;
        try {
            bb = msg.getBytes("euc-kr");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        for (i = 0; i < bb.length; i++)
            packet[i] = bb[i];
        return packet;
    }

    // Server에게 network으로 전송
	/*public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			//AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}*/

//    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
//        try {
//            oos.writeObject(ob);
//        } catch (IOException e) {
//            // textArea.append("메세지 송신 에러!!\n");
//            //AppendText("SendObject Error");
//        }
//    }

    //내가 짠 함수 들

    /**
     * userName,socket 정보를 받아 LoginView -> AppView 전환 & ListenNetwork Thread run() 하는 함수
     *
     * @param username 유저 이름
     * @param ip_addr  접속할 서버 주소
     * @param port_no  접속할 포트 번호
     */
    public void ChangeLoginViewToAppView(String username, String ip_addr, String port_no) {
        ListenNetwork net = new ListenNetwork();
        //controller.setNet(net);
        net.start();
        App appView = new App(username, ip_addr, port_no);
        controller.setAppView(appView);
        controller.loginView.setVisible(false);
        controller.appView.setVisible(true);
    }

    /***
     * LocalTime 형 변수를 받아서 원하는 형식으로 변환 후 변환한 형식의 문자열로 반환해주는 함수
     * @param time 변환 할 LocalTime 형 변수
     * @return 원하는 형식으로 변환한 문자열
     */
    public String DateToString(LocalTime time) {
        String formatedNow = time.format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("ko")));
        return formatedNow;
    }

    /***
     * Controller의 AppView를 갱신을 위해 AppView 삭제 후 같은 위치에 재생성하는 함수
     */
    public void reGenerateAppView() {
        if (!(controller.appView == null)) {
            Point positon = controller.appView.getLocation();
            controller.appView.dispose();
            controller.setAppView(new App(controller.getUser().getUserName(), controller.ip_addr, controller.port_no));
            controller.appView.setLocation(positon);
            controller.appView.setVisible(true);
        }
    }
    /***
     *  110 protocol로 받은 문자열 userData로 controller의 User를 세팅하는 함수
     * @param data 110 protocol로 받은 문자열 userData
     */
    public void dataSetUser(String data) {
        data = removeProtocolString(data);
        String[] userData = data.split(","); // 0=uid, 1=state, 2=RoomAuth, 3=userName, 4=ImgPath

        ArrayList<Integer> roomAuth = getArrayListFromAuthString(userData[2]);


        User user = new User.UserBuilder().
                setUid(Integer.parseInt(userData[0])).
                setState(userData[1]).
                setRoomAuth(roomAuth).
                setUserName(userData[3]).
                setImg(new ImageIcon(userData[4])).
                build();

        controller.setUser(user);
    }
    /***
     * 문자열 Data 앞에 protocol 코드 + 공백 제거=> ex.) [Server],600 제거 해주는 함수
     * @param data 제거 하기 전 String data
     * @return 제거 된 String data
     */
    public String removeProtocolString(String data) {
        // 앞에 protocol 코드 + 공백 제거=> ex.) [Server],600 제거
        String[] deleteTarget = data.split(" ");
        data = data.substring(deleteTarget[0].length() + 1);
        return data;
    }
    /**
     * 서버에서 받은 문자열로 UserList,RoomList,RoomAuth ..으로 변환하는 함수
     * 600,610,620 프로토콜로 받은 모든 문자열 적용 가능
     *
     * @param data Server에서 받은 데이터
     */
    public void dataReformat(String code, String data) {
        //data = data + " "; //[SERVER] 0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2 | roomList

        data = removeProtocolString(data);

        if (code.equals("600")) {
            //UserList 문자열과 RoomList 문자열로 구분
            String[] receivedData = data.split(DivString.regxListDiv);
            //receivedData[0] = userList , receivedData[1] = RoomList

            //RoomList가 존재하면 UserList,RoomList 둘 다 세팅
            if (receivedData.length > 1) {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                String stringRoomList = receivedData[1]; //0:0<_^$%#[0.2]<_^$%#ABCD<_^$%#<1-_%^#@더미채팅 ㅓㅐㅓㅐㅓ-_%^#@sdfsdf>$#@1:1<_^$%#[0.1]<_^$%#BDCD<_^$%#<1-_%^#@더미채팅 ㅓㅐㅓㅐㅓ-_%^#@sdfsdf>$#@2:2<_^$%#[1.2]<_^$%#DCFF<_^$%#<1-_%^#@더미채팅 ㅓㅐㅓㅐㅓ-_%^#@sdfsdf>$#@

                //UserList 문자열을 데이터 형식으로 변환
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);
                //RoomList 문자열을 데이터 형식으로 변환
                Map<Integer, Room> roomList = StringDatatoRoomList(stringRoomList);

                controller.setUserList(userList);
                controller.setRoomList(roomList);


            }
            //RoomList가 존재 하지 않으면 UserList만 세팅
            else {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                //UserList 문자열을 데이터 형식으로 변환
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);

                controller.setUserList(userList);
            }
        }
        if (code.equals("610")) {
            //UserList 문자열을 데이터 형식으로 변환
            Map<Integer, User> userList = StringDatatoUserList(data);
            controller.setUserList(userList);
        }
        if (code.equals("620")) {
            //RoomList 문자열을 데이터 형식으로 변환
            Map<Integer, Room> roomList = StringDatatoRoomList(data);
            controller.setRoomList(roomList);
        }
    }


    /**
     * String으로 된 UserList를 보내면 Map<Integer, User> UserList로 변환해서 반환해주는 함수
     *
     * @param data 변환 하기전 String으로 된 UserList를 보내면
     * @return Map<Integer, User> UserList
     */
    public Map<Integer, User> StringDatatoUserList(String data) { // NULL 처리 필요!
        String[] StringUserListData = data.split(" ");
        Map<Integer, User> userList = new HashMap<Integer, User>(); //Map<Integer, User> <- User 삽입 하기위해 생성

        // User 생성 후 UserList에 삽입
        for (String s : StringUserListData) {
            // User 생성을 위한 String & ArrayList 들 생성
            String[] deleteTarget = s.split(":");
            s = s.substring(deleteTarget[0].length() + 1); // 앞에 Map의 Key : 값 제거 ex) 1:

            // stringUserData  생성
            String[] stringUserData = s.split(","); // 0 = uid, 1 = state , 2 = RoomAuth , 3 = userName, 4 = img
            String stringRoomAuth = stringUserData[2];

            ArrayList<Integer> roomAuth = getArrayListFromAuthString(stringRoomAuth);

            // ImgIcon setting 용 File 생성
            File file = new File(stringUserData[4]);

            // user 생성
            User user = User.UserBuilder.anUser().
                    setUid(Integer.parseInt(stringUserData[0])).
                    setState(stringUserData[1]).
                    setRoomAuth(roomAuth).
                    setUserName(stringUserData[3]).
                    setImg(new ImageIcon(file.getPath())).
                    build();

            // userList에 생성한 user 삽입
            userList.put(user.getUid(), user);
        }
        return userList;
    }


    /***
     * String으로 된 RoomList를 보내면 Map<Integer, Room> RoomList로 변환해서 반환해주는 함수
     * @param data 변환하기 전 String으로 된 RoomList
     * @return Map<Integer, Room> RoomList
     */
    public Map<Integer, Room> StringDatatoRoomList(String data) {
        String[] StringRoomListData = data.split(DivString.regxRoomListDiv); // Room 별로 분할
        Map<Integer, Room> roomList = new HashMap<Integer, Room>(); // 반환할 room 변수

        //Room 생성 후 삽입
        for (String s : StringRoomListData) {
            // Room 생성을 위한 String & ArrayList 들 생성
            // 앞에 Map의 Key : 값 제거 ex) 1:
            s = s.substring(s.indexOf(":") + 1);

            // stringRoomData  생성
            String[] stringRoomData = s.split(DivString.regxRoomDiv); // 0 = rid, 1 = userAuth , 2 = roomName , 3 = Chat

            //userAuth 처리
            ArrayList<Integer> userAuth = getArrayListFromAuthString(stringRoomData[1]);

            //Chat 처리
            ArrayList<Chat> chatArrayList = new ArrayList<Chat>();
            // ArrayList<Integer> Chat에 넣기 위한 data reformate(앞뒤[]제거)
            String stringChat = stringRoomData[3];
            stringChat = deleteCharStarEnd(stringChat);

            String[] stringChatList = stringChat.split(DivString.regxChatListDiv);

            //Chat이 존재하면
            if (!stringChatList[0].isEmpty()) {
                for (String rb : stringChatList) {
                    String[] stringChatDataArray = rb.split(DivString.regxChatDiv); //0 = uid, 1 = msg , 2 = date
                    Chat chat = Chat.ChatBuilder.aChat().
                            setUid(Integer.parseInt(stringChatDataArray[0])).
                            setMsg(stringChatDataArray[1]).
                            setDate(stringChatDataArray[2]).
                            build();
                    chatArrayList.add(chat);
                }
            }


            // Room 생성
            Room room = Room.RoomBuilder.aRoom().setRid(Integer.parseInt(stringRoomData[0])).
                    setUserAuth(userAuth).
                    setRoomName(stringRoomData[2]).
                    setChat(chatArrayList).
                    build();

            // userList에 생성한 user 삽입
            roomList.put(room.getRid(), room);
        }
        return roomList;
    }

    /***
     * Stirng data 를 받아서 앞 뒤 문자를 제거해주는 함수
     * @param data
     * @return
     */
    public String deleteCharStarEnd(String data) {
        StringBuffer str = new StringBuffer(data);
        str.deleteCharAt(0);
        str.deleteCharAt(str.length() - 1);
        data = str.toString();
        return data;
    }

    /**
     * MakeChatRoomView 에서 선택한 UserName으로 선택한 ArrayList를 받아 Uid ArrayList로 반환해주는 함수
     *
     * @param selectionList 변환 할 userName ArrayList
     * @return ArrayList<Integer> Uid로 변환한 ArrayList
     */
    public ArrayList<Integer> uesrNameToUserID(ArrayList<String> selectionList) {

        // return 할 데이터
        ArrayList<Integer> uidList = new ArrayList<Integer>();

        //Server에서 UserList를 받을때 까지 대기
        while (controller.UserList.isEmpty()) ;

        // UserName => Uid로 변환
        for (Integer key : controller.UserList.keySet()) {
            int index = 0;
            while (index < selectionList.size()) {
                if (controller.UserList.get(key).getUserName().equals(selectionList.get(index))) {
                    uidList.add(controller.UserList.get(key).getUid());
                    break;
                }
                index++;
            }
        }
        return uidList;
    }

    /***
     * 권한 형식으로 되어 있는 문자열 ex) [1.2.3] 을 받아 ArrayList<Integer> 형식으로 변환해주는 함수
     * @param stringAuthData 권한 형식으로 되어 있는 문자열 ex) [1.2.3]
     * @return
     */
    public ArrayList<Integer> getArrayListFromAuthString(String stringAuthData) {
        //반환용 빈 ArrayList<Integer> 선언
        ArrayList<Integer> Auth = new ArrayList<Integer>();

        // ArrayList<Integer> Auth에 넣기 위한 data reformate(앞뒤[]제거)
        stringAuthData = deleteCharStarEnd(stringAuthData);

        //stringRoomAuth ","로 분할
        String[] eachAuth = stringAuthData.split("\\.");

        // Auth가 존재하면 setting
        if (!eachAuth[0].isEmpty()) {
            for (String ra : eachAuth) {
                Auth.add(Integer.parseInt(ra));
            }
        }
        return Auth;
    }

    /**
     * 710 Protocol data를 받아서 ChatRoomView를 초기화 하는 함수
     * @param data 서버에서 710으로 수신받은 문자열 데이터
     */
    public ChatRoomView initChatRoomView(String data) {
        //protocol 코드 제거 [server]
        data = removeProtocolString(data);
        String[] roomData = data.split(","); // 0= Rid, 1= roomName, 2= userAuth

        //user 인원 수 세기
        String userAuth = deleteCharStarEnd(roomData[2]); // 앞뒤 [] 제거
        int userNum = 0;
        String[] num = userAuth.split("\\.");
        for(String s : num)
            userNum++;

        ChatRoomView c = new ChatRoomView(roomData[1],Integer.toString(userNum));
        return c;
    }

    /***
     * 710 Protocol data를 받아서 RoomID를 반환하는 함수
     * @param data 서버에서 710으로 수신받은 문자열 데이터
     * @return
     */
    public int getRidfromData(String data) {
        //protocol 코드 제거 [server]
        data = removeProtocolString(data);
        String[] roomData = data.split(","); // 0= Rid, 1= roomName, 2= userAuth

        return Integer.parseInt(roomData[0]);
    }
    public int getRidfromRoomName(String rName){
        for(Integer i : controller.chatRoomViewList.keySet()){
            if(chatRoomViewList.get(i).getLblRoomName().getText().equals(rName))
                return i;
        }
        return 0;
    }

}

