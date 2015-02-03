package prebenneirijnck.be.footistics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.ui.BaseNavDrawerActivity;

public class DrawerAdapter extends ArrayAdapter<BaseNavDrawerActivity.DrawerItem> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_DIVIDER = 1;
    private static final int VIEW_TYPE_ACCOUNT = 2;

    public DrawerAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        BaseNavDrawerActivity.DrawerItem item = getItem(position);
        if(item instanceof BaseNavDrawerActivity.DrawerItemDivider){
            return VIEW_TYPE_DIVIDER;
        }
        if(item instanceof BaseNavDrawerActivity.DrawerItemAccount){
            return VIEW_TYPE_ACCOUNT;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        BaseNavDrawerActivity.DrawerItem item = getItem(position);
        return !(item instanceof BaseNavDrawerActivity.DrawerItemDivider);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseNavDrawerActivity.DrawerItem item = getItem(position);

        int type = getItemViewType(position);

        //no viewholder needed, divider and account only appear once
        if(type==VIEW_TYPE_DIVIDER){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item_divider, parent, false);
            return convertView;
        }
        if(type==VIEW_TYPE_ACCOUNT){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item_account, parent, false);
            TextView account = (TextView) convertView.findViewById(R.id.drawerItemAccount);
            TextView user = (TextView) convertView.findViewById(R.id.drawerItemUsername);

            account.setText(R.string.app_name);
            user.setText(R.string.app_name);
            return convertView;
        }

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item, parent, false);
            holder = new ViewHolder();
            holder.attach(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(item.mIconRes);
        holder.title.setText(item.mTitle);

        return convertView;
    }

    private static class ViewHolder {

        public TextView title;

        public ImageView icon;

        public void attach(View v) {
            icon = (ImageView) v.findViewById(R.id.drawerMenuIcon);
            title = (TextView) v.findViewById(R.id.drawerMenuTitle);
        }
    }

}
