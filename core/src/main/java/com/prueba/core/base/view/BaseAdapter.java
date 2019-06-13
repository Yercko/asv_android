package com.prueba.core.base.view;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import com.prueba.core.base.model.ModelType;

/**
 * Created by prueba on 8/2/17.
 */

public class BaseAdapter<V extends BaseViewHolder, M extends ModelType> extends RecyclerView.Adapter<V> {

    private List<M> mModel = Collections.emptyList();
    private int mResource;

    public BaseAdapter(@LayoutRes int resource) {
        mResource = resource;
    }

    public void setModel(List<M> model) {
        mModel = model;
        notifyDataSetChanged();
    }


    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(mResource, parent, false);
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<V> clazz = (Class<V>) superClass.getActualTypeArguments()[0];
            Constructor<V> constructor = clazz.getConstructor(View.class);
            return constructor.newInstance(itemView);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.viewModel().updateModel(mModel.get(position));
        holder.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }
}
