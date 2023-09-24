package ui;

import model.Model_Authentication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UI_Authentication extends JFrame implements ActionListener {

	private JTextField username_field;
	private JPasswordField password_field;
	private JLabel form_feedback_label;
	
	public UI_Authentication() {
		// # Initialization

		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300, 100));
		setLocationRelativeTo(null); // Centers window relative to user's screen.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// # Username Panel

		JLabel un_label = new JLabel("Username");
		username_field = new JTextField(20);

		JPanel username_panel = new JPanel(new FlowLayout());
		username_panel.add(un_label);
		username_panel.add(username_field);

		// # Password Panel

		JLabel pw_label = new JLabel("Password");
		password_field = new JPasswordField(20);

		JPanel password_panel = new JPanel(new FlowLayout());
		password_panel.add(pw_label);
		password_panel.add(password_field);

		// # Submit

		JButton submit_btn = new JButton("Submit");
		submit_btn.addActionListener(e -> {
			Model_Authentication model = new Model_Authentication();
			int login_result = model.login(username_field.getText(), String.valueOf(password_field.getPassword()));

			if (login_result == Model_Authentication.LOGIN_UNSUCCESSFUL) {
				JOptionPane.showMessageDialog(this,
						"Invalid username or password.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			} else { // Login successful.
				System.out.println("Verification successful. Opening main GUI...");
				dispose();
				new UI_Main();
			}
		});

		// # Finalization

		add(username_panel);
		add(password_panel);
		form_feedback_label = new JLabel("");
		add(form_feedback_label);
		add(submit_btn);

		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		/*
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);
		
		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		button = new JButton("Submit!");
		button.setBounds(10, 80, 80, 25);
		button.addActionListener(new UI_Authentication());
		panel.add(button);
		
		success = new JLabel("");
		success.setBounds(10, 110, 300, 25);
		panel.add(success);

		frame.pack();
		frame.setVisible(true);
		 */

		new UI_Authentication();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public JTextField get_username_field() {
		return username_field;
	}

	public JPasswordField get_password_field() {
		return password_field;
	}

	public JLabel get_form_feedback_label() {
		return form_feedback_label;
	}
}
