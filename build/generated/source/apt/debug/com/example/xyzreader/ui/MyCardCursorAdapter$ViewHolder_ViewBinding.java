// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyCardCursorAdapter$ViewHolder_ViewBinding<T extends MyCardCursorAdapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public MyCardCursorAdapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.mImageView = Utils.findRequiredViewAsType(source, R.id.article_thumbnail, "field 'mImageView'", ImageView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.article_title, "field 'mTitle'", TextView.class);
    target.mSubTitle = Utils.findRequiredViewAsType(source, R.id.article_subtitle, "field 'mSubTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mImageView = null;
    target.mTitle = null;
    target.mSubTitle = null;

    this.target = null;
  }
}
