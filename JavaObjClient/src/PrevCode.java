import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

    public class PrevCode extends JFrame {
        private JPanel contentPane; // 메인 Panel(맨 뒤 백그라운드)
        private JLabel lblChatInputView; // "채팅방 입력" 을 보여주는 Label
        private JTextField txtRoomNameInput; // 채팅방 이름을 입력하는 textField
        private JButton btnComplete; // (채팅방 이름 입력) 완료 버튼
        private JScrollPane selectedFriendScrollPane; // 선택한 친구들을 보여주는 좌우 scolllPane
        private JScrollPane friendScrollPane; // 친구 목록을 보여주는 상하 scolllPane

        //private 채팅방 이름 칸 / 채팅방 이름 / 선택한 친구 칸 / 친구 목록 보여주는 칸
        public PrevCode(){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X 버튼 누르면 종료
            setBounds(100, 100, 394, 630); // 크기 설정

            contentPane = new JPanel(); // 메인 ContentPane 생성
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // JFrame으로부터 5씩 띄움
            setContentPane(contentPane);
            contentPane.setLayout(null); // 배치 관리자는 NULL

            lblChatInputView = new JLabel("채팅방 이름: ");
            lblChatInputView.setBorder(new LineBorder(new Color(0,0,0)));
            lblChatInputView.setBackground(new Color(255,255,255));
            lblChatInputView.setFont(new Font("굴림",Font.BOLD,14));
            lblChatInputView.setHorizontalAlignment(SwingConstants.CENTER); // text 가운데 정렬
            lblChatInputView.setBounds(5,20,100,45);
            contentPane.add(lblChatInputView);

            txtRoomNameInput = new JTextField();
            txtRoomNameInput.setBounds(120,20,150,46);
            contentPane.add(txtRoomNameInput);
            txtRoomNameInput.setColumns(50);

            btnComplete = new JButton("완료");
            btnComplete.setFont(new Font("굴림",Font.PLAIN,14));
            btnComplete.setBounds(300,20,62,46);
            contentPane.add(btnComplete);

            selectedFriendScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            selectedFriendScrollPane.setBounds(12,80,352,100);
            contentPane.add(selectedFriendScrollPane);

            selectedFriendScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            selectedFriendScrollPane.setBounds(12,80,352,100);
            contentPane.add(selectedFriendScrollPane);

            friendScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            friendScrollPane.setBounds(12,180,352,400);
            contentPane.add(friendScrollPane);

            setVisible(true);
        }
    }


