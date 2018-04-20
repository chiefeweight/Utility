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
 * <li>3、用<code>setHelp()</code>和<code>setAbout()</code>为两个菜单项设置响应面板，完成Help和About菜单项的制作。</li>
 * <li>4、如需响应关闭事件，则先调用<code>setDefaultCloseOperation()</code>改变关闭操作，然后用<br>
 * <code>addWindowListener(new {@code WindowAdapter}(){public void windowClosing(WindowEvent we) {}})</code>添加关闭响应。</li>
 * <br />
 * 注：1、制作各个菜单时，必须用{@code JMenu}和{@code JMenuItem}的setName()为每个项设置标识。2、用<code>switchTo()</code>管理不同Panel之间的切换。
 * </ul>
 * </p>
 * 
 * @author chiefeweight
 */
public class CardFrame extends JFrame {
	public static final int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -4754225216291161975L;
	private CardLayout card_layout;
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
		this.menubar = new JMenuBar();
		this.menubar.setName("menubar");
		this.setJMenuBar(this.menubar);
		this.help_menu = new JMenu("Help");
		this.help_menu.setName("help_menu");
		this.addJMenu(this.help_menu, this.help_menu.getName(), 0);
		this.help_menu_item_Help_Contents = new JMenuItem("Help Contents");
		this.help_menu_item_Help_Contents.setName("help_menu_item_Help_Contents");
		this.help_menu.add(this.help_menu_item_Help_Contents, 0);
		this.help_menu.addSeparator();
		this.help_menu_item_About = new JMenuItem("About " + title);
		this.help_menu_item_About.setName("help_menu_item_About");
		this.help_menu.add(this.help_menu_item_About, -1);
	}

	/**
	 * 向菜单栏中添加菜单，即向{@code JMenuBar}中添加{@code JMenu}。
	 * 
	 * @param jMenu
	 *            要添加的菜单
	 * @param menu_obj_name
	 *            所添加菜单在其所属{@code CardFrame}中的标识，不是这个菜单显示的文本。
	 * @param index
	 *            所添加菜单是从左数第几个。输入范围≥1。
	 */
	public void addJMenu(JMenu jMenu, String menu_obj_name, int index) {
		this.menubar.add(jMenu, menu_obj_name, index - 1);
	}

	/**
	 * 向{@code CardFrame}中添加{@code GridBagPanel}。
	 * 
	 * @param gridBagPanel
	 *            要添加的<code>gridBagPanel</code>
	 * @param panel_obj_name
	 *            所添加gridBagPanel在其所属{@code CardFrame}中的标识，不是这个gridBagPanel显示的文本。
	 */
	public void addGridBagPanel(GridBagPanel gridBagPanel, String panel_obj_name) {
		this.add(gridBagPanel, panel_obj_name);
		this.validate();
	}

	public void setHelpContents(final JFrame frame) {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.help_menu_item_Help_Contents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setLocation(CardFrame.this.getX() + (CardFrame.this.getWidth() - frame.getWidth()) / 2, CardFrame.this.getY() + (CardFrame.this.getHeight() - frame.getHeight()) / 2);
				frame.setVisible(true);
			}
		});
	}

	public void setAbout(final Dialog dialog) {
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dialog.dispose();
			}
		});
		this.help_menu_item_About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dialog.setLocation(CardFrame.this.getX() + (CardFrame.this.getWidth() - dialog.getWidth()) / 2, CardFrame.this.getY() + (CardFrame.this.getHeight() - dialog.getHeight()) / 2);
				dialog.setVisible(true);
			}
		});
	}

	/**
	 * 切换到目标gridBagPanel
	 * 
	 * @param gridBagPanel
	 *            目标gridBagPanel
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
