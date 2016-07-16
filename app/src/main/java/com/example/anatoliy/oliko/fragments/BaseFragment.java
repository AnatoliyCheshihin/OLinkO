package com.example.anatoliy.oliko.fragments;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.example.anatoliy.oliko.activities.BaseActivity;
import com.example.anatoliy.oliko.activities.MainActivity;

/**
 * Created by anatoliy on 20/02/16.
 */
public class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    /**
     * Call this method to show SnackBar within {@link MainActivity}
     *
     * @param text
     */
    public void showSnackBar(@NonNull String text){

        showSnackBar(text, Snackbar.LENGTH_SHORT);
    }

    /**
     * Call this method to show SnackBar within {@link MainActivity}
     *
     * @param text content of SnackBar to show
     * @param length e.g. {@link Snackbar#LENGTH_LONG}
     */
    public void showSnackBar(@NonNull String text, int length){

        if (!isAdded() || !(getActivity() instanceof MainActivity)){
            throw new IllegalStateException("Fragment is not added at all, or host activity is not"
                    + MainActivity.class.getSimpleName() + " activity");
        }
        ((MainActivity) getActivity()).showSnackBarMessage(text, Snackbar.LENGTH_SHORT);
    }
}
