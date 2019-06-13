package com.prueba.core.base.service;

import com.prueba.core.base.BaseApplication;
import com.prueba.core.base.component.ServiceComponent;
import com.prueba.core.base.component.ServiceComponentType;
//import io.realm.Realm;

/**
 * Created by prueba on 22/2/17.
 */

public class BaseDataService extends BaseService {
    private final ServiceComponentType mServiceComponent;

    public BaseDataService() {
        mServiceComponent = new ServiceComponent(BaseApplication.getAppComponent());
    }

  /*  public Realm realm() {
        return mServiceComponent.realm();
    }*/
}
