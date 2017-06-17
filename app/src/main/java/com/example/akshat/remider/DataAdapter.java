package com.example.akshat.remider;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akshat.remider.data.dbContract;

import java.util.List;

/**
 * Created by akshat on 16/6/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    public Cursor mCursor;
    public Context mContext;

    public DataAdapter (Context context, Cursor cursor){
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.data_container, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(dbContract.dbEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(dbContract.dbEntry._ID));
        holder.name.setText(name);
        holder.itemView.setTag(id);
    }

    public void swapCursor (Cursor newCursor) {
        if(mCursor!=null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor!=null){
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
       return mCursor.getCount();
    }

    class DataViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public DataViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
