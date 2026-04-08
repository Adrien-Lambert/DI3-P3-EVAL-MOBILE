package com.example.evalp3.data.local

import com.example.evalp3.data.local.objects.LocationObject
import com.example.evalp3.data.remote.responses.CharacterResponse
import com.example.evalp3.data.remote.responses.LocationResponse
import com.example.evalp3.domain.location.models.Location
import com.example.evalp3.domain.location.models.LocationDetails
import com.example.evalp3.domain.location.models.Resident

/**
 * Extracts character IDs from the list of resident URLs.
 * Each URL looks like "https://rickandmortyapi.com/api/character/1".
 */
private fun List<String>.toIdsCsv(): String =
    mapNotNull { url -> url.trimEnd('/').substringAfterLast('/').toIntOrNull() }
        .joinToString(",")

// --- Response → DB Object ---

fun LocationResponse.toDBObject(): LocationObject = LocationObject(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.toIdsCsv(),
    created = created
)

// --- DB Object → Domain Model ---

fun LocationObject.toModel(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationObject.toDetailedModel(residents: List<Resident>): LocationDetails = LocationDetails(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    residentCount = residents.size
)

// --- Response → Domain Model ---

fun CharacterResponse.toResident(): Resident = Resident(
    id = id,
    name = name,
    avatarUrl = image
)
