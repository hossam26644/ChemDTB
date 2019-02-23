package DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

public class Model {
	static String serverURL;
	static String userName;
	static String password;
	static String tablename;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	static void setParams(String filename) throws FileNotFoundException{
		
		Scanner jsonScanner = new Scanner(new File(filename));
		String myJson = jsonScanner.useDelimiter("\\Z").next();		
		JSONObject obj = new JSONObject(myJson);
		serverURL = obj.getJSONObject("Params").getString("server");
		userName = obj.getJSONObject("Params").getString("userName");
		password = obj.getJSONObject("Params").getString("password");
		tablename = obj.getJSONObject("Params").getString("TableName");
		
		jsonScanner.close();
		}
	static void EmptyTable(JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.setNumRows(0);	
		
	}
	}
