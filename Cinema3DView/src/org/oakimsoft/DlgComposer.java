package org.oakimsoft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DlgComposer extends JDialog implements ActionListener, MouseListener, PaintComponentExtension {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= 1L;
	private final JPanel		contentPanel			= new JPanel();
	public JPanelPicture		pnLeftImage				= new JPanelPicture();
	public JPanelPicture		pnRightImage			= new JPanelPicture();
	public JSpinner				spnrResultOffsetX		= new JSpinner();
	public JSpinner				spnrResultOffsetY		= new JSpinner();
	public JSpinner				spnrLeftRotation		= new JSpinner();
	public JSpinner				spnrRightRotation		= new JSpinner();
	public JSpinner				spnrWidth				= new JSpinner();
	public JSpinner				spnrHeight				= new JSpinner();
	public GlobManager			globManager				= null;
	public StereoComposer		composer				= new StereoComposer();
	private ActionListener		externalActionListener	= null;
	private StereoViewer		sView					= null;
	private final ButtonGroup	buttonGroup				= new ButtonGroup();
	private JRadioButton		rdbtnLeftIsBase			= null;
	private JRadioButton		rdbtnImageCenter		= null;
	private JRadioButton		rdbtnRightIsBase		= null;
	private JLabel				lblLeftInfo;
	private JLabel				lblRightInfo;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { try { DlgComposer dialog = new
	 * DlgComposer(); dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * /** Create the dialog.
	 */
	public DlgComposer(GlobManager pGlobManager, StereoViewer stereoViewer, ActionListener al) {
		globManager = pGlobManager;
		sView = stereoViewer;
		externalActionListener = al;
		pnLeftImage.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnLeftImage.paintComponentExtension = (PaintComponentExtension) this;
		pnLeftImage.setExternalMouseListener((MouseListener) this);
		pnRightImage.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnRightImage.paintComponentExtension = (PaintComponentExtension) this;
		pnRightImage.setExternalMouseListener((MouseListener) this);

		setMinimumSize(new Dimension(600, 10));
		setTitle("Composer");
		setBounds(100, 100, 700, 507);
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
			pnLeftImage.setPreferredSize(new Dimension(10, 90));
			pnLeftImage.setMinimumSize(new Dimension(10, 70));
			pnLeftImage.biDisplaying = composer.biLeftImage;
			panel.add(pnLeftImage);
			pnRightImage.setPreferredSize(new Dimension(10, 90));
			pnRightImage.setMinimumSize(new Dimension(10, 90));
			pnRightImage.biDisplaying = composer.biRightImage;
			panel.add(pnRightImage);
		}
		{
			JPanel panel = new JPanel();
			panel.setMaximumSize(new Dimension(32767, 70));
			panel.setBorder(null);
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1);
				{
					JButton btnLoadLeftImage = new JButton("Load... ");
					panel_1.add(btnLoadLeftImage);
					btnLoadLeftImage.setMinimumSize(new Dimension(90, 23));
					btnLoadLeftImage.setMaximumSize(new Dimension(90, 23));
					btnLoadLeftImage.setPreferredSize(new Dimension(90, 23));
					btnLoadLeftImage.setActionCommand("LoadImageLeft");
					{
						lblLeftInfo = new JLabel("No image");
						lblLeftInfo.setForeground(Color.RED);
						lblLeftInfo.setPreferredSize(new Dimension(120, 14));
						lblLeftInfo.setMinimumSize(new Dimension(80, 14));
						lblLeftInfo.setMaximumSize(new Dimension(120, 14));
						lblLeftInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
						panel_1.add(lblLeftInfo);
					}
					btnLoadLeftImage.addActionListener(this);
				}
			}
			{
				JPanel panel_2 = new JPanel();
				panel.add(panel_2);
				panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				{
					JButton btnLoadRightImage = new JButton("Load... ");
					panel_2.add(btnLoadRightImage);
					btnLoadRightImage.setPreferredSize(new Dimension(90, 23));
					btnLoadRightImage.setMinimumSize(new Dimension(90, 23));
					btnLoadRightImage.setMaximumSize(new Dimension(90, 23));
					btnLoadRightImage.setActionCommand("LoadImageRight");
					{
						lblRightInfo = new JLabel("No image");
						lblRightInfo.setForeground(Color.RED);
						lblRightInfo.setPreferredSize(new Dimension(120, 14));
						lblRightInfo.setMinimumSize(new Dimension(80, 14));
						lblRightInfo.setMaximumSize(new Dimension(120, 14));
						lblRightInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
						panel_2.add(lblRightInfo);
					}
					btnLoadRightImage.addActionListener(this);
				}
			}
		}
		{
			JPanel panel2 = new JPanel();
			panel2.setPreferredSize(new Dimension(600, 35));
			panel2.setMinimumSize(new Dimension(600, 35));
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
						JLabel lblRotation = new JLabel("Rotation");
						panel_1.add(lblRotation);
					}
					{
						spnrLeftRotation.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
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
						JLabel label = new JLabel("Rotation");
						panel_1.add(label);
					}
					{
						spnrRightRotation.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
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
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setMaximumSize(new Dimension(32767, 30));
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				rdbtnLeftIsBase = new JRadioButton("Left is base");
				rdbtnLeftIsBase.setActionCommand("BASELEFT");
				rdbtnLeftIsBase.addActionListener(this);
				buttonGroup.add(rdbtnLeftIsBase);
				rdbtnLeftIsBase.setSelected(true);
				panel.add(rdbtnLeftIsBase);
			}
			{
				rdbtnImageCenter = new JRadioButton("Image center");
				rdbtnImageCenter.setActionCommand("BASECENTER");
				rdbtnImageCenter.addActionListener(this);			
				buttonGroup.add(rdbtnImageCenter);
				panel.add(rdbtnImageCenter);
			}
			{
				rdbtnRightIsBase = new JRadioButton("Right is base");
				rdbtnRightIsBase.setActionCommand("BASERIGHT");
				rdbtnRightIsBase.addActionListener(this);
				buttonGroup.add(rdbtnRightIsBase);
				panel.add(rdbtnRightIsBase);
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			// FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setMaximumSize(new Dimension(32767, 30));
			contentPanel.add(panel);
			{
				JLabel lblNewLabel = new JLabel("Offset X");
				panel.add(lblNewLabel);
			}
			{
				panel.add(spnrResultOffsetX);
				spnrResultOffsetX.setPreferredSize(new Dimension(60, 18));
				spnrResultOffsetX.setMaximumSize(new Dimension(60, 32767));
				spnrResultOffsetX.setMinimumSize(new Dimension(60, 18));
			}
			{
				JLabel lblOffsetY = new JLabel("Offset Y");
				panel.add(lblOffsetY);
			}
			{
				panel.add(spnrResultOffsetY);
				spnrResultOffsetY.setPreferredSize(new Dimension(60, 18));
				spnrResultOffsetY.setMinimumSize(new Dimension(60, 18));
				spnrResultOffsetY.setMaximumSize(new Dimension(60, 32767));
			}
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
			JPanel panel = new JPanel();
			panel.setMaximumSize(new Dimension(32767, 50));
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				JButton btnApply = new JButton("Apply");
				btnApply.setActionCommand("APPLY");
				btnApply.addActionListener(this);

				panel.add(btnApply);
				getRootPane().setDefaultButton(btnApply);
			}
			{
				JButton btnB1 = new JButton("Reset");
				btnB1.setActionCommand("RESET");
				btnB1.addActionListener(this);
				panel.add(btnB1);
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

		if (e.getActionCommand().startsWith("LoadImage")) {
			JDialog jdlg = new JDialog();
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics (JPEG,BMP)", "jpg", "jpeg", "bmp", "jps", "png");
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Open file...");
			if (globManager.registry.get("LastOpenFile") != null)
				chooser.setCurrentDirectory(new File(globManager.registry.get("LastOpenFile")));
			int returnVal = chooser.showOpenDialog(jdlg);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// globManager.currentFile = 0;
				globManager.registry.put("LastOpenFile", chooser.getSelectedFile().getAbsolutePath());
				// globManager.files.clear();
				// globManager.files.add(chooser.getSelectedFile());
				sView.biConverted = null;
				if (e.getActionCommand().endsWith("Left")) {
					composer.loadLeftImage(chooser.getSelectedFile());
					if (composer.biLeftImage != null) {
						composer.LeftAnchor.setLocation(composer.biLeftImage.getWidth() / 2, composer.biLeftImage.getHeight() / 2);
						if ((Integer) (this.spnrHeight.getValue()) == 0) {
							this.spnrHeight.setValue(composer.biLeftImage.getHeight());
							this.spnrResultOffsetY.setValue(-composer.biLeftImage.getHeight() / 2);
						}
						if ((Integer) (this.spnrWidth.getValue()) == 0) {
							this.spnrWidth.setValue(composer.biLeftImage.getWidth());
							this.spnrResultOffsetX.setValue(-composer.biLeftImage.getWidth() / 2);
						}
					}

					pnLeftImage.biDisplaying = composer.biLeftImage;
					this.pnLeftImage.repaint();
				}
				if (e.getActionCommand().endsWith("Right")) {
					composer.loadRightImage(chooser.getSelectedFile());
					if (composer.biRightImage != null) {
						composer.RightAnchor.setLocation(composer.biRightImage.getWidth() / 2, composer.biRightImage.getHeight() / 2);
						if ((Integer) (this.spnrHeight.getValue()) == 0) {
							this.spnrHeight.setValue(composer.biRightImage.getHeight());
							this.spnrResultOffsetY.setValue(-composer.biLeftImage.getHeight() / 2);
						}
						if ((Integer) (this.spnrWidth.getValue()) == 0) {
							this.spnrWidth.setValue(composer.biRightImage.getWidth());
							this.spnrResultOffsetX.setValue(-composer.biLeftImage.getWidth() / 2);
						}
					}
					pnRightImage.biDisplaying = composer.biRightImage;
					this.pnRightImage.repaint();
				}
				this.updateComposerByValues();
			}
			this.pnLeftImage.repaint();
			this.pnRightImage.repaint();
		}

		if (e.getActionCommand().equals("APPLY")) {
			this.updateComposerByValues();
			composer.convert();
			if (composer.biResult != null) {
				sView.biSource = composer.biResult;
				this.externalActionListener.actionPerformed(new ActionEvent(this, MouseEvent.MOUSE_CLICKED, C3DActions.refreshImage));
			}
			this.pnLeftImage.repaint();
			this.pnRightImage.repaint();
		}

		if (e.getActionCommand().startsWith("BASE")) {
			this.updateComposerByValues();
			composer.convert();
			if (composer.biResult != null) {
				sView.biSource = composer.biResult;
				this.externalActionListener.actionPerformed(new ActionEvent(this, MouseEvent.MOUSE_CLICKED, C3DActions.refreshImage));
			}
			this.pnLeftImage.repaint();
			this.pnRightImage.repaint();
		}

		if (e.getActionCommand().startsWith("RESET")) {

			composer.RightAnchor.setLocation(composer.biLeftImage.getWidth() / 2, composer.biLeftImage.getHeight() / 2);
			composer.RightAnchor.setLocation(composer.biRightImage.getWidth() / 2, composer.biRightImage.getHeight() / 2);

			this.pnLeftImage.repaint();
			this.pnRightImage.repaint();
		}

	}

	public void updateValuesFromComposer() {
		this.spnrWidth.setValue(this.composer.ResultWidth);
		this.spnrHeight.setValue(this.composer.ResultHeight);

	}

	public void updateComposerByValues() {
		if (this.composer.biLeftImage == null) {
			this.lblLeftInfo.setForeground(Color.red);
		} else {
			this.lblLeftInfo.setForeground(Color.black);
			this.lblLeftInfo.setText(this.composer.biLeftImage.getWidth() + " x " + this.composer.biLeftImage.getHeight());
		}
		if (this.composer.biRightImage == null) {
			this.lblRightInfo.setForeground(Color.red);
		} else {
			this.lblRightInfo.setForeground(Color.black);
			this.lblRightInfo.setText(this.composer.biRightImage.getWidth() + " x " + this.composer.biRightImage.getHeight());
		}

		this.composer.ResultWidth = (Integer) this.spnrWidth.getValue();
		this.composer.ResultHeight = (Integer) this.spnrHeight.getValue();

		this.composer.resultOffsetX = (Integer) this.spnrResultOffsetX.getValue();
		this.composer.resultOffsetY = (Integer) this.spnrResultOffsetY.getValue();

		this.composer.LeftRotation = (Double) this.spnrLeftRotation.getValue();
		this.composer.RightRotation = (Double) this.spnrRightRotation.getValue();

		if (this.rdbtnLeftIsBase.isSelected()) {
			this.composer.alignmentType = 0;
		}
		if (this.rdbtnImageCenter.isSelected()) {
			this.composer.alignmentType = 1;
		}
		if (this.rdbtnRightIsBase.isSelected()) {
			this.composer.alignmentType = 2;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println(e.getComponent().getName());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(this.pnLeftImage)) {
			composer.LeftAnchor.setLocation(this.pnLeftImage.coorsFromPanelToPic(e.getPoint()));
			this.pnLeftImage.repaint();
		}
		if (e.getSource().equals(this.pnRightImage)) {
			composer.RightAnchor.setLocation(this.pnRightImage.coorsFromPanelToPic(e.getPoint()));
			this.pnRightImage.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paintComponentExt(Object sourceObject, Graphics g) {
		if (sourceObject.equals(this.pnLeftImage)) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setBackground(Color.white);
			if (composer.alignmentType == 0) {
				g2d.setColor(Color.red);
			} else {
				g2d.setColor(Color.gray);
			}
			g2d.drawLine(this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).x,
					0,
					this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).x,
					this.pnLeftImage.getHeight());
			g2d.drawLine(0,
					this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).y,
					this.pnLeftImage.getWidth(),
					this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).y);
			g2d.fill3DRect(this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).x - 3,
					this.pnLeftImage.coorsFromPicToPanel(composer.LeftAnchor).y - 3, 6, 6, true);
			Point leftTop = new Point();
			leftTop.setLocation(this.pnLeftImage.coorsFromPicToPanel(
					new Point(composer.LeftAnchor.x + composer.resultOffsetX, 
							composer.LeftAnchor.y + composer.resultOffsetY)));
			Point LeftBottom = new Point();
			LeftBottom.setLocation(this.pnLeftImage.coorsFromPicToPanel(
					new Point(composer.LeftAnchor.x + composer.resultOffsetX + composer.ResultWidth,
							composer.LeftAnchor.y + composer.resultOffsetY + composer.ResultHeight)));

			g2d.drawRect(leftTop.x, leftTop.y, LeftBottom.x-leftTop.x, LeftBottom.y-leftTop.y);

		}
		if (sourceObject.equals(this.pnRightImage)) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setBackground(Color.white);
			if (composer.alignmentType == 2) {
				g2d.setColor(Color.red);
			} else {
				g2d.setColor(Color.gray);
			}
			g2d.drawLine(this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).x,
					0,
					this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).x,
					this.pnRightImage.getHeight());
			g2d.drawLine(0,
					this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).y,
					this.pnRightImage.getWidth(),
					this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).y);

			g2d.fill3DRect(this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).x - 3,
					this.pnRightImage.coorsFromPicToPanel(composer.RightAnchor).y - 3, 6, 6, true);

			Point leftTop = new Point();
			leftTop.setLocation(this.pnRightImage.coorsFromPicToPanel(
					new Point(composer.RightAnchor.x + composer.resultOffsetX, 
							composer.RightAnchor.y + composer.resultOffsetY)));
			Point rightBottom = new Point();
			rightBottom.setLocation(this.pnRightImage.coorsFromPicToPanel(
					new Point(composer.RightAnchor.x + composer.resultOffsetX + composer.ResultWidth,
							composer.RightAnchor.y + composer.resultOffsetY + composer.ResultHeight)));

			g2d.drawRect(leftTop.x, leftTop.y, rightBottom.x-leftTop.x, rightBottom.y-leftTop.y);
			

		}

	}

}
