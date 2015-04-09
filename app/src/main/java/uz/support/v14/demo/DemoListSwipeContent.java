package uz.support.v14.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uz.support.v14.app.UI;
import uz.support.v14.app.content.ContentListFragment;
import uz.support.v14.collection.MyArray;

public class DemoListSwipeContent extends ContentListFragment<String, DemoListSwipeContent.ViewHolder> {

    @Override
    protected void onContentStart(Bundle savedInstanceState) {
        setListItems(MyArray.from("Samsung", "Apple", "LG", "Sony", "Artel"));
    }

    @Override
    protected int adapterGetLayoutResource() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    protected ViewHolder adapterMakeHolder(View view) {
        ViewHolder h = new ViewHolder();
        h.name = UI.id(view, android.R.id.text1);
        return h;
    }

    @Override
    protected void adapterPopulate(ViewHolder holder, String item) {
        holder.name.setText(item);
    }

    public class ViewHolder {
        TextView name;
    }
}
