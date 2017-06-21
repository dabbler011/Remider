package com.example.akshat.remider;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshat.remider.data.dbContract;

import java.util.List;

/**
 * Created by akshat on 16/6/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    public Cursor mCursor,tempCursor;
    public Context mContext;

    public DataAdapter(Context context, Cursor cursor, DataAdapterOnClickListner dataClicker){
        this.mContext = context;
        this.mCursor = cursor;

        this.dataClicker = dataClicker;
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
        //Toast.makeText(mContext.getApplicationContext(),name,Toast.LENGTH_SHORT).show();
        holder.name.setText(name);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
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

    public interface DataAdapterOnClickListner {
        void onClick (String dataName,int position);
    }
    

    private final DataAdapterOnClickListner dataClicker;

    public class DataViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView name;
        public DataViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String holderName = this.name.getText().toString();
            Log.v("maa","maamam");
            dataClicker.onClick(holderName,position);
            notifyDataSetChanged();

        }



    }
}
