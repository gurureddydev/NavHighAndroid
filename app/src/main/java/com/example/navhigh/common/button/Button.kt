package com.example.navhigh.common.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.DarkBackground
import com.example.navhigh.ui.theme.LoginButtonEnd
import com.example.navhigh.ui.theme.LoginButtonMiddle
import com.example.navhigh.ui.theme.LoginButtonStart
import com.example.navhigh.ui.theme.NavHighTheme

@Composable
fun Button(text:String, modifier:Modifier=Modifier, isLoading:Boolean=false, onClick:()->Unit
){
    Box(modifier=modifier
            .fillMaxWidth()
            .height(AppDimensions.ButtonHeight)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(
                brush=Brush.horizontalGradient(
                    colors=listOf(
                        LoginButtonStart,
                        LoginButtonMiddle,
                        LoginButtonEnd
                    )
                )
            )
            .clickable(enabled=!isLoading){
                onClick()
            },
        contentAlignment=Alignment.Center
    ){
        if(isLoading){
            CircularProgressIndicator(
                modifier=Modifier.size(22.dp),
                color=Color.White,
                strokeWidth=3.dp,
                trackColor=Color.Transparent
            )
        }else{
            Text(
                text=text,
                color=Color.White,
                fontSize=AppTypography.ButtonTextSize,
                fontWeight=FontWeight.Normal
            )
        }
    }
}

@Preview(
    showBackground=true,
    showSystemUi=false
)
@Composable
private fun ButtonPreview(){
    NavHighTheme{
        Box(
            modifier=Modifier.background(DarkBackground),
            contentAlignment=Alignment.Center
        ){
            Button(
                text="Next",
                onClick={}
            )
        }
    }
}

@Preview(
    showBackground=true,
    showSystemUi=false
)
@Composable
private fun ButtonLoadingPreview(){
    NavHighTheme{
        Box(
            modifier=Modifier.background(DarkBackground),
            contentAlignment=Alignment.Center
        ){
            Button(
                text="Next",
                isLoading=true,
                onClick={}
            )
        }
    }
}