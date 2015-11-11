package co.edu.udea.pi.sjm.petted;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.facebook.FacebookSdk;
import com.parse.PushService;

import co.edu.udea.pi.sjm.petted.vista.MainActivity;

/**
 * Created by Juan on 08/11/2015.
 */
public class UseParse extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(getApplicationContext(), "nnKfB6bJoD2xe7LIWiwFc7Sygw02leA30rtNaTeZ", "f2RXWk8G5Q6Jdn0dIz7qXHvhYQuyahVJH6oc0HOn");
//        PushService.setDefaultPushCallback(getApplicationContext(), MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveEventually();
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(getApplicationContext());
    }
}
