package app.demo.Vcalls.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.demo.Vcalls.R
import app.demo.Vcalls.navigation.Screen
import app.demo.Vcalls.network.resource.Resource
import app.demo.Vcalls.network.viewmodels.AuthViewModel
import app.demo.Vcalls.ui.theme.Background
import app.demo.Vcalls.ui.theme.PrimaryDark

import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel = koinViewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val sign by viewModel.signUpState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(sign) {
        if (sign is Resource.Success<*>) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Signup.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.call,
                    contentDescription = null,
                    modifier = Modifier.size(250.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Sign Up",
                    color = PrimaryDark,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Background,
                        focusedContainerColor = Background,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = PrimaryDark,
                        unfocusedIndicatorColor = Color.Black,
                        focusedLabelColor = PrimaryDark,
                        unfocusedLabelColor = PrimaryDark,
                        cursorColor = PrimaryDark
                    ),
                    shape = RoundedCornerShape(15.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Background,
                        focusedContainerColor = Background,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = PrimaryDark,
                        unfocusedIndicatorColor = Color.Black,
                        focusedLabelColor = PrimaryDark,
                        unfocusedLabelColor = PrimaryDark,
                        cursorColor = PrimaryDark
                    ),
                    shape = RoundedCornerShape(15.dp)
                )

                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("Create Password") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Background,
                        focusedContainerColor = Background,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = PrimaryDark,
                        unfocusedIndicatorColor = Color.Black,
                        focusedLabelColor = PrimaryDark,
                        unfocusedLabelColor = PrimaryDark,
                        cursorColor = PrimaryDark
                    ),
                    shape = RoundedCornerShape(15.dp)
                )

                Button(
                    onClick = {
                        if (name.isNotBlank() && email.isNotBlank() && otp.isNotBlank()) {
                            viewModel.signUp(
                                name, email, otp,
                                context = context
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryDark,
                        contentColor = Background
                    )
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "Please Login after 5 to 10 minutes",
                color = Color.Red,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have an account?")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Login",
                    color = PrimaryDark,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {navController.navigate( Screen.Login.route) }
                )
            }
        }

        if (sign is Resource.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryDark)
            }
        }


        if (sign is Resource.Error) {
            LaunchedEffect(sign) {
                Toast.makeText(
                    context,
                    "Error: ${(sign as Resource.Error).message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

