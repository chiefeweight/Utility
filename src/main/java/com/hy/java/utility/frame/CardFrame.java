package com.hy.java.utility.frame;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
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
 * <li><code>setDefaultClosing()</code>：设置默认关闭事件。</li>
 * </ul>
 * </p>
 * <p>
 * 操作步驟：
 * <ul>
 * <li>1、制作各个菜单（使用{@code JMenu}和{@code JMenuItem}），然后用<code>addJMenu()</code>把制作好的菜单添加到菜单栏中。</li>
 * <li>2、制作各个Panel（使用{@code GridBagPanel}），然后用<code>addGridBagPanel()</code>把制作好的各个Panel添加到整个窗口容器中。</li>
 * <li>3、用<code>setHelp()</code>和<code>setAbout()</code>为两个菜单项设置动作，完成Help和About菜单项的制作。</li>
 * <li>4、用<code>addWindowListener()</code>添加关闭响应。如果没有事做，则可以调用<code>setDefaultClosing()</code>。</li>
 * <br />
 * 注：1、制作各个菜单时，必须用{@code JMenu}和{@code JMenuItem}的setName()为每个项设置标识。2、用<code>switchTo()</code>管理不同Panel之间的切换。
 * </ul>
 * </p>
 * 
 * @author chiefeweight
 */
public class CardFrame extends JFrame {
	private static final long serialVersionUID = -6988198275877839529L;
	private static final int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int default_width = screen_width / 2;
	public static final int default_height = screen_height / 2;
	private final CardLayout cardLayout;
	private final JMenuBar jMenuBar_Main;
	private final JMenu jMenu_Help;
	private final JMenuItem jMenuItem_About;
	private final JMenuItem jMenuItem_HelpContents;

	/**
	 * {@code CardFrame}的构造法。
	 * <p>
	 * <code>width</code>和<code>height</code>的单位均为像素。
	 */
	public CardFrame(String title, int width, int height) {
		/* 初始化窗口 */
		this.cardLayout = new CardLayout();
		this.initFrame(title, width, height);
		/* 初始化菜单栏 */
		this.jMenuBar_Main = new JMenuBar();
		this.jMenuBar_Main.setName("JMenuBar_Main");
		this.jMenu_Help = new JMenu("Help");
		this.jMenu_Help.setName("JMenu_Help");
		this.jMenuItem_HelpContents = new JMenuItem("Help Contents");
		this.jMenuItem_HelpContents.setName("Help Contents");
		this.jMenuItem_About = new JMenuItem("About " + title);
		this.jMenuItem_About.setName("About");
		this.initJMenuBar();
		/* 最后设置可见 */
		this.setVisible(true);
	}

	private void initFrame(String title, int width, int height) {
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		this.setSize(width, height);
		this.setLocation((CardFrame.screen_width - width) / 2, (CardFrame.screen_height - height) / 2);
		this.setLayout(this.cardLayout);
	}

	private void initJMenuBar() {
		this.jMenu_Help.add(this.jMenuItem_HelpContents, 0);
		this.jMenu_Help.addSeparator();
		this.jMenu_Help.add(this.jMenuItem_About, -1);
		this.addJMenu(this.jMenu_Help, this.jMenu_Help.getName(), 0);
		this.setJMenuBar(this.jMenuBar_Main);
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
		this.jMenuBar_Main.add(jMenu, menu_obj_name, index - 1);
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

	public void setHelp(ActionListener l) {
		this.jMenuItem_HelpContents.addActionListener(l);
	}

	public void setAbout(ActionListener l) {
		this.jMenuItem_About.addActionListener(l);
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
			this.cardLayout.show(contentPane, (String) gridBagPanel);
		} else if (gridBagPanel instanceof GridBagPanel) {
			this.cardLayout.show(contentPane, contentPane.getComponent(contentPane.getComponentZOrder((Component) gridBagPanel)).getName());
		}
	}

	/**
	 * 设置默认关闭事件
	 */
	public void setDefaultClosing() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
}
