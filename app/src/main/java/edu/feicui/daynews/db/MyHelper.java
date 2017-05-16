package edu.feicui.daynews.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 用来管理数据库的更新和创建
 * Created by Administrator on 16-10-24.
 */
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "test_db", null, 1);//参数2：数据库名  参数3：工厂  可为null  参数4：版本
    }

    /**
     * 1.创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStu="create table stu(_id integer,name text,level integer)";//1.1新建数据库表
        //create table：建表  stu：表名 _id：列  integer：列类型  name：列  text：列类型
        String sqlTea="create table tea(_id integer,name text,level integer)";
        db.execSQL(sqlStu);//1.2创建表
        db.execSQL(sqlTea);//创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
