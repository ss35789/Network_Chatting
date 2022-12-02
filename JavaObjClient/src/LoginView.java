import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginView extends JFrame{

    // JFrame 요소들
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUserName;
    private JTextField txtIpAddress;
    private JTextField txtPortNumber;

    //JavaObjClientMainViewController
    private JavaObjClientMainViewController controller;



    public LoginView() { // Login 창 생성
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 254, 321);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("User Name");
        lblNewLabel.setBounds(12, 39, 82, 33);
        contentPane.add(lblNewLabel);

        txtUserName = new JTextField();
        txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
        txtUserName.setBounds(101, 39, 116, 33);
        contentPane.add(txtUserName);
        txtUserName.setColumns(10);

        JLabel lblIpAddress = new JLabel("IP Address");
        lblIpAddress.setBounds(12, 100, 82, 33);
        contentPane.add(lblIpAddress);

        txtIpAddress = new JTextField();
        txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
        txtIpAddress.setText("127.0.0.1");
        txtIpAddress.setColumns(10);
        txtIpAddress.setBounds(101, 100, 116, 33);
        contentPane.add(txtIpAddress);

        JLabel lblPortNumber = new JLabel("Port Number");
        lblPortNumber.setBounds(12, 163, 82, 33);
        contentPane.add(lblPortNumber);

        txtPortNumber = new JTextField();
        txtPortNumber.setText("30000");
        txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
        txtPortNumber.setColumns(10);
        txtPortNumber.setBounds(101, 163, 116, 33);
        contentPane.add(txtPortNumber);

        JButton btnConnect = new JButton("Connect");
        btnConnect.setBounds(12, 223, 205, 38);
        contentPane.add(btnConnect);
        LoginView.Myaction action = new LoginView.Myaction();
        btnConnect.addActionListener(action);
        txtUserName.addActionListener(action);
        txtIpAddress.addActionListener(action);
        txtPortNumber.addActionListener(action);


    }

    class Myaction implements ActionListener // 로그인 창 에서 ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = txtUserName.getText().trim();
            String ip_addr = txtIpAddress.getText().trim();
            String port_no = txtPortNumber.getText().trim();
            // Controller 가져오기 & 세팅
            controller = JavaObjClientMainViewController.getInstance();
            controller.setUserUserName(username);
            controller.setIp_addr(ip_addr);
            controller.setPort_no(port_no);

            try {
                //Controller socket, oos,ois 세팅
                Socket socket = new Socket(ip_addr, Integer.parseInt(port_no));
                controller.setSocket(socket);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.flush();
                controller.setOOS(oos);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                controller.setOIS(ois);

                //Controller에 보낼 msg 세팅
                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "100", controller.getUser().getUserName() + " Log in");

                //Controller에서 100 protocol Send
                controller.SendObject(obcm);

                //Controller에서 로그인 성공 시 AppView로 전환
                controller.ChangeLoginViewToAppView(username,ip_addr,port_no);
            } catch (IOException ex) {
                ex.printStackTrace();
                //AppendText("connect error");
            }
        }
    }
}

