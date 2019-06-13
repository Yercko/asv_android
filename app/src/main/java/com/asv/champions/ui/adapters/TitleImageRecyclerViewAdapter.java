package com.asv.champions.ui.adapters;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.asv.champions.R;
import com.asv.champions.ui.selector.TitleImage;

import java.util.List;

import me.zhouzhuo.zzletterssidebar.adapter.BaseSortRecyclerViewAdapter;
import me.zhouzhuo.zzletterssidebar.viewholder.BaseRecyclerViewHolder;

public class TitleImageRecyclerViewAdapter extends BaseSortRecyclerViewAdapter<TitleImage,
        BaseRecyclerViewHolder> {

    public TitleImageRecyclerViewAdapter(Context ctx, List<TitleImage> mDatas) {
        super(ctx, mDatas);
    }

    //return your itemView layoutRes id
    @Override
    public int getItemLayoutId() {
        return R.layout.country_item;
    }

    //add a header ,optional, if no need, return 0
    @Override
    public int getHeaderLayoutId() {
        return 0;
    }

    //add a footer, optional, if no need, return 0
    @Override
    public int getFooterLayoutId() {
        return 0;
    }

    @Override
    public BaseRecyclerViewHolder getViewHolder(View itemView, int type) {
        switch (type) {
            case BaseSortRecyclerViewAdapter.TYPE_HEADER:
                return new HeaderHolder(itemView);

        }
        return new CoinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, final int position) {

        if (holder instanceof CoinViewHolder) {
            CoinViewHolder coinViewHolder = (CoinViewHolder) holder;
            //must add this
            final int mPos = position - getHeadViewSize();
            if (mPos < mDatas.size()) {
                TitleImage item = mDatas.get(mPos);
                initLetter(holder, mPos);
                coinViewHolder.tvName.setText(item.getTitle());

                coinViewHolder.rootView.setOnClickListener(v -> {
                    toggleRow(mPos);
                    if (clickListener != null) {
                        clickListener.onClick(coinViewHolder.rootView, mPos);
                    }
                });

            }
        } else if (holder instanceof HeaderHolder) {

        }
    }

    private void toggleRow(int mRow) {
        for(int i=0; i < mDatas.size(); i++) {
            if( mDatas.get(i).isChecked() && i!=mRow ) {
                mDatas.get(i).toggle();
                notifyItemChanged(i);
            }
        }
        mDatas.get(mRow).toggle();
        notifyItemChanged(mRow);
    }

    public static class CoinViewHolder extends BaseRecyclerViewHolder {

        protected TextView tvName;
        protected ImageView ivIcon;

        public CoinViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.list_item_tv_name);
            ivIcon = (ImageView) itemView.findViewById(R.id.icon_coin);
        }
    }

    public static class HeaderHolder extends BaseRecyclerViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }



}