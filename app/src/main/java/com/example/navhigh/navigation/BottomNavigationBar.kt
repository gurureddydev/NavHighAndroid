package com.example.navhigh.navigation


import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


import com.example.navhigh.ui.create.CreateScreen
import com.example.navhigh.ui.email.EmailScreen
import com.example.navhigh.ui.home.HomeFeedScreen
import com.example.navhigh.ui.login.LoginScreen
import com.example.navhigh.ui.otp.OtpScreen
import com.example.navhigh.ui.password.PasswordScreen
import com.example.navhigh.ui.search.SearchScreen
import com.example.navhigh.ui.splashscreen.SplashScreen
import com.example.navhigh.ui.name.FullNameScreen
import com.example.navhigh.ui.name.UserNameScreen

import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.NavHighTheme



@Composable
fun MainScreen(){



    var showSplash by remember {

        mutableStateOf(true)

    }




    var currentRoute by remember {

        mutableStateOf("Login")

    }





    var userEmail by remember {

        mutableStateOf("")

    }





    var userFullName by remember {

        mutableStateOf("")

    }







    if(showSplash){



        SplashScreen(

            onLoadingFinished = {


                showSplash = false

                currentRoute = "Login"


            }

        )


        return

    }








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









    if(currentRoute == "Email"){



        EmailScreen(


            onBackClick = {


                currentRoute = "Login"


            },


            onLoginClick = {


                currentRoute = "Login"


            },


            onNextClick = { email ->



                userEmail = email


                currentRoute = "Otp"



            }


        )



        return

    }









    if(currentRoute == "Otp"){



        OtpScreen(


            email = userEmail,


            onBackClick = {


                currentRoute = "Email"


            },


            onNextClick = {



                currentRoute = "Password"


            }


        )



        return

    }
    if(currentRoute == "Password"){



        PasswordScreen(



            onBackClick = {



                currentRoute = "Otp"



            },




            onNextClick = {



                currentRoute = "FullName"



            },




            onLoginClick = {



                currentRoute = "Login"



            }



        )



        return

    }









    if(currentRoute == "FullName"){



        FullNameScreen(



            onBackClick = {



                currentRoute = "Password"



            },




            onNextClick = { name ->



                userFullName = name



                currentRoute = "Username"



            },




            onLoginClick = {



                currentRoute = "Login"



            },




            onContinueClick = {



                currentRoute = "FullName"



            }



        )



        return

    }









    if(currentRoute == "Username"){



        UserNameScreen(



            fullName = userFullName,




            onBackClick = {



                currentRoute = "FullName"



            },




            onNextClick = {



                currentRoute = "Home"



            },




            onLoginClick = {



                currentRoute = "Login"



            },




            onContinueClick = {



                currentRoute = "Username"



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



    ){ innerPadding ->







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




                    SearchScreen()




                }









                "Create" -> {




                    CreateScreen(



                        onCloseClick = {



                            currentRoute = "Home"



                        },



                        onDraftsClick = {}



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

    title:String

){



    Box(


        modifier = Modifier.fillMaxSize(),



        contentAlignment = Alignment.Center



    ){



        Text(



            text = title,



            color = Color.White,



            fontSize = 20.sp



        )



    }



}
@Composable
fun BottomNavigationBar(

    currentRoute:String,

    onRouteSelected:(String)->Unit

){



    Column(

        modifier = Modifier

            .fillMaxWidth()

            .background(

                Color(0xFF040A18)

            )

            .windowInsetsPadding(

                WindowInsets.navigationBars

            )

    ){



        HorizontalDivider(

            thickness = AppDimensions.BottomNavigationDividerThickness,

            color = Color(0xFF102040)

        )





        Row(

            modifier = Modifier

                .fillMaxWidth()

                .height(

                    AppDimensions.BottomNavigationHeight

                )

                .padding(

                    horizontal = AppDimensions.BottomNavigationHorizontalPadding

                ),


            verticalAlignment = Alignment.CenterVertically

        ){





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







            NavItem(

                modifier = Modifier.weight(1f),

                label = "Create",

                selected = currentRoute == "Create",

                iconOutlined = Icons.Outlined.Add,

                iconFilled = Icons.Filled.Add,

                onClick = {


                    onRouteSelected("Create")


                }

            )








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









@Composable
fun NavItem(

    modifier: Modifier = Modifier,

    label:String,

    selected:Boolean,

    iconOutlined:ImageVector,

    iconFilled:ImageVector,

    badgeCount:Int = 0,

    onClick:()->Unit

){





    val animatedContentColor by animateColorAsState(



        targetValue = if(selected)

            Color.White

        else

            Color(0xFF8A95B5),



        animationSpec = tween(250),

        label = "Color"



    )








    Column(

        modifier = modifier

            .fillMaxHeight()

            .clickable(

                interactionSource = remember {

                    MutableInteractionSource()

                },

                indication = null

            ){



                onClick()



            },

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ){





        BadgedBox(

            badge = {



                if(badgeCount > 0){



                    Badge {



                        Text(

                            text = badgeCount.toString(),

                            fontSize = AppTypography.NotificationBadgeTextSize

                        )



                    }



                }



            }



        ){





            Crossfade(

                targetState = selected,

                animationSpec = tween(150),

                label = "Icon"

            ){



                Icon(



                    imageVector = if(it)

                        iconFilled

                    else

                        iconOutlined,



                    contentDescription = label,



                    tint = animatedContentColor,



                    modifier = Modifier.size(

                        AppDimensions.BottomNavigationIconSize

                    )



                )



            }



        }








        Spacer(

            modifier = Modifier.height(

                AppDimensions.BottomNavigationItemSpacing

            )

        )







        Text(



            text = label,



            color = animatedContentColor,



            fontSize = AppTypography.BottomNavigationLabelSize,



            fontWeight = FontWeight.Medium



        )



    }



}









@Preview(

    showBackground = true,

    showSystemUi = true,

    device = "spec:width=412dp,height=915dp,dpi=420"

)

@Composable
fun MainScreenPreview(){



    NavHighTheme{



        MainScreen()



    }



}