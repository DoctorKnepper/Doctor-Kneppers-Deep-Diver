# Doctor Knepper's Deep Diver

A craftable copper diving suit. Wear the full set to walk the ocean floor like dry land, breathe underwater, see in the deep and swim back up whenever you like.

![Deep Diver](src/main/resources/assets/deepdiver/icon.png)

## The suit

| Piece | Armor | Bonus on its own |
|---|---|---|
| Diving Helmet | 2 | Breathe underwater, night vision while your head is underwater, faster mining underwater |
| Diving Chestplate | 6 | |
| Diving Leggings | 5 | |
| Weighted Diving Boots | 2 | Move faster through water |

The armor values match iron, with a little toughness and knockback resistance. Repair the pieces with copper ingots.

## Full set: walk the sea floor

With all four pieces on:

- You walk on the ocean floor at normal speed instead of swimming.
- You sink at a steady pace and take no fall damage underwater.
- Press jump to jump off the floor, or hold it to rise back up to the surface.
- Swimming (sprint underwater), flying or riding something switches back to normal water movement.

## Recipes

**Diving Helmet**

| | | |
|:-:|:-:|:-:|
| Copper Ingot | Copper Ingot | Copper Ingot |
| Copper Ingot | Glass | Copper Ingot |
|   |   |   |

**Diving Chestplate**

| | | |
|:-:|:-:|:-:|
| Copper Ingot |   | Copper Ingot |
| Copper Ingot | Bucket | Copper Ingot |
| Copper Ingot | Copper Ingot | Copper Ingot |

**Diving Leggings**

| | | |
|:-:|:-:|:-:|
| Copper Ingot | Copper Ingot | Copper Ingot |
| Prismarine Shard |   | Prismarine Shard |
| Copper Ingot |   | Copper Ingot |

**Weighted Diving Boots**

| | | |
|:-:|:-:|:-:|
| Copper Ingot |   | Copper Ingot |
| Iron Ingot |   | Iron Ingot |
|   |   |   |

## Requirements

- Minecraft **26.3** (Java Edition)
- [Fabric Loader](https://fabricmc.net/use/) 0.20.1 or newer
- [Fabric API](https://modrinth.com/mod/fabric-api)

Install it on both the client and the server.

## Installing

Download `deep-diver-<version>.jar` from the [releases page](https://github.com/DoctorKnepper/MC-Deep-Diver/releases), Modrinth or CurseForge, and put it in your `mods` folder along with Fabric API.

## Building from source

You need a Java 25 JDK.

```sh
./gradlew build    # the mod jar ends up in build/libs/
```

## Releasing

Publishing a GitHub release (Releases, then "Draft a new release", with a tag like `v1.0.0`) runs
`.github/workflows/release.yml`. It builds the jar, attaches it to the release and uploads it to Modrinth
and CurseForge. It needs these repository settings (Settings, then "Secrets and variables", then "Actions"):

| Name | Kind | Value |
|---|---|---|
| `MODRINTH_TOKEN` | secret | a Modrinth personal access token with the "Create versions" scope |
| `CURSEFORGE_TOKEN` | secret | a CurseForge API token (from your CurseForge account's API tokens page) |
| `MODRINTH_ID` | variable | the Modrinth project ID |
| `CURSEFORGE_ID` | variable | the CurseForge project ID |

If one platform's values aren't set, the workflow skips that platform. Update `mod_version` in
`gradle.properties` before tagging a new release.

## License

CC0 1.0 (public domain). See [LICENSE](LICENSE).
