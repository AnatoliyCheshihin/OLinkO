package com.example.anatoliy.oliko.adapters.regular;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.models.chat.ChatMessage;

import java.util.List;

/**
 *
 * Adapts model data of user/owner messages to layouts that will be popualted later within
 * {@link RecyclerView}, (partially implemented)
 *
 * Created by anatoliy on 16/07/2016.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_USER = 0;
    private static final int VIEW_TYPE_OWNER = 1;

    private List<ChatMessage> mDataList;

    public ChatAdapter(List<ChatMessage> dataList) {
        mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        switch (viewType){
            case VIEW_TYPE_USER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_adapter_user_item_layout, parent, false);
                return new UserPostItemViewHolder(view);
            case VIEW_TYPE_OWNER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_adapter_owner_item_layout, parent, false);
                return new OwnerPostItemViewHolder(view);
            default:
                throw new IllegalStateException("Found unknown message type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatMessage item = getListItem(position);

        switch (item.getBitmapResource()){

            case ChatMessage.NO_IMAGE: // Text
                if (holder instanceof UserPostItemViewHolder){
                    ((UserPostItemViewHolder) holder).mUserMessage.setText(item.getMessage());
                } else if (holder instanceof OwnerPostItemViewHolder){
                    ((OwnerPostItemViewHolder) holder).mOwnerMessage.setText(item.getMessage());
                } else {
                    throw new IllegalArgumentException("Unknown holder view type");
                }
                break;

            default: // Image
                if (holder instanceof UserPostItemViewHolder){
                    ((UserPostItemViewHolder) holder).mUserImage.setImageResource(item.getBitmapResource());
                } else if (holder instanceof OwnerPostItemViewHolder){
                    ((OwnerPostItemViewHolder) holder).mOwnerImage.setImageResource(item.getBitmapResource());
                } else {
                    throw new IllegalArgumentException("Unknown holder view type");
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {

        ChatMessage item = getListItem(position);
        switch (item.getMessageType()){
            case OWNER:
                return VIEW_TYPE_OWNER;
            case USER:
                return VIEW_TYPE_USER;
            default:
                throw new IllegalStateException("Found unknown message type");

        }
    }

    public void addItem(ChatMessage item){
        mDataList.add(item);
        notifyItemInserted(mDataList.size()-1);
    }

    private ChatMessage getListItem(final int position){

        return mDataList.get(position);
    }


    protected static class OwnerPostItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mOwnerImage;
        private TextView mOwnerMessage;

        public OwnerPostItemViewHolder(View itemView) {
            super(itemView);
            mOwnerMessage = (TextView) itemView.findViewById(R.id.tvChatOwnerPostMessage);
            mOwnerImage = (ImageView) itemView.findViewById(R.id.ivChatOwnerPostImage);
        }
    }

    protected static class UserPostItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mUserImage;
        private TextView mUserMessage;

        public UserPostItemViewHolder(View itemView) {
            super(itemView);
            mUserMessage = (TextView) itemView.findViewById(R.id.tvChatUserPostMessage);
            mUserImage = (ImageView) itemView.findViewById(R.id.ivChatUserPostImage);
        }
    }
}
