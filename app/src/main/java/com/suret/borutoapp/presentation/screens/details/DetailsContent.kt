package com.suret.borutoapp.presentation.screens.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.suret.borutoapp.R
import com.suret.borutoapp.domain.model.Hero
import com.suret.borutoapp.presentation.components.InfoBox
import com.suret.borutoapp.presentation.components.OrderedList
import com.suret.borutoapp.ui.theme.*
import com.suret.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.suret.borutoapp.util.Constants.BASE_URL
import com.suret.borutoapp.util.Constants.MIN_BACKGROUND_IMAGE_HEIGHT
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun DetailsContent(
    navController: NavController,
    selectedHero: Hero?,
    colors: Map<String, String>
) {
    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#FFFFFF") }

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.apply {
            setStatusBarColor(
                color = Color(parseColor(darkVibrant))
            )
            setNavigationBarColor(
                color = Color(parseColor(darkVibrant))
            )
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        floatingActionButton = null,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let { hero ->
                BottomSheetContent(
                    selectedHero = hero,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant))
                )
            }
        },
        content = {
            selectedHero?.let { hero ->
                BackgroundContent(
                    heroImage = hero.image,
                    imageFraction = currentSheetFraction,
                    onCloseClicked = { navController.popBackStack() },
                    backgroundColor = Color(parseColor(darkVibrant))
                )
            }
        })
}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$BASE_URL${heroImage}")
                .crossfade(true)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .build(),
            contentDescription = stringResource(R.string.hero_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = SMALL_PADDING),
                onClick = { onCloseClicked() }) {
                Icon(
                    modifier = Modifier.size(INFO_BOX_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        Timber.d("ASdsaasfsaf fraction $fraction")
        Timber.d("ASdsaasfsaf targetValue $targetValue")
        Timber.d("ASdsaasfsaf currentValue $currentValue")

        return when {
            currentValue == Collapsed && targetValue == Collapsed -> 1f
            currentValue == Expanded && targetValue == Expanded -> 0f
            currentValue == Collapsed && targetValue == Expanded -> 1f - fraction
            currentValue == Expanded && targetValue == Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_BOX_ICON_SIZE)
                    .weight(weight = 2f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(weight = 8f),
                text = selectedHero.name,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum",
            rating = 4.5,
            power = 0,
            month = "Oct",
            day = "1st",
            family = listOf("Minato", "Kushina", "Boruto", "Himawari"),
            abilities = listOf("Sage Mode", "Shadow Clone", "Rasengan"),
            natureTypes = listOf("Earth", "Wind")
        )

    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomSheetContentDarkPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum",
            rating = 4.5,
            power = 0,
            month = "Oct",
            day = "1st",
            family = listOf("Minato", "Kushina", "Boruto", "Himawari"),
            abilities = listOf("Sage Mode", "Shadow Clone", "Rasengan"),
            natureTypes = listOf("Earth", "Wind")
        )

    )
}