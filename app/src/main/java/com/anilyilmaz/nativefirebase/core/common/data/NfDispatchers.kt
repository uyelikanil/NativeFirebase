package com.anilyilmaz.nativefirebase.core.common.data

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val nfDispatchers: NfDispatchers)

enum class NfDispatchers {
    IO,
}