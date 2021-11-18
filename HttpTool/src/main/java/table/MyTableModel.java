package table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class MyTableModel extends AbstractTableModel {
	
	private ArrayList<TableEntry> entries;
	
	public MyTableModel() {
		this.entries = new ArrayList<TableEntry>();
	}

	// Anzahl Spalten
	public int getColumnCount() {
		return 2;
	}

	// Anzahl Zeilen
	public int getRowCount() {
		return this.entries.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		TableEntry entry = this.entries.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return entry.getKey();
		case 1:
			return entry.getValue();
		}
		return null;
	}
	
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		TableEntry entry = this.entries.get(rowIndex);
		
        if(0 == columnIndex) {
        	entry.setKey((String)obj);
        }
        else if(1 == columnIndex) {
        	entry.setValue((String)obj);
        }	
	}
	
	// Bestimmten, welchen Typ die Spalten haben sollen
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		}
		return null;
	}
	
	// Headerbeschreibung der Tabelle festlegen
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Header-Key";
		case 1:
			return "Header-Value";
		}
		return null;
	}

	public void addEntry(TableEntry entry) {
		this.entries.add(entry);
		fireTableDataChanged(); // for add or change
//		fireTableRowsDeleted(firstRow, lastRow); // for delete
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
}
