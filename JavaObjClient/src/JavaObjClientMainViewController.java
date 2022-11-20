
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Map;

import Object.*;


public class JavaObjClientMainViewController {
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
    private Socket socket; // �������
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user; // User Object ����
    private Map<Integer, User> UserList; // ���� ���� ���� UserList
    private Map<Integer, Room> RoomList; // ���� ���� �ϴ� RoomList

    private static JavaObjClientMainViewController controller; // Singleton Pattern ����

    // Singleton pattern ����
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
    // Getter & Setter ��

    public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
        try {
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
        loginView.setVisible(true);// ó�� ����Ǹ� Login â�� ������, userName,ip_Addr,portNo ����

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


    // Server Message�� �����ؼ� ȭ�鿡 ǥ��
    class ListenNetwork extends Thread {
        public void run() {
            while (true) {
                try {
                    Object obcm = null;
                    String msg = null;
                    ChatMsg cm;
                    try {
                        obcm = ois.readObject(); // �Է��� ��ٸ��� �κ�
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

}

