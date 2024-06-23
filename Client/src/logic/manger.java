package logic;

import java.util.Map;

public class manger {
	private String ID;
	private static manger instance;
	
	private manger(String ID) {
		this.ID=ID;
	}
	public static manger getInstance (String ID) {
		 instance = new manger (ID);
	     return instance;

	}
	public static manger convertToManger(Map<String, Object> row) {
		String ID = (String) row.get("ID");
		return new manger(ID);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
}
