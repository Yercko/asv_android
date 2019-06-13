package com.asv.champions.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.prueba.core.base.view.BaseAdapter;

import java.util.List;

/**
 * Created by prueba on 4/4/18.
 */

public class RecyclerViewDataBinding {

    @BindingAdapter({"adapter", "data"})
    public void bind(RecyclerView recyclerView, BaseAdapter baseAdapter, List data) {
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.setModel(data);
    }
}
