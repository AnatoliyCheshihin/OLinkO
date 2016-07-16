package com.example.anatoliy.oliko.adapters.regular;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.listeners.OnListItemClickListener;
import com.example.anatoliy.oliko.models.Link;
import com.example.anatoliy.oliko.utils.DateHelper;

import java.util.List;

/**
 * Adapter, creates views for history items and binds related data accordingly
 *
 * Created by anatoliy on 20/02/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private List<Link> mItemList;
    private OnListItemClickListener mListener;

    /**
     * Adapter's constructor, retrieves as arguments list with data and optional listener
     * for intercepting click events
     *
     * @param itemList list with {@link Link} objects
     * @param listener {@link OnListItemClickListener} for retrieving click events within list
     */
    public HistoryAdapter(@NonNull List<Link> itemList, OnListItemClickListener listener) {

        mItemList = itemList;
        if (listener != null) {
            mListener = listener;
        }
    }

    /**
     * Replaces existing content of adapter to updated, based on provided data
     *
     * @param updatedData List with {@link Link} objects that should replace existing
     */
    public void updateData(@NonNull List<Link> updatedData){

        // Remove all data
        clearList();

        // Add new data
        final int listCount = updatedData.size();
        for (int i=0; i<listCount; i++){
            addItem(i, updatedData.get(i));
        }
    }

    /**
     * Creates view for history item
     *
     * @param parent hosting group view
     * @param viewType item's view type
     * @return {@link HistoryViewHolder}
     */
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);
        return new HistoryViewHolder(view);
    }

    /**
     * Binds data from history item to view
     *
     * @param holder {@link HistoryViewHolder}
     * @param position int, item position within list
     */
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        Link item = getListItem(position);

        holder.link.setText(item.getKey().toUpperCase());
        holder.linkValue.setText(item.getValue());
        holder.visitTime.setText(DateHelper.shortDateHourFromDate(item.getLastTimeUsedDate()));

        if (item.isFavorite()){
            holder.favoriteIcon.setImageResource(android.R.drawable.star_big_on);
        } else {
            holder.favoriteIcon.setImageResource(android.R.drawable.star_big_off);
        }
    }

    /**
     * Returns list size
     *
     * @return int, list count
     */
    @Override
    public int getItemCount() {

        return mItemList.size();
    }

    private void clearList(){

        final int listCount = mItemList.size();
        for (int i = listCount-1; i>=0; i--){
            removeItem(i);
        }
    }

    private void addItem(final int position, Link item){

        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    private void removeItem(final int position){

        mItemList.remove(position);
        notifyItemRemoved(position);
    }

    private Link getListItem(final int position){

        return mItemList.get(position);
    }

    // --------------------------------------------------------------------------------------------
    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView link;
        TextView linkValue;
        TextView visitTime;
        ImageView favoriteIcon;

        HistoryViewHolder(View itemView) {
            super(itemView);

            link = (TextView) itemView.findViewById(R.id.tvHistoryListItemLink);
            linkValue = (TextView) itemView.findViewById(R.id.tvHistoryListItemLinkValue);
            visitTime = (TextView) itemView.findViewById(R.id.tvHistoryListItemDate);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.ivHistoryListItemFavorite);

            // Specify listener
            itemView.setOnClickListener(HistoryViewHolder.this);
        }

        @Override
        public void onClick(View v) {

            if (mListener != null){

                final int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION){

                    mListener.onListItemClick(position);
                    Log.i(TAG, "Item " + position + " clicked");
                }
            }
        }
    }
}
