package DataBase;

import javax.swing.JLabel;

public class HomeController extends Controller{
	
	MainController controller;
	
	public HomeController( MainController incontroller) {
		controller = incontroller;
		 MouseEntered();
		 MouseExit();
		 MouseClicked();
	}
	
	private void MouseClicked() {
		mainFrame.viewDBlbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	controller.DatabasePanelSelected();
            }
        });
		mainFrame.importlbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	controller.ImportPanelSelected();
            }
        });		
	}

	private void MouseEntered() {
		mainFrame.importlbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	        	lblMouseEntered( mainFrame.importlbl);
	        }
	    });
		
		mainFrame.viewDBlbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	        	lblMouseEntered( mainFrame.viewDBlbl);
	        }
	    });
		
		mainFrame.gettingStartedLbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	        	lblMouseEntered( mainFrame.gettingStartedLbl);
	        }
	    });
		
		mainFrame.aboutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	        	lblMouseEntered( mainFrame.aboutLabel);
	        }
	    });		

		
	}
	private void MouseExit() {
		mainFrame.importlbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseExited(java.awt.event.MouseEvent evt) {
	        	lblMouseExited(mainFrame.importlbl);
	        }
	    });

		mainFrame.viewDBlbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseExited(java.awt.event.MouseEvent evt) {
	        	lblMouseExited(mainFrame.viewDBlbl);
	        }
	    });

		mainFrame.gettingStartedLbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseExited(java.awt.event.MouseEvent evt) {
	        	lblMouseExited(mainFrame.gettingStartedLbl);
	        }
	    });

		mainFrame.aboutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseExited(java.awt.event.MouseEvent evt) {
	        	lblMouseExited(mainFrame.aboutLabel);
	        }
	    });
		}
	
	
	private void lblMouseEntered(JLabel label) {
		label.setForeground(new java.awt.Color(100,100,76));
	}


	private void lblMouseExited(JLabel label) {
		label.setForeground(new java.awt.Color(204,204,204));
		
	}
}
