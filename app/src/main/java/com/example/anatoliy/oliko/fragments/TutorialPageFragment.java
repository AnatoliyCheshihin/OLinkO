package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.listeners.TutorialCallbacks;

/**
 * Created by anatoliy on 27/02/16.
 */
public class TutorialPageFragment extends BaseFragment {

    public static final String TAG = TutorialPageFragment.class.getSimpleName();

    private static final String PAGE_POSITION_KEY = "page_position_index";

    private int mPagePosition;
    private TextView mPageContent;
    private TextView mContinue;

    private TutorialCallbacks mListener;

    private static String[] sPageContentArray = {"Tutorial Explanation Text #1",
            "Tutorial Explanation Text #2", "Tutorial Explanation Text #3"};

    public static TutorialPageFragment newInstance(int pagePosition){

        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_POSITION_KEY, pagePosition);
        TutorialPageFragment fragment = new TutorialPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (TutorialCallbacks) context;
        } catch (ClassCastException e){
            throw new IllegalStateException("Host activity must implement "
                    + TutorialCallbacks.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mPagePosition = bundle.getInt(PAGE_POSITION_KEY);
        } else {
            throw new IllegalStateException("Argument was not provided");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tutorial_layout, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {

        mPageContent = (TextView) view.findViewById(R.id.tvTutorialFragmentContent);
        mPageContent.setText(sPageContentArray[mPagePosition]);

        if (isLastPagePosition(mPagePosition)){

            mContinue = (TextView) view.findViewById(R.id.tvTutorialFragmentContinue);
            mContinue.setVisibility(View.VISIBLE);
            mContinue.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mListener != null){
                        mListener.onContinueClicked();
                    }
                }
            });
        }
    }

    private static boolean isLastPagePosition(int pagePosition){

        return (sPageContentArray.length-1) == pagePosition;
    }

    public static int getPageCount(){

        return sPageContentArray.length;
    }
}
