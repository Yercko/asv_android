package com.asv.champions.ui.clubadd;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.asv.champions.BR;
import com.asv.champions.R;
import com.asv.champions.databinding.ClubaddActivityBinding;
import com.asv.champions.model.ClubItem;
import com.asv.champions.ui.selector.TitleImage;
import com.asv.champions.ui.selector.TitleImageSelectorActivity;
import com.asv.champions.ui.views.DatePickerFragment;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.prueba.core.base.view.BaseActivity;
import com.prueba.core.base.view.ViewType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjection;

public class ClubAddActivity extends BaseActivity<ClubaddActivityBinding, ClubAddViewModel> implements ViewType {
    private static int REQUEST_SELECT_COUNTRY = 32;
    private static int REQUEST_SELECT_PHOTO = 33;
    private static String EXTRA_MODEL = "EXTRA_MODEL";
    private ClubItem clubItem = new ClubItem();
    ArrayAdapter<String> adapter;

    public static Intent getStartIntent(Context context, ClubItem clubItem) {
        Intent intent = new Intent(context, ClubAddActivity.class);
        intent.putExtra(EXTRA_MODEL,clubItem);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fillExtras();
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setAndBindContentView(R.layout.clubadd_activity, BR.vm, savedInstanceState);

        setSupportActionBar(binding.toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(true);
            //ab.setTitle(R.string.c);
        }

        initEvents();

        if (clubItem !=null){
            initViews();
        }
    }

    public void fillExtras(){
        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();
        if (extraBundle != null && !extraBundle.isEmpty()) {
            clubItem = (ClubItem) extraBundle.getSerializable(EXTRA_MODEL);
        }
    }

    private void initEvents(){
        viewModel.bindName(RxTextView.textChanges(binding.etName));
        viewModel.bindRival(RxTextView.textChanges(binding.etRival));
        viewModel.bindSubmit(RxView.clicks(binding.fab));

        compositeDisposable.add(viewModel.emitValidName().subscribe(s -> {
            binding.fab.setEnabled(s);
            if (s) {
                binding.fab.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOrange)));
            } else {
                binding.fab.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTextGrayDark)));
            }
        }));
        compositeDisposable.add(
                viewModel.emitError().filter(s -> !s.isEmpty()).subscribe(s -> {
                    binding.tvError.setText(s);
                    binding.tvError.setVisibility(View.VISIBLE);
                })
        );


        viewModel.bindShowCountries(RxView.clicks(binding.llCountry));

        viewModel.addDisposable(
                viewModel.emitShowCountries().subscribe(this::showSeleccionarCountry)
        );

        compositeDisposable.add(
                viewModel.emitCountry().subscribe(binding.etCountry::setText)
        );

        compositeDisposable.add(
                viewModel.emitFinish().doOnNext(s -> {
                    binding.tvError.setText("");
                    binding.tvError.setVisibility(View.VISIBLE);
                    Snackbar.make(binding.fab, getString(R.string.club_add_success), Snackbar.LENGTH_LONG)
                            .show();
                }).delay(1, TimeUnit.SECONDS).subscribe(s -> {
                    popActivity();
                })
        );

        compositeDisposable.add(
                RxView.clicks(binding.llPhoto).subscribe(s -> {
                    dispatchSelectPictureIntent(REQUEST_SELECT_PHOTO);
                })
        );

        compositeDisposable.add(
            viewModel.emitChampions().subscribe(this::updateList)
        );
        compositeDisposable.add(
                RxView.clicks(binding.tvAddChampions).subscribe(s -> {
                    showDatePickerDialog();
                })
        );
    }

    private void initViews(){
        initList();

        if (clubItem != null && clubItem.getName()!= null){
            binding.etName.setText(clubItem.getName());
            binding.etName.setEnabled(false);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(R.string.update_club);
            }
            binding.etRival.setText(clubItem.getRival());

            if ( clubItem.getCountry() != null){
                binding.etCountry.setText(clubItem.getCountry());
            }

            viewModel.parseDate(clubItem.getChampions());

        }


    }

    private void initList(){
       adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());
        binding.list.setAdapter(adapter);
    }

    private void updateList(List<String> items){
        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    private void showDatePickerDialog( ) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            viewModel.setChampion(day,month+1,year);
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int num){
        return (num < 10) ? "0"+num : ""+num;
    }

    public ClubItem getClubItem() {
        return clubItem;
    }

    private void showSeleccionarCountry(List<TitleImage> resultList) {
        Intent intent = TitleImageSelectorActivity.getStartIntent(this, resultList);
        pushActivity(intent, REQUEST_SELECT_COUNTRY);
    }
    public void dispatchSelectPictureIntent(int option) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 192);
        intent.putExtra("outputY", 128);
        intent.putExtra("aspectX", 6);
        intent.putExtra("aspectY", 4);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, option);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SELECT_COUNTRY && resultCode == RESULT_OK){
            String country = data.getStringExtra
                    (TitleImageSelectorActivity.EXTRA_country);
            viewModel.nextCountry(country);
            viewModel.model().setCountry(country);
        } else if(requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap newProfilePic = extras.getParcelable("data");
                viewModel.nextPhoto(newProfilePic);

            }
        }
    }
}
