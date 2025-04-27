package com.gondroid.rayacashapp.di

import com.gondroid.rayacashapp.domain.useCases.GetCurrentRate
import com.gondroid.rayacashapp.domain.useCases.GetTotalBalance
import com.gondroid.rayacashapp.domain.useCases.UpdateBalance
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetTotalBalance)
    factoryOf(::GetCurrentRate)
    factoryOf(::UpdateBalance)
}