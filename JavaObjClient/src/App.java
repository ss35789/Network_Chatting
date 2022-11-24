import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private JScrollPane chatRoomList;
    private JLabel chatting_myName;
    private JList userList;
    private JLabel UserButton;
    private JLabel chatting_chattingRoombutton;
    private JLabel user_MakeChatRoomButton;
    private JLabel chatting_MakeChatRoomButton;
    private JList RoomList;
    private JLabel myImg;

    private String username;
    private String ip_addr;
    private String port_no;
    private String [] user = {"user1", "user2", "user3"};

    private JavaObjClientMainViewController controller; // controller

    public void setUser(String[] user) {
        this.user = user;
    }

    private String MyimgPath ="JavaObjClient/images/lion.jpg";
    private ArrayList<String> arr = new ArrayList<>();
    //더미 유저들

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();


    }
    public App(){}

    public App(String username, String ip_addr,String port_no){
        this.username=username;
        this.ip_addr=ip_addr;
        this.port_no=port_no;

        controller = JavaObjClientMainViewController.getInstance();
        chatting_MakeChatRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //controller.setMakeChatRoomView();
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

        myImg.setIcon(changeIcon);

        chatting_myName.setText(username);
        DefaultListModel model = new DefaultListModel();
        for(String s : user){
            model.addElement(s);
        }
        userList.setModel(model);
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String clickedUser = (String)userList.getSelectedValue();
                if(arr.contains(clickedUser)){
                    arr.remove(clickedUser);
                }
                else{
                    arr.add(clickedUser);
                }


                System.out.println(arr);
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(userPanel);

        this.pack();
        this.setVisible(true);

    }


}
