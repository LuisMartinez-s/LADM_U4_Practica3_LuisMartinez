package com.example.ladm_u4_practica3_luismartinez

import android.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


/*
* RECEIVER= evento u oyente de android que permite la lectura de eventos del SO
* */
class SMSReceiver:BroadcastReceiver(){
    var celularOrigen=""
    var contenidoSMS=""
    var baseRemota = FirebaseFirestore.getInstance()
    var notasLista = ArrayList<String>()
    var mensajeSMS=""
    override fun onReceive(context: Context, intent: Intent) {
        val extras=intent.extras
        if(extras!=null){
            var sms=extras.get("pdus") as Array<Any>
            for (indice in sms.indices){
                var formato=extras.getString("format")
                var smsMensaje=if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    SmsMessage.createFromPdu(sms[indice] as ByteArray,formato)
                }else{
                    SmsMessage.createFromPdu(sms[indice]as ByteArray)
                }

                celularOrigen=smsMensaje.originatingAddress.toString()
                contenidoSMS=smsMensaje.messageBody.toString()

            }//for
            when(contenidoSMS){
                "notas fancy"->{
                    enviarNotas("fancy")
                }//Fancy
                "notas little star"->{
                    enviarNotas("twinkle twinkle little star")
                }//little star
                "notas happy birthday"->{
                    enviarNotas("happy birthday")
                }//HP
                "notas minuet in G"->{
                    enviarNotas("minuet in G")
                }//MINUET
                "notas himno alegria"->{
                    enviarNotas("ode to joy")
                }//ode to joy
                else->{
                    enviarSMSValidacion()
                }

            }//when
        }//if
    }//onReceive

    private fun enviarSMSValidacion() {
        SmsManager.getDefault()
            .sendTextMessage(celularOrigen, null, "Error, sintaxis incorrecta, la sintaxis correcta es: notas nombreCancion" , null, null)
    }

    private fun enviarNotas(msg:String) {
        baseRemota.collection("notas")
            .whereEqualTo("nombre",msg)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                notasLista.clear()
                for (document in querySnapshot!!) {
                    var sms = "Nombre:"+document.getString("nombre") + "\n" +
                            "Notas: "+ document.getString("notas") + "\nTutorial: " + document.getString("tutorial")
                    notasLista.add(sms)
                    mensajeSMS=sms
                }//for
                SmsManager.getDefault()
                    .sendTextMessage(celularOrigen, null, mensajeSMS , null, null)
            }//addsnap
    }//enviarNotasFancy
}