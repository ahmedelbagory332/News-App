package com.example.presentation.screens.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.screens.favorites.FavoritesActivity
import com.example.presentation.screens.onboardingScreen.countries
import com.example.presentation.screens.search.component.SearchArticle
import com.example.presentation.screens.search.component.UserFavCategories
import com.example.presentation.theme.NewsAppTheme
import com.example.presentation.theme.darkWhite
import com.example.presentation.theme.grey
import com.example.presentation.utils.ErrorHolder
import com.example.presentation.utils.LoadingIndicator
import com.example.presentation.utils.PlaceHolder
import com.example.presentation.utils.TopBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = grey
                ) {
                    SearchScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchScreen() {
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .background(darkWhite)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Surface(shadowElevation = 3.dp) {
                TopBar(
                    title = stringResource(R.string.search_on_articles),
                    menu = {
                    }
                )
            }
            LaunchedEffect(Unit) {
                delay(200)
                focusRequester.requestFocus()
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .focusRequester(focusRequester),
                value = viewModel.query.value,
                onValueChange = { viewModel.query.value = it },
                label = { Text(stringResource(R.string.enter_article_name)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        viewModel.showPlaceHolder.value = false
                        countries[viewModel.onBoardingStatus.value.country]?.let {
                            viewModel.getSearchedArticles(
                                it,
                                viewModel.selectedCategory.value,
                                viewModel.query.value
                            )
                        }

                    }

                )
            )
            if (viewModel.showPlaceHolder.value) {
                PlaceHolder(
                    text = getString(R.string.search_on_articles),
                    painter = painterResource(id = R.drawable.search)
                )
            } else {
                Box {}
            }
            UserFavCategories(viewModel) { category ->
                if (viewModel.query.value.isEmpty()) {
                    Toast.makeText(
                        this@SearchActivity,
                        getString(R.string.must_search_first), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    countries[viewModel.onBoardingStatus.value.country]?.let {
                        viewModel.getSearchedArticles(
                            it,
                            category,
                            viewModel.query.value
                        )
                    }
                }
            }
            Articles(viewModel)

        }


    }

    @Composable
    fun Articles(searchViewModel: SearchViewModel) {
        if (searchViewModel.headLines.value.isLoading) {
            LoadingIndicator()
        } else if (searchViewModel.headLines.value.headLines.isNotEmpty()) {

            LazyColumn {
                items(searchViewModel.headLines.value.headLines) { article ->
                    SearchArticle(article, searchViewModel)
                }
            }
        } else if (searchViewModel.headLines.value.error.isNotEmpty()) {
            ErrorHolder(text = searchViewModel.headLines.value.error)
        } else if (searchViewModel.headLines.value.headLines.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_result),
                    contentDescription = "no_result",
                    modifier = Modifier.size(width = 250.dp, height = 250.dp)
                )
            }
        }

    }


    private fun startFavoritesActivity() {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }


}


 
