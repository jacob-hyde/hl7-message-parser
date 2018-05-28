package models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DAO;
public class HL7Processed extends DAO
{
	public int uid;
	public String event;
	public String message;
	
	public HL7Processed()
	{
		super();
		this.table = "hl7_processed";
	}
	
	public HL7Processed(ResultSet result)
	{
		super();
		this.table = "hl7_processed";
		try {
			this.uid = result.getInt("uid");
			this.event = result.getString("event");
			this.message = result.getString("message");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected ArrayList<HL7Processed> parseResults(ResultSet results)
	{
		ArrayList<HL7Processed> data = new ArrayList<HL7Processed>();
		try {
			while(results.next())
			{
				HL7Processed populate = new HL7Processed(results);
				data.add(populate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
