package DataBase;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

public class Lipinski extends Molecule{

	 int getViolationScore(String structure,int logpAlgorithm) throws CDKException { 
		 //logplogPAlgorithm: 0 for alogp, 1 for xlogp, 2 for MannholdLogPDescriptor
		 		
		SetMoleculebyStructure(structure);
		int score = 0;
		if (getMolecularWeight()>500) score++ ;
		if (HydrogenBondAcceptors()>10) score++ ;
		if (HydrogenBondDonors()>5) score++ ;
       
		AtomContainerManipulator.convertImplicitToExplicitHydrogens(molecule);
        switch (logpAlgorithm) {
	        case 0: 
	    		if (AlogP()>=5) score++ ;
	        	break;
	        case 1: 
	    		if (XlogP()>=5) score++ ;
	        	break;
	        case 2: 
	    		if (MannholdLogP()>=5) score++ ;
	        	break;
        }

		
		return score;
	}

}
