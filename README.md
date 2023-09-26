# item-hunt

> Fabric/Quilt mod, which adds a game-like challenge to gather all items
>> Goal: Collect all items in the game! :)

## Minecraft compatibility
This project is not actively maintained by me anymore. Nevertheless, i will fix bugs and update to a specific version on request. For this simply join my discord linked below.

## Configuration

The items contained in this challenge are configurable via the config file:

<details>
<summary>config/item-hunt/config.json</summary>

```json
{
  "defaultExcludeConfig": {
    "excludeCreatives": true,
    "custom": []
  }
}
```

`defaultExcludeConfig` - object to define the default excluded items for new worlds
<br>`excludeCreatives` - automatically remove all items, which are only available in creative
<br>`custom` - exclude your own items (e.g. minecraft:stone)
</details>

Furthermore, the client settings can be changed via modmenu.
<br> Make sure not to delete any files in the item-hunt folder as there are stored all the progress of the players.

### Other

⚠️ The development version is always the latest stable release of minecraft.
Therefore, new features will only be available for the current and following minecraft versions.

If you need help with any of my mods just join my [discord server](https://nyon.dev/discord).