import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class App extends JFrame{

    private JPanel mainPanel;
    private JButton userButton;
    private JPanel sideBarPanel;
    private JButton chattingRoomButton;
    private JLabel myName;
    private JPanel userHeaderPanel;
    private JScrollPane userListPanel;
    private JPanel userPanel;
    private JPanel ChattingPannel;
    private JPanel Chatting_sideBar;
    private JPanel chattingHeader;
    private JScrollPane chatRoomList;
    private JLabel chatting_myName;
    private JButton chatting_userButton;
    private JButton chatting_chattingRoombutton;
    private JButton chatting_MakeChatRoomButton;
    private JButton user_chatting_MakeChatRoomButton;
    private JList userList;
    private String username;
    private String ip_addr;
    private String port_no;
    private String [] user = {"user1", "user2", "user3"};
    private ArrayList<String> arr = new ArrayList<>();
    //더미 유저들
    private void ChattingButton_Click(JFrame App){
       //chattingPanel로 이동
        App.setContentPane(ChattingPannel);
        App.pack();
    }
    private void UserButton_Click(JFrame App){
        //chattingPanel로 이동
        App.setContentPane(userPanel);
        App.pack();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();


    }
    public App(){}

    public App(String username, String ip_addr,String port_no){
        this.username=username;
        this.ip_addr=ip_addr;
        this.port_no=port_no;
        chattingRoomButton.addActionListener(event -> ChattingButton_Click(this));
        chatting_userButton.addActionListener(event -> UserButton_Click(this));
        chattingRoomButton.setIcon(new ImageIcon("icon1.jpg"));
        myName.setText(username);
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
