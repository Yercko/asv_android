package com.asv.champions.ui.selector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.asv.champions.BR;
import com.asv.champions.R;
import com.asv.champions.databinding.TitleImageSelectorActivityBinding;
import com.asv.champions.ui.adapters.TitleImageRecyclerViewAdapter;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.prueba.core.base.view.BaseActivity;
import com.prueba.core.base.view.ViewType;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjection;
import me.zhouzhuo.zzletterssidebar.ZzLetterSideBar;
import me.zhouzhuo.zzletterssidebar.adapter.BaseSortRecyclerViewAdapter;
import me.zhouzhuo.zzletterssidebar.interf.OnLetterTouchListener;
import me.zhouzhuo.zzletterssidebar.widget.ZzRecyclerView;

public class TitleImageSelectorActivity extends BaseActivity<TitleImageSelectorActivityBinding, TitleImageSelectorViewModel>
        implements /*Observer,*/ ViewType {

    private SearchView searchView;
    private ZzRecyclerView rv_listado;
    private TitleImageRecyclerViewAdapter adapter;
    private ZzLetterSideBar sideBar;
    private TextView tvDialog;
    private Toolbar mTopToolbar;

    private static final String EXTRA_MODEL = "EXTRA_MODEL";
    public static final String EXTRA_country = "EXTRA_country";

    public static Intent getStartIntent(Context context, List<TitleImage> countryResults) {
        Intent intent = new Intent(context, TitleImageSelectorActivity.class);
        ArrayList<TitleImage> results = new ArrayList<TitleImage>(countryResults);
        intent.putExtra(EXTRA_MODEL, results);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setAndBindContentView(R.layout.title_image_selector_activity, BR.vm, savedInstanceState);
        fillViewModel();
        setupToolbar();

        tvDialog = binding.tvDialog;
        sideBar = binding.sidebar;
        rv_listado = binding.rv;
        setupRecyclerView(rv_listado);

    }

    private void setupToolbar() {
        Toolbar toolbar = binding.barra;
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(true);
            ab.setTitle(R.string.country);
        }

    }

    private void fillViewModel() {
        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();
        if (extraBundle != null && !extraBundle.isEmpty()) {
            ArrayList<TitleImage> results =  (ArrayList<TitleImage>) extraBundle.getSerializable
                    (EXTRA_MODEL);
            for (int i=0;i<results.size(); i++){
                results.get(i).setOrder("a"+i);
            }
            viewModel.addToModel(results);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        viewModel.bindSearchEvent(RxSearchView.queryTextChanges(searchView));
        viewModel.addDisposable(
                viewModel.emitSelectedCountry().subscribe(obcountry-> {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_country, obcountry.getTitle());
                    popActivity(intent);
                })
        );
        viewModel.emitFilterCountrys().subscribe(this::update);
        return true;
    }
    public void update(List<TitleImage> listado){
        adapter.updateRecyclerView(listado);
        adapter.notifyDataSetChanged();
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new TitleImageRecyclerViewAdapter(this, viewModel.model());
        //set click event, optional
        adapter.setRecyclerViewClickListener((itemView, pos) -> viewModel.selectCountry(pos));

        rv_listado.setAdapter(adapter);

        sideBar.setLetterTouchListener(rv_listado, adapter, tvDialog, new OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter, int position) {}
            @Override
            public void onActionUp() {}
        });
    }



}