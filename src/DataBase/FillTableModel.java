package DataBase;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.openscience.cdk.exception.CDKException;

public class FillTableModel extends Model{
	View view;
	MainFrameView mainFrame;
	JTable dbtable;
	
	
	public FillTableModel(View inview) {
		view = inview;
		mainFrame = view.mainFrame;
		dbtable = mainFrame.dbtable;
		
		try {
			FillTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void FillTable() throws SQLException {
		EmptyTable(dbtable);
		
		Connection conn = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(serverURL, userName, password);

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT cd_id,cd_formula,cd_molweight,cd_structure, cd_smiles "
					+ " FROM chems;");
			while (rs.next()){

				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("cd_id"));
				row.add(rs.getString("cd_formula"));
				row.add(rs.getString("cd_molweight"));
				Lipinski lipinski = new Lipinski();
				LeadLikness leadLikness = new LeadLikness(view);
				QED qed = new QED();
				
				try {
					qed.SetMoleculebyStructure(rs.getString("cd_structure"));
					row.add(String.valueOf(qed.NumberOfStAlerts()));
					row.add(String.valueOf(lipinski.getViolationScore(rs.getString("cd_structure"),
														mainFrame.logPcomboBox.getSelectedIndex())));
					row.add(String.valueOf(leadLikness.getViolationScore(rs.getString("cd_structure"),
														mainFrame.logPcomboBox.getSelectedIndex())));
					row.add(qed.getScore(rs.getString("cd_structure"),
														mainFrame.bioWeightscomboBox.getSelectedIndex()));

				} catch (CDKException e) {
					row.add("NA");
					row.add("NA");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				FillRow(row);
  		  	}

		}catch (ClassNotFoundException e) {
  			  
  		  }


	}



	private void FillRow(ArrayList<String> row) {
      
		Object[] list = new Object[row.size()];

		for (int i = 0; i < row.size(); i++) {
	        list[i] = row.get(i);

		}


        ((DefaultTableModel) dbtable.getModel()).insertRow(0, list );
		
	}
	
}
