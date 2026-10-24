### ZA WARUDO!!!

![DIO activating The World](src/main/resources/za-warudo-jojo-bizarre-adventure.gif)

A Minecraft plugin inspired by **JoJo’s Bizarre Adventure**. Wield the power of **The World**, stop time, and make the server wait for you.

> 🚧 **Early beta:** the core ability is here. More features, improvements, and fixes are planned for future updates.

## Features

- **Stop time for approximately 10 seconds.** Freeze the world and prevent other players who are online at activation from moving.
- **Make an entrance.** A **ZA WARUDO!** title and sound effects announce your ability.
- **Wield the Ban Stick.** Receive a temporary stick during time stop. Hit a frozen player to temporarily ban them from the server.
- **Choose the ability owner.** Set the owner’s Minecraft username in the configuration.
- **Punish the unworthy.** Attempting to use the command without access leaves the player at half a heart. Not everyone can wield **The World**.

## Commands

| Command      | Description            |
| ------------ | ---------------------- |
| `/za_warudo` | Activate time stop     |
| `/zawarudo`  | Alias for `/za_warudo` |
| `/zw`        | Short alias            |

Commands must be used in-game by the configured owner.

## Installation

1. Download the plugin JAR from this repository’s **Releases** section once a build is available.
2. Place the JAR in your server’s `plugins` folder.
3. Start the server to generate the configuration, then stop it.
4. Open `plugins/ZaWarudo/config.yml` and enter your Minecraft username:

```yaml
owner: "YourNickname"
```

5. Start the server and run `/za_warudo`.

**Declared API version:** `1.21.11`.

The plugin requires a Minecraft server that supports Bukkit/Paper plugins and the APIs used by this build.

## Project status

This is the first beta release of **ZaWarudo**, focused on the basic time-stop ability. Expect rough edges as development continues.

Feedback and bug reports are welcome through this repository’s **Issues** section.

*And time resumes…*
