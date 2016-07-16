package com.example.anatoliy.oliko.models;

/**
 * Created by anatoliy on 27/02/16.
 */
public class UserProfile {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPhone;
    private String mCountry;
    private String mLanguage;

    public class Builder {

        private String mFirstName;
        private String mLastName;
        private String mEmail;
        private String mPhone;
        private String mCountry;
        private String mLanguage;

        public Builder firstName(String firstName){

            mFirstName = firstName;
            return Builder.this;
        }

        public Builder lastName(String lastName){

            mLastName = lastName;
            return Builder.this;
        }

        public Builder email(String email){

            mEmail = email;
            return Builder.this;
        }

        public Builder phone(String phone){

            mPhone = phone;
            return Builder.this;
        }

        public Builder country(String country){

            mCountry = country;
            return Builder.this;
        }

        public Builder language(String language){

            mLanguage = language;
            return Builder.this;
        }

        public UserProfile build(){

            return new UserProfile(this);
        }
    }

    /**
     * Constructor
     *
     * @param builder
     */
    public UserProfile(Builder builder){

        mFirstName = builder.mFirstName;
        mLastName = builder.mLastName;
        mEmail = builder.mEmail;
        mPhone = builder.mPhone;
        mCountry = builder.mCountry;
        mLanguage = builder.mLanguage;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }
}
