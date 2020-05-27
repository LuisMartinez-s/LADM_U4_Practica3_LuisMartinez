package com.example.ladm_u4_practica3_luismartinez

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val siPermisoEnviar = 1
    val siPermisoReceiver = 2
    val siPermisoLectura = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ////////////////////PERMISOS/////////////////////////
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS), siPermisoReceiver)
        }//if receive sms
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS), siPermisoEnviar)
        }//if SEND SMS
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_SMS), siPermisoLectura)
        }//if READ SMS

    }//onCreate


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == siPermisoEnviar) {
            Toast.makeText(this, "Se otorgó el permiso para envio de SMS",Toast.LENGTH_LONG).show()
        }//if

        if (requestCode == siPermisoReceiver) {
            Toast.makeText(this, "Se otorgó el permiso para Recibir SMS",Toast.LENGTH_LONG).show()
        }//if
        if (requestCode == siPermisoLectura) {
            Toast.makeText(this, "Se otorgó el permiso para lectura de SMS",Toast.LENGTH_LONG).show()
        }//if

    }

}//class
