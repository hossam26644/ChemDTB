package DataBase;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportModel extends Model{
	

	public ExportModel() {
		
	}

	public boolean export(String[] cd_id) {
		
		Connection conn = null;
		String result = "";
		
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(serverURL, userName, password);
				Statement st = conn.createStatement();
				for (int i = 0; i < cd_id.length; i++) {
					ResultSet rs = st.executeQuery("SELECT cd_structure "
							+ " FROM chems"
							+ " WHERE cd_id = " + cd_id[i] + " ;");
					rs.next();
					result += rs.getString("cd_structure");
					//result += "\n";
					result += ">  <CdId> \n";
					result += cd_id[i] + "\n";
					result +="\n$$$$ \n";
				}
				WriteToFile(result);
				return true;
			} catch (ClassNotFoundException e) {
				return false;
			} catch (SQLException e) {
				return false;
			}


		
	}

	private void WriteToFile(String result) {

		try {
			PrintWriter out = new PrintWriter("ExportedResults.sdf");
			out.println(result);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
