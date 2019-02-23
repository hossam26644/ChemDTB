package DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.ALOGPDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondAcceptorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondDonorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.MannholdLogPDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.RotatableBondsCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.TPSADescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.ringsearch.RingSearch;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

public class Molecule {
	IAtomContainer molecule;
	public static final String alertsFile = "StructuralAllertSmarts.txt";
	
	void SetMoleculebyStructure(String sturcture) {
		molecule = IAtomContainerFromStructure(sturcture);
	}
	
	
	static IAtomContainer IAtomContainerFromStructure(String sturcture) {
		
		StringReader strReader = new StringReader(sturcture);
        IChemObjectBuilder bldr = DefaultChemObjectBuilder.getInstance();
        IAtomContainer inMolecule;
        try {
    		IteratingSDFReader mdliter = new IteratingSDFReader(strReader,bldr);
    		inMolecule =  mdliter.next();
			mdliter.close();
		} catch (IOException e) {
			inMolecule = null;
			e.printStackTrace();
		}
		
		return inMolecule;
		
	}

	Double MannholdLogP() {
		MannholdLogPDescriptor manlogPDis = new MannholdLogPDescriptor();
        DescriptorValue val = manlogPDis.calculate(molecule);
        
        String resultString = val.getValue().toString();
        
        return Double.parseDouble(resultString);
	}

	Double AlogP() throws CDKException {
        ALOGPDescriptor alogPDis = new ALOGPDescriptor();
        DescriptorValue val = alogPDis.calculate(molecule);

        String resultString = val.getValue().toString();
        List<String> items = Arrays.asList(resultString.split("\\s*,\\s*"));
        
        return Double.parseDouble(items.get(0));

	}

	Double XlogP() {
		XLogPDescriptor xlogPDis = new XLogPDescriptor();
        DescriptorValue val = xlogPDis.calculate(molecule);
        
        String resultString = val.getValue().toString();

        
        return Double.parseDouble(resultString);
	}

	int HydrogenBondDonors() {
    	HBondDonorCountDescriptor hDDes = new HBondDonorCountDescriptor() ;
        DescriptorValue val = hDDes.calculate(molecule);
     
        return Integer.parseInt(val.getValue().toString());
	}

	int HydrogenBondAcceptors() {
    	HBondAcceptorCountDescriptor hADes = new HBondAcceptorCountDescriptor() ;
        DescriptorValue val = hADes.calculate(molecule);
                
        return Integer.parseInt(val.getValue().toString());
	}

	Double getMolecularWeight() {
		
        IMolecularFormula form = MolecularFormulaManipulator.getMolecularFormula(molecule);
        
		return MolecularFormulaManipulator.getNaturalExactMass(form);
	}
	Double PolarSurfaceArea() {
		TPSADescriptor tpaDis = new TPSADescriptor() ;
        DescriptorValue val = tpaDis.calculate(molecule);

		return Double.parseDouble(val.getValue().toString());
	}
	
	int NumberOfRotatableBonds() {
		
		RotatableBondsCountDescriptor rbdis = new RotatableBondsCountDescriptor();
        DescriptorValue val = rbdis.calculate(molecule);
		
        return Integer.parseInt(val.getValue().toString());
	}
	
	int NumberOfRings() {
		RingSearch ringSearch = new RingSearch(molecule); 
		
		return ringSearch.numRings();		
	}
	
	
	int NumberOfStAlerts() throws FileNotFoundException, CDKException { //to be changed by Pattern 
		
		Scanner fileIn = new Scanner(new File(alertsFile));

		int alerts = 0;
		
		while(fileIn.hasNext()) {
			fileIn.next(); //the alert structure name (not used)
			String alertUnit = fileIn.next();

			Pattern comparator = SmartsPattern.create(alertUnit) ;
			if(comparator.matches(molecule))  alerts++;
		}
		fileIn.close();
		return alerts;
	}
	


}
