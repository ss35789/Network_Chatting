import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Object.User;
import Object.Room;

public class App extends JFrame {

    private JPanel mainPanel;
    private JPanel sideBarPanel;
    private JLabel chattingRoomButton;
    private JLabel chatting_userButton;
    private JLabel myName;
    private JPanel userHeaderPanel;
    private JScrollPane userListPanel;
    private JPanel userPanel;
    private JPanel ChattingPannel;
    private JPanel Chatting_sideBar;
    private JPanel chattingHeader;
    private JScrollPane ChatRoomListPanel;
    private JLabel chatting_myName;

    private JLabel UserButton;
    private JLabel chatting_chattingRoombutton;
    private JLabel user_MakeChatRoomButton;
    private JLabel chatting_MakeChatRoomButton;
    private JLabel myImg;
    private JPanel UserList;
    private JPanel ChatRoomList;
    private JPanel Profile;
    private JLabel chatting_myImg;
    private JPanel chatting_Profile;

    private JPanel userListSpace;
    private JavaObjClientMainViewController controller;
    private String username;
    private String ip_addr;
    private String port_no;
    public static Map<Integer, User> userList = new HashMap<>();
    public static Map<Integer, Room> roomList = new HashMap<>();


    private String MyimgPath = "JavaObjClient/images/defaultProfileImg.jpg";
    private ArrayList<String> arr = new ArrayList<>();
    //더미 유저들

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();
    }

    //생성자 함수
    public App(String username, String ip_addr, String port_no) {
        this.username = username;
        this.ip_addr = ip_addr;
        this.port_no = port_no;

        controller = JavaObjClientMainViewController.getInstance();

        userList = controller.getUserList();
        roomList = controller.getRoomList();

        chatting_MakeChatRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MakeChatRoomView makeChatRoomView = new MakeChatRoomView();
                makeChatRoomView.setVisible(true);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        user_MakeChatRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MakeChatRoomView makeChatRoomView = new MakeChatRoomView();
                makeChatRoomView.setVisible(true);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        chatting_userButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //마우스 이벤트
                setContentPane(userPanel);
                revalidate();
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        chattingRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //마우스 이벤트
                setContentPane(ChattingPannel);
                revalidate();
                repaint();


                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        myName.setText(username);

        ImageIcon icon = new ImageIcon(MyimgPath);

        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon changeIcon = new ImageIcon(changeImg);


        chatting_myImg.setIcon(changeIcon);
        myImg.setIcon(changeIcon);

        chatting_myName.setText(username);

        //마우스 클릭시 userList 반응
//        DefaultListModel model = new DefaultListModel();
//        for(User s : user){
//            model.addElement(s.userName);
//        }
//        userList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                String clickedUser = (String)userList.getSelectedValue();
//                if(arr.contains(clickedUser)){
//                    arr.remove(clickedUser);
//                }
//                else{
//                    arr.add(clickedUser);
//                }
//
//
//                System.out.println(arr);
//            }
//        });


        setUserListPanel();

        setRoomListPanel(changeIcon);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(userPanel);

        this.pack();
        this.setVisible(true);

    }


    public void setUserListPanel() {
        for (int i = 0; i < 100; i++) {
            JPanel u = new JPanel();
            if (userList.size() > i) {
                u.setLayout(new BorderLayout());
                JLabel jl = new JLabel(userList.get(i).getUserName());

                // 이미지 스케일 조정
                ImageIcon originImg = userList.get(i).getImg();
                Image targetImg = originImg.getImage();
                Image scaledImg = targetImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon profileImg = new ImageIcon(scaledImg);
                jl.setIcon(profileImg);

                jl.setFont(new Font("Serif", Font.BOLD, 31));
                u.add(jl, BorderLayout.WEST);
                JLabel state = new JLabel(userList.get(i).getState());

                u.add(state, BorderLayout.EAST);
                u.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            }
            u.setBackground(Color.WHITE);

            UserList.add(u);
        }
        UserList.setLayout(new BoxLayout(UserList, BoxLayout.Y_AXIS));
        userListPanel.setViewportView(UserList);
    }

    public void setRoomListPanel(ImageIcon changeIcon) {

        // server에서 110 protocol 수신을 대기
        while (controller.getUser().getState() == null) ;
        int roomListSize = roomList.size();

        //RoomList를 순회
        for (int i = 0; i < 100; i++) {
            JPanel u = new JPanel();

            //Room의 userAuth를 순회

            //RoomList 의 패널을 먼저 추가 시킴
            if (i < roomListSize) {

                for (Integer j : roomList.get(i).getUserAuth()) {

                    //Room의 userAuth(접근권한)에 Uid(유저가 있으면) JLabel 추가
                    if (j == controller.getUser().getUid()) {
                        u.setLayout(new BorderLayout());

                        //채팅방 이름과 이미지를 가져옴
                        JLabel jl = new JLabel(roomList.get(i).getRoomName()); // i는 key값
                        jl.setIcon(changeIcon);
                        jl.setFont(new Font("Serif", Font.BOLD, 31));
                        u.add(jl, BorderLayout.WEST);

                        //맨 끝 채팅을 가져옴
                        if (!roomList.get(i).getChatList().isEmpty()) {
                            JLabel lastChat = new JLabel(roomList.get(i).getChatList().get(roomList.get(i).getChatList().size() - 1).getMsg());
                            lastChat.setBackground(Color.gray);
                            u.add(lastChat, BorderLayout.EAST);

                            //아래 쪽에 빈 패널 추가(동남쪽에 패널 배치용)
                            JPanel southPanel = new JPanel(new BorderLayout());
                            u.add(southPanel, BorderLayout.SOUTH);

                            //맨 끝 채팅의 시간을 가져옴
                            JLabel lastChatTime = new JLabel(roomList.get(i).getChatList().get(roomList.get(i).getChatList().size() - 1).getDate());
                            lastChatTime.setBackground(Color.gray);
                            southPanel.add(lastChatTime, BorderLayout.EAST);
                            southPanel.setBackground(Color.WHITE);
                        }

                        u.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        u.setBackground(Color.WHITE);
                        ChatRoomList.add(u);
                    }
                    int panelOrder = i;
                    u.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            try {
                                //로그인시 new AppView, 600시 new AppView로 두번 불림
                                controller.getChatRoomViewList().get(panelOrder).setVisible(true);
                            } catch (NullPointerException nullPointerException) {
                                controller.restoreChatRoomView(panelOrder);
                                controller.getChatRoomViewList().get(panelOrder).setVisible(true);
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                        }
                    });
                }
            }
            //roomList 패널 먼저 추가 한 후 빈 패널을 아래에 배치
            else {
                ChatRoomList.add(u);
            }

        }
        ChatRoomList.setLayout(new BoxLayout(ChatRoomList, BoxLayout.Y_AXIS));
        ChatRoomListPanel.setViewportView(ChatRoomList);
    }

    // Getter & Setter
    public static void setUserList(Map<Integer, User> userList) {
        App.userList = userList;
    }

}
