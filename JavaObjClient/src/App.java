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
    private JButton chatting_MakeChatRoomButton;
    private JButton user_chatting_MakeChatRoomButton;
    private JList userList;
    private JLabel UserButton;
    private JLabel chatting_chattingRoombutton;
    private String username;
    private String ip_addr;
    private String port_no;
    private String [] user = {"user1", "user2", "user3"};
    private ArrayList<String> arr = new ArrayList<>();
    //���� ������

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();


    }
    public App(){}

    public App(String username, String ip_addr,String port_no){
        this.username=username;
        this.ip_addr=ip_addr;
        this.port_no=port_no;

        chatting_userButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //���콺 �̺�Ʈ
                setContentPane(userPanel);
                pack();
            }
        });
        chattingRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //���콺 �̺�Ʈ
                setContentPane(ChattingPannel);
                pack();
            }
        });

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
