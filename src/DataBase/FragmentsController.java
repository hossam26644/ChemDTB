package DataBase;

import java.io.IOException;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.openscience.cdk.exception.InvalidSmilesException;

import net.iharder.dnd.FileDrop;

public class FragmentsController extends Controller {

	
	FragmentModel fragmentModel;
	public FragmentsController() {
		
		IntitializeTableGraphics(mainFrame.fragTable);
		TableModel tableModel = mainFrame.fragTable.getModel();
		TableRowSorter<TableModel> tableSorter = new TableRowSorter<TableModel>(tableModel); //to sort rows by clicking on the table header 
		mainFrame.fragTable.setRowSorter(tableSorter);
		mainFrame.dbScrollPane1.setBorder( null );
		
		FileCompare();
		SearchBox();
		fragmentModel = new FragmentModel(view);
		
		
	}

	private void SearchBox() {
        mainFrame.searchBoxFragments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode()==10)
					try {
						fragmentModel.CompareBySmiles(mainFrame.searchBoxFragments.getText());

					} catch (InvalidSmilesException e) {
						FileUploaded message = new FileUploaded();
						message.noOfAddedRecords.setText("Invalid Smiles input");
						message.setVisible(true);
					};
            }
        });		
	}



	private void FileCompare() {
		 new  FileDrop( mainFrame.fragFileDragNDrop, new FileDrop.Listener()
		  {   public void  filesDropped( java.io.File[] files ) {

			 try {
				fragmentModel.CompareByStructure(files[0].getPath());
			} catch (IOException e) {
				FileUploaded message = new FileUploaded();
				message.noOfAddedRecords.setText("Invalid input file");
				message.setVisible(true);
			}
		       
		  		}
		  });  		
	}
	
	
}
