package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.listeners.RegistrationCallbacks;
import com.example.anatoliy.oliko.utils.Prefs;

/**
 * Created by anatoliy on 27/02/16.
 */
public class SignInFragment extends BaseFragment implements View.OnClickListener{

    public static final String TAG = SignInFragment.class.getSimpleName();

    public TextView mSkip;
    public EditText mEmail;
    public EditText mPassword;
    public TextView mNotRegistered;
    public TextView mSignIn;

    private RegistrationCallbacks mListener;

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
        View view = inflater.inflate(R.layout.fragment_sign_in_layout, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        mSkip = (TextView) view.findViewById(R.id.tvSignInSkip);
        mEmail = (EditText) view.findViewById(R.id.etSignInEmail);
        mPassword = (EditText) view.findViewById(R.id.etSignInPassword);
        mNotRegistered = (TextView) view.findViewById(R.id.tvSignInNotRegistered);
        mSignIn = (TextView) view.findViewById(R.id.tvSignInSignIn);

        mSignIn.setOnClickListener(this);
        mSkip.setOnClickListener(this);
        mNotRegistered.setOnClickListener(this);
    }

    private boolean isCredentialsValid(){

        if (TextUtils.isEmpty(mEmail.getText().toString())){

            mEmail.setError("Email is missing");
            return false;
        }

        String email = Prefs.getEmail();

        if (email.compareToIgnoreCase(mEmail.getText().toString()) != 0){
            mEmail.setError("Email is incorrect");
            return false;
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())){

            mPassword.setError("Password is missing");
            return false;
        }

        String password = Prefs.getPassword();
        if (password.compareToIgnoreCase(mPassword.getText().toString()) != 0){
            mPassword.setError("Password is incorrect");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvSignInNotRegistered:
                if (mListener != null){
                    mListener.onNotRegisteredClicked();
                }
                break;

            case R.id.tvSignInSignIn:

                if (isCredentialsValid()){
                    if (mListener != null){
                        mListener.onSignInClicked();
                    }
                }
                break;

            case R.id.tvSignInSkip:
                if (mListener != null){
                    mListener.onSkipClicked();
                }
                break;

            default:
                // Do nothing
        }
    }
}