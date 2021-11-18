package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import table.MyTableHeaderRenderer;
import table.MyTableModel;
import table.MyTableRenderer;
import table.TableEntry;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;


public class MainFrame extends JFrame implements ActionListener, View{

	private static final String appName = "HttpTool";
	private static final String appVersion = "v.0.1";
	private int frameWidth = 600;
	private int frameHeight = 800;
	
	// Menu
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	
	// topPanel
	private JTextField txtUrl;
	private JComboBox<String> methodBox;
	
	// properties Panel
	private JPanel propertiesPanel;
	private JTable propertiesTable;
	private MyTableModel tableModel;
	
	// button Panel
	private JPanel buttonPanel;
	private JButton startButton;
	private JProgressBar progressBar;
	private JButton addEntryButton;
	private JButton deleteEntryButton;
	
	
	// content Panel
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setTitle(appName+" - "+appVersion);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, frameHeight, frameWidth);
		this.setLocation(screenSize.width/2-this.getSize().width/2, screenSize.height/2-this.getSize().height/2);
		
		initMenu();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(frameWidth, frameHeight));
		setContentPane(contentPane);
		GridBagLayout contentLayout = new GridBagLayout();
		contentLayout.columnWeights = new double[]{1.0};
		contentLayout.rowWeights = new double[]{0.0, 0.0, 0.1};
		contentPane.setLayout(contentLayout);
		
		initTopPanel();
		initPropertiesPanel();
		initButtonPanel();
	}
	
	private void initMenu(){
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File...");
		menuBar.add(fileMenu);
		
		openMenuItem = new JMenuItem("Open record...");
		openMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		
		saveMenuItem = new JMenuItem("Save record...");
		saveMenuItem.addActionListener(this);
		fileMenu.add(saveMenuItem);
		
		exitMenuItem = new JMenuItem("Exit...");
		exitMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
	}
	
	private void initTopPanel(){
		JPanel topPanel = new JPanel();
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[]{86, 86};
		gbl_topPanel.rowHeights = new int[] {35, 35};
		gbl_topPanel.columnWeights = new double[]{0.0, 1.0};
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0};
		topPanel.setLayout(gbl_topPanel);
		
		addLabel("URL", 0, topPanel);
		addLabel("Method", 1, topPanel);
		
		txtUrl = new JTextField("http://");
		txtUrl.addActionListener(this);
	    GridBagConstraints gridBagConstraintForAddress = new GridBagConstraints();
	    gridBagConstraintForAddress.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForAddress.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForAddress.gridx = 1;
	    gridBagConstraintForAddress.gridy = 0;
	    gridBagConstraintForAddress.anchor = GridBagConstraints.NORTHWEST;
	    topPanel.add(txtUrl, gridBagConstraintForAddress);
	    txtUrl.setColumns(10);
	    
	    methodBox = new JComboBox<String>();
	    methodBox.setBackground(Color.WHITE);
	    methodBox.addItem("POST");
	    methodBox.addItem("GET");
	    methodBox.addItem("DELETE");
	    methodBox.addItem("HEAD");
	    methodBox.addItem("PUT");
	    methodBox.addItem("CONNECT");
	    methodBox.addItem("OPTIONS");
	    methodBox.addItem("TRACE");
	    methodBox.addItem("PATCH");
	    methodBox.addActionListener(this);
	    GridBagConstraints gridBagConstraintForMethodBox = new GridBagConstraints();
	    gridBagConstraintForMethodBox.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForMethodBox.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForMethodBox.gridx = 1;
	    gridBagConstraintForMethodBox.gridy = 1;
	    topPanel.add(methodBox, gridBagConstraintForMethodBox);
//	    methodBox.setColumns(10);
		
		
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(0, 0, 0, 0);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.anchor = GridBagConstraints.NORTHWEST;
		contentPane.add(topPanel, c);
	}
	
	private void initPropertiesPanel(){
		propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BorderLayout(0, 0));
		
		tableModel = new MyTableModel();
		fillTestProperties(tableModel);
		propertiesTable = new JTable(tableModel);
		propertiesTable.setRowHeight(30);
		propertiesTable.getTableHeader().setDefaultRenderer(new MyTableHeaderRenderer());
		
		propertiesTable.setDefaultRenderer(String.class, new MyTableRenderer());
		propertiesTable.setRowSelectionAllowed(true);
		
		JScrollPane scrollpane = new JScrollPane(propertiesTable);
		propertiesPanel.add(scrollpane);
		
		propertiesTable.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				Point p = e.getPoint();
				int row = propertiesTable.rowAtPoint(p);
				int column = propertiesTable.columnAtPoint(p);
				System.out.println(propertiesTable.getSelectedRow());
				if(row != -1 && column == 0) {
					String key = (String)propertiesTable.getValueAt(propertiesTable.getSelectedRow(), propertiesTable.getSelectedColumn());
					System.out.println(key);
				}else if(row != -1 && column == 1) {
					String value = (String)propertiesTable.getValueAt(propertiesTable.getSelectedRow(), propertiesTable.getSelectedColumn());
					System.out.println(value);
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
		GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(0, 0, 0, 0);
	    c.gridx = 0;
	    c.gridy = 1;
	    c.anchor = GridBagConstraints.NORTHWEST;
		contentPane.add(propertiesPanel, c);
	}
	
	private void initButtonPanel(){
		buttonPanel = new JPanel();
//		buttonsPanel.setBounds(5, 290, 782, 42);
		buttonPanel.setLayout(new BorderLayout(0, 0));
		startButton = new JButton("Start");
		startButton.setBackground(Color.GREEN);
		startButton.setSize(150, 30);
		startButton.setPreferredSize(new Dimension(150, 5));
		startButton.addActionListener(this);
		
		JPanel tableButtonPanel = new JPanel();
		tableButtonPanel.setLayout(null);
		tableButtonPanel.setPreferredSize(new Dimension(150,5));
		addEntryButton = new JButton("Add new entry");
		addEntryButton.setSize(170, 30);
		deleteEntryButton = new JButton("Delete selected entry");
		deleteEntryButton.setSize(150, 30);
		deleteEntryButton.setBounds(180, 0, 170, 30);
		tableButtonPanel.add(addEntryButton);
		tableButtonPanel.add(deleteEntryButton);
		buttonPanel.add(tableButtonPanel, BorderLayout.CENTER);
		
		JPanel tmpStart = new JPanel();
		tmpStart.setLayout(null);
		tmpStart.add(startButton);
		tmpStart.setPreferredSize(new Dimension(150,5));
		buttonPanel.add(tmpStart, BorderLayout.EAST);
//		buttonPanel.add(startButton, BorderLayout.EAST);
		
		progressBar = new JProgressBar();
		progressBar.setEnabled(false);
		progressBar.setForeground(Color.BLUE);
		buttonPanel.add(progressBar, BorderLayout.SOUTH);
		
		GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(0, 0, 0, 0);
	    c.gridx = 0;
	    c.gridy = 2;
	    c.anchor = GridBagConstraints.NORTHWEST;
		contentPane.add(buttonPanel, c);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.exitMenuItem){
			System.exit(NORMAL);
		}else if(e.getSource() == this.openMenuItem){
			// implementation needed
		}else if(e.getSource() == this.saveMenuItem){
			// implementation needed
		}else if(e.getSource() == this.addEntryButton){
			tableModel.addEntry(new TableEntry("key", "value"));
		}
		
	}
	
	private void addLabel(String labelText, int yPos, Container containingPanel) {
	    JLabel label = new JLabel(labelText);
	    GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
	    gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
	    gridBagConstraintForLabel.gridx = 0;
	    gridBagConstraintForLabel.gridy = yPos;
	    gridBagConstraintForLabel.anchor = GridBagConstraints.NORTHWEST;
	    containingPanel.add(label, gridBagConstraintForLabel);
	}
	
	private void fillTestProperties(MyTableModel myTableModel){
		
		for(int i = 0; i < 10; i++){
			myTableModel.addEntry(new TableEntry("Key "+i, "Value "+i));
		}
		
	}

	public void setFinishedStatus(boolean status) {
		// TODO Auto-generated method stub
		
	}

}
