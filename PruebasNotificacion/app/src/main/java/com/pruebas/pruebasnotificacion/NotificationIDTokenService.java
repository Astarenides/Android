package com.pruebas.pruebasnotificacion;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class NotificationIDTokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
