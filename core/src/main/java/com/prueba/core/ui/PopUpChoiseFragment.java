package com.prueba.core.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.asv.core.R;
import com.prueba.core.base.model.PopupClick;

import java.util.List;



public class PopUpChoiseFragment extends DialogFragment {
    public static String TAG = "PopUpChoiseFragment";
    public static String EXTRA_LISTMAP = "EXTRA_LISTMAP";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    private String title= "";
    private List<String> objetosClaveValor;

     private PopupClick click;

    public static PopUpChoiseFragment newInstance(PopupClick popupClick) {
        PopUpChoiseFragment myFragment = new PopUpChoiseFragment();
        myFragment.click = popupClick;
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString(EXTRA_TITLE);
            objetosClaveValor = (List<String>) getArguments().getSerializable(EXTRA_LISTMAP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popupchoice_fragment, container, false);
        if ( getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        TextView tvTiele = (TextView) v. findViewById(R.id.tv_title);
        tvTiele.setText(title);
        ListView listView = (ListView)v. findViewById(R.id.list);
        listView.setAdapter(new CustomList(getActivity(),objetosClaveValor));

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                click.result(position);
                dismiss();
            }
        });

        Button btCancel = (Button)v.findViewById(R.id.btn_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    private class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        private List<String> items;
        public CustomList(Activity context, List<String> items) {
            super(context, R.layout.item_choice,  items);
            this.context = context;
            this.items = items;
        }


        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.item_choice, null, true);
            LinearLayout llroot = rowView.findViewById(R.id.ll_item);
            TextView description = (TextView)llroot.findViewById(R.id.tv_description);
            description.setText(items.get(position));

            return rowView;
        }
    }


}