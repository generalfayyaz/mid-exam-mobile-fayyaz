package com.example.mid_exam_fayyaz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student_db";
    private static final String TB_NAME = "student";
    private static final String Col_ID = "id";
    private static final String Col_Name = "name";
    private static final String Col_StudentID = "student_id";
    private static final String Col_Enrolled = "enrolled";

    private SQLiteDatabase mySQLite;

    private static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
            + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Col_Name + " TEXT NOT NULL, "
            + Col_StudentID + " TEXT NOT NULL UNIQUE, "
            + Col_Enrolled + " INTEGER NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }
    public boolean addStudent(StudentModel student) {
        ContentValues cv = new ContentValues();
        cv.put(Col_Name, student.getName());
        cv.put(Col_StudentID, student.getStudentId());
        cv.put(Col_Enrolled, student.isEnrolled() ? 1 : 0);
        mySQLite = this.getWritableDatabase();
        long result = mySQLite.insert(TB_NAME, null, cv);
        return result != -1;
    }
    public boolean updateStudent(StudentModel student) {
        ContentValues cv = new ContentValues();
        cv.put(Col_Name, student.getName());
        cv.put(Col_StudentID, student.getStudentId());
        cv.put(Col_Enrolled, student.isEnrolled() ? 1 : 0);
        mySQLite = this.getWritableDatabase();
        try {
            mySQLite.update(TB_NAME, cv, Col_ID + " = ?",
                    new String[]{String.valueOf(student.getId())});
            return true;
        } catch (SQLiteConstraintException e) {
            return false;
        }
    }

    public void deleteStudent(int id) {
        mySQLite = this.getWritableDatabase();
        mySQLite.delete(TB_NAME, Col_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<StudentModel> getStudentList() {
        String viewData = "SELECT id, name, student_id, enrolled FROM " + TB_NAME;
        mySQLite = this.getReadableDatabase();
        List<StudentModel> list = new ArrayList<>();
        Cursor cursor = mySQLite.rawQuery(viewData, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String studentId = cursor.getString(2);
                boolean enrolled = cursor.getInt(3) == 1;
                list.add(new StudentModel(id, name, studentId, enrolled));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}