package app.demo.Vcalls.view.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import app.demo.Vcalls.MainActivity
import app.demo.Vcalls.navigation.Screen
import app.demo.Vcalls.network.Const.APP_ID
import app.demo.Vcalls.network.Const.APP_SIGN
import app.demo.Vcalls.network.resource.Resource
import app.demo.Vcalls.network.viewmodels.AuthViewModel
import app.demo.Vcalls.network.viewmodels.HomeScreenViewModel
import app.demo.Vcalls.sharedPref.UserPreferences
import app.demo.Vcalls.ui.theme.PrimaryDark
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    val state by viewModel.userState.collectAsState()
    val logoutState by authViewModel.logoutState.collectAsState()

    val savedEmail = prefs.getEmail()
    val savedName = prefs.getName()


    LaunchedEffect(logoutState) {
        when (logoutState) {
            is Resource.Success -> {
                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, (logoutState as Resource.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state is Resource.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryDark)
            }
        }

        Text("Welcome: $savedName")
        Text("UserId: $savedEmail")

        val targetUserId = remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter Target User ID:")
        OutlinedTextField(
            value = targetUserId.value,
            onValueChange = { targetUserId.value = it },
            label = { Text(text = "User ID / Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Always show call buttons once initialized
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CallButton(isVideoCall = false) { button ->
                if (targetUserId.value.isNotEmpty()) {
                    button.setInvitees(listOf(ZegoUIKitUser(targetUserId.value, targetUserId.value)))
                }
            }
            CallButton(isVideoCall = true) { button ->
                if (targetUserId.value.isNotEmpty()) {
                    button.setInvitees(
                        mutableListOf(
                            ZegoUIKitUser(targetUserId.value, targetUserId.value)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { authViewModel.logout(context) },
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryDark,
                contentColor = Color.White
            )
        ) {
            if (logoutState is Resource.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Logout")
            }
        }
    }
}

@Composable
fun CallButton(
    isVideoCall: Boolean,
    onClick: (ZegoSendCallInvitationButton) -> Unit
) {
//    AndroidView(
//        factory = { context ->
//            val button = ZegoSendCallInvitationButton(context).apply {
//                setIsVideoCall(isVideoCall)
//                resourceID = "zego_uikit_call"
//            }
//            button
//        },
//        modifier = Modifier.size(60.dp)
//    ) { zegoCallButton ->
//        zegoCallButton.setOnClickListener { onClick(zegoCallButton) }
//    }

    AndroidView(
        factory = { context ->
            ZegoSendCallInvitationButton(context).apply {
                setIsVideoCall(isVideoCall)
                resourceID = "zego_uikit_call" // <-- must match console
            }
        },
        modifier = Modifier.size(60.dp)
    ) { button ->
        onClick(button) // sets invitees whenever targetUserId changes
    }
}
