package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.app.Activity;
import android.view.ViewStub;

import brunonm.conductor.mobile.desafio.desafiomobile.R;

public class LayoutLoader {

    public static void load(Activity activity, int layout) {
        ViewStub stub = activity.findViewById(R.id.layout_stub);
        stub.setLayoutResource(layout);
        stub.inflate();
    }
}
