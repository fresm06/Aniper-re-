package com.aniper.app.data.remote.mock

import com.aniper.app.data.remote.dto.CharacterDto
import com.aniper.app.data.remote.dto.CharacterListDto

object MockMarketDataSource {
    fun getMockCharacters(): CharacterListDto {
        val mockCharacters = listOf(
            CharacterDto(
                id = "market_001",
                name = "Fluffy Cloud",
                description = "A soft and fluffy cloud character who loves to float around",
                author = "Cloud Creator",
                isOfficial = true,
                tags = listOf("cloud", "cute", "pastel"),
                idleImage = "asset://fluffy_idle",
                walkLeft = "asset://fluffy_walk_left",
                walkRight = "asset://fluffy_walk_right",
                click = "asset://fluffy_click",
                grab = "asset://fluffy_grab",
                fall = "asset://fluffy_fall",
                land = "asset://fluffy_land",
                downloadCount = 5432,
                isApproved = true
            ),
            CharacterDto(
                id = "market_002",
                name = "Sparkle Star",
                description = "A shiny star that twinkles with joy",
                author = "Star Maker",
                isOfficial = true,
                tags = listOf("star", "sparkle", "magic"),
                idleImage = "asset://sparkle_idle",
                walkLeft = "asset://sparkle_walk_left",
                walkRight = "asset://sparkle_walk_right",
                click = "asset://sparkle_click",
                grab = "asset://sparkle_grab",
                fall = "asset://sparkle_fall",
                land = "asset://sparkle_land",
                downloadCount = 3821,
                isApproved = true
            ),
            CharacterDto(
                id = "market_003",
                name = "Mint Buddy",
                description = "A cool mint-colored companion",
                author = "Cool Creator",
                tags = listOf("mint", "cool", "tech"),
                idleImage = "asset://mint_idle",
                walkLeft = "asset://mint_walk_left",
                walkRight = "asset://mint_walk_right",
                click = "asset://mint_click",
                grab = "asset://mint_grab",
                fall = "asset://mint_fall",
                land = "asset://mint_land",
                downloadCount = 2156,
                isApproved = true
            ),
            CharacterDto(
                id = "market_004",
                name = "Pink Puff",
                description = "A soft pink character with a cheerful personality",
                author = "Pink Pen",
                tags = listOf("pink", "soft", "cute"),
                idleImage = "asset://pink_idle",
                walkLeft = "asset://pink_walk_left",
                walkRight = "asset://pink_walk_right",
                click = "asset://pink_click",
                grab = "asset://pink_grab",
                fall = "asset://pink_fall",
                land = "asset://pink_land",
                downloadCount = 1987,
                isApproved = true
            ),
            CharacterDto(
                id = "market_005",
                name = "Purple Dream",
                description = "A mystical purple character from another dimension",
                author = "Dream Artist",
                tags = listOf("purple", "mystic", "dreamy"),
                idleImage = "asset://purple_idle",
                walkLeft = "asset://purple_walk_left",
                walkRight = "asset://purple_walk_right",
                click = "asset://purple_click",
                grab = "asset://purple_grab",
                fall = "asset://purple_fall",
                land = "asset://purple_land",
                downloadCount = 1654,
                isApproved = true
            ),
            CharacterDto(
                id = "market_006",
                name = "Sunny Boy",
                description = "A cheerful yellow character bringing warmth",
                author = "Sunny Day",
                tags = listOf("yellow", "happy", "warm"),
                idleImage = "asset://sunny_idle",
                walkLeft = "asset://sunny_walk_left",
                walkRight = "asset://sunny_walk_right",
                click = "asset://sunny_click",
                grab = "asset://sunny_grab",
                fall = "asset://sunny_fall",
                land = "asset://sunny_land",
                downloadCount = 1432,
                isApproved = true
            ),
            CharacterDto(
                id = "market_007",
                name = "Cool Breeze",
                description = "A smooth blue character that flows like wind",
                author = "Wind Dancer",
                tags = listOf("blue", "calm", "cool"),
                idleImage = "asset://breeze_idle",
                walkLeft = "asset://breeze_walk_left",
                walkRight = "asset://breeze_walk_right",
                click = "asset://breeze_click",
                grab = "asset://breeze_grab",
                fall = "asset://breeze_fall",
                land = "asset://breeze_land",
                downloadCount = 987,
                isApproved = true
            ),
            CharacterDto(
                id = "market_008",
                name = "Forest Friend",
                description = "A green woodland character",
                author = "Nature Lover",
                tags = listOf("green", "nature", "forest"),
                idleImage = "asset://forest_idle",
                walkLeft = "asset://forest_walk_left",
                walkRight = "asset://forest_walk_right",
                click = "asset://forest_click",
                grab = "asset://forest_grab",
                fall = "asset://forest_fall",
                land = "asset://forest_land",
                downloadCount = 756,
                isApproved = true
            ),
            CharacterDto(
                id = "market_009",
                name = "Rose Bloom",
                description = "A romantic red character with elegance",
                author = "Rose Garden",
                tags = listOf("red", "romantic", "elegant"),
                idleImage = "asset://rose_idle",
                walkLeft = "asset://rose_walk_left",
                walkRight = "asset://rose_walk_right",
                click = "asset://rose_click",
                grab = "asset://rose_grab",
                fall = "asset://rose_fall",
                land = "asset://rose_land",
                downloadCount = 543,
                isApproved = true
            ),
            CharacterDto(
                id = "market_010",
                name = "Night Owl",
                description = "A mysterious dark character for night lovers",
                author = "Midnight Muse",
                tags = listOf("dark", "night", "mysterious"),
                idleImage = "asset://owl_idle",
                walkLeft = "asset://owl_walk_left",
                walkRight = "asset://owl_walk_right",
                click = "asset://owl_click",
                grab = "asset://owl_grab",
                fall = "asset://owl_fall",
                land = "asset://owl_land",
                downloadCount = 421,
                isApproved = true
            )
        )
        return CharacterListDto(characters = mockCharacters, total = mockCharacters.size)
    }

    fun searchCharacters(query: String, tags: List<String> = emptyList()): CharacterListDto {
        val allCharacters = getMockCharacters().characters
        val filtered = allCharacters.filter { character ->
            val nameMatches = character.name.contains(query, ignoreCase = true)
            val descMatches = character.description.contains(query, ignoreCase = true)
            val tagsMatch = if (tags.isNotEmpty()) {
                tags.any { tag -> character.tags.any { it.contains(tag, ignoreCase = true) } }
            } else {
                true
            }
            (nameMatches || descMatches) && tagsMatch
        }
        return CharacterListDto(characters = filtered, total = filtered.size)
    }
}
