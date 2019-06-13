package com.asv.champions.ui.clubslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asv.champions.BR;
import com.asv.champions.R;
import com.asv.champions.databinding.ClublistActivityBinding;
import com.asv.champions.model.DeleteRequest;
import com.asv.champions.ui.clubadd.ClubAddActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.prueba.core.base.view.BaseActivity;
import com.prueba.core.base.view.ViewType;

import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjection;

public class ClubListActivity extends BaseActivity<ClublistActivityBinding, ClubListViewModel> implements ViewType {
    private static int UPDATE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setAndBindContentView(R.layout.clublist_activity, BR.vm, savedInstanceState);

        setSupportActionBar(binding.toolbar);

        compositeDisposable.add(
                RxView.clicks(binding.fab).throttleFirst(1, TimeUnit.SECONDS).subscribe(s -> {
                    Intent intent = new Intent(ClubListActivity.this, ClubAddActivity.class);
                    pushActivity(intent,UPDATE);
                })
        );

        compositeDisposable.add(
            viewModel.emitLoading().subscribe(binding.swiperefresh::setRefreshing)
        );

        binding.swiperefresh.setOnRefreshListener(() -> {
            viewModel.updateList();
        });

        compositeDisposable.add(
            viewModel.emitRemove().subscribe(this::alertRemove)
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE && resultCode == RESULT_OK)  {
            viewModel.updateList();
        }
    }


    public void alertRemove(String idRemove){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_title)
                .setCancelable(true)
                .setNegativeButton(R.string.bt_cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.bt_ok, (dialog, which) -> {
                    dialog.dismiss();
                    DeleteRequest request = new DeleteRequest();
                    request.setName(idRemove);
                    viewModel.deleteItem(request);
                });
        AlertDialog alerta = builder.create();
        alerta.show();
    }


}
