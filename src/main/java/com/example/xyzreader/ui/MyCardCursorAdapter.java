package com.example.xyzreader.ui;

/**
 * Created by chris on 1/23/2017.
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import static com.example.xyzreader.R.id.photo;
import static com.example.xyzreader.R.id.view;


/**
 * Created by skyfishjy on 10/31/14.
 */
public class MyCardCursorAdapter extends CursorRecyclerViewAdapter<MyCardCursorAdapter.ViewHolder> {


    public MyCardCursorAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_thumbnail)
        DynamicHeightImageView mImageView;


//        @BindView(R.id.article_thumbnail)
//        ImageView mImageView;

        @BindView(R.id.article_title)
        TextView mTitle;

        @BindView(R.id.article_subtitle)
        TextView mSubTitle;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);
        final ViewHolder vh = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))));

                String aspectRatio = getCursor().getString(ArticleLoader.Query.ASPECT_RATIO);

                Log.d("LOG_TAG", "The Aspect Ratio of this Image is " + aspectRatio);

            }
        });

        return vh;
    }




    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        viewHolder.mTitle.setText(cursor.getString(ArticleLoader.Query.TITLE));


        viewHolder.mSubTitle.setText(cursor.getString(ArticleLoader.Query.AUTHOR));

//        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.THUMB_URL)).centerCrop().fit().into(viewHolder.mImageView);


        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                String aspectRatio = cursor.getString(ArticleLoader.Query.ASPECT_RATIO);
                String photo = cursor.getString(ArticleLoader.Query.THUMB_URL);

                float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
                viewHolder.mImageView.setHeightRatio(ratio);
                viewHolder.mImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
//        viewHolder.mImageView.setAspectRatio(cursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));
//
        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.PHOTO_URL)).into(target);






//        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.THUMB_URL)).resize(700, 700).centerCrop().into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                viewHolder.mImageView.setImageBitmap(bitmap);
//                Palette.from(bitmap)
//                        .generate(new Palette.PaletteAsyncListener() {
//                                      @Override
//                                      public void onGenerated(Palette palette) {
//                                          Palette.Swatch textSwatch = palette.getMutedSwatch();
//                                          if (textSwatch == null) {
//                                              return;
//                                          }
//
////                                          viewHolder.itemView.setBackgroundColor(textSwatch.getRgb());
//
//                                          viewHolder.mTitle.setTextColor(textSwatch.getTitleTextColor());
//
//                                      }
//                                  }
//                        );
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });

    }
}
