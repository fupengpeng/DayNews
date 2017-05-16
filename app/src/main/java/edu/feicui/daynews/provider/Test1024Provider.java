package edu.feicui.daynews.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import edu.feicui.daynews.db.MyHelper;

/**
 * 内容提供者
 * Created by Administrator on 16-10-24.
 */
public class Test1024Provider extends ContentProvider {
    SQLiteDatabase mDAteBase;//2.1声明真正操作数据库
    UriMatcher mUriMatcher;//3.2 需要一个Uri  在此声明
    public static final String AUTHORITY="edu.feicui.daynews.provider.Test1024Provider";//3.5 参数1
    public static final String STU="stu";//3.6 参数2
    public static final String TEA="tea";
    public static final int CODE_STU=11;//3.7 参数3：匹配码
    public static final int CODE_TEA=22;
    /**
     *
     * @return  提供者生效  必须返回true
     */
    @Override
    public boolean onCreate() {
        Context context=getContext();//2.3获取Context
        MyHelper myHelper=new MyHelper(context);//2.2需要SQLiteOpenHelper对象，MyHelper继承SQLiteOpenHelper.需要参数不能this
        mDAteBase=myHelper.getWritableDatabase();//2.4 初始化SQLiteDatabase 如果不可写，返回可读
        mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);//3.3 初始化一个UriMatcher对象
        mUriMatcher.addURI(AUTHORITY,STU,CODE_STU);   //3.4 添加所支持的Uri
        mUriMatcher.addURI(AUTHORITY,TEA,CODE_TEA);   // 添加所支持的Uri
        return true;
    }

    /**
     * 查询数据库   与SQLiteDatabase
     * @param uri  访问者传递来
     * @param projection  查询列
     * @param selection  条件
     * @param selectionArgs  条件的值
     * @param sortOrder  排序方式
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //3.1 通过Uri获取具体要操作的数据对象  表

        //4.1 操作数据
        Cursor cursor=mDAteBase.query(getTableNameFormUri(uri),projection,selection,selectionArgs,null,null,sortOrder);//4.2 查询数据
        // 参数1：表名外界传入的Uri  参数2：查询列  null,null  ContentProvider 不支持分组
        return cursor;//4.3  返回cursor
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 插入数据
     * @param uri  插入的数据
     * @param values  插入的
     * @return  需要返回所插入数据行的Uri
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id=mDAteBase.insert(getTableNameFormUri(uri),null,values);  //5.1插入
        Uri uriResult=Uri.parse(uri.toString()+"/"+id);//5.2 获取返回的Uri
        return uriResult;
    }

    /**
     * 删除
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int row=mDAteBase.delete(getTableNameFormUri(uri),selection,selectionArgs);//6.1 删除
        return row;
    }

    /**
     * 更新
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * 获取Uri
     * @param uri
     * @return
     */
    public String getTableNameFormUri(Uri uri){
        //3.1 通过Uri获取具体要操作的数据对象  表
        int code=mUriMatcher.match(uri);//3.8 返回匹配成功的匹配码，匹配失败返回-1  uri：访问者传入
        String tableName="";//3.11 声明表名
        switch (code){//3.9 匹配code
            case CODE_STU://3.10 匹配上CODE_STU  寻找到学生表
                tableName=STU;//3.12 获取表名
                break;
            case CODE_TEA://3.10 匹配上CODE_STU  寻找到老师表
                tableName=TEA;
                break;
        }
        return tableName;
    }
}
