
// JavaObjClientView.java ObjecStram 奄鋼 Client
//叔霜旋昔 辰特 但

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

import static java.sql.Types.NULL;


public class JavaObjClientMainViewController {
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128; // Windows 坦軍 BUF_LEN 聖 舛税
    private Socket socket; // 尻衣社掴
    private ObjectInputStream ois; // 脊径遂 梓端
    private ObjectOutputStream oos; // 窒径遂 梓端
    private User user; // 羨紗馬壱 赤澗 User 沙昔
    private Map<Integer, User> UserList = new HashMap<>(); // 薄仙 羨紗 掻昔 UserList
    private Map<Integer, Room> RoomList = new HashMap<>(); // 薄仙 糎仙 馬澗 RoomList
    private LoginView loginView; // LoginView
    private App appView; // AppView(MainView)
    private Map<Integer, ChatRoomView> chatRoomViewList = new HashMap<Integer, ChatRoomView>(); // ChatRoomViewList
    private String username;
    private String ip_addr;
    private String port_no;
    private static JavaObjClientMainViewController controller; // Singleton Pattern 旋遂

    // Singleton pattern 獣拙 (持失切)
    private JavaObjClientMainViewController() {
    }

    public static JavaObjClientMainViewController getInstance() {
        if (controller == null) {
            controller = new JavaObjClientMainViewController(); // Main拭辞 坦製 持失喫
        }
        return controller;
    }
    // Singleton pattern 魁

    // Getter & Setter 獣拙
    public User getUser() {
        return user;
    }

    public Map<Integer, ChatRoomView> getChatRoomViewList() { return chatRoomViewList;}

    public String getUsername() { return username; }

    public String getPort_no() { return port_no;}

    public String getIp_addr() { return ip_addr; }

    public Map<Integer, User> getUserList() {
        return UserList;
    }

    public Map<Integer, Room> getRoomList() {
        return RoomList;
    }

    public void setUser(String username) { user = new User.UserBuilder().setUserName(username).build(); }

    public void setSocket(Socket socket) { this.socket = socket; }

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

    public void setUsername(String username) { this.username = username; }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public void setPort_no(String port_no) {
        this.port_no = port_no;
    }

    // Getter & Setter 魁

    //Controller setting methods 獣拙


    /**
     * chatRoomView研 閤焼辞 controller税 chatRoomViewList 固 魁拭 蓄亜馬澗 method
     *
     * @param chatRoomView 蓄亜拝 View
     */
    public void addChatRoomView(ChatRoomView chatRoomView) {
        this.chatRoomViewList.put(chatRoomViewList.size() + 1, chatRoomView);
    }


    public void increaseChatRoomIndex(int data) {
    }

    public void increaseMakeChatRoomIndex(int data) {
    }

    //Controller setting methods 魁

    public void SendObject(Object ob) { // 辞獄稽 五室走研 左鎧澗 五社球
        try {
            ChatMsg cm = (ChatMsg) ob;
            System.out.println("client send "+cm.getCode() +" "+ cm.getId() + " " + cm.getData());
            oos.writeObject(ob);
        } catch (IOException e) {
            // textArea.append("五室走 勺重 拭君!!\n");
            //AppendText("SendObject Error");
        }
    }

    /**
     * Controller亜 叔楳喫
     */
    public void activate() {
        LoginView loginView = new LoginView();
        setLoginView(loginView);
        loginView.setVisible(true);// 坦製 叔楳鞠檎 Login 但聖 持失敗, userName,ip_Addr,portNo 竺舛
    }

    // Server Message研 呪重背辞 鉢檎拭 妊獣
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
                        case "300": // Image 歎採
                            //AppendText("[" + cm.getId() + "]");
                            //AppendImage(cm.img);
                            break;
                        case "600":
                            System.out.println("Client received 600 " + msg);
                            dataReformat(cm.getCode(),msg);
                            //重鋭 政煽 羨紗 獣 政煽軒什闘 飴重
                            reGenerateAppView();
                            break;
                        case "610":
                            System.out.println("Client received 610" + msg);
                            dataReformat(cm.getCode(),msg);
                            reGenerateAppView();
                            break;
                        case "620":
                            System.out.println("Client received 620 " + msg);
                            dataReformat(cm.getCode(),msg);
                            reGenerateAppView();
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
                    } // catch庚 魁
                } // 郊甥 catch庚魁

            }
        }
    }



//	// keyboard enter key 帖檎 辞獄稽 穿勺
//	class TextSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Send button聖 刊牽暗蟹 五獣走 脊径馬壱 Enter key 帖檎
//			if (e.getSource() == btnSend || e.getSource() == txtInput) {
//				String msg = null;
//				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
//				msg = txtInput.getText();
//				SendMessage(msg);
//				txtInput.setText(""); // 五室走研 左鎧壱 蟹檎 五室走 床澗但聖 搾錘陥.
//				txtInput.requestFocus(); // 五室走研 左鎧壱 朕辞研 陥獣 努什闘 琶球稽 是帖獣轍陥
//				if (msg.contains("/exit")) // 曽戟 坦軒
//					System.exit(0);
//			}
//		}
//	}

//	class ImageSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// 衝芝 戚坤闘亜 sendBtn析凶 暁澗 textField 拭室 Enter key 帖檎
//			if (e.getSource() == imgBtn) {
//				frame = new Frame("戚耕走歎採");
//				fd = new FileDialog(frame, "戚耕走 識澱", FileDialog.LOAD);
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
//		// 魁生稽 戚疑
//		textArea.setCaretPosition(len);
//		textArea.insertIcon(icon);
//	}
//
//	// 鉢檎拭 窒径
//	public void AppendText(String msg) {
//		// textArea.append(msg + "\n");
//		//AppendIcon(icon1);
//		msg = msg.trim(); // 蒋及 blank人 \n聖 薦暗廃陥.
//		int len = textArea.getDocument().getLength();
//		// 魁生稽 戚疑
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
//		// Image亜 格巷 滴檎 置企 亜稽 暁澗 室稽 200 奄層生稽 逐社獣轍陥.
//		if (width > 200 || height > 200) {
//			if (width > height) { // 亜稽 紫遭
//				ratio = (double) height / width;
//				width = 200;
//				height = (int) (width * ratio);
//			} else { // 室稽 紫遭
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
//		// new_icon.addActionListener(viewaction); // 鎧採適掘什稽 衝芝 軒什格研 雌紗閤精 適掘什稽
//	}

    // Windows 坦軍 message 薦須廃 蟹袴走 採歳精 NULL 稽 幻級奄 是廃 敗呪
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

    // Server拭惟 network生稽 穿勺
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

//    public void SendObject(Object ob) { // 辞獄稽 五室走研 左鎧澗 五社球
//        try {
//            oos.writeObject(ob);
//        } catch (IOException e) {
//            // textArea.append("五室走 勺重 拭君!!\n");
//            //AppendText("SendObject Error");
//        }
//    }

    //鎧亜 則 敗呪 級

    /**
     * userName,socket 舛左研 閤焼 LoginView -> AppView 穿発 & ListenNetwork Thread run() 馬澗 敗呪
     *
     * @param username 政煽 戚硯
     * @param ip_addr  羨紗拝 辞獄 爽社
     * @param port_no  羨紗拝 匂闘 腰硲
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
     * 辞獄拭辞 閤精 庚切伸稽 UserList,RoomList,RoomAuth ..生稽 痕発馬澗 敗呪
     * 600,610,620 覗稽塘紬稽 閤精 乞窮 庚切伸 旋遂 亜管
     *
     * @param data Server拭辞 閤精 汽戚斗
     */
    public void dataReformat(String code,String data) {
        //data = data + " "; //[SERVER] 0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2 | roomList

        // 蒋拭 protocol 坪球 + 因拷 薦暗=> ex.) [Server],600 薦暗
        String[] deleteTarget = data.split(" ");
        data = data.substring(deleteTarget[0].length() + 1);

        if(code.equals("600")) {
            //UserList 庚切伸引 RoomList 庚切伸稽 姥歳
            String[] receivedData = data.split(DivString.ListDiv);
            //receivedData[0] = userList , receivedData[1] = RoomList

            //RoomList亜 糎仙馬檎 UserList,RoomList 却 陥 室特
            if (receivedData.length > 1) {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                String stringRoomList = receivedData[1]; //0:0<_^$%#[0.2]<_^$%#ABCD<_^$%#<1-_%^#@希耕辰特 っだっだっ-_%^#@sdfsdf>$#@1:1<_^$%#[0.1]<_^$%#BDCD<_^$%#<1-_%^#@希耕辰特 っだっだっ-_%^#@sdfsdf>$#@2:2<_^$%#[1.2]<_^$%#DCFF<_^$%#<1-_%^#@希耕辰特 っだっだっ-_%^#@sdfsdf>$#@

                //UserList 庚切伸聖 汽戚斗 莫縦生稽 痕発
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);
                //RoomList 庚切伸聖 汽戚斗 莫縦生稽 痕発
                Map<Integer, Room> roomList = StringDatatoRoomList(stringRoomList);

                controller.setUserList(userList);
                controller.setRoomList(roomList);
            }
            //RoomList亜 糎仙 馬走 省生檎 UserList幻 室特
            else {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                //UserList 庚切伸聖 汽戚斗 莫縦生稽 痕発
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);

                controller.setUserList(userList);
            }
        }
        if(code.equals("610")){
            //UserList 庚切伸聖 汽戚斗 莫縦生稽 痕発
            Map<Integer, User> userList = StringDatatoUserList(data);
            controller.setUserList(userList);
        }
        if(code.equals("620")){
            //RoomList 庚切伸聖 汽戚斗 莫縦生稽 痕発
            Map<Integer, Room> roomList = StringDatatoRoomList(data);
            controller.setRoomList(roomList);
        }
    }

    /**
     * String生稽 吉 UserList研 左鎧檎 Map<Integer, User> UserList稽 痕発背辞 鋼発背爽澗 敗呪
     *
     * @param data 痕発 馬奄穿 String生稽 吉 UserList研 左鎧檎
     * @return Map<Integer, User> UserList
     */
    public Map<Integer, User> StringDatatoUserList(String data) { // NULL 坦軒 琶推!
        String[] StringUserListData = data.split(" ");
        Map<Integer, User> userList = new HashMap<Integer, User>(); //Map<Integer, User> <- User 諮脊 馬奄是背 持失

        // User 持失 板 UserList拭 諮脊
        for (String s : StringUserListData) {
            // User 持失聖 是廃 String & ArrayList 級 持失
            String[] deleteTarget = s.split(":");
            s = s.substring(deleteTarget[0].length() + 1); // 蒋拭 Map税 Key : 葵 薦暗 ex) 1:

            // stringUserData  持失
            String[] stringUserData = s.split(","); // 0 = uid, 1 = state , 2 = RoomAuth , 3 = userName, 4 = img

            ArrayList<Integer> roomAuth = new ArrayList<Integer>();
            // ArrayList<Integer> roomAuth拭 隔奄 是廃 data reformate(蒋及[]薦暗)
            String stringRoomAuth = stringUserData[2];
            StringBuffer str = new StringBuffer(stringRoomAuth);
            stringRoomAuth = str.delete(0, stringRoomAuth.length()).toString();

            //stringRoomAuth ","稽 歳拝

            String[] stringRoomAuthRid = stringRoomAuth.split(",");

            // RoomAuth亜 糎仙馬檎 setting
            if (!stringRoomAuthRid[0].isEmpty()) {
                for (String ra : stringRoomAuthRid) {
                    roomAuth.add(Integer.parseInt(ra));
                }
            }

            // ImgIcon setting 遂 File 持失
            File file = new File(stringUserData[4]);

            // user 持失
            User user = User.UserBuilder.anUser().
                    setUid(Integer.parseInt(stringUserData[0])).
                    setState(stringUserData[1]).
                    setRoomAuth(roomAuth).
                    setUserName(stringUserData[3]).
                    setImg(new ImageIcon(file.getPath())).
                    build();

            // userList拭 持失廃 user 諮脊
            userList.put(user.getUid(), user);
        }
        return userList;
    }

    /***
     * String生稽 吉 RoomList研 左鎧檎 Map<Integer, Room> RoomList稽 痕発背辞 鋼発背爽澗 敗呪
     * @param data 痕発馬奄 穿 String生稽 吉 RoomList
     * @return Map<Integer, Room> RoomList
     */
    public Map<Integer, Room> StringDatatoRoomList(String data) {
        String[] StringRoomListData = data.split(DivString.RoomListDiv); // Room 紺稽 歳拝
        Map<Integer, Room> roomList = new HashMap<Integer, Room>(); // 鋼発拝 room 痕呪

        //Room 持失 板 諮脊
        for(String s:StringRoomListData){
            // Room 持失聖 是廃 String & ArrayList 級 持失
            // 蒋拭 Map税 Key : 葵 薦暗 ex) 1:
            s = s.substring(s.indexOf(":")+1);

            // stringRoomData  持失
            String[] stringRoomData = s.split(DivString.RoomDiv); // 0 = rid, 1 = userAuth , 2 = roomName , 3 = Chat

            //userAuth 坦軒
            ArrayList<Integer> userAuth = new ArrayList<Integer>();
            // ArrayList<Integer> roomAuth拭 隔奄 是廃 data reformate(蒋及[]薦暗)
            String stringUserAuth = stringRoomData[1];
            StringBuffer str = new StringBuffer(stringUserAuth);
            str.deleteCharAt(0);
            str.deleteCharAt(str.length()-1);
            stringUserAuth = str.toString();
            //stringRoomAuth "."稽 歳拝
            String[] stringUserAuthRid = stringUserAuth.split("\\.");
            // RoomAuth亜 糎仙馬檎 setting
            if (!stringUserAuthRid[0].isEmpty()) {
                for (String ra : stringUserAuthRid) {
                    userAuth.add(Integer.parseInt(ra));
                }
            }

            //Chat 坦軒
            ArrayList<Chat> chatArrayList = new ArrayList<Chat>();
            // ArrayList<Integer> Chat拭 隔奄 是廃 data reformate(蒋及[]薦暗)
            String stringChat = stringRoomData[3];
            StringBuffer strb = new StringBuffer(stringChat);
            strb.deleteCharAt(0);
            strb.deleteCharAt(strb.length()-1);
            stringChat = strb.toString();
            String[] stringChatList = stringChat .split(DivString.ChatListDiv);

            //Chat戚 糎仙馬檎
            if (!stringChatList[0].isEmpty()) {
                for (String rb : stringChatList) {
                    String[] stringChatDataArray = rb.split(DivString.ChatDiv); //0 = uid, 1 = msg , 2 = date
                    Chat chat = Chat.ChatBuilder.aChat().
                            setUid(Integer.parseInt(stringChatDataArray[0])).
                            setMsg(stringChatDataArray[1]).
                            setDate(stringChatDataArray[2]).
                            build();
                    chatArrayList.add(chat);
                }
            }


            // Room 持失
            Room room = Room.RoomBuilder.aRoom().setRid(Integer.parseInt(stringRoomData[0])).
                    setUserAuth(userAuth).
                    setRoomName(stringRoomData[2]).
                    setChat(chatArrayList).
                    build();

            // userList拭 持失廃 user 諮脊
            roomList.put(room.getRid(), room);
        }
        return roomList;
    }

    /**
     * MakeChatRoomView 拭辞 識澱廃 UserName生稽 識澱廃 ArrayList研 閤焼 Uid ArrayList稽 鋼発背爽澗 敗呪
     *
     * @param selectionList 痕発 拝 userName ArrayList
     * @return ArrayList<Integer> Uid稽 痕発廃 ArrayList
     */
    public ArrayList<Integer> uesrNameToUserID(ArrayList<String> selectionList) {

        // return 拝 汽戚斗
        ArrayList<Integer> uidList = new ArrayList<Integer>();

        //Server拭辞 UserList研 閤聖凶 猿走 企奄
        while (controller.UserList.isEmpty()) ;

        // UserName => Uid稽 痕発
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
     * LocalTime 莫 痕呪研 閤焼辞 据馬澗 莫縦生稽 痕発 板 痕発廃 莫縦税 庚切伸稽 鋼発背爽澗 敗呪
     * @param time 痕発 拝 LocalTime 莫 痕呪
     * @return 据馬澗 莫縦生稽 痕発廃 庚切伸
     */
    public String DateToString(LocalTime time){
        String formatedNow = time.format(DateTimeFormatter.ofPattern("a HH獣 mm歳").withLocale(Locale.forLanguageTag("ko")));
        return "神穿 0:00";
    }

    /***
     * Controller税 AppView研 飴重聖 是背 AppView 肢薦 板 旭精 是帖拭 仙持失馬澗 敗呪
     */
    public void reGenerateAppView() {
        if (!(controller.appView == null)) {
            Point positon = controller.appView.getLocation();
            controller.appView.dispose();
            controller.setAppView(new App(controller.username, controller.ip_addr, controller.username));
            controller.appView.setLocation(positon);
            controller.appView.setVisible(true);
        }
    }
}

