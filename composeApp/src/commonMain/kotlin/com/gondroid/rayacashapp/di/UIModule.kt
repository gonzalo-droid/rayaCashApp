package com.gondroid.rayacashapp.di

import com.gondroid.rayacashapp.ui.convert.ConvertScreenViewModel
import com.gondroid.rayacashapp.ui.home.HomeScreenViewModel
import com.gondroid.rayacashapp.ui.transaction.TransactionScreenViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::TransactionScreenViewModel)
    viewModelOf(::ConvertScreenViewModel)
}