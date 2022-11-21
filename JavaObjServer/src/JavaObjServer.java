//JavaObjServer.java ObjectStream ��� ä�� Server

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaObjServer extends JFrame {

	/**
	 *
	 * fuck develop env
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // ��������
	private Socket client_socket; // accept() ���� ������ client ����
	private Vector UserVec = new Vector(); // ����� ����ڸ� ������ ����
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	private static Map<Integer, User> userList = new HashMap<>();
	private static Map<Integer, Room> roomList = new HashMap<>();
	/**
	 * Launch the application.sed
	 */

	public static ListData getListData(){
		ListData ListData = new ListData(userList, roomList);
		return ListData;
	}
	public static void setListData(ListData ListData){
		userList = ListData.userList;
		roomList = ListData.roomList;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaObjServer frame = new JavaObjServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JavaObjServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");

		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // ������ ���̻� �����Ű�� �� �ϰ� ���´�
				txtPortNumber.setEnabled(false); // ���̻� ��Ʈ��ȣ ������ �ϰ� ���´�
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);

	}

	// ���ο� ������ accept() �ϰ� user thread�� ���� �����Ѵ�.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
				try {

					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
					AppendText("���ο� ������ from " + client_socket);
					// User �� �ϳ��� Thread ����
					UserService new_user = new UserService(client_socket);

					UserVec.add(new_user); // ���ο� ������ �迭�� �߰�
					 // ���� ��ü�� ������ ����
					AppendText("���� ������ �� " + UserVec.size());
					new_user.start();
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("����ڷκ��� ���� �޼��� : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg msg) {
		// textArea.append("����ڷκ��� ���� object : " + str+"\n");
		textArea.append("code = " + msg.getCode() + "\n");
		textArea.append("id = " + msg.getId() + "\n");
		textArea.append("data = " + msg.getData() + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User �� �����Ǵ� Thread
	// Read One ���� ��� -> Write All
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;
		private User user;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private Map<Integer, User> UserList;
		private Map<Integer, Room> RoomList;
		private Socket client_socket;
		private Vector user_vc;
		private String UserName;
		public String UserState;

		//user.UserName �� UserName�� ���� ����
		public void setUser(User user){
			this.user = user;
		}
		public void setListData(){
			ListData ListData =  JavaObjServer.getListData();
			this.UserList = ListData.userList;
			this.RoomList = ListData.roomList;
		}


		public UserService(Socket client_socket) {
			// TODO Auto-generated constructor stub
			// �Ű������� �Ѿ�� �ڷ� ����

			this.client_socket = client_socket;
			this.user_vc = UserVec;

			setListData();
			try {
//				is = client_socket.getInputStream();
//				dis = new DataInputStream(is);
//				os = client_socket.getOutputStream();
//				dos = new DataOutputStream(os);

				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

				// line1 = dis.readUTF();
				// /login user1 ==> msg[0] msg[1]
//				byte[] b = new byte[BUF_LEN];
//				dis.read(b);		
//				String line1 = new String(b);
//
//				//String[] msg = line1.split(" ");
//				//UserName = msg[1].trim();
//				UserState = "O"; // Online ����
//				Login();
			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		public void Login() {

			boolean dupcheck =false;
			User user;
			int uid=0;
			if(UserList.size() == 0){
				User newUser =  new User(uid,"Online",new ArrayList<Integer>(), this.UserName,"file");
				UserList.put(uid,newUser);
			}
			else{
				for(int i=0;i<UserList.size();i++){
					if(UserList.get(i).userName.equals(this.UserName)){
						user = UserList.get(i);
						dupcheck=true;
						this.setUser(user);
						user.setState("Online");
						System.out.println("�̹� �ִ� �������� �α��ε˴ϴ�.");
						break;
					}
				}
				if(!dupcheck){

					for(int i=0;i<=UserList.size();i++){
						if(!UserList.containsKey(i)){
							uid = i;
							break;
						}
					}

					User newUser =  new User(uid,"Online",new ArrayList<Integer>(), this.UserName,"file");
					UserList.put(uid,newUser);


				}

			}

			JavaObjServer.setListData(new ListData(UserList, RoomList));
			setListData();
			for(int j=0;j<UserList.size();j++){
				System.out.println("id : "+ Integer.toString(UserList.get(j).uid) +", name : " + UserList.get(j).userName);

			}


			AppendText("���ο� ������ " + UserName + " ����.");
			WriteOne("Welcome to Java chat server\n");
			WriteOne(UserName + "�� ȯ���մϴ�.\n"); // ����� ����ڿ��� ���������� �˸�
			String msg = "[" + UserName + "]���� ���� �Ͽ����ϴ�.\n";
			AppendText(msg);





			SendListData(); // ������ �α��� �� ������ ������ ����



		}

		public void Logout() {
			UserVec.removeElement(this); // Logout�� ���� ��ü�� ���Ϳ��� �����
			ListData ld = JavaObjServer.getListData();
			for(int i=0;i<ld.userList.size();i++){
				if(UserName.equals(ld.userList.get(i).userName))ld.userList.get(i).setState("Offline");
			}
			JavaObjServer.setListData(ld);
			SendUserData();
			AppendText("����� " + "[" + UserName + "] ����. ���� ������ �� " + UserVec.size());


		}

		public void SendListData(){
			ListData sld = JavaObjServer.getListData();
			ChatMsg obcm = new ChatMsg("SERVER", "600", sld.AllListData());
			WriteAllObject(obcm);
		}
		public void SendUserData(){
			ListData sld = JavaObjServer.getListData();
			ChatMsg obcm = new ChatMsg("SERVER", "610", sld.getUserListToString());
			WriteAllObject(obcm);
		}
		public void SendRoomData(){
			ListData sld = JavaObjServer.getListData();
			ChatMsg obcm = new ChatMsg("SERVER", "620", sld.getRoomListToString());
			WriteAllObject(obcm);
		}

		public void setSleepMode(String username){

			ListData sld = JavaObjServer.getListData();
			Map<Integer,User> userList = sld.userList;
			for(int i=0; i<userList.size();i++){
				if(userList.get(i).userName .equals(username)){
					userList.get(i).setState("Sleep");
				}
			}
			JavaObjServer.setListData(sld);

		}
		public void MakeRoom(String data){
			String[] str=data.split(",");
			ArrayList<Integer> userAuth = new ArrayList<>();
			String roomName =str[0];
			StringBuffer userAuthToStringBuffer = new StringBuffer(str[1]);
			//[,] ����
			userAuthToStringBuffer.deleteCharAt(userAuthToStringBuffer.length());
			userAuthToStringBuffer.deleteCharAt(0);
			String userAuthToString = userAuthToStringBuffer.toString();
			String[] arr = userAuthToString.split(",");
			for(String s : arr){
				int uid = Integer.parseInt(s);
				userAuth.add(uid);
			}

			ListData sld = JavaObjServer.getListData();
			for(int i=0;i<=roomList.size();i++){
				if(!roomList.containsKey(i)){
					Room room = new Room(i, userAuth, roomName);
					sld.roomList.put(i, room);
					JavaObjServer.setListData(sld);
					SendListData();
					break;
				}
			}
		}

		public void UpdateChatting(){
			//Ư������ ä�ó����� ������Ʈ�ϴ°� �ƴ϶� ���� �� ����
			ListData sld = JavaObjServer.getListData();
			ChatMsg obcm = new ChatMsg("SERVER", "600", sld.getRoomListToString());
			WriteAllObject(obcm);


		}
		public void Chatting(int rid, Chat chat){
			ListData sld = JavaObjServer.getListData();
			Room room = sld.roomList.get(rid);
			room.createChat(chat);
			JavaObjServer.setListData(sld);


			UpdateChatting();
		}



		// ��� User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.

		// ��� User�鿡�� Object�� ���. ä�� message�� image object�� ���� �� �ִ�
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				System.out.println(user.UserName);
				if (user.UserState.equals("Online"))
					user.WriteOneObject(ob);
			}
		}

		// ���� ������ User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.


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
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread�� ����ϴ� Client ���� 1:1 ����
		public void WriteOne(String msg) {
			try {
				// dos.writeUTF(msg);
//				byte[] bb;
//				bb = MakePacket(msg);
//				dos.write(bb, 0, bb.length);
				ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
//					dos.close();
//					dis.close();
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
		}

		// �ӼӸ� ����
		public void WriteChat(Object msg) {
			try {
				ChatMsg obcm = new ChatMsg("ä��", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
		}
		public void WriteOneObject(Object ob) {
			try {

			    oos.writeObject(ob);
			} 
			catch (IOException e) {
				AppendText("oos.writeObject(ob) error");		
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}
		
		public void run() {
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
				try {
					// String msg = dis.readUTF();
//					byte[] b = new byte[BUF_LEN];
//					int ret;
//					ret = dis.read(b);
//					if (ret < 0) {
//						AppendText("dis.read() < 0 error");
//						try {
//							dos.close();
//							dis.close();
//							client_socket.close();
//							Logout();
//							break;
//						} catch (Exception ee) {
//							break;
//						} // catch�� ��
//					}
//					String msg = new String(b, "euc-kr");
//					msg = msg.trim(); // �յ� blank NULL, \n ��� ����
					Object obcm = null;
					String msg = null;
					ChatMsg cm = null;

					if (socket == null)
						break;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						AppendObject(cm);
					} else
						continue;
					if (cm.getCode().matches("100")) {
						UserName = cm.getId();
						UserState = "Online";

						//�ű� ������ userList�� �߰�, �ƴϸ� user����

						Login();
					} else if (cm.getCode().matches("200")) {
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
						AppendText(msg); // server ȭ�鿡 ���
						String[] args = msg.split(" "); // �ܾ���� �и��Ѵ�.
						if (args.length == 1) { // Enter key �� ���� ��� Wakeup ó���� �Ѵ�.
							user.setState("Online");
						} else if (args[1].matches("/exit")) {
							Logout();
							break;
						} else if (args[1].matches("/list")) {
							WriteOne("User list\n");
							WriteOne("Name\tStatus\n");
							WriteOne("-----------------------------\n");
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								WriteOne(user.UserName + "\t" + user.UserState + "\n");
							}
							WriteOne("-----------------------------\n");
						} else if (args[1].matches("/sleep")) {
							UserState = "Sleep";
						} else if (args[1].matches("/wakeup")) {
							UserState = "Online";
						} else if (args[1].matches("/to")) { // �ӼӸ�
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								if (user.UserName.matches(args[2]) && user.UserState.matches("Online")) {
									String msg2 = "";
									for (int j = 3; j < args.length; j++) {// ���� message �κ�
										msg2 += args[j];
										if (j < args.length - 1)
											msg2 += " ";
									}
									// /to ����.. [�ӼӸ�] [user1] Hello user2..
									user.WriteChat(args[0] + " " + msg2 + "\n");
									//user.WriteOne("[�ӼӸ�] " + args[0] + " " + msg2 + "\n");
									break;
								}
							}
						} else { // �Ϲ� ä�� �޽���
							UserState = "Online";
							//WriteAll(msg + "\n"); // Write All
							WriteAllObject(cm);
						}
					} else if (cm.getCode().matches("400")) { // logout message ó��
						Logout();
						break;
					} else if (cm.getCode().matches("300")) {
						WriteAllObject(cm);
					} else if(cm.getCode().matches("700")){  // �����
						String str = (String)cm.getData();
						MakeRoom(str);
						SendRoomData();
					}else if(cm.getCode().matches("720")){  // setSleep
						String username = cm.getId();
						setSleepMode(username);
						SendUserData();
					}

				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						Logout();
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						client_socket.close();
						 // �������� ���� ��ü�� ���Ϳ��� �����
						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����
			} // while
		} // run
	}

}
