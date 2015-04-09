package uz.support.v14.app.content;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import uz.support.v14.R;
import uz.support.v14.collection.MyAdapter;
import uz.support.v14.collection.MyArray;
import uz.support.v14.collection.MyPredicate;
import uz.support.v14.widget.SearchQuery;
import uz.support.v14.widget.ViewSetup;

public abstract class ContentListFragment<E, H> extends ContentFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    protected abstract void onContentStart(Bundle savedInstanceState);

    protected ListView cList;
    protected TextView cEmpty;
    protected ContentListAdapter adapter;

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.i_list_content);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipe = vsRoot.swipeRefresh(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener(this);
        swipe.setColorScheme(swipeRefreshColor());
        adapter = null;
        cList = vsRoot.id(android.R.id.list);
        cEmpty = vsRoot.id(android.R.id.empty);
        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                E item = adapter.getItem(position);
                onListItemClick(item);
            }
        });
        cList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (cList == null || cList.getChildCount() == 0) ?
                        0 : cList.getChildAt(0).getTop();
                swipe.setEnabled(swipeEnable() && firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        onContentStart(savedInstanceState);
    }

    protected boolean swipeEnable() {
        return false;
    }

    @Override
    public void onRefresh() {
    }

    public int[] swipeRefreshColor() {
        return new int[]{R.color.app_color};
    }

    public void completeRefresh() {
        vsRoot.swipeRefresh(R.id.swipe_refresh_layout).setRefreshing(false);
    }

    public void setHeader(View view) {
        FrameLayout fl = vsRoot.id(R.id.header);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public ViewSetup setHeader(int layoutId) {
        ViewSetup vs = new ViewSetup(getActivity(), layoutId);
        setHeader(vs.view);
        return vs;
    }

    public View getHeader() {
        FrameLayout fl = vsRoot.id(R.id.header);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    public void setFooter(View view) {
        FrameLayout fl = vsRoot.id(R.id.footer);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public ViewSetup setFooter(int layoutId) {
        ViewSetup vs = new ViewSetup(getActivity(), layoutId);
        setFooter(vs.view);
        return vs;
    }

    public View getFooter() {
        FrameLayout fl = vsRoot.id(R.id.footer);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    public MyArray<E> getListFilteredItems() {
        if (adapter != null) {
            return adapter.getFilteredItems();
        }
        return null;
    }

    public MyArray<E> getListItems() {
        if (adapter != null) {
            return adapter.getItems();
        }
        return null;
    }

    public void setListItems(MyArray<E> items) {
        if (adapter == null) {
            adapter = new ContentListAdapter(getActivity());
            adapter.registerDataSetObserver(new DataSetObserver() {

                private void populate() {
                    if (adapter.getCount() == 0) {
                        cList.setVisibility(View.GONE);
                        cEmpty.setVisibility(View.VISIBLE);
                    } else {
                        cList.setVisibility(View.VISIBLE);
                        cEmpty.setVisibility(View.GONE);
                    }
                    onListItemChanged();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    populate();
                }

                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    populate();
                }
            });
            cList.setAdapter(adapter);
            vsRoot.id(R.id.listContainer).setVisibility(View.VISIBLE);
            vsRoot.id(R.id.progressContainer).setVisibility(View.GONE);
        }
        adapter.setItems(items);
    }

    public void setListFilter(MyPredicate<E> predicate) {
        adapter.predicateOthers = predicate;
        adapter.filter();
    }

    public void setEmptyText(CharSequence emptyText) {
        cEmpty.setText(emptyText);
    }

    public void setEmptyText(int stringResId) {
        setEmptyText(getString(stringResId));
    }

    public final void setHasLongClick() {
        cList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                E item = adapter.getItem(position);
                onListItemLongClick(item);
                return true;
            }
        });
    }

    protected void onListItemClick(E item) {
    }

    protected void onListItemLongClick(E item) {
    }

    protected void onListItemChanged() {
    }

    protected void createAdapterIndex(View convertView, int index) {
    }

    protected abstract int adapterGetLayoutResource();

    protected abstract H adapterMakeHolder(View view);

    protected abstract void adapterPopulate(H holder, E item);

    public class ContentListAdapter extends MyAdapter<E, H> {

        public ContentListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResource() {
            return adapterGetLayoutResource();
        }

        @Override
        public H makeHolder(View view) {
            return adapterMakeHolder(view);
        }

        @Override
        public void populate(H holder, E item) {
            adapterPopulate(holder, item);
        }

        @Override
        public void createIndex(View convertView, int index) {
            createAdapterIndex(convertView, index);
        }
    }

    public abstract class MoldSearchListQuery extends SearchQuery {

        @Override
        public void onQueryText(final String s) {
            if (adapter != null) {
                adapter.predicateSearch = new MyPredicate<E>() {
                    @Override
                    public boolean apply(E e) {
                        return filter(e, s);
                    }
                };
                adapter.filter();
            }
        }

        public abstract boolean filter(E item, String text);
    }
}
