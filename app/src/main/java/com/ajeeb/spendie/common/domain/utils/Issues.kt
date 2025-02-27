package com.ajeeb.spendie.common.domain.utils

sealed interface Issues {

    /**Errors that can occur while performing network calls*/
    sealed class Network : Issues {
        data object NoResponse : Network()
        data object NoInternet : Network()
        data object InternalError : Network()
        data object RequestTimeOut : Network()
        data object PayloadTooLarge : Network()
        data object Unknown : Network()
        data object UnableToUpload : Network()
        data object FileNotFound : Network()
        data class ConflictFound(val type: String? = null) : Network()
        data object UnAuthorized : Network()
        data object BadRequest : Network()
        data object MappingFailed : Network()
    }

    /**Errors that can occur while performing operations on Datastore*/
    sealed class DataStore : Issues {
        data object NoSuchData : DataStore()
        data object Unknown : DataStore()
    }

    /** Errors that can occur while accessing local storage files */
    sealed class Storage : Issues {
        data object UnableToAccessFile : Storage()
        data object FileNotFound : Storage()
        data object PermissionDenied : Storage()
        data object IoError : Storage()
        data object InsufficientStorage : Storage()
        data object InvalidUri : Storage()
        data object UnsupportedFileType : Storage()
        data object SecurityException : Storage()
        data object FileTooLarge : Storage()
        data object FileAlreadyExists : Storage()
    }

    /**Errors that can occur while performing validations*/
    sealed class Validation : Issues {
        data object TooLong : Validation()
        data object TooSmall : Validation()
        data object Blank : Validation()
        data object Invalid : Validation()
        data object UnsupportedFile : Validation()
        data object NotVerified : Validation()
        data object FileTooLarge : Validation()
        data object InvalidEmail : Validation()
        data object MinAge : Validation()
    }

    sealed class Database : Issues {
        data object Unknown : Database()
    }
}