package com.example.navhigh.navigation


import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.example.navhigh.R
import com.example.navhigh.ui.create.CreateScreen
import com.example.navhigh.ui.home.HomeFeedScreen
import com.example.navhigh.ui.login.LoginScreen
import com.example.navhigh.ui.splashscreen.SplashScreen
import com.example.navhigh.ui.email.EmailScreen
import com.example.navhigh.ui.otp.OtpScreen




@Composable
fun MainScreen() {



    var showSplash by remember {

        mutableStateOf(true)

    }



    var currentRoute by remember {

        mutableStateOf("Login")

    }




    // STORE EMAIL FROM EMAIL SCREEN

    var userEmail by remember {

        mutableStateOf("")

    }







    // SPLASH SCREEN

    if(showSplash){


        SplashScreen(

            onLoadingFinished = {


                showSplash = false

                currentRoute = "Login"


            }


        )


        return

    }









    // LOGIN SCREEN

    if(currentRoute == "Login"){



        LoginScreen(


            onCreateAccountClick = {


                currentRoute = "Email"


            },



            onLoginSuccess = {


                currentRoute = "Home"


            }



        )


        return


    }









    // EMAIL SCREEN


    if(currentRoute == "Email"){



        EmailScreen(



            onBackClick = {


                currentRoute = "Login"


            },



            onLoginClick = {


                currentRoute = "Login"


            },



            onNextClick = { enteredEmail ->



                userEmail = enteredEmail


                currentRoute = "Otp"



            }



        )


        return


    }









    // OTP SCREEN


    if(currentRoute == "Otp"){



        OtpScreen(


            email = userEmail,



            onBackClick = {


                currentRoute = "Email"


            },



            onNextClick = {



                currentRoute = "Home"


            }



        )


        return


    }









    Scaffold(


        containerColor = Color(0xFF020817),



        bottomBar = {



            BottomNavigationBar(


                currentRoute = currentRoute,



                onRouteSelected = { newRoute ->



                    currentRoute = newRoute


                }


            )


        }


    ) { innerPadding ->





        Box(


            modifier = Modifier

                .padding(innerPadding)

                .fillMaxSize()


        ){



            when(currentRoute){



                "Home" -> {



                    HomeFeedScreen()



                }





                "Search" -> {



                    PlaceholderScreen(

                        title = "Discover Screen"

                    )



                }






                "Create" -> {



                    CreateScreen(



                        onCloseClick = {



                            currentRoute = "Home"


                        },



                        onDraftsClick = {



                        }



                    )


                }







                "Notifications" -> {



                    PlaceholderScreen(

                        title = "Notifications Screen"

                    )



                }







                "Profile" -> {



                    PlaceholderScreen(

                        title = "Profile Screen"

                    )



                }




            }



        }



    }


}
@Composable
fun PlaceholderScreen(
    title: String
) {


    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.Center

    ) {


        Text(

            text = title,

            color = Color.White,

            fontSize = 20.sp

        )


    }


}






@Composable
fun BottomNavigationBar(

    currentRoute: String,

    onRouteSelected: (String) -> Unit = {}

) {



    Column(

        modifier = Modifier


            .fillMaxWidth()


            .background(

                Color(0xFF040A18)

            )


            .windowInsetsPadding(

                WindowInsets.navigationBars

            )

    ) {





        HorizontalDivider(


            thickness = 1.dp,


            color = Color(0xFF102040)


        )






        Box(


            modifier = Modifier


                .fillMaxWidth()


                .height(76.dp),



            contentAlignment = Alignment.Center


        ) {





            Row(


                modifier = Modifier


                    .fillMaxSize()


                    .padding(horizontal = 5.dp),



                verticalAlignment = Alignment.CenterVertically


            ) {





                NavItem(


                    modifier = Modifier.weight(1f),



                    label = "Home",



                    selected = currentRoute == "Home",



                    iconOutlined = Icons.Outlined.Home,



                    iconFilled = Icons.Filled.Home,



                    onClick = {



                        onRouteSelected("Home")



                    }



                )









                NavItem(



                    modifier = Modifier.weight(1f),



                    label = "Discover",



                    selected = currentRoute == "Search",



                    iconOutlined = Icons.Outlined.Explore,



                    iconFilled = Icons.Filled.Explore,



                    onClick = {



                        onRouteSelected("Search")



                    }



                )











                Column(



                    horizontalAlignment = Alignment.CenterHorizontally,



                    verticalArrangement = Arrangement.Center,



                    modifier = Modifier


                        .weight(1f)


                        .fillMaxHeight()


                        .clickable(


                            interactionSource = remember {


                                MutableInteractionSource()


                            },


                            indication = null



                        ) {



                            onRouteSelected("Create")



                        }



                ) {





                    Image(



                        painter = painterResource(


                            id = R.drawable.plus


                        ),



                        contentDescription = "Create",



                        modifier = Modifier.size(30.dp)



                    )





                }












                NavItem(



                    modifier = Modifier.weight(1f),



                    label = "Notifications",



                    selected = currentRoute == "Notifications",



                    iconOutlined = Icons.Outlined.Notifications,



                    iconFilled = Icons.Filled.Notifications,



                    badgeCount = 3,



                    onClick = {



                        onRouteSelected("Notifications")



                    }



                )









                NavItem(



                    modifier = Modifier.weight(1f),



                    label = "Profile",



                    selected = currentRoute == "Profile",



                    iconOutlined = Icons.Outlined.Person,



                    iconFilled = Icons.Filled.Person,



                    onClick = {



                        onRouteSelected("Profile")



                    }



                )





            }



        }



    }



}








@Composable
fun NavItem(


    modifier: Modifier = Modifier,


    label: String,


    selected: Boolean,


    iconOutlined: ImageVector,


    iconFilled: ImageVector,


    badgeCount: Int = 0,


    onClick: () -> Unit = {}


) {



    val animatedContentColor by animateColorAsState(



        targetValue = if(selected)


            Color.White


        else


            Color(0xFF8A95B5),



        animationSpec = tween(250),



        label = "TabColorAnimation"


    )







    Column(



        horizontalAlignment = Alignment.CenterHorizontally,



        verticalArrangement = Arrangement.Center,



        modifier = modifier



            .fillMaxHeight()



            .clickable(



                interactionSource = remember {



                    MutableInteractionSource()



                },



                indication = null



            ) {



                onClick()



            }



    ) {






        BadgedBox(



            badge = {



                if(badgeCount > 0){



                    Badge(



                        containerColor = Color(0xFF1D9FFF),



                        contentColor = Color.White



                    ){



                        Text(



                            text = badgeCount.toString(),



                            fontSize = 9.sp,



                            fontWeight = FontWeight.Bold



                        )



                    }



                }



            }



        ){





            Crossfade(



                targetState = selected,



                animationSpec = tween(150),



                label = "IconCrossfade"



            ){ isSelected ->





                Icon(



                    imageVector = if(isSelected)



                        iconFilled



                    else



                        iconOutlined,



                    contentDescription = label,



                    tint = animatedContentColor,



                    modifier = Modifier.size(20.dp)



                )



            }



        }







        Spacer(



            modifier = Modifier.height(4.dp)



        )








        Text(



            text = label,



            color = animatedContentColor,



            fontSize = 10.sp,



            fontWeight = if(selected)



                FontWeight.SemiBold



            else



                FontWeight.Medium



        )





    }





}








@Preview(

    showBackground = true,

    device = "spec:width=390dp,height=844dp,dpi=440"

)

@Composable
fun MainScreenPreview(){


    MaterialTheme {


        MainScreen()


    }


}