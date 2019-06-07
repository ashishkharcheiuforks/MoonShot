package com.haroldadmin.moonshot.database.rocket

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.haroldadmin.moonshot.database.BaseDao
import com.haroldadmin.moonshot.models.rocket.PayloadWeight
import com.haroldadmin.moonshot.models.rocket.Rocket
import com.haroldadmin.moonshot.models.rocket.RocketWithPayloadWeights

@Dao
abstract class RocketsDao : BaseDao<Rocket> {

    @Query("SELECT * FROM rockets")
    abstract suspend fun getAllRockets(): List<Rocket>

    @Query("SELECT * FROM rockets WHERE rocket_id = :rocketId")
    abstract suspend fun getRocket(rocketId: String): Rocket

    @Query("SELECT * FROM rockets WHERE rocket_id = :rocketId")
    @Transaction
    abstract suspend fun getRocketWithPayloadWeights(rocketId: String): RocketWithPayloadWeights

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun savePayloadWeights(payloadWeights: List<PayloadWeight>)

    @Transaction
    open suspend fun saveRocketsWithPayloadWeights(rockets: List<Rocket>, payloadWeights: List<PayloadWeight>) {
        saveAll(rockets)
        savePayloadWeights(payloadWeights)
    }

    @Transaction
    open suspend fun saveRocketWithPayloadWeights(rocket: Rocket, payloadWeights: List<PayloadWeight>) {
        save(rocket)
        savePayloadWeights(payloadWeights)
    }

    @Delete
    abstract suspend fun deleteRocket(rocket: Rocket)
}