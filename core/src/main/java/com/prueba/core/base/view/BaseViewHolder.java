package com.prueba.core.base.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.prueba.core.base.component.ViewHolderComponent;
import com.prueba.core.base.component.ViewHolderComponentType;
import com.prueba.core.base.viewmodel.BasicViewModelType;


/**
 * Created by prueba on 2/2/17.
 */

public abstract class BaseViewHolder<B extends ViewDataBinding, V extends BasicViewModelType> extends RecyclerView.ViewHolder implements ViewType {
    protected B binding;
    protected V viewModel;

    protected final View itemView;

    private ViewHolderComponentType viewHolderComponent;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }


    protected final void bindContentView(@NonNull View view, int variableId) {
        if(viewModel == null) { throw new IllegalStateException("viewModel must not be null and should be setted"); }
        binding = DataBindingUtil.bind(view);
        binding.setVariable(variableId, viewModel);

        try {
            //noinspection unchecked
            viewModel.attachView((ViewType) this, null);
        } catch(ClassCastException e) {
                throw new RuntimeException(getClass().getSimpleName() + " must implement ViewType subclass as declared in " + viewModel.getClass().getSimpleName());
        }
    }

    protected final ViewHolderComponentType viewHolderComponent() {
        if(viewHolderComponent == null) {
            viewHolderComponent = new ViewHolderComponent(itemView);
        }

        return viewHolderComponent;
    }

    public final V viewModel() {
        return viewModel;
    }

    public final View itemView() {return  itemView; }

    public final void executePendingBindings() {
        if(binding != null) { binding.executePendingBindings(); }
    }

}
