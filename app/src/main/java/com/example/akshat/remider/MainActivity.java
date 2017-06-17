package com.example.akshat.remider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.akshat.remider.data.dbContract;
import com.example.akshat.remider.data.dbHelper;

public class MainActivity extends AppCompatActivity {

    private DataAdapter mAdapter;
    private EditText name;
    private EditText status;
    private SQLiteDatabase mDb;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

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

        mAdapter = new DataAdapter(this,mDb.query(dbContract.dbEntry.TABLE_NAME,null,null,null,null,null,dbContract.dbEntry._ID));
        dataRecyclerView.setAdapter(mAdapter);

    }

    public void addToData (View view){
        if (name.getText().length()==0 || status.getText().length()==0){
            return;
        }
        addData(name.getText().toString(),status.getText().toString());
        mAdapter.swapCursor(mDb.query(dbContract.dbEntry.TABLE_NAME,null,null,null,null,null,dbContract.dbEntry._ID));

        status.clearFocus();
        name.getText().clear();
        status.getText().clear();
    }

    private void addData (String l_name, String l_status) {
        ContentValues cv = new ContentValues();
        cv.put (dbContract.dbEntry.COLUMN_NAME,l_name);
        cv.put(dbContract.dbEntry.COLUMN_STATUS,l_status);
        mDb.insert(dbContract.dbEntry.TABLE_NAME,null,cv);

    }
}
