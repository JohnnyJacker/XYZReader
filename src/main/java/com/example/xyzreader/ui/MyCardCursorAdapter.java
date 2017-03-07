package com.example.xyzreader.ui;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;
import static com.example.xyzreader.R.id.photo;
import static com.example.xyzreader.R.id.view;


/**
 * Created by skyfishjy on 10/31/14.
 */


public class MyCardCursorAdapter extends CursorRecyclerViewAdapter<MyCardCursorAdapter.ViewHolder> {

    /**
     * @param cursor
     */
    public MyCardCursorAdapter(Cursor cursor) {
        super(cursor);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.article_thumbnail)
        DynamicHeightImageView mImageView;

        @BindView(R.id.article_title)
        TextView mTitle;

        @BindView(R.id.article_subtitle)
        TextView mSubTitle;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);
        final ViewHolder vh = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))));


            }
        });

        return vh;
    }

    /**
     * @param viewHolder
     * @param cursor
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        viewHolder.mTitle.setText(cursor.getString(ArticleLoader.Query.TITLE));


        viewHolder.mSubTitle.setText(cursor.getString(ArticleLoader.Query.AUTHOR));




        Target target = new Target() {
            /**
             *
             * @param bitmap
             * @param from
             */
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                viewHolder.mImageView.setAspectRatio(cursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));
                viewHolder.mImageView.setImageBitmap(bitmap);


                viewHolder.mImageView.setImageBitmap(bitmap);
                Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                                      /**
                                       *
                                       * @param palette
                                       */
                                      @Override
                                      public void onGenerated(Palette palette) {
                                          Palette.Swatch textSwatch = palette.getLightMutedSwatch();

                                          if (textSwatch != null) {
                                              viewHolder.itemView.setBackgroundColor(textSwatch.getRgb());
                                          }



                                          if (textSwatch == null) {
                                              return;
                                          }


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
        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.PHOTO_URL)).into(target);


    }
}
