package io.openfuture.snapshot.util

import java.util.concurrent.ExecutorService

fun ExecutorService.awaitAll() {
    this.shutdown()
    while (!this.isTerminated) {
    }
}
