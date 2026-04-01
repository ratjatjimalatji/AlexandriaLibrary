package com.example.libraryreader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.navigation.ReaderScreens
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
    imeAction: ImeAction = ImeAction.Next,
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
        )
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
                if (icon != null){
                    Icon(imageVector = icon, contentDescription = "back arrow",tint = Color.Blue, modifier = Modifier.clickable{ onBackArrowClicked.invoke() })
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
            if (showIcon)
            {Column {
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
        modifier = modifier.padding(5.dp)
    ) {
        Column {
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
        shape = RoundedCornerShape(50.dp),
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


@Preview
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
        color = Color.Black
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


@Preview
@Composable
fun ListCard(
    book: FireBaseBook = FireBaseBook(
        "1",
        "Money Laws",
        "Curtis Jackson",
        "Addition of 48 Laws",
        "a",
        listOf("Self Help"),
        "16 December 2018",
        380
    ),
    onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
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
                    painter = rememberAsyncImagePainter(
                        model = "data:image/png;base64,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"
                    ),
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
                Text(
                    text = book.title, modifier = Modifier.padding(vertical = 4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Clip
                )

                Text(
                    text = book.authors,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton("Reading", 70)
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
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            Icon(
                imageVector = Icons.Default.StarBorder,
                modifier = Modifier.padding(3.dp),
                contentDescription = "Favourite"
            )
            Text(text = score.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
@Composable
fun ContentBelowTopAppBar(navController: NavController) {

    var listOfBooks = listOf(
        FireBaseBook("1", "50 Laws", "Curtis Jackson", "Addition of 48 Laws", "a", categories = listOf("Self Help"), publishedDate = "28/04/1948", pageCount = 380),
        FireBaseBook("2", "Dorian Grey", "Charles Dickens", "Dichotomy of man", "a", categories = listOf("Love Story"), publishedDate = "24/08/1868", pageCount = 200),
        FireBaseBook("3", "AI Laws", "Curtis Jackson", "Addition of 48 Laws", "a", categories = listOf("Self Help"), publishedDate = "28/04/1948", pageCount = 380),
        FireBaseBook("4", "Extreme ownership", "Charles Dickens", "Dichotomy of man", "a", categories = listOf("Love Story"), publishedDate = "24/08/1868", pageCount = 200),

        )
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUsername = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email!!.split("@")?.get(0) else "N/A"
    Column(
        //Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top,

        )
    {

        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(
                label = "Reading List",
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
        ListCard()
        TitleSection(Modifier, "Reading list")

        BookListArea(listOfBooks, navController)
    }


}

@Composable
fun BookListArea(listOfBooks: List<FireBaseBook>, navController: NavController) {

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
    }}}

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

