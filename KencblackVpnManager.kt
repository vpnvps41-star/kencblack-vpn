package com.kencblack.vpn

import android.content.Context
import android.widget.Toast
import com.wireguard.android.backend.WgQuickBackend
import com.wireguard.android.model.Config
import com.wireguard.android.model.Interface
import com.wireguard.android.model.Peer

class KencblackVpnManager(private val context: Context) {

    private val backend = WgQuickBackend(context)

    fun crearPerfil(
        privateKey: String,
        publicKeyServidor: String,
        endpoint: String,
        allowedIPs: String = "0.0.0.0/0"
    ) {
        val iface = Interface.Builder()
            .name("kencblack")
            .privateKey(privateKey)
            .listenPort(51820)
            .build()

        val peer = Peer.Builder()
            .publicKey(publicKeyServidor)
            .endpoint(endpoint)
            .allowedIps(allowedIPs)
            .build()

        val config = Config.Builder()
            .setInterface(iface)
            .addPeer(peer)
            .build()

        try {
            backend.setConfig("kencblack", config)
            Toast.makeText(context, "Perfil kencblack creado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error creando perfil: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun conectar() {
        try {
            backend.up("kencblack")
            Toast.makeText(context, "kencblack VPN conectada", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al conectar VPN: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun desconectar() {
        try {
            backend.down("kencblack")
            Toast.makeText(context, "kencblack VPN desconectada", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al desconectar VPN: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
