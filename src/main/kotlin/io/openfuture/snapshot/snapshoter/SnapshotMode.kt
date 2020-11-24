package io.openfuture.snapshot.snapshoter

enum class SnapshotMode {
    ARCHIVED,
    EVENT;

    companion object {
        fun convert(name: String): SnapshotMode {
            return values().find { it.name.toLowerCase() == name }
                    ?: throw IllegalArgumentException("Invalid mode")
        }
    }

}
