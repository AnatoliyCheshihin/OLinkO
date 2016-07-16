package com.example.anatoliy.oliko.app;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.models.Link;
import com.example.anatoliy.oliko.utils.Prefs;
import com.example.anatoliy.oliko.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by anatoliy on 18/02/16.
 */
public class PointerPlusApplication extends Application {

    public static final String TAG = PointerPlusApplication.class.getSimpleName();

    private static volatile PointerPlusApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = PointerPlusApplication.this;
        initSingletons();

        // TODO : Delete prior to releasing
        insertPresetPointers();
    }

    private void insertPresetPointers() {

        boolean inserted = false;

        Resources resources = getResources();

        if (resources != null){

            String[] keyArray = resources.getStringArray(R.array.default_pointer_key_array);
            String[] valueArray = resources.getStringArray(R.array.default_pointer_value_array);

            for (int i=0; i<keyArray.length; i++){

                // Get instance of link for key (if exists)
                Link link = RealmHelper.getLinkByKey(keyArray[i]);

                if (link != null){
                    // Link exists - continue to next iteration
                    Log.i(TAG, "Iteration for: " + keyArray[i] + " skipped (already exists)");
                    continue;
                }

                link = new Link();
                link.setKey(keyArray[i]);
                link.setValue(valueArray[i]);
                link.setCreationDate(new Date(System.currentTimeMillis()));
                // Add link to database
                RealmHelper.addLink(link);
                // Store in history
                RealmHelper.updateLinkHistory(link, new Date(System.currentTimeMillis()));

                if (!inserted){
                    inserted = true;
                }

                Log.i(TAG, "Added new pointer [" + keyArray[i] + "," + valueArray[i]+"]");
            }
        }
    }

    private void initSingletons() {
        // Init shared prefs
        Prefs.init(getInstance());
        // Init realm db
        RealmHelper.init(getInstance());
    }

    public static PointerPlusApplication getInstance(){

        if (sInstance != null){
            return sInstance;
        } else {
            throw new IllegalStateException("Application instance was not initialized");
        }
    }
}
