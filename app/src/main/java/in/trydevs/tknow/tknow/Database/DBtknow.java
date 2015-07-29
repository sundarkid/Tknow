package in.trydevs.tknow.tknow.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.trydevs.tknow.tknow.DataClasses.Post;

/**
 * Created by Sundareswaran on 28-07-2015.
 */
public class DBtknow {

    private final short TABLE_POSTS = 0;

    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;

    public DBtknow(Context context){
        mySQLiteHelper = new MySQLiteHelper(context);
        database = mySQLiteHelper.getWritableDatabase();
    }

    public void insertPostData(List<Post> posts){
        // Create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_POSTS) + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
        // Inserting into table
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < posts.size() ; i++){
            Post current = posts.get(i);
            statement.clearBindings();
            statement.bindString(2,Long.toString(current.getUser_id()));
            statement.bindString(3,Long.toString(current.getPost_id()));
            statement.bindString(4,current.getName());
            statement.bindString(5,current.getTitle());
            statement.bindString(6,current.getMessage());
            statement.bindString(7,current.getImage());
            statement.bindString(8,current.getDate());

            statement.execute();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public List<Post> getPostdata(){
        List<Post> posts;

        Cursor cursor = database.query(getTableName(TABLE_POSTS),null,null,null,null,null, MySQLiteHelper.COLUMN_SNO + " DESC ");
        if (cursor != null && cursor.moveToFirst()){
            posts = new ArrayList<>();
            int index_sno = cursor.getColumnIndex(MySQLiteHelper.COLUMN_SNO);
            int index_user_id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID);
            int index_post_id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_POST_ID);
            int index_name = cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME);
            int index_title = cursor.getColumnIndex(MySQLiteHelper.COLUMN_TITLE);
            int index_message = cursor.getColumnIndex(MySQLiteHelper.COLUMN_MESSAGE);
            int index_image = cursor.getColumnIndex(MySQLiteHelper.COLUMN_IMAGE);
            int index_date = cursor.getColumnIndex(MySQLiteHelper.COLUMN_DATE);
            do{
                Post post = new Post();
                post.setSno(cursor.getLong(index_sno));
                post.setUser_id(cursor.getLong(index_user_id));
                post.setPost_id(cursor.getLong(index_post_id));
                post.setName(cursor.getString(index_name));
                post.setTitle(cursor.getString(index_title));
                post.setMessage(cursor.getString(index_message));
                post.setImage(cursor.getString(index_image));
                post.setDate(cursor.getString(index_date));

                posts.add(post);
            }while (cursor.moveToNext());
            cursor.close();
        }
        else
            posts = Collections.emptyList();
        return posts;
    }

    private String getTableName(short i) {
        switch (i){
            case TABLE_POSTS:
                return MySQLiteHelper.TABLE_POST;
        }
        return null;
    }

}
