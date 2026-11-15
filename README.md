# Sell All (Fabric 1.21.11)

Client-side mod. When the /sell menu is open, a "Sell All" button moves your whole inventory into the sell menu.

## Status

Not yet tested. The slot layout and the menu-detection check are guesses until they're verified against the real Donut SMP /sell GUI. See the TODOs in `SellAllClient.java`.

## Before the first build

Check `gradle.properties` against https://fabricmc.net/develop/ and update the Minecraft, Yarn, Loader, Loom and Fabric API versions if needed.

## Build

Locally (needs Gradle and Java 21):

    gradle build

Or push to GitHub: the workflow in `.github/workflows/build.yml` builds the jar and uploads it as an artifact.

The jar is in `build/libs/`. Drop it into your `mods` folder alongside Fabric API.

## Server rules

Automated inventory clicks may break server rules. Check the rules of the server you play on before using this.
