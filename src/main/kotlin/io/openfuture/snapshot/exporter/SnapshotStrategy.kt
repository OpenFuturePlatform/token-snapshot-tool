package io.openfuture.snapshot.exporter

import java.lang.IllegalArgumentException

enum class SnapshotStrategy {
    ARCHIVED,
    TRANSFER_EVENT;

    companion object {
        fun convert(name: String): SnapshotStrategy {
            return values().find { it.name.toLowerCase() == name }
                    ?: throw IllegalArgumentException("Invalid strategy")
        }
    }
}
