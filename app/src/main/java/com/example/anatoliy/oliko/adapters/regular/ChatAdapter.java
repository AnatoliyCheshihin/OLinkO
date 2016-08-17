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
    private static final int VIEW_TYPE_SYSTEM = 2;

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
            case VIEW_TYPE_SYSTEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_adapter_system_item_layout, parent, false);
                return new SystemPostItemViewHolder(view);
            default:
                throw new IllegalStateException("Found unknown message type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatMessage item = getListItem(position);

        if (holder instanceof MessageViewHolder) {
            if(((MessageViewHolder) holder).mImage != null) {
                if (item.getBitmap() == null) {
                    ((MessageViewHolder) holder).mImage.setVisibility(View.GONE);
                }else{
                    ((MessageViewHolder) holder).mImage.setVisibility(View.VISIBLE);
                    ((MessageViewHolder) holder).mImage.setImageBitmap(item.getBitmap());
                }
            }
            if(item.getMessage() == null || item.getMessage().length()==0){
                ((MessageViewHolder) holder).mMessage.setVisibility(View.GONE);
            }else {
                ((MessageViewHolder) holder).mMessage.setVisibility(View.VISIBLE);
                ((MessageViewHolder) holder).mMessage.setText(item.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Unknown holder view type");
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
            case SYSTEM:
                return VIEW_TYPE_SYSTEM;
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

    protected static class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mMessage;

        public MessageViewHolder(View itemView, int messageResourceID, int imageResourceID) {
            super(itemView);
            mMessage = (TextView) itemView.findViewById(messageResourceID);
            if(imageResourceID!=0) {
                mImage = (ImageView) itemView.findViewById(imageResourceID);
            }
        }
    }

    protected static class SystemPostItemViewHolder extends MessageViewHolder {

        public SystemPostItemViewHolder(View itemView) {
            super(itemView, R.id.tvChatSystemPostMessage,0);
        }
    }

    protected static class OwnerPostItemViewHolder extends MessageViewHolder {
        public OwnerPostItemViewHolder(View itemView) {
            super(itemView, R.id.tvChatOwnerPostMessage,R.id.ivChatOwnerPostImage);
        }
    }

    protected static class UserPostItemViewHolder extends MessageViewHolder {
        public UserPostItemViewHolder(View itemView) {
            super(itemView,R.id.tvChatUserPostMessage,R.id.ivChatUserPostImage);
        }
    }
}
