package com.hy.java.utility.frame;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

/**
 * 用{@code CardLayout}管理{@code JPanel}切换的{@code JFrame}。
 * <p>
 * 方法介紹：
 * <ul>
 * <li><code>addJMenu({@code JMenu} jMenu,{@code String} menu_obj_name,{@code int} index)</code>：向菜单栏中添加{@code JMenu}。</li>
 * <li><code>addGridBagPanel({@code GridBagPanel} gridBagPanel,{@code String} panel_obj_name)</code>：向
 * {@code JFrame}中添加{@code GridBagPanel}。</li>
 * <li><code>switchTo({@code Object} gridBagPanel)</code>：切换到目标gridBagPanel。</li>
 * </ul>
 * </p>
 * <p>
 * 操作步驟：
 * <ul>
 * <li>1、制作各个菜单（使用{@code JMenu}和{@code JMenuItem}），然后用<code>addJMenu()</code>把制作好的菜单添加到菜单栏中。</li>
 * <li>2、制作各个Panel（使用{@code GridBagPanel}），然后用<code>addGridBagPanel()</code>把制作好的各个Panel添加到整个窗口容器中。</li>
 * <li>3、制作Help
 * Contents菜单项对应的{@code JFrame}（{@code JFrame}的尺寸、所含的{@code GridBagPanel}的内容），然后用<code>setHelpContents()</code>把制作好的窗口添加到Help
 * Contents菜单项的监听中。</li>
 * <li>4、制作About菜单项对应的{@code Dialog}（{@code Dialog}的尺寸、内容），然后用<code>setAbout()</code>把制作好的对话框添加到About菜单项的监听中。</li>
 * <li>5、如需响应整个窗口的关闭事件，则先调用<code>setDefaultCloseOperation()</code>改变关闭操作，然后用<br>
 * <code>addWindowListener(new {@code WindowAdapter}(){public void windowClosing(WindowEvent we) {}})</code>添加关闭响应。</li>
 * </ul>
 * 注：1、制作各个菜单时，必须用{@code JMenu}和{@code JMenuItem}的setName()为每个项设置标识。2、用<code>switchTo()</code>管理不同Panel之间的切换。
 * </p>
 * 
 * @author chiefeweight
 */
public class CardFrame extends JFrame {
	public static final int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int default_width = CardFrame.screen_width / 2;
	public static final int default_height = CardFrame.screen_height / 2;
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -4754225216291161975L;
	private CardLayout card_layout;
	private Map<String, JMenu> menu_map;
	private Map<String, GridBagPanel> panel_map;
	private JMenuBar menubar;
	private JMenu help_menu;
	private JMenuItem help_menu_item_Help_Contents;
	private JMenuItem help_menu_item_About;

	/**
	 * {@code CardFrame}的构造法。
	 * <p>
	 * <code>width</code>和<code>height</code>的单位均为像素。
	 */
	public CardFrame(String title, int width, int height) {
		this.menu_map = new HashMap<>();
		this.panel_map = new HashMap<>();
		/* 初始化窗口 */
		this.initFrame(title, width, height);
		/* 初始化菜单栏 */
		this.initJMenuBar(title);
		/* 最后设置可见 */
		this.setVisible(true);
	}

	private void initFrame(String title, int width, int height) {
		this.setTitle(title);
		this.setSize(width, height);
		this.setLocation((CardFrame.screen_width - width) / 2, (CardFrame.screen_height - height) / 2);
		this.card_layout = new CardLayout();
		this.setLayout(this.card_layout);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void initJMenuBar(String title) {
		// 菜单栏
		this.menubar = new JMenuBar();
		this.setJMenuBar(this.menubar);
		// "Help"菜单
		this.help_menu = new JMenu("Help");
		this.help_menu.setName("help_menu");
		this.addJMenu(this.help_menu, this.help_menu.getName(), 0);
		// "Help Contents"菜单项
		this.help_menu_item_Help_Contents = new JMenuItem("Help Contents");
		this.help_menu_item_Help_Contents.setName("help_menu_item_Help_Contents");
		this.help_menu.add(this.help_menu_item_Help_Contents, 0);
		this.help_menu.addSeparator();
		// "About"菜单项
		this.help_menu_item_About = new JMenuItem("About " + title);
		this.help_menu_item_About.setName("help_menu_item_About");
		this.help_menu.add(this.help_menu_item_About, -1);
	}

	/**
	 * 向菜单栏中添加菜单，即向{@code JMenuBar}中添加{@code JMenu}。
	 * 
	 * @param jMenu         要添加的菜单
	 * @param menu_obj_name 所添加菜单在其所属{@code CardFrame}中的标识，不是这个菜单显示的文本。
	 * @param index         所添加菜单是从左数第几个。输入范围≥1。
	 */
	public void addJMenu(JMenu jMenu, String menu_obj_name, int index) {
		if (!this.menu_map.containsKey(menu_obj_name)) {
			jMenu.setName(menu_obj_name);
			this.menubar.add(jMenu, jMenu.getName(), index - 1);
			this.menu_map.put(jMenu.getName(), jMenu);
		} else {
			System.out.println(menu_obj_name + "已存在，该菜单添加失败。请给菜单对象重起menu_obj_name");
		}
	}

	/**
	 * 向{@code CardFrame}中添加{@code GridBagPanel}。
	 * 
	 * @param gridBagPanel 要添加的<code>gridBagPanel</code>
	 */
	public void addGridBagPanel(GridBagPanel gridBagPanel) {
		String panel_obj_name = gridBagPanel.getName();
		if (!this.panel_map.containsKey(panel_obj_name)) {
			this.add(gridBagPanel, panel_obj_name);
			this.panel_map.put(panel_obj_name, gridBagPanel);
			this.validate();
		} else {
			System.out.println(panel_obj_name + "已存在，该面板添加失败。请给面板对象重起panel_obj_name");
		}
	}

	/**
	 * 设置Help Contents菜单项对应的{@code JFrame}。
	 * 
	 * @param frame Help Contents菜单项对应的<code>frame</code>
	 */
	public void setHelpContents(JFrame frame) {
		this.help_menu_item_Help_Contents.addActionListener(new HelpContentsListener(frame));
	}

	/**
	 * Help Contents菜单项的监听器
	 */
	private class HelpContentsListener implements ActionListener {
		private JFrame frame;
		private int default_width;
		private int default_height;

		public HelpContentsListener(JFrame frame) {
			this.frame = frame;
			this.frame.setTitle("Help");
			this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.default_width = this.frame.getWidth();
			this.default_height = this.frame.getHeight();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.frame.setSize(this.default_width, this.default_height);
			this.frame.setLocation(CardFrame.this.getX() + (CardFrame.this.getWidth() - this.frame.getWidth()) / 2, CardFrame.this.getY() + (CardFrame.this.getHeight() - this.frame.getHeight()) / 2);
			this.frame.setVisible(true);
		}
	}

	/**
	 * 设置About菜单项对应的{@code Dialog}。
	 * 
	 * @param dialog About菜单项对应的<code>dialog</code>
	 */
	public void setAbout(Dialog dialog) {
		this.help_menu_item_About.addActionListener(new AboutListener(dialog));
	}

	/**
	 * About菜单项的监听器
	 */
	private class AboutListener implements ActionListener {
		private Dialog dialog;
		private int default_width;
		private int default_height;

		public AboutListener(Dialog dialog) {
			this.dialog = dialog;
			this.dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			this.dialog.setTitle(CardFrame.this.help_menu_item_About.getText());
			this.dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					AboutListener.this.dialog.dispose();
				}
			});
			this.default_width = this.dialog.getWidth();
			this.default_height = this.dialog.getHeight();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.dialog.setSize(this.default_width, this.default_height);
			this.dialog.setLocation(CardFrame.this.getX() + (CardFrame.this.getWidth() - this.dialog.getWidth()) / 2,
					CardFrame.this.getY() + (CardFrame.this.getHeight() - this.dialog.getHeight()) / 2);
			this.dialog.setVisible(true);
		}
	}

	/**
	 * 根据menu_obj_name，返回菜单
	 * 
	 * @param menu_obj_name 菜单的menu_obj_name
	 * @return 菜单。如果该{@code CardFrame}中不包含menu_obj_name，则返回{@code null}
	 */
	public JMenu getMenu(String menu_obj_name) {
		JMenu result = null;
		if (this.menu_map.containsKey(menu_obj_name)) {
			result = this.menu_map.get(menu_obj_name);
		}
		return result;
	}

	/**
	 * 根据panel_obj_name，返回面板
	 * 
	 * @param panel_obj_name 面板的panel_obj_name
	 * @return 面板。如果该{@code CardFrame}中不包含panel_obj_name，则返回{@code null}
	 */
	public GridBagPanel getGridBagPanel(String panel_obj_name) {
		GridBagPanel result = null;
		if (this.panel_map.containsKey(panel_obj_name)) {
			result = this.panel_map.get(panel_obj_name);
		}
		return result;
	}

	/**
	 * 返回所有面板
	 * 
	 * @return 所有面板
	 */
	public Map<String, GridBagPanel> getAllGridBagPanel() {
		return this.panel_map;
	}

	/**
	 * 切换到目标gridBagPanel
	 * 
	 * @param gridBagPanel 目标gridBagPanel
	 */
	public void switchTo(Object gridBagPanel) {
		Container contentPane = this.getContentPane();
		if (gridBagPanel instanceof String) {
			this.card_layout.show(contentPane, (String) gridBagPanel);
		} else if (gridBagPanel instanceof GridBagPanel) {
			this.card_layout.show(contentPane, contentPane.getComponent(contentPane.getComponentZOrder((Component) gridBagPanel)).getName());
		}
	}
}
