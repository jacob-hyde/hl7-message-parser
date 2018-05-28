package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class DAO
{
	private String query = "";
	private ArrayList<String> whereConditions = new ArrayList<String>();
	private ArrayList<String> select = new ArrayList<String>();
	private ArrayList<String[]> orderBy = new ArrayList<String[]>();
	private int limit = 0;
	private int offset = 0;
	private String groupBy;
	protected DatabaseConnection DB;
	protected String table;
	protected String uniqueIdentifier = "uid";
	
	public DAO()
	{
		this.DB = new DatabaseConnection();
		this.reset();
	}
	
	final public ArrayList<?> get()
	{
		this.createQuery();
		ArrayList<?> data = new ArrayList<>();
		try {
			ResultSet results = this.DB.read(this.query);
			data = this.parseResults(results);
			this.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	abstract protected ArrayList<?> parseResults(ResultSet results);
	
	final public void where(String column, String where, String ifOperator)
	{
		if(ifOperator != null) this.whereConditions.add(column+" "+where+" "+ifOperator);
		else this.whereConditions.add(column+" = "+where);
	}
	
	final public void select(String[] selectValues)
	{
		if(selectValues.length == 0) return;
		for(String s : selectValues)
		{
			this.select.add(s);
		}
	}
	
	final public void orderBy(String[] orderByCondition)
	{
		if(orderByCondition.length == 0) return;
		this.orderBy.add(orderByCondition);
	}
	
	final public void groupBy(String gb)
	{
		this.groupBy = gb;
	}
	
	final public void setLimit(int l)
	{
		if(l > 0) this.limit = l;
	}
	
	final public void setOffset(int o)
	{
		if(o > 0) this.offset = o;
	}
	
	protected String createQuery()
	{
		this.query = "SELECT ";
		this.select.forEach(value -> {
			this.query = this.query.concat(value+", ");
		});
		this.query = this.query.substring(0, this.query.length()-2);
		this.query = this.query.concat(" FROM "+this.table);
		if(!whereConditions.isEmpty())
		{
			this.query = this.query.concat(" WHERE ");
			this.whereConditions.forEach(value -> {
				this.query = this.query.concat(value+" AND ");
			});
			this.query = this.query.substring(0, this.query.length()-5);
		}
		if(this.groupBy != null) this.query = this.query.concat(" GROUP BY "+this.groupBy);
		if(!this.orderBy.isEmpty())
		{
			this.query = this.query.concat(" ORDER BY ");
			this.orderBy.forEach(value -> {
				this.query = this.query.concat(value[0]+" "+value[1]+", ");
			});
			this.query = this.query.substring(0, this.query.length()-2);
		}
		if(this.limit != 0) this.query = this.query.concat(" LIMIT "+this.limit);
		if(this.offset != 0) this.query = this.query.concat(" OFFSET "+this.offset);
		return this.query;
	}
	
	private void reset()
	{
		this.whereConditions.clear();
		this.select.clear();
		this.select.add("*");
		this.orderBy.clear();
		String[] defaultOrderBy = {this.uniqueIdentifier, "ASC"};
		this.orderBy.add(defaultOrderBy);
		this.limit = 0;
		this.offset = 0;
		this.groupBy = null;
	}
}
