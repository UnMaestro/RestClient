package com.odfin.gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class PanelRenderer implements ListCellRenderer<JPanel>{
	@Override
	public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JPanel renderer = value;
		return renderer;
	}

}
