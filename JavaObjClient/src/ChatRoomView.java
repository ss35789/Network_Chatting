import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChatRoomView extends JFrame {
    private JPanel contentPane;
    private JTextField txtRoomNameInput;
    private JButton btnComplete;
    private JPanel inputChatRoomPanel;
    private JLabel lblChatInputView;
    private JScrollPane selectedFriendScrollPane;
    private JScrollPane friendScrollPane;
    private JList friendList;
    private JList selectedFriendList;
    private String[] friend = {"user1", "user2", "user3", "user4", "user5", "user6"};
    List selectionList;
    DefaultListModel selectedmodel = new DefaultListModel();

    public ChatRoomView() {
        setContentPane(contentPane);
        setTitle("채팅방 생성");
        setSize(500, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        lblChatInputView.setFocusable(true); // textField Default 값 출력을 위해 강제로 다른 곳에 포커스 주기
        txtRoomNameInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtRoomNameInput.setForeground(new Color(0, 0, 0));
                txtRoomNameInput.setText("");
            }
        });

        DefaultListModel model = new DefaultListModel();
        for (String s : friend) {
            model.addElement(s);
        }
        friendList.setModel(model);
        friendList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // int index = friendList.locationToIndex(e.getPoint());
                //System.out.println(friendList.getSelectedIndex() + "," +  friendList.getSelectedValue().toString());
                selectionList = friendList.getSelectedValuesList();
                System.out.println(selectionList);
                selectedmodel.clear();
                selectedmodel.addElement(selectionList);
                selectedFriendList.setModel(selectedmodel);
            }
        };
        friendList.addMouseListener(mouseListener);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
