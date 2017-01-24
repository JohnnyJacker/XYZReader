//package com.example.xyzreader.ui;
//
//import android.app.Fragment;
//import android.app.LoaderManager;
//import android.content.Intent;
//import android.content.Loader;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.Rect;
//import android.graphics.Typeface;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.v4.app.ShareCompat;
//import android.support.v7.graphics.Palette;
//import android.text.Html;
//import android.text.format.DateUtils;
//import android.text.method.LinkMovementMethod;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.ImageLoader;
//import com.example.xyzreader.R;
//import com.example.xyzreader.data.ArticleLoader;
//
//import static com.example.xyzreader.R.styleable.DrawInsetsFrameLayout;
//
///**
// * A fragment representing a single Article detail screen. This fragment is
// * either contained in a {@link ArticleListActivity} in two-pane mode (on
// * tablets) or a {@link ArticleDetailActivity} on handsets.
// */
//public class ArticleDetailFragment extends Fragment implements
//        LoaderManager.LoaderCallbacks<Cursor> {
//    private static final String TAG = "ArticleDetailFragment";
//
//    public static final String ARG_ITEM_ID = "item_id";
//
//
//
//    private long mItemId;
//    private View mRootView;
//
//
//    /**
//     * Mandatory empty constructor for the fragment manager to instantiate the
//     * fragment (e.g. upon screen orientation changes).
//     */
//    public ArticleDetailFragment() {
//    }
//
//    public static ArticleDetailFragment newInstance(long itemId) {
//        Bundle arguments = new Bundle();
//        arguments.putLong(ARG_ITEM_ID, itemId);
//        ArticleDetailFragment fragment = new ArticleDetailFragment();
//        fragment.setArguments(arguments);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    public ArticleDetailActivity getActivityCast() {
//        return (ArticleDetailActivity) getActivity();
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
//        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
//        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
//        // we do this in onActivityCreated.
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
//
//
//        return mRootView;
//    }
//
//
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }
//
//
//}
