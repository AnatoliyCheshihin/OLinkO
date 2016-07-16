package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.listeners.RegistrationCallbacks;
import com.example.anatoliy.oliko.utils.Prefs;

/**
 * Created by anatoliy on 27/02/16.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getSimpleName();

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mCountry;
    private EditText mLanguage;
    private EditText mPassword;
    private ImageView mBackButton;
    private TextView mSignUpButton;

    private RegistrationCallbacks mListener;

    public static SignUpFragment newInstance(){

        return new SignUpFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (RegistrationCallbacks) context;
        } catch (ClassCastException e){
            throw new RuntimeException("Activity must implement "
                    + RegistrationCallbacks.class.getSimpleName());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_up_layout, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {

        mFirstName = (EditText) view.findViewById(R.id.etSignUpFirstNameValue);
        mLastName = (EditText) view.findViewById(R.id.etSignUpLastNameValue);
        mEmail = (EditText) view.findViewById(R.id.etSignUpEmailValue);
        mPhone = (EditText) view.findViewById(R.id.etSignUpPhoneValue);
        mCountry = (EditText) view.findViewById(R.id.etSignUpCountryValue);
        mLanguage = (EditText) view.findViewById(R.id.etSignUpLanguageValue);
        mPassword = (EditText) view.findViewById(R.id.etSignUpPasswordValue);

        mBackButton = (ImageView) view.findViewById(R.id.ivSignUpBackArrow);
        mSignUpButton = (TextView) view.findViewById(R.id.tvSignUpSignUpButton);
        mBackButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    private boolean isRequiredFieldsFilled(){

        if (TextUtils.isEmpty(mFirstName.getText().toString())){
            mFirstName.setError("First name is missing");
            return false;
        }

        if (TextUtils.isEmpty(mLastName.getText().toString())){
            mLastName.setError("Last name is missing");
            return false;
        }

        if (TextUtils.isEmpty(mEmail.getText().toString())){
            mEmail.setError("Email is missing");
            return false;
        }

        if (TextUtils.isEmpty(mPhone.getText().toString())){
            mPhone.setError("Phone is missing");
            return false;
        }

        if (TextUtils.isEmpty(mCountry.getText().toString())){
            mCountry.setError("Country is missing");
            return false;
        }

        if (TextUtils.isEmpty(mLanguage.getText().toString())){
            mLanguage.setError("Language is missing");
            return false;
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())){
            mPassword.setError("Password is missing");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ivSignUpBackArrow:
                if (mListener != null){
                    mListener.onSignUpBackClicked();
                }
                break;

            case R.id.tvSignUpSignUpButton:

                if (isRequiredFieldsFilled()){ // All required data was filled

                    // Store data
                    storeDataIntoPrefs();

                    if (mListener != null){

                        // Proceed further
                        mListener.onRegistrationAccomplished();
                    }
                }
                break;

            default:
        }
    }

    private void storeDataIntoPrefs() {

        Prefs.setFirstName(mFirstName.getText().toString());
        Prefs.setLastName(mLastName.getText().toString());
        Prefs.setEmail(mEmail.getText().toString());
        Prefs.setPhone(mPhone.getText().toString());
        Prefs.setCountry(mCountry.getText().toString());
        Prefs.setLanguage(mLanguage.getText().toString());
        Prefs.setPassword(mPassword.getText().toString());
    }
}
