
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â

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
    private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
    private Socket socket; // �������
    private ObjectInputStream ois; // �Է¿� ��ü
    private ObjectOutputStream oos; // ��¿� ��ü
    private User user; // User Object ����
    private Map<Integer, User> UserList = new HashMap<>(); // ���� ���� ���� UserList
    private Map<Integer, Room> RoomList = new HashMap<>(); // ���� ���� �ϴ� RoomList
    private LoginView loginView; // LoginView
    private App appView; // AppView(MainView)
    private Map<Integer, ChatRoomView> chatRoomViewList = new HashMap<Integer, ChatRoomView>(); // ChatRoomViewList
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

    // Getter & Setter ��

    //Controller setting methods ����


    /**
     * chatRoomView�� �޾Ƽ� controller�� chatRoomViewList �� ���� �߰��ϴ� method
     *
     * @param chatRoomView �߰��� View
     */
    public void addChatRoomView(ChatRoomView chatRoomView) {
        this.chatRoomViewList.put(chatRoomViewList.size() + 1, chatRoomView);
    }


    public void increaseChatRoomIndex(int data) {
    }

    public void increaseMakeChatRoomIndex(int data) {
    }

    //Controller setting methods ��

    public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
        try {
            ChatMsg cm = (ChatMsg)ob;
            String msg = String.format("[%s] %s", cm.getId(), cm.getData());
            System.out.println("client send " + msg );
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
//        ListenNetwork net = new ListenNetwork();
//        net.start();
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
                        case "200": // chat message
                            //AppendText(msg);
                            break;
                        case "300": // Image ÷��
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
        net.start();
        App appView = new App(username, ip_addr, port_no);
        controller.setAppView(appView);
        controller.loginView.setVisible(false);
        controller.appView.setVisible(true);
    }

    /**
     * �������� ���� ���ڿ��� UserList,RoomList,RoomAuth ..���� ��ȯ�ϴ� �Լ�
     *
     * @param data Server���� ���� ������
     */
    public void dataReformat(String data) {
        data = data + " "; //[SERVER] 0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2 | roomList
        String[] deleteTarget = data.split(" ");
        data = data.substring(deleteTarget[0].length() + 1); // �տ� protocol �ڵ� + ���� ����=> ex.) [Server],600 ����
        String[] receivedData = data.split("\\|");
        String stringUserList = receivedData[0]; //0:0,Online,[],user1,file 1:1,Offline,[],user10,file 2:2
        //String stringRoomList = receivedData[1]; // roomList
        Map<Integer, User> userList = StringDatatoUserList(stringUserList);
//      Map<Integer, Room> RoomList = StringDatatoRoomList(stringRoomList);
        controller.setUserList(userList);
//            controller.setUserList(userList);
    }

    /**
     * String���� �� UserList�� ������ Map<Integer, User> UserList�� ��ȯ�ؼ� ��ȯ���ִ� �Լ�
     *
     * @param data String���� �� UserList�� ������
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

            ArrayList<Integer> roomAuth = new ArrayList<Integer>();
            // ArrayList<Integer> roomAuth�� �ֱ� ���� data reformate(�յ�[]����)
            String stringRoomAuth = stringUserData[2];
            StringBuffer str = new StringBuffer(stringRoomAuth);
            stringRoomAuth = str.delete(0, stringRoomAuth.length()).toString();

            //stringRoomAuth ","�� ����
            String[] stringRoomAuthRid = stringRoomAuth.split(",");

            // RoomAuth�� �����ϸ� setting
            if (!stringRoomAuthRid[0].isEmpty()) {
                for (String ra : stringRoomAuthRid) {
                    roomAuth.add(Integer.parseInt(ra));
                }
            }

            // ImgIcon setting �� File ���� (�տ� \ ���� ����)
            File file = new File(stringUserData[4].substring(1));

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

    public Map<Integer, Room> StringDatatoRoomList(String data) {
        Map<Integer, Room> room = new HashMap<Integer, Room>();
        return room;
    }

    /**
     * MakeChatRoomView ���� ������ UserName���� ������ ArrayList�� �޾� Uid ArrayList�� ��ȯ���ִ� �Լ�
     *
     * @param selectionList
     * @return
     */
    public ArrayList<Integer> uesrNameToUserID(ArrayList<String> selectionList) {

        // return �� ������
        ArrayList<Integer> uidList = new ArrayList<Integer>();

        //Server���� UserList�� ������ ���� ���
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

