package com;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {

	JLabel label;
	JTextField ecran;
	JTextPane text;
	JPanel connect;
	JPanel boutons;
	JPanel textArea;
	JPanel panelFinal;
	OutputStream out;

	public Window() {
		this.setTitle("TestCom Reb'UT");
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.connect = connect();
		this.boutons = sendMessagePanel();
		this.panelFinal = new JPanel();
		this.textArea = new JPanel();
		this.textArea = receiveMessage();
		panelFinal.add(connect);
		panelFinal.add(boutons);
		panelFinal.add(textArea);
		this.add(panelFinal);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		@SuppressWarnings("unused")
		Window w = new Window();
	}

	// Panel qui va contenir le champ pour saisir le nom du periph et le bouton
	// pour le connecter
	public JPanel connect() {
		JPanel connect = new JPanel();
		JButton bouton = new JButton("Connect");
		this.label = new JLabel("Nom du périphérique");
		ecran = new JTextField(40);
		text = new JTextPane();
		connect.add(label);
		connect.add(ecran);
		connect.add(bouton);
		bouton.addActionListener(this);
		connect.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Définition du périphérique", 0, 0, new Font("Dialog", 1, 12),
				Color.BLACK));

		return connect;
	}

	public JPanel sendMessagePanel() {
		JPanel sendMessage = new JPanel();

		// On cree les boutons pour les messages
		JButton a = new JButton("A");
		JButton b = new JButton("B");
		JButton c = new JButton("C");

		sendMessage.add(a);
		sendMessage.add(b);
		sendMessage.add(c);
		a.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);

		sendMessage.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Envoi de message", 0, 0,
				new Font("Dialog", 1, 12), Color.BLACK));

		return sendMessage;
	}

	public JPanel receiveMessage() {
		this.textArea.add(this.text);
		// scrollpane = new JScrollPane(this.text);
		// textArea.add(scrollpane);
		return this.textArea;
	}

	// public JPanel receiveMessage() {
	// JPanel receive = new JPanel();
	// JTextField ecran = new JTextField(40);
	// receive.add(ecran);
	// return receive;
	// }

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				this.out = serialPort.getOutputStream();

				(new Thread(new SerialWriter(this.out, this))).start();

				serialPort.addEventListener(new SerialReader(in, this));
				// serialPort.addEventListener(new SerialWriter(out)) ;
				serialPort.notifyOnDataAvailable(true);

			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			if (((JButton) source).getText().equals("A")) {
				sendMessage("A\n");
			} else if (((JButton) source).getText().equals("B")) {
				System.out.println("B");
			} else if (((JButton) source).getText().equals("C")) {
				System.out.println("C");
			} else if (((JButton) source).getText().equals("Connect")) {
				try {
					connect(this.ecran.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

	}

	private void sendMessage(String string) {
		byte[] b = string.getBytes();
		try {
			this.out.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public JTextPane getText() {
		return text;
	}

	public void setText(JTextPane text) {
		this.text = text;
	}

	// public JTextPane getPane(){
	// return scrollpane ;
	// }

}
