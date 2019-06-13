package com.prueba.core.base.view;

import android.database.DataSetObserver;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.utils.UIUtils;

public abstract class BasePagerAdapter<V extends BaseViewHolder,M extends ModelType> extends PagerAdapter {

    private List<M> mModel = Collections.emptyList();
    private SparseArrayCompat<V> mViewHolders = new SparseArrayCompat<>();
    private int mResource;



    public BasePagerAdapter(@LayoutRes int resource) {
        mResource = resource;
    }

    public void setModel(List<M> model) {
        mModel = model;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mModel!=null)
            try{
                return mModel.size();
            }
            catch (Exception ex){
                Log.i("EXCEPTION",ex.toString());
                return 0;
            }
        else
            return 0;
    }


    public @Nullable
    M getItem(int position) {
        return (position<0 || position>=mModel.size() ? null : mModel.get(position));
    }


    protected final V getViewHolder(int position) {
        return mViewHolders.get(position);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mViewHolders.remove(position);
        container.removeView((View) object);
        UIUtils.unbindDrawables((View)object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    @Override
    @CallSuper
    public Object instantiateItem(ViewGroup parent, int position) {
        M model = getItem(position);
        if( model!=null ) {
            V viewHolder = getViewHolder(position);
            if (viewHolder == null) {
                viewHolder = createViewHolder(parent);
                mViewHolders.put(position, viewHolder);
                parent.addView(viewHolder.itemView());
            }
            viewHolder.viewModel().updateModel(model);
            viewHolder.executePendingBindings();
            return viewHolder.itemView();
        }
        return null;

        //return LayoutInflater.from(collection.getmContext()).inflate(mResource, collection, false);
    }

    /**
     * Crea un nuevo ViewHolder del tipo parametrizado
     * @param parent Vista del ViewHolder
     * @return
     */
    private V createViewHolder(ViewGroup parent) {
        try {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(mResource, parent, false);
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<V> clazz = (Class<V>) superClass.getActualTypeArguments()[0];
            Constructor<V> constructor = clazz.getConstructor(View.class);
            return constructor.newInstance(itemView);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Log.e("createViewHolder",e.toString());
        }
        return null;
    }


}
