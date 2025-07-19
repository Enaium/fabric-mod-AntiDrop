# AntiDrop

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-AntiDrop?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-OneKeyMiner/releases)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/580810?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/onekeyminer)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/CWgsoj4A?style=flat-square&logo=modrinth)](https://modrinth.com/mod/onekeyminer)


A Fabric mod that prevents players from accidentally dropping valuable items in Minecraft. AntiDrop allows you to create
a customizable list of items that cannot be dropped, helping protect your most important gear and resources.

## ✨ Features

- **Item Protection**: Prevent specific items from being dropped accidentally
- **Customizable List**: Add or remove items from the protection list via commands
- **Visual Interface**: User-friendly GUI for managing protected items
- **Persistent Configuration**: Settings are saved and restored between sessions
- **Multi-Language Support**: Available in English, French, Japanese, and Chinese

## 🚀 Installation

### Prerequisites

- Minecraft
- Fabric Loader
- Fabric API

### Steps

1. Download the appropriate version for your Minecraft version from the releases
2. Place the `.jar` file in your `mods` folder
3. Start Minecraft with Fabric Loader
4. The mod will automatically create a configuration file

## 📋 Commands

AntiDrop provides several commands to manage your protected items:

| Command                   | Description                                  | Usage                         |
|---------------------------|----------------------------------------------|-------------------------------|
| `/antidrop screen`        | Opens the AntiDrop GUI for visual management | `/antidrop screen`            |
| `/antidrop list`          | Shows all currently protected items          | `/antidrop list`              |
| `/antidrop ADD <item>`    | Adds an item to the protection list          | `/antidrop ADD diamond_sword` |
| `/antidrop REMOVE <item>` | Removes an item from the protection list     | `/antidrop REMOVE stone`      |

## 🎮 Usage

### Getting Started

1. **Launch the game** with AntiDrop installed
2. **Add items** to your protection list using commands or the GUI
3. **Test protection** by trying to drop a protected item - it should be prevented

### GUI Interface

- Use `/antidrop screen` to open the visual interface
- Browse through your inventory items
- Click "Add" to protect an item
- Click "Remove" to unprotect an item
- The interface shows all items in your inventory with their protection status

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to
discuss what you would like to change.

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.