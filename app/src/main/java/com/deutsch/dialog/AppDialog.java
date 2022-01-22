package com.deutsch.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.deutsch.R;
import com.deutsch.model.Word;
import com.deutsch.service.AppService;
import com.deutsch.util.Util;


public class AppDialog extends AppCompatDialogFragment {
    private Word word;

    public AppDialog(Word word) {

        this.word = word;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_app_dialog, null);
        loadTable(view);
        builder.setView(view);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    public void loadTable(View view) {

        TableLayout table = (TableLayout) view.findViewById(R.id.dialog_table);
        TableRow tableHeader = new TableRow(getContext());
        tableHeader.setBackgroundColor(getResources().getColor(R.color.silver));
        tableHeader.setPadding(5, 5, 5, 5);

        createTableHeader(tableHeader, "Pronoun");
        createTableHeader(tableHeader, "Present");
        createTableHeader(tableHeader, "Past");
        createTableHeader(tableHeader, "Future");
        table.addView(tableHeader);

        Util.loadVerbProperty(word);


        addTableRow(table, "ich", (String) Util.presentProperties.get("ich"), (String) Util.pasProperties.get("ich"), (String) Util.futureProperties.get("ich"));
        addTableRow(table, "du", (String) Util.presentProperties.get("du"), (String) Util.pasProperties.get("du"), (String) Util.futureProperties.get("du"));
        addTableRow(table, "er", (String) Util.presentProperties.get("er"), (String) Util.pasProperties.get("er"), (String) Util.futureProperties.get("er"));
        addTableRow(table, "wir", (String) Util.presentProperties.get("wir"), (String) Util.pasProperties.get("wir"), (String) Util.futureProperties.get("wir"));
        addTableRow(table, "ihr", (String) Util.presentProperties.get("ihr"), (String) Util.pasProperties.get("ihr"), (String) Util.futureProperties.get("ihr"));
        addTableRow(table, "Sie", (String) Util.presentProperties.get("Sie"), (String) Util.pasProperties.get("Sie"), (String) Util.futureProperties.get("Sie"));


    }

    public void createTableHeader(TableRow tableHeader, String headerText) {

        TextView header = new TextView(getContext());
        header.setAllCaps(true);
        header.setText(" " + headerText + " ");
        header.setTextColor(Color.BLACK);
        header.setGravity(Gravity.CENTER);
        header.setTypeface(null, Typeface.BOLD);
        tableHeader.addView(header);
    }

    public void addTableRow(TableLayout tableLayout, String pronoun, String present, String past, String future) {

        TableRow row = new TableRow(getContext());
        TextView t1v = new TextView(getContext());
        t1v.setText(" " + pronoun);
        t1v.setTextColor(Color.BLUE);
        t1v.setGravity(Gravity.CENTER_HORIZONTAL);
        t1v.setPadding(5, 5, 5, 5);
        t1v.setTypeface(null, Typeface.BOLD);
        row.addView(t1v);

        TextView t2v = new TextView(getContext());
        t2v.setText(" " + present);
        t2v.setTextColor(Color.BLACK);
        t2v.setGravity(Gravity.CENTER_HORIZONTAL);
        t2v.setPadding(5, 5, 5, 5);
        row.addView(t2v);

        TextView t3v = new TextView(getContext());
        t3v.setText(" " + past);
        t3v.setTextColor(Color.BLACK);
        t3v.setGravity(Gravity.CENTER_HORIZONTAL);
        t3v.setPadding(5, 5, 5, 5);
        row.addView(t3v);

        TextView t4v = new TextView(getContext());
        t4v.setText(" " + future);
        t4v.setTextColor(Color.BLACK);
        t4v.setGravity(Gravity.CENTER_HORIZONTAL);
        t4v.setPadding(5, 5, 5, 5);
        row.addView(t4v);
        tableLayout.addView(row);
    }
}