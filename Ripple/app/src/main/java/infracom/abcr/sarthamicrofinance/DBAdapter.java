package infracom.abcr.sarthamicrofinance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter extends SQLiteOpenHelper{
	
	/******************** if debug is set true then it will show all Logcat message ************/
	public static final boolean DEBUG = true;
	
	/******************** Logcat TAG ************/
	public static final String LOG_TAG = "DBAdapter";
	
	/******************** Table Fields ************/
	public static final String KEY_ID = "_id";

	public static final String KEY_USER_IMEI    = "user_imei";

	public static final String KEY_USER_NAME    = "user_name";
	
	public static final String KEY_USER_MESSAGE = "user_message";

	public static final String KEY_DEVICE_IMEI  = "device_imei";
	
	public static final String KEY_DEVICE_NAME  = "device_name";

	public static final String KEY_DEVICE_EMAIL = "device_email";

	public static final String KEY_DEVICE_REGID = "device_regid";

	public static final String KEY_branch_name = "branch_name";
	public static final String KEY_branch_id = "branch_id";


	public static final String KEY_Duser_idID = "user_id";


	public static final String KEY_under_depart = "under_depart";

	/******************** Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";
	
	/******************** Database Version (Increase one if want to also upgrade your database) ************/
	public static final int DATABASE_VERSION = 1;// started at 1

	/** Table names */
	public static final String USER_TABLE = "tbl_user";
	public static final String DEVICE_TABLE = "tbl_device";
	public static final String PRODUCT_TABLE = "tbl_product";
	
	/******************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ************/
	private static final String[] ALL_TABLES = { USER_TABLE,DEVICE_TABLE,PRODUCT_TABLE };
	
	/** Create table syntax */
	//private static final String USER_MAIN_CREATE = "create table tbl_user_main(_id integer primary key autoincrement, user_name text not null, user_imei text not null);";
	private static final String USER_CREATE = "create table tbl_user(_id integer primary key autoincrement, user_name text not null, user_imei text not null, user_message text not null);";
	private static final String DEVICE_CREATE = "create table tbl_device(_id integer primary key autoincrement, device_name text not null, device_email text not null, device_regid text not null, device_imei text not null, user_id text not null, under_depart text not null, branch_name text not null, branch_id text not null);";
	private static final String PRODUCT_CREATE = "create table tbl_product(_id integer primary key autoincrement not null);";

	/******************** Used to open database in syncronized way ************/
	private static DataBaseHelper DBHelper = null;

	public DBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    /******************* Initialize database *************/
	public static void init(Context context) {
		if (DBHelper == null) {
			if (DEBUG)
				Log.i("DBAdapter", context.toString());
			DBHelper = new DataBaseHelper(context);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/********************** Main Database creation INNER class ********************/
	private static class DataBaseHelper extends SQLiteOpenHelper {
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.i(LOG_TAG, "new create");
			try {
				//db.execSQL(USER_MAIN_CREATE);
				db.execSQL(USER_CREATE);
				db.execSQL(DEVICE_CREATE);
				db.execSQL(PRODUCT_CREATE);

			} catch (Exception exception) {
				if (DEBUG)
					Log.i(LOG_TAG, "Exception onCreate() exception");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (DEBUG)
				Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
						+ "to" + newVersion + "...");

			for (String table : ALL_TABLES) {
				db.execSQL("DROP TABLE IF EXISTS " + table);
			}
			onCreate(db);
		}

	} // Inner class closed
	
	
	/********************** Open database for insert,update,delete in syncronized manner ********************/
	private static synchronized SQLiteDatabase open() throws SQLException {
		return DBHelper.getWritableDatabase();
	}


	
	// Insert installing device data
	public static void addDeviceData(String DeviceName, String DeviceEmail,String DeviceRegID,String DeviceIMEI, String user_idID,String D_under_depart, String branch_name, String branch_id) {
	    try{
			final SQLiteDatabase db = open();
	    	
	    	String imei  = sqlEscapeString(DeviceIMEI);
	    	String name  = sqlEscapeString(DeviceName);
			String email = sqlEscapeString(DeviceEmail);
			String regid = sqlEscapeString(DeviceRegID);
			String Duser_idID = sqlEscapeString(user_idID);

			String D_under_departKEY = sqlEscapeString(D_under_depart);
			
			ContentValues cVal = new ContentValues();
			cVal.put(KEY_DEVICE_IMEI, imei);
			cVal.put(KEY_DEVICE_NAME, name);
			cVal.put(KEY_DEVICE_EMAIL, email);
			cVal.put(KEY_DEVICE_REGID, regid);
			cVal.put(KEY_Duser_idID, Duser_idID);
			cVal.put(KEY_under_depart, D_under_departKEY);


			cVal.put(KEY_branch_name, branch_name);
			cVal.put(KEY_branch_id, branch_id);

			db.insert(DEVICE_TABLE, null, cVal);
	        db.close(); // Closing database connection
	    } catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
    }
	
	
	// Adding new user
 
    public static void addUserData(UserData uData) {
    	try{
		    	final SQLiteDatabase db = open();
		    	
		    	String imei  = sqlEscapeString(uData.getIMEI());
		    	String name  = sqlEscapeString(uData.getName());
		    	String message  = sqlEscapeString(uData.getMessage());
				
				ContentValues cVal = new ContentValues();
				cVal.put(KEY_USER_IMEI, imei);
				cVal.put(KEY_USER_NAME, name);
				cVal.put(KEY_USER_MESSAGE, message);
				db.insert(USER_TABLE, null, cVal);
		        db.close(); // Closing database connection
	    } catch (Throwable t) {
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
    }
 
    // Getting single user data
    public static UserData getUserData(int id) {
    	final SQLiteDatabase db = open();
 
        Cursor cursor = db.query(USER_TABLE, new String[] { KEY_ID,
        		KEY_USER_NAME, KEY_USER_IMEI,KEY_USER_MESSAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        UserData data = new UserData(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return data;
    }
 
    // Getting All user data
    public static List<UserData> getAllUserData() {
        List<UserData> contactList = new ArrayList<UserData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE+" ORDER BY "+KEY_ID+" desc";
 
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	UserData data = new UserData();
            	data.setID(Integer.parseInt(cursor.getString(0)));
            	data.setName(cursor.getString(1));
            	data.setIMEI(cursor.getString(2));
            	data.setMessage(cursor.getString(3));
                // Adding contact to list
                contactList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return contactList;
    }
    
    // Getting users Count
    public static int getUserDataCount() {
        String countQuery = "SELECT  * FROM " + USER_TABLE;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        cursor.close();
        
        // return count
        return count;
    }
    
    // Getting installed device have self data or not
    public static int validateDevice() {
        String countQuery = "SELECT  * FROM " + DEVICE_TABLE;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        cursor.close();
        
        // return count
        return count;
    }
    
    // Getting distinct user data use in spinner
    public static List<UserData> getDistinctUser() {
    	List<UserData> contactList = new ArrayList<UserData>();
        // Select All Query
        String selectQuery = "SELECT  distinct(user_imei),user_name FROM " + USER_TABLE+" ORDER BY "+KEY_ID+" desc";
        
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	UserData data = new UserData();
            	
            	data.setIMEI(cursor.getString(0));
            	data.setName(cursor.getString(1));
            	// Adding contact to list
                contactList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        return contactList;
    }
    
    // Getting imei already in user table or not 
    public static int validateNewMessageUserData(String IMEI) {
    	 int count = 0;
    	try {
	        String countQuery = "SELECT "+KEY_ID+" FROM " + USER_TABLE + " WHERE user_imei='"+IMEI+"'";
	        final SQLiteDatabase db = open();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        
	        count = cursor.getCount();
	        cursor.close();
    	} catch (Throwable t) {
    		count = 10;
			Log.i("Database", "Exception caught: " + t.getMessage(), t);
		}
        return count;
    }

    
	/*********************** Escape string for single quotes (Insert,Update)************/
	private static String sqlEscapeString(String aString) {
		String aReturn = "";
		
		if (null != aString) {
			//aReturn = aString.replace("'", "''");
			aReturn = DatabaseUtils.sqlEscapeString(aString);
			// Remove the enclosing single quotes ...
			aReturn = aReturn.substring(1, aReturn.length() - 1);
		}
		
		return aReturn;
	}
	/*********************** UnEscape string for single quotes (show data)************/
	private static String sqlUnEscapeString(String aString) {
		
		String aReturn = "";
		
		if (null != aString) {
			aReturn = aString.replace("''", "'");
		}
		
		return aReturn;
	}

	public HashMap<String, String> getLogininfo() {
		HashMap<String, String> wordList = new HashMap<String, String>();
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "+DEVICE_TABLE+" where "+KEY_DEVICE_IMEI+"!= '"+""+"'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				//HashMap<String, String> map = new HashMap<String, String>();

				wordList.put("name", cursor.getString(1));
				wordList.put("email", cursor.getString(2));
				wordList.put("regid", cursor.getString(3));

				wordList.put("device_imei", cursor.getString(4));

				wordList.put("user_id", cursor.getString(5));

				wordList.put("under_depart", cursor.getString(6));

				wordList.put("branch_name", cursor.getString(7));
				wordList.put("branch_id", cursor.getString(8));

				//wordList.add(map);
			} while (cursor.moveToNext());
		}
		return wordList;
	}

	public Cursor addColumn(String name){

		SQLiteDatabase database = this.getReadableDatabase();
		database=this.getWritableDatabase();
		return  database.rawQuery("alter table tbl_product add column " + name + " text", null);

	}

	public static void deleteAll()
	{
		final SQLiteDatabase db = open();
		db.delete(DEVICE_TABLE,null,null);
		db.delete(PRODUCT_TABLE,null,null);
		db.delete(USER_TABLE,null,null);
		//db.execSQL("delete * from "+ DEVICE_TABLE);
		//db.execSQL("TRUNCATE table" + PRODUCT_TABLE);
		db.close();
	}
	/********************************************************************/
}
