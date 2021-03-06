/*
 * * Copyright 2020 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.spleefx.command.parent;

import io.github.spleefx.arena.ModeType;
import io.github.spleefx.arena.bow.BowSpleefArena;
import io.github.spleefx.command.sub.CommandManager;
import io.github.spleefx.command.sub.base.*;
import io.github.spleefx.extension.standard.bowspleef.BowSpleefExtension;
import io.github.spleefx.message.MessageKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

import static io.github.spleefx.command.parent.CommandSpleef.HELP;

public class CommandBowSpleef implements TabExecutor {

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0)
            args = HELP;
        if (!CommandManager.BOW_SPLEEF.runSub(command, sender, args))
            MessageKey.UNKNOWN_SUBCOMMAND.send(sender, null, null, null, null,
                    command.getName(), null, -1, BowSpleefExtension.EXTENSION);
        return false;
    }

    static {
        CommandManager.BOW_SPLEEF.registerCommand(
                new StatsCommand(),
                new JoinSubcommand((c) -> BowSpleefExtension.EXTENSION),
                new JoinGUISubcommand(),
                new LeaveSubcommand(),
                new HelpSubcommand(CommandManager.BOW_SPLEEF),
                new ArenaSubcommand<>(ModeType.BOW_SPLEEF, ((key, displayName, regenerationPoint, arenaType, extensionMode) -> new BowSpleefArena(key, displayName, regenerationPoint, arenaType))),
                new ListArenasCommand(c -> ListArenasCommand.fromType(ModeType.BOW_SPLEEF)));
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return CommandSpleefX.onTab(sender, command, args, CommandManager.BOW_SPLEEF);
    }
}