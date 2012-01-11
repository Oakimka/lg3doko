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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class DlgComposer extends JDialog implements ActionListener, MouseListener, PaintComponentExtension, ChangeListener {

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
	private JSpinner			spnrLeftAnchorX;
	private JSpinner			spnrLeftAnchorY;
	private JSpinner			spnrRightAnchorX;
	private JSpinner			spnrRightAnchorY;
	private JCheckBox			chckbxAutoApply;
	public boolean				stopAutoApply			= false;

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
		setTitle("Stereo composer");
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
			pnLeftImage.setMinimumSize(new Dimension(10, 30));
			pnLeftImage.biDisplaying = composer.biLeftImage;
			panel.add(pnLeftImage);
			pnRightImage.setPreferredSize(new Dimension(10, 90));
			pnRightImage.setMinimumSize(new Dimension(10, 30));
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
				panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
				panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(10, 35));
			panel.setMaximumSize(new Dimension(32767, 35));
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1);
				{
					JLabel lblX = new JLabel("Anchor. X:");
					panel_1.add(lblX);
				}
				{
					spnrLeftAnchorX = new JSpinner();
					spnrLeftAnchorX.setPreferredSize(new Dimension(60, 18));
					spnrLeftAnchorX.setMinimumSize(new Dimension(60, 18));
					spnrLeftAnchorX.setMaximumSize(new Dimension(60, 32767));
					spnrLeftAnchorX.addChangeListener(this);
					panel_1.add(spnrLeftAnchorX);
				}
				{
					JLabel lblY = new JLabel("Y:");
					panel_1.add(lblY);
				}
				{
					spnrLeftAnchorY = new JSpinner();
					spnrLeftAnchorY.setPreferredSize(new Dimension(60, 18));
					spnrLeftAnchorY.setMinimumSize(new Dimension(60, 18));
					spnrLeftAnchorY.setMaximumSize(new Dimension(60, 32767));
					spnrLeftAnchorY.addChangeListener(this);
					panel_1.add(spnrLeftAnchorY);
				}
				{
					JLabel lblRotation = new JLabel(".  Angle :");
					panel_1.add(lblRotation);
				}
				{
					panel_1.add(spnrLeftRotation);
					spnrLeftRotation.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
					spnrLeftRotation.setPreferredSize(new Dimension(40, 18));
					spnrLeftRotation.setMinimumSize(new Dimension(40, 18));
					spnrLeftRotation.setMaximumSize(new Dimension(40, 32767));
					spnrLeftRotation.addChangeListener(this);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1);
				{
					JLabel lblAnchorX = new JLabel("Anchor. X:");
					panel_1.add(lblAnchorX);
				}
				{
					spnrRightAnchorX = new JSpinner();
					spnrRightAnchorX.setPreferredSize(new Dimension(60, 18));
					spnrRightAnchorX.setMinimumSize(new Dimension(60, 18));
					spnrRightAnchorX.setMaximumSize(new Dimension(60, 32767));
					spnrRightAnchorX.addChangeListener(this);
					panel_1.add(spnrRightAnchorX);
				}
				{
					JLabel lblY_1 = new JLabel("Y:");
					panel_1.add(lblY_1);
				}
				{
					spnrRightAnchorY = new JSpinner();
					spnrRightAnchorY.setPreferredSize(new Dimension(60, 18));
					spnrRightAnchorY.setMinimumSize(new Dimension(60, 18));
					spnrRightAnchorY.setMaximumSize(new Dimension(60, 32767));
					spnrRightAnchorY.addChangeListener(this);
					panel_1.add(spnrRightAnchorY);
				}
				{
					JLabel lblAngle = new JLabel(".  Angle :");
					panel_1.add(lblAngle);
				}
				{
					panel_1.add(spnrRightRotation);
					spnrRightRotation.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
					spnrRightRotation.setPreferredSize(new Dimension(40, 18));
					spnrRightRotation.setMinimumSize(new Dimension(40, 18));
					spnrRightRotation.setMaximumSize(new Dimension(40, 32767));
					spnrRightRotation.addChangeListener(this);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			// FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Croping", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setMaximumSize(new Dimension(32767, 30));
			contentPanel.add(panel);
			{
				JLabel lblNewLabel = new JLabel("Offset from anchor. X:");
				panel.add(lblNewLabel);
			}
			{
				panel.add(spnrResultOffsetX);
				spnrResultOffsetX.setPreferredSize(new Dimension(60, 18));
				spnrResultOffsetX.setMaximumSize(new Dimension(60, 32767));
				spnrResultOffsetX.setMinimumSize(new Dimension(60, 18));
				spnrResultOffsetX.addChangeListener(this);

			}
			{
				JLabel lblOffsetY = new JLabel(" Y:");
				panel.add(lblOffsetY);
			}
			{
				panel.add(spnrResultOffsetY);
				spnrResultOffsetY.setPreferredSize(new Dimension(60, 18));
				spnrResultOffsetY.setMinimumSize(new Dimension(60, 18));
				spnrResultOffsetY.setMaximumSize(new Dimension(60, 32767));
				spnrResultOffsetY.addChangeListener(this);
			}
			{
				JLabel lblWifth = new JLabel("Frame size. Width:");
				panel.add(lblWifth);
			}
			{
				spnrWidth.setPreferredSize(new Dimension(60, 18));
				spnrWidth.setMinimumSize(new Dimension(60, 18));
				spnrWidth.setMaximumSize(new Dimension(60, 32767));
				spnrWidth.addChangeListener(this);
				panel.add(spnrWidth);
			}
			{
				JLabel lblHeight = new JLabel("Height:");
				panel.add(lblHeight);
			}
			{
				spnrHeight.setPreferredSize(new Dimension(60, 18));
				spnrHeight.setMinimumSize(new Dimension(60, 18));
				spnrHeight.setMaximumSize(new Dimension(60, 32767));
				spnrHeight.addChangeListener(this);
				panel.add(spnrHeight);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel.setPreferredSize(new Dimension(10, 35));
			panel.setMinimumSize(new Dimension(10, 35));
			panel.setMaximumSize(new Dimension(32767, 35));
			contentPanel.add(panel);
			{
				chckbxAutoApply = new JCheckBox("Auto apply");
				panel.add(chckbxAutoApply);
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
			{
				JButton btnSaveResult = new JButton("Save result");
				btnSaveResult.setActionCommand("SAVE");
				btnSaveResult.addActionListener(this);
				panel.add(btnSaveResult);
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
						this.stopAutoApply = true;
						this.spnrLeftAnchorX.setValue(this.composer.LeftAnchor.x);
						this.spnrLeftAnchorY.setValue(this.composer.LeftAnchor.y);
						if ((Integer) (this.spnrHeight.getValue()) == 0) {
							this.spnrHeight.setValue(composer.biLeftImage.getHeight());
							this.spnrResultOffsetY.setValue(-composer.biLeftImage.getHeight() / 2);
						}
						if ((Integer) (this.spnrWidth.getValue()) == 0) {
							this.spnrWidth.setValue(composer.biLeftImage.getWidth());
							this.spnrResultOffsetX.setValue(-composer.biLeftImage.getWidth() / 2);
						}
						this.stopAutoApply = false;
						if (this.chckbxAutoApply.isSelected()){
							this.applyChanges();
						}
						
					}
					pnLeftImage.biDisplaying = composer.biLeftImage;
					this.pnLeftImage.repaint();
				}
				if (e.getActionCommand().endsWith("Right")) {
					composer.loadRightImage(chooser.getSelectedFile());
					if (composer.biRightImage != null) {
						composer.RightAnchor.setLocation(composer.biRightImage.getWidth() / 2, composer.biRightImage.getHeight() / 2);
						this.stopAutoApply = true;
						this.spnrRightAnchorX.setValue(this.composer.RightAnchor.x);
						this.spnrRightAnchorY.setValue(this.composer.RightAnchor.y);
						if ((Integer) (this.spnrHeight.getValue()) == 0) {
							this.spnrHeight.setValue(composer.biRightImage.getHeight());
							this.spnrResultOffsetY.setValue(-composer.biRightImage.getHeight() / 2);
						}
						if ((Integer) (this.spnrWidth.getValue()) == 0) {
							this.spnrWidth.setValue(composer.biRightImage.getWidth());
							this.spnrResultOffsetX.setValue(-composer.biRightImage.getWidth() / 2);
						}
						this.stopAutoApply = false;
						if (this.chckbxAutoApply.isSelected()){
							this.applyChanges();
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
			this.applyChanges();
		}
		
		if (e.getActionCommand().equals("SAVE")) {
			this.save();
			
		}
		

		if (e.getActionCommand().startsWith("RESET")) {
			if ((composer.biLeftImage != null) && (composer.biRightImage != null)) {
				this.stopAutoApply = true;
				this.spnrLeftAnchorX.setValue(composer.biLeftImage.getWidth() / 2);
				this.spnrLeftAnchorY.setValue(composer.biLeftImage.getHeight() / 2);
				this.spnrRightAnchorX.setValue(composer.biRightImage.getWidth() / 2);
				this.spnrRightAnchorY.setValue(composer.biRightImage.getHeight() / 2);
				this.spnrHeight.setValue(composer.biLeftImage.getHeight());
				this.spnrWidth.setValue(composer.biLeftImage.getWidth());
				this.spnrResultOffsetX.setValue(-composer.biLeftImage.getWidth() / 2);
				this.spnrLeftRotation.setValue(0);
				this.spnrRightRotation.setValue(0);
				this.spnrResultOffsetY.setValue(-composer.biLeftImage.getHeight() / 2);
				this.stopAutoApply = false;
				this.pnLeftImage.repaint();
				this.pnRightImage.repaint();
				this.updateComposerByValues();
				if (this.chckbxAutoApply.isSelected()){
					this.applyChanges();
				}
					
				
			}

		}

	}

	public void applyChanges() {
		this.updateComposerByValues();
		composer.convert();
		if (composer.biResult != null) {
			sView.biSource = composer.biResult;
			this.externalActionListener.actionPerformed(new ActionEvent(this, MouseEvent.MOUSE_CLICKED, C3DActions.refreshImage));
		}
		this.pnLeftImage.repaint();
		this.pnRightImage.repaint();
	}

	public void updateValuesFromComposer() {
		this.spnrWidth.setValue(this.composer.ResultWidth);
		this.spnrHeight.setValue(this.composer.ResultHeight);

	}

	public void updateComposerByValues() {
		if (this.composer.biLeftImage == null) {
			this.lblLeftInfo.setForeground(Color.red);
			this.lblLeftInfo.setText("No image");
		} else {
			this.lblLeftInfo.setForeground(Color.black);
			this.lblLeftInfo.setText(this.composer.biLeftImage.getWidth() + " x " + this.composer.biLeftImage.getHeight());
		}
		if (this.composer.biRightImage == null) {
			this.lblRightInfo.setForeground(Color.red);
			this.lblRightInfo.setText("No image");
		} else {
			this.lblRightInfo.setForeground(Color.black);
			this.lblRightInfo.setText(this.composer.biRightImage.getWidth() + " x " + this.composer.biRightImage.getHeight());
		}

		this.composer.ResultWidth = (Integer) this.spnrWidth.getValue();
		this.composer.ResultHeight = (Integer) this.spnrHeight.getValue();

		this.composer.resultOffsetX = (Integer) this.spnrResultOffsetX.getValue();
		this.composer.resultOffsetY = (Integer) this.spnrResultOffsetY.getValue();

		this.composer.LeftRotation = (Integer) (this.spnrLeftRotation.getValue());
		this.composer.RightRotation = (Integer) (this.spnrRightRotation.getValue());

		this.composer.LeftAnchor.x = (Integer) this.spnrLeftAnchorX.getValue();
		this.composer.LeftAnchor.y = (Integer) this.spnrLeftAnchorY.getValue();
		this.composer.RightAnchor.x = (Integer) this.spnrRightAnchorX.getValue();
		this.composer.RightAnchor.y = (Integer) this.spnrRightAnchorY.getValue();

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
			this.stopAutoApply = true;
			this.spnrLeftAnchorX.setValue(this.composer.LeftAnchor.x);
			this.stopAutoApply = false;
			this.spnrLeftAnchorY.setValue(this.composer.LeftAnchor.y);
			this.pnLeftImage.repaint();
		}
		if (e.getSource().equals(this.pnRightImage)) {
			composer.RightAnchor.setLocation(this.pnRightImage.coorsFromPanelToPic(e.getPoint()));
			this.stopAutoApply = true;
			this.spnrRightAnchorX.setValue(this.composer.RightAnchor.x);
			this.stopAutoApply = false;
			this.spnrRightAnchorY.setValue(this.composer.RightAnchor.y);
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
			g2d.setColor(Color.red);
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

			g2d.setColor(Color.green);
			g2d.drawRect(leftTop.x, leftTop.y, LeftBottom.x - leftTop.x, LeftBottom.y - leftTop.y);

		}
		if (sourceObject.equals(this.pnRightImage)) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setBackground(Color.white);
			g2d.setColor(Color.red);
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

			g2d.setColor(Color.green);
			g2d.drawRect(leftTop.x, leftTop.y, rightBottom.x - leftTop.x, rightBottom.y - leftTop.y);

		}

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (this.chckbxAutoApply.isSelected()) {
			if (!this.stopAutoApply)
				this.applyChanges();
		}

	}
	
	public void save(){
		if (composer.biResult==null){
			return;
		}
			
		
		JDialog jdlg = new JDialog();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics (png)", "png");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Save file...");
		chooser.setApproveButtonText("Save");
		if (globManager.registry.get("LastOpenFile") != null)
			chooser.setCurrentDirectory(new File(globManager.registry.get("LastOpenFile")));
		int returnVal = chooser.showOpenDialog(jdlg);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			globManager.registry.put("LastOpenFile", chooser.getSelectedFile().getAbsolutePath());
			try {
				 ImageIO.write( composer.biResult, "PNG", chooser.getSelectedFile());
				 } catch (IOException e) {
				 e.printStackTrace();
				 }
			
		}
		
	}

}
