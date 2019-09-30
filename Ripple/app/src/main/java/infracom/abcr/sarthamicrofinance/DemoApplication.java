package infracom.abcr.sarthamicrofinance;


import android.app.Application;
import android.content.Context;
import android.os.Build;

import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Rey on 5/22/2015.
 */
public class DemoApplication extends Application{

    public static RefWatcher getRefWatcher(Context context) {
        DemoApplication application = (DemoApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG)
            //refWatcher = LeakCanary.install(this);
            ThemeManager.init(this, 2, 0, null);
    }

}
