package com.example.anatoliy.oliko.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.utils.Prefs;

/**
 * Created by anatoliy on 27/02/16.
 */
public class UserProfileFragment extends BaseFragment {

    public static final String TAG = UserProfileFragment.class.getSimpleName();

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mPhone;
    private TextView mCountry;
    private TextView mLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_profile_layout, container,false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {

        mFirstName = (TextView) view.findViewById(R.id.tvUserFirstNameValue);
        mLastName = (TextView) view.findViewById(R.id.tvUserLastNameValue);
        mEmail = (TextView) view.findViewById(R.id.tvUserProfileEmailValue);
        mPhone = (TextView) view.findViewById(R.id.tvUserProfilePhoneValue);
        mCountry = (TextView) view.findViewById(R.id.tvUserProfileCountryValue);
        mLanguage = (TextView) view.findViewById(R.id.tvUserProfileLanguageValue);

        mFirstName.setText(Prefs.getFirstName());
        mLastName.setText(Prefs.getLastName());
        mEmail.setText(Prefs.getEmail());
        mPhone.setText(Prefs.getPhone());
        mCountry.setText(Prefs.getCountry());
        mLanguage.setText(Prefs.getLanguage());
    }
}
