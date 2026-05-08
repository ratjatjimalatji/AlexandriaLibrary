package com.example.libraryreader.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import com.example.libraryreader.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.navigation.ReaderScreens
import com.example.libraryreader.screens.home.HomeScreenViewModel
import com.example.libraryreader.screens.home.ReadingRightNowArea
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ReaderLogo(modifier: Modifier = Modifier, color: Color = Color.Blue) {
    Text(
        text = "Alexandria lib",
        style = MaterialTheme.typography.displayMedium,
        fontWeight = FontWeight.Bold,
        color = color
    )
}


@Preview
@Composable
fun LoginSignUpUserForm(
    loading: Boolean = false, registerScreen: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = remember { FocusRequester() } //FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        //.height(350.dp)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (registerScreen) {
            Text(
                text = stringResource(R.string.create_account),
                modifier = Modifier.padding(4.dp)
            )
        } else {
            Text(text = "")
        }

        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus() //Moves cursor to the password field when imeButton pressed
            })

        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,//ToDO,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })

        SubmitButton(
            textId = if (registerScreen) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { TogglePasswordVisibility(passwordVisibility = passwordVisibility) }, //Eye icons shows/hides password
        keyboardActions = onAction,
    )
}


@Composable
fun TogglePasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icon(
            imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = "Toggle Password Visibility"
        )
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        placeHolderId = "example@domain.com",
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    placeHolderId: String = "",
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        placeholder = { Text(text = placeHolderId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp) // Slightly more padding for a better touch target
            .fillMaxWidth()
            .height(50.dp), // Fixed height prevents the button from "jumping" when loading
        enabled = !loading && validInputs,
        shape = RoundedCornerShape(15.dp)
    ) {
        if (loading) {
            // Circular looks much better inside a button than Linear
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                color = MaterialTheme.colorScheme.onPrimary, // Matches button text color
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = textId,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    icon: ImageVector? = null,
    title: String,
    showIcon: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
                title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showIcon) {
                    Icon(
                        imageVector = Icons.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .scale(.5f)
                    )
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "back arrow",
                        tint = Color.Blue,
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = title,
                    color = Color.Blue.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.7f)
                    ),
                )

            }
        },

        actions = {
            if (showIcon) {
                Column{
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut().run {
                            navController.navigate(ReaderScreens.LoginScreen.name)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.Blue.copy(alpha = 0.9f),
                            modifier = Modifier.fillMaxSize(0.8f)

                        )
                    }
                }
            }
        },
    )
}


@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(
        modifier = modifier.padding(horizontal = 5.dp)

    ) {
        Column(modifier = Modifier.background(Color.White)){
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Color.LightGray
            )

            Text(
                text = label,
                fontSize = 20.sp,
                style = TextStyle(
                    fontStyle = FontStyle.Normal
                ),
                textAlign = TextAlign.Left
            )
        }
    }
}
@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.Blue,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book to reading list",
            tint = Color.White
        )
    }
}


@Composable
fun RoundedButton(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                topStartPercent = radius
            )
        ),
        color = Color.LightGray.copy(alpha = 0.9f)
    )
    {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp))
        }
    }
}



@Composable
fun ListCard(
    book: FireBaseBook
    ,onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp)
            .clickable { onPressDetails(book.id.toString()) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),

            horizontalAlignment = Alignment.Start
        ) {
            //Image & rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberImagePainter(data = book.photoUrl.toString()),
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Thumbs up",
                        modifier = Modifier
                            .padding(end = 12.dp, bottom = 1.dp)
                    )
                    BookRating(score = 4.5)
                }
            }
            //Book details below Image and rating
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                book?.title?.let {
                    Text(
                        text = it, modifier = Modifier.padding(vertical = 4.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Clip
                    )
                }

                book.authors?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton("Reading", 10)
            }
        }
    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color.White
    ) {
        Box() {
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color.Blue.copy(alpha = 0.9f),
                    modifier = Modifier.padding(3.dp),
                    contentDescription = "Favourite"
                )
                Text(text = score.toString(), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ContentBelowTopAppBar(navController: NavController, viewModel: HomeScreenViewModel) {

    //var listOfBooks = emptyList<FireBaseBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

//    if(!viewModel.data.value.data.isNullOrEmpty()){
//        listOfBooks = viewModel.data.value?.data!!.toList()!!.filter { fireBaseBook ->
//            fireBaseBook.userId == currentUser?.uid.toString()
//        }
//        Log.d("Books", "ContentBelowTopAppBar: ${listOfBooks.toString()}")
//    }
    var listOfBooks = listOf(
        FireBaseBook(
            "1",
            "50 Laws",
            "Curtis Jackson",
            "Addition of 48 Laws",
            "http://books.google.com/books/content?id=m4uP0ujpXtUC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
            categories = "Self Help",
            publishedDate = "28/04/1948",
            pageCount = "380"
        ),
        FireBaseBook(
            "2",
            "Dorian Grey",
            "Charles Dickens",
            "Dichotomy of man",
            "a",
            categories = "Love Story",
            publishedDate = "24/08/1868",
            pageCount = "200"
        ),
        FireBaseBook(
            "3",
            "AI Laws",
            "Curtis Jackson",
            "Addition of 48 Laws",
            "a",
            categories = "Self Help",
            publishedDate = "28/04/1948",
            pageCount = "380"
        ),
        FireBaseBook(
            "4",
            "Extreme ownership",
            "Charles Dickens",
            "Dichotomy of man",
            "a",
            categories = "Love Story",
            publishedDate = "24/08/1868",
            pageCount = "200"
        )
        )

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUsername = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email!!.split("@")?.get(0) else "N/A"
    Column(
        verticalArrangement = Arrangement.Top,

        )
    {

        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(
                label = "Current Reading",
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
//                .align(alignment = Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable { navController.navigate(ReaderScreens.StatsScreen.name) }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryFixedVariant)
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    text = currentUsername.toString(),
                    style = TextStyle(
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        ReadingRightNowArea(books = listOf(), navController = navController)

        TitleSection(Modifier, "Reading list")

        ReadingListArea(listOfBooks, navController)
    }


}

@Composable
fun ReadingListArea(listOfBooks: List<FireBaseBook>, navController: NavController) {

    HorizontalScrollableComponent(listOfBooks) {
        //Todo: onClick Action navigate to details screen}
        @Composable
        fun BookRating(score: Double = 4.5) {
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 4.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        modifier = Modifier.padding(3.dp),
                        contentDescription = "Favourite"
                    )
                    Text(text = score.toString(), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<FireBaseBook>,
    onCardPressed: (String) -> Unit
) {
    val scrollableSate = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollableSate)
    ) {

        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}

