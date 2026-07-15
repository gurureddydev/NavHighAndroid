package com.example.navhigh.common.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.ErrorNeonRose
import com.example.navhigh.ui.theme.TextFieldBorder
import com.example.navhigh.ui.theme.TextFieldHint
import com.example.navhigh.ui.theme.TextFieldIcon
import com.example.navhigh.ui.theme.TextFieldText


// ---------------- COMMON TEXT FIELD ----------------

@Composable
fun CommonTextField(

    value: String,

    onValueChange: (String) -> Unit,

    label: String,

    modifier: Modifier = Modifier,

    isPassword: Boolean = false,

    isError: Boolean = false,

    showSuccessIcon: Boolean = false

) {

    var passwordVisible by remember {

        mutableStateOf(false)

    }


    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,


        modifier = modifier
            .fillMaxWidth(),


        textStyle = TextStyle(
            fontSize = 14.sp
        ),


        singleLine = true,


        shape = RoundedCornerShape(
            AppDimensions.TextFieldRadius
        ),


        label = {

            Text(
                text = label,
                fontSize = 12.sp
            )

        },


        trailingIcon = {


            when {


                // PASSWORD EYE ICON

                isPassword -> {


                    IconButton(

                        onClick = {

                            passwordVisible =
                                !passwordVisible

                        }

                    ) {


                        Icon(

                            painter = painterResource(

                                id =
                                    if (passwordVisible)

                                        R.drawable.eye_open

                                    else

                                        R.drawable.eye_close

                            ),


                            contentDescription =
                                "Password Visibility",


                            tint = TextFieldIcon,


                            modifier =
                                Modifier.size(22.dp)

                        )

                    }


                }



                // USERNAME GREEN TICK

                showSuccessIcon -> {


                    Icon(

                        imageVector =
                            Icons.Default.CheckCircle,


                        contentDescription =
                            "Username Available",


                        tint =
                            Color(0xFF00E676),


                        modifier =
                            Modifier.size(22.dp)

                    )


                }


            }

        },


        visualTransformation =


            if (isPassword && !passwordVisible)


                PasswordVisualTransformation()


            else


                VisualTransformation.None,



        isError = isError,


        colors = OutlinedTextFieldDefaults.colors(


            focusedContainerColor =
                Color.Transparent,


            unfocusedContainerColor =
                Color.Transparent,


            focusedBorderColor =

                if (isError)

                    ErrorNeonRose

                else

                    TextFieldBorder,



            unfocusedBorderColor =

                if (isError)

                    ErrorNeonRose

                else

                    TextFieldBorder,



            focusedTextColor =
                TextFieldText,


            unfocusedTextColor =
                TextFieldText,


            focusedLabelColor =

                if (isError)

                    ErrorNeonRose

                else

                    TextFieldBorder,



            unfocusedLabelColor =

                if (isError)

                    ErrorNeonRose

                else

                    TextFieldHint,



            focusedTrailingIconColor =
                TextFieldIcon,


            unfocusedTrailingIconColor =
                TextFieldIcon,



            cursorColor =

                if (isError)

                    ErrorNeonRose

                else

                    TextFieldBorder


        )

    )

}
// ---------------- FULL NAME ----------------

@Composable
fun FullNameTextField(

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    isError: Boolean = false

) {

    CommonTextField(

        value = value,

        onValueChange = onValueChange,

        label = "Full name",

        modifier = modifier.height(
            AppDimensions.TextFieldHeight
        ),

        isError = isError

    )

}



// ---------------- USERNAME ----------------

@Composable
fun UsernameTextField(

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    isError: Boolean = false,

    isAvailable: Boolean = true

) {

    CommonTextField(

        value = value,

        onValueChange = onValueChange,

        label = "Username",

        modifier = modifier.height(
            AppDimensions.TextFieldHeight
        ),

        isError = isError,

        showSuccessIcon = isAvailable

    )

}



// ---------------- USERNAME OR EMAIL ----------------
// No green tick icon

@Composable
fun UsernameOrEmailTextField(

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    isError: Boolean = false

) {

    CommonTextField(

        value = value,

        onValueChange = onValueChange,

        label = "Username or Email",

        modifier = modifier.height(
            AppDimensions.TextFieldHeight
        ),

        isError = isError,

        showSuccessIcon = false

    )

}



// ---------------- EMAIL ----------------

@Composable
fun EmailTextField(

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    isError: Boolean = false

) {

    CommonTextField(

        value = value,

        onValueChange = onValueChange,

        label = "Email",

        modifier = modifier.height(
            AppDimensions.TextFieldHeight
        ),

        isError = isError

    )

}



// ---------------- PASSWORD ----------------

@Composable
fun PasswordTextField(

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    isError: Boolean = false

) {

    CommonTextField(

        value = value,

        onValueChange = onValueChange,

        label = "Password",

        modifier = modifier.height(
            AppDimensions.TextFieldHeight
        ),

        isPassword = true,

        isError = isError

    )

}
// ---------------- PREVIEW ----------------

@Preview(
    showBackground = true,
    backgroundColor = 0xFF020613,
    showSystemUi = true
)
@Composable
fun TextFieldPreview() {


    var fullName by remember {

        mutableStateOf("")

    }


    var username by remember {

        mutableStateOf("t.poornaprakash")

    }


    var usernameOrEmail by remember {

        mutableStateOf("")

    }


    var email by remember {

        mutableStateOf("")

    }


    var password by remember {

        mutableStateOf("")

    }



    MaterialTheme {


        Surface(

            modifier = Modifier.fillMaxSize(),

            color = Color(0xFF020613)

        ) {


            Column(

                modifier = Modifier

                    .fillMaxSize()

                    .padding(16.dp)

            ) {



                FullNameTextField(

                    value = fullName,

                    onValueChange = {

                        fullName = it

                    }

                )



                Spacer(

                    modifier = Modifier.height(16.dp)

                )




                UsernameTextField(

                    value = username,

                    onValueChange = {

                        username = it

                    },

                    isAvailable = true

                )



                Spacer(

                    modifier = Modifier.height(16.dp)

                )




                UsernameOrEmailTextField(

                    value = usernameOrEmail,

                    onValueChange = {

                        usernameOrEmail = it

                    }

                )



                Spacer(

                    modifier = Modifier.height(16.dp)

                )




                EmailTextField(

                    value = email,

                    onValueChange = {

                        email = it

                    }

                )



                Spacer(

                    modifier = Modifier.height(16.dp)

                )




                PasswordTextField(

                    value = password,

                    onValueChange = {

                        password = it

                    }

                )


            }

        }

    }

}