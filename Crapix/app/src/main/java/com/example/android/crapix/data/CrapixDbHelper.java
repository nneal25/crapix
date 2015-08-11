package com.example.android.crapix.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.crapix.data.CrapixContract.CrapixEntry;

/**
 * Manages a local database for weather data.
 */
public class CrapixDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "crapix.db";

    public CrapixDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CRAPIX_TABLE = "CREATE TABLE " + CrapixEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                CrapixEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                CrapixEntry.COLUMN_Money + " INTEGER NOT NULL, " +
                CrapixEntry.COLUMN_Housing + " INTEGER NOT NULL, " +

                CrapixEntry.COLUMN_Metal + " INTEGER NOT NULL," +
                CrapixEntry.COLUMN_MetalMine + " INTEGER NOT NULL, " +

                CrapixEntry.COLUMN_Minerals + " INTEGER NOT NULL, " +
                CrapixEntry.COLUMN_MineralPlant + " INTEGER NOT NULL, " +

                CrapixEntry.COLUMN_Oil + " INTEGER NOT NULL, " +
                CrapixEntry.COLUMN_OilRefinery + " INTEGER NOT NULL, " +

                CrapixEntry.COLUMN_Uranium + " INTEGER NOT NULL, " +
                CrapixEntry.COLUMN_UraniumEnrichment + " INTEGER NOT NULL, " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + CrapixEntry._ID  + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_CRAPIX_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CrapixEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
