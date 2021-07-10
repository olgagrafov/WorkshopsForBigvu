package com.olgag.workshopsbigvu.activities

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olgag.workshopsbigvu.R
import com.olgag.workshopsbigvu.activities.ui.theme.WorkshopsBigvuTheme
import com.olgag.workshopsbigvu.model.Workshop
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState


class ComposeWorkshopDetails : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var workshop : Workshop = intent.getParcelableExtra<Workshop>("workshop")!!

        setContent {
            WorkshopsBigvuTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(workshop)
                }
            }
        }
    }
}

@Composable
fun Greeting(get_workshop: Workshop) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier.padding(16.dp),
        )
        {
            Text(text = "I am ${get_workshop.name}",
                style = typography.h6,
                color= colorResource(id = R.color.purple_700),
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline ,
               )

            Spacer(Modifier.height(30.dp))

            var painter = rememberGlidePainter("${get_workshop.image}")
            Box {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.app_name),
                )

                when (painter.loadState) {
                    is ImageLoadState.Loading -> {
                        // Display a circular progress indicator whilst loading
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    is ImageLoadState.Error -> {
                        Image(
                            painterResource(R.drawable.no_pic),
                            contentDescription = null,
                            modifier = Modifier
                                .height(350.dp)
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        }
    }
}
