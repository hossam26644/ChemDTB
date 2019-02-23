package DataBase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Controller {
	static ImportModel importModel;
	static DataBaseMainFrame mainFrame;
	static DatabaseController databaseController;
	static HomeController homeController;
	static ImportController importController;
	static FileUploaded fileUploaded;
	static View view;   
	static int mouseX;
	static int mouseY;
	static FragmentsController fragmentsController;

	static void set(View inview) {
		view = inview;
		mainFrame = view.mainFrame;
		fileUploaded = view.fileUploaded;

		
	}
	
	static void IntitializeTableGraphics(JTable table) {

		
    	JTableHeader header = table.getTableHeader();
    	header.setFont(new Font("Segoe UI",Font.BOLD,12));
    	header.setOpaque(false);
    	header.setForeground(new Color(255,255,255));
    	
    	DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
    	headerRenderer.setBackground(new Color(0,18,50));
    	headerRenderer.setForeground(new Color(242,241,240));
    	headerRenderer.setFont(new Font("Segoe UI",Font.BOLD,12));
    	//headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    	

    	
    	for (int i = 0; i < table.getModel().getColumnCount(); i++) {
    	        table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    	}
    	header.setForeground(new Color(255,255,255));
        header.setPreferredSize(new Dimension(100, 30));
    	table.setRowHeight(25);

		
	}
}
