package DataBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;



public class ImportModel extends Model{

	
	 public int ImportSDF(String filename) throws CDKException, IOException{

		 int noAddedrecords = 0;

		 FileReader sdfile = new FileReader(new File(filename));
		 FileReader structureReader = new FileReader(new File(filename));
		 
		 Scanner molScanner = new Scanner(structureReader);
		 
		 IteratingSDFReader sdfReader = new IteratingSDFReader(sdfile,
                 DefaultChemObjectBuilder.getInstance());
		
		 SmilesGenerator sg      = new SmilesGenerator(SmiFlavor.Generic);
		 while (sdfReader.hasNext()) {
	        	IAtomContainer molecule =  sdfReader.next();
	            IMolecularFormula form = MolecularFormulaManipulator.getMolecularFormula(molecule);
	            
	            try {
		        	String cdId = molecule.getProperty("CdId");
		        	String mol = molScanner.useDelimiter("\\$\\$\\$\\$").next();//gets each molecule from file
		        	String structure = mol.split("M  END")[0]; //gets the structure by stopping at the M END word
		        	structure += "M  END"; //adds the m end to the structure
		        	String smiles = sg.create(molecule);
		        	String formula = MolecularFormulaManipulator.getString(form) ;
		        	Double molWeight = MolecularFormulaManipulator.getNaturalExactMass(form);
		        	
		        	ArrayList<String> params = new ArrayList<String>();
		        	params.add(cdId);
		        	params.add(structure);
		        	params.add(smiles);
		        	params.add(formula);

		        	if(insertToDatabase(params,molWeight)) {
		        		noAddedrecords++;	
		        	}
		        	
					
				} catch (Exception e) {
					e.printStackTrace();
				}
	            
	        }
		 sdfReader.close();
		 molScanner.close();
		 
		return noAddedrecords;
 
	 }
	private  boolean insertToDatabase(ArrayList<String> params,Double molWeight) throws CDKException, IOException{
		try {
			java.sql.Connection conn = DriverManager.getConnection(serverURL, userName, password);
			Statement st = conn.createStatement();
			
			String query = " SELECT *"
						 + " FROM "+tablename
						 + " WHERE cd_id = "+params.get(0)+" ;";
			
			ResultSet rs = st.executeQuery(query); //check if the record is already in the database
			rs.last();
			if(rs.getRow() == 0) {
				query = "INSERT INTO "+ tablename+ " (cd_id, cd_structure, cd_smiles, cd_formula,cd_molweight)"
						+"VALUES (\""+ params.get(0)+"\" , \""+ params.get(1) + "\" , \" "
						+params.get(2) + "\" , \""+ params.get(3) + "\" , \"" + String.valueOf(molWeight) + "\" );" ;
				st.execute(query);
				return true;
			}
			  return false;
		} catch (SQLException e) {
			return false;
		}

		  

	  }

}
