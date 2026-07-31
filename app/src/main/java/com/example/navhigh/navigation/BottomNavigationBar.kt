package com.example.navhigh.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.getstarted.NavHighLogoScreen
import com.example.navhigh.ui.agree.TermsAgreementScreen
import com.example.navhigh.ui.birthday.BirthdayScreen
import com.example.navhigh.ui.contacts.ContactsSyncScreen
import com.example.navhigh.ui.accountsetup.AccountSetupScreen
import com.example.navhigh.ui.accountselection.AccountPrivacyScreen
import com.example.navhigh.ui.bottombar.BottomNavigationBar
import com.example.navhigh.ui.create.CreateScreen
import com.example.navhigh.ui.email.EmailScreen
import com.example.navhigh.ui.follow.FollowSuggestionsScreen
import com.example.navhigh.ui.follow.SuggestedUser
import com.example.navhigh.ui.home.HomeFeedScreen
import com.example.navhigh.ui.login.LoginScreen
import com.example.navhigh.ui.name.FullNameScreen
import com.example.navhigh.ui.name.UserNameScreen
import com.example.navhigh.ui.otp.OtpScreen
import com.example.navhigh.ui.password.PasswordScreen
import com.example.navhigh.ui.search.SearchScreen
import com.example.navhigh.ui.splashscreen.SplashScreen
import com.example.navhigh.ui.theme.NavHighTheme


@Composable
fun MainScreen() {

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

    if (showSplash) {

        SplashScreen(
            onLoadingFinished = {
                showSplash = false
                currentRoute = "Login"
            }
        )

        return
    }

    if (currentRoute == "Login") {

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

    if (currentRoute == "Email") {

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

    if (currentRoute == "Otp") {

        OtpScreen(
            email = userEmail,
            onBackClick = {
                currentRoute = "Email"
            },
            onNextClick = {
                currentRoute = "Password"
            },
            onChangeEmailClick = {
                currentRoute = "Email"
            }
        )

        return
    }

    if (currentRoute == "Password") {

        PasswordScreen(
            onBackClick = {
                currentRoute = "Otp"
            },
            onNextClick = {
                currentRoute = "Birthday"
            },
            onLoginClick = {
                currentRoute = "Login"
            }
        )

        return
    }

    if (currentRoute == "Birthday") {

        BirthdayScreen(
            onBack = {
                currentRoute = "Password"
            },
            onNext = { year, month, day ->
                currentRoute = "FullName"
            },
            onLogin = {
                currentRoute = "Login"
            },
            onContinue = {
                currentRoute = "Birthday"
            }
        )

        return
    }

    if (currentRoute == "FullName") {

        FullNameScreen(
            onBackClick = {
                currentRoute = "Birthday"
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

    if (currentRoute == "Username") {

        UserNameScreen(
            fullName = userFullName,
            onBackClick = {
                currentRoute = "FullName"
            },
            onNextClick = {
                currentRoute = "TermsAgreement"
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

    if (currentRoute == "TermsAgreement") {

        TermsAgreementScreen(
            onBackClick = {
                currentRoute = "Username"
            },
            onLearnMoreClick = {
                // TODO: open Learn more content
            },
            onTermsClick = {
                // TODO: open Terms content
            },
            onPrivacyPolicyClick = {
                // TODO: open Privacy Policy content
            },
            onCookiesPolicyClick = {
                // TODO: open Cookies Policy content
            },
            onAgreeClick = {
                currentRoute = "GetStarted"
            },
            onLogin = {
                currentRoute = "Login"
            },
            onContinue = {
                currentRoute = "TermsAgreement"
            }
        )

        return
    }

    if (currentRoute == "GetStarted") {

        NavHighLogoScreen(
            fullName = userFullName,
            onNextClick = {
                currentRoute = "ContactsSync"
            },
            onLoginClick = {
                currentRoute = "Login"
            }
        )

        return
    }

    if (currentRoute == "ContactsSync") {

        ContactsSyncScreen(
            onNextClick = {
                currentRoute = "AccountSetup"
            },
            onSkipClick = {
                currentRoute = "AccountSetup"
            },
            onLearnMoreClick = {
                // TODO: open Learn more content for contacts syncing
            }
        )

        return
    }

    if (currentRoute == "AccountSetup") {

        AccountSetupScreen(
            onSetupFinished = {
                // After the 6s setup spinner, go to Account Privacy first,
                // then Privacy screen's own "Next" button takes the user to Follow
                currentRoute = "AccountPrivacy"
            }
        )

        return
    }

    if (currentRoute == "AccountPrivacy") {

        AccountPrivacyScreen(
            onBackClick = {
                currentRoute = "AccountSetup"
            },
            onNextClick = { selectedPrivacy ->
                // TODO: persist selectedPrivacy (e.g. save to user profile / backend) if needed
                currentRoute = "Follow"
            }
        )

        return
    }

    if (currentRoute == "Follow") {

        FollowSuggestionsScreen(
            // TODO: replace this hardcoded list with your real data source (ViewModel / API)
            users = listOf(
                SuggestedUser(id = "1", displayName = "Sai lakshmi Narayana", username = "karanam_sai_26"),
                SuggestedUser(id = "2", displayName = "k.sravan", username = "k_sravan_kumar_"),
                SuggestedUser(id = "3", displayName = "Sai Reddy", username = "iam_saikumar_reddy"),
                SuggestedUser(id = "4", displayName = "tharun chowdary", username = "tharun_chowdary_15"),
                SuggestedUser(id = "5", displayName = "EasyhomeShiftingbangalore", username = "ehomeshifting"),
                SuggestedUser(id = "6", displayName = "T Nitish Timpuraram", username = "nitish_t")
            ),
            onBackClick = {
                currentRoute = "AccountPrivacy"
            },
            onSkipClick = {
                currentRoute = "Home"
            },
            onFollowClick = { selectedIds ->
                // TODO: persist selectedIds (follow API call / ViewModel)
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
        ) {

            when (currentRoute) {

                "Home" -> {

                    // HomeFeedScreen already contains its own top bar,
                    // For You / Following / Trending tabs, and the swipeable
                    // reels pager in the feed area -- nothing extra needed here.
                    HomeFeedScreen(
                        onNavigate = { route ->

                            when {
                                route == "search_route" -> {
                                    currentRoute = "Search"
                                }
                                route == "notifications_route" -> {
                                    currentRoute = "Notifications"
                                }
                                route.startsWith("story_route/") -> {
                                    // TODO: navigate to the profile/story screen for this user
                                }
                                route.startsWith("comments_route/") -> {
                                    // TODO: navigate to the comments screen for this post
                                }
                                else -> {
                                    // TODO: handle any other dynamic routes if needed
                                }
                            }

                        }
                    )

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
                    PlaceholderScreen(title = "Notifications Screen")
                }

                "Profile" -> {
                    PlaceholderScreen(title = "Profile Screen")
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


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun MainScreenPreview() {

    NavHighTheme {
        MainScreen()
    }

}