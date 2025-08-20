# kencblack VPN - Android App (Project skeleton)

**IMPORTANTE:** Aquí hay un proyecto Android listo para compilar localmente o en CI. 
**No incluye** el motor nativo de WireGuard (wireguard-go / wireguard-android) por razones de licencia y compilación; en su lugar te digo exactamente cómo integrarlo y construir el APK.

## Qué contiene
- Proyecto Android (Kotlin) minimal con UI: conectar/desconectar/estado.
- Scripts y workflow de GitHub Actions para compilar un APK (release/debug).
- README con pasos para integrar la librería oficial de WireGuard Android y generar el APK.

## Resumen rápido (pasos)
1. Clona este repositorio localmente:
   ```bash
   unzip kencblack_android_vpn.zip -d kencblack && cd kencblack
   ```
2. Clona el repositorio oficial de WireGuard Android (se usa como módulo):
   ```bash
   git clone https://git.zx2c4.com/android-wireguard wireguard-android
   ```
   *Si no puedes clonar desde ahí, hay repos en GitHub que sirven de espejo.*
3. Mueve/integra el contenido de `wireguard-android` dentro de este proyecto (al mismo nivel que `app`).
4. Abre Android Studio en el proyecto root y sincroniza Gradle.
5. Compila y genera APK: `./gradlew assembleDebug` o desde Android Studio.

## GitHub Actions (opcional)
- He incluido un workflow `.github/workflows/android-build.yml` que instala SDK, gradle and builds the APK.
- Para firmar el APK automático necesitarás añadir secrets (KEYSTORE, STOREPASS, etc.) en GitHub.

## Notas técnicas importantes
- Android obliga a que las VPN que tocan paquetes IP usen `VpnService` o una implementación nativa. La app oficial de WireGuard incluye código nativo (`wireguard-go`) y bindings. Para reproducir la funcionalidad completa debes integrar el módulo `wireguard-android`.
- Este proyecto expone una UI que crea/gestiona perfiles de WireGuard (archivo `.conf`) y usa las APIs del módulo `wireguard-android` para iniciar/stop el túnel (una vez integrado).
- Si quieres yo puedo preparar los pasos exactos y los cambios de código para integrar `wireguard-android` dentro del proyecto (modificar `settings.gradle`, `build.gradle`, y llamadas a la API). Dime si quieres que lo haga.

## Licencias
- WireGuard y su código están bajo licencias específicas (GPL/ISC). Revisa la licencia del módulo `wireguard-android` antes de distribuir.

