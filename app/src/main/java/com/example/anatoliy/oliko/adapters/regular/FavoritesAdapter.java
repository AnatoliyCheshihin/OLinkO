package com.example.anatoliy.oliko.adapters.regular;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.anatoliy.oliko.models.Link;

import java.util.List;

/**
 * Created by anatoliy on 20/02/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteRecyclerView>{

    private List<Link> mItemList;

    public FavoritesAdapter(List<Link> itemList) {
        mItemList = itemList;
    }

    @Override
    public FavoriteRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FavoriteRecyclerView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FavoriteRecyclerView extends RecyclerView.ViewHolder{

        public FavoriteRecyclerView(View itemView) {
            super(itemView);
        }
    }
}
