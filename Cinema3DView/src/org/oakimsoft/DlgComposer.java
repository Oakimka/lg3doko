package org.oakimsoft;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;

public class DlgComposer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		try {
			DlgComposer dialog = new DlgComposer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgComposer() {
		setTitle("Composer");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JButton btnLoadLeftImage = new JButton("Load Left Image");
				panel.add(btnLoadLeftImage);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setPreferredSize(new Dimension(10, 70));
				panel_1.setMinimumSize(new Dimension(10, 70));
				panel_1.setMaximumSize(new Dimension(32767, 70));
				panel_1.setBackground(Color.BLACK);
				panel.add(panel_1);
			}
			{
				JButton btnLoadRightImage = new JButton("Load Right Image");
				panel.add(btnLoadRightImage);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setPreferredSize(new Dimension(10, 70));
				panel_1.setMinimumSize(new Dimension(10, 70));
				panel_1.setMaximumSize(new Dimension(32767, 70));
				panel_1.setBackground(Color.BLACK);
				panel.add(panel_1);
			}
		}
		{
			JPanel panel2 = new JPanel();
			contentPanel.add(panel2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
