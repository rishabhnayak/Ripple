package infracom.abcr.sarthamicrofinance.Register;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Customer_Launch;
import infracom.abcr.sarthamicrofinance.Profile.DealerLogin;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Marketing;
import infracom.abcr.sarthamicrofinance.SendPushNotification;
import infracom.abcr.sarthamicrofinance.ShowMessage;
import infracom.abcr.sarthamicrofinance.UserData;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    DBAdapter db;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                //LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();


                String[] StringAll;
                StringAll = message.split("\\^");

                String imei  = "";

                int StringLength = StringAll.length;
                if (StringLength > 0) {

                    title   = StringAll[0];
                    imei    = StringAll[1];
                    message = StringAll[2];
                    imageUrl = StringAll[2];
                }

                // Store new message data in sqlite database
                UserData userdata = new UserData(1,imei,title,message,imageUrl);
                DBAdapter.addUserData(userdata);
                Intent intent = new Intent("update-message");
                intent.putExtra("message", message);


                db = new DBAdapter(this);

                // db.open();
                HashMap<String, String> dataf = db.getLogininfo();
                String email = dataf.get("email");
                //empname = dataf.get("name");
                //regid = dataf.get("regid");
                String empdevice_imei = dataf.get("device_imei");
                //deviceIMEI = empdevice_imei.toString().trim();

                String D_under_departKEY = dataf.get("under_depart");
                //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
                db.close();



                if(imageUrl.equals("22")){

                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                    i2.putExtra("message", message);

                    // i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i2);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i2, imageUrl);
                    }
                    startActivity(i2);
                    //finish();
                    return;
                }


                if(D_under_departKEY.equals("Manager")){
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                    i2.putExtra("message", message);

                    //i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i2);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i2, imageUrl);
                    }
                    startActivity(i2);
                    //finish();
                }else {


                    // Launch Main Activity
                    if(D_under_departKEY.equals("Admin")){


                       LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i65= new Intent(getApplicationContext(), Admin_Launch.class);
                        i65.putExtra("message", message);

                        //i65.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i65);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i65, imageUrl);
                        }
                        //startActivity(i65);
                    }else {
                        if (D_under_departKEY.equals("Customer")) {


                           LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                            Intent i6 = new Intent(getApplicationContext(), Customer_Launch.class);
                            i6.putExtra("message", message);

                            //i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            if (TextUtils.isEmpty(imageUrl)) {
                                showNotificationMessage(getApplicationContext(), title, message, timestamp, i6);
                            } else {
                                // image is present, show notification with image
                                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i6, imageUrl);
                            }
                           // startActivity(i6);
                            //finish();
                        } else {
                            // Launch Main Activity

                            if (D_under_departKEY.equals("Dealer")) {


                                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                Intent i11 = new Intent(getApplicationContext(), Customer_Launch.class);
                                i11.putExtra("message", message);

                                //i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                if (TextUtils.isEmpty(imageUrl)) {
                                    showNotificationMessage(getApplicationContext(), title, message, timestamp, i11);
                                } else {
                                    // image is present, show notification with image
                                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i11, imageUrl);
                                }
                                // startActivity(i6);
                                //finish();
                            } else {

                                if (D_under_departKEY.equals("DSA Person")) {


                                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                    Intent i11 = new Intent(getApplicationContext(), Marketing.class);
                                    i11.putExtra("message", message);

                                    //i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    if (TextUtils.isEmpty(imageUrl)) {
                                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i11);
                                    } else {
                                        // image is present, show notification with image
                                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i11, imageUrl);
                                    }
                                    // startActivity(i6);
                                    //finish();
                                } else {
                                    // Launch Main Activity


                                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                    Intent i4 = new Intent(getApplicationContext(), Launch.class);
                                    i4.putExtra("message", message);

                                    // i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    if (TextUtils.isEmpty(imageUrl)) {
                                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i4);
                                    } else {
                                        // image is present, show notification with image
                                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i4, imageUrl);
                                    }
                                    //startActivity(i4);
                                    //finish();
                                }
                            }


                        }
                        // finish();
                    }

                }

/*

                switch (D_under_departKEY) {
                    case "Manager":
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                        i2.putExtra("message", message);

                        //i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i2);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i2, imageUrl);
                        }
                        startActivity(i2);

                        break;
                    case "Admin":
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i65= new Intent(getApplicationContext(), Admin_Launch.class);
                        i65.putExtra("message", message);

                        //i65.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i65);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i65, imageUrl);
                        }

                        break;
                    case "Customer":

                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i6 = new Intent(getApplicationContext(), Customer_Launch.class);
                        i6.putExtra("message", message);

                        //i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i6);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i6, imageUrl);
                        }

                        break;
                    case "Dealer":

                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i7 = new Intent(getApplicationContext(), Launch.class);
                        i7.putExtra("message", message);

                        // i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i7);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i7, imageUrl);
                        }

                        break;
                    case "Sales Person":
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i4 = new Intent(getApplicationContext(), Launch.class);
                        i4.putExtra("message", message);

                        // i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i4);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i4, imageUrl);
                        }

                        break;
                }


*/
             //   Intent resultIntent = new Intent(getApplicationContext(), ShowMessage.class);
                //resultIntent.putExtra("message", message);

                // check for image attachment
               // if (TextUtils.isEmpty(imageUrl)) {
                 //   showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                //} else {
                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                //}




                // Call broadcast defined on ShowMessage.java to show message on ShowMessage.java screen
               // aController.displayMessageOnScreen(context, title,message,imei);




            } else {
                // app is in background, show the notification in notification tray

                db = new DBAdapter(this);

                // db.open();
                HashMap<String, String> dataf = db.getLogininfo();
                String email = dataf.get("email");
                //empname = dataf.get("name");
                //regid = dataf.get("regid");
                String empdevice_imei = dataf.get("device_imei");
                //deviceIMEI = empdevice_imei.toString().trim();

                String D_under_departKEY = dataf.get("under_depart");
                //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
                db.close();

                Intent intent = new Intent("update-message");
                intent.putExtra("message", message);

                if(imageUrl.equals("22")){

                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                    i2.putExtra("message", message);

                    // i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i2);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i2, imageUrl);
                    }
                    startActivity(i2);
                    //finish();
                    return;
                }


                if(D_under_departKEY.equals("Manager")){

                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                    i2.putExtra("message", message);

                   // i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i2);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i2, imageUrl);
                    }
                    startActivity(i2);
                    //finish();
                }else {
                    // Launch Main Activity
                    if(D_under_departKEY.equals("Admin")){

                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                        Intent i65= new Intent(getApplicationContext(), Admin_Launch.class);
                        i65.putExtra("message", message);

                       // i65.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, message, timestamp, i65);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i65, imageUrl);
                        }
                        //startActivity(i65);
                    }else {


                        if (D_under_departKEY.equals("Customer")) {

                            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                            Intent i6 = new Intent(getApplicationContext(), Customer_Launch.class);
                            i6.putExtra("message", message);

                           // i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            if (TextUtils.isEmpty(imageUrl)) {
                                showNotificationMessage(getApplicationContext(), title, message, timestamp, i6);
                            } else {
                                // image is present, show notification with image
                                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i6, imageUrl);
                            }
                            // startActivity(i6);
                            //finish();
                        } else {


                            if (D_under_departKEY.equals("Dealer")) {

                                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                Intent i55 = new Intent(getApplicationContext(), Customer_Launch.class);
                                i55.putExtra("message", message);

                                // i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                if (TextUtils.isEmpty(imageUrl)) {
                                    showNotificationMessage(getApplicationContext(), title, message, timestamp, i55);
                                } else {
                                    // image is present, show notification with image
                                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i55, imageUrl);
                                }
                                // startActivity(i6);
                                //finish();
                            } else {
                                if (D_under_departKEY.equals("DSA Person")) {

                                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                    Intent i55 = new Intent(getApplicationContext(), Marketing.class);
                                    i55.putExtra("message", message);

                                    // i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    if (TextUtils.isEmpty(imageUrl)) {
                                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i55);
                                    } else {
                                        // image is present, show notification with image
                                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i55, imageUrl);
                                    }
                                    // startActivity(i6);
                                    //finish();
                                } else {
                                    // Launch Main Activity
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                                    Intent i4 = new Intent(getApplicationContext(), Launch.class);
                                    i4.putExtra("message", message);

                                    //  i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    if (TextUtils.isEmpty(imageUrl)) {
                                        showNotificationMessage(getApplicationContext(), title, message, timestamp, i4);
                                    } else {
                                        // image is present, show notification with image
                                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, i4, imageUrl);
                                    }
                                    //startActivity(i4);
                                    //finish();
                                }
                            }

                        }
                        // finish();
                    }

                }


            }



        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
