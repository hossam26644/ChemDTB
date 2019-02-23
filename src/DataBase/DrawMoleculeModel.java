package DataBase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.RingGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

//With the help of method found on GitHub by user:Gilleain https://gist.github.com/gilleain/1021964

public class DrawMoleculeModel extends Model{

	public Image MolImage(String cd_id) {
		IAtomContainer molecule = null;
		Connection conn = null;
		String structure ;
		
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(serverURL, userName, password);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT cd_structure "
							+ " FROM chems"
							+ " WHERE cd_id = " + cd_id + " ;");
					rs.next();
					structure = rs.getString("cd_structure");
					
					molecule = Molecule.IAtomContainerFromStructure(structure);
					Image image = drawMolecule(molecule);
					return image;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
    public static Image drawMolecule(IAtomContainer molecule) throws Exception {
    	
        // layout the molecule
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        sdg.setMolecule(molecule, false);
        sdg.generateCoordinates();
        
        // make generators
        List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new RingGenerator());
        generators.add(new BasicAtomGenerator());
        
        // setup the renderer
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        RendererModel model = renderer.getRenderer2DModel(); 
        model.set(BasicAtomGenerator.CompactAtom.class, true);
        model.set(BasicAtomGenerator.AtomRadius.class, 6.0);
        model.set(BasicAtomGenerator.CompactShape.class, BasicAtomGenerator.Shape.OVAL);
        model.set(BasicAtomGenerator.KekuleStructure.class, true);
        model.set(BasicBondGenerator.BondWidth.class, 5.0);
        
        // get the image
        Image image = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(new Color(214,217,223));
        g.fill(new Rectangle2D.Double(0, 0, 300, 300));
        
        // paint
        renderer.paint(molecule, new AWTDrawVisitor(g), 
                new Rectangle2D.Double(50, 50, 200, 200), true);
        g.dispose();
    	
    	return image;
    }

}
