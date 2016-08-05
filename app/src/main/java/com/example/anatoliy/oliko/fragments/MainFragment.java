package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.listeners.MainFragmentClickListener;
import com.example.anatoliy.oliko.models.Link;

import java.util.List;

/**
 *
 * Created by anatoliy on 17/02/16.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int AUTO_COMPLETE_CHAR_THRESHOLD = 1;

    private AutoCompleteTextView mAutoCompleteInput;
    private TextView mGo;

    private MainFragmentClickListener mListener;

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

    private void initUi(View view) {

        mAutoCompleteInput=(AutoCompleteTextView) view.findViewById(R.id.actvMainAutoCompleteInput);
        // Start autocomplete search after typed by user x chars
        mAutoCompleteInput.setThreshold(AUTO_COMPLETE_CHAR_THRESHOLD);
        List<String> pointerKeySet = RealmHelper.getKeySet();
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, pointerKeySet);
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

        boolean skipLinkRequest = true;
        mListener.onChatActivityStartRequest();
        return skipLinkRequest;
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
}
