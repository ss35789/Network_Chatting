import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class App extends JFrame{

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
    public static String [] user = {"user1", "user2", "user3"};
    public static String [] room = {"room1", "room2","room3"};

    private String MyimgPath ="JavaObjClient/images/defaultProfileImg.jpg";
    private ArrayList<String> arr = new ArrayList<>();
    //더미 유저들

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();


    }
//    public static void setUserList(Map<Integer, User> UserList){
//        userList=UserList;
//    }
//    public static void setRoomList(Map<Integer, Room> RoomList){
//        roomList=RoomList;
//    }

    public App(String username, String ip_addr,String port_no){
        this.username=username;
        this.ip_addr=ip_addr;
        this.port_no=port_no;

        controller = JavaObjClientMainViewController.getInstance();

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
        ImageIcon icon = new ImageIcon(MyimgPath);;
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(40,40,Image.SCALE_SMOOTH);

        ImageIcon changeIcon = new ImageIcon(changeImg);


        chatting_myImg.setIcon(changeIcon);
        myImg.setIcon(changeIcon);

        chatting_myName.setText(username);
        DefaultListModel model = new DefaultListModel();
        for(String s : user){
            model.addElement(s);
        }
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





        for(int i=0 ; i<100;i++){

            JPanel u = new JPanel();
            if(user.length>i){
                u.setLayout(new BorderLayout());
                JLabel jl = new JLabel(App.user[i]);
                jl.setIcon(changeIcon);
                jl.setFont(new Font("Serif", Font.BOLD,31));
                u.add(jl,BorderLayout.WEST);
                JLabel state = new JLabel("Online");

                u.add(state,BorderLayout.EAST);
                u.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

            }
            u.setBackground(Color.WHITE);

            UserList.add(u);
        }
        UserList.setLayout(new BoxLayout(UserList,BoxLayout.Y_AXIS));
        userListPanel.setViewportView(UserList);

        for(int i=0 ; i<100;i++){

            JPanel u = new JPanel();
            if(room.length>i){
                u.setLayout(new BorderLayout());

                JLabel jl = new JLabel(App.room[i]);
                jl.setIcon(changeIcon);
                jl.setFont(new Font("Serif", Font.BOLD,31));
                u.add(jl,BorderLayout.WEST);

                JLabel JoinUser = new JLabel("sangminlee, sdfdsf, sd");
                JoinUser.setBackground(Color.gray);
                u.add(JoinUser,BorderLayout.EAST);
                u.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

            }
            u.setBackground(Color.WHITE);

            ChatRoomList.add(u);
        }
        ChatRoomList.setLayout(new BoxLayout(ChatRoomList,BoxLayout.Y_AXIS));
        ChatRoomListPanel.setViewportView(ChatRoomList);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(userPanel);

        this.pack();
        this.setVisible(true);

    }


}
