package DataBase;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.openscience.cdk.exception.CDKException;
import net.iharder.dnd.FileDrop;


public class ImportController extends Controller {
	

	
	public ImportController() {
		importModel = new ImportModel();
		ImportSDF();
	}
	
	
	private void ImportSDF() {
		 new  FileDrop( mainFrame.dragNDroplbl, new FileDrop.Listener()
		  {   public void  filesDropped( java.io.File[] files )
		      {   
			  Message message = new Message();
			  int noOfAddedRecords = 0;

			  try {
				noOfAddedRecords = importModel.ImportSDF(files[0].getPath());
				if(noOfAddedRecords>0)databaseController.fillModel.FillTable();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (CDKException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			  Point p = MouseInfo.getPointerInfo().getLocation();
			  message.noOfAddedRecords.setText(String.valueOf(noOfAddedRecords) + " Molecules imported to database ");
			  message.setLocation(p.x, p.y-message.getHeight());
			  message.setVisible(true);
		      }   
		  
		  });  
	}

}
