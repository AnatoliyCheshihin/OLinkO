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
import com.example.anatoliy.oliko.models.ChatList;
import com.example.anatoliy.oliko.utils.DateHelper;

import java.util.List;

/**
 * Adapter, creates views for history items and binds related data accordingly
 *
 * Created by anatoliy on 20/02/16.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    private static final String TAG = ChatListAdapter.class.getSimpleName();

    private List<ChatList> mItemList;
    private OnListItemClickListener mListener;

    /**
     * Adapter's constructor, retrieves as arguments list with data and optional listener
     * for intercepting click events
     *
     * @param itemList list with {@link ChatList} objects
     * @param listener {@link OnListItemClickListener} for retrieving click events within list
     */
    public ChatListAdapter(@NonNull List<ChatList> itemList, OnListItemClickListener listener) {

        mItemList = itemList;
        if (listener != null) {
            mListener = listener;
        }
    }

    /**
     * Replaces existing content of adapter to updated, based on provided data
     *
     * @param updatedData List with {@link ChatList} objects that should replace existing
     */
    public void updateData(@NonNull List<ChatList> updatedData){

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
     * @return {@link ChatListViewHolder}
     */
    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item, parent, false);
        return new ChatListViewHolder(view);
    }

    /**
     * Binds data from history item to view
     *
     * @param holder {@link ChatListViewHolder}
     * @param position int, item position within list
     */
    @Override
    public void onBindViewHolder(ChatListViewHolder holder, int position) {

        ChatList item = getListItem(position);

        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.timestamp.setText(DateHelper.shortDateHourFromDate(item.getLastTime()));
        holder.badge.setText(String.valueOf(item.getMessageCount()));
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

    private void addItem(final int position, ChatList item){

        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    private void removeItem(final int position){

        mItemList.remove(position);
        notifyItemRemoved(position);
    }

    private ChatList getListItem(final int position){

        return mItemList.get(position);
    }

    // --------------------------------------------------------------------------------------------
    class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subtitle;
        TextView timestamp;
        TextView badge;
        ImageView chatIcon;

        ChatListViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvChatListItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.tvChatListItemSubtitle);
            timestamp = (TextView) itemView.findViewById(R.id.tvChatListItemTimestamp);
            badge = (TextView) itemView.findViewById(R.id.tvChatListItemCount);
            chatIcon = (ImageView) itemView.findViewById(R.id.ivChatListItemIcon);

            // Specify listener
            itemView.setOnClickListener(ChatListViewHolder.this);
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
