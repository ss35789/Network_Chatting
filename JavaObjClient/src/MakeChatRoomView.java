import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import Object.Room;
import Object.ListData;

public class MakeChatRoomView extends JFrame  {
    private JPanel contentPane;
    private JTextField txtRoomNameInput;
    private JButton btnComplete;
    private JPanel inputChatRoomPanel;
    private JLabel lblChatInputView;
    private JScrollPane selectedFriendScrollPane;
    private JScrollPane friendScrollPane;
    private JList friendList;
    private JList selectedFriendList;
    private String[] friend = {};
    List selectionList;
    DefaultListModel selectedmodel = new DefaultListModel();
    JavaObjClientMainViewController controller = JavaObjClientMainViewController.getInstance();

    public MakeChatRoomView() {
        setContentPane(contentPane);
        setTitle("채팅방 생성");
        setSize(500, 800);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        lblChatInputView.setFocusable(true); // textField Default 값 출력을 위해 강제로 다른 곳에 포커스 주기
        txtRoomNameInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtRoomNameInput.setForeground(new Color(0, 0, 0));
                txtRoomNameInput.setText("");
            }
        });

        //친구 목록 띄우기

        DefaultListModel model = new DefaultListModel();
        for (Integer i : controller.getUserList().keySet()) {
            model.addElement(controller.getUserList().get(i).getUserName());
        }
        friendList.setModel(model);
        friendList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        //완료 버튼 누르면
        btnComplete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Room Data 생성 후 서버로 전송
                
                //선택한 UserName을 Uid로 변환
                ArrayList<Integer> roomUserAuth = controller.uesrNameToUserID((ArrayList<String>) selectionList);
                //Room Data 생성
                Room room = new Room.RoomBuilder().setRoomName(txtRoomNameInput.getText()).setUserAuth(roomUserAuth).build();
                //서버에 전송을 하기 위하여 ListData 생성
                ListData lsd = new ListData.ListDataBuilder().setRoom(room).build();
                //RoomData를 String 변환 후 서버 전송용 Object 생성
                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "700",lsd.getRoomToString());
                // 서버 전송
                controller.SendObject(obcm);
                
                //ChatRoomView 생성
                ChatRoomView chatRoomView = new ChatRoomView();
                controller.addChatRoomView(chatRoomView);
                
                //채팅방 생성 View => ChatRoomView로 화면 전환
                chatRoomView.setVisible(true);
                setVisible(false);
            }
        });


        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
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

    //map 기존 키값 변경 코드
    //map.put("update_menu_id", map.remove("menu_id"));

}
