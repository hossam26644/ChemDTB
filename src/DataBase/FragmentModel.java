package DataBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.smiles.SmilesParser;


public class FragmentModel extends Model {

	View view; 
	DataBaseMainFrame mainFrame;
	public FragmentModel(View inView) {
		view = inView;
		mainFrame = view.mainFrame;
	}
	
	void DoComparison(IAtomContainer outMolecule) {
		EmptyTable(mainFrame.fragTable);
		try {
     	Connection conn = null;
		
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(serverURL, userName, password);

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT cd_id,cd_structure,cd_formula"
					+ " FROM chems;");
		CompareMolecules compareMolecules= new CompareMolecules();
		while (rs.next()){ //loop through all molecules in database
			ArrayList<String> row = new ArrayList<String>();

			row.add(rs.getString("cd_id"));
			row.add(rs.getString("cd_formula"));
			String inMoleculeStructure =  rs.getString("cd_structure");
			IAtomContainer inMolecule =Molecule.IAtomContainerFromStructure(inMoleculeStructure);
			
			
			float structsimilarity = compareMolecules.Get3DSimilarity(inMolecule,outMolecule);
			row.add(String.valueOf(structsimilarity));
			
			float funsimilarity = compareMolecules.GetFunctionalSimilarity(inMolecule,outMolecule);
			row.add(String.valueOf(funsimilarity));
			
			
			boolean isomerism = compareMolecules.CheckIsomerism(inMolecule, outMolecule);
			row.add(String.valueOf(isomerism));
			
			boolean substructure = compareMolecules.IsSubStructure(inMolecule, outMolecule);
			row.add(String.valueOf(substructure));
			FillRow(row);

			}
		}catch (SQLException e) {
			FileUploaded message = new FileUploaded();
			message.noOfAddedRecords.setText("Can't connect to database");
			message.setVisible(true);
		} catch (ClassNotFoundException e) {

		}
		
		
	}
	

	private void FillRow(ArrayList<String> row) {
      
		Object[] list = new Object[row.size()];

		for (int i = 0; i < row.size(); i++) {
	        list[i] = row.get(i);

		}


        ((DefaultTableModel) mainFrame.fragTable.getModel()).insertRow(0, list );
		
	}

	public void CompareBySmiles(String smiles) throws InvalidSmilesException {
		SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IAtomContainer container= parser.parseSmiles(smiles);
    	DoComparison(container);

	}

	public void CompareByStructure(String file) throws IOException {
		 FileReader sdfile = new FileReader(new File(file));
		 IteratingSDFReader sdfReader = new IteratingSDFReader(sdfile,
                DefaultChemObjectBuilder.getInstance());
    	IAtomContainer outMolecule =  sdfReader.next(); //gets only the first molecule (we compare one molecule against database
		sdfReader.close();
    	DoComparison(outMolecule);
	}
}
