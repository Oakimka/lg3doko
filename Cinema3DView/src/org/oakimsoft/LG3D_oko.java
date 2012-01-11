package org.oakimsoft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LG3D_oko implements ActionListener {

	// private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JToolBar globalToolbar;
	private JToolBar downToolbar;
	public PnDraw jpnMain = null;
	public StereoViewer sView = new StereoViewer();
	public JSlider jsliderParallax = null;
	public JLabel lblMediaInfo = new JLabel();
	public BufferedImage biBack = null;
	public GlobManager globManager = new GlobManager();
	public DlgComposer composer = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LG3D_oko window = new LG3D_oko();

					window.frame.setVisible(true);
					window.frame.setExtendedState(Frame.MAXIMIZED_BOTH);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LG3D_oko() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				sView.convert();
				if (sView.biConverted != null)
					jpnMain.biDisplaying = sView.biConverted;
				jpnMain.repaint();
			
			}

		});

		frame.setTitle("LG3D oKo (v.0.1.0)");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setExtendedState(Frame.MAXIMIZED_BOTH);

		this.globalToolbar = new JToolBar();

		try {
			// biBack = new
			// ImageIcon(this.getClass().getResource("/pic/36x36/HFLR.png");

			biBack = ImageIO.read(this.getClass().getResource(
					"/pic/all/LOGOc3dv.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}

		JButton jbtnOpen = new JButton();
		jbtnOpen.setText("File..");
		jbtnOpen.setActionCommand("OpenFile");
		jbtnOpen.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/24x24/fileopen.png")));
		
		jbtnOpen.addActionListener(this);
		this.globalToolbar.add(jbtnOpen);

		JButton jbtnOpenFolder = new JButton();
		jbtnOpenFolder.setText("Folder...");
		jbtnOpenFolder.setActionCommand("OpenFolder");
		jbtnOpenFolder.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/24x24/folder_open.png")));
		jbtnOpenFolder.addActionListener(this);
		this.globalToolbar.add(jbtnOpenFolder);

		JButton jbtnComposer = new JButton();
		jbtnComposer.setText("Composer");
		jbtnComposer.setActionCommand("Composer");
		jbtnComposer.addActionListener(this);
		jbtnComposer.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/24x24/gnome-run.png")));
		this.globalToolbar.add(jbtnComposer);

		
		this.globalToolbar.add(this.lblMediaInfo);		
		
		// JButton jbtnClose = new JButton();
		// jbtnClose.setText("Close");
		// jbtnClose.setActionCommand("Close");
		// jbtnClose.addActionListener(this);
		// this.globalToolbar.add(jbtnClose);
		//

		frame.getContentPane().add(this.globalToolbar, BorderLayout.NORTH);

		this.downToolbar = new JToolBar();

		JButton jbtnREFRESH = new JButton();
		jbtnREFRESH.setActionCommand(C3DActions.refreshImage);
		jbtnREFRESH.addActionListener(this);
		jbtnREFRESH.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/Refresh.png")));
		jbtnREFRESH.setToolTipText("Refresh image");


		JButton jbtnHHLR = new JButton();
		jbtnHHLR.setActionCommand(C3DActions.setTypeHHLR);
		jbtnHHLR.addActionListener(this);
		jbtnHHLR.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/HHLR.png")));

		JButton jbtnHHRL = new JButton();
		jbtnHHRL.setActionCommand(C3DActions.setTypeHHRL);
		jbtnHHRL.addActionListener(this);
		jbtnHHRL.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/HHRL.png")));

		JButton jbtnHFLR = new JButton();
		jbtnHFLR.setActionCommand(C3DActions.setTypeHFLR);
		jbtnHFLR.addActionListener(this);
		jbtnHFLR.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/HFLR.png")));

		JButton jbtnHFRL = new JButton();
		jbtnHFRL.setActionCommand(C3DActions.setTypeHFRL);
		jbtnHFRL.addActionListener(this);
		jbtnHFRL.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/HFRL.png")));

		JButton jbtnVHLR = new JButton();
		jbtnVHLR.setActionCommand(C3DActions.setTypeVHLR);
		jbtnVHLR.addActionListener(this);
		jbtnVHLR.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/VHLR.png")));

		JButton jbtnVHRL = new JButton();
		jbtnVHRL.setActionCommand(C3DActions.setTypeVHRL);
		jbtnVHRL.addActionListener(this);
		jbtnVHRL.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/VHRL.png")));

		JButton jbtnVFLR = new JButton();
		jbtnVFLR.setActionCommand(C3DActions.setTypeVFLR);
		jbtnVFLR.addActionListener(this);
		jbtnVFLR.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/VFLR.png")));

		JButton jbtnVFRL = new JButton();
		jbtnVFRL.setActionCommand(C3DActions.setTypeVFRL);
		jbtnVFRL.addActionListener(this);
		jbtnVFRL.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/VFRL.png")));

		JButton jbtnASIS = new JButton();
		jbtnASIS.setActionCommand(C3DActions.setTypeASIS);
		jbtnASIS.addActionListener(this);
		jbtnASIS.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/PIC_ASIS.png")));
		jbtnASIS.setToolTipText("Show image as is");

		JButton jbtnSIZEFS = new JButton();
		jbtnSIZEFS.setActionCommand(C3DActions.setSIZEFS);
		jbtnSIZEFS.addActionListener(this);
		jbtnSIZEFS.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/SIZEFS.png")));
		jbtnSIZEFS.setToolTipText("Fit window ");

		JButton jbtnSIZE100 = new JButton();
		jbtnSIZE100.setActionCommand(C3DActions.setSIZE100);
		jbtnSIZE100.addActionListener(this);
		jbtnSIZE100.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/SIZE100.png")));
		jbtnSIZE100.setToolTipText("Zoom 1x");

		JButton jbtnSIZE050 = new JButton();
		jbtnSIZE050.setActionCommand(C3DActions.setSIZE050);
		jbtnSIZE050.addActionListener(this);
		jbtnSIZE050.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/SIZE050.png")));
		jbtnSIZE050.setToolTipText("Zoom 50%");

		JButton jbtnSIZE025 = new JButton();
		jbtnSIZE025.setActionCommand(C3DActions.setSIZE025);
		jbtnSIZE025.addActionListener(this);
		jbtnSIZE025.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/SIZE025.png")));
		jbtnSIZE025.setToolTipText("Zoom 25%");

		JButton jbtnPREV = new JButton();
		jbtnPREV.setActionCommand(C3DActions.selectPrevFile);
		jbtnPREV.addActionListener(this);
		jbtnPREV.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/PREV.png")));
		jbtnPREV.setToolTipText("Prev image");

		JButton jbtnNEXT = new JButton();
		jbtnNEXT.setActionCommand(C3DActions.selectNextFile);
		jbtnNEXT.addActionListener(this);
		jbtnNEXT.setIcon(new ImageIcon(this.getClass().getResource(
				"/pic/36x36/NEXT.png")));
		jbtnNEXT.setToolTipText("Next image");

		jsliderParallax = new JSlider();
		jsliderParallax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (jpnMain != null) {
					sView.parallax_pix = (int) jsliderParallax.getValue();
					sView.convert();
					jpnMain.biDisplaying = sView.biConverted;
					jpnMain.repaint();
				}
			}
		});

		jsliderParallax.setMinimum(-100);
		jsliderParallax.setMaximum(100);
		jsliderParallax.setValue(0);
		jsliderParallax.setMajorTickSpacing(20);
		jsliderParallax.setMinorTickSpacing(1);
		jsliderParallax.setPaintTicks(true);
		jsliderParallax.setPaintLabels(true);
		jsliderParallax.setMaximumSize(new Dimension(400, 500));

		this.downToolbar.add(jbtnREFRESH);
		this.downToolbar.add(jbtnASIS);
		this.downToolbar.add(jbtnHFLR);
		this.downToolbar.add(jbtnHFRL);
		this.downToolbar.add(jbtnVFLR);
		this.downToolbar.add(jbtnVFRL);
		this.downToolbar.add(jbtnHHRL);
		this.downToolbar.add(jbtnHHLR);
		this.downToolbar.add(jbtnVHLR);
		this.downToolbar.add(jbtnVHRL);
		this.downToolbar.add(jbtnSIZEFS);
		this.downToolbar.add(jbtnSIZE100);
		this.downToolbar.add(jbtnSIZE050);
		this.downToolbar.add(jbtnSIZE025);
		this.downToolbar.add(jsliderParallax);
		this.downToolbar.add(jbtnPREV);
		this.downToolbar.add(jbtnNEXT);

		sView.setStereoType(StereoViewer.AS_IS);
		sView.setStretch(StereoViewer.STRETCH_SIZE100);

		frame.getContentPane().add(this.downToolbar, BorderLayout.SOUTH);

		jpnMain = new PnDraw(sView);
		jpnMain.biDisplaying = this.biBack;

		frame.getContentPane().add(jpnMain, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("OpenFile")) {
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
				globManager.currentFile = 0;
				System.out.println("You chose to open this file: "
						+ chooser.getSelectedFile().getName());
				globManager.registry.put("LastOpenFile", chooser
						.getSelectedFile().getAbsolutePath());
				globManager.files.clear();
				globManager.files.add(chooser.getSelectedFile());
				sView.biConverted = null;
				sView.load(globManager.files.get(globManager.currentFile));
				sView.setTargetSize(jpnMain.getWidth(), jpnMain.getHeight());
				sView.convert();
				jpnMain.biDisplaying = sView.biConverted;
				jpnMain.repaint();
				this.updateMediaInfo();
			}
		}

		if (ae.getActionCommand().equals("OpenFolder")) {
			JDialog jdlg = new JDialog();
			JFileChooser chooser = new JFileChooser();
			// FileNameExtensionFilter filter = new FileNameExtensionFilter(
			// "Graphics (JPEG,BMP)", "jpg", "jpeg","bmp");
			// chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Open folder...");
			if (globManager.registry.get("LastOpenFolder") != null)
				chooser.setCurrentDirectory(new File(globManager.registry
						.get("LastOpenFolder")));
			int returnVal = chooser.showOpenDialog(jdlg);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				globManager.currentFile = 0;
				globManager.registry.put("LastOpenFolder", chooser
						.getSelectedFile().getAbsolutePath());
				File[] files;
				// This filter only returns directories
				FilenameFilter filter = new FilenameFilter() {
					public boolean accept(File file, String name) {
						return name.toUpperCase().endsWith(".JPG")
								|| name.toUpperCase().endsWith(".JPEG")
								|| name.toUpperCase().endsWith(".JPS")
								|| name.toUpperCase().endsWith(".PNG")
								|| name.toUpperCase().endsWith(".BMP");
					}
				};
				files = chooser.getSelectedFile().listFiles(filter);
				globManager.files.clear();
				for (int i = 0; i < files.length; i++) {
					globManager.files.add(files[i]);
				}

				sView.biConverted = null;
				if (globManager.files.size() > 0) {
					sView.load(globManager.files.get(globManager.currentFile));
					sView.setTargetSize(jpnMain.getWidth(), jpnMain.getHeight());
					sView.convert();
					jpnMain.biDisplaying = sView.biConverted;
				} else {
					jpnMain.biDisplaying = this.biBack;
				}
				this.updateMediaInfo();
				jpnMain.repaint();
			}
		}

		
		if (ae.getActionCommand().equals("Composer")) {
			if (composer == null)
			  composer = new DlgComposer(globManager, sView, (ActionListener)this);
			composer.setVisible(true);
			
		}

		if (ae.getActionCommand().equals("Close")) {
			System.exit(0);
		}

		if (ae.getActionCommand().equals(C3DActions.refreshImage)) {
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeASIS)) {
			sView.setStereoType(StereoViewer.AS_IS);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeHHLR)) {
			sView.setStereoType(StereoViewer.HORIZONTAL_HALFSIZE_LR);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeHHRL)) {
			sView.setStereoType(StereoViewer.HORIZONTAL_HALFSIZE_RL);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeHFLR)) {
			sView.setStereoType(StereoViewer.HORIZONTAL_FULLSIZE_LR);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeHFRL)) {
			sView.setStereoType(StereoViewer.HORIZONTAL_FULLSIZE_RL);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeVHLR)) {
			sView.setStereoType(StereoViewer.VERTICAL_HALFSIZE_LR);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeVHRL)) {
			sView.setStereoType(StereoViewer.VERTICAL_HALFSIZE_RL);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeVFLR)) {
			sView.setStereoType(StereoViewer.VERTICAL_FULLSIZE_LR);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setTypeVFRL)) {
			sView.setStereoType(StereoViewer.VERTICAL_FULLSIZE_RL);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setSIZEFS)) {
			sView.setStretch(StereoViewer.STRETCH_FULLSCREEN);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setSIZE100)) {
			sView.setStretch(StereoViewer.STRETCH_SIZE100);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setSIZE050)) {
			sView.setStretch(StereoViewer.STRETCH_SIZE050);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.setSIZE025)) {
			sView.setStretch(StereoViewer.STRETCH_SIZE025);
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.repaint();
				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.selectNextFile)) {
			if (this.globManager.files.size() == 0)
				return;
			globManager.currentFile++;
			if (globManager.currentFile >= this.globManager.files.size()) {
				globManager.currentFile = 0;
			}
			sView.load(this.globManager.files.get(globManager.currentFile));
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			this.updateMediaInfo();
			jpnMain.repaint();
		}

		if (ae.getActionCommand().equals(C3DActions.selectPrevFile)) {
			if (this.globManager.files.size() == 0)
				return;

			globManager.currentFile--;
			if (globManager.currentFile < 0) {
				globManager.currentFile = this.globManager.files.size() - 1;
			}
			sView.load(this.globManager.files.get(globManager.currentFile));
			sView.convert();
			if (sView.biConverted != null)
				jpnMain.biDisplaying = sView.biConverted;
			this.updateMediaInfo();
			jpnMain.repaint();
		}

	}
	
	
	public void updateMediaInfo(){
		this.lblMediaInfo.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		this.lblMediaInfo.setText( 
				"   [ "+String.valueOf(globManager.currentFile+1)+"/"+String.valueOf(globManager.files.size())+" ]   " +
				
				globManager.files.get(globManager.currentFile).getAbsolutePath().concat(
						"  "+ " "
						
						));
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			sView.parallax_pix = (int) source.getValue();
			sView.convert();
			if (sView.biConverted != null)

				jpnMain.biDisplaying = sView.biConverted;
			jpnMain.repaint();

		}
	}

}
