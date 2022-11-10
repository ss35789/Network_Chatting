import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ChatRoomView extends JFrame{
    private JPanel contentPane;
    private JTextField txtRoomNameInput;
    private JButton btnComplete;
    private JPanel inputChatRoomPanel;
    private JLabel lblChatInputView;
    private JScrollPane selectedFriendScrollPane;
    private JScrollPane friendScrollPane;

    public ChatRoomView() {
        setContentPane(contentPane);
        setTitle("ä�ù� ����");
        setSize(400,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        lblChatInputView.setFocusable(true); // textField Default �� ����� ���� ������ �ٸ� ���� ��Ŀ�� �ֱ�
        txtRoomNameInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtRoomNameInput.setForeground(new Color(0,0,0));
                txtRoomNameInput.setText("");
            }
        });

    }
}
