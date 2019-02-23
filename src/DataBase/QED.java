package DataBase;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import org.openscience.cdk.exception.CDKException;

public class QED extends Molecule{
	String smiles;
	
	public static final Double[] qedMaxParams = {0.5,0.25,0.5,0.0,0.0,0.5,0.25,1.0};
	public static final Double[] qedMeanParams = {0.66,0.46,0.61,0.05,0.06,0.65,0.48,0.95};
	public static final Double[] qedunweightedParams = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
	
	public static final Double[][] desirabilityParams =	{{2.817,392.575,290.749,2.420,49.223,65.371},
														 {3.173,137.862,2.535,4.581,0.823,0.576},
														 {1.619,1010.051,0.985,1.0E-12,0.714,0.921},
														 {2.949,160.461,3.615,4.436,0.290,1.301},
														 {1.877,125.223,62.908,87.834,12.020,28.513},
														 {0.010,272.412,2.558,1.566,1.272,2.758},
														 {3.218,957.737,2.275,1.0E-12,1.318,0.376},
														 {0.010,1199.094,-0.090,1.0E-12,0.186,0.875}};

	public static final Double[] maxValues ={104.981,131.319,258.163,148.776,104.569,105.442,312.337,417.725};
	
	String getScore(String structure,int paramsIndex) throws CDKException, FileNotFoundException {//0:Max , 1:mean, 2:unweighted
		
		SetMoleculebyStructure(structure);

		Double[] weight;
		if(paramsIndex == 0) weight = qedMaxParams;
		else if(paramsIndex == 0) weight = qedMeanParams;
		else weight = qedunweightedParams;
		
		
		Double[] params = {getMolecularWeight(),
					  AlogP(),
					  (double) HydrogenBondDonors(),
					  (double) HydrogenBondAcceptors(),
					  PolarSurfaceArea(),
					  (double) NumberOfRotatableBonds(),
					  (double) NumberOfRings(),
					  (double) NumberOfStAlerts()};
		
		Double numerator = 0.0;
		Double denominator = 0.0;
		

		for (int i = 0; i < params.length; i++) {
		
			Double d = getDesirability(params[i],desirabilityParams[i]);			
			
			numerator += (weight[i] * Math.log(d/maxValues[i])); //ln , log is log10
			denominator += weight[i];
		
		}
		Double result = Math.exp(numerator/denominator);
	      DecimalFormat df1 = new DecimalFormat("0.####");
	      return df1.format(result);
	}


	private Double getDesirability(Double x, Double[] params) {
		
		Double desirability = params[0]; //add (a)
		
		Double expression1 = -(x - params[2] + (params[3]/2))
									/params[4] ;
		Double expression2 = -(x - params[2] - (params[3]/2))
								/params[5]	;
		
		desirability += (params[1]/(1+Math.exp(expression1)))*
						(1-(1/(1+Math.exp(expression2))));
		
		
		return desirability;
	}
	
	
	
}