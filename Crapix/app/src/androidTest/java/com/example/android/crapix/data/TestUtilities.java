package com.example.android.crapix.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;


public class TestUtilities extends AndroidTestCase {

    private static long TEST_ID = 0;

    /*
        Students: Use this to create some default weather values for your database tests.
     */
    static ContentValues createPlayerValues() {
        ContentValues playerValues = new ContentValues();
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Money, 1000000);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Housing, 25);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Metal, 500000);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_MetalMine, 20);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Minerals, 400000);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_MineralPlant, 23);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Oil, 200000);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_OilRefinery, 5);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_Uranium, 100);
        playerValues.put(CrapixContract.CrapixEntry.COLUMN_UraniumEnrichment, 0);

        return playerValues;
    }

    /*
        Students: You can uncomment this helper function once you have finished creating the
        LocationEntry part of the WeatherContract.
     */
    static long createCountryId() {
        TEST_ID += 1;
        return TEST_ID;
    }

    /*
        Students: You can uncomment this function once you have finished creating the
        LocationEntry part of the WeatherContract as well as the WeatherDbHelper.
     */
    static long InsertCountryId(Context context) {
        // insert our test records into the database
        CrapixDbHelper dbHelper = new CrapixDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long CountryId;
        CountryId = db.insert(CrapixContract.CrapixEntry.TABLE_NAME, null, createPlayerValues());

        // Verify we got a row back.
        assertTrue("Error: Failure to insert CountryId", CountryId != -1);

        return CountryId;
    }

    /*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.

        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

       public void waitForNotificationOrFail() {
          //// Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
          //// It's useful to look at the Android CTS source for ideas on how to test your Android
          //// applications.  The reason that PollingCheck works is that, by default, the JUnit
          //// testing framework is not running on the main Android application thread.
          //new PollingCheck(5000) {
          //
          //    @Override
          //    protected boolean check() {
          //        return mContentChanged;
          //    }
          //}.run();
          //mHT.quit();
       }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}
