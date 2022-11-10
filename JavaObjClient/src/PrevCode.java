import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

    public class PrevCode extends JFrame {
        private JPanel contentPane; // ���� Panel(�� �� ��׶���)
        private JLabel lblChatInputView; // "ä�ù� �Է�" �� �����ִ� Label
        private JTextField txtRoomNameInput; // ä�ù� �̸��� �Է��ϴ� textField
        private JButton btnComplete; // (ä�ù� �̸� �Է�) �Ϸ� ��ư
        private JScrollPane selectedFriendScrollPane; // ������ ģ������ �����ִ� �¿� scolllPane
        private JScrollPane friendScrollPane; // ģ�� ����� �����ִ� ���� scolllPane

        //private ä�ù� �̸� ĭ / ä�ù� �̸� / ������ ģ�� ĭ / ģ�� ��� �����ִ� ĭ
        public PrevCode(){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X ��ư ������ ����
            setBounds(100, 100, 394, 630); // ũ�� ����

            contentPane = new JPanel(); // ���� ContentPane ����
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // JFrame���κ��� 5�� ���
            setContentPane(contentPane);
            contentPane.setLayout(null); // ��ġ �����ڴ� NULL

            lblChatInputView = new JLabel("ä�ù� �̸�: ");
            lblChatInputView.setBorder(new LineBorder(new Color(0,0,0)));
            lblChatInputView.setBackground(new Color(255,255,255));
            lblChatInputView.setFont(new Font("����",Font.BOLD,14));
            lblChatInputView.setHorizontalAlignment(SwingConstants.CENTER); // text ��� ����
            lblChatInputView.setBounds(5,20,100,45);
            contentPane.add(lblChatInputView);

            txtRoomNameInput = new JTextField();
            txtRoomNameInput.setBounds(120,20,150,46);
            contentPane.add(txtRoomNameInput);
            txtRoomNameInput.setColumns(50);

            btnComplete = new JButton("�Ϸ�");
            btnComplete.setFont(new Font("����",Font.PLAIN,14));
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


