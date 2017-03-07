package com.example.xyzreader.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";
    private Unbinder unbinder;
    private long mItemId;

    /**
     * Bind the views
     */
    @BindView(R.id.photo)
    ImageView mPhotoView;
    @BindView(R.id.meta_bar)
    LinearLayout metaBar;
    @BindView(R.id.article_title)
    TextView mTitleView;
//    @BindView(R.id.article_author)
//    TextView mAuthorView;
    @BindView(R.id.article_date)
    TextView mDateView;
    @BindView(R.id.article_body)
    TextView mBodyView;
    @BindView(R.id.fab)
    FloatingActionButton mShareFab;
    @Nullable
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @Nullable
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Nullable
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;


    public ArticleDetailFragment() {
    }


    /**
     * Create a new instance of ArticleDetailFragment, providing "itemId"
     * as an argument
     *
     * @param itemId
     * @return The new ArticleDetailFragment
     */

    public static ArticleDetailFragment newInstance(long itemId) {
        ArticleDetailFragment f = new ArticleDetailFragment();

        //  Supply itemId as an argument
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        f.setArguments(arguments);
        return f;
    }


    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        setHasOptionsMenu(true);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * @param i
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    /**
     * @param cursorLoader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, final Cursor cursor) {
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            return;
        }



        final String title = cursor.getString(ArticleLoader.Query.TITLE);


        final String body = Html.fromHtml(cursor.getString(ArticleLoader.Query.BODY)).toString();

        if (mToolbar != null) {

            mToolbar.setTitle(title);
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }




        mTitleView.setText(title);
        mBodyView.setText(body);


        mDateView.setText(Html.fromHtml(
                DateUtils.getRelativeTimeSpanString(
                        cursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString()
                        + " by <font color='#000000'>"
                        + cursor.getString(ArticleLoader.Query.AUTHOR)
                        + "</font>"));


        Target target = new Target() {
            /**
             *
             * @param bitmap
             * @param from
             */
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                mPhotoView.setImageBitmap(bitmap);
                Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                                      /**
                                       *
                                       * @param palette
                                       */
                                      @Override
                                      public void onGenerated(Palette palette) {
                                          Palette.Swatch textSwatch = palette.getLightMutedSwatch();
                                          if (textSwatch == null) {
                                              return;
                                          }

                                          metaBar.setBackgroundColor(textSwatch.getRgb());

                                          mTitleView.setTextColor(textSwatch.getTitleTextColor());

                                      }
                                  }
                        );
            }

            /**
             *
             * @param errorDrawable
             */
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            /**
             *
             * @param placeHolderDrawable
             */
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(this.getContext()).load(cursor.getString(ArticleLoader.Query.PHOTO_URL)).into(target);

        mShareFab.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(body)
                        .getIntent(), getString(R.string.action_share)));
            }
        });
    }

    /**
     * @param cursorLoader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    /**
     *
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
