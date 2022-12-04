
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â

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
    private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
    private Socket socket; // �������
    private ObjectInputStream ois; // �Է¿� ��ü
    private ObjectOutputStream oos; // ��¿� ��ü
    private User user; // �����ϰ� �ִ� User ����
    private Map<Integer, User> UserList = new HashMap<>(); // ���� ���� ���� UserList
    private Map<Integer, Room> RoomList = new HashMap<>(); // ���� ���� �ϴ� RoomList
    private LoginView loginView; // LoginView
    private App appView; // AppView(MainView)
    private Map<Integer, ChatRoomView> chatRoomViewList = new HashMap<Integer, ChatRoomView>(); // ChatRoomViewList Key: Rid Value: ChatRoomView
    private String ip_addr;
    private String port_no;
    //private ListenNetwork net ;
    private static JavaObjClientMainViewController controller; // Singleton Pattern ����

    // Singleton pattern ���� (������)
    private JavaObjClientMainViewController() {
    }

    public static JavaObjClientMainViewController getInstance() {
        if (controller == null) {
            controller = new JavaObjClientMainViewController(); // Main���� ó�� ������
        }
        return controller;
    }
    // Singleton pattern ��

    // Getter & Setter ����
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

    // Getter & Setter ��

    //Controller setting methods ����


    /**
     * chatRoomView�� �޾Ƽ� controller�� chatRoomViewList �� ���� �߰��ϴ� method
     *
     * @param chatRoomView �߰��� View
     */
    public void addChatRoomView(Integer rid,ChatRoomView chatRoomView) {
        this.chatRoomViewList.put(rid, chatRoomView);
    }


    public void increaseChatRoomIndex(int data) {
    }

    public void increaseMakeChatRoomIndex(int data) {
    }

    //Controller setting methods ��

    public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
        try {
            ChatMsg cm = (ChatMsg) ob;
            System.out.println("client send " + cm.getCode() + " " + cm.getId() + " " + cm.getData());
            oos.writeObject(ob);
        } catch (IOException e) {
            // textArea.append("�޼��� �۽� ����!!\n");
            //AppendText("SendObject Error");
        }
    }

    /**
     * Controller�� �����
     */
    public void activate() {
        LoginView loginView = new LoginView();
        setLoginView(loginView);
        loginView.setVisible(true);// ó�� ����Ǹ� Login â�� ������, userName,ip_Addr,portNo ����
    }

    // Server Message�� �����ؼ� ȭ�鿡 ǥ��
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
                        case "300": // Image ÷��
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
                    } // catch�� ��
                } // �ٱ� catch����

            }
        }

    }



//	// keyboard enter key ġ�� ������ ����
//	class TextSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
//			if (e.getSource() == btnSend || e.getSource() == txtInput) {
//				String msg = null;
//				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
//				msg = txtInput.getText();
//				SendMessage(msg);
//				txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
//				txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
//				if (msg.contains("/exit")) // ���� ó��
//					System.exit(0);
//			}
//		}
//	}

//	class ImageSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
//			if (e.getSource() == imgBtn) {
//				frame = new Frame("�̹���÷��");
//				fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
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
//		// ������ �̵�
//		textArea.setCaretPosition(len);
//		textArea.insertIcon(icon);
//	}
//
//	// ȭ�鿡 ���
//	public void AppendText(String msg) {
//		// textArea.append(msg + "\n");
//		//AppendIcon(icon1);
//		msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
//		int len = textArea.getDocument().getLength();
//		// ������ �̵�
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
//		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
//		if (width > 200 || height > 200) {
//			if (width > height) { // ���� ����
//				ratio = (double) height / width;
//				width = 200;
//				height = (int) (width * ratio);
//			} else { // ���� ����
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
//		// new_icon.addActionListener(viewaction); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
//	}

    // Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
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

    // Server���� network���� ����
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

//    public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
//        try {
//            oos.writeObject(ob);
//        } catch (IOException e) {
//            // textArea.append("�޼��� �۽� ����!!\n");
//            //AppendText("SendObject Error");
//        }
//    }

    //���� § �Լ� ��

    /**
     * userName,socket ������ �޾� LoginView -> AppView ��ȯ & ListenNetwork Thread run() �ϴ� �Լ�
     *
     * @param username ���� �̸�
     * @param ip_addr  ������ ���� �ּ�
     * @param port_no  ������ ��Ʈ ��ȣ
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
     * LocalTime �� ������ �޾Ƽ� ���ϴ� �������� ��ȯ �� ��ȯ�� ������ ���ڿ��� ��ȯ���ִ� �Լ�
     * @param time ��ȯ �� LocalTime �� ����
     * @return ���ϴ� �������� ��ȯ�� ���ڿ�
     */
    public String DateToString(LocalTime time) {
        String formatedNow = time.format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("ko")));
        return formatedNow;
    }

    /***
     * Controller�� AppView�� ������ ���� AppView ���� �� ���� ��ġ�� ������ϴ� �Լ�
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
     *  110 protocol�� ���� ���ڿ� userData�� controller�� User�� �����ϴ� �Լ�
     * @param data 110 protocol�� ���� ���ڿ� userData
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
     * ���ڿ� Data �տ� protocol �ڵ� + ���� ����=> ex.) [Server],600 ���� ���ִ� �Լ�
     * @param data ���� �ϱ� �� String data
     * @return ���� �� String data
     */
    public String removeProtocolString(String data) {
        // �տ� protocol �ڵ� + ���� ����=> ex.) [Server],600 ����
        String[] deleteTarget = data.split(" ");
        data = data.substring(deleteTarget[0].length() + 1);
        return data;
    }
    /**
     * �������� ���� ���ڿ��� UserList,RoomList,RoomAuth ..���� ��ȯ�ϴ� �Լ�
     * 600,610,620 �������ݷ� ���� ��� ���ڿ� ���� ����
     *
     * @param data Server���� ���� ������
     */
    public void dataReformat(String code, String data) {
        //data = data + " "; //[SERVER] 0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2 | roomList

        data = removeProtocolString(data);

        if (code.equals("600")) {
            //UserList ���ڿ��� RoomList ���ڿ��� ����
            String[] receivedData = data.split(DivString.regxListDiv);
            //receivedData[0] = userList , receivedData[1] = RoomList

            //RoomList�� �����ϸ� UserList,RoomList �� �� ����
            if (receivedData.length > 1) {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                String stringRoomList = receivedData[1]; //0:0<_^$%#[0.2]<_^$%#ABCD<_^$%#<1-_%^#@����ä�� �ä��ä���-_%^#@sdfsdf>$#@1:1<_^$%#[0.1]<_^$%#BDCD<_^$%#<1-_%^#@����ä�� �ä��ä���-_%^#@sdfsdf>$#@2:2<_^$%#[1.2]<_^$%#DCFF<_^$%#<1-_%^#@����ä�� �ä��ä���-_%^#@sdfsdf>$#@

                //UserList ���ڿ��� ������ �������� ��ȯ
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);
                //RoomList ���ڿ��� ������ �������� ��ȯ
                Map<Integer, Room> roomList = StringDatatoRoomList(stringRoomList);

                controller.setUserList(userList);
                controller.setRoomList(roomList);


            }
            //RoomList�� ���� ���� ������ UserList�� ����
            else {
                String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
                //UserList ���ڿ��� ������ �������� ��ȯ
                Map<Integer, User> userList = StringDatatoUserList(stringUserList);

                controller.setUserList(userList);
            }
        }
        if (code.equals("610")) {
            //UserList ���ڿ��� ������ �������� ��ȯ
            Map<Integer, User> userList = StringDatatoUserList(data);
            controller.setUserList(userList);
        }
        if (code.equals("620")) {
            //RoomList ���ڿ��� ������ �������� ��ȯ
            Map<Integer, Room> roomList = StringDatatoRoomList(data);
            controller.setRoomList(roomList);
        }
    }


    /**
     * String���� �� UserList�� ������ Map<Integer, User> UserList�� ��ȯ�ؼ� ��ȯ���ִ� �Լ�
     *
     * @param data ��ȯ �ϱ��� String���� �� UserList�� ������
     * @return Map<Integer, User> UserList
     */
    public Map<Integer, User> StringDatatoUserList(String data) { // NULL ó�� �ʿ�!
        String[] StringUserListData = data.split(" ");
        Map<Integer, User> userList = new HashMap<Integer, User>(); //Map<Integer, User> <- User ���� �ϱ����� ����

        // User ���� �� UserList�� ����
        for (String s : StringUserListData) {
            // User ������ ���� String & ArrayList �� ����
            String[] deleteTarget = s.split(":");
            s = s.substring(deleteTarget[0].length() + 1); // �տ� Map�� Key : �� ���� ex) 1:

            // stringUserData  ����
            String[] stringUserData = s.split(","); // 0 = uid, 1 = state , 2 = RoomAuth , 3 = userName, 4 = img
            String stringRoomAuth = stringUserData[2];

            ArrayList<Integer> roomAuth = getArrayListFromAuthString(stringRoomAuth);

            // ImgIcon setting �� File ����
            File file = new File(stringUserData[4]);

            // user ����
            User user = User.UserBuilder.anUser().
                    setUid(Integer.parseInt(stringUserData[0])).
                    setState(stringUserData[1]).
                    setRoomAuth(roomAuth).
                    setUserName(stringUserData[3]).
                    setImg(new ImageIcon(file.getPath())).
                    build();

            // userList�� ������ user ����
            userList.put(user.getUid(), user);
        }
        return userList;
    }


    /***
     * String���� �� RoomList�� ������ Map<Integer, Room> RoomList�� ��ȯ�ؼ� ��ȯ���ִ� �Լ�
     * @param data ��ȯ�ϱ� �� String���� �� RoomList
     * @return Map<Integer, Room> RoomList
     */
    public Map<Integer, Room> StringDatatoRoomList(String data) {
        String[] StringRoomListData = data.split(DivString.regxRoomListDiv); // Room ���� ����
        Map<Integer, Room> roomList = new HashMap<Integer, Room>(); // ��ȯ�� room ����

        //Room ���� �� ����
        for (String s : StringRoomListData) {
            // Room ������ ���� String & ArrayList �� ����
            // �տ� Map�� Key : �� ���� ex) 1:
            s = s.substring(s.indexOf(":") + 1);

            // stringRoomData  ����
            String[] stringRoomData = s.split(DivString.regxRoomDiv); // 0 = rid, 1 = userAuth , 2 = roomName , 3 = Chat

            //userAuth ó��
            ArrayList<Integer> userAuth = getArrayListFromAuthString(stringRoomData[1]);

            //Chat ó��
            ArrayList<Chat> chatArrayList = new ArrayList<Chat>();
            // ArrayList<Integer> Chat�� �ֱ� ���� data reformate(�յ�[]����)
            String stringChat = stringRoomData[3];
            stringChat = deleteCharStarEnd(stringChat);

            String[] stringChatList = stringChat.split(DivString.regxChatListDiv);

            //Chat�� �����ϸ�
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


            // Room ����
            Room room = Room.RoomBuilder.aRoom().setRid(Integer.parseInt(stringRoomData[0])).
                    setUserAuth(userAuth).
                    setRoomName(stringRoomData[2]).
                    setChat(chatArrayList).
                    build();

            // userList�� ������ user ����
            roomList.put(room.getRid(), room);
        }
        return roomList;
    }

    /***
     * Stirng data �� �޾Ƽ� �� �� ���ڸ� �������ִ� �Լ�
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
     * MakeChatRoomView ���� ������ UserName���� ������ ArrayList�� �޾� Uid ArrayList�� ��ȯ���ִ� �Լ�
     *
     * @param selectionList ��ȯ �� userName ArrayList
     * @return ArrayList<Integer> Uid�� ��ȯ�� ArrayList
     */
    public ArrayList<Integer> uesrNameToUserID(ArrayList<String> selectionList) {

        // return �� ������
        ArrayList<Integer> uidList = new ArrayList<Integer>();

        //Server���� UserList�� ������ ���� ���
        while (controller.UserList.isEmpty()) ;

        // UserName => Uid�� ��ȯ
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
     * ���� �������� �Ǿ� �ִ� ���ڿ� ex) [1.2.3] �� �޾� ArrayList<Integer> �������� ��ȯ���ִ� �Լ�
     * @param stringAuthData ���� �������� �Ǿ� �ִ� ���ڿ� ex) [1.2.3]
     * @return
     */
    public ArrayList<Integer> getArrayListFromAuthString(String stringAuthData) {
        //��ȯ�� �� ArrayList<Integer> ����
        ArrayList<Integer> Auth = new ArrayList<Integer>();

        // ArrayList<Integer> Auth�� �ֱ� ���� data reformate(�յ�[]����)
        stringAuthData = deleteCharStarEnd(stringAuthData);

        //stringRoomAuth ","�� ����
        String[] eachAuth = stringAuthData.split("\\.");

        // Auth�� �����ϸ� setting
        if (!eachAuth[0].isEmpty()) {
            for (String ra : eachAuth) {
                Auth.add(Integer.parseInt(ra));
            }
        }
        return Auth;
    }

    /**
     * 710 Protocol data�� �޾Ƽ� ChatRoomView�� �ʱ�ȭ �ϴ� �Լ�
     * @param data �������� 710���� ���Ź��� ���ڿ� ������
     */
    public ChatRoomView initChatRoomView(String data) {
        //protocol �ڵ� ���� [server]
        data = removeProtocolString(data);
        String[] roomData = data.split(","); // 0= Rid, 1= roomName, 2= userAuth

        //user �ο� �� ����
        String userAuth = deleteCharStarEnd(roomData[2]); // �յ� [] ����
        int userNum = 0;
        String[] num = userAuth.split("\\.");
        for(String s : num)
            userNum++;

        ChatRoomView c = new ChatRoomView(roomData[1],Integer.toString(userNum));
        return c;
    }

    /***
     * 710 Protocol data�� �޾Ƽ� RoomID�� ��ȯ�ϴ� �Լ�
     * @param data �������� 710���� ���Ź��� ���ڿ� ������
     * @return
     */
    public int getRidfromData(String data) {
        //protocol �ڵ� ���� [server]
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

