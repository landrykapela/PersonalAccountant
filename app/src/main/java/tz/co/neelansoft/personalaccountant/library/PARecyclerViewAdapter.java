package tz.co.neelansoft.personalaccountant.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import tz.co.neelansoft.personalaccountant.R;
import tz.co.neelansoft.personalaccountant.Record;

public class PARecyclerViewAdapter extends RecyclerView.Adapter<PARecyclerViewAdapter.ViewHolder>{

    private List<Record> mRecords;
    private Context context;
    private ItemClickListener ItemClickListener;

    public interface ItemClickListener{
        void onItemClick(int itemId);
    }

    public PARecyclerViewAdapter(Context context, ItemClickListener listener){
        this.context = context;
        this.ItemClickListener = listener;
    }
    @NonNull
    @Override
    public PARecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.record_item_layout,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull PARecyclerViewAdapter.ViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mRecords != null) return mRecords.size();
        else return 0;
    }

    public List<Record> getRecords(){
        return this.mRecords;
    }
    public Record getRecordWithId(int itemId){
        Record r = new Record();
        for(int i=0; i< mRecords.size();i++){
            if(mRecords.get(i).getId() == itemId)
                r =mRecords.get(i);
        }
        return r;
    }

    public void setRecords(List<Record> list){
        this.mRecords = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextViewAmount;
        TextView mTextViewDate;

        ImageView mImageIndicator;
        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewAmount = itemView.findViewById(R.id.tv_amount);
            mTextViewDate   = itemView.findViewById(R.id.tv_date);

            mImageIndicator= itemView.findViewById(R.id.iv_indicator);
            itemView.setOnClickListener(this);
        }


        public void bind(int position){
            Record record = getRecords().get(position);
            NumberFormat format = Config.CurrencyFormat();
            String amount = String.valueOf(format.format(record.getAmount()));
            String date   = record.getUpdatedAt();
            int recordType = record.getRecordType();
            if(recordType != 0){
                mImageIndicator.setRotation(-45);
                mImageIndicator.setColorFilter(R.color.colorAccent);
                mTextViewAmount.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }

            mTextViewAmount.setText(amount);
            mTextViewDate.setText(date);
        }

        @Override
        public void onClick(View v){
            int itemId = mRecords.get(getAdapterPosition()).getId();
            ItemClickListener.onItemClick(itemId);
        }
    }
}
