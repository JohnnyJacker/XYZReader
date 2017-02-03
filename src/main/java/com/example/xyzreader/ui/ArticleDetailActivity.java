package com.example.xyzreader.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public final static String EXTRA_CURRENT_ID = "EXTRA_CURRENT_ID";
    private Cursor mCursor;
    private long mCurrentId;
    private MyPagerAdapter mPagerAdapter;

    /**
     * Bind the pager
     */
    @BindView(R.id.pager)
    ViewPager mPager;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        ButterKnife.bind(this);


        //  If the savedInstanceState is null
        if (savedInstanceState == null) {
            //  If getIntent is not null and getIntent().getData is not null
            if (getIntent() != null && getIntent().getData() != null) {
                //  Get the current id
                mCurrentId = ItemsContract.Items.getItemId(getIntent().getData());
            }
        } else {
            //  If the savedInstanceState was not null get the current id
            mCurrentId = savedInstanceState.getLong(EXTRA_CURRENT_ID);
        }

        //  Get a reference to MyPagerAdapter
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //  setAdapter on ViewPager with MyPagerAdapter
        mPager.setAdapter(mPagerAdapter);
        //  Listen for a change
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            /**
             *
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                //  Do nothing
            }

            /**
             * Checks if the cursor is not null then moves to the position selected
             *
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                if (mCursor != null) {
                    mCursor.moveToPosition(position);
                }
            }
        });

        //  Ensures a loader is initialized and active.  If the loader doesn't already exist, one
        //  is created and (if the activity/fragment is currently started) starts the loader.
        //  Otherwise the last created loader is re-used
        // https://developer.android.com/reference/android/support/v4/app/LoaderManager.html

        getSupportLoaderManager().initLoader(0, null, this);
    }


    /**
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXTRA_CURRENT_ID, mCurrentId);
    }

    /**
     * @param i
     * @param bundle
     * @return All Articles
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    /**
     * @param cursorLoader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        //  If the current id is greater than zero
        if (mCurrentId > 0) {
            //  Move to the first position in the cursor
            cursor.moveToFirst();
            //  While there is not an item after the one selected
            while (!cursor.isAfterLast()) {
                //  If the current id is equal to the query id
                if (cursor.getLong(ArticleLoader.Query._ID) == mCurrentId) {
                    final int position = cursor.getPosition();
                    mCursor = cursor;
                    mPagerAdapter.notifyDataSetChanged();
                    mPager.setCurrentItem(position, false);
                    break;
                }
                //  Keep moving to the next position as long as there is one to go to
                cursor.moveToNext();
            }
        }
    }

    /**
     * @param cursorLoader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        mPagerAdapter.notifyDataSetChanged();
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        /**
         * @param fm
         */
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * @param position
         * @return New Instance of ArticleDetailFragment using the cursors id
         */
        @Override
        public Fragment getItem(int position) {
            mCursor.moveToPosition(position);
            return ArticleDetailFragment.newInstance(mCursor.getLong(ArticleLoader.Query._ID));
        }

        /**
         * @return conditional operator
         * If it is true that the cursor is not null return the count of the cursor
         * If it is false that the cursor is not null return zero
         */
        @Override
        public int getCount() {

            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
