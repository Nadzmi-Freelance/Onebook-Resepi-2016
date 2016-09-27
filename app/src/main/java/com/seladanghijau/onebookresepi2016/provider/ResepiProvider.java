package com.seladanghijau.onebookresepi2016.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.seladanghijau.onebookresepi2016.dto.Resepi;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class ResepiProvider extends SQLiteOpenHelper {
    public static final String DB_NAME = "resepi_2016.db";
    public static final int DB_VERSION = 1;
    public static String DB_PATH;

    private Context context;

    public ResepiProvider(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.context = context;
    }

    // @overwrite methods --------------------------------------------------------------------------
    public void onCreate(SQLiteDatabase db) {}
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    // ---------------------------------------------------------------------------------------------

    // init methods --------------------------------------------------------------------------------
    public void initDB() throws Exception { // initialize db
        if(!checkDBExistence()) {
            this.getReadableDatabase();
            copyDB();
        }
    }

    private boolean checkDBExistence() { // check if db exist or not
        return context.getDatabasePath(DB_NAME).exists();
    }

    private void copyDB() throws Exception { // copy resepi_100.db from external file to app internal path
        InputStream inputStream;
        OutputStream outputStream;
        int dataLength;

        inputStream = context.getAssets().open(DB_NAME); // secify inputstream toward resepi_100.db file
        outputStream = new FileOutputStream(DB_PATH); // specify outputstream towards app internal path

        // copy resepi_100.db from external file to app internal path
        byte[] buffer = new byte[1024];
        while ((dataLength = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, dataLength);
        }

        // close stream
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    private static Bitmap byteArrayToBitmap(byte[] resepiByteArray, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateinSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(resepiByteArray, 0, resepiByteArray.length, options);
    }

    private static int calculateinSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int sampleSize = 2;

        if(reqWidth == 0 || reqHeight == 0) return sampleSize;

        if(width > reqWidth || height > reqHeight) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return sampleSize;
    }
    // ---------------------------------------------------------------------------------------------

    // provider methods ----------------------------------------------------------------------------
    public int getResepiCategoryId(String categoryName) {
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;
        int categoryId;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT categoryId FROM lookupresepicategory WHERE categoryDesc LIKE '%" + categoryName + "%' LIMIT 1"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        categoryId = cursor.getInt(cursor.getColumnIndex("categoryId"));

        cursor.close();
        sqliteDB.close();

        return categoryId;
    }

    public ArrayList<Resepi> getResepiList() {
        ArrayList<Resepi> resepiList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT * FROM resepi"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for query=ied data

        cursor.moveToFirst(); // move cursor to first index
        resepiList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            int resepiId, resepiCategory;
            String resepiName;
            Bitmap resepiImg;

            resepiId = cursor.getInt(cursor.getColumnIndex("resepiId"));
            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiCategory = cursor.getInt(cursor.getColumnIndex("resepiCategory"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiList.add(new Resepi(resepiId, resepiName, resepiCategory, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiList;
    }

    public ArrayList<Resepi> getResepiList(int resepiCategory) {
        ArrayList<Resepi> resepiList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT * FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            int resepiId;
            String resepiName;
            Bitmap resepiImg;

            resepiId = cursor.getInt(cursor.getColumnIndex("resepiId"));
            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiList.add(new Resepi(resepiId, resepiName, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiList;
    }

    public String[] getResepiNameList(int resepiCategory) {
        String[] resepiNameList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameList = new String[cursor.getCount()];
        for(int x=0 ; x<cursor.getCount() ; x++) {
            String resepiName;

            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiNameList[x] = resepiName;
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameList;
    }

    public String[] getResepiNameList() {
        String[] resepiNameList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName FROM resepi"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameList = new String[cursor.getCount()];
        for(int x=0 ; x<cursor.getCount() ; x++) {
            String resepiName;

            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiNameList[x] = resepiName;
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameList;
    }

    public void addFavorite(int resepiId) {
        SQLiteDatabase sqliteDB;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "UPDATE resepi SET resepiFavorite='1' WHERE resepiId LIKE '" + resepiId + "'"; // sql query
        sqliteDB.execSQL(sql);

        sqliteDB.close();
    }

    public void removeFavorite(int resepiId) {
        SQLiteDatabase sqliteDB;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "UPDATE resepi SET resepiFavorite='0' WHERE resepiId LIKE '" + resepiId + "'"; // sql query
        sqliteDB.execSQL(sql);

        sqliteDB.close();
    }

    public ArrayList<Pair<String, Bitmap>> getFavoriteResepi() {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi WHERE resepiFavorite LIKE '1'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String resepiName;
            Bitmap resepiImg;

            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(resepiName, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg() {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String resepiName;
            Bitmap resepiImg;

            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(resepiName, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory) {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String resepiName;
            Bitmap resepiImg;

            resepiName = cursor.getString(cursor.getColumnIndexOrThrow("resepiName"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(resepiName, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory, int resepiId) {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "' AND resepiId LIKE '" + resepiId + "'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String resepiName;
            Bitmap resepiImg;

            resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            resepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(resepiName, resepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory, String resepiName) {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "' AND resepiName LIKE '%" + resepiName + "%'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String tempResepiName;
            Bitmap tempResepiImg;

            tempResepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            tempResepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(tempResepiName, tempResepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(String resepiName) {
        ArrayList<Pair<String, Bitmap>> resepiNameWithImgList;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiName, resepiGambar FROM resepi WHERE resepiName LIKE '%" + resepiName + "%'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiNameWithImgList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String tempResepiName;
            Bitmap tempResepiImg;

            tempResepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
            tempResepiImg = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
            resepiNameWithImgList.add(new Pair<>(tempResepiName, tempResepiImg));

            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiNameWithImgList;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiCategoryListWithImg() {
        ArrayList<Pair<String, Bitmap>> resepiCategory;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT categoryDesc, categoryGambar FROM lookupresepicategory"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiCategory = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String categoryName = cursor.getString(cursor.getColumnIndex("categoryDesc"));
            Bitmap categoryGambar = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("categoryGambar")), 300, 200);

            resepiCategory.add(new Pair<>(categoryName, categoryGambar));
            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiCategory;
    }

    public ArrayList<Pair<String, Bitmap>> getResepiCategoryListWithImg(int categoryId) {
        ArrayList<Pair<String, Bitmap>> resepiCategory;
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT categoryDesc, categoryGambar FROM lookupresepicategory WHERE categoryId LIKE '" + categoryId + "' LIMIT 1"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiCategory = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String categoryName = cursor.getString(cursor.getColumnIndex("categoryDesc"));
            Bitmap categoryGambar = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("categoryGambar")), 300, 200);

            resepiCategory.add(new Pair<>(categoryName, categoryGambar));
            cursor.moveToNext();
        }

        cursor.close();
        sqliteDB.close();

        return resepiCategory;
    }

    public int getResepiId(String resepiName) {
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;
        int resepiId;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT resepiId FROM resepi WHERE resepiName LIKE '" + resepiName + "' LIMIT 1"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiId = cursor.getInt(cursor.getColumnIndex("resepiId"));

        cursor.close();
        sqliteDB.close();

        return resepiId;
    }

    public int getResepiCount(int resepiCategory) {
        SQLiteDatabase sqliteDB;
        Cursor cursor;
        String sql;
        int resepiId;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        sql = "SELECT COUNT(resepiId) AS resepiCount FROM resepi WHERE resepiCategory LIKE '" + resepiCategory + "'"; // sql query
        cursor = sqliteDB.rawQuery(sql, null); // cursor for queried data

        cursor.moveToFirst(); // move cursor to first index
        resepiId = cursor.getInt(cursor.getColumnIndex("resepiCount"));

        cursor.close();
        sqliteDB.close();

        return resepiId;
    }

    public Resepi getResepiInfo(int resepiId) {
        SQLiteDatabase sqliteDB;
        Cursor cursor, cursorLangkah, cursorBahan;
        String sql, sqlLangkah, sqlBahan;

        // resepi info
        int resepiCategory;
        String resepiName, resepiRingkasan;
        Bitmap resepiGambar;
        String[] resepiLangkah;
        boolean resepiFavorite;
        ArrayList<Pair<String, String>> resepiBahan;

        sqliteDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE); // read sqlite db

        // list of related sql
        sql = "SELECT * FROM resepi WHERE resepiId LIKE '" + resepiId + "'";
        sqlLangkah = "SELECT * FROM langkah WHERE resepiId LIKE '" + resepiId + "' ORDER BY langkahNo";
        sqlBahan = "SELECT * FROM bahan WHERE resepiId LIKE '" + resepiId + "' ORDER BY bahanId";

        // list of related cursor
        cursor = sqliteDB.rawQuery(sql, null);
        cursorLangkah = sqliteDB.rawQuery(sqlLangkah, null);
        cursorBahan = sqliteDB.rawQuery(sqlBahan, null);

        // move cursor to first index
        cursor.moveToFirst();
        cursorLangkah.moveToFirst();
        cursorBahan.moveToFirst();

        // get basic info for resepi
        resepiId = cursor.getInt(cursor.getColumnIndex("resepiId"));
        resepiName = cursor.getString(cursor.getColumnIndex("resepiName"));
        resepiCategory = cursor.getInt(cursor.getColumnIndex("resepiCategory"));
        resepiRingkasan = cursor.getString(cursor.getColumnIndex("resepiRingkasan"));
        resepiGambar = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex("resepiGambar")), 300, 200);
        if(cursor.getInt(cursor.getColumnIndex("resepiFavorite")) == 1)
            resepiFavorite = true;
        else
            resepiFavorite = false;

        // get langkah info for resepi
        resepiLangkah = new String[cursorLangkah.getCount()];
        for(int x=0 ; x<cursorLangkah.getCount() ; x++) {
            resepiLangkah[x] = cursorLangkah.getString(cursorLangkah.getColumnIndex("langkahDesc"));

            cursorLangkah.moveToNext();
        }

        // get bahan info for resepi
        resepiBahan = new ArrayList<>();
        while (!cursorBahan.isAfterLast()) {
            String bahanDesc, bahanName;

            bahanDesc = cursorBahan.getString(cursorBahan.getColumnIndex("bahanDesc"));
            bahanName = cursorBahan.getString(cursorBahan.getColumnIndex("bahanName"));

            if(bahanDesc == null)
                bahanDesc = "";

            resepiBahan.add(new Pair<>(String.valueOf(bahanDesc), bahanName));

            cursorBahan.moveToNext();
        }

        cursor.close();
        cursorBahan.close();
        cursorLangkah.close();
        sqliteDB.close();

        return new Resepi(resepiId, resepiName, resepiRingkasan, resepiCategory, resepiGambar, resepiLangkah, resepiBahan, resepiFavorite);
    }
    // ---------------------------------------------------------------------------------------------
}
