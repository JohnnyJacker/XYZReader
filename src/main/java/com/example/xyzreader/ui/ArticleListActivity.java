package com.example.xyzreader.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.UpdaterService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.card_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MyCardCursorAdapter mCardCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        ButterKnife.bind(this);

        mSwipeRefresh.setOnRefreshListener(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setLogo(R.drawable.logo);


        int columnCount = 2;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);



        mCardCursorAdapter = new MyCardCursorAdapter(null);

        mCardCursorAdapter.setHasStableIds(true);

        mRecyclerView.setAdapter(mCardCursorAdapter);


        getSupportLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            startService(new Intent(this, UpdaterService.class));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onRefresh();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver,
                new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }




    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                boolean isRefreshing = intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING, false);
                mSwipeRefresh.setRefreshing(isRefreshing);
            }
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
       mCardCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCardCursorAdapter.swapCursor(null);
    }


    @Override
    public void onRefresh() {

        startService(new Intent(this, UpdaterService.class));

    }


}
