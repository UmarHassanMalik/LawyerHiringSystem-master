package com.innova.lawyerhiringsystem.adapter;
/* This is an ADAPTER
*   It populated listview on view quotations fragment
* */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.model.Quotation;

import java.util.ArrayList;

public class ViewQuotations extends ArrayAdapter<Quotation> {

    public ViewQuotations(Context context, ArrayList<Quotation> quotations) {
        super(context, 0, quotations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Quotation quotation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quotations_row, parent, false);
        }
        // Lookup view for data population
        TextView date = (TextView) convertView.findViewById(R.id.row_date);
        TextView name = (TextView) convertView.findViewById(R.id.row_name);
        TextView email = (TextView) convertView.findViewById(R.id.row_email);
        TextView phone = (TextView) convertView.findViewById(R.id.row_phone);
        TextView status = (TextView) convertView.findViewById(R.id.row_status);

        // Populate the data into the template view using the data object
        date.setText(quotation.getDate());
        name.setText(quotation.getName());
        email.setText(quotation.getEmail());
        phone.setText(quotation.getPhone());
        status.setText(quotation.getStatus());

        // Return the completed view to render on screen
        return convertView;
    }

}
