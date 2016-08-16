package com.example.anatoliy.oliko.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.anatoliy.oliko.models.ChatList;
import com.example.anatoliy.oliko.models.Link;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by anatoliy on 17/02/16.
 */
public final class RealmHelper {

    private static final String TAG = RealmHelper.class.getSimpleName();

    private static Realm sInstance;

    /**
     * Initializes instance of {@link Realm} database
     *
     * @param context context for database first initialization
     */
    public static void init(Context context){
        if (sInstance == null){
            sInstance = Realm.getInstance(context);
        }
    }

    private static Realm getInstance(){

        if (sInstance != null){
            return sInstance;
        }
        throw new IllegalStateException("Database was not initialized, run init first");
    }

    public static void addLink(Link link){

        if (link != null
                && !TextUtils.isEmpty(link.getValue())
                && !TextUtils.isEmpty(link.getKey())){

            getInstance().beginTransaction();
            getInstance().copyToRealmOrUpdate(link);
            getInstance().commitTransaction();
        }
    }

    public static Link getLinkByKey(String key){

        RealmQuery<Link> query = getInstance().where(Link.class).equalTo(Tables.Link.KEY, key);
        return query.findFirst();
    }

    /**
     * Returns list of stored pointer keys within db (or empty list)
     *
     * @return List filled with {@link Link} or empty
     */
    public static List<String> getKeySet(){

        // Prepare query
        RealmQuery<Link> query = getInstance().where(Link.class);
        // Query DB and get results
        RealmResults<Link> results = query.findAll();

        if (results != null && results.size() > 0){
            // Sort result by key name in ascending alphabetical order (A -> B)
            results.sort(Tables.Link.KEY, Sort.ASCENDING);
        }

        // Prepare key set in oder to return later as result
        List<String> keyList = new ArrayList<String>();

        if (results != null){
            for (Link each : results){
                keyList.add(each.getKey());
            }
        }

        return keyList;
    }

    /**
     * Returns list with all {@link Link} items stored within DB (or empty list)
     *
     * @return List filled with {@link Link} or empty
     */
    public static List<Link> getLinkList(){

        RealmQuery<Link> query = getInstance().where(Link.class);
        RealmResults<Link> results = query.findAll();
        if (results != null){
            return  results;
        }
        else {
            return new ArrayList<Link>();
        }
    }

    public static List<Link> getHistoryList(){

        RealmQuery<Link> query = getInstance()
                .where(Link.class).isNotNull(Tables.Link.LAST_TIME_USED_DATE);
        // Get history of all items
        RealmResults<Link> results = query.findAll();
        if (results != null && results.size() > 0) {
            // Sort result by recency
            results.sort(Tables.Link.LAST_TIME_USED_DATE, Sort.DESCENDING);
        }
        return results;
    }

    /**
     * Updates {@code Link} last used time (Needed for history)
     *
     * @param link
     * @param updatedDate
     */
    public static void updateLinkHistory(Link link, Date updatedDate){

        if (link != null && updatedDate != null){

            Link linkToUpdate = getInstance()
                    .where(Link.class).equalTo(Tables.Link.KEY, link.getKey()).findFirst();

            if (linkToUpdate != null){

                getInstance().beginTransaction();
                linkToUpdate.setLastTimeUsedDate(updatedDate);
                getInstance().commitTransaction();
            }
        }
    }

    /**
     * Sets/Unsets {@code Link} item as favorite
     *
     * @param link
     * @param favorite
     */
    public static void updateLinkFavoriteState(Link link, boolean favorite){

        if (link != null){

            Link linkToUpdate = getInstance()
                    .where(Link.class).equalTo(Tables.Link.KEY, link.getKey()).findFirst();
            if (linkToUpdate != null){
                getInstance().beginTransaction();
                linkToUpdate.setFavorite(favorite);
                getInstance().commitTransaction();
            }
        }
    }

    public static ArrayList<ChatList> chatList = new ArrayList<>();
    public static void addOrCreateChatList(String key, int icon, String msg, String title, String subtitle, Date lastModified, int msgCount){
        for(ChatList chat : chatList){
            if(key.equals(chat.getKey())){
                updateChatList(chat, icon, msg, title, subtitle, lastModified, msgCount);
                return;
            }
        }
        ChatList chat = new ChatList();
        chat.setKey(key);
        updateChatList(chat, icon, msg, title, subtitle, lastModified, msgCount);
        chatList.add(chat);
    }
    private static void updateChatList(ChatList chat, int icon, String msg, String title, String subtitle, Date lastModified, int msgCount){
        chat.setTitle(title);
        chat.setMessage(msg);
        chat.setBitmapResourceID(icon);
        chat.setSubtitle(subtitle);
        chat.setLastTime(lastModified);
        chat.setMessageCount(msgCount);
    }
   /* private static void addProduct(Product product){

        if (product != null && product.getName() != null){

            getInstance().beginTransaction();
            getInstance().copyToRealmOrUpdate(product);
            getInstance().commitTransaction();
        }
    }

    public static void addProductList(List<Product> productList){
        if (productList != null && productList.size() > 0){
            getInstance().beginTransaction();
            getInstance().copyToRealmOrUpdate(productList);
            getInstance().commitTransaction();
        }
    }

    private static void addCategory(Category category){

        if (category != null && category.getName() != null && category.getProductList() != null){
            getInstance().beginTransaction();
            getInstance().copyToRealmOrUpdate(category);
            getInstance().commitTransaction();
        }
    }

    public static void addCategoryList(List<Category> categoryList){

        if (categoryList != null && categoryList.size() > 0){
            getInstance().beginTransaction();
            getInstance().copyToRealmOrUpdate(categoryList);
            getInstance().commitTransaction();
        }
    }*/
/*

    public static Product getProductById(int id){

        RealmQuery<Product> query = getInstance().where(Product.class).equalTo(Tables.Product.ID, id);
        return query.findFirst();
    }

    public static RealmResults<Category> getCategoryList(){

        RealmQuery<Category> query = getInstance().where(Category.class);
        return query.findAll();
    }

    public static RealmResults<Product> getProductList(){

        RealmQuery<Product> query = getInstance().where(Product.class);
        return query.findAll();
    }

    public static void loadProductsInWorkerThread(final RealmCallbacks listener){

        getInstance().executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {

                RealmQuery<Product> query = realm.where(Product.class);

                if (getInstance() != null){
                    listener.onDataReady(query.findAll());
                }
            }
        }, new Realm.Transaction.Callback() {

            @Override
            public void onSuccess() {

                // Original Queries and Realm objects are automatically updated.
            }
        });
    }

    public static RealmResults<Product> getProductListByCategoryId(int categoryId){

        RealmQuery<Product> query
                = getInstance().where(Product.class).equalTo(Tables.Product.CATEGORY_ID,categoryId);
        // Return results
        return query.findAll();
    }

    public static void printDatabaseContent(){

        RealmQuery<Category> categoryQuery = getInstance().where(Category.class);
        List<Category> categoryList =  categoryQuery.findAll();

        RealmQuery<Product> productQuery = getInstance().where(Product.class);
        List<Product> productList = productQuery.findAll();

        */
/*for (Category each : categoryList){
            Log.i(TAG, "Category - name: " + each.getName() + ", id: " + each.getId());
            for (Product )
        }*//*

    }
*/

    // --------------------------------------------------------------------------------------------
    interface Table{

    }

    static class Tables {

        class Link implements Table {
            public static final String KEY = "key";
            public static final String VALUE = "value";
            // public static final String IS_PHONE_NUMBER = "isPhoneNumber";
            public static final String CREATION_DATE = "creationDate";
            public static final String LAST_TIME_USED_DATE = "lastTimeUsedDate";
            public static final String FAVORITE = "favorite";
        }

        class ChatHistory implements Table {
            public static final String KEY = "key";
            public static final String TITLE = "title";
            public static final String SUBTITLE = "subtitle";
            public static final String MESSAGECOUNT = "messageCount";
            public static final String CREATION_DATE = "creationDate";
            public static final String LAST_DATE = "lastTime";
        }
    }
}