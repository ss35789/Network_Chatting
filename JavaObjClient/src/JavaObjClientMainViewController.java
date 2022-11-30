
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Object.User;
import Object.Room;

import javax.swing.*;

import static java.sql.Types.NULL;


public class JavaObjClientMainViewController {
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
    private Socket socket; // 연결소켓
    private ObjectInputStream ois; // 입력용 객체
    private ObjectOutputStream oos; // 출력용 객체
    private User user; // User Object 정의
    private Map<Integer, User> UserList = new HashMap<>(); // 현재 접속 중인 UserList
    private Map<Integer, Room> RoomList = new HashMap<>(); // 현재 존재 하는 RoomList
    private LoginView loginView; // LoginView
    private App appView; // AppView(MainView)
    private Map<Integer, ChatRoomView> chatRoomViewList = new HashMap<Integer, ChatRoomView>(); // ChatRoomViewList
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

    public void setUser(String username) {
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

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setAppView(App appView) {
        this.appView = appView;
    }

    public Map<Integer, ChatRoomView> getChatRoomViewList() {
        return chatRoomViewList;
    }

    // Getter & Setter 끝

    //Controller setting methods 시작


    /**
     * chatRoomView를 받아서 controller의 chatRoomViewList 맨 끝에 추가하는 method
     *
     * @param chatRoomView 추가할 View
     */
    public void addChatRoomView(ChatRoomView chatRoomView) {
        this.chatRoomViewList.put(chatRoomViewList.size() + 1, chatRoomView);
    }


    public void increaseChatRoomIndex(int data) {
    }

    public void increaseMakeChatRoomIndex(int data) {
    }

    //Controller setting methods 끝

    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
        try {
            ChatMsg cm = (ChatMsg)ob;
            String msg = String.format("[%s] %s", cm.getId(), cm.getData());
            System.out.println("client send " + msg );
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
//        ListenNetwork net = new ListenNetwork();
//        net.start();
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
                        case "200": // chat message
                            //AppendText(msg);
                            break;
                        case "300": // Image 첨부
                            //AppendText("[" + cm.getId() + "]");
                            //AppendImage(cm.img);
                            break;
                        case "600":
                            System.out.println("Client received " + msg);
                            dataReformat(msg);
                            System.out.println("controller UserList" + controller.UserList);
                            break;
                        case "610":
                            System.out.println("Client received " + msg);
                            dataReformat(msg);
                            System.out.println("controller.UserList" + controller.UserList);
                            break;
                        case "620":
                            System.out.println("Client received " + msg);
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
        net.start();
        App appView = new App(username, ip_addr, port_no);
        controller.setAppView(appView);
        controller.loginView.setVisible(false);
        controller.appView.setVisible(true);
    }

    /**
     * 서버에서 받은 문자열로 UserList,RoomList,RoomAuth ..으로 변환하는 함수
     *
     * @param data Server에서 받은 데이터
     */
    public void dataReformat(String data) {
        data = data + " "; //[SERVER] 0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2 | roomList
        String[] deleteTarget = data.split(" ");
        data = data.substring(deleteTarget[0].length() + 1); // 앞에 protocol 코드 + 공백 제거=> ex.) [Server],600 제거
        String[] receivedData = data.split("\\|");
        String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
        //String stringRoomList = receivedData[1]; // roomList
        Map<Integer, User> userList = StringDatatoUserList(stringUserList);
//      Map<Integer, Room> RoomList = StringDatatoRoomList(stringRoomList);
        controller.setUserList(userList);
//            controller.setUserList(userList);
    }

    /**
     * String으로 된 UserList를 보내면 Map<Integer, User> UserList로 변환해서 반환해주는 함수
     *
     * @param data String으로 된 UserList를 보내면
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

            ArrayList<Integer> roomAuth = new ArrayList<Integer>();
            // ArrayList<Integer> roomAuth에 넣기 위한 data reformate(앞뒤[]제거)
            String stringRoomAuth = stringUserData[2];
            StringBuffer str = new StringBuffer(stringRoomAuth);
            stringRoomAuth = str.delete(0, stringRoomAuth.length()).toString();

            //stringRoomAuth ","로 분할
            String[] stringRoomAuthRid = stringRoomAuth.split(",");

            // RoomAuth가 존재하면 setting
            if (!stringRoomAuthRid[0].isEmpty()) {
                for (String ra : stringRoomAuthRid) {
                    roomAuth.add(Integer.parseInt(ra));
                }
            }

            // ImgIcon setting 용 File 생성 (앞에 \ 문자 제거)
            File file = new File(stringUserData[4].substring(1));

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

    public Map<Integer, Room> StringDatatoRoomList(String data) {
        Map<Integer, Room> room = new HashMap<Integer, Room>();
        return room;
    }

    /**
     * MakeChatRoomView 에서 선택한 UserName으로 선택한 ArrayList를 받아 Uid ArrayList로 반환해주는 함수
     *
     * @param selectionList
     * @return
     */
    public ArrayList<Integer> uesrNameToUserID(ArrayList<String> selectionList) {

        // return 할 데이터
        ArrayList<Integer> uidList = new ArrayList<Integer>();

        //Server에서 UserList를 받을때 까지 대기
        while (controller.UserList.isEmpty()) ;

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
}

