package com.example.anatoliy.oliko.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.activities.MainActivity;
import com.example.anatoliy.oliko.adapters.helpers.DividerItemDecoration;
import com.example.anatoliy.oliko.adapters.regular.HistoryAdapter;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.listeners.OnListItemClickListener;
import com.example.anatoliy.oliko.models.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents list of history items loaded from data base
 *
 * Created by anatoliy on 20/02/16.
 */
public final class HistoryFragment extends BaseFragment implements OnListItemClickListener {

    public static final String TAG = HistoryFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private List<Link> mItemList;

    /**
     * Static factory method for retrieving {@link HistoryFragment}
     *
     * @return {@link HistoryFragment}
     */
    public static HistoryFragment newInstance(){

        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_history_layout, container, false);
        init(view);
        return view;
    }

    /**
     * Prepares UI & adapter for further data representation
     *
     * @param view {@link View} with inflated fragment's layout
     */
    private void init(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvHistoryRecyclerView);
        mItemList = new ArrayList<>();
        mAdapter = new HistoryAdapter(mItemList, HistoryFragment.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadBusinessData();
    }

    private void loadBusinessData(){

        List<Link> list = RealmHelper.getHistoryList();
        if (list != null) {
            mAdapter.updateData(list);
        }
    }

    @Override
    public void onListItemClick(int position) {

        Link link = mItemList.get(position);

        if (link != null && getActivity() != null && !getActivity().isFinishing()) {
            // Open
            ((MainActivity) getActivity()).startActivityFromLink(link);
        }
    }
}
