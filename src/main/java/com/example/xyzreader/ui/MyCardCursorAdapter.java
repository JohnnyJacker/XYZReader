package com.example.xyzreader.ui;

/**
 * Created by chris on 1/23/2017.
 */

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        ImageView mImageView;

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
//                parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
//                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))));

                String aspectRatio = getCursor().getString(ArticleLoader.Query.ASPECT_RATIO);

                Log.d("LOG_TAG", "The Aspect Ratio of this Image is " + aspectRatio);

            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {

        viewHolder.mTitle.setText(cursor.getString(ArticleLoader.Query.TITLE));


        viewHolder.mSubTitle.setText(cursor.getString(ArticleLoader.Query.AUTHOR));

        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.THUMB_URL)).resize(800, 800).centerInside().into(viewHolder.mImageView);

//        Picasso.with(viewHolder.mImageView.getContext()).load(cursor.getString(ArticleLoader.Query.THUMB_URL)).resize(300, 300).centerCrop().into(new Target() {
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
