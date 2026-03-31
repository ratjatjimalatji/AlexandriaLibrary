package com.example.libraryreader.screens.home

import android.R
import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.libraryreader.components.FABContent
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.components.TitleSection
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FABContent() {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ReaderAppBar("Alexandria Library", navController = navController)
            ContentBelowTopAppBar(navController)}
        }

}
@Composable
fun ReadingRightNowArea(books: List<FireBaseBook>, navController: NavController) {

}

@Preview
@Composable
fun ListCard(book: FireBaseBook = FireBaseBook("1", "50 Laws", "Curtis Jackson", "Addition of 48 Laws", "a", listOf("Self Help"), "16 December 2018", 380),
onPressDetails: (String) -> Unit = {})
{
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

Card(shape = RoundedCornerShape(29.dp),
//    colors = Color.White,
//    elevation = 6.dp,
    modifier= Modifier
        .padding(16.dp)
        .height(250.dp)
        .width(200.dp)
        .clickable{ onPressDetails(book.id.toString())}
            ){
    Column(modifier = Modifier.width(screenWidth.dp - (spacing*2)),
        horizontalAlignment = Alignment.Start){
        Row(horizontalArrangement = Arrangement.Center) {
            Image(painter = rememberImagePainter(data = ""),
                contentDescription = "Book Image",
                modifier = Modifier.height(140.dp)
                    .width(100.dp)
                    .padding(4.dp))
            Spacer(modifier = Modifier.width(50.dp))

            Column(modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
                Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "Thumbs up", modifier = Modifier.padding(bottom = 1.dp))
                BookRating(score = 4.5)


            }
        }
        Text(text = "Book title", modifier= Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Clip)

        Text(text = "Author name",
            style = MaterialTheme.typography.bodyMedium,
            modifier= Modifier.padding(4.dp),)
    }
}

}

@Composable
fun BookRating(score: Double = 4.5) {
   Surface(modifier = Modifier
       .padding(4.dp)
       .height(70.dp),
       shape = RoundedCornerShape(56.dp),
       shadowElevation = 6.dp,
   color = Color.White){
       Column(modifier = Modifier.padding(4.dp)){
           Icon(imageVector = Icons.Default.StarBorder,
               modifier = Modifier.padding(3.dp),
                   contentDescription = "Favourite")
           Text(text = score.toString(),  style = MaterialTheme.typography.bodyLarge)
       }
   }
}

@Composable
fun ContentBelowTopAppBar(navController: NavController) {

    val currentUsername = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email!!.split("@")?.get(0) else "N/A"
    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly,

    )
    {

        Row(modifier = Modifier.align(alignment = Alignment.Start )) {
            TitleSection(label = "Reading List", modifier = Modifier.align(alignment = Alignment.CenterVertically, ))
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
    }
}

