//JavaObjServer.java ObjectStream 기반 채팅 Server

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

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
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
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);

	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {

					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);

					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					 // 만든 객체의 스레드 실행
					AppendText("현재 참가자 수 " + UserVec.size());
					new_user.start();
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg msg) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		textArea.append("code = " + msg.getCode() + "\n");
		textArea.append("id = " + msg.getId() + "\n");
		textArea.append("data = " + msg.getData() + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
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
		public String UserStatus;

		//user.UserName 과 UserName은 같은 것임
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
			// 매개변수로 넘어온 자료 저장

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
//				UserStatus = "O"; // Online 상태
//				Login();
			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		public void Login() {
			AppendText("새로운 참가자 " + UserName + " 입장.");
			WriteOne("Welcome to Java chat server\n");
			WriteOne(UserName + "님 환영합니다.\n"); // 연결된 사용자에게 정상접속을 알림
			String msg = "[" + UserName + "]님이 입장 하였습니다.\n";
			AppendText(msg);
			SendListData(); // 누군가 로그인 할 때마다 데이터 갱신

		}

		public void Logout() {
			String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());

			SendListData();
		}

		public void SendListData(){
			for (int i = 0; i < user_vc.size(); i++) {

				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus.equals("Online"))
					try {

						ChatMsg obcm = new ChatMsg("SERVER", "600", "listdata");
						ListData sld = null;
						obcm.setSld(sld);

						oos.writeObject(obcm);
						System.out.println(userList.get(0).userName);

					} catch (IOException e) {
						AppendText("send listData error");
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
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
					}
			}

		}
		public void MakeRoom(String roomName, ArrayList<Integer> userAuth){
			int rid=-1;

			for(int i=0;i<=roomList.size();i++){
				if(roomList.containsKey(i))continue;
				rid=i;
			}

			Room room = new Room(rid, userAuth, roomName);
			roomList.put(rid, room);
			SendListData();
		}

		public void UpdateChatting(int rid){
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				for(int u=0;u<userList.size();u++){
					if(roomList.get(rid).userAuth.contains(u)){

						user.WriteChat(roomList.get(u).chat);
						//userList 유저들 중에서 rid 방의 권한을 갖고있는 유저들에게 rid의 방의 채팅내역을 보내줌
					}
				}
			}

		}
		public void Chatting(int rid, Chat chat){
			roomList.get(rid).createChat(user.uid, chat);
			UpdateChatting(rid);
		}



		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.

		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus.equals("Online"))
					user.WriteOneObject(ob);
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.


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
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread가 담당하는 Client 에게 1:1 전송
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
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// 귓속말 전송
		public void WriteChat(Object msg) {
			try {
				ChatMsg obcm = new ChatMsg("채팅", "200", msg);
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
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
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
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
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
//						} // catch문 끝
//					}
//					String msg = new String(b, "euc-kr");
//					msg = msg.trim(); // 앞뒤 blank NULL, \n 모두 제거
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
						UserStatus = "Online";

						AppendText("ASdsfsaf");
						//신규 유저면 userList에 추가, 아니면 user설정
						boolean dupcheck =false;
						User user;
						int uid=0;
						if(UserList.size() != 0){
							for(int i=0;i<UserList.size();i++){
								if(UserList.get(i).userName.equals(this.UserName)){
									user = UserList.get(i);
									dupcheck=true;
									this.setUser(user);
									System.out.println("이미 있는 계정으로 로그인됩니다.");
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
							}

						}
						User newUser =  new User(uid,"Online",new ArrayList<Integer>(), this.UserName,"file");
						UserList.put(uid,newUser);
						JavaObjServer.setListData(new ListData(UserList, RoomList));
						setListData();
						for(int j=0;j<UserList.size();j++){
							System.out.println("id : "+ Integer.toString(UserList.get(j).uid) +", name : " + UserList.get(j).userName);

						}
						Login();
					} else if (cm.getCode().matches("200")) {
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
						AppendText(msg); // server 화면에 출력
						String[] args = msg.split(" "); // 단어들을 분리한다.
						if (args.length == 1) { // Enter key 만 들어온 경우 Wakeup 처리만 한다.
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
								WriteOne(user.UserName + "\t" + user.UserStatus + "\n");
							}
							WriteOne("-----------------------------\n");
						} else if (args[1].matches("/sleep")) {
							UserStatus = "Sleep";
						} else if (args[1].matches("/wakeup")) {
							UserStatus = "Online";
						} else if (args[1].matches("/to")) { // 귓속말
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								if (user.UserName.matches(args[2]) && user.UserStatus.matches("Online")) {
									String msg2 = "";
									for (int j = 3; j < args.length; j++) {// 실제 message 부분
										msg2 += args[j];
										if (j < args.length - 1)
											msg2 += " ";
									}
									// /to 빼고.. [귓속말] [user1] Hello user2..
									user.WriteChat(args[0] + " " + msg2 + "\n");
									//user.WriteOne("[귓속말] " + args[0] + " " + msg2 + "\n");
									break;
								}
							}
						} else { // 일반 채팅 메시지
							UserStatus = "Online";
							//WriteAll(msg + "\n"); // Write All
							WriteAllObject(cm);
						}
					} else if (cm.getCode().matches("400")) { // logout message 처리
						Logout();
						break;
					} else if (cm.getCode().matches("300")) {
						WriteAllObject(cm);
					} else if(cm.getCode().matches("700")){  // 방생성
						MakeChattingRoomOrder mo = (MakeChattingRoomOrder) cm.getData();
						MakeRoom(mo.roomName, mo.userAuth);
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			} // while
		} // run
	}

}
