package pl.orangeapi.warsawcitygame.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.pojo.GameProgress;
import pl.orangeapi.warsawcitygame.db.pojo.Score;

public class GameProgressAdapter extends BaseAdapter {
    private List<GameProgress> searchArrayList;

    private LayoutInflater mInflater;
    private ImageView iv;
    Context context;
    Score patient;

    public GameProgressAdapter(Context context, List<GameProgress> results) {
        searchArrayList = results;
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_single, null);
                holder = new ViewHolder();

                iv = (ImageView) convertView.findViewById(R.id.icon);
                holder.firstLine = (TextView) convertView.findViewById(R.id.firstLine);
                holder.secondLine = (TextView) convertView.findViewById(R.id.secondLine);
                holder.number = (TextView) convertView.findViewById(R.id.number);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (searchArrayList.get(position).getType().equals("Drzewo")) {
                iv.setImageResource(R.drawable.tree_icon);
            }
            if (searchArrayList.get(position).getType().equals("Krzew")) {
                iv.setImageResource(R.drawable.shrub);
            }
            holder.firstLine.setText("Nazwa: " + searchArrayList.get(position).getName());
            holder.secondLine.setText("Czas: " + searchArrayList.get(position).getTime());
            holder.number.setText(""+(position+1)+".");

        return convertView;
    }

    static class ViewHolder {
        TextView firstLine;
        TextView secondLine;
        TextView number;
    }
}
