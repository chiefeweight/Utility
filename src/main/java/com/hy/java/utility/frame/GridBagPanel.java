package com.hy.java.utility.frame;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * 用{@code GridBagLayout}管理layout的{@code JPanel}。
 * <p>
 * 方法介紹：
 * <ul>
 * <li><code>addComponent({@code Component} comp,{@code String} comp_obj_name,{@code int} row, {@code int} column, {@code int} gridwidth, {@code int} gridheight, {@code double} weightx, {@code double} weighty)</code>：向panel中第<code>row</code>行第<code>column</code>列添加特定大小的<code>comp</code>；</li>
 * </ul>
 * </p>
 * <p>
 * 操作步驟：
 * <ul>
 * <li>1、制作各个<code>comp</code>（如{@code JMenu}、{@code JMenuItem}、{@code JButton}、{@code JList}、{@code TextArea}、{@code TextField}、{@code JComboBox}等）。</li>
 * <li>2、用<code>addComponent()</code>把制作好的各个comp添加到panel中。</li> <br />
 * 注：制作各个<code>comp</code>时，必须分别用其各自的setName()设置其在所属panel中的标识。
 * </ul>
 * </p>
 * 
 * @author chiefeweight
 */
public class GridBagPanel extends JPanel {
	/**
	 * Specifies that this component is the next-to-last component in its column or row
	 * (<code>gridwidth</code>, <code>gridheight</code>), or that this component be placed next to the
	 * previously added component ( <code>gridx</code>, <code>gridy</code>).
	 * 
	 * @see java.awt.GridBagConstraints#gridwidth
	 * @see java.awt.GridBagConstraints#gridheight
	 * @see java.awt.GridBagConstraints#gridx
	 * @see java.awt.GridBagConstraints#gridy
	 */
	public static final int RELATIVE = 0;
	/**
	 * Specifies that this component is the last component in its column or row.
	 */
	public static final int REMAINDER = 0;
	private static final long serialVersionUID = -6984508425312677255L;
	private GridBagLayout grid_bag_layout;
	private GridBagConstraints grid_bag_constraints;
	private Map<String, Component> component_map;

	/**
	 * {@code GridBagPanel}的构造法。
	 * 
	 * @param panel_obj_name
	 *            这个panel在其所属{@code CardFrame}中的标识。
	 */
	public GridBagPanel(String panel_obj_name) {
		this.setName(panel_obj_name);
		this.grid_bag_layout = new GridBagLayout();
		this.setLayout(this.grid_bag_layout);
		this.grid_bag_constraints = new GridBagConstraints();
		this.component_map = new HashMap<>();
	}

	/**
	 * 设置组件的标识（名字）、位置、大小及拉伸程度
	 * 
	 * @param comp
	 *            组件
	 * @param comp_obj_name
	 *            组件对象在其所属{@code GridBagPanel}中的标识（即名字），不是这个组件显示的文本
	 * @param row
	 *            组件所在行
	 * @param column
	 *            组件所在列
	 * @param gridwidth
	 *            组件宽度占的格子数
	 * @param gridheight
	 *            组件高度占的格子数
	 * @param weightx
	 *            组件横向额外占的格子数，默认是0
	 * @param weighty
	 *            组件纵向额外占的格子数，默认是0
	 * @param fill
	 *            组件是否填充所占格。true填充，false不填充
	 * @see java.awt.GridBagConstraints#gridy
	 * @see java.awt.GridBagConstraints#gridx
	 * @see java.awt.GridBagConstraints#gridwidth
	 * @see java.awt.GridBagConstraints#gridheight
	 * @see java.awt.GridBagConstraints#weightx
	 * @see java.awt.GridBagConstraints#weighty
	 */
	public void addComponent(Component comp, String comp_obj_name, int row, int column, int gridwidth, int gridheight, double weightx, double weighty, boolean fill) {
		if (!this.component_map.containsKey(comp_obj_name)) {
			comp.setName(comp_obj_name);
			this.grid_bag_constraints.gridy = row - 1;
			this.grid_bag_constraints.gridx = column - 1;
			this.grid_bag_constraints.gridwidth = gridwidth;
			this.grid_bag_constraints.gridheight = gridheight;
			this.grid_bag_constraints.weightx = weightx;
			this.grid_bag_constraints.weighty = weighty;
			if (fill) {
				this.grid_bag_constraints.fill = GridBagConstraints.BOTH;
			} else {
				this.grid_bag_constraints.fill = GridBagConstraints.NONE;
			}
			this.grid_bag_layout.setConstraints(comp, this.grid_bag_constraints);
			this.add(comp);
			this.component_map.put(comp.getName(), comp);
			this.validate();
		} else {
			System.out.println(comp_obj_name + "已存在，该组件添加失败。请给组件对象重起comp_obj_name");
		}
	}

	/**
	 * 根据comp_obj_name，返回组件
	 * 
	 * @param comp_obj_name
	 *            组件的comp_obj_name
	 * @return 组件。如果该{@code GridBagPanel}中不包含comp_obj_name，则返回{@code null}
	 */
	public Component getComponent(String comp_obj_name) {
		Component result = null;
		if (this.component_map.containsKey(comp_obj_name)) {
			result = this.component_map.get(comp_obj_name);
		}
		return result;
	}
}
