package com.example.android.crapix.data;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class CrapixContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.android.crapix.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_Crapix = "crapix";

    public static final class CrapixEntry implements BaseColumns {

        public static final String TABLE_NAME = "ecoTable";

        public static final String COLUMN_Country_ID = "country_id";

        public static final String COLUMN_Money = "money";

        public static final String COLUMN_Housing = "housing";

        public static final String COLUMN_Metal = "metal";

        public static final String COLUMN_MetalMine = "metalMine";

        public static final String COLUMN_Minerals = "minerals";

        public static final String COLUMN_MineralPlant = "mineralPlant";

        public static final String COLUMN_Oil = "oil";

        public static final String COLUMN_OilRefinery = "oilRefinery";

        public static final String COLUMN_Uranium = "uranium";

        public static final String COLUMN_UraniumEnrichment = "uraniumEnrichment";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_Crapix).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Crapix;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Crapix;

        public static Uri buildCrapixUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }
}