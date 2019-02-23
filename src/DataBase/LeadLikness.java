package DataBase;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

public class LeadLikness extends Molecule { //Rule Of Three
	
	int maxMolWeight;
	int maxHBA;
	int maxHBD;
	Double maxLogp;
	
	public LeadLikness(View view) {
		maxMolWeight = Integer.parseInt(view.mainFrame.maxWeighttxt.getText());
		maxHBA = Integer.parseInt(view.mainFrame.maxHBA.getText());
		maxHBD = Integer.parseInt(view.mainFrame.maxHBD.getText());
		maxLogp = Double.parseDouble(view.mainFrame.maxLogp.getText());
	}

	int getViolationScore(String structure,int logpAlgorithm) throws CDKException { 
		 //logplogPAlgorithm: 0 for alogp, 1 for xlogp, 2 for MannholdLogP
		 		
		SetMoleculebyStructure(structure);
	
		int score = 0;
		if (getMolecularWeight()>maxMolWeight) score++ ;
		if (HydrogenBondAcceptors()>maxHBA) score++ ;
		if (HydrogenBondDonors()>maxHBD) score++ ;
       
		AtomContainerManipulator.convertImplicitToExplicitHydrogens(molecule);
        switch (logpAlgorithm) {
	        case 0: 
	    		if (AlogP()>maxLogp) score++ ;
	        	break;
	        case 1: 
	    		if (XlogP()>maxLogp) score++ ;
	        	break;
	        case 2: 
	    		if (MannholdLogP()>maxLogp) score++ ;
	        	break;
        }

		
		return score;
	}
}
