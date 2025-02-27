package com.ajeeb.spendie.main.data.utils

import androidx.datastore.preferences.core.doublePreferencesKey

val FOOD_AND_BEV_BUDGET = doublePreferencesKey("food_and_bev_budget")
val TRANSPORT_BUDGET = doublePreferencesKey("transport_budget")
val ENTERTAINMENT_BUDGET = doublePreferencesKey("entertainment_budget")
val BILLS_BUDGET = doublePreferencesKey("bills_budget")
val MISCELLANEOUS_BUDGET = doublePreferencesKey("miscellaneous_budget")

//Api repository
const val INR = "INR"