package tn.droidcon.testprovider.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tn.droidcon.testprovider.R;
import tn.droidcon.testprovider.wrapper.ListItemWrapper;


public class CustomAdapter extends
        RecyclerView.Adapter<CustomAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<ListItemWrapper> mObjectsList = new ArrayList<ListItemWrapper>();
    private OnItemClickedListener mItemClickedListener;

    private CustomAdapter(){
        //hide default constructor
    }

    public CustomAdapter(ArrayList<ListItemWrapper> itemsList, OnItemClickedListener listener) {

        mObjectsList = itemsList;
        mItemClickedListener = listener;
    }

    @Override
    public int getItemCount() {

        return mObjectsList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_item_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ... Nothing to do
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mPosition = position;
        holder.mItemTitle.setText(mObjectsList.get(position).getTitle());
        holder.mItemDescription.setText(mObjectsList.get(position).getDescription());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(holder);

    }

    @Override
    public void onClick(View v) {
        mItemClickedListener.onItemClicked(((ViewHolder)v.getTag()).mPosition);
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int mPosition;
        public TextView mItemTitle;
        public TextView mItemDescription;


        public ViewHolder(View view) {
            super(view);
            mItemTitle = (TextView) view.findViewById(R.id.label);
            mItemDescription = (TextView) view.findViewById(R.id.description);

        }
    }


    public interface OnItemClickedListener {

        public void onItemClicked(int position);


    }

}
