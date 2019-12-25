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
 * <li><code>addComponent({@code Component} comp,{@code String} comp_obj_name,{@code int} row, {@code int} column, {@code int} horizontal_grids, {@code int} vertical_grids, {@code double} gridwidth, {@code double} gridheight, {@code boolean} fill)</code>：向panel中第<code>row</code>行第<code>column</code>列添加名称为<code>comp_obj_name</code>的<code>comp</code>，该<code>comp</code>横向占<code>horizontal_grids</code>个格子、纵向占<code>vertical_grids</code>个格子，所占的每个格子宽<code>gridwidth</code>（倍）、高<code>gridheight</code>（倍）；</li>
 * </ul>
 * </p>
 * <p>
 * 操作步驟：
 * <ul>
 * <li>1、制作各个<code>comp</code>（如{@code JPopupMenu}、{@code JTextField}、{@code JButton}、{@code JScrollPane}、{@code JList}、{@code JTree}、{@code JTextArea}、{@code JComboBox}等）。</li>
 * <li>2、用<code>addComponent()</code>把制作好的各个comp添加到panel中。</li> <br />
 * 注：制作各个<code>comp</code>时，必须分别用其各自的setName()设置其在所属panel中的标识。
 * </ul>
 * </p>
 * 
 * @author chiefeweight
 */
public class GridBagPanel extends JPanel {
	/**
	 * Specifies that this component is the next-to-last component in its column or
	 * row (<code>gridwidth</code>, <code>gridheight</code>), or that this component
	 * be placed next to the previously added component ( <code>gridx</code>,
	 * <code>gridy</code>).
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
	 * @param panel_obj_name 这个panel在其所属{@code CardFrame}中的标识。
	 */
	public GridBagPanel(String panel_obj_name) {
		setName(panel_obj_name);
		grid_bag_layout = new GridBagLayout();
		setLayout(grid_bag_layout);
		grid_bag_constraints = new GridBagConstraints();
		component_map = new HashMap<>();
	}

	/**
	 * 设置组件的标识（名字）、位置、大小及拉伸程度
	 * 
	 * @param comp             组件
	 * @param comp_obj_name    组件对象在其所属{@code GridBagPanel}中的标识（即名字），不是这个组件显示的文本
	 * @param row              组件所在行
	 * @param column           组件所在列
	 * @param horizontal_grids 组件横向占的格子数。注意：horizontal_grids不是组件所占格子的宽度。格子宽度由gridwidth控制
	 * @param vertical_grids   组件纵向占的格子数。注意：vertical_grids不是组件所占格子的高度。格子高度由gridheight控制
	 * @param gridwidth        组件所占格子的横向拉伸系数。可用于控制格子宽度。如果为0，则组件所占格子不跟随窗口横向拉伸；如果不为0，则组件所占格子跟随窗口横向拉伸，且拉伸时组件所占格子保持其在该行的拉伸比例。
	 * @param gridheight       组件所占格子的纵向拉伸系数。可用于控制格子高度。如果为0，则组件所占格子不跟随窗口纵向拉伸；如果不为0，则组件所占格子跟随窗口纵向拉伸，且拉伸时组件所占格子保持其在该列的拉伸比例。
	 * @param fill             组件是否填充所占格子。true填充，false不填充
	 * @see java.awt.GridBagConstraints#gridy
	 * @see java.awt.GridBagConstraints#gridx
	 * @see java.awt.GridBagConstraints#gridwidth
	 * @see java.awt.GridBagConstraints#gridheight
	 * @see java.awt.GridBagConstraints#weightx
	 * @see java.awt.GridBagConstraints#weighty
	 * @see java.awt.GridBagConstraints#fill
	 */
	public void addComponent(Component comp, String comp_obj_name, int row, int column, int horizontal_grids, int vertical_grids, double gridwidth,
			double gridheight, boolean fill) {
		if (!component_map.containsKey(comp_obj_name)) {
			comp.setName(comp_obj_name);
			grid_bag_constraints.gridy = row - 1;
			grid_bag_constraints.gridx = column - 1;
			grid_bag_constraints.gridwidth = horizontal_grids;
			grid_bag_constraints.gridheight = vertical_grids;
			grid_bag_constraints.weightx = gridwidth;
			grid_bag_constraints.weighty = gridheight;
			if (fill) {
				grid_bag_constraints.fill = GridBagConstraints.BOTH;
			} else {
				grid_bag_constraints.fill = GridBagConstraints.NONE;
			}
			grid_bag_layout.setConstraints(comp, grid_bag_constraints);
			add(comp);
			component_map.put(comp.getName(), comp);
			validate();
		} else {
			System.out.println(comp_obj_name + "已存在，该组件添加失败。请给组件对象重起comp_obj_name");
		}
	}

	/**
	 * 根据comp_obj_name，返回组件
	 * 
	 * @param comp_obj_name 组件的comp_obj_name
	 * @return 组件。如果该{@code GridBagPanel}中不包含comp_obj_name，则返回{@code null}
	 */
	public Component getComponent(String comp_obj_name) {
		Component result = null;
		if (component_map.containsKey(comp_obj_name)) {
			result = component_map.get(comp_obj_name);
		}
		return result;
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		super.removeAll();
		component_map.clear();
	}
}
