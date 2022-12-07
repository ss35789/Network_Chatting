import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import Object.Chat;

public class ChatRoomView extends JFrame {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel RoomInfoPanel;
    private JTextPane textArea;
    private JLabel lblRoomName;
    private JLabel lblRoomUserNum;
    private JLabel btnUserList;
    private JTextField ChatInput;
    private JLabel btnSubmit;
    private JLabel btnSendImg;
    private Integer rid = null;
    private Integer currentViewChatSize = 0;
    JavaObjClientMainViewController controller = JavaObjClientMainViewController.getInstance();

    //������ �Լ�
    public ChatRoomView(String roomName, String userNum) {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(controller.getUser().getUserName());
        setSize(500, 800);
        setContentPane(mainPanel);
        lblRoomName.setText(roomName);
        lblRoomUserNum.setText(userNum);
        textArea.setFont(new Font("Serif", Font.BOLD, 20));
        textArea.setDisabledTextColor(Color.BLACK);
        //textArea.setSize(textArea.getWidth(), textArea.getPreferredSize().height);//For fixed width
        //textArea.setLineWrap(true);

        //�̹��� ���� ��ư �׼� ������ ����
        btnSendImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                //���콺 �̺�Ʈ
//                setContentPane(mainPanel);
//                revalidate();
//                repaint();
                JFrame frame = new JFrame();
                FileDialog fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir") + "\\JavaObjClient\\images");
                fd.setVisible(true);

                // server���� 300 protocol ����
                Chat chat = new Chat.ChatBuilder().
                        setUid(controller.getUser().getUid()).
                        setMsg("IMG").
                        setDate(controller.DateToString(LocalTime.now())).
                        build();

                isRidNullSet(rid);

                String msg = rid.toString() + DivString.RoomDiv + chat.toString(chat);

                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "300", msg);
                ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                obcm.setImg(img);
                controller.SendObject(obcm);

                //ä��â�� �̹��� �߰�
                appendImage(new ImageIcon(fd.getDirectory() + fd.getFile()));
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
        //ä��InputField �׼� ������ ����
        ChatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendText();
            }
        });

        //���� ��ư �׼� ������ ����
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //���콺 �̺�Ʈ
                setContentPane(mainPanel);
                revalidate();
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                sendText();
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

    }


    // Getters & Setters ���� 
    public void setLblRoomName(String lblRoomName) {
        this.lblRoomName.setText(lblRoomName);
    }

    public void setLblRoomUserNum(String lblRoomUserNum) {
        this.lblRoomUserNum.setText(lblRoomUserNum);
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public JTextPane getTextArea() {
        return textArea;
    }

    public JLabel getLblRoomName() {
        return lblRoomName;
    }
    // Getters & Setters ��

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    //���� ä���� ������
    public void sendText() {
        // ä���� ������ ó�� ���� ����
        if (ChatInput.getText().equals(""))
            return;

        isRidNullSet(this.rid);

        //ä�� �ð� ���
        LocalTime time = LocalTime.now();
        //ä�ù� �信 ä�� �߰�
        appnedText(controller.getUser().getUid(), ChatInput.getText());
//
//        //TextArea�� ��ġ�� �� �Ʒ��� �ű�
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len);


        // �������� 200 Protocol ����
        Chat chat = new Chat.ChatBuilder().
                setUid(controller.getUser().getUid()).
                setMsg(ChatInput.getText()).
                setDate(controller.DateToString(time)).
                build();
        String msg = rid + DivString.RoomDiv + chat.toString(chat);
        ChatMsg obcm = new ChatMsg(Integer.toString(controller.getUser().getUid()), "200", msg);
        controller.SendObject(obcm);

        //Client RoomList�� Room�� ChatList�� ä�� �߰�
        controller.getRoomList().get(rid).getChatList().add(chat);

        //ä�ù� �� reSetting
        ChatInput.setText("");
        ChatInput.requestFocus();
    }

    private void isRidNullSet(Integer rid) {
        //rid ������ �Ǿ����� ������ rid�� ������
        if (rid == null) {
            setRid(controller.getRidfromRoomName(lblRoomName.getText()));
        }
    }

    public void receiveText(Integer uid, String text) {
        //rid ������ �Ǿ����� ������ rid�� ������
        isRidNullSet(this.rid);

        // �ٸ� ����� ���� msg�� �߰�
        if (uid != controller.getUser().getUid()) {
            //ä�ù� �信 ä�� �߰�
            appnedText(controller.getUserList().get(uid).getUid(), text);
            ChatInput.requestFocus();
//            // ������ �̵�
//            int len = textArea.getDocument().getLength();
//            textArea.setCaretPosition(len);

            //Client RoomList�� Room�� ChatList�� ä�� �߰�
            Chat chat = new Chat.ChatBuilder().
                    setUid(uid).
                    setMsg(text).
                    build();

            //controller.getRoomList().get(rid).getChatList().add(chat);
        }
//        //TextArea�� ��ġ�� �� �Ʒ��� �ű�
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len);
        return;
    }

    public void appnedText(Integer uid, String text) {

        String userName = controller.getUserNameFromUid(uid);
        movetoEndLine();

        // ù ä���Ͻ� �ٷ� �߰�
        if(textArea.getText().length()==0){
            textArea.replaceSelection(userName + "\n");
            movetoEndLine();
            textAreaAppendText(text, 12);
            return;
        }

        //ùä���� �ƴҰ�� ���� ���� print �ߺ� ó��
        if(!checkChatUserSame(uid)){
            textArea.replaceSelection(userName + "\n");
            movetoEndLine();
            textAreaAppendText(text, 12);
        }
        else
            textAreaAppendText(text, 12);
    }

    private void textAreaAppendText(String text, int widthlimit) {
        // text �߰� , �� text ���� ó��
        if (text.length() > widthlimit) {
            while (text.length() > widthlimit) {
                textArea.replaceSelection(text.substring(0, widthlimit - 1) + "\n");
                text = text.substring(widthlimit);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
            textArea.replaceSelection(text + "\n");
        } else
            textArea.replaceSelection(text + "\n");
    }

    /***
     * �߰��� ä�ð� �� ���� ä���� ������ ���� üũ�ϴ� �Լ�
     * @param uid �߰��� ä���� UserID
     * @return ������ true �ٸ��� false
     */
    public boolean checkChatUserSame(Integer uid) {
        Document doc = textArea.getDocument();
        try {
            String[] str = doc.getText(0,doc.getLength()-1).split("\\n");
            for(int i=str.length-1;i>=0;i--){
                //userName�� ã�� ���ϸ� �ٷ� ���� �ؽ�Ʈ�� �ٽ� �Ѿ
                if(controller.getUidFromUserName(str[i])==999)
                    continue;
                
                else{
                 if((controller.getUidFromUserName(str[i])==uid))
                     return true;
                 else
                     return false;
                }
            }
            return false;
        } catch (BadLocationException e) {
            e.printStackTrace();
            System.out.println("First Chat Append in this ChatRoomView");
            return false;
        }
    }

    public void movetoEndLine() {
        // ������ �̵�
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
    }

    public void appendImage(ImageIcon ori_icon) {
        movetoEndLine();
        Image ori_img = ori_icon.getImage();
        int width, height;
        double ratio;
        width = ori_icon.getIconWidth();
        height = ori_icon.getIconHeight();
        // Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
        if (width > 200 || height > 200) {
            if (width > height) { // ���� ����
                ratio = (double) height / width;
                width = 200;
                height = (int) (width * ratio);
            } else { // ���� ����
                ratio = (double) width / height;
                height = 200;
                width = (int) (height * ratio);
            }
            Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon new_icon = new ImageIcon(new_img);
            textArea.insertIcon(new_icon);
        } else
            textArea.insertIcon(ori_icon);
        movetoEndLine();
        textArea.replaceSelection("\n");
        // ImageViewAction viewaction = new ImageViewAction();
        // new_icon.addActionListener(viewaction); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
    }
}
