
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Map;

import Object.*;


public class JavaObjClientMainViewController {
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
    private Socket socket; // 연결소켓
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user; // User Object 정의
    private Map<Integer, User> UserList; // 현재 접속 중인 UserList
    private Map<Integer, Room> RoomList; // 현재 존재 하는 RoomList

    private static JavaObjClientMainViewController controller; // Singleton Pattern 적용

    // Singleton pattern 시작
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
    // Getter & Setter 끝

    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
        try {
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
        loginView.setVisible(true);// 처음 실행되면 Login 창을 생성함, userName,ip_Addr,portNo 설정

        //SendMessage("/login " + UserName);

        //SendObject(obcm);

        ListenNetwork net = new ListenNetwork();
        net.start();
    }

    public JavaObjClientMainViewController(String username, String ip_addr, String port_no) {
        try {
            socket = new Socket(ip_addr, Integer.parseInt(port_no));


            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            //SendMessage("/login " + UserName);
            //ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
            //SendObject(obcm);

            ListenNetwork net = new ListenNetwork();
            net.start();
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //AppendText("connect error");
        }

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
                        obcm = ois.readObject(); // 입력을 기다리는 부분
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

}

