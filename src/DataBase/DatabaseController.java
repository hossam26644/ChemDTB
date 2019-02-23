package DataBase;


import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class DatabaseController extends Controller{
	DataBaseMainFrame mainFrame;
	TableModel tableModel;
	List<RowFilter<Object,Object>> tableRowFilter = new ArrayList<RowFilter<Object,Object>>();
	TableRowSorter<TableModel> tableSorter;
	FillTableModel fillModel;
	ExportModel exportModel;
	DrawMoleculeModel drawMoleculeModel;
	
	public DatabaseController() {
		mainFrame = view.mainFrame;
		tableModel = mainFrame.dbtable.getModel();
		tableSorter = new TableRowSorter<TableModel>(tableModel); //to sort rows by clicking on the table header 
		mainFrame.dbtable.setRowSorter(tableSorter);

		
		CheckBoxes();
		IntitializeTableGraphics(mainFrame.dbtable);
		TableSetColWidth();
		SearchBox();
		ExportButton();
		
		fillModel = new FillTableModel(view);
		exportModel = new ExportModel();
		drawMoleculeModel = new DrawMoleculeModel();
		TableDrawMolecule();
		
	}

	private void TableSetColWidth() {
		mainFrame.dbScrollPane.setBorder( null );
		mainFrame.dbtable.getColumnModel().getColumn(0).setPreferredWidth(30);
		mainFrame.dbtable.getColumnModel().getColumn(2).setPreferredWidth(40);
		mainFrame.dbtable.getColumnModel().getColumn(3).setPreferredWidth(30);
	}


	private void TableDrawMolecule() {

        mainFrame.dbtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	int rowToDraw = mainFrame.dbtable.getSelectedRows()[0]; //Draw the first molecule of the selection
            	String cd_id = (String) mainFrame.dbtable.getValueAt(rowToDraw, 0);
            	
            	Image molImage = drawMoleculeModel.MolImage(cd_id); //get the molecule image from model
            	
            	
            	molImage = molImage.getScaledInstance(mainFrame.structurelbl.getWidth(), //scaling image to canvas size
            										  mainFrame.structurelbl.getWidth(),
            										  Image.SCALE_SMOOTH);
            	
            	mainFrame.structurelbl.setIcon(new ImageIcon(molImage));


            }
        });
	}




	private void ExportButton() {
		
		mainFrame.exportbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mainFrame.exportbtn.setBackground(new java.awt.Color(22, 37, 80));
            }
        });	
		
		mainFrame.exportbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	mainFrame.exportbtn.setBackground(new java.awt.Color(32, 47, 90));
            	
            }
        });
		mainFrame.exportbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
        		
            	Message message = new Message();
        		Point p = MouseInfo.getPointerInfo().getLocation();
  			  	message.setLocation(p.x-message.getWidth(), p.y-message.getHeight());


            	int[] selectedRows = mainFrame.dbtable.getSelectedRows();
	            if(selectedRows.length >0) {
	            	String[] cd_id = new String[selectedRows.length]; //to hold cd_id of selected rows
	            	
	            	for (int i = 0; i < selectedRows.length; i++) {
						cd_id[i] =  (String) mainFrame.dbtable.getValueAt(selectedRows[i], 0);
					}
	            	
	            	if(exportModel.export(cd_id)) {
	            		message.noOfAddedRecords.setText("Molecules exported to ExportedResults.sdf");
	            		message.setSize(message.getWidth()+30, message.getHeight());
	            		message.setVisible(true);
	            	}else {
	            		message.noOfAddedRecords.setText("There were errors exporting molecules");
	            		message.setVisible(true);
	            	}
            	}else {
            		message.noOfAddedRecords.setText("Please select molecules to export");
            		message.setVisible(true);
            	}
            }
        });
		
	}




	

	private void SearchBox() {
		
		mainFrame.searchBox.getDocument().addDocumentListener(new DocumentListener() {
			RowFilter<Object, Object> searchBoxfilter;

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TableFilter();
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TableFilter();
				}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TableFilter();
			}

			private void TableFilter() {
				tableRowFilter.remove(searchBoxfilter); //remove previous searchBoxfilter
				String query = mainFrame.searchBox.getText(); //Get text from search box
				
				searchBoxfilter = RowFilter.regexFilter(query); //Filter By new Value
				tableRowFilter.add(searchBoxfilter); //Add to the filters list
				
        		RowFilter<Object,Object> filter = RowFilter.andFilter(tableRowFilter); //filters list to a filter
				tableSorter.setRowFilter(filter); //apply filter
				
			}
		});
	}

	private void CheckBoxes() {		
		mainFrame.lipinski.addActionListener(new java.awt.event.ActionListener() {
			RowFilter<Object, Object> lipinskifilter =  RowFilter.regexFilter("[0-1]",4); 
		
			public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(mainFrame.lipinski.isSelected()) {
            		CheckmarkChecked(mainFrame.lipinski);
            		
            		AddFilter(lipinskifilter); //Applies lipinski filter 

            	}
            	else if(!mainFrame.lipinski.isSelected()) {
            		CheckmarkUnChecked(mainFrame.lipinski);
            		RemoveFilter(lipinskifilter);

            		}
            	}
            
        });
		mainFrame.leadLikness.addActionListener(new java.awt.event.ActionListener() {
			RowFilter<Object, Object> leadLiknessfilter = RowFilter.regexFilter("[0-1]",5); 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(mainFrame.leadLikness.isSelected()) {
            		CheckmarkChecked(mainFrame.leadLikness);
            		AddFilter(leadLiknessfilter);	
            	}
            	else if(!mainFrame.leadLikness.isSelected()) {
            		CheckmarkUnChecked(mainFrame.leadLikness);
            		RemoveFilter(leadLiknessfilter);

            		}
            	}
            
        });
		mainFrame.bioavailability.addActionListener(new java.awt.event.ActionListener() {
			RowFilter<Object, Object> bioavailabilityfilter ;
			
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(mainFrame.bioavailability.isSelected()) {
            		CheckmarkChecked(mainFrame.bioavailability);
        			
            		String input = mainFrame.bioThreshold.getText();
            		bioavailabilityfilter = RowFilter.regexFilter(getRegex(input), 6);
            		AddFilter(bioavailabilityfilter);	

            	}
            	else if(!mainFrame.bioavailability.isSelected()) {
            		CheckmarkUnChecked(mainFrame.bioavailability);
            		RemoveFilter(bioavailabilityfilter);
            		}
            	}
            private String getRegex(String text) {
            	try {
            		Double inNo = Double.parseDouble(text);
            		text = String.valueOf(inNo); //not using the row input string, it may have spaces
            		text += "0000";
            		if(inNo>1 || inNo<0) {
    					mainFrame.bioThreshold.setText("0.45");
            			return("[0-1]+(\\.[4][5-9])|(\\.[5-9][0-9])");
            			}
            		int secondPoint = Character.getNumericValue(text.charAt(2));
            		String first = "[0-1]+(\\.";
            		String second = "["+text.charAt(2)+"]["+text.charAt(3)+"-9])";
            		String third = "|(\\.["+String.valueOf(secondPoint+1)+"-9][0-9])";
            		text = first+second+third;
					
				} catch (Exception e) {
					mainFrame.bioThreshold.setText("0.45");
					return("[0-1]+(\\.[4][5-9])|(\\.[5-9][0-9])");
				}
            	return text;
            }
            
        });

	}
	void AddFilter(RowFilter<Object, Object> filter) {
		

		tableRowFilter.add(filter); //Add to the filters list
		
		RowFilter<Object,Object> totalfilter = RowFilter.andFilter(tableRowFilter); //filters list to a filter
		tableSorter.setRowFilter(totalfilter); //apply filter

	}
	
	
	
	
	
	
	void RemoveFilter(RowFilter<Object, Object> filter) {
		tableRowFilter.remove(filter);
		RowFilter<Object,Object> rfs2 = RowFilter.andFilter(tableRowFilter); //filters list to a filter
		tableSorter.setRowFilter(rfs2);
	}

	private void CheckmarkChecked(JCheckBox checkBox) {
		checkBox.setIcon(new javax.swing.ImageIcon(mainFrame.getClass().getResource("/DataBase/Images/Checked.png"))); // NOI18N
		
	}


	private void CheckmarkUnChecked(JCheckBox checkBox) {
		checkBox.setIcon(new javax.swing.ImageIcon(mainFrame.getClass().getResource("/DataBase/Images/Unchecked.png"))); // NOI18N
		
	}
}
