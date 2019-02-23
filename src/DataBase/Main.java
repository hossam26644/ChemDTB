package DataBase;

import java.io.FileNotFoundException;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		View view = new View();

		try {
			Model.setParams("Params.json");
			Controller.set(view);
			MainController controller = new MainController();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		view.mainFrame.setVisible(true);
	}


}
