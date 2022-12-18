package com.example.calcurator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class CalcAdapter extends RealmBaseAdapter<CalcModel> {

    public CalcAdapter(OrderedRealmCollection<CalcModel> data) {
        super(data);
    }

    private static class ViewHolder {
        TextView formula;
        TextView answer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.formula = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.answer = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            CalcModel calcModel = adapterData.get(position);
            viewHolder.formula.setText(calcModel.getFormula());
            viewHolder.answer.setText(calcModel.getAnswer());

        return convertView;
    }
}
