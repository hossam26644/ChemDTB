package DataBase;


import javax.swing.JFrame;

public class MainController extends Controller {
	

	
	public MainController() {
		
		HomePanelSelected();

		homeController = new HomeController(this);
		databaseController = new DatabaseController();
		importController = new ImportController();
		fragmentsController = new FragmentsController();
		
		CloseButton();
		TitleBar();
		SidePanel();
		SettingsApplybtn();
			
		}
	
	


	void CloseButton() {
	mainFrame.close.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
        	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	view.dispose();
        }
    });	
	}
	
	void TitleBar() {
	
		mainFrame.titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
	        	mouseX = evt.getX();
	        	mouseY = evt.getY();	        }
	    });

	
		mainFrame.titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
	        public void mouseDragged(java.awt.event.MouseEvent evt) {
	            int xCor = evt.getXOnScreen();
	            int yCor = evt.getYOnScreen();
	            
	            mainFrame.setLocation(xCor-mouseX, yCor-mouseY);	        }
	    });
	}
	
	
	private void SettingsApplybtn() {

		mainFrame.applybtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mainFrame.applybtn.setBackground(new java.awt.Color(22, 37, 80));
            }
        });		
		
		mainFrame.applybtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	
            	FileUploaded message = new FileUploaded();
            	message.setVisible(true);
            	
            	try {
					databaseController.fillModel.FillTable();
					message.noOfAddedRecords.setText("calculations are finished");

				} catch (Exception e) {
					message.noOfAddedRecords.setText("calculations can not be made");
				}

				message.dispose();
				}
        });
		
		mainFrame.applybtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	mainFrame.applybtn.setBackground(new java.awt.Color(32, 47, 90));
            	
            }
        });		
	}

	void SidePanel() {
	
		mainFrame.importbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	ImportPanelSelected();
            }
        });
	
		mainFrame.databasebtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	DatabasePanelSelected();
	           }
        });
		
		mainFrame.homebutn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	HomePanelSelected();
            }
        });

		
		mainFrame.Settingsbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	SettingsPanelSelected();
            }
        });
		
		mainFrame.fragmentsbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	fragmentsPanelSelected();
            }
        });
	
	}
	
    protected void fragmentsPanelSelected() {
    	HideAll();
    	mainFrame.fragmentsbtn.setBackground(new java.awt.Color(30, 61, 118));
    	mainFrame.northFragments.setVisible(true);
    	mainFrame.fragmentsPanel.setVisible(true);			
	}




	protected void HomePanelSelected() {
    	HideAll();
    	mainFrame.homebutn.setBackground(new java.awt.Color(30, 61, 118));
    	mainFrame.northHome.setVisible(true);
    	mainFrame.homePanel.setVisible(true);		
	}
    protected void SettingsPanelSelected() {
    	HideAll();
    	mainFrame.Settingsbtn.setBackground(new java.awt.Color(30, 61, 118));
    	mainFrame.northSettings.setVisible(true);
    	mainFrame.SettingsPanel.setVisible(true);		
	}

	protected void DatabasePanelSelected() {
    	HideAll();
    	
    	mainFrame.databasebtn.setBackground(new java.awt.Color(30, 61, 118));
    	mainFrame.northDatabase.setVisible(true);
    	mainFrame.databasepanel.setVisible(true);		
	}


	protected void ImportPanelSelected() {
    	HideAll();
    	
    	mainFrame.importbtn.setBackground(new java.awt.Color(30, 61, 118));
    	mainFrame.northImport.setVisible(true);
    	mainFrame.importPanel.setVisible(true);		
	}


	void HideAll() {
    	mainFrame.databasepanel.setVisible(false);
    	mainFrame.importPanel.setVisible(false);
    	mainFrame.homePanel.setVisible(false);
    	mainFrame.SettingsPanel.setVisible(false);
    	mainFrame.fragmentsPanel.setVisible(false);
    	
    	mainFrame.northDatabase.setVisible(false);
    	mainFrame.northHome.setVisible(false);
    	mainFrame.northImport.setVisible(false);
    	mainFrame.northSettings.setVisible(false);
    	mainFrame.northFragments.setVisible(false);
    	
    	mainFrame.homebutn.setBackground(new java.awt.Color(32, 47, 90));
        mainFrame.importbtn.setBackground(new java.awt.Color(32, 47, 90));
        mainFrame.databasebtn.setBackground(new java.awt.Color(32, 47, 90));
        mainFrame.Settingsbtn.setBackground(new java.awt.Color(32, 47, 90));
        mainFrame.fragmentsbtn.setBackground(new java.awt.Color(32, 47, 90));


    }

}
