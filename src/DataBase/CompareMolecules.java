package DataBase;

import java.io.IOException;
import java.util.BitSet;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.modeling.builder3d.ModelBuilder3D;
import org.openscience.cdk.similarity.DistanceMoment;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;


public class CompareMolecules extends Molecule{


	public float Get3DSimilarity(IAtomContainer inMolecule, IAtomContainer outMolecule)  {
		molecule = inMolecule;
		try {
			return DistanceMoment.calculate(inMolecule,outMolecule);
		} catch (CDKException e) {
			 try {
				ModelBuilder3D builder  = ModelBuilder3D.getInstance( DefaultChemObjectBuilder.getInstance()) ;
				outMolecule = builder.generate3DCoordinates(outMolecule, false);
				return DistanceMoment.calculate(inMolecule,outMolecule);

			} catch (CDKException e1) {
				// TODO Auto-generated catch block
				return 0;
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				return 0;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return 0;
			}
		}
	}
	
	public float GetFunctionalSimilarity(IAtomContainer inMolecule, IAtomContainer outMolecule)  {
		Fingerprinter fingerPrinter = new Fingerprinter();
		
		BitSet inFingerPrint=null;
		BitSet outFingerPrint = null;
		try {
			inFingerPrint = fingerPrinter.getFingerprint(inMolecule);
			outFingerPrint = fingerPrinter.getFingerprint(outMolecule);

		} catch (CDKException e) {
			return 0;
		}
		
		float denominator = 0;
		float numerator = 0;
		for (int i = 0; i < inFingerPrint.size(); i++) {
			if(inFingerPrint.get(i)==true) {
				denominator +=1;
				if(outFingerPrint.get(i)==true) numerator++;
			}
		}

		return numerator/denominator;
		
	}
	
/*	public boolean CheckIsomerism(IAtomContainer inMolecule,IAtomContainer outMolecule)  { //change to check molformula
		molecule = inMolecule;
		Pattern comparator = Pattern.findIdentical(outMolecule);
		return comparator.matches(inMolecule);

	}*/
	public boolean CheckIsomerism(IAtomContainer inMolecule,IAtomContainer outMolecule)  { //change to check molformula
		molecule = inMolecule;
        IMolecularFormula inForm = MolecularFormulaManipulator.getMolecularFormula(molecule);
        IMolecularFormula outForm = MolecularFormulaManipulator.getMolecularFormula(outMolecule);

		String inMolFormula = MolecularFormulaManipulator.getString(inForm) ;
		String outMolFormula = MolecularFormulaManipulator.getString(outForm) ;


		return inMolFormula.equals(outMolFormula);

	}
	public boolean IsSubStructure(IAtomContainer inMolecule,IAtomContainer outMolecule) {
		Pattern comparator = Pattern.findSubstructure(outMolecule) ;
		return comparator.matches(inMolecule);
	}
	



}
