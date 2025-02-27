package com.ajeeb.spendie.common.presentation.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

/**This extension streamlines access to read-only data from the ViewModel's state.*/
val <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.currentState: STATE
    get() = container.stateFlow.value

/**Extension function to simplify state updates in an Orbit MVI `ContainerHost`.
 * It reduces boilerplate by directly applying the state update logic.*/
@OptIn(OrbitExperimental::class)
suspend inline fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.reduceState(
    crossinline update: STATE.() -> STATE
) = subIntent {
    reduce { state.update() }
}

/**Extension function to simplify posting side effects in an Orbit MVI `ContainerHost`.
 *  It reduces boilerplate by directly triggering the side effect.*/
inline fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.postSideEffect(
    crossinline sideEffect: () -> SIDE_EFFECT
) = intent {
    this.postSideEffect(sideEffect())
}

/**
 * Creates a custom `NavType` for passing complex data classes as navigation arguments using JSON serialization.
 */
class SpendieNavType<T>(private val serializer: KSerializer<T>) :
    NavType<T>(isNullableAllowed = true) {

    override fun get(bundle: Bundle, key: String): T? {
        val jsonString = bundle.getString(key) ?: return null
        return Json.decodeFromString(serializer, jsonString)
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }
}

/**
 * Creates a custom `NavType` for passing complex data classes as navigation arguments using JSON serialization.
 */
inline fun <reified T> navType(): NavType<T> {
    return object : NavType<T>(isNullableAllowed = false) {

        override fun get(bundle: Bundle, key: String): T? {
            val jsonString = bundle.getString(key) ?: return null
            return Json.decodeFromString(jsonString)
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}