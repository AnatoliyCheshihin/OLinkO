package com.example.anatoliy.oliko.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.models.Link;
import com.example.anatoliy.oliko.utils.Utils;

import java.util.Date;

/**
 * Created by anatoliy on 17/02/16.
 */
public class EditorFragment extends BaseFragment implements View.OnClickListener{

    private EditText mKey;
    private EditText mValue;
    private TextView mSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_editor_layout, container,false);
        mKey = (EditText) view.findViewById(R.id.etEditorInputKey);
        mValue = (EditText) view.findViewById(R.id.etEditorInputValue);

        mKey.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mKey.getText().length() > 0 && mValue.getText().length() > 0){
                    enableSaveButton();
                } else {
                    disableSaveButton();
                }
            }
        });

        mValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mKey.getText().length() > 0 && mValue.getText().length() > 0){
                    enableSaveButton();
                } else {
                    disableSaveButton();
                }
            }
        });

        mSave = (TextView) view.findViewById(R.id.tvEditorSave);
        mSave.setOnClickListener(this);
        disableSaveButton();
        return view;
    }


    private void enableSaveButton(){

        if (mSave != null && !mSave.isEnabled()){
            mSave.setEnabled(true);
        }
    }

    private void disableSaveButton(){

        if (mSave != null && mSave.isEnabled()){
            mSave.setEnabled(false);
        }
    }

    /**
     *
     * Makes sure that user entered correctly all required data. If not shows error message
     *
     * @return true|false
     */
    private boolean isInputSetCorrectly(){

        String key = mKey.getText().toString();

        if (TextUtils.isEmpty(key)){ // Oops, keyword is missing
            mKey.setError(getString(R.string.keyword_is_missing));
            return false;
        }

        String value = mValue.getText().toString();

        if (TextUtils.isEmpty(value)){ // Value is missing
            mValue.setError(getString(R.string.link_is_missing));
            return false;
        }

        return true;
    }

    private void saveInput() {

        String key = mKey.getText().toString();
        String value = mValue.getText().toString();

        boolean phoneNumber = Utils.isValidPhoneNumber(value);

        // Apply url prefix for proper redirecting to web sites
        if (!phoneNumber){
            if (!value.startsWith("http://") && !value.startsWith("https://")){
                value = "http://" + value;
            }
        }

        // Create link object
        Link link = new Link();
        link.setKey(key);
        link.setValue(value);
        link.setCreationDate(new Date(System.currentTimeMillis()));
        // link.setPhoneNumber(phoneNumber);
        RealmHelper.addLink(link);

        if (isAdded()){
            showSnackBar("Link was added");
        }

        clearInputFields();
        enableSaveButton();
    }

    private void clearInputFields(){
        if (mKey != null){
            mKey.setText("");
        }
        if (mValue != null){
            mValue.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tvEditorSave:
                if (isInputSetCorrectly()) {
                    disableSaveButton();
                    saveInput();
                }
                break;

            default:
                // Do nothing
        }
    }
}
