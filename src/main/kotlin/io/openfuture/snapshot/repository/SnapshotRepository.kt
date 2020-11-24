package io.openfuture.snapshot.repository

import io.openfuture.snapshot.dto.SnapshotDto
import java.math.BigDecimal

interface SnapshotRepository {

    fun saveAll(snapshots: List<SnapshotDto>)

    fun getAll(): Map<String, BigDecimal>

}
