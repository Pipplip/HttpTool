package table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableRenderer extends JLabel implements TableCellRenderer {

	public MyTableRenderer() {
		super.setOpaque(true);
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
		
		String name = (String)object;
		setText(name);
		setHorizontalAlignment(JLabel.LEFT);
		
        if (isSelected)
        {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }
        else
        {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
		
		
//		if(name.length() < 4) {
//			super.setBackground(Color.green);
//			super.setForeground(Color.black);
//		}else {
//			super.setBackground(Color.red);
//			super.setForeground(Color.white);
//		}
		return this;
	}

}
