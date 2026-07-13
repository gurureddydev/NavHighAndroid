package com.example.navhigh.common.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import com.example.navhigh.common.dialoguebox.AlreadyHaveAccountDialog
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.NavHighTheme



@Composable
fun AlreadyHaveAccount(

    onLogin: () -> Unit,

    onContinue: () -> Unit

) {


    var showDialog by remember {

        mutableStateOf(false)

    }



    Text(

        text = "I already have an account",

        color = ForgotPasswordBlue,

        fontSize = AppTypography.DialogButtonSize,

        fontWeight = FontWeight.SemiBold,

        textAlign = TextAlign.Center,


        modifier = Modifier

            .fillMaxWidth()

            .padding(

                bottom = AppDimensions.BottomSpace

            )

            .clickable {


                showDialog = true


            }

    )





    if(showDialog){


        AlreadyHaveAccountDialog(


            onDismiss = {


                showDialog = false


            },


            onLogin = {


                showDialog = false

                onLogin()


            },


            onContinue = {


                showDialog = false

                onContinue()


            }

        )


    }


}





@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun AlreadyHaveAccountPreview(){


    NavHighTheme{


        AlreadyHaveAccount(

            onLogin = {},

            onContinue = {}

        )


    }

}