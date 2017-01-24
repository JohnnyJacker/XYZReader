// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleListActivity_ViewBinding<T extends ArticleListActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ArticleListActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipe_refresh, "field 'mSwipeRefresh'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.card_list, "field 'mRecyclerView'", RecyclerView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mSwipeRefresh = null;
    target.mRecyclerView = null;
    target.mToolbar = null;

    this.target = null;
  }
}
