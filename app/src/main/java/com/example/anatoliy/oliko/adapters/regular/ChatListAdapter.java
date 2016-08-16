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
import com.example.anatoliy.oliko.models.Ads;
import com.example.anatoliy.oliko.models.ChatList;
import com.example.anatoliy.oliko.utils.DateHelper;

import java.util.List;

/**
 * Adapter, creates views for history items and binds related data accordingly
 *
 * Created by anatoliy on 20/02/16.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    private static final int CHAT_LIST_ROW_TYPE_CHAT = 0;
    private static final int CHAT_LIST_ROW_TYPE_ADS = 1;

    private static final String TAG = ChatListAdapter.class.getSimpleName();

    private List<Object> mItemList;
    private OnListItemClickListener mListener;

    /**
     * Adapter's constructor, retrieves as arguments list with data and optional listener
     * for intercepting click events
     *
     * @param itemList list with {@link ChatList} objects
     * @param listener {@link OnListItemClickListener} for retrieving click events within list
     */
    public ChatListAdapter(@NonNull List<Object> itemList, OnListItemClickListener listener) {

        mItemList = itemList;
        if (listener != null) {
            mListener = listener;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (getListItem(position) instanceof Ads) ? CHAT_LIST_ROW_TYPE_ADS : CHAT_LIST_ROW_TYPE_CHAT;

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

        if(viewType==CHAT_LIST_ROW_TYPE_CHAT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_list_item, parent, false);
            return new ChatListChatViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_list_ads_item, parent, false);
            return new ChatListAdsViewHolder(view);
        }
    }

    /**
     * Binds data from history item to view
     *
     * @param holderObj {@link ChatListViewHolder}
     * @param position int, item position within list
     */
    @Override
    public void onBindViewHolder(ChatListViewHolder holderObj, int position) {

        Object itemObj = getListItem(position);

        if(itemObj instanceof ChatList){
            ChatList item = (ChatList)itemObj;
            ChatListChatViewHolder holder = (ChatListChatViewHolder)holderObj;
            holder.title.setText(item.getTitle());
            holder.subtitle.setText(item.getSubtitle());
            holder.timestamp.setText(DateHelper.shortDateHourFromDate(item.getLastTime()));
            holder.badge.setText(String.valueOf(item.getMessageCount()));
            if(item.getBitmapResourceID()!=0){
                holder.chatIcon.setImageResource(item.getBitmapResourceID());
            }
        } else if(itemObj instanceof Ads){
            Ads item = (Ads)itemObj;
            ChatListAdsViewHolder holder = (ChatListAdsViewHolder) holderObj;
            holder.adsImage.setImageResource(item.getBitmapResourceID());
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

    public Object getListItem(final int position){

        return mItemList.get(position);
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ChatListViewHolder(View itemView) {
            super(itemView);
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

    // --------------------------------------------------------------------------------------------
    class ChatListChatViewHolder extends ChatListViewHolder {

        TextView title;
        TextView subtitle;
        TextView timestamp;
        TextView badge;
        ImageView chatIcon;

        ChatListChatViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvChatListItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.tvChatListItemSubtitle);
            timestamp = (TextView) itemView.findViewById(R.id.tvChatListItemTimestamp);
            badge = (TextView) itemView.findViewById(R.id.tvChatListItemCount);
            chatIcon = (ImageView) itemView.findViewById(R.id.ivChatListItemIcon);
        }
    }
    // --------------------------------------------------------------------------------------------
    class ChatListAdsViewHolder extends ChatListViewHolder {

        ImageView adsImage;

        ChatListAdsViewHolder(View itemView) {
            super(itemView);

            adsImage = (ImageView) itemView.findViewById(R.id.ivChatListAdsItemBitmap);
        }
    }
}
