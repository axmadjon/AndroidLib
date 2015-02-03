package uz.support.v14.collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public abstract class MyAdapter<E, H> extends BaseAdapter implements Filterable {

    protected final Context context;
    protected final LayoutInflater inflater;
    protected MyArray<E> items;
    protected MyArray<E> filteredItems = MyArray.emptyArray();

    private ItemFilter itemFilter;
    public MyPredicate<E> predicateSearch;
    public MyPredicate<E> predicateOthers;

    public MyAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = MyArray.emptyArray();
        this.filteredItems = getFilteredItems();
    }

    private MyArray<E> getFilteredItems() {
        MyPredicate<E> predicate = MyPredicate.and(predicateSearch, predicateOthers);
        if (predicate != null) {
            return items.filter(predicate);
        } else {
            return items;
        }
    }

    public void setItems(MyArray<E> items) {
        this.items = items;
        this.filteredItems = getFilteredItems();
        notifyDataSetChanged();
    }

    public MyArray<E> getItems() {
        return this.items;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public E getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            H holder;
            if (convertView == null) {
                convertView = inflater.inflate(getLayoutResource(), parent, false);
                holder = makeHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (H) convertView.getTag();
            }

            populate(holder, getItem(position));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener(getItem(position));
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener(getItem(position));
                    return true;
                }
            });
            return convertView;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void filter() {
        getFilter().filter(null);
    }

    public abstract int getLayoutResource();

    public abstract H makeHolder(View view);

    public abstract void populate(H holder, E item);

    protected void onClickListener(E item) {
    }

    protected void onLongClickListener(E item) {
    }

    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }
        return itemFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            MyArray<E> filteredItems = getFilteredItems();
            result.values = filteredItems;
            result.count = filteredItems.size();
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (MyArray<E>) results.values;
            notifyDataSetChanged();
        }
    }
}
