package com.example.anatoliy.oliko.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.activities.ChatActivity;
import com.example.anatoliy.oliko.activities.MainActivity;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.models.Link;

import java.util.List;
import java.util.Set;

/**
 * Created by anatoliy on 17/02/16.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int AUTO_COMPLETE_CHAR_THRESHOLD = 1;

    private AutoCompleteTextView mAutoCompleteInput;
    private TextView mGo;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvGo:

                startActivity(new Intent(getActivity(), ChatActivity.class));
                if (true){
                    return;
                }

                if (isInputSetCorrectly()) { // Validate fields

                    Link link = RealmHelper.getLinkByKey(mAutoCompleteInput.getText().toString());

                    if (link != null){ // Check if such link was found
                        proceedViaLink(link);
                    } else {
                        showSnackBar(getString(R.string.link_not_found_error_message));
                    }
                }
                break;
            default:
                // Do nothing
        }
    }

    private void proceedViaLink(@NonNull Link link) {

        ((MainActivity) getActivity()).startActivityFromLink(link);
    }
}
