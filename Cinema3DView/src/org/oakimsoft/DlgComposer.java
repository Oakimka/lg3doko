package org.oakimsoft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DlgComposer extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JPanelPicture pnLeftImage = new JPanelPicture();
	public JPanelPicture pnRightImage = new JPanelPicture();
	public JSpinner spnrLeftOffsetX = new JSpinner();
	public JSpinner spnrLeftOffsetY = new JSpinner();
	public JSpinner spnrLeftRotation = new JSpinner();
	public JSpinner spnrRightOffsetX = new JSpinner();
	public JSpinner spnrRightOffsetY = new JSpinner();
	public JSpinner spnrRightRotation = new JSpinner();
	public JSpinner spnrWidth = new JSpinner();
	public JSpinner spnrHeight = new JSpinner();
	public GlobManager globManager = null;	
	public StereoComposer composer = new StereoComposer();

	
	
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
	public DlgComposer(GlobManager pGlobManager) {
		globManager = pGlobManager;
		setMinimumSize(new Dimension(600, 10));
		setTitle("Composer");
		setBounds(100, 100, 700, 307);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setMinimumSize(new Dimension(600, 10));
		contentPanel.setPreferredSize(new Dimension(600, 10));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JButton btnLoadLeftImage = new JButton("Load Left Image");
				btnLoadLeftImage.setActionCommand("LoadLeftImage");
				btnLoadLeftImage.addActionListener(this);
				panel.add(btnLoadLeftImage);
			}
    		pnLeftImage.setPreferredSize(new Dimension(10, 90));
	    	pnLeftImage.setMinimumSize(new Dimension(10, 70));
			pnLeftImage.setMaximumSize(new Dimension(32767, 90));
			pnLeftImage.biDisplaying = composer.biLeftImage;
			panel.add(pnLeftImage);
			{
				JButton btnLoadRightImage = new JButton("Load Right Image");
				btnLoadRightImage.setActionCommand("LoadRightImage");
				btnLoadRightImage.addActionListener(this);
                panel.add(btnLoadRightImage);
			}
    		pnRightImage.setPreferredSize(new Dimension(10, 90));
    		pnRightImage.setMinimumSize(new Dimension(10, 90));
    		pnRightImage.setMaximumSize(new Dimension(32767, 90));
			pnRightImage.biDisplaying = composer.biRightImage;
			panel.add(pnRightImage);
		}
		{
			JPanel panel2 = new JPanel();
			panel2.setPreferredSize(new Dimension(600, 10));
			panel2.setMinimumSize(new Dimension(600, 10));
			contentPanel.add(panel2);
			panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
			{
				JPanel panel = new JPanel();
				panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				panel2.add(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				{
					JPanel panel_1 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_1.setMaximumSize(new Dimension(32767, 30));
					panel.add(panel_1);
					{
						JLabel lblNewLabel = new JLabel("Offset X");
						panel_1.add(lblNewLabel);
					}
					{
						spnrLeftOffsetX.setPreferredSize(new Dimension(60, 18));
						spnrLeftOffsetX.setMaximumSize(new Dimension(60, 32767));
						spnrLeftOffsetX.setMinimumSize(new Dimension(60, 18));
						panel_1.add(spnrLeftOffsetX);
					}
					{
						JLabel lblOffsetY = new JLabel("Offset Y");
						panel_1.add(lblOffsetY);
					}
					{
						panel_1.add(spnrLeftOffsetY);
						spnrLeftOffsetY.setPreferredSize(new Dimension(60, 18));
						spnrLeftOffsetY.setMinimumSize(new Dimension(60, 18));
						spnrLeftOffsetY.setMaximumSize(new Dimension(60, 32767));
					}
				}
				{
					JPanel panel_1 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_1.setMaximumSize(new Dimension(32767, 30));
					panel.add(panel_1);
					{
						JLabel lblRotation = new JLabel("Rotation");
						panel_1.add(lblRotation);
					}
					{
						spnrLeftRotation.setPreferredSize(new Dimension(60, 18));
						spnrLeftRotation.setMinimumSize(new Dimension(60, 18));
						spnrLeftRotation.setMaximumSize(new Dimension(60, 32767));
						panel_1.add(spnrLeftRotation);
					}
				}
			}
			{
				JPanel panel = new JPanel();
				panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				panel2.add(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				{
					JPanel panel_1 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_1.setMaximumSize(new Dimension(32767, 30));
					panel.add(panel_1);
					{
						JLabel label = new JLabel("Offset X");
						panel_1.add(label);
					}
					{
						spnrRightOffsetX.setPreferredSize(new Dimension(60, 18));
						spnrRightOffsetX.setMinimumSize(new Dimension(60, 18));
						spnrRightOffsetX.setMaximumSize(new Dimension(60, 32767));
						panel_1.add(spnrRightOffsetX);
					}
					{
						JLabel label = new JLabel("Offset Y");
						panel_1.add(label);
					}
					{
						spnrRightOffsetY.setPreferredSize(new Dimension(60, 18));
						spnrRightOffsetY.setMinimumSize(new Dimension(60, 18));
						spnrRightOffsetY.setMaximumSize(new Dimension(60, 32767));
						panel_1.add(spnrRightOffsetY);
					}
				}
				{
					JPanel panel_1 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_1.setMaximumSize(new Dimension(32767, 30));
					panel.add(panel_1);
					{
						JLabel label = new JLabel("Rotation");
						panel_1.add(label);
					}
					{
						spnrRightRotation.setPreferredSize(new Dimension(60, 18));
						spnrRightRotation.setMinimumSize(new Dimension(60, 18));
						spnrRightRotation.setMaximumSize(new Dimension(60, 32767));
						panel_1.add(spnrRightRotation);
					}
				}
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setMaximumSize(new Dimension(32767, 30));
			contentPanel.add(panel);
			{
				JLabel lblWifth = new JLabel("Width");
				panel.add(lblWifth);
			}
			{
				spnrWidth.setPreferredSize(new Dimension(60, 18));
				spnrWidth.setMinimumSize(new Dimension(60, 18));
				spnrWidth.setMaximumSize(new Dimension(60, 32767));
				panel.add(spnrWidth);
			}
			{
				JLabel lblHeight = new JLabel("Height");
				panel.add(lblHeight);
			}
			{
				spnrHeight.setPreferredSize(new Dimension(60, 18));
				spnrHeight.setMinimumSize(new Dimension(60, 18));
				spnrHeight.setMaximumSize(new Dimension(60, 32767));
				panel.add(spnrHeight);
			}
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



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("LoadRightImage")) {
			
		}
		if (e.getActionCommand().equals("LoadLeftImage")) {
			JDialog jdlg = new JDialog();
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Graphics (JPEG,BMP)", "jpg", "jpeg", "bmp", "jps","png");
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Open file...");
			if (globManager.registry.get("LastOpenFile") != null)
				chooser.setCurrentDirectory(new File(globManager.registry
						.get("LastOpenFile")));
			int returnVal = chooser.showOpenDialog(jdlg);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				this.currentFile = 0;
//				System.out.println("You chose to open this file: "
//						+ chooser.getSelectedFile().getName());
//				globManager.registry.put("LastOpenFile", chooser
//						.getSelectedFile().getAbsolutePath());
//				globManager.files.clear();
//				globManager.files.add(chooser.getSelectedFile());
//				sView.biConverted = null;
//				sView.load(globManager.files.get(this.currentFile));
//				sView.setTargetSize(jpnMain.getWidth(), jpnMain.getHeight());
//				sView.convert();
//				jpnMain.biDisplaying = sView.biConverted;
//				jpnMain.repaint();
//				this.updateMediaInfo();
			}
		}
		
		

		
	}

}
