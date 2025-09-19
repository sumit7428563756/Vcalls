//package app.demo.Vcalls.network.callmanager
//
//import android.app.Application
//import android.util.Log
//import androidx.fragment.app.FragmentActivity
//import com.permissionx.guolindev.PermissionX
//import com.zegocloud.uikit.internal.ZegoUIKitLanguage
//import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
//import com.zegocloud.uikit.prebuilt.call.core.invite.ZegoCallInvitationData
//import com.zegocloud.uikit.prebuilt.call.event.CallEndListener
//import com.zegocloud.uikit.prebuilt.call.event.ErrorEventsListener
//import com.zegocloud.uikit.prebuilt.call.event.SignalPluginConnectListener
//import com.zegocloud.uikit.prebuilt.call.event.ZegoCallEndReason
//import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
//import com.zegocloud.uikit.prebuilt.call.invite.internal.ZegoTranslationText
//import com.zegocloud.uikit.prebuilt.call.invite.internal.ZegoUIKitPrebuiltCallConfigProvider
//import im.zego.zim.enums.ZIMConnectionEvent
//import im.zego.zim.enums.ZIMConnectionState
//import org.json.JSONObject
//import timber.log.Timber
//
//
//object ZegoCallManager {
//
//    private const val APP_ID = 1891742561L
//    private const val APP_SIGN =
//        "6986e6bdf5db20223b5c8454db1378893bcc88e84f46acd97d8ac812b5b3a5b8"
//
//    private var initialized = false
//
//    fun init(
//        application: Application,
//        userID: String,
//        userName: String
//    ) {
//        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig().apply {
//            translationText = ZegoTranslationText(ZegoUIKitLanguage.ENGLISH)
//            provider =
//                ZegoUIKitPrebuiltCallConfigProvider { invitationData: ZegoCallInvitationData? ->
//                    ZegoUIKitPrebuiltCallInvitationConfig.generateDefaultConfig(invitationData)
//                }
//        }
//
//        // Error listener
//        ZegoUIKitPrebuiltCallService.events.errorEventsListener =
//            ErrorEventsListener { errorCode: Int, message: String ->
//                Timber.e("onError() errorCode = [$errorCode], message = [$message]")
//            }
//
//        // Connection listener
//        ZegoUIKitPrebuiltCallService.events.invitationEvents.pluginConnectListener =
//            SignalPluginConnectListener { state: ZIMConnectionState, event: ZIMConnectionEvent, extendedData: JSONObject ->
//                Timber.d("Connection state: $state, event: $event, data: $extendedData")
//            }
//
//        // Init service
//        ZegoUIKitPrebuiltCallService.init(
//            application,
//            APP_ID,
//            APP_SIGN,
//            userID,
//            userName,
//            callInvitationConfig
//        )
//
//        // Enable FCM push
//        ZegoUIKitPrebuiltCallService.enableFCMPush()
//
//        // Call end listener
//        ZegoUIKitPrebuiltCallService.events.callEvents.callEndListener =
//            CallEndListener { callEndReason: ZegoCallEndReason?, jsonObject: String? ->
//                Log.d("CallEndListener", "Call Ended: $callEndReason, json: $jsonObject")
//            }
//        initialized = true
//    }
//
//    fun unInit() {
//        ZegoUIKitPrebuiltCallService.unInit()
//        initialized = false
//    }
//
//    fun isInitialized(): Boolean = initialized
//
//    fun requestOverlayPermission(activityContext: FragmentActivity) {
//        PermissionX.init(activityContext).permissions(android.Manifest.permission.SYSTEM_ALERT_WINDOW)
//            .onExplainRequestReason { scope, deniedList ->
//                val message =
//                    "We need your consent for the following permissions in order to use the offline call function properly"
//                scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")
//            }.request { _, _, _ -> }
//    }
//}
