package com.mezri.deezertestapp.data.errors

import androidx.annotation.StringRes
import com.mezri.deezertestapp.R

/**
 * Enum for app messages
 */
enum class AppMessages(@StringRes private val resourceId: Int) {
    ERROR_UNKNOWN(R.string.unknown_error),
    ALBUMS_NOT_FOUND(R.string.albums_not_found),
    NETWORK_ERROR(R.string.network_error),
    NO_NETWORK_AVAILABLE(R.string.no_network_available);

    fun getAppError(throwable: Throwable? = null): AppInformation {
        if (throwable != null) {
            // TODO send app errors to remote server ( Example: Fabric Crashlytics )
        }
        return AppInformation(this.resourceId, throwable)
    }
}