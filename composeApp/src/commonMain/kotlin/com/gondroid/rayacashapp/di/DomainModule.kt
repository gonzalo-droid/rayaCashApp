package com.gondroid.rayacashapp.di

import com.gondroid.rayacashapp.domain.useCases.GetTotalBalance
import com.gondroid.rayacashapp.domain.useCases.GetTransactions
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetTotalBalance)
    factoryOf(::GetTransactions)
}