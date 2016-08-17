package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.adapters.helpers.DividerItemDecoration;
import com.example.anatoliy.oliko.adapters.regular.ChatListAdapter;
import com.example.anatoliy.oliko.adapters.regular.HistoryAdapter;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.listeners.MainFragmentClickListener;
import com.example.anatoliy.oliko.listeners.OnListItemClickListener;
import com.example.anatoliy.oliko.models.Ads;
import com.example.anatoliy.oliko.models.ChatList;
import com.example.anatoliy.oliko.models.Link;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;

/**
 *
 * Created by anatoliy on 17/02/16.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener, OnListItemClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int AUTO_COMPLETE_CHAR_THRESHOLD = 1;

    private AutoCompleteTextView mAutoCompleteInput;
    private TextView mGo;

    private MainFragmentClickListener mListener;
    private ChatListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MainFragmentClickListener)){
            throw new IllegalStateException("Host activity must implement "
                    + MainFragmentClickListener.class.getSimpleName());
        }

        mListener = (MainFragmentClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new ChatListAdapter(buildChatListArray(), MainFragment.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initUi(View view) {

        mAutoCompleteInput=(AutoCompleteTextView) view.findViewById(R.id.actvMainAutoCompleteInput);
        // Start autocomplete search after typed by user x chars
        mAutoCompleteInput.setThreshold(AUTO_COMPLETE_CHAR_THRESHOLD);
        List<String> pointerKeySet = RealmHelper.getKeySet();
        LinkedList<String> possibleCases = new LinkedList<>();
        possibleCases.addAll(pointerKeySet);
        for(String s : ChatFragment.possibleCases)
            possibleCases.add(s);
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, possibleCases);
        mAutoCompleteInput.setAdapter(autoCompleteAdapter);
        mAutoCompleteInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAutoCompleteInput.getText().length() > 0) {
                    enableGoButton();
                } else {
                    disableGoButton();
                }
            }
        });

        mGo = (TextView) view.findViewById(R.id.tvGo);
        mGo.setOnClickListener(this);
        disableGoButton();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvChatList);
        mAdapter = new ChatListAdapter(buildChatListArray(), MainFragment.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Object> buildChatListArray() {
        ArrayList<Object> list = new ArrayList<>();
        if(RealmHelper.chatList.size()==0){
            Ads ads = new Ads();
            ads.setBitmapResourceID(R.drawable.adv1);
            list.add(ads);
        } else if(RealmHelper.chatList.size()==1){
            list.addAll(RealmHelper.chatList);
            Ads ads = new Ads();
            ads.setBitmapResourceID(R.drawable.adv2);
            list.add(ads);
        } else {
            list.addAll(RealmHelper.chatList);
            Ads ads = new Ads();
            ads.setBitmapResourceID(R.drawable.adv3);
            list.add(1,ads);
        }
        return list;
    }

    private boolean isInputSetCorrectly(){

        if (TextUtils.isEmpty(mAutoCompleteInput.getText())){
            mAutoCompleteInput.setError(getString(R.string.keyword_is_missing));
            return false;
        }
        return true;
    }

    private void enableGoButton(){

        if (mGo != null && !mGo.isEnabled()){
            mGo.setEnabled(true);
        }
    }

    private void disableGoButton(){

        if (mGo != null && mGo.isEnabled()){
            mGo.setEnabled(false);
        }
    }

    // TODO: delete this after implementation of chat activity instead of normal flow that was used previously
    private boolean testChatActivity(){

        String word = mAutoCompleteInput.getText().toString();

        String firstMatch = null;
        int match_len = 0;

        for(String caseWord : ChatFragment.possibleCases){
            if(caseWord.equalsIgnoreCase(word)){
                firstMatch = word;
                break;
            }
            while(caseWord.length() > match_len && word.length() > match_len
                    && caseWord.substring(0, match_len+1).equalsIgnoreCase(word.substring(0, match_len+1))){
                match_len += 1;
                firstMatch = caseWord;
            }
        }

        if(firstMatch!=null) {
            mListener.onChatActivityStartRequest(firstMatch);
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvGo:

                // TODO: normal flow is changed for {@link com.example.anatoliy.oliko.activities.ChatActivity}
                // test purposes, this part of code should be reimplemented after complete implementation
                // of activity in order to suit client's requirement
                if (testChatActivity()){
                    return;
                }

                if (isInputSetCorrectly()) { // Validate fields

                    Link link = RealmHelper.getLinkByKey(mAutoCompleteInput.getText().toString());

                    if (link != null){ // Check if such link was found
                        mListener.onProceedViaLinkRequest(link);
                    } else {
                        showSnackBar(getString(R.string.link_not_found_error_message));
                    }
                }
                break;
            default:
                // Do nothing
                break;
        }
    }

    @Override
    public void onListItemClick(int position) {
        if(position>=0 && position<RealmHelper.chatList.size()) {
            ChatList chat = RealmHelper.chatList.get(position);
            mListener.onChatActivityStartRequest(chat.getMessage());
        }
    }
}
