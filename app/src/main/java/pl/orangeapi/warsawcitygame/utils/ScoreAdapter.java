package pl.orangeapi.warsawcitygame.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.pojo.Score;

public class ScoreAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private static List<Score> searchArrayList;

    private LayoutInflater mInflater;
    Context context;
    Score patient;

    public ScoreAdapter(Context context, List<Score> results) {
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
        notifyDataSetChanged();
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_single, null);
            holder = new ViewHolder();
            holder.firstLine = (TextView) convertView.findViewById(R.id.firstLine);
            holder.secondLine = (TextView) convertView.findViewById(R.id.secondLine);
            holder.thirdLine = (TextView) convertView.findViewById(R.id.secondLine);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.firstLine.setText("Nazwa użytkownika: "+ searchArrayList.get(position).getUser());
        holder.secondLine.setText("Liczba obiektów: "+searchArrayList.get(position).getNumber() +", w czasie: "+searchArrayList.get(position).getTime());
        holder.thirdLine.setText("Punkty: "+ searchArrayList.get(position).getPoints());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    static class ViewHolder {
        TextView firstLine;
        TextView secondLine;
        TextView thirdLine;
    }
}
