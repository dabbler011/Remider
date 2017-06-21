package com.example.akshat.remider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akshat.remider.data.dbContract;
import com.example.akshat.remider.data.dbHelper;

public class MainActivity extends AppCompatActivity implements DataAdapter.DataAdapterOnClickListner {

    public static String num;
    private DataAdapter mAdapter;
    private EditText name;
    private EditText status;
    private SQLiteDatabase mDb;
    private Cursor cCursor;
    private  static int RequestCode = 1;
    private static String r_status;
    private static String[] columns = {dbContract.dbEntry.COLUMN_NAME,dbContract.dbEntry.COLUMN_STATUS};
    private String final_string;
    int final_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView dataRecyclerView;

        dataRecyclerView = (RecyclerView) this.findViewById(R.id.all_data_view);
        name = (EditText) this.findViewById(R.id.editText2);
        status = (EditText) this.findViewById(R.id.editText3);

        dataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper DbHelper = new dbHelper(this);

        mDb = DbHelper.getWritableDatabase();

        mAdapter = new DataAdapter(this,mDb.query(dbContract.dbEntry.TABLE_NAME,null,null,null,null,
                null,dbContract.dbEntry._ID),this);
        dataRecyclerView.setAdapter(mAdapter);

    }

    public void addToData (View view){
        if (name.getText().length()==0 || status.getText().length()==0){
            return;
        }
        addData(name.getText().toString(),status.getText().toString());
        mAdapter.swapCursor(mDb.query(dbContract.dbEntry.TABLE_NAME,null,null,null,null,null,
                dbContract.dbEntry._ID));


        status.clearFocus();
        name.getText().clear();
        status.getText().clear();
    }

    private long addData (String l_name, String l_status) {
        ContentValues cv = new ContentValues();
        cv.put (dbContract.dbEntry.COLUMN_NAME,l_name);
        cv.put(dbContract.dbEntry.COLUMN_STATUS,l_status);
        //Toast.makeText(getApplicationContext(),"add "+l_name, Toast.LENGTH_SHORT).show();
        return mDb.insert(dbContract.dbEntry.TABLE_NAME,null,cv);

    }

    @Override
    public void onClick(String dataName,int position) {
        cCursor=mDb.query(dbContract.dbEntry.TABLE_NAME,columns,dbContract.dbEntry.COLUMN_NAME+"=?",
                new String[]{dataName},null,null,null);
        if(cCursor.moveToFirst()){
            r_status=cCursor.getString(cCursor.getColumnIndex(dbContract.dbEntry.COLUMN_STATUS));
            Intent intent = new Intent(getBaseContext(), StatusActivity.class);
            intent.putExtra("STATUS", r_status);
            intent.putExtra("NAME",dataName);
            final_string = dataName;
            final_position = position;
            startActivityForResult(intent,RequestCode);
        }
        //Log.v("muapp",cCursor.getString(cCursor.getColumnIndex(dbContract.dbEntry.COLUMN_STATUS)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String num1 = "0";

        if(requestCode==RequestCode&&resultCode==RESULT_OK&&data!=null) {
            num1 = data.getStringExtra(num);
            Toast.makeText(getApplicationContext(), num1, Toast.LENGTH_SHORT).show();

            if (num1.equals("1")) {
                Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
                mDb.delete(dbContract.dbEntry.TABLE_NAME, dbContract.dbEntry.COLUMN_NAME + "=?" ,
                        new String[] {final_string});
                mAdapter.swapCursor(mDb.query(dbContract.dbEntry.TABLE_NAME,null,null,null,null,null,
                        dbContract.dbEntry._ID));
                mAdapter.notifyItemRemoved(final_position);
                Log.v("aaa","aaaa");
            }
        }
    }
}
