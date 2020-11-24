package io.openfuture.snapshot.repository

import java.math.BigDecimal

abstract class InMemorySnapshotRepository : SnapshotRepository {

    protected val snapshotsMap: MutableMap<String, BigDecimal> = mutableMapOf()

    override fun getAll(): Map<String, BigDecimal> = snapshotsMap

}
