package com.asv.champions.ui;

import android.databinding.DataBindingComponent;
/**
 * Created by prueba on 4/4/18.
 */

public class AppDataBindingComponent  implements DataBindingComponent {

    @Override
    public RecyclerViewDataBinding getRecyclerViewDataBinding() {
        return new RecyclerViewDataBinding();
    }
}
