package table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableHeaderRenderer extends JLabel implements TableCellRenderer {

	public MyTableHeaderRenderer() {
		super.setOpaque(true);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEtchedBorder());
	}
	
	public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
		
		String name = (String)object;
		setText(name);
		setHorizontalAlignment(JLabel.LEFT);
		return this;
	}
}
