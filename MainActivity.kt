package com.kencblack.vpn

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val REQ_VPN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConnect = findViewById<Button>(R.id.btn_connect)
        val btnDisconnect = findViewById<Button>(R.id.btn_disconnect)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        val vpnManager = KencblackVpnManager(this)

        btnConnect.setOnClickListener {
            val intent = VpnService.prepare(this)
            if (intent != null) {
                startActivityForResult(intent, REQ_VPN)
            } else {
                vpnManager.crearPerfil(
                    privateKey = "<TU_PRIVATE_KEY>",
                    publicKeyServidor = "<SERVER_PUBLIC_KEY>",
                    endpoint = "<IP:PUERTO_DEL_SERVIDOR>"
                )
                vpnManager.conectar()
            }
        }

        btnDisconnect.setOnClickListener {
            vpnManager.desconectar()
        }

        tvStatus.text = "Estado: desconectado"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_VPN) {
            if (resultCode == Activity.RESULT_OK) {
                val vpnManager = KencblackVpnManager(this)
                vpnManager.crearPerfil(
                    privateKey = "<TU_PRIVATE_KEY>",
                    publicKeyServidor = "<SERVER_PUBLIC_KEY>",
                    endpoint = "<IP:PUERTO_DEL_SERVIDOR>"
                )
                vpnManager.conectar()
            } else {
                AlertDialog.Builder(this).setMessage("Permiso VPN denegado").setPositiveButton("OK", null).show()
            }
        }
    }
}
