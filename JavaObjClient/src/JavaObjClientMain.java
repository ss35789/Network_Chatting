
// JavaObjClient.java
// ObjecStream ����ϴ� ä�� Client
//sfsflksflnlksnfllasnfl
import java.awt.EventQueue;

import javax.swing.JFrame;

public class JavaObjClientMain extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaObjClientMainViewController main = JavaObjClientMainViewController.getInstance();
					main.activate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
