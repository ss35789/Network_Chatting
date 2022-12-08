import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
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
    private JButton myState;

    private JPanel userListSpace;
    private JavaObjClientMainViewController controller;
    private String username;
    private String ip_addr;
    private String port_no;
    public static Map<Integer, User> userList = new HashMap<>();
    public static Map<Integer, Room> roomList = new HashMap<>();
    private String userState;


    private String MyimgPath = "JavaObjClient/images/defaultProfileImg.jpg";
    private ArrayList<String> arr = new ArrayList<>();
    //���� ������

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();
    }

    //������ �Լ�
    public App(String username, String ip_addr, String port_no) {
        this.username = username;
        this.ip_addr = ip_addr;
        this.port_no = port_no;

        controller = JavaObjClientMainViewController.getInstance();

        userList = controller.getUserList();
        roomList = controller.getRoomList();
        //user ���� ���
        while (controller.getUser().getState()==null);
        userState = controller.getUser().getState();
        //user ���� ���
        while (controller.getUser().getImg()==null);
        MyimgPath = controller.getUser().getImg().toString();

        myState.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //���� ���ۿ� ��ü�ν��Ͻ����� ����
                ChatMsg obcm;


                switch (userState) {
                    case "Online": // �ٲٱ� �� ������ ���°� �¶��� �̸� Sleep����
                        // sleep protocol �������� ����
                        obcm = new ChatMsg(controller.getUser().getUserName(), "720", "set SleepMode");
                        controller.SendObject(obcm);
                        break;
                    case "Sleep": // �ٲٱ� �� ������ ���°� Sleep �̸� Online����
                        // wakeup protocol �������� ����
                        obcm = new ChatMsg(controller.getUser().getUserName(), "730", "set SleepMode");
                        controller.SendObject(obcm);
                        break;
                    default:
                        break;
                }
            }
        });

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
                //���콺 �̺�Ʈ
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
                //���콺 �̺�Ʈ
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

        myImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //�̹��� ���� â�� ���
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                JFrame frame = new JFrame();
                FileDialog fd = new FileDialog(frame, "������ �̹��� ����", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir") + "\\JavaObjClient\\images");
                fd.setVisible(true);

                // �������� 350 protocol ����
                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "350", "Change ProfileIMG");
                ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                obcm.setImg(img);
                controller.SendObject(obcm);

                ImageIcon changeIcon = reSacledProfileImg(new ImageIcon(fd.getDirectory() + fd.getFile()));
                chatting_myImg.setIcon(changeIcon);
                myImg.setIcon(changeIcon);
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


        ImageIcon changeIcon = reSacledProfileImg(new ImageIcon(MyimgPath));


        chatting_myImg.setIcon(changeIcon);
        myImg.setIcon(changeIcon);
        chatting_myName.setText(username);
        myName.setText(username);
        if(userState.equals("Sleep"))
            myState.setText("set Online");
        else if (userState.equals("Online"))
            myState.setText("set Sleep");


        setUserListPanel();

        setRoomListPanel(changeIcon);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(userPanel);

        this.pack();
        this.setVisible(true);

    }

    private ImageIcon reSacledProfileImg(ImageIcon MyimgPath) {
        ImageIcon icon = MyimgPath;

        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon changeIcon = new ImageIcon(changeImg);
        return changeIcon;
    }


    public void setUserListPanel() {
        for (int i = 0; i < 100; i++) {
            JPanel u = new JPanel();
            if (userList.size() > i) {
                u.setLayout(new BorderLayout());
                JLabel jl = new JLabel(userList.get(i).getUserName());

                // �̹��� ������ ����
                ImageIcon profileImg = reSacledProfileImg(userList.get(i).getImg());
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

        // server���� 110 protocol ������ ���
        while (controller.getUser().getState() == null) ;
        int roomListSize = roomList.size();

        //RoomList�� ��ȸ
        for (int i = 0; i < 100; i++) {
            JPanel u = new JPanel();

            //Room�� userAuth�� ��ȸ

            //RoomList �� �г��� ���� �߰� ��Ŵ
            if (i < roomListSize) {

                for (Integer j : roomList.get(i).getUserAuth()) {

                    //Room�� userAuth(���ٱ���)�� Uid(������ ������) JLabel �߰�
                    if (j == controller.getUser().getUid()) {
                        u.setLayout(new BorderLayout());

                        //ä�ù� �̸��� �̹����� ������
                        JLabel jl = new JLabel(roomList.get(i).getRoomName()); // i�� key��
                        jl.setIcon(changeIcon);
                        jl.setFont(new Font("Serif", Font.BOLD, 31));
                        u.add(jl, BorderLayout.WEST);

                        //�� �� ä���� ������
                        if (!roomList.get(i).getChatList().isEmpty()) {
                            int limitChatLength = 10;
                            String strLastChat = roomList.get(i).getChatList().get(roomList.get(i).getChatList().size() - 1).getMsg();
                            if (strLastChat.length() > limitChatLength)
                                strLastChat = strLastChat.substring(0, limitChatLength) + "...";
                            JLabel lastChat = new JLabel(strLastChat);
                            lastChat.setBackground(Color.gray);
                            u.add(lastChat, BorderLayout.EAST);

                            //�Ʒ� �ʿ� �� �г� �߰�(�����ʿ� �г� ��ġ��)
                            JPanel southPanel = new JPanel(new BorderLayout());
                            u.add(southPanel, BorderLayout.SOUTH);

                            //�� �� ä���� �ð��� ������
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
                                //�α��ν� new AppView, 600�� new AppView�� �ι� �Ҹ�
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
            //roomList �г� ���� �߰� �� �� �� �г��� �Ʒ��� ��ġ
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
